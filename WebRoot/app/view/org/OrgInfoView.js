Ext.define("AP.view.org.OrgInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.orgInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var OrgInfoWithInternetView=null;
        if(isShowMap&&isConnectionInternet){
        	OrgInfoWithInternetView = Ext.create('AP.view.org.OrgInfoWithInternetView');
        }else{
        	OrgInfoWithInternetView = Ext.create('AP.view.org.OrgInfoWithoutInternetView');
        }
        Ext.apply(me, {
            items: [OrgInfoWithInternetView]
        });
        me.callParent(arguments);
    }

});