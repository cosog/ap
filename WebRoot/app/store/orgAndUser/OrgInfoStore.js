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
            var treeGridPanel = Ext.getCmp("OrgInfoTreeGridView_Id");
            if (!isNotVal(treeGridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createOrgTreeHeadColumns(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
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
//                    selType: 'checkboxmodel',
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	if(selected.length>0){
                        		if((selected[0].data.text=='组织根节点'&&parseInt(selected[0].data.orgParent)==0) || parseInt(selected[0].data.orgId)==parseInt(userOrg_Id)){
                        			Ext.getCmp("editOrgLableClassBtn_Id").disable();
                                	Ext.getCmp("delOrgLableClassBtn_Id").disable();
                        		}else{
                        			Ext.getCmp("editOrgLableClassBtn_Id").enable();
                                	Ext.getCmp("delOrgLableClassBtn_Id").enable();
                        		}
                        	}
                        },
                        itemdblclick: function (grid, record, item, index, e, eOpts) {
                        	if(!( (record.data.text=='组织根节点'&&parseInt(record.data.orgParent)==0) || parseInt(record.data.orgId)==parseInt(userOrg_Id)   )){
                        		modifyOrgInfo();
                    		}
                        },
                        itemclick: function (view,record,item,ndex,e,eOpts) {
                        	var gridPanel = Ext.getCmp("UserInfoGridPanel_Id");
                        	if (isNotVal(gridPanel)) {
                        		gridPanel.getStore().load();
                        	}
                        }
                    }

                });
                var panel = Ext.getCmp("OrgAndUserOrgInfoPanel_Id");
                panel.add(treeGridPanel);
            }

            //加载用户列表
            var gridPanel = Ext.getCmp("UserInfoGridPanel_Id");
        	if (isNotVal(gridPanel)) {
        		gridPanel.getStore().load();
        	}else{
        		Ext.create("AP.store.orgAndUser.UserPanelInfoStore");
        	}
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