Ext.define('AP.alert.AlarmManager', {
    singleton: true,

    alarms: {},     // alarmKey -> alarm 对象
    ackKeys: {},    // alarmKey -> true（已确认标记）
    win: null,

    levelStyle: {
        100: { text: '一级', color: '#dc2828' },
        200: { text: '二级', color: '#f09614' },
        300: { text: '三级', color: '#fae600' }
    },

    // ============ 统一入口 ============
    handle: function (data) {
    	if (typeof alarmAlert !== 'undefined' && !alarmAlert) return;
        if (!data || !data.functionCode) return;
        var fc = data.functionCode.toUpperCase();

        if (fc === 'DEVICEREALTIMEMONITORINGDATA') {
            var list = this.extract(data);
            for (var i = 0; i < list.length; i++) {
                this.upsert(list[i]);
            }
            this.checkRecover(data);
        } else if (fc === 'DEVICEREALTIMEMONITORINGSTATUSDATA') {
            // 只处理通信报警，避免和 deviceRealTimeMonitoringData 重复
            var key = data.deviceId + '|commStatusName';
            if (data.commAlarmLevel === undefined) return;
            if (data.commAlarmLevel > 0) {
                this.upsert({
                    key: key,
                    alertId: null,
                    deviceId: data.deviceId,
                    deviceName: data.deviceName,
                    orgId: data.orgId,
                    column: 'commStatusName',
                    columnName: '通信状态',
                    value: data.commStatusName,
                    alarmLevel: data.commAlarmLevel,
                    acqTime: data.acqTime
                });
            } else if (this.alarms[key]) {
                this.recover(data.deviceId, 'commStatusName');
            }
        }
    },

    // ============ 从推送提取报警 ============
    extract: function (data) {
        var result = [];
        var deviceId = data.deviceId;
        var deviceName = data.deviceName;
        var orgId = data.orgId;
        var acqTime = data.acqTime;
        var map = {};

        var push = function (item) {
            if (!item.column || !item.alarmLevel || item.alarmLevel <= 0) return;
            var key = deviceId + '|' + item.column;
            if (map[key]) return;
            map[key] = true;
            result.push({
                key: key,
                alertId: item.alertId || null,
                deviceId: deviceId,
                deviceName: deviceName,
                orgId: orgId,
                column: item.column,
                columnName: item.columnName || item.column,
                value: item.value,
                alarmLevel: item.alarmLevel,
                acqTime: acqTime
            });
        };

        var all = data.allItemInfo || [];
        for (var i = 0; i < all.length; i++) {
            push(all[i]);
        }
        if (data.commAlarmLevel > 0) {
            push({ column: 'commStatusName', columnName: '通信状态', value: data.commStatusName, alarmLevel: data.commAlarmLevel });
        }
        if (data.runAlarmLevel > 0) {
            push({ column: 'runStatusName', columnName: '运行状态', value: data.runStatusName, alarmLevel: data.runAlarmLevel });
        }
        if (data.resultAlarmLevel > 0) {
            push({ column: 'resultName', columnName: '工况', value: data.resultName, alarmLevel: data.resultAlarmLevel });
        }
        return result;
    },

    // ============ 新增 / 更新 ============
    upsert: function (alarm) {
        var old = this.alarms[alarm.key];

        // 已确认，且级别没升高（数值没变小）→ 只更新显示
        if (this.ackKeys[alarm.key] && old && alarm.alarmLevel >= old.alarmLevel) {
            old.value = alarm.value;
            old.acqTime = alarm.acqTime;
            this.updateNode(old);
            return;
        }

        // 已确认但级别升高 → 摘掉确认标记，走正常提醒流程
        if (this.ackKeys[alarm.key]) {
            delete this.ackKeys[alarm.key];
        }

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

    // ============ 恢复检测 ============
    checkRecover: function (data) {
        var deviceId = data.deviceId;
        var levelMap = {};
        var all = data.allItemInfo || [];
        for (var i = 0; i < all.length; i++) {
            levelMap[all[i].column] = all[i].alarmLevel || 0;
        }
        levelMap['commStatusName'] = (data.commAlarmLevel === undefined) ? -1 : (data.commAlarmLevel || 0);
        levelMap['runStatusName'] = (data.runAlarmLevel === undefined) ? -1 : (data.runAlarmLevel || 0);
        levelMap['resultName'] = (data.resultAlarmLevel === undefined) ? -1 : (data.resultAlarmLevel || 0);

        var toRemove = [];
        for (var key in this.alarms) {
            if (!this.alarms.hasOwnProperty(key)) continue;
            if (this.alarms[key].deviceId != deviceId) continue;
            if (levelMap[this.alarms[key].column] === 0) {
                toRemove.push(this.alarms[key].column);
            }
        }
        for (var j = 0; j < toRemove.length; j++) {
            this.recover(deviceId, toRemove[j]);
        }
    },

    recover: function (deviceId, column) {
        var key = deviceId + '|' + column;
        if (!this.alarms[key]) return;
        delete this.alarms[key];
        delete this.ackKeys[key];
        this.removeNode(key);
    },

    // ============ 弹窗控制 ============
    ensureWindow: function () {
        if (!this.win) {
            this.win = Ext.create('AP.view.alert.AlarmWindow');
        }
        return this.win;
    },

    ensureWindowExpanded: function () {
        var win = this.ensureWindow();
        if (!win.isVisible()) win.show();
        if (win.collapsed) win.expand();
        win.startBlink();
    },

    // ============ 树节点操作（委托给弹窗） ============
    addNode: function (alarm) {
        this.ensureWindow().addAlarmNode(alarm);
    },
    updateNode: function (alarm) {
        if (this.win) this.win.updateAlarmNode(alarm);
    },
    removeNode: function (key) {
        if (this.win) this.win.removeAlarmNode(key);
    },

    // ============ 确认 ============
    ack: function (keys, callback) {
    	var self = this;
        var validKeys = [];
        Ext.Array.each(keys, function (key) {
            if (self.alarms[key]) validKeys.push(key);
        });
        if (validKeys.length === 0) {
            if (callback) callback(true);   // 全都被恢复了，视为成功
            return;
        }

        var withId = [], withoutId = [];
        Ext.Array.each(validKeys, function (key) {
            var a = self.alarms[key];
            if (a.alertId) withId.push({ key: key, alertId: a.alertId });
            else withoutId.push(key);
        });

        var finish = function (ok) {
            if (ok) {
                Ext.Array.each(withId, function (x) { self.ackKeys[x.key] = true; });
                Ext.Array.each(withoutId, function (k) { self.ackKeys[k] = true; });
            }
            if (callback) callback(ok);
        };

        if (withId.length === 0) {
        	if (!this._localAckWarned) {
                this._localAckWarned = true;
                Ext.toast({
                    html: '当前报警记录未同步到服务端，本次仅本地确认，刷新页面后可能重新出现',
                    title: '提示',
                    width: 300,
                    align: 't'
                });
            }
        	finish(true);   // 降级：本地确认
            return;
        }

        Ext.Ajax.request({
        	url: context + '/alarmQueryController/batchAck',
            method: 'POST',
            jsonData: { alertIds: Ext.Array.pluck(withId, 'alertId') },
            success: function (resp) {
                var r;
                try { r = Ext.JSON.decode(resp.responseText); } catch (e) { r = { success: false }; }
                if (r.success) {
                    finish(true);
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
    }
});