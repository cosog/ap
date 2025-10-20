Ext.define('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolAlarmUnitTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/modbusProtocolAlarmUnitTreeData',
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
        	var protocolList=[];
        	var protocolTreeGridPanelSelection= Ext.getCmp("AlarmUnitProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
        	if(protocolTreeGridPanelSelection.length>0){
        		if(protocolTreeGridPanelSelection[0].data.classes==1){
        			protocolList.push(protocolTreeGridPanelSelection[0].data.code);
        		}else{
        			if(isNotVal(protocolTreeGridPanelSelection[0].data.children)){
        				for(var i=0;i<protocolTreeGridPanelSelection[0].data.children.length;i++){
        					protocolList.push(protocolTreeGridPanelSelection[0].data.children[i].code);
        				}
        			}
        		}
        		
        	}
        	var new_params = {
        			protocol: protocolList.join(",")
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var gridPanel = Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolAlarmUnitConfigTreeGridPanel_Id",
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
                    	text: loginUserLanguageResource.alarmUnitList,
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
                        	Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").setValue(index);
                        	
                        	var tabPanel = Ext.getCmp("ModbusProtocolAlarmUnitConfigRightTabPanel_Id");
                        	var propertiesTabPanel = tabPanel.getComponent("ModbusProtocolAlarmUnitPropertiesConfigPanel_Id");
                			var itemsConfigTabPanel = tabPanel.getComponent("ModbusProtocolAlarmUnitItemsConfigTabPanel_Id");
                        	if(record.data.classes!=3){
                        		Ext.getCmp("AlarmUnitConfigInformationLabel_Id").setHtml('');
                        	    Ext.getCmp("AlarmUnitConfigInformationLabel_Id").hide();
                        	    
                        		if(protocolConfigAlarmUnitPropertiesHandsontableHelper!=null){
                					if(protocolConfigAlarmUnitPropertiesHandsontableHelper.hot!=undefined){
                						protocolConfigAlarmUnitPropertiesHandsontableHelper.hot.destroy();
                					}
                					protocolConfigAlarmUnitPropertiesHandsontableHelper=null;
                				}
                        		
                        		if(protocolAlarmUnitConfigNumItemsHandsontableHelper!=null){
                					if(protocolAlarmUnitConfigNumItemsHandsontableHelper.hot!=undefined){
                						protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.destroy();
                					}
                					protocolAlarmUnitConfigNumItemsHandsontableHelper=null;
                				}
                				if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper!=null){
                					if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined){
                						protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.destroy();
                					}
                					protocolAlarmUnitConfigSwitchItemsHandsontableHelper=null;
                				}
                				if(protocolAlarmUnitConfigEnumItemsHandsontableHelper!=null){
                					if(protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot!=undefined){
                						protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot.destroy();
                					}
                					protocolAlarmUnitConfigEnumItemsHandsontableHelper=null;
                				}
                				if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper!=null){
                					if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot!=undefined){
                						protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.destroy();
                					}
                					protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper=null;
                				}
                				if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper!=null){
                					if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=undefined){
                						protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.destroy();
                					}
                					protocolAlarmUnitConfigRunStatusItemsHandsontableHelper=null;
                				}
                				if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper!=null){
                					if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=undefined){
                						protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.destroy();
                					}
                					protocolAlarmUnitConfigCommStatusItemsHandsontableHelper=null;
                				}
                        		if(propertiesTabPanel!=undefined){
                        			tabPanel.remove("ModbusProtocolAlarmUnitPropertiesConfigPanel_Id");
                        		}
                        		if(itemsConfigTabPanel!=undefined){
                        			tabPanel.remove("ModbusProtocolAlarmUnitItemsConfigTabPanel_Id");
                        		}
                        	}else if(record.data.classes==3){
                        		if(tabPanel.getActiveTab()==undefined){//无激活标签
                					if(propertiesTabPanel==undefined){
                    					tabPanel.insert(0,alarmUnitConfigRightTabPanelItems[0]);
                    				}
                					if(itemsConfigTabPanel==undefined){
                						tabPanel.insert(1,alarmUnitConfigRightTabPanelItems[1]);
                            		}
                					tabPanel.setActiveTab("ModbusProtocolAlarmUnitPropertiesConfigPanel_Id");
                    			}else{
                    				var activeId = tabPanel.getActiveTab().id; 
                    				if(activeId=='ModbusProtocolAlarmUnitPropertiesConfigPanel_Id'){
                    					CreateProtocolAlarmUnitConfigPropertiesInfoTable(record.data);
                    					if(itemsConfigTabPanel==undefined){
                    						tabPanel.insert(1,alarmUnitConfigRightTabPanelItems[1]);
                                		}
                    				}else if(activeId=='ModbusProtocolAlarmUnitItemsConfigTabPanel_Id'){
                    					CreateProtocolAlarmUnitContentConfigInfoTable(record);
                    					if(propertiesTabPanel==undefined){
                    						tabPanel.insert(0,alarmUnitConfigRightTabPanelItems[0]);
                                		}
                    				}
                    			}
                        	}
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes==0 || record.data.classes==1){
                        		return;
                        	}else if(record.data.classes==2){
                        		info=loginUserLanguageResource.alarmUnit;
                        	}else if(record.data.classes==3){
                        		info=loginUserLanguageResource.alarmUnit;
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
                                                	if(record.data.classes==3){
                                                		var saveData={};
                                                		saveData.delidslist=[];
                                                		saveData.delidslist.push(record.data.id);
                                                		SaveModbusProtocolAlarmUnitConfigData(saveData);
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
                var panel = Ext.getCmp("ModbusProtocolAlarmUnitConfigPanel_Id");
                panel.add(gridPanel);
            }
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue());
            if(selectedRow==0){
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes>0){
            			selectedRow=i;
            			if(store.getAt(i).data.classes==3){
            				break;
            			}
            		}
            	}
            }
            gridPanel.getSelectionModel().deselectAll(true);
            gridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});