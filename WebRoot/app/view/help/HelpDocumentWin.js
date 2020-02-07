Ext.define('AP.view.help.HelpDocumentWin', {
    extend: 'Ext.Window',
    alias: 'widget.helpDocumentWin',
    id: "HelpDocumentWinId",
    width: 360,
    height:360,	
//    x:1400,
    layout: 'fit',
    closeAction: 'destroy',
    constrain: true,
    resizable: false,
    border:false,
    modal: true,
    html:'<div align="center" style="width:100%;height:100%;padding:20px;"><img src="../images/help2vm.png" width="260" height="260" /><br/>扫一扫，手机查看<br/><a href="https://github.com/AgileProduction/Information/blob/master/README.md?timestamp=201810181211" target="view_window">文档地址</a></div>',
//    title: '帮助',
    bodyStyle:{
    	background:'#ffffff'
    },
    initComponent: function () {
        var me = this;
        Ext.apply(this, {
        	listeners: {
        		blur:function (component, eventObject,eOpts) {
        			component.close();
        		}
        	}
        });
        me.callParent(arguments);
    }
});