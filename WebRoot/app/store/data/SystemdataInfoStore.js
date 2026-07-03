Ext.define('AP.store.data.SystemdataInfoStore', {
	extend: 'Ext.data.Store',
    id: "SystemdataInfoStoreId",
    alias: 'widget.systemdataInfoStore',
    model: 'AP.model.data.SystemdataInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/systemdataInfoController/findSystemdataInfo',
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
    //分页监听事件
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var showChineseName=get_rawData.showChineseName;
            var showEnglishName=get_rawData.showEnglishName;
            var showRussianName=get_rawData.showRussianName;
            var arrColumns = get_rawData.columns;
            var gridPanel = Ext.getCmp("SystemdataInfoGridPanelId");
            if (!isNotVal(gridPanel)) {
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "SystemdataInfoGridPanelId",
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
                        emptyText: "<div class='con_div_' id='div_dataactiveid'>" + Ext.String.htmlEncode("<" + loginUserLanguageResource.emptyMsg + ">") + "</div>"
                    },
                    store: store,
                    columns: [{
                        text: loginUserLanguageResource.idx,
                        width: 50,
                        xtype: 'rownumberer',
                        sortable: false,
                        align: 'center',
                        locked: false
                    }, {
                        header: loginUserLanguageResource.language_zh_CN,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 2,
                        dataIndex: 'name_zh_CN',
                        hidden:!showChineseName,
                        editor: loginUserDataDictionaryManagementModuleRight.editFlag==1?{
                            allowBlank:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        }:""
                    }, {
                        header: loginUserLanguageResource.language_en,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 2,
                        dataIndex: 'name_en',
                        hidden:!showEnglishName,
                        editor: loginUserDataDictionaryManagementModuleRight.editFlag==1?{
                        	allowBlank:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        }:""
                    }, {
                        header: loginUserLanguageResource.language_ru,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 2,
                        dataIndex: 'name_ru',
                        hidden:!showRussianName,
                        editor: loginUserDataDictionaryManagementModuleRight.editFlag==1?{
                        	allowBlank:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1
                        }:""
                    }, {
                    	text: loginUserLanguageResource.displayOrder,
                        flex: 1,
                        align: 'center',
                        dataIndex: 'sorts',
                        editor: loginUserDataDictionaryManagementModuleRight.editFlag==1?{
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: true,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                            minValue: 1
                        }:""
                    },{
                    	text: loginUserLanguageResource.dictionaryBelongTo,
                        flex: 2,
                        align: 'center',
                        dataIndex: 'moduleName',
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
                        	}
                        }
                    },{
                    	header: loginUserLanguageResource.save,
                    	xtype: 'actioncolumn',
                    	width: getLabelWidth(loginUserLanguageResource.save,loginUserLanguage)+'px',
                        align: 'center',
                        hidden: true,
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'submit',
                            tooltip: loginUserLanguageResource.save,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	var editFlag=parseInt(Ext.getCmp("DataDictionaryManagementModuleEditFlag").getValue());
        	                    if(editFlag==1){
        	                    	updateDataDictionaryInfoByGridBtn(record);
        	                    }
                            }
                        }]
                    },{
                    	header: loginUserLanguageResource.deleteData,
                    	xtype: 'actioncolumn',
                    	width: getLabelWidth(loginUserLanguageResource.deleteData,loginUserLanguage)+'px',
                        align: 'center',
                        hidden: true,
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'delete',
                            tooltip: loginUserLanguageResource.deleteData,
                            disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
        	                    var editFlag=parseInt(Ext.getCmp("DataDictionaryManagementModuleEditFlag").getValue());
        	                    if(editFlag==1){
        	                    	deleteDataDictionaryInfoByGridBtn(record);
        	                    }
                            }
                        }]
                    }],
                    listeners: {
                        itemdblclick: function () {
                        	
                        },
                        select: function(grid, record, index, eOpts) {
                        	if(record.data.code=='realTimeMonitoring_Overview' || record.data.code=='historyQuery_Overview'){
                        		Ext.getCmp("addDictionaryItemBtn_Id").show();
                        	}else{
                        		Ext.getCmp("addDictionaryItemBtn_Id").hide();
                        	}
                        	
                        	Ext.getCmp("selectedDataDictionaryId").setValue(record.data.sysdataid);
                        	var dataDictionaryItemGridPanel = Ext.getCmp("dataDictionaryItemGridPanel_Id"); 
                        	if (isNotVal(dataDictionaryItemGridPanel)) {
                        		dataDictionaryItemGridPanel.getStore().load();
                        	}else{
                        		Ext.create("AP.store.data.DataDictionaryItemInfoStore");
                        	}
                        }
                    }
                });
                
                if(isNotVal(Ext.getCmp("SystemdataInfoGridPanelViewId"))){
                	Ext.getCmp("SystemdataInfoGridPanelViewId").add(gridPanel);
                }
            }
            var selectRow=0;
            var selectedDataDictionaryId=Ext.getCmp("selectedDataDictionaryId").getValue();
            
            if(isNotVal(selectedDataDictionaryId)){
				for(var i=0;i<get_rawData.totalRoot.length;i++){
        			if(selectedDataDictionaryId==get_rawData.totalRoot[i].sysdataid){
        				selectRow=i;
        				break;
        			}
        		}
			}
			gridPanel.getSelectionModel().deselectAll(true);
        	gridPanel.getSelectionModel().select(selectRow, true);
        },
        beforeload: function (store, options) {
            var new_params = {
                typeName: Ext.getCmp('sysdatacomboxfield_Id').getValue(),
                sysName: Ext.getCmp('sysname_Id').getValue()

            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});