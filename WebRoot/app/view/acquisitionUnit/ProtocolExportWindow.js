
Ext.define("AP.view.acquisitionUnit.ProtocolExportWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.protocolExportWindow',
    layout: 'fit',
    title:'协议导出',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1500,
    minWidth: 1500,
    height: 700,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.create('AP.store.acquisitionUnit.ExportProtocolTreeInfoStore');
        Ext.apply(me, {
            layout: "border",
            tbar:['->',{
            	xtype: 'button',
            	id:'ExportProtocolWindowExportBtn_Id',
    			text: '导出',
    			iconCls: 'export',
    			handler: function (v, o) {
    				var deviceType=0;
    	        	var protocolName="";
    	        	var protocolCode="";
    	        	var exportProtocolTreeGridPanelSelection=Ext.getCmp("ExportProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
    	        	if(exportProtocolTreeGridPanelSelection.length>0 && exportProtocolTreeGridPanelSelection[0].data.classes==1){
    	        		deviceType=exportProtocolTreeGridPanelSelection[0].data.deviceType;
    	        		protocolName=exportProtocolTreeGridPanelSelection[0].data.text;
    	        		protocolCode=exportProtocolTreeGridPanelSelection[0].data.code;
    	        		
    	        		//导出的采集单元和采集组
    	        		var acqUnit=[];
    	        		var acqGroup=[];
    	        		var exportProtocolAcqUnitTreeGridPanel=Ext.getCmp("ExportProtocolAcqUnitTreeGridPanel_Id").getSelectionModel().getSelection();
    	        		if(exportProtocolAcqUnitTreeGridPanel.length>0){
    	        			for(var i=0;i<exportProtocolAcqUnitTreeGridPanel.length;i++){
    	        				if(exportProtocolAcqUnitTreeGridPanel[i].data.classes==2){
    	        					acqUnit.push(exportProtocolAcqUnitTreeGridPanel[i].data.id);
    	        				}else if(exportProtocolAcqUnitTreeGridPanel[i].data.classes==3){
    	        					acqGroup.push(exportProtocolAcqUnitTreeGridPanel[i].data.id);
    	        				}
    	        			}
    	        		}
    	        		
    	        		//导出的显示单元
    	        		var displayUnit=[];
    	        		var exportProtocolDisplayUnitTreeGridPanel=Ext.getCmp("ExportProtocolDisplayUnitTreeGridPanel_Id").getSelectionModel().getSelection();
    	        		if(exportProtocolDisplayUnitTreeGridPanel.length>0){
    	        			for(var i=0;i<exportProtocolDisplayUnitTreeGridPanel.length;i++){
    	        				if(exportProtocolDisplayUnitTreeGridPanel[i].data.classes==2){
    	        					displayUnit.push(exportProtocolDisplayUnitTreeGridPanel[i].data.id);
    	        				}
    	        			}
    	        		}
    	        		
    	        		//导出的报警单元
    	        		var alarmUnit=[];
    	        		var exportProtocolAlarmUnitTreeGridPanel=Ext.getCmp("ExportProtocolAlarmUnitTreeGridPanel_Id").getSelectionModel().getSelection();
    	        		if(exportProtocolAlarmUnitTreeGridPanel.length>0){
    	        			for(var i=0;i<exportProtocolAlarmUnitTreeGridPanel.length;i++){
    	        				if(exportProtocolAlarmUnitTreeGridPanel[i].data.classes==3){
    	        					alarmUnit.push(exportProtocolAlarmUnitTreeGridPanel[i].data.id);
    	        				}
    	        			}
    	        		}
    	        		
    	        		//导出的采控实例
    	        		var acqInstance=[];
    	        		var exportProtocolAcqInstanceTreeGridPanel=Ext.getCmp("ExportProtocolAcqInstanceTreeGridPanel_Id").getSelectionModel().getSelection();
    	        		if(exportProtocolAcqInstanceTreeGridPanel.length>0){
    	        			for(var i=0;i<exportProtocolAcqInstanceTreeGridPanel.length;i++){
    	        				if(exportProtocolAcqInstanceTreeGridPanel[i].data.classes==1){
    	        					acqInstance.push(exportProtocolAcqInstanceTreeGridPanel[i].data.id);
    	        				}
    	        			}
    	        		}
    	        		
    	        		//导出的显示实例
    	        		var displayInstance=[];
    	        		var exportProtocolDisplayInstanceTreeGridPanel=Ext.getCmp("ExportProtocolDisplayInstanceTreeGridPanel_Id").getSelectionModel().getSelection();
    	        		if(exportProtocolDisplayInstanceTreeGridPanel.length>0){
    	        			for(var i=0;i<exportProtocolDisplayInstanceTreeGridPanel.length;i++){
    	        				if(exportProtocolDisplayInstanceTreeGridPanel[i].data.classes==1){
    	        					displayInstance.push(exportProtocolDisplayInstanceTreeGridPanel[i].data.id);
    	        				}
    	        			}
    	        		}
    	        		
    	        		//导出的报警实例
    	        		var alarmInstance=[];
    	        		var exportProtocolAlarmInstanceTreeGridPanel=Ext.getCmp("ExportProtocolAlarmInstanceTreeGridPanel_Id").getSelectionModel().getSelection();
    	        		if(exportProtocolAlarmInstanceTreeGridPanel.length>0){
    	        			for(var i=0;i<exportProtocolAlarmInstanceTreeGridPanel.length;i++){
    	        				if(exportProtocolAlarmInstanceTreeGridPanel[i].data.classes==1){
    	        					alarmInstance.push(exportProtocolAlarmInstanceTreeGridPanel[i].data.id);
    	        				}
    	        			}
    	        		}
    	        		
    	        		var url=context + '/acquisitionUnitManagerController/exportProtocolConfigData?deviceType='+deviceType
                		+'&protocolName='+URLencode(URLencode(protocolName))
                		+'&protocolCode='+protocolCode
                		+'&acqUnit='+acqUnit.join(",")
                		+'&acqGroup='+acqGroup.join(",")
                		+'&displayUnit='+displayUnit.join(",")
                		+'&alarmUnit='+alarmUnit.join(",")
                		+'&acqInstance='+acqInstance.join(",")
                		+'&displayInstance='+displayInstance.join(",")
                		+'&alarmInstance='+alarmInstance.join(",");
                    	document.location.href = url;
    	        	}else{
    	        		Ext.MessageBox.alert("信息","请选择要导出的协议");
    	        	}
    			}
            }],
            items: [{
            	region: 'west',
            	width:'25%',
            	title:'协议列表',
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"ExportPootocolTreePanel_Id"
            },{
            	region: 'center',
            	layout: "border",
            	border: false,
            	items: [{
            		region: 'center',
                	title:'相关单元',
                	layout: "border",
                	split: true,
                    items: [{
                		region: 'west',
                		width:'33%',
                    	layout: 'fit',
                    	title:'采控单元',
                    	id:"ProtocolExportAcqUnitPanel_Id"
                	},{
                		region: 'center',
                		layout: 'fit',
                		title:'显示单元',
                    	id:"ProtocolExportDisplayUnitPanel_Id"
                	},{
                		region: 'east',
                		width:'33%',
                		layout: 'fit',
                		title:'报警单元',
                    	id:"ProtocolExportAlarmUnitPanel_Id"
                	}]
            	},{
            		region: 'south',
                	height:'50%',
                	title:'相关实例',
                	layout: "border",
                	split: true,
                    collapsible: true,
                    items: [{
                		region: 'west',
                		width:'33%',
                    	layout: 'fit',
                    	title:'采控实例',
                    	id:"ProtocolExportAcqInstancePanel_Id"
                	},{
                		region: 'center',
                		layout: 'fit',
                		title:'显示实例',
                    	id:"ProtocolExportDisplayInstancePanel_Id"
                	},{
                		region: 'east',
                		width:'33%',
                		layout: 'fit',
                		title:'报警实例',
                    	id:"ProtocolExportAlarmInstancePanel_Id"
                	}]
            	}]
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

