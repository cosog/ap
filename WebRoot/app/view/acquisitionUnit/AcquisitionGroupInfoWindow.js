Ext.define("AP.view.acquisitionUnit.AcquisitionGroupInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.acquisitionGroupInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'acquisitionGroup_editWin_Id',
    closeAction: 'destroy',
    width: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        var modbusProtocolStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/acquisitionUnitManagerController/getModbusProtoclCombList',
				type : "ajax",
				actionMethods: {
                    read: 'POST'
                },
                reader: {
                	type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
			},
			autoLoad : true,
			listeners : {
				beforeload : function(store, options) {
					var new_params = {
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var modbusProtocolComb = Ext.create(
				'Ext.form.field.ComboBox', {
					fieldLabel :  '协议名称',
					id : 'formAcquisitionGroupProtocolComb_Id',
					anchor : '100%',
					store: modbusProtocolStore,
					queryMode : 'remote',
					typeAhead : true,
					autoSelect : false,
					allowBlank : false,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					listeners : {
						select: function (v,o) {
							Ext.getCmp("formAcquisitionGroupProtocol_Id").setValue(this.value);
	                    }
					}
				});
        var acqUnitStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/acquisitionUnitManagerController/getAcquisitionUnitCombList',
				type : "ajax",
				actionMethods: {
                    read: 'POST'
                },
                reader: {
                	type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
			},
			autoLoad : true,
			listeners : {
				beforeload : function(store, options) {
					var protocol=Ext.getCmp('formAcquisitionGroupProtocolComb_Id').getValue();
					var new_params = {
						protocol:protocol
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var acqUnitComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  '单元名称',
					id : 'formAcquisitionGroupAcqUnitComb_Id',
					anchor : '100%',
					store: acqUnitStore,
					queryMode : 'remote',
					typeAhead : true,
					autoSelect : false,
					allowBlank : false,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					listeners : {
						expand: function (sm, selections) {
							acqUnitComb.getStore().load();
		                },
						select: function (v,o) {
							Ext.getCmp("formAcquisitionGroupAcqUnit_Id").setValue(this.value);
	                    }
					}
				});
        
        var postAcquisitionGroupEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '序号',
                id: 'formAcquisitionGroupJlbh_Id',
                anchor: '100%',
                name: "acquisitionGroup.id"
            },{
				xtype : "hidden",
				id : 'formAcquisitionGroupProtocol_Id',
				value:'',
				name : "acquisitionGroup.protocol"
			},modbusProtocolComb,{
				xtype : "hidden",
				id : 'formAcquisitionGroupAcqUnit_Id',
				value:'',
				name : "acquisitionGroup.acqUnit"
			},acqUnitComb, {
                id: 'formAcquisitionGroupName_Id',
                name: "acquisitionGroup.groupName",
                fieldLabel: '组名称',
                anchor: '100%',
                value: ''
            }, {
                id: 'formAcquisitionGroupCode_Id',
                name: "acquisitionGroup.groupCode",
                fieldLabel: '组编码',
                hidden:true,
                anchor: '100%',
                value: ''
                
            }, {
                id: 'formAcquisitionGroupAcqCycle_Id',
                name: "acquisitionGroup.acqCycle",
                fieldLabel: '采集周期(s)',
                anchor: '100%',
                value: ''
                
            }, {
                id: 'formAcquisitionGroupSaveCycle_Id',
                name: "acquisitionGroup.saveCycle",
                fieldLabel: '保存周期(s)',
                anchor: '100%',
                value: ''
                
            }, {
            	id: 'acquisitionGroupRemark_Id',
            	name: "acquisitionGroup.remark",
                fieldLabel: '组描述',
                anchor: '100%',
                value: '',
                xtype: 'textareafield',
                
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormAcquisitionGroup_Id',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
                	SaveAcquisitionGroupSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaAquisitionGroup_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
                	UpdateAcquisitionGroupDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("acquisitionGroup_editWin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postAcquisitionGroupEditForm
        });
        me.callParent(arguments);
    }

});