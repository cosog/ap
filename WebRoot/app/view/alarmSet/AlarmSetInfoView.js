Ext.require([
'MyExtend.Form.Field.ColorField'
]);
Ext.define("AP.view.alarmSet.AlarmSetInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.alarmSetInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var AlarmSetInfoGridPanel = Ext.create("AP.view.alarmSet.AlarmSetInfoGridPanel");
        Ext.applyIf(me, {
            items: [{
                border: false,
                layout: 'border',
                id: 'alarmSetGlobalPanel_Id',
                items: [{
                    region: 'west',
                    width: '50%',
                    split: true,
                    border: false,
                    // collapsible: true,
                    height: '100%',
                    layout: 'fit',
                    autoScroll: false,
                    items: AlarmSetInfoGridPanel
                }, {
                    region: 'center',
                    height: '100%',
                    layout: 'fit',
                    border: false,
                    tbar: [{
                    	xtype:'label',
                    	text:'报警颜色'
                    },{
                        id: 'alarmLevelOpacity0_id',//报警背景色透明度
                        xtype: 'textfield',
                        value: 1,
                        hidden: true
                    },{
                        id: 'alarmLevelOpacity1_id',//报警背景色透明度
                        xtype: 'textfield',
                        value: 1,
                        hidden: true
                    },{
                        id: 'alarmLevelOpacity2_id',//报警背景色透明度
                        xtype: 'textfield',
                        value: 1,
                        hidden: true
                    },{
                        id: 'alarmLevelOpacity3_id',//报警背景色透明度
                        xtype: 'textfield',
                        value: 1,
                        hidden: true
                    },'->', {
                        xtype: 'button',
                        hidden: false,
                        itemId: 'saveAlarmColorSetBtnId',
                        id: 'saveAlarmColorSetBtnId',
                        action: '',
                        text : cosog.string.update,
                        iconCls: 'edit',
                        handler: function () {
                            setAlarmLevelColor();
                        }
                    }],
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
                                id: 'alarmLevelBackgroundColor1_id',
                                fieldLabel: '一级报警',
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('alarmLevelBackgroundColor1_id')!=undefined){
                                    		Ext.getCmp('alarmLevelOpacity1_id').setValue(field.color.a);
                                        	Ext.getCmp('alarmLevelBackgroundColor1_id').inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'alarmLevelBackgroundColor2_id',
                                fieldLabel: '二级报警',
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('alarmLevelBackgroundColor2_id')!=undefined){
                                    		Ext.getCmp('alarmLevelOpacity2_id').setValue(field.color.a);
                                        	Ext.getCmp('alarmLevelBackgroundColor2_id').inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'alarmLevelBackgroundColor3_id',
                                fieldLabel: '三级报警',
                                anchor:'90%',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('alarmLevelBackgroundColor3_id')!=undefined){
                                    		Ext.getCmp('alarmLevelOpacity3_id').setValue(field.color.a);
                                        	Ext.getCmp('alarmLevelBackgroundColor3_id').inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'alarmLevelBackgroundColor0_id',
                                fieldLabel: '正常',
                                anchor:'90%',
                                listeners : {
                                    collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('alarmLevelBackgroundColor0_id')!=undefined){
                                    		Ext.getCmp('alarmLevelOpacity0_id').setValue(field.color.a);
                                        	Ext.getCmp('alarmLevelBackgroundColor0_id').inputEl.applyStyles({
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
                                id: 'alarmLevelColor1_id',
                                fieldLabel: '一级报警',
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                    collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('alarmLevelColor1_id')!=undefined){
                                        	Ext.getCmp('alarmLevelColor1_id').inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'alarmLevelColor2_id',
                                fieldLabel: '二级报警',
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('alarmLevelColor2_id')!=undefined){
                                        	Ext.getCmp('alarmLevelColor2_id').inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'alarmLevelColor3_id',
                                fieldLabel: '三级报警',
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('alarmLevelColor3_id')!=undefined){
                                        	Ext.getCmp('alarmLevelColor3_id').inputEl.applyStyles({
                          		              background: '#'+field.value,
                          		              opacity:field.color.a
                          		            });
                                    	}
                                    }
                                }
                        	},{
                                id: 'alarmLevelColor0_id',
                                fieldLabel: '正常',
                                anchor:'90%',
                                value:'#FFFFFF',
                                listeners : {
                                	collapse: function (field,eOpts) {
                                    	if(Ext.getCmp('alarmLevelColor0_id')!=undefined){
                                        	Ext.getCmp('alarmLevelColor0_id').inputEl.applyStyles({
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