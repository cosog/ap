Ext.define('AP.view.org.OrgCoordSetWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.orgCoordSetWindow',
    layout: 'fit',
    iframe: true,
    id: 'orgCoordSetWindow_Id',
    closeAction: 'destroy',
    width: 1350,
    height:700,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    constrain: true,
    plain: true,
    modal: true,
    border: false,
    buttonAlign:'center',
    buttons: [
              { 
            	  text: cosog.string.save,
            	  iconCls:'save',
            	  handler: function () {
                      Ext.getCmp("orgCoordSetWindow_Id").close();
                  }
              },{ 
            	  text: cosog.string.cancel,
            	  iconCls:'cancel',
            	  handler: function () {
                      Ext.getCmp("orgCoordSetWindow_Id").close();
                  }
              }
            ],
    initComponent: function () {
        var me = this;
        var panel=Ext.create('Ext.panel.Panel', {
        	id: 'backOrgMapTab_Id',
        	layout: "fit",
            border: false,
        	width:'100%',
        	height:'100%',
            html:'<div id="orgCoordSetDiv_Id" style="width:100%;height:100%;"></div>'
        });
        Ext.apply(me, {
            items: panel
        })
        me.callParent(arguments);
    }
})
