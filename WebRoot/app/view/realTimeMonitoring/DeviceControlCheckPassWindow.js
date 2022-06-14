Ext.define('AP.view.realTimeMonitoring.DeviceControlCheckPassWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.deviceControlCheckPassWindow',
    layout: 'fit',
    iframe: true,
    id: 'DeviceControlCheckPassWindow_Id',
    closeAction: 'destroy',
    width: 360,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    constrain: true,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        var controlTypeCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '设置项',
                    id: "DeviceControlValueCombo_Id",
                    labelWidth: 120,
                    labelAlign: 'left',
                    queryMode: 'local',
                    autoSelect: true,
                    hidden: true,
                    editable: false,
//                    allowBlank: false,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey"
                });
        var checkPassFrom = Ext.create('Ext.form.Panel', {
        	baseCls: 'x-plain',
            defaultType: 'textfield',
            id: "WellControlCheckPass_form_id",
            items: [{
                id: 'DeviceControlDeviceId_Id',//选择的设备Id
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'DeviceControlWellName_Id',//选择的设备
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'DeviceControlDeviceType_Id',//选择的设备类型
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'DeviceControlShowType_Id',//显示类型 0-不显示 1-输入框 2-下拉框
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'DeviceControlType_Id',//控制项
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'DeviceControlValue_Id',//控制值
                fieldLabel: '设置值',
                xtype: 'textfield',
                labelWidth: 120,
//              allowBlank: false,
                value: '',
                hidden: true
            },controlTypeCombo
//            ,{
//            	id: "checkPassFromPassword_id",
//                inputType: 'password',
//                fieldLabel: '请输入密码',
//                //vtype:"loginnum_",
//                allowBlank: false,
//                emptyText: '请输入密码',
//                labelWidth: 120,
//                msgTarget: 'side',
//                blankText: '请输入密码'
//            }
            ],
            buttons: [{
                xtype: 'button',
                id: 'checkPassFromSaveBtn_Id',
                text: '确定',
                hidden: false,
                iconCls: 'edit',
                handler:function () {
                	var form = Ext.getCmp("WellControlCheckPass_form_id");
                	if (form.getForm().isValid()) {
                		var controlValue=Ext.getCmp('DeviceControlValue_Id').getValue();
                		var controlShowType=Ext.getCmp("DeviceControlShowType_Id").getValue();
                		if(controlShowType==1){
                			controlValue=Ext.getCmp('DeviceControlValueCombo_Id').getValue();
                		}
                		form.getForm().submit({
                            url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
                            method: "POST",
                            waitMsg: cosog.string.updatewait,
                            waitTitle: 'Please Wait...',
                            params: {
                            	deviceId:Ext.getCmp('DeviceControlDeviceId_Id').getValue(),
                            	wellName: Ext.getCmp('DeviceControlWellName_Id').getValue(),
                            	deviceType: Ext.getCmp('DeviceControlDeviceType_Id').getValue(),
                                controlType:Ext.getCmp('DeviceControlType_Id').getValue(),
                                controlValue:controlValue
                            },
                            success: function (response, action) {
                            	if (action.result.flag == false) {
                            		Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                                    Ext.MessageBox.show({
                                        title: cosog.string.ts,
                                        msg: "<font color=red>" + cosog.string.sessionINvalid + "。</font>",
                                        icon: Ext.MessageBox.INFO,
                                        buttons: Ext.Msg.OK,
                                        fn: function () {
                                            window.location.href = context + "/login/toLogin";
                                        }
                                    });

                                } else if (action.result.flag == true && action.result.error == false) {
                                    Ext.Msg.alert(cosog.string.ts, "<font color=red>" + action.result.msg + "</font>");
                                }  else if (action.result.flag == true && action.result.error == true) {
                                	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                                    Ext.Msg.alert(cosog.string.ts, "<font color=red>" + action.result.msg + "</font>");
                                } 
                            },
                            failure: function () {
                            	Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font>】：" + cosog.string.contactadmin + "！")
                            }
                        });
                	}
                	
                }
         }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("DeviceControlCheckPassWindow_Id").close();
                }
         }]
        });
        Ext.apply(me, {
        	title: '操作',
            items: checkPassFrom
        })
        me.callParent(arguments);
    }
})


