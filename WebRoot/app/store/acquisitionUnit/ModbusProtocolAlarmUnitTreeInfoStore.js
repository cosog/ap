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
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '报警单元列表',
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
                        	Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").setValue(index);
                        	var activeId = Ext.getCmp("ModbusProtocolAlarmUnitItemsConfigTabPanel_Id").getActiveTab().id;
                			if(activeId=="ModbusProtocolAlarmUnitNumItemsConfigTableInfoPanel_Id"){
                				if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateProtocolAlarmUnitNumItemsConfigInfoTable(record.data.children[0].text,record.data.children[0].classes,record.data.children[0].code);
                            		}
                            	}else if(record.data.classes==1){
                            		CreateProtocolAlarmUnitNumItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                            	}else if(record.data.classes==2||record.data.classes==3){
                            		CreateProtocolAlarmUnitNumItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code);
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
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(record.data.children[0].text,record.data.children[0].classes,record.data.children[0].code);
                            		}
                            	}else if(record.data.classes==1){
                            		CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                            	}else if(record.data.classes==2||record.data.classes==3){
                            		CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code);
                            	}
                        	}
                        	CreateProtocolAlarmUnitConfigPropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes==0 || record.data.classes==1){
                        		return;
                        	}if(record.data.classes==2){
                        		info='报警单元';
                        	}else if(record.data.classes==3){
                        		info='报警单元';
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
            gridPanel.getSelectionModel().deselectAll(true);
            gridPanel.getSelectionModel().select(0, true);
        }
    }
});