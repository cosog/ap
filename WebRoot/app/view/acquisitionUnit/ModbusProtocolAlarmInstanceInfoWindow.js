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
        
        var protocolAndAlarmUnitTree=Ext.create('AP.view.well.TreePicker',{
        	id:'modbusInstanceProtocolAndAlarmUnit_Id',
        	anchor: '100%',
        	fieldLabel: loginUserLanguageResource.alarmUnit+'<font color=red>*</font>',
            emptyText: loginUserLanguageResource.selectAlarmUnit+'...',
            blankText: loginUserLanguageResource.selectAlarmUnit+'...',
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
                		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.selectAlarmUnit+"</font>");
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
                fieldLabel: loginUserLanguageResource.idx,
                id: 'formModbusProtocolAlarmInstance_Id',
                anchor: '100%',
                name: "protocolAlarmInstance.id"
            },{
                id: 'formModbusProtocolAlarmInstanceName_Id',
                name: "protocolAlarmInstance.name",
                fieldLabel: loginUserLanguageResource.instanceName+'<font color=red>*</font>',
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
                                url: context + '/acquisitionUnitManagerController/judgeAlarmInstanceExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>"+loginUserLanguageResource.alarmInstanceExist+"</font>,"+loginUserLanguageResource.pleaseConfirm, function(btn, text){
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
				xtype : "hidden",
				id : 'modbusInstanceAlarmUnit_Id',
				value: 0,
				name : "protocolAlarmInstance.AlarmUnitId"
			},protocolAndAlarmUnitTree,{
            	xtype: 'numberfield',
            	id: "modbusProtocolAlarmInstanceSort_Id",
                name: 'protocolAlarmInstance.sort',
                fieldLabel: loginUserLanguageResource.sortNum,
                allowBlank: true,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }],
            buttons: [{
            	xtype: 'button',
            	id: 'addFormModbusProtocolAlarmInstance_Id',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	saveModbusProtocolAlarmInstanceSubmitBtnForm();
                }
         }, {
                xtype: 'button',
                id: 'updateFormaModbusProtocolAlarmInstance_Id',
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