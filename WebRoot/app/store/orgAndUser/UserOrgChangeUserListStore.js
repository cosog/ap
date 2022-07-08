Ext.define('AP.store.orgAndUser.UserOrgChangeUserListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.userOrgChangeUserListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/userManagerController/getUserOrgChangeUserList',
        actionMethods: {
            read: 'POST'
        },
    reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, record, f, op, o) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var gridPanel = Ext.getCmp("UserOrgChangeUserListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "UserOrgChangeUserListGridPanel_Id",
                    border: false,
                    autoLoad: true,
                    columnLines: true,
                    forceFit: false,
                    selType: 'checkboxmodel',
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: [{
                        text: '序号',
                        lockable: true,
                        align: 'center',
                        width: 50,
                        xtype: 'rownumberer',
                        sortable: false,
                        locked: false
                    }, {
                        text: '用户名称',
                        lockable: true,
                        align: 'center',
                        flex: 10,
                        sortable: false,
                        locked: false,
                        dataIndex: 'userName',
                        renderer: function (value,o,p,e) {
                        	var showVal=value;
                         	if(p.data.id==user_){
                         		showVal="*"+value;
                         	}
                         	if(isNotVal(showVal)){
                         		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (showVal == undefined ? "" : showVal) + "</span>";
                         	}
                        }
                    }, {
                        text: '用户账号',
                        lockable: true,
                        align: 'center',
                        flex: 10,
                        sortable: false,
                        locked: false,
                        dataIndex: 'userID',
                        renderer: function (value) {
                        	if(isNotVal(value)){
                            	return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                         	}
                        }
                    }]
                });
                var userListPanel = Ext.getCmp("UserOrgChangeWinUserListPanel_Id");
                userListPanel.add(gridPanel);
            }
        },
        beforeload: function (store, options) {
        	var selectedOrgId="";
        	var orgTreeSelection = Ext.getCmp("OrgInfoTreeGridView_Id").getSelectionModel().getSelection();
        	if (orgTreeSelection.length > 0) {
        		selectedOrgId=foreachAndSearchOrgChildId(orgTreeSelection[0]);
        	}
        	var UserName_Id = Ext.getCmp('UserName_Id').getValue();
            var orgId = Ext.getCmp('leftOrg_Id').getValue();
            if(selectedOrgId==""){
            	selectedOrgId=Ext.getCmp('leftOrg_Id').getValue();
            }
            var new_params = {
            		orgId: selectedOrgId,
                	userName: UserName_Id
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});