Ext.define("AP.view.realTimeMonitoring.ItemRealtimeDataWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.itemRealtimeDataWindow',
    layout: 'fit',
    title: loginUserLanguageResource.data,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 450,
    minWidth: 450,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar:[{
                id: 'RealtimeDataItemCode_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            }],
            layout: 'fit',
        	html: '<div id="ItemRealtimeDataDiv_Id" style="width:100%;height:100%;"></div>',
            listeners: {
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                	
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});