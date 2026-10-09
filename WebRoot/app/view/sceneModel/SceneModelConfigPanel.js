Ext.define('AP.view.sceneModel.SceneModelConfigPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.sceneModelConfigPanel',
    requires: [
        'AP.view.sceneModel.SceneModelAnnotationPanel'
    ],

    layout: 'fit',
    border: false,
    bodyStyle: 'background:#fff',

    currentRecord: null,

    initComponent: function() {
        var me = this;
        me.items = [{
            xtype: 'sceneModelAnnotationPanel',
            itemId: 'annotationPanel'
        }];
        me.callParent(arguments);
    },

    /**
     * 从左侧网格选中场景时调用
     * @param {AP.model.sceneModel.SceneModel} record
     */
    loadScene: function(record) {
        this.currentRecord = record;
        var cfg = record.getConfig();
        var panel = this.down('#annotationPanel');
        if (panel) {
            panel.loadConfig(cfg, record);
        }
    },

    /**
     * 保存当前面板配置回 record
     */
    saveScene: function() {
        var me = this;
        var panel = me.down('#annotationPanel');
        if (!panel || !me.currentRecord) return;

        var cfg = panel.getConfig();
        me.currentRecord.setConfig(cfg);
        return me.currentRecord;
    },

    resetPanel: function() {
        this.currentRecord = null;
        var panel = this.down('#annotationPanel');
        if (panel) panel.clearAll();
    }
});