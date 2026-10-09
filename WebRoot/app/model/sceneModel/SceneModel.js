Ext.define('AP.model.sceneModel.SceneModel', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id',          type: 'int' },
        { name: 'orgid',       type: 'int' },
        { name: 'devicetype',  type: 'int', defaultValue: 101 },
        { name: 'name_zh_CN',  type: 'string' },
        { name: 'name_en',     type: 'string' },
        { name: 'name_ru',     type: 'string' },
        { name: 'config',      type: 'string' },   // CLOB，存标注 JSON
        { name: 'sort',        type: 'int' }
    ],
    idProperty: 'id',

    /**
     * 解析 config 字段为对象
     * @returns {Object}
     */
    getConfig: function() {
        var raw = this.get('config');
        if (!raw) return { imageUrl: '', shapes: [] };
        try {
            return (typeof raw === 'string') ? JSON.parse(raw) : raw;
        } catch (e) {
            console.error('场景配置解析失败', e);
            return { imageUrl: '', shapes: [] };
        }
    },

    /**
     * 写回 config
     * @param {Object} cfg
     */
    setConfig: function(cfg) {
        this.set('config', JSON.stringify(cfg || { imageUrl: '', shapes: [] }));
    }
});