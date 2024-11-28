Ext.define("AP.view.orgAndUser.UserOrgChangeWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.userOrgChangeWindow',
    layout: 'fit',
    title:loginUserLanguageResource.userOrgChange,
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
        Ext.apply(me, {
        	bbar: ['->', {
                xtype: 'button',
                text: loginUserLanguageResource.changeOwner,
                iconCls: 'move',
                style: 'margin-right: 15px;margin-bottom: 5px',
                pressed: false,
                handler: function () {
                	var selectedUser=Ext.getCmp("UserOrgChangeUserListGridPanel_Id").getSelectionModel().getSelection();
                	var selectedOrg=Ext.getCmp("UserOrgChangeOrgListTreePanel_Id").getSelectionModel().getSelection();
                	if(selectedUser.length==0){
                		Ext.MessageBox.alert(loginUserLanguageResource.message,"请选择要迁移的用户！");
                		return;
                	}
                	if(selectedOrg.length==0){
                		Ext.MessageBox.alert(loginUserLanguageResource.message,"请选择目的组织！");
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
                				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.changeOwnerSuccess);
                			}
                			if (result.success == false) {
                				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.changeOwnerFail+"</font>");
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
        		title:loginUserLanguageResource.userList,
        		layout: 'fit',
        		id:'UserOrgChangeWinUserListPanel_Id'
            },{
            	region: 'east',
        		width: '25%',
        		title:loginUserLanguageResource.targetOrg,
        		layout: 'fit',
        		id:'UserOrgChangeWinOrgListPanel_Id'
            }]
        });
        me.callParent(arguments);
    }
});