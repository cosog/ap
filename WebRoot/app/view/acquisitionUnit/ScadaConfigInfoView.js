Ext.define("AP.view.acquisitionUnit.ScadaConfigInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.scadaConfigInfoView', //别名
    layout: 'fit',
    iframe: true,
    border: false,
    referenceHolder: true,
    initComponent: function () {
    	var DataSourceConfigInfoView = Ext.create('AP.view.acquisitionUnit.DataSourceConfigInfoView');
        var ProtocolConfigInfoView = Ext.create('AP.view.acquisitionUnit.ProtocolConfigInfoView');

        var DataSourceConfigItems=[{
            title: '采控直读',
            id:'DriverConfigInfoPanel_Id',
            layout: "fit",
            border: false,
            items:ProtocolConfigInfoView
        },{
            title: '数据库直读',
            id:'DataSourceConfigInfoPanel_Id',
            layout: "fit",
            border: false,
            items: DataSourceConfigInfoView
        }];
        
        if(dataSourceSN==0){
        	DataSourceConfigItems=[{
                title: '采控直读',
                id:'DriverConfigInfoPanel_Id',
                layout: "fit",
                border: false,
                items:ProtocolConfigInfoView
            },{
                title: '数据库直读',
                id:'DataSourceConfigInfoPanel_Id',
                layout: "fit",
                border: false,
                items: DataSourceConfigInfoView
            }];
        }else if(dataSourceSN==1){
        	DataSourceConfigItems=[{
                title: '采控直读',
                id:'DriverConfigInfoPanel_Id',
                layout: "fit",
                border: false,
                items:ProtocolConfigInfoView
            }];
        }else if(dataSourceSN==2){
        	DataSourceConfigItems=[{
                title: '数据库直读',
                id:'DataSourceConfigInfoPanel_Id',
                layout: "fit",
                border: false,
                items: DataSourceConfigInfoView
            }];
        }
        Ext.apply(this, {
            items: [{
                xtype: 'tabpanel',
                id:"ScaDataSourceConfigInfoViewdaConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: DataSourceConfigItems,
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	
                    }
                }
             }]
        });
        this.callParent(arguments);
    }
});