var srpFESDiagramCalculateMaintainingHandsontableHelper=null;
Ext.define("AP.view.dataMaintaining.SRPCalculateMaintainingInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.SRPCalculateMaintainingInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var bbar = new Ext.toolbar.Paging({
        	id:'SRPFESDiagramCalculateMaintainingBbar',
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
                    var deviceName = Ext.getCmp('SRPCalculateMaintainingWellListComBox_Id').getValue();
                    var deviceType=0;
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceName: deviceName,
                        calculateType: 1
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellListComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: deviceShowName,
                    id: 'SRPCalculateMaintainingWellListComBox_Id',
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
                        	resultNameComb.clearValue();
                        	var gridPanel = Ext.getCmp("SRPCalculateMaintainingWellListGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().load();
            				}else{
            					Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingWellListStore');
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
                	var deviceName='';
                	var deviceId=0;
                	
                	var selectRow= Ext.getCmp("SRPCalculateMaintainingDeviceListSelectRow_Id").getValue();
                	if(selectRow>=0){
                		deviceName = Ext.getCmp("SRPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
                		deviceId=Ext.getCmp("SRPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                	}
                	
                    var startDate=Ext.getCmp('SRPCalculateMaintainingStartDate_Id').rawValue;
                    var endDate=Ext.getCmp('SRPCalculateMaintainingEndDate_Id').rawValue;
                    var new_params = {
                    		orgId: orgId,
                    		deviceId: deviceId,
                    		deviceName: deviceName,
                            startDate:startDate,
                            endDate:endDate,
                            calculateType:1
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
                    id: 'SRPCalculateMaintainingCalculateSignComBox_Id',
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
            				
            				var bbar=Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar");
            				if (isNotVal(bbar)) {
            					if(bbar.getStore().isEmptyStore){
            						var SRPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
            						bbar.setStore(SRPCalculateMaintainingDataStore);
            					}else{
            						bbar.getStore().loadPage(1);
            					}
            				}else{
            					Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
            				}
                        }
                    }
        });
        
        var resultNameStore = new Ext.data.JsonStore({
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/calculateManagerController/getResultNameList',
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
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                	var deviceName='';
                	var deviceId=0;
                	
                	var selectRow= Ext.getCmp("SRPCalculateMaintainingDeviceListSelectRow_Id").getValue();
                	if(selectRow>=0){
                		deviceName = Ext.getCmp("SRPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
                		deviceId=Ext.getCmp("SRPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                	}
                	
                	var startDate=Ext.getCmp('SRPCalculateMaintainingStartDate_Id').rawValue;
                    var startTime_Hour=Ext.getCmp('SRPCalculateMaintainingStartTime_Hour_Id').getValue();
                	var startTime_Minute=Ext.getCmp('SRPCalculateMaintainingStartTime_Minute_Id').getValue();
//                	var startTime_Second=Ext.getCmp('SRPCalculateMaintainingStartTime_Second_Id').getValue();
                	var startTime_Second=0;
                	
                    var endDate=Ext.getCmp('SRPCalculateMaintainingEndDate_Id').rawValue;
                    var endTime_Hour=Ext.getCmp('SRPCalculateMaintainingEndTime_Hour_Id').getValue();
                	var endTime_Minute=Ext.getCmp('SRPCalculateMaintainingEndTime_Minute_Id').getValue();
//                	var endTime_Second=Ext.getCmp('SRPCalculateMaintainingEndTime_Second_Id').getValue();
                	var endTime_Second=0;
                    
                    var new_params = {
                    		orgId: orgId,
                    		deviceId: deviceId,
                    		deviceName: deviceName,
                    		startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                            calculateType:1
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var resultNameComb = Ext.create(
                'Ext.form.field.ComboBox', {
                	fieldLabel: loginUserLanguageResource.FSDiagramWorkType,
                    labelWidth: getStringLength(loginUserLanguageResource.FSDiagramWorkType)*8,
                    width: (getStringLength(loginUserLanguageResource.FSDiagramWorkType)*8+150),
                    id: 'SRPCalculateMaintainingResultNameComBox_Id',
                    store: resultNameStore,
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
                    		resultNameComb.clearValue();
                    		resultNameComb.getStore().load();
                        },
                        select: function (combo, record, index) {
            				var bbar=Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar");
            				if (isNotVal(bbar)) {
            					if(bbar.getStore().isEmptyStore){
            						var SRPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
            						bbar.setStore(SRPCalculateMaintainingDataStore);
            					}else{
            						bbar.getStore().loadPage(1);
            					}
            				}else{
            					Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
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
                         id: 'SRPCalculateMaintainingDeviceListSelectRow_Id',
                         xtype: 'textfield',
                         value: -1,
                         hidden: true
                     },{
                         xtype: 'button',
                         text: loginUserLanguageResource.refresh,
                         iconCls: 'note-refresh',
                         hidden:false,
                         handler: function (v, o) {
                         	refreshSRPCalculateMaintainingData();
                         }
             		},'-',wellListComb
             			,"-",{
                         xtype: 'datefield',
                         anchor: '100%',
                         fieldLabel: '',
                         labelWidth: 0,
                         width: 90,
                         format: 'Y-m-d ',
                         id: 'SRPCalculateMaintainingStartDate_Id',
                         value: '',
                         listeners: {
                         	select: function (combo, record, index) {
                         		calculateSignComb.clearValue();
                         		resultNameComb.clearValue();
                         		var activeId = Ext.getCmp("SRPCalculateMaintainingTabPanel").getActiveTab().id;
                     			if(activeId=="SRPCalculateMaintainingPanel"){
                     				var bbar=Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar");
                     				if (isNotVal(bbar)) {
                     					if(bbar.getStore().isEmptyStore){
                     						var SRPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
                     						bbar.setStore(SRPCalculateMaintainingDataStore);
                     					}else{
                     						bbar.getStore().loadPage(1);
                     					}
                     				}else{
                     					Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
                     				}
                     			}else if(activeId=="SRPTotalCalculateMaintainingPanel"){
                     				var gridPanel = Ext.getCmp("SRPTotalCalculateMaintainingDataGridPanel_Id");
                     	            if (isNotVal(gridPanel)) {
                     	            	gridPanel.getStore().loadPage(1);
                     	            }else{
                     	            	Ext.create("AP.store.dataMaintaining.SRPTotalCalculateMaintainingDataStore");
                     	            }
                     			}
                             }
                         }
                     },{
                     	xtype: 'numberfield',
                     	id: 'SRPCalculateMaintainingStartTime_Hour_Id',
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
                     	id: 'SRPCalculateMaintainingStartTime_Minute_Id',
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
                     	id: 'SRPCalculateMaintainingStartTime_Second_Id',
                     	hidden: true,
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
                         id: 'SRPCalculateMaintainingEndDate_Id',
                         value: '',
                         listeners: {
                         	select: function (combo, record, index) {
                         		calculateSignComb.clearValue();
                         		resultNameComb.clearValue();
                         		var activeId = Ext.getCmp("SRPCalculateMaintainingTabPanel").getActiveTab().id;
                     			if(activeId=="SRPCalculateMaintainingPanel"){
                     				var bbar=Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar");
                     				if (isNotVal(bbar)) {
                     					if(bbar.getStore().isEmptyStore){
                     						var SRPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
                     						bbar.setStore(SRPCalculateMaintainingDataStore);
                     					}else{
                     						bbar.getStore().loadPage(1);
                     					}
                     				}else{
                     					Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
                     				}
                     			}else if(activeId=="SRPTotalCalculateMaintainingPanel"){
                     				var gridPanel = Ext.getCmp("SRPTotalCalculateMaintainingDataGridPanel_Id");
                     	            if (isNotVal(gridPanel)) {
                     	            	gridPanel.getStore().loadPage(1);
                     	            }else{
                     	            	Ext.create("AP.store.dataMaintaining.SRPTotalCalculateMaintainingDataStore");
                     	            }
                     			}
                             }
                         }
                     },{
                     	xtype: 'numberfield',
                     	id: 'SRPCalculateMaintainingEndTime_Hour_Id',
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
                     	id: 'SRPCalculateMaintainingEndTime_Minute_Id',
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
                     	id: 'SRPCalculateMaintainingEndTime_Second_Id',
                     	hidden: true,
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
                     },"-",calculateSignComb,'-',resultNameComb,'-',{
                         xtype: 'button',
                         text: loginUserLanguageResource.search,
                         iconCls: 'search',
                         pressed: false,
                         hidden:false,
                         handler: function (v, o) {
                         	var activeId = Ext.getCmp("SRPCalculateMaintainingTabPanel").getActiveTab().id;
                 			if(activeId=="SRPCalculateMaintainingPanel"){
                 				var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                             	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                             	var startTime_Hour=Ext.getCmp('SRPCalculateMaintainingStartTime_Hour_Id').getValue();
                             	if(!r.test(startTime_Hour)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                             		Ext.getCmp('SRPCalculateMaintainingStartTime_Hour_Id').focus(true, 100);
                             		return;
                             	}
                             	var startTime_Minute=Ext.getCmp('SRPCalculateMaintainingStartTime_Minute_Id').getValue();
                             	if(!r2.test(startTime_Minute)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                             		Ext.getCmp('SRPCalculateMaintainingStartTime_Minute_Id').focus(true, 100);
                             		return;
                             	}
//                             	var startTime_Second=Ext.getCmp('SRPCalculateMaintainingStartTime_Second_Id').getValue();
//                             	if(!r2.test(startTime_Second)){
//                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
//                             		Ext.getCmp('SRPCalculateMaintainingStartTime_Second_Id').focus(true, 100);
//                             		return;
//                             	}
                             	var startTime_Second=0;
                             	
                             	var endTime_Hour=Ext.getCmp('SRPCalculateMaintainingEndTime_Hour_Id').getValue();
                             	if(!r.test(endTime_Hour)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                             		Ext.getCmp('SRPCalculateMaintainingEndTime_Hour_Id').focus(true, 100);
                             		return;
                             	}
                             	var endTime_Minute=Ext.getCmp('SRPCalculateMaintainingEndTime_Minute_Id').getValue();
                             	if(!r2.test(endTime_Minute)){
                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                             		Ext.getCmp('SRPCalculateMaintainingEndTime_Minute_Id').focus(true, 100);
                             		return;
                             	}
//                             	var endTime_Second=Ext.getCmp('SRPCalculateMaintainingEndTime_Second_Id').getValue();
//                             	if(!r2.test(endTime_Second)){
//                             		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
//                             		Ext.getCmp('SRPCalculateMaintainingEndTime_Second_Id').focus(true, 100);
//                             		return;
//                             	}
                             	var endTime_Second=0;
                 				
                 				
                 				var bbar=Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar");
                 				if (isNotVal(bbar)) {
                 					if(bbar.getStore().isEmptyStore){
                 						var SRPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
                 						bbar.setStore(SRPCalculateMaintainingDataStore);
                 					}else{
                 						bbar.getStore().loadPage(1);
                 					}
                 				}else{
                 					Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
                 				}
                 			}else if(activeId=="SRPTotalCalculateMaintainingPanel"){
                 				var gridPanel = Ext.getCmp("SRPTotalCalculateMaintainingDataGridPanel_Id");
                 	            if (isNotVal(gridPanel)) {
                 	            	gridPanel.getStore().loadPage(1);
                 	            }else{
                 	            	Ext.create("AP.store.dataMaintaining.SRPTotalCalculateMaintainingDataStore");
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
                         id:'SRPCalculateMaintainingUpdateDataBtn',
                         pressed: false,
                         iconCls: 'edit',
                         handler: function (v, o) {
                         	srpFESDiagramCalculateMaintainingHandsontableHelper.saveData();
                         }
                     },"-",{
                         xtype: 'button',
                         text: loginUserLanguageResource.linkProductionDataCalculate,
                         disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                         pressed: false,
                         iconCls: 'save',
                         id:'SRPCalculateMaintainingLinkedDataBtn',
                         handler: function (v, o) {
                         	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                         	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                         	var startTime_Hour=Ext.getCmp('SRPCalculateMaintainingStartTime_Hour_Id').getValue();
                         	if(!r.test(startTime_Hour)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                         		Ext.getCmp('SRPCalculateMaintainingStartTime_Hour_Id').focus(true, 100);
                         		return;
                         	}
                         	var startTime_Minute=Ext.getCmp('SRPCalculateMaintainingStartTime_Minute_Id').getValue();
                         	if(!r2.test(startTime_Minute)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                         		Ext.getCmp('SRPCalculateMaintainingStartTime_Minute_Id').focus(true, 100);
                         		return;
                         	}
//                         	var startTime_Second=Ext.getCmp('SRPCalculateMaintainingStartTime_Second_Id').getValue();
//                         	if(!r2.test(startTime_Second)){
//                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
//                         		Ext.getCmp('SRPCalculateMaintainingStartTime_Second_Id').focus(true, 100);
//                         		return;
//                         	}
                         	var startTime_Second=0;
                         	
                         	var endTime_Hour=Ext.getCmp('SRPCalculateMaintainingEndTime_Hour_Id').getValue();
                         	if(!r.test(endTime_Hour)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                         		Ext.getCmp('SRPCalculateMaintainingEndTime_Hour_Id').focus(true, 100);
                         		return;
                         	}
                         	var endTime_Minute=Ext.getCmp('SRPCalculateMaintainingEndTime_Minute_Id').getValue();
                         	if(!r2.test(endTime_Minute)){
                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                         		Ext.getCmp('SRPCalculateMaintainingEndTime_Minute_Id').focus(true, 100);
                         		return;
                         	}
//                         	var endTime_Second=Ext.getCmp('SRPCalculateMaintainingEndTime_Second_Id').getValue();
//                         	if(!r2.test(endTime_Second)){
//                         		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
//                         		Ext.getCmp('SRPCalculateMaintainingEndTime_Second_Id').focus(true, 100);
//                         		return;
//                         	}
                         	var endTime_Second=0;
                         	
                         	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                            var deviceName=Ext.getCmp('SRPCalculateMaintainingWellListComBox_Id').getValue();
                            var startDate=Ext.getCmp('SRPCalculateMaintainingStartDate_Id').rawValue;
                            var startTime_Hour=Ext.getCmp('SRPCalculateMaintainingStartTime_Hour_Id').getValue();
                         	var startTime_Minute=Ext.getCmp('SRPCalculateMaintainingStartTime_Minute_Id').getValue();
//                         	var startTime_Second=Ext.getCmp('SRPCalculateMaintainingStartTime_Second_Id').getValue();
                         	var startTime_Second=0;
                            var endDate=Ext.getCmp('SRPCalculateMaintainingEndDate_Id').rawValue;
                            var endTime_Hour=Ext.getCmp('SRPCalculateMaintainingEndTime_Hour_Id').getValue();
                         	var endTime_Minute=Ext.getCmp('SRPCalculateMaintainingEndTime_Minute_Id').getValue();
//                         	var endTime_Second=Ext.getCmp('SRPCalculateMaintainingEndTime_Second_Id').getValue();
                         	var endTime_Second=0;
                            var calculateSign=Ext.getCmp('SRPCalculateMaintainingCalculateSignComBox_Id').getValue();
                            var deviceType=0;
                            var calculateType=1;
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
                 	                            srpFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
                 	                            Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar").getStore().loadPage(1);
                 	                        } else {
                 	                        	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.operationFailure);

                 	                        }
                 	            		},
                 	            		failure:function(){
                 	            			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
                 	                        srpFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
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
                         id:'SRPCalculateMaintainingExportDataBtn',
                         handler: function (v, o) {
                        	 var checkedStatus=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtProp('checked');
                        	 var checkedList=[];
                        	 if(checkedStatus.length>0){
                        		 for(var i=0;i<checkedStatus.length;i++){
                        			 if(checkedStatus[i]){
                        				 checkedList.push(i);
                        			 }
                        		 }
                        	 }
                        	 
                         	if(checkedList.length>0){
//                         		var row=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getSelected()[0][0];
                         		for(var i=0;i<checkedList.length;i++){
                         			var row=checkedList[i];
                             		
                             		var recordId=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(row,'recordId');
                             		var deviceName=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(row,'deviceName');
                             		var acqTime=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(row,'acqTime');
                             		
                             		var calculateType=1;//1-抽油机井诊断计产 2-螺杆泵井诊断计产 3-抽油机井汇总计算  4-螺杆泵井汇总计算 5-电参反演地面功图计算
                             		var url=context + '/calculateManagerController/exportCalculateRequestData?recordId='+recordId+'&deviceName='+URLencode(URLencode(deviceName))+'&acqTime='+acqTime+'&calculateType='+calculateType;
//                                 	document.location.href = url;
                                 	
                                 	downloadFile(url);
                         		}
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
                         id:'SRPCalculateMaintainingDeleteDataBtn',
                         handler: function (v, o) {
                        	 var checkedStatus=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtProp('checked');
                        	 var deleteRecordList=[];
                        	 var deviceId=0;
                        	 var selectRow= Ext.getCmp("SRPCalculateMaintainingDeviceListSelectRow_Id").getValue();
                        	 if(selectRow>=0){
                         		deviceId=Ext.getCmp("SRPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                         	}
                        	 
                        	 
                        	 if(checkedStatus.length>0){
                        		 for(var i=0;i<checkedStatus.length;i++){
                        			 if(checkedStatus[i]){
                        				 var recordId=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(i,'recordId'); 
                        				 deleteRecordList.push(recordId);
                        			 }
                        		 }
                        	 }
                        	 if(deleteRecordList.length>0){
                        		 deleteCalculateData(deviceId,deleteRecordList,1);
                        	 }else{
                        		 Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.noSelectionRecord);
                        	 }
                         }
                     },{
                         xtype: 'button',
                         text: loginUserLanguageResource.reTotalCalculate,
                         disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                         id:'SRPCalculateMaintainingReTotalBtn',
                         pressed: false,
                         hidden:true,
                         iconCls: 'edit',
                         handler: function (v, o) {
                         	ReTotalFESDiagramData();
                         }
                     },"-",{
                         xtype: 'button',
                         text: loginUserLanguageResource.exportRequestData,
                         disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                         pressed: false,
                         hidden: true,
                         iconCls: 'export',
                         id:'SRPTotalCalculateMaintainingExportDataBtn',
                         handler: function (v, o) {
                         	var gridPanel = Ext.getCmp("SRPTotalCalculateMaintainingDataGridPanel_Id");
                             var selectionModel = gridPanel.getSelectionModel();
                             var _record = selectionModel.getSelection();
                             if (_record.length>0) {
                            	 for(var i=0;i<_record.length;i++){
                            		 var recordId=_record[i].data.id;
                                 	 var deviceId=_record[i].data.deviceId;
                                 	 var deviceName=_record[i].data.deviceName;
                                 	 var calDate=_record[i].data.calDate;
                             		 var calculeteType=0;
                             		 var url=context + '/calculateManagerController/exportTotalCalculateRequestData?recordId='+recordId
                             		 +'&deviceId='+deviceId
                             		 +'&deviceName='+URLencode(URLencode(deviceName))
                             		 +'&calDate='+calDate
                             		 +'&calculeteType='+calculeteType;
                             		 downloadFile(url);
                            	 }
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
            	id: 'SRPCalculateMaintainingWellListPanel_Id',
            	collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
            	layout: "fit"
            },{
            	region: 'center',
            	xtype: 'tabpanel',
        		id:"SRPCalculateMaintainingTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        			title: loginUserLanguageResource.singleRecord,
    				layout: "fit",
    				id:'SRPCalculateMaintainingPanel',
    				iconCls: 'check3',
    				border: false,
    				tbar:[{
                        xtype: 'button',
                        text: loginUserLanguageResource.selectAll,
                        disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                        pressed: false,
                        handler: function (v, o) {
                        	var rowCount = srpFESDiagramCalculateMaintainingHandsontableHelper.hot.countRows();
                        	var updateData=[];
                        	var selected=true;
                            for(var i=0;i<rowCount;i++){
                            	var data=[i,'checked',selected];
                            	updateData.push(data);
                            }
                            srpFESDiagramCalculateMaintainingHandsontableHelper.hot.setDataAtRowProp(updateData);
                        }
                    },{
                        xtype: 'button',
                        text: loginUserLanguageResource.deselectAll,
                        disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                        pressed: false,
                        handler: function (v, o) {
                        	var rowCount = srpFESDiagramCalculateMaintainingHandsontableHelper.hot.countRows();
                        	var updateData=[];
                        	var selected=false;
                            for(var i=0;i<rowCount;i++){
                            	var data=[i,'checked',selected];
                            	updateData.push(data);
                            }
                            srpFESDiagramCalculateMaintainingHandsontableHelper.hot.setDataAtRowProp(updateData);
                        }
                    }],
    				bbar: bbar,
    				html:'<div class=SRPCalculateMaintainingContainer" style="width:100%;height:100%;"><div class="con" id="SRPCalculateMaintainingDiv_id"></div></div>',
    				listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(srpFESDiagramCalculateMaintainingHandsontableHelper!=null && srpFESDiagramCalculateMaintainingHandsontableHelper.hot!=undefined){
//                        		srpFESDiagramCalculateMaintainingHandsontableHelper.hot.refreshDimensions();
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		var thisPanelBbar=thisPanel.bbar; 	
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		newHeight-=29;
                        		srpFESDiagramCalculateMaintainingHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
        		},{
        			title: loginUserLanguageResource.recordTotal,
    				layout: "fit",
    				id:'SRPTotalCalculateMaintainingPanel',
    				border: false
        		}],
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check3');
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
    					if(newCard.id=="SRPCalculateMaintainingPanel"){
    						Ext.getCmp("SRPCalculateMaintainingUpdateDataBtn").show();
    						Ext.getCmp("SRPCalculateMaintainingLinkedDataBtn").show();
    						Ext.getCmp("SRPCalculateMaintainingExportDataBtn").show();
    						Ext.getCmp("SRPCalculateMaintainingDeleteDataBtn").show();
    						Ext.getCmp("SRPCalculateMaintainingCalculateSignComBox_Id").show();
    						Ext.getCmp("SRPCalculateMaintainingReTotalBtn").hide();
    						Ext.getCmp("SRPTotalCalculateMaintainingExportDataBtn").hide();
    						
    						Ext.getCmp('SRPCalculateMaintainingStartTime_Hour_Id').show();
    						Ext.getCmp('SRPCalculateMaintainingStartTime_Minute_Id').show();
//    						Ext.getCmp('SRPCalculateMaintainingStartTime_Second_Id').show();
    						Ext.getCmp('SRPCalculateMaintainingEndTime_Hour_Id').show();
    						Ext.getCmp('SRPCalculateMaintainingEndTime_Minute_Id').show();
//    						Ext.getCmp('SRPCalculateMaintainingEndTime_Second_Id').show();
    					}else if(newCard.id=="SRPTotalCalculateMaintainingPanel"){
    						Ext.getCmp("SRPCalculateMaintainingUpdateDataBtn").hide();
    						Ext.getCmp("SRPCalculateMaintainingLinkedDataBtn").hide();
    						Ext.getCmp("SRPCalculateMaintainingExportDataBtn").hide();
    						Ext.getCmp("SRPCalculateMaintainingDeleteDataBtn").hide();
    						Ext.getCmp("SRPCalculateMaintainingCalculateSignComBox_Id").hide();
    						Ext.getCmp("SRPCalculateMaintainingReTotalBtn").show();
    						Ext.getCmp("SRPTotalCalculateMaintainingExportDataBtn").show();
    						
    						Ext.getCmp('SRPCalculateMaintainingStartTime_Hour_Id').hide();
    						Ext.getCmp('SRPCalculateMaintainingStartTime_Minute_Id').hide();
//    						Ext.getCmp('SRPCalculateMaintainingStartTime_Second_Id').hide();
    						Ext.getCmp('SRPCalculateMaintainingEndTime_Hour_Id').hide();
    						Ext.getCmp('SRPCalculateMaintainingEndTime_Minute_Id').hide();
//    						Ext.getCmp('SRPCalculateMaintainingEndTime_Second_Id').hide();
    					}
    					refreshSRPCalculateMaintainingData();
    				}
    			}
            }]
        });
        me.callParent(arguments);
    }
});

////数据行渲染器
//function checkboxRenderer(instance, td, row, col, prop, value) {
//  const checkbox = document.createElement('input');
//  checkbox.type = 'checkbox';
//  checkbox.checked = value;
//  checkbox.className = 'row-checkbox';
//  checkbox.addEventListener('click', (e) => e.stopPropagation());
//  td.innerHTML = '';
//  td.appendChild(checkbox);
//  td.classList.add('htCenter');
//  return td;
//}
//
//// 表头渲染器
//function headerCheckboxRenderer(instance, td) {
//  const checkbox = document.createElement('input');
//  checkbox.type = 'checkbox';
//  checkbox.className = 'header-checkbox';
//  
//  const data = instance.getSourceData();
//  const allChecked = data.every(row => row.checked);
//  const someChecked = data.some(row => row.checked);
//  checkbox.checked = allChecked;
//  checkbox.indeterminate = !allChecked && someChecked;
//
//  checkbox.addEventListener('click', (e) => {
//    e.stopPropagation();
//    const isChecked = checkbox.checked;
//    const newData = data.map(row => ({ row, checked: isChecked }));
//    instance.loadData(newData);
//  });
//
//  td.innerHTML = '';
//  td.appendChild(checkbox);
//  td.classList.add('htCenter');
//  return td;
//}
//
//
//
//function CreateAndLoadSRPCalculateMaintainingTable(isNew,result,divid){
//	const data = [
//        { id: 1, checked: false, name: 'Alice' },
//        { id: 2, checked: false, name: 'Bob' },
//        { id: 3, checked: false, name: 'Charlie' }
//      ];
//	const container = document.getElementById(divid);
//    const hot = new Handsontable(container, {
//      data: data,
//      colHeaders: ['Select', 'ID', 'Name'],
//      columns: [
//        {
//          data: 'checked',
//          renderer: checkboxRenderer,
//          headerRenderer: headerCheckboxRenderer // 应用表头渲染器
//        },
//        { data: 'id' },
//        { data: 'name' }
//      ],
//      afterChange: function(changes) {
//        if (changes && changes[0][1] === 'checked') {
//          this.render(); // 刷新表头
//        }
//      }
//    });
//}

function CreateAndLoadSRPCalculateMaintainingTable(isNew,result,divid){
	if(isNew&&srpFESDiagramCalculateMaintainingHandsontableHelper!=null){
        srpFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
        srpFESDiagramCalculateMaintainingHandsontableHelper.hot.destroy();
        srpFESDiagramCalculateMaintainingHandsontableHelper=null;
	}
	
	var applicationScenarios=result.applicationScenarios;
	
	if(srpFESDiagramCalculateMaintainingHandsontableHelper==null){
		srpFESDiagramCalculateMaintainingHandsontableHelper = SRPFESDiagramCalculateMaintainingHandsontableHelper.createNew(divid);
		var colHeaders=[];
        var columns=[];
        var singleColumn={
        	      data: 'checked',
        	      type: 'checkbox', // 基础复选框列类型
        	      className: 'htCenter'
        	    };
        
        columns.push(singleColumn);
        colHeaders.push('');
        for(var i=0;i<result.columns.length;i++){
        	var colHeader=result.columns[i].header;
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
            
            singleColumn={};
            singleColumn.data=dataIndex;
        	if(dataIndex.toUpperCase()=="id".toUpperCase()){
        		
        	}else if(dataIndex.toUpperCase()==="deviceName".toUpperCase()||dataIndex.toUpperCase()==="acqTime".toUpperCase()||dataIndex.toUpperCase()==="resultName".toUpperCase()){
    			
    		}else if(dataIndex==="anchoringStateName"){
        		singleColumn.type='dropdown';
        		singleColumn.strict=true;
        		singleColumn.allowInvalid=false;
        		singleColumn.source=['锚定', '未锚定'];
        	}else if(dataIndex.toUpperCase()==="barrelTypeName".toUpperCase()){
        		singleColumn.type='dropdown';
        		singleColumn.strict=true;
        		singleColumn.allowInvalid=false;
        		singleColumn.source=[loginUserLanguageResource.barrelType_L, loginUserLanguageResource.barrelType_H];
        	}else if(dataIndex.toUpperCase()==="pumpTypeName".toUpperCase()){
        		singleColumn.type='dropdown';
        		singleColumn.strict=true;
        		singleColumn.allowInvalid=false;
        		singleColumn.source=['杆式泵', '管式泵'];
        	}else if(dataIndex.toUpperCase()==="pumpGrade".toUpperCase()){
        		singleColumn.type='dropdown';
        		singleColumn.strict=true;
        		singleColumn.allowInvalid=false;
        		singleColumn.source=['1', '2','3', '4','5'];
        	}else if (dataIndex.toUpperCase() === "manualInterventionResult".toUpperCase()) {
        		singleColumn.type='dropdown';
        		singleColumn.strict=true;
        		singleColumn.allowInvalid=false;
        		singleColumn.source=[];
        		
                for (var j = 0; j < result.resultNameList.length; j++) {
                    singleColumn.source.push(result.resultNameList[j]);
                }
            }else if(dataIndex.toUpperCase()==="rodGrade1".toUpperCase() || dataIndex.toUpperCase()==="rodGrade2".toUpperCase() || dataIndex.toUpperCase()==="rodGrade3".toUpperCase() || dataIndex.toUpperCase()==="rodGrade4".toUpperCase()){
        		singleColumn.type='dropdown';
        		singleColumn.strict=true;
        		singleColumn.allowInvalid=false;
        		singleColumn.source=['','A','B','C','D','K','KD','HL','HY'];
        		singleColumn.validator=function(val, callback){return handsontableDataCheck_RodGrade(val, callback,this.row, this.col,srpFESDiagramCalculateMaintainingHandsontableHelper);};
        	}else if(dataIndex.toUpperCase()==="rodTypeName1".toUpperCase() || dataIndex.toUpperCase()==="rodTypeName2".toUpperCase() || dataIndex.toUpperCase()==="rodTypeName3".toUpperCase() || dataIndex.toUpperCase()==="rodTypeName4".toUpperCase()){
        		singleColumn.type='dropdown';
        		singleColumn.strict=true;
        		singleColumn.allowInvalid=false;
        		singleColumn.source=['',loginUserLanguageResource.rodStringTypeValue1,loginUserLanguageResource.rodStringTypeValue2,loginUserLanguageResource.rodStringTypeValue3];
        		singleColumn.validator=function(val, callback){return handsontableDataCheck_RodType(val, callback,this.row, this.col,srpFESDiagramCalculateMaintainingHandsontableHelper);};
        	}else{
    			singleColumn.type='text';
        		singleColumn.allowInvalid=true;
        		singleColumn.validator=function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,srpFESDiagramCalculateMaintainingHandsontableHelper);};
    		}
        	colHeaders.push(colHeader);
        	columns.push(singleColumn);
        }
        colHeaders.push('recordId');
        singleColumn={data: 'recordId'};
        columns.push(singleColumn);
        
    	srpFESDiagramCalculateMaintainingHandsontableHelper.colHeaders=colHeaders;
    	srpFESDiagramCalculateMaintainingHandsontableHelper.columns=columns;
    	
    	if(result.totalRoot.length==0){
        	srpFESDiagramCalculateMaintainingHandsontableHelper.createTable([{}]);
        }else{
        	srpFESDiagramCalculateMaintainingHandsontableHelper.createTable(result.totalRoot);
        }
	}else{
		if(result.totalRoot.length==0){
			srpFESDiagramCalculateMaintainingHandsontableHelper.hot.loadData([{}]);
    	}else{
    		srpFESDiagramCalculateMaintainingHandsontableHelper.hot.loadData(result.totalRoot);
    	}
	}
};


var SRPFESDiagramCalculateMaintainingHandsontableHelper = {
	    createNew: function (divid) {
	        var srpFESDiagramCalculateMaintainingHandsontableHelper = {};
	        srpFESDiagramCalculateMaintainingHandsontableHelper.hot = '';
	        srpFESDiagramCalculateMaintainingHandsontableHelper.divid = divid;
	        srpFESDiagramCalculateMaintainingHandsontableHelper.validresult=true;//数据校验
	        srpFESDiagramCalculateMaintainingHandsontableHelper.colHeaders=[];
	        srpFESDiagramCalculateMaintainingHandsontableHelper.columns=[];
	        srpFESDiagramCalculateMaintainingHandsontableHelper.hiddenRows = [];
	        
	        srpFESDiagramCalculateMaintainingHandsontableHelper.AllData={};
	        srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist=[];
	        srpFESDiagramCalculateMaintainingHandsontableHelper.delidslist=[];
	        srpFESDiagramCalculateMaintainingHandsontableHelper.insertlist=[];
	        
	        srpFESDiagramCalculateMaintainingHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(srpFESDiagramCalculateMaintainingHandsontableHelper.columns[col].type=='checkbox'){
	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
	        	}else if(srpFESDiagramCalculateMaintainingHandsontableHelper.columns[col].type=='dropdown'){
	        		Handsontable.renderers.DropdownRenderer.apply(this, arguments);
	        	}else{
	        		Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	}
	        	
	            if(col<=8 && col>=1){
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
            	
            	if(srpFESDiagramCalculateMaintainingHandsontableHelper.columns[col].type!='checkbox'){
            		td.style.whiteSpace='nowrap'; //文本不换行
                	td.style.overflow='hidden';//超出部分隐藏
                	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        	}
	        }
	        
	        srpFESDiagramCalculateMaintainingHandsontableHelper.addReadOnlyCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(srpFESDiagramCalculateMaintainingHandsontableHelper.columns[col].type=='checkbox'){
	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
	        	}else if(srpFESDiagramCalculateMaintainingHandsontableHelper.columns[col].type=='dropdown'){
	        		Handsontable.renderers.DropdownRenderer.apply(this, arguments);
	        	}else{
	        		Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	}
	        	
	        	td.style.backgroundColor = 'rgb(245, 245, 245)';
            	
            	if(srpFESDiagramCalculateMaintainingHandsontableHelper.columns[col].type!='checkbox'){
            		td.style.whiteSpace='nowrap'; //文本不换行
                	td.style.overflow='hidden';//超出部分隐藏
                	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        	}
	        }
	        
	        srpFESDiagramCalculateMaintainingHandsontableHelper.createTable = function (data) {
	        	$('#'+srpFESDiagramCalculateMaintainingHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+srpFESDiagramCalculateMaintainingHandsontableHelper.divid);
	        	srpFESDiagramCalculateMaintainingHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		fixedColumnsLeft:4, //固定左侧多少列不能水平滚动
	                hiddenColumns: {
	                    columns: [srpFESDiagramCalculateMaintainingHandsontableHelper.columns.length-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
//	                hiddenRows: {
//	                    rows: [0],
//	                    indicators: false,
//	                    copyPasteEnabled: false
//	                },
	                columns:srpFESDiagramCalculateMaintainingHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:srpFESDiagramCalculateMaintainingHandsontableHelper.colHeaders,//显示列头
	                columnSorting: false,//允许排序
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
	                    	if (visualColIndex >= 1 && visualColIndex <= 8) {
								cellProperties.readOnly = true;
			                }else if(srpFESDiagramCalculateMaintainingHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='pumpGrade'.toUpperCase()
			                		&& srpFESDiagramCalculateMaintainingHandsontableHelper.hot!=undefined 
			                		&& srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtCell!=undefined){
			                	var columns=srpFESDiagramCalculateMaintainingHandsontableHelper.columns;
			                	var barrelTypeColIndex=-1;
			                	for(var i=0;i<columns.length;i++){
		                        	if(columns[i].data.toUpperCase() === "barrelTypeName".toUpperCase()){
		                        		barrelTypeColIndex=i;
		                        		break;
		                        	}
		                        }
			                	if(barrelTypeColIndex>0){
		                        	var barrelType=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtCell(row,barrelTypeColIndex);
		                        	if(barrelType==loginUserLanguageResource.barrelType_H){
		                        		this.source = ['1','2','3','4','5'];
		                        	}else if(barrelType==loginUserLanguageResource.barrelType_L){
		                        		this.source = ['1','2','3'];
		                        	}else if(barrelType==''){
		                        		this.source = ['1','2','3','4','5'];
		                        	}
		                        }
			                }
	                    	cellProperties.renderer = srpFESDiagramCalculateMaintainingHandsontableHelper.addCellStyle;
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=srpFESDiagramCalculateMaintainingHandsontableHelper.addReadOnlyCellStyle;
	                    }
//	                    if(srpFESDiagramCalculateMaintainingHandsontableHelper.columns[visualColIndex].type == undefined 
//	                    		|| 
//	                    		(srpFESDiagramCalculateMaintainingHandsontableHelper.columns[visualColIndex].type!='dropdown' || srpFESDiagramCalculateMaintainingHandsontableHelper.columns[visualColIndex].type!='checkbox')
//	                    			){
//                    		cellProperties.renderer = srpFESDiagramCalculateMaintainingHandsontableHelper.addCellStyle;
//                    	}
	                    
	                    return cellProperties;
	                },
	                afterDestroy: function() {
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    //封装id成array传入后台
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var recordId=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(i,'recordId');
	                            ids.push(rowdata[recordId]);
	                        }
	                        srpFESDiagramCalculateMaintainingHandsontableHelper.delExpressCount(ids);
	                        srpFESDiagramCalculateMaintainingHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRow(index);
		                        var recordId=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRowProp(index,'recordId');
		                        params.push(recordId);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<srpFESDiagramCalculateMaintainingHandsontableHelper.columns.length;j++){
		                        		data+=srpFESDiagramCalculateMaintainingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<srpFESDiagramCalculateMaintainingHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            srpFESDiagramCalculateMaintainingHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(srpFESDiagramCalculateMaintainingHandsontableHelper!=null&&srpFESDiagramCalculateMaintainingHandsontableHelper.hot!=''&&srpFESDiagramCalculateMaintainingHandsontableHelper.hot!=undefined && srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        srpFESDiagramCalculateMaintainingHandsontableHelper.insertExpressCount=function() {
	            var idsdata = srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = srpFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<srpFESDiagramCalculateMaintainingHandsontableHelper.columns.length;j++){
                        		data+=srpFESDiagramCalculateMaintainingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<srpFESDiagramCalculateMaintainingHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        srpFESDiagramCalculateMaintainingHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (srpFESDiagramCalculateMaintainingHandsontableHelper.insertlist.length != 0) {
	            	srpFESDiagramCalculateMaintainingHandsontableHelper.AllData.insertlist = srpFESDiagramCalculateMaintainingHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        srpFESDiagramCalculateMaintainingHandsontableHelper.saveData = function () {
        		//插入的数据的获取
	        	srpFESDiagramCalculateMaintainingHandsontableHelper.insertExpressCount();
	            if (JSON.stringify(srpFESDiagramCalculateMaintainingHandsontableHelper.AllData) != "{}" && srpFESDiagramCalculateMaintainingHandsontableHelper.validresult) {
	            	var bbarId="SRPFESDiagramCalculateMaintainingBbar";
	            	var deviceType=0;
	            	var calculateType=1;//1-抽油机诊断计产 2-螺杆泵诊断计产 3-抽油机汇总计算  4-螺杆泵汇总计算 5-电参反演地面功图计算
	            	
	            	var applicationScenarios=0;
	            	var selectRow= Ext.getCmp("SRPCalculateMaintainingDeviceListSelectRow_Id").getValue();
	            	if(selectRow>=0){
	            		applicationScenarios=Ext.getCmp("SRPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.applicationScenarios;
	            	}
	            	
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/calculateManagerController/saveRecalculateData',
	            		success:function(response) {
	            			var rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	var successInfo=loginUserLanguageResource.calculateMaintainingEditSuccessInfo;
	                            //保存以后重置全局容器
	                            srpFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
	                            Ext.MessageBox.alert(loginUserLanguageResource.message,successInfo);
	                            Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar").getStore().loadPage(1);
	                        } else {
	                        	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
	                        srpFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(srpFESDiagramCalculateMaintainingHandsontableHelper.AllData),
	                    	deviceType: deviceType,
	                    	applicationScenarios: applicationScenarios,
	                    	calculateType: calculateType
	                    }
	            	}); 
	            } else {
	                if (!srpFESDiagramCalculateMaintainingHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.dataTypeError);
	                } else {
	                	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.noDataChange);
	                }
	            }
	        }
	        
	        
	      //删除的优先级最高
	        srpFESDiagramCalculateMaintainingHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	srpFESDiagramCalculateMaintainingHandsontableHelper.delidslist.push(id);
	                }
	            });
	            srpFESDiagramCalculateMaintainingHandsontableHelper.AllData.delidslist = srpFESDiagramCalculateMaintainingHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        srpFESDiagramCalculateMaintainingHandsontableHelper.screening=function() {
	            if (srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist.length != 0 && srpFESDiagramCalculateMaintainingHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < srpFESDiagramCalculateMaintainingHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist.length; j++) {
	                        if (srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist[j].id == srpFESDiagramCalculateMaintainingHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                srpFESDiagramCalculateMaintainingHandsontableHelper.AllData.updatelist = srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        srpFESDiagramCalculateMaintainingHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist.push(data);
	                //封装
	                srpFESDiagramCalculateMaintainingHandsontableHelper.AllData.updatelist = srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist;
	            }
	        }
	        
	        srpFESDiagramCalculateMaintainingHandsontableHelper.clearContainer = function () {
	        	srpFESDiagramCalculateMaintainingHandsontableHelper.AllData = {};
	        	srpFESDiagramCalculateMaintainingHandsontableHelper.updatelist = [];
	        	srpFESDiagramCalculateMaintainingHandsontableHelper.delidslist = [];
	        	srpFESDiagramCalculateMaintainingHandsontableHelper.insertlist = [];
	        }
	        
	        return srpFESDiagramCalculateMaintainingHandsontableHelper;
	    }
};

function ReTotalFESDiagramData(){
	var gridPanel = Ext.getCmp("SRPTotalCalculateMaintainingDataGridPanel_Id");
    var selectionModel = gridPanel.getSelectionModel();
    var _record = selectionModel.getSelection();
    if (_record.length>0) {
    	var reCalculateData='';
    	Ext.Array.each(_record, function (name, index, countriesItSelf) {
    		reCalculateData+=_record[index].data.id+","+_record[index].data.deviceId+","+_record[index].data.deviceName+","+_record[index].data.calDate+";"
    	});
    	reCalculateData = reCalculateData.substring(0, reCalculateData.length - 1);
    	Ext.getCmp("SRPTotalCalculateMaintainingPanel").el.mask(loginUserLanguageResource.recalculating+'...').show();
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/calculateManagerController/reTotalCalculate',
    		success:function(response) {
    			Ext.getCmp("SRPTotalCalculateMaintainingPanel").getEl().unmask();
    			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.recalculationComplete);
                Ext.getCmp("SRPTotalCalculateMaintainingDataGridPanel_Id").getStore().loadPage(1);
    		},
    		failure:function(){
    			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
    		},
    		params: {
    			deviceType: 0,
    			reCalculateDate: reCalculateData
            }
    	}); 
    }else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
}

function deleteCalculateData(deviceId,recordIdList,calculateType){
    if (recordIdList.length>0) {
    	Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
    		if (btn == "yes") {
    			Ext.Ajax.request({
    	    		method:'POST',
    	    		url:context + '/calculateManagerController/deleteCalculateData',
    	    		success:function(response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                        	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.deleteSuccessfully);
                            //保存以后重置全局容器
                        	
                        	if(calculateType==1){
                        		var bbar=Ext.getCmp("SRPFESDiagramCalculateMaintainingBbar");
                 				if (isNotVal(bbar)) {
                 					if(bbar.getStore().isEmptyStore){
                 						var SRPCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
                 						bbar.setStore(SRPCalculateMaintainingDataStore);
                 					}else{
                 						bbar.getStore().loadPage(1);
                 					}
                 				}else{
                 					Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingDataStore');
                 				}
                        	}else if(calculateType==2){
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
                        } else {
                            Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
                        }
                    },
    	    		failure:function(){
    	    			Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
    	    		},
    	    		params: {
    	    			deviceId: deviceId,
    	    			calculateType: calculateType,
    	    			recordIds:  recordIdList.join(",")
    	            }
    	    	}); 
    		}
    	});
    }else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
}

function resetSRPCalculateMaintainingQueryParams(){
	Ext.getCmp('SRPCalculateMaintainingWellListComBox_Id').setValue('');
	Ext.getCmp('SRPCalculateMaintainingWellListComBox_Id').setRawValue('');
	Ext.getCmp('SRPCalculateMaintainingStartDate_Id').setValue('');
	Ext.getCmp('SRPCalculateMaintainingStartDate_Id').setRawValue('');
	Ext.getCmp('SRPCalculateMaintainingStartTime_Hour_Id').setValue('');
	Ext.getCmp('SRPCalculateMaintainingStartTime_Minute_Id').setValue('');
//	Ext.getCmp('SRPCalculateMaintainingStartTime_Second_Id').setValue('');
	Ext.getCmp('SRPCalculateMaintainingEndDate_Id').setValue('');
	Ext.getCmp('SRPCalculateMaintainingEndDate_Id').setRawValue('');
	Ext.getCmp('SRPCalculateMaintainingEndTime_Hour_Id').setValue('');
	Ext.getCmp('SRPCalculateMaintainingEndTime_Minute_Id').setValue('');
//	Ext.getCmp('SRPCalculateMaintainingEndTime_Second_Id').setValue('');
	Ext.getCmp('SRPCalculateMaintainingCalculateSignComBox_Id').setValue('');
	Ext.getCmp('SRPCalculateMaintainingCalculateSignComBox_Id').setRawValue('');
	Ext.getCmp('SRPCalculateMaintainingResultNameComBox_Id').setValue('');
	Ext.getCmp('SRPCalculateMaintainingResultNameComBox_Id').setRawValue('');
}

function refreshSRPCalculateMaintainingData(){
	resetSRPCalculateMaintainingQueryParams();
	var gridPanel = Ext.getCmp("SRPCalculateMaintainingWellListGridPanel_Id");
	if (isNotVal(gridPanel)) {
		gridPanel.getStore().load();
	}else{
		Ext.create('AP.store.dataMaintaining.SRPCalculateMaintainingWellListStore');
	}
}
