Ext.define("AP.view.acquisitionUnit.AlarmUnitInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.alarmUnitInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'alarmUnit_editWin_Id',
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
					var deviceTypeIds='';
		        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
		        	if(tabTreeGridPanelSelection.length>0){
		        		deviceTypeIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
		        	}
					var new_params = {
							deviceTypeIds: deviceTypeIds
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var modbusProtocolComb = Ext.create(
				'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.protocolName+'<font color=red>*</font>',
					id : 'formAlarmUnitProtocolComb_Id',
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
							Ext.getCmp("formAlarmUnitProtocol_Id").setValue(this.value);
	                    }
					}
				});
        
        var postalarmUnitEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.idx,
                id: 'formAlarmUnit_Id',
                anchor: '100%',
                name: "alarmUnit.id"
            },{
				xtype : "hidden",
				id : 'formAlarmUnitProtocol_Id',
				value:'',
				name : "alarmUnit.protocol"
			},modbusProtocolComb, {
                id: 'formAlarmUnitName_Id',
                name: "alarmUnit.unitName",
                fieldLabel: loginUserLanguageResource.unitName+'<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        if(value_!=''){
                        	var protocolName=Ext.getCmp("formAlarmUnitProtocol_Id").getValue();
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	protocolName:protocolName,
                                	unitName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeAlarmUnitExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.alarmUnitExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
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
            }, {
                id: 'formAlarmUnitCode_Id',
                name: "alarmUnit.unitCode",
                fieldLabel: '单元编码',
                hidden:true,
                anchor: '100%',
                value: ''
                
            }, {
            	id: 'alarmUnitRemark_Id',
            	name: "alarmUnit.remark",
                fieldLabel: loginUserLanguageResource.unitDescription,
                anchor: '100%',
                value: '',
                xtype: 'textareafield',
                
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormAlarmUnit_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	SaveAlarmUnitSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaAquisitionUnit_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
                	UpdateAlarmUnitDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("alarmUnit_editWin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postalarmUnitEditForm
        });
        me.callParent(arguments);
    }

});