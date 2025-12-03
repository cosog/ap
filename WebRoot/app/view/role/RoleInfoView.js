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
        var RightLanguageInfoTreeGridView = Ext.create('AP.view.role.RightLanguageInfoTreeGridView');
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
        		width:'40%',
        		layout: 'border',
        		header:false,
        		split: true,
                collapsible: true,
                tbar:['->',{
                    xtype: 'button',
                    itemId: 'roleGrantRightBtnId',
                    id: 'roleGrantRightBtn_Id',
                    text: loginUserLanguageResource.save,
                    iconCls: 'save',
                    pressed: false,
                    disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                    handler: function () {
                    	var rightmodule_panel = Ext.getCmp("RightModuleTreeInfoGridPanel_Id");
                    	var righttab_panel = Ext.getCmp("RightTabTreeInfoGridPanel_Id");
                    	var rightLanguageGridPanel = Ext.getCmp("RightLanguageTreeInfoGridPanel_Id");
                    	
                    	var selectedModuleCount=0;
                    	var selectedDeviceTypeCount=0;
                    	var selectLanguageCount=0;
                    	
                    	var roleLevel="";
                    	if(Ext.getCmp("RoleInfoGridPanel_Id")!=undefined){
                        	var _record = Ext.getCmp("RoleInfoGridPanel_Id").getSelectionModel().getSelection();
                            if(_record.length>0){
                            	roleLevel=_record[0].data.roleLevel;
                            }
                        }
                    	
                    	var moduleRecord = rightmodule_panel.store.data.items;
                    	Ext.Array.each(moduleRecord, function (name, index, countriesItSelf) {
                            var checked=moduleRecord[index].get('viewFlagName');
                            if(checked){
                            	selectedModuleCount++;
                            }
                        });
                    	
                    	if(parseInt(roleLevel)==1){//如果是超级管理员，授予所有模块权限
                    		selectedDeviceTypeCount = righttab_panel.store.data.items.length;
                    		selectLanguageCount = rightLanguageGridPanel.store.data.items.length;
                        }else{
                        	selectedDeviceTypeCount = righttab_panel.getChecked().length;
                        	selectLanguageCount = rightLanguageGridPanel.getChecked().length;
                        }
                    	
                    	if(selectedModuleCount>0 && selectedDeviceTypeCount>0 && selectLanguageCount>0){
                    		grantRolePermission();
                        	grantRoleTabPermission();
                        	grantRoleLanguagePermission();
                    	}else{
                    		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
                    	}
                    	
                    	
                    }
        		}],
        		items:[{
        			region:'center',
        			layout: "fit",
        			title:loginUserLanguageResource.moduleLicense,
        			header:false,
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
                        hidden:true,
                        pressed: false,
                        disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                        handler: function () {
                        	grantRolePermission();
                        }
            		}]
        		},{
        			region:'east',
        			width:'40%',
        			split: true,
                    collapsible: true,
                    header:false,
                    layout: 'border',
                    items:[{
                    	region:'center',
                    	title:loginUserLanguageResource.deviceTypeLicense,
                    	header:false,
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
                            hidden:true,
                            handler: function () {
                            	grantRoleTabPermission();
                            }
                		}]
                    },{
                    	region:'south',
            			height:'50%',
            			split: true,
                        collapsible: true,
                        title:loginUserLanguageResource.languageLicense,
                        header:false,
                        layout: "fit",
                        items: RightLanguageInfoTreeGridView,
                        tbar: [{
                            xtype: 'label',
                            html: loginUserLanguageResource.languageLicense,
                            id:'RightLanguageTreeInfoLabel_Id',
                            style: 'margin-left: 4px'
                        },'->', {
                            xtype: 'button',
                            itemId: 'addRightLanguageLableClassBtnId',
                            id: 'addRightLanguageLableClassBtn_Id',
                            text: loginUserLanguageResource.save,
                            iconCls: 'save',
                            disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                            pressed: false,
                            hidden:true,
                            handler: function () {
                            	grantRoleLanguagePermission();
                            }
                		}]
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
            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
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
            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
        }
    });
    return false;
}

function clearRoleRightLanguageTreeSelect(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', false);
		if (childArrNode.childNodes != null) {
			clearRoleRightLanguageTreeSelect(childArrNode.childNodes);
        }
	});
}


function selectEachLanguageCombox(node, root) {
    if (null != root && root != "") {
        var chlidArray = node;
        if (!Ext.isEmpty(chlidArray)) {
            Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
                var x_node_seId = chlidArray[index].data.languageId;
                Ext.Array.each(root, function (name, index,countriesItSelf) {
                    var menuselectid = root[index].language;
                    // 处理已选择的节点
                    if (x_node_seId == menuselectid) {
                        childArrNode.set('checked', true);
                    }
                });
                // 递归
                if (childArrNode.childNodes != null) {
                	selectEachLanguageCombox(childArrNode.childNodes, root);
                }
            });
        }
    }
    return false;
};

clkLoadLanguageAjaxFn = function () {
	var treeGridPanel=Ext.getCmp("RightLanguageTreeInfoGridPanel_Id");
	if(isNotVal(treeGridPanel)){
		var store_=treeGridPanel.getStore();
		var getNode = store_.root.childNodes;
		clearRoleRightLanguageTreeSelect(getNode);
	}
	
	var roleId = Ext.getCmp("RightBottomRoleCodes_Id").getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/moduleShowRightManagerController/doShowRightCurrentRoleOwnLanguages?roleId=' + roleId,
        success: function (response, opts) {
            // 处理后
            var roleLanguages = Ext.decode(response.responseText);
            var store_=Ext.getCmp("RightLanguageTreeInfoGridPanel_Id").getStore();
            if (null != roleLanguages && roleLanguages != "") {
                var getNode = store_.root.childNodes;
                selectEachLanguageCombox(getNode, roleLanguages);
            }
        },
        failure: function (response, opts) {
            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
        }
    });
    return false;
}