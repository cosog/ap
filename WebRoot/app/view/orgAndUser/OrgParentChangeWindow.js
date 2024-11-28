Ext.define("AP.view.orgAndUser.OrgParentChangeWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.orgParentChangeWindow',
    layout: 'fit',
    title:loginUserLanguageResource.orgParentChange,
    id: 'orgParentChangeWindow_Id',
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
                	var selectedCurrentOrg=Ext.getCmp("OrgParentChangeWinCurrentOrgListTreePanel_Id").getSelectionModel().getSelection();
                	var selectedDestinationOrg=Ext.getCmp("OrgParentChangeWinDestinationOrgListTreePanel_Id").getSelectionModel().getSelection();
                	if(selectedCurrentOrg.length==0){
                		Ext.MessageBox.alert(loginUserLanguageResource.message,"请选择要迁移的组织！");
                		return;
                	}
                	if(selectedDestinationOrg.length==0){
                		Ext.MessageBox.alert(loginUserLanguageResource.message,"请选择目的组织！");
                		return;
                	}
                	var selectedCurrentOrgId="";
                	var selectedCurrentOrgArr=[];
                	var selectedDestinationOrgId=selectedDestinationOrg[0].data.orgId;
                	for(var i=0;i<selectedCurrentOrg.length;i++){
                		selectedCurrentOrgArr.push(selectedCurrentOrg[i].data.orgId);
                	}
                	selectedCurrentOrgId="" + selectedCurrentOrgArr.join(",");
                	Ext.Ajax.request({
                		url : context + '/orgManagerController/changeOrgParent',
                		method : "POST",
                		params : {
                			selectedCurrentOrgId : selectedCurrentOrgId,
                			selectedDestinationOrgId : selectedDestinationOrgId
                		},
                		success : function(response) {
                			var result = Ext.JSON.decode(response.responseText);
                			Ext.getCmp("IframeView_Id").getStore().load();
                			if (result.success == true && result.resultStatus>0) {
                				Ext.getCmp('orgParentChangeWindow_Id').close();
                				Ext.getCmp("IframeView_Id").getStore().load();//右侧组织数刷新
                				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.changeOwnerSuccess);
                			}else if (result.success == true && result.resultStatus==-1) {
                				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.orgChangeOwnerFai+"</font>");
                			}else if (result.success == false) {
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
        		title:loginUserLanguageResource.orgList,
        		layout: 'fit',
        		id:'OrgParentChangeWinCurrentOrgListPanel_Id'
            },{
            	region: 'east',
        		width: '40%',
        		title:loginUserLanguageResource.targetOrg,
        		layout: 'fit',
        		id:'OrgParentChangeWinDestinationOrgListPanel_Id'
            }]
        });
        me.callParent(arguments);
    }
});