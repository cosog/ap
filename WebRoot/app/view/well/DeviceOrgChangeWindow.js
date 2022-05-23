Ext.define("AP.view.well.DeviceOrgChangeWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.deviceOrgChangeWindow',
    layout: 'fit',
    title:'设备隶属迁移',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 500,
    minWidth: 500,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        var deviceCombStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/wellInformationManagerController/loadWellComboxList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('DeviceOrgChangeDeviceListComb_Id').getValue();
                    var deviceType=Ext.getCmp('DeviceOrgChangeWinDeviceType_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: deviceType,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var deviceCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: "DeviceOrgChangeDeviceListComb_Id",
                labelWidth: 35,
                width: 145,
                labelAlign: 'left',
                queryMode: 'remote',
                typeAhead: true,
                store: deviceCombStore,
                autoSelect: false,
                editable: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                pageSize: comboxPagingStatus,
                minChars: 0,
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                listeners: {
                    expand: function (sm, selections) {
                        deviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    select: function (combo, record, index) {
                    	var gridPanel=Ext.getCmp("DeviceOrgChangeDeviceListGridPanel_Id");
                    	if(isNotVal(gridPanel)){
                    		gridPanel.getStore().load();
                    	}else{
                    		Ext.create("AP.store.well.DeviceOrgChangeDeviceListStore");
                    	}
                    }
                }
            });
        Ext.apply(me, {
        	bbar: ['->', {
                xtype: 'button',
                text: '迁移',
                iconCls: 'move',
                style: 'margin-right: 15px;margin-bottom: 5px',
                pressed: true,
                handler: function () {
                	var selectedDevice=Ext.getCmp("DeviceOrgChangeDeviceListGridPanel_Id").getSelectionModel().getSelection();
                	var selectedOrg=Ext.getCmp("DeviceOrgChangeOrgListTreePanel_Id").getSelectionModel().getSelection();
                	var deviceType=Ext.getCmp("DeviceOrgChangeWinDeviceType_Id").getValue();
                	if(selectedDevice.length==0){
                		Ext.MessageBox.alert("信息","请选择要迁移的设备！");
                		return;
                	}
                	if(selectedOrg.length==0){
                		Ext.MessageBox.alert("信息","请选择目的组织！");
                		return;
                	}
                	var selectedDeviceId="";
                	var selectedDeviceArr=[];
                	var selectedOrgId=selectedOrg[0].data.orgId;
                	for(var i=0;i<selectedDevice.length;i++){
                		selectedDeviceArr.push(selectedDevice[i].data.id);
                	}
                	selectedDeviceId="" + selectedDeviceArr.join(",");
                	
                	Ext.Ajax.request({
                		url : context + '/wellInformationManagerController/changeDeviceOrg',
                		method : "POST",
                		params : {
                			selectedDeviceId : selectedDeviceId,
                			selectedOrgId : selectedOrgId,
                			deviceType : deviceType
                		},
                		success : function(response) {
                			var result = Ext.JSON.decode(response.responseText);
                			if (result.success == true) {
                				Ext.Msg.alert('提示', "设备隶属迁移成功");
                			}
                			if (result.success == false) {
                				Ext.Msg.alert('提示', "<font color=red>设备隶属迁移失败。</font>");
                			}
                		},
                		failure : function() {
                			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
                		}
                	});
                }
    		}
//        	, {
//                xtype: 'tbspacer',
//                flex: 1
//    		}
        	],
        	layout: 'border',
            items: [{
            	region: 'center',
        		title:'设备列表',
        		layout: 'fit',
        		id:'DeviceOrgChangeWinDeviceListPanel_Id',
        		tbar:[deviceCombo,{
                    id: 'DeviceOrgChangeWinDeviceType_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }]
            },{
            	region: 'east',
        		width: '50%',
        		title:'目标组织',
        		layout: 'fit',
        		id:'DeviceOrgChangeWinOrgListPanel_Id'
            }]
        });
        me.callParent(arguments);
    }
});