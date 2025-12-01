Ext.define('AP.store.acquisitionUnit.ModbusProtocolAcqUnitTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolAcqUnitTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/acquisitionUnitTreeData',
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
        	var protocolTreeGridPanelSelection= Ext.getCmp("AcqUnitProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
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
        	var gridPanel = Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolAcqGroupConfigTreeGridPanel_Id",
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
                    	text: loginUserLanguageResource.acqUnitList,
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
                        	Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").setValue(index);
                        	Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRowClasses_Id").setValue(record.data.classes);
                        	Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRowId_Id").setValue(record.data.id);
                        	var tabPanel = Ext.getCmp("ModbusProtocolAcqUnitConfigRightTabPanel_Id");
                        	var propertiesTabPanel = tabPanel.getComponent("ModbusProtocolAcqUnitPropertiesConfigPanel_Id");
                			var acqGroupItemsTabPanel = tabPanel.getComponent("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id");
                        	if(record.data.classes==0 || record.data.classes==1){
                        		Ext.getCmp("AcqUnitConfigInformationLabel_Id").setHtml('');
                        	    Ext.getCmp("AcqUnitConfigInformationLabel_Id").hide();
                        		
                        		if(protocolConfigAcqUnitPropertiesHandsontableHelper!=null){
                					if(protocolConfigAcqUnitPropertiesHandsontableHelper.hot!=undefined){
                						protocolConfigAcqUnitPropertiesHandsontableHelper.hot.destroy();
                					}
                					protocolConfigAcqUnitPropertiesHandsontableHelper=null;
                				}
                        		
                        		if(protocolAcqUnitConfigItemsHandsontableHelper!=null){
                					if(protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
                						protocolAcqUnitConfigItemsHandsontableHelper.hot.destroy();
                					}
                					protocolAcqUnitConfigItemsHandsontableHelper=null;
                				}
                        		if(propertiesTabPanel!=undefined){
                        			tabPanel.remove("ModbusProtocolAcqUnitPropertiesConfigPanel_Id");
                        		}
                        		if(acqGroupItemsTabPanel!=undefined){
                        			tabPanel.remove("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id");
                        		}
                        	}else if(record.data.classes==2){
                    			if(tabPanel.getActiveTab()==undefined){//无激活标签
                    				if(acqGroupItemsTabPanel!=undefined){
                            			tabPanel.remove("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id");
                            		}
                					if(propertiesTabPanel==undefined){
                    					tabPanel.insert(0,acqUnitConfigRightTabPanelItems[0]);
                    				}
                					tabPanel.setActiveTab("ModbusProtocolAcqUnitPropertiesConfigPanel_Id");
                    			}else{
                    				var activeId = tabPanel.getActiveTab().id; 
                    				if(activeId=='ModbusProtocolAcqUnitPropertiesConfigPanel_Id'){
                    					if(acqGroupItemsTabPanel!=undefined){
                    						if(protocolAcqUnitConfigItemsHandsontableHelper!=null){
                            					if(protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
                            						protocolAcqUnitConfigItemsHandsontableHelper.hot.destroy();
                            					}
                            					protocolAcqUnitConfigItemsHandsontableHelper=null;
                            				}
                                			tabPanel.remove("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id");
                                		}
                    					CreateProtocolAcqUnitConfigPropertiesInfoTable(record.data);
                    				}else if(activeId=='ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id'){
                    					if(protocolAcqUnitConfigItemsHandsontableHelper!=null){
                        					if(protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
                        						protocolAcqUnitConfigItemsHandsontableHelper.hot.destroy();
                        					}
                        					protocolAcqUnitConfigItemsHandsontableHelper=null;
                        				}
                    					tabPanel.remove("ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id");
                    					
                    					if(propertiesTabPanel==undefined){
                        					tabPanel.insert(0,acqUnitConfigRightTabPanelItems[0]);
                        				}
                    					tabPanel.setActiveTab("ModbusProtocolAcqUnitPropertiesConfigPanel_Id");
                    				}
                    			}
                        	}else if(record.data.classes==3){
                        		if(tabPanel.getActiveTab()==undefined){//无激活标签
                					if(propertiesTabPanel==undefined){
                    					tabPanel.insert(0,acqUnitConfigRightTabPanelItems[0]);
                    				}
                					if(acqGroupItemsTabPanel==undefined){
                						tabPanel.insert(1,acqUnitConfigRightTabPanelItems[1]);
                            		}
                					tabPanel.setActiveTab("ModbusProtocolAcqUnitPropertiesConfigPanel_Id");
                    			}else{
                    				var activeId = tabPanel.getActiveTab().id; 
                    				if(activeId=='ModbusProtocolAcqUnitPropertiesConfigPanel_Id'){
                    					CreateProtocolAcqUnitConfigPropertiesInfoTable(record.data);
                    					if(acqGroupItemsTabPanel==undefined){
                    						tabPanel.insert(1,acqUnitConfigRightTabPanelItems[1]);
                                		}
                    				}else if(activeId=='ModbusProtocolAcqGroupItemsConfigTableInfoPanel_Id'){
                    					CreateProtocolAcqUnitItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code,record.data.type,record.data.text);
                    					if(propertiesTabPanel==undefined){
                    						tabPanel.insert(0,acqUnitConfigRightTabPanelItems[0]);
                                		}
                    				}
                    			}
                        	}
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='node';
                        	if(record.data.classes==0 || record.data.classes==1){
                        		return;
                        	}else if(record.data.classes==2){
                        		info=loginUserLanguageResource.acqUnit;
                        	}else if(record.data.classes==3){
                        		if(record.data.type==0){
                        			info=loginUserLanguageResource.acqGroup;
                        		}else if(record.data.type==1){
                        			info=loginUserLanguageResource.controlGroup;
                        		}
                        	}
                        	var menu = Ext.create('Ext.menu.Menu', {
                                floating: true,
                                items: [{
                                    text: loginUserLanguageResource.deleteData,
                                    glyph: 0xf056,
                                    handler: function () {
                                    	if(record.data.classes==2){
                                    		var acqUnitSaveData={};
                                    		acqUnitSaveData.delidslist=[];
                                    		acqUnitSaveData.delidslist.push(record.data.id);
                                    		saveAcquisitionUnitConfigData(acqUnitSaveData,record.data.protocol,record.parentNode.data.deviceType);
                                    	}else if(record.data.classes==3){
                                    		var acqGroupSaveData={};
                                    		acqGroupSaveData.delidslist=[];
                                    		acqGroupSaveData.delidslist.push(record.data.id);
                                    		saveAcquisitionGroupConfigData(acqGroupSaveData,record.data.protocol,record.parentNode.data.id);
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
                var panel = Ext.getCmp("ModbusProtocolAcqGroupConfigPanel_Id");
                panel.add(gridPanel);
            }
//            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").getValue());
//            if(selectedRow==0){
//            	for(var i=0;i<store.data.length;i++){
//            		if(store.getAt(i).data.classes>=2){
//            			selectedRow=i;
//            			if(isNotVal(store.getAt(i).data.children) && store.getAt(i).data.children.length>0){
//            				selectedRow+=1;
//            			}
//            			break;
//            		}
//            	}
//            }
            
            
            var selectedRow=0;
            var addObjectName=Ext.getCmp("ModbusProtocolAcqGroupConfigAddObjectName_Id").getValue();
            var addObjectClasses=parseInt(Ext.getCmp("ModbusProtocolAcqGroupConfigAddObjectClasses_Id").getValue());
            if(isNotVal(addObjectName)){
            	Ext.getCmp("ModbusProtocolAcqGroupConfigAddObjectName_Id").setValue('');
            	Ext.getCmp("ModbusProtocolAcqGroupConfigAddObjectClasses_Id").setValue('');
            	var maxObjectId=0;
            	var objectCount=0;
            	
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes==addObjectClasses && store.getAt(i).data.text==addObjectName){
            			selectedRow=i;
            			objectCount++;
            		}
            	}
            	if(objectCount>1){
            		for(var i=0;i<store.data.length;i++){
                		if(store.getAt(i).data.classes==addObjectClasses && store.getAt(i).data.text==addObjectName){
                			if(store.getAt(i).data.id>maxObjectId){
                				maxObjectId=store.getAt(i).data.id;
                				selectedRow=i;
                			}
                		}
                	}
            	}
            }else{
                selectedRow=parseInt(Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").getValue());
                var selectedObjectId=parseInt(Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRowId_Id").getValue());
                var selectedObjectClasses=parseInt(Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRowClasses_Id").getValue());
                
                if(selectedRow==0){
                	for(var i=0;i<store.data.length;i++){
                		if(store.getAt(i).data.classes>=2){
                			selectedRow=i;
                			if(isNotVal(store.getAt(i).data.children) && store.getAt(i).data.children.length>0){
                				selectedRow+=1;
                			}
                			break;
                		}
                	}
                }else{
                	for(var i=0;i<store.data.length;i++){
                		if(store.getAt(i).data.id==selectedObjectId){
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