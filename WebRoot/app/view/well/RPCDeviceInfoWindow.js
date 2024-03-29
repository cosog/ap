Ext.define("AP.view.well.RPCDeviceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.rpcDeviceInfoWindow',
    id: 'RPCDeviceInfoWindow_Id',
    layout: 'fit',
    iframe: true,
    closeAction: 'destroy',
    width: 300,
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
        /**采控实例*/
        var acqInstanceStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/wellInformationManagerController/getAcqInstanceCombList',
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
							deviceType: 101
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var rpcDeviceAcqInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  '采控实例',
					emptyText : '请选择采控实例',
					blankText : '请选择采控实例',
					id : 'rpcDeviceAcqInstanceComb_Id',
					anchor : '95%',
					store: acqInstanceStore,
					queryMode : 'remote',
					hidden: !IoTConfig,
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
							Ext.getCmp("rpcDeviceAcqInstanceCode_Id").setValue(this.value);
	                    }
					}
				});
        
        /**显示实例*/
        var displayInstanceStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/wellInformationManagerController/getDisplayInstanceCombList',
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
							deviceType: 101
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var rpcDeviceDisplayInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  '显示实例',
					emptyText : '请选择显示实例',
					blankText : '请选择显示实例',
					id : 'rpcDeviceDisplayInstanceComb_Id',
					anchor : '95%',
					store: displayInstanceStore,
					queryMode : 'remote',
					hidden: !IoTConfig,
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
							Ext.getCmp("rpcDeviceDisplayInstanceCode_Id").setValue(this.value);
	                    }
					}
				});
        
        /**报表实例*/
        var reportInstanceStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/wellInformationManagerController/getReportInstanceCombList',
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
							deviceType: 101
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var rpcDeviceReportInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  '报表实例',
					emptyText : '请选择报表实例',
					blankText : '请选择报表实例',
					id : 'rpcDeviceReportInstanceComb_Id',
					anchor : '95%',
					store: reportInstanceStore,
					queryMode : 'remote',
					hidden: !IoTConfig,
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
							Ext.getCmp("rpcDeviceReportInstanceCode_Id").setValue(this.value);
	                    }
					}
				});
        
        /**报警实例*/
        var alarmInstanceStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/wellInformationManagerController/getAlarmInstanceCombList',
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
							deviceType: 101
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var rpcDeviceAlarmInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  '报警实例',
					emptyText : '请选择报警实例',
					blankText : '请选择报警实例',
					id : 'rpcDeviceAlarmInstanceComb_Id',
					anchor : '95%',
					store: alarmInstanceStore,
					queryMode : 'remote',
					hidden: !IoTConfig,
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
							Ext.getCmp("rpcDeviceAlarmInstanceCode_Id").setValue(this.value);
	                    }
					}
				});

        var rpcDeviceEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addRPCDeviceForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: 'label',
                id: 'rpcDeviceWinOgLabel_Id',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备编号',
                id: 'rpcDevice_Id',
                value: '',
                name: "rpcDeviceInformation.id"
            },{
                xtype: "hidden",
                fieldLabel: '单位编号',
                id: 'rpcDeviceOrg_Id',
                value: '',
                name: "rpcDeviceInformation.orgId"
            },{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'rpcDeviceType_Id',
                value: '',
                name: "rpcDeviceInformation.deviceType"
            }, 
//            orgTreePicker, 
            {
                fieldLabel: '井名<font color=red>*</font>',
                id: 'rpcDeviceName_Id',
                allowBlank: false,
                anchor: '95%',
                name: "rpcDeviceInformation.wellName",
                listeners: {
                	blur: function (t, e) {
                        if(t.value!=''){
                        	var orgId=Ext.getCmp("rpcDeviceOrg_Id").getValue();
                        	var deviceType=Ext.getCmp("rpcDeviceType_Id").getValue();
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	orgId:orgId,
                                	deviceName: t.value,
                                    deviceType:deviceType
                                },
                                url: context + '/wellInformationManagerController/judgeDeviceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【该组织下已存在设备:"+t.value+"】</font>,请确认！", function(btn, text){
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
            	xtype : "combobox",
				fieldLabel : '应用场景<font color=red>*</font>',
				id : 'rpcDeviceApplicationScenariosComb_Id',
				anchor : '95%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:'',
			    allowBlank: sceneConfig!='all',
			    hidden: sceneConfig!='all',
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [[0, '煤层气井'],[1, '油井']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择应用场景',
				blankText : '请选择应用场景',
				listeners : {
					select:function(v,o){
						Ext.getCmp("rpcDeviceApplicationScenarios_Id").setValue(this.value);
					}
				}
            },{
                xtype: "hidden",
                fieldLabel: '应用场景值',
                id: 'rpcDeviceApplicationScenarios_Id',
                value: sceneConfig=='cbm'?0:1,
                name: "rpcDeviceInformation.applicationScenarios"
            },rpcDeviceAcqInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '采控实例编码',
                id: 'rpcDeviceAcqInstanceCode_Id',
                value: '',
                name: "rpcDeviceInformation.instanceCode"
            },rpcDeviceDisplayInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '显示实例编码',
                id: 'rpcDeviceDisplayInstanceCode_Id',
                value: '',
                name: "rpcDeviceInformation.displayInstanceCode"
            },rpcDeviceReportInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '报表实例编码',
                id: 'rpcDeviceReportInstanceCode_Id',
                value: '',
                name: "rpcDeviceInformation.reportInstanceCode"
            },rpcDeviceAlarmInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '报警实例编码',
                id: 'rpcDeviceAlarmInstanceCode_Id',
                value: '',
                name: "rpcDeviceInformation.alarmInstanceCode"
            }, {
            	xtype : "combobox",
				fieldLabel : '下位机TCP类型',
				id : 'rpcDeviceTcpTypeComb_Id',
				anchor : '95%',
				triggerAction : 'all',
				hidden: !IoTConfig,
				selectOnFocus : false,
			    forceSelection : true,
			    value:'',
			    allowBlank: true,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [['TCP Server', 'TCP Server'],['TCP Client', 'TCP Client']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择下位机TCP类型',
				blankText : '请选择下位机TCP类型',
				listeners : {
					select:function(v,o){
						Ext.getCmp("rpcDeviceTcpType_Id").setValue(this.value);
						if(this.value=='TCP Server'){
							Ext.getCmp('rpcDeviceSignInId_Id').disable();
							Ext.getCmp('rpcDeviceIpPort_Id').enable();
						}else{
							Ext.getCmp('rpcDeviceSignInId_Id').enable();
							Ext.getCmp('rpcDeviceIpPort_Id').disable();
						}
					}
				}
            },{
                xtype: "hidden",
                fieldLabel: '下位机TCP类型值',
                id: 'rpcDeviceTcpType_Id',
                value: '',
                name: "rpcDeviceInformation.tcpType"
            },{
                xtype: "textfield",
                fieldLabel: '注册包ID',
                allowBlank: true,
                id: 'rpcDeviceSignInId_Id',
                anchor: '95%',
                name: "rpcDeviceInformation.signInId",
                value: '',
                hidden: !IoTConfig,
                listeners: {
                	blur: function (t, e) {
                        var slave=Ext.getCmp("rpcDeviceSlave_Id").getValue();
                        var deviceType=Ext.getCmp("rpcDeviceType_Id").getValue();
                		if(t.value!=''&&slave!=''){
                        	var orgId=Ext.getCmp("rpcDeviceOrg_Id").getValue();
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	signinId: t.value,
                                	slave:slave,
                                    deviceType:deviceType
                                },
                                url: context + '/wellInformationManagerController/judgeDeviceExistOrNotBySigninIdAndSlave',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【注册包ID/IP端口和设备从地址与其他设备冲突】</font>,请确认！", function(btn, text){
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
                fieldLabel: '下位机IP端口',
                allowBlank: true,
                hidden: !IoTConfig,
                id: 'rpcDeviceIpPort_Id',
                anchor: '95%',
                name: "rpcDeviceInformation.ipPort",
                value: '',
                regex: /^((\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*)|((\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])))\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
                regexText: '您输入格式不正确',
                listeners: {
                	blur: function (t, e) {
                        var slave=Ext.getCmp("rpcDeviceSlave_Id").getValue();
                        var deviceType=Ext.getCmp("rpcDeviceType_Id").getValue();
                		if(t.value!=''&&slave!=''){
                        	var orgId=Ext.getCmp("rpcDeviceOrg_Id").getValue();
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	ipPort: t.value,
                                	slave:slave,
                                    deviceType:deviceType
                                },
                                url: context + '/wellInformationManagerController/judgeDeviceExistOrNotByIpPortAndSlave',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【下位机IP端口和设备从地址与其他设备冲突】</font>,请确认！", function(btn, text){
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
         		fieldLabel: '设备从地址',
         		id: 'rpcDeviceSlave_Id',
         		anchor: '95%',
         		name: "rpcDeviceInformation.slave",
         		value:'01',
         		hidden: !IoTConfig,
         		listeners: {
                	blur: function (t, e) {
                        var signinId=Ext.getCmp("rpcDeviceSignInId_Id").getValue();
                        var deviceType=Ext.getCmp("rpcDeviceType_Id").getValue();
                		if(signinId!=''&&t.value!=''){
                        	var orgId=Ext.getCmp("rpcDeviceOrg_Id").getValue();
                    		Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	signinId: signinId,
                                	slave:t.value,
                                    deviceType:deviceType
                                },
                                url: context + '/wellInformationManagerController/judgeDeviceExistOrNotBySigninIdAndSlave',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【注册包ID/IP端口和设备从地址与其他设备冲突】</font>,请确认！", function(btn, text){
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
            	xtype: 'numberfield',
            	id: "rpcDevicePeakDelay_Id",
            	name: "rpcDeviceInformation.peakDelay",
                fieldLabel: '错峰延时(s)',
                allowBlank: true,
                hidden: !IoTConfig,
                minValue: 0,
                anchor: '95%',
                msgTarget: 'side'
            },
//            {
//         		xtype: "textfield",
//         		fieldLabel: '视频监控路径1',
//         		id: 'rpcDeviceVideoUrl1_Id',
//         		anchor: '95%',
//         		name: "rpcDeviceInformation.videoUrl1",
//         		value:''
//            },{
//         		xtype: "textfield",
//         		fieldLabel: '视频监控路径2',
//         		id: 'rpcDeviceVideoUrl2_Id',
//         		anchor: '95%',
//         		name: "rpcDeviceInformation.videoUrl2",
//         		value:''
//            },
            {
            	xtype: 'fieldcontainer',
                fieldLabel : '状态<font color=red>*</font>',
                defaultType: 'radiofield',
                id: 'rpcDeviceStatus_Id',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:'使能',
                        name:'rpcDeviceInformation.status',
                        checked:true,
                        inputValue: '1',
                        id: 'rpcDeviceStatusRadio1_Id'
                    }, {
                        boxLabel:'失效',
                        name:'rpcDeviceInformation.status',
                        inputValue:'0',
                        id:'rpcDeviceStatusRadio0_Id'
                    }
                ]
            },{
            	xtype: 'numberfield',
            	id: "rpcDeviceSortNum_Id",
            	name: "rpcDeviceInformation.sortNum",
                fieldLabel: '排序编号',
                allowBlank: true,
                minValue: 1,
                anchor: '95%',
                msgTarget: 'side'
            }],
            buttons: [{
                id: 'addFormRPCDevice_Id',
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("RPCDeviceInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doRPCDeviceAdd',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: cosog.string.sendServer,
                            waitTitle: 'Please Wait...',
                            success: function (response, action) {
                                Ext.getCmp('RPCDeviceInfoWindow_Id').close();
                                CreateAndLoadRPCDeviceInfoTable();
                                
                                if (action.result.msg == true && action.result.resultCode==1) {
                                    Ext.Msg.alert(cosog.string.ts, "<font color=blue>" + cosog.string.success + "</font>");
                                }else if (action.result.msg == true && action.result.resultCode==-66) {
                                    Ext.Msg.alert(cosog.string.ts, "<font color=red>设备数许可超限</font>");
                                }else if (action.result.msg == false) {
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
                id: 'updateFormRPCDevice_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("RPCDeviceInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doRPCDeviceEdit',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: cosog.string.sendServer,
                            waitTitle: 'Please Wait...',
                            success: function (response, action) {
                                Ext.getCmp('RPCDeviceInfoWindow_Id').close();
                                CreateAndLoadRPCDeviceInfoTable();
                                
                                if (action.result.msg == true) {
                                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
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
                        Ext.Msg.alert(cosog.string.ts, "<font color=red>" + cosog.string.validdata + "</font>");
                    }
                    return false;
                }
            }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("RPCDeviceInfoWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: rpcDeviceEditForm
        });
        me.callParent(arguments);
    }
});