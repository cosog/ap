Ext.define('AP.alert.AlarmSoundManager', {
    singleton: true,

    unlocked: false,
    pendingLevel: null,
    currentLevel: null,
    audioCtx: null,
    sourceNode: null,
    audioBufferCache: {},
    htmlAudio: null,

    config: {},
    baseUrl: '',
    _loaded: false,

    initUnlock: function () {
        var self = this;
        
        // 全局点击兜底
        var unlock = function () {
            if (self.unlocked) return;
            self.unlocked = true;
            if (self.audioCtx && self.audioCtx.state === 'suspended') {
                self.audioCtx.resume().catch(function(){});
            }
            
            document.removeEventListener('click', unlock, true);
            document.removeEventListener('pointerdown', unlock, true);
            document.removeEventListener('keydown', unlock, true);
            
            if (AP.alert && AP.alert.AlarmManager && AP.alert.AlarmManager.win) {
                var tip = AP.alert.AlarmManager.win.down('#muteTip');
                if (tip) tip.hide();
            }

            if (self.pendingLevel !== null) {
                var lv = self.pendingLevel;
                self.pendingLevel = null;
                self.play(lv);
            }
        };

        document.addEventListener('click', unlock, true);
        document.addEventListener('pointerdown', unlock, true);
        document.addEventListener('keydown', unlock, true);

        // 初始化 AudioContext
        this._initAudioCtx();
    },

    _initAudioCtx: function() {
        try {
            if (!this.audioCtx) {
                var Ctx = window.AudioContext || window.webkitAudioContext;
                if (Ctx) {
                    this.audioCtx = new Ctx();
                }
            }
            if (this.audioCtx) {
                if (this.audioCtx.state === 'suspended') {
                    this.audioCtx.resume().then(() => {
                        this.unlocked = true;
                        console.log('【报警声音】AudioContext 恢复成功，已解锁！');
                    }).catch(e => {
                        console.warn('【报警声音】AudioContext 启动失败，等待用户点击');
                    });
                } else {
                    this.unlocked = true;
                }
            }
        } catch(e) {}
    },

    loadConfig: function () {
        var self = this;
        Ext.Ajax.request({
            url: context + '/alarmSetManagerController/getAlarmLevelColor',
            method: 'GET',
            success: function (resp) {
                var r;
                try { r = Ext.JSON.decode(resp.responseText); } catch (e) { return; }
                if (!r || !r.Data) return;

                self.config = {};
                self.baseUrl = context + '/alarmSetManagerController/playAlarmSound?fileName=';

                var mk = function (style) {
                    if (style && style.SoundFile) return { fileName: style.SoundFile };
                    return null;
                };
                
                var c1 = mk(r.Data.FirstLevel);
                var c2 = mk(r.Data.SecondLevel);
                var c3 = mk(r.Data.ThirdLevel);
                
                if (c1) self.config[100] = c1;
                if (c2) self.config[200] = c2;
                if (c3) self.config[300] = c3;
                
                self._loaded = true;
            }
        });
    },

    play: function (level) {
        if (!level || level <= 0) return;

        if (!this._loaded) {
            this.loadConfig();
            var self = this;
            Ext.defer(function() { self.play(level); }, 500);
            return;
        }

        var cfg = this.config[level];
        if (!cfg || !cfg.fileName) return;

        // 正在播更高级别，不打断
        if (this.currentLevel !== null && level > this.currentLevel) return;

        this.stop();
        this.currentLevel = level;

        var url = this.baseUrl + encodeURIComponent(cfg.fileName) + '&_t=' + new Date().getTime();

        // 如果解锁成功，优先走底层（体验最好，支持无限循环）
        if (this.unlocked && this.audioCtx && this.audioCtx.state === 'running') {
            this._playWithWebAudio(url, level);
        } else {
            // 兜底方案：如果底层被拦截，尝试用传统 Audio 元素（少数浏览器对 Audio 标签宽容）
            this._playWithHtmlAudio(url, level);
        }
    },

    _playWithWebAudio: function(url, level) {
        var self = this;
        
        if (this.audioBufferCache[url]) {
            this._doPlayBuffer(this.audioBufferCache[url], level);
            return;
        }

        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.responseType = 'arraybuffer';
        xhr.onload = function() {
            if (xhr.status === 200) {
                self.audioCtx.decodeAudioData(xhr.response, function(buffer) {
                    self.audioBufferCache[url] = buffer;
                    self._doPlayBuffer(buffer, level);
                }, function(err) {
                    console.error('【报警声音】解码失败', err);
                    // 解码失败退回传统播放
                    self._playWithHtmlAudio(url, level);
                });
            }
        };
        xhr.send();
    },

    _doPlayBuffer: function(buffer, level) {
        if (this.currentLevel !== level) return;

        try {
            this.sourceNode = this.audioCtx.createBufferSource();
            this.sourceNode.buffer = buffer;
            this.sourceNode.loop = true; // 开启无限循环
            this.sourceNode.connect(this.audioCtx.destination);
            this.sourceNode.start(0);
            console.log('【报警声音】Web Audio API 开始循环播放级别:', level);
            
            if (AP.alert && AP.alert.AlarmManager && AP.alert.AlarmManager.win) {
                var tip = AP.alert.AlarmManager.win.down('#muteTip');
                if (tip) tip.hide(); // 如果底层播成功了，把提示隐藏
            }
        } catch (e) {
            console.error('【报警声音】底层播放失败', e);
        }
    },

    _playWithHtmlAudio: function(url, level) {
        var self = this;
        this.stop(); // 清空之前的
        
        this.htmlAudio = new Audio(url);
        this.htmlAudio.loop = true;
        
        this.htmlAudio.play().then(function() {
            self.unlocked = true;
            console.log('【报警声音】传统 Audio 播放成功级别:', level);
            if (AP.alert && AP.alert.AlarmManager && AP.alert.AlarmManager.win) {
                var tip = AP.alert.AlarmManager.win.down('#muteTip');
                if (tip) tip.hide();
            }
        }).catch(function(err) {
            console.warn('【报警声音】播放彻底被拦截，等待用户点击。');
            self.pendingLevel = level;
            if (AP.alert && AP.alert.AlarmManager && AP.alert.AlarmManager.win) {
                var tip = AP.alert.AlarmManager.win.down('#muteTip');
                if (tip) tip.show();
            }
        });
    },

    stop: function () {
        if (this.sourceNode) {
            try {
                this.sourceNode.stop();
                this.sourceNode.disconnect();
            } catch (e) {}
            this.sourceNode = null;
        }
        
        if (this.htmlAudio) {
            try {
                this.htmlAudio.pause();
                this.htmlAudio.currentTime = 0;
            } catch(e) {}
            this.htmlAudio = null;
        }
        
        this.currentLevel = null;
        console.log('【报警声音】已停止播放');
    },

    refresh: function () {
        this._loaded = false;
        this.audioBufferCache = {};
        this.loadConfig();
    }
});