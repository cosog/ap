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
    width: 750,
    minWidth: 750,
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
                    var deviceName = Ext.getCmp('DeviceOrgChangeDeviceListComb_Id').getValue();
                    var deviceType=Ext.getCmp('DeviceOrgChangeWinDeviceType_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: deviceType,
                        deviceName: deviceName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var deviceCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: deviceShowName,
                id: "DeviceOrgChangeDeviceListComb_Id",
                labelWidth: 8*deviceShowNameLength,
                width: (8*deviceShowNameLength+110),
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
            	layout: 'border',
            	width: '25%',
            	items:[{
            		region: 'north',
            		height: '50%',
            		title:'目标组织',
            		layout: 'fit',
            		id:'DeviceOrgChangeWinOrgListPanel_Id',
            		tbar: ['->', {
                        xtype: 'button',
                        text: '迁移',
                        iconCls: 'move',
//                        style: 'margin-right: 15px;margin-bottom: 5px',
                        pressed: false,
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
                        	var selectedOrgName=selectedOrg[0].data.text;
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
                        			selectedOrgName : selectedOrgName,
                        			deviceType : deviceType
                        		},
                        		success : function(response) {
                        			var result = Ext.JSON.decode(response.responseText);
                        			if (result.success == true) {
                        				Ext.Msg.alert('提示', "设备隶属组织迁移成功");
                        			}
                        			if (result.success == false) {
                        				Ext.Msg.alert('提示', "<font color=red>设备隶属组织迁移失败。</font>");
                        			}
                        		},
                        		failure : function() {
                        			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
                        		}
                        	});
                        }
            		}]
            	},{
            		region: 'center',
            		title:'设备类型',
            		layout: 'fit',
            		id:'DeviceTypeChangeWinTypeListPanel_Id',
            		tbar: ['->', {
                        xtype: 'button',
                        text: '迁移',
                        iconCls: 'move',
                        pressed: false,
                        handler: function () {
                        	var selectedDevice=Ext.getCmp("DeviceOrgChangeDeviceListGridPanel_Id").getSelectionModel().getSelection();
                        	var selectedDeviceType=Ext.getCmp("DeviceTypeChangeDeviceTypeTreeGridView_Id").getSelectionModel().getSelection();
                        	var deviceType=Ext.getCmp("DeviceOrgChangeWinDeviceType_Id").getValue();
                        	if(selectedDevice.length==0){
                        		Ext.MessageBox.alert("信息","请选择要迁移的设备！");
                        		return;
                        	}
                        	if(selectedDeviceType.length==0){
                        		Ext.MessageBox.alert("信息","请选择目的设备类型！");
                        		return;
                        	}else{
                        		if(!selectedDeviceType[0].isLeaf()){
                        			Ext.MessageBox.alert("信息","目的设备类型不是叶子节点！");
                        			return;
                        		}
                        	}
                        	
                        	var selectedDeviceId="";
                        	var selectedDeviceArr=[];
                        	var selectedDeviceTypeId=selectedDeviceType[0].data.deviceTypeId;
                        	var selectedDeviceTypeName=selectedDeviceType[0].data.text;
                        	for(var i=0;i<selectedDevice.length;i++){
                        		selectedDeviceArr.push(selectedDevice[i].data.id);
                        	}
                        	selectedDeviceId="" + selectedDeviceArr.join(",");
                        	
                        	Ext.Ajax.request({
                        		url : context + '/wellInformationManagerController/changeDeviceType',
                        		method : "POST",
                        		params : {
                        			selectedDeviceId : selectedDeviceId,
                        			selectedDeviceTypeId : selectedDeviceTypeId,
                        			selectedDeviceTypeName : selectedDeviceTypeName,
                        			deviceType : deviceType
                        		},
                        		success : function(response) {
                        			var result = Ext.JSON.decode(response.responseText);
                        			if (result.success == true) {
                        				Ext.Msg.alert('提示', "设备隶属类型迁移成功");
                        			}
                        			if (result.success == false) {
                        				Ext.Msg.alert('提示', "<font color=red>设备隶属类型迁移失败。</font>");
                        			}
                        		},
                        		failure : function() {
                        			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
                        		}
                        	});
                        }
            		}]
            	}]
            }]
        });
        me.callParent(arguments);
    }
});