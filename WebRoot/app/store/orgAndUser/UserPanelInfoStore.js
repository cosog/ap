Ext.define('AP.store.orgAndUser.UserPanelInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.userPanelInfoStore',
    model: 'AP.model.user.UserPanelInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/userManagerController/doUserShow',
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
            var gridPanel = Ext.getCmp("UserInfoGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createUserGridColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "UserInfoGridPanel_Id",
                    selModel: 'cellmodel',//cellmodel rowmodel
                    plugins: [{
                        ptype: 'cellediting',//cellediting rowediting
                        clicksToEdit: 2
                    }],
                    border: false,
                    stateful: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    selType: 'checkboxmodel',
                    multiSelect: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.idx,loginUserLanguage)+'px',
                        xtype: 'rownumberer'
                    }, {
                        header: loginUserLanguageResource.userName,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'userName',
                        flex:2,
                        editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value, o, p, e) {
                            return adviceCurrentUserName(value, o, p, e);
                        }
                    }, {
                        header: loginUserLanguageResource.userAccount,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userId',
                        flex:2,
                        editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    }, {
                        header: loginUserLanguageResource.role,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userTypeName',
                        flex:2,
                        editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                            xtype: 'combo',
                            typeAhead: true,
                            triggerAction: 'all',
                            allowBlank: false,
                            editable: false,
                            store: get_rawData.roleList,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: loginUserLanguageResource.phone,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userPhone',
                        flex:2,
                        editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                        	regex: /^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\d{8}$/,
                        	allowBlank: true,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                            if(isNotVal(value)){
                            	return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? " " : value) + "</span>";
                            }
                        }
                    }, {
                        header: loginUserLanguageResource.email,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userInEmail',
                        flex:3,
                        editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                        	vtype: 'email',
                            regex: /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/,
                            allowBlank: true,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    }, {
                        header: loginUserLanguageResource.userQuickLogin,
                        xtype: 'checkcolumn',
                        disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.userQuickLogin,loginUserLanguage)+'px',
                        dataIndex: 'userQuickLoginName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	var OrgAndUserModuleEditFlag=parseInt(Ext.getCmp("OrgAndUserModuleEditFlag").getValue());
        	                    if(OrgAndUserModuleEditFlag!=1){
        	                    	return false;
        	                    }
                    	    }
                    	}
                    }, {
                        header: loginUserLanguageResource.receiveSMS,
                        xtype: 'checkcolumn',
                        lockable: true,
                        disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.receiveSMS,loginUserLanguage)+'px',
                        dataIndex: 'receiveSMSName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	var OrgAndUserModuleEditFlag=parseInt(Ext.getCmp("OrgAndUserModuleEditFlag").getValue());
        	                    if(OrgAndUserModuleEditFlag!=1){
        	                    	return false;
        	                    }
                    	    }
                    	}
                    }, {
                        header: loginUserLanguageResource.receiveMail,
                        xtype: 'checkcolumn',
                        disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.receiveMail,loginUserLanguage)+'px',
                        dataIndex: 'receiveMailName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	var OrgAndUserModuleEditFlag=parseInt(Ext.getCmp("OrgAndUserModuleEditFlag").getValue());
        	                    if(OrgAndUserModuleEditFlag!=1){
        	                    	return false;
        	                    }
                    	    }
                    	}
                    }, {
                        header: loginUserLanguageResource.language,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userLanguageName',
                        flex:2,
                        hidden:true,
                        editor: loginUserOrgAndUserModuleRight.editFlag==1?{
                            xtype: 'combo',
                            typeAhead: true,
                            triggerAction: 'all',
                            allowBlank: false,
                            editable: false,
                            store: get_rawData.languageList,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: loginUserLanguageResource.status,
                        xtype: 'checkcolumn',
                        disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userEnableName',
                        headerCheckbox: false,
                        width: getLabelWidth(loginUserLanguageResource.enable,loginUserLanguage)+'px',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                    	listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	var OrgAndUserModuleEditFlag=parseInt(Ext.getCmp("OrgAndUserModuleEditFlag").getValue());
        	                    if(OrgAndUserModuleEditFlag==1){
        	                    	if(rowIndex==0){
                        	    		return false;
                        	    	}else{
                                        return true;
                                    }
        	                    }else{
        	                    	return false;
        	                    }
                    	    }
                    	}
                    }, {
                        header: loginUserLanguageResource.owningOrg,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex:2,
                        dataIndex: 'allPath',
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    }, {
                        header: loginUserLanguageResource.createTime,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex:2,
                        dataIndex: 'userRegtime',
                        renderer:function(value,o,p,e){
                        	return adviceTimeFormat(value,o,p,e);
                        }
                    },{
                    	header: loginUserLanguageResource.save,
                    	xtype: 'actioncolumn',
                    	width: getLabelWidth(loginUserLanguageResource.save,loginUserLanguage)+'px',
                        align: 'center',
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'submit',
                            tooltip: loginUserLanguageResource.save,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	var OrgAndUserModuleEditFlag=parseInt(Ext.getCmp("OrgAndUserModuleEditFlag").getValue());
        	                    if(OrgAndUserModuleEditFlag==1){
        	                    	updateUserInfoByGridBtn(record);
        	                    }
                            }
//                        	,isDisabled : function (view, recIndex, cellIndex, item, record) {
//                        		return true;
//                            }
                        }]
                    },{
                    	header: loginUserLanguageResource.deleteData,
                    	xtype: 'actioncolumn',
                    	width: getLabelWidth(loginUserLanguageResource.deleteData,loginUserLanguage)+'px',
                        align: 'center',
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'delete',
                            tooltip: loginUserLanguageResource.deleteData,
                            disabled:loginUserOrgAndUserModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	var OrgAndUserModuleEditFlag=parseInt(Ext.getCmp("OrgAndUserModuleEditFlag").getValue());
        	                    if(OrgAndUserModuleEditFlag==1){
        	                    	delUserInfoByGridBtn(record);
        	                    }
                            }
                        }]
                    }],
                    listeners: {
                    	celldblclick : function( grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                    		var record = grid.getStore().getAt(rowIndex);
                            var dataIndex=grid.getHeaderAtIndex(cellIndex).dataIndex;
                            if (record.data.userNo==user_ 
                            		&& ( dataIndex.toUpperCase()=='userId'.toUpperCase() 
                            				|| dataIndex.toUpperCase()=='userTypeName'.toUpperCase() 
                            				|| dataIndex.toUpperCase()=='userEnableName'.toUpperCase()  
                            				)
                            	) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                });
                var panel = Ext.getCmp("OrgAndUserUserInfoPanel_Id");
                panel.add(gridPanel);
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
        }
    }
});
//生成Grid-Fields 动态创建报警信息查询的columns
createUserHeadColumn = function (columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        myColumns += "{ header:'" + attr.header + "'";
        if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable:false,align:'center'";
        } else {
            myColumns += ",dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};