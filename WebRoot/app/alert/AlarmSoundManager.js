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

    // ★ 每次 play 唯一 token：让所有在途异步解码/播放回调失效
    _playToken: 0,

    initUnlock: function () {
        var self = this;

        // 全局点击兜底
        var unlock = function () {
            if (self.unlocked) return;
            self.unlocked = true;
            if (self.audioCtx && self.audioCtx.state === 'suspended') {
                self.audioCtx.resume().catch(function () {});
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
                // 如果当前处于静音状态，不解锁播放
                if (!(AP.alert && AP.alert.AlarmManager && AP.alert.AlarmManager.isMuted)) {
                    self.play(lv);
                }
            }
        };

        document.addEventListener('click', unlock, true);
        document.addEventListener('pointerdown', unlock, true);
        document.addEventListener('keydown', unlock, true);

        // 初始化 AudioContext
        this._initAudioCtx();
    },

    _initAudioCtx: function () {
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
        } catch (e) {}
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
        
        // 1. 如果 AlarmManager 处于静音状态，不播放
        if (AP.alert && AP.alert.AlarmManager && AP.alert.AlarmManager.isMuted) {
            return;
        }

        // ★ 核心防御：在发声前，向 AlarmManager 强制询问“当前真的还有未确认的报警吗？”
        if (AP.alert && AP.alert.AlarmManager) {
            var currentHighestLevel = AP.alert.AlarmManager._getHighestUnackLevel();
            if (!currentHighestLevel) {
                return; // 如果没有未确认的报警，直接拦截，绝不发声！
            }
            // 如果前端状态有变，强制使用当前真实的最高级别
            level = currentHighestLevel;
        }

        if (!this._loaded) {
            this.loadConfig();
            var self = this;
            // 延迟重试：500ms 后再次调用 play，此时会重新走上面的“核心防御”校验
            Ext.defer(function () { 
                self.play(level); 
            }, 500);
            return;
        }

        var cfg = this.config[level];
        if (!cfg || !cfg.fileName) return;

        // 正在播更高级别，不打断
        if (this.currentLevel !== null && level > this.currentLevel) return;

        this.stop();
        this.currentLevel = level;

        // ★ 递增 token：让此前的异步回调全部失效
        var token = ++this._playToken;
        var url = this.baseUrl + encodeURIComponent(cfg.fileName) + '&_t=' + new Date().getTime();

        if (this.unlocked && this.audioCtx && this.audioCtx.state === 'running') {
            this._playWithWebAudio(url, level, token);
        } else {
            this._playWithHtmlAudio(url, level, token);
        }
    },

    _playWithWebAudio: function (url, level, token) {
        var self = this;

        // 用缓存也必须校验 token
        if (this.audioBufferCache[url]) {
            if (this._playToken === token) {
                this._doPlayBuffer(this.audioBufferCache[url], level, token);
            }
            return;
        }

        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.responseType = 'arraybuffer';
        xhr.onload = function () {
            if (xhr.status !== 200) return;
            if (self._playToken !== token) return; // ★ 发出前 token 已过期，直接丢弃
            self.audioCtx.decodeAudioData(xhr.response, function (buffer) {
                self.audioBufferCache[url] = buffer;
                if (self._playToken !== token) return; // ★ 解码完成时 token 已过期，丢弃
                self._doPlayBuffer(buffer, level, token);
            }, function (err) {
                console.error('【报警声音】解码失败', err);
                if (self._playToken !== token) return;
                // 解码失败退回传统播放
                self._playWithHtmlAudio(url, level, token);
            });
        };
        xhr.send();
    },

    _doPlayBuffer: function (buffer, level, token) {
        if (this._playToken !== token) return;
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
                if (tip) tip.hide();
            }
        } catch (e) {
            console.error('【报警声音】底层播放失败', e);
        }
    },

    _playWithHtmlAudio: function (url, level, token) {
        var self = this;
        this.stop(); // 清空之前的
        this.currentLevel = level; // stop 会置 null，重新赋值
        this._playToken = token;   // 保持与本次 token 一致（stop 已 ++，这里同步回当前 token）

        this.htmlAudio = new Audio(url);
        this.htmlAudio.loop = true;

        this.htmlAudio.play().then(function () {
            if (self._playToken !== token) {
                // 已被后续 stop/play 取代
                try { self.htmlAudio.pause(); } catch (e) {}
                self.htmlAudio = null;
                return;
            }
            self.unlocked = true;
            console.log('【报警声音】传统 Audio 播放成功级别:', level);
            if (AP.alert && AP.alert.AlarmManager && AP.alert.AlarmManager.win) {
                var tip = AP.alert.AlarmManager.win.down('#muteTip');
                if (tip) tip.hide();
            }
        }).catch(function (err) {
            if (self._playToken !== token) return;
            console.warn('【报警声音】播放彻底被拦截，等待用户点击。');
            self.pendingLevel = level;
            if (AP.alert && AP.alert.AlarmManager && AP.alert.AlarmManager.win) {
                var tip = AP.alert.AlarmManager.win.down('#muteTip');
                if (tip) tip.show();
            }
        });
    },

    stop: function () {
        // ★ 让所有在途异步回调失效
        this._playToken++;

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
            } catch (e) {}
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