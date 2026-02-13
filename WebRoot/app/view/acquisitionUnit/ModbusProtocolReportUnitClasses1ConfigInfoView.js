Ext.define("AP.view.acquisitionUnit.ModbusProtocolReportUnitClasses1ConfigInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolReportUnitClasses1ConfigInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	items:[{
        		layout: "border",
        		items:[{
                    region: 'center',
                    title: '报表模板',
                    id: 'ReportUnitClasses1TemplateTableInfoPanel_Id',
                    layout: 'fit',
                    border: false,
                    html: '<div class="ReportUnitClasses1TemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitClasses1TemplateTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	
                        }
                    }
            	}, {
                    region: 'south',
                    height: '50%',
                    title: '报表内容配置',
                    collapsible: true,
                    split: true,
                    layout: 'fit',
                    border: false,
                    id: "ReportUnitClasses1ContentConfigTableInfoPanel_Id",
                    layout: 'fit',
                    html: '<div class="ReportUnitClasses1ContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportUnitClasses1ContentConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	
                        }
                    }
            	}]
        	}],
        	listeners: {
    			beforeclose: function ( panel, eOpts) {
    				
    			},
    			afterrender: function ( panel, eOpts) {
    				
    			}
    		}
        });
        me.callParent(arguments);
    }
});