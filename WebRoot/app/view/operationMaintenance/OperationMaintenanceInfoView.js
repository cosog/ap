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
//        	tbar:['->', {
//        		xtype: 'button',
//                text: loginUserLanguageResource.save,
//                iconCls: 'save',
//                disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
//                hidden: false,
//                handler: function (v, o) {
//                	
//                }
//            },"-", {
//                xtype: 'button',
//                text: loginUserLanguageResource.exportData,
//                iconCls: 'export',
//                disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
//                hidden: false,
//                handler: function (v, o) {
//                	
//                }
//            },"-",{
//            	xtype: 'button',
//    			text: loginUserLanguageResource.importData,
//    			disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
//    			iconCls: 'import',
//    			handler: function (v, o) {
//    				
//    			}
//            }],
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
    				                        	fieldLabel: '抽稀阈值',
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
//    				            var form = this.up('panel').down('form').getForm();
//    				            if (form.isValid()) {
//    				                form.submit({
//    				                    success: function(form, action) {
//    				                        Ext.Msg.alert('成功', action.result.message);
//    				                    },
//    				                    failure: function(form, action) {
//    				                        Ext.Msg.alert('失败', action.result.message);
//    				                    }
//    				                });
//    				            }
    				        	
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
        			id:'OperationMaintenanceBackupsInfoPanel_Id'
        		}],
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check3');
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
    					if(newCard.id=="OperationMaintenanceBasicInfoPanel_Id"){
    						
    					}else if(newCard.id=="OperationMaintenanceBackupsInfoPanel_Id"){
    						
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