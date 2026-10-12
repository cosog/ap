Ext.define('AP.model.sceneModel.SceneModel', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'modelId',    type: 'int' },
        { name: 'id',         type: 'int' },
        { name: 'orgid',      type: 'int' },
        { name: 'devicetype', type: 'int', defaultValue: 101 },
        { name: 'name_zh_CN', type: 'string' },
        { name: 'name_en',    type: 'string' },
        { name: 'name_ru',    type: 'string' },
        { name: 'config',     type: 'string' },
        { name: 'sort',       type: 'int' }
    ]
});