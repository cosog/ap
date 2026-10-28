Ext.define('AP.alert.AlarmSoundManager', {
    singleton: true,

    unlocked: false,
    pendingLevel: null,
    currentLevel: null,
    audio: null,

    config: {},
    baseUrl: '',
    _loaded: false,
    _retryTask: null,

    initUnlock: function () {
        var self = this;
        
        var unlock = function () {
            if (self.unlocked) return;
            self.unlocked = true;
            console.log('【报警声音】用户点击页面，已激活音频。');
            
            document.removeEventListener('click', unlock, true);
            document.removeEventListener('pointerdown', unlock, true);
            document.removeEventListener('keydown', unlock, true);
            
            // 隐藏弹窗里的静音提示
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

        if (this.currentLevel === level && this.audio && !this.audio.paused) return;

        this.stop();
        
        var url = this.baseUrl + encodeURIComponent(cfg.fileName) + '&_t=' + new Date().getTime();
        var a = new Audio(url);
        
        this.audio = a;
        this.currentLevel = level;
        
        // ★ 核心：手动控制重播
        a.onended = function() {
            if (AP.alert.AlarmSoundManager.audio === a) {
                a.currentTime = 0;
                a.play().catch(function() {
                    console.warn('【报警声音】重播被浏览器拦截，等待用户点击。');
                });
            }
        };

        a.play().then(function() {
            console.log('【报警声音】开始播放级别:', level);
            if (AP.alert.AlarmSoundManager) {
                AP.alert.AlarmSoundManager.unlocked = true;
            }
            if (AP.alert.AlarmSoundManager._retryTask) {
                clearInterval(AP.alert.AlarmSoundManager._retryTask);
                AP.alert.AlarmSoundManager._retryTask = null;
            }
        }).catch(function (err) {
            console.warn('【报警声音】自动播放被拦截，等待用户交互。');
            AP.alert.AlarmSoundManager.pendingLevel = level;
            AP.alert.AlarmSoundManager.unlocked = false;
            
            // 轮询重试机制
            if (!AP.alert.AlarmSoundManager._retryTask) {
                AP.alert.AlarmSoundManager._retryTask = setInterval(function() {
                    if (AP.alert.AlarmSoundManager.unlocked) {
                        clearInterval(AP.alert.AlarmSoundManager._retryTask);
                        AP.alert.AlarmSoundManager._retryTask = null;
                        return;
                    }
                    if (AP.alert.AlarmSoundManager.audio) {
                        AP.alert.AlarmSoundManager.audio.play().then(function(){
                            AP.alert.AlarmSoundManager.unlocked = true;
                            clearInterval(AP.alert.AlarmSoundManager._retryTask);
                            AP.alert.AlarmSoundManager._retryTask = null;
                        }).catch(function(){});
                    }
                }, 2000);
            }
        });
    },

    stop: function () {
        if (this.audio) {
            try { 
                this.audio.pause(); 
                this.audio.currentTime = 0; 
                this.audio.onended = null;
            } catch (e) {}
        }
        this.audio = null;
        this.currentLevel = null;
        
        if (this._retryTask) {
            clearInterval(this._retryTask);
            this._retryTask = null;
        }
    },

    refresh: function () {
        this._loaded = false;
        this.loadConfig();
    }
});