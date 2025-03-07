var pcpRPMCalculateMaintainingHandsontableHelper=null;
Ext.define("AP.view.dataMaintaining.PCPCalculateMaintainingInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PCPCalculateMaintainingInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var bbar = new Ext.toolbar.Paging({
        	id:'PCPFESDiagramCalculateMaintainingBbar',
            pageSize: defaultPageSize,
//            displayMsg: '当前 {0}~{1}条  共 {2} 条',
//            emptyMsg: "没有记录可显示",
//            prevText: "上一页",
//            nextText: "下一页",
//            refreshText: "刷新",
//            lastText: "最后页",
//            firstText: "第一页",
//            beforePageText: "当前页",
//            afterPageText: "共{0}页",
            displayInfo: true
        });
        var wellListStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
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
                    var deviceName = Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceName: deviceName,
                        calculateType: 2
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellListComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: deviceShowName,
                    id: 'PCPCalculateMaintainingWellListComBox_Id',
                    store: wellListStore,
                    labelWidth: 8*deviceShowNameLength,
                    width: (8*deviceShowNameLength+110),
                    queryMode: 'remote',
                    emptyText: '--'+loginUserLanguageResource.all+'--',
                    blankText: '--'+loginUserLanguageResource.all+'--',
                    typeAhead: true,
                    autoSelect: false,
                    allowBlank: true,
                    triggerAction: 'all',
                    editable: true,
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize: comboxPagingStatus,
                    minChars: 0,
                    listeners: {
                    	expand: function (sm, selections) {
                    		wellListComb.getStore().loadPage(1);
                        },
                        select: function (combo, record, index) {
                        	calculateSignComb.clearValue();
                			var gridPanel = Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().load();
            				}else{
            					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingWellListStore');
            				}
                        }
                    }
                });
        
        var calculateSignStore = new Ext.data.JsonStore({
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/calculateManagerController/getCalculateStatusList',
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
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    var welName=Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').getValue();
                    var startDate=Ext.getCmp('PCPCalculateMaintainingStartDate_Id').rawValue;
                    var endDate=Ext.getCmp('PCPCalculateMaintainingEndDate_Id').rawValue;
                    var new_params = {
                    		orgId: orgId,
                    		welName: welName,
                            startDate:startDate,
                            endDate:endDate,
                            deviceType:1
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var calculateSignComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: loginUserLanguageResource.resultStatus,
                    labelWidth: getStringLength(loginUserLanguageResource.resultStatus)*8,
                    width: (getStringLength(loginUserLanguageResource.resultStatus)*8+100),
                    id: 'PCPCalculateMaintainingCalculateSignComBox_Id',
                    store: calculateSignStore,
                    queryMode: 'remote',
                    emptyText: '--'+loginUserLanguageResource.all+'--',
                    blankText: '--'+loginUserLanguageResource.all+'--',
                    typeAhead: false,
                    autoSelect: false,
                    allowBlank: true,
                    triggerAction: 'all',
                    editable: false,
                    displayField: "boxval",
                    valueField: "boxkey",
                    minChars: 0,
                    listeners: {
                    	expand: function (sm, selections) {
                    		calculateSignComb.clearValue();
                    		calculateSignComb.getStore().load(); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
//                        	var gridPanel = Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id");
//            				if (isNotVal(gridPanel)) {
//            					gridPanel.getStore().load();
//            				}else{
//            					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingWellListStore');
//            				}
            				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
            				if (isNotVal(bbar)) {
            					if(bbar.getStore().isEmptyStore){
            						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
            						bbar.setStore(PCPCalculateMaintainingDataStore);
            					}else{
            						bbar.getStore().loadPage(1);
            					}
            				}else{
            					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
            				}
                        }
                    }
        });
        Ext.apply(me, {
        	layout: 'border',
            border: false,
            tbar:{
            	xtype:"container",
            	border:false,
            	items:[{
            	     //tbar第一行工具栏
            	     xtype:"toolbar",
            	     items : [{
                         id: 'PCPCalculateMaintainingDeviceListSelectRow_Id',
                         xtype: 'textfield',
                         value: -1,
                         hidden: true
                     },{
                         xtype: 'button',
                         text: loginUserLanguageResource.refresh,
                         iconCls: 'note-refresh',
                         hidden:false,
                         handler: function (v, o) {
                         	refreshPCPCalculateMaintainingData();
                         }
             		},'-',wellListComb
             			,"-",{
                         xtype: 'datefield',
                         anchor: '100%',
                         fieldLabel: '',
                         labelWidth: 0,
                         width: 90,
                         format: 'Y-m-d ',
                         id: 'PCPCalculateMaintainingStartDate_Id',
                         value: '',
                         listeners: {
                         	select: function (combo, record, index) {
                         		calculateSignComb.clearValue();
                         		var activeId = Ext.getCmp("PCPCalculateMaintainingTabPanel").getActiveTab().id;
                     			if(activeId=="PCPCalculateMaintainingPanel"){
                     				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
                     				if (isNotVal(bbar)) {
                     					if(bbar.getStore().isEmptyStore){
                     						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
                     						bbar.setStore(PCPCalculateMaintainingDataStore);
                     					}else{
                     						bbar.getStore().loadPage(1);
                     					}
                     				}else{
                     					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
                     				}
                     			}else if(activeId=="PCPTotalCalculateMaintainingPanel"){
                     				var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
                     	            if (isNotVal(gridPanel)) {
                     	            	gridPanel.getStore().loadPage(1);
                     	            }else{
                     	            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
                     	            }
                     			}
                             }
                         }
                     },{
                     	xtype: 'numberfield',
                     	id: 'PCPCalculateMaintainingStartTime_Hour_Id',
                     	fieldLabel: loginUserLanguageResource.hour,
                         labelWidth: getStringLength(loginUserLanguageResource.hour)*8,
                         width: getStringLength(loginUserLanguageResource.hour)*8+45,
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
                     	id: 'PCPCalculateMaintainingStartTime_Minute_Id',
                     	fieldLabel: loginUserLanguageResource.minute,
                         labelWidth: getStringLength(loginUserLanguageResource.minute)*8,
                         width: getStringLength(loginUserLanguageResource.minute)*8+45,
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
                     	id: 'PCPCalculateMaintainingStartTime_Second_Id',
                     	fieldLabel: loginUserLanguageResource.second,
                         labelWidth: getStringLength(loginUserLanguageResource.second)*8,
                         width: getStringLength(loginUserLanguageResource.second)*8+45,
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
                         labelWidth: getStringLength(loginUserLanguageResource.timeTo)*8,
                         width: getStringLength(loginUserLanguageResource.timeTo)*8+95,
                         format: 'Y-m-d ',
                         id: 'PCPCalculateMaintainingEndDate_Id',
                         value: '',
                         listeners: {
                         	select: function (combo, record, index) {
                         		calculateSignComb.clearValue();
                         		var activeId = Ext.getCmp("PCPCalculateMaintainingTabPanel").getActiveTab().id;
                     			if(activeId=="PCPCalculateMaintainingPanel"){
                     				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
                     				if (isNotVal(bbar)) {
                     					if(bbar.getStore().isEmptyStore){
                     						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
                     						bbar.setStore(PCPCalculateMaintainingDataStore);
                     					}else{
                     						bbar.getStore().loadPage(1);
                     					}
                     				}else{
                     					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
                     				}
                     			}else if(activeId=="PCPTotalCalculateMaintainingPanel"){
                     				var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
                     	            if (isNotVal(gridPanel)) {
                     	            	gridPanel.getStore().loadPage(1);
                     	            }else{
                     	            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
                     	            }
                     			}
                             }
                         }
                     },{
                     	xtype: 'numberfield',
                     	id: 'PCPCalculateMaintainingEndTime_Hour_Id',
                     	fieldLabel: loginUserLanguageResource.hour,
                         labelWidth: getStringLength(loginUserLanguageResource.hour)*8,
                         width: getStringLength(loginUserLanguageResource.hour)*8+45,
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
                     	id: 'PCPCalculateMaintainingEndTime_Minute_Id',
                     	fieldLabel: loginUserLanguageResource.minute,
                         labelWidth: getStringLength(loginUserLanguageResource.minute)*8,
                         width: getStringLength(loginUserLanguageResource.minute)*8+45,
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
                     	id: 'PCPCalculateMaintainingEndTime_Second_Id',
                     	fieldLabel: loginUserLanguageResource.second,
                         labelWidth: getStringLength(loginUserLanguageResource.second)*8,
                         width: getStringLength(loginUserLanguageResource.second)*8+45,
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
                     },"-",calculateSignComb,'-',{
                         xtype: 'button',
                         text: loginUserLanguageResource.search,
                         iconCls: 'search',
                         pressed: false,
                         hidden:false,
                         handler: function (v, o) {
                         	var activeId = Ext.getCmp("PCPCalculateMaintainingTabPanel").getActiveTab().id;
                 			if(activeId=="PCPCalculateMaintainingPanel"){
                 				var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                             	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                             	var startTime_Hour=Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').getValue();
                             	if(!r.test(startTime_Hour)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                             		Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').focus(true, 100);
                             		return;
                             	}
                             	var startTime_Minute=Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').getValue();
                             	if(!r2.test(startTime_Minute)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                             		Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').focus(true, 100);
                             		return;
                             	}
                             	var startTime_Second=Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').getValue();
                             	if(!r2.test(startTime_Second)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                             		Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').focus(true, 100);
                             		return;
                             	}
                             	
                             	var endTime_Hour=Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').getValue();
                             	if(!r.test(endTime_Hour)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                             		Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').focus(true, 100);
                             		return;
                             	}
                             	var endTime_Minute=Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').getValue();
                             	if(!r2.test(endTime_Minute)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                             		Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').focus(true, 100);
                             		return;
                             	}
                             	var endTime_Second=Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').getValue();
                             	if(!r2.test(endTime_Second)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                             		Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').focus(true, 100);
                             		return;
                             	}
                 				var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
                 				if (isNotVal(bbar)) {
                 					if(bbar.getStore().isEmptyStore){
                 						var PCPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
                 						bbar.setStore(PCPCalculateMaintainingDataStore);
                 					}else{
                 						bbar.getStore().loadPage(1);
                 					}
                 				}else{
                 					Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore');
                 				}
                 			}else if(activeId=="PCPTotalCalculateMaintainingPanel"){
                 				var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
                 	            if (isNotVal(gridPanel)) {
                 	            	gridPanel.getStore().loadPage(1);
                 	            }else{
                 	            	Ext.create("AP.store.dataMaintaining.PCPTotalCalculateMaintainingDataStore");
                 	            }
                 			}
                         }
                     
             		}]
            	},{
            	     //tbar第二行工具栏
            	     xtype:"toolbar",
            	     items : ['->',{
                         xtype: 'button',
                         text: loginUserLanguageResource.editHistoryDataCalculate,
                         disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                         id:'PCPCalculateMaintainingUpdateDataBtn',
                         pressed: false,
                         iconCls: 'edit',
                         handler: function (v, o) {
                         	pcpRPMCalculateMaintainingHandsontableHelper.saveData();
                         }
                     },"-",{
                         xtype: 'button',
                         text: loginUserLanguageResource.linkProductionDataCalculate,
                         disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                         pressed: false,
                         iconCls: 'save',
                         id:'PCPCalculateMaintainingLinkedDataBtn',
                         handler: function (v, o) {
                         	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                         	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                         	var startTime_Hour=Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').getValue();
                         	if(!r.test(startTime_Hour)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                         		Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').focus(true, 100);
                         		return;
                         	}
                         	var startTime_Minute=Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').getValue();
                         	if(!r2.test(startTime_Minute)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                         		Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').focus(true, 100);
                         		return;
                         	}
                         	var startTime_Second=Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').getValue();
                         	if(!r2.test(startTime_Second)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                         		Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').focus(true, 100);
                         		return;
                         	}
                         	
                         	var endTime_Hour=Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').getValue();
                         	if(!r.test(endTime_Hour)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                         		Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').focus(true, 100);
                         		return;
                         	}
                         	var endTime_Minute=Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').getValue();
                         	if(!r2.test(endTime_Minute)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                         		Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').focus(true, 100);
                         		return;
                         	}
                         	var endTime_Second=Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').getValue();
                         	if(!r2.test(endTime_Second)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                         		Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').focus(true, 100);
                         		return;
                         	}
                         	
                         	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                             var deviceName=Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').getValue();
                             var startDate=Ext.getCmp('PCPCalculateMaintainingStartDate_Id').rawValue;
                             var startTime_Hour=Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').getValue();
                         	var startTime_Minute=Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').getValue();
                         	var startTime_Second=Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').getValue();
                             var endDate=Ext.getCmp('PCPCalculateMaintainingEndDate_Id').rawValue;
                             var endTime_Hour=Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').getValue();
                         	var endTime_Minute=Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').getValue();
                         	var endTime_Second=Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').getValue();
                             var calculateSign=Ext.getCmp('PCPCalculateMaintainingCalculateSignComBox_Id').getValue();
                             var deviceType=1;
                             var calculateType=2;
                             var showDeviceName=deviceName;
                             if(deviceName == '' || deviceName == null){
                         		if(calculateType==1){
                         			showDeviceName=loginUserLanguageResource.allSRPCalculateWell;
                         		}else if(calculateType==2){
                         			showDeviceName=loginUserLanguageResource.allPCPCalculateWell;
                         		}
                         	}else{
//                         		showDeviceName+='井';
                         	}
                         	var operaName=loginUserLanguageResource.takeEffectScope+":"+showDeviceName+" "+getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second)+"~"+getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)+" </br><font color=red>"+loginUserLanguageResource.calculateMaintainingConfirm+"</font>"
                         	Ext.Msg.confirm(loginUserLanguageResource.confirm, operaName, function (btn) {
                                 if (btn == "yes") {
                                 	Ext.Ajax.request({
                 	            		method:'POST',
                 	            		url:context + '/calculateManagerController/recalculateByProductionData',
                 	            		success:function(response) {
                 	            			var rdata=Ext.JSON.decode(response.responseText);
                 	            			if (rdata.success) {
                 	                        	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.calculateMaintainingEditSuccessInfo);
                 	                            //保存以后重置全局容器
                 	                            pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
                 	                            Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar").getStore().loadPage(1);
                 	                        } else {
                 	                        	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.operationFailure);

                 	                        }
                 	            		},
                 	            		failure:function(){
                 	            			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
                 	                        pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
                 	            		},
                 	            		params: {
                 	            			orgId: orgId,
                 	            			deviceName: deviceName,
                 	                        startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                 	                        endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                 	                        calculateSign:calculateSign,
                 	                        calculateType:calculateType,
                 	                        deviceType:deviceType
                 	                    }
                 	            	}); 
                                 }
                             });
                         }
                     },"-",{
                         xtype: 'button',
                         text: loginUserLanguageResource.exportRequestData,
                         disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                         pressed: false,
                         hidden: false,
                         iconCls: 'export',
                         id:'PCPCalculateMaintainingExportDataBtn',
                         handler: function (v, o) {
                         	if(pcpRPMCalculateMaintainingHandsontableHelper.hot.getSelected()){
                         		var row=pcpRPMCalculateMaintainingHandsontableHelper.hot.getSelected()[0][0];
                         		
                         		var recordId=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(row,'recordId');
                          		var deviceName=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(row,'deviceName');
                          		var acqTime=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(row,'acqTime');
                         		
                         		
                         		var calculateType=2;//1-抽油机井诊断计产 2-螺杆泵井诊断计产 3-抽油机井汇总计算  4-螺杆泵井汇总计算 5-电参反演地面功图计算
                         		var url=context + '/calculateManagerController/exportCalculateRequestData?recordId='+recordId+'&deviceName='+URLencode(URLencode(deviceName))+'&acqTime='+acqTime+'&calculateType='+calculateType;
                             	document.location.href = url;
                         	}else{
                         		Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.noSelectionRecord);
                         	}
                         }
                     },"-",{
                         xtype: 'button',
                         text: loginUserLanguageResource.deleteData,
                         disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                         pressed: false,
                         hidden: false,
                         iconCls: 'delete',
                         id:'PCPCalculateMaintainingDeleteDataBtn',
                         handler: function (v, o) {
                        	 var checkedStatus=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtProp('checked');
                        	 var deleteRecordList=[];
                        	 
                        	 var deviceId=0;
                        	 var selectRow= Ext.getCmp("PCPCalculateMaintainingDeviceListSelectRow_Id").getValue();
                        	 if(selectRow>=0){
                         		deviceId=Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                         	 }
                        	 
                        	 if(checkedStatus.length>0){
                        		 for(var i=0;i<checkedStatus.length;i++){
                        			 if(checkedStatus[i]){
                        				 var recordId=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(i,'recordId'); 
                        				 deleteRecordList.push(recordId);
                        			 }
                        		 }
                        	 }
                        	 if(deleteRecordList.length>0){
                        		 deleteCalculateData(deviceId,deleteRecordList,2);
                        	 }else{
                        		 Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.noSelectionRecord);
                        	 }
                         }
                     },{
                         xtype: 'button',
                         text: loginUserLanguageResource.reTotalCalculate,
                         disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                         id:'PCPCalculateMaintainingReTotalBtn',
                         pressed: false,
                         hidden:true,
                         iconCls: 'edit',
                         handler: function (v, o) {
                         	ReTotalRPMData();
                         }
                     },"-",{
                         xtype: 'button',
                         text: loginUserLanguageResource.exportRequestData,
                         disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                         pressed: false,
                         hidden: true,
                         iconCls: 'export',
                         id:'PCPTotalCalculateMaintainingExportDataBtn',
                         handler: function (v, o) {
                         	var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
                             var selectionModel = gridPanel.getSelectionModel();
                             var _record = selectionModel.getSelection();
                             if (_record.length>0) {
                             	var recordId=_record[0].data.id;
                             	var wellId=_record[0].data.wellId;
                             	var deviceName=_record[0].data.deviceName;
                             	var calDate=_record[0].data.calDate;
                         		var deviceType=1;
                         		var url=context + '/calculateManagerController/exportTotalCalculateRequestData?recordId='+recordId
                         		+'&wellId='+wellId
                         		+'&deviceName='+URLencode(URLencode(deviceName))
                         		+'&calDate='+calDate
                         		+'&deviceType='+deviceType;
                             	document.location.href = url;
                             }else{
                             	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.noSelectionRecord);
                             }
                         }
                     }]
            	}]
            },
        	items: [{
        		region: 'west',
            	width: '30%',
            	title: loginUserLanguageResource.deviceList,
            	id: 'PCPCalculateMaintainingWellListPanel_Id',
            	collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
            	layout: "fit"
            },{
            	region: 'center',
            	xtype: 'tabpanel',
        		id:"PCPCalculateMaintainingTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        			title: loginUserLanguageResource.singleRecord,
    				layout: "fit",
    				id:'PCPCalculateMaintainingPanel',
    				iconCls: 'check3',
    				border: false,
    				bbar: bbar,
    				html:'<div class=PCPCalculateMaintainingContainer" style="width:100%;height:100%;"><div class="con" id="PCPCalculateMaintainingDiv_id"></div></div>',
    				listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(pcpRPMCalculateMaintainingHandsontableHelper!=null && pcpRPMCalculateMaintainingHandsontableHelper.hot!=undefined){
//                        		pcpRPMCalculateMaintainingHandsontableHelper.hot.refreshDimensions();
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		newHeight-=29;
                        		pcpRPMCalculateMaintainingHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
        		},{
        			title: loginUserLanguageResource.recordTotal,
    				layout: "fit",
    				id:'PCPTotalCalculateMaintainingPanel',
    				border: false,
    				html:'<div class=PCPTotalCalculateMaintainingContainer" style="width:100%;height:100%;"><div class="con" id="PCPTotalCalculateMaintainingDiv_id"></div></div>',
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
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
    					if(newCard.id=="PCPCalculateMaintainingPanel"){
    						Ext.getCmp("PCPCalculateMaintainingUpdateDataBtn").show();
    						Ext.getCmp("PCPCalculateMaintainingLinkedDataBtn").show();
    						Ext.getCmp("PCPCalculateMaintainingExportDataBtn").show();
    						Ext.getCmp("PCPCalculateMaintainingDeleteDataBtn").show();
    						Ext.getCmp("PCPCalculateMaintainingCalculateSignComBox_Id").show();
    						Ext.getCmp("PCPCalculateMaintainingReTotalBtn").hide();
    						Ext.getCmp("PCPTotalCalculateMaintainingExportDataBtn").hide();
    						
    						Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').show();
    						Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').show();
    						Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').show();
    						Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').show();
    						Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').show();
    						Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').show();
    					}else if(newCard.id=="PCPTotalCalculateMaintainingPanel"){
    						Ext.getCmp("PCPCalculateMaintainingUpdateDataBtn").hide();
    						Ext.getCmp("PCPCalculateMaintainingLinkedDataBtn").hide();
    						Ext.getCmp("PCPCalculateMaintainingExportDataBtn").hide();
    						Ext.getCmp("PCPCalculateMaintainingDeleteDataBtn").hide();
    						Ext.getCmp("PCPCalculateMaintainingCalculateSignComBox_Id").hide();
    						Ext.getCmp("PCPCalculateMaintainingReTotalBtn").show();
    						Ext.getCmp("PCPTotalCalculateMaintainingExportDataBtn").show();
    						
    						Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').hide();
    						Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').hide();
    						Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').hide();
    						Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').hide();
    						Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').hide();
    						Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').hide();
    					}
    					refreshPCPCalculateMaintainingData();
    				}
    			}
            }]
        });
        me.callParent(arguments);
    }
});


function CreateAndLoadPCPCalculateMaintainingTable(isNew,result,divid){
	if(isNew&&pcpRPMCalculateMaintainingHandsontableHelper!=null){
        pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
        if(pcpRPMCalculateMaintainingHandsontableHelper.hot!=undefined){
        	pcpRPMCalculateMaintainingHandsontableHelper.hot.destroy();
        }
        pcpRPMCalculateMaintainingHandsontableHelper=null;
	}
	var applicationScenarios=result.applicationScenarios;
	if(pcpRPMCalculateMaintainingHandsontableHelper==null){
		pcpRPMCalculateMaintainingHandsontableHelper = PCPRPMCalculateMaintainingHandsontableHelper.createNew(divid);
		var colHeaders="['',";
        var columns="[{data:'checked',type:'checkbox'},";
        for(var i=0;i<result.columns.length;i++){
        	var colHeader="'" + result.columns[i].header + "'";
            var dataIndex=result.columns[i].dataIndex;
            
            if(applicationScenarios==0){
            	if(dataIndex.toUpperCase() === "crudeOilDensity".toUpperCase() 
            			|| dataIndex.toUpperCase() === "saturationPressure".toUpperCase() 
            			|| dataIndex.toUpperCase() === "waterCut".toUpperCase() 
            			|| dataIndex.toUpperCase() === "weightWaterCut".toUpperCase() 
            			|| dataIndex.toUpperCase() === "productionGasOilRatio".toUpperCase() ){
            		continue;
            	}else if(dataIndex.toUpperCase() === "reservoirDepth".toUpperCase() || dataIndex.toUpperCase() === "reservoirTemperature".toUpperCase()){
            		colHeader=colHeader.replace(loginUserLanguageResource.reservoirDepth,loginUserLanguageResource.reservoirDepth_cbm);
            		colHeader=colHeader.replace(loginUserLanguageResource.reservoirTemperature,loginUserLanguageResource.reservoirTemperature_cbm);
            	}else if(dataIndex.toUpperCase() === "TubingPressure".toUpperCase()){
            		colHeader=colHeader.replace(loginUserLanguageResource.tubingPressure,loginUserLanguageResource.tubingPressure_cbm);
                }
            }
            colHeaders += colHeader;
        	columns+="{data:'"+dataIndex+"'";
        	if(dataIndex.toUpperCase()=="id".toUpperCase()){
//        		columns+=",type: 'checkbox'";
        	}else if(dataIndex.toUpperCase()==="deviceName".toUpperCase()||dataIndex.toUpperCase()==="acqTime".toUpperCase()||dataIndex.toUpperCase()==="resultName".toUpperCase()){
    			
    		}else if(dataIndex==="anchoringStateName"){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['锚定', '未锚定']";
        	}else if(dataIndex.toUpperCase()==="barrelTypeName".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.barrelType_L+"', '"+loginUserLanguageResource.barrelType_H+"']";
        	}else if(dataIndex.toUpperCase()==="pumpTypeName".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['杆式泵', '管式泵']";
        	}else if(dataIndex.toUpperCase()==="pumpGrade".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['1', '2','3', '4','5']";
        	}else if(dataIndex.toUpperCase()==="rodGrade1".toUpperCase() || dataIndex.toUpperCase()==="rodGrade2".toUpperCase() || dataIndex.toUpperCase()==="rodGrade3".toUpperCase() || dataIndex.toUpperCase()==="rodGrade4".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['','A','B','C','D','K','KD','HL','HY'], validator: function(val, callback){return handsontableDataCheck_RodGrade(val, callback,this.row, this.col,pcpRPMCalculateMaintainingHandsontableHelper);}";
        	}else if(dataIndex.toUpperCase()==="rodTypeName1".toUpperCase() || dataIndex.toUpperCase()==="rodTypeName2".toUpperCase() || dataIndex.toUpperCase()==="rodTypeName3".toUpperCase() || dataIndex.toUpperCase()==="rodTypeName4".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['','"+loginUserLanguageResource.rodStringTypeValue1+"','"+loginUserLanguageResource.rodStringTypeValue2+"','"+loginUserLanguageResource.rodStringTypeValue3+"'], validator: function(val, callback){return handsontableDataCheck_RodType(val, callback,this.row, this.col,pcpRPMCalculateMaintainingHandsontableHelper);}";
        	}else{
    			columns+=",type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,pcpRPMCalculateMaintainingHandsontableHelper);}";
    		}
        	columns+="}";
        	if(i<result.columns.length-1){
        		colHeaders+=",";
            	columns+=",";
        	}
        }
        colHeaders+=",'recordId'";
        columns+=",{data: 'recordId'}";
        colHeaders+="]";
    	columns+="]";
    	pcpRPMCalculateMaintainingHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
    	pcpRPMCalculateMaintainingHandsontableHelper.columns=Ext.JSON.decode(columns);
    	
    	if(result.totalRoot.length==0){
    		pcpRPMCalculateMaintainingHandsontableHelper.createTable([{}]);
        }else{
        	pcpRPMCalculateMaintainingHandsontableHelper.createTable(result.totalRoot);
        }
	}else{
		if(result.totalRoot.length==0){
			pcpRPMCalculateMaintainingHandsontableHelper.hot.loadData([{}]);
    	}else{
    		pcpRPMCalculateMaintainingHandsontableHelper.hot.loadData(result.totalRoot);
    	}
	}
};


var PCPRPMCalculateMaintainingHandsontableHelper = {
	    createNew: function (divid) {
	        var pcpRPMCalculateMaintainingHandsontableHelper = {};
	        pcpRPMCalculateMaintainingHandsontableHelper.hot = '';
	        pcpRPMCalculateMaintainingHandsontableHelper.divid = divid;
	        pcpRPMCalculateMaintainingHandsontableHelper.validresult=true;//数据校验
	        pcpRPMCalculateMaintainingHandsontableHelper.colHeaders=[];
	        pcpRPMCalculateMaintainingHandsontableHelper.columns=[];
	        pcpRPMCalculateMaintainingHandsontableHelper.hiddenRows = [];
	        
	        pcpRPMCalculateMaintainingHandsontableHelper.AllData={};
	        pcpRPMCalculateMaintainingHandsontableHelper.updatelist=[];
	        pcpRPMCalculateMaintainingHandsontableHelper.delidslist=[];
	        pcpRPMCalculateMaintainingHandsontableHelper.insertlist=[];
	        
	        pcpRPMCalculateMaintainingHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(pcpRPMCalculateMaintainingHandsontableHelper.columns[col].type=='checkbox'){
	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
	        	}else if(pcpRPMCalculateMaintainingHandsontableHelper.columns[col].type=='dropdown'){
	        		Handsontable.renderers.DropdownRenderer.apply(this, arguments);
	        	}else{
	        		Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	}
	        	
	            if(col<=8 && col>=1){
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
            	
            	if(pcpRPMCalculateMaintainingHandsontableHelper.columns[col].type!='checkbox'){
            		td.style.whiteSpace='nowrap'; //文本不换行
                	td.style.overflow='hidden';//超出部分隐藏
                	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        	}
	        }
	        
	        pcpRPMCalculateMaintainingHandsontableHelper.addReadOnlyCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(pcpRPMCalculateMaintainingHandsontableHelper.columns[col].type=='checkbox'){
	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
	        	}else if(pcpRPMCalculateMaintainingHandsontableHelper.columns[col].type=='dropdown'){
	        		Handsontable.renderers.DropdownRenderer.apply(this, arguments);
	        	}else{
	        		Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	}
	        	
	        	td.style.backgroundColor = 'rgb(245, 245, 245)';
            	
            	if(pcpRPMCalculateMaintainingHandsontableHelper.columns[col].type!='checkbox'){
            		td.style.whiteSpace='nowrap'; //文本不换行
                	td.style.overflow='hidden';//超出部分隐藏
                	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        	}
	        }
	        
	        
	        pcpRPMCalculateMaintainingHandsontableHelper.createTable = function (data) {
	        	$('#'+pcpRPMCalculateMaintainingHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+pcpRPMCalculateMaintainingHandsontableHelper.divid);
	        	pcpRPMCalculateMaintainingHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		fixedColumnsLeft:4, //固定左侧多少列不能水平滚动
	                hiddenColumns: {
	                    columns: [pcpRPMCalculateMaintainingHandsontableHelper.columns.length-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                hiddenRows: {
	                    rows: [],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:pcpRPMCalculateMaintainingHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:pcpRPMCalculateMaintainingHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                outsideClickDeselects: false, // 点击到表格以外，表格就失去focus
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    var CalculateMaintainingModuleEditFlag=parseInt(Ext.getCmp("CalculateMaintainingModuleEditFlag").getValue());
	                    if(CalculateMaintainingModuleEditFlag==1){
	                    	if (visualColIndex >= 1 && visualColIndex <= 7) {
								cellProperties.readOnly = true;
			                }
	                    	cellProperties.renderer = pcpRPMCalculateMaintainingHandsontableHelper.addCellStyle;
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer=pcpRPMCalculateMaintainingHandsontableHelper.addReadOnlyCellStyle;
		                }
	                    return cellProperties;
	                },
	                afterDestroy: function() {
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    //封装id成array传入后台
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var recordId=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(i,'recordId');
	                            ids.push(rowdata[recordId]);
	                        }
	                        pcpRPMCalculateMaintainingHandsontableHelper.delExpressCount(ids);
	                        pcpRPMCalculateMaintainingHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRow(index);
		                        var recordId=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(index,'recordId');
		                        params.push(recordId);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<pcpRPMCalculateMaintainingHandsontableHelper.columns.length;j++){
		                        		data+=pcpRPMCalculateMaintainingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<pcpRPMCalculateMaintainingHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            pcpRPMCalculateMaintainingHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(pcpRPMCalculateMaintainingHandsontableHelper!=null&&pcpRPMCalculateMaintainingHandsontableHelper.hot!=''&&pcpRPMCalculateMaintainingHandsontableHelper.hot!=undefined && pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	      //插入的数据的获取
	        pcpRPMCalculateMaintainingHandsontableHelper.insertExpressCount=function() {
	            var idsdata = pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = pcpRPMCalculateMaintainingHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<pcpRPMCalculateMaintainingHandsontableHelper.columns.length;j++){
                        		data+=pcpRPMCalculateMaintainingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<pcpRPMCalculateMaintainingHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        pcpRPMCalculateMaintainingHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (pcpRPMCalculateMaintainingHandsontableHelper.insertlist.length != 0) {
	            	pcpRPMCalculateMaintainingHandsontableHelper.AllData.insertlist = pcpRPMCalculateMaintainingHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        pcpRPMCalculateMaintainingHandsontableHelper.saveData = function () {
        		//插入的数据的获取
	        	pcpRPMCalculateMaintainingHandsontableHelper.insertExpressCount();
	            if (JSON.stringify(pcpRPMCalculateMaintainingHandsontableHelper.AllData) != "{}" && pcpRPMCalculateMaintainingHandsontableHelper.validresult) {
	            	var bbarId="PCPFESDiagramCalculateMaintainingBbar";
	            	var deviceType=1;
	            	var calculateType=2;//1-抽油机井诊断计产 2-螺杆泵井诊断计产 3-抽油机井汇总计算  4-螺杆泵井汇总计算 5-电参反演地面功图计算
	            	
	            	var applicationScenarios=0;
	            	var selectRow= Ext.getCmp("PCPCalculateMaintainingDeviceListSelectRow_Id").getValue();
	            	if(selectRow>=0){
	            		applicationScenarios=Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.applicationScenarios;
	            	}
	            	
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/calculateManagerController/saveRecalculateData',
	            		success:function(response) {
	            			var rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	var successInfo=loginUserLanguageResource.calculateMaintainingEditSuccessInfo;
	                            //保存以后重置全局容器
	                            pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
	                            Ext.MessageBox.alert(loginUserLanguageResource.message,successInfo);
	                            Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar").getStore().loadPage(1);
	                        } else {
	                        	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
	                        pcpRPMCalculateMaintainingHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(pcpRPMCalculateMaintainingHandsontableHelper.AllData),
	                    	deviceType: deviceType,
	                    	applicationScenarios: applicationScenarios,
	                    	calculateType: calculateType
	                    }
	            	}); 
	            } else {
	                if (!pcpRPMCalculateMaintainingHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.dataTypeError);
	                } else {
	                	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.noDataChange);
	                }
	            }
	        }
	        
	        
	      //删除的优先级最高
	        pcpRPMCalculateMaintainingHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	pcpRPMCalculateMaintainingHandsontableHelper.delidslist.push(id);
	                }
	            });
	            pcpRPMCalculateMaintainingHandsontableHelper.AllData.delidslist = pcpRPMCalculateMaintainingHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        pcpRPMCalculateMaintainingHandsontableHelper.screening=function() {
	            if (pcpRPMCalculateMaintainingHandsontableHelper.updatelist.length != 0 && pcpRPMCalculateMaintainingHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < pcpRPMCalculateMaintainingHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < pcpRPMCalculateMaintainingHandsontableHelper.updatelist.length; j++) {
	                        if (pcpRPMCalculateMaintainingHandsontableHelper.updatelist[j].id == pcpRPMCalculateMaintainingHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	pcpRPMCalculateMaintainingHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                pcpRPMCalculateMaintainingHandsontableHelper.AllData.updatelist = pcpRPMCalculateMaintainingHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        pcpRPMCalculateMaintainingHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(pcpRPMCalculateMaintainingHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        pcpRPMCalculateMaintainingHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && pcpRPMCalculateMaintainingHandsontableHelper.updatelist.push(data);
	                //封装
	                pcpRPMCalculateMaintainingHandsontableHelper.AllData.updatelist = pcpRPMCalculateMaintainingHandsontableHelper.updatelist;
	            }
	        }
	        
	        pcpRPMCalculateMaintainingHandsontableHelper.clearContainer = function () {
	        	pcpRPMCalculateMaintainingHandsontableHelper.AllData = {};
	        	pcpRPMCalculateMaintainingHandsontableHelper.updatelist = [];
	        	pcpRPMCalculateMaintainingHandsontableHelper.delidslist = [];
	        	pcpRPMCalculateMaintainingHandsontableHelper.insertlist = [];
	        }
	        
	        return pcpRPMCalculateMaintainingHandsontableHelper;
	    }
};

function ReTotalRPMData(){
	var gridPanel = Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id");
    var selectionModel = gridPanel.getSelectionModel();
    var _record = selectionModel.getSelection();
    if (_record.length>0) {
    	var reCalculateData='';
    	Ext.Array.each(_record, function (name, index, countriesItSelf) {
    		reCalculateData+=_record[index].data.id+","+_record[index].data.wellId+","+_record[index].data.deviceName+","+_record[index].data.calDate+";"
    	});
    	reCalculateData = reCalculateData.substring(0, reCalculateData.length - 1);
    	Ext.getCmp("PCPTotalCalculateMaintainingPanel").el.mask(loginUserLanguageResource.recalculating+'...').show();
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/calculateManagerController/reTotalCalculate',
    		success:function(response) {
    			Ext.getCmp("PCPTotalCalculateMaintainingPanel").getEl().unmask();
    			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.recalculationComplete);
                Ext.getCmp("PCPTotalCalculateMaintainingDataGridPanel_Id").getStore().loadPage(1);
    		},
    		failure:function(){
    			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
    		},
    		params: {
    			deviceType: 1,
    			reCalculateDate: reCalculateData
            }
    	}); 
    }else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
}

function resetPCPCalculateMaintainingQueryParams(){
	Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingWellListComBox_Id').setRawValue('');
	Ext.getCmp('PCPCalculateMaintainingStartDate_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingStartDate_Id').setRawValue('');
	Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingEndDate_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingEndDate_Id').setRawValue('');
	Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingCalculateSignComBox_Id').setValue('');
	Ext.getCmp('PCPCalculateMaintainingCalculateSignComBox_Id').setRawValue('');
}

function refreshPCPCalculateMaintainingData(){
	resetPCPCalculateMaintainingQueryParams();
	var gridPanel = Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id");
	if (isNotVal(gridPanel)) {
		gridPanel.getStore().load();
	}else{
		Ext.create('AP.store.dataMaintaining.PCPCalculateMaintainingWellListStore');
	}
}