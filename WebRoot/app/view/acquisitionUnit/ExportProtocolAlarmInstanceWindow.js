Ext.define("AP.view.acquisitionUnit.ExportProtocolAlarmInstanceWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.ExportProtocolAlarmInstanceWindow',
    id: 'ExportProtocolAlarmInstanceWindow_Id',
    layout: 'fit',
    title:loginUserLanguageResource.exportData,
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
        Ext.create('AP.store.acquisitionUnit.ExportProtocolAlarmInstanceTreeInfoStore');
        Ext.apply(me, {
            layout: "border",
            tbar:['->',{
            	xtype: 'button',
            	id:'ExportProtocolAlarmInstanceWindowExportBtn_Id',
    			text: loginUserLanguageResource.exportData,
    			iconCls: 'export',
    			handler: function (v, o) {
    				var exportAlarmInstanceList = [];
    				
    				var treeGridPanel = Ext.getCmp("ExportProtocolAlarmInstanceTreeGridPanel_Id");
    				var selectedRecord = treeGridPanel.getChecked();
    				if(selectedRecord.length>0){
    					Ext.Array.each(selectedRecord, function (name, index, countriesItSelf) {
        			        var unidId = selectedRecord[index].get('id');
        			        exportAlarmInstanceList.push(unidId);
        			    });
    					
    					var timestamp=new Date().getTime();
    					var key='exportProtocolAlarmInstanceData'+'_'+timestamp;
    					var maskPanelId='ExportProtocolAlarmInstanceWindow_Id';
    					
    	        		var url=context + '/acquisitionUnitManagerController/exportProtocolAlarmInstanceData?key='+key+'&instanceList='+exportAlarmInstanceList.join(",");
    	        		
    	        		exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    	        	    openExcelWindow(url);
    				}else{
    					Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.checkOne);
    				}
    			}
            }],
            items: [{
            	region: 'center',
//            	width:'25%',
            	title:loginUserLanguageResource.unitList,
            	layout: 'fit',
            	split: true,
                collapsible: false,
            	id:"ProtocolExportAlarmInstancePanel_Id"
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

