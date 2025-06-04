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
                    	mode:'MULTI',//"SINGLE" / "SIMPLE" / "MULTI" 
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
                    	dataIndex: 'name',
                    	editor: loginUserDataDictionaryManagementModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    },{
                    	header: loginUserLanguageResource.columnDataSource,
                    	align: 'center',
                    	flex: 1,
                    	dataIndex: 'columnDataSourceName',
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
                        editor: loginUserDataDictionaryManagementModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        }:"",
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
                    	editor: loginUserDataDictionaryManagementModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    },{
                    	xtype: 'checkcolumn',
                    	align: 'center',
                    	text: loginUserLanguageResource.language_zh_CN,
                    	disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    	dataIndex: 'status_cn',
                    	width: (getLabelWidth(loginUserLanguageResource.language_zh_CN,loginUserLanguage)+30),
                    	hidden:isExist(loginUserLanguageList,1)==0,
                    	headerCheckbox: true,
                    	headerCls: 'horizontal-header',
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
                    	xtype: 'checkcolumn',
                    	align: 'center',
                    	text: loginUserLanguageResource.language_en,
                    	disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    	dataIndex: 'status_en',
                    	width: getLabelWidth(loginUserLanguageResource.language_en,loginUserLanguage)+30,
                    	hidden:isExist(loginUserLanguageList,2)==0,
                    	headerCheckbox: true,
                    	headerCls: 'horizontal-header',
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
                    	xtype: 'checkcolumn',
                    	align: 'center',
                    	text: loginUserLanguageResource.language_ru,
                    	disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    	dataIndex: 'status_ru',
                    	width: getLabelWidth(loginUserLanguageResource.language_ru,loginUserLanguage)+30,
                    	hidden:isExist(loginUserLanguageList,3)==0,
                    	headerCheckbox: true,
                    	headerCls: 'horizontal-header',
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
                    	header: loginUserLanguageResource.sortNum,
                    	align: 'center',
                    	width: 40,
                    	dataIndex: 'sorts',
                    	editor: loginUserDataDictionaryManagementModuleRight.editFlag==1?{
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: false,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                            minValue: 1
                        }:""
                    },{
                    	xtype: 'checkcolumn',
                    	align: 'center',
                    	header: loginUserLanguageResource.enable,
                    	disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                    	dataIndex: 'status',
                    	width: getLabelWidth(loginUserLanguageResource.enable,loginUserLanguage)+'px',
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
                    	width: getLabelWidth(loginUserLanguageResource.save,loginUserLanguage)+'px',
                        align: 'center',
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'submit',
                            tooltip: loginUserLanguageResource.save,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	var editFlag=parseInt(Ext.getCmp("DataDictionaryManagementModuleEditFlag").getValue());
        	                    if(editFlag==1){
        	                    	updateDataDictionaryItemInfoByGridBtn(record);
        	                    }
                            }
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
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	var editFlag=parseInt(Ext.getCmp("DataDictionaryManagementModuleEditFlag").getValue());
        	                    if(editFlag==1){
        	                    	deleteDataDictionaryItemInfoByGridBtn(record);
        	                    }
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
        	var deviceType=getDeviceTypeFromTabId("DictItemRootTabPanel");
        	if(deviceType.includes(",")){
        		deviceType=getDeviceTypeFromTabId_first("DictItemRootTabPanel");
        	}
        	
        	
        	var new_params = {
        			dictionaryId:dictionaryId,
        			type:type,
        			value:value,
        			deviceType:deviceType
        	};
            Ext.apply(store.proxy.extraParams, new_params); 	
        }
    }
});