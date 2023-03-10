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
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '显示单元列表',
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
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			CreateProtocolDisplayUnitAcqItemsConfigInfoTable(record.data.children[0].text,record.data.children[0].classes,record.data.children[0].code);
                        			CreateProtocolDisplayUnitCtrlItemsConfigInfoTable(record.data.children[0].text,record.data.children[0].classes,record.data.children[0].code);
                        		}else{
                        			CreateProtocolDisplayUnitAcqItemsConfigInfoTable('',1,'');
                        			CreateProtocolDisplayUnitCtrlItemsConfigInfoTable('',1,'');
                            	}
                        		CreateProtocolDisplayUnitCalItemsConfigInfoTable(record.data.deviceType,record.data.children[0].classes);
                        	}else if(record.data.classes==1){
                        		CreateProtocolDisplayUnitAcqItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                        		CreateProtocolDisplayUnitCtrlItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                        		CreateProtocolDisplayUnitCalItemsConfigInfoTable(record.parentNode.data.deviceType,record.data.classes);
                        	}else if(record.data.classes==2){
                        		CreateProtocolDisplayUnitAcqItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code,record.data.id,record.data.acqUnitId,record.data.text);
                        		CreateProtocolDisplayUnitCtrlItemsConfigInfoTable(record.data.protocol,record.data.classes,record.data.code,record.data.id,record.data.acqUnitId,record.data.text);
                        		CreateProtocolDisplayUnitCalItemsConfigInfoTable(record.parentNode.parentNode.data.deviceType,record.data.classes,record.data.id,record.data.text);
                        	}
                        	CreateProtocolDisplayUnitConfigPropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes==0 || record.data.classes==1){
                        		return;
                        	}if(record.data.classes==2){
                        		info='显示单元';
                        	}
                        	var menu = Ext.create('Ext.menu.Menu', {
                                floating: true,
                                items: [{
                                    text: '删除'+info,
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
            gridPanel.getSelectionModel().deselectAll(true);
            gridPanel.getSelectionModel().select(0, true);
        }
    }
});