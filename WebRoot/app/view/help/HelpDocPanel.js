Ext.define('AP.view.help.HelpDocPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.helpDocPanel',
    layout: "fit",
    border: false,
    initComponent: function () {
    	var url=helpDocumentUrl+'?timestamp='+helpDocumentTimestamp;
    	Ext.apply(this, {
    		items:{
    			id:'HelpDocPanel_Id',
    			autoScroll: false,
    			html:"<iframe src='"+url+"' width=100% height=100%></iframe>"
    		}
    	})
    	this.callParent(arguments);
    }
});