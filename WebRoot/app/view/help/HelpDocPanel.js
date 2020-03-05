Ext.define('AP.view.help.HelpDocPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.helpDocPanel',
    layout: "fit",
    border: false,
    initComponent: function () {
    	Ext.apply(this, {
    		items:{
    			id:'HelpDocPanel_Id',
    			autoScroll: true,
    			html:''
    		}
    	})
    	this.callParent(arguments);
    }
});