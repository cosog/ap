Ext.define("AP.view.acquisitionUnit.ModbusProtocolInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.modbusProtocolInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'modbusProtocol_editWin_Id',
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
        var postModbusProtocolEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '序号',
                id: 'formModbusProtocol_Id',
                anchor: '100%',
                name: "modbusProtocol.id"
            },{
				xtype : "hidden",
				id : 'modbusProtocolDeviceType_Id',
				value:'0',
				name : "modbusProtocol.deviceType"
			},{
            	xtype : "combobox",
				fieldLabel : '设备类型<font color=red>*</font>',
				id : 'modbusProtocolDeviceTypeComb_Id',
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:0,
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [[0, '井设备']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择设备类型',
				blankText : '请选择设备类型',
				listeners : {
					select:function(v,o){
						Ext.getCmp("modbusProtocolDeviceType_Id").setValue(this.value);
					}
				}
            }, {
                id: 'formModbusProtocolName_Id',
                name: "modbusProtocol.name",
                fieldLabel: '协议名称<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        if(value_!=''){
                        	var deviceType=Ext.getCmp("modbusProtocolDeviceType_Id").getValue();
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	deviceType:deviceType,
                                	protocolName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeProtocolExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【协议已存在】</font>,请确认！", function(btn, text){
                                    	    if (btn == 'ok'){
                                    	    	t.focus(true, 100);
                                    	    }
                                    	});
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                                }
                            });
                        }
                    }
                }
            }, {
            	xtype: 'numberfield',
            	id: "modbusProtocolSort_Id",
                name: 'modbusProtocol.sort',
                fieldLabel: '排序',
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocol_Id',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocol_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
//                	UpdatemodbusProtocolDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("modbusProtocol_editWin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postModbusProtocolEditForm
        });
        me.callParent(arguments);
    }

});