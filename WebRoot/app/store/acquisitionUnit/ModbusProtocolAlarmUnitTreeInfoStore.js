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
                        	var activeId = Ext.getCmp("ModbusProtocolAlarmUnitItemsConfigTabPanel_Id").getActiveTab().id;
                			if(activeId=="ModbusProtocolAlarmUnitNumItemsConfigTableInfoPanel_Id"){
                				if(record.data.classes==0){
                					if(protocolAlarmUnitConfigNumItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigNumItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigNumItemsHandsontableHelper=null;
                    				}
                    				if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigCalNumItemsHandsontableHelper=null;
                    				}
                            	}else if(record.data.classes==1){
                            		CreateProtocolAlarmUnitNumItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                            		CreateProtocolAlarmUnitCalNumItemsConfigInfoTable(record.data.deviceType,record.data.classes,record.data.code,0);
                            	}else if(record.data.classes==2||record.data.classes==3){
                            		CreateProtocolAlarmUnitNumItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code);
                            		CreateProtocolAlarmUnitCalNumItemsConfigInfoTable(record.data.deviceType,record.data.classes,record.data.code,record.data.calculateType);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmUnitSwitchItemsConfigTableInfoPanel_Id"){
                        		var gridPanel=Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsGridPanel_Id");
                        		if(isNotVal(gridPanel)){
                        			gridPanel.getSelectionModel().deselectAll(true);
                        			gridPanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitSwitchItemsStore');
                        		}
                        	}else if(activeId=="ModbusProtocolAlarmUnitEnumItemsConfigTableInfoPanel_Id"){
                        		var gridPanel=Ext.getCmp("ModbusProtocolAlarmUnitEnumItemsGridPanel_Id");
                        		if(isNotVal(gridPanel)){
                        			gridPanel.getSelectionModel().deselectAll(true);
                        			gridPanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitEnumItemsStore');
                        		}
                        	}else if(activeId=="ModbusProtocolAlarmUnitCommStatusConfigTableInfoPanel_Id"){
                    			if(record.data.classes==0){
                    				if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigCommStatusItemsHandsontableHelper=null;
                    				}
                    			}else if(record.data.classes==1){
                            		CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                            	}else if(record.data.classes==2||record.data.classes==3){
                            		CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmUnitRunStatusConfigTableInfoPanel_Id"){
                    			if(record.data.classes==0){
                    				if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigRunStatusItemsHandsontableHelper=null;
                    				}
                            	}else if(record.data.classes==1){
                            		CreateProtocolAlarmUnitRunStatusItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                            	}else if(record.data.classes==2||record.data.classes==3){
                            		CreateProtocolAlarmUnitRunStatusItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id"){
                    			if(record.data.classes==0){
                    				if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper=null;
                    				}
                            	}else if(record.data.classes==1){
                            		CreateProtocolAlarmUnitFESDiagramConditionsConfigInfoTable(record.data.text,record.data.classes,record.data.code,0);
                            	}else if(record.data.classes==2||record.data.classes==3){
                            		CreateProtocolAlarmUnitFESDiagramConditionsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code,record.data.calculateType);
                            	}
                        	}
                        	CreateProtocolAlarmUnitConfigPropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes==0 || record.data.classes==1){
                        		return;
                        	}if(record.data.classes==2){
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
            			break;
            		}
            	}
            }
            gridPanel.getSelectionModel().deselectAll(true);
            gridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});