Ext.define('AP.alert.AlarmManager', {
    singleton: true,

    alarms: {},
    ackKeys: {},
    win: null,
    _localAckWarned: false,

    levelStyle: {
        100: { text: '一级', color: '#dc2828' },
        200: { text: '二级', color: '#f09614' },
        300: { text: '三级', color: '#fae600' }
    },

    handle: function (data) {
        if (typeof alarmAlert !== 'undefined' && !alarmAlert) return;
        if (!data || !data.functionCode) return;
        var fc = data.functionCode.toUpperCase();

        if (fc === 'DEVICEREALTIMEMONITORINGDATA') {
            var list = this.extract(data);
            for (var i = 0; i < list.length; i++) this.upsert(list[i]);
            this.checkRecover(data);
        } else if (fc === 'DEVICEREALTIMEMONITORINGSTATUSDATA') {
            var key = data.deviceId + '|commStatusName';
            if (data.commAlarmLevel === undefined) return;
            if (data.commAlarmLevel > 0) {
                this.upsert({
                    key: key, alertId: null,
                    deviceId: data.deviceId, deviceName: data.deviceName, orgId: data.orgId,
                    column: 'commStatusName', columnName: '通信状态',
                    value: data.commStatusName, alarmLevel: data.commAlarmLevel, acqTime: data.acqTime
                });
            } else if (this.alarms[key]) {
                this.recover(data.deviceId, 'commStatusName');
            }
        }
    },

    extract: function (data) {
        var result = [];
        var deviceId = data.deviceId, deviceName = data.deviceName, orgId = data.orgId, acqTime = data.acqTime;
        var map = {};
        var push = function (item) {
            if (!item.column || !item.triggerAlarm || !item.alertId || !item.alarmLevel || item.alarmLevel <= 0) return;
            var key = deviceId + '|' + item.column;
            if (map[key]) return;
            map[key] = true;
            result.push({
                key: key, alertId: item.alertId || null,
                deviceId: deviceId, deviceName: deviceName, orgId: orgId,
                column: item.column, columnName: item.columnName || item.column,
                value: item.value, alarmLevel: item.alarmLevel, acqTime: acqTime
            });
        };
        var all = data.allItemInfo || [];
        for (var i = 0; i < all.length; i++) push(all[i]);
        if (data.commAlarmLevel > 0) push({ column: 'commStatusName', columnName: '通信状态', value: data.commStatusName, alarmLevel: data.commAlarmLevel });
        if (data.runAlarmLevel > 0) push({ column: 'runStatusName', columnName: '运行状态', value: data.runStatusName, alarmLevel: data.runAlarmLevel });
        if (data.resultAlarmLevel > 0) push({ column: 'resultName', columnName: '工况', value: data.resultName, alarmLevel: data.resultAlarmLevel });
        return result;
    },

    upsert: function (alarm) {
        var old = this.alarms[alarm.key];

        if (this.ackKeys[alarm.key] && old && alarm.alarmLevel >= old.alarmLevel) {
            old.value = alarm.value;
            old.acqTime = alarm.acqTime;
            this.updateNode(old);
            return;
        }
        if (this.ackKeys[alarm.key]) delete this.ackKeys[alarm.key];

        if (!old) {
            this.alarms[alarm.key] = alarm;
            this.addNode(alarm);
            this.ensureWindowExpanded();
            AP.alert.AlarmSoundManager.play(alarm.alarmLevel);
        } else {
            var levelUp = alarm.alarmLevel < old.alarmLevel;
            old.value = alarm.value;
            old.acqTime = alarm.acqTime;
            old.alarmLevel = alarm.alarmLevel;
            old.alertId = alarm.alertId || old.alertId;
            this.updateNode(old);
            if (levelUp) {
                this.ensureWindowExpanded();
                AP.alert.AlarmSoundManager.play(alarm.alarmLevel);
            }
        }
    },

    checkRecover: function (data) {
        var deviceId = data.deviceId;
        var levelMap = {};
        var all = data.allItemInfo || [];
        for (var i = 0; i < all.length; i++) levelMap[all[i].column] = all[i].alarmLevel || 0;
        levelMap['commStatusName']  = (data.commAlarmLevel  === undefined) ? -1 : (data.commAlarmLevel  || 0);
        levelMap['runStatusName']   = (data.runAlarmLevel   === undefined) ? -1 : (data.runAlarmLevel   || 0);
        levelMap['resultName']      = (data.resultAlarmLevel === undefined) ? -1 : (data.resultAlarmLevel || 0);

        var toRemove = [];
        for (var key in this.alarms) {
            if (!this.alarms.hasOwnProperty(key)) continue;
            if (this.alarms[key].deviceId != deviceId) continue;
            if (levelMap[this.alarms[key].column] === 0) toRemove.push(this.alarms[key].column);
        }
        for (var j = 0; j < toRemove.length; j++) this.recover(deviceId, toRemove[j]);
    },

    recover: function (deviceId, column) {
        var key = deviceId + '|' + column;
        if (!this.alarms[key]) return;
        delete this.alarms[key];
        delete this.ackKeys[key];
        this.removeNode(key);
    },

    ensureWindow: function () {
        if (!this.win) this.win = Ext.create('AP.view.alert.AlarmWindow');
        return this.win;
    },
    ensureWindowExpanded: function () {
        var win = this.ensureWindow();
        if (!win.isVisible()) win.show();
        if (win.collapsed) win.expand();
        win.startBlink();
    },
    addNode:    function (a) { this.ensureWindow().addAlarmNode(a); },
    updateNode: function (a) { if (this.win) this.win.updateAlarmNode(a); },
    removeNode: function (k) { if (this.win) this.win.removeAlarmNode(k); },

    ack: function (keys, callback) {
        var self = this;
        var validKeys = [];
        Ext.Array.each(keys, function (key) { if (self.alarms[key]) validKeys.push(key); });
        if (validKeys.length === 0) { if (callback) callback(true); return; }

        var withId = [], withoutId = [];
        Ext.Array.each(validKeys, function (key) {
            var a = self.alarms[key];
            if (a.alertId) withId.push({ key: key, alertId: a.alertId });
            else withoutId.push(key);
        });

        var finish = function (ok) {
            if (ok) {
                Ext.Array.each(withId, function (x) {
                    self.ackKeys[x.key] = true;
                    if (self.alarms[x.key]) self.alarms[x.key].alertId = null;
                });
                Ext.Array.each(withoutId, function (k) { self.ackKeys[k] = true; });
            }
            if (callback) callback(ok);
        };

        if (withId.length === 0) {
            if (!this._localAckWarned) {
                this._localAckWarned = true;
                Ext.toast({ html: '当前报警未同步到服务端，本次仅本地确认', title: '提示', width: 300, align: 't' });
            }
            finish(true);
            return;
        }

        Ext.Ajax.request({
            url: context + '/alarmQueryController/batchAck',
            method: 'POST',
            jsonData: { alertIds: Ext.Array.pluck(withId, 'alertId') },
            success: function (resp) {
                var r; try { r = Ext.JSON.decode(resp.responseText); } catch (e) { r = { success: false }; }
                if (r.success) {
                    finish(true);
                    if (r.data && r.data.failedIds && r.data.failedIds.length > 0) {
                        Ext.toast({ html: r.data.failedIds.length + ' 条确认失败', title: '提示', width: 300, align: 't' });
                    }
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, r.message || '确认失败');
                    finish(false);
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.ajaxError);
                finish(false);
            }
        });
    },

    // 远程确认（其他用户已确认）
    onRemoteAck: function (alertIds, ackUser, ackTime) {
        if (!alertIds || alertIds.length === 0) return;
        var self = this;
        var ackSet = {};
        Ext.Array.each(alertIds, function (id) { ackSet[id] = true; });
        Ext.Object.each(this.alarms, function (key, alarm) {
            if (alarm.alertId && ackSet[alarm.alertId]) self.ackKeys[key] = true;
        });
        if (this.win) this.win.markRemoteAck(ackSet);
    },

    // 登出重置
    reset: function () {
        this.alarms = {};
        this.ackKeys = {};
        this._localAckWarned = false;
        if (this.win) {
            try { this.win.stopBlink(); this.win.close(); } catch (e) {}
            this.win = null;
        }
    },

    // 初次进入拉取未确认报警
    loadPendingAlarms: function () {
        var self = this;
        if (typeof alarmAlert !== 'undefined' && !alarmAlert) return;
        var orgIdCmp = Ext.getCmp('leftOrg_Id');
        var orgId = orgIdCmp ? orgIdCmp.getValue() : '';
        var deviceType = '';
        try {
            if (typeof getDeviceTypeFromTabId === 'function') {
                deviceType = getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
            }
        } catch (e) {}

        Ext.Ajax.request({
            url: context + '/alarmQueryController/getPendingAlarms',
            method: 'GET',
            params: { orgId: orgId, deviceType: deviceType, limit: 200 },
            success: function (resp) {
                var r; try { r = Ext.JSON.decode(resp.responseText); } catch (e) { return; }
                if (!r || !r.success || !r.data || r.data.length === 0) return;
                Ext.Array.each(r.data, function (item) {
                    self.upsertSilent({
                        key: item.deviceId + '|' + item.column,
                        alertId: item.alertId,
                        deviceId: item.deviceId,
                        deviceName: item.deviceName,
                        orgId: item.orgId,
                        column: item.column,
                        columnName: item.columnName,
                        value: item.value,
                        alarmLevel: item.alarmLevel,
                        acqTime: item.acqTime
                    });
                });
                if (self.win && self.win.hasUnacknowledged && self.win.hasUnacknowledged()) {
                    self.ensureWindowExpanded();
                    var maxLevel = self._getHighestUnackLevel();
                    if (maxLevel) AP.alert.AlarmSoundManager.play(maxLevel);
                }
            }
        });
    },

    upsertSilent: function (alarm) {
        if (!this.alarms[alarm.key]) {
            this.alarms[alarm.key] = alarm;
            this.addNode(alarm);
        }
    },
    _getHighestUnackLevel: function () {
        var min = null;
        Ext.Object.each(this.alarms, function (k, a) {
            if (this.ackKeys[k]) return;
            if (min === null || a.alarmLevel < min) min = a.alarmLevel;
        }, this);
        return min;
    }
});