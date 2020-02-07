Ext.define('AP.store.org.OrgInfoStore', {
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
//            rootProperty: 'list',
            keepRawData: true
        }
//,
//        extraParams: {
//            tid: '0'
//        }
    },
    //根节点
    root: {
        text: '组织导航',
        expanded: true,
        orgId: '0'
    },
    listeners: {
//    	nodebeforeexpand: function (node, eOpts) {
//            this.proxy.extraParams.tid = node.data.orgId;
//        },
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var ResHeadInfoGridPanel_Id = Ext.getCmp("OrgInfoTreeGridView_Id");
            if (!isNotVal(ResHeadInfoGridPanel_Id)) {
                var arrColumns = get_rawData.columns;
                var cloums = createTreeHeadColumns(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                var ResHeadrInfoGridViewPanelGrid = Ext.create('Ext.tree.Panel', {
                    id: "OrgInfoTreeGridView_Id",
                    border: false,
                    layout: "fit",
                    loadMask: true,
                    rootVisible: false,
                    useArrows: true,
                    draggable: false,
                    forceFit: true,
                    onlyLeafCheckable: false, // 所有结点可选，如果不需要checkbox,该属性去掉
                    singleExpand: false,
                    selType: 'checkboxmodel',
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selections) {
                        },
                        itemdblclick: function () {
                            modifyOrgInfo();
                        },
                        itemclick: function (view,record,item,ndex,e,eOpts) {
                        	
                        }
                    }

                });
                var OrgInfoTreeGridViewPanel_Id = Ext.getCmp("OrgInfoTreeGridViewPanel_Id");
                OrgInfoTreeGridViewPanel_Id.add(ResHeadrInfoGridViewPanelGrid);
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