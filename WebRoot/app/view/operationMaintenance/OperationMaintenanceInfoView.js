var loginUserOperationMaintenanceModuleRight=getRoleModuleRight('OperationMaintenance');
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
                    fieldLabel: '登录界面语言',
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
        
        Ext.apply(me, {
        	items:[{
        		xtype: 'tabpanel',
        		id:"OperationMaintenanceTabPanel_Id",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'top',
        		items:[{
        			title: '基础信息',
        			id:'OperationMaintenanceBasicInfoPanel_Id',
    				iconCls: 'check3',
    				layout: 'border',
    				bodyStyle: 'background-color:#ffffff;',
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
    				            	title: '基础信息',
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
    				                            fieldLabel : '时率单位',
    				                            defaultType: 'radiofield',
    				                            anchor: '100%',
    				                            defaults: {
    				                                flex: 1
    				                            },
    				                            layout: 'hbox',
    				                            items: [
    				                                {
    				                                    boxLabel:'小数',
    				                                    name:'operationMaintenance.timeEfficiencyUnit',
    				                                    inputValue: '1',
    				                                    id: 'operationMaintenance_timeEfficiencyUnit1_Id'
    				                                }, {
    				                                    boxLabel: '百分数',
    				                                    name:'operationMaintenance.timeEfficiencyUnit',
    				                                    checked:true,
    				                                    inputValue:'2',
    				                                    id:'operationMaintenance_timeEfficiencyUnit2_Id'
    				                                }
    				                            ]
    				                        },
    				                        {
    				                        	xtype: 'fieldcontainer',
    				                            fieldLabel : '是否发送模拟数据',
    				                            defaultType: 'radiofield',
    				                            anchor: '100%',
    				                            defaults: {
    				                                flex: 1
    				                            },
    				                            layout: 'hbox',
    				                            items: [
    				                                {
    				                                    boxLabel:loginUserLanguageResource.yes,
    				                                    name:'operationMaintenance.simulateAcqEnable',
    				                                    inputValue: '1',
    				                                    id: 'operationMaintenance_simulateAcqEnable1_Id',
    				                                    listeners: {
    	    						                    	change ( field, newValue, oldValue, eOpts) {
    	    						                    		if(newValue){
    	    						                    			Ext.getCmp('operationMaintenance_sendCycle_Id').enable();
    	    						                    		}else{
    	    						                    			Ext.getCmp('operationMaintenance_sendCycle_Id').disable();
    	    						                    		}
    	    						            			}
    	    						                    }
    				                                }, {
    				                                    boxLabel: loginUserLanguageResource.no,
    				                                    name:'operationMaintenance.simulateAcqEnable',
    				                                    checked:true,
    				                                    inputValue:'0',
    				                                    id:'operationMaintenance_simulateAcqEnable0_Id'
    				                                }
    				                            ]
    				                        }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
    				                        	xtype: 'fieldcontainer',
    				                            fieldLabel : '是否显示图标',
    				                            defaultType: 'radiofield',
    				                            anchor: '100%',
    				                            defaults: {
    				                                flex: 1
    				                            },
    				                            layout: 'hbox',
    				                            items: [
    				                                {
    				                                    boxLabel:loginUserLanguageResource.yes,
    				                                    name:'operationMaintenance.showLogo',
    				                                    inputValue: '1',
    				                                    id: 'operationMaintenance_showLogo1_Id'
    				                                }, {
    				                                    boxLabel: loginUserLanguageResource.no,
    				                                    name:'operationMaintenance.showLogo',
    				                                    checked:true,
    				                                    inputValue:'0',
    				                                    id:'operationMaintenance_showLogo0_Id'
    				                                }
    				                            ]
    				                        },
    				                        { 
    				                        	fieldLabel: '资源监测保存记录数' ,
    				                        	xtype: 'numberfield',
    				                        	name:'operationMaintenance.resourceMonitoringSaveData',
    				                        	id:'operationMaintenance_resourceMonitoringSaveData_Id',
    				                        	minValue: 1
    				                        },
    				                        {
    				                        	fieldLabel: '模拟数据发送周期(秒)',
    				                        	xtype: 'numberfield',
    				                        	name:'operationMaintenance.sendCycle',
    				                        	id:'operationMaintenance_sendCycle_Id',
    				                        	minValue: 1
    				                        }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
    				                        	xtype: 'fieldcontainer',
    				                            fieldLabel : '是否打印日志',
    				                            defaultType: 'radiofield',
    				                            anchor: '100%',
    				                            defaults: {
    				                                flex: 1
    				                            },
    				                            layout: 'hbox',
    				                            items: [
    				                                {
    				                                    boxLabel:loginUserLanguageResource.yes,
    				                                    name:'operationMaintenance.printLog',
    				                                    inputValue: '1',
    				                                    id: 'operationMaintenance_printLog1_Id'
    				                                }, {
    				                                    boxLabel: loginUserLanguageResource.no,
    				                                    name:'operationMaintenance.printLog',
    				                                    checked:true,
    				                                    inputValue:'0',
    				                                    id:'operationMaintenance_printLog0_Id'
    				                                }
    				                            ]
    				                        },
    				                        {
    				                        	fieldLabel: '导出excel数据上限',
    				                        	xtype: 'numberfield',
    				                        	name:'operationMaintenance.exportLimit',
    				                        	id:'operationMaintenance_exportLimit_Id',
    				                        	minValue: 1,
    				                        	maxValue: 65534
    				                        }
    				                    ]
    				                }]
    				            },
    				        	{
    				            	xtype: 'fieldset',
    				            	title: '历史数据维护',
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
    				                        	fieldLabel: '执行周期(天)',
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
    						                    			Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').disable();
    						                    			
    						                    			Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').disable();
    						                    			Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').disable();
    						                    			Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').disable();
    						                    			Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').disable();
    						                    			Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').disable();
    						                    			Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').disable();
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
    						                    			Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').enable();
    						                    			
    						                    			Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').enable();
    						                    			Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').enable();
    						                    			Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').enable();
    						                    			Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').enable();
    						                    			Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').enable();
    						                    			Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').enable();
    						                    			Ext.getCmp('operationMaintenance_acqdata_vacuate_retentionTim_id').enable();
    						                    		}
    						            			}
    						                    }
    						                },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: '历史数据表',
    					                        name: 'operationMaintenance.acqdata_hist_enabled',
    					                        id:'operationMaintenance_acqdata_hist_enabled_Id',
    					                        boxLabel: '启用'
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: '原始数据表',
    					                        name: 'operationMaintenance.acqrawdata_enabled',
    					                        id: 'operationMaintenance_acqrawdata_enabled_id',
    					                        boxLabel: '启用'
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: '报警数据表',
    					                        name: 'operationMaintenance.alarminfo_hist_enabled',
    					                        id: 'operationMaintenance_alarminfo_hist_enabled_id',
    					                        boxLabel: '启用'
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: '日累计计算表',
    					                        name: 'operationMaintenance.dailytotalcalculate_hist_enabled',
    					                        id: 'operationMaintenance_dailytotalcalculate_hist_enabled_id',
    					                        boxLabel: '启用'
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: '全天汇总表',
    					                        name: 'operationMaintenance.dailycalculationdata_enabled',
    					                        id: 'operationMaintenance_dailycalculationdata_enabled_id',
    					                        boxLabel: '启用'
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: '定时汇总表',
    					                        name: 'operationMaintenance.timingcalculationdata_enabled',
    					                        id: 'operationMaintenance_timingcalculationdata_enabled_id',
    					                        boxLabel: '启用'
    					                    },
    						                {
    					                    	xtype: 'checkboxfield',
    					                        fieldLabel: '数据抽稀表',
    					                        name: 'operationMaintenance.acqdata_vacuate_enabled',
    					                        id: 'operationMaintenance_acqdata_vacuate_enabled_id',
    					                        boxLabel: '启用'
    					                    }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
    				                        	fieldLabel: '执行时间',
    				                        	name:'operationMaintenance.databaseMaintenanceStartTime',
    				                        	id:'operationMaintenance_databaseMaintenanceStartTime_Id',
    						                    allowBlank: false
    						                },
    				                        { 
    						                	fieldLabel: '数据保留时间(天)',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.acqdata_hist_retentionTime',
    					                        id:'operationMaintenance_acqdata_hist_retentionTime_Id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: '数据保留时间(天)',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.acqrawdata_retentionTime',
    					                        id: 'operationMaintenance_acqrawdata_retentionTime_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: '数据保留时间(天)',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.alarminfo_hist_retentionTime',
    					                        id: 'operationMaintenance_alarminfo_hist_retentionTime_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: '数据保留时间(天)',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.dailytotalcalculate_hist_retentionTime',
    					                        id: 'operationMaintenance_dailytotalcalculate_hist_retentionTime_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: '数据保留时间(天)',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.dailycalculationdata_retentionTime',
    					                        id: 'operationMaintenance_dailycalculationdata_retentionTime_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: '数据保留时间(天)',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.timingcalculationdata_retentionTim',
    					                        id: 'operationMaintenance_timingcalculationdata_retentionTim_id',
    				                        	minValue: 1
    						                },
    				                        { 
    						                	fieldLabel: '数据保留时间(天)',
    						                	xtype: 'numberfield',
    						                	name: 'operationMaintenance.acqdata_vacuate_retentionTim',
    					                        id: 'operationMaintenance_acqdata_vacuate_retentionTim_id',
    				                        	minValue: 1
    						                }
    				                    ]
    				                }, {
    				                    items: [
    				                        {
    				                        	fieldLabel: '结束时间',
    				                        	name:'operationMaintenance.databaseMaintenanceEndTime',
    				                        	id:'operationMaintenance_databaseMaintenanceEndTime_Id',
    						                    allowBlank: false
    						                }
    				                    ]
    				                }]
    				            },{
    				            	xtype: 'fieldset',
    				            	title: '数据抽稀',
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
    				                        	fieldLabel: '抽稀记录数',
    						                    allowBlank: false,
    						                	xtype: 'numberfield',
    						                	name:'operationMaintenance.vacuateRecord',
    				                        	id:'operationMaintenance_vacuateRecord_Id',
    				                        	minValue: 1
    						                },
    						                {
    				                        	fieldLabel: '抽稀数据保存周期波动(分钟)',
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
    				                        	fieldLabel: '抽稀数据保存周期(小时)',
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
    				                        	fieldLabel: '抽稀表启用限值(记录数大于)',
    				                        	tpl:'查询总记录数超过该值，将从抽稀表中查询数据',
    						                    allowBlank: false,
    						                	xtype: 'numberfield',
    						                	name:'operationMaintenance.vacuateThreshold',
    				                        	id:'operationMaintenance_vacuateThreshold_Id',
    				                        	minValue: 1
    						                }
    				                    ]
    				                }]
    				            }
    				        ]
    				    }],
    				    buttons: [{
    				        text: '提交',
//    				        formBind: true, // 表单验证通过时启用按钮
    				        iconCls: 'save',
    				        handler: function() {
    				        	var OperationMaintenanceBasicInfoSubForm = Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id").down('form');
    				        	if (OperationMaintenanceBasicInfoSubForm.isValid()) {
    				        		var configFile={};
    				        		configFile.others={};
    				        		configFile.others.loginLanguage=Ext.getCmp('operationMaintenance_LogonLanguageCombox_Id').getValue();
    				        		configFile.others.showLogo=Ext.getCmp('operationMaintenance_showLogo1_Id').getValue();
    				        		configFile.others.printLog=Ext.getCmp('operationMaintenance_printLog1_Id').getValue();
    				        		configFile.others.timeEfficiencyUnit=Ext.getCmp('operationMaintenance_timeEfficiencyUnit1_Id').getValue()?1:2;
    				        		configFile.others.resourceMonitoringSaveData=Ext.getCmp('operationMaintenance_resourceMonitoringSaveData_Id').getValue();
    				        		configFile.others.exportLimit=Ext.getCmp('operationMaintenance_exportLimit_Id').getValue();
    				        		configFile.others.simulateAcqEnable=Ext.getCmp('operationMaintenance_simulateAcqEnable1_Id').getValue();
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
    				        		
    				        		configFile.databaseMaintenance.tableConfig.acqdata_vacuate={};
    				        		configFile.databaseMaintenance.tableConfig.acqdata_vacuate.enabled=Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').getValue();
    				        		configFile.databaseMaintenance.tableConfig.acqdata_vacuate.retentionTime=Ext.getCmp('operationMaintenance_acqdata_vacuate_retentionTim_id').getValue();
    				        		
    				        		configFile.dataVacuate={};
    				        		configFile.dataVacuate.vacuateRecord=Ext.getCmp('operationMaintenance_vacuateRecord_Id').getValue();
    				        		configFile.dataVacuate.saveInterval=Ext.getCmp('operationMaintenance_vacuateSaveInterval_Id').getValue();
    				        		configFile.dataVacuate.saveIntervalWaveRange=Ext.getCmp('operationMaintenance_vacuateSaveIntervalWaveRange_Id').getValue();
    				        		configFile.dataVacuate.vacuateThreshold=Ext.getCmp('operationMaintenance_vacuateThreshold_Id').getValue();
    				        		
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
        				                        loadOemConfigInfo();
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
    				    }]
    	            }]
        		},{
        			title: '备份与恢复',
        			id:'OperationMaintenanceBackupsInfoPanel_Id',
        			layout: 'border',
        			items:[{
        				region: 'west',
        				title:"导出",
        				width:'20%',
        				id:'BatchExportModuleTreePanel_Id',
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
                    		text: '一键导出',
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
                        }],
        			},{
        				region: 'center',
        				title:'导入',
        				layout: 'fit',
        				id:'OperationMaintenanceDataImportPanel_Id',
        				tbar:[{
                            xtype: 'button',
                            text: '上一步',
                            iconCls: 'forward',
                            id:'OperationMaintenanceImportDataForwardBtn_Id',
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                            handler: function (v, o) {
                            	var selectRow=Ext.getCmp("BatchExportModuleDataListSelectRow_Id").getValue();
                            	var gridPanel = Ext.getCmp("BatchExportModuleTreeGridPanel_Id");
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
                            text: '下一步',
                            iconCls: 'backwards',
                            id:'OperationMaintenanceImportDataBackwardBtn_Id',
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                            handler: function (v, o) {
                            	var selectRow=Ext.getCmp("BatchExportModuleDataListSelectRow_Id").getValue();
                            	var gridPanel = Ext.getCmp("BatchExportModuleTreeGridPanel_Id");
                            	if(parseInt(selectRow)>=0){
                            		gridPanel.getSelectionModel().deselectAll(true);
                                	gridPanel.getSelectionModel().select(parseInt(selectRow)+1, true);
                            	}
                            }
                        }]
        			}]
        		}],
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check3');
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
    					if(newCard.id=="OperationMaintenanceBasicInfoPanel_Id"){
    						loadOemConfigInfo();
    					}else if(newCard.id=="OperationMaintenanceBackupsInfoPanel_Id"){
    						var treeGridPanel = Ext.getCmp("BatchExportModuleTreeGridPanel_Id");
    			            if (isNotVal(treeGridPanel)) {
    			            	treeGridPanel.getStore().load();
    			            }else{
    			            	Ext.create("AP.store.operationMaintenance.BatchExportModuleTreeInfoStore");
    			            }
    					}
    				}
    			}
        	}],
        	listeners: {
    			beforeclose: function ( panel, eOpts) {
    				
    			},
    			afterrender: function ( panel, eOpts) {
    				loadOemConfigInfo();
    			}
    		}
        });
        me.callParent(arguments);
    }
});

function loadOemConfigInfo(){
	Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/operationMaintenanceController/loadOemConfigInfo',
		success:function(response) {
			Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id").getEl().unmask();
			var data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				var configFile=data.configFile;
				initOemConfigData(configFile);
//				alert(configFile.others.printLog);
			} else {
				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
			}
		},
		failure:function(){
			Ext.getCmp("OperationMaintenanceBasicInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			
		}
	});
}

function initOemConfigData(configFile){
	Ext.getCmp('operationMaintenance_LogonLanguageCombox_Id').setRawValue(configFile.others.loginLanguage);
	if(configFile.others.showLogo){
    	Ext.getCmp('operationMaintenance_showLogo1_Id').setValue(true);
    }else{
    	Ext.getCmp('operationMaintenance_showLogo0_Id').setValue(true);
    }
	if(configFile.others.printLog){
    	Ext.getCmp('operationMaintenance_printLog1_Id').setValue(true);
    }else{
    	Ext.getCmp('operationMaintenance_printLog0_Id').setValue(true);
    }
	
	if(configFile.others.timeEfficiencyUnit==1){
    	Ext.getCmp('operationMaintenance_timeEfficiencyUnit1_Id').setValue(true);
    }else{
    	Ext.getCmp('operationMaintenance_timeEfficiencyUnit2_Id').setValue(true);
    }
	Ext.getCmp('operationMaintenance_resourceMonitoringSaveData_Id').setValue(configFile.others.resourceMonitoringSaveData);
	Ext.getCmp('operationMaintenance_exportLimit_Id').setValue(configFile.others.exportLimit);
	
	if(configFile.others.simulateAcqEnable){
    	Ext.getCmp('operationMaintenance_simulateAcqEnable1_Id').setValue(true);
    }else{
    	Ext.getCmp('operationMaintenance_simulateAcqEnable0_Id').setValue(true);
    }
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
	Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').setValue(configFile.databaseMaintenance.tableConfig.acqdata_vacuate.enabled);
	
	Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').setValue(configFile.databaseMaintenance.tableConfig.acqdata_hist.retentionTime);
	Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').setValue(configFile.databaseMaintenance.tableConfig.acqrawdata.retentionTime);
	Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').setValue(configFile.databaseMaintenance.tableConfig.alarminfo_hist.retentionTime);
	Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').setValue(configFile.databaseMaintenance.tableConfig.dailytotalcalculate_hist.retentionTime);
	Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').setValue(configFile.databaseMaintenance.tableConfig.dailycalculationdata.retentionTime);
	Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').setValue(configFile.databaseMaintenance.tableConfig.timingcalculationdata.retentionTime);
	Ext.getCmp('operationMaintenance_acqdata_vacuate_retentionTim_id').setValue(configFile.databaseMaintenance.tableConfig.acqdata_vacuate.retentionTime);
	
	Ext.getCmp('operationMaintenance_vacuateRecord_Id').setValue(configFile.dataVacuate.vacuateRecord);
	Ext.getCmp('operationMaintenance_vacuateSaveInterval_Id').setValue(configFile.dataVacuate.saveInterval);
	Ext.getCmp('operationMaintenance_vacuateSaveIntervalWaveRange_Id').setValue(configFile.dataVacuate.saveIntervalWaveRange);
	Ext.getCmp('operationMaintenance_vacuateThreshold_Id').setValue(configFile.dataVacuate.vacuateThreshold);
	
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
		Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').disable();
		
		Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').disable();
		Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').disable();
		Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').disable();
		Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').disable();
		Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').disable();
		Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').disable();
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
		Ext.getCmp('operationMaintenance_acqdata_vacuate_enabled_id').enable();
		
		Ext.getCmp('operationMaintenance_acqdata_hist_retentionTime_Id').enable();
		Ext.getCmp('operationMaintenance_acqrawdata_retentionTime_id').enable();
		Ext.getCmp('operationMaintenance_alarminfo_hist_retentionTime_id').enable();
		Ext.getCmp('operationMaintenance_dailytotalcalculate_hist_retentionTime_id').enable();
		Ext.getCmp('operationMaintenance_dailycalculationdata_retentionTime_id').enable();
		Ext.getCmp('operationMaintenance_timingcalculationdata_retentionTim_id').enable();
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
//    	submitAuxiliaryDeviceBackupData();
    }else if(code.toUpperCase()=='primaryDevice'.toUpperCase()){
//    	submitPrimaryDeviceBackupData();
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
	
	
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
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
	
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
//    	saveAuxiliaryDeviceBackupData();
    }else if(code.toUpperCase()=='primaryDevice'.toUpperCase()){
//    	savePrimaryDeviceBackupData();
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
	Ext.Ajax.request({
        url: context + '/moduleManagerController/saveAllImportedModule',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveBackupDataDictionaryFile(){
	Ext.Ajax.request({
        url: context + '/systemdataInfoController/saveAllImportedDataDictionary',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveBackupOrganizationFile(){
	Ext.Ajax.request({
        url: context + '/orgManagerController/saveAllImportedOrganization',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveBackupImportedRoleFile(){
	Ext.Ajax.request({
        url: context + '/roleManagerController/saveAllImportedRole',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function saveBackupUserFile(){
	Ext.Ajax.request({
        url: context + '/userManagerController/saveAllImportedUser',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}
function saveProtocolBackupData(){
	Ext.Ajax.request({
        url: context + '/userManagerController/saveAllImportedUser',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailure + "</font>");
            }
            Ext.getCmp("ImportBackupContentGridPanel_Id").getStore().load();
        },
        failure: function () {
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
		verification.info='请先导入模块数据';
	}else if(code.toUpperCase()=='organization'.toUpperCase()){
		
	}else if(code.toUpperCase()=='role'.toUpperCase()){
		verification.info='请先导入模块数据';
	}else if(code.toUpperCase()=='user'.toUpperCase()){
		verification.info='请先导入组织数据和角色数据';
    }else if(code.toUpperCase()=='auxiliaryDevice'.toUpperCase()){
    	
    }else if(code.toUpperCase()=='primaryDevice'.toUpperCase()){
    	verification.info='请先导入组织数据和驱动数据';
    }else if(code.toUpperCase()=='protocol'.toUpperCase()){
    	
    }else if(code.toUpperCase()=='acqUnit'.toUpperCase()){
    	verification.info='请先导入协议数据';
    }else if(code.toUpperCase()=='displayUnit'.toUpperCase()){
    	verification.info='请先导入采控单元数据';
    }else if(code.toUpperCase()=='alarmUnit'.toUpperCase()){
    	verification.info='请先导入协议数据';
    }else if(code.toUpperCase()=='reportUnit'.toUpperCase()){
    	
    }else if(code.toUpperCase()=='acqInstance'.toUpperCase()){
    	verification.info='请先导入采控单元数据';
    }else if(code.toUpperCase()=='displayInstance'.toUpperCase()){
    	verification.info='请先导入显示单元数据';
    }else if(code.toUpperCase()=='alarmInstance'.toUpperCase()){
    	verification.info='请先导入报警单元数据';
    }else if(code.toUpperCase()=='reportInstance'.toUpperCase()){
    	verification.info='请先导入报表单元数据';
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
        
//        success: function (response, action) {
//        	verification.sign=action.result.msg;
//        },
        
        failure: function () {
        	
        }
    });
	return verification;
}
