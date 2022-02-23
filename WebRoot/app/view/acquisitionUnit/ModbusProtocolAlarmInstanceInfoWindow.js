Ext.define("AP.view.acquisitionUnit.ModbusProtocolAlarmInstanceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.modbusProtocolAlarmInstanceInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'modbusProtocolAlarmInstanceInfoWindow_Id',
    closeAction: 'destroy',
    width: 330,
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
        var ProtocolAndAlarmUnitTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['orgId', 'text', 'leaf'],
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: context + '/acquisitionUnitManagerController/modbusProtocolAndAlarmUnitTreeData',
                reader: 'json'
            },
            root: {
                expanded: true,
                text: 'orgName'
            },
            listeners: {
            	beforeload: function (store, options) {
            		var deviceTypeObj=Ext.getCmp('modbusProtocolAlarmInstanceDeviceTypeComb_Id');
            		var deviceType=0;
            		if(isNotVal(deviceTypeObj)){
            			deviceType=deviceTypeObj.getValue();
            		}
            		var new_params = {
            				deviceType:deviceType
    					};
    					Ext.apply(store.proxy.extraParams,new_params);
            	}
            }
        });
        
        var protocolAndAlarmUnitTree=Ext.create('AP.view.well.TreePicker',{
        	id:'modbusInstanceProtocolAndAlarmUnit_Id',
        	anchor: '100%',
        	fieldLabel: '报警单元<font color=red>*</font>',
            emptyText: '请选择报警单元...',
            blankText: '请选择报警单元...',
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            allowBlank: false,
            store:ProtocolAndAlarmUnitTreeStore,
            listeners: {
            	expand: function (sm, selections) {
            		protocolAndAlarmUnitTree.getStore().load();
                },
            	select: function (picker,record,eOpts) {
                	if(record.data.classes==1){
                		Ext.Msg.alert('info', "<font color=red>当前选中为协议，请选择报警单元！</font>");
                	}else{
                		Ext.getCmp("modbusInstanceAlarmUnit_Id").setValue(record.data.id);
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
                id: 'formModbusProtocolAlarmInstance_Id',
                anchor: '100%',
                name: "protocolAlarmInstance.id"
            },{
				xtype : "hidden",
				id : 'modbusProtocolAlarmInstanceDeviceType_Id',
				value: 0,
				name : "protocolAlarmInstance.deviceType"
			},{
            	xtype : "combobox",
				fieldLabel : '设备类型<font color=red>*</font>',
				id : 'modbusProtocolAlarmInstanceDeviceTypeComb_Id',
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    value:0,
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [[0, '泵设备'],[1, '管设备']]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择设备类型',
				blankText : '请选择设备类型',
				listeners : {
					select:function(v,o){
						Ext.getCmp("modbusProtocolAlarmInstanceDeviceType_Id").setValue(this.value);
					}
				}
            }, {
                id: 'formModbusProtocolAlarmInstanceName_Id',
                name: "protocolAlarmInstance.name",
                fieldLabel: '实例名称<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                value: '',
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        if(value_!=''){
                        	var deviceType=Ext.getCmp("modbusProtocolAlarmInstanceDeviceType_Id").getValue();
                        	Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                	deviceType:deviceType,
                                	instanceName: t.value
                                },
                                url: context + '/acquisitionUnitManagerController/judgeAlarmInstanceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【报警实例已存在】</font>,请确认！", function(btn, text){
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
				id : 'modbusInstanceAlarmUnit_Id',
				value: 0,
				name : "protocolAlarmInstance.AlarmUnitId"
			},protocolAndAlarmUnitTree,{
            	xtype: 'numberfield',
            	id: "modbusProtocolAlarmInstanceSort_Id",
                name: 'protocolAlarmInstance.sort',
                fieldLabel: '排序',
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocolAlarmInstance_Id',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolAlarmInstanceSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocolAlarmInstance_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: function () {
//                	UpdatemodbusProtocolDataInfoSubmitBtnForm();
                }
         }, {
        	 	xtype: 'button',   
        	 	text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("modbusProtocolAlarmInstanceInfoWindow_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postModbusProtocolEditForm
        });
        me.callParent(arguments);
    }

});