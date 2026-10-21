Ext.define("AP.view.sceneModel.SceneModelInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.sceneModelInfoWindow',
    id: 'sceneModelInfoWindow_Id',
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
        var labelWidth=getLabelWidth(loginUserLanguageResource.name+'*',loginUserLanguage);
        if(labelWidth<getLabelWidth(loginUserLanguageResource.sequenceNumber,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.sequenceNumber,loginUserLanguage);
        }

        var deviceEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addSceneModelForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: 'id',
                labelWidth: labelWidth,
                id: 'addSceneModel_Id',
                value: '',
                name: "sceneModel.id"
            },{
                fieldLabel: loginUserLanguageResource.name+'<font color=red>*</font>',
                labelWidth: labelWidth,
                id: 'addSceneModelName_zh_CN_Id',
                allowBlank:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                anchor: '95%',
                name: "sceneModel.name_zh_CN",
                listeners: {
                	
                }
            },{
                fieldLabel: loginUserLanguageResource.name+'<font color=red>*</font>',
                labelWidth: labelWidth,
                id: 'addSceneModelName_en_Id',
                allowBlank:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                anchor: '95%',
                name: "sceneModel.name_en",
                listeners: {
                	
                }
            },{
                fieldLabel: loginUserLanguageResource.name+'<font color=red>*</font>',
                labelWidth: labelWidth,
                id: 'addSceneModelName_ru_Id',
                allowBlank:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                anchor: '95%',
                name: "sceneModel.name_ru",
                listeners: {
                	
                }
            },{
            	xtype: "hidden",
                id: 'addSceneModelOrg_Id',
                name: "sceneModel.orgId"
            },{
            	xtype: "hidden",
                id: 'addSceneModelDevicetype_Id',
                value:'1',
                name: "sceneModel.devicetype"
            },{
            	xtype: 'numberfield',
            	id: "addSceneModelSortNum_Id",
            	name: "sceneModel.sort",
                fieldLabel: loginUserLanguageResource.sequenceNumber,
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
                	var sceneModelInfoWindowForm = Ext.getCmp("sceneModelInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
                    if (sceneModelInfoWindowForm.getForm().isValid()) {
                        sceneModelInfoWindowForm.getForm().submit({
                            url: context + '/sceneModelController/addSceneModel',
                            clientValidation: false, // 进行客户端验证
                            method: "POST",
                            waitMsg: loginUserLanguageResource.submittingData,
                            waitTitle: loginUserLanguageResource.wait,
                            success: function (response, action) {
                            	if(loginUserLanguage.toUpperCase()=='ZH_CN'){
                            		Ext.getCmp('addSceneModelSelectName_Id').setValue(Ext.getCmp('addSceneModelName_zh_CN_Id').getValue());
                            	}else if(loginUserLanguage.toUpperCase()=='EN'){
                            		Ext.getCmp('addSceneModelSelectName_Id').setValue(Ext.getCmp('addSceneModelName_en_Id').getValue());
                            	}else if(loginUserLanguage.toUpperCase()=='RU'){
                            		Ext.getCmp('addSceneModelSelectName_Id').setValue(Ext.getCmp('addSceneModelName_ru_Id').getValue());
                            	}
                            	
                                Ext.getCmp('sceneModelInfoWindow_Id').close();
                                Ext.getCmp("SceneModelGridView_Id").getStore().load();
                                if (action.result.msg == true) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addedSuccessfully + "</font>");
                                }
                                if (action.result.msg == false) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.addFailure);

                                }
                            },
                            failure: function () {
                                Ext.Msg.alert(loginUserLanguageResource.tip, "[<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>]:" + loginUserLanguageResource.contactAdmin);
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
                    Ext.getCmp("sceneModelInfoWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: deviceEditForm
        });
        me.callParent(arguments);
    }
});