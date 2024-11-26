Ext.define("AP.view.well.PumpingModelInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.pumpingModelInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'PumpingModelInfoWindow_Id',
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
        var pumpingModelEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addPumpingModelForm_Id',
            defaultType: 'textfield',
            items: [{
                fieldLabel: '厂家<font color=red>*</font>',
                labelWidth: 120,
                id: 'pumpingManufacturer_Id',
                allowBlank: false,
                anchor: '95%',
                name: "pumpingModelInformation.manufacturer",
                listeners: {
                	blur: function (t, e) {
                		var manufacturer=Ext.getCmp("pumpingManufacturer_Id").getValue();
                        var model=Ext.getCmp("pumpingModel_Id").getValue();
                		if(manufacturer!=''&&model!=''){
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	manufacturer: manufacturer,
                                	model:model
                                },
                                url: context + '/wellInformationManagerController/judgePumpingModelExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【该型号抽油机已存在】</font>,请确认！", function(btn, text){
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
                fieldLabel: '型号<font color=red>*</font>',
                labelWidth: 120,
                allowBlank: false,
                id: 'pumpingModel_Id',
                anchor: '95%',
                name: "pumpingModelInformation.model",
                value: '',
                listeners : {
                	blur: function (t, e) {
                		var manufacturer=Ext.getCmp("pumpingManufacturer_Id").getValue();
                        var model=Ext.getCmp("pumpingModel_Id").getValue();
                		if(manufacturer!=''&&model!=''){
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	manufacturer: manufacturer,
                                	model:model
                                },
                                url: context + '/wellInformationManagerController/judgePumpingModelExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【该型号抽油机已存在】</font>,请确认！", function(btn, text){
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
            }, {
         		xtype: "textfield",
         		fieldLabel: '冲程(m)<font color=red>*</font>',
         		labelWidth: 120,
         		id: 'pumpingStroke_Id',
         		allowBlank: false,
         		anchor: '95%',
         		name: "pumpingModelInformation.stroke",
         		value:''
            }, {
            	xtype : "combobox",
				fieldLabel : '曲柄旋转方向<font color=red>*</font>',
				labelWidth: 120,
				id : 'pumpingCrankRotationDirectionComb_Id',
				anchor : '95%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:'',
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [['Clockwise', '顺时针'],['Anticlockwise', '逆时针']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择曲柄旋转方向',
				blankText : '请选择曲柄旋转方向',
				listeners : {
					select:function(v,o){
						Ext.getCmp("pumpingStrokeCrankRotationDirection_Id").setValue(this.value);
					}
				}
            },{
                xtype: "hidden",
                fieldLabel: '曲柄旋转方向值',
                id: 'pumpingStrokeCrankRotationDirection_Id',
                value: '',
                name: "pumpingModelInformation.crankRotationDirection"
            }, {
         		xtype: "textfield",
         		fieldLabel: '曲柄偏置角(°)<font color=red>*</font>',
         		labelWidth: 120,
         		id: 'pumpingOffsetAngleOfCrank_Id',
         		allowBlank: false,
         		anchor: '95%',
         		name: "pumpingModelInformation.offsetAngleOfCrank",
         		value:''
            }, {
         		xtype: "textfield",
         		fieldLabel: '曲柄重心半径(m)<font color=red>*</font>',
         		labelWidth: 120,
         		id: 'pumpingCrankGravityRadius_Id',
         		allowBlank: false,
         		anchor: '95%',
         		name: "pumpingModelInformation.crankGravityRadius",
         		value:''
            }, {
         		xtype: "textfield",
         		fieldLabel: '单块曲柄重量(kN)<font color=red>*</font>',
         		labelWidth: 120,
         		id: 'pumpingSingleCrankWeight_Id',
         		allowBlank: false,
         		anchor: '95%',
         		name: "pumpingModelInformation.singleCrankWeight",
         		value:''
            }, {
         		xtype: "textfield",
         		fieldLabel: '单块曲柄销重量(kN)<font color=red>*</font>',
         		labelWidth: 120,
         		id: 'pumpingSingleCrankPinWeight_Id',
         		allowBlank: false,
         		anchor: '95%',
         		name: "pumpingModelInformation.singleCrankPinWeight",
         		value:''
            },{
         		xtype: "textfield",
         		fieldLabel: '结构不平衡重(kN)<font color=red>*</font>',
         		labelWidth: 120,
         		id: 'pumpingStructuralUnbalance_Id',
         		allowBlank: false,
         		anchor: '95%',
         		name: "pumpingModelInformation.structuralUnbalance",
         		value:''
            }, {
         		xtype: "textfield",
         		fieldLabel: '平衡块重量(kN)<font color=red>*</font>',
         		labelWidth: 120,
         		id: 'pumpingBalanceWeight_Id',
         		allowBlank: false,
         		anchor: '95%',
         		name: "pumpingModelInformation.balanceWeight",
         		value:''
            }],
            buttons: [{
                id: 'addFormPumpingModel_Id',
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("PumpingModelInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doPumpingModelAdd',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: cosog.string.sendServer,
                            waitTitle: 'Please Wait...',
                            success: function (response, action) {
                                Ext.getCmp('PumpingModelInfoWindow_Id').close();
                                CreateAndLoadPumpingModelInfoTable();
                                if (action.result.msg == true) {
                                    Ext.Msg.alert(cosog.string.ts, "<font color=blue>" + cosog.string.success + "</font>");
                                }
                                if (action.result.msg == false) {
                                    Ext.Msg.alert(cosog.string.ts, "<font color=red>" + cosog.string.failInfo + "</font>");

                                }
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
            }, {
                xtype: 'button',
                id: 'updateFormPumpingModel_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function (v, o) {
                	
                }
            }, {
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("PumpingModelInfoWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: pumpingModelEditForm
        });
        me.callParent(arguments);
    }
});