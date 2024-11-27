Ext.define("AP.view.acquisitionUnit.ProtocoDeviceTypeChangeWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.protocoDeviceTypeChangeWindow',
    id:'protocoDeviceTypeChangeWindow_Id',
    layout: 'fit',
    title:loginUserLanguageResource.protocoDeviceTypeChange,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 600,
    minWidth: 600,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	bbar: ['->', {
                xtype: 'button',
                text: loginUserLanguageResource.changeOwner,
                iconCls: 'move',
                style: 'margin-right: 15px;margin-bottom: 5px',
                pressed: false,
                handler: function () {
                	var selectedProtocol=Ext.getCmp("ProtocolDeviceTypeChangeProtocolListGridPanel_Id").getSelectionModel().getSelection();
                	var selectedDeviceType=Ext.getCmp("ProtocolDeviceTypeChangeDeviceTypeTreeGridView_Id").getSelectionModel().getSelection();
                	if(selectedProtocol.length==0){
                		Ext.MessageBox.alert(loginUserLanguageResource.message,"请选择要迁移的协议！");
                		return;
                	}
                	if(selectedDeviceType.length==0){
                		Ext.MessageBox.alert(loginUserLanguageResource.message,"请选择设备类型！");
                		return;
                	}else{
                		if(!selectedDeviceType[0].isLeaf()){
//                			Ext.MessageBox.alert(loginUserLanguageResource.message,"选择的设备类型不是叶子节点！");
//                			return;
                		}
                	}
                	var selectedProtocolId="";
                	var selectedProtocolArr=[];
                	var selectedDeviceTypeId=selectedDeviceType[0].data.deviceTypeId;
                	for(var i=0;i<selectedProtocol.length;i++){
                		selectedProtocolArr.push(selectedProtocol[i].data.id);
                	}
                	selectedProtocolId="" + selectedProtocolArr.join(",");
                	
                	Ext.Ajax.request({
                		url : context + '/acquisitionUnitManagerController/changeProtocolDeviceType',
                		method : "POST",
                		params : {
                			selectedProtocolId : selectedProtocolId,
                			selectedDeviceTypeId : selectedDeviceTypeId
                		},
                		success : function(response) {
                			var result = Ext.JSON.decode(response.responseText);
                			Ext.getCmp("IframeView_Id").getStore().load();
                			if (result.success == true) {
                				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.changeProtocolBelongToSuccess);
                				Ext.getCmp('protocoDeviceTypeChangeWindow_Id').close();
                				var treeGridPanel = Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id");
                                if (isNotVal(treeGridPanel)) {
                                	treeGridPanel.getStore().load();
                                }
                			}
                			if (result.success == false) {
                				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.changeProtocolBelongToFail+"</font>");
                			}
                		},
                		failure : function() {
                			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
                		}
                	});
                }
    		}],
        	layout: 'border',
            items: [{
            	region: 'center',
        		title:loginUserLanguageResource.protocolList,
        		layout: 'fit',
        		id:'ProtocolDeviceTypeChangeWinProtocolListPanel_Id'
            },{
            	region: 'east',
        		width: '40%',
        		title:loginUserLanguageResource.targetType,
        		layout: 'fit',
        		id:'ProtocolDeviceTypeChangeWinDeviceTypeListPanel_Id'
            }]
        });
        me.callParent(arguments);
    }
});