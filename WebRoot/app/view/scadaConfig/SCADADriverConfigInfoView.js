Ext.define('AP.view.scadaConfig.SCADADriverConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.SCADADriverConfigInfoView',
    layout: "fit",
    id:'SCADADriverConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
                layout: "border",
                border: false,
                id:'a9RawDataInfoPanelId',
                title: '驱动列表',
//        		tbar: [],
                items: [{
                    region: 'center',
                    border: false,
                    layout: 'fit',
                    collapsible: false, // 是否折叠
                    split: true // 竖折叠条
                }, {
                    region: 'east',
                    width: '65%',
                    layout: 'fit',
                    border: false,
                    header:false,
                    collapsible: true,
                    split: true,
                    autoScroll:true,
                    scrollable: true
                }]
    		}],
    		listeners: {
    			beforeclose: function ( panel, eOpts) {},
    			afterrender: function ( panel, eOpts) {}
    		}
        });
        this.callParent(arguments);

    }
});