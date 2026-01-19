Ext.define('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolDisplayUnitTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/displayUnitTreeData',
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
        	var protocolTreeGridPanelSelection= Ext.getCmp("DisplayUnitProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
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
        	var gridPanel = Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolDisplayUnitConfigTreeGridPanel_Id",
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
                    	text: loginUserLanguageResource.displayUnitList,
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
                        	Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").setValue(index);
                        	Ext.getCmp("DisplayUnitTreeSelectUnitId_Id").setValue(record.data.id);

                        	var tabPanel = Ext.getCmp("ModbusProtocolDisplayUnitConfigRightTabPanel_Id");
                        	var propertiesTabPanel = tabPanel.getComponent("ModbusProtocolDisplayUnitPropertiesConfigPanel_Id");
                			var itemsConfigTabPanel = tabPanel.getComponent("ModbusProtocolDisplayUnitItemsConfigTableInfoPanel_Id");
                        	if(record.data.classes!=2){
                        		Ext.getCmp("DisplayUnitConfigInformationLabel_Id").setHtml('');
                        	    Ext.getCmp("DisplayUnitConfigInformationLabel_Id").hide();
                        		
                        		if(protocolDisplayUnitPropertiesHandsontableHelper!=null){
                					if(protocolDisplayUnitPropertiesHandsontableHelper.hot!=undefined){
                						protocolDisplayUnitPropertiesHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayUnitPropertiesHandsontableHelper=null;
                				}
                        		
                        		if(protocolDisplayUnitAcqItemsConfigHandsontableHelper!=null){
                					if(protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot!=undefined){
                						protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayUnitAcqItemsConfigHandsontableHelper=null;
                				}
                				if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper!=null){
                					if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot!=undefined){
                						protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayUnitCtrlItemsConfigHandsontableHelper=null;
                				}
                        		
                        		
                        		if(propertiesTabPanel!=undefined){
                        			tabPanel.remove("ModbusProtocolDisplayUnitPropertiesConfigPanel_Id");
                        		}
                        		if(itemsConfigTabPanel!=undefined){
                        			tabPanel.remove("ModbusProtocolDisplayUnitItemsConfigTableInfoPanel_Id");
                        		}
                        	}else if(record.data.classes==2){
                        		if(tabPanel.getActiveTab()==undefined){//无激活标签
                					if(propertiesTabPanel==undefined){
                    					tabPanel.insert(0,displayUnitConfigRightTabPanelItems[0]);
                    				}
                					if(itemsConfigTabPanel==undefined){
                						tabPanel.insert(1,displayUnitConfigRightTabPanelItems[1]);
                            		}
                					tabPanel.setActiveTab("ModbusProtocolDisplayUnitItemsConfigTableInfoPanel_Id");
                    			}else{
                    				var activeId = tabPanel.getActiveTab().id; 
                    				if(activeId=='ModbusProtocolDisplayUnitPropertiesConfigPanel_Id'){
                    					CreateProtocolDisplayUnitConfigPropertiesInfoTable(record.data);
                    					if(itemsConfigTabPanel==undefined){
                    						tabPanel.insert(1,displayUnitConfigRightTabPanelItems[1]);
                                		}
                    				}else if(activeId=='ModbusProtocolDisplayUnitItemsConfigTableInfoPanel_Id'){
                    					CreateProtocolDisplayUnitAcqItemsConfigInfoTable(record.data.protocolCode,record.data.classes,record.data.code,record.data.id,record.data.acqUnitId,record.data.text,record.data.calculateType);
                                		CreateProtocolDisplayUnitCtrlItemsConfigInfoTable(record.data.protocolCode,record.data.classes,record.data.code,record.data.id,record.data.acqUnitId,record.data.text,record.data.calculateType);
                    					
                    					if(propertiesTabPanel==undefined){
                    						tabPanel.insert(0,displayUnitConfigRightTabPanelItems[0]);
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
                        		info=loginUserLanguageResource.displayUnit;
                        	}
                        	var menu = Ext.create('Ext.menu.Menu', {
                                floating: true,
                                items: [{
                                    text: loginUserLanguageResource.deleteData,
                                    glyph: 0xf056,
                                    handler: function () {
                                    	if(record.data.classes==2){
                                    		var displayUnitSaveData={};
                                    		displayUnitSaveData.delidslist=[];
                                    		displayUnitSaveData.delidslist.push(record.data.id);
                                    		saveDisplayUnitConfigData(displayUnitSaveData,record.data.protocol,record.parentNode.data.deviceType);
                                    	}
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
                var panel = Ext.getCmp("ModbusProtocolDisplayUnitConfigPanel_Id");
                panel.add(gridPanel);
            }
//            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue());
//            if(selectedRow==0){
//            	for(var i=0;i<store.data.length;i++){
//            		if(store.getAt(i).data.classes>0){
//            			selectedRow=i;
//            			if(store.getAt(i).data.classes==2){
//            				break;
//            			}
//            		}
//            	}
//            }
            
            
            var selectedRow=0;
            var addUnitName=Ext.getCmp("AddNewDisplayUnitName_Id").getValue();
            if(isNotVal(addUnitName)){
            	Ext.getCmp("AddNewDisplayUnitName_Id").setValue('');
            	var maxInstanceId=0;
            	var instanceCount=0;
            	
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes>0 && store.getAt(i).data.text==addUnitName){
            			selectedRow=i;
            			instanceCount++;
            		}
            	}
            	if(instanceCount>1){
            		for(var i=0;i<store.data.length;i++){
                		if(store.getAt(i).data.classes>0 && store.getAt(i).data.text==addUnitName){
                			if(store.getAt(i).data.id>maxInstanceId){
                				maxInstanceId=store.getAt(i).data.id;
                				selectedRow=i;
                			}
                		}
                	}
            	}
            }else{
            	selectedRow=parseInt(Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue());
            	var selectedUnitId=parseInt(Ext.getCmp("DisplayUnitTreeSelectUnitId_Id").getValue());
                if(selectedUnitId==0){
                	for(var i=0;i<store.data.length;i++){
                		if(store.getAt(i).data.classes>0){
                			selectedRow=i;
                			break;
                		}
                	}
                }else{
                	for(var i=0;i<store.data.length;i++){
                		if(store.getAt(i).data.id==selectedUnitId){
                			selectedRow=i;
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