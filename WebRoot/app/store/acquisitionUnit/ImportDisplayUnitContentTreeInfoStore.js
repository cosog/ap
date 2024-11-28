Ext.define('AP.store.acquisitionUnit.ImportDisplayUnitContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importDisplayUnitContentTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getUploadedDisplayUnitTreeData',
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
        	var deviceType=Ext.getCmp("ImportDisplayUnitWinDeviceType_Id").getValue();
        	var new_params = {
        			deviceType: deviceType
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var treeGridPanel = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ImportDisplayUnitContentTreeGridPanel_Id",
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
                    	text: '预导入单元',
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
                    		return adviceImportDisplayUnitCollisionInfoColor(value,o,p,e);
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
                			return iconImportSingleDisplayUnitAction(value,e,o)
                		} 
                    }],
                    listeners: {
                        select( v, record, index, eOpts ){
                        	if(record.data.classes==0){//选中设备类型deviceType
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			CreateImportDisplayUnitAcqItemsInfoTable(record.data.children[0].protocol,record.data.children[0].acqUnit,record.data.children[0].text,record.data.children[0].calculateType);
                        			CreateImportDisplayUnitCalItemsInfoTable(record.data.children[0].protocol,record.data.children[0].acqUnit,record.data.children[0].text,record.data.children[0].calculateType);
                        			CreateImportDisplayUnitInputItemsInfoTable(record.data.children[0].protocol,record.data.children[0].acqUnit,record.data.children[0].text,record.data.children[0].calculateType);
                        			CreateImportDisplayUnitCtrlItemsInfoTable(record.data.children[0].protocol,record.data.children[0].acqUnit,record.data.children[0].text,record.data.children[0].calculateType);
                        		}else{
                        			CreateImportDisplayUnitAcqItemsInfoTable('','','',0);
                        			CreateImportDisplayUnitCalItemsInfoTable('','','',0);
                        			CreateImportDisplayUnitInputItemsInfoTable('','','',0);
                        			CreateImportDisplayUnitCtrlItemsInfoTable('','','',0);
                        		}
                        	}else if(record.data.classes==1){//选中显示单元
                        		CreateImportDisplayUnitAcqItemsInfoTable(record.data.protocol,record.data.acqUnit,record.data.text,record.data.calculateType);
                        		CreateImportDisplayUnitCalItemsInfoTable(record.data.protocol,record.data.acqUnit,record.data.text,record.data.calculateType);
                        		CreateImportDisplayUnitInputItemsInfoTable(record.data.protocol,record.data.acqUnit,record.data.text,record.data.calculateType);
                        		CreateImportDisplayUnitCtrlItemsInfoTable(record.data.protocol,record.data.acqUnit,record.data.text,record.data.calculateType);
                        	}
                        }
                    }

                });
                var panel = Ext.getCmp("importDisplayUnitTreePanel_Id");
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