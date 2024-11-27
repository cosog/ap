Ext.define('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolDisplayInstanceTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/modbusDisplayInstanceConfigTreeData',
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
        	var deviceTypeIds='';
        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        	if(tabTreeGridPanelSelection.length>0){
        		deviceTypeIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
        	}
        	var new_params = {
        			deviceTypeIds: deviceTypeIds
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
                treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id",
//                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: loginUserLanguageResource.displayInstanceList,
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text',
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    },{
                        header: 'id',
                        hidden: true,
                        dataIndex: 'id'
                    }],
                    listeners: {
                    	checkchange: function (node, checked) {
                    		
                        },
                        selectionchange ( view, selected, eOpts ){
                        	
                        },select( v, record, index, eOpts ){
                        	Ext.getCmp("ModbusProtocolDisplayInstanceTreeSelectRow_Id").setValue(index);
                        	if(record.data.classes==0){
                        		if(protocolDisplayInstanceAcqItemsHandsontableHelper!=null){
                					if(protocolDisplayInstanceAcqItemsHandsontableHelper.hot!=undefined){
                						protocolDisplayInstanceAcqItemsHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayInstanceAcqItemsHandsontableHelper=null;
                				}
                				if(protocolDisplayInstanceCalItemsHandsontableHelper!=null){
                					if(protocolDisplayInstanceCalItemsHandsontableHelper.hot!=undefined){
                						protocolDisplayInstanceCalItemsHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayInstanceCalItemsHandsontableHelper=null;
                				}
                				if(protocolDisplayInstanceCtrlItemsHandsontableHelper!=null){
                					if(protocolDisplayInstanceCtrlItemsHandsontableHelper.hot!=undefined){
                						protocolDisplayInstanceCtrlItemsHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayInstanceCtrlItemsHandsontableHelper=null;
                				}
                				if(protocolDisplayInstanceInputItemsHandsontableHelper!=null){
                					if(protocolDisplayInstanceInputItemsHandsontableHelper.hot!=undefined){
                						protocolDisplayInstanceInputItemsHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayInstanceInputItemsHandsontableHelper=null;
                				}
                        	}else if(record.data.classes==2){//选中显示单元
                        		CreateProtocolDisplayInstanceAcqItemsInfoTable(record.parentNode.data.id,record.parentNode.data.text,record.parentNode.data.classes);
                        		CreateProtocolDisplayInstanceCalItemsInfoTable(record.parentNode.data.id,record.parentNode.data.text,record.parentNode.data.classes,record.parentNode.data.calculateType);
                        		CreateProtocolDisplayInstanceInputItemsInfoTable(record.parentNode.data.id,record.parentNode.data.text,record.parentNode.data.classes,record.parentNode.data.calculateType);
                        		CreateProtocolDisplayInstanceCtrlItemsInfoTable(record.parentNode.data.id,record.parentNode.data.text,record.parentNode.data.classes);
                        	}else{
                        		CreateProtocolDisplayInstanceAcqItemsInfoTable(record.data.id,record.data.text,record.data.classes);
                        		CreateProtocolDisplayInstanceCalItemsInfoTable(record.data.id,record.data.text,record.data.classes,record.data.calculateType);
                        		CreateProtocolDisplayInstanceInputItemsInfoTable(record.data.id,record.data.text,record.data.classes,record.data.calculateType);
                        		CreateProtocolDisplayInstanceCtrlItemsInfoTable(record.data.id,record.data.text,record.data.classes);
                        	}
                        	
                        	CreateProtocolDisplayInstancePropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes!=1){
                        		return;
                        	}else{
                        		info=loginUserLanguageResource.displayInstance;
                        	}
                        	var menu = Ext.create('Ext.menu.Menu', {
                                floating: true,
                                items: [{
                                    text: '删除'+info,
                                    glyph: 0xf056,
                                    handler: function () {
//                                        Ext.MessageBox.confirm("确认","您确定要进行删除操作吗?",
//                                            function(ok){
//                                                if("yes"==ok) {
                                    	
                                                	if(record.data.classes==1){
                                                		var configInfo={};
                                            			configInfo.delidslist=[];
                                            			configInfo.delidslist.push(record.data.id);
                                            			SaveModbusProtocolDisplayInstanceData(configInfo);
                                                	}
                                                	
                                                	
//                                                }
//                                            }
//                                        )
                                    }
                                }],
                                renderTo: document.body
                            });
                        	var xy = Ext.get(td).getXY();
                            Ext.menu.MenuMgr.hideAll();//这个方法避免每次都点击的时候出现重复菜单。
                            menu.showAt(xy[0] + 100, xy[1]);
                        }
                    }
                });
                var panel = Ext.getCmp("ModbusProtocolDisplayInstanceConfigPanel_Id");
                panel.add(treeGridPanel);
            }
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolDisplayInstanceTreeSelectRow_Id").getValue());
            if(selectedRow==0){
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes>0){
            			selectedRow=i;
            			break;
            		}
            	}
            }
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});