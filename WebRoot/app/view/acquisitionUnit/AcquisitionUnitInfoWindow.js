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
                fieldLabel: loginUserLanguageResource.idx,
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
                fieldLabel: loginUserLanguageResource.unitName+'<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        if(value_!=''){
                        	var protocolName=Ext.getCmp("formAcquisitionUnitProtocol_Id").getValue();
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	protocolName:protocolName,
                                	unitName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeAcqUnitExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.acqUnitExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
                                    	    if (btn == 'ok'){
                                    	    	t.focus(true, 100);
                                    	    }
                                    	});
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
                                }
                            });
                        }
                    }
                }
            }, {
                id: 'formAcquisitionUnitCode_Id',
                name: "acquisitionUnit.unitCode",
                fieldLabel: '单元编码',
                hidden:true,
                anchor: '100%',
                value: ''
            },{
            	xtype: 'numberfield',
            	id: "acquisitionUnitSort_Id",
                name: 'acquisitionUnit.sort',
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }, {
            	id: 'acquisitionUnitRemark_Id',
            	name: "acquisitionUnit.remark",
                fieldLabel: loginUserLanguageResource.unitDescription,
                anchor: '100%',
                value: '',
                xtype: 'textareafield'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormAcquisitionUnit_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	SaveAcquisitionUnitSubmitBtnForm();
                }
            }, {
                xtype: 'button',
                id: 'updateFormaAquisitionUnit_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
                	UpdateAcquisitionUnitDataInfoSubmitBtnForm();
                }
            }, {
        	 	xtype: 'button',   
        	 	text: loginUserLanguageResource.cancel,
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