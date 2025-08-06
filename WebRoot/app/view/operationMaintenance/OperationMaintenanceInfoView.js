var loginUserOperationMaintenanceModuleRight=getRoleModuleRight('OperationMaintenance');
Ext.define("AP.view.operationMaintenance.OperationMaintenanceInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.moduleInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    bodyStyle: 'background-color:#ffffff;',
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar:['->', {
        		xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                hidden: false,
                handler: function (v, o) {
                	
                }
            },"-", {
                xtype: 'button',
                text: loginUserLanguageResource.exportData,
                iconCls: 'export',
                disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                hidden: false,
                handler: function (v, o) {
                	
                }
            },"-",{
            	xtype: 'button',
    			text: loginUserLanguageResource.importData,
    			disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
    			iconCls: 'import',
    			handler: function (v, o) {
    				
    			}
            }],
            
            items:[{
            	region: 'north',
				height:'1500px',
				xtype:'form',
				border: false,
//	            id: "sysSubFormId",
				bodyPadding: 10,
				border:false,
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
			                        {
					                    xtype: 'combobox',
					                    fieldLabel: '登录界面语言',
					                    name: 'country',
					                    store: ['zh_CN', 'en', 'ru',], // 简单数组存储
					                    queryMode: 'local',
					                    editable: false,
					                    allowBlank: false
					                },
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
			                                    name:'timeEfficiencyUnit',
			                                    inputValue: '1',
			                                    id: 'timeEfficiencyUnit1_Id'
			                                }, {
			                                    boxLabel: '百分数',
			                                    name:'timeEfficiencyUnit',
			                                    checked:true,
			                                    inputValue:'2',
			                                    id:'timeEfficiencyUnit2_Id'
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
			                                    name:'simulateAcqEnable',
			                                    inputValue: '1',
			                                    id: 'simulateAcqEnable1_Id'
			                                }, {
			                                    boxLabel: loginUserLanguageResource.no,
			                                    name:'simulateAcqEnable',
			                                    checked:true,
			                                    inputValue:'0',
			                                    id:'simulateAcqEnable0_Id'
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
			                                    name:'showLogo',
			                                    inputValue: '1',
			                                    id: 'showLogo1_Id'
			                                }, {
			                                    boxLabel: loginUserLanguageResource.no,
			                                    name:'showLogo',
			                                    checked:true,
			                                    inputValue:'0',
			                                    id:'showLogo0_Id'
			                                }
			                            ]
			                        },
			                        { fieldLabel: '资源监测保存记录数' },
			                        { fieldLabel: '模拟数据发送周期' }
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
			                                    name:'printLog',
			                                    inputValue: '1',
			                                    id: 'printLog1_Id'
			                                }, {
			                                    boxLabel: loginUserLanguageResource.no,
			                                    name:'printLog',
			                                    checked:true,
			                                    inputValue:'0',
			                                    id:'printLog0_Id'
			                                }
			                            ]
			                        },
			                        { fieldLabel: '导出excel数据上限' }
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
			                        	fieldLabel: '执行周期',
					                    allowBlank: false
					                },
					                {
				                    	xtype: 'checkboxfield',
				                        fieldLabel: '历史数据表',
				                        name: 'acqdata_hist_enabled',
				                        boxLabel: '启用'
				                    },
					                {
				                    	xtype: 'checkboxfield',
				                        fieldLabel: '原始数据表',
				                        name: 'acqrawdata_enabled',
				                        boxLabel: '启用'
				                    },
					                {
				                    	xtype: 'checkboxfield',
				                        fieldLabel: '报警数据表',
				                        name: 'alarminfo_hist_enabled',
				                        boxLabel: '启用'
				                    },
					                {
				                    	xtype: 'checkboxfield',
				                        fieldLabel: '日累计计算表',
				                        name: 'dailytotalcalculate_hist_enabled',
				                        boxLabel: '启用'
				                    }
			                    ]
			                }, {
			                    items: [
			                        {
			                        	fieldLabel: '执行时间',
					                    allowBlank: false
					                },
			                        { 
					                	fieldLabel: '数据保留时间(天)' 
					                },
			                        { 
					                	fieldLabel: '数据保留时间(天)' 
					                },
			                        { 
					                	fieldLabel: '数据保留时间(天)' 
					                },
			                        { 
					                	fieldLabel: '数据保留时间(天)' 
					                }
			                    ]
			                }, {
			                    items: [
			                        {
			                        	fieldLabel: '结束时间',
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
					                    allowBlank: false
					                }
			                    ]
			                }, {
			                    items: [
			                        {
			                        	fieldLabel: '抽稀数据保存周期(小时)',
					                    allowBlank: false
					                }
			                    ]
			                }, {
			                    items: [
			                        {
			                        	fieldLabel: '抽稀阈值',
					                    allowBlank: false
					                }
			                    ]
			                }]
			            }
			        ]
			    }],
			    buttons: [{
			        text: '提交',
//			        formBind: true, // 表单验证通过时启用按钮
			        handler: function() {
//			            var form = this.up('panel').down('form').getForm();
//			            if (form.isValid()) {
//			                form.submit({
//			                    success: function(form, action) {
//			                        Ext.Msg.alert('成功', action.result.message);
//			                    },
//			                    failure: function(form, action) {
//			                        Ext.Msg.alert('失败', action.result.message);
//			                    }
//			                });
//			            }
			        }
			    }]
            }]
        });
        me.callParent(arguments);
    }

});