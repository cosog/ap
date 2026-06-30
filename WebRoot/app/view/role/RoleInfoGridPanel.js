Ext.define('AP.view.role.RoleInfoGridPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.roleInfoGridPanel',
    id: 'RoleInfoGridPanelView_id',
    layout: "fit",
    border: false,
    initComponent: function () {
        var roleStore = Ext.create("AP.store.role.RoleInfoStore");
        Ext.apply(this, {
        	tbar: [{
                xtype: "hidden",
                id: 'currentUserRoleId_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'currentUserRoleLevel_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'currentUserRoleShowLevel_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'currentUserRoleVideoKeyEdit_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'currentUserRoleLanguageEdit_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'addRoleFlag_Id',
                value: ''
            },{
                xtype: "hidden",
                id: 'selectedRoleId_Id',
                value: 0
            },{
                xtype: 'button',
                text: loginUserLanguageResource.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	var gridPanel = Ext.getCmp("RoleInfoGridPanel_Id");
                    if (isNotVal(gridPanel)) {
                    	gridPanel.getStore().load();
                    }else{
                    	Ext.create('AP.store.role.RoleInfoStore');
                    }
                }
    		},{
                id: 'RoleName_Id',
                fieldLabel: loginUserLanguageResource.roleName,
                name: 'RoleName',
                labelWidth: (getLabelWidth(loginUserLanguageResource.roleName,loginUserLanguage)),
                width: (getLabelWidth(loginUserLanguageResource.roleName,loginUserLanguage)+100),
                labelAlign: 'right',
                xtype: 'textfield'
    		}, {
                xtype: 'button',
                name: 'RoleNameBtn_Id',
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                handler: function () {
                    roleStore.load();
                }
    		}, '->', {
                xtype: 'button',
                itemId: 'addroleLabelClassBtnId',
                id: 'addroleLabelClassBtn_Id',
                action: 'addroleAction',
                text: loginUserLanguageResource.add,
                disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                iconCls: 'add'
    		},'-',{
                xtype: 'button',
                text: loginUserLanguageResource.deleteData,
                disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                iconCls: 'delete',
                handler: function () {
                	var selectRoleId=[];
                	var RoleInfoGridPanel = Ext.getCmp("RoleInfoGridPanel_Id");
                	var selectionModel = RoleInfoGridPanel.getSelectionModel();
                    var selectionRecord = selectionModel.getSelection();
                	if(selectionRecord.length>0){
                		Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDelete, function (btn) {
                            if (btn == "yes") {
                            	var currentId=Ext.getCmp("currentUserRoleId_Id").getValue();
                            	
                            	if(selectionRecord.length==1 && parseInt(selectionRecord[0].data.roleId)==parseInt(currentId)){
                            		Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.cannotDeleteLoginUserRole);
                            	}else{
                            		selectionRecord.forEach(function(record) {
                                		if (parseInt(record.data.roleId)!=parseInt(currentId)){
                                			selectRoleId.push(record.data.roleId);
                                		}
        	                	    });
                            		if(selectRoleId.length>0){
                            			Ext.Ajax.request({
            	                			method:'POST',
            	                			url : context + '/roleManagerController/doRoleBulkDelete',
            	                			success:function(response) {
            	                				var result = Ext.JSON.decode(response.responseText);
            	                  				if (result.flag == true) {
            	                  					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteSuccessfully);
            	                  				}
            	                  				if (result.flag == false) {
            	                  					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.deleteFailed+"</font>");
            	                  				}
            	                  				Ext.getCmp("selectedRoleId_Id").setValue(0);
            	                  				Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();
            	                			},
            	                			failure:function(){
            	                				Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailed);
            	                			},
            	                			params: {
            	                				paramsId: selectRoleId.join(",")
            	                	        }
            	                		});
                            		}
                            		
                            	}
                            }
                        });
                	} else {
                        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
                    }
                	
                }
    		},'-',{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                iconCls: 'save',
                handler: function () {
                	var RoleInfoGridPanel = Ext.getCmp("RoleInfoGridPanel_Id");
                	var store=RoleInfoGridPanel.getStore();
                	var modifiedRecords = store.getModifiedRecords();
                	updateRoleInfo(modifiedRecords);
                }
    		},"-", {
                xtype: 'button',
                text: loginUserLanguageResource.exportData,
                iconCls: 'export',
                disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                hidden: false,
                handler: function (v, o) {
                	exportRoleCompleteData();
                }
            },"-",{
            	xtype: 'button',
    			text: loginUserLanguageResource.importData,
    			disabled:loginUserRoleManagerModuleRight.editFlag!=1,
    			iconCls: 'import',
    			handler: function (v, o) {
    				var window = Ext.create("AP.view.role.ImportRoleWindow");
                    window.show();
    			}
            }]
        });
        this.callParent(arguments);
    }
});

function exportRoleCompleteData(){
	var url = context + '/roleManagerController/exportRoleCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportRoleCompleteData'+'_'+timestamp;
	var maskPanelId='RoleInfoGridPanelView_id';
	
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.roleExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loadingData);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}