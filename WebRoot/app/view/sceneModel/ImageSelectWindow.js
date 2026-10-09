Ext.define('AP.view.sceneModel.ImageSelectWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.imageSelectWindow',

    title: '选择服务器图片',
    width: 720,
    height: 520,
    modal: true,
    layout: 'border',
    closeAction: 'hide',

    initComponent: function() {
        var me = this;

        me.items = [{
            // 左侧：目录树
            region: 'west',
            width: 220,
            xtype: 'treepanel',
            itemId: 'dirTree',
            rootVisible: true,
            split: true,
            store: {
                type: 'tree',
                proxy: {
                    type: 'ajax',
                    url: context + '/sceneModelController/getSceneImageDirs'
                },
                root: {
                    text: '图片根目录',
                    id: '/uploads/scene/',
                    expanded: true
                }
            },
            listeners: {
                itemclick: function(view, record) {
                    me.loadImages(record.get('id'));
                }
            }
        }, {
            // 右侧：图片列表
            region: 'center',
            xtype: 'panel',
            itemId: 'imagePanel',
            layout: 'fit',
            tbar: [{
                xtype: 'textfield',
                itemId: 'searchField',
                emptyText: '搜索文件名...',
                width: 200,
                listeners: {
                    change: function(field, v) { me.filterImages(v); }
                }
            }],
            items: [{
                xtype: 'dataview',
                itemId: 'imageView',
                cls: 'scene-image-view',
                itemSelector: 'div.scene-image-item',
                overItemCls: 'scene-image-item-over',
                selectedItemCls: 'scene-image-item-selected',
                emptyText: '请选择左侧目录',
                store: {
                    fields: ['name', 'url', 'size', 'mtime'],
                    data: []
                },
                tpl: [
                    '<tpl for=".">',
                        '<div class="scene-image-item" style="display:inline-block;width:120px;height:140px;',
                        'margin:8px;text-align:center;cursor:pointer;border:2px solid #e9ecef;border-radius:6px;padding:6px;">',
                            '<img src="{url}" style="max-width:100px;max-height:100px;object-fit:contain;" onerror="this.src=\'resources/images/no-image.png\'"/>',
                            '<div style="font-size:11px;color:#666;margin-top:4px;overflow:hidden;text-overflow:ellipsis;" title="{name}">{name}</div>',
                        '</div>',
                    '</tpl>'
                ],
                listeners: {
                    itemdblclick: function(view, record) {
                        me.doSelect(record.get('url'));
                    }
                }
            }]
        }];

        me.buttons = [
            { text: '取消', handler: function() { me.hide(); } },
            { text: '确定', cls: 'x-btn-primary', handler: function() {
                var view = me.down('#imageView');
                var sel = view.getSelection();
                if (sel.length === 0) {
                    Ext.Msg.alert('提示', '请选择一张图片');
                    return;
                }
                me.doSelect(sel[0].get('url'));
            }}
        ];

        me.callParent(arguments);
    },

    loadImages: function(dirPath) {
        var me = this;
        var store = me.down('#imageView').getStore();
        Ext.Ajax.request({
            url: context + '/sceneModelController/getSceneImageList',
            method: 'POST',
            params: { dir: dirPath },
            success: function(resp) {
                var result = Ext.decode(resp.responseText);
                var list = (result.data || []).map(function(item) {
                    return {
                        name: item.name,
                        url: item.url,
                        size: item.size,
                        mtime: item.mtime
                    };
                });
                store.removeAll();
                store.add(list);
                me.allImages = list;   // 保存原始列表用于过滤
            }
        });
    },

    filterImages: function(keyword) {
        var store = this.down('#imageView').getStore();
        store.removeAll();
        if (!keyword) {
            store.add(this.allImages || []);
        } else {
            var matched = (this.allImages || []).filter(function(it) {
                return it.name.toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
            });
            store.add(matched);
        }
    },

    doSelect: function(url) {
        this.fireEvent('imageSelected', url);
        this.hide();
    }
});