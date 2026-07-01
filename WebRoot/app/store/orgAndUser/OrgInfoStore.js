Ext.define('AP.store.orgAndUser.OrgInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.orgInfoStore',
    model: 'AP.model.org.OrgInfoModel',
    autoLoad: false, // 自动加载数据
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: context + '/orgManagerController/constructOrgTreeGridTree',
        reader: {
            type: 'json',
            keepRawData: true
        }
    },
    //根节点
    root: {
        text: '组织导航',
        expanded: true,
        orgId: '0'
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var showChineseName=get_rawData.showChineseName;
            var showEnglishName=get_rawData.showEnglishName;
            var showRussianName=get_rawData.showRussianName;
            var treeGridPanel = Ext.getCmp("OrgInfoTreeGridView_Id");
            if (!isNotVal(treeGridPanel)) {
                var treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "OrgInfoTreeGridView_Id",
                    border: false,
                    layout: "fit",
                    loadMask: true,
                    rootVisible: false,
                    useArrows: true,
                    draggable: false,
                    forceFit: false,
                    onlyLeafCheckable: false, // 所有结点可选，如果不需要checkbox,该属性去掉
                    singleExpand: false,
                    selModel: 'cellmodel',//cellmodel rowmodel
                    plugins: [{
                        ptype: 'cellediting',//cellediting rowediting
                        clicksToEdit: 2
                    }],
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.orgName,
                        lockable: true,
                        align: 'left',
                        flex: 3,
                        xtype: 'treecolumn',
                        dataIndex: 'text'
                    },{
                    	text: loginUserLanguageResource.language_zh_CN,
                    	flex: 3,
                    	align: 'left',
                    	dataIndex: 'orgName_zh_CN',
                    	hidden:!showChineseName,
                    	editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1
                        }:"",
                    },{
                    	text: loginUserLanguageResource.language_en,
                    	flex: 3,
                    	align: 'left',
                    	dataIndex: 'orgName_en',
                    	hidden:!showEnglishName,
                    	editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1
                        }:"",
                    },{
                    	text: loginUserLanguageResource.language_ru,
                    	flex: 3,
                    	align: 'left',
                    	dataIndex: 'orgName_ru',
                    	hidden:!showRussianName,
                    	editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1
                        }:"",
                    }, {
                        header: loginUserLanguageResource.sequenceNumber,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'orgSeq',
                        editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                            allowBlank: true,
                            xtype: 'numberfield',
                            editable: false,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                            minValue: 1
                        }:""
                    }],
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	if(selected.length>0){
                        		if((selected[0].data.text==loginUserLanguageResource.orgRootNode&&parseInt(selected[0].data.orgParent)==0) || parseInt(selected[0].data.orgId)==parseInt(userOrg_Id)){
                        			Ext.getCmp("editOrgLableClassBtn_Id").disable();
                                	Ext.getCmp("delOrgLableClassBtn_Id").disable();
                        		}else{
                        			Ext.getCmp("editOrgLableClassBtn_Id").enable();
                                	Ext.getCmp("delOrgLableClassBtn_Id").enable();
                        		}
                        	}
                        },
                        itemdblclick: function (grid, record, item, index, e, eOpts) {
//                        	var OrgAndUserModuleEditFlag=parseInt(Ext.getCmp("OrgAndUserModuleEditFlag").getValue());
//    	                    if(OrgAndUserModuleEditFlag==1){
//    	                    	if(!( (record.data.text==loginUserLanguageResource.orgRootNode&&parseInt(record.data.orgParent)==0) || parseInt(record.data.orgId)==parseInt(userOrg_Id)   )){
//                            		modifyOrgInfo();
//                        		}
//    	                    }
                        },
                        itemclick: function (view,record,item,ndex,e,eOpts) {
//                        	var gridPanel = Ext.getCmp("UserInfoGridPanel_Id");
//                        	if (isNotVal(gridPanel)) {
//                        		gridPanel.getStore().load();
//                        	}
                        },
                        select( v, record, index, eOpts ){
                        	Ext.getCmp("selectedOrgId_Id").setValue(record.data.orgId);
                        	var gridPanel = Ext.getCmp("UserInfoGridPanel_Id");
                        	if (isNotVal(gridPanel)) {
                        		gridPanel.getStore().load();
                        	}else{
                        		Ext.create("AP.store.orgAndUser.UserPanelInfoStore");
                            }
                    	}
                    }

                });
                var panel = Ext.getCmp("OrgAndUserOrgInfoPanel_Id");
                if(isNotVal(panel)){
                	panel.add(treeGridPanel);
                }
            }

//            //加载用户列表
//            var gridPanel = Ext.getCmp("UserInfoGridPanel_Id");
//        	if (isNotVal(gridPanel)) {
//        		gridPanel.getStore().load();
//        	}else{
//        		Ext.create("AP.store.orgAndUser.UserPanelInfoStore");
//        	}
            
            
            var selectedRow=0;
            var selectedOrgId= Ext.getCmp("selectedOrgId_Id").getValue();
    		if(selectedOrgId>0){
    			for(var i=0;i<store.data.length;i++){
            		if(selectedOrgId==store.getAt(i).data.orgId){
            			selectedRow=i;
            			break;
            		}
            	}
    		}
            
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(selectedRow, true);
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var org_name_Id = Ext.getCmp('org_name_Id');
            if (!Ext.isEmpty(org_name_Id)) {
                org_name_Id = org_name_Id.getValue();
            }

            var new_params = {
            	orgId:orgId,
                orgName: org_name_Id

            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }

});