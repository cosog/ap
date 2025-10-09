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
        	var protocolList=[];
        	var protocolTreeGridPanelSelection= Ext.getCmp("AlarmInstanceProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
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
        	var treeGridPanel = Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
                treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id",
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
                    	text: loginUserLanguageResource.alarmInstanceList,
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text',
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    },{
                    	text: loginUserLanguageResource.primaryDeviceCount,
                    	align: 'center',
                    	dataIndex: 'deviceCount',
                    	flex: 3
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
                        	CreateProtocolAlarmInstancePropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes!=1){
                        		return;
                        	}else{
                        		info=loginUserLanguageResource.alarmInstance;
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
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").getValue());
            if(selectedRow==0 || selectedRow>=store.data.length ){
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes>0){
            			selectedRow=i;
            			break;
            		}
            	}
            }
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});