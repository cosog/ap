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
    				                        	fieldLabel: '查询限值',
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
                    	tbar:['->', {
                    		xtype: 'button',
                    		text: '一键导出',
                    		iconCls: 'export',
                    		disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                    		hidden: false,
                    		handler: function (v, o) {
                    			oneKeyBackupData();
                    		}
                    	}],
        			},{
        				region: 'center',
        				title:'导入'
        			}]
        			
        			
        			
//                	tbar:['->', {
//                        xtype: 'button',
//                        text: '一键导出',
//                        iconCls: 'export',
//                        disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
//                        hidden: false,
//                        handler: function (v, o) {
//                        	oneKeyBackupData();
//                        }
//                    }],
//                    layout: 'border',
//                    items:[{
//                    	region: 'center',
//                    	title:'数据导入',
//    					border: false,
////    		            id: "OperationMaintenanceBasicInfoSubFormId",
//    					layout: 'fit',
//                    	items: [{
//                            xtype: 'form',
//                            baseCls: 'x-plain',
//                            border:false,
//                            defaults: {
//                            	border: false,
//                                baseCls: 'x-plain',
//                                flex: 1,
//                                defaultType: 'colorfield',
//                                layout: 'anchor'
//                            },
//                            style: {
//                                padding: '10px 10px'
//                            },
//                            layout:'hbox',
//                            items: [{
//                            	items:[{
//                            		xtype: 'form',
//                                    id: 'ModuleBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
//                                        name: 'file',
//                                        fieldLabel: '模块',
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
//                                            	submitBackupModuleFile();
//                                            }
//                                        }
//                                    }]
//                            	},{
//                            		xtype: 'form',
//                                    id: 'DataDictionaryBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
//                                        name: 'file',
//                                        fieldLabel: '字典',
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
//                                            	submitBackupDataDictionaryFile();
//                                            }
//                                        }
//                                    }]
//                            	},{
//                            		xtype: 'form',
//                                    id: 'OrganizationBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
//                                        name: 'file',
//                                        fieldLabel: '组织',
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
//                                            	submitBackupOrganizationFile();
//                                            }
//                                        }
//                                    }]
//                            	},{
//                            		xtype: 'form',
//                                    id: 'RoleBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                		xtype: 'filefield',
//                                        name: 'file',
//                                        fieldLabel: '角色',
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
//                                            	submitBackupImportedRoleFile();
//                                            }
//                                        }
//                                    }]
//                            	},{
//                            		xtype: 'form',
//                                    id: 'UserBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
//                                        name: 'file',
//                                        fieldLabel: '用户',
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
//                                            	submitBackupUserFile();
//                                            }
//                                        }
//                                    }]
//                            	},{
//                            		xtype: 'form',
//                                    id: 'ProtocolBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
//                                        name: 'file',
//                                        fieldLabel: '协议',
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            		
//                            	},{
//                            		xtype: 'form',
//                                    id: 'AcqUnitBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '采控单元',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            	},{
//                            		xtype: 'form',
//                                    id: 'DisplayUnitBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '显示单元',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            	},{
//                            		xtype: 'form',
//                                    id: 'AlarmUnitBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '报警单元',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            		
//                            	},{
//                            		xtype: 'form',
//                                    id: 'ReportUnitBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '报表单元',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            		
//                            	},{
//                            		xtype: 'form',
//                                    id: 'AcqInstanceBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '采控实例',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            		
//                            	},{
//                            		xtype: 'form',
//                                    id: 'DisplayInstanceBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '显示实例',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            		
//                            	},{
//                            		xtype: 'form',
//                                    id: 'AlarmInstanceBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '报警实例',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            		
//                            	},{
//                            		xtype: 'form',
//                                    id: 'ReportInstanceBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '报表实例',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            		
//                            	},{
//                            		xtype: 'form',
//                                    id: 'PrimaryDeviceBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '主设备',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            		
//                            	},{
//                            		xtype: 'form',
//                                    id: 'AuxiliaryDeviceBackupImportForm_Id',
//                                    bodyPadding: 0,
//                                    frame: true,
//                                    items: [{
//                                    	xtype: 'filefield',
////                                    	id:'ProtocolImportFilefield_Id',
//                                        name: 'file',
//                                        fieldLabel: '辅件设备',
////                                        labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
//                                        width:'100%',
//                                        msgTarget: 'side',
//                                        allowBlank: true,
//                                        anchor: '100%',
//                                        draggable:true,
//                                        buttonText: loginUserLanguageResource.selectUploadFile,
//                                        accept:'.json',
//                                        listeners:{
//                                            change:function(cmp){
////                                            	submitImportedProtocolFile();
//                                            }
//                                        }
//                                    }]
//                            		
//                            	}]
//                            }]
//                        }]
//                    },{
//                    	region: 'east',
//                    	width:'50%',
//                    	split: true,
//                        collapsible: true,
//                        layout:'fit',
//                        id:'importBackupDataContentPanel_Id',
//                        bbar:['->', {
//                            xtype: 'button',
//                            text: loginUserLanguageResource.save,
//                            iconCls: 'save',
//                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
//                            hidden: false,
//                            disabled:true,
//                            handler: function (v, o) {
////                            	oneKeyBackupData();
//                            }
//                        }],
//                    }]
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
function oneKeyBackupData(){
	var treeGridPanel = Ext.getCmp("BatchExportModuleTreeGridPanel_Id");
	var selectedRecord = treeGridPanel.getChecked();
	var exportContentList = [];
	
	if(selectedRecord.length>0){
		Ext.Array.each(selectedRecord, function (name, index, countriesItSelf) {
	        var code = selectedRecord[index].get('code');
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
	        }
	    });
	}else{
		Ext.MessageBox.alert(loginUserLanguageResource.message,"请选择要导出的数据");
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
	var url = context + '/acquisitionUnitManagerController/exportProtocolData';
	var timestamp=new Date().getTime();
	var key='exportProtocolData'+'_'+timestamp;
	var maskPanelId='OperationMaintenanceBackupsInfoPanel_Id';
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.roleExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    downloadFile(url + '?flag=true' + param);
//	document.location.href = url;
    
    
    
    var timestamp=new Date().getTime();
	var key='exportProtocolData'+'_'+timestamp;
	var maskPanelId='ExportProtocolWindow_Id';
	
	var url=context + '/acquisitionUnitManagerController/exportProtocolData?key='+key+'&protocolList='+exportProtocolList.join(",");
	if(type==2){
		key='exportProtocolInitData'+'_'+timestamp;
		url=context + '/acquisitionUnitManagerController/exportProtocolInitData?key='+key+'&protocolList='+exportProtocolList.join(",");
	}
	exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    openExcelWindow(url);
//	document.location.href = url;
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
	
	var param = "&orgId=" + leftOrg_Id 
    + "&deviceType="+deviceType
    + "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}

function submitBackupModuleFile() {
	Ext.getCmp('importBackupDataContentPanel_Id').setTitle(loginUserLanguageResource.importModule);
	Ext.getCmp('importBackupDataContentPanel_Id').removeAll();
    var form = Ext.getCmp("ModuleBackupImportForm_Id");
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
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                }

                var importBackupModuleContentTreeGridPanel = Ext.getCmp("ImportBackupModuleContentTreeGridPanel_Id");
                if (isNotVal(importBackupModuleContentTreeGridPanel)) {
                	importBackupModuleContentTreeGridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupModuleContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
            }
        });
    }
    return false;
};

function submitBackupDataDictionaryFile() {
	Ext.getCmp('importBackupDataContentPanel_Id').setTitle(loginUserLanguageResource.importDataDictionary);
	Ext.getCmp('importBackupDataContentPanel_Id').removeAll();
    var form = Ext.getCmp("DataDictionaryBackupImportForm_Id");
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
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                }

                var importBackupDataDictionaryContentTreeGridPanel = Ext.getCmp("ImportBackupDataDictionaryContentTreeGridPanel_Id");
                if (isNotVal(importBackupDataDictionaryContentTreeGridPanel)) {
                	importBackupDataDictionaryContentTreeGridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupDataDictionaryContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
            }
        });
    }
    return false;
};

function submitBackupOrganizationFile() {
	Ext.getCmp('importBackupDataContentPanel_Id').setTitle(loginUserLanguageResource.importOrganization);
	Ext.getCmp('importBackupDataContentPanel_Id').removeAll();
    var form = Ext.getCmp("OrganizationBackupImportForm_Id");
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
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                }

                var importBackupOrganizationContentTreeGridPanel = Ext.getCmp("ImportBackupOrganizationContentTreeGridPanel_Id");
                if (isNotVal(importBackupOrganizationContentTreeGridPanel)) {
                	importBackupOrganizationContentTreeGridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupOrganizationContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
            }
        });
    }
    return false;
};

function submitBackupImportedRoleFile() {
	Ext.getCmp('importBackupDataContentPanel_Id').setTitle(loginUserLanguageResource.importRole);
	Ext.getCmp('importBackupDataContentPanel_Id').removeAll();
    var form = Ext.getCmp("RoleBackupImportForm_Id");
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
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                }

                var importBackupRoleContentTreeGridPanel = Ext.getCmp("ImportBackupRoleContentTreeGridPanel_Id");
                if (isNotVal(importBackupRoleContentTreeGridPanel)) {
                	importBackupRoleContentTreeGridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupRoleContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
            }
        });
    }
    return false;
};

function submitBackupUserFile() {
	Ext.getCmp('importBackupDataContentPanel_Id').setTitle(loginUserLanguageResource.importUser);
	Ext.getCmp('importBackupDataContentPanel_Id').removeAll();
    var form = Ext.getCmp("UserBackupImportForm_Id");
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
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                }

                var importBackupUserContentTreeGridPanel = Ext.getCmp("ImportBackupUserContentTreeGridPanel_Id");
                if (isNotVal(importBackupUserContentTreeGridPanel)) {
                	importBackupUserContentTreeGridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.operationMaintenance.ImportBackupUserContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
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