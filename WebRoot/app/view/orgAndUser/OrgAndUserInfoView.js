Ext.define("AP.view.orgAndUser.OrgAndUserInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.orgAndUserInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	items: [{
        		region:'west',
        		width:'25%',
        		layout: "fit",
        		title:'组织列表',
        		header:false,
        		split: true,
                collapsible: true,
        		id:'OrgAndUserOrgInfoPanel_Id',
        		tbar: [{
                    xtype: 'button',
                    text: cosog.string.refresh,
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
                },"-", {
        			xtype: 'button',
        			text:'组织隶属迁移',
        			iconCls: 'move',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.orgAndUser.OrgParentChangeWindow");
                        window.show();
                        Ext.create("AP.store.orgAndUser.OrgParentChangeCurrentOrgListStore");
                        Ext.create("AP.store.orgAndUser.OrgParentChangeDestinationOrgListStore");
        			}
        		}]
        	},{
        		
        		region:'center',
        		id:'OrgAndUserUserInfoPanel_Id',
        		title:'用户列表',
        		header:false,
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
                    text: '修改密码',
                    disabled: false,
                    iconCls: 'edit',
                    handler: function () {
                    	modifyUserInfo();
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