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
                    alarmType: 3
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
            if (!item.column || !item.triggerAlarm || !item.alarmLevel || item.alarmLevel <= 0) return;

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
                alarminfo: item.alarminfo || ''
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

    upsert: function (alarm) {
        var old = this.alarms[alarm.key];

        // ① 已确认后再次触发，且不升级（同级或降级）→ 保留已确认
        if (this.ackKeys[alarm.key] && old && alarm.alarmLevel >= old.alarmLevel) {
            old.value = alarm.value;
            old.alarminfo = alarm.alarminfo || '';
            old.acqTime = alarm.acqTime;
            old.alertId = alarm.alertId || old.alertId;
            old.alarmLevel = alarm.alarmLevel;
            old.ackStatus = 1;
            this.updateNode(old);
            return;
        }

        // ② 已确认后再次触发，且升级 → 迁回待确认（标记 needReack，统一在 else 处理）
        var needReack = !!(this.ackKeys[alarm.key] && old && alarm.alarmLevel < old.alarmLevel);
        if (needReack) {
            delete this.ackKeys[alarm.key];
        }

        if (!old) {
            // 新建
            alarm.ackStatus = (alarm.ackStatus == 1) ? 1 : 0;
            if (alarm.ackStatus === 1) this.ackKeys[alarm.key] = true;
            this.alarms[alarm.key] = alarm;
            this.addNode(alarm);
            // ★ 新报警切到待确认 Tab
            this.ensureWindowExpanded(true);
            AP.alert.AlarmSoundManager.play(alarm.alarmLevel);
        } else {
            var levelUp = alarm.alarmLevel < old.alarmLevel;
            var wasAckedByAlertId = (old.alertId && !alarm.alertId);

            old.value = alarm.value;
            old.alarminfo = alarm.alarminfo || '';
            old.acqTime = alarm.acqTime;
            old.alarmLevel = alarm.alarmLevel;
            old.alertId = alarm.alertId || old.alertId;

            if (needReack) {
                old.ackStatus = 0;
            } else if (wasAckedByAlertId && old.ackStatus !== 1) {
                old.ackStatus = 1;
                this.ackKeys[alarm.key] = true;
            } else {
                old.ackStatus = this.ackKeys[alarm.key] ? 1 : (old.ackStatus == 1 ? 1 : 0);
            }

            this.updateNode(old);

            if (levelUp) {
                // ★ 升级切到待确认 Tab（含 needReack 场景）
                this.ensureWindowExpanded(true);
                AP.alert.AlarmSoundManager.play(alarm.alarmLevel);
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
                toRemoveKeys.push(key);
            }
        }

        for (var j = 0; j < toRemoveKeys.length; j++) {
            this.recover(toRemoveKeys[j]);
        }
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
    /**
     * 展开窗口 + 闪烁
     * @param {Boolean} focusPending 是否切到「待确认」Tab（新报警/升级/首次加载有未确认时传 true）
     */
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
                            self.alarms[x.key].alertId = null;
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

                    if (item.ackStatus == 1) {
                        self.ackKeys[key] = true;
                    } else {
                        hasNewUnack = true;
                    }

                    self.upsertSilent({
                        key: key,
                        alertId: item.alertId,
                        deviceId: item.deviceId,
                        deviceName: item.deviceName,
                        orgId: item.orgId,
                        column: item.column,
                        columnName: item.columnName,
                        value: item.value,
                        alarmLevel: item.alarmLevel,
                        acqTime: item.acqTime,
                        ackStatus: item.ackStatus,
                        alarmType: item.alarmType,
                        alarminfo: item.alarminfo
                    });
                });

                if (self.win && self.win.getTotalLeafCount() > 0) {
                    if (hasNewUnack) {
                        // ★ 首次加载有未确认，切到待确认 Tab
                        self.ensureWindowExpanded(true);

                        if (!AP.alert.AlarmSoundManager.unlocked) {
                            var tip = self.win.down('#muteTip');
                            if (tip) tip.show();
                        }

                        var maxLevel = self._getHighestUnackLevel();
                        if (maxLevel) {
                            AP.alert.AlarmSoundManager.play(maxLevel);
                        }
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
            this.alarms[alarm.key] = alarm;
            this.addNode(alarm);
        } else {
            var old = this.alarms[alarm.key];
            if (alarm.ackStatus == 1) {
                old.ackStatus = 1;
                this.ackKeys[alarm.key] = true;
            }
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
            if (a.ackStatus == 1) return;
            if (min === null || a.alarmLevel < min) min = a.alarmLevel;
        });
        return min;
    },

    _refreshSoundAfterAck: function () {
        var self = this;

        var unackKeys = [];
        Ext.Object.each(this.alarms, function (k, a) {
            if (a.ackStatus != 1) unackKeys.push(k);
        });

        if (unackKeys.length === 0) {
            AP.alert.AlarmSoundManager.stop();
            if (this.win) {
                this.win.stopBlink();

                var ackCount = this.win.countLeaf(this.win.storeAcked);
                if (ackCount > 0 && this.win.tabPanel) {
                    this.win.tabPanel.setActiveItem(this.win.treeAcked);
                }

                this.win.collapse();

                var tip = this.win.down('#muteTip');
                if (tip) tip.hide();
            }
            return;
        }

        var targetLevel = null;
        Ext.Array.each(unackKeys, function (k) {
            var lv = self.alarms[k].alarmLevel;
            if (targetLevel === null || lv < targetLevel) targetLevel = lv;
        });

        var playing = AP.alert.AlarmSoundManager.currentLevel;
        if (playing === targetLevel) {
            return;
        }
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
            try {
                this.win.stopBlink();
                this.win.close();
            } catch (e) {}
            this.win = null;
        }
        console.log('【报警权限】失去权限，已清理所有报警弹窗和声音');
    }
});