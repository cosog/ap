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
        var languageComboxStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/userManagerController/loadLanguageList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json'
                }
            },
            autoLoad: true
        });

        var languageCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: loginUserLanguageResource.language+'<font color=red>*</font>',
                id: 'protocolLanguage_Id1',
                anchor: '100%',
                value: '',
                store: languageComboxStore,
                emptyText: '--'+loginUserLanguageResource.selectLanguage+'--',
                blankText: '--'+loginUserLanguageResource.selectLanguage+'--',
                typeAhead: true,
                allowBlank: false,
                editable:false,
                disabled:true,
                readOnly:true,
                blankText: loginUserLanguageResource.required,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
//                        Ext.getCmp("protocolLanguage_Id").setValue(this.value);
                    }
                }
            });
        var postModbusProtocolEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
            	xtype: 'label',
            	id: 'protocolWinTabLabel_Id',
            	hidden:true,
            	html: ''
            },{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.idx,
                id: 'formModbusProtocol_Id',
                anchor: '100%',
                name: "protocolModel.id"
            },{
				xtype : "hidden",
				id : 'modbusProtocolDeviceType_Id',
				value:'0',
				name : "protocolModel.deviceType"
			},{
                id: 'formModbusProtocolName_Id',
                name: "protocolModel.name",
                fieldLabel: loginUserLanguageResource.protocolName+'<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        var deviceType=Ext.getCmp("modbusProtocolDeviceType_Id").getValue();
                        if(value_!=''){
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	deviceType: deviceType,
                                	protocolName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeProtocolExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.protocolExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
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
            },languageCombox, {
            	xtype: 'numberfield',
            	id: "modbusProtocolSort_Id",
                name: 'protocolModel.sort',
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocol_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocol_Id',
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