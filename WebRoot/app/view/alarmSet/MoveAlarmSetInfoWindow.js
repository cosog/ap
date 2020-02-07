Ext.define("AP.view.alarmSet.MoveAlarmSetInfoWindow", {
			extend : 'Ext.window.Window',
			alias : 'widget.moveAlarmSetInfoWindow',
			id : 'MoveAlarmSetInfoWindowwin_Id',
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
				var bdbjlxStore_A = new Ext.data.SimpleStore(
								{
									fields : [ {
										name : "boxkey",
										type : "string"
									}, {
										name : "boxval",
										type : "string"
									} ],
									proxy : {
										url : context+ '/moveAlarmSetManagerController/loadMoveAlarmType',
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
												type : 'bdbjlx'
											};
											Ext.apply(store.proxy.extraParams,
													new_params);
										}
									}
								});

						// Simple ComboBox using the data store
						var bdbjlxCombo_A = Ext.create(
								'Ext.form.field.ComboBox', {
									fieldLabel :cosog.string.bjlx,
									id : 'bdbjlx_Id',
									name : "move.bdbjlx",
									anchor : '95%',
									store: bdbjlxStore_A,
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
											//gklxCombo_A.clearValue();
											//gklxCombo_A.getStore().load(); // 
										}
									}
								});
						var bdbjjbStore_B = new Ext.data.SimpleStore(
								{
									fields : [ {
										name : "boxkey",
										type : "string"
									}, {
										name : "boxval",
										type : "string"
									} ],
									proxy : {
										url : context+ '/moveAlarmSetManagerController/loadMoveAlarmType',
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
												type : 'bdbjjb'
											};
											Ext.apply(store.proxy.extraParams,
													new_params);
										}
									}
								});

						// Simple ComboBox using the data store
						var bdbjjbCombo_B = Ext.create(
								'Ext.form.field.ComboBox', {
									fieldLabel : cosog.string.bjjb,
									id : 'bdbjjb_Id',
									name : "move.bdbjjb",
									anchor : '95%',
									store: bdbjjbStore_B,
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
											//gklxCombo_A.clearValue();
											//gklxCombo_A.getStore().load(); // 
										}
									}
								});
								var ssgldwStore_d = new Ext.data.SimpleStore(
								{
									fields : [ {
										name : "boxkey",
										type : "string"
									}, {
										name : "boxval",
										type : "string"
									} ],
									proxy : {
										url : context+ '/moveAlarmSetManagerController/loadMoveAlarmType',
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
												type : 'bdssgldw'
											};
											Ext.apply(store.proxy.extraParams,
													new_params);
										}
									}
								});

						// Simple ComboBox using the data store
						var ssgldwCombo_d = Ext.create(
								'Ext.form.field.ComboBox', {
									fieldLabel :cosog.string.ssgldy,
									id : 'bdssgldw_Id',
									name : "move.ssgldw",
									anchor : '95%',
									store: ssgldwStore_d,
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
											//bjjbCombo_C.clearValue();
											//bjjbCombo_C.getStore().load(); // 
										}
							            
									}
								});
				var postEditForm = Ext.create('Ext.form.Panel', {
							baseCls : 'x-plain',
							defaultType : 'textfield',
							items : [{
										xtype : "hidden",
										id : 'jlbh_Id',
										name : "move.jlbh"
									}, {
										fieldLabel :cosog.string.bdmc,
										id : 'bdmc_Id',
										anchor : '95%',
										name : "move.bdmc"
									}, {
										fieldLabel : cosog.string.bdfw,
										id : 'bdfw_Id',
										anchor : '95%',
										name : "move.bdfw"
									},bdbjlxCombo_A,bdbjjbCombo_B ,{
										xtype : "combobox",
										fieldLabel : cosog.string.bjzt,
										name : 'move.bjbz',
										id : 'bjbz_Id',
										value:'0',
										triggerAction : 'all',
										selectOnFocus : true,
										anchor : '95%',
										forceSelection : true,
										allowBlank : false,
										store : new Ext.data.SimpleStore({
													fields : ['value', 'text'],
													data : [['1', '开'],['0', '关']]
												}),
										displayField : 'text',
										valueField : 'value',
										mode : 'local',
										emptyText : cosog.string.chooseType,
										blankText : cosog.string.chooseType,
										listeners : {
										}
									},ssgldwCombo_d, {
										fieldLabel : cosog.string.bz,
										id : 'bz_Id',
									    anchor : '95%',
										name : "move.bz"
									}],
							buttons : [{
										id : 'addFormMoveAlarmSet_Id',
										xtype : 'button',
										text : cosog.string.save,
										hidden:true,
										iconCls : 'save'
									}, {
										xtype : 'button',
										id : 'updateFormMoveAlarmSet_Id',
										text :cosog.string.update,
										width : 40,
										iconCls : 'edit',
										hidden : true,
										handler : UpdateMoveAlarmSetSubmitBtnForm
									}, {
										text :cosog.string.cancel,
										width : 40,
										iconCls : 'cancel',
										handler : function() {
											Ext.getCmp("MoveAlarmSetInfoWindowwin_Id").close();
										}
									}]
						});

				Ext.applyIf(me, {
							// title : "修改用户信息",
							items : postEditForm
						});
				me.callParent(arguments);
			}

		});