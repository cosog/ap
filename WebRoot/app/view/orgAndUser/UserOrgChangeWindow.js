Ext.define("AP.view.orgAndUser.UserOrgChangeWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.userOrgChangeWindow',
    layout: 'fit',
    title:'用户隶属迁移',
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
                	var selectedUser=Ext.getCmp("UserOrgChangeUserListGridPanel_Id").getSelectionModel().getSelection();
                	var selectedOrg=Ext.getCmp("UserOrgChangeOrgListTreePanel_Id").getSelectionModel().getSelection();
                	if(selectedUser.length==0){
                		Ext.MessageBox.alert("信息","请选择要迁移的用户！");
                		return;
                	}
                	if(selectedOrg.length==0){
                		Ext.MessageBox.alert("信息","请选择目的组织！");
                		return;
                	}
                	var selectedUserId="";
                	var selectedUserArr=[];
                	var selectedOrgId=selectedOrg[0].data.orgId;
                	for(var i=0;i<selectedUser.length;i++){
                		selectedUserArr.push(selectedUser[i].data.id);
                	}
                	selectedUserId="" + selectedUserArr.join(",");
                	
                	Ext.Ajax.request({
                		url : context + '/userManagerController/changeUserOrg',
                		method : "POST",
                		params : {
                			selectedUserId : selectedUserId,
                			selectedOrgId : selectedOrgId
                		},
                		success : function(response) {
                			var result = Ext.JSON.decode(response.responseText);
                			Ext.getCmp("IframeView_Id").getStore().load();
                			if (result.success == true) {
                				Ext.Msg.alert('提示', "用户隶属迁移成功");
                			}
                			if (result.success == false) {
                				Ext.Msg.alert('提示', "<font color=red>用户隶属迁移失败。</font>");
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
        		title:'用户列表',
        		layout: 'fit',
        		id:'UserOrgChangeWinUserListPanel_Id'
            },{
            	region: 'east',
        		width: '40%',
        		title:'目标组织',
        		layout: 'fit',
        		id:'UserOrgChangeWinOrgListPanel_Id'
            }]
        });
        me.callParent(arguments);
    }
});