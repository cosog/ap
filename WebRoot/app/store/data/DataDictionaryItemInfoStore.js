Ext.define('AP.store.data.DataDictionaryItemInfoStore',{
	extend:'Ext.data.Store',
	id:"DataDictionaryItemInfoStoreId",
	alias : 'widget.dataDictionaryItemInfoStore',
//	pageSize:defaultPageSize, 
	model:'AP.model.data.DataitemsInfoModel',
	autoLoad:true,
    proxy: {
        type: 'ajax', 
        url : context+'/dataitemsInfoController/getDataDictionaryItemList',       
        actionMethods : {
			read : 'POST'
		},
		start:0,
		limit:defaultPageSize,	 
        reader: {
            type: 'json',
            rootProperty: 'totalRoot', 
            totalProperty:'totalCount',
            keepRawData: true
        }
    }, 
    listeners: {
    	load: function (store, options, eOpts) {
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var gridPanel = Ext.getCmp("dataDictionaryItemGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "dataDictionaryItemGridPanel_Id",
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
                    selModel:{
                    	selType: (loginUserDataDictionaryManagementModuleRight.editFlag==1?'checkboxmodel':''),
                    	mode:'SINGLE',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly:false,
                    	allowDeselect:false
                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: 50,
                        xtype: 'rownumberer'
                    },{
                    	header: loginUserLanguageResource.dataColumnName,
                    	align: 'center',
                    	flex: 1,
                    	hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                    	dataIndex: 'name_zh_CN',
                    	editor: {
                            allowBlank: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        },
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    },{
                    	header: loginUserLanguageResource.dataColumnName,
                    	align: 'center',
                    	flex: 1,
                    	hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                    	dataIndex: 'name_en',
                    	editor: {
                            allowBlank: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        },
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    },{
                        header: loginUserLanguageResource.dataColumnName,
                        align: 'center',
                        flex: 1,
                        hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                        dataIndex: 'name_ru',
                        editor: {
                            allowBlank: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        },
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    },{
                        header: loginUserLanguageResource.dataColumnCode,
                        align: 'center',
                        flex: 1,
                        dataIndex: 'code',
                        editor: {
                            allowBlank: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        },
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    },{
                    	header: loginUserLanguageResource.dataColumnParams,
                    	align: 'center',
                    	flex: 1,
                    	dataIndex: 'datavalue',
                    	editor: {
                            allowBlank: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        },
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    },{
                    	header: loginUserLanguageResource.sortNum,
                    	align: 'center',
                    	width: 40,
                    	dataIndex: 'sorts',
                    	editor: {
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                            minValue: 1
                        }
                    },{
                    	xtype: 'checkcolumn',
                    	align: 'center',
                    	header: loginUserLanguageResource.enable,
                    	disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    	dataIndex: 'status',
                    	width: 65,
                    	editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                    	listeners: {
                    		checkchange: function (sm, e, ival, o, n) {
                    			
                    		}
                    	}
                    },{
                    	header: loginUserLanguageResource.save,
                    	xtype: 'actioncolumn',
                    	width: getStringLength(loginUserLanguageResource.save)*10,
                        align: 'center',
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'submit',
                            tooltip: loginUserLanguageResource.save,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
//                            	var OrgAndUserModuleEditFlag=parseInt(Ext.getCmp("OrgAndUserModuleEditFlag").getValue());
//        	                    if(OrgAndUserModuleEditFlag==1){
//        	                    	updateUserInfoByGridBtn(record);
//        	                    }
                            }
                        }]
                    },{
                    	header: loginUserLanguageResource.deleteData,
                    	xtype: 'actioncolumn',
                    	width: getStringLength(loginUserLanguageResource.deleteData)*10,
                        align: 'center',
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'delete',
                            tooltip: loginUserLanguageResource.deleteData,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
//                            	var OrgAndUserModuleEditFlag=parseInt(Ext.getCmp("OrgAndUserModuleEditFlag").getValue());
//        	                    if(OrgAndUserModuleEditFlag==1){
//        	                    	delUserInfoByGridBtn(record);
//        	                    }
                            }
                        }]
                    }],
                    listeners: {
                        itemdblclick: function () {
                        	
                        },
                        selectionchange: function (sm, selections) {
                        	
                        }
                    }
                });
                Ext.getCmp("dataDictionaryItemPanel_Id").add(gridPanel);
            }
            
            
            
        },
    	beforeload: function(store, options) {
        	var dictionaryId='';
        	var type=Ext.getCmp('dataDictionaryItemSearchTypeComb_Id').getValue();
        	var value=Ext.getCmp('dataDictionaryItemSearchValue_Id').getValue();
        	
        	var dictionarySelection= Ext.getCmp("SystemdataInfoGridPanelId").getSelectionModel().getSelection();
        	if(dictionarySelection.length>0){
        		dictionaryId = Ext.getCmp("SystemdataInfoGridPanelId").getSelectionModel().getSelection()[0].data.sysdataid;
        	}
        	
        	var new_params = {
        			dictionaryId:dictionaryId,
        			type:type,
        			value:value
        	};
            Ext.apply(store.proxy.extraParams, new_params); 	
        }
    }
});