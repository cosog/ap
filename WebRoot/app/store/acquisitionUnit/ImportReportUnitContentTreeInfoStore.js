Ext.define('AP.store.acquisitionUnit.ImportReportUnitContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importReportUnitContentTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getUploadedReportUnitTreeData',
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
        	var deviceType=Ext.getCmp("ImportReportUnitWinDeviceType_Id").getValue();
        	var new_params = {
        			deviceType: deviceType
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
        	var treeGridPanel = Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ImportReportUnitContentTreeGridPanel_Id",
//                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    selModel: 'cellmodel',//cellmodel rowmodel
                    plugins: [{
                        ptype: 'cellediting',//cellediting rowediting
                        clicksToEdit: 2
                    }],
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '预导入单元',
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text',
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    },{
                    	header: '冲突信息',
                    	flex: 12,
                    	dataIndex: 'msg',
                    	renderer:function(value,o,p,e){
                    		return adviceImportReportUnitCollisionInfoColor(value,o,p,e);
                    	}
                    },{
                        header: 'id',
                        hidden: true,
                        dataIndex: 'id'
                    },{
                		text: loginUserLanguageResource.save, 
                		dataIndex: 'action',
//                		locked:true,
                		align:'center',
                		width:50,
                		renderer :function(value,e,o){
                			return iconImportSingleReportUnitAction(value,e,o)
                		} 
                    }],
                    listeners: {
                        select( v, record, index, eOpts ){
                        	var unitName='';
                        	var reportType=0;
                        	if(record.data.classes==0){
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			unitName=record.data.children[0].text;
                        		}
                        	}else if(record.data.classes==1){
                        		unitName=record.data.text;
                        	}
                        	
                        	var tabPanel = Ext.getCmp("importReportUnitReportTemplateTabPanel_Id");
            				var activeId = tabPanel.getActiveTab().id;
            				if(activeId=="importReportUnitSingleWellReportTemplatePanel_Id"){
            					var singleWellReportActiveId=Ext.getCmp("importReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
            					if(singleWellReportActiveId=='importReportUnitSingleWellDailyReportTemplatePanel_Id'){
            						reportType=2;
            					}else if(singleWellReportActiveId=='importReportUnitSingleWellRangeReportTemplatePanel_Id'){
            						reportType=0;
            					}
            				}else if(activeId=="importReportUnitProductionReportTemplatePanel_Id"){
            					reportType=1;
            				}
                        	
            				if(reportType==0){
                    			CreateImportReportUnitSingleWellRangeReportTemplateInfoTable(unitName);
                    			CreateImportReportUnitSingleWellRangeTotalItemsInfoTable(unitName);
                        	}else if(reportType==2){
                    			CreateImportReportUnitSingleWellDailyReportTemplateInfoTable(unitName);
                    			CreateImportReportUnitSingleWellDailyTotalItemsInfoTable(unitName);
                        	}else{
                        		CreateImportReportUnitProductionReportTemplateInfoTable(unitName);
                        		CreateImportReportUnitProductionTotalItemsInfoTable(unitName);
                        	}
                        }
                    }

                });
                var panel = Ext.getCmp("importReportUnitTreePanel_Id");
                panel.add(treeGridPanel);
            }
            
            treeGridPanel.getSelectionModel().deselectAll(true);
            var selectedRow=0;
            if(store.data.length>1){
            	selectedRow=1;
            	for(var i=0;i<store.data.length;i++){
            		if(store.getAt(i).data.classes==1){
            			selectedRow=i;
            			break;
            		}
            	}
            }
            treeGridPanel.getSelectionModel().select(selectedRow, true);
        }
    }
});