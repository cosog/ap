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
        // 如果明确有 alarmType，按规则判断
        var type = parseInt(alarm.alarmType);
        if (!isNaN(type)) {
        	//报警类型 2(数据量/数值) 或 5(扩展数据量)
            if (type == 2 || type == 5) {
                //用 报警信息(高报/低报) 区分，避免覆盖
                var info = alarm.alarminfo || '';
                return alarm.deviceId + '|' + alarm.column + '|' + info;
            }
        }
        
        // 其他类型（3通信、4工况、6运行状态、0开关量、1枚举量等），必须带 value 区分（如上线/离线）
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
        	        alarmType: 3 // 明确指定为 3
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
            
            // ★ 核心修改：使用统一的 getAlarmKey 生成 Key
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
        
        // ★ 针对顶层独立字段，手动赋予对应的报警类型
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

        if (this.ackKeys[alarm.key] && old && alarm.alarmLevel >= old.alarmLevel) {
            old.value = alarm.value;
            old.acqTime = alarm.acqTime;
            old.alertId = alarm.alertId || old.alertId;
            this.updateNode(old);
            return; 
        }
        
        if (this.ackKeys[alarm.key]) {
            delete this.ackKeys[alarm.key];
            this.updateNode(old); 
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
        	self.unmaskWindow();
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

        
        this.maskWindow();
        
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
 // 新增两个辅助方法
    maskWindow: function() {
        if (this.win) {
            var maskMsg = '正在确认报警...';
            if (typeof loginUserLanguageResource !== 'undefined' && loginUserLanguageResource.loadingData) {
                maskMsg = loginUserLanguageResource.loadingData;
            }
            // 对整个窗口加遮罩，防止用户重复点击
            this.win.getEl().mask(maskMsg);
        }
    },

    unmaskWindow: function() {
        if (this.win && this.win.getEl()) {
            this.win.getEl().unmask();
        }
    },
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
                var r; try { r = Ext.JSON.decode(resp.responseText); } catch (e) { return; }
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
                        self.ensureWindowExpanded();
                        
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
    },
    
 // 失去权限时清理界面
    clearAllForPermissionLoss: function() {
        this.alarms = {};
        this.ackKeys = {};
        this._localAckWarned = false;
        
        // 停止声音
        if (AP.alert && AP.alert.AlarmSoundManager) {
            AP.alert.AlarmSoundManager.stop();
        }
        
        // 关闭并销毁窗口
        if (this.win) {
            try {
                this.win.stopBlink();
                this.win.close();
            } catch (e) {}
            this.win = null; // 置空，下次有权限时重新创建
        }
        console.log('【报警权限】失去权限，已清理所有报警弹窗和声音');
    }
});