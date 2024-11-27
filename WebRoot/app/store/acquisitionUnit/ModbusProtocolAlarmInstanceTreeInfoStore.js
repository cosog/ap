Ext.define('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolAlarmInstanceTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/modbusAlarmInstanceConfigTreeData',
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
        	var treeGridPanel = Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
                treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id",
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
                    	text: loginUserLanguageResource.alarmInstanceList,
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
                        	Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").setValue(index);
                        	
                        	var activeId = Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").getActiveTab().id;
                        	if(activeId=="ModbusProtocolAlarmInstanceNumItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                        			if(protocolAlarmInstanceConfigNumItemsHandsontableHelper!=null){
                    					if(protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmInstanceConfigNumItemsHandsontableHelper=null;
                    				}
                    				if(protocolAlarmInstanceConfigCalNumItemsHandsontableHelper!=null){
                    					if(protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmInstanceConfigCalNumItemsHandsontableHelper=null;
                    				}
                    				Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle("数据量报警项");
                        		}else{
                            		CreateProtocolAlarmInstanceNumItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes);
                            		CreateProtocolAlarmInstanceCalNumItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes,record.data.deviceType);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmInstanceSwitchItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                        			if(protocolAlarmInstanceConfigSwitchItemsHandsontableHelper!=null){
                    					if(protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmInstanceConfigSwitchItemsHandsontableHelper=null;
                    				}
                        			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle("开关量报警项");
                        		}else{
                            		CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmInstanceEnumItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                        			if(protocolAlarmInstanceConfigEnumItemsHandsontableHelper!=null){
                    					if(protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmInstanceConfigEnumItemsHandsontableHelper=null;
                    				}
                        			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle("枚举量报警项");
                        		}else{
                            		CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                        			if(protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper!=null){
                    					if(protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper=null;
                    				}
                        			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle("工况诊断报警项");
                        		}else{
                            		CreateProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmInstanceRunStatusItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                        			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle("运行状态报警项");
                        			if(protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper!=null){
                    					if(protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper=null;
                    				}
                        		}else{
                            		CreateProtocolAlarmInstanceRunStatusItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmInstanceCommStatusItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                        			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle("通信状态报警项");
                        			if(protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper!=null){
                    					if(protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper=null;
                    				}
                        		}else{
                            		CreateProtocolAlarmInstanceCommStatusItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes);
                            	}
                        	}
                        	CreateProtocolAlarmInstancePropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes!=1){
                        		return;
                        	}else{
                        		info=loginUserLanguageResource.alarmInstance;
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
                                            			SaveModbusProtocolAlarmInstanceData(configInfo);
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
                var panel = Ext.getCmp("ModbusProtocolAlarmInstanceConfigPanel_Id");
                panel.add(treeGridPanel);
            }
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").getValue());
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