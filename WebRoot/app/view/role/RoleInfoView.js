var loginUserRoleManagerModuleRight=getRoleModuleRight('RoleManagement');
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
        var RightTabInfoTreeGridView = Ext.create('AP.view.role.RightTabInfoTreeGridView');
        Ext.apply(me, {
        	tbar:[{
            	id: 'RoleManagerModuleViewFlag',
            	xtype: 'textfield',
                value: loginUserRoleManagerModuleRight.viewFlag,
                hidden: true
             },{
            	id: 'RoleManagerModuleEditFlag',
            	xtype: 'textfield',
                value: loginUserRoleManagerModuleRight.editFlag,
                hidden: true
             },{
            	id: 'RoleManagerModuleControlFlag',
            	xtype: 'textfield',
                value: loginUserRoleManagerModuleRight.controlFlag,
                hidden: true
            }],
        	items: [{
        		region:'center',
        		layout: "fit",
        		title:'角色列表',
        		header:false,
        		items:RoleInfoGridPanel
        	},{
        		region:'east',
        		width:'25%',
        		layout: 'border',
        		header:false,
        		items:[{
        			region:'center',
        			layout: "fit",
        			title:loginUserLanguageResource.moduleLicense,
            		split: true,
                    collapsible: true,
            		layout: "fit",
            		items:RightModuleInfoGridPanel,
            		tbar: [{
                        xtype: 'label',
                        html: loginUserLanguageResource.moduleLicense,
                        id:'RightModuleTreeInfoLabel_Id',
                        style: 'margin-left: 4px'
                    },'->', {
                        xtype: 'button',
                        itemId: 'addRightModuleLableClassBtnId',
                        id: 'addRightModuleLableClassBtn_Id',
                        text: loginUserLanguageResource.save,
                        iconCls: 'save',
                        pressed: false,
                        disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                        handler: function () {
                        	grantRolePermission();
                        }
            		}]
        		},{
        			region:'south',
        			height:'30%',
        			title:loginUserLanguageResource.deviceTypeLicense,
        			split: true,
                    collapsible: true,
            		layout: "fit",
            		items:RightTabInfoTreeGridView,
            		tbar: [{
                        xtype: 'label',
                        html: loginUserLanguageResource.deviceTypeLicense,
                        id:'RightTabTreeInfoLabel_Id',
                        style: 'margin-left: 4px'
                    },'->', {
                        xtype: 'button',
                        itemId: 'addRightTabLableClassBtnId',
                        id: 'addRightTabLableClassBtn_Id',
                        text: loginUserLanguageResource.save,
                        iconCls: 'save',
                        disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                        pressed: false,
                        handler: function () {
                        	grantRoleTabPermission();
                        }
            		}]
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

adviceCurrentRoleName = function(val,o,p,e) {
 	var showVal=val;
 	var currentRoleId=Ext.getCmp("currentUserRoleId_Id").getValue();
 	if(p.data.roleId==parseInt(currentRoleId)){
 		showVal="*"+val;
 	}
 	if(isNotVal(showVal)){
 	 	return '<span data-qtip="'+val+'" data-dismissDelay=10000>'+showVal+'</span>';
 	}
};

function selectEachCombox(node, root) {
    if (null != root && root != "") {
        var chlidArray = node;
        if (!Ext.isEmpty(chlidArray)) {
            Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
                var x_node_seId = chlidArray[index].data.mdId;
                Ext.Array.each(root, function (name, index,countriesItSelf) {
                    var menuselectid = root[index].rmModuleid;
                   
                    // 处理已选择的节点
                    if (x_node_seId == menuselectid) {
//                        childArrNode.set('checked', true);
                        var rmMatrix=root[index].rmMatrix;
                        if(isNotVal(rmMatrix)){
                        	var rmMatrixArr=rmMatrix.split(",");
                        	if(rmMatrixArr.length==3){
                        		childArrNode.set('viewFlagName', parseInt(rmMatrixArr[0])==1?true:false);
                        		childArrNode.set('editFlagName', parseInt(rmMatrixArr[1])==1?true:false);
                        		childArrNode.set('controlFlagName', parseInt(rmMatrixArr[2])==1?true:false);
                        	}
                        }
                        childArrNode.expand('true');
                    }
                });
                // 递归
                if (childArrNode.childNodes != null) {
                    selectEachCombox(childArrNode.childNodes, root);
                }
            });
        }
    }
    return false;
};

function clearRoleRightModuleTreeSelect(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
//		childArrNode.set('checked', false);
		childArrNode.set('viewFlagName', false);
		childArrNode.set('editFlagName', false);
		childArrNode.set('controlFlagName', false);
		if (childArrNode.childNodes != null) {
			clearRoleRightModuleTreeSelect(childArrNode.childNodes);
        }
	});
}

clkLoadAjaxFn = function () {
	var treeGridPanel=Ext.getCmp("RightModuleTreeInfoGridPanel_Id");
	if(isNotVal(treeGridPanel)){
		var store_=treeGridPanel.getStore();
		var getNode = store_.root.childNodes;
		clearRoleRightModuleTreeSelect(getNode);
	}
	
	var RightBottomRoleId = Ext.getCmp("RightBottomRoleCodes_Id").getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/moduleShowRightManagerController/doShowRightCurrentRoleOwnModules?roleId=' + RightBottomRoleId,
        success: function (response, opts) {
            // 处理后
            var moduleIds = Ext.decode(response.responseText);
            var store_=Ext.getCmp("RightModuleTreeInfoGridPanel_Id").getStore();
            if (null != moduleIds && moduleIds != "") {
                var getNode = store_.root.childNodes;
                selectEachCombox(getNode, moduleIds);
            }
        },
        failure: function (response, opts) {
            Ext.Msg.alert("信息提示", "后台获取数据失败！");
        }
    });
    return false;
}

function clearRoleRightTabTreeSelect(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', false);
		if (childArrNode.childNodes != null) {
			clearRoleRightTabTreeSelect(childArrNode.childNodes);
        }
	});
}

function selectEachTabCombox(node, root) {
    if (null != root && root != "") {
        var chlidArray = node;
        if (!Ext.isEmpty(chlidArray)) {
            Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
                var x_node_seId = chlidArray[index].data.deviceTypeId;

                Ext.Array.each(root, function (name, index,
                    countriesItSelf) {
                    var menuselectid = root[index].rdDeviceTypeId;
                    // 处理已选择的节点
                    if (x_node_seId == menuselectid) {
                        childArrNode.set('checked', true);
                        childArrNode.expand('true');
                    }
                });
                // 递归
                if (childArrNode.childNodes != null) {
                	selectEachTabCombox(childArrNode.childNodes, root);
                }
            });
        }
    }
    return false;
};

clkLoadTabAjaxFn = function () {
	var treeGridPanel=Ext.getCmp("RightTabTreeInfoGridPanel_Id");
	if(isNotVal(treeGridPanel)){
		var store_=treeGridPanel.getStore();
		var getNode = store_.root.childNodes;
		clearRoleRightTabTreeSelect(getNode);
	}
	
	var RightBottomRoleId = Ext.getCmp("RightBottomRoleCodes_Id").getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/moduleShowRightManagerController/doShowRightCurrentRoleOwnTabs?roleId=' + RightBottomRoleId,
        success: function (response, opts) {
            // 处理后
            var deviceTypeIds = Ext.decode(response.responseText);
            var store_=Ext.getCmp("RightTabTreeInfoGridPanel_Id").getStore();
            if (null != deviceTypeIds && deviceTypeIds != "") {
                var getNode = store_.root.childNodes;
                selectEachTabCombox(getNode, deviceTypeIds);
            }
        },
        failure: function (response, opts) {
            Ext.Msg.alert("信息提示", "后台获取数据失败！");
        }
    });
    return false;
}