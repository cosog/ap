Ext.define('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolReportUnitTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/repoerUnitTreeData',
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
        	var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolReportUnitConfigTreeGridPanel_Id",
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
                    	text: '单元列表',
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
                        	Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").setValue(index);
                        	
                        	var selectedUnitCode='';
                        	var selectedUnitId=0;
                        	if(record.data.classes==0){
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			selectedUnitCode=record.data.children[0].code;
                        			selectedUnitId=record.data.children[0].id;
                        		}
                        	}else if(record.data.classes==1){
                        		selectedUnitCode=record.data.code;
                        		selectedUnitId=record.data.id;
                        	}
                        	
                        	var tabPanel = Ext.getCmp("ModbusProtocolReportUnitReportTemplateTabPanel_Id");
            				var activeId = tabPanel.getActiveTab().id;
            				if(activeId=="ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id"){
            					var singleWellReportActiveId=Ext.getCmp("ModbusProtocolReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
            					if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellDailyReportTemplatePanel_Id'){
            						var ReportUnitSingleWellDailyReportTemplateListGridPanel=Ext.getCmp("ReportUnitSingleWellDailyReportTemplateListGridPanel_Id");
                                	if (isNotVal(ReportUnitSingleWellDailyReportTemplateListGridPanel)) {
                                		ReportUnitSingleWellDailyReportTemplateListGridPanel.getStore().load();
                                	}else{
                                		Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellDailyReportTemplateStore')
                                	}
//                                	CreateSingleWellDailyReportTotalItemsInfoTable(record.data.calculateType,selectedUnitId,record.data.text,record.data.classes);
                                	
            					}else if(singleWellReportActiveId=='ModbusProtocolReportUnitSingleWellRangeReportTemplatePanel_Id'){
            						var ReportUnitSingleWellRangeReportTemplateListGridPanel=Ext.getCmp("ReportUnitSingleWellRangeReportTemplateListGridPanel_Id");
                                	if (isNotVal(ReportUnitSingleWellRangeReportTemplateListGridPanel)) {
                                		ReportUnitSingleWellRangeReportTemplateListGridPanel.getStore().load();
                                	}else{
                                		Ext.create('AP.store.acquisitionUnit.ModbusProtocolSingleWellRangeReportTemplateStore')
                                	}
//                                	CreateSingleWellRangeReportTotalItemsInfoTable(record.data.calculateType,selectedUnitId,record.data.text,record.data.classes);
            					}
            				}else if(activeId=="ModbusProtocolReportUnitProductionReportTemplatePanel_Id"){
            					var ReportUnitProductionReportTemplateListGridPanel=Ext.getCmp("ReportUnitProductionReportTemplateListGridPanel_Id");
                            	if (isNotVal(ReportUnitProductionReportTemplateListGridPanel)) {
                            		ReportUnitProductionReportTemplateListGridPanel.getStore().load();
                            	}else{
                            		Ext.create('AP.store.acquisitionUnit.ModbusProtocolProductionReportTemplateStore')
                            	}
//                            	CreateproductionReportTotalItemsInfoTable(record.data.calculateType,selectedUnitId,record.data.text,record.data.classes);
            				}
                        	
                        	CreateProtocolReportUnitPropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes==0){
                        		return;
                        	}else if(record.data.classes==1){
                        		info='报表单元';
                        	}
                        	var menu = Ext.create('Ext.menu.Menu', {
                                floating: true,
                                items: [{
                                    text: '删除'+info,
                                    glyph: 0xf056,
                                    handler: function () {
                                    	if(record.data.classes==1){
                                    		var reportUnitSaveData={};
                                    		reportUnitSaveData.delidslist=[];
                                    		reportUnitSaveData.delidslist.push(record.data.id);
                                    		SaveModbusProtocolReportUnitData(reportUnitSaveData);
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
                var panel = Ext.getCmp("ModbusProtocolReportUnitConfigPanel_Id");
                panel.add(treeGridPanel);
            }
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").getValue());
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});