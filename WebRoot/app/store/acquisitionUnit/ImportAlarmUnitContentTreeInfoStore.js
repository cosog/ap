Ext.define('AP.store.acquisitionUnit.ImportAlarmUnitContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importAlarmUnitContentTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getUploadedAlarmUnitTreeData',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            keepRawData: true
        }
    },
    listeners: {
        beforeload: function (store, options) {
        	var deviceType=Ext.getCmp("ImportAlarmUnitWinDeviceType_Id").getValue();
        	var new_params = {
        			deviceType: deviceType
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var treeGridPanel = Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ImportAlarmUnitContentTreeGridPanel_Id",
//                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    selModel: 'cellmodel',//cellmodel rowmodel
                    plugins: [{
                        ptype: 'cellediting',//cellediting rowediting
                        clicksToEdit: 2
                    }],
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '预导入实例',
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text',
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    },{
                    	header: loginUserLanguageResource.collisionInfo,
                    	flex: 12,
                    	dataIndex: 'msg',
                    	renderer:function(value,o,p,e){
                    		return adviceImportAlarmUnitCollisionInfoColor(value,o,p,e);
                    	}
                    },{
                        header: 'id',
                        hidden: true,
                        dataIndex: 'id'
                    },{
                		text: loginUserLanguageResource.save, 
                		dataIndex: 'action',
//                		locked:true,
                		align:'center',
                		width:50,
                		renderer :function(value,e,o){
                			return iconImportSingleAlarmUnitAction(value,e,o)
                		} 
                    }],
                    listeners: {
                        select( v, record, index, eOpts ){
                        	if(record.data.classes!=1){
                        		clearImportAlarmUnitHandsontable();
                        	}else{
                        		var activeId = Ext.getCmp("importAlarmUnitItemsConfigTabPanel_Id").getActiveTab().id;
                            	if(activeId=="importAlarmUnitNumItemsConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitNumItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitSwitchItemsConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitSwitchItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitEnumItemsConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitEnumItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitFESDiagramResultItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitRunStatusConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitRunStatusItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitCommStatusConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitCommStatusItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}
                        	}
                        }
                    }

                });
                var panel = Ext.getCmp("importAlarmUnitTreePanel_Id");
                panel.add(treeGridPanel);
            }
            
            treeGridPanel.getSelectionModel().deselectAll(true);
            var selectedRow=0;
            if(store.data.length>1){
            	selectedRow=1;
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes==1){
            			selectedRow=i;
            			break;
            		}
            	}
            }
            treeGridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});