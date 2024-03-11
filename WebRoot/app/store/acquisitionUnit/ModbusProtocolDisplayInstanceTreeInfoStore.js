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
        	var tabIds='';
        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        	if(tabTreeGridPanelSelection.length>0){
        		tabIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
        	}
        	var new_params = {
        			tabIds: tabIds
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
                    	text: '显示实例列表',
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
                        	if(record.data.classes==0){//选中设备类型deviceType
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			CreateProtocolDisplayInstanceAcqItemsInfoTable(record.data.children[0].id,record.data.children[0].text,record.data.children[0].classes);
                        			CreateProtocolDisplayInstanceCalItemsInfoTable(record.data.children[0].id,record.data.children[0].text,record.data.children[0].classes,record.data.deviceType);
                        			CreateProtocolDisplayInstanceInputItemsInfoTable(record.data.children[0].id,record.data.children[0].text,record.data.children[0].classes,record.data.deviceType);
                        			CreateProtocolDisplayInstanceCtrlItemsInfoTable(record.data.children[0].id,record.data.children[0].text,record.data.children[0].classes);
                        		}else{
                        			CreateProtocolDisplayInstanceAcqItemsInfoTable(-1,'',1);
                        			CreateProtocolDisplayInstanceCalItemsInfoTable(-1,'',1,record.data.deviceType);
                        			CreateProtocolDisplayInstanceInputItemsInfoTable(-1,'',1,record.data.deviceType);
                        			CreateProtocolDisplayInstanceCtrlItemsInfoTable(-1,'',1);
                        		}
                        	}else if(record.data.classes==2){//选中显示单元
                        		CreateProtocolDisplayInstanceAcqItemsInfoTable(record.parentNode.data.id,record.parentNode.data.text,record.parentNode.data.classes);
                        		CreateProtocolDisplayInstanceCalItemsInfoTable(record.parentNode.data.id,record.parentNode.data.text,record.parentNode.data.classes,record.parentNode.data.deviceType);
                        		CreateProtocolDisplayInstanceInputItemsInfoTable(record.parentNode.data.id,record.parentNode.data.text,record.parentNode.data.classes,record.parentNode.data.deviceType);
                        		CreateProtocolDisplayInstanceCtrlItemsInfoTable(record.parentNode.data.id,record.parentNode.data.text,record.parentNode.data.classes);
                        	}else{
                        		CreateProtocolDisplayInstanceAcqItemsInfoTable(record.data.id,record.data.text,record.data.classes);
                        		CreateProtocolDisplayInstanceCalItemsInfoTable(record.data.id,record.data.text,record.data.classes,record.data.deviceType);
                        		CreateProtocolDisplayInstanceInputItemsInfoTable(record.data.id,record.data.text,record.data.classes,record.data.deviceType);
                        		CreateProtocolDisplayInstanceCtrlItemsInfoTable(record.data.id,record.data.text,record.data.classes);
                        	}
                        	
                        	CreateProtocolDisplayInstancePropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes!=1){
                        		return;
                        	}else{
                        		info='显示实例';
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
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});