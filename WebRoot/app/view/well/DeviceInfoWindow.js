Ext.define("AP.view.well.DeviceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.deviceInfoWindow',
    id: 'deviceInfoWindow_Id',
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
        var deviceTypes=getDeviceTypeFromTabId("DeviceManagerTabPanel");
        
        var labelWidth=getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage);
        if(labelWidth<getLabelWidth(loginUserLanguageResource.deviceType,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.deviceType,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.applicationScenarios,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.applicationScenarios,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.acqInstance,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.acqInstance,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.displayInstance,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.displayInstance,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.reportInstance,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.reportInstance,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.alarmInstance,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.alarmInstance,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.deviceTcpType,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.deviceTcpType,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.signInId,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.signInId,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.ipPort,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.ipPort,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.slave,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.slave,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.peakDelay,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.peakDelay,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.status,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.status,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.sortNum,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.sortNum,loginUserLanguage);
        }
        var deviceTypeStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/wellInformationManagerController/getDeviceTypeComb',
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
							deviceTypes:deviceTypes	
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
			}
		});
        
        var deviceTypeComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.deviceType+'<font color=red>*</font>',
					labelWidth: labelWidth,
					emptyText : loginUserLanguageResource.selectDeviceType,
					blankText : loginUserLanguageResource.selectDeviceType,
					id : 'deviceTypeComb_Id',
					anchor : '95%',
					store: deviceTypeStore,
					queryMode : 'remote',
					typeAhead : true,
					autoSelect : false,
					allowBlank : false,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					disabled: isNumber(deviceTypes)?true:false,
					forceSelection : isNumber(deviceTypes)?true:false,
				    value: isNumber(deviceTypes)?deviceTypes:null,
					listeners : {
						select: function (v,o) {
							if(o.data.boxkey==''){
								v.setValue('');
								v.setRawValue(' ');
							}
							Ext.getCmp("addDeviceType_Id").setValue(this.value);
	                    }
					}
				});
        
        
        var applicationScenariosStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/wellInformationManagerController/getApplicationScenariosComb',
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
        
        var applicationScenariosComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.applicationScenarios,
					labelWidth: labelWidth,
					emptyText : loginUserLanguageResource.selectApplicationScenarios,
					blankText : loginUserLanguageResource.selectApplicationScenarios,
					id : 'deviceApplicationScenariosComb_Id',
					anchor : '95%',
					store: applicationScenariosStore,
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
							Ext.getCmp("deviceApplicationScenarios_Id").setValue(this.value);
	                    }
					}
				});
        
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
        
        var deviceAcqInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.acqInstance,
					labelWidth: labelWidth,
					emptyText : loginUserLanguageResource.selectAcqInstance,
					blankText : loginUserLanguageResource.selectAcqInstance,
					id : 'deviceAcqInstanceComb_Id',
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
							Ext.getCmp("deviceAcqInstanceCode_Id").setValue(this.value);
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
        
        var deviceDisplayInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.displayInstance,
					labelWidth: labelWidth,
					emptyText : loginUserLanguageResource.selectdisplayInstance,
					blankText : loginUserLanguageResource.selectdisplayInstance,
					id : 'deviceDisplayInstanceComb_Id',
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
							Ext.getCmp("deviceDisplayInstanceCode_Id").setValue(this.value);
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
        
        var deviceReportInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.reportInstance,
					labelWidth: labelWidth,
					emptyText : loginUserLanguageResource.selectReportInstance,
					blankText : loginUserLanguageResource.selectReportInstance,
					id : 'deviceReportInstanceComb_Id',
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
							Ext.getCmp("deviceReportInstanceCode_Id").setValue(this.value);
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
        
        var deviceAlarmInstanceComb = Ext.create(
        		'Ext.form.field.ComboBox', {
					fieldLabel :  loginUserLanguageResource.alarmInstance,
					labelWidth: labelWidth,
					emptyText : loginUserLanguageResource.selectAlarmInstance,
					blankText : loginUserLanguageResource.selectAlarmInstance,
					id : 'deviceAlarmInstanceComb_Id',
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
							Ext.getCmp("deviceAlarmInstanceCode_Id").setValue(this.value);
	                    }
					}
				});

        var deviceEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addDeviceForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: 'label',
                id: 'deviceWinOgLabel_Id',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: '设备编号',
                labelWidth: labelWidth,
                id: 'device_Id',
                value: '',
                name: "deviceInformation.id"
            },{
                xtype: "hidden",
                fieldLabel: '单位编号',
                labelWidth: labelWidth,
                id: 'addDeviceOrg_Id',
                value: '',
                name: "deviceInformation.orgId"
            },{
                fieldLabel: loginUserLanguageResource.deviceName+'<font color=red>*</font>',
                labelWidth: labelWidth,
                id: 'deviceName_Id',
                allowBlank: false,
                anchor: '95%',
                name: "deviceInformation.deviceName",
                listeners: {
                	blur: function (t, e) {
                        if(t.value!=''){
                        	var orgId=Ext.getCmp("addDeviceOrg_Id").getValue();
                        	var deviceType=Ext.getCmp("addDeviceType_Id").getValue();
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
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>【"+t.value+" "+loginUserLanguageResource.deviceExist+"】</font>,"+loginUserLanguageResourceFirstLower.pleaseConfirm, function(btn, text){
                                    	    if (btn == 'ok'){
                                    	    	t.focus(true, 100);
                                    	    }
                                    	});
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
                                }
                            });
                        }
                    }
                }
            },deviceTypeComb,{
         		xtype: "textfield",
         		fieldLabel: loginUserLanguageResource.deviceType,
         		labelWidth: labelWidth,
         		hidden:true,
         		id: 'addDeviceType_Id',
         		anchor: '95%',
         		name: "deviceInformation.deviceType",
         		value: getDeviceTypeFromTabId("DeviceManagerTabPanel")
            },applicationScenariosComb,{
            	xtype: "hidden",
                fieldLabel: loginUserLanguageResource.applicationScenarios,
                labelWidth: labelWidth,
                id: 'deviceApplicationScenarios_Id',
                value: '',
                name: "deviceInformation.applicationScenarios"
            },deviceAcqInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '采控实例编码',
                labelWidth: labelWidth,
                id: 'deviceAcqInstanceCode_Id',
                value: '',
                name: "deviceInformation.instanceCode"
            },deviceDisplayInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '显示实例编码',
                labelWidth: labelWidth,
                id: 'deviceDisplayInstanceCode_Id',
                value: '',
                name: "deviceInformation.displayInstanceCode"
            },deviceReportInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '报表实例编码',
                labelWidth: labelWidth,
                id: 'deviceReportInstanceCode_Id',
                value: '',
                name: "deviceInformation.reportInstanceCode"
            },deviceAlarmInstanceComb,{
            	xtype: "hidden",
                fieldLabel: '报警实例编码',
                labelWidth: labelWidth,
                id: 'deviceAlarmInstanceCode_Id',
                value: '',
                name: "deviceInformation.alarmInstanceCode"
            }, {
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.deviceTcpType,
				labelWidth: labelWidth,
				id : 'deviceTcpTypeComb_Id',
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
				emptyText : loginUserLanguageResource.selectDeviceTcpType,
				blankText : loginUserLanguageResource.selectDeviceTcpType,
				listeners : {
					select:function(v,o){
						Ext.getCmp("deviceTcpType_Id").setValue(this.value);
						if(this.value=='TCP Server'){
							Ext.getCmp('deviceSignInId_Id').disable();
							Ext.getCmp('deviceIpPort_Id').enable();
						}else{
							Ext.getCmp('deviceSignInId_Id').enable();
							Ext.getCmp('deviceIpPort_Id').disable();
						}
					}
				}
            },{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.deviceTcpType,
                labelWidth: labelWidth,
                id: 'deviceTcpType_Id',
                value: '',
                name: "deviceInformation.tcpType"
            },{
                xtype: "textfield",
                fieldLabel: loginUserLanguageResource.signInId,
                labelWidth: labelWidth,
                allowBlank: true,
                id: 'deviceSignInId_Id',
                anchor: '95%',
                name: "deviceInformation.signInId",
                value: '',
                hidden: !IoTConfig,
                listeners: {
                	blur: function (t, e) {
                        var slave=Ext.getCmp("deviceSlave_Id").getValue();
                        var deviceType=Ext.getCmp("addDeviceType_Id").getValue();
                		if(t.value!=''&&slave!=''){
                        	var orgId=Ext.getCmp("addDeviceOrg_Id").getValue();
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
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>【"+loginUserLanguageResource.collisionInfo1+"】</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
                                    	    if (btn == 'ok'){
                                    	    	t.focus(true, 100);
                                    	    }
                                    	});
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
                                }
                            });
                        }
                    }
                }
            },{
                xtype: "textfield",
                fieldLabel: loginUserLanguageResource.ipPort,
                labelWidth: labelWidth,
                allowBlank: true,
                hidden: !IoTConfig,
                id: 'deviceIpPort_Id',
                anchor: '95%',
                name: "deviceInformation.ipPort",
                value: '',
                regex: /^((\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*)|((\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])))\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
                regexText: '您输入格式不正确',
                listeners: {
                	blur: function (t, e) {
                        var slave=Ext.getCmp("deviceSlave_Id").getValue();
                        var deviceType=Ext.getCmp("addDeviceType_Id").getValue();
                		if(t.value!=''&&slave!=''){
                        	var orgId=Ext.getCmp("addDeviceOrg_Id").getValue();
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
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>【"+loginUserLanguageResource.collisionInfo2+"】</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
                                    	    if (btn == 'ok'){
                                    	    	t.focus(true, 100);
                                    	    }
                                    	});
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
                                }
                            });
                        }
                    }
                }
            }, {
         		xtype: "textfield",
         		fieldLabel: loginUserLanguageResource.slave,
         		labelWidth: labelWidth,
         		id: 'deviceSlave_Id',
         		anchor: '95%',
         		name: "deviceInformation.slave",
         		value:'01',
         		hidden: !IoTConfig,
         		listeners: {
                	blur: function (t, e) {
                        var signinId=Ext.getCmp("deviceSignInId_Id").getValue();
                        var deviceType=Ext.getCmp("addDeviceType_Id").getValue();
                		if(signinId!=''&&t.value!=''){
                        	var orgId=Ext.getCmp("addDeviceOrg_Id").getValue();
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
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>【"+loginUserLanguageResource.collisionInfo1+"】</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
                                    	    if (btn == 'ok'){
                                    	    	t.focus(true, 100);
                                    	    }
                                    	});
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
                                }
                            });
                        }
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: "devicePeakDelay_Id",
            	name: "deviceInformation.peakDelay",
                fieldLabel: loginUserLanguageResource.peakDelay+'(s)',
                labelWidth: labelWidth,
                allowBlank: true,
                hidden: !IoTConfig,
                minValue: 0,
                anchor: '95%',
                msgTarget: 'side'
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : loginUserLanguageResource.status+'<font color=red>*</font>',
                labelWidth: labelWidth,
                defaultType: 'radiofield',
                id: 'deviceStatus_Id',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:loginUserLanguageResource.enable,
                        name:'deviceInformation.status',
                        checked:true,
                        inputValue: '1',
                        id: 'deviceStatusRadio1_Id'
                    }, {
                        boxLabel:loginUserLanguageResource.disable,
                        name:'deviceInformation.status',
                        inputValue:'0',
                        id:'deviceStatusRadio0_Id'
                    }
                ]
            }
            ,{
                xtype: 'datefield',
                format: 'Y-m-d ',
                value: '',
                fieldLabel: loginUserLanguageResource.commissioningDate,
                labelWidth: labelWidth,
                anchor: '95%',
                allowBlank: true,
                listeners: {
                	select: function (combo, record, index) {
                		Ext.getCmp('deviceCommissioningDate_Id').setValue(combo.rawValue.replace(/\s*/g,""));
                	}
                }
            },{
                xtype: "textfield",
                hidden: true,
                allowBlank: true,
                fieldLabel: loginUserLanguageResource.commissioningDate,
                labelWidth: labelWidth,
                anchor: '95%',
                id: "deviceCommissioningDate_Id",
                name: "deviceInformation.commissioningDate",
                value: ''
            }
            ,{
            	xtype: 'numberfield',
            	id: "deviceSortNum_Id",
            	name: "deviceInformation.sortNum",
                fieldLabel: loginUserLanguageResource.sortNum,
                labelWidth: labelWidth,
                allowBlank: true,
                minValue: 1,
                anchor: '95%',
                msgTarget: 'side'
            }],
            buttons: [{
                id: 'addFormDevice_Id',
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("deviceInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doDeviceAdd',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: loginUserLanguageResource.sendServer,
                            waitTitle: loginUserLanguageResource.wait,
                            success: function (response, action) {
                                Ext.getCmp('deviceInfoWindow_Id').close();
                                CreateAndLoadDeviceInfoTable();
                                
                                if (action.result.msg == true && action.result.resultCode==1) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                                }else if (action.result.msg == true && action.result.resultCode==-66) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>设备数许可超限</font>");
                                }else if (action.result.msg == false) {
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
                id: 'updateFormDevice_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function (v, o) {
                    var winForm = Ext.getCmp("deviceInfoWindow_Id").down('form');
                    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
                    if (winForm.getForm().isValid()) {
                        winForm.getForm().submit({
                            url: context + '/wellInformationManagerController/doDeviceEdit',
                            clientValidation: true, // 进行客户端验证
                            method: "POST",
                            waitMsg: loginUserLanguageResource.sendServer,
                            waitTitle: loginUserLanguageResource.wait,
                            success: function (response, action) {
                                Ext.getCmp('deviceInfoWindow_Id').close();
                                CreateAndLoadDeviceInfoTable();
                                
                                if (action.result.msg == true) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
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
                        Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.invalidData + "</font>");
                    }
                    return false;
                }
            }, {
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("deviceInfoWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: deviceEditForm
        });
        me.callParent(arguments);
    }
});