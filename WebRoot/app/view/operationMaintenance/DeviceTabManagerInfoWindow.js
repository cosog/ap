Ext.define("AP.view.operationMaintenance.DeviceTabManagerInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.deviceTabManagerInfoWindow',
    id: 'deviceTabManagerInfoWindow_Id',
    layout: 'fit',
    iframe: true,
    closeAction: 'destroy',
    width: 400,
    shadow: 'sides',
    resizable: true,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        
        
        var labelWidth=getLabelWidth(loginUserLanguageResource.name,loginUserLanguage);
        if(labelWidth<getLabelWidth(loginUserLanguageResource.name,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.name,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.calculateType,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.calculateType,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.sortNum,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.sortNum,loginUserLanguage);
        }
        
        

        var deviceEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addDeviceTabManagerInstanceForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: 'id',
                labelWidth: labelWidth,
                id: 'addDeviceTabManagerInstance_Id',
                value: '',
                name: "deviceTabManager.id"
            },{
                fieldLabel: loginUserLanguageResource.name+'<font color=red>*</font>',
                labelWidth: labelWidth,
                id: 'addDeviceTabManagerInstanceName_Id',
                allowBlank: false,
                anchor: '95%',
                name: "deviceTabManager.name",
                listeners: {
                	
                }
            },{
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.calculateType,
				labelWidth: labelWidth,
				id : 'addDeviceTabManagerInstanceCalculateTypeComb_Id',
				anchor : '95%',
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
						Ext.getCmp("addDeviceTabManagerInstanceCalculateType_Id").setValue(this.value);
						
					}
				}
            },{
            	xtype: "hidden",
                fieldLabel: loginUserLanguageResource.calculateType,
                labelWidth: labelWidth,
                id: 'addDeviceTabManagerInstanceCalculateType_Id',
                name: "deviceTabManager.calculateType"
            },{
            	xtype: 'numberfield',
            	id: "addDeviceTabManagerInstanceSortNum_Id",
            	name: "deviceTabManager.sort",
                fieldLabel: loginUserLanguageResource.sortNum,
                labelWidth: labelWidth,
                allowBlank: true,
                minValue: 1,
                anchor: '95%',
                msgTarget: 'side'
            }],
            buttons: [{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                	var functionTabInfoWindowForm = Ext.getCmp("deviceTabManagerInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
                    if (functionTabInfoWindowForm.getForm().isValid()) {
                        functionTabInfoWindowForm.getForm().submit({
                            url: context + '/operationMaintenanceController/addDeviceTabManagerInstance',
                            clientValidation: false, // 进行客户端验证
                            method: "POST",
                            waitMsg: loginUserLanguageResource.sendServer,
                            waitTitle: loginUserLanguageResource.wait,
                            success: function (response, action) {
                            	Ext.getCmp('addDeviceTabManagerInstanceSelectName_Id').setValue(Ext.getCmp('addDeviceTabManagerInstanceName_Id').getValue());
                                Ext.getCmp('deviceTabManagerInfoWindow_Id').close();
                                Ext.getCmp("operationMaintenanceDeviceTabManagerGridView_Id").getStore().load();
                                if (action.result.msg == true) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                                }
                                if (action.result.msg == false) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.addFailure);

                                }
                            },
                            failure: function () {
                                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
                            }
                        });
                    } else {
                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
                    }
                    // 设置返回值 false : 让Extjs4 自动回调 success函数
                    return false;
                }
            },{
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("deviceTabManagerInfoWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: deviceEditForm
        });
        me.callParent(arguments);
    }
});