Ext.define("AP.view.acquisitionUnit.ModbusProtocolSMSInstanceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.modbusProtocolSMSInstanceInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'modbusProtocolSMSInstanceInfoWindow_Id',
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
        var postModbusProtocolSMSInstanceEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.idx,
                id: 'formModbusprotocolSMSInstance_Id',
                anchor: '100%',
                name: "protocolSMSInstance.id"
            }, {
                id: 'formModbusprotocolSMSInstanceName_Id',
                name: "protocolSMSInstance.name",
                fieldLabel: loginUserLanguageResource.instanceName+'<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: ''
            }, {
                id: 'formModbusprotocolSMSInstanceCode_Id',
                name: "protocolSMSInstance.code",
                fieldLabel: '实例代码',
                hidden: true,
                allowBlank: true,
                anchor: '100%',
                value: ''
            },{
				xtype : "hidden",
				id : 'modbusSMSInstanceAcqProtocolType_Id',
				value:'modbus-tcp',
				name : "protocolSMSInstance.acqProtocolType"
			},{
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.acqProtocolType+'<font color=red>*</font>',
				id : 'modbusInstanceAcqProtocolTypeComb_Id',
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : true,
			    forceSelection : true,
			    value:'modbus-tcp',
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [['private-lq1000', 'private-lq1000']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : loginUserLanguageResource.selectProtocolType+'...',
				blankText : loginUserLanguageResource.selectProtocolType+'...',
				listeners : {
					select:function(v,o){
						Ext.getCmp("modbusSMSInstanceAcqProtocolType_Id").setValue(this.value);
					}
				}
            },{
				xtype : "hidden",
				id : 'modbusSMSInstanceCtrlProtocolType_Id',
				value:'modbus-tcp',
				name : "protocolSMSInstance.ctrlProtocolType"
			},{
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.ctrlProtocolType+'<font color=red>*</font>',
				id : 'modbusInstanceCtrlProtocolTypeComb_Id',
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : true,
			    forceSelection : true,
			    value:'modbus-tcp',
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [['private-lq1000', 'private-lq1000']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : loginUserLanguageResource.selectProtocolType+'...',
				blankText : loginUserLanguageResource.selectProtocolType+'...',
				listeners : {
					select:function(v,o){
						Ext.getCmp("modbusSMSInstanceCtrlProtocolType_Id").setValue(this.value);
					}
				}
            },{
            	xtype: 'numberfield',
            	id: "modbusProtocolSMSInstanceSort_Id",
                name: 'protocolSMSInstance.sort',
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusprotocolSMSInstance_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolSMSInstanceSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusprotocolSMSInstance_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
                	UpdateModbusProtocolSMSInstanceSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("modbusProtocolSMSInstanceInfoWindow_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postModbusProtocolSMSInstanceEditForm
        });
        me.callParent(arguments);
    }

});