Ext.define("AP.view.acquisitionUnit.ProtocoDeviceTypeChangeWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.protocoDeviceTypeChangeWindow',
    id:'protocoDeviceTypeChangeWindow_Id',
    layout: 'fit',
    title:'协议隶属迁移',
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
                text: '迁移',
                iconCls: 'move',
                style: 'margin-right: 15px;margin-bottom: 5px',
                pressed: false,
                handler: function () {
                	var selectedProtocol=Ext.getCmp("ProtocolDeviceTypeChangeProtocolListGridPanel_Id").getSelectionModel().getSelection();
                	var selectedDeviceType=Ext.getCmp("ProtocolDeviceTypeChangeDeviceTypeTreeGridView_Id").getSelectionModel().getSelection();
                	if(selectedProtocol.length==0){
                		Ext.MessageBox.alert("信息","请选择要迁移的协议！");
                		return;
                	}
                	if(selectedDeviceType.length==0){
                		Ext.MessageBox.alert("信息","请选择设备类型！");
                		return;
                	}else{
                		if(!selectedDeviceType[0].isLeaf()){
//                			Ext.MessageBox.alert("信息","选择的设备类型不是叶子节点！");
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
                				Ext.Msg.alert('提示', "协议隶属迁移成功");
                				Ext.getCmp('protocoDeviceTypeChangeWindow_Id').close();
                				var treeGridPanel = Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id");
                                if (isNotVal(treeGridPanel)) {
                                	treeGridPanel.getStore().load();
                                }
                			}
                			if (result.success == false) {
                				Ext.Msg.alert('提示', "<font color=red>协议隶属迁移失败。</font>");
                			}
                		},
                		failure : function() {
                			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
                		}
                	});
                }
    		}],
        	layout: 'border',
            items: [{
            	region: 'center',
        		title:'协议列表',
        		layout: 'fit',
        		id:'ProtocolDeviceTypeChangeWinProtocolListPanel_Id'
            },{
            	region: 'east',
        		width: '40%',
        		title:'目标类型',
        		layout: 'fit',
        		id:'ProtocolDeviceTypeChangeWinDeviceTypeListPanel_Id'
            }]
        });
        me.callParent(arguments);
    }
});