Ext.define("AP.view.well.SMSDeviceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.smsDeviceInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'SMSDeviceInfoWindow_Id',
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
        /**短信实例*/
        var smsInstanceStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/wellInformationManagerController/getSMSInstanceCombList',
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
        
        var smsDeviceSMSInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.SMSInstance,
					emptyText : '请选择短信实例',
					blankText : '请选择短信实例',
					id : 'smsDeviceSMSInstanceComb_Id',
					anchor : '95%',
					store: smsInstanceStore,
					queryMode : 'remote',
					typeAhead : true,
					autoSelect : false,
					allowBlank : true,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					listeners : {
						select: function (v,o) {
							if(o.data.boxkey==''){
								v.setValue('');
								v.setRawValue(' ');
							}
							Ext.getCmp("smsDeviceAcqInstanceCode_Id").setValue(this.value);
	                    }
					}
				});
        var smsDeviceEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addSMSDeviceForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: 'label',
                id: 'smsDeviceWinOgLabel_Id',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: '单位编号',
                id: 'smsDeviceOrg_Id',
                value: '',
                name: "smsDeviceInformation.orgId"
            },{
                fieldLabel: loginUserLanguageResource.deviceName+'<font color=red>*</font>',
                id: 'smsDeviceName_Id',
                allowBlank: false,
                anchor: '95%',
                name: "smsDeviceInformation.deviceName"
            },smsDeviceSMSInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '短信实例编码',
                id: 'smsDeviceAcqInstanceCode_Id',
                value: '',
                name: "smsDeviceInformation.instanceCode"
            },{
                xtype: "textfield",
                fieldLabel: loginUserLanguageResource.signInId,
                allowBlank: true,
                id: 'smsDeviceSignInId_Id',
                anchor: '95%',
                name: "smsDeviceInformation.signInId",
                value: ''
            },{
            	xtype: 'numberfield',
            	id: "smsDeviceSortNum_Id",
            	name: "smsDeviceInformation.sortNum",
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: 1,
                anchor: '95%',
                msgTarget: 'side'
            }],
            buttons: [{
                id: 'addFormSMSDevice_Id',
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("SMSDeviceInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doSMSDeviceAdd',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: loginUserLanguageResource.sendServer,
                            waitTitle: loginUserLanguageResource.wait,
                            success: function (response, action) {
                                Ext.getCmp('SMSDeviceInfoWindow_Id').close();
                                CreateAndLoadSMSDeviceInfoTable();
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
                id: 'updateFormSMSDevice_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function (v, o) {
                	
                }
            }, {
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("SMSDeviceInfoWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: smsDeviceEditForm
        });
        me.callParent(arguments);
    }
});