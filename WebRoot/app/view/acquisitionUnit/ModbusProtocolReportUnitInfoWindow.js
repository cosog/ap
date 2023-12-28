Ext.define("AP.view.acquisitionUnit.ModbusProtocolReportUnitInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.modbusProtocolReportUnitInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'modbusProtocolReportUnitInfoWindow_Id',
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
                id: 'formModbusProtocolReportUnit_Id',
                anchor: '100%',
                name: "reportUnit.id"
            },
//            {
//				xtype : "hidden",
//				id : 'modbusProtocolReportUnitDeviceType_Id',
//				value: 0,
//				name : "reportUnit.deviceType"
//			},{
//            	xtype : "combobox",
//				fieldLabel : '设备类型<font color=red>*</font>',
//				id : 'modbusProtocolReportUnitDeviceTypeComb_Id',
//				anchor : '100%',
//				triggerAction : 'all',
//				selectOnFocus : false,
//			    forceSelection : true,
//			    value:0,
//			    allowBlank: false,
//				editable : false,
//				store : new Ext.data.SimpleStore({
//							fields : ['value', 'text'],
//							data : [[0, '抽油机井'],[1, '螺杆泵井']]
//						}),
//				displayField : 'text',
//				valueField : 'value',
//				queryMode : 'local',
//				emptyText : '请选择设备类型',
//				blankText : '请选择设备类型',
//				listeners : {
//					select:function(v,o){
//						Ext.getCmp("modbusProtocolReportUnitDeviceType_Id").setValue(this.value);
//						Ext.getCmp("modbusProtocolReportUnitTemplateComb_Id").setValue("");
//						Ext.getCmp("modbusProtocolReportUnitTemplateComb_Id").setRawValue("");
//						
//					}
//				}
//            }, 
            {
                id: 'formModbusProtocolReportUnitName_Id',
                name: "reportUnit.unitName",
                fieldLabel: '单元名称<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        if(value_!=''){
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	unitName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeReportUnitExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【报表单元已存在】</font>,请确认！", function(btn, text){
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
				id : 'modbusUnitReportUnitCode_Id',
				value: '',
				name : "reportUnit.unitCode"
			},{
            	xtype: 'numberfield',
            	id: "modbusProtocolReportUnitSort_Id",
                name: 'reportUnit.sort',
                fieldLabel: '排序',
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocolReportUnit_Id',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolReportUnitSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocolReportUnit_Id',
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
                    Ext.getCmp("modbusProtocolReportUnitInfoWindow_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postModbusProtocolEditForm
        });
        me.callParent(arguments);
    }

});