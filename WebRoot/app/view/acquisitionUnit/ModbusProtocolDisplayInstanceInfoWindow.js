Ext.define("AP.view.acquisitionUnit.ModbusProtocolDisplayInstanceInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.modbusProtocolDisplayInstanceInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'modbusProtocolDisplayInstanceInfoWindow_Id',
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
        var ProtocolAndDisplayUnitTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['orgId', 'text', 'leaf'],
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: context + '/acquisitionUnitManagerController/modbusProtocolAndDisplayUnitTreeData',
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
        
        var protocolAndDisplayUnitTree=Ext.create('AP.view.well.TreePicker',{
        	id:'modbusInstanceProtocolAndDisplayUnit_Id',
        	anchor: '100%',
        	fieldLabel: '显示单元<font color=red>*</font>',
            emptyText: '请选择显示单元...',
            blankText: '请选择显示单元...',
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            allowBlank: false,
            store:ProtocolAndDisplayUnitTreeStore,
            listeners: {
            	expand: function (sm, selections) {
            		protocolAndDisplayUnitTree.getStore().load();
                },
            	select: function (picker,record,eOpts) {
                	if(record.data.classes==1){
                		Ext.Msg.alert('info', "<font color=red>当前选中为协议，请选择显示单元！</font>");
                	}else{
                		Ext.getCmp("modbusInstanceDisplayUnit_Id").setValue(record.data.id);
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
                id: 'formModbusProtocolDisplayInstance_Id',
                anchor: '100%',
                name: "protocolDisplayInstance.id"
            },
//            {
//				xtype : "hidden",
//				id : 'modbusProtocolDisplayInstanceDeviceType_Id',
//				value: 0,
//				name : "protocolDisplayInstance.deviceType"
//			},{
//            	xtype : "combobox",
//				fieldLabel : '设备类型<font color=red>*</font>',
//				id : 'modbusProtocolDisplayInstanceDeviceTypeComb_Id',
//				anchor : '100%',
//				triggerAction : 'all',
//				selectOnFocus : false,
//			    forceSelection : true,
//			    value:0,
//			    allowBlank: false,
//				editable : false,
//				store : new Ext.data.SimpleStore({
//							fields : ['value', 'text'],
//							data : [[0, '抽油机井'],[1, '螺杆泵井']]
//						}),
//				displayField : 'text',
//				valueField : 'value',
//				queryMode : 'local',
//				emptyText : '请选择设备类型',
//				blankText : '请选择设备类型',
//				listeners : {
//					select:function(v,o){
//						Ext.getCmp("modbusProtocolDisplayInstanceDeviceType_Id").setValue(this.value);
//					}
//				}
//            }, 
            {
                id: 'formModbusProtocolDisplayInstanceName_Id',
                name: "protocolDisplayInstance.name",
                fieldLabel: '实例名称<font color=red>*</font>',
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
                                url: context + '/acquisitionUnitManagerController/judgeDisplayInstanceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(cosog.string.ts, "<font color='red'>【显示实例已存在】</font>,请确认！", function(btn, text){
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
				id : 'modbusInstanceDisplayUnit_Id',
				value: 0,
				name : "protocolDisplayInstance.DisplayUnitId"
			},protocolAndDisplayUnitTree,{
            	xtype: 'numberfield',
            	id: "modbusProtocolDisplayInstanceSort_Id",
                name: 'protocolDisplayInstance.sort',
                fieldLabel: '排序',
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocolDisplayInstance_Id',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolDisplayInstanceSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocolDisplayInstance_Id',
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
                    Ext.getCmp("modbusProtocolDisplayInstanceInfoWindow_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postModbusProtocolEditForm
        });
        me.callParent(arguments);
    }

});