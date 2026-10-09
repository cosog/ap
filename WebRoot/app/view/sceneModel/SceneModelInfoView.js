var loginUserSceneModelRight = getRoleModuleRight('SceneModel');

Ext.define("AP.view.sceneModel.SceneModelInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.sceneModelInfoView',
    layout: 'border',
    border: false,
    requires: [
        'AP.view.sceneModel.SceneModelConfigPanel'
    ],

    initComponent: function() {
        var me = this;

        Ext.apply(me, {
            tbar: [],
            items: [{
                // ============ 左侧：场景列表 ============
                region: 'west',
                width: '32%',
                layout: 'fit',
                title: '场景列表',
                header: false,
                split: true,
                collapsible: true,
                id: 'SceneModelInfoPanel_Id',
                tbar: [{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    handler: function() {
                        var gridPanel = Ext.getCmp("SceneModelGridView_Id");
                        if (isNotVal(gridPanel)) gridPanel.getStore().load();
                    }
                }, '->', {
                    xtype: 'button',
                    text: loginUserLanguageResource.add,
                    iconCls: 'add',
                    disabled: loginUserSceneModelRight.editFlag != 1,
                    handler: function() { me.onCreateScene(); }
                }, "-", {
                    xtype: 'button',
                    text: loginUserLanguageResource.deleteData,
                    iconCls: 'delete',
                    disabled: loginUserSceneModelRight.editFlag != 1,
                    handler: function() { me.onDeleteScene(); }
                }, "-", {
                    xtype: 'button',
                    text: loginUserLanguageResource.save,
                    iconCls: 'save',
                    disabled: loginUserSceneModelRight.editFlag != 1,
                    handler: function() { me.onSaveScene(); }
                }, "-", {
                    xtype: 'button',
                    text: loginUserLanguageResource.exportData,
                    iconCls: 'export',
                    disabled: loginUserSceneModelRight.editFlag != 1,
                    handler: function() { me.onExportScenes(); }
                }, "-", {
                    xtype: 'button',
                    text: loginUserLanguageResource.importData,
                    iconCls: 'import',
                    disabled: loginUserSceneModelRight.editFlag != 1,
                    handler: function() { me.onImportScenes(); }
                }]
            }, {
                // ============ 右侧：场景配置（图形标注） ============
                region: 'center',
                id: 'SceneModelConfigPanel_Id',
                xtype: 'sceneModelConfigPanel',          // ★ 引用配置面板
                title: '场景配置',
                header: true
            }]
        });

        me.callParent(arguments);
    },

    // ============ 场景增删改 ============
    onCreateScene: function() {
        var grid = Ext.getCmp("SceneModelGridView_Id");
        if (!isNotVal(grid)) return;

        var store = grid.getStore();
        var rec = Ext.create('AP.model.sceneModel.SceneModel', {
            id: 0,
            orgid: Ext.getCmp('leftOrg_Id') ? Ext.getCmp('leftOrg_Id').getValue() : 0,
            devicetype: 101,
            name_zh_CN: '',
            name_en: '',
            name_ru: '',
            config: JSON.stringify({ imageUrl: '', shapes: [] }),
            sort: store.getCount() + 1
        });
        store.add(rec);
        grid.getSelectionModel().select(rec);
    },

    onDeleteScene: function() {
        var grid = Ext.getCmp("SceneModelGridView_Id");
        if (!isNotVal(grid)) return;
        var selected = grid.getSelectionModel().getSelection();
        if (selected.length === 0) {
            Ext.Msg.alert('提示', '请选择要删除的场景');
            return;
        }
        Ext.Msg.confirm('确认', '确定要删除选中的场景吗？', function(btn) {
            if (btn !== 'yes') return;
            Ext.Ajax.request({
                url: context + '/sceneModelController/deleteSceneModel',
                method: 'POST',
                params: { ids: selected.map(r => r.get('id')).join(',') },
                success: function() {
                    grid.getStore().load();
                    Ext.getCmp('SceneModelConfigPanel_Id').resetPanel();
                }
            });
        });
    },

    onSaveScene: function() {
        var grid = Ext.getCmp("SceneModelGridView_Id");
        if (!isNotVal(grid)) return;

        var store = grid.getStore();
        var records = store.getRange();
        var payload = records.map(r => ({
            id: r.get('id'),
            orgid: r.get('orgid'),
            devicetype: r.get('devicetype'),
            name_zh_CN: r.get('name_zh_CN'),
            name_en: r.get('name_en'),
            name_ru: r.get('name_ru'),
            sort: r.get('sort'),
            config: r.get('config') || JSON.stringify({ imageUrl: '', shapes: [] })
        }));

        Ext.Ajax.request({
            url: context + '/sceneModelController/saveSceneModelList',
            method: 'POST',
            jsonData: { list: payload },
            success: function() {
                Ext.Msg.alert('成功', '场景列表已保存');
                store.load();
            }
        });
    },

    onExportScenes: function() {
        var grid = Ext.getCmp("SceneModelGridView_Id");
        if (!isNotVal(grid)) return;
        var data = grid.getStore().getRange().map(r => r.getData());
        // 通过 Blob 下载
        var blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
        var url = URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = 'scenes-' + Date.now() + '.json';
        a.click();
        URL.revokeObjectURL(url);
    },

    onImportScenes: function() {
        var me = this;
        var input = document.createElement('input');
        input.type = 'file';
        input.accept = '.json';
        input.onchange = function(e) {
            var file = e.target.files[0];
            if (!file) return;
            var reader = new FileReader();
            reader.onload = function(ev) {
                try {
                    var list = JSON.parse(ev.target.result);
                    Ext.Ajax.request({
                        url: context + '/sceneModelController/saveSceneModelList',
                        method: 'POST',
                        jsonData: { list: list },
                        success: function() {
                            var grid = Ext.getCmp("SceneModelGridView_Id");
                            if (isNotVal(grid)) grid.getStore().load();
                        }
                    });
                } catch (err) {
                    Ext.Msg.alert('错误', '文件格式不正确');
                }
            };
            reader.readAsText(file);
        };
        input.click();
    }
});