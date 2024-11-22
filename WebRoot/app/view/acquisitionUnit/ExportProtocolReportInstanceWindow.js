Ext.define("AP.view.acquisitionUnit.ExportProtocolReportInstanceWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.ExportProtocolReportInstanceWindow',
    id: 'ExportProtocolReportInstanceWindow_Id',
    layout: 'fit',
    title:'报警单元导出',
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
        Ext.create('AP.store.acquisitionUnit.ExportProtocolReportInstanceTreeInfoStore');
        Ext.apply(me, {
            layout: "border",
            tbar:['->',{
            	xtype: 'button',
            	id:'ExportProtocolReportInstanceWindowExportBtn_Id',
    			text: loginUserLanguageResource.exportData,
    			iconCls: 'export',
    			handler: function (v, o) {
    				var exportReportInstanceList = [];
    				
    				var treeGridPanel = Ext.getCmp("ExportProtocolReportInstanceTreeGridPanel_Id");
    				var selectedRecord = treeGridPanel.getChecked();
    				if(selectedRecord.length>0){
    					Ext.Array.each(selectedRecord, function (name, index, countriesItSelf) {
        			        var unidId = selectedRecord[index].get('id');
        			        exportReportInstanceList.push(unidId);
        			    });
    					
    					var timestamp=new Date().getTime();
    					var key='exportProtocolReportInstanceData'+'_'+timestamp;
    					var maskPanelId='ExportProtocolReportInstanceWindow_Id';
    					
    	        		var url=context + '/acquisitionUnitManagerController/exportProtocolReportInstanceData?key='+key+'&instanceList='+exportReportInstanceList.join(",");
    	        		
    	        		exportDataMask(key,maskPanelId,cosog.string.loading);
    	        	    openExcelWindow(url);
    				}else{
    					Ext.MessageBox.alert("信息","请选择要导出的报警单元");
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
            	id:"ProtocolExportReportInstancePanel_Id"
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

