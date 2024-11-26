Ext.define("AP.view.alarmSet.AlarmSetInfoWindow", {
			extend : 'Ext.window.Window',
			alias : 'widget.alarmSetInfoWindow',
			id : 'AlarmSetInfoWindowwin_Id',
			closeAction : 'destroy',
			width : 330,
			shadow : 'sides',
			resizable : true,
			collapsible : true,
			maximizable : true,
			layout : 'fit',
			plain : true,
			bodyStyle : 'padding:5px;background-color:#D9E5F3;',
			modal : true,
			border : false,
			initComponent : function() {
				var me = this;
				var resultStore = new Ext.data.SimpleStore({
									fields : [ {
										name : "boxkey",
										type : "string"
									}, {
										name : "boxval",
										type : "string"
									} ],
									proxy : {
										url : context+ '/alarmSetManagerController/loadAlarmType',
										type : "ajax",
										actionMethods: {
						                    read: 'POST'
						                },
						                reader: {
						                    type: 'json'
						                }
									},
									autoLoad : true,
									listeners : {
										beforeload : function(store, options) {
											var new_params = {
												type : 'result'
											};
											Ext.apply(store.proxy.extraParams,new_params);
										}
									}
								});

						// Simple ComboBox using the data store
						var resultComb = Ext.create(
								'Ext.form.field.ComboBox', {
									fieldLabel :  '报警项',
									id : 'alarmSetResultCodeComb_Id',
									name : "resultcode",
									anchor : '95%',
									store: resultStore,
									queryMode : 'local',
									emptyText :  '--'+loginUserLanguageResource.all+'--',
									blankText :  '--'+loginUserLanguageResource.all+'--',
									typeAhead : true,
									autoSelect : false,
									allowBlank : true,
									triggerAction : 'all',
									editable : false,
									displayField : "boxval",
									valueField : "boxkey",
									listeners : {
										select: function () {
					                    }
									}
								});
						var alarmTypeStore = new Ext.data.SimpleStore(
								{
									fields : [ {
										name : "boxkey",
										type : "string"
									}, {
										name : "boxval",
										type : "string"
									} ],
									proxy : {
										url : context+ '/alarmSetManagerController/loadAlarmType',
										type : "ajax",
										actionMethods: {
						                    read: 'POST'
						                },
						                reader: {
						                    type: 'json'
						                }
									},
									autoLoad : true,
									listeners : {
										beforeload : function(store, options) {
											var new_params = {
												type : 'alarmType'
											};
											Ext.apply(store.proxy.extraParams,new_params);
										}
									}
								});

						// Simple ComboBox using the data store
						var alarmTypeComb = Ext.create(
								'Ext.form.field.ComboBox', {
									fieldLabel :  cosog.string.alarmType,
									id : 'alarmSetAlarmType_Id',
									name : "alarmtype",
									anchor : '95%',
									store: alarmTypeStore,
									queryMode : 'local',
									emptyText :  '--'+loginUserLanguageResource.all+'--',
									blankText :  '--'+loginUserLanguageResource.all+'--',
									typeAhead : true,
									autoSelect : false,
									allowBlank : true,
									triggerAction : 'all',
									editable : false,
									displayField : "boxval",
									valueField : "boxkey",
									listeners : {
										expand : function(sm, selections) {
										}
									}
								});
								var alarmLevelStore = new Ext.data.SimpleStore(
								{
									fields : [ {
										name : "boxkey",
										type : "string"
									}, {
										name : "boxval",
										type : "string"
									} ],
									proxy : {
										url : context+ '/alarmSetManagerController/loadAlarmType',
										type : "ajax",
										actionMethods: {
						                    read: 'POST'
						                },
						                reader: {
						                    type: 'json'
						                }
									},
									autoLoad : true,
									listeners : {
										beforeload : function(store, options) {
											var new_params = {
												type : 'alarmLevel'
											};
											Ext.apply(store.proxy.extraParams,new_params);
										}
									}
								});

						// Simple ComboBox using the data store
						var alarmLevelComb = Ext.create(
								'Ext.form.field.ComboBox', {
									fieldLabel :  cosog.string.alarmLevel,
									id : 'alarmSetAlarmLevel_Id',
									name : "alarmlevel",
									anchor : '95%',
									store: alarmLevelStore,
									queryMode : 'local',
									emptyText :  '--'+loginUserLanguageResource.all+'--',
									blankText :  '--'+loginUserLanguageResource.all+'--',
									typeAhead : true,
									autoSelect : false,
									allowBlank : true,
									triggerAction : 'all',
									editable : false,
									displayField : "boxval",
									valueField : "boxkey",
									listeners : {
										expand : function(sm, selections) {
										}
							            
									}
								});
				var postEditForm = Ext.create('Ext.form.Panel', {
							baseCls : 'x-plain',
							defaultType : 'textfield',
							items : [{
										xtype : "hidden",
										id : 'alarmSetRecord_Id',
										name : "id"
									},{
										xtype : "hidden",
										id : 'alarmSetAlarmSign_Id',
										value:'0',
										name : "alarmsign"
									},alarmTypeComb,resultComb,alarmLevelComb ,{
										xtype : "combobox",
										fieldLabel : '报警开关',
										id : 'alarmSetAlarmSignComb_Id',
										anchor : '95%',
										triggerAction : 'all',
										selectOnFocus : true,
									    forceSelection : true,
									    value:'0',
										allowBlank : true,
										editable : false,
										store : new Ext.data.SimpleStore({
													fields : ['value', 'text'],
													data : [['1', '开'],['0', '关']]
												}),
										displayField : 'text',
										valueField : 'value',
										queryMode : 'local',
										emptyText : cosog.string.chooseType,
										blankText : cosog.string.chooseType,
										listeners : {
											select:function(v,o){
											  Ext.getCmp("alarmSetAlarmSign_Id").setValue(this.value);
											}
										}
									}, {
										fieldLabel : cosog.string.remark,
										id : 'alarmSetRemark_Id',
										anchor : '95%',
										//vtype : 'email',
										name : "remark"
									}],
							buttons : [{
										id : 'addFormAlarmSet_Id',
										xtype : 'button',
										text : loginUserLanguageResource.save,
										hidden:true,
										iconCls : 'save'
									}, {
										xtype : 'button',
										id : 'updateFormAlarm_Id',
										text : loginUserLanguageResource.update,
										width : 40,
										iconCls : 'edit',
										hidden : true,
										handler : UpdateAlarmSetSubmitBtnForm
									}, {
										text : loginUserLanguageResource.cancel,
										width : 40,
										iconCls : 'cancel',
										handler : function() {
											Ext.getCmp("AlarmSetInfoWindowwin_Id").close();
										}
									}]
						});

				Ext.applyIf(me, {
							items : postEditForm
						});
				me.callParent(arguments);
			}

		});