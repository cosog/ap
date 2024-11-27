Ext.define("AP.view.acquisitionUnit.ModbusProtocolInstanceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.modbusProtocolInstanceInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'modbusProtocolInstanceInfoWindow_Id',
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
        var ProtocolAndAcqUnitTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['orgId', 'text', 'leaf'],
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: context + '/acquisitionUnitManagerController/modbusProtocolAndAcqUnitTreeData',
                reader: 'json'
            },
            root: {
                expanded: true,
                text: 'orgName'
            },
            listeners: {
            	beforeload: function (store, options) {
					var deviceTypeIds='';
		        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
		        	if(tabTreeGridPanelSelection.length>0){
		        		deviceTypeIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
		        	}
					var new_params = {
							deviceTypeIds: deviceTypeIds
					};
					Ext.apply(store.proxy.extraParams,new_params);
				}
            }
        });
        
        var protocolAndAcqUnitTree=Ext.create('AP.view.well.TreePicker',{
        	id:'modbusInstanceProtocolAndAcqUnit_Id',
        	anchor: '100%',
        	fieldLabel: loginUserLanguageResource.acqUnit+'<font color=red>*</font>',
        	labelWidth: 135,
            emptyText: '请选择采控单元...',
            blankText: '请选择采控单元...',
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            allowBlank: false,
            store:ProtocolAndAcqUnitTreeStore,
            listeners: {
            	expand: function (sm, selections) {
            		protocolAndAcqUnitTree.getStore().load();
                },
            	select: function (picker,record,eOpts) {
                	if(record.data.classes==1){
                		Ext.Msg.alert('info', "<font color=red>当前选中为协议，请选择采控单元！</font>");
                	}else{
                		Ext.getCmp("modbusInstanceAcqUnit_Id").setValue(record.data.id);
                	}
                }
            }
        });
        
        
        
        var postModbusProtocolEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '序号',
                id: 'formModbusProtocolInstance_Id',
                anchor: '100%',
                name: "protocolInstance.id"
            },{
                id: 'formModbusProtocolInstanceName_Id',
                name: "protocolInstance.name",
                fieldLabel: '实例名称<font color=red>*</font>',
                labelWidth: 135,
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        if(value_!=''){
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	instanceName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeInstanceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>"+loginUserLanguageResource.acqInstanceExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
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
				xtype : "hidden",
				id : 'modbusInstanceAcqUnit_Id',
				value: 0,
				name : "protocolInstance.unitId"
			},protocolAndAcqUnitTree,{
				xtype : "hidden",
				id : 'modbusInstanceAcqProtocolType_Id',
				value:'modbus-tcp',
				name : "protocolInstance.acqProtocolType"
			},{
            	xtype : "combobox",
				fieldLabel : '采集协议类型<font color=red>*</font>',
				labelWidth: 135,
				id : 'modbusInstanceAcqProtocolTypeComb_Id',
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:'modbus-tcp',
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [['modbus-tcp', 'modbus-tcp'],['modbus-rtu', 'modbus-rtu'],['private-rpc', 'private-rpc'],['private-mqtt', 'private-mqtt'],['private-kd93', 'private-kd93'],['private-lq1000', 'private-lq1000']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择协议类型',
				blankText : '请选择协议类型',
				listeners : {
					select:function(v,o){
						Ext.getCmp("modbusInstanceAcqProtocolType_Id").setValue(this.value);
					}
				}
            },{
				xtype : "hidden",
				id : 'modbusInstanceCtrlProtocolType_Id',
				value:'modbus-tcp',
				name : "protocolInstance.ctrlProtocolType"
			},{
            	xtype : "combobox",
				fieldLabel : '控制协议类型<font color=red>*</font>',
				labelWidth: 135,
				id : 'modbusInstanceCtrlProtocolTypeComb_Id',
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:'modbus-tcp',
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [['modbus-tcp', 'modbus-tcp'],['modbus-rtu', 'modbus-rtu'],['private-rpc', 'private-rpc'],['private-mqtt', 'private-mqtt']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择协议类型',
				blankText : '请选择协议类型',
				listeners : {
					select:function(v,o){
						Ext.getCmp("modbusInstanceCtrlProtocolType_Id").setValue(this.value);
					}
				}
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : '注册包前后缀十六进制<font color=red>*</font>',
                labelWidth: 135,
                defaultType: 'radiofield',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:'HEX',
                        name:'protocolInstance.signInPrefixSuffixHex',
                        checked:true,
                        inputValue: '1',
                        id: 'protocolInstanceSignInPrefixSuffixHexRadio1_Id'
                    }, {
                        boxLabel: 'ASC',
                        name:'protocolInstance.signInPrefixSuffixHex',
                        inputValue:'0',
                        id:'protocolInstanceSignInPrefixSuffixHexRadio0_Id'
                    }
                ]
            }, {
                id: 'formModbusProtocolInstanceSignInPrefix_Id',
                name: "protocolInstance.signInPrefix",
                fieldLabel: '注册包前缀',
                labelWidth: 135,
                anchor: '100%',
                value: ''
            }, {
            	id: 'modbusProtocolInstanceSignInSuffix_Id',
            	name: "protocolInstance.signInSuffix",
                fieldLabel: '注册包后缀',
                labelWidth: 135,
                anchor: '100%',
                value: ''
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : '注册包ID十六进制<font color=red>*</font>',
                labelWidth: 135,
                defaultType: 'radiofield',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:'HEX',
                        name:'protocolInstance.signInIDHex',
                        checked:true,
                        inputValue: '1',
                        id: 'protocolInstanceSignInIDHexRadio1_Id'
                    }, {
                        boxLabel: 'ASC',
                        name:'protocolInstance.signInIDHex',
                        inputValue:'0',
                        id:'protocolInstanceSignInIDHexRadio0_Id'
                    }
                ]
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : '心跳包前后缀十六进制<font color=red>*</font>',
                labelWidth: 135,
                defaultType: 'radiofield',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:'HEX',
                        name:'protocolInstance.heartbeatPrefixSuffixHex',
                        checked:true,
                        inputValue: '1',
                        id: 'protocolInstanceHeartbeatPrefixSuffixHexRadio1_Id'
                    }, {
                        boxLabel: 'ASC',
                        name:'protocolInstance.heartbeatPrefixSuffixHex',
                        inputValue:'0',
                        id:'protocolInstanceHeartbeatPrefixSuffixHexRadio0_Id'
                    }
                ]
            }, {
            	id: 'modbusProtocolInstanceHeartbeatPrefix_Id',
            	name: "protocolInstance.heartbeatPrefix",
                fieldLabel: '心跳包前缀',
                labelWidth: 135,
                anchor: '100%',
                value: ''
            }, {
            	id: 'modbusProtocolInstanceHeartbeatSuffix_Id',
            	name: "protocolInstance.heartbeatSuffix",
                fieldLabel: '心跳包后缀',
                labelWidth: 135,
                anchor: '100%',
                value: ''
            }, {
                id: 'modbusProtocolInstancePacketSendInterval_Id',
                name: "protocolInstance.packetSendInterval",
                fieldLabel: '单包发送间隔(ms)',
                labelWidth: 135,
                anchor: '100%',
                value: ''
            }, {
            	xtype: 'numberfield',
            	id: "modbusProtocolInstanceSort_Id",
                name: 'protocolInstance.sort',
                fieldLabel: loginUserLanguageResource.sortNum,
                labelWidth: 135,
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocolInstance_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolInstanceSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocolInstance_Id',
                text: loginUserLanguageResource.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
//                	UpdatemodbusProtocolDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("modbusProtocolInstanceInfoWindow_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postModbusProtocolEditForm
        });
        me.callParent(arguments);
    }

});