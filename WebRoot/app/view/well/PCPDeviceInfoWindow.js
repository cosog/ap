Ext.define("AP.view.well.PCPDeviceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.pcpDeviceInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'PCPDeviceInfoWindow_Id',
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
							deviceType: 201
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var pcpDeviceAcqInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  '采控实例',
					emptyText : '请选择采控实例',
					blankText : '请选择采控实例',
					id : 'pcpDeviceAcqInstanceComb_Id',
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
							Ext.getCmp("pcpDeviceAcqInstanceCode_Id").setValue(this.value);
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
							deviceType: 201
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var pcpDeviceDisplayInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  '显示实例',
					emptyText : '请选择显示实例',
					blankText : '请选择显示实例',
					id : 'pcpDeviceDisplayInstanceComb_Id',
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
							Ext.getCmp("pcpDeviceDisplayInstanceCode_Id").setValue(this.value);
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
							deviceType: 201
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var pcpDeviceReportInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  '报表实例',
					emptyText : '请选择报表实例',
					blankText : '请选择报表实例',
					id : 'pcpDeviceReportInstanceComb_Id',
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
							Ext.getCmp("pcpDeviceReportInstanceCode_Id").setValue(this.value);
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
							deviceType: 201
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var pcpDeviceAlarmInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  '报警实例',
					emptyText : '请选择报警实例',
					blankText : '请选择报警实例',
					id : 'pcpDeviceAlarmInstanceComb_Id',
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
							Ext.getCmp("pcpDeviceAlarmInstanceCode_Id").setValue(this.value);
	                    }
					}
				});

        var pcpDeviceEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addPCPDeviceForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: 'label',
                id: 'pcpDeviceWinOgLabel_Id',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备编号',
                id: 'pcpDevice_Id',
                value: '',
                name: "pcpDeviceInformation.id"
            },{
                xtype: "hidden",
                fieldLabel: '单位编号',
                id: 'pcpDeviceOrg_Id',
                value: '',
                name: "pcpDeviceInformation.orgId"
            },{
                xtype: "hidden",
                fieldLabel: '设备类型',
                id: 'pcpDeviceType_Id',
                value: '',
                name: "pcpDeviceInformation.deviceType"
            }, 
//            orgTreePicker, 
            {
                fieldLabel: '井名<font color=red>*</font>',
                id: 'pcpDeviceName_Id',
                allowBlank: false,
                anchor: '95%',
                name: "pcpDeviceInformation.wellName",
                listeners: {
                	blur: function (t, e) {
                        if(t.value!=''){
                        	var orgId=Ext.getCmp("pcpDeviceOrg_Id").getValue();
                        	var deviceType=Ext.getCmp("pcpDeviceType_Id").getValue();
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
				id : 'pcpDeviceApplicationScenariosComb_Id',
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
						Ext.getCmp("pcpDeviceApplicationScenarios_Id").setValue(this.value);
					}
				}
            },{
                xtype: "hidden",
                fieldLabel: '应用场景值',
                id: 'pcpDeviceApplicationScenarios_Id',
                value: sceneConfig=='cbm'?0:1,
                name: "pcpDeviceInformation.applicationScenarios"
            },pcpDeviceAcqInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '采控实例编码',
                id: 'pcpDeviceAcqInstanceCode_Id',
                value: '',
                name: "pcpDeviceInformation.instanceCode"
            },pcpDeviceDisplayInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '显示实例编码',
                id: 'pcpDeviceDisplayInstanceCode_Id',
                value: '',
                name: "pcpDeviceInformation.displayInstanceCode"
            },pcpDeviceReportInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '报表实例编码',
                id: 'pcpDeviceReportInstanceCode_Id',
                value: '',
                name: "pcpDeviceInformation.reportInstanceCode"
            },pcpDeviceAlarmInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '报警实例编码',
                id: 'pcpDeviceAlarmInstanceCode_Id',
                value: '',
                name: "pcpDeviceInformation.alarmInstanceCode"
            }, {
            	xtype : "combobox",
				fieldLabel : '下位机TCP类型',
				id : 'pcpDeviceTcpTypeComb_Id',
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
						Ext.getCmp("pcpDeviceTcpType_Id").setValue(this.value);
						if(this.value=='TCP Server'){
							Ext.getCmp('pcpDeviceSignInId_Id').disable();
							Ext.getCmp('pcpDeviceIpPort_Id').enable();
						}else{
							Ext.getCmp('pcpDeviceSignInId_Id').enable();
							Ext.getCmp('pcpDeviceIpPort_Id').disable();
						}
					}
				}
            },{
                xtype: "hidden",
                fieldLabel: '下位机TCP类型值',
                id: 'pcpDeviceTcpType_Id',
                value: '',
                name: "pcpDeviceInformation.tcpType"
            },{
                xtype: "textfield",
                fieldLabel: '注册包ID',
                allowBlank: true,
                hidden: !IoTConfig,
                id: 'pcpDeviceSignInId_Id',
                anchor: '95%',
                name: "pcpDeviceInformation.signInId",
                value: '',
                listeners: {
                	blur: function (t, e) {
                        var slave=Ext.getCmp("pcpDeviceSlave_Id").getValue();
                        var deviceType=Ext.getCmp("pcpDeviceType_Id").getValue();
                		if(t.value!=''&&slave!=''){
                        	var orgId=Ext.getCmp("pcpDeviceOrg_Id").getValue();
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
                id: 'pcpDeviceIpPort_Id',
                anchor: '95%',
                name: "pcpDeviceInformation.ipPort",
                value: '',
                regex: /^((\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*)|((\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])))\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
                regexText: '您输入格式不正确',
                listeners: {
                	blur: function (t, e) {
                        var slave=Ext.getCmp("pcpDeviceSlave_Id").getValue();
                        var deviceType=Ext.getCmp("pcpDeviceType_Id").getValue();
                		if(t.value!=''&&slave!=''){
                        	var orgId=Ext.getCmp("pcpDeviceOrg_Id").getValue();
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
         		id: 'pcpDeviceSlave_Id',
         		anchor: '95%',
         		name: "pcpDeviceInformation.slave",
         		value:'01',
         		hidden: !IoTConfig,
         		listeners: {
                	blur: function (t, e) {
                        var signinId=Ext.getCmp("pcpDeviceSignInId_Id").getValue();
                        var deviceType=Ext.getCmp("pcpDeviceType_Id").getValue();
                		if(signinId!=''&&t.value!=''){
                        	var orgId=Ext.getCmp("pcpDeviceOrg_Id").getValue();
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
            	id: "pcpDevicePeakDelay_Id",
            	name: "pcpDeviceInformation.peakDelay",
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
//         		id: 'pcpDeviceVideoUrl1_Id',
//         		anchor: '95%',
//         		name: "pcpDeviceInformation.videoUrl1",
//         		value:''
//            },{
//         		xtype: "textfield",
//         		fieldLabel: '视频监控路径2',
//         		id: 'pcpDeviceVideoUrl2_Id',
//         		anchor: '95%',
//         		name: "pcpDeviceInformation.videoUrl2",
//         		value:''
//            },
            {
            	xtype: 'fieldcontainer',
                fieldLabel : '状态<font color=red>*</font>',
                defaultType: 'radiofield',
                id: 'pcpDeviceStatus_Id',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:'使能',
                        name:'pcpDeviceInformation.status',
                        checked:true,
                        inputValue: '1',
                        id: 'pcpDeviceStatusRadio1_Id'
                    }, {
                        boxLabel:'失效',
                        name:'pcpDeviceInformation.status',
                        inputValue:'0',
                        id:'pcpDeviceStatusRadio0_Id'
                    }
                ]
            },{
            	xtype: 'numberfield',
            	id: "pcpDeviceSortNum_Id",
            	name: "pcpDeviceInformation.sortNum",
                fieldLabel: '排序编号',
                allowBlank: true,
                minValue: 1,
                anchor: '95%',
                msgTarget: 'side'
            
            }],
            buttons: [{
                id: 'addFormPCPDevice_Id',
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("PCPDeviceInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doPCPDeviceAdd',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: cosog.string.sendServer,
                            waitTitle: 'Please Wait...',
                            success: function (response, action) {
                                Ext.getCmp('PCPDeviceInfoWindow_Id').close();
                                CreateAndLoadPCPDeviceInfoTable();
                                
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
                id: 'updateFormPCPDevice_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("PCPDeviceInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doPCPDeviceEdit',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: cosog.string.sendServer,
                            waitTitle: 'Please Wait...',
                            success: function (response, action) {
                                Ext.getCmp('PCPDeviceInfoWindow_Id').close();
                                CreateAndLoadPCPDeviceInfoTable();
                                
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
                    Ext.getCmp("PCPDeviceInfoWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: pcpDeviceEditForm
        });
        me.callParent(arguments);
    }
});