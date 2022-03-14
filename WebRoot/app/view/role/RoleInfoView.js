Ext.define("AP.view.role.RoleInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.roleInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var RoleInfoGridPanel = Ext.create('AP.view.role.RoleInfoGridPanel');
        var RightModuleInfoGridPanel = Ext.create('AP.view.role.RightModuleInfoTreeGridView');
        Ext.apply(me, {
        	items: [{
        		region:'center',
        		layout: "fit",
        		title:'角色列表',
        		header:false,
        		items:RoleInfoGridPanel
        	},{
        		region:'east',
        		width:'25%',
        		title:'模块授权',
        		header:false,
        		split: true,
                collapsible: true,
        		layout: "fit",
        		items:RightModuleInfoGridPanel,
        		tbar: [{
                    xtype: 'label',
                    html: '模块授权',
                    style: 'margin-left: 4px'
                },'->', {
                    xtype: 'button',
                    itemId: 'addRightModuleLableClassBtnId',
                    id: 'addRightModuleLableClassBtn_Id',
                    text: '保存',
                    iconCls: 'save',
                    pressed: false,
                    handler: function () {
                    	grantRolePermission();
                    }
        		}]
        	}]
        });
        me.callParent(arguments);
    }
});

createRoleGridColumn = function(columnInfo) {
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
		}else if (attr.dataIndex.toUpperCase()=='roleName'.toUpperCase()) {
            myColumns += ",dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCurrentRoleName(value,o,p,e);}";
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

adviceCurrentRoleName = function(val,o,p,e) {
 	var showVal=val;
 	var currentRoleId=Ext.getCmp("currentUserRoleId_Id").getValue();
 	if(p.data.roleId==parseInt(currentRoleId)){
 		showVal="*"+val;
 	}
 	return '<span data-qtip="'+val+'" data-dismissDelay=10000>'+showVal+'</span>';
};