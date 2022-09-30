Ext.define("AP.view.acquisitionUnit.AlarmColorSelectWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.alarmColorSelectWindow',
    layout: 'fit',
    title:'报警颜色配置',
    iframe: true,
    id: 'AlarmColorSelectWindow_Id',
    closeAction: 'destroy',
    width: 500,
    height: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
//    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    bodyStyle: 'padding:5px;background-color:#FFFFFF;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout: 'fit',
        	tbar: [{
                id: 'goOnlineOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },{
                id: 'onlineOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },{
                id: 'offlineOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },{
                id: 'runOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },{
                id: 'stopOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },{
                id: 'normalOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },{
                id: 'normalOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },{
                id: 'firstLevelOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },{
                id: 'secondLevelOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },{
                id: 'thirdLevelOpacity_id',//报警背景色透明度
                xtype: 'textfield',
                value: 1,
                hidden: true
            },'->', {
                xtype: 'button',
                hidden: false,
                action: '',
                text: cosog.string.save,
    			iconCls: 'save',
                handler: function () {
                    saveAlarmColor();
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
                        id: 'goOnlineBackgroundColor_id',
                        fieldLabel: '上线',
                        labelWidth: 60,
                        anchor:'90%',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('goOnlineBackgroundColor_id')!=undefined){
                            		Ext.getCmp('goOnlineOpacity_id').setValue(field.color.a);
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'onlineBackgroundColor_id',
                        fieldLabel: '在线',
                        labelWidth: 60,
                        anchor:'90%',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('onlineBackgroundColor_id')!=undefined){
                            		Ext.getCmp('onlineOpacity_id').setValue(field.color.a);
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'offlineBackgroundColor_id',
                        fieldLabel: '离线',
                        labelWidth: 60,
                        anchor:'90%',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('offlineBackgroundColor_id')!=undefined){
                            		Ext.getCmp('offlineOpacity_id').setValue(field.color.a);
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'runBackgroundColor_id',
                        fieldLabel: '运行',
                        labelWidth: 60,
                        anchor:'90%',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('runBackgroundColor_id')!=undefined){
                            		Ext.getCmp('runOpacity_id').setValue(field.color.a);
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'stopBackgroundColor_id',
                        fieldLabel: '停抽',
                        labelWidth: 60,
                        anchor:'90%',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('stopBackgroundColor_id')!=undefined){
                            		Ext.getCmp('stopOpacity_id').setValue(field.color.a);
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'firstLevelBackgroundColor_id',
                        fieldLabel: '一级报警',
                        labelWidth: 60,
                        anchor:'90%',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('firstLevelBackgroundColor_id')!=undefined){
                            		Ext.getCmp('firstLevelOpacity_id').setValue(field.color.a);
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'secondLevelBackgroundColor_id',
                        fieldLabel: '二级报警',
                        labelWidth: 60,
                        anchor:'90%',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('secondLevelBackgroundColor_id')!=undefined){
                            		Ext.getCmp('secondLevelOpacity_id').setValue(field.color.a);
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'thirdLevelBackgroundColor_id',
                        fieldLabel: '三级报警',
                        labelWidth: 60,
                        anchor:'90%',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('thirdLevelBackgroundColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'normalBackgroundColor_id',
                        fieldLabel: '正常',
                        labelWidth: 60,
                        anchor:'90%',
                        listeners : {
                            collapse: function (field,eOpts) {
                            	if(Ext.getCmp('normalBackgroundColor_id')!=undefined){
                            		Ext.getCmp('normalOpacity_id').setValue(field.color.a);
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
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
                        id: 'goOnlineColor_id',
                        fieldLabel: '上线',
                        labelWidth: 60,
                        anchor:'90%',
                        value:'#FFFFFF',
                        listeners : {
                            collapse: function (field,eOpts) {
                            	if(Ext.getCmp('goOnlineColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'onlineColor_id',
                        fieldLabel: '在线',
                        labelWidth: 60,
                        anchor:'90%',
                        value:'#FFFFFF',
                        listeners : {
                            collapse: function (field,eOpts) {
                            	if(Ext.getCmp('onlineColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'offlineColor_id',
                        fieldLabel: '离线',
                        labelWidth: 60,
                        anchor:'90%',
                        value:'#FFFFFF',
                        listeners : {
                            collapse: function (field,eOpts) {
                            	if(Ext.getCmp('offlineColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'runColor_id',
                        fieldLabel: '运行',
                        labelWidth: 60,
                        anchor:'90%',
                        value:'#FFFFFF',
                        listeners : {
                            collapse: function (field,eOpts) {
                            	if(Ext.getCmp('runColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'stopColor_id',
                        fieldLabel: '停抽',
                        labelWidth: 60,
                        anchor:'90%',
                        value:'#FFFFFF',
                        listeners : {
                            collapse: function (field,eOpts) {
                            	if(Ext.getCmp('stopColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'firstLevelColor_id',
                        fieldLabel: '一级报警',
                        labelWidth: 60,
                        anchor:'90%',
                        value:'#FFFFFF',
                        listeners : {
                            collapse: function (field,eOpts) {
                            	if(Ext.getCmp('firstLevelColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'secondLevelColor_id',
                        fieldLabel: '二级报警',
                        labelWidth: 60,
                        anchor:'90%',
                        value:'#FFFFFF',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('secondLevelColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'thirdLevelColor_id',
                        fieldLabel: '三级报警',
                        labelWidth: 60,
                        anchor:'90%',
                        value:'#FFFFFF',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('thirdLevelColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	},{
                        id: 'normalColor_id',
                        fieldLabel: '正常',
                        labelWidth: 60,
                        anchor:'90%',
                        value:'#FFFFFF',
                        listeners : {
                        	collapse: function (field,eOpts) {
                            	if(Ext.getCmp('normalColor_id')!=undefined){
                            		var opacity=field.color.a;
                            		field.setValue(field.value);
                    	        	var BackgroundColor0=field.color;
                    	        	BackgroundColor0.a=opacity;
                    	        	field.inputEl.applyStyles({
                    	        		background: '#'+field.value,
                    	        		opacity:opacity
                    	        	});
                    	        	field.setColor(BackgroundColor0);
                            	}
                            }
                        }
                	}]
                }]

            }]
        });
        me.callParent(arguments);
    }
});

function getAlarmLevelSetColor(){
	 Ext.Ajax.request({
	        url: context + '/alarmSetManagerController/getAlarmLevelColor',
	        method: "POST",
	        success: function (response) {
	        	var AlarmShowStyle = Ext.decode(response.responseText); // 获取返回数据
	        	
	        	//初始化概览数据报警颜色
	        	Ext.getCmp('goOnlineBackgroundColor_id').setValue(AlarmShowStyle.Comm.goOnline.BackgroundColor);
	        	var BackgroundColor0=Ext.getCmp('goOnlineBackgroundColor_id').color;
	        	BackgroundColor0.a=AlarmShowStyle.Comm.goOnline.Opacity;
	        	Ext.getCmp('goOnlineBackgroundColor_id').inputEl.applyStyles({
	        		background: '#'+AlarmShowStyle.Comm.goOnline.BackgroundColor,
	        		opacity:AlarmShowStyle.Comm.goOnline.Opacity
	        	});
	        	Ext.getCmp('goOnlineBackgroundColor_id').setColor(BackgroundColor0);
	        	
	        	Ext.getCmp('onlineBackgroundColor_id').setValue(AlarmShowStyle.Comm.online.BackgroundColor);
	        	var BackgroundColor0=Ext.getCmp('onlineBackgroundColor_id').color;
	        	BackgroundColor0.a=AlarmShowStyle.Comm.online.Opacity;
	        	Ext.getCmp('onlineBackgroundColor_id').inputEl.applyStyles({
	        		background: '#'+AlarmShowStyle.Comm.online.BackgroundColor,
	        		opacity:AlarmShowStyle.Comm.online.Opacity
	        	});
	        	Ext.getCmp('onlineBackgroundColor_id').setColor(BackgroundColor0);
	        	
	        	Ext.getCmp('offlineBackgroundColor_id').setValue(AlarmShowStyle.Comm.offline.BackgroundColor);
	        	var BackgroundColor0=Ext.getCmp('offlineBackgroundColor_id').color;
	        	BackgroundColor0.a=AlarmShowStyle.Comm.offline.Opacity;
	        	Ext.getCmp('offlineBackgroundColor_id').inputEl.applyStyles({
	        		background: '#'+AlarmShowStyle.Comm.offline.BackgroundColor,
	        		opacity:AlarmShowStyle.Comm.offline.Opacity
	        	});
	        	Ext.getCmp('offlineBackgroundColor_id').setColor(BackgroundColor0);
	        	
	        	
	        	Ext.getCmp('runBackgroundColor_id').setValue(AlarmShowStyle.Run.run.BackgroundColor);
	        	var BackgroundColor0=Ext.getCmp('runBackgroundColor_id').color;
	        	BackgroundColor0.a=AlarmShowStyle.Run.run.Opacity;
	        	Ext.getCmp('runBackgroundColor_id').inputEl.applyStyles({
	        		background: '#'+AlarmShowStyle.Run.run.BackgroundColor,
	        		opacity:AlarmShowStyle.Run.run.Opacity
	        	});
	        	Ext.getCmp('runBackgroundColor_id').setColor(BackgroundColor0);
	        	
	        	Ext.getCmp('stopBackgroundColor_id').setValue(AlarmShowStyle.Run.stop.BackgroundColor);
	        	var BackgroundColor0=Ext.getCmp('stopBackgroundColor_id').color;
	        	BackgroundColor0.a=AlarmShowStyle.Run.stop.Opacity;
	        	Ext.getCmp('stopBackgroundColor_id').inputEl.applyStyles({
	        		background: '#'+AlarmShowStyle.Run.stop.BackgroundColor,
	        		opacity:AlarmShowStyle.Run.stop.Opacity
	        	});
	        	Ext.getCmp('stopBackgroundColor_id').setColor(BackgroundColor0);
	        	
	        	
	        	Ext.getCmp('normalBackgroundColor_id').setValue(AlarmShowStyle.Data.Normal.BackgroundColor);
	        	var BackgroundColor0=Ext.getCmp('normalBackgroundColor_id').color;
	        	BackgroundColor0.a=AlarmShowStyle.Data.Normal.Opacity;
	        	Ext.getCmp('normalBackgroundColor_id').inputEl.applyStyles({
	        		background: '#'+AlarmShowStyle.Data.Normal.BackgroundColor,
	        		opacity:AlarmShowStyle.Data.Normal.Opacity
	        	});
	        	Ext.getCmp('normalBackgroundColor_id').setColor(BackgroundColor0);
	        	
	            Ext.getCmp('firstLevelBackgroundColor_id').setValue(AlarmShowStyle.Data.FirstLevel.BackgroundColor);
	            var BackgroundColor1=Ext.getCmp('firstLevelBackgroundColor_id').color;
	        	BackgroundColor1.a=AlarmShowStyle.Data.FirstLevel.Opacity;
	        	Ext.getCmp('firstLevelBackgroundColor_id').inputEl.applyStyles({
	        		background: '#'+AlarmShowStyle.Data.FirstLevel.BackgroundColor,
	        		opacity:AlarmShowStyle.Data.FirstLevel.Opacity
	        	});
	        	Ext.getCmp('firstLevelBackgroundColor_id').setColor(BackgroundColor1);
	            
	            
	            Ext.getCmp('secondLevelBackgroundColor_id').setValue(AlarmShowStyle.Data.SecondLevel.BackgroundColor);
	            var BackgroundColor2=Ext.getCmp('secondLevelBackgroundColor_id').color;
	        	BackgroundColor2.a=AlarmShowStyle.Data.SecondLevel.Opacity;
	        	Ext.getCmp('secondLevelBackgroundColor_id').inputEl.applyStyles({
	        		background: '#'+AlarmShowStyle.Data.SecondLevel.BackgroundColor,
	        		opacity:AlarmShowStyle.Data.SecondLevel.Opacity
	        	});
	        	Ext.getCmp('secondLevelBackgroundColor_id').setColor(BackgroundColor2);
	            
	            
	            Ext.getCmp('thirdLevelBackgroundColor_id').setValue(AlarmShowStyle.Data.ThirdLevel.BackgroundColor);
	            var BackgroundColor3=Ext.getCmp('thirdLevelBackgroundColor_id').color;
	        	BackgroundColor3.a=AlarmShowStyle.Data.ThirdLevel.Opacity;
	        	Ext.getCmp('thirdLevelBackgroundColor_id').inputEl.applyStyles({
	        		background: '#'+AlarmShowStyle.Data.ThirdLevel.BackgroundColor,
	        		opacity:AlarmShowStyle.Data.ThirdLevel.Opacity
	        	});
	        	Ext.getCmp('thirdLevelBackgroundColor_id').setColor(BackgroundColor3);
	            
	        	
	        	Ext.getCmp('goOnlineColor_id').setValue(AlarmShowStyle.Comm.goOnline.Color);
	            var Color0=Ext.getCmp('goOnlineColor_id').color;
	            Ext.getCmp('goOnlineColor_id').inputEl.applyStyles({
	            	background: '#'+AlarmShowStyle.Comm.goOnline.Color,
	            });

	        	Ext.getCmp('onlineColor_id').setValue(AlarmShowStyle.Comm.online.Color);
	            var Color0=Ext.getCmp('onlineColor_id').color;
	            Ext.getCmp('onlineColor_id').inputEl.applyStyles({
	            	background: '#'+AlarmShowStyle.Comm.online.Color,
	            });
	            
	            Ext.getCmp('offlineColor_id').setValue(AlarmShowStyle.Comm.offline.Color);
	            var Color0=Ext.getCmp('offlineColor_id').color;
	            Ext.getCmp('offlineColor_id').inputEl.applyStyles({
	            	background: '#'+AlarmShowStyle.Comm.offline.Color,
	            });
	            
	            Ext.getCmp('runColor_id').setValue(AlarmShowStyle.Run.run.Color);
	            var Color0=Ext.getCmp('runColor_id').color;
	            Ext.getCmp('runColor_id').inputEl.applyStyles({
	            	background: '#'+AlarmShowStyle.Run.run.Color,
	            });
	            
	            Ext.getCmp('stopColor_id').setValue(AlarmShowStyle.Run.stop.Color);
	            var Color0=Ext.getCmp('stopColor_id').color;
	            Ext.getCmp('stopColor_id').inputEl.applyStyles({
	            	background: '#'+AlarmShowStyle.Run.stop.Color,
	            });
	            
	            
	            Ext.getCmp('normalColor_id').setValue(AlarmShowStyle.Data.Normal.Color);
	            var Color0=Ext.getCmp('normalColor_id').color;
	            Ext.getCmp('normalColor_id').inputEl.applyStyles({
	            	background: '#'+AlarmShowStyle.Data.Normal.Color,
	            });
	            
	            Ext.getCmp('firstLevelColor_id').setValue(AlarmShowStyle.Data.FirstLevel.Color);
	            var Color1=Ext.getCmp('firstLevelColor_id').color;
	            Ext.getCmp('firstLevelColor_id').inputEl.applyStyles({
	            	background: '#'+AlarmShowStyle.Data.FirstLevel.Color,
	            });
	            
	            
	            Ext.getCmp('secondLevelColor_id').setValue(AlarmShowStyle.Data.SecondLevel.Color);
	            var Color2=Ext.getCmp('secondLevelColor_id').color;
	            Ext.getCmp('secondLevelColor_id').inputEl.applyStyles({
	            	background: '#'+AlarmShowStyle.Data.SecondLevel.Color,
	            });
	            
	            Ext.getCmp('thirdLevelColor_id').setValue(AlarmShowStyle.Data.ThirdLevel.Color);
	            var Color3=Ext.getCmp('thirdLevelColor_id').color;
	            Ext.getCmp('thirdLevelColor_id').inputEl.applyStyles({
	            	background: '#'+AlarmShowStyle.Data.ThirdLevel.Color,
	            });
	            
	            Ext.getCmp('goOnlineOpacity_id').setValue(AlarmShowStyle.Comm.goOnline.Opacity);
	            Ext.getCmp('onlineOpacity_id').setValue(AlarmShowStyle.Comm.online.Opacity);
	            Ext.getCmp('offlineOpacity_id').setValue(AlarmShowStyle.Comm.offline.Opacity);
	            Ext.getCmp('runOpacity_id').setValue(AlarmShowStyle.Run.run.Opacity);
	            Ext.getCmp('stopOpacity_id').setValue(AlarmShowStyle.Run.stop.Opacity);
	            Ext.getCmp('normalOpacity_id').setValue(AlarmShowStyle.Data.Normal.Opacity);
	            Ext.getCmp('firstLevelOpacity_id').setValue(AlarmShowStyle.Data.FirstLevel.Opacity);
	            Ext.getCmp('secondLevelOpacity_id').setValue(AlarmShowStyle.Data.SecondLevel.Opacity);
	            Ext.getCmp('thirdLevelOpacity_id').setValue(AlarmShowStyle.Data.ThirdLevel.Opacity);
	        },
	        failure: function () {
	            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
	        }
	    });
}

function saveAlarmColor(){
	var goOnlineBackgroundColor=Ext.getCmp('goOnlineBackgroundColor_id').getValue();
	var onlineBackgroundColor=Ext.getCmp('onlineBackgroundColor_id').getValue();
	var offlineBackgroundColor=Ext.getCmp('offlineBackgroundColor_id').getValue();
	var runBackgroundColor=Ext.getCmp('runBackgroundColor_id').getValue();
	var stopBackgroundColor=Ext.getCmp('stopBackgroundColor_id').getValue();
	var normalBackgroundColor=Ext.getCmp('normalBackgroundColor_id').getValue();
	var firstLevelBackgroundColor=Ext.getCmp('firstLevelBackgroundColor_id').getValue();
	var secondLevelBackgroundColor=Ext.getCmp('secondLevelBackgroundColor_id').getValue();
	var thirdLevelBackgroundColor=Ext.getCmp('thirdLevelBackgroundColor_id').getValue();
	 
	var goOnlineColor=Ext.getCmp('goOnlineColor_id').getValue();
	var onlineColor=Ext.getCmp('onlineColor_id').getValue();
	var offlineColor=Ext.getCmp('offlineColor_id').getValue();
	var runColor=Ext.getCmp('runColor_id').getValue();
	var stopColor=Ext.getCmp('stopColor_id').getValue();
	var normalColor=Ext.getCmp('normalColor_id').getValue();
	var firstLevelColor=Ext.getCmp('firstLevelColor_id').getValue();
	var secondLevelColor=Ext.getCmp('secondLevelColor_id').getValue();
	var thirdLevelColor=Ext.getCmp('thirdLevelColor_id').getValue();
	
	var goOnlineOpacity=Ext.getCmp('goOnlineOpacity_id').getValue();
	var onlineOpacity=Ext.getCmp('onlineOpacity_id').getValue();
	var offlineOpacity=Ext.getCmp('offlineOpacity_id').getValue();
	var runOpacity=Ext.getCmp('runOpacity_id').getValue();
	var stopOpacity=Ext.getCmp('stopOpacity_id').getValue();
	var normalOpacity=Ext.getCmp('normalOpacity_id').getValue();
	var firstLevelOpacity=Ext.getCmp('firstLevelOpacity_id').getValue();
	var secondLevelOpacity=Ext.getCmp('secondLevelOpacity_id').getValue();
	var thirdLevelOpacity=Ext.getCmp('thirdLevelOpacity_id').getValue();
	 
	 

	 var AlarmShowStyle={};
	 AlarmShowStyle.Data={};
	 AlarmShowStyle.Data.Normal={};
	 AlarmShowStyle.Data.Normal.Value=0;
	 AlarmShowStyle.Data.Normal.BackgroundColor=normalBackgroundColor;
	 AlarmShowStyle.Data.Normal.Color=normalColor;
	 AlarmShowStyle.Data.Normal.Opacity=normalOpacity;
	 AlarmShowStyle.Data.FirstLevel={};
	 AlarmShowStyle.Data.FirstLevel.Value=100;
	 AlarmShowStyle.Data.FirstLevel.BackgroundColor=firstLevelBackgroundColor;
	 AlarmShowStyle.Data.FirstLevel.Color=firstLevelColor;
	 AlarmShowStyle.Data.FirstLevel.Opacity=firstLevelOpacity;
	 AlarmShowStyle.Data.SecondLevel={};
	 AlarmShowStyle.Data.SecondLevel.Value=200;
	 AlarmShowStyle.Data.SecondLevel.BackgroundColor=secondLevelBackgroundColor;
	 AlarmShowStyle.Data.SecondLevel.Color=secondLevelColor;
	 AlarmShowStyle.Data.SecondLevel.Opacity=secondLevelOpacity;
	 AlarmShowStyle.Data.ThirdLevel={};
	 AlarmShowStyle.Data.ThirdLevel.Value=300;
	 AlarmShowStyle.Data.ThirdLevel.BackgroundColor=thirdLevelBackgroundColor;
	 AlarmShowStyle.Data.ThirdLevel.Color=thirdLevelColor;
	 AlarmShowStyle.Data.ThirdLevel.Opacity=thirdLevelOpacity;
	 
	 AlarmShowStyle.Comm={};
	 
	 AlarmShowStyle.Comm.goOnline={};
	 AlarmShowStyle.Comm.goOnline.Value=2;
	 AlarmShowStyle.Comm.goOnline.BackgroundColor=goOnlineBackgroundColor;
	 AlarmShowStyle.Comm.goOnline.Color=goOnlineColor;
	 AlarmShowStyle.Comm.goOnline.Opacity=goOnlineOpacity;
	 AlarmShowStyle.Comm.online={};
	 AlarmShowStyle.Comm.online.Value=1;
	 AlarmShowStyle.Comm.online.BackgroundColor=onlineBackgroundColor;
	 AlarmShowStyle.Comm.online.Color=onlineColor;
	 AlarmShowStyle.Comm.online.Opacity=onlineOpacity;
	 AlarmShowStyle.Comm.offline={};
	 AlarmShowStyle.Comm.offline.Value=0;
	 AlarmShowStyle.Comm.offline.BackgroundColor=offlineBackgroundColor;
	 AlarmShowStyle.Comm.offline.Color=offlineColor;
	 AlarmShowStyle.Comm.offline.Opacity=offlineOpacity;
	 
	 AlarmShowStyle.Run={};
	 AlarmShowStyle.Run.run={};
	 AlarmShowStyle.Run.run.Value=1;
	 AlarmShowStyle.Run.run.BackgroundColor=runBackgroundColor;
	 AlarmShowStyle.Run.run.Color=runColor;
	 AlarmShowStyle.Run.run.Opacity=runOpacity;
	 AlarmShowStyle.Run.stop={};
	 AlarmShowStyle.Run.stop.Value=0;
	 AlarmShowStyle.Run.stop.BackgroundColor=stopBackgroundColor;
	 AlarmShowStyle.Run.stop.Color=stopColor;
	 AlarmShowStyle.Run.stop.Opacity=stopOpacity;
	 Ext.Ajax.request({
	        url: context + '/alarmSetManagerController/setAlarmColor',
	        method: "POST",
	        params: {
	        	data: JSON.stringify(AlarmShowStyle)
	        },
	        success: function (response, action) {
	        	if (Ext.decode(response.responseText).msg) {
	        		Ext.getCmp("AlarmColorSelectWindow_Id").close();
	        		Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
	        	}else {
                   Ext.Msg.alert(cosog.string.ts,
                       "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
               }
	        },
	        failure: function () {
	            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
	        }
	    });
}