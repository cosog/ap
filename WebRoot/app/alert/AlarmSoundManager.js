Ext.define('AP.alert.AlarmSoundManager', {
    singleton: true,

    unlocked: false,
    pendingLevel: null,
    currentLevel: null,
    audio: null,
    lastPlayTime: 0,
    minInterval: 1500,   // 同级别最小间隔，防刷屏

    // 默认配置；实际以 loadConfig 从后端覆盖
    config: {
        100: { url: context + '/resources/alarm/level1.mp3' },
        200: { url: context + '/resources/alarm/level2.mp3' },
        300: { url: context + '/resources/alarm/level3.mp3' }
    },

    // 浏览器音频自动播放限制：必须在用户交互后解锁
    initUnlock: function () {
        var self = this;
        if (this.unlocked) return;

        var unlock = function () {
            if (self.unlocked) return;
            try {
                var Ctx = window.AudioContext || window.webkitAudioContext;
                if (Ctx) {
                    var ctx = new Ctx();
                    if (ctx.state === 'suspended') ctx.resume();
                }
                var a = new Audio();
                a.muted = true;
                var p = a.play();
                if (p && p.then) {
                    p.then(function () {
                        self.unlocked = true;
                        document.removeEventListener('click', unlock);
                        document.removeEventListener('keydown', unlock);
                        if (self.pendingLevel !== null) {
                            var lv = self.pendingLevel;
                            self.pendingLevel = null;
                            self.play(lv);
                        }
                    }).catch(function () {
                        // 保持 unlocked = false，下次点击继续尝试
                    });
                } else {
                    self.unlocked = true;
                }
            } catch (e) {}
        };

        document.addEventListener('click', unlock);
        document.addEventListener('keydown', unlock);
    },

    // 只拉"级别 -> URL"，上传/删除放在报警等级配置页面
    loadConfig: function () {
        var self = this;
        Ext.Ajax.request({
        	url: context + '/alarmQueryController/getAlarmSoundList',
            method: 'GET',
            success: function (resp) {
                var r;
                try { r = Ext.JSON.decode(resp.responseText); } catch (e) { return; }
                if (r && r.success && r.data) {
                    Ext.Array.each(r.data, function (item) {
                        if (item.level && item.url) {
                            self.config[item.level] = { url: item.url };
                        }
                    });
                }
            },
            failure: function () {
            	if (window.console) console.warn('报警音配置加载失败，使用默认音频');
            }
        });
    },

    play: function (level) {
        if (!level || level <= 0) return;
        if (!this.unlocked) {
            if (this.pendingLevel === null || level < this.pendingLevel) {
                this.pendingLevel = level;
            }
            return;
        }
        var cfg = this.config[level];
        if (!cfg || !cfg.url) return;

        // 正在播更高级别，不打断
        if (this.currentLevel !== null && level > this.currentLevel) return;
        // 同级别正在播，不叠加
        if (this.currentLevel === level && this.audio && !this.audio.paused) return;
        // 同级别最小间隔
        if (this.currentLevel === level &&
            (Date.now() - this.lastPlayTime) < this.minInterval) return;

        this.stop();
        var self = this;
        var a = new Audio(cfg.url);
        this.audio = a;
        this.currentLevel = level;
        this.lastPlayTime = Date.now();
        a.onended = function () {
            if (self.audio === a) {
                self.currentLevel = null;
                self.audio = null;
            }
        };
        a.play().catch(function () {
            if (self.audio === a) {
                self.currentLevel = null;
                self.audio = null;
            }
        });
    },

    stop: function () {
        if (this.audio) {
            try { this.audio.pause(); this.audio.currentTime = 0; } catch (e) {}
        }
        this.audio = null;
        this.currentLevel = null;
    }
});