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
        var reportUnitStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/acquisitionUnitManagerController/getReportUnitCombList',
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
        
        var reportUnitComb = Ext.create(
				'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.reportUnit+'<font color=red>*</font>',
					id : 'modbusProtocolReportInstanceTemplateComb_Id',
					anchor : '100%',
					store: reportUnitStore,
					queryMode : 'remote',
					typeAhead : true,
					autoSelect : false,
					allowBlank : false,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					emptyText: loginUserLanguageResource.selectReportUnit+'...',
		            blankText: loginUserLanguageResource.selectReportUnit+'...',
					listeners : {
						expand: function (sm, selections) {
							reportUnitComb.getStore().load();
		                },select: function (v,o) {
							Ext.getCmp("modbusInstanceReportUnit_Id").setValue(this.value);
	                    }
					}
				});
        
        
        
        var postModbusProtocolEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.idx,
                id: 'formModbusProtocolReportInstance_Id',
                anchor: '100%',
                name: "protocolReportInstance.id"
            },{
                id: 'formModbusProtocolReportInstanceName_Id',
                name: "protocolReportInstance.name",
                fieldLabel: loginUserLanguageResource.instanceName+'<font color=red>*</font>',
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
                                	instanceName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeReportInstanceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.reportInstanceExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
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
				id : 'modbusInstanceReportUnit_Id',
				value: 0,
				name : "protocolReportInstance.unitId"
			},reportUnitComb,{
            	xtype: 'numberfield',
            	id: "modbusProtocolReportInstanceSort_Id",
                name: 'protocolReportInstance.sort',
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocolReportInstance_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolReportInstanceSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocolReportInstance_Id',
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