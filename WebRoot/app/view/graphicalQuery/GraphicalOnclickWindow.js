Ext.define("AP.view.graphicalQuery.GraphicalOnclickWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.GraphicalOnclickWindow',
    layout: 'fit',
    border: false,
    hidden: false,
    collapsible: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 450,
    minWidth: 300,
    height: 350,
    draggable: true, // 是否可拖曳
    modal: false, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            listeners: {
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    var GraphicalOnclickWindowStore = Ext.create("AP.store.graphicalQuery.GraphicalOnclickWindowStore");
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});