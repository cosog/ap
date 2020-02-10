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
				var workingConditionStore = new Ext.data.SimpleStore({
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
												type : 'gklx'
											};
											Ext.apply(store.proxy.extraParams,new_params);
										}
									}
								});

						// Simple ComboBox using the data store
						var workingConditionComb = Ext.create(
								'Ext.form.field.ComboBox', {
									fieldLabel :  '报警项',
									id : 'alarmSetWorkingConditionComb_Id',
									name : "workingconditioncode",
									anchor : '95%',
									store: workingConditionStore,
									queryMode : 'local',
									emptyText :  cosog.string.all,
									blankText :  cosog.string.all,
									typeAhead : true,
									autoSelect : false,
									allowBlank : true,
									triggerAction : 'all',
									editable : true,
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
												type : 'bjlx'
											};
											Ext.apply(store.proxy.extraParams,new_params);
										}
									}
								});

						// Simple ComboBox using the data store
						var alarmTypeComb = Ext.create(
								'Ext.form.field.ComboBox', {
									fieldLabel :  cosog.string.bjlx,
									id : 'alarmSetAlarmType_Id',
									name : "alarmtype",
									anchor : '95%',
									store: alarmTypeStore,
									queryMode : 'local',
									emptyText :  cosog.string.all,
									blankText :  cosog.string.all,
									typeAhead : true,
									autoSelect : false,
									allowBlank : true,
									triggerAction : 'all',
									editable : true,
									displayField : "boxval",
									valueField : "boxkey",
									listeners : {
										expand : function(sm, selections) {
										}
									}
								});
								var bjjbStore_C = new Ext.data.SimpleStore(
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
												type : 'bjjb'
											};
											Ext.apply(store.proxy.extraParams,new_params);
										}
									}
								});

						// Simple ComboBox using the data store
						var bjjbCombo_C = Ext.create(
								'Ext.form.field.ComboBox', {
									fieldLabel :  cosog.string.bjjb,
									id : 'bjjb_Id',
									name : "alarmlevel",
									anchor : '95%',
									store: bjjbStore_C,
									queryMode : 'local',
									emptyText :  cosog.string.all,
									blankText :  cosog.string.all,
									typeAhead : true,
									autoSelect : false,
									allowBlank : true,
									triggerAction : 'all',
									editable : true,
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
										id : 'jlbh_Id',
										name : "id"
									},{
										xtype : "hidden",
										id : 'bjbz_Id',
										value:'0',
										name : "alarmsign"
									},alarmTypeComb,workingConditionComb,bjjbCombo_C ,{
										xtype : "combobox",
										fieldLabel : cosog.string.bjzt,
										id : 'bjbz_Id1',
										anchor : '95%',
										triggerAction : 'all',
										selectOnFocus : true,
									    forceSelection : true,
									    value:'0',
										allowBlank : true,
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
											  Ext.getCmp("bjbz_Id").setValue(this.value);
											}
										}
									}, {
										fieldLabel : cosog.string.bz,
										id : 'bz_Id',
										anchor : '95%',
										//vtype : 'email',
										name : "remark"
									}],
							buttons : [{
										id : 'addFormAlarmSet_Id',
										xtype : 'button',
										text : cosog.string.save,
										hidden:true,
										iconCls : 'save'
									}, {
										xtype : 'button',
										id : 'updateFormAlarm_Id',
										text : cosog.string.update,
										width : 40,
										iconCls : 'edit',
										hidden : true,
										handler : UpdateAlarmSetSubmitBtnForm
									}, {
										text : cosog.string.cancel,
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