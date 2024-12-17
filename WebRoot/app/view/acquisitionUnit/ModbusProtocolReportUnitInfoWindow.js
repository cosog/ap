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
                fieldLabel: loginUserLanguageResource.idx,
                id: 'formModbusProtocolReportUnit_Id',
                anchor: '100%',
                name: "reportUnit.id"
            },{
                id: 'formModbusProtocolReportUnitName_Id',
                name: "reportUnit.unitName",
                fieldLabel: loginUserLanguageResource.unitName+'<font color=red>*</font>',
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
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.reportUnitExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
                                    	    if (btn == 'ok'){
                                    	    	t.focus(true, 100);
                                    	    }
                                    	});
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, cosog.string.fail);
                                }
                            });
                        }
                    }
                }
            },{
				xtype : "hidden",
				id : 'modbusProtocolReportUnitCalculateType_Id',
				value: 0,
				name : "reportUnit.calculateType"
			},{
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.calculateType+'<font color=red>*</font>',
				id : 'modbusProtocolReportUnitCalculateTypeComb_Id',
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:0,
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [[0, loginUserLanguageResource.nothing],[1, loginUserLanguageResource.SRPCalculate],[2, loginUserLanguageResource.PCPCalculate]]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : loginUserLanguageResource.selectCalculateType,
				blankText : loginUserLanguageResource.selectCalculateType,
				listeners : {
					select:function(v,o){
						Ext.getCmp("modbusProtocolReportUnitCalculateType_Id").setValue(this.value);
						
					}
				}
            }, {
				xtype : "hidden",
				id : 'modbusUnitReportUnitCode_Id',
				value: '',
				name : "reportUnit.unitCode"
			},{
            	xtype: 'numberfield',
            	id: "modbusProtocolReportUnitSort_Id",
                name: 'reportUnit.sort',
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocolReportUnit_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolReportUnitSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocolReportUnit_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
//                	UpdatemodbusProtocolDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: loginUserLanguageResource.cancel,
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