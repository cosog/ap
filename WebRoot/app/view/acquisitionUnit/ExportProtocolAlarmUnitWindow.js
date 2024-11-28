Ext.define("AP.view.acquisitionUnit.ExportProtocolAlarmUnitWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.ExportProtocolAlarmUnitWindow',
    id: 'ExportProtocolAlarmUnitWindow_Id',
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
        Ext.create('AP.store.acquisitionUnit.ExportProtocolAlarmUnitTreeInfoStore');
        Ext.apply(me, {
            layout: "border",
            tbar:['->',{
            	xtype: 'button',
            	id:'ExportProtocolAlarmUnitWindowExportBtn_Id',
    			text: loginUserLanguageResource.exportData,
    			iconCls: 'export',
    			handler: function (v, o) {
    				var exportAlarmUnitList = [];
    				
    				var treeGridPanel = Ext.getCmp("ExportProtocolAlarmUnitTreeGridPanel_Id");
    				var selectedRecord = treeGridPanel.getChecked();
    				if(selectedRecord.length>0){
    					Ext.Array.each(selectedRecord, function (name, index, countriesItSelf) {
        			        var unidId = selectedRecord[index].get('id');
        			        exportAlarmUnitList.push(unidId);
        			    });
    					
    					var timestamp=new Date().getTime();
    					var key='exportProtocolAlarmUnitData'+'_'+timestamp;
    					var maskPanelId='ExportProtocolAlarmUnitWindow_Id';
    					
    	        		var url=context + '/acquisitionUnitManagerController/exportProtocolAlarmUnitData?key='+key+'&unitList='+exportAlarmUnitList.join(",");
    	        		
    	        		exportDataMask(key,maskPanelId,cosog.string.loading);
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
            	id:"ProtocolExportAlarmUnitPanel_Id"
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

