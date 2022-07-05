Ext.define("AP.view.orgAndUser.OrgAndUserInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.orgAndUserInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var orgStore = Ext.create("AP.store.orgAndUser.OrgInfoStore");
        Ext.apply(me, {
        	items: [{
        		region:'center',
        		layout: "fit",
        		title:'组织列表',
        		header:false,
        		id:'OrgAndUserOrgInfoPanel_Id',
        		tbar: [{
                    iconCls: 'icon-collapse-all', // 收缩按钮
                    text: cosog.string.collapse,
                    tooltip: {
                        text: cosog.string.collapseAll
                    },
                    handler: function () {
                        Ext.getCmp("OrgInfoTreeGridView_Id").collapseAll();
                    }
                }, '-', {
                    iconCls: 'icon-expand-all', // 展开按钮
                    text: cosog.string.expand,
                    tooltip: {
                        text: cosog.string.expandAll
                    },
                    handler: function () {
                        Ext.getCmp("OrgInfoTreeGridView_Id").expandAll();
                    }
                }, '-', {
                    fieldLabel: cosog.string.orgName,
                    id: 'org_name_Id',
                    name: 'org_name',
                    emptyText: cosog.string.queryOrg,
                    labelWidth: 60,
                    labelAlign: 'right',
                    width: 165,
                    xtype: 'textfield'
                },'-', {
                    xtype: 'button',
                    text: cosog.string.search,
                    iconCls: 'search',
                    handler: function () {
                    	var gridPanel = Ext.getCmp("OrgInfoTreeGridView_Id");
                    	if (isNotVal(gridPanel)) {
                    		gridPanel.getStore().proxy.extraParams.tid = 0;
                    		gridPanel.getStore().load();
                    	}
                    }
                }, '->', {
                    xtype: 'button',
                    itemId: 'addOrgLableClassBtnId',
                    id: 'addOrgLableClassBtn_Id',
                    text: cosog.string.add,
                    iconCls: 'add',
                    handler: function () {
                    	addOrgInfo();
                    }
                }, "-", {
                    xtype: 'button',
                    itemId: 'editOrgLableClassBtnId',
                    id: 'editOrgLableClassBtn_Id',
                    text: cosog.string.update,
                    disabled: false,
                    iconCls: 'edit',
                    handler: function () {
                    	modifyOrgInfo();
                    }
                }, "-", {
                    xtype: 'button',
                    itemId: 'delOrgLableClassBtnId',
                    id: 'delOrgLableClassBtn_Id',
                    disabled: false,
                    action: 'delOrgAction',
                    text: cosog.string.del,
                    iconCls: 'delete',
                    handler: function () {
                    	delOrgInfo();
                    }
                }]
        	},{
        		region:'east',
        		width:'60%',
        		id:'OrgAndUserUserInfoPanel_Id',
        		title:'用户列表',
        		header:false,
        		split: true,
                collapsible: true,
        		layout: "fit",
        		tbar: [{
                    id: 'UserName_Id',
                    fieldLabel: cosog.string.userName,
                    emptyText: cosog.string.queryUserName,
                    labelWidth: 60,
                    width: 165,
                    labelAlign: 'right',
                    xtype: 'textfield'
        		},"-", {
                    xtype: 'button',
                    text: cosog.string.search,
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
                    text: cosog.string.add,
                    iconCls: 'add',
                    handler: function () {
                    	addUserInfo();
                    }
        		}, "-", {
                    xtype: 'button',
                    id: 'editUserLableClassBtn_Id',
                    text: cosog.string.update,
                    disabled: false,
                    iconCls: 'edit',
                    handler: function () {
                    	modifyUserInfo();
                    }
        		}, "-", {
                    xtype: 'button',
                    id: 'delUserLableClassBtn_Id',
                    disabled: false,
                    text: cosog.string.del,
                    iconCls: 'delete',
                    handler: function () {
                    	delUserInfo();
                    }
        		},"-", {
        			xtype: 'button',
        			text:'用户隶属迁移',
        			iconCls: 'move',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.orgAndUser.UserOrgChangeWindow", {
                            title: '用户隶属迁移'
                        });
                        window.show();
                        Ext.create("AP.store.orgAndUser.UserOrgChangeUserListStore");
                        Ext.create("AP.store.orgAndUser.UserOrgChangeOrgListStore");
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
			myColumns +=  ",dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
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
 	return '<span data-qtip="'+val+'" data-dismissDelay=10000>'+showVal+'</span>';
};