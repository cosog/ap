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
    
    // 记录当前已配置的三个级别的音频（不可删除）
    configFiles: [],
    // 全局试听音频实例
    _previewAudio: null,

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
                },
                {
                    xtype: 'button',
                    text: '删除',
                    iconCls: 'delete',
                    itemId: 'btnDelete',
                    disabled: true, // 默认禁用
                    handler: function() { me.doDelete(); }
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
                        '<div class="scene-audio-item">',
                            iconTpl,
                            '<div style="font-size:12px;color:#333;margin-top:10px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;font-weight:bold;" title="{fileName}">{fileName}</div>',
                        '</div>',
                    '</tpl>'
                ],
                listeners: { 
                    itemclick: function(view, record) {
                        view.getSelectionModel().select(record);
                        me.updateDeleteBtnStatus(record.get('fileName'));
                    },
                    selectionchange: function(view, selections) {
                        if (selections.length > 0) {
                            me.updateDeleteBtnStatus(selections[0].get('fileName'));
                        } else {
                            me.down('#btnDelete').setDisabled(true);
                        }
                    },
                    itemdblclick: function(view, record) { 
                        me.doSelect(record.get('fileName')); 
                    }
                }
            }]
        }];

        // ★ 扩展底部按钮：播放、暂停、停止、取消、确定
        me.buttons = [
            { 
                text: '▶ 播放', 
                iconCls: 'play',
                handler: function() { me.playPreview(); }
            },
            { 
                text: '⏸ 暂停', 
                handler: function() { me.pausePreview(); }
            },
            { 
                text: '⏹ 停止', 
                handler: function() { me.stopPreview(); }
            },
            '->',
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
        
        // 1. 先获取已配置的声音（用于判断是否可删除）
        Ext.Ajax.request({
            url: context + '/alarmSetManagerController/getAlarmLevelColor',
            method: 'GET',
            success: function (resp) {
                var r;
                try { r = Ext.JSON.decode(resp.responseText); } catch (e) { return; }
                if (r && r.Data) {
                    me.configFiles = [];
                    if (r.Data.FirstLevel && r.Data.FirstLevel.SoundFile) me.configFiles.push(r.Data.FirstLevel.SoundFile);
                    if (r.Data.SecondLevel && r.Data.SecondLevel.SoundFile) me.configFiles.push(r.Data.SecondLevel.SoundFile);
                    if (r.Data.ThirdLevel && r.Data.ThirdLevel.SoundFile) me.configFiles.push(r.Data.ThirdLevel.SoundFile);
                }
                
                // 2. 获取音频列表
                me._loadAudioList(keepSelection);
            }
        });
    },

    _loadAudioList: function(keepSelection) {
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
                    
                    // 刷新后更新删除按钮状态
                    var view = me.down('[itemId=audioView]');
                    var sel = view.getSelection();
                    if (sel.length > 0) {
                        me.updateDeleteBtnStatus(sel[0].get('fileName'));
                    } else {
                        me.down('#btnDelete').setDisabled(true);
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
                Ext.defer(function() {
                    view.getSelectionModel().select(matched);
                    view.ensureVisible(matched);
                    me.updateDeleteBtnStatus(matched.get('fileName'));
                }, 50);
            } catch (e) {}
        }
    },

    // 判断选中的文件是否可删除
    updateDeleteBtnStatus: function(fileName) {
        var me = this;
        var btnDelete = me.down('#btnDelete');
        if (!btnDelete) return;
        
        if (!fileName) {
            btnDelete.setDisabled(true);
            btnDelete.setTooltip('请先选择音频');
            return;
        }
        
        // 如果选中的文件在正在配置的三个级别中，则不可删除
        if (me.configFiles.indexOf(fileName) > -1) {
            btnDelete.setDisabled(true);
            btnDelete.setTooltip('当前音频正在被报警级别使用，无法删除');
        } else {
            btnDelete.setDisabled(false);
            btnDelete.setTooltip('删除选中的音频');
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

    // ============ 音频试听控制 ============
    playPreview: function() {
        var me = this;
        var view = me.down('[itemId=audioView]');
        var sel = view.getSelection();
        if (sel.length === 0) {
            Ext.Msg.alert('提示', '请先选择一个音频');
            return;
        }
        
        var fileName = sel[0].get('fileName');
        var url = context + '/alarmSetManagerController/playAlarmSound?fileName=' + encodeURIComponent(fileName) + '&_t=' + new Date().getTime();

        if (!me._previewAudio) {
            me._previewAudio = new Audio();
        }
        
        // 如果当前播放的已经是这首，并且是暂停状态，直接恢复
        if (me._previewAudio.src && me._previewAudio.src.indexOf(encodeURIComponent(fileName)) > -1 && me._previewAudio.paused) {
            me._previewAudio.play();
            return;
        }
        
        // 否则切换新音源
        me._previewAudio.src = url;
        me._previewAudio.play().catch(function() {
            Ext.Msg.alert('提示', '音频播放失败，请检查文件格式或网络');
        });
    },

    pausePreview: function() {
        var me = this;
        if (me._previewAudio && !me._previewAudio.paused) {
            me._previewAudio.pause();
        }
    },

    stopPreview: function() {
        var me = this;
        if (me._previewAudio) {
            me._previewAudio.pause();
            me._previewAudio.currentTime = 0;
        }
    },

    // ============ 删除音频 ============
    doDelete: function() {
        var me = this;
        var view = me.down('[itemId=audioView]');
        var sel = view.getSelection();
        if (sel.length === 0) return;
        
        var fileName = sel[0].get('fileName');
        
        Ext.Msg.confirm('确认', '确定要永久删除音频文件 <b>' + fileName + '</b> 吗？', function(btn) {
            if (btn === 'yes') {
                Ext.Ajax.request({
                    url: context + '/alarmSetManagerController/deleteAlarmSoundPool',
                    method: 'POST',
                    params: { fileName: fileName },
                    success: function(response) {
                        var r = Ext.decode(response.responseText);
                        if (r.success) {
//                            Ext.toast({ html: '删除成功', title: '提示', width: 200, align: 't' });
                            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteSuccessfully);
                            me.stopPreview(); // 如果正在播，先停掉
                            me.currentFile = ''; // 清空当前选中
                            me.loadData(true); // 刷新列表
                        } else {
                        	Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteFailed);
                        }
                    }
                });
            }
        });
    },

    doSelect: function(fileName) {
        // 关闭前停止试听
        this.stopPreview();
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
                	Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadSuccess);
                    var newFileName = r.fileName || fileInput.files[0].name;
                    me.currentFile = newFileName; // 自动选中刚刚上传的（如果是重名被加了时间戳，这里也是最新的文件名）
                    me.loadData(true); 
                } else {
                	Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadFail);
                }
                document.body.removeChild(fileInput);
            };
            xhr.onerror = function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadFail);
                document.body.removeChild(fileInput);
            };
            xhr.send(formData);
        };
        fileInput.click();
    },
    
    // 窗口关闭时清理音频资源
    onDestroy: function() {
        this.stopPreview();
        this._previewAudio = null;
        this.callParent(arguments);
    }
});