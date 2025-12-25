var realtimeAcquisitionDataMaintainingHandsontableHelper=null;
var historyAcquisitionDataMaintainingHandsontableHelper=null;
Ext.define("AP.view.dataMaintaining.AcquisitionDataMaintainingInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.AcquisitionDataMaintainingInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var bbar = new Ext.toolbar.Paging({
        	id:'RealtimeAcquisitionDataMaintainingBbar',
            pageSize: defaultPageSize,
            displayInfo: true
        });
        
        var historyAcquisitionDataBbar = new Ext.toolbar.Paging({
        	id:'HistoryAcquisitionDataMaintainingBbar',
            pageSize: defaultPageSize,
            displayInfo: true
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
                         xtype: 'datefield',
                         anchor: '100%',
                         fieldLabel: '',
                         labelWidth: 0,
                         width: 90,
                         format: 'Y-m-d ',
                         id: 'AcquisitionDataMaintainingStartDate_Id',
                         value: '',
                         readOnly:true,
                         disabled:true,
                         listeners: {
                         	select: function (combo, record, index) {
                         		var activeId = Ext.getCmp("AcquisitionDataMaintainingTabPanel").getActiveTab().id;
                     			if(activeId=="RealtimeAcquisitionDataMaintainingPanel"){
        	        				var bbar=Ext.getCmp("RealtimeAcquisitionDataMaintainingBbar");
        	        				if (isNotVal(bbar)) {
        	        					if(bbar.getStore().isEmptyStore){
        	        						var RealtimeAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
        	        						bbar.setStore(RealtimeAcquisitionDataMaintainingDataStore);
        	        					}else{
        	        						bbar.getStore().loadPage(1);
        	        					}
        	        				}else{
        	        					Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
        	        				}
        	        			}else if(activeId=="HistoryAcquisitionDataMaintainingPanel"){
        	        				var bbar=Ext.getCmp("HistoryAcquisitionDataMaintainingBbar");
        	        				if (isNotVal(bbar)) {
        	        					if(bbar.getStore().isEmptyStore){
        	        						var HistoryAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
        	        						bbar.setStore(HistoryAcquisitionDataMaintainingDataStore);
        	        					}else{
        	        						bbar.getStore().loadPage(1);
        	        					}
        	        				}else{
        	        					Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
        	        				}
        	        			}
                             }
                         }
                     },{
                     	xtype: 'numberfield',
                     	id: 'AcquisitionDataMaintainingStartTime_Hour_Id',
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
                     	id: 'AcquisitionDataMaintainingStartTime_Minute_Id',
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
                     	id: 'AcquisitionDataMaintainingStartTime_Second_Id',
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
                         id: 'AcquisitionDataMaintainingEndDate_Id',
                         value: '',
                         readOnly:true,
                         disabled:true,
                         listeners: {
                         	select: function (combo, record, index) {
                         		var activeId = Ext.getCmp("AcquisitionDataMaintainingTabPanel").getActiveTab().id;
                     			if(activeId=="RealtimeAcquisitionDataMaintainingPanel"){
        	        				var bbar=Ext.getCmp("RealtimeAcquisitionDataMaintainingBbar");
        	        				if (isNotVal(bbar)) {
        	        					if(bbar.getStore().isEmptyStore){
        	        						var RealtimeAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
        	        						bbar.setStore(RealtimeAcquisitionDataMaintainingDataStore);
        	        					}else{
        	        						bbar.getStore().loadPage(1);
        	        					}
        	        				}else{
        	        					Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
        	        				}
        	        			}else if(activeId=="HistoryAcquisitionDataMaintainingPanel"){
        	        				var bbar=Ext.getCmp("HistoryAcquisitionDataMaintainingBbar");
        	        				if (isNotVal(bbar)) {
        	        					if(bbar.getStore().isEmptyStore){
        	        						var HistoryAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
        	        						bbar.setStore(HistoryAcquisitionDataMaintainingDataStore);
        	        					}else{
        	        						bbar.getStore().loadPage(1);
        	        					}
        	        				}else{
        	        					Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
        	        				}
        	        			}
                             }
                         }
                     },{
                     	xtype: 'numberfield',
                     	id: 'AcquisitionDataMaintainingEndTime_Hour_Id',
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
                     	id: 'AcquisitionDataMaintainingEndTime_Minute_Id',
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
                     	id: 'AcquisitionDataMaintainingEndTime_Second_Id',
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
                         pressed: false,
                         hidden:false,
                         handler: function (v, o) {
                        	 var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                          	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                          	var startTime_Hour=Ext.getCmp('AcquisitionDataMaintainingStartTime_Hour_Id').getValue();
                          	if(!r.test(startTime_Hour)){
                          		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                          		Ext.getCmp('AcquisitionDataMaintainingStartTime_Hour_Id').focus(true, 100);
                          		return;
                          	}
                          	var startTime_Minute=Ext.getCmp('AcquisitionDataMaintainingStartTime_Minute_Id').getValue();
                          	if(!r2.test(startTime_Minute)){
                          		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                          		Ext.getCmp('AcquisitionDataMaintainingStartTime_Minute_Id').focus(true, 100);
                          		return;
                          	}
                          	var startTime_Second=0;
                          	
                          	var endTime_Hour=Ext.getCmp('AcquisitionDataMaintainingEndTime_Hour_Id').getValue();
                          	if(!r.test(endTime_Hour)){
                          		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                          		Ext.getCmp('AcquisitionDataMaintainingEndTime_Hour_Id').focus(true, 100);
                          		return;
                          	}
                          	var endTime_Minute=Ext.getCmp('AcquisitionDataMaintainingEndTime_Minute_Id').getValue();
                          	if(!r2.test(endTime_Minute)){
                          		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                          		Ext.getCmp('AcquisitionDataMaintainingEndTime_Minute_Id').focus(true, 100);
                          		return;
                          	}
                          	var endTime_Second=0;
                         	var activeId = Ext.getCmp("AcquisitionDataMaintainingTabPanel").getActiveTab().id;
                 			if(activeId=="RealtimeAcquisitionDataMaintainingPanel"){
    	        				var bbar=Ext.getCmp("RealtimeAcquisitionDataMaintainingBbar");
    	        				if (isNotVal(bbar)) {
    	        					if(bbar.getStore().isEmptyStore){
    	        						var RealtimeAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
    	        						bbar.setStore(RealtimeAcquisitionDataMaintainingDataStore);
    	        					}else{
    	        						bbar.getStore().loadPage(1);
    	        					}
    	        				}else{
    	        					Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
    	        				}
    	        			}else if(activeId=="HistoryAcquisitionDataMaintainingPanel"){
    	        				var bbar=Ext.getCmp("HistoryAcquisitionDataMaintainingBbar");
    	        				if (isNotVal(bbar)) {
    	        					if(bbar.getStore().isEmptyStore){
    	        						var HistoryAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
    	        						bbar.setStore(HistoryAcquisitionDataMaintainingDataStore);
    	        					}else{
    	        						bbar.getStore().loadPage(1);
    	        					}
    	        				}else{
    	        					Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
    	        				}
    	        			}
                         }
                     
             		},'->',{
                        xtype: 'button',
                        text: loginUserLanguageResource.deleteData,
                        disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                        pressed: false,
                        hidden: false,
                        iconCls: 'delete',
                        id:'AcquisitionDataMaintainingDeleteDataBtn',
                        handler: function (v, o) {
                        	var activeId = Ext.getCmp("AcquisitionDataMaintainingTabPanel").getActiveTab().id;
    	        			if(activeId=="RealtimeAcquisitionDataMaintainingPanel"){
    	        				deleteRealtimeAcquisitionData();
    	        			}else if(activeId=="HistoryAcquisitionDataMaintainingPanel"){
    	        				deleteHistoryAcquisitionData();
    	        			}
                        }
                    }]
            	}]
            },
        	items: [{
            	region: 'center',
            	xtype: 'tabpanel',
        		id:"AcquisitionDataMaintainingTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'left',
        		items: [{
        			title: loginUserLanguageResource.realtimeMonitoring,
    				layout: "fit",
    				id:'RealtimeAcquisitionDataMaintainingPanel',
    				iconCls: 'check3',
    				border: false,
    				tbar:[{
                        xtype: 'button',
                        text: loginUserLanguageResource.selectAll,
                        disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                        pressed: false,
                        handler: function (v, o) {
                        	var rowCount = realtimeAcquisitionDataMaintainingHandsontableHelper.hot.countRows();
                        	var updateData=[];
                        	var selected=true;
                            for(var i=0;i<rowCount;i++){
                            	var data=[i,'checked',selected];
                            	updateData.push(data);
                            }
                            realtimeAcquisitionDataMaintainingHandsontableHelper.hot.setDataAtRowProp(updateData);
                        }
                    },{
                        xtype: 'button',
                        text: loginUserLanguageResource.deselectAll,
                        disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                        pressed: false,
                        handler: function (v, o) {
                        	var rowCount = realtimeAcquisitionDataMaintainingHandsontableHelper.hot.countRows();
                        	var updateData=[];
                        	var selected=false;
                            for(var i=0;i<rowCount;i++){
                            	var data=[i,'checked',selected];
                            	updateData.push(data);
                            }
                            realtimeAcquisitionDataMaintainingHandsontableHelper.hot.setDataAtRowProp(updateData);
                        }
                    }],
    				bbar: bbar,
    				html:'<div class=AcquisitionDataMaintainingContainer" style="width:100%;height:100%;"><div class="con" id="AcquisitionDataMaintainingDiv_id"></div></div>',
    				listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(realtimeAcquisitionDataMaintainingHandsontableHelper!=null && realtimeAcquisitionDataMaintainingHandsontableHelper.hot!=undefined){
                        		var newWidth=width;
                        		var newHeight=height;
                        		var newHeight=height-22-1;//减去tbar
                        		var newHeight=newHeight-28-1;//减去bbar
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		realtimeAcquisitionDataMaintainingHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
        		},{
        			title: loginUserLanguageResource.historyQuery,
    				layout: "fit",
    				id:'HistoryAcquisitionDataMaintainingPanel',
    				border: false,
    				tbar:[{
                        xtype: 'button',
                        text: loginUserLanguageResource.selectAll,
                        disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                        pressed: false,
                        handler: function (v, o) {
                        	var rowCount = historyAcquisitionDataMaintainingHandsontableHelper.hot.countRows();
                        	var updateData=[];
                        	var selected=true;
                            for(var i=0;i<rowCount;i++){
                            	var data=[i,'checked',selected];
                            	updateData.push(data);
                            }
                            historyAcquisitionDataMaintainingHandsontableHelper.hot.setDataAtRowProp(updateData);
                        }
                    },{
                        xtype: 'button',
                        text: loginUserLanguageResource.deselectAll,
                        disabled:loginUserCalculateMaintainingModuleRight.editFlag!=1,
                        pressed: false,
                        handler: function (v, o) {
                        	var rowCount = historyAcquisitionDataMaintainingHandsontableHelper.hot.countRows();
                        	var updateData=[];
                        	var selected=false;
                            for(var i=0;i<rowCount;i++){
                            	var data=[i,'checked',selected];
                            	updateData.push(data);
                            }
                            historyAcquisitionDataMaintainingHandsontableHelper.hot.setDataAtRowProp(updateData);
                        }
                    }],
    				bbar: historyAcquisitionDataBbar,
    				html:'<div class=HistoryAcquisitionDataMaintainingContainer" style="width:100%;height:100%;"><div class="con" id="HistoryAcquisitionDataMaintainingDiv_id"></div></div>',
    				listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(historyAcquisitionDataMaintainingHandsontableHelper!=null && historyAcquisitionDataMaintainingHandsontableHelper.hot!=undefined){
                        		var newWidth=width;
                        		var newHeight=height;
                        		var newHeight=height-22-1;//减去tbar
                        		var newHeight=newHeight-28-1;//减去bbar
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		historyAcquisitionDataMaintainingHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
        		}],
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				if(oldCard!=undefined){
                			oldCard.setIconCls(null);
                	    }
                	    if(newCard!=undefined){
                	    	newCard.setIconCls('check3');				
                	    }
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        				resetAcquisitionDataMaintainingQueryParams();
        				if(newCard.id=='RealtimeAcquisitionDataMaintainingPanel'){
        					Ext.getCmp('AcquisitionDataMaintainingStartDate_Id').setReadOnly(true);
        					Ext.getCmp('AcquisitionDataMaintainingEndDate_Id').setReadOnly(true);
        					Ext.getCmp('AcquisitionDataMaintainingStartDate_Id').setDisabled(true);
        					Ext.getCmp('AcquisitionDataMaintainingEndDate_Id').setDisabled(true);
        					
        					var bbar=Ext.getCmp("RealtimeAcquisitionDataMaintainingBbar");
	        				if (isNotVal(bbar)) {
	        					if(bbar.getStore().isEmptyStore){
	        						var RealtimeAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
	        						bbar.setStore(RealtimeAcquisitionDataMaintainingDataStore);
	        					}else{
	        						bbar.getStore().loadPage(1);
	        					}
	        				}else{
	        					Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
	        				}
        				}else{
        					Ext.getCmp('AcquisitionDataMaintainingStartDate_Id').setReadOnly(false);
        					Ext.getCmp('AcquisitionDataMaintainingEndDate_Id').setReadOnly(false);
        					Ext.getCmp('AcquisitionDataMaintainingStartDate_Id').setDisabled(false);
        					Ext.getCmp('AcquisitionDataMaintainingEndDate_Id').setDisabled(false);
        					
        					var bbar=Ext.getCmp("HistoryAcquisitionDataMaintainingBbar");
	        				if (isNotVal(bbar)) {
	        					if(bbar.getStore().isEmptyStore){
	        						var HistoryAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
	        						bbar.setStore(HistoryAcquisitionDataMaintainingDataStore);
	        					}else{
	        						bbar.getStore().loadPage(1);
	        					}
	        				}else{
	        					Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
	        				}
        				}
    				}
    			}
            }]
        });
        me.callParent(arguments);
    }
});


function CreateAndLoadRealtimeAcquisitionDataMaintainingTable(isNew,result,divid){
	if(isNew&&realtimeAcquisitionDataMaintainingHandsontableHelper!=null){
        realtimeAcquisitionDataMaintainingHandsontableHelper.clearContainer();
        if(realtimeAcquisitionDataMaintainingHandsontableHelper.hot!=undefined){
        	realtimeAcquisitionDataMaintainingHandsontableHelper.hot.destroy();
        }
        realtimeAcquisitionDataMaintainingHandsontableHelper=null;
	}
	var applicationScenarios=result.applicationScenarios;
	if(realtimeAcquisitionDataMaintainingHandsontableHelper==null){
		realtimeAcquisitionDataMaintainingHandsontableHelper = RealtimeAcquisitionDataMaintainingHandsontableHelper.createNew(divid);
		var colHeaders="['',";
        var columns="[{data:'checked',type:'checkbox'},";
        for(var i=0;i<result.columns.length;i++){
        	var colHeader="'" + result.columns[i].header + "'";
            var dataIndex=result.columns[i].dataIndex;
            
            colHeaders += colHeader;
        	columns+="{data:'"+dataIndex+"'}";
        	if(i<result.columns.length-1){
        		colHeaders+=",";
            	columns+=",";
        	}
        }
        colHeaders+="]";
    	columns+="]";
    	realtimeAcquisitionDataMaintainingHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
    	realtimeAcquisitionDataMaintainingHandsontableHelper.columns=Ext.JSON.decode(columns);
    	
    	if(result.totalRoot.length==0){
    		realtimeAcquisitionDataMaintainingHandsontableHelper.hiddenRows=[0];
    		realtimeAcquisitionDataMaintainingHandsontableHelper.createTable([{}]);
        }else{
        	realtimeAcquisitionDataMaintainingHandsontableHelper.hiddenRows=[];
        	realtimeAcquisitionDataMaintainingHandsontableHelper.createTable(result.totalRoot);
        }
	}else{
		if(result.totalRoot.length==0){
			realtimeAcquisitionDataMaintainingHandsontableHelper.hiddenRows=[0];
			realtimeAcquisitionDataMaintainingHandsontableHelper.hot.loadData([{}]);
    	}else{
    		realtimeAcquisitionDataMaintainingHandsontableHelper.hiddenRows=[];
    		realtimeAcquisitionDataMaintainingHandsontableHelper.hot.loadData(result.totalRoot);
    	}
	}
};


var RealtimeAcquisitionDataMaintainingHandsontableHelper = {
	    createNew: function (divid) {
	        var realtimeAcquisitionDataMaintainingHandsontableHelper = {};
	        realtimeAcquisitionDataMaintainingHandsontableHelper.hot = '';
	        realtimeAcquisitionDataMaintainingHandsontableHelper.divid = divid;
	        realtimeAcquisitionDataMaintainingHandsontableHelper.validresult=true;//数据校验
	        realtimeAcquisitionDataMaintainingHandsontableHelper.colHeaders=[];
	        realtimeAcquisitionDataMaintainingHandsontableHelper.columns=[];
	        realtimeAcquisitionDataMaintainingHandsontableHelper.hiddenRows = [];
	        
	        realtimeAcquisitionDataMaintainingHandsontableHelper.AllData={};
	        realtimeAcquisitionDataMaintainingHandsontableHelper.updatelist=[];
	        realtimeAcquisitionDataMaintainingHandsontableHelper.delidslist=[];
	        realtimeAcquisitionDataMaintainingHandsontableHelper.insertlist=[];
	        
	        realtimeAcquisitionDataMaintainingHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(realtimeAcquisitionDataMaintainingHandsontableHelper.columns[col].type=='checkbox'){
	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
	        	}else if(realtimeAcquisitionDataMaintainingHandsontableHelper.columns[col].type=='dropdown'){
	        		Handsontable.renderers.DropdownRenderer.apply(this, arguments);
	        	}else{
	        		Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	}
	        	
	            if(col>=1){
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
            	
            	if(realtimeAcquisitionDataMaintainingHandsontableHelper.columns[col].type!='checkbox'){
            		td.style.whiteSpace='nowrap'; //文本不换行
                	td.style.overflow='hidden';//超出部分隐藏
                	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        	}
	        }
	        
	        realtimeAcquisitionDataMaintainingHandsontableHelper.addReadOnlyCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(realtimeAcquisitionDataMaintainingHandsontableHelper.columns[col].type=='checkbox'){
	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
	        	}else if(realtimeAcquisitionDataMaintainingHandsontableHelper.columns[col].type=='dropdown'){
	        		Handsontable.renderers.DropdownRenderer.apply(this, arguments);
	        	}else{
	        		Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	}
	        	
	        	td.style.backgroundColor = 'rgb(245, 245, 245)';
            	
            	if(realtimeAcquisitionDataMaintainingHandsontableHelper.columns[col].type!='checkbox'){
            		td.style.whiteSpace='nowrap'; //文本不换行
                	td.style.overflow='hidden';//超出部分隐藏
                	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        	}
	        }
	        
	        
	        realtimeAcquisitionDataMaintainingHandsontableHelper.createTable = function (data) {
	        	$('#'+realtimeAcquisitionDataMaintainingHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+realtimeAcquisitionDataMaintainingHandsontableHelper.divid);
	        	realtimeAcquisitionDataMaintainingHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		fixedColumnsLeft:4, //固定左侧多少列不能水平滚动
	                hiddenColumns: {
	                    columns: [],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                hiddenRows: {
	                    rows: realtimeAcquisitionDataMaintainingHandsontableHelper.hiddenRows,
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:realtimeAcquisitionDataMaintainingHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:realtimeAcquisitionDataMaintainingHandsontableHelper.colHeaders,//显示列头
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
	                    	if (visualColIndex >= 1) {
								cellProperties.readOnly = true;
			                }
	                    	cellProperties.renderer = realtimeAcquisitionDataMaintainingHandsontableHelper.addCellStyle;
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer=realtimeAcquisitionDataMaintainingHandsontableHelper.addReadOnlyCellStyle;
		                }
	                    return cellProperties;
	                },
	                afterDestroy: function() {
	                },
	                beforeRemoveRow: function (index, amount) {
	                	
	                },
	                afterChange: function (changes, source) {
	                	
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && realtimeAcquisitionDataMaintainingHandsontableHelper!=null&&realtimeAcquisitionDataMaintainingHandsontableHelper.hot!=''&&realtimeAcquisitionDataMaintainingHandsontableHelper.hot!=undefined && realtimeAcquisitionDataMaintainingHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=realtimeAcquisitionDataMaintainingHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        realtimeAcquisitionDataMaintainingHandsontableHelper.insertExpressCount=function() {}
	        //保存数据
	        realtimeAcquisitionDataMaintainingHandsontableHelper.saveData = function () {}
	        
	        
	        //删除的优先级最高
	        realtimeAcquisitionDataMaintainingHandsontableHelper.delExpressCount=function(ids) {}

	        //updatelist数据更新
	        realtimeAcquisitionDataMaintainingHandsontableHelper.screening=function() {}
	        
	        //更新数据
	        realtimeAcquisitionDataMaintainingHandsontableHelper.updateExpressCount=function(data) {}
	        
	        realtimeAcquisitionDataMaintainingHandsontableHelper.clearContainer = function () {
	        	realtimeAcquisitionDataMaintainingHandsontableHelper.AllData = {};
	        	realtimeAcquisitionDataMaintainingHandsontableHelper.updatelist = [];
	        	realtimeAcquisitionDataMaintainingHandsontableHelper.delidslist = [];
	        	realtimeAcquisitionDataMaintainingHandsontableHelper.insertlist = [];
	        }
	        
	        return realtimeAcquisitionDataMaintainingHandsontableHelper;
	    }
};

function CreateAndLoadHistoryAcquisitionDataMaintainingTable(isNew,result,divid){
	if(isNew&&historyAcquisitionDataMaintainingHandsontableHelper!=null){
        historyAcquisitionDataMaintainingHandsontableHelper.clearContainer();
        if(historyAcquisitionDataMaintainingHandsontableHelper.hot!=undefined){
        	historyAcquisitionDataMaintainingHandsontableHelper.hot.destroy();
        }
        historyAcquisitionDataMaintainingHandsontableHelper=null;
	}
	var applicationScenarios=result.applicationScenarios;
	if(historyAcquisitionDataMaintainingHandsontableHelper==null){
		historyAcquisitionDataMaintainingHandsontableHelper = HistoryAcquisitionDataMaintainingHandsontableHelper.createNew(divid);
		var colHeaders="['',";
        var columns="[{data:'checked',type:'checkbox'},";
        for(var i=0;i<result.columns.length;i++){
        	var colHeader="'" + result.columns[i].header + "'";
            var dataIndex=result.columns[i].dataIndex;
            
            colHeaders += colHeader;
        	columns+="{data:'"+dataIndex+"'}";
        	if(i<result.columns.length-1){
        		colHeaders+=",";
            	columns+=",";
        	}
        }
        colHeaders+=",''";
        columns+=",{data:'recordId'}";
        
        colHeaders+="]";
    	columns+="]";
    	historyAcquisitionDataMaintainingHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
    	historyAcquisitionDataMaintainingHandsontableHelper.columns=Ext.JSON.decode(columns);
    	
    	if(result.totalRoot.length==0){
    		historyAcquisitionDataMaintainingHandsontableHelper.hiddenRows=[0];
    		historyAcquisitionDataMaintainingHandsontableHelper.createTable([{}]);
        }else{
        	historyAcquisitionDataMaintainingHandsontableHelper.hiddenRows=[];
        	historyAcquisitionDataMaintainingHandsontableHelper.createTable(result.totalRoot);
        }
	}else{
		if(result.totalRoot.length==0){
			historyAcquisitionDataMaintainingHandsontableHelper.hiddenRows=[0];
			historyAcquisitionDataMaintainingHandsontableHelper.hot.loadData([{}]);
    	}else{
    		historyAcquisitionDataMaintainingHandsontableHelper.hiddenRows=[];
    		historyAcquisitionDataMaintainingHandsontableHelper.hot.loadData(result.totalRoot);
    	}
	}
};


var HistoryAcquisitionDataMaintainingHandsontableHelper = {
	    createNew: function (divid) {
	        var historyAcquisitionDataMaintainingHandsontableHelper = {};
	        historyAcquisitionDataMaintainingHandsontableHelper.hot = '';
	        historyAcquisitionDataMaintainingHandsontableHelper.divid = divid;
	        historyAcquisitionDataMaintainingHandsontableHelper.validresult=true;//数据校验
	        historyAcquisitionDataMaintainingHandsontableHelper.colHeaders=[];
	        historyAcquisitionDataMaintainingHandsontableHelper.columns=[];
	        historyAcquisitionDataMaintainingHandsontableHelper.hiddenRows = [];
	        
	        historyAcquisitionDataMaintainingHandsontableHelper.AllData={};
	        historyAcquisitionDataMaintainingHandsontableHelper.updatelist=[];
	        historyAcquisitionDataMaintainingHandsontableHelper.delidslist=[];
	        historyAcquisitionDataMaintainingHandsontableHelper.insertlist=[];
	        
	        historyAcquisitionDataMaintainingHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(historyAcquisitionDataMaintainingHandsontableHelper.columns[col].type=='checkbox'){
	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
	        	}else if(historyAcquisitionDataMaintainingHandsontableHelper.columns[col].type=='dropdown'){
	        		Handsontable.renderers.DropdownRenderer.apply(this, arguments);
	        	}else{
	        		Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	}
	        	
	            if(col>=1){
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
            	
            	if(historyAcquisitionDataMaintainingHandsontableHelper.columns[col].type!='checkbox'){
            		td.style.whiteSpace='nowrap'; //文本不换行
                	td.style.overflow='hidden';//超出部分隐藏
                	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        	}
	        }
	        
	        historyAcquisitionDataMaintainingHandsontableHelper.addReadOnlyCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(historyAcquisitionDataMaintainingHandsontableHelper.columns[col].type=='checkbox'){
	        		Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
	        	}else if(historyAcquisitionDataMaintainingHandsontableHelper.columns[col].type=='dropdown'){
	        		Handsontable.renderers.DropdownRenderer.apply(this, arguments);
	        	}else{
	        		Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	}
	        	
	        	td.style.backgroundColor = 'rgb(245, 245, 245)';
            	
            	if(historyAcquisitionDataMaintainingHandsontableHelper.columns[col].type!='checkbox'){
            		td.style.whiteSpace='nowrap'; //文本不换行
                	td.style.overflow='hidden';//超出部分隐藏
                	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        	}
	        }
	        
	        
	        historyAcquisitionDataMaintainingHandsontableHelper.createTable = function (data) {
	        	$('#'+historyAcquisitionDataMaintainingHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+historyAcquisitionDataMaintainingHandsontableHelper.divid);
	        	historyAcquisitionDataMaintainingHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		fixedColumnsLeft:4, //固定左侧多少列不能水平滚动
	                hiddenColumns: {
	                    columns: [historyAcquisitionDataMaintainingHandsontableHelper.columns.length-1],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                hiddenRows: {
	                    rows: historyAcquisitionDataMaintainingHandsontableHelper.hiddenRows,
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:historyAcquisitionDataMaintainingHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:historyAcquisitionDataMaintainingHandsontableHelper.colHeaders,//显示列头
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
	                    	if (visualColIndex >= 1) {
								cellProperties.readOnly = true;
			                }
	                    	cellProperties.renderer = historyAcquisitionDataMaintainingHandsontableHelper.addCellStyle;
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer=historyAcquisitionDataMaintainingHandsontableHelper.addReadOnlyCellStyle;
		                }
	                    return cellProperties;
	                },
	                afterDestroy: function() {
	                },
	                beforeRemoveRow: function (index, amount) {
	                	
	                },
	                afterChange: function (changes, source) {
	                	
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && historyAcquisitionDataMaintainingHandsontableHelper!=null&&historyAcquisitionDataMaintainingHandsontableHelper.hot!=''&&historyAcquisitionDataMaintainingHandsontableHelper.hot!=undefined && historyAcquisitionDataMaintainingHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=historyAcquisitionDataMaintainingHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        historyAcquisitionDataMaintainingHandsontableHelper.insertExpressCount=function() {}
	        //保存数据
	        historyAcquisitionDataMaintainingHandsontableHelper.saveData = function () {}
	        
	        
	        //删除的优先级最高
	        historyAcquisitionDataMaintainingHandsontableHelper.delExpressCount=function(ids) {}

	        //updatelist数据更新
	        historyAcquisitionDataMaintainingHandsontableHelper.screening=function() {}
	        
	        //更新数据
	        historyAcquisitionDataMaintainingHandsontableHelper.updateExpressCount=function(data) {}
	        
	        historyAcquisitionDataMaintainingHandsontableHelper.clearContainer = function () {
	        	historyAcquisitionDataMaintainingHandsontableHelper.AllData = {};
	        	historyAcquisitionDataMaintainingHandsontableHelper.updatelist = [];
	        	historyAcquisitionDataMaintainingHandsontableHelper.delidslist = [];
	        	historyAcquisitionDataMaintainingHandsontableHelper.insertlist = [];
	        }
	        
	        return historyAcquisitionDataMaintainingHandsontableHelper;
	    }
};

function resetAcquisitionDataMaintainingQueryParams(){
	Ext.getCmp('AcquisitionDataMaintainingStartDate_Id').setValue('');
	Ext.getCmp('AcquisitionDataMaintainingStartDate_Id').setRawValue('');
	Ext.getCmp('AcquisitionDataMaintainingStartTime_Hour_Id').setValue('');
	Ext.getCmp('AcquisitionDataMaintainingStartTime_Minute_Id').setValue('');
	Ext.getCmp('AcquisitionDataMaintainingEndDate_Id').setValue('');
	Ext.getCmp('AcquisitionDataMaintainingEndDate_Id').setRawValue('');
	Ext.getCmp('AcquisitionDataMaintainingEndTime_Hour_Id').setValue('');
	Ext.getCmp('AcquisitionDataMaintainingEndTime_Minute_Id').setValue('');
}

function refreshAcquisitionDataMaintainingData(){
	resetAcquisitionDataMaintainingQueryParams();
	var activeId = Ext.getCmp("AcquisitionDataMaintainingTabPanel").getActiveTab().id;
	if(activeId=="RealtimeAcquisitionDataMaintainingPanel"){
		var bbar=Ext.getCmp("RealtimeAcquisitionDataMaintainingBbar");
		if (isNotVal(bbar)) {
			if(bbar.getStore().isEmptyStore){
				var RealtimeAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
				bbar.setStore(RealtimeAcquisitionDataMaintainingDataStore);
			}else{
				bbar.getStore().loadPage(1);
			}
		}else{
			Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
		}
	}else if(activeId=="HistoryAcquisitionDataMaintainingPanel"){
		var bbar=Ext.getCmp("HistoryAcquisitionDataMaintainingBbar");
		if (isNotVal(bbar)) {
			if(bbar.getStore().isEmptyStore){
				var HistoryAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
				bbar.setStore(HistoryAcquisitionDataMaintainingDataStore);
			}else{
				bbar.getStore().loadPage(1);
			}
		}else{
			Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
		}
	}
}

function deleteRealtimeAcquisitionData() {
    var checkedStatus = realtimeAcquisitionDataMaintainingHandsontableHelper.hot.getDataAtProp('checked');
    var deleteAcqTimeList = [];
    var deviceId = 0;
    if (Ext.getCmp("DataMaintainingDeviceListGridPanel_Id").getSelectionModel().getSelection().length > 0) {
        deviceId = Ext.getCmp("DataMaintainingDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    }

    if (checkedStatus.length > 0) {
        for (var i = 0; i < checkedStatus.length; i++) {
            if (checkedStatus[i]) {
                var acqTime = realtimeAcquisitionDataMaintainingHandsontableHelper.hot.getDataAtRowProp(i, 'acqTime');
                deleteAcqTimeList.push(acqTime);
            }
        }
    }
    if (deleteAcqTimeList.length > 0) {
    	Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
    		if (btn == "yes") {
    			Ext.Ajax.request({
    	    		method:'POST',
    	    		url:context + '/calculateManagerController/deleteRealtimeAcquisitionData',
    	    		success:function(response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                        	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.deleteSuccessfully);
                        	var bbar=Ext.getCmp("RealtimeAcquisitionDataMaintainingBbar");
	        				if (isNotVal(bbar)) {
	        					if(bbar.getStore().isEmptyStore){
	        						var RealtimeAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
	        						bbar.setStore(RealtimeAcquisitionDataMaintainingDataStore);
	        					}else{
	        						bbar.getStore().loadPage(1);
	        					}
	        				}else{
	        					Ext.create('AP.store.dataMaintaining.RealtimeAcquisitionDataMaintainingDataStore');
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
    	    			calculateType: 0,
    	    			acqTimeList:  deleteAcqTimeList.join(",")
    	            }
    	    	}); 
    		}
    	});
    } else {
        Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.noSelectionRecord);
    }
}

function deleteHistoryAcquisitionData() {
    var checkedStatus = historyAcquisitionDataMaintainingHandsontableHelper.hot.getDataAtProp('checked');
    var deleteRecordList = [];
    var deviceId = 0;
    var calculateType = 0;
    if (Ext.getCmp("DataMaintainingDeviceListGridPanel_Id").getSelectionModel().getSelection().length > 0) {
        deviceId = Ext.getCmp("DataMaintainingDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        calculateType = Ext.getCmp("DataMaintainingDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
    }

    if (checkedStatus.length > 0) {
        for (var i = 0; i < checkedStatus.length; i++) {
            if (checkedStatus[i]) {
                var recordId = historyAcquisitionDataMaintainingHandsontableHelper.hot.getDataAtRowProp(i, 'recordId');
                deleteRecordList.push(recordId);
            }
        }
    }
    if (deleteRecordList.length > 0) {
    	Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
    		if (btn == "yes") {
    			Ext.Ajax.request({
    	    		method:'POST',
    	    		url:context + '/calculateManagerController/deleteHistoryAcquisitionData',
    	    		success:function(response) {
                        rdata = Ext.JSON.decode(response.responseText);
                        if (rdata.success) {
                        	Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.deleteSuccessfully);
                        	var bbar=Ext.getCmp("HistoryAcquisitionDataMaintainingBbar");
	        				if (isNotVal(bbar)) {
	        					if(bbar.getStore().isEmptyStore){
	        						var HistoryAcquisitionDataMaintainingDataStore=Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
	        						bbar.setStore(HistoryAcquisitionDataMaintainingDataStore);
	        					}else{
	        						bbar.getStore().loadPage(1);
	        					}
	        				}else{
	        					Ext.create('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore');
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
    	    			recordIds:  deleteRecordList.join(",")
    	            }
    	    	}); 
    		}
    	});
    } else {
        Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.noSelectionRecord);
    }
}
