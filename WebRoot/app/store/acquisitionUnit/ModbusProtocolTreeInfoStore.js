Ext.define('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData',
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
        	var treeGridPanel = Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolAddrMappingConfigTreeGridPanel_Id",
//                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: loginUserLanguageResource.protocolList,
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
                        	Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").setValue(index);
                        	
                        	if(Ext.getCmp("ProtocolConfigRightTabPanel_Id").getActiveTab().id=='ProtocolPropertiesConfigRightTabPanel_Id'){
                        		CreateProtocolConfigAddrMappingPropertiesInfoTable(record.data);
                        	}else if(Ext.getCmp("ProtocolConfigRightTabPanel_Id").getActiveTab().id=='ProtocolContentConfigRightTabPanel_Id'){
                        		if(record.data.classes==0){
                            		if(protocolItemsConfigHandsontableHelper!=null){
                    					if(protocolItemsConfigHandsontableHelper.hot!=undefined){
                    						protocolItemsConfigHandsontableHelper.hot.destroy();
                    					}
                    					protocolItemsConfigHandsontableHelper=null;
                    				}
                            		if(protocolItemsMeaningConfigHandsontableHelper!=null){
                    					if(protocolItemsMeaningConfigHandsontableHelper.hot!=undefined){
                    						protocolItemsMeaningConfigHandsontableHelper.hot.destroy();
                    					}
                    					protocolItemsMeaningConfigHandsontableHelper=null;
                    				}
                            	}else if(record.data.classes==1){
                            		CreateModbusProtocolAddrMappingItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                            	}
                        	}else if(Ext.getCmp("ProtocolConfigRightTabPanel_Id").getActiveTab().id=="ProtocolExtendedFieldConfigRightTabPanel_Id"){
                        		if(record.data.classes==0){
                            		if(protocolExtendedFieldConfigHandsontableHelper!=null){
                    					if(protocolExtendedFieldConfigHandsontableHelper.hot!=undefined){
                    						protocolExtendedFieldConfigHandsontableHelper.hot.destroy();
                    					}
                    					protocolExtendedFieldConfigHandsontableHelper=null;
                    				}
                            	}else if(record.data.classes==1){
                            		CreateProtocolExtendedFieldConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                            	}
                        	}
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes==0){
                        		return;
                        	}else if(record.data.classes==1){
                        		info='协议';
                        	}
                        	var menu = Ext.create('Ext.menu.Menu', {
                                floating: true,
                                items: [{
                                    text: loginUserLanguageResource.deleteData,
                                    glyph: 0xf056,
                                    handler: function () {
//                                        Ext.MessageBox.confirm("确认","您确定要进行删除操作吗?",
//                                            function(ok){
//                                                if("yes"==ok) {
                                                	if(record.data.classes==1){
                                                		var configInfo={};
                                            			configInfo.delidslist=[];
                                            			configInfo.delidslist.push(record.data.text);
                                            			saveModbusProtocolAddrMappingConfigData(configInfo);
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
                var panel = Ext.getCmp("ModbusProtocolAddrMappingConfigPanel_Id");
                panel.add(treeGridPanel);
            }
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").getValue());
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