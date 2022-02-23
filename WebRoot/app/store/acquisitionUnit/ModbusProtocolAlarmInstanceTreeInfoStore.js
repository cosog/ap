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
        },
        load: function (store, options, eOpts) {
        	var treeGridPanel = Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
                treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id",
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
                    	text: '报警实例列表',
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text'
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
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateProtocolAlarmInstanceNumItemsConfigInfoTable(record.data.children[0].id,record.data.children[0].text,record.data.children[0].classes);
                            		}else{
                            			CreateProtocolAlarmInstanceNumItemsConfigInfoTable(-1,'',1);
                            		}
                            	}else{
                            		CreateProtocolAlarmInstanceNumItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmInstanceSwitchItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(record.data.children[0].id,record.data.children[0].text,record.data.children[0].classes);
                            		}else{
                            			CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(-1,'',1);
                            		}
                            	}else{
                            		CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes);
                            	}
                        	}else if(activeId=="ModbusProtocolAlarmInstanceEnumItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(record.data.children[0].id,record.data.children[0].text,record.data.children[0].classes);
                            		}else{
                            			CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(-1,'',1);
                            		}
                            	}else{
                            		CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(record.data.id,record.data.text,record.data.classes);
                            	}
                        	}
                        	CreateProtocolAlarmInstancePropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes!=1){
                        		return;
                        	}else{
                        		info='报警实例';
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
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(0, true);
        }
    }
});