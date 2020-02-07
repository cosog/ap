Ext.define('AP.view.graphicalUpload.GraphicalUploadInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.graphicalUploadInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
    	var SurfaceCardUploadPanel = Ext.create('AP.view.graphicalUpload.SurfaceCardUploadPanel');
        Ext.apply(this, {
            items: [{
                xtype: 'tabpanel',
                tabPosition: 'bottom',
                items: [
                    {
                        id: 'graphicalUploadPanelId',
                        title: '地面功图上传',
                        layout: 'fit',
                        border: false,
                        items:SurfaceCardUploadPanel
                    }]
            }]
        });
        this.callParent(arguments);
    }
});