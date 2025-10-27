Ext.define("AP.view.acquisitionUnit.ExportProtocolAcqInstanceWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.ExportProtocolAcqInstanceWindow',
    id: 'ExportProtocolAcqInstanceWindow_Id',
    layout: 'fit',
    title: loginUserLanguageResource.exportAcqInstance,
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
        Ext.create('AP.store.acquisitionUnit.ExportProtocolAcqInstanceTreeInfoStore');
        Ext.apply(me, {
            layout: "border",
            tbar:[{
                xtype: 'radiogroup',
              id: 'ExportProtocolAcqInstanceWindowDataType_Id',
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
            	id:'ExportProtocolAcqInstanceWindowExportBtn_Id',
    			text: loginUserLanguageResource.exportData,
    			iconCls: 'export',
    			handler: function (v, o) {
    				var exportAcqInstanceList = [];
    				var type = Ext.getCmp("ExportProtocolAcqInstanceWindowDataType_Id").getValue().type;
    				var treeGridPanel = Ext.getCmp("ExportProtocolAcqInstanceTreeGridPanel_Id");
    				var selectedRecord = treeGridPanel.getChecked();
    				if(selectedRecord.length>0){
    					Ext.Array.each(selectedRecord, function (name, index, countriesItSelf) {
        			        var unidId = selectedRecord[index].get('id');
        			        exportAcqInstanceList.push(unidId);
        			    });
    					
    					var timestamp=new Date().getTime();
    					var key='exportProtocolAcqInstanceData'+'_'+timestamp;
    					var maskPanelId='ExportProtocolAcqInstanceWindow_Id';
    					
    	        		var url=context + '/acquisitionUnitManagerController/exportProtocolAcqInstanceData?key='+key+'&instanceList='+exportAcqInstanceList.join(",");
    	        		
    	        		if(type==2){
    	        			key='exportProtocolAcqInstanceInitData'+'_'+timestamp;
    	        			url=context + '/acquisitionUnitManagerController/exportProtocolAcqInstanceInitData?key='+key+'&instanceList='+exportAcqInstanceList.join(",");
    	        		}
    	        		
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
            	title:loginUserLanguageResource.instanceList,
            	layout: 'fit',
            	split: true,
                collapsible: false,
            	id:"ProtocolExportAcqInstancePanel_Id"
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

