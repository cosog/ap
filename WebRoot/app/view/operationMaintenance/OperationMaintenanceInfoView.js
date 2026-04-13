var loginUserOperationMaintenanceModuleRight=getRoleModuleRight('OperationMaintenance');
var lowerComputerProgramUpgradeHandsontableHelper = null;
Ext.define("AP.view.operationMaintenance.OperationMaintenanceInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.moduleInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
//    bodyStyle: 'background-color:#ffffff;',
    initComponent: function () {
        var me = this;
        
        var languageComboxStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/userManagerController/loadLanguageNameList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json'
                }
            },
            autoLoad: false
        });
        
        
        var languageCombox = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: loginUserLanguageResource.loginInterfaceLanguage,
                    id: 'operationMaintenance_LogonLanguageCombox_Id',
                    anchor: '100%',
                    value: '',
                    name:'operationMaintenance.loginLanguage',
                    store: languageComboxStore,
                    emptyText: '--'+loginUserLanguageResource.selectLanguage+'--',
                    blankText: '--'+loginUserLanguageResource.selectLanguage+'--',
                    typeAhead: true,
                    allowBlank: false,
                    blankText: loginUserLanguageResource.required,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    listeners: {
                        select: function () {
                        	
                        }
                    }
                });
        
        var deviceListCombStore = new Ext.data.JsonStore({
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
                    var deviceName='';
                    if(isNotVal(Ext.getCmp('lowerComputerProgramUpgradeDeviceListComb_Id'))){
                    	deviceName = Ext.getCmp('lowerComputerProgramUpgradeDeviceListComb_Id').getValue();
                    }
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceName: deviceName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var deviceListDeviceCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: loginUserLanguageResource.deviceName,
                id: "lowerComputerProgramUpgradeDeviceListComb_Id",
                labelWidth: getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage),
                width: (getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage)+110),
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: deviceListCombStore,
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
                    	deviceListDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    select: function (combo, record, index) {
                    	loadLowerComputerProgramUpgradeDeviceList();
                    }
                }
            });
        
        Ext.apply(me, {
        	items:[{
        		xtype: 'tabpanel',
        		id:"OperationMaintenanceTabPanel_Id",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'top',
        		items:[{
        			title: loginUserLanguageResource.automatedOperationsManagement,
        			id:'OperationMaintenanceBasicInfoPanel_Id',
    				iconCls: 'check3',
    				layout: 'border',
    				bodyStyle: 'background-color:#ffffff;',
    				tbar: [{
                        xtype: 'button',
                        name: 'RoleNameBtn_Id',
                        text: loginUserLanguageResource.refresh,
                        iconCls: 'note-refresh',
                        handler: function () {
                        	loadOemOperationConfigInfo();
                        }
            		},'->',{
    	                xtype: 'button',
    	                text: loginUserLanguageResource.save,
    	                disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
    	                iconCls: 'save',
    	                hidden:false,
    	                handler: function (v, o) {
				        	var OperationMaintenanceBasicInfoSubForm = Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id").down('form');
				        	if (OperationMaintenanceBasicInfoSubForm.isValid()) {
				        		var configFile={};
				        		configFile.others={};
				        		configFile.others.loginLanguage=Ext.getCmp('operationMaintenance_LogonLanguageCombox_Id').getValue();
				        		configFile.others.showLogo=Ext.getCmp('operationMaintenance_showLogo_Id').getValue();
				        		configFile.others.printLog=Ext.getCmp('operationMaintenance_printLog_Id').getValue();
				        		configFile.others.printAdLog=Ext.getCmp('operationMaintenance_printAdLog_Id').getValue();
				        		configFile.others.printExceptionLog=Ext.getCmp('operationMaintenance_printExceptionLog_Id').getValue();
				        		configFile.others.timeEfficiencyUnit=Ext.getCmp('operationMaintenance_timeEfficiencyUnit1_Id').getValue()?1:2;
				        		configFile.others.resourceMonitoringSaveData=Ext.getCmp('operationMaintenance_resourceMonitoringSaveData_Id').getValue();
				        		configFile.others.exportLimit=Ext.getCmp('operationMaintenance_exportLimit_Id').getValue();
				        		configFile.others.simulateAcqEnable=Ext.getCmp('operationMaintenance_simulateAcqEnable_Id').getValue();
				        		configFile.others.sendCycle=Ext.getCmp('operationMaintenance_sendCycle_Id').getValue();
				        		
				        		configFile.databaseMaintenance={};
				        		configFile.databaseMaintenance.cycle=Ext.getCmp('operationMaintenance_databaseMaintenanceCycle_Id').getValue();
				        		configFile.databaseMaintenance.startTime=Ext.getCmp('operationMaintenance_databaseMaintenanceStartTime_Id').getValue();
				        		configFile.databaseMaintenance.endTime=Ext.getCmp('operationMaintenance_databaseMaintenanceEndTime_Id').getValue();
				        		
				        		configFile.databaseMaintenance.tableConfig={};
				        		configFile.databaseMaintenance.tableConfig.acqdata_hist={};
				        		configFile.databaseMaintenance.tableConfig.acqdata_hist.enabled=Ext.getCmp('operationMaintenance_acqdata_hist_enabled_Id').getValue();
				        		configFile.databaseMaintenance.tableConfig.acqdata_hist.retentionTime=Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').getValue();
				        		
				        		configFile.databaseMaintenance.tableConfig.acqrawdata={};
				        		configFile.databaseMaintenance.tableConfig.acqrawdata.enabled=Ext.getCmp('operationMaintenance_acqrawdata_enabled_id').getValue();
				        		configFile.databaseMaintenance.tableConfig.acqrawdata.retentionTime=Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').getValue();
				        		
				        		configFile.databaseMaintenance.tableConfig.alarminfo_hist={}
				        		configFile.databaseMaintenance.tableConfig.alarminfo_hist.enabled=Ext.getCmp('operationMaintenance_alarminfo_hist_enabled_id').getValue();
				        		configFile.databaseMaintenance.tableConfig.alarminfo_hist.retentionTime=Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').getValue();
				        		
				        		configFile.databaseMaintenance.tableConfig.dailytotalcalculate_hist={};
				        		configFile.databaseMaintenance.tableConfig.dailytotalcalculate_hist.enabled=Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_enabled_id').getValue();
				        		configFile.databaseMaintenance.tableConfig.dailytotalcalculate_hist.retentionTime=Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').getValue();
				        		
				        		configFile.databaseMaintenance.tableConfig.dailycalculationdata={};
				        		configFile.databaseMaintenance.tableConfig.dailycalculationdata.enabled=Ext.getCmp('operationMaintenance_dailycalculationdata_enabled_id').getValue();
				        		configFile.databaseMaintenance.tableConfig.dailycalculationdata.retentionTime=Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').getValue();
				        		
				        		configFile.databaseMaintenance.tableConfig.timingcalculationdata={};
				        		configFile.databaseMaintenance.tableConfig.timingcalculationdata.enabled=Ext.getCmp('operationMaintenance_timingcalculationdata_enabled_id').getValue();
				        		configFile.databaseMaintenance.tableConfig.timingcalculationdata.retentionTime=Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').getValue();
				        		
				        		configFile.databaseMaintenance.tableConfig.timingrecorddata={};
				        		configFile.databaseMaintenance.tableConfig.timingrecorddata.enabled=Ext.getCmp('operationMaintenance_timingrecorddata_enabled_id').getValue();
				        		configFile.databaseMaintenance.tableConfig.timingrecorddata.retentionTime=Ext.getCmp('operationMaintenance_timingrecorddata_retentionTim_id').getValue();
				        		
				        		configFile.databaseMaintenance.tableConfig.acqdata_vacuate={};
				        		configFile.databaseMaintenance.tableConfig.acqdata_vacuate.enabled=Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').getValue();
				        		configFile.databaseMaintenance.tableConfig.acqdata_vacuate.retentionTime=Ext.getCmp('operationMaintenance_acqdata_vacuate_retentionTim_id').getValue();
				        		
				        		configFile.dataVacuate={};
				        		configFile.dataVacuate.vacuateRecord=Ext.getCmp('operationMaintenance_vacuateRecord_Id').getValue();
				        		configFile.dataVacuate.saveInterval=Ext.getCmp('operationMaintenance_vacuateSaveInterval_Id').getValue();
				        		configFile.dataVacuate.saveIntervalWaveRange=Ext.getCmp('operationMaintenance_vacuateSaveIntervalWaveRange_Id').getValue();
				        		configFile.dataVacuate.vacuateThreshold=Ext.getCmp('operationMaintenance_vacuateThreshold_Id').getValue();
				        		
				        		configFile.report={};
				        		configFile.report.offsetHour=Ext.getCmp('operationMaintenance_reportOffsetHour_Id').getValue();
				        		configFile.report.interval=Ext.getCmp('operationMaintenance_reportInterval_Id').getValue();
				        		
				        		OperationMaintenanceBasicInfoSubForm.getForm().submit({
    				                url: context + '/operationMaintenanceController/updateOemConfigInfo',
    				                clientValidation: false, // 进行客户端验证
    				                method: "POST",
    				                params: {
    				                	configFile: JSON.stringify(configFile)
    				                },
    				                waitMsg: loginUserLanguageResource.sendServer,
    				                waitTitle: loginUserLanguageResource.wait,
    				                success: function (response, action) {
    				                    if (action.result.msg == true) {
    				                        Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.saveSuccessfully + "</font>");
    				                        loadOemOperationConfigInfo();
    				                    }
    				                    if (action.result.msg == false) {
    				                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveFailure);
    				                    }
    				                },
    				                failure: function () {
    				                    Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
    				                }
    				            });
				        	}
				        }
    	    		}],
    				items:[{
    	            	region: 'center',
    					height:'1500px',
    					xtype:'form',
    					border: false,
    		            id: "OperationMaintenanceBasicInfoSubFormId",
    					bodyPadding: 10,
    					items: [{
    				        xtype: 'form',
    				        bodyPadding: 10,
    				        fieldDefaults: {
    				            labelAlign: 'right',
    				            labelWidth: 100,
    				            msgTarget: 'side'
    				        },
    				        items: [
    				            // 第一个 FieldSet
    				            {
    				            	xtype: 'fieldset',
    				            	title: loginUserLanguageResource.basicInformation,
    				            	layout: 'column',
    				            	defaults: {
    				                    layout: 'form',
    				                    xtype: 'container',
    				                    defaultType: 'textfield',
    				                    style: 'width: 33%'
    				                },
    				                items: [{
    				                    items: [
    				                    	languageCombox,
    						                {
    				                        	xtype: 'fieldcontainer',
    				                            fieldLabel : loginUserLanguageResource.timeEfficiencyUnit,
    				                            defaultType: 'radiofield',
    				                            anchor: '100%',
    				                            defaults: {
    				                                flex: 1
    				                            },
    				                            layout: 'hbox',
    				                            items: [
    				                                {
    				                                    boxLabel: loginUserLanguageResource.decimals+'&nbsp;&nbsp;',
    				                                    name:'operationMaintenance.timeEfficiencyUnit',
    				                                    inputValue: '1',
    				                                    id: 'operationMaintenance_timeEfficiencyUnit1_Id'
    				                                }, {
    				                                    boxLabel:  loginUserLanguageResource.percent,
    				                                    name:'operationMaintenance.timeEfficiencyUnit',
    				                                    checked:true,
    				                                    inputValue:'2',
    				                                    id:'operationMaintenance_timeEfficiencyUnit2_Id'
    				                                }
    				                            ]
    				                        },{
                                        	    xtype: 'checkboxfield',
                                        	    fieldLabel: loginUserLanguageResource.sendSimulationData,
                                        	    name: 'operationMaintenance.simulateAcqEnable',
                                        	    id: 'operationMaintenance_simulateAcqEnable_Id',
                                        	    checked: false
    				                        },
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.exportDataLimits,
    				                        	xtype: 'numberfield',
    				                        	name:'operationMaintenance.exportLimit',
    				                        	id:'operationMaintenance_exportLimit_Id',
    				                        	minValue: 1,
    				                        	maxValue: 65534
    				                        }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
                                        	    xtype: 'checkboxfield',
                                        	    fieldLabel: loginUserLanguageResource.displayTheLogo,
                                        	    name: 'operationMaintenance.showLogo',
                                        	    id: 'operationMaintenance_showLogo_Id',
                                        	    checked: false
    				                        },
    				                        { 
    				                        	fieldLabel: loginUserLanguageResource.resourceMonitoringLimit ,
    				                        	xtype: 'numberfield',
    				                        	name:'operationMaintenance.resourceMonitoringSaveData',
    				                        	id:'operationMaintenance_resourceMonitoringSaveData_Id',
    				                        	minValue: 1
    				                        },
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.simulateDataSendingCycles+'(s)',
    				                        	xtype: 'numberfield',
    				                        	name:'operationMaintenance.sendCycle',
    				                        	id:'operationMaintenance_sendCycle_Id',
    				                        	minValue: 1
    				                        }
    				                    ]
    				                }, {
    				                    items: [
    				                    	{
                                        	    xtype: 'checkboxfield',
                                        	    fieldLabel: loginUserLanguageResource.printExceptionLogs,
                                        	    name: 'operationMaintenance.printExceptionLog',
                                        	    id: 'operationMaintenance_printExceptionLog_Id',
                                        	    checked: false
    				                        },{
                                        	    xtype: 'checkboxfield',
                                        	    fieldLabel: loginUserLanguageResource.printLogs,
                                        	    name: 'operationMaintenance.printLog',
                                        	    id: 'operationMaintenance_printLog_Id',
                                        	    checked: false
    				                        },{
                                        	    xtype: 'checkboxfield',
                                        	    fieldLabel: loginUserLanguageResource.printAdLogs,
                                        	    name: 'operationMaintenance.printAdLog',
                                        	    id: 'operationMaintenance_printAdLog_Id',
                                        	    checked: false
    				                        }
    				                    ]
    				                }]
    				            },
    				        	{
    				            	xtype: 'fieldset',
    				            	title: loginUserLanguageResource.historicalDataMaintenance,
    				            	layout: 'column',
    				            	defaults: {
    				                    layout: 'form',
    				                    xtype: 'container',
    				                    defaultType: 'textfield',
    				                    style: 'width: 33%'
    				                },
    				                items: [{
    				                    items: [
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.executionCycle+'('+loginUserLanguageResource.day+')',
    				                        	xtype: 'numberfield',
    				                        	name:'operationMaintenance.databaseMaintenanceCycle',
    				                        	id:'operationMaintenance_databaseMaintenanceCycle_Id',
    				                        	minValue: 0,
    						                    allowBlank: false,
    						                    listeners: {
    						                    	change ( field, newValue, oldValue, eOpts) {
    						                    		if(newValue<=0){
    						                    			Ext.getCmp('operationMaintenance_databaseMaintenanceStartTime_Id').disable();
    						                    			Ext.getCmp('operationMaintenance_databaseMaintenanceEndTime_Id').disable();
    						                    			
    						                    			Ext.getCmp('operationMaintenance_acqdata_hist_enabled_Id').disable();
    						                    			Ext.getCmp('operationMaintenance_acqrawdata_enabled_id').disable();
    						                    			Ext.getCmp('operationMaintenance_alarminfo_hist_enabled_id').disable();
    						                    			Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_enabled_id').disable();
    						                    			Ext.getCmp('operationMaintenance_dailycalculationdata_enabled_id').disable();
    						                    			Ext.getCmp('operationMaintenance_timingcalculationdata_enabled_id').disable();
    						                    			Ext.getCmp('operationMaintenance_timingrecorddata_enabled_id').disable();
    						                    			Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').disable();
    						                    			
    						                    			Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').disable();
    						                    			Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').disable();
    						                    			Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').disable();
    						                    			Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').disable();
    						                    			Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').disable();
    						                    			Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').disable();
    						                    			Ext.getCmp('operationMaintenance_timingrecorddata_retentionTim_id').disable();
    						                    			Ext.getCmp('operationMaintenance_acqdata_vacuate_retentionTim_id').disable();
    						                    		}else if(oldValue<=0 && newValue>0){
    						                    			Ext.getCmp('operationMaintenance_databaseMaintenanceStartTime_Id').enable();
    						                    			Ext.getCmp('operationMaintenance_databaseMaintenanceEndTime_Id').enable();
    						                    			
    						                    			Ext.getCmp('operationMaintenance_acqdata_hist_enabled_Id').enable();
    						                    			Ext.getCmp('operationMaintenance_acqrawdata_enabled_id').enable();
    						                    			Ext.getCmp('operationMaintenance_alarminfo_hist_enabled_id').enable();
    						                    			Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_enabled_id').enable();
    						                    			Ext.getCmp('operationMaintenance_dailycalculationdata_enabled_id').enable();
    						                    			Ext.getCmp('operationMaintenance_timingcalculationdata_enabled_id').enable();
    						                    			Ext.getCmp('operationMaintenance_timingrecorddata_enabled_id').enable();
    						                    			Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').enable();
    						                    			
    						                    			Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').enable();
    						                    			Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').enable();
    						                    			Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').enable();
    						                    			Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').enable();
    						                    			Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').enable();
    						                    			Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').enable();
    						                    			Ext.getCmp('operationMaintenance_timingrecorddata_retentionTim_id').enable();
    						                    			Ext.getCmp('operationMaintenance_acqdata_vacuate_retentionTim_id').enable();
    						                    		}
    						            			}
    						                    }
    						                },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: loginUserLanguageResource.historicalDataTable,
    					                        name: 'operationMaintenance.acqdata_hist_enabled',
    					                        id:'operationMaintenance_acqdata_hist_enabled_Id',
    					                        boxLabel: loginUserLanguageResource.enable
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: loginUserLanguageResource.sourceDataTable,
    					                        name: 'operationMaintenance.acqrawdata_enabled',
    					                        id: 'operationMaintenance_acqrawdata_enabled_id',
    					                        boxLabel: loginUserLanguageResource.enable
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: loginUserLanguageResource.alarmHistoryTable,
    					                        name: 'operationMaintenance.alarminfo_hist_enabled',
    					                        id: 'operationMaintenance_alarminfo_hist_enabled_id',
    					                        boxLabel: loginUserLanguageResource.enable
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: loginUserLanguageResource.dailyTotalCalculateTable,
    					                        name: 'operationMaintenance.dailytotalcalculate_hist_enabled',
    					                        id: 'operationMaintenance_dailytotalcalculate_hist_enabled_id',
    					                        boxLabel: loginUserLanguageResource.enable
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: loginUserLanguageResource.dailyCalculationTable,
    					                        name: 'operationMaintenance.dailycalculationdata_enabled',
    					                        id: 'operationMaintenance_dailycalculationdata_enabled_id',
    					                        boxLabel: loginUserLanguageResource.enable
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: loginUserLanguageResource.timingCalculationTable,
    					                        name: 'operationMaintenance.timingcalculationdata_enabled',
    					                        id: 'operationMaintenance_timingcalculationdata_enabled_id',
    					                        boxLabel: loginUserLanguageResource.enable
    					                    },
    					                    {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: loginUserLanguageResource.timingRecordTable,
    					                        name: 'operationMaintenance.timingrecorddata_enabled',
    					                        id: 'operationMaintenance_timingrecorddata_enabled_id',
    					                        boxLabel: loginUserLanguageResource.enable
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: loginUserLanguageResource.acqdataVacuateTable,
    					                        name: 'operationMaintenance.acqdata_vacuate_enabled',
    					                        id: 'operationMaintenance_acqdata_vacuate_enabled_id',
    					                        boxLabel: loginUserLanguageResource.enable
    					                    }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.executionTime,
    				                        	name:'operationMaintenance.databaseMaintenanceStartTime',
    				                        	id:'operationMaintenance_databaseMaintenanceStartTime_Id',
    						                    allowBlank: false
    						                },
    				                        { 
    						                	fieldLabel: loginUserLanguageResource.dataRetentionTime+'('+loginUserLanguageResource.day+')',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.acqdata_hist_retentionTime',
    					                        id:'operationMaintenance_acqdata_hist_retentionTime_Id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: loginUserLanguageResource.dataRetentionTime+'('+loginUserLanguageResource.day+')',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.acqrawdata_retentionTime',
    					                        id: 'operationMaintenance_acqrawdata_retentionTime_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: loginUserLanguageResource.dataRetentionTime+'('+loginUserLanguageResource.day+')',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.alarminfo_hist_retentionTime',
    					                        id: 'operationMaintenance_alarminfo_hist_retentionTime_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: loginUserLanguageResource.dataRetentionTime+'('+loginUserLanguageResource.day+')',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.dailytotalcalculate_hist_retentionTime',
    					                        id: 'operationMaintenance_dailytotalcalculate_hist_retentionTime_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: loginUserLanguageResource.dataRetentionTime+'('+loginUserLanguageResource.day+')',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.dailycalculationdata_retentionTime',
    					                        id: 'operationMaintenance_dailycalculationdata_retentionTime_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: loginUserLanguageResource.dataRetentionTime+'('+loginUserLanguageResource.day+')',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.timingcalculationdata_retentionTim',
    					                        id: 'operationMaintenance_timingcalculationdata_retentionTim_id',
    				                        	minValue: 1
    						                },
    						                { 
    						                	fieldLabel: loginUserLanguageResource.dataRetentionTime+'('+loginUserLanguageResource.day+')',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.timingrecorddata_retentionTim',
    					                        id: 'operationMaintenance_timingrecorddata_retentionTim_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: loginUserLanguageResource.dataRetentionTime+'('+loginUserLanguageResource.day+')',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.acqdata_vacuate_retentionTim',
    					                        id: 'operationMaintenance_acqdata_vacuate_retentionTim_id',
    				                        	minValue: 1
    						                }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.endTime,
    				                        	name:'operationMaintenance.databaseMaintenanceEndTime',
    				                        	id:'operationMaintenance_databaseMaintenanceEndTime_Id',
    						                    allowBlank: false
    						                }
    				                    ]
    				                }]
    				            },{
    				            	xtype: 'fieldset',
    				            	title: loginUserLanguageResource.dataSparseness,
    				            	layout: 'column',
    				            	defaults: {
    				                    layout: 'form',
    				                    xtype: 'container',
    				                    defaultType: 'textfield',
    				                    style: 'width: 33%'
    				                },
    				                items: [{
    				                    items: [
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.sparseRecordCount,
    						                    allowBlank: false,
    						                	xtype: 'numberfield',
    						                	name:'operationMaintenance.vacuateRecord',
    				                        	id:'operationMaintenance_vacuateRecord_Id',
    				                        	minValue: 1
    						                },
    						                {
    				                        	fieldLabel: loginUserLanguageResource.vacuateSaveIntervalWaveRange+'('+loginUserLanguageResource.minute+')',
    						                    allowBlank: false,
    						                	xtype: 'numberfield',
    						                	name:'operationMaintenance.vacuateSaveIntervalWaveRange',
    				                        	id:'operationMaintenance_vacuateSaveIntervalWaveRange_Id',
    				                        	minValue: 0
    						                }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.vacuateSaveInterval+'('+loginUserLanguageResource.minute+')',
    						                    allowBlank: false,
    						                	xtype: 'numberfield',
    						                	name:'operationMaintenance.vacuateSaveInterval',
    				                        	id:'operationMaintenance_vacuateSaveInterval_Id',
    				                        	minValue: 1
    						                }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.vacuateThreshold+'('+loginUserLanguageResource.recordsGreaterThan+')',
//    				                        	tpl:'查询总记录数超过该值，将从抽稀表中查询数据',
    						                    allowBlank: false,
    						                	xtype: 'numberfield',
    						                	name:'operationMaintenance.vacuateThreshold',
    				                        	id:'operationMaintenance_vacuateThreshold_Id',
    				                        	minValue: 1
    						                }
    				                    ]
    				                }]
    				            },{
    				            	xtype: 'fieldset',
    				            	title: loginUserLanguageResource.reportConfig,
    				            	layout: 'column',
    				            	defaults: {
    				                    layout: 'form',
    				                    xtype: 'container',
    				                    defaultType: 'textfield',
    				                    style: 'width: 33%'
    				                },
    				                items: [{
    				                    items: [
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.offsetTime+'('+loginUserLanguageResource.hour+')',
    						                    allowBlank: false,
    						                	xtype: 'numberfield',
    						                	name:'operationMaintenance.reportOffsetHour',
    				                        	id:'operationMaintenance_reportOffsetHour_Id',
    				                        	minValue: 0,
    				                        	maxValue: 12
    						                }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
    				                        	fieldLabel: loginUserLanguageResource.deviceHourlyReportInterval+'('+loginUserLanguageResource.hour+')',
    						                    allowBlank: false,
    						                	xtype: 'numberfield',
    						                	name:'operationMaintenance.reportInterval',
    				                        	id:'operationMaintenance_reportInterval_Id',
    				                        	minValue: 1,
    				                        	maxValue: 12
    						                }
    				                    ]
    				                }]
    				            }
    				        ]
    				    }]
    	            }]
        		},{
        			title: loginUserLanguageResource.backupAndRecovery,
        			id:'OperationMaintenanceBackupsInfoTabPanel_Id',
        			xtype: 'tabpanel',
        			activeTab: 0,
        			border: false,
	        		tabPosition: 'left',
	        		items:[{
	        			title: loginUserLanguageResource.exportData,
	        			id:'BatchExportModuleTreePanel_Id',
	        			iconCls: 'check3',
                    	tbar:[{
                            xtype: 'button',
                            text: loginUserLanguageResource.selectAll,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                            pressed: false,
                            handler: function (v, o) {
                            	backupDataSelectAll(true);
                            }
                        },{
                            xtype: 'button',
                            text: loginUserLanguageResource.deselectAll,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                            pressed: false,
                            handler: function (v, o) {
                            	backupDataSelectAll(false);
                            }
                        },'->', {
                    		xtype: 'button',
                    		text: loginUserLanguageResource.oneKeyBackup,
                    		iconCls: 'export',
                    		disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                    		hidden: false,
                    		handler: function (v, o) {
                    			oneKeyBackupData();
                    		}
                    	},{
                        	id: 'BatchExportModuleDataListSelectRow_Id',
                        	xtype: 'textfield',
                            value: -1,
                            hidden: true
                        },{
                        	id: 'BatchExportModuleDataListSelectCode_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                        }]
	        		},{
        				title: loginUserLanguageResource.importData,
        				id:'OperationMaintenanceDataImportTabPanel_Id',
            			layout: 'border',
            			items:[{
            				region: 'west',
            				title: loginUserLanguageResource.featureList,
            				width:'20%',
            				layout: 'fit',
            				id:'BatchImportModuleTreePanel_Id'
            			},{
            				region: 'center',
            				title: loginUserLanguageResource.backupDataImport,
            				layout: 'fit',
            				id:'OperationMaintenanceDataImportPanel_Id',
            				collapsible: true,
            				tbar:[{
                                xtype: 'button',
                                text: loginUserLanguageResource.previous,
                                iconCls: 'forward',
                                id:'OperationMaintenanceImportDataForwardBtn_Id',
                                disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                                handler: function (v, o) {
                                	var selectRow=Ext.getCmp("BatchExportModuleDataListSelectRow_Id").getValue();
                                	var gridPanel = Ext.getCmp("BatchImportModuleTreeGridPanel_Id");
                                	if(parseInt(selectRow)>0){
                                		gridPanel.getSelectionModel().deselectAll(true);
                                    	gridPanel.getSelectionModel().select(parseInt(selectRow)-1, true);
                                	}
                                }
                            },{
                                xtype: 'form',
                                id: 'OperationMaintenanceImportForm_Id',
                                width: 500,
                                bodyPadding: 0,
                                frame: true,
                                items: [{
                                    xtype: 'filefield',
                                    id: 'OperationMaintenanceImportFilefield_Id',
                                    disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                                    name: 'file',
                                    fieldLabel: loginUserLanguageResource.uploadFile,
                                    labelWidth: (getLabelWidth(loginUserLanguageResource.uploadFile, loginUserLanguage)),
                                    width: '100%',
                                    msgTarget: 'side',
                                    allowBlank: true,
                                    anchor: '100%',
                                    draggable: true,
                                    buttonText: loginUserLanguageResource.selectUploadFile,
                                    accept: '.json',
                                    listeners: {
                                        change: function (cmp) {
                                        	submitOperationMaintenanceImportedFile();
                                        }
                                    }
                        	    }]
                    		},{
                            	xtype: 'label',
                            	id: 'OperationMaintenanceImportDataTipLabel_Id',
                            	hidden:true,
                            	html: ''
                            },'->',{
                    			xtype: 'button',
                                text: loginUserLanguageResource.save,
                                iconCls: 'save',
                                id:'OperationMaintenanceImportDataSaveBtn_Id',
                                disabled: true,
                                handler: function (v, o) {
                                    var gridStore = Ext.getCmp("ImportBackupContentGridPanel_Id").getStore();
                                    var count = gridStore.getCount();
                                    var overlayCount = 0;
                                    var collisionCount = 0;
                                    for (var i = 0; i < count; i++) {
                                        if (gridStore.getAt(i).data.saveSign == 1) {
                                            overlayCount++;
                                        } else if (gridStore.getAt(i).data.saveSign == 2) {
                                            collisionCount++;
                                        }
                                    }
                                    if (overlayCount > 0 || collisionCount > 0) {
                                    	var info = loginUserLanguageResource.collisionInfo4+"?";
                                        Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
                                            if (btn == "yes") {
                                                saveBackupsData();
                                            }
                                        });
                                    } else {
                                        saveBackupsData();
                                    }
                                }
                    		},{
                                xtype: 'button',
                                text: loginUserLanguageResource.next,
                                iconCls: 'backwards',
                                id:'OperationMaintenanceImportDataBackwardBtn_Id',
                                disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                                handler: function (v, o) {
                                	var selectRow=Ext.getCmp("BatchExportModuleDataListSelectRow_Id").getValue();
                                	var gridPanel = Ext.getCmp("BatchImportModuleTreeGridPanel_Id");
                                	if(parseInt(selectRow)>=0){
                                		gridPanel.getSelectionModel().deselectAll(true);
                                    	gridPanel.getSelectionModel().select(parseInt(selectRow)+1, true);
                                	}
                                }
                            },{
                            	id: 'OperationMaintenanceImportDataCheckSign_Id',
                            	xtype: 'textfield',
                                value: 1,
                                hidden: true
                            }]
            			}]
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
	    					if(newCard.id=="BatchExportModuleTreePanel_Id"){
	    						var treeGridPanel = Ext.getCmp("BatchExportModuleTreeGridPanel_Id");
	    			            if (isNotVal(treeGridPanel)) {
	    			            	treeGridPanel.getStore().load();
	    			            }else{
	    			            	Ext.create("AP.store.operationMaintenance.BatchExportModuleTreeInfoStore");
	    			            }
	    					}else if(newCard.id=="OperationMaintenanceDataImportTabPanel_Id"){
	    						var treeGridPanel = Ext.getCmp("BatchImportModuleTreeGridPanel_Id");
	    			            if (isNotVal(treeGridPanel)) {
	    			            	treeGridPanel.getStore().load();
	    			            }else{
	    			            	Ext.create("AP.store.operationMaintenance.BatchImportModuleTreeInfoStore");
	    			            }
	    					}
	    				}
	    			}
        		},{
        			title: loginUserLanguageResource.oemConfig,
        			id:'OperationMaintenanceOEMInfoTabPanel_Id',
        			layout: 'border',
        			hidden: true,
        		    bodyStyle: 'background-color:#ffffff;',
        		    tbar: ['->',{
    	                xtype: 'button',
    	                text: loginUserLanguageResource.save,
    	                disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
    	                iconCls: 'save',
    	                hidden:false,
    	                handler: function (v, o) {
				        	var OperationMaintenanceOEMInfoSubForm = Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id").down('form');
				        	if (OperationMaintenanceOEMInfoSubForm.isValid()) {
				        		
				        	}
				        }
    	    		}],
        		    items: [{
        		    	region: 'center',
        		        height: '1500px',
        		        xtype: 'form',
        		        border: false,
        		        id: "OperationMaintenanceOEMInfoSubFormId",
        		        bodyPadding: 10,
        		        items: [{
        		        	xtype: 'form',
        		            bodyPadding: 10,
        		            fieldDefaults: {
        		                labelAlign: 'right',
//        		                labelWidth: 100,
        		                msgTarget: 'side'
        		            },
        		            items: [{
        		            	xtype: 'fieldset',
        	                    title: '项目名称及简介',
        	                    layout: 'column',
        	                    defaults: {
        	                        layout: 'form',
        	                        xtype: 'container',
        	                        defaultType: 'textfield',
        	                        width: '100%'
        	                    },
        	                    items: [{
        	                    	columnWidth: 1,
        	                    	items: [{
        	                    		fieldLabel: '项目名称'+ '<font color=red>*</font>',
    	                                name: 'operationMaintenance.databaseMaintenanceProjectName',
    	                                id: 'operationMaintenance_OEM_ProjectName_Id',
    	                                anchor: '100%',
    	                                allowBlank: false
        	                    	},{
        	                    		fieldLabel: '项目简介'+ '<font color=red>*</font>',
        	                    		xtype: 'textarea',
        	                    		width: '100%',
        	                    		anchor: '100%',
    	                                name: 'operationMaintenance.databaseMaintenanceProjectProfile',
    	                                id: 'operationMaintenance_OEM_ProjectProfile_Id',
    	                                allowBlank: false
        	                    	}]
        	                    }]
        		            },{
        		            	xtype: 'fieldset',
        	                    title: '背景及图标',
        	                    layout: 'column',
        	                    defaults: {
        	                        layout: 'form',
        	                        xtype: 'container',
        	                        defaultType: 'textfield',
        	                        width: '100%'
        	                    },
        	                    items: [{
        	                    	columnWidth: 1,
        	                    	items: [
//        	                    		{
//        	                    		xtype: 'filefield',
//        	                            emptyText: '请选择项目logo...',
//        	                            fieldLabel: '项目logo',
//        	                            name: 'operationMaintenance.databaseMaintenanceProjectLogo',
//        	                            buttonText: '上传',
//        	                            buttonConfig: {
//        	                                iconCls: 'file-uploads-image-add'
//        	                            }
//        	                    	},
        	                    	{
        	                    	    xtype: 'fieldcontainer',
        	                    	    fieldLabel: '项目logo',
        	                    	    layout: 'hbox',
        	                    	    items: [{
        	                    	        xtype: 'filefield',
        	                    	        name: 'operationMaintenance.databaseMaintenanceProjectLogo',
        	                    	        id: 'operationMaintenance_OEM_ProjectLogo_Id',
//        	                    	        flex: 1,
        	                    	        width:500,
        	                    	        margin: '0 5 0 0',
        	                    	        buttonText: '上传', // filefield自带的按钮文字
        	                    	        emptyText: '请选择项目logo...',
        	                    	        buttonConfig: {
        	                    	            // 可以自定义filefield按钮的样式
        	                    	            cls: 'x-btn-default'
        	                    	        }
        	                    	    }, {
        	                    	        xtype: 'button',
        	                    	        text: '修改',
//        	                    	        width: 60,
        	                    	        cls: 'x-btn-default', // 确保样式一致
        	                    	        style: {
        	                    	            // 如果需要更精确的样式匹配，可以添加一些调整
        	                    	            'margin-top': '0px'
        	                    	        },
        	                    	        handler: function() {
        	                    	            console.log('浏览按钮被点击');
        	                    	            // 这里可以触发自定义的文件浏览逻辑
        	                    	        }
        	                    	    }]
        	                    	},{
        	                    	    xtype: 'fieldcontainer',
        	                    	    fieldLabel: '网页logo',
        	                    	    layout: 'hbox',
        	                    	    items: [{
        	                    	        xtype: 'filefield',
        	                    	        name: 'operationMaintenance.databaseMaintenanceProjectFavicon',
        	                    	        id: 'operationMaintenance_OEM_ProjectFavicon_Id',
//        	                    	        flex: 1,
        	                    	        width:500,
        	                    	        margin: '0 5 0 0',
        	                    	        buttonText: '上传', // filefield自带的按钮文字
        	                    	        emptyText: '请选择网页logo...',
        	                    	        buttonConfig: {
        	                    	            // 可以自定义filefield按钮的样式
        	                    	            cls: 'x-btn-default'
        	                    	        }
        	                    	    }, {
        	                    	        xtype: 'button',
        	                    	        text: '修改',
//        	                    	        width: 60,
        	                    	        cls: 'x-btn-default', // 确保样式一致
        	                    	        style: {
        	                    	            // 如果需要更精确的样式匹配，可以添加一些调整
        	                    	            'margin-top': '0px'
        	                    	        },
        	                    	        handler: function() {
        	                    	            console.log('浏览按钮被点击');
        	                    	            // 这里可以触发自定义的文件浏览逻辑
        	                    	        }
        	                    	    }]
        	                    	},{
        	                    	    xtype: 'fieldcontainer',
        	                    	    fieldLabel: '登录界面背景图',
        	                    	    layout: 'hbox',
        	                    	    items: [{
        	                    	        xtype: 'filefield',
        	                    	        name: 'operationMaintenance.databaseMaintenanceLoginBackgroundImage',
        	                    	        id: 'operationMaintenance_OEM_ProjectLoginBackgroundImage_Id',
//        	                    	        flex: 1,
        	                    	        width:500,
        	                    	        margin: '0 5 0 0',
        	                    	        buttonText: '上传', // filefield自带的按钮文字
        	                    	        emptyText: '请选择图片...',
        	                    	        buttonConfig: {
        	                    	            // 可以自定义filefield按钮的样式
        	                    	            cls: 'x-btn-default'
        	                    	        }
        	                    	    }, {
        	                    	        xtype: 'button',
        	                    	        text: '修改',
//        	                    	        width: 60,
        	                    	        cls: 'x-btn-default', // 确保样式一致
        	                    	        style: {
        	                    	            // 如果需要更精确的样式匹配，可以添加一些调整
        	                    	            'margin-top': '0px'
        	                    	        },
        	                    	        handler: function() {
        	                    	            console.log('浏览按钮被点击');
        	                    	            // 这里可以触发自定义的文件浏览逻辑
        	                    	        }
        	                    	    }]
        	                    	},{
        	                    	    xtype: 'fieldcontainer',
        	                    	    fieldLabel: '帮助按钮图标',
        	                    	    layout: 'hbox',
        	                    	    items: [{
        	                    	        xtype: 'filefield',
        	                    	        name: 'operationMaintenance.databaseMaintenanceHelpButtonIcon',
        	                    	        id: 'operationMaintenance_OEM_HelpButtonIcon_Id',
//        	                    	        flex: 1,
        	                    	        width:500,
        	                    	        margin: '0 5 0 0',
        	                    	        buttonText: '上传', // filefield自带的按钮文字
        	                    	        emptyText: '请选择图片...',
        	                    	        buttonConfig: {
        	                    	            // 可以自定义filefield按钮的样式
        	                    	            cls: 'x-btn-default'
        	                    	        }
        	                    	    }, {
        	                    	        xtype: 'button',
        	                    	        text: '修改',
//        	                    	        width: 60,
        	                    	        cls: 'x-btn-default', // 确保样式一致
        	                    	        style: {
        	                    	            // 如果需要更精确的样式匹配，可以添加一些调整
        	                    	            'margin-top': '0px'
        	                    	        },
        	                    	        handler: function() {
        	                    	            console.log('浏览按钮被点击');
        	                    	            // 这里可以触发自定义的文件浏览逻辑
        	                    	        }
        	                    	    }]
        	                    	},{
        	                    	    xtype: 'fieldcontainer',
        	                    	    fieldLabel: '退出按钮图标',
        	                    	    layout: 'hbox',
        	                    	    items: [{
        	                    	        xtype: 'filefield',
        	                    	        name: 'operationMaintenance.databaseMaintenanceExitButtonIcon',
        	                    	        id: 'operationMaintenance_OEM_ExitButtonIcon_Id',
//        	                    	        flex: 1,
        	                    	        width:500,
        	                    	        margin: '0 5 0 0',
        	                    	        buttonText: '上传', // filefield自带的按钮文字
        	                    	        emptyText: '请选择图片...',
        	                    	        buttonConfig: {
        	                    	            // 可以自定义filefield按钮的样式
        	                    	            cls: 'x-btn-default'
        	                    	        }
        	                    	    }, {
        	                    	        xtype: 'button',
        	                    	        text: '修改',
//        	                    	        width: 60,
        	                    	        cls: 'x-btn-default', // 确保样式一致
        	                    	        style: {
        	                    	            // 如果需要更精确的样式匹配，可以添加一些调整
        	                    	            'margin-top': '0px'
        	                    	        },
        	                    	        handler: function() {
        	                    	            console.log('浏览按钮被点击');
        	                    	            // 这里可以触发自定义的文件浏览逻辑
        	                    	        }
        	                    	    }]
        	                    	},{
        	                    	    xtype: 'fieldcontainer',
        	                    	    fieldLabel: '语言切换按钮图标',
        	                    	    layout: 'hbox',
        	                    	    items: [{
        	                    	        xtype: 'filefield',
        	                    	        name: 'operationMaintenance.databaseMaintenanceSwitchButtonIcon',
        	                    	        id: 'operationMaintenance_OEM_SwitchButtonIcon_Id',
//        	                    	        flex: 1,
        	                    	        width:500,
        	                    	        margin: '0 5 0 0',
        	                    	        buttonText: '上传', // filefield自带的按钮文字
        	                    	        emptyText: '请选择图片...',
        	                    	        buttonConfig: {
        	                    	            // 可以自定义filefield按钮的样式
        	                    	            cls: 'x-btn-default'
        	                    	        }
        	                    	    }, {
        	                    	        xtype: 'button',
        	                    	        text: '修改',
//        	                    	        width: 60,
        	                    	        cls: 'x-btn-default', // 确保样式一致
        	                    	        style: {
        	                    	            // 如果需要更精确的样式匹配，可以添加一些调整
        	                    	            'margin-top': '0px'
        	                    	        },
        	                    	        handler: function() {
        	                    	            console.log('浏览按钮被点击');
        	                    	            // 这里可以触发自定义的文件浏览逻辑
        	                    	        }
        	                    	    }]
        	                    	},{
        	                    	    xtype: 'fieldcontainer',
        	                    	    fieldLabel: '禁用语言切换按钮图标',
        	                    	    layout: 'hbox',
        	                    	    items: [{
        	                    	        xtype: 'filefield',
        	                    	        name: 'operationMaintenance.databaseMaintenanceSwitchDisabledButtonIcon',
        	                    	        id: 'operationMaintenance_OEM_SwitchDisabledButtonIcon_Id',
//        	                    	        flex: 1,
        	                    	        width:500,
        	                    	        margin: '0 5 0 0',
        	                    	        buttonText: '上传', // filefield自带的按钮文字
        	                    	        emptyText: '请选择图片...',
        	                    	        buttonConfig: {
        	                    	            // 可以自定义filefield按钮的样式
        	                    	            cls: 'x-btn-default'
        	                    	        }
        	                    	    }, {
        	                    	        xtype: 'button',
        	                    	        text: '修改',
//        	                    	        width: 60,
        	                    	        cls: 'x-btn-default', // 确保样式一致
        	                    	        style: {
        	                    	            // 如果需要更精确的样式匹配，可以添加一些调整
        	                    	            'margin-top': '0px'
        	                    	        },
        	                    	        handler: function() {
        	                    	            console.log('浏览按钮被点击');
        	                    	            // 这里可以触发自定义的文件浏览逻辑
        	                    	        }
        	                    	    }]
        	                    	}]
        	                    }]
        		            }]
        		        }]
        		    }]
        		},{
        			title: loginUserLanguageResource.tagManagement,
        			id:'OperationMaintenanceTabManagerTabPanel_Id',
        			xtype: 'tabpanel',
        			activeTab: 0,
        			border: false,
	        		tabPosition: 'left',
	        		items:[{
	        			title: loginUserLanguageResource.projectTag,
	        			id:'OperationMaintenanceDeviceTypeMaintenancePanel_Id',
	        			iconCls: 'check3',
	        			layout: 'border',
	        			tbar: [{
	                        xtype: 'button',
	                        name: 'RoleNameBtn_Id',
	                        text: loginUserLanguageResource.refresh,
	                        iconCls: 'note-refresh',
	                        handler: function () {
	                        	var deviceTypeMaintenanceTreeGridView = Ext.getCmp("deviceTypeMaintenanceTreeGridView_Id");
                                if (isNotVal(deviceTypeMaintenanceTreeGridView)) {
                                	deviceTypeMaintenanceTreeGridView.getStore().load();
                                }else{
                                	Ext.create("AP.store.operationMaintenance.TabManagerInfoStore");
                                }
	                        }
	            		},'->',{
	    	                xtype: 'button',
	    	                text: loginUserLanguageResource.save,
	    	                disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
	    	                iconCls: 'save',
	    	                hidden:false,
	    	                handler: function (v, o) {
	    	                	var deviceTypeMaintenanceTreeGridView = Ext.getCmp("deviceTypeMaintenanceTreeGridView_Id");
	    	                	
//	    	                	var modifiedCells=getExtjsModifiedCells(deviceTypeMaintenanceTreeGridView);
	    	                	var store=deviceTypeMaintenanceTreeGridView.getStore();
	    	                	var modifiedRecords = store.getModifiedRecords();
	    	                	

    	                		var modifiedDeviceTypes=[];
    	                		
    	                		modifiedRecords.forEach(function(record) {
    	                			modifiedDeviceTypes.push({
    	                				id: record.data.deviceTypeId,
    	                				name_zh_CN: record.data.text_zh_CN,
    	                				name_en: record.data.text_en,
    	                				name_ru: record.data.text_ru,
    	                				parentId: record.data.parentNodeId,
    	                				sortNum: record.data.sortNum,
    	                				status: record.data.deviceTypeEnable?1:0
    	                	        });
    	                	    });
    	                		
    	                		
    	                		var selectDeviceTypeId = 0;
    	                		var selectionModel = deviceTypeMaintenanceTreeGridView.getSelectionModel();
    	                		var selectionRecord = selectionModel.getSelection();
    	                		if (selectionRecord.length > 0) {
    	                			selectDeviceTypeId = selectionRecord[0].data.deviceTypeId;
    	                		}
    	                	
    	                		var contentConfig={};
		                		contentConfig.DeviceRealTimeMonitoring={};
		                		contentConfig.DeviceHistoryQuery={};
		                		contentConfig.AlarmQuery={};
		                		
		                		contentConfig.DeviceRealTimeMonitoring.FESDiagramStatPie=false;
		                		contentConfig.DeviceRealTimeMonitoring.CommStatusStatPie=false;
		                		contentConfig.DeviceRealTimeMonitoring.RunStatusStatPie=false;
		                		contentConfig.DeviceRealTimeMonitoring.NumStatusStatPie=false;
		                		
		                		contentConfig.DeviceHistoryQuery.FESDiagramStatPie=false;
		                		contentConfig.DeviceHistoryQuery.CommStatusStatPie=false;
		                		contentConfig.DeviceHistoryQuery.RunStatusStatPie=false;
		                		contentConfig.DeviceHistoryQuery.NumStatusStatPie=false;
		                		
		                		contentConfig.AlarmQuery.FESDiagramResultAlarm=false;
		                		contentConfig.AlarmQuery.RunStatusAlarm=false;
		                		contentConfig.AlarmQuery.CommStatusAlarm=false;
		                		contentConfig.AlarmQuery.NumericValueAlarm=false;
		                		contentConfig.AlarmQuery.EnumValueAlarm=false;
		                		contentConfig.AlarmQuery.SwitchingValueAlarm=false;
		                		
		                		var projectTabConfigTreeGridView = Ext.getCmp("projectTabConfigTreeGridView_Id");
		                		if(isNotVal(projectTabConfigTreeGridView)){
		                			var selected = projectTabConfigTreeGridView.getChecked();
		                			Ext.Array.each(selected, function (name, index, countriesItSelf) {
		                		        var code = selected[index].get('code')
		                		        if(code.toUpperCase()=='realtimeMonitoringModule_FESResultStatisticsPieChart'.toUpperCase()){
		                		        	contentConfig.DeviceRealTimeMonitoring.FESDiagramStatPie=true;
		                		        }else if(code.toUpperCase()=='realtimeMonitoringModule_commStatusStatisticsPieChart'.toUpperCase()){
		                		        	contentConfig.DeviceRealTimeMonitoring.CommStatusStatPie=true;
		                		        }else if(code.toUpperCase()=='realtimeMonitoringModule_runStatusStatisticsPieChart'.toUpperCase()){
		                		        	contentConfig.DeviceRealTimeMonitoring.RunStatusStatPie=true;
		                		        }else if(code.toUpperCase()=='realtimeMonitoringModule_numStatusStatisticsPieChart'.toUpperCase()){
		                		        	contentConfig.DeviceRealTimeMonitoring.NumStatusStatPie=true;
		                		        }
		                		        
		                		        else if(code.toUpperCase()=='historyQueryModule_FESResultStatisticsPieChart'.toUpperCase()){
		                		        	contentConfig.DeviceHistoryQuery.FESDiagramStatPie=true;
		                		        }else if(code.toUpperCase()=='historyQueryModule_commStatusStatisticsPieChart'.toUpperCase()){
		                		        	contentConfig.DeviceHistoryQuery.CommStatusStatPie=true;
		                		        }else if(code.toUpperCase()=='historyQueryModule_runStatusStatisticsPieChart'.toUpperCase()){
		                		        	contentConfig.DeviceHistoryQuery.RunStatusStatPie=true;
		                		        }else if(code.toUpperCase()=='historyQueryModule_numStatusStatisticsPieChart'.toUpperCase()){
		                		        	contentConfig.DeviceHistoryQuery.NumStatusStatPie=true;
		                		        }

		                		        else if(code.toUpperCase()=='alarmQueryModule_numericValueAlarm'.toUpperCase()){
		                		        	contentConfig.AlarmQuery.NumericValueAlarm=true;
		                		        }
		                		        else if(code.toUpperCase()=='alarmQueryModule_enumValueAlarm'.toUpperCase()){
		                		        	contentConfig.AlarmQuery.EnumValueAlarm=true;
		                		        }
		                		        else if(code.toUpperCase()=='alarmQueryModule_switchingValueAlarm'.toUpperCase()){
		                		        	contentConfig.AlarmQuery.SwitchingValueAlarm=true;
		                		        }
		                		        else if(code.toUpperCase()=='alarmQueryModule_commStatusAlarm'.toUpperCase()){
		                		        	contentConfig.AlarmQuery.CommStatusAlarm=true;
		                		        }
		                		        else if(code.toUpperCase()=='alarmQueryModule_runStatusAlarm'.toUpperCase()){
		                		        	contentConfig.AlarmQuery.RunStatusAlarm=true;
		                		        }
		                		        else if(code.toUpperCase()=='alarmQueryModule_FESDiagramResultAlarm'.toUpperCase()){
		                		        	contentConfig.AlarmQuery.FESDiagramResultAlarm=true;
		                		        }
		                		    });
		                		}
    	                		
    	                		
    	                		Ext.Ajax.request({
    	                			method:'POST',
    	                			url:context + '/operationMaintenanceController/saveDeviceTypeMaintenanceData',
    	                			success:function(response) {
    	                				var data=Ext.JSON.decode(response.responseText);
    	                				
    	                				if (data.success) {
    	                	            	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
    	                	            	store.commitChanges();
    	                	            } else {
    	                	            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
    	                	            }
    	                			},
    	                			failure:function(){
    	                				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
    	                			},
    	                			params: {
    	                				data:JSON.stringify(modifiedDeviceTypes),
    	                				selectDeviceTypeId: selectDeviceTypeId,
    	                				contentConfig: JSON.stringify(contentConfig)
    	                	        }
    	                		});
	    	                }
	    	    		}],
	        		    items: [{
	        		        region: 'west',
	        		        title: loginUserLanguageResource.deviceTypeList,
	        		        width: '45%',
	        		        layout: 'fit',
	        		        collapsible: true,
	        		        id: 'OperationMaintenanceDeviceTypeMaintenanceListPanel_Id'
	        		    }, {
	        		        region: 'center',
	        		        title: loginUserLanguageResource.displayContentConfig,
	        		        id: 'OperationMaintenanceDeviceTypeContentConfigPanel_Id',
	        		        layout: 'fit'
	        		    }]
	        		},{
	        			title: loginUserLanguageResource.deviceTag,
	        			id:'OperationMaintenanceDeviceTabManagerTabPanel_Id',
	        			iconCls: 'check3',
	        			layout: 'border',
	        			tbar:[{
	                        xtype: 'button',
	                        name: 'RoleNameBtn_Id',
	                        text: loginUserLanguageResource.refresh,
	                        iconCls: 'note-refresh',
	                        handler: function () {
	                        	var operationMaintenanceDeviceTabManagerGridView = Ext.getCmp("operationMaintenanceDeviceTabManagerGridView_Id");
	                            if (isNotVal(operationMaintenanceDeviceTabManagerGridView)) {
	                            	operationMaintenanceDeviceTabManagerGridView.getStore().load();
	                            }else{
	                            	Ext.create("AP.store.operationMaintenance.DeviceTabManagerInfoStore");
	                            }
	                        }
	            		},{
	                        id: 'DeviceTabManagerInstanceSelectId_Id',
	                        xtype: 'textfield',
	                        value: 0,
	                        hidden: true
	                    },{
	                        id: 'addDeviceTabManagerInstanceSelectName_Id',
	                        xtype: 'textfield',
	                        value: '',
	                        hidden: true
	                    }, '->', {
	                        xtype: 'button',
	                        text: loginUserLanguageResource.add,
	                        disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
	                        iconCls: 'add',
	                        handler: function () {
	                        	var functionTabInfoWindow = Ext.create("AP.view.operationMaintenance.DeviceTabManagerInfoWindow");
	                        	functionTabInfoWindow.show();
	                        }
	            		},'-',{
	                        xtype: 'button',
	                        text: loginUserLanguageResource.deleteData,
	                        disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
	                        iconCls: 'delete',
	                        handler: function () {
	                        	var selectInstanceId=[];
	                        	var operationMaintenanceDeviceTabManagerGridView = Ext.getCmp("operationMaintenanceDeviceTabManagerGridView_Id");
	    	                	var selectionModel = operationMaintenanceDeviceTabManagerGridView.getSelectionModel();
	    	                    var selectionRecord = selectionModel.getSelection();
	    	                	if(selectionRecord.length>0){
	    	                		selectionRecord.forEach(function(record) {
	    	                			selectInstanceId.push(record.data.instanceId);
	    	                	    });
	    	                		
	    	                		Ext.Ajax.request({
	    	                			method:'POST',
	    	                			url:context + '/operationMaintenanceController/deleteDeviceTabManagerInstance',
	    	                			success:function(response) {
	    	                				var data=Ext.JSON.decode(response.responseText);
	    	                				if (data.success) {
	    	                					Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.deleteSuccessfully);
	    	                	            	Ext.getCmp("operationMaintenanceDeviceTabManagerGridView_Id").getStore().load();
	    	                	            } else {
	    	                	            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.deleteFailure+"</font>");
	    	                	            }
	    	                			},
	    	                			failure:function(){
	    	                				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
	    	                			},
	    	                			params: {
	    	                				instanceIds: selectInstanceId.join(",")
	    	                	        }
	    	                		});
	    	                		
	    	                	} else {
	    	                        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
	    	                    }
	                        	
	                        }
	            		},'-',{
	                        xtype: 'button',
	                        text: loginUserLanguageResource.save,
	                        disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
	                        iconCls: 'save',
	                        handler: function () {
	    	                	var operationMaintenanceDeviceTabManagerGridView = Ext.getCmp("operationMaintenanceDeviceTabManagerGridView_Id");
	    	                	
	    	                	var store=operationMaintenanceDeviceTabManagerGridView.getStore();
	    	                	var modifiedRecords = store.getModifiedRecords();
	    	                	
	    	                	var selectInstanceId=0;
	    	                	var selectionModel = operationMaintenanceDeviceTabManagerGridView.getSelectionModel();
	    	                    var selectionRecord = selectionModel.getSelection();
	    	                	if(selectionRecord.length>0){
	    	                		selectInstanceId=selectionRecord[0].data.instanceId;
	    	                	}
	    	                	
		                		var modifiedTabDisplayInstance=[];
		                		
		                		modifiedRecords.forEach(function(record) {
		                			var tabDisplayInstance={};
		                			tabDisplayInstance.id=record.data.instanceId;
		                			tabDisplayInstance.name=record.data.name;
		                			tabDisplayInstance.sort=record.data.sort;
		                			tabDisplayInstance.calculateType=0;
		                			if(record.data.calculateType==loginUserLanguageResource.SRPCalculate){
		                				tabDisplayInstance.calculateType=1;
		                			}else if(record.data.calculateType==loginUserLanguageResource.PCPCalculate){
		                				tabDisplayInstance.calculateType=2;
		                			}
		                			
		                			modifiedTabDisplayInstance.push(tabDisplayInstance);
		                	    });
		                		
		                		var instanceConfig={};
		                		instanceConfig.DeviceRealTimeMonitoring={};
		                		instanceConfig.DeviceHistoryQuery={};
		                		instanceConfig.PrimaryDevice={};
		                		
		                		instanceConfig.DeviceRealTimeMonitoring.WellboreAnalysis=Ext.getCmp('calculationModel_showRealtimeWellboreAnalysis_Id').getValue();
		                		instanceConfig.DeviceRealTimeMonitoring.SurfaceAnalysis=Ext.getCmp('calculationModel_showRealtimeSurfaceAnalysis_Id').getValue();
		                		instanceConfig.DeviceRealTimeMonitoring.TrendCurve=Ext.getCmp('calculationModel_showRealtimeTrendCurve_Id').getValue();
		                		instanceConfig.DeviceRealTimeMonitoring.DynamicData=Ext.getCmp('calculationModel_showRealtimeDynamicData_Id').getValue();
		                		instanceConfig.DeviceRealTimeMonitoring.DeviceControl=Ext.getCmp('calculationModel_showRealtimeDeviceControl_Id').getValue();
		                		instanceConfig.DeviceRealTimeMonitoring.DeviceInformation=Ext.getCmp('calculationModel_showRealtimeDeviceInformation_Id').getValue();
		                		
		                		instanceConfig.DeviceRealTimeMonitoring.RodStressChart_MaxRodStress=Ext.getCmp('calculationModel_rodStressChart_maxRodStress_Id').getValue();
		                		instanceConfig.DeviceRealTimeMonitoring.RodStressChart_RodStressRange=Ext.getCmp('calculationModel_rodStressChart_rodStressRange_Id').getValue();
		                		
		                		
		                		
		                		instanceConfig.DeviceHistoryQuery.TrendCurve=Ext.getCmp('calculationModel_showHistoryTrendCurve_Id').getValue();
		                		instanceConfig.DeviceHistoryQuery.TiledDiagram=Ext.getCmp('calculationModel_showHistoryTiledDiagram_Id').getValue();
		                		instanceConfig.DeviceHistoryQuery.DiagramOverlay=Ext.getCmp('calculationModel_showHistoryDiagramOverlay_Id').getValue();
		                		
		                		instanceConfig.PrimaryDevice.AdditionalInformation=Ext.getCmp('calculationModel_showPrimaryDeviceAdditionalInformation_Id').getValue();
		                		instanceConfig.PrimaryDevice.AuxiliaryDevice=Ext.getCmp('calculationModel_showPrimaryDeviceAuxiliaryDevice_Id').getValue();
		                		instanceConfig.PrimaryDevice.VideoConfig=Ext.getCmp('calculationModel_showPrimaryDeviceVideoConfig_Id').getValue();
		                		instanceConfig.PrimaryDevice.CalculateDataConfig=Ext.getCmp('calculationModel_showPrimaryDeviceCalculateDataConfig_Id').getValue();
		                		instanceConfig.PrimaryDevice.FSDiagramConstruction=Ext.getCmp('calculationModel_showPrimaryDeviceFSDiagramConstruction_Id').getValue();
		                		instanceConfig.PrimaryDevice.SystemParameterConfig=Ext.getCmp('calculationModel_showPrimaryDeviceSystemParameterConfiguration_Id').getValue();
		                		
		                		instanceConfig.PrimaryDevice.IntelligentFrequencyConversion=Ext.getCmp('calculationModel_showPrimaryDeviceIntelligentFrequencyConversion_Id').getValue();
		                		instanceConfig.PrimaryDevice.InterlockProtection=Ext.getCmp('calculationModel_showPrimaryDeviceInterlockProtection_Id').getValue();
		                		
		                		Ext.Ajax.request({
		                			method:'POST',
		                			url:context + '/operationMaintenanceController/saveDeviceTabManagerInstance',
		                			success:function(response) {
		                				var data=Ext.JSON.decode(response.responseText);
		                				
		                				if (data.success) {
		                	            	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
		                	            	store.commitChanges();
		                	            	
		                	            	Ext.getCmp("operationMaintenanceDeviceTabManagerGridView_Id").getStore().load();
		                	            } else {
		                	            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
		                	            }
		                			},
		                			failure:function(){
		                				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		                			},
		                			params: {
		                				data: JSON.stringify(modifiedTabDisplayInstance),
		                				selectInstanceId: selectInstanceId,
		                				instanceConfig: JSON.stringify(instanceConfig)
		                	        }
		                		});
		                	
	                        }
	            		}],
	        			items:[{
	        				region: 'west',
	        				title: loginUserLanguageResource.instanceList,
	        				width:'45%',
	        				layout: 'fit',
	        				collapsible: true,
	        				id:'OperationMaintenanceDeviceTabManangerPanel_Id'
	        			},{
	        				region: 'center',
	        				title: loginUserLanguageResource.displayContentConfig,
	        				id:'OperationMaintenanceDeviceTabManagerContentConfigPanel_Id',
	        				layout: 'border',
	        			    bodyStyle: 'background-color:#ffffff;',
	                        items: [{
	                            region: 'center',
	                            height: '1500px',
	                            xtype: 'form',
	                            border: false,
	                            id: "OperationMaintenanceDeviceTabManagerContentSubFormId",
	                            bodyPadding: 10,
	                            items: [{
	                                xtype: 'form',
	                                bodyPadding: 10,
	                                fieldDefaults: {
	                                    labelAlign: 'right',
	                                    labelWidth: 100,
	                                    msgTarget: 'side'
	                                },
	                                items: [
	        				            // 第一个 FieldSet
	                                    {
	                                        xtype: 'fieldset',
	                                        title: loginUserLanguageResource.realtimeMonitoringModule,
	                                        layout: 'column',
	                                        defaults: {
	                                            layout: 'form',
	                                            xtype: 'container',
	                                            defaultType: 'textfield',
	                                            style: 'width: 33%'
	                                        },
	                                        items: [{
	                                            items: [{
	                                            		xtype: 'checkboxfield',
	                                            	    fieldLabel: loginUserLanguageResource.wellboreAnalysis,
	                                            	    name: 'calculationModel.showRealtimeWellboreAnalysis',
	                                            	    id: 'calculationModel_showRealtimeWellboreAnalysis_Id',
	                                            	    checked: false
	                                            	},{
	                                            		xtype: 'checkboxfield',
	                                            	    fieldLabel: loginUserLanguageResource.dynamicData,
	                                            	    name: 'calculationModel.showRealtimeDynamicData',
	                                            	    id: 'calculationModel_showRealtimeDynamicData_Id',
	                                            	    checked: false
	                                            	}]
	        				                }, {
	                                            items: [{
	                                            	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.surfaceAnalysis,
	                                        	    name: 'calculationModel.showRealtimeSurfaceAnalysis',
	                                        	    id: 'calculationModel_showRealtimeSurfaceAnalysis_Id',
	                                        	    checked: false
	    				                        },{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.deviceControl,
	                                        	    name: 'calculationModel.showRealtimeDeviceControl',
	                                        	    id: 'calculationModel_showRealtimeDeviceControl_Id',
	                                        	    checked: false
	    				                        }]
	        				                }, {
	                                            items: [{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.trendCurve,
	                                        	    name: 'calculationModel.showRealtimeTrendCurve',
	                                        	    id: 'calculationModel_showRealtimeTrendCurve_Id',
	                                        	    checked: false
	    				                        },{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.deviceInformation,
	                                        	    name: 'calculationModel.showRealtimeDeviceInformation',
	                                        	    id: 'calculationModel_showRealtimeDeviceInformation_Id',
	                                        	    checked: false
	    				                        }]
	        				                },{
	        				                    xtype: 'container',        // 外层容器，负责占满整行
	        				                    style: 'width: 100%',      // 覆盖默认的33%，实现跨列
	        				                    layout: 'form',            // 保持与内部一致
	        				                    items: [
	        				                        {
	        				                            xtype: 'checkboxgroup',
	        				                            fieldLabel: loginUserLanguageResource.rodStressChartContent,
	        				                            labelWidth: 100,
	        				                            // 使用 hbox 布局让选项强制在一行（或通过 columns 和宽度控制）
	        				                            layout: {
	        				                                type: 'hbox',
	        				                                align: 'middle'
	        				                            },
	        				                            defaults: {
	        				                                margin: '0 15 0 0'   // 选项之间的右间隙
	        				                            },
	        				                            items: [
	        				                                {
	        				                                	boxLabel: loginUserLanguageResource.maxRodStress,
	                                                            name: 'calculationModel.rodStressChart_maxRodStress',
	                                                            id: 'calculationModel_rodStressChart_maxRodStress_Id',
	        				                                    checked: false,
	        				                                    flex: 1          // 可选，让选项均匀分布
	        				                                },
	        				                                {
	        				                                	boxLabel: loginUserLanguageResource.rodStressRange,
	                                                            name: 'calculationModel.rodStressChart_rodStressRange',
	                                                            id: 'calculationModel_rodStressChart_rodStressRange_Id',
	        				                                    checked: false,
	        				                                    flex: 1
	        				                                }
	        				                            ]
	        				                        }
	        				                    ]
	        				                }]
	        				            },{
	                                        xtype: 'fieldset',
	                                        title: loginUserLanguageResource.historyQueryModule,
	                                        layout: 'column',
	                                        defaults: {
	                                            layout: 'form',
	                                            xtype: 'container',
	                                            defaultType: 'textfield',
	                                            style: 'width: 33%'
	                                        },
	                                        items: [{
	                                            items: [{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.trendCurve,
	                                        	    name: 'calculationModel.showHistoryTrendCurve',
	                                        	    id: 'calculationModel_showHistoryTrendCurve_Id',
	                                        	    checked: false
	    				                        }]
	        				                }, {
	                                            items: [{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.tiledDiagram,
	                                        	    name: 'calculationModel.showHistoryTiledDiagram',
	                                        	    id: 'calculationModel_showHistoryTiledDiagram_Id',
	                                        	    checked: false
	    				                        }]
	        				                }, {
	                                            items: [{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.diagramOverlay,
	                                        	    name: 'calculationModel.showHistoryDiagramOverlay',
	                                        	    id: 'calculationModel_showHistoryDiagramOverlay_Id',
	                                        	    checked: false
	    				                        }]
	        				                }]
	        				            },{
	                                        xtype: 'fieldset',
	                                        title: loginUserLanguageResource.primaryDeviceModule,
	                                        layout: 'column',
	                                        defaults: {
	                                            layout: 'form',
	                                            xtype: 'container',
	                                            defaultType: 'textfield',
	                                            style: 'width: 33%'
	                                        },
	                                        items: [{
	                                            items: [{
	                                            	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.additionalInformation,
	                                        	    name: 'calculationModel.showPrimaryDeviceAdditionalInformation',
	                                        	    id: 'calculationModel_showPrimaryDeviceAdditionalInformation_Id',
	                                        	    checked: false
	    				                        },{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.calculateDataConfig,
	                                        	    name: 'calculationModel.showPrimaryDeviceCalculateDataConfig',
	                                        	    id: 'calculationModel_showPrimaryDeviceCalculateDataConfig_Id',
	                                        	    checked: false
	    				                        },{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.intelligentFrequencyConversion,
	                                        	    name: 'calculationModel.showPrimaryDeviceIntelligentFrequencyConversion',
	                                        	    id: 'calculationModel_showPrimaryDeviceIntelligentFrequencyConversion_Id',
	                                        	    checked: false
	    				                        }]
	        				                }, {
	                                            items: [{
	                                            	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.auxiliaryDevice,
	                                        	    name: 'calculationModel.showPrimaryDeviceAuxiliaryDevice',
	                                        	    id: 'calculationModel_showPrimaryDeviceAuxiliaryDevice_Id',
	                                        	    checked: false
	    				                        },{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.fsDiagramConstruction,
	                                        	    name: 'calculationModel.showPrimaryDeviceFSDiagramConstruction',
	                                        	    id: 'calculationModel_showPrimaryDeviceFSDiagramConstruction_Id',
	                                        	    checked: false
	    				                        },{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.interlockProtection,
	                                        	    name: 'calculationModel.showPrimaryDeviceInterlockProtection',
	                                        	    id: 'calculationModel_showPrimaryDeviceInterlockProtection_Id',
	                                        	    checked: false
	    				                        }]
	        				                }, {
	                                            items: [{
	                                            	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.videoConfig,
	                                        	    name: 'calculationModel.showPrimaryDeviceVideoConfig',
	                                        	    id: 'calculationModel_showPrimaryDeviceVideoConfig_Id',
	                                        	    checked: false
	    				                        },{
	    				                        	xtype: 'checkboxfield',
	                                        	    fieldLabel: loginUserLanguageResource.systemParameterConfiguration,
	                                        	    name: 'calculationModel.showPrimaryDeviceSystemParameterConfiguration',
	                                        	    id: 'calculationModel_showPrimaryDeviceSystemParameterConfiguration_Id',
	                                        	    checked: false
	    				                        }]
	        				                }]
	        				            }
	        				        ]
	        				    }]
	        	            }]
	        			}]
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
	    					if(newCard.id=="OperationMaintenanceDeviceTypeMaintenancePanel_Id"){
        						var deviceTypeMaintenanceTreeGridView = Ext.getCmp("deviceTypeMaintenanceTreeGridView_Id");
                                if (isNotVal(deviceTypeMaintenanceTreeGridView)) {
                                	deviceTypeMaintenanceTreeGridView.getStore().load();
                                }else{
                                	Ext.create("AP.store.operationMaintenance.TabManagerInfoStore");
                                }
        					}else if(newCard.id=="OperationMaintenanceDeviceTabManagerTabPanel_Id"){
        						var operationMaintenanceDeviceTabManagerGridView = Ext.getCmp("operationMaintenanceDeviceTabManagerGridView_Id");
                                if (isNotVal(operationMaintenanceDeviceTabManagerGridView)) {
                                	operationMaintenanceDeviceTabManagerGridView.getStore().load();
                                }else{
                                	Ext.create("AP.store.operationMaintenance.DeviceTabManagerInfoStore");
                                }
        					}
	    				}
	    			}
        		},{
        			title: loginUserLanguageResource.memoryCurve,
        			id:'OperationMaintenanceMonitorCurveTabPanel_Id',
        			layout: 'fit',
        		    tbar: [{
                        xtype: 'button',
                        text: loginUserLanguageResource.refresh,
                        iconCls: 'note-refresh',
                        hidden:false,
                        handler: function (v, o) {
                        	
                        	
                        	Ext.getCmp('OperationMaintenanceMonitorCurveStartDate_Id').setValue('');
                        	Ext.getCmp('OperationMaintenanceMonitorCurveStartDate_Id').setRawValue('');
                        	Ext.getCmp('OperationMaintenanceMonitorCurveStartTime_Hour_Id').setValue('');
                        	Ext.getCmp('OperationMaintenanceMonitorCurveStartTime_Minute_Id').setValue('');
                        	
                            Ext.getCmp('OperationMaintenanceMonitorCurveEndDate_Id').setValue('');
                            Ext.getCmp('OperationMaintenanceMonitorCurveEndDate_Id').setRawValue('');
                            Ext.getCmp('OperationMaintenanceMonitorCurveEndTime_Hour_Id').setValue('');
                        	Ext.getCmp('OperationMaintenanceMonitorCurveEndTime_Minute_Id').setValue('');
                        	
                        	getOperationMaintenanceMonitorCurveData();
                        }
            		},'-',{
                        xtype: 'datefield',
                        anchor: '100%',
                        fieldLabel: loginUserLanguageResource.range,
                        labelWidth: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage),
                        width: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage)+100,
                        format: 'Y-m-d ',
                        value: '',
                        id: 'OperationMaintenanceMonitorCurveStartDate_Id',
                        listeners: {
                        	select: function (combo, record, index) {
                        		
                        	}
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'OperationMaintenanceMonitorCurveStartTime_Hour_Id',
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
                    	id: 'OperationMaintenanceMonitorCurveStartTime_Minute_Id',
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
                        id: 'OperationMaintenanceMonitorCurveEndDate_Id',
                        listeners: {
                        	select: function (combo, record, index) {
                        		
                        	}
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'OperationMaintenanceMonitorCurveEndTime_Hour_Id',
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
                    	id: 'OperationMaintenanceMonitorCurveEndTime_Minute_Id',
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
                    },'-',{
                        xtype: 'button',
                        text: loginUserLanguageResource.search,
                        iconCls: 'search',
                        handler: function () {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('OperationMaintenanceMonitorCurveStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('OperationMaintenanceMonitorCurveStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('OperationMaintenanceMonitorCurveStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('OperationMaintenanceMonitorCurveStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('OperationMaintenanceMonitorCurveEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('OperationMaintenanceMonitorCurveEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('OperationMaintenanceMonitorCurveEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('OperationMaintenanceMonitorCurveEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	getOperationMaintenanceMonitorCurveData();
                        }
                    }],
        		    html: '<div id="OperationMaintenanceMonitorCurveDiv_Id" style="width:100%;height:100%;"></div>',
        	        listeners: {
        	            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	                if ($("#OperationMaintenanceMonitorCurveDiv_Id").highcharts() != undefined) {
        	                	highchartsResize("OperationMaintenanceMonitorCurveDiv_Id");
        	                }
        	            }
        	        }
        		},{
        			title: loginUserLanguageResource.lowerComputerProgramUpgrade,
        			id:'OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id',
        			layout: 'fit',
            		tbar:{
            			xtype:"container",
            			border:false,
            			items:[{
            				xtype:"toolbar",
            				items:[{
                                xtype: 'button',
                                text: loginUserLanguageResource.refresh,
                                iconCls: 'note-refresh',
                                hidden:false,
                                handler: function (v, o) {
                                	loadLowerComputerProgramUpgradeDeviceList();
                                }
                    		},'-',deviceListDeviceCombo,'->',{
                    			xtype: 'button',
                    			text:'采控程序下行',
                    			iconCls: 'downlink',
                    			disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                    			handler: function (v, o) {
                    				selectList=[];
                                	if(lowerComputerProgramUpgradeHandsontableHelper!=null && lowerComputerProgramUpgradeHandsontableHelper.hot!=undefined){
                                		var checkedArr=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtProp('checked');
                                    	Ext.Array.each(checkedArr, function (name, index, countriesItSelf) {
                                            if (checkedArr[index]) {
                                            	selectList.push(index);
                                            	lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(index,'boxDownlinkStatus',loginUserLanguageResource.waitingForDownlink);
                                            }
                                        });
                                	}
                                	
                                	if(selectList.length>0){
                                		lowerComputerProgramBatchUpgrade(selectList,'box');
                                	}else{
                                		Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.checkOne);
                                	}
                    			}
                    		},'-',{
                    			xtype: 'button',
                    			text:'计算程序下行',
                    			iconCls: 'downlink',
                    			disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                    			handler: function (v, o) {
                    				selectList=[];
                                	if(lowerComputerProgramUpgradeHandsontableHelper!=null && lowerComputerProgramUpgradeHandsontableHelper.hot!=undefined){
                                		var checkedArr=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtProp('checked');
                                    	Ext.Array.each(checkedArr, function (name, index, countriesItSelf) {
                                            if (checkedArr[index]) {
                                            	selectList.push(index);
                                            	lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(index,'acDownlinkStatus',loginUserLanguageResource.waitingForDownlink);
                                            }
                                        });
                                	}
                                	
                                	if(selectList.length>0){
                                		lowerComputerProgramBatchUpgrade(selectList,'ac');
                                	}else{
                                		Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.checkOne);
                                	}
                    			}
                    		},'-',{
                    			xtype: 'button',
                    			text:'版本号上行',
                    			iconCls: 'uplink',
                    			disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                    			handler: function (v, o) {
                    				deviceIdList=[];
                                	if(lowerComputerProgramUpgradeHandsontableHelper!=null && lowerComputerProgramUpgradeHandsontableHelper.hot!=undefined){
                                		var checkedArr=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtProp('checked');
                                    	Ext.Array.each(checkedArr, function (name, index, countriesItSelf) {
                                            if (checkedArr[index]) {
                                            	var deviceId=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtRowProp(index,'deviceId');
                                            	deviceIdList.push(deviceId);
                                            }
                                        });
                                	}
                                	lowerComputerProgramVersionDataBatchUplink(deviceIdList,'');
                    			}
                    		}]
            			},{
            				xtype:"toolbar",
            				items:[{
            				    xtype: 'button',
            				    text: loginUserLanguageResource.selectAll,
            				    disabled: loginUserOperationMaintenanceModuleRight.editFlag != 1,
            				    pressed: false,
            				    handler: function (v, o) {
            				        lowerComputerProgramUpgradeSelectAll(true);
            				    }
            				}, {
            				    xtype: 'button',
            				    text: loginUserLanguageResource.deselectAll,
            				    disabled: loginUserOperationMaintenanceModuleRight.editFlag != 1,
            				    pressed: false,
            				    handler: function (v, o) {
            				        lowerComputerProgramUpgradeSelectAll(false);
            				    }
            				}]
            			}]
            		},
        		    html: '<div class="OperationMaintenanceLowerComputerProgramUpgradeContainer" style="width:100%;height:100%;"><div class="con" id="OperationMaintenanceLowerComputerProgramUpgradeDiv_Id"></div></div>',
        	        listeners: {
        	            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
        	            	if (lowerComputerProgramUpgradeHandsontableHelper != null && lowerComputerProgramUpgradeHandsontableHelper.hot != null && lowerComputerProgramUpgradeHandsontableHelper.hot != undefined) {
        	            		var newWidth=width;
        	            		var newHeight=height-23-1;//减去工具条高度
        	            		var header=thisPanel.getHeader();
        	            		if(header){
        	            			newHeight=newHeight-header.lastBox.height-2;
        	            		}
        	            		lowerComputerProgramUpgradeHandsontableHelper.hot.updateSettings({
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
    					if(newCard.id=="OperationMaintenanceBasicInfoPanel_Id"){
    						loadOemOperationConfigInfo();
    					}else if(newCard.id=="OperationMaintenanceBackupsInfoTabPanel_Id"){
    						var importExportActiveId = Ext.getCmp(newCard.id).getActiveTab().id;
    						if(importExportActiveId=="BatchExportModuleTreePanel_Id"){
	    						var treeGridPanel = Ext.getCmp("BatchExportModuleTreeGridPanel_Id");
	    			            if (isNotVal(treeGridPanel)) {
	    			            	treeGridPanel.getStore().load();
	    			            }else{
	    			            	Ext.create("AP.store.operationMaintenance.BatchExportModuleTreeInfoStore");
	    			            }
	    					}else if(importExportActiveId=="OperationMaintenanceDataImportTabPanel_Id"){
	    						var treeGridPanel = Ext.getCmp("BatchImportModuleTreeGridPanel_Id");
	    			            if (isNotVal(treeGridPanel)) {
	    			            	treeGridPanel.getStore().load();
	    			            }else{
	    			            	Ext.create("AP.store.operationMaintenance.BatchImportModuleTreeInfoStore");
	    			            }
	    					}
    					}else if(newCard.id=="OperationMaintenanceOEMInfoTabPanel_Id"){
    						loadOemConfigInfo();
    					}else if(newCard.id=='OperationMaintenanceMonitorCurveTabPanel_Id'){
    						getOperationMaintenanceMonitorCurveData();
    					}else if(newCard.id=='OperationMaintenanceTabManagerTabPanel_Id'){
    						var tabManagerTabPanelactiveId = Ext.getCmp("OperationMaintenanceTabManagerTabPanel_Id").getActiveTab().id;
    						if(tabManagerTabPanelactiveId=='OperationMaintenanceDeviceTypeMaintenancePanel_Id'){
        						var deviceTypeMaintenanceTreeGridView = Ext.getCmp("deviceTypeMaintenanceTreeGridView_Id");
                                if (isNotVal(deviceTypeMaintenanceTreeGridView)) {
                                	deviceTypeMaintenanceTreeGridView.getStore().load();
                                }else{
                                	Ext.create("AP.store.operationMaintenance.TabManagerInfoStore");
                                }
        					}else if(tabManagerTabPanelactiveId=='OperationMaintenanceDeviceTabManagerTabPanel_Id'){
        						var operationMaintenanceDeviceTabManagerGridView = Ext.getCmp("operationMaintenanceDeviceTabManagerGridView_Id");
                                if (isNotVal(operationMaintenanceDeviceTabManagerGridView)) {
                                	operationMaintenanceDeviceTabManagerGridView.getStore().load();
                                }else{
                                	Ext.create("AP.store.operationMaintenance.DeviceTabManagerInfoStore");
                                }
        					} 
    					}else if(newCard.id=="OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id"){
    						loadLowerComputerProgramUpgradeDeviceList();
    					}
    				}
    			}
        	}],
        	listeners: {
    			beforeclose: function ( panel, eOpts) {
    				
    			},
    			afterrender: function ( panel, eOpts) {
    				loadOemOperationConfigInfo();
    			}
    		}
        });
        me.callParent(arguments);
    }
});

function loadOemOperationConfigInfo(){
	if(Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/operationMaintenanceController/loadOemConfigInfo',
		success:function(response) {
			if(Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id").getEl().unmask();
			}
			var data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				var configFile=data.configFile;
				initOemOperationConfigInfo(configFile);
//				alert(configFile.others.printLog);
			} else {
				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
			}
		},
		failure:function(){
			if(Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			
		}
	});
}

function loadOemConfigInfo(){
	if(Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/operationMaintenanceController/loadOemConfigInfo',
		success:function(response) {
			if(Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id").getEl().unmask();
			}
			var data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				var configFile=data.configFile;
				initOemConfigInfo(configFile);
			} else {
				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
			}
		},
		failure:function(){
			if(Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			
		}
	});
}

function initOemConfigInfo(configFile){
	Ext.getCmp('operationMaintenance_OEM_ProjectName_Id').setValue(loginUserLanguageResource.projectName);
	Ext.getCmp('operationMaintenance_OEM_ProjectProfile_Id').setValue(loginUserLanguageResource.projectProfile);
	Ext.getCmp('operationMaintenance_OEM_ProjectLogo_Id').setRawValue(configFile.oem.logo);
	Ext.getCmp('operationMaintenance_OEM_ProjectFavicon_Id').setRawValue(configFile.oem.favicon);
	Ext.getCmp('operationMaintenance_OEM_ProjectLoginBackgroundImage_Id').setRawValue(configFile.oem.loginBackgroundImage);
	
	Ext.getCmp('operationMaintenance_OEM_HelpButtonIcon_Id').setRawValue(configFile.oem.helpButtonIcon);
	Ext.getCmp('operationMaintenance_OEM_ExitButtonIcon_Id').setRawValue(configFile.oem.exitButtonIcon);
	
	Ext.getCmp('operationMaintenance_OEM_SwitchButtonIcon_Id').setRawValue(configFile.oem.switchButtonIcon);
	Ext.getCmp('operationMaintenance_OEM_SwitchDisabledButtonIcon_Id').setRawValue(configFile.oem.switchDisabledButtonIcon);
}

function initOemOperationConfigInfo(configFile){
	Ext.getCmp('operationMaintenance_LogonLanguageCombox_Id').setRawValue(configFile.others.loginLanguage);
	Ext.getCmp('operationMaintenance_showLogo_Id').setValue(configFile.others.showLogo);
	Ext.getCmp('operationMaintenance_printLog_Id').setValue(configFile.others.printLog);
	Ext.getCmp('operationMaintenance_printAdLog_Id').setValue(configFile.others.printAdLog);
	Ext.getCmp('operationMaintenance_printExceptionLog_Id').setValue(configFile.others.printExceptionLog);
	
	if(configFile.others.timeEfficiencyUnit==1){
    	Ext.getCmp('operationMaintenance_timeEfficiencyUnit1_Id').setValue(true);
    }else{
    	Ext.getCmp('operationMaintenance_timeEfficiencyUnit2_Id').setValue(true);
    }
	Ext.getCmp('operationMaintenance_resourceMonitoringSaveData_Id').setValue(configFile.others.resourceMonitoringSaveData);
	Ext.getCmp('operationMaintenance_exportLimit_Id').setValue(configFile.others.exportLimit);
	
	Ext.getCmp('operationMaintenance_simulateAcqEnable_Id').setValue(configFile.others.simulateAcqEnable);
	Ext.getCmp('operationMaintenance_sendCycle_Id').setValue(configFile.others.sendCycle);
	
	Ext.getCmp('operationMaintenance_databaseMaintenanceCycle_Id').setValue(configFile.databaseMaintenance.cycle);
	Ext.getCmp('operationMaintenance_databaseMaintenanceStartTime_Id').setValue(configFile.databaseMaintenance.startTime);
	Ext.getCmp('operationMaintenance_databaseMaintenanceEndTime_Id').setValue(configFile.databaseMaintenance.endTime);
	
	Ext.getCmp('operationMaintenance_acqdata_hist_enabled_Id').setValue(configFile.databaseMaintenance.tableConfig.acqdata_hist.enabled);
	Ext.getCmp('operationMaintenance_acqrawdata_enabled_id').setValue(configFile.databaseMaintenance.tableConfig.acqrawdata.enabled);
	Ext.getCmp('operationMaintenance_alarminfo_hist_enabled_id').setValue(configFile.databaseMaintenance.tableConfig.alarminfo_hist.enabled);
	Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_enabled_id').setValue(configFile.databaseMaintenance.tableConfig.dailytotalcalculate_hist.enabled);
	Ext.getCmp('operationMaintenance_dailycalculationdata_enabled_id').setValue(configFile.databaseMaintenance.tableConfig.dailycalculationdata.enabled);
	Ext.getCmp('operationMaintenance_timingcalculationdata_enabled_id').setValue(configFile.databaseMaintenance.tableConfig.timingcalculationdata.enabled);
	Ext.getCmp('operationMaintenance_timingrecorddata_enabled_id').setValue(configFile.databaseMaintenance.tableConfig.timingrecorddata.enabled);
	Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').setValue(configFile.databaseMaintenance.tableConfig.acqdata_vacuate.enabled);
	
	Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').setValue(configFile.databaseMaintenance.tableConfig.acqdata_hist.retentionTime);
	Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').setValue(configFile.databaseMaintenance.tableConfig.acqrawdata.retentionTime);
	Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').setValue(configFile.databaseMaintenance.tableConfig.alarminfo_hist.retentionTime);
	Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').setValue(configFile.databaseMaintenance.tableConfig.dailytotalcalculate_hist.retentionTime);
	Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').setValue(configFile.databaseMaintenance.tableConfig.dailycalculationdata.retentionTime);
	Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').setValue(configFile.databaseMaintenance.tableConfig.timingcalculationdata.retentionTime);
	Ext.getCmp('operationMaintenance_timingrecorddata_retentionTim_id').setValue(configFile.databaseMaintenance.tableConfig.timingrecorddata.retentionTime);
	Ext.getCmp('operationMaintenance_acqdata_vacuate_retentionTim_id').setValue(configFile.databaseMaintenance.tableConfig.acqdata_vacuate.retentionTime);
	
	Ext.getCmp('operationMaintenance_vacuateRecord_Id').setValue(configFile.dataVacuate.vacuateRecord);
	Ext.getCmp('operationMaintenance_vacuateSaveInterval_Id').setValue(configFile.dataVacuate.saveInterval);
	Ext.getCmp('operationMaintenance_vacuateSaveIntervalWaveRange_Id').setValue(configFile.dataVacuate.saveIntervalWaveRange);
	Ext.getCmp('operationMaintenance_vacuateThreshold_Id').setValue(configFile.dataVacuate.vacuateThreshold);
	
	Ext.getCmp('operationMaintenance_reportOffsetHour_Id').setValue(configFile.report.offsetHour);
	Ext.getCmp('operationMaintenance_reportInterval_Id').setValue(configFile.report.interval);
	
	if(configFile.others.simulateAcqEnable){
		Ext.getCmp('operationMaintenance_sendCycle_Id').enable();
	}else{
		Ext.getCmp('operationMaintenance_sendCycle_Id').disable();
	}
	
	if(configFile.databaseMaintenance.cycle<=0){
		Ext.getCmp('operationMaintenance_databaseMaintenanceStartTime_Id').disable();
		Ext.getCmp('operationMaintenance_databaseMaintenanceEndTime_Id').disable();
		
		Ext.getCmp('operationMaintenance_acqdata_hist_enabled_Id').disable();
		Ext.getCmp('operationMaintenance_acqrawdata_enabled_id').disable();
		Ext.getCmp('operationMaintenance_alarminfo_hist_enabled_id').disable();
		Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_enabled_id').disable();
		Ext.getCmp('operationMaintenance_dailycalculationdata_enabled_id').disable();
		Ext.getCmp('operationMaintenance_timingcalculationdata_enabled_id').disable();
		Ext.getCmp('operationMaintenance_timingrecorddata_enabled_id').disable();
		Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').disable();
		
		Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').disable();
		Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').disable();
		Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').disable();
		Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').disable();
		Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').disable();
		Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').disable();
		Ext.getCmp('operationMaintenance_timingrecorddata_retentionTim_id').disable();
		Ext.getCmp('operationMaintenance_acqdata_vacuate_retentionTim_id').disable();
	}else{
		Ext.getCmp('operationMaintenance_databaseMaintenanceStartTime_Id').enable();
		Ext.getCmp('operationMaintenance_databaseMaintenanceEndTime_Id').enable();
		
		Ext.getCmp('operationMaintenance_acqdata_hist_enabled_Id').enable();
		Ext.getCmp('operationMaintenance_acqrawdata_enabled_id').enable();
		Ext.getCmp('operationMaintenance_alarminfo_hist_enabled_id').enable();
		Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_enabled_id').enable();
		Ext.getCmp('operationMaintenance_dailycalculationdata_enabled_id').enable();
		Ext.getCmp('operationMaintenance_timingcalculationdata_enabled_id').enable();
		Ext.getCmp('operationMaintenance_timingrecorddata_enabled_id').enable();
		Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').enable();
		
		Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').enable();
		Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').enable();
		Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').enable();
		Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').enable();
		Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').enable();
		Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').enable();
		Ext.getCmp('operationMaintenance_timingrecorddata_retentionTim_id').enable();
		Ext.getCmp('operationMaintenance_acqdata_vacuate_retentionTim_id').enable();
	}
	
}

function submitOperationMaintenanceImportedFile(){
	var code=Ext.getCmp("BatchExportModuleDataListSelectCode_Id").getValue();
	if(code.toUpperCase()=='module'.toUpperCase()){
		submitBackupModuleFile();
    }else if(code.toUpperCase()=='dataDictionary'.toUpperCase()){
    	submitBackupDataDictionaryFile();
    }else if(code.toUpperCase()=='organization'.toUpperCase()){
    	submitBackupOrganizationFile();
    }else if(code.toUpperCase()=='role'.toUpperCase()){
    	submitBackupImportedRoleFile();
    }else if(code.toUpperCase()=='user'.toUpperCase()){
    	submitBackupUserFile();
    }else if(code.toUpperCase()=='auxiliaryDevice'.toUpperCase()){
    	submitAuxiliaryDeviceBackupData();
    }else if(code.toUpperCase()=='primaryDevice'.toUpperCase()){
    	submitPrimaryDeviceBackupData();
    }else if(code.toUpperCase()=='protocol'.toUpperCase()){
    	submitProtocolBackupData();
    }else if(code.toUpperCase()=='acqUnit'.toUpperCase()){
    	submitAcqUnitBackupData();
    }else if(code.toUpperCase()=='displayUnit'.toUpperCase()){
    	submitDisplayUnitBackupData();
    }else if(code.toUpperCase()=='alarmUnit'.toUpperCase()){
    	submitAlarmUnitBackupData();
    }else if(code.toUpperCase()=='reportUnit'.toUpperCase()){
    	submitReportUnitBackupData();
    }else if(code.toUpperCase()=='acqInstance'.toUpperCase()){
    	submitAcqInstanceBackupData();
    }else if(code.toUpperCase()=='displayInstance'.toUpperCase()){
    	submitDisplayInstanceBackupData();
    }else if(code.toUpperCase()=='alarmInstance'.toUpperCase()){
    	submitAlarmInstanceBackupData();
    }else if(code.toUpperCase()=='reportInstance'.toUpperCase()){
    	submitReportInstanceBackupData();
    }
}

function oneKeyBackupData(){
	var records = Ext.getCmp("BatchExportModuleTreeGridPanel_Id").store.data.items;
	var selectedRecordCodeArr=[];
	
	Ext.Array.each(records, function (name, index, countriesItSelf) {
        var checked=records[index].get('checked');
        var enabled=!records[index].get('disabled');
        if(enabled && checked){
            var code = records[index].get('code');
            selectedRecordCodeArr.push(code);
        }
    });
	
	if (selectedRecordCodeArr.length > 0) {
		Ext.Array.each(selectedRecordCodeArr, function (name, index, countriesItSelf) {
	        setTimeout(() => {
	        	var code = selectedRecordCodeArr[index];
		        if(code.toUpperCase()=='module'.toUpperCase()){
		        	exportModuleBackupData();
		        }else if(code.toUpperCase()=='dataDictionary'.toUpperCase()){
		        	exportDataDictionaryBackupData();
		        }else if(code.toUpperCase()=='organization'.toUpperCase()){
		        	exportOrganizationBackupData();
		        }else if(code.toUpperCase()=='role'.toUpperCase()){
		        	exportRoleBackupData();
		        }else if(code.toUpperCase()=='user'.toUpperCase()){
		        	exportUserBackupData();
		        }else if(code.toUpperCase()=='auxiliaryDevice'.toUpperCase()){
		        	exportAuxiliaryDeviceBackupData();
		        }else if(code.toUpperCase()=='primaryDevice'.toUpperCase()){
		        	exportPrimaryDeviceBackupData();
		        }else if(code.toUpperCase()=='protocol'.toUpperCase()){
		        	exportProtocolBackupData();
		        }else if(code.toUpperCase()=='acqUnit'.toUpperCase()){
		        	exportAcqUnitBackupData();
		        }else if(code.toUpperCase()=='displayUnit'.toUpperCase()){
		        	exportDisplayUnitBackupData();
		        }else if(code.toUpperCase()=='alarmUnit'.toUpperCase()){
		        	exportAlarmUnitBackupData();
		        }else if(code.toUpperCase()=='reportUnit'.toUpperCase()){
		        	exportReportUnitBackupData();
		        }else if(code.toUpperCase()=='acqInstance'.toUpperCase()){
		        	exportAcqInstanceBackupData();
		        }else if(code.toUpperCase()=='displayInstance'.toUpperCase()){
		        	exportDisplayInstanceBackupData();
		        }else if(code.toUpperCase()=='alarmInstance'.toUpperCase()){
		        	exportAlarmInstanceBackupData();
		        }else if(code.toUpperCase()=='reportInstance'.toUpperCase()){
		        	exportReportInstanceBackupData();
		        }
	        	console.log('export data:'+code);
            }, 1000*index);
	    });
		Ext.getCmp("BatchExportModuleTreeGridPanel_Id").getStore().commitChanges();
	} else {
    	Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
}
function exportModuleBackupData(){
	var url = context + '/moduleManagerController/exportModuleCompleteData';
	var timestamp=new Date().getTime();
	var key='exportModuleCompleteData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.moduleExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportDataDictionaryBackupData(){
	var url = context + '/systemdataInfoController/exportDataDictionaryCompleteData';
	var timestamp=new Date().getTime();
	var key='exportDataDictionaryCompleteData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.dataDictionaryExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportOrganizationBackupData(){
	var url = context + '/orgManagerController/exportOrganizationCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportOrganizationCompleteData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.organizationExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportUserBackupData(){
	var url = context + '/userManagerController/exportUserCompleteData';
	var timestamp=new Date().getTime();
	var key='exportUserCompleteData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.userExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportRoleBackupData(){
	var url = context + '/roleManagerController/exportRoleCompleteData';
	var timestamp=new Date().getTime();
	var key='exportRoleCompleteData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.roleExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportProtocolBackupData(){
	var url = context + '/acquisitionUnitManagerController/exportAllProtocolData';
	var timestamp=new Date().getTime();
	var key='exportProtocolBackupData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.exportProtocol)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportAcqUnitBackupData(){
	var url = context + '/acquisitionUnitManagerController/exportAllProtocolAcqUnitData';
	var timestamp=new Date().getTime();
	var key='exportAllProtocolAcqUnitData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.exportAcqUnit)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportDisplayUnitBackupData(){
	var url = context + '/acquisitionUnitManagerController/exportAllProtocolDisplayUnitData';
	var timestamp=new Date().getTime();
	var key='exportAllProtocolDisplayUnitData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.exportDisplayUnit)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportAlarmUnitBackupData(){
	var url = context + '/acquisitionUnitManagerController/exportAllProtocolAlarmUnitData';
	var timestamp=new Date().getTime();
	var key='exportAllProtocolAlarmUnitData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.exportAlarmUnit)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportReportUnitBackupData(){
	var url = context + '/acquisitionUnitManagerController/exportAllReportUnitData';
	var timestamp=new Date().getTime();
	var key='exportAllReportUnitData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.exportReportUnit)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportAcqInstanceBackupData(){
	var url = context + '/acquisitionUnitManagerController/exportAllProtocolAcqInstanceData';
	var timestamp=new Date().getTime();
	var key='exportAllProtocolAcqInstanceData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.exportAcqInstance)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportDisplayInstanceBackupData(){
	var url = context + '/acquisitionUnitManagerController/exportAllProtocolDisplayInstanceData';
	var timestamp=new Date().getTime();
	var key='exportAllProtocolDisplayInstanceData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.exportDisplayInstance)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportAlarmInstanceBackupData(){
	var url = context + '/acquisitionUnitManagerController/exportAllProtocolAlarmInstanceData';
	var timestamp=new Date().getTime();
	var key='exportAllProtocolAlarmInstanceData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.exportAlarmInstance)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportReportInstanceBackupData(){
	var url = context + '/acquisitionUnitManagerController/exportAllReportInstanceData';
	var timestamp=new Date().getTime();
	var key='exportReportInstanceBackupData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.exportReportInstance)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
}

function exportPrimaryDeviceBackupData(){
	var url = context + '/wellInformationManagerController/exportDeviceCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportPrimaryDeviceBackupData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	
	
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.primaryDdeviceExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}

function exportAuxiliaryDeviceBackupData(){
    var fileName=loginUserLanguageResource.auxiliaryDdeviceExportFileName;
    var title=fileName;
	
	var url = context + '/wellInformationManagerController/exportAuxiliaryDeviceCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportAuxiliaryDeviceBackupData'+'_'+timestamp;
	var maskPanelId='BatchExportModuleTreePanel_Id';
	
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}

function submitBackupModuleFile() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if (form.isValid()) {
        form.submit({
            url: context + '/moduleManagerController/uploadImportedModuleFile',
            timeout: 1000 * 60 * 10,
            method: 'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function (response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupModuleContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
                Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
            }
        });
    }
    return false;
};

function submitBackupDataDictionaryFile() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if (form.isValid()) {
        form.submit({
            url: context + '/systemdataInfoController/uploadImportedDataDictionaryFile',
            timeout: 1000 * 60 * 10,
            method: 'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function (response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupDataDictionaryContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
                Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
            }
        });
    }
    return false;
};

function submitBackupOrganizationFile() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if (form.isValid()) {
        form.submit({
            url: context + '/orgManagerController/uploadImportedOrganizationFile',
            timeout: 1000 * 60 * 10,
            method: 'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function (response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupOrganizationContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
                Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
            }
        });
    }
    return false;
};

function submitBackupImportedRoleFile() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if (form.isValid()) {
        form.submit({
            url: context + '/roleManagerController/uploadImportedRoleFile',
            timeout: 1000 * 60 * 10,
            method: 'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function (response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupRoleContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
                Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
            }
        });
    }
    return false;
};

function submitBackupUserFile() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if (form.isValid()) {
        form.submit({
            url: context + '/userManagerController/uploadImportedUserFile',
            timeout: 1000 * 60 * 10,
            method: 'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function (response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupUserContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
                Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
            }
        });
    }
    return false;
};

function submitProtocolBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedProtocolFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupProtocolContentTreeInfoStore');
                }
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
            	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
			}
        });
    }
    return false;
};

function submitAcqUnitBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedAcqUnitFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupAcqUnitContentTreeInfoStore');
                }
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
            	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
			}
        });
    }
    return false;
};

function submitDisplayUnitBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedDisplayUnitFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupDisplayUnitContentTreeInfoStore');
                }
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
            	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
			}
        });
    }
    return false;
};

function submitAlarmUnitBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedAlarmUnitFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupAlarmUnitContentTreeInfoStore');
                }
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
            	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
			}
        });
    }
    return false;
};

function submitReportUnitBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedReportUnitFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupReportUnitContentTreeInfoStore');
                }
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
            	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
			}
        });
    }
    return false;
};

function submitAcqInstanceBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedAcqInstanceFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupAcqInstanceContentTreeInfoStore');
                }
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
            	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
			}
        });
    }
    return false;
};

function submitDisplayInstanceBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedDisplayInstanceFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupDisplayInstanceContentTreeInfoStore');
                }
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
            	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
			}
        });
    }
    return false;
};

function submitAlarmInstanceBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedAlarmInstanceFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupAlarmInstanceContentTreeInfoStore');
                }
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
            	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
			}
        });
    }
    return false;
};

function submitReportInstanceBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedReportInstanceFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupReportInstanceContentTreeInfoStore');
                }
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
            	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
			}
        });
    }
    return false;
};

function submitAuxiliaryDeviceBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if (form.isValid()) {
        form.submit({
            url: context + '/wellInformationManagerController/uploadAuxiliaryDeviceBackupData',
            timeout: 1000 * 60 * 10,
            method: 'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function (response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupAuxiliaryDeviceContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
                Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
            }
        });
    }
    return false;
};

function submitPrimaryDeviceBackupData() {
	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
    var form = Ext.getCmp("OperationMaintenanceImportForm_Id");
    if (form.isValid()) {
        form.submit({
            url: context + '/wellInformationManagerController/uploadPrimaryDeviceBackupData',
            timeout: 1000 * 60 * 10,
            method: 'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function (response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                    Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                }

                var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupPrimaryDeviceContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
                Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
            }
        });
    }
    return false;
};

adviceImportBackupDataCollisionInfoColor = function (val, o, p, e) {
    var saveSign = p.data.saveSign;
    var tipval = val;
    var backgroundColor = '#FFFFFF';
    var color = '#DC2828';
    if (saveSign == 0) {
        color = '#000000';
    }
    var opacity = 0;
    var rgba = color16ToRgba(backgroundColor, opacity);
    o.style = 'background-color:' + rgba + ';color:' + color + ';';
    if (isNotVal(tipval)) {
        return '<span data-qtip="' + tipval + '" data-dismissDelay=10000>' + val + '</span>';
    }
}

function backupDataSelectAll(select) {
    var store= Ext.getCmp("BatchExportModuleTreeGridPanel_Id").getStore();
	store.each(function (record) {
		var enabled=!record.get('disabled');
		if(enabled){
			record.set('checked', select);
		}
    });
}

function saveBackupsData(){
	var code=Ext.getCmp("BatchExportModuleDataListSelectCode_Id").getValue();
	if(code.toUpperCase()=='module'.toUpperCase()){
		saveBackupModuleFile();
    }else if(code.toUpperCase()=='dataDictionary'.toUpperCase()){
    	saveBackupDataDictionaryFile();
    }else if(code.toUpperCase()=='organization'.toUpperCase()){
    	saveBackupOrganizationFile();
    }else if(code.toUpperCase()=='role'.toUpperCase()){
    	saveBackupImportedRoleFile();
    }else if(code.toUpperCase()=='user'.toUpperCase()){
    	saveBackupUserFile();
    }else if(code.toUpperCase()=='auxiliaryDevice'.toUpperCase()){
    	saveAuxiliaryDeviceBackupData();
    }else if(code.toUpperCase()=='primaryDevice'.toUpperCase()){
    	savePrimaryDeviceBackupData();
    }else if(code.toUpperCase()=='protocol'.toUpperCase()){
    	saveProtocolBackupData();
    }else if(code.toUpperCase()=='acqUnit'.toUpperCase()){
    	saveAcqUnitBackupData();
    }else if(code.toUpperCase()=='displayUnit'.toUpperCase()){
    	saveDisplayUnitBackupData();
    }else if(code.toUpperCase()=='alarmUnit'.toUpperCase()){
    	saveAlarmUnitBackupData();
    }else if(code.toUpperCase()=='reportUnit'.toUpperCase()){
    	saveReportUnitBackupData();
    }else if(code.toUpperCase()=='acqInstance'.toUpperCase()){
    	saveAcqInstanceBackupData();
    }else if(code.toUpperCase()=='displayInstance'.toUpperCase()){
    	saveDisplayInstanceBackupData();
    }else if(code.toUpperCase()=='alarmInstance'.toUpperCase()){
    	saveAlarmInstanceBackupData();
    }else if(code.toUpperCase()=='reportInstance'.toUpperCase()){
    	saveReportInstanceBackupData();
    }
}

function saveBackupModuleFile(){
	if(Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/moduleManagerController/saveAllImportedModule',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
            Ext.getCmp("MainIframeView_Id").getStore().load();//右侧模块导航数刷新
            
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceOEMInfoTabPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveBackupDataDictionaryFile(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/systemdataInfoController/saveAllImportedDataDictionary',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveBackupOrganizationFile(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/orgManagerController/saveAllImportedOrganization',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            
            Ext.getCmp("IframeView_Id").getStore().load();//右侧组织数刷新
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveBackupImportedRoleFile(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/roleManagerController/saveAllImportedRole',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveBackupUserFile(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/userManagerController/saveAllImportedUser',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveProtocolBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/acquisitionUnitManagerController/saveProtocolBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveAcqUnitBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/acquisitionUnitManagerController/saveAcqUnitBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveDisplayUnitBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/acquisitionUnitManagerController/saveDisplayUnitBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveAlarmUnitBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/acquisitionUnitManagerController/saveAlarmUnitBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveReportUnitBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/acquisitionUnitManagerController/saveReportUnitBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveAcqInstanceBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/acquisitionUnitManagerController/saveAcqInstanceBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveDisplayInstanceBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/acquisitionUnitManagerController/saveDisplayInstanceBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveAlarmInstanceBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/acquisitionUnitManagerController/saveAlarmInstanceBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveReportInstanceBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/acquisitionUnitManagerController/saveReportInstanceBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveAuxiliaryDeviceBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/wellInformationManagerController/saveAuxiliaryDeviceBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function savePrimaryDeviceBackupData(){
	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDataImportPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
        url: context + '/wellInformationManagerController/savePrimaryDeviceBackupData',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
            Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceDataImportPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceDataImportPanel_Id").getEl().unmask();
        	}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function importedFilePermissionVerification(code){
	var verification={};
	verification.sign=false;
	verification.info="";
	
	
	if(code.toUpperCase()=='module'.toUpperCase()){
		
	}else if(code.toUpperCase()=='dataDictionary'.toUpperCase()){
		verification.info=loginUserLanguageResource.pleaseImportModuleData;
	}else if(code.toUpperCase()=='organization'.toUpperCase()){
		
	}else if(code.toUpperCase()=='role'.toUpperCase()){
		verification.info=loginUserLanguageResource.pleaseImportModuleData;
	}else if(code.toUpperCase()=='user'.toUpperCase()){
		verification.info=loginUserLanguageResource.pleaseImportOrgAndRoleData;
    }else if(code.toUpperCase()=='auxiliaryDevice'.toUpperCase()){
    	
    }else if(code.toUpperCase()=='primaryDevice'.toUpperCase()){
    	verification.info=loginUserLanguageResource.pleaseImportOrgAndDriverData;
    }else if(code.toUpperCase()=='protocol'.toUpperCase()){
    	
    }else if(code.toUpperCase()=='acqUnit'.toUpperCase()){
    	verification.info=loginUserLanguageResource.pleaseImportProtocolData;
    }else if(code.toUpperCase()=='displayUnit'.toUpperCase()){
    	verification.info=loginUserLanguageResource.pleaseImportAcqUnitData;
    }else if(code.toUpperCase()=='alarmUnit'.toUpperCase()){
    	verification.info=loginUserLanguageResource.pleaseImportProtocolData;
    }else if(code.toUpperCase()=='reportUnit'.toUpperCase()){
    	
    }else if(code.toUpperCase()=='acqInstance'.toUpperCase()){
    	verification.info=loginUserLanguageResource.pleaseImportAcqUnitData;
    }else if(code.toUpperCase()=='displayInstance'.toUpperCase()){
    	verification.info=loginUserLanguageResource.pleaseImportDisplayUnitData;
    }else if(code.toUpperCase()=='alarmInstance'.toUpperCase()){
    	verification.info=loginUserLanguageResource.pleaseImportAlarmUnitData;
    }else if(code.toUpperCase()=='reportInstance'.toUpperCase()){
    	verification.info=loginUserLanguageResource.pleaseImportReportUnitData;
    }
	
	var code=Ext.getCmp("BatchExportModuleDataListSelectCode_Id").getValue();
	Ext.Ajax.request({
        url: context + '/operationMaintenanceController/importedFilePermissionVerification',
        method: "POST",
        async: false,
        params: {
        	code:code
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            verification.sign = result.success&&result.msg
        },
        failure: function () {
        	
        }
    });
	return verification;
}

function getOperationMaintenanceMonitorCurveData(){
	var startDateId="OperationMaintenanceMonitorCurveStartDate_Id";
	var startHourId="OperationMaintenanceMonitorCurveStartTime_Hour_Id";
	var startMinuteId="OperationMaintenanceMonitorCurveStartTime_Minute_Id";
	
	var endDateId="OperationMaintenanceMonitorCurveEndDate_Id";
	var endHourId="OperationMaintenanceMonitorCurveEndTime_Hour_Id";
	var endMinuteId="OperationMaintenanceMonitorCurveEndTime_Minute_Id";
	
	var divId="OperationMaintenanceMonitorCurveDiv_Id";
	var panelId="OperationMaintenanceMonitorCurveTabPanel_Id";
	
	
	var startDate=Ext.getCmp(startDateId).rawValue;
	var startTime_Hour=Ext.getCmp(startHourId).getValue();
	var startTime_Minute=Ext.getCmp(startMinuteId).getValue();
	var startTime_Second=0;
    var endDate=Ext.getCmp(endDateId).rawValue;
    var endTime_Hour=Ext.getCmp(endHourId).getValue();
	var endTime_Minute=Ext.getCmp(endMinuteId).getValue();
	var endTime_Second=0;
	

	if(Ext.getCmp(panelId)!=undefined){
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/operationMaintenanceController/getOperationMaintenanceMonitorCurveData',
		success:function(response) {
			if(Ext.getCmp(panelId)!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			var result =  Ext.JSON.decode(response.responseText);
			
			
			var startDate=Ext.getCmp(startDateId);
		    if(startDate.rawValue==''||null==startDate.rawValue){
		    	startDate.setValue(result.startDate.split(' ')[0]);
		    	Ext.getCmp(startHourId).setValue(result.startDate.split(' ')[1].split(':')[0]);
		    	Ext.getCmp(startMinuteId).setValue(result.startDate.split(' ')[1].split(':')[1]);
		    }
		    var endDate=Ext.getCmp(endDateId);
		    if(endDate.rawValue==''||null==endDate.rawValue){
		    	endDate.setValue(result.endDate.split(' ')[0]);
		    	Ext.getCmp(endHourId).setValue(result.endDate.split(' ')[1].split(':')[0]);
		    	Ext.getCmp(endMinuteId).setValue(result.endDate.split(' ')[1].split(':')[1]);
		    }
			
			
		    var data = result.list;
		    
		    
		    var timeFormat='%m-%d';
		    if(data.length>0 && result.minAcqTime.split(' ')[0]==result.maxAcqTime.split(' ')[0]){
			    timeFormat='%H:%M';
		    }
		    
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 10) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    var title = loginUserLanguageResource.memoryCurve;
		    var xTitle=loginUserLanguageResource.acqTime;
		    var legendName =result.curveItems;
		    var legendCode =result.curveItemCodes;
		    
		    
		    var color=defaultColors;
		    var yAxis=[];
		    var series = [];
		    
		    
		    for (var i = 0; i < legendName.length; i++) {
		        var maxValue=null;
		        var minValue=null;
		        var allPositive=true;//全部是非负数
		        var allNegative=true;//全部是负值
		        
		        var singleSeries={};
		        singleSeries.name=legendName[i];
		        singleSeries.code=legendCode[i];
		        singleSeries.type='spline';
		        singleSeries.lineWidth=3;
		        singleSeries.dashStyle='Solid';
		        singleSeries.marker={enabled: false};
		        singleSeries.yAxis=i;
		        singleSeries.data=[];
		        for (var j = 0; j < data.length; j++) {
		        	var pointData=[];
		        	pointData.push(Date.parse(data[j].acqTime.replace(/-/g, '/')));
		        	pointData.push(data[j].data[i]);
		        	
		        	if(parseFloat(data[j].data[i])<0){
		            	allPositive=false;
		            }else if(parseFloat(data[j].data[i])>=0){
		            	allNegative=false;
		            }
		        	
		        	singleSeries.data.push(pointData);
		        }

		        series.push(singleSeries);
		        var opposite=false;
		        
		        if(allNegative){
		        	maxValue=0;
		        }else if(allPositive){
		        	minValue=0;
		        }
		        
		        var singleAxis={
		        		max:maxValue,
		        		min:minValue,
		        		code:legendCode[i],
		        		title: {
		                    text: legendName[i],
		                    style: {
		                        color: color[i],
		                    }
		                },
		                labels: {
		                	style: {
		                        color: color[i],
		                    }
		                },
		                opposite:opposite
		          };
		        yAxis.push(singleAxis);
		        
		    }
		   
		    initOperationMaintenanceMonitorCurveChartFn(series, tickInterval, divId, title, '', '', yAxis, color,true,timeFormat);
		},
		failure:function(){
			if(Ext.getCmp(panelId)!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
        }
	});
}

function initOperationMaintenanceMonitorCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		Highcharts.setOptions({
	        global: {
	            useUTC: false
	        }
	    });

	    var mychart = new Highcharts.Chart({
	        chart: {
	            renderTo: divId,
	            type: 'spline',
	            shadow: true,
	            borderWidth: 0,
	            zoomType: 'xy'
	        },
	        credits: {
	            enabled: false
	        },
	        title: {
	            text: title
	        },
	        subtitle: {
	            text: subtitle
	        },
	        colors: color,
	        xAxis: {
	            type: 'datetime',
	            title: {
	                text: xtitle
	            },
	            tickPixelInterval:tickInterval,
	            labels: {
	                formatter: function () {
	                    return Highcharts.dateFormat(timeFormat, this.value);
	                },
	                autoRotation:true,//自动旋转
	                rotation: -45 //倾斜度，防止数量过多显示不全  
	            }
	        },
	        yAxis: yAxis,
	        tooltip: {
	            crosshairs: true, //十字准线
	            shared: true,
	            style: {
	                color: '#333333',
	                fontSize: '12px',
	                padding: '8px'
	            },
	            dateTimeLabelFormats: {
	                millisecond: '%Y-%m-%d %H:%M:%S.%L',
	                second: '%Y-%m-%d %H:%M:%S',
	                minute: '%Y-%m-%d %H:%M',
	                hour: '%Y-%m-%d %H',
	                day: '%Y-%m-%d',
	                week: '%m-%d',
	                month: '%Y-%m',
	                year: '%Y'
	            }
	        },
	        plotOptions: {
	            spline: {
	                fillOpacity: 0.3,
	                shadow: true,
	                events: {
	                	legendItemClick: function(e){
	                	}
	                }
	            }
	        },
	        legend: {
	            layout: 'horizontal',//horizontal水平 vertical 垂直
	            align: 'center',  //left，center 和 right
	            verticalAlign: 'bottom',//top，middle 和 bottom
	            enabled: legend,
	            borderWidth: 0
	        },
	        series: series
	    });
	}
};

function initDeviceTabManagerInstance(instanceId){
	if(Ext.getCmp("OperationMaintenanceDeviceTabManangerPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDeviceTabManangerPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/operationMaintenanceController/loadDeviceTabManagerInstance',
		success:function(response) {
			if(Ext.getCmp("OperationMaintenanceDeviceTabManangerPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceDeviceTabManangerPanel_Id").getEl().unmask();
			}
			var data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				var instanceConfig=data.config;
				initDeviceTabManagerInstanceConfig(instanceConfig);
			} else {
				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
			}
		},
		failure:function(){
			if(Ext.getCmp("OperationMaintenanceDeviceTabManangerPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceDeviceTabManangerPanel_Id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			instanceId:instanceId
		}
	});
}

function initDeviceTabManagerInstanceConfig(instanceConfig){
	Ext.getCmp("calculationModel_showRealtimeWellboreAnalysis_Id").setValue(false);
	Ext.getCmp("calculationModel_showRealtimeSurfaceAnalysis_Id").setValue(false);
	Ext.getCmp("calculationModel_showRealtimeTrendCurve_Id").setValue(false);
	Ext.getCmp("calculationModel_showRealtimeDynamicData_Id").setValue(false);
	Ext.getCmp("calculationModel_showRealtimeDeviceControl_Id").setValue(false);
	Ext.getCmp("calculationModel_showRealtimeDeviceInformation_Id").setValue(false);
	Ext.getCmp("calculationModel_rodStressChart_maxRodStress_Id").setValue(false);
	Ext.getCmp("calculationModel_rodStressChart_rodStressRange_Id").setValue(false);
	
	Ext.getCmp("calculationModel_showHistoryTrendCurve_Id").setValue(false);
	Ext.getCmp("calculationModel_showHistoryTiledDiagram_Id").setValue(false);
	Ext.getCmp("calculationModel_showHistoryDiagramOverlay_Id").setValue(false);
	
	Ext.getCmp("calculationModel_showPrimaryDeviceAdditionalInformation_Id").setValue(false);
	Ext.getCmp("calculationModel_showPrimaryDeviceAuxiliaryDevice_Id").setValue(false);
	Ext.getCmp("calculationModel_showPrimaryDeviceVideoConfig_Id").setValue(false);
	Ext.getCmp("calculationModel_showPrimaryDeviceCalculateDataConfig_Id").setValue(false);
	Ext.getCmp("calculationModel_showPrimaryDeviceFSDiagramConstruction_Id").setValue(false);
	Ext.getCmp("calculationModel_showPrimaryDeviceSystemParameterConfiguration_Id").setValue(false);
	
	Ext.getCmp("calculationModel_showPrimaryDeviceIntelligentFrequencyConversion_Id").setValue(false);
	Ext.getCmp("calculationModel_showPrimaryDeviceInterlockProtection_Id").setValue(false);
	
	if(instanceConfig!=undefined){
		if(instanceConfig.DeviceRealTimeMonitoring!=undefined){
			Ext.getCmp("calculationModel_showRealtimeWellboreAnalysis_Id").setValue(instanceConfig.DeviceRealTimeMonitoring.WellboreAnalysis!=undefined?instanceConfig.DeviceRealTimeMonitoring.WellboreAnalysis:false);
			Ext.getCmp("calculationModel_showRealtimeSurfaceAnalysis_Id").setValue(instanceConfig.DeviceRealTimeMonitoring.SurfaceAnalysis!=undefined?instanceConfig.DeviceRealTimeMonitoring.SurfaceAnalysis:false);
			Ext.getCmp("calculationModel_showRealtimeTrendCurve_Id").setValue(instanceConfig.DeviceRealTimeMonitoring.TrendCurve!=undefined?instanceConfig.DeviceRealTimeMonitoring.TrendCurve:false);
			Ext.getCmp("calculationModel_showRealtimeDynamicData_Id").setValue(instanceConfig.DeviceRealTimeMonitoring.DynamicData!=undefined?instanceConfig.DeviceRealTimeMonitoring.DynamicData:false);
			Ext.getCmp("calculationModel_showRealtimeDeviceControl_Id").setValue(instanceConfig.DeviceRealTimeMonitoring.DeviceControl!=undefined?instanceConfig.DeviceRealTimeMonitoring.DeviceControl:false);
			Ext.getCmp("calculationModel_showRealtimeDeviceInformation_Id").setValue(instanceConfig.DeviceRealTimeMonitoring.DeviceInformation!=undefined?instanceConfig.DeviceRealTimeMonitoring.DeviceInformation:false);
			
			Ext.getCmp("calculationModel_rodStressChart_maxRodStress_Id").setValue(instanceConfig.DeviceRealTimeMonitoring.RodStressChart_MaxRodStress!=undefined?instanceConfig.DeviceRealTimeMonitoring.RodStressChart_MaxRodStress:false);
			Ext.getCmp("calculationModel_rodStressChart_rodStressRange_Id").setValue(instanceConfig.DeviceRealTimeMonitoring.RodStressChart_RodStressRange!=undefined?instanceConfig.DeviceRealTimeMonitoring.RodStressChart_RodStressRange:false);
		}
		
		if(instanceConfig.DeviceHistoryQuery!=undefined){
			Ext.getCmp("calculationModel_showHistoryTrendCurve_Id").setValue(instanceConfig.DeviceHistoryQuery.TrendCurve!=undefined?instanceConfig.DeviceHistoryQuery.TrendCurve:false);
			Ext.getCmp("calculationModel_showHistoryTiledDiagram_Id").setValue(instanceConfig.DeviceHistoryQuery.TiledDiagram!=undefined?instanceConfig.DeviceHistoryQuery.TiledDiagram:false);
			Ext.getCmp("calculationModel_showHistoryDiagramOverlay_Id").setValue(instanceConfig.DeviceHistoryQuery.DiagramOverlay!=undefined?instanceConfig.DeviceHistoryQuery.DiagramOverlay:false);
		}
		
		if(instanceConfig.PrimaryDevice!=undefined){
			Ext.getCmp("calculationModel_showPrimaryDeviceAdditionalInformation_Id").setValue(instanceConfig.PrimaryDevice.AdditionalInformation!=undefined?instanceConfig.PrimaryDevice.AdditionalInformation:false);
			Ext.getCmp("calculationModel_showPrimaryDeviceAuxiliaryDevice_Id").setValue(instanceConfig.PrimaryDevice.AuxiliaryDevice!=undefined?instanceConfig.PrimaryDevice.AuxiliaryDevice:false);
			Ext.getCmp("calculationModel_showPrimaryDeviceVideoConfig_Id").setValue(instanceConfig.PrimaryDevice.VideoConfig!=undefined?instanceConfig.PrimaryDevice.VideoConfig:false);
			Ext.getCmp("calculationModel_showPrimaryDeviceCalculateDataConfig_Id").setValue(instanceConfig.PrimaryDevice.CalculateDataConfig!=undefined?instanceConfig.PrimaryDevice.CalculateDataConfig:false);
			Ext.getCmp("calculationModel_showPrimaryDeviceFSDiagramConstruction_Id").setValue(instanceConfig.PrimaryDevice.FSDiagramConstruction!=undefined?instanceConfig.PrimaryDevice.FSDiagramConstruction:false);
			Ext.getCmp("calculationModel_showPrimaryDeviceSystemParameterConfiguration_Id").setValue(instanceConfig.PrimaryDevice.SystemParameterConfig!=undefined?instanceConfig.PrimaryDevice.SystemParameterConfig:false);
			
			Ext.getCmp("calculationModel_showPrimaryDeviceIntelligentFrequencyConversion_Id").setValue(instanceConfig.PrimaryDevice.IntelligentFrequencyConversion!=undefined?instanceConfig.PrimaryDevice.IntelligentFrequencyConversion:false);
			Ext.getCmp("calculationModel_showPrimaryDeviceInterlockProtection_Id").setValue(instanceConfig.PrimaryDevice.InterlockProtection!=undefined?instanceConfig.PrimaryDevice.InterlockProtection:false);
		}
	}
}

function initDeviceTypeContentConfig(deviceTypeId,deviceTypeName){
	if(Ext.getCmp("OperationMaintenanceDeviceTypeMaintenanceListPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceDeviceTypeMaintenanceListPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/operationMaintenanceController/loadDeviceTypeContentConfig',
		success:function(response) {
			if(Ext.getCmp("OperationMaintenanceDeviceTypeMaintenanceListPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceDeviceTypeMaintenanceListPanel_Id").getEl().unmask();
			}
			var data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				var deviceTypeContentConfig=data.config;
				updateDeviceTypeContentConfig(deviceTypeContentConfig);
			} else {
				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
			}
		},
		failure:function(){
			if(Ext.getCmp("OperationMaintenanceDeviceTypeMaintenanceListPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceDeviceTypeMaintenanceListPanel_Id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			deviceTypeId:deviceTypeId
		}
	});
}

function updateDeviceTypeContentConfig(deviceTypeContentConfig){
	Ext.getCmp("calculationModel_showRealtimeFESDiagramStatPie_Id").setValue(false);
	Ext.getCmp("calculationModel_showRealtimeCommStatusStatPie_Id").setValue(false);
	Ext.getCmp("calculationModel_showRealtimeRunStatusStatPie_Id").setValue(false);
	Ext.getCmp("calculationModel_showRealtimeNumStatusStatPie_Id").setValue(false);
	
	Ext.getCmp("calculationModel_showHistoryFESDiagramStatPie_Id").setValue(false);
	Ext.getCmp("calculationModel_showHistoryCommStatusStatPie_Id").setValue(false);
	Ext.getCmp("calculationModel_showHistoryRunStatusStatPie_Id").setValue(false);
	Ext.getCmp("calculationModel_showHistoryNumStatusStatPie_Id").setValue(false);
	
	Ext.getCmp("calculationModel_showAlarmQueryFESDiagramResultAlarm_Id").setValue(false);
	Ext.getCmp("calculationModel_showAlarmQueryRunStatusAlarm_Id").setValue(false);
	Ext.getCmp("calculationModel_showAlarmQueryCommStatusAlarm_Id").setValue(false);
	Ext.getCmp("calculationModel_showAlarmQueryNumericValueAlarm_Id").setValue(false);
	Ext.getCmp("calculationModel_showAlarmQueryEnumValueAlarm_Id").setValue(false);
	Ext.getCmp("calculationModel_showAlarmQuerySwitchingValueAlarm_Id").setValue(false);
	
	if(deviceTypeContentConfig!=undefined){
		if(deviceTypeContentConfig.DeviceRealTimeMonitoring!=undefined){
			Ext.getCmp("calculationModel_showRealtimeFESDiagramStatPie_Id").setValue(deviceTypeContentConfig.DeviceRealTimeMonitoring.FESDiagramStatPie!=undefined?deviceTypeContentConfig.DeviceRealTimeMonitoring.FESDiagramStatPie:false);
			Ext.getCmp("calculationModel_showRealtimeCommStatusStatPie_Id").setValue(deviceTypeContentConfig.DeviceRealTimeMonitoring.CommStatusStatPie!=undefined?deviceTypeContentConfig.DeviceRealTimeMonitoring.CommStatusStatPie:false);
			Ext.getCmp("calculationModel_showRealtimeRunStatusStatPie_Id").setValue(deviceTypeContentConfig.DeviceRealTimeMonitoring.RunStatusStatPie!=undefined?deviceTypeContentConfig.DeviceRealTimeMonitoring.RunStatusStatPie:false);
			Ext.getCmp("calculationModel_showRealtimeNumStatusStatPie_Id").setValue(deviceTypeContentConfig.DeviceRealTimeMonitoring.NumStatusStatPie!=undefined?deviceTypeContentConfig.DeviceRealTimeMonitoring.NumStatusStatPie:false);
		}
		
		if(deviceTypeContentConfig.DeviceHistoryQuery!=undefined){
			Ext.getCmp("calculationModel_showHistoryFESDiagramStatPie_Id").setValue(deviceTypeContentConfig.DeviceHistoryQuery.FESDiagramStatPie!=undefined?deviceTypeContentConfig.DeviceHistoryQuery.FESDiagramStatPie:false);
			Ext.getCmp("calculationModel_showHistoryCommStatusStatPie_Id").setValue(deviceTypeContentConfig.DeviceHistoryQuery.CommStatusStatPie!=undefined?deviceTypeContentConfig.DeviceHistoryQuery.CommStatusStatPie:false);
			Ext.getCmp("calculationModel_showHistoryRunStatusStatPie_Id").setValue(deviceTypeContentConfig.DeviceHistoryQuery.RunStatusStatPie!=undefined?deviceTypeContentConfig.DeviceHistoryQuery.RunStatusStatPie:false);
			Ext.getCmp("calculationModel_showHistoryNumStatusStatPie_Id").setValue(deviceTypeContentConfig.DeviceHistoryQuery.NumStatusStatPie!=undefined?deviceTypeContentConfig.DeviceHistoryQuery.NumStatusStatPie:false);
		}
		
		if(deviceTypeContentConfig.AlarmQuery!=undefined){
			Ext.getCmp("calculationModel_showAlarmQueryFESDiagramResultAlarm_Id").setValue(deviceTypeContentConfig.AlarmQuery.FESDiagramResultAlarm!=undefined?deviceTypeContentConfig.AlarmQuery.FESDiagramResultAlarm:false);
			Ext.getCmp("calculationModel_showAlarmQueryRunStatusAlarm_Id").setValue(deviceTypeContentConfig.AlarmQuery.RunStatusAlarm!=undefined?deviceTypeContentConfig.AlarmQuery.RunStatusAlarm:false);
			Ext.getCmp("calculationModel_showAlarmQueryCommStatusAlarm_Id").setValue(deviceTypeContentConfig.AlarmQuery.CommStatusAlarm!=undefined?deviceTypeContentConfig.AlarmQuery.CommStatusAlarm:false);
			Ext.getCmp("calculationModel_showAlarmQueryNumericValueAlarm_Id").setValue(deviceTypeContentConfig.AlarmQuery.NumericValueAlarm!=undefined?deviceTypeContentConfig.AlarmQuery.NumericValueAlarm:false);
			Ext.getCmp("calculationModel_showAlarmQueryEnumValueAlarm_Id").setValue(deviceTypeContentConfig.AlarmQuery.EnumValueAlarm!=undefined?deviceTypeContentConfig.AlarmQuery.EnumValueAlarm:false);
			Ext.getCmp("calculationModel_showAlarmQuerySwitchingValueAlarm_Id").setValue(deviceTypeContentConfig.AlarmQuery.SwitchingValueAlarm!=undefined?deviceTypeContentConfig.AlarmQuery.SwitchingValueAlarm:false);
		}
	}
}

function lowerComputerProgramVersionDataBatchUplink(deviceIdList,name){
	if(deviceIdList.length>0){
		if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
			Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").el.mask(loginUserLanguageResource.commandSending+'...').show();
		}
		Ext.Ajax.request({
	        url: context + '/wellInformationManagerController/lowerComputerProgramVersionDataBatchUplink',
	        method: "POST",
	        params: {
	        	deviceIds: deviceIdList.join(","),
	        	name:name
	        },
	        success: function (response, action) {
	        	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
	            	Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
	    		}
	        	var result =  Ext.JSON.decode(response.responseText);
	        	
	        	if (result.flag == false) {
	                Ext.MessageBox.show({
	                    title: loginUserLanguageResource.tip,
	                    msg: "<font color=red>" + loginUserLanguageResource.sessionInvalid + "。</font>",
	                    icon: Ext.MessageBox.INFO,
	                    buttons: Ext.Msg.OK,
	                    fn: function () {
	                        window.location.href = context + "/login";
	                    }
	                });
	            } else if (result.flag == true && result.error == false) {
	                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + result.msg + "</font>");
	            }  else if (result.flag == true && result.error == true) {
	            	var deviceIdArr=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtProp('deviceId');
                	for(var i=0;i<deviceIdArr.length;i++){
                		for(var j=0;j<result.uplinkData.length;j++){
                			if(result.uplinkData[j].deviceId==deviceIdArr[i]){
                				lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(i,'lowerComputerDeviceId',result.uplinkData[j].lowerComputerDeviceId);
                				if(name=='box'){
            	            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(i,'boxVersion',result.uplinkData[j].boxVersion);
            	            	}else if(name=='ac'){
            	            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(i,'acVersion',result.uplinkData[j].acVersion);
            	            	}else if(name==''){
            	            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(i,'boxVersion',result.uplinkData[j].boxVersion);
            	            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(i,'acVersion',result.uplinkData[j].acVersion);
            	            	}
                				break;
                			}
                		}
                	}
	            	
	            	
	            	const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
	            	if(name=='box'){
	            		plugin.showColumns([4]);
	            	}else if(name=='ac'){
	            		plugin.showColumns([9]);
	            	}else if(name==''){
	            		plugin.showColumns([4,9]);
	            	}
	            	lowerComputerProgramUpgradeHandsontableHelper.hot.render();
	            } 
	        },
	        failure: function () {
	        	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
	            	Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
	    		}
	            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
	        }
	    });
	}else{
		Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.checkOne);
	}
	
}

function lowerComputerProgramVersionDataUplink(row,name){
	var deviceId=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtRowProp(row,'deviceId');
	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").el.mask(loginUserLanguageResource.commandSending+'...').show();
	}
	
	Ext.Ajax.request({
        url: context + '/wellInformationManagerController/lowerComputerProgramVersionDataUplink',
        method: "POST",
        params: {
        	deviceId: deviceId,
        	name:name
        },
        success: function (response, action) {
        	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
    		}
        	var result =  Ext.JSON.decode(response.responseText);
        	
        	if (result.flag == false) {
                Ext.MessageBox.show({
                    title: loginUserLanguageResource.tip,
                    msg: "<font color=red>" + loginUserLanguageResource.sessionInvalid + "。</font>",
                    icon: Ext.MessageBox.INFO,
                    buttons: Ext.Msg.OK,
                    fn: function () {
                        window.location.href = context + "/login";
                    }
                });
            } else if (result.flag == true && result.error == false) {
                lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'lowerComputerDeviceId',result.msg);
            	const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
            	if(name=='box'){
            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxVersion',result.msg);
            		plugin.showColumns([4]);
            	}else if(name=='ac'){
            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acVersion',result.msg);
            		plugin.showColumns([9]);
            	}else if(name==''){
            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxVersion',result.msg);
            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acVersion',result.msg);
            		plugin.showColumns([4,9]);
            	}
            	
            	lowerComputerProgramUpgradeHandsontableHelper.hot.render();
                
            }  else if (result.flag == true && result.error == true) {
            	lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'lowerComputerDeviceId',result.lowerComputerDeviceId);
            	const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
            	if(name=='box'){
            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxVersion',result.boxVersion);
            		plugin.showColumns([4]);
            	}else if(name=='ac'){
            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acVersion',result.acVersion);
            		plugin.showColumns([9]);
            	}else if(name==''){
            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxVersion',result.boxVersion);
            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acVersion',result.acVersion);
            		plugin.showColumns([4,9]);
            	}
            	
            	lowerComputerProgramUpgradeHandsontableHelper.hot.render();
            } 
        },
        failure: function () {
        	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
            	Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
    		}
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
        }
    });
}

function lowerComputerProgramBatchUpgrade(selectList,name) {
    var i = 0;
    function next() {
        if (i >= selectList.length) {
            console.log("所有升级任务执行完毕");
            return;
        }
        lowerComputerProgramUpgrade(selectList[i],name)
            .then(function(result) {
                console.log("第 " + (i+1) + " 次升级完成，结果：", result);
                i++;
                next();
            })
            .catch(function(error) {
                console.error("第 " + (i+1) + " 次升级失败：", error);
                // 根据需求决定是否继续
                i++;
                next();  // 继续下一次；若想中断则改为 return
            });
    }
    next();
}


function lowerComputerProgramUpgrade(row, name) {
    return new Promise((resolve, reject) => {
        // 原有遮罩显示逻辑...
    	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
    		Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").el.mask(loginUserLanguageResource.downlinking+'...').show();
    	}
    	var deviceId=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtRowProp(row,'deviceId');
    	const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
    	if(name=='box'){
    		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxDownlinkStatus',loginUserLanguageResource.downlinking+'...');
    		plugin.showColumns([7]);
    	}else if(name=='ac'){
    		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acDownlinkStatus',loginUserLanguageResource.downlinking+'...');
    		plugin.showColumns([12]);
    	}
    	lowerComputerProgramUpgradeHandsontableHelper.hot.render();
    	Ext.Ajax.request({
            url: context + '/wellInformationManagerController/lowerComputerProgramUpgrade',
            method: "POST",
            timeout: 10*60*1000, //超时时间 10分钟
            params: {
            	deviceId: deviceId,
            	name:name
            },
            success: function (response, action) {
            	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
                	Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
        		}
            	var result =  Ext.JSON.decode(response.responseText);
            	
            	if (result.flag == false) {
                    const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
                	if(name=='box'){
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxDownlinkStatus',loginUserLanguageResource.sessionInvalid);
                	}else if(name=='ac'){
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acDownlinkStatus',loginUserLanguageResource.sessionInvalid);
                	}
                	reject(new Error(loginUserLanguageResource.sessionInvalid));  // 让批处理进入 catch，不再继续
                    return;
                } else if (result.flag == true && result.error == false) {
                    const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
                	if(name=='box'){
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxDownlinkStatus',result.msg);
                	}else if(name=='ac'){
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acDownlinkStatus',result.msg);
                	}
                	lowerComputerProgramUpgradeHandsontableHelper.hot.render();
                    
                }  else if (result.flag == true && result.error == true) {
                	const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
                	if(name=='box'){
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxDownlinkStatus',result.msg);
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxUpdateStatus',result.msg);
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxUpdateTime',result.updateTime);
                	}else if(name=='ac'){
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acDownlinkStatus',result.msg);
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acUpdateStatus',result.msg);
                		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acUpdateTime',result.updateTime);
                	}
                } 
            	resolve(result);
            },
            failure: function () {
            	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
                	Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
        		}
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin);
                reject(new Error("请求失败"));
            }
        });
    });
}

//function lowerComputerProgramUpgrade(row,name){
//	var deviceId=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtRowProp(row,'deviceId');
//	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
//		Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").el.mask(loginUserLanguageResource.commandSending+'...').show();
//	}
//	
//	const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
//	if(name=='box'){
//		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxDownlinkStatus',loginUserLanguageResource.downlinking);
//		plugin.showColumns([7]);
//	}else if(name=='ac'){
//		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acDownlinkStatus',loginUserLanguageResource.downlinking);
//		plugin.showColumns([12]);
//	}
//	lowerComputerProgramUpgradeHandsontableHelper.hot.render();
//	
//	Ext.Ajax.request({
//        url: context + '/wellInformationManagerController/lowerComputerProgramUpgrade',
//        method: "POST",
//        timeout: 10*60*1000, //超时时间 10分钟
//        params: {
//        	deviceId: deviceId,
//        	name:name
//        },
//        success: function (response, action) {
//        	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
//            	Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
//    		}
//        	var result =  Ext.JSON.decode(response.responseText);
//        	
//        	if (result.flag == false) {
//                Ext.MessageBox.show({
//                    title: loginUserLanguageResource.tip,
//                    msg: "<font color=red>" + loginUserLanguageResource.sessionInvalid + "。</font>",
//                    icon: Ext.MessageBox.INFO,
//                    buttons: Ext.Msg.OK,
//                    fn: function () {
//                        window.location.href = context + "/login";
//                    }
//                });
//            } else if (result.flag == true && result.error == false) {
//                const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
//            	if(name=='box'){
//            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxDownlinkStatus',result.msg);
//            		plugin.showColumns([7]);
//            	}else if(name=='ac'){
//            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acDownlinkStatus',result.msg);
//            		plugin.showColumns([12]);
//            	}
//            	lowerComputerProgramUpgradeHandsontableHelper.hot.render();
//                
//            }  else if (result.flag == true && result.error == true) {
//            	const plugin = lowerComputerProgramUpgradeHandsontableHelper.hot.getPlugin('hiddenColumns');
//            	if(name=='box'){
//            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxDownlinkStatus',result.msg);
//            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxUpdateStatus',result.updateStatus);
//            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'boxUpdateTime',result.updateTime);
//            		
//            		plugin.showColumns([7]);
//            	}else if(name=='ac'){
//            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acDownlinkStatus',result.msg);
//            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acUpdateStatus',result.updateStatus);
//            		lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(row,'acUpdateTime',result.updateTime);
//            		plugin.showColumns([12]);
//            	}
//            	
//            	lowerComputerProgramUpgradeHandsontableHelper.hot.render();
//            } 
//        },
//        failure: function () {
//        	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
//            	Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
//    		}
//            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
//        }
//    });
//}

//通用按钮渲染器工厂
function createLowerComputerProgramUpgradeButtonRenderer(buttonText, clickHandler, bgColor, hoverColor = null) {
    if (!hoverColor) {
        // 简单提亮颜色（或者手动传参）
        hoverColor = bgColor === '#409eff' ? '#66b1ff' :
                     bgColor === '#67c23a' ? '#85ce61' :
                     bgColor === '#e6a23c' ? '#ebb563' : '#909399';
    }
    return function(instance, td, row, col, prop, value, cellProperties) {
        td.innerHTML = '';
        const container = document.createElement('div');
        container.style.display = 'flex';
        container.style.justifyContent = 'center';
        container.style.gap = '8px';
        
        const btn = document.createElement('button');
        btn.textContent = buttonText;
        
        // 公共样式
        btn.style.padding = '2px 14px';
        btn.style.fontSize = '12px';
        btn.style.fontWeight = '500';
        btn.style.border = 'none';
        btn.style.borderRadius = '20px';
        btn.style.cursor = 'pointer';
        btn.style.backgroundColor = bgColor;
        btn.style.color = 'white';
        btn.style.transition = 'all 0.2s';
        btn.style.boxShadow = '0 1px 2px rgba(0,0,0,0.1)';
        
        // 悬停效果
        btn.addEventListener('mouseenter', () => {
            btn.style.backgroundColor = hoverColor;
            btn.style.transform = 'translateY(-1px)';
        });
        btn.addEventListener('mouseleave', () => {
            btn.style.backgroundColor = bgColor;
            btn.style.transform = 'translateY(0)';
        });
        
        btn.onclick = (e) => {
            e.stopPropagation();
            clickHandler(instance, td, row, col, prop, value, cellProperties);
        };
        
        container.appendChild(btn);
        td.appendChild(container);
        return td;
    };
}

function loadLowerComputerProgramUpgradeDeviceList(){
	if(lowerComputerProgramUpgradeHandsontableHelper!=null){
		if(lowerComputerProgramUpgradeHandsontableHelper.hot!=undefined){
			lowerComputerProgramUpgradeHandsontableHelper.hot.destroy();
		}
		lowerComputerProgramUpgradeHandsontableHelper=null;
	}
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
    if(isNotVal(Ext.getCmp('lowerComputerProgramUpgradeDeviceListComb_Id'))){
    	deviceName = Ext.getCmp('lowerComputerProgramUpgradeDeviceListComb_Id').getValue();
    }
	if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
		Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/operationMaintenanceController/loadLowerComputerProgramUpgradeDeviceList',
		success:function(response) {
			if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
	    	}
			var result =  Ext.JSON.decode(response.responseText);
			
			if(lowerComputerProgramUpgradeHandsontableHelper==null || lowerComputerProgramUpgradeHandsontableHelper.hot==undefined){
				lowerComputerProgramUpgradeHandsontableHelper = LowerComputerProgramUpgradeHandsontableHelper.createNew("OperationMaintenanceLowerComputerProgramUpgradeDiv_Id");
				var colHeaders=[
					['','','','',{label: '采控程序', colspan: 5},{label:'计算程序', colspan: 5},'',''],
					['',loginUserLanguageResource.idx,loginUserLanguageResource.deviceName,
					loginUserLanguageResource.uplink,
					'边缘版本','更新状态','更新时间',loginUserLanguageResource.downlinkStatus,loginUserLanguageResource.downlink,
					'边缘版本','更新状态','更新时间',loginUserLanguageResource.downlinkStatus,loginUserLanguageResource.downlink,
					'lowerComputerDeviceId',
					'deviceId']
				];
				
				var columns=[{data:'checked',type:'checkbox'},
		        	{data:'id'}, 
					{data:'deviceName'}, 
					{
                        data: 'uplink',
                        renderer: createLowerComputerProgramUpgradeButtonRenderer(
                                '版本号上行',
                                (instance, td, row, col, prop, value, cellProperties) => 
                                    lowerComputerProgramVersionDataUplink(row, ''),
                                '#409eff'  // 蓝色
                            ),
                        readOnly: true
                    },
                    {data:'boxVersion'},
					{data:'boxUpdateStatus'}, 
					{data:'boxUpdateTime'}, 
					{data:'boxDownlinkStatus'},
					{
                        data: 'uplink',
                        renderer: createLowerComputerProgramUpgradeButtonRenderer(
                                '程序下行',
                                (instance, td, row, col, prop, value, cellProperties) => 
                                    lowerComputerProgramUpgrade(row,'box'),
                                '#67c23a'  // 绿色
                            ),
                        readOnly: true
                    },

					{data:'acVersion'},
                    {data:'acUpdateStatus'}, 
					{data:'acUpdateTime'}, 
					{data:'acDownlinkStatus'},
					{
                        data: 'uplink',
                        renderer: createLowerComputerProgramUpgradeButtonRenderer(
                                '程序下行',
                                (instance, td, row, col, prop, value, cellProperties) => 
                                    lowerComputerProgramUpgrade(row,'ac'),
                                '#e6a23c'  // 橙色
                            ),
                        readOnly: true
                    },
					{data:'lowerComputerDeviceId'},
					{data:'deviceId'}
				];
				
				
				
				lowerComputerProgramUpgradeHandsontableHelper.colHeaders=colHeaders;
				lowerComputerProgramUpgradeHandsontableHelper.columns=columns;
				if(result.totalRoot.length==0){
                	lowerComputerProgramUpgradeHandsontableHelper.hiddenRows = [0];
                	lowerComputerProgramUpgradeHandsontableHelper.createTable([{}]);
                }else{
                	lowerComputerProgramUpgradeHandsontableHelper.hiddenRows = [];
                	lowerComputerProgramUpgradeHandsontableHelper.createTable(result.totalRoot);
                }
			}else{
				if(result.totalRoot.length==0){
                	lowerComputerProgramUpgradeHandsontableHelper.hiddenRows = [0];
                	lowerComputerProgramUpgradeHandsontableHelper.createTable([{}]);
                }else{
                	lowerComputerProgramUpgradeHandsontableHelper.hiddenRows = [];
                	lowerComputerProgramUpgradeHandsontableHelper.createTable(result.totalRoot);
                }
			}
		},
		failure:function(){
			if(Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id")!=undefined){
				Ext.getCmp("OperationMaintenanceLowerComputerProgramUpgradeTabPanel_Id").getEl().unmask();
	    	}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId: leftOrg_Id,
			deviceName: deviceName
        }
	});

}

var LowerComputerProgramUpgradeHandsontableHelper = {
	    createNew: function (divid) {
	        var lowerComputerProgramUpgradeHandsontableHelper = {};
	        lowerComputerProgramUpgradeHandsontableHelper.hot = '';
	        lowerComputerProgramUpgradeHandsontableHelper.divid = divid;
	        lowerComputerProgramUpgradeHandsontableHelper.colHeaders = [];
	        lowerComputerProgramUpgradeHandsontableHelper.columns = [];
	        
	        lowerComputerProgramUpgradeHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        lowerComputerProgramUpgradeHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        lowerComputerProgramUpgradeHandsontableHelper.addDownlinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(value==loginUserLanguageResource.downlinking+'...'){
	        		td.style.backgroundColor = "#13f500";
	        	}else if(value==loginUserLanguageResource.waitingForDownlink){
	        		td.style.backgroundColor = "#f09614";
	        	}
	        	td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        lowerComputerProgramUpgradeHandsontableHelper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(isNotVal(lowerComputerProgramUpgradeHandsontableHelper.hot)){
	            	var itemValue=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtRowProp(row,'itemValue');
		            if(isNotVal(value)){
		            	if (value === loginUserLanguageResource.uplinkFailed) {
		            	    td.style.backgroundColor = 'rgb(245, 245, 245)';
		            	}else if (value === loginUserLanguageResource.noUplink) {
		            	    td.style.backgroundColor = 'rgb(245, 245, 245)';
		            	}else{
		            		if(isNumber(itemValue) && isNumber(value)){
		            			if(parseFloat(itemValue)==parseFloat(value)){
		            				td.style.backgroundColor = 'rgb(245, 245, 245)';
		            			}else{
		            				td.style.backgroundColor = "#f09614";
		            			}
		            		}else{
		            			if(itemValue==value){
		            				td.style.backgroundColor = 'rgb(245, 245, 245)';
		            			}else{
		            				td.style.backgroundColor = "#f09614";
		            			}
		            		}
		            	}
		            }else{
		            	td.innerHTML='';
		            	td.style.backgroundColor = 'rgb(245, 245, 245)';
		            }
	            }else{
	            	td.style.backgroundColor = 'rgb(245, 245, 245)';
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        lowerComputerProgramUpgradeHandsontableHelper.createTable = function (data) {
	            $('#' + lowerComputerProgramUpgradeHandsontableHelper.divid).empty();
	            var hotElement = document.querySelector('#' + lowerComputerProgramUpgradeHandsontableHelper.divid);
	            lowerComputerProgramUpgradeHandsontableHelper.hot = new Handsontable(hotElement, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: data,
	                hiddenColumns: {
	                    columns: [4,7,9,12,14,15],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                hiddenRows: {
	                    rows: [],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	            	colWidths: [2,3,10,5,10,10,10,10,5,10,10,10,10,5,10,10],
	                columns: lowerComputerProgramUpgradeHandsontableHelper.columns,
	                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false, //显示行头
	                nestedHeaders: lowerComputerProgramUpgradeHandsontableHelper.colHeaders, //显示列头
	                columnHeaderHeight: 28,
	                columnSorting: true, //允许排序
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                contextMenu: {
	                    items: {
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut
	                        }
	                    }
	                }, 
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if(loginUserOperationMaintenanceModuleRight.editFlag==1){
	                    	if(prop!='checked'){
	                    		cellProperties.readOnly = true;
	                    	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    }
	                    
	                    if(prop=='boxDownlinkStatus' || prop=='acDownlinkStatus'){
	                    	cellProperties.renderer = lowerComputerProgramUpgradeHandsontableHelper.addDownlinkStatusCellStyle;
	                    }   
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && lowerComputerProgramUpgradeHandsontableHelper!=null&&lowerComputerProgramUpgradeHandsontableHelper.hot!=''&&lowerComputerProgramUpgradeHandsontableHelper.hot!=undefined && lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=lowerComputerProgramUpgradeHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return lowerComputerProgramUpgradeHandsontableHelper;
	    }
	};

function lowerComputerProgramUpgradeSelectAll(selected) {
	if(lowerComputerProgramUpgradeHandsontableHelper!=undefined && lowerComputerProgramUpgradeHandsontableHelper.hot!=undefined){
		var rowCount = lowerComputerProgramUpgradeHandsontableHelper.hot.countRows();
    	var updateData=[];
        for(var i=0;i<rowCount;i++){
        	var data=[i,'checked',selected];
        	updateData.push(data);
        }
        lowerComputerProgramUpgradeHandsontableHelper.hot.setDataAtRowProp(updateData);
	}
}