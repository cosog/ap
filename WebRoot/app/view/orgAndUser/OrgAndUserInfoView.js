var loginUserOrgAndUserModuleRight=getRoleModuleRight('OrganizationAndUserManagement');
Ext.define("AP.view.orgAndUser.OrgAndUserInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.orgAndUserInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar:[{
            	id: 'OrgAndUserModuleViewFlag',
            	xtype: 'textfield',
                value: loginUserOrgAndUserModuleRight.viewFlag,
                hidden: true
             },{
            	id: 'OrgAndUserModuleEditFlag',
            	xtype: 'textfield',
                value: loginUserOrgAndUserModuleRight.editFlag,
                hidden: true
             },{
            	id: 'OrgAndUserModuleControlFlag',
            	xtype: 'textfield',
                value: loginUserOrgAndUserModuleRight.controlFlag,
                hidden: true
            }],
        	items: [{
        		region:'west',
        		width:'30%',
        		layout: "fit",
        		title:'组织列表',
        		header:false,
        		split: true,
                collapsible: true,
        		id:'OrgAndUserOrgInfoPanel_Id',
        		tbar: [{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("OrgInfoTreeGridView_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.orgAndUser.OrgInfoStore');
                        }
                    }
        		},'->', {
                    xtype: 'button',
                    itemId: 'addOrgLableClassBtnId',
                    id: 'addOrgLableClassBtn_Id',
                    text: loginUserLanguageResource.add,
                    iconCls: 'add',
                    disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                    handler: function () {
                    	addOrgInfo();
                    }
                }, "-", {
                    xtype: 'button',
                    itemId: 'editOrgLableClassBtnId',
                    id: 'editOrgLableClassBtn_Id',
                    text: loginUserLanguageResource.update,
                    disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                    iconCls: 'edit',
                    handler: function () {
                    	modifyOrgInfo();
                    }
                }, "-", {
                    xtype: 'button',
                    itemId: 'delOrgLableClassBtnId',
                    id: 'delOrgLableClassBtn_Id',
                    disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                    action: 'delOrgAction',
                    text: loginUserLanguageResource.deleteData,
                    iconCls: 'delete',
                    handler: function () {
                    	delOrgInfo();
                    }
                },"-", {
        			xtype: 'button',
        			text:loginUserLanguageResource.orgParentChange,
        			iconCls: 'move',
        			disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.orgAndUser.OrgParentChangeWindow");
                        window.show();
                        Ext.create("AP.store.orgAndUser.OrgParentChangeCurrentOrgListStore");
                        Ext.create("AP.store.orgAndUser.OrgParentChangeDestinationOrgListStore");
        			}
        		},"-", {
                    xtype: 'button',
                    text: loginUserLanguageResource.exportData,
                    iconCls: 'export',
                    disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                    hidden: false,
                    handler: function (v, o) {
                    	exportOrganizationCompleteData();
                    }
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.importData,
        			disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
        			iconCls: 'import',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.orgAndUser.ImportOrganizationWindow");
                        window.show();
        			}
                }]
        	},{
        		region:'center',
        		id:'OrgAndUserUserInfoPanel_Id',
        		title:loginUserLanguageResource.userList,
        		header:false,
        		layout: "fit",
        		tbar: [{
                    id: 'UserName_Id',
                    fieldLabel: loginUserLanguageResource.userName,
                    emptyText: '',
                    labelWidth: (getLabelWidth(loginUserLanguageResource.userName,loginUserLanguage)),
                    width: (getLabelWidth(loginUserLanguageResource.userName,loginUserLanguage)+100),
                    labelAlign: 'right',
                    xtype: 'textfield'
        		},"-", {
                    xtype: 'button',
                    text: loginUserLanguageResource.search,
                    iconCls: 'search',
                    handler: function () {
                    	var gridPanel = Ext.getCmp("UserInfoGridPanel_Id");
                    	if (isNotVal(gridPanel)) {
                    		gridPanel.getStore().load();
                    	}
                    }
        		}, '->', {
                    xtype: 'button',
                    id: 'addUserLableClassBtn_Id',
                    text: loginUserLanguageResource.add,
                    iconCls: 'add',
                    disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                    handler: function () {
                    	addUserInfo();
                    }
        		}, "-", {
                    xtype: 'button',
                    id: 'editUserLableClassBtn_Id',
                    text: loginUserLanguageResource.passwordReset,
                    disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                    iconCls: 'edit',
                    handler: function () {
                    	modifyUserInfo();
                    }
        		},"-", {
        			xtype: 'button',
        			text:loginUserLanguageResource.userOrgChange,
        			iconCls: 'move',
        			disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.orgAndUser.UserOrgChangeWindow", {
                            title: loginUserLanguageResource.userOrgChange
                        });
                        window.show();
                        Ext.create("AP.store.orgAndUser.UserOrgChangeUserListStore");
                        Ext.create("AP.store.orgAndUser.UserOrgChangeOrgListStore");
        			}
        		},"-", {
                    xtype: 'button',
                    text: loginUserLanguageResource.exportData,
                    iconCls: 'export',
                    disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                    hidden: false,
                    handler: function (v, o) {
                    	exportUserCompleteData();
                    }
                }]
        	}]
        });
        me.callParent(arguments);
    }
});

createOrgTreeHeadColumns = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns ="[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        var flex_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
        myColumns += "{header:'" + attr.header + "',lockable:true,align:'left' "+width_+flex_;
		if (attr.dataIndex=='text'){
			myColumns +=",xtype: 'treecolumn',dataIndex:'"+attr.dataIndex+"'";
		}else {
			myColumns +=",dataIndex:'"+attr.dataIndex+"'";
		}
		myColumns +="}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns +="]";
	
	return myColumns;
};

createUserGridColumn = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns = "[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		var width_="";
		var flex_ = "";
		var lock_="";
		var hidden_="";
		if(isNotVal(attr.lock)){
//			lock_=",locked:"+attr.lock;
		}
		if(isNotVal(attr.hidden==true)){
			hidden_=",hidden:"+attr.hidden;
		}
		if(isNotVal(attr.width)){
			width_=",width:"+attr.width;
		}
		if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
		myColumns += "{header:'" + attr.header + "',lockable:true,align:'center',sortable:true"+width_+flex_+hidden_+lock_;
		if (attr.dataIndex=='id'){
			 myColumns +=",xtype: 'rownumberer'" ;
		}else if (attr.dataIndex.toUpperCase()=='userName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCurrentUserName(value,o,p,e);}";
        }else if(attr.dataIndex=='userRegtime'){
//			myColumns +=",dataIndex:'" + attr.dataIndex + "',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')";
			myColumns +=",dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
		}else {
			myColumns +=  ",dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
		}
		myColumns += "}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns +="]";
	return myColumns;
};

adviceCurrentUserName = function(val,o,p,e) {
 	var showVal=val;
 	if(p.data.userNo==user_){
 		showVal="*"+val;
 	}
 	if(isNotVal(showVal)){
 		return '<span data-qtip="'+val+'" data-dismissDelay=10000>'+showVal+'</span>';
 	}
};

function exportOrganizationCompleteData(){
	var url = context + '/orgManagerController/exportOrganizationCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportOrganizationCompleteData'+'_'+timestamp;
	var maskPanelId='OrgAndUserOrgInfoPanel_Id';
	
	
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.organizationExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}

function exportUserCompleteData(){
	var url = context + '/userManagerController/exportUserCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportUserCompleteData'+'_'+timestamp;
	var maskPanelId='OrgAndUserUserInfoPanel_Id';
	
	
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.userExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}

