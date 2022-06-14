Ext.define("AP.view.alarmSet.AlarmSetInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.alarmSetInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        Ext.applyIf(me, {
            items: [{
                border: false,
                layout: 'border',
                id: 'alarmSetGlobalPanel_Id',
                tbar: [{
                	xtype:'label',
                	text:'报警颜色'
                },{
                    id: 'overviewNormalOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'overviewFirstLevelOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'overviewSecondLevelOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'overviewThirdLevelOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'detailsNormalOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'detailsFirstLevelOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'detailsSecondLevelOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'detailsThirdLevelOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'statisticsNormalOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'statisticsFirstLevelOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'statisticsSecondLevelOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },{
                    id: 'statisticsThirdLevelOpacity_id',//报警背景色透明度
                    xtype: 'textfield',
                    value: 1,
                    hidden: true
                },'->', {
                    xtype: 'button',
                    hidden: false,
                    itemId: 'saveAlarmColorSetBtnId',
                    id: 'saveAlarmColorSetBtnId',
                    action: '',
                    text: cosog.string.save,
        			iconCls: 'save',
                    handler: function () {
                        setAlarmLevelColor();
                    }
                }],
                items: [{
                	title:'概览数据',
                	region: 'west',
                	split: true,
                    collapsible: true,
                	width: '33%',
                    layout: 'fit',
                    border: false,
                    items: [{
                        xtype: 'form',
                        baseCls: 'x-plain',
                        border:false,
                        defaults: {
                        	border: false,
                            baseCls: 'x-plain',
                            flex: 1,
                            defaultType: 'colorfield',
                            layout: 'anchor'
                        },
                        style: {
                            padding: '10px 10px'
                        },
                        layout:'hbox',
                        items: [{
                        	items:[{
                        		xtype:'displayfield',
                            	fieldLabel: '<font color=red >背景色</font>',
                            	value:''
                            },{
                                id: 'overviewFirstLevelBackgroundColor_id',
                                fieldLabel: '一级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('overviewFirstLevelBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('overviewFirstLevelOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'overviewSecondLevelBackgroundColor_id',
                                fieldLabel: '二级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('overviewSecondLevelBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('overviewSecondLevelOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'overviewThirdLevelBackgroundColor_id',
                                fieldLabel: '三级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('overviewThirdLevelBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('overviewThirdLevelOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'overviewNormalBackgroundColor_id',
                                fieldLabel: '正常',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                    collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('overviewNormalBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('overviewNormalOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	}]
                        },{
                        	items:[{
                        		xtype:'displayfield',
                            	fieldLabel: '<font color=red >前景色</font>',
                            	value:''
                            },{
                                id: 'overviewFirstLevelColor_id',
                                fieldLabel: '一级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                    collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('overviewFirstLevelColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'overviewSecondLevelColor_id',
                                fieldLabel: '二级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('overviewSecondLevelColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'overviewThirdLevelColor_id',
                                fieldLabel: '三级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('overviewThirdLevelColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'overviewNormalColor_id',
                                fieldLabel: '正常',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('overviewNormalColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	}]
                        }]

                    }]
                },{
                	title:'实时/历史数据',
                	region: 'center',
                	layout: 'fit',
                    border: false,
                    items: [{
                        xtype: 'form',
                        baseCls: 'x-plain',
                        border:false,
                        defaults: {
                        	border: false,
                            baseCls: 'x-plain',
                            flex: 1,
                            defaultType: 'colorfield',
                            layout: 'anchor'
                        },
                        style: {
                            padding: '10px 10px'
                        },
                        layout:'hbox',
                        items: [{
                        	items:[{
                        		xtype:'displayfield',
                            	fieldLabel: '<font color=red >背景色</font>',
                            	value:''
                            },{
                                id: 'detailsFirstLevelBackgroundColor_id',
                                fieldLabel: '一级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('detailsFirstLevelBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('detailsFirstLevelOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'detailsSecondLevelBackgroundColor_id',
                                fieldLabel: '二级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('detailsSecondLevelBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('detailsSecondLevelOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'detailsThirdLevelBackgroundColor_id',
                                fieldLabel: '三级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('detailsThirdLevelBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('detailsThirdLevelOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'detailsNormalBackgroundColor_id',
                                fieldLabel: '正常',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                    collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('detailsNormalBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('detailsNormalOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	}]
                        },{
                        	items:[{
                        		xtype:'displayfield',
                            	fieldLabel: '<font color=red >前景色</font>',
                            	value:''
                            },{
                                id: 'detailsFirstLevelColor_id',
                                fieldLabel: '一级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                    collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('detailsFirstLevelColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'detailsSecondLevelColor_id',
                                fieldLabel: '二级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('detailsSecondLevelColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'detailsThirdLevelColor_id',
                                fieldLabel: '三级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('detailsThirdLevelColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'detailsNormalColor_id',
                                fieldLabel: '正常',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('detailsNormalColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	}]
                        }]

                    }]
                },{
                	title:'统计数据',
                	region: 'east',
                	split: true,
                    collapsible: true,
                	width: '33%',
                	layout: 'fit',
                    border: false,
                    items: [{
                        xtype: 'form',
                        baseCls: 'x-plain',
                        border:false,
                        defaults: {
                        	border: false,
                            baseCls: 'x-plain',
                            flex: 1,
                            defaultType: 'colorfield',
                            layout: 'anchor'
                        },
                        style: {
                            padding: '10px 10px'
                        },
                        layout:'hbox',
                        items: [{
                        	items:[{
                        		xtype:'displayfield',
                            	fieldLabel: '<font color=red >背景色</font>',
                            	value:''
                            },{
                                id: 'statisticsFirstLevelBackgroundColor_id',
                                fieldLabel: '一级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('statisticsFirstLevelBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('statisticsFirstLevelOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'statisticsSecondLevelBackgroundColor_id',
                                fieldLabel: '二级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('statisticsSecondLevelBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('statisticsSecondLevelOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'statisticsThirdLevelBackgroundColor_id',
                                fieldLabel: '三级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('statisticsThirdLevelBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('statisticsThirdLevelOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'statisticsNormalBackgroundColor_id',
                                fieldLabel: '正常',
                                labelWidth: 60,
                                anchor:'90%',
                                listeners : {
                                    collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('statisticsNormalBackgroundColor_id')!=undefined){
                                    		Ext.getCmp('statisticsNormalOpacity_id').setValue(field.color.a);
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	}]
                        },{
                        	items:[{
                        		xtype:'displayfield',
                            	fieldLabel: '<font color=red >前景色</font>',
                            	value:''
                            },{
                                id: 'statisticsFirstLevelColor_id',
                                fieldLabel: '一级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                    collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('statisticsFirstLevelColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'statisticsSecondLevelColor_id',
                                fieldLabel: '二级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('statisticsSecondLevelColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'statisticsThirdLevelColor_id',
                                fieldLabel: '三级报警',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('statisticsThirdLevelColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'statisticsNormalColor_id',
                                fieldLabel: '正常',
                                labelWidth: 60,
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('statisticsNormalColor_id')!=undefined){
                                    		field.inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	}]
                        }]

                    }]
                }]
            }]
        });
        me.callParent(arguments);
    }

});