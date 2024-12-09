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
                        	if(record.data.classes==0){
                        		if(protocolDisplayUnitAcqItemsConfigHandsontableHelper!=null){
                					if(protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot!=undefined){
                						protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayUnitAcqItemsConfigHandsontableHelper=null;
                				}
                				if(protocolDisplayUnitCalItemsConfigHandsontableHelper!=null){
                					if(protocolDisplayUnitCalItemsConfigHandsontableHelper.hot!=undefined){
                						protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayUnitCalItemsConfigHandsontableHelper=null;
                				}
                				if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper!=null){
                					if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot!=undefined){
                						protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayUnitCtrlItemsConfigHandsontableHelper=null;
                				}
                				if(protocolDisplayUnitInputItemsConfigHandsontableHelper!=null){
                					if(protocolDisplayUnitInputItemsConfigHandsontableHelper.hot!=undefined){
                						protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.destroy();
                					}
                					protocolDisplayUnitInputItemsConfigHandsontableHelper=null;
                				}
                				Ext.getCmp("ModbusProtocolDisplayUnitAcqItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.acquisitionItemConfig);
                				Ext.getCmp("ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.controlItemConfig);
                				Ext.getCmp("ModbusProtocolDisplayUnitCalItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.calculateItemConfig);
                				Ext.getCmp("ModbusProtocolDisplayUnitInputItemsConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.inputItemConfig);
                        	}else if(record.data.classes==1){
                        		CreateProtocolDisplayUnitAcqItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                        		CreateProtocolDisplayUnitCtrlItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                        		CreateProtocolDisplayUnitCalItemsConfigInfoTable(record.parentNode.data.deviceType,record.data.classes);
                        		CreateProtocolDisplayUnitInputItemsConfigInfoTable(record.parentNode.data.deviceType,record.data.classes);
                        	}else if(record.data.classes==2){
                        		CreateProtocolDisplayUnitAcqItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code,record.data.id,record.data.acqUnitId,record.data.text);
                        		CreateProtocolDisplayUnitCtrlItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code,record.data.id,record.data.acqUnitId,record.data.text);
                        		CreateProtocolDisplayUnitCalItemsConfigInfoTable(record.parentNode.parentNode.data.deviceType,record.data.classes,record.data.id,record.data.text,record.data.calculateType);
                        		CreateProtocolDisplayUnitInputItemsConfigInfoTable(record.parentNode.parentNode.data.deviceType,record.data.classes,record.data.id,record.data.text,record.data.calculateType);
                        	}
                        	CreateProtocolDisplayUnitConfigPropertiesInfoTable(record.data);
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
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").getValue());
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