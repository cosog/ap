Ext.define("AP.view.acquisitionUnit.ModbusProtocolReportInstanceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.modbusProtocolReportInstanceInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'modbusProtocolReportInstanceInfoWindow_Id',
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
        var reportTemplateStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/acquisitionUnitManagerController/getReportTemplateCombList',
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
					var deviceType=Ext.getCmp("modbusProtocolReportInstanceDeviceTypeComb_Id").getValue();
					var new_params = {
							deviceType:deviceType
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var reportTemplateComb = Ext.create(
				'Ext.form.field.ComboBox', {
					fieldLabel :  '报表单元<font color=red>*</font>',
					id : 'modbusProtocolReportInstanceTemplateComb_Id',
					anchor : '100%',
					store: reportTemplateStore,
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
							reportTemplateComb.getStore().load();
		                },select: function (v,o) {
							Ext.getCmp("modbusInstanceReportUnitCode_Id").setValue(this.value);
	                    }
					}
				});
        
        
        
        var postModbusProtocolEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '序号',
                id: 'formModbusProtocolReportInstance_Id',
                anchor: '100%',
                name: "protocolReportInstance.id"
            },{
				xtype : "hidden",
				id : 'modbusProtocolReportInstanceDeviceType_Id',
				value: 0,
				name : "protocolReportInstance.deviceType"
			},{
            	xtype : "combobox",
				fieldLabel : '设备类型<font color=red>*</font>',
				id : 'modbusProtocolReportInstanceDeviceTypeComb_Id',
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:0,
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [[0, '抽油机井'],[1, '螺杆泵井']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择设备类型',
				blankText : '请选择设备类型',
				listeners : {
					select:function(v,o){
						Ext.getCmp("modbusProtocolReportInstanceDeviceType_Id").setValue(this.value);
						Ext.getCmp("modbusProtocolReportInstanceTemplateComb_Id").setValue("");
						Ext.getCmp("modbusProtocolReportInstanceTemplateComb_Id").setRawValue("");
						
					}
				}
            }, {
                id: 'formModbusProtocolReportInstanceName_Id',
                name: "protocolReportInstance.name",
                fieldLabel: '实例名称<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        if(value_!=''){
                        	var deviceType=Ext.getCmp("modbusProtocolReportInstanceDeviceType_Id").getValue();
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	deviceType:deviceType,
                                	instanceName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeReportInstanceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【报表实例已存在】</font>,请确认！", function(btn, text){
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
            },{
				xtype : "hidden",
				id : 'modbusInstanceReportUnitCode_Id',
				value: 0,
				name : "protocolReportInstance.unitCode"
			},reportTemplateComb,{
            	xtype: 'numberfield',
            	id: "modbusProtocolReportInstanceSort_Id",
                name: 'protocolReportInstance.sort',
                fieldLabel: '排序',
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocolReportInstance_Id',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolReportInstanceSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocolReportInstance_Id',
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
                    Ext.getCmp("modbusProtocolReportInstanceInfoWindow_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postModbusProtocolEditForm
        });
        me.callParent(arguments);
    }

});