Ext.define('AP.store.acquisitionUnit.ImportAlarmInstanceContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importAlarmInstanceContentTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getUploadedAlarmInstanceTreeData',
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
        	var deviceType=Ext.getCmp("ImportAlarmInstanceWinDeviceType_Id").getValue();
        	var new_params = {
        			deviceType: deviceType
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var treeGridPanel = Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ImportAlarmInstanceContentTreeGridPanel_Id",
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
                    	header: '冲突信息',
                    	flex: 12,
                    	dataIndex: 'msg',
                    	renderer:function(value,o,p,e){
                    		return adviceImportAlarmInstanceCollisionInfoColor(value,o,p,e);
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
                			return iconImportSingleAlarmInstanceAction(value,e,o)
                		} 
                    }],
                    listeners: {
                        select( v, record, index, eOpts ){
                        	var activeId = Ext.getCmp("importAlarmInstanceItemsConfigTabPanel_Id").getActiveTab().id;
                        	if(activeId=="importAlarmInstanceNumItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceNumItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            			CreateImportAlarmInstanceCalNumItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceNumItemsConfigInfoTable('','','');
                            			CreateImportAlarmInstanceCalNumItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceNumItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            		CreateImportAlarmInstanceCalNumItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceSwitchItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceSwitchItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceSwitchItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceSwitchItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceEnumItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceEnumItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceEnumItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceEnumItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceFESDiagramResultItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceFESDiagramResultItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceFESDiagramResultItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceRunStatusItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceRunStatusItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceRunStatusItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceRunStatusItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceCommStatusItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceCommStatusItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceCommStatusItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceCommStatusItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}
                        }
                    }

                });
                var panel = Ext.getCmp("importAlarmInstanceTreePanel_Id");
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