Ext.define("AP.view.well.AuxiliaryDeviceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.auxiliaryDeviceInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'AuxiliaryDeviceInfoWindow_Id',
    closeAction: 'destroy',
    width: 330,
    constrain: true,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    layout: 'fit',
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        var auxiliaryDeviceEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addAuxiliaryDeviceForm_Id',
            defaultType: 'textfield',
            items: [{
                fieldLabel: loginUserLanguageResource.deviceName+'<font color=red>*</font>',
                id: 'auxiliaryDeviceName_Id',
                allowBlank: false,
                anchor: '95%',
                name: "auxiliaryDeviceInformation.name",
                listeners: {
                	blur: function (t, e) {
                        var name=t.value;
                        var type=Ext.getCmp("auxiliaryDeviceType_Id").getValue();
                        var manufacturer=Ext.getCmp("auxiliaryDeviceManufacturer_Id").getValue();
                        var model=Ext.getCmp("auxiliaryDeviceModel_Id").getValue();
                		if(name!='' && model!=''){
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	name: name,
                                	type:type,
                                	manufacturer:manufacturer,
                                	model:model
                                },
                                url: context + '/wellInformationManagerController/judgeAuxiliaryDeviceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.deviceExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
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
                xtype: "hidden",
                fieldLabel: '类型值',
                id: 'auxiliaryDeviceType_Id',
                value: '',
                name: "auxiliaryDeviceInformation.type"
            },{
                xtype: "textfield",
                fieldLabel: loginUserLanguageResource.manufacturer,
                allowBlank: true,
                id: 'auxiliaryDeviceManufacturer_Id',
                anchor: '95%',
                name: "auxiliaryDeviceInformation.manufacturer",
                value: '',
                listeners : {
                	blur: function (t, e) {
                        var name=Ext.getCmp("auxiliaryDeviceName_Id").getValue();
                        var type=Ext.getCmp("auxiliaryDeviceType_Id").getValue();
                        var model=Ext.getCmp("auxiliaryDeviceModel_Id").getValue();
                        var manufacturer=Ext.getCmp("auxiliaryDeviceManufacturer_Id").getValue();
                		if(name!='' && model!=''){
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	name: name,
                                	type:type,
                                	manufacturer: manufacturer,
                                	model:model
                                },
                                url: context + '/wellInformationManagerController/judgeAuxiliaryDeviceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.deviceExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
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
                xtype: "textfield",
                fieldLabel: loginUserLanguageResource.model,
                allowBlank: true,
                id: 'auxiliaryDeviceModel_Id',
                anchor: '95%',
                name: "auxiliaryDeviceInformation.model",
                value: '',
                listeners : {
                	blur: function (t, e) {
                        var name=Ext.getCmp("auxiliaryDeviceName_Id").getValue();
                        var type=Ext.getCmp("auxiliaryDeviceType_Id").getValue();
                        var manufacturer=Ext.getCmp("auxiliaryDeviceManufacturer_Id").getValue();
                        var model=Ext.getCmp("auxiliaryDeviceModel_Id").getValue();
                		if(name!='' && model!=''){
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	name: name,
                                	type:type,
                                	manufacturer: manufacturer,
                                	model:model
                                },
                                url: context + '/wellInformationManagerController/judgeAuxiliaryDeviceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.deviceExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
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
         		xtype: "textareafield",
         		fieldLabel: loginUserLanguageResource.remark,
         		id: 'auxiliaryRemark_Id',
         		anchor: '95%',
         		name: "auxiliaryDeviceInformation.remark",
         		value:''
            },{
            	xtype: 'numberfield',
            	id: "auxiliaryDeviceSort_Id",
            	name: "auxiliaryDeviceInformation.sort",
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: 1,
                anchor: '95%',
                msgTarget: 'side'
            }],
            buttons: [{
                id: 'addFormAuxiliaryDevice_Id',
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("AuxiliaryDeviceInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doAuxiliaryDeviceAdd',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: cosog.string.sendServer,
                            waitTitle: 'Please Wait...',
                            success: function (response, action) {
                                Ext.getCmp('AuxiliaryDeviceInfoWindow_Id').close();
                                CreateAndLoadAuxiliaryDeviceInfoTable();
                                if (action.result.msg == true) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                                }
                                if (action.result.msg == false) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.addFailure + "</font>");

                                }
                            },
                            failure: function () {
                                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
                            }
                        });
                    } else {
                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
                    }
                    return false;
                }
            }, {
                xtype: 'button',
                id: 'updateFormAuxiliaryDevice_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function (v, o) {
                	
                }
            }, {
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("AuxiliaryDeviceInfoWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: auxiliaryDeviceEditForm
        });
        me.callParent(arguments);
    }
});