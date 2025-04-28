Ext.define('AP.store.acquisitionUnit.ImportProtocolContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importProtocolContentTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getUploadedProtocolTreeData',
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
        	var deviceType=Ext.getCmp("ImportProtocolWinDeviceType_Id").getValue();
        	var new_params = {
        			deviceType: deviceType
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var treeGridPanel = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ImportProtocolContentTreeGridPanel_Id",
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
                    	text: '预导入协议',
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text',
//                        editor: {
//                        	disabled:true
//                        },
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    },{
                    	header: loginUserLanguageResource.collisionInfo,
                    	flex: 12,
                    	dataIndex: 'msg',
//                    	editor: {
//                        	disabled:true
//                        },
                    	renderer:function(value,o,p,e){
                    		return adviceDataInfoColor(value,o,p,e);
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
                			return iconImportSingleProtocolAction(value,e,o)
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
                        			CreateUploadedProtocolContentInfoTable(record.data.children[0].text,record.data.children[0].classes,record.data.children[0].code);
                        		}else{
                        			if(importProtocolContentHandsontableHelper!=null && importProtocolContentHandsontableHelper.hot!=undefined){
                        				importProtocolContentHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
                        			}
                        		}
                        	}else if(record.data.classes==1){
                        		CreateUploadedProtocolContentInfoTable(record.data.text,record.data.classes,record.data.code);
                        	}
                        },
                        beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        },
                        checkchange: function (node, checked) {
                            listenerCheck(node, checked);
                        }
                    }

                });
                var panel = Ext.getCmp("importProtocolTreePanel_Id");
                panel.add(treeGridPanel);
            }
            
            treeGridPanel.getSelectionModel().deselectAll(true);
            if(store.data.length>1){
            	treeGridPanel.getSelectionModel().select(1, true);
            }else{
            	treeGridPanel.getSelectionModel().select(0, true);
            }
            
        }
    }
});