Ext.define("AP.view.well.VideoKeyAddWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.videoKeyAddWindow',
    id: 'VideoKeyAddWindow_Id',
    layout: 'fit',
    iframe: true,
    closeAction: 'destroy',
    width: 400,
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
        var videoKeyEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addVideoKeyForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: 'label',
                id: 'videoKeyWinOrgLabel_Id',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: '单位编号',
                labelWidth: 60,
                id: 'videoKeyOrg_Id',
                value: '',
                name: "videoKey.orgId"
            },{
                fieldLabel: '名称<font color=red>*</font>',
                labelWidth: 60,
                id: 'videoKeyAccount_Id',
                allowBlank: false,
                anchor: '95%',
                name: "videoKey.account",
                listeners: {
                	blur: function (t, e) {
                        if(t.value!=''){
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	account: t.value
                                },
                                url: context + '/wellInformationManagerController/judgeVideoKeyExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【该名称已存在:"+t.value+"】</font>,请确认！", function(btn, text){
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
                xtype: "textfield",
                fieldLabel: 'appKey<font color=red>*</font>',
                labelWidth: 60,
                allowBlank: false,
                id: 'videoKeyAppKey_Id',
                anchor: '95%',
                name: "videoKey.appkey",
                value: ''
            },{
                xtype: "textfield",
                fieldLabel: 'secret<font color=red>*</font>',
                labelWidth: 60,
                allowBlank: false,
                id: 'videoKeySecret_Id',
                anchor: '95%',
                name: "videoKey.secret",
                value: ''
            }],
            buttons: [{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("VideoKeyAddWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doVideoKeyAdd',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: cosog.string.sendServer,
                            waitTitle: 'Please Wait...',
                            success: function (response, action) {
                                Ext.getCmp('VideoKeyAddWindow_Id').close();
                                CreateDeviceKeyDataTable();
                                
                                Ext.Msg.alert(cosog.string.ts, "<font color=blue>" + cosog.string.success + "</font>");
                            },
                            failure: function () {
                                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font> 】：" + cosog.string.contactadmin + "！");
                            }
                        });
                    } else {
                        Ext.Msg.alert(cosog.string.ts, "<font color=red>*为必填项，请检查数据有效性.</font>");
                    }
                    return false;
                }
            },{
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("VideoKeyAddWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: videoKeyEditForm
        });
        me.callParent(arguments);
    }
});