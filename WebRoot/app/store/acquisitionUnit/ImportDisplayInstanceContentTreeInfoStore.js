Ext.define('AP.store.acquisitionUnit.ImportDisplayInstanceContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importDisplayInstanceContentTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getUploadedDisplayInstanceTreeData',
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
        	var deviceType=Ext.getCmp("ImportDisplayInstanceWinDeviceType_Id").getValue();
        	var new_params = {
        			deviceType: deviceType
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var treeGridPanel = Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ImportDisplayInstanceContentTreeGridPanel_Id",
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
                    		return adviceImportDisplayInstanceCollisionInfoColor(value,o,p,e);
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
                			return iconImportSingleDisplayInstanceAction(value,e,o)
                		} 
                    }],
                    listeners: {
                        select( v, record, index, eOpts ){
//                        	if(record.data.classes==0){//选中设备类型deviceType
//                        		if(isNotVal(record.data.children) && record.data.children.length>0){
//                        			CreateImportDisplayInstanceAcqItemsInfoTable(record.data.children[0].protocol,record.data.children[0].acqUnitName,record.data.children[0].displayUnitName,record.data.children[0].text);
//                        			CreateImportDisplayInstanceCalItemsInfoTable(record.data.children[0].protocol,record.data.children[0].acqUnitName,record.data.children[0].displayUnitName,record.data.children[0].text);
//                        			CreateImportDisplayInstanceInputItemsInfoTable(record.data.children[0].protocol,record.data.children[0].acqUnitName,record.data.children[0].displayUnitName,record.data.children[0].text);
//                        			CreateImportDisplayInstanceCtrlItemsInfoTable(record.data.children[0].protocol,record.data.children[0].acqUnitName,record.data.children[0].displayUnitName,record.data.children[0].text);
//                        		}else{
//                        			CreateImportDisplayInstanceAcqItemsInfoTable('','','','');
//                        			CreateImportDisplayInstanceCalItemsInfoTable('','','','');
//                        			CreateImportDisplayInstanceInputItemsInfoTable('','','','');
//                        			CreateImportDisplayInstanceCtrlItemsInfoTable('','','','');
//                        		}
//                        	}else{
//                        		CreateImportDisplayInstanceAcqItemsInfoTable(record.data.protocol,record.data.acqUnitName,record.data.displayUnitName,record.data.text);
//                        		CreateImportDisplayInstanceCalItemsInfoTable(record.data.protocol,record.data.acqUnitName,record.data.displayUnitName,record.data.text);
//                        		CreateImportDisplayInstanceInputItemsInfoTable(record.data.protocol,record.data.acqUnitName,record.data.displayUnitName,record.data.text);
//                        		CreateImportDisplayInstanceCtrlItemsInfoTable(record.data.protocol,record.data.acqUnitName,record.data.displayUnitName,record.data.text);
//                        	}
                        }
                    }

                });
                var panel = Ext.getCmp("importDisplayInstanceTreePanel_Id");
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