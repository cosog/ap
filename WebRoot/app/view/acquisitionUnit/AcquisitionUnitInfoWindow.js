Ext.define("AP.view.acquisitionUnit.AcquisitionUnitInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.acquisitionUnitInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'acquisitionUnit_editWin_Id',
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
					id : 'formAcquisitionUnitProtocolComb_Id',
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
							Ext.getCmp("formAcquisitionUnitProtocol_Id").setValue(this.value);
	                    }
					}
				});
        
        var postacquisitionUnitEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '序号',
                id: 'formAcquisitionUnit_Id',
                anchor: '100%',
                name: "acquisitionUnit.id"
            },{
				xtype : "hidden",
				id : 'formAcquisitionUnitProtocol_Id',
				value:'',
				name : "acquisitionUnit.protocol"
			},modbusProtocolComb, {
                id: 'formAcquisitionUnitName_Id',
                name: "acquisitionUnit.unitName",
                fieldLabel: '单元名称',
                allowBlank: false,
                anchor: '100%',
                value: ''
            }, {
                id: 'formAcquisitionUnitCode_Id',
                name: "acquisitionUnit.unitCode",
                fieldLabel: '类型编码',
                hidden:true,
                anchor: '100%',
                value: ''
                
            }, {
            	id: 'acquisitionUnitRemark_Id',
            	name: "acquisitionUnit.remark",
                fieldLabel: '类型描述',
                anchor: '100%',
                value: '',
                xtype: 'textareafield',
                
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormAcquisitionUnit_Id',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
                	SaveAcquisitionUnitSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaAquisitionUnit_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
                	UpdateAcquisitionUnitDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("acquisitionUnit_editWin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postacquisitionUnitEditForm
        });
        me.callParent(arguments);
    }

});