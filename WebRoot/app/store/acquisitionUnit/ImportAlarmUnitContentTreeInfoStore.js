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
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
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
                    	header: '冲突信息',
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
                		text: '保存', 
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
                        	var activeId = Ext.getCmp("importAlarmUnitItemsConfigTabPanel_Id").getActiveTab().id;
                        	if(activeId=="importAlarmUnitNumItemsConfigTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmUnitNumItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].text);
                            			CreateImportAlarmUnitCalNumItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmUnitNumItemsConfigInfoTable('','');
                            			CreateImportAlarmUnitCalNumItemsConfigInfoTable('','');
                            		}
                            	}else{
                            		CreateImportAlarmUnitNumItemsConfigInfoTable(record.data.protocol,record.data.text);
                            		CreateImportAlarmUnitCalNumItemsConfigInfoTable(record.data.protocol,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmUnitSwitchItemsConfigTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmUnitSwitchItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmUnitSwitchItemsConfigInfoTable('','');
                            		}
                            	}else{
                            		CreateImportAlarmUnitSwitchItemsConfigInfoTable(record.data.protocol,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmUnitEnumItemsConfigTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmUnitEnumItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmUnitEnumItemsConfigInfoTable('','');
                            		}
                            	}else{
                            		CreateImportAlarmUnitEnumItemsConfigInfoTable(record.data.protocol,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmUnitFESDiagramResultItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmUnitFESDiagramResultItemsConfigInfoTable('','');
                            		}
                            	}else{
                            		CreateImportAlarmUnitFESDiagramResultItemsConfigInfoTable(record.data.protocol,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmUnitRunStatusConfigTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmUnitRunStatusItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmUnitRunStatusItemsConfigInfoTable('','');
                            		}
                            	}else{
                            		CreateImportAlarmUnitRunStatusItemsConfigInfoTable(record.data.protocol,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmUnitCommStatusConfigTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmUnitCommStatusItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmUnitCommStatusItemsConfigInfoTable('','');
                            		}
                            	}else{
                            		CreateImportAlarmUnitCommStatusItemsConfigInfoTable(record.data.protocol,record.data.text);
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