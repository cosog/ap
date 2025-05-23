
Ext.define("AP.view.acquisitionUnit.ExportProtocolWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.ExportProtocolWindow',
    id: 'ExportProtocolWindow_Id',
    layout: 'fit',
    title:loginUserLanguageResource.exportProtocol,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 450,
    minWidth: 450,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.create('AP.store.acquisitionUnit.ExportProtocolTreeInfoStore');
        Ext.apply(me, {
            layout: "border",
            tbar:[{
                xtype: 'radiogroup',
                id: 'ExportProtocolWindowDataType_Id',
                cls: 'x-check-group-alt',
                name: 'type',
                hidden: !exportAdInitData,
                items: [
                    {boxLabel: loginUserLanguageResource.exportProtocolFormat1,width: getLabelWidth(loginUserLanguageResource.exportProtocolFormat1,loginUserLanguage)+20, inputValue: 1, checked: true},
                    {boxLabel: loginUserLanguageResource.exportProtocolFormat2,width: getLabelWidth(loginUserLanguageResource.exportProtocolFormat2,loginUserLanguage)+20, inputValue: 2}
                ],
                listeners: {
               	 	change: function (radiogroup, newValue, oldValue, eOpts) {
               	 		
               	 	}
                }
            },'->',{
            	xtype: 'button',
            	id:'ExportProtocolWindowExportBtn_Id',
    			text: loginUserLanguageResource.exportData,
    			iconCls: 'export',
    			handler: function (v, o) {
    				var type = Ext.getCmp("ExportProtocolWindowDataType_Id").getValue().type;
    				
    				var exportProtocolList = [];
    				var exportProtocolTreeGridPanelSelection=Ext.getCmp("ExportProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
    				for(var i=0;i<exportProtocolTreeGridPanelSelection.length;i++){
    					
    				}
    				var treeGridPanel = Ext.getCmp("ExportProtocolTreeGridPanel_Id");
    				var selectedRecord = treeGridPanel.getChecked();
    				if(selectedRecord.length>0){
    					Ext.Array.each(selectedRecord, function (name, index, countriesItSelf) {
        			        var protocolCode = selectedRecord[index].get('code');
        			        exportProtocolList.push(protocolCode);
        			    });
    					
    					var timestamp=new Date().getTime();
    					var key='exportProtocolData'+'_'+timestamp;
    					var maskPanelId='ExportProtocolWindow_Id';
    					
    	        		var url=context + '/acquisitionUnitManagerController/exportProtocolData?key='+key+'&protocolList='+exportProtocolList.join(",");
    	        		if(type==2){
    	        			key='exportProtocolInitData'+'_'+timestamp;
    	        			url=context + '/acquisitionUnitManagerController/exportProtocolInitData?key='+key+'&protocolList='+exportProtocolList.join(",");
    	        		}
    	        		exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    	        	    openExcelWindow(url);
//                    	document.location.href = url;
    				}else{
    					Ext.MessageBox.alert(loginUserLanguageResource.message,"请选择要导出的协议");
    				}
    			}
            }],
            items: [{
            	region: 'center',
//            	width:'25%',
            	title:loginUserLanguageResource.protocolList,
            	layout: 'fit',
            	split: true,
                collapsible: false,
            	id:"ExportProtocolTreePanel_Id"
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

