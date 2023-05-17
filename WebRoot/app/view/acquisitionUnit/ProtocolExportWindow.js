
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
    			text: '导出',
    			iconCls: 'export',
    			handler: function (v, o) {
    				
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

