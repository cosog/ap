Ext.define("AP.view.acquisitionUnit.ExportProtocolDisplayInstanceWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.ExportProtocolDisplayInstanceWindow',
    id: 'ExportProtocolDisplayInstanceWindow_Id',
    layout: 'fit',
    title:'显示实例导出',
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
        Ext.create('AP.store.acquisitionUnit.ExportProtocolDisplayInstanceTreeInfoStore');
        Ext.apply(me, {
            layout: "border",
            tbar:['->',{
            	xtype: 'button',
            	id:'ExportProtocolDisplayInstanceWindowExportBtn_Id',
    			text: loginUserLanguageResource.exportData,
    			iconCls: 'export',
    			handler: function (v, o) {
    				var exportDisplayInstanceList = [];
    				
    				var treeGridPanel = Ext.getCmp("ExportProtocolDisplayInstanceTreeGridPanel_Id");
    				var selectedRecord = treeGridPanel.getChecked();
    				if(selectedRecord.length>0){
    					Ext.Array.each(selectedRecord, function (name, index, countriesItSelf) {
        			        var unidId = selectedRecord[index].get('id');
        			        exportDisplayInstanceList.push(unidId);
        			    });
    					
    					var timestamp=new Date().getTime();
    					var key='exportProtocolDisplayInstanceData'+'_'+timestamp;
    					var maskPanelId='ExportProtocolDisplayInstanceWindow_Id';
    					
    	        		var url=context + '/acquisitionUnitManagerController/exportProtocolDisplayInstanceData?key='+key+'&instanceList='+exportDisplayInstanceList.join(",");
    	        		
    	        		exportDataMask(key,maskPanelId,cosog.string.loading);
    	        	    openExcelWindow(url);
    				}else{
    					Ext.MessageBox.alert("信息","请选择要导出的显示实例");
    				}
    			}
            }],
            items: [{
            	region: 'center',
//            	width:'25%',
            	title:'实例列表',
            	layout: 'fit',
            	split: true,
                collapsible: false,
            	id:"ProtocolExportDisplayInstancePanel_Id"
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

