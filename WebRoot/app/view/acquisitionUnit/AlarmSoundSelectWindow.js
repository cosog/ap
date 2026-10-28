Ext.define('AP.view.acquisitionUnit.AlarmSoundSelectWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.alarmSoundSelectWindow',
    title: '选择报警声音',
    width: 820,
    height: 580,
    modal: true,
    layout: 'fit',
    closeAction: 'destroy',
    constrain: true,
    
    // 传入的当前已选文件名
    currentFile: '',
    // 确认回调
    onConfirm: Ext.emptyFn,

    initComponent: function () {
        var me = this;

        // ★ 内置 CSS：动态向文档头部注入样式
        if (!Ext.util.CSS.getRule('.scene-audio-item')) {
            Ext.util.CSS.createStyleSheet(
                '.scene-audio-item {' +
                '    display: inline-block; width: 140px; height: 140px;' +
                '    margin: 10px; text-align: center; cursor: pointer;' +
                '    border: 2px solid #e9ecef; border-radius: 6px;' +
                '    padding: 10px; background: #fff; vertical-align: top;' +
                '    transition: all 0.2s ease-in-out;' +
                '}' +
                '.scene-audio-item-over {' +
                '    border-color: #1677ff !important;' +
                '    box-shadow: 0 0 8px rgba(22, 119, 255, 0.3);' +
                '}' +
                '.scene-audio-item-selected {' +
                '    border-color: #1677ff !important;' +
                '    background-color: #e6f0ff !important;' +
                '    box-shadow: 0 0 10px rgba(22, 119, 255, 0.5);' +
                '}'
            );
        }

        me.store = Ext.create('Ext.data.Store', {
            fields: ['fileName', 'url', 'size']
        });

        // 图标模板
        var iconTpl = '<div style="height:60px;display:flex;align-items:center;justify-content:center;overflow:hidden;font-size:36px;color:#1677ff;">🎵</div>';

        me.items = [{
            xtype: 'panel',
            layout: 'fit',
            tbar: [
                { 
                    xtype: 'textfield', 
                    itemId: 'searchField', 
                    emptyText: '搜索文件名...', 
                    width: 220,
                    listeners: { 
                        change: function(f, v) { me.filterAudios(v); } 
                    } 
                },
                '->',
                { 
                    xtype: 'button', 
                    text: '📤 上传新音频', 
                    handler: function() { me.doUpload(); } 
                },
                { 
                    xtype: 'button', 
                    text: '刷新', 
                    iconCls: 'note-refresh',
                    handler: function() { me.loadData(true); } 
                }
            ],
            items: [{
                xtype: 'dataview',
                itemId: 'audioView',
                itemSelector: 'div.scene-audio-item',
                overItemCls: 'scene-audio-item-over',
                selectedItemCls: 'scene-audio-item-selected',
                emptyText: '<div style="padding:40px;color:#aaa;text-align:center;">暂无音频文件</div>',
                store: me.store,
                tpl: [
                    '<tpl for=".">',
                        // ★ 内置样式应用
                        '<div class="scene-audio-item">',
                            iconTpl,
                            '<div style="font-size:12px;color:#333;margin-top:10px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;font-weight:bold;" title="{fileName}">{fileName}</div>',
                        '</div>',
                    '</tpl>'
                ],
                listeners: { 
                    itemclick: function(view, record) {
                        view.getSelectionModel().select(record);
                    },
                    itemdblclick: function(view, record) { 
                        me.doSelect(record.get('fileName')); 
                    }
                }
            }]
        }];

        me.buttons = [
            { 
                text: '试听', 
                iconCls: 'play',
                handler: function() {
                    var view = me.down('[itemId=audioView]');
                    var sel = view.getSelection();
                    if (sel.length === 0) {
                        Ext.Msg.alert('提示', '请先选择一个音频');
                        return;
                    }
                    me.playPreview(sel[0].get('fileName'));
                }
            },
            { 
                text: '取消', 
                iconCls: 'cancel', 
                handler: function() { me.close(); } 
            },
            { 
                text: '确定', 
                iconCls: 'save', 
                handler: function() {
                    var view = me.down('[itemId=audioView]');
                    var sel = view.getSelection();
                    if (sel.length === 0) {
                        Ext.Msg.confirm('确认', '未选择任何音频，是否清空该级别的报警声音？', function(btn) {
                            if (btn === 'yes') {
                                me.doSelect('');
                            }
                        });
                        return;
                    }
                    me.doSelect(sel[0].get('fileName'));
                }
            }
        ];

        me.callParent(arguments);
        
        // 初始化加载
        me.loadData();
    },

    loadData: function(keepSelection) {
        var me = this;
        Ext.Ajax.request({
            url: context + '/alarmSetManagerController/getAlarmSoundPool',
            method: 'GET',
            success: function(response) {
                var r;
                try { r = Ext.decode(response.responseText); } catch (e) { return; }
                if (r && r.success) {
                    me.allAudios = r.data || [];
                    me.store.loadData(me.allAudios);
                    
                    if (keepSelection || me.currentFile) {
                        me.selectCurrentFile();
                    }
                }
            }
        });
    },

    // 自动定位并选中当前配置的音频
    selectCurrentFile: function() {
        var me = this;
        if (!me.currentFile) return;
        
        var view = me.down('[itemId=audioView]');
        var store = view.getStore();
        var matched = null;
        
        store.each(function(rec) {
            if (rec.get('fileName') === me.currentFile) {
                matched = rec;
                return false;
            }
        });
        
        if (matched) {
            try {
                // 延迟一点点执行，确保 dataview 的 DOM 已经渲染完毕
                Ext.defer(function() {
                    view.getSelectionModel().select(matched);
                    view.ensureVisible(matched);
                }, 50);
            } catch (e) {
                console.warn('默认选中失败', e);
            }
        }
    },

    filterAudios: function(keyword) {
        var me = this;
        var view = me.down('[itemId=audioView]');
        var allData = me.allAudios || [];
        view.getStore().removeAll();
        
        if (!keyword) {
            view.getStore().add(allData);
            me.selectCurrentFile();
            return;
        }
        
        var filtered = allData.filter(function(item) {
            return item.fileName.toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
        });
        view.getStore().add(filtered);
        me.selectCurrentFile();
    },

    playPreview: function(fileName) {
        // 走后端流式接口，避免浏览器阻止或 404
        var url = context + '/alarmSetManagerController/playAlarmSound?fileName=' + encodeURIComponent(fileName) + '&_t=' + new Date().getTime();
        var audio = new Audio(url);
        audio.play().catch(function() {
            Ext.Msg.alert('提示', '音频播放失败，请检查文件格式或网络');
        });
    },

    doSelect: function(fileName) {
        if (this.onConfirm) {
            this.onConfirm(fileName);
        }
        this.close();
    },

    doUpload: function() {
        var me = this;
        var fileInput = document.createElement('input');
        fileInput.type = 'file';
        fileInput.accept = 'audio/*';
        fileInput.style.display = 'none';
        document.body.appendChild(fileInput);

        fileInput.onchange = function() {
            if (!fileInput.files || fileInput.files.length === 0) {
                document.body.removeChild(fileInput);
                return;
            }
            var formData = new FormData();
            formData.append('file', fileInput.files[0]);

            var xhr = new XMLHttpRequest();
            xhr.open('POST', context + '/alarmSetManagerController/uploadAlarmSound', true);
            xhr.onload = function() {
                var r;
                try { r = JSON.parse(xhr.responseText); } catch (e) { r = { success: false }; }
                if (r.success) {
                    Ext.toast({ html: '上传成功', title: '提示', width: 200, align: 't' });
                    var newFileName = r.fileName || fileInput.files[0].name;
                    me.currentFile = newFileName; // 自动选中刚刚上传的
                    me.loadData(true); 
                } else {
                    Ext.Msg.alert('提示', r.message || '上传失败');
                }
                document.body.removeChild(fileInput);
            };
            xhr.onerror = function() {
                Ext.Msg.alert('错误', '上传失败');
                document.body.removeChild(fileInput);
            };
            xhr.send(formData);
        };
        fileInput.click();
    }
});