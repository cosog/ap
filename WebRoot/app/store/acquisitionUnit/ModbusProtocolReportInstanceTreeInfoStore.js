Ext.define('AP.store.acquisitionUnit.ModbusProtocolReportInstanceTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.modbusProtocolReportInstanceTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/modbusReportInstanceConfigTreeData',
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
        	var treeGridPanel = Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
                treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ModbusProtocolReportInstanceConfigTreeGridPanel_Id",
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
                    	text: '报表实例列表',
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
                        	Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").setValue(index);
                        	
                        	var reportType=0;
                        	var selectedUnitId='';
                        	var selectedTemplateCode='';
                        	var selectedInstanceName='';
                        	
                        	var tabPanel = Ext.getCmp("ModbusProtocolReportInstanceReportTemplateTabPanel_Id");
            				var activeId = tabPanel.getActiveTab().id;
            				if(activeId=="ModbusProtocolReportInstanceSingleWellReportTemplatePanel_Id"){
            					reportType=0;
            				}else if(activeId=="ModbusProtocolReportInstanceProductionReportTemplatePanel_Id"){
            					reportType=1;
            				}
                        	
                        	if(record.data.classes==0){//选中设备类型deviceType
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			selectedUnitId=record.data.children[0].unitId;
                        			selectedInstanceName=record.data.children[0].text;
                        			if(reportType==0){
                        				selectedTemplateCode=record.data.children[0].singleWellReportTemplate;
                        			}else{
                        				selectedTemplateCode=record.data.children[0].productionReportTemplate;
                        			}
                        		}
                        	}else if(record.data.classes==2){//选中报表单元
                        		selectedUnitId=record.data.unitId;
                        		selectedInstanceName=record.parentNode.data.text;
                        		if(reportType==0){
                    				selectedTemplateCode=record.data.singleWellReportTemplate;
                    			}else{
                    				selectedTemplateCode=record.data.productionReportTemplate;
                    			}
                        	}else{
                        		selectedUnitId=record.data.unitId;
                        		selectedInstanceName=record.data.text;
                        		if(reportType==0){
                    				selectedTemplateCode=record.data.singleWellReportTemplate;
                    			}else{
                    				selectedTemplateCode=record.data.productionReportTemplate;
                    			}
                        	}
                        	
                        	if(reportType==0){
                    			CreateReportInstanceSingleWellTemplateInfoTable(record.data.deviceType,selectedTemplateCode,selectedInstanceName);
                    			CreateSingleWellReportInstanceTotalItemsInfoTable(record.data.deviceType,selectedUnitId,selectedInstanceName);
                        	}else{
                        		CreateReportInstanceProductionTemplateInfoTable(record.data.deviceType,selectedTemplateCode,selectedInstanceName);
                        		CreateProductionReportInstanceTotalItemsInfoTable(record.data.deviceType,selectedUnitId,selectedInstanceName);
                        	}
//                        	
                        	CreateProtocolReportInstancePropertiesInfoTable(record.data);
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {//右键事件
                        	e.preventDefault();//去掉点击右键是浏览器的菜单
                        	var info='节点';
                        	if(record.data.classes!=1){
                        		return;
                        	}else{
                        		info='报表实例';
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
                                            			SaveModbusProtocolReportInstanceData(configInfo);
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
                var panel = Ext.getCmp("ModbusProtocolReportInstanceConfigPanel_Id");
                panel.add(treeGridPanel);
            }
            var selectedRow=parseInt(Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").getValue());
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});