Ext.define("AP.view.org.OrgInfoWithoutInternetView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.orgInfoWithoutInternetView',
    layout: 'fit',
//    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;                   //地图工具库返回来的操作对象
        var OrgInfoTreeGridView = Ext.create('AP.view.org.OrgInfoTreeGridView');
        Ext.apply(me, {
            //items: [OrgInfoTreeGridView]
        	items:[{
        		id:'orgMapPanel_Id',
                border : false,
                layout:'border',
                items: [
                {
                    layout: 'fit',
                    region: 'center',
                    width: '100%',
                    collapsible: false,
                    split: true,
                    border: false,
                    items: [OrgInfoTreeGridView]
                }]
        	}]
        });
        me.callParent(arguments);
    }
});

function aa(a,b){
	alert("a="+a+",b="+b);
}