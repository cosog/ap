Ext.define('AP.store.acquisitionUnit.ImportAcqUnitContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importAcqUnitContentTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getUploadedAcqUnitTreeData',
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
        	var deviceType=Ext.getCmp("ImportAcqUnitWinDeviceType_Id").getValue();
        	var new_params = {
        			deviceType: deviceType
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var treeGridPanel = Ext.getCmp("ImportAcqUnitContentTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ImportAcqUnitContentTreeGridPanel_Id",
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
                    		return adviceImportAcqUnitCollisionInfoColor(value,o,p,e);
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
                			return iconImportSingleAcqUnitAction(value,e,o)
                		} 
                    }],
                    listeners: {
                    	rowclick: function( grid, record, element, index, e, eOpts) {
                    		
                    	},
                    	checkchange: function (node, checked) {
                    		
                        },
                        selectionchange ( view, selected, eOpts ){
                        	
                        },
                        select( v, record, index, eOpts ){
                        	if(record.data.classes==0){
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			CreateUploadedAcqUnitContentInfoTable(
                        					record.data.children[0].protocol,
                        					record.data.children[0].classes,
                        					record.data.children[0].text
                        					);
                        		}else{
                        			CreateUploadedAcqUnitContentInfoTable('',1,'');
                        		}
                        	}else if(record.data.classes==1){
                        		CreateUploadedAcqUnitContentInfoTable(record.data.protocol,record.data.classes,record.data.text);
                        	}else if(record.data.classes==2){
                        		CreateUploadedAcqUnitContentInfoTable(
                        				record.data.protocol,
                        				record.data.classes,
                        				record.parentNode.data.text,
                        				record.data.text,
                        				record.data.type);
                        	}
                        },
                        beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        },
                        checkchange: function (node, checked) {
                            listenerCheck(node, checked);
                        }
                    }

                });
                var panel = Ext.getCmp("importAcqUnitTreePanel_Id");
                panel.add(treeGridPanel);
            }
            
            treeGridPanel.getSelectionModel().deselectAll(true);
            var selectedRow=0;
            if(store.data.length>1){
            	selectedRow=1;
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes==2){
            			selectedRow=i;
            			break;
            		}
            	}
            }
            treeGridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});