Ext.define('AP.alert.AlarmManager', {
    singleton: true,

    alarms: {},
    ackKeys: {},
    win: null,
    _localAckWarned: false,
    isMuted: false,

    levelStyle: {
        100: { text: '一级', color: '#dc2828' },
        200: { text: '二级', color: '#f09614' },
        300: { text: '三级', color: '#fae600' }
    },

    getAlarmKey: function (alarm) {
        var type = parseInt(alarm.alarmType);
        if (!isNaN(type)) {
            if (type == 2 || type == 5) {
                var info = alarm.alarminfo || '';
                return alarm.deviceId + '|' + alarm.column + '|' + info;
            }
        }
        var val = (alarm.value !== undefined && alarm.value !== null) ? alarm.value : '';
        return alarm.deviceId + '|' + alarm.column + '|' + val;
    },

    handle: function (data) {
        if (typeof alarmAlert !== 'undefined' && !alarmAlert) return;
        if (!data || !data.functionCode) return;
        var fc = data.functionCode.toUpperCase();

        if (!AP.alert.AlarmSoundManager._loaded) {
            AP.alert.AlarmSoundManager.loadConfig();
        }

        if (fc === 'DEVICEREALTIMEMONITORINGDATA') {
            var list = this.extract(data);
            for (var i = 0; i < list.length; i++) this.upsert(list[i]);
            this.checkRecover(data);
        } else if (fc === 'DEVICEREALTIMEMONITORINGSTATUSDATA') {
            var commAlarm = {
                deviceId: data.deviceId,
                column: 'commStatusName',
                value: data.commStatusName,
                alarmType: 3
            };
            var key = this.getAlarmKey(commAlarm);

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
                    acqTime: data.acqTime,
                    alarmType: 3,
                    suppressStatus: data.suppressStatus || 0, 
                    suppressStatus: 0,
                    suppressEndTime: '',
                    triggerAlarm: data.triggerAlarm !== false,
                    pendingDelay: data.pendingDelay === true
                });
            } else {
                var toRemoveKeys = [];
                for (var k in this.alarms) {
                    if (this.alarms.hasOwnProperty(k) &&
                        this.alarms[k].deviceId == data.deviceId &&
                        this.alarms[k].column === 'commStatusName') {
                        toRemoveKeys.push(k);
                    }
                }
                for (var j = 0; j < toRemoveKeys.length; j++) {
                    this.recover(toRemoveKeys[j]);
                }
            }
        }
    },

    extract: function (data) {
        var result = [];
        var deviceId = data.deviceId, deviceName = data.deviceName, orgId = data.orgId, acqTime = data.acqTime;
        var map = {};
        var push = function (item) {
            if (!item.column || !item.alarmLevel || item.alarmLevel <= 0) return;

            var key = AP.alert.AlarmManager.getAlarmKey({
                deviceId: deviceId,
                column: item.column,
                value: item.value,
                alarmType: item.alarmType,
                alarminfo: item.alarminfo
            });

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
                acqTime: acqTime,
                alarmType: item.alarmType || 0,
                alarminfo: item.alarminfo || '',
                suppressStatus: item.suppressStatus || 0,
                suppressEndTime: item.suppressEndTime || '',
                triggerAlarm: item.triggerAlarm !== false,
                pendingDelay: item.pendingDelay === true
            });
        };

        var all = data.allItemInfo || [];
        for (var i = 0; i < all.length; i++) push(all[i]);

        if (data.commAlarmLevel > 0) {
            push({ column: 'commStatusName', columnName: '通信状态', value: data.commStatusName, alarmLevel: data.commAlarmLevel, alarmType: 3 });
        }
        if (data.runAlarmLevel > 0) {
            push({ column: 'runStatusName', columnName: '运行状态', value: data.runStatusName, alarmLevel: data.runAlarmLevel, alarmType: 6 });
        }
        if (data.resultAlarmLevel > 0) {
            push({ column: 'resultName', columnName: '工况', value: data.resultName, alarmLevel: data.resultAlarmLevel, alarmType: 4 });
        }

        return result;
    },

    _applySuppressStatus: function (old, alarm) {
        var pushSupp = alarm.suppressStatus || 0;
        var pushEndTime = alarm.suppressEndTime || '';
        
        if (pushSupp === 2) {
            old.suppressStatus = 2;
            old.suppressEndTime = pushEndTime;
            old.suppressReason = alarm.suppressReason || old.suppressReason || '';
            old.suppressUser = alarm.suppressUser || old.suppressUser || '';
            old.suppressIp = alarm.suppressIp || old.suppressIp || '';
            old.suppressTime = alarm.suppressTime || old.suppressTime || '';
            return;
        }

        if (old.suppressStatus === 2) {
            if (old.suppressEndTime) {
                var now = new Date().getTime();
                var localEnd = new Date(old.suppressEndTime.replace(/-/g, '/')).getTime();
                if (now >= localEnd) {
                    old.suppressStatus = 0;
                    old.suppressEndTime = '';
                }
            } else {
                old.suppressStatus = 0;
            }
        } else {
            old.suppressStatus = 0;
        }
    },

    upsert: function (alarm) {
        // ★ delay 期间（尚未达到持续检测时间）：完全忽略，不弹窗、不响铃
        if (alarm.pendingDelay) {
            return;
        }

        var old = this.alarms[alarm.key];

        if (this.ackKeys[alarm.key] && old && alarm.alarmLevel >= old.alarmLevel) {
            old.value = alarm.value;
            old.alarminfo = alarm.alarminfo || '';
            old.acqTime = alarm.acqTime;
            old.alertId = alarm.alertId || old.alertId;
            old.alarmLevel = alarm.alarmLevel;
            old.triggerAlarm = alarm.triggerAlarm;              // ★ 同步
            old.ackStatus = 1;
            this._applySuppressStatus(old, alarm);
            this.updateNode(old);
            return;
        }

        var needReack = !!(this.ackKeys[alarm.key] && old && alarm.alarmLevel < old.alarmLevel);
        if (needReack) {
            delete this.ackKeys[alarm.key];
        }

        if (!old) {
            alarm.ackStatus = (alarm.ackStatus == 1) ? 1 : 0;
            alarm.suppressStatus = alarm.suppressStatus || 0;
            alarm.suppressEndTime = alarm.suppressEndTime || '';
            if (alarm.ackStatus === 1) this.ackKeys[alarm.key] = true;
            this.alarms[alarm.key] = alarm;
            this.addNode(alarm);

            // ★ 只有真正触发才弹窗 + 响铃
            if (alarm.triggerAlarm && alarm.suppressStatus != 2 && alarm.suppressStatus != 1) {
                this.ensureWindowExpanded(true);
                if (!this.isMuted) {
                    AP.alert.AlarmSoundManager.play(alarm.alarmLevel);
                }
            }
        } else {
            var levelUp = alarm.alarmLevel < old.alarmLevel;
            var isAutoRecovered = (old.suppressStatus === 1 && old.ackStatus !== 1);
            if (isAutoRecovered) {
                old.suppressStatus = 0;
                old.suppressEndTime = '';
            }

            var prevTrigger = old.triggerAlarm;                  // ★ 记录旧值

            var pushedAckStatus = (alarm.ackStatus === 1) ? 1 : (alarm.ackStatus === 0 ? 0 : null);
            var wasAckedByAlertId = (old.alertId && !alarm.alertId);
            var ackedByPush = (pushedAckStatus === 1);

            old.value = alarm.value;
            old.alarminfo = alarm.alarminfo || '';
            old.acqTime = alarm.acqTime;
            old.alarmLevel = alarm.alarmLevel;
            old.alertId = alarm.alertId || old.alertId;
            old.triggerAlarm = alarm.triggerAlarm;              // ★ 同步

            if (needReack) {
                old.ackStatus = 0;
            } else if ((ackedByPush || wasAckedByAlertId) && old.ackStatus !== 1) {
                old.ackStatus = 1;
                this.ackKeys[alarm.key] = true;
            } else {
                old.ackStatus = this.ackKeys[alarm.key] ? 1 : (old.ackStatus == 1 ? 1 : 0);
            }

            this._applySuppressStatus(old, alarm);
            this.updateNode(old);

            // ★ 触发条件：升级 / 自动消警复活 / 从 false 变为 true（跨重触发点）
            var shouldAlert = (levelUp || isAutoRecovered || (prevTrigger === false && alarm.triggerAlarm === true));
            if (shouldAlert && alarm.triggerAlarm && old.suppressStatus != 2 && old.suppressStatus != 1) {
                this.ensureWindowExpanded(true);
                if (!this.isMuted) {
                    AP.alert.AlarmSoundManager.play(alarm.alarmLevel);
                }
            }
        }
    },

    checkRecover: function (data) {
        var deviceId = data.deviceId;
        var toRemoveKeys = [];
        var currentLevelMap = {};
        var all = data.allItemInfo || [];
        for (var i = 0; i < all.length; i++) {
            var item = all[i];
            if (item.column) {
                currentLevelMap[item.column] = item.alarmLevel || 0;
            }
        }
        if (currentLevelMap['commStatusName'] === undefined && data.commAlarmLevel !== undefined) {
            currentLevelMap['commStatusName'] = data.commAlarmLevel;
        }
        if (currentLevelMap['runStatusName'] === undefined && data.runAlarmLevel !== undefined) {
            currentLevelMap['runStatusName'] = data.runAlarmLevel;
        }
        if (currentLevelMap['resultName'] === undefined && data.resultAlarmLevel !== undefined) {
            currentLevelMap['resultName'] = data.resultAlarmLevel;
        }
        
        for (var key in this.alarms) {
            if (!this.alarms.hasOwnProperty(key)) continue;
            var alarm = this.alarms[key];
            if (alarm.deviceId != deviceId) continue;
            var col = alarm.column;
            var level = currentLevelMap[col];
            if (level === 0) {
                if (alarm.ackStatus !== 1 && alarm.suppressStatus !== 1) {
                    // ★ 未确认且未标记自动消警，则标记为自动消警
                    alarm.suppressStatus = 1;
                    this.updateNode(alarm);
                } else if (alarm.ackStatus === 1 && alarm.suppressStatus !== 2 && alarm.suppressStatus !== 1) {
                    // 已确认但未人工消警，正常物理恢复，删除节点
                    toRemoveKeys.push(key);
                }
            }
        }
        for (var j = 0; j < toRemoveKeys.length; j++) {
            this.recover(toRemoveKeys[j]);
        }
        // 刷新声音，因为如果有自动消警了，应该停止对应音效
        this._refreshSoundAfterAck();
    },

    recover: function (key) {
        if (!this.alarms[key]) return;
        delete this.alarms[key];
        delete this.ackKeys[key];
        this.removeNode(key);
    },

    ensureWindow: function () {
        if (!this.win) this.win = Ext.create('AP.view.alert.AlarmWindow');
        return this.win;
    },
    ensureWindowExpanded: function (focusPending) {
        var win = this.ensureWindow();
        if (!win.isVisible()) win.show();
        if (win.collapsed) win.expand();
        if (focusPending && win.focusPendingTab) win.focusPendingTab();
        win.startBlink();
    },
    addNode: function (a) { this.ensureWindow().syncAlarmNode(a); },
    updateNode: function (a) { if (this.win) this.win.syncAlarmNode(a); },
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

        var finish = function (ok, okIds) {
            self.unmaskWindow();
            if (ok) {
                Ext.Array.each(withId, function (x) {
                    if (!okIds || okIds[x.alertId]) {
                        self.ackKeys[x.key] = true;
                        if (self.alarms[x.key]) {
                            self.alarms[x.key].ackStatus = 1;
                        }
                    }
                });
                Ext.Array.each(withoutId, function (k) {
                    self.ackKeys[k] = true;
                    if (self.alarms[k]) self.alarms[k].ackStatus = 1;
                });
            }
            if (callback) callback(ok);
        };

        if (withId.length === 0) {
            if (!this._localAckWarned) {
                this._localAckWarned = true;
                Ext.toast({ html: '当前报警未同步到服务端，本次仅本地确认', title: '提示', width: 300, align: 't' });
            }
            finish(true, null);
            return;
        }

        this.maskWindow();
        Ext.Ajax.request({
            url: context + '/alarmQueryController/batchAck',
            method: 'POST',
            jsonData: { alertIds: Ext.Array.pluck(withId, 'alertId') },
            success: function (resp) {
                var r;
                try { r = Ext.JSON.decode(resp.responseText); } catch (e) { r = { success: false }; }
                if (r.success) {
                    var okIds = {};
                    var data = r.data || {};
                    Ext.Array.each(data.successIds || [], function (id) { okIds[id] = true; });
                    Ext.Array.each(data.alreadyAckedIds || [], function (id) { okIds[id] = true; });

                    finish(true, okIds);

                    if (data.failedIds && data.failedIds.length > 0) {
                        Ext.toast({ html: data.failedIds.length + ' 条确认失败', title: '提示', width: 300, align: 't' });
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

    maskWindow: function () {
        if (this.win) {
            var maskMsg = '正在确认报警...';
            if (typeof loginUserLanguageResource !== 'undefined' && loginUserLanguageResource.loadingData) {
                maskMsg = loginUserLanguageResource.loadingData;
            }
            this.win.getEl().mask(maskMsg);
        }
    },

    unmaskWindow: function () {
        if (this.win && this.win.getEl()) {
            this.win.getEl().unmask();
        }
    },

    onRemoteAck: function (alertIds, ackUser, ackIp, ackTime) {
        if (!alertIds || alertIds.length === 0) return;
        var self = this;
        var ackSet = {};
        Ext.Array.each(alertIds, function (id) { ackSet[id] = true; });

        Ext.Object.each(this.alarms, function (key, alarm) {
            if (alarm.alertId && ackSet[alarm.alertId]) {
                self.ackKeys[key] = true;
                alarm.ackStatus = 1;
                alarm.ackUser = ackUser;
                alarm.ackIp = ackIp;
                alarm.ackTime = ackTime;
                if (self.win) self.win.syncAlarmNode(alarm);
            }
        });

        if (this.win) {
            this.win.updateToolbar();
        }
        this._refreshSoundAfterAck();
    },

    suppressAlarm: function (alertIds, reason, durationHours, callback) {
        var self = this;
        if (!alertIds || alertIds.length === 0) { if (callback) callback(false); return; }
        this.maskWindow();
        Ext.Ajax.request({
            url: context + '/alarmQueryController/suppressAlarm',
            method: 'POST',
            jsonData: { alertIds: alertIds, reason: reason, durationHours: durationHours },
            success: function (resp) {
                self.unmaskWindow();
                var r;
                try { r = Ext.JSON.decode(resp.responseText); } catch (e) { r = { success: false }; }
                if (r.success) {
                    callback(true, r.data || {});
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, r.message || '消警失败');
                    callback(false);
                }
            },
            failure: function () {
                self.unmaskWindow();
                Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.ajaxError);
                callback(false);
            }
        });
    },

    onRemoteSuppress: function (alertIds, suppressReason, suppressUser, suppressIp, suppressTime, suppressEndTime) {
        if (!alertIds || alertIds.length === 0) return;
        var self = this;
        var idSet = {};
        Ext.Array.each(alertIds, function (id) { idSet[id] = true; });

        Ext.Object.each(this.alarms, function (key, alarm) {
            if (alarm.alertId && idSet[alarm.alertId]) {
                alarm.suppressStatus = 2;
                alarm.suppressReason = suppressReason || '';
                alarm.suppressUser = suppressUser || '';
                alarm.suppressIp = suppressIp || '';
                alarm.suppressTime = suppressTime || '';
                alarm.suppressEndTime = suppressEndTime || '';
                if (self.win) self.win.syncAlarmNode(alarm);
            }
        });

        if (this.win) this.win.updateToolbar();
        this._refreshSoundAfterAck();
    },

    reset: function () {
        this.alarms = {};
        this.ackKeys = {};
        this._localAckWarned = false;
        if (this.win) {
            try { this.win.stopBlink(); this.win.close(); } catch (e) {}
            this.win = null;
        }
    },

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
                var r;
                try { r = Ext.JSON.decode(resp.responseText); } catch (e) { return; }
                if (!r || !r.success || !r.data || r.data.length === 0) return;

                var hasNewUnack = false;
                Ext.Array.each(r.data, function (item) {
                    var key = self.getAlarmKey(item);
                    var isSuppressed = (item.suppressStatus == 2 || item.suppressStatus == 1);
                    if (isSuppressed) {
                        // 消警状态不加入 ackKeys，不触发展开和响铃
                    } else if (item.ackStatus == 1) {
                        self.ackKeys[key] = true;
                    } else {
                        hasNewUnack = true;
                    }
                    
                    self.upsertSilent({
                        key: key, alertId: item.alertId, deviceId: item.deviceId, deviceName: item.deviceName,
                        orgId: item.orgId, column: item.column, columnName: item.columnName,
                        value: item.value, alarmLevel: item.alarmLevel, acqTime: item.acqTime,
                        ackStatus: item.ackStatus, alarmType: item.alarmType, alarminfo: item.alarminfo,
                        suppressStatus: item.suppressStatus || 0, suppressReason: item.suppressReason || '',
                        suppressUser: item.suppressUser || '', suppressIp: item.suppressIp || '',
                        suppressTime: item.suppressTime || '', suppressEndTime: item.suppressEndTime || '',
                        triggerAlarm: item.triggerAlarm !== false,
                        pendingDelay: false
                    });
                });

                if (self.win && self.win.getTotalLeafCount() > 0) {
                    if (hasNewUnack) {
                        self.ensureWindowExpanded(true);
                        if (!AP.alert.AlarmSoundManager.unlocked) {
                            var tip = self.win.down('#muteTip');
                            if (tip) tip.show();
                        }
                        var maxLevel = self._getHighestUnackLevel();
                        if (maxLevel && !self.isMuted) AP.alert.AlarmSoundManager.play(maxLevel);
                    } else {
                        var win = self.ensureWindow();
                        if (!win.isVisible()) win.show();
                        if (!win.collapsed) win.collapse();
                        win.updateTitle();
                    }
                }
            }
        });
    },

    upsertSilent: function (alarm) {
        if (!this.alarms[alarm.key]) {
            alarm.ackStatus = (alarm.ackStatus == 1) ? 1 : 0;
            alarm.suppressStatus = (alarm.suppressStatus == 2 || alarm.suppressStatus == 1) ? alarm.suppressStatus : 0;
            this.alarms[alarm.key] = alarm;
            this.addNode(alarm);
        } else {
            var old = this.alarms[alarm.key];
            if (alarm.ackStatus == 1) {
                old.ackStatus = 1;
                this.ackKeys[alarm.key] = true;
            }
            this._applySuppressStatus(old, alarm);
            old.value = alarm.value;
            old.acqTime = alarm.acqTime;
            old.alarminfo = alarm.alarminfo || '';
            if (alarm.alertId) old.alertId = alarm.alertId;
            this.updateNode(old);
        }
    },

    _getHighestUnackLevel: function () {
        var min = null;
        Ext.Object.each(this.alarms, function (k, a) {
            if (a.suppressStatus == 2 || a.suppressStatus == 1) return;
            if (a.ackStatus == 1) return;
            if (!a.triggerAlarm) return;      // ★ 只统计活跃报警
            if (min === null || a.alarmLevel < min) min = a.alarmLevel;
        });
        return min;
    },

    _refreshSoundAfterAck: function () {
        var self = this;
        var unackKeys = [];
        Ext.Object.each(this.alarms, function (k, a) {
            if (a.suppressStatus == 2 || a.suppressStatus == 1) return;
            if (a.ackStatus != 1 && a.triggerAlarm) unackKeys.push(k);
        });

        if (unackKeys.length === 0) {
            AP.alert.AlarmSoundManager.stop();
            if (this.win) {
                this.win.stopBlink();
                var ackCount = this.win.countLeaf(this.win.storeAcked);
                if (ackCount > 0 && this.win.tabPanel) {
                    this.win.tabPanel.setActiveItem(this.win.treeAcked);
                } else {
                    this.win.collapse();
                }
                var tip = this.win.down('#muteTip');
                if (tip) tip.hide();
            }
            return;
        }
        
        if (this.isMuted) {
            return;
        }

        var targetLevel = null;
        Ext.Array.each(unackKeys, function (k) {
            var lv = self.alarms[k].alarmLevel;
            if (targetLevel === null || lv < targetLevel) targetLevel = lv;
        });

        var playing = AP.alert.AlarmSoundManager.currentLevel;
        if (playing === targetLevel) return;
        AP.alert.AlarmSoundManager.stop();
        AP.alert.AlarmSoundManager.play(targetLevel);
    },

    clearAllForPermissionLoss: function () {
        this.alarms = {};
        this.ackKeys = {};
        this._localAckWarned = false;
        if (AP.alert && AP.alert.AlarmSoundManager) {
            AP.alert.AlarmSoundManager.stop();
        }
        if (this.win) {
            try { this.win.stopBlink(); this.win.close(); } catch (e) {}
            this.win = null;
        }
        console.log('【报警权限】失去权限，已清理所有报警弹窗和声音');
    }
});