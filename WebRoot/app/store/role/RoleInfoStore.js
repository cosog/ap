Ext.define('AP.store.role.RoleInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.roleInfoStore',
    model: 'AP.model.role.RoleInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/doRoleShow',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: defaultPageSize,
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var ResHeadInfoGridPanel_Id = Ext.getCmp("RoleInfoGridPanel_Id");
            if (!isNotVal(ResHeadInfoGridPanel_Id)) {
                var arrColumns = get_rawData.columns;
                var cloums = createDiagStatisticsColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                //分页工具栏
                var bbar = new Ext.PageNumberToolbar({
                    store: store,
                    pageSize: defaultPageSize,
                    displayInfo: true,
//                    displayMsg: '当前记录 {0} -- {1} 条 共 {2} 条记录',
                    displayMsg: '当前 {0}~{1}条  共 {2} 条',
                    emptyMsg: "没有记录可显示",
                    prevText: "上一页",
                    nextText: "下一页",
                    refreshText: "刷新",
                    lastText: "最后页",
                    firstText: "第一页",
                    beforePageText: "当前页",
                    afterPageText: "共{0}页"
                });
                var SystemdataInfoGridPanel_panel = Ext.create('Ext.grid.Panel', {
                    id: "RoleInfoGridPanel_Id",
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
//                    selType: 'checkboxmodel',
//                    multiSelect: true,
                    selModel:{
                    	selType:'checkboxmodel',
                    	showHeaderCheckbox:false,
                    	mode:'SINGLE'
                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    bbar: bbar,
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	var roleId='';
                        	var roleCode='';
                        	if(selected.length>0){
                        		roleId = selected[0].data.roleId;
                        		roleCode = selected[0].data.roleCode;
                        	}
                            Ext.getCmp("RightBottomRoleCodes_Id").setValue(roleId);
                            Ext.getCmp("RightModuleTreeInfoGridPanel_Id").getStore().load();
                            if(roleCode=="sysAdmin"){
                            	Ext.getCmp("delroleLabelClassBtn_Id").disable();
                            	Ext.getCmp("editroleLabelClassBtn_Id").disable();
                            }else{
                            	Ext.getCmp("delroleLabelClassBtn_Id").enable();
                            	Ext.getCmp("editroleLabelClassBtn_Id").enable();
                            }
                        },
                        itemdblclick: function () {
                            modifyroleInfo();
                        }
                    }
                });
                var OrgInfoTreeGridViewPanel_Id = Ext.getCmp("RoleInfoGridPanelView_id");
                OrgInfoTreeGridViewPanel_Id.add(SystemdataInfoGridPanel_panel);
            }

        },
        beforeload: function (store, options) {
            var RoleName_Id = Ext.getCmp('RoleName_Id');

            if (!Ext.isEmpty(RoleName_Id)) {
                RoleName_Id = RoleName_Id.getValue();
            }
            var new_params = {
                roleName: RoleName_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});