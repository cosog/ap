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
        	var gridPanel = Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolAcqGroupConfigTreeGridPanel_Id",
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
                        	if(record.data.classes==0 || record.data.classes==1){
                        		if(protocolAcqUnitConfigItemsHandsontableHelper!=null){
                					if(protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
                						protocolAcqUnitConfigItemsHandsontableHelper.hot.destroy();
                					}
                					protocolAcqUnitConfigItemsHandsontableHelper=null;
                				}
                        	}
                        	
                        	else if(record.data.classes==2){
                        		CreateProtocolAcqUnitItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code);
                        	}else if(record.data.classes==3){
                        		CreateProtocolAcqUnitItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code,record.data.type);
                        	}
                        	CreateProtocolAcqUnitConfigPropertiesInfoTable(record.data);
                        	
                        	if(loginUserProtocolConfigModuleRight.editFlag==1){
                        		if(record.data.classes==3){
                        			Ext.getCmp('acqUnitConfigSelectAllBtn').enable();
                        			Ext.getCmp('acqUnitConfigDeselectAllBtn').enable();
                        		}else{
                        			Ext.getCmp('acqUnitConfigSelectAllBtn').disable();
                        			Ext.getCmp('acqUnitConfigDeselectAllBtn').disable();
                        		}
                        	}
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes==0 || record.data.classes==1){
                        		return;
                        	}else if(record.data.classes==2){
                        		info=loginUserLanguageResource.acqUnit;
                        	}else if(record.data.classes==3){
                        		info='采控组';
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
                var panel = Ext.getCmp("ModbusProtocolAcqGroupConfigPanel_Id");
                panel.add(gridPanel);
            }
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").getValue());
            if(selectedRow==0){
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes>1){
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