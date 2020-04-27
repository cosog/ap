Ext.define('AP.view.diagnosis.WellControlCheckPassWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.wellControlCheckPassWindow',
    layout: 'fit',
    iframe: true,
    id: 'WellControlCheckPassWindow_Id',
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
                    id: "ProductionWellControlValueCombo_Id",
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
                id: 'ProductionWellControlWellName_Id',//选择的井名
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'ProductionWellControlShowType_Id',//显示类型 0-不显示 1-输入框 2-下拉框
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                id: 'ProductionWellControlType_Id',//控制项
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'ProductionWellControlValue_Id',//控制值
                fieldLabel: '设置值',
                xtype: 'textfield',
                labelWidth: 120,
//              allowBlank: false,
                value: '',
                hidden: true
            },controlTypeCombo,{
            	id: "checkPassFromPassword_id",
                inputType: 'password',
                fieldLabel: '请输入密码',
                //vtype:"loginnum_",
                allowBlank: false,
                emptyText: '请输入密码',
                labelWidth: 120,
                msgTarget: 'side',
                blankText: '请输入密码'
            }],
            buttons: [{
                xtype: 'button',
                id: 'checkPassFromSaveBtn_Id',
                text: '确定',
                hidden: false,
                iconCls: 'edit',
                handler:function () {
                	var form = Ext.getCmp("WellControlCheckPass_form_id");
                	if (form.getForm().isValid()) {
                		var controlValue=Ext.getCmp('ProductionWellControlValue_Id').getValue();
                		var controlShowType=Ext.getCmp("ProductionWellControlShowType_Id").getValue();
                		if(controlShowType==2){
                			controlValue=Ext.getCmp('ProductionWellControlValueCombo_Id').getValue();
                		}
                		form.getForm().submit({
                            url: context + '/diagnosisAnalysisOnlyController/wellControlOperation',
                            method: "POST",
                            waitMsg: cosog.string.updatewait,
                            waitTitle: 'Please Wait...',
                            params: {
                            	wellName: Ext.getCmp('ProductionWellControlWellName_Id').getValue(),
                                password: Ext.getCmp('checkPassFromPassword_id').getValue(),
                                controlType:Ext.getCmp('ProductionWellControlType_Id').getValue(),
                                controlValue:controlValue
                            },
                            success: function (response, action) {
                            	if (action.result.flag == false) {
                            		Ext.getCmp("WellControlCheckPassWindow_Id").close();
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
                                	Ext.getCmp("WellControlCheckPassWindow_Id").close();
                                    Ext.Msg.alert(cosog.string.ts, "<font color=red>" + action.result.msg + "</font>");
                                    
                                    var tabPanel = Ext.getCmp("ProductionWellRealtimeAnalisisPanel");
                            		var activeId = tabPanel.getActiveTab().id;
                            		var gridPanelId="DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id";
                            		if(activeId=="pumpUnitRealtimeAnalysisPanel_Id"){
                            			Ext.getCmp("DiagnosisControlDataGridPanel_Id").getStore().commitChanges();
                            		}else if(activeId=="screwPumpRealtimeAnalysisPanel_Id"){
                            			Ext.getCmp("ScrewPumpRTControlDataGridPanel_Id").getStore().commitChanges(); 
                            		}
                                } 
                            },
                            failure: function () {
                            	Ext.getCmp("WellControlCheckPassWindow_Id").close();
                                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font>】：" + cosog.string.contactadmin + "！")
                            }
                        });
                	}
                	
                }
         }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("WellControlCheckPassWindow_Id").close();
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


