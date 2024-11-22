Ext.define("AP.view.acquisitionUnit.ExportProtocolReportUnitWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.ExportProtocolReportUnitWindow',
    id: 'ExportProtocolReportUnitWindow_Id',
    layout: 'fit',
    title:'报表单元导出',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 400,
    minWidth: 400,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.create('AP.store.acquisitionUnit.ExportProtocolReportUnitTreeInfoStore');
        Ext.apply(me, {
            layout: "border",
            tbar:['->',{
            	xtype: 'button',
            	id:'ExportProtocolReportUnitWindowExportBtn_Id',
    			text: loginUserLanguageResource.exportData,
    			iconCls: 'export',
    			handler: function (v, o) {
    				var exportReportUnitList = [];
    				
    				var treeGridPanel = Ext.getCmp("ExportProtocolReportUnitTreeGridPanel_Id");
    				var selectedRecord = treeGridPanel.getChecked();
    				if(selectedRecord.length>0){
    					Ext.Array.each(selectedRecord, function (name, index, countriesItSelf) {
        			        var unidId = selectedRecord[index].get('id');
        			        exportReportUnitList.push(unidId);
        			    });
    					
    					var timestamp=new Date().getTime();
    					var key='exportProtocolReportUnitData'+'_'+timestamp;
    					var maskPanelId='ExportProtocolReportUnitWindow_Id';
    					
    	        		var url=context + '/acquisitionUnitManagerController/exportProtocolReportUnitData?key='+key+'&unitList='+exportReportUnitList.join(",");
    	        		
    	        		exportDataMask(key,maskPanelId,cosog.string.loading);
    	        	    openExcelWindow(url);
    				}else{
    					Ext.MessageBox.alert("信息","请选择要导出的报表单元");
    				}
    			}
            }],
            items: [{
            	region: 'center',
//            	width:'25%',
            	title:'单元列表',
            	layout: 'fit',
            	split: true,
                collapsible: false,
            	id:"ProtocolExportReportUnitPanel_Id"
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

