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
                        	Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").setValue(index);
                        	var selectedUnitCode='';
                        	var selectedUnitName='';
                        	if(record.data.classes==0){//选中设备类型deviceType
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			selectedUnitCode=record.data.children[0].unitCode;
                        			selectedUnitName=record.data.children[0].unitName;
                        			CreateReportInstanceTemplateInfoTable(record.data.children[0].unitName,record.data.children[0].classes,record.data.children[0].unitCode);
                        		}else{
                        			Ext.getCmp("ModbusProtocolReportUnitTemplateTableInfoPanel_Id").setTitle('报表模板');
                        			if(reportInstanceTemplateHandsontableHelper!=null && reportInstanceTemplateHandsontableHelper.hot!=undefined){
                        				reportInstanceTemplateHandsontableHelper.hot.loadData([]);
                        			}
                        		}
                        	}else if(record.data.classes==2){//选中报表单元
                        		selectedUnitCode=record.data.code;
                        		selectedUnitName=record.data.name;
                        		CreateReportInstanceTemplateInfoTable(record.data.text,record.data.classes,record.data.code);
                        	}else{
                        		selectedUnitCode=record.data.unitCode;
                        		selectedUnitName=record.data.unitName;
                        		CreateReportInstanceTemplateInfoTable(record.data.unitName,record.data.classes,record.data.unitCode);
                        	}
//                        	
                        	CreateProtocolReportInstancePropertiesInfoTable(record.data);
                        	CreateReportInstanceTotalItemsInfoTable(record.data.deviceType,selectedUnitCode,selectedUnitName,record.data.classes);
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
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(0, true);
        }
    }
});