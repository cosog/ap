/**
 * 报警业务及声音管理
 */
var AlarmManager = {
    alarms: {},
    ackKeys: {},
    isMuted: false,
    win: null,
    suppListWin: null,
    blinkTask: null,
    blinkOn: false,
    _localAckWarned: false,
    showSuppressedTab: false, // false 表示默认隐藏人工消警的 Tab

    levelStyle: {
        100: { text: _loginUserLanguageResource.level1, color: '#dc2828' },
        200: { text: _loginUserLanguageResource.level2, color: '#f09614' },
        300: { text: _loginUserLanguageResource.level3, color: '#fae600' }
    },

    init: function() {
        this.win = mini.get("AlarmWindow");
        this.suppListWin = mini.get("suppressedListWindow");
        if (!this.win) return;
        
//        var headerEl = this.win.getHeaderEl();
//        headerEl.addEventListener('click', function() {
//            if (AlarmManager.win.getHeight() > 100) {
//                AlarmManager.win.setHeight(40); // 缩成小条
//                AlarmManager.win.setWidth(300); // 变窄
//            } else {
//                AlarmManager.win.setHeight(540); // 展开
//                AlarmManager.win.setWidth(900);
//                AlarmManager.alignToBottomRight(900, 540);
//            }
//        });
        this.loadPendingAlarms();
        
        // 提前加载声音配置
        if (!AlarmSoundManager._loaded) {
            AlarmSoundManager.loadConfig();
        }
    },

    getAlarmKey: function (alarm) {
        var type = parseInt(alarm.alarmType);
        if (!isNaN(type) && (type == 2 || type == 5)) {
            var info = alarm.alarminfo || '';
            return alarm.deviceId + '|' + alarm.column + '|' + info;
        }
        var val = (alarm.value !== undefined && alarm.value !== null) ? alarm.value : '';
        return alarm.deviceId + '|' + alarm.column + '|' + val;
    },

    handle: function (data) {
    	if (!this.win) this.win = mini.get("AlarmWindow");
        if (!this.win) return; 
        if (typeof alarmAlert !== 'undefined' && !alarmAlert) return;
        if (!data || !data.functionCode) return;
        var fc = data.functionCode.toUpperCase();

        if (!AlarmSoundManager._loaded) AlarmSoundManager.loadConfig();

        if (fc === 'DEVICEREALTIMEMONITORINGDATA') {
            var list = this.extract(data);
            for (var i = 0; i < list.length; i++) this.upsert(list[i]);
            this.checkRecover(data);
        } else if (fc === 'DEVICEREALTIMEMONITORINGSTATUSDATA') {
            var commAlarm = { deviceId: data.deviceId, column: 'commStatusName', value: data.commStatusName, alarmType: 3 };
            var key = this.getAlarmKey(commAlarm);

            if (data.commAlarmLevel === undefined) return;
            if (data.commAlarmLevel > 0) {
                this.upsert({
                    key: key, alertId: null, deviceId: data.deviceId, deviceName: data.deviceName,
                    orgId: data.orgId, column: 'commStatusName', columnName: _loginUserLanguageResource.commStatus,
                    value: data.commStatusName, alarmLevel: data.commAlarmLevel, acqTime: data.acqTime,
                    alarmType: 3, suppressStatus: data.suppressStatus || 0, suppressEndTime: data.suppressEndTime || '',
                    triggerAlarm: data.triggerAlarm !== false, pendingDelay: data.pendingDelay === true
                });
            } else {
                var toRemoveKeys = [];
                for (var k in this.alarms) {
                    if (this.alarms.hasOwnProperty(k) && this.alarms[k].deviceId == data.deviceId && this.alarms[k].column === 'commStatusName') {
                        toRemoveKeys.push(k);
                    }
                }
                for (var j = 0; j < toRemoveKeys.length; j++) this.recover(toRemoveKeys[j]);
            }
        }
    },

    extract: function (data) {
        var result = [];
        var deviceId = data.deviceId, deviceName = data.deviceName, orgId = data.orgId, acqTime = data.acqTime;
        var map = {};
        var self = this;
        
        var push = function (item) {
            if (!item.column || !item.alarmLevel || item.alarmLevel <= 0) return;
            var key = self.getAlarmKey({
                deviceId: deviceId, column: item.column, value: item.value,
                alarmType: item.alarmType, alarminfo: item.alarminfo
            });
            if (map[key]) return;
            map[key] = true;

            result.push({
                key: key, alertId: item.alertId || null, deviceId: deviceId, deviceName: deviceName, orgId: orgId,
                column: item.column, columnName: item.columnName || item.column, value: item.value, 
                alarmLevel: item.alarmLevel, acqTime: acqTime, alarmType: item.alarmType || 0, alarminfo: item.alarminfo || '',
                suppressStatus: item.suppressStatus || 0, suppressEndTime: item.suppressEndTime || '',
                triggerAlarm: item.triggerAlarm !== false, pendingDelay: item.pendingDelay === true
            });
        };

        var all = data.allItemInfo || [];
        for (var i = 0; i < all.length; i++) push(all[i]);
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
        var old = this.alarms[alarm.key];

        if (alarm.pendingDelay) {
            if (!old) return;
            old.pendingDelay = true; old.triggerAlarm = false;
            old.value = alarm.value; old.alarminfo = alarm.alarminfo || '';
            old.acqTime = alarm.acqTime; old.alarmLevel = alarm.alarmLevel;
            old.alertId = alarm.alertId || old.alertId;
            this._applySuppressStatus(old, alarm);
            this.updateUI();
            return;
        }

        if (this.ackKeys[alarm.key] && old && alarm.alarmLevel >= old.alarmLevel) {
            old.value = alarm.value; old.alarminfo = alarm.alarminfo || '';
            old.acqTime = alarm.acqTime; old.alertId = alarm.alertId || old.alertId;
            old.alarmLevel = alarm.alarmLevel; old.triggerAlarm = alarm.triggerAlarm;
            old.pendingDelay = false; old.ackStatus = 1;
            this._applySuppressStatus(old, alarm);
            this.updateUI();
            return;
        }

        var needReack = !!(this.ackKeys[alarm.key] && old && alarm.alarmLevel < old.alarmLevel);
        if (needReack) delete this.ackKeys[alarm.key];

        if (!old) {
            alarm.ackStatus = (alarm.ackStatus == 1) ? 1 : 0;
            alarm.suppressStatus = alarm.suppressStatus || 0;
            alarm.suppressEndTime = alarm.suppressEndTime || '';
            alarm.pendingDelay = false;
            if (alarm.ackStatus === 1) this.ackKeys[alarm.key] = true;
            this.alarms[alarm.key] = alarm;
            this.updateUI();

            if (alarm.triggerAlarm && alarm.suppressStatus != 2 && alarm.suppressStatus != 1) {
                this.ensureWindowExpanded();
                if (!this.isMuted) AlarmSoundManager.play(alarm.alarmLevel);
            }
        } else {
            var levelUp = alarm.alarmLevel < old.alarmLevel;
            var isAutoRecovered = (old.suppressStatus === 1 && old.ackStatus !== 1);
            if (isAutoRecovered) { old.suppressStatus = 0; old.suppressEndTime = ''; }

            var prevTrigger = old.triggerAlarm;
            var pushedAckStatus = (alarm.ackStatus === 1) ? 1 : (alarm.ackStatus === 0 ? 0 : null);
            var wasAckedByAlertId = (old.alertId && !alarm.alertId);
            var ackedByPush = (pushedAckStatus === 1);

            old.value = alarm.value; old.alarminfo = alarm.alarminfo || '';
            old.acqTime = alarm.acqTime; old.alarmLevel = alarm.alarmLevel;
            old.alertId = alarm.alertId || old.alertId; old.triggerAlarm = alarm.triggerAlarm;
            old.pendingDelay = false;

            if (needReack) {
                old.ackStatus = 0;
            } else if ((ackedByPush || wasAckedByAlertId) && old.ackStatus !== 1) {
                old.ackStatus = 1; this.ackKeys[alarm.key] = true;
            } else {
                old.ackStatus = this.ackKeys[alarm.key] ? 1 : (old.ackStatus == 1 ? 1 : 0);
            }

            this._applySuppressStatus(old, alarm);
            this.updateUI();

            var shouldAlert = (levelUp || isAutoRecovered || (prevTrigger === false && alarm.triggerAlarm === true));
            if (shouldAlert && alarm.triggerAlarm && old.suppressStatus != 2 && old.suppressStatus != 1) {
                this.ensureWindowExpanded();
                if (!this.isMuted) AlarmSoundManager.play(alarm.alarmLevel);
            }
        }
    },

    checkRecover: function (data) {
        var deviceId = data.deviceId;
        var toRemoveKeys = [];
        var currentLevelMap = {};
        var all = data.allItemInfo || [];
        for (var i = 0; i < all.length; i++) {
            if (all[i].column) currentLevelMap[all[i].column] = all[i].alarmLevel || 0;
        }
        if (currentLevelMap['commStatusName'] === undefined && data.commAlarmLevel !== undefined) currentLevelMap['commStatusName'] = data.commAlarmLevel;
        
        for (var key in this.alarms) {
            if (!this.alarms.hasOwnProperty(key)) continue;
            var alarm = this.alarms[key];
            if (alarm.deviceId != deviceId) continue;
            
            if (currentLevelMap[alarm.column] === 0) {
                if (alarm.ackStatus !== 1 && alarm.suppressStatus !== 1) {
                    alarm.suppressStatus = 1;
                } else if (alarm.ackStatus === 1) {
                    toRemoveKeys.push(key);
                }
            }
        }
        for (var j = 0; j < toRemoveKeys.length; j++) this.recover(toRemoveKeys[j]);
        this._refreshSoundAfterAck();
    },

    recover: function (key) {
        if (!this.alarms[key]) return;
        delete this.alarms[key];
        delete this.ackKeys[key];
        this.updateUI();
    },
    
//    ensureWindowExpanded: function () {
//        if (!this.win) this.win = mini.get("AlarmWindow");
//        
//        // 如果窗口还没显示，则显示并定位
//        if (!this.win.getVisible()) {
//            // 使用 showAtPos 直接定位到右下角
//            // right, bottom 对应窗口的右下角对齐屏幕的右下角
//            this.win.showAtPos("right", "bottom");
//        }
//        this.startBlink();
//    },
    
    ensureWindowExpanded: function () {
        if (!this.win) this.win = mini.get("AlarmWindow");
        
        if (!this.win.getVisible()) {
            // 1. 动态创建一个隐藏的锚点元素（放在屏幕右下角）
            var anchor = document.getElementById("_alarm_anchor_");
            if (!anchor) {
                anchor = document.createElement("div");
                anchor.id = "_alarm_anchor_";
                anchor.style.cssText = "position:fixed; right:5px; bottom:40px; width:1px; height:1px; overflow:hidden;";
                document.body.appendChild(anchor);
            }
            
            // 2. 让窗口显示在锚点处 (向右上方弹出)
            this.win.showAtEl(anchor, {
                xAlign: 'right',   // 窗口的右边对齐锚点的右边
                yAlign: 'bottom'   // 窗口的底部对齐锚点的底部
            });
        }
        this.startBlink();
    },

    updateUI: function () {
    	if (!this.win) this.win = mini.get("AlarmWindow"); 
    	if (!this.win) return;
        
        var pendingMap = {}, ackMap = {}, suppMap = {};
        
        for (var key in this.alarms) {
            var a = this.alarms[key];
            var target = null;
            if (a.ackStatus !== 1) {
                target = pendingMap;
            } else if (a.suppressStatus == 2) {
                target = suppMap;
            } else if (a.suppressStatus == 1) {
                continue; // 已确认且自动消警 -> 直接抹掉
            } else {
                target = ackMap;
            }

            if (!target[a.deviceId]) {
                target[a.deviceId] = { id: a.deviceId, text: a.deviceName || a.deviceId, deviceId: a.deviceId, expanded: true, children: [] };
            }

            var displayText = a.columnName + '：' + a.value;
            if ((a.alarmType == 2 || a.alarmType == 5) && a.alarminfo) {
                displayText = a.columnName + '（' + a.alarminfo + '）：' + a.value;
            }

            target[a.deviceId].children.push({
                id: key, alarmKey: key, text: displayText, alarmLevel: a.alarmLevel,
                deviceId: a.deviceId, deviceName: a.deviceName, column: a.column, columnName: a.columnName,
                value: a.value, alarminfo: a.alarminfo, acqTime: a.acqTime, alertId: a.alertId,
                status: a.ackStatus === 1 ? (a.suppressStatus === 2 ? 'SUPPRESSED' : 'ACK') : 'NEW',
                triggerAlarm: a.triggerAlarm, pendingDelay: a.pendingDelay,
                suppressStatus: a.suppressStatus, suppressReason: a.suppressReason,
                suppressUser: a.suppressUser, suppressTime: a.suppressTime, suppressEndTime: a.suppressEndTime
            });
        }

        var pendingData = [], ackData = [], suppData = [];
        for (var k in pendingMap) pendingData.push(pendingMap[k]);
        for (var k in ackMap) ackData.push(ackMap[k]);
        for (var k in suppMap) suppData.push(suppMap[k]);

        mini.get("pendingGrid").setData(pendingData);
        mini.get("ackGrid").setData(ackData);
        mini.get("suppressedGrid").setData(suppData);
        
        if (this.suppListWin && this.suppListWin.getVisible()) {
            this.openSuppressedList(true); 
        }

        this.updateToolbar(pendingData, ackData, suppData);
        
        if (pendingData.length > 0 || ackData.length > 0) {
            this.ensureWindowExpanded();
        } else {
            if (this.win.getVisible()) this.win.hide();
        }
    },

    updateToolbar: function(pendingData, ackData, suppData) {
    	if (!this.win) this.win = mini.get("AlarmWindow"); 
    	if (!this.win) return;
        var countLeaves = function(data) {
            var c = 0;
            data.forEach(function(d) { if (d.children) c += d.children.length; });
            return c;
        };
        
        var unackCount = countLeaves(pendingData);
        var ackingCount = countLeaves(ackData);
        var suppCount = countLeaves(suppData);
        
        var el;
        el = document.getElementById("pendingSelText");
        if (el) el.innerHTML = loginUserLanguageResource.selected + "：" + this.getCheckedPendingKeys().length;

        el = document.getElementById("ackedSelText");
        if (el) el.innerHTML = loginUserLanguageResource.selected + "：" + this.getCheckedAckedKeys().length;

        el = document.getElementById("ackedCountText");
        if (el) el.innerHTML = loginUserLanguageResource.confirmed + "：" + ackingCount;

        el = document.getElementById("suppressedCountText");
        if (el) el.innerHTML = loginUserLanguageResource.suppressed + "：" + suppCount;

        var tabs = mini.get("alarmTabs");
        tabs.updateTab(tabs.getTab("pending"), { title: loginUserLanguageResource.unconfirmed + " (" + unackCount + ")" });
        tabs.updateTab(tabs.getTab("acked"), { title: loginUserLanguageResource.pendingSuppress + " (" + ackingCount + ")" });
        tabs.updateTab(tabs.getTab("suppressed"), { title: loginUserLanguageResource.manualSuppress + " (" + suppCount + ")" });

        // 隐藏/显示 第三个 Tab
        var suppTabEl = tabs.getTabEl("suppressed");
        if (suppTabEl) {
            if (!this.showSuppressedTab) {
                if (tabs.getActiveTab() && tabs.getActiveTab().name === "suppressed") {
                    tabs.activeTab(unackCount > 0 ? "pending" : "acked");
                }
                suppTabEl.style.display = 'none';
            } else {
                if (suppCount > 0) {
                    suppTabEl.style.display = '';
                } else if (tabs.getActiveTab() && tabs.getActiveTab().name === "suppressed") {
                    tabs.activeTab(unackCount > 0 ? "pending" : "acked");
                    suppTabEl.style.display = 'none';
                } else {
                    suppTabEl.style.display = 'none';
                }
            }
        }

        this.updateTitle();
    },

    startBlink: function () {
        var self = this;
        if (this.blinkTask) return;
        this.blinkOn = false;
        this.blinkTask = setInterval(function () {
            var realUnack = 0;
            for (var k in self.alarms) {
                if (self.alarms[k].ackStatus !== 1 && self.alarms[k].pendingDelay !== true) realUnack++;
            }
            if (realUnack === 0) { self.stopBlink(); return; }
            self.blinkOn = !self.blinkOn;
            self.updateTitle();
        }, 800);
    },

    stopBlink: function () {
        if (this.blinkTask) { clearInterval(this.blinkTask); this.blinkTask = null; }
        this.blinkOn = false;
        this.updateTitle();
    },

    updateTitle: function () {
    	if (!this.win) this.win = mini.get("AlarmWindow");
        if (!this.win) return; 
        var unack = 0, acking = 0;
        for (var k in this.alarms) {
            if (this.alarms[k].ackStatus !== 1) unack++;
            else if (this.alarms[k].suppressStatus == 0) acking++;
        }
        var total = unack + acking;
        
        var realUnack = 0;
        for (var k in this.alarms) {
            if (this.alarms[k].ackStatus !== 1 && this.alarms[k].pendingDelay !== true) realUnack++;
        }

        var text = _loginUserLanguageResource.alarmTitle;
        if (total > 0) text = _loginUserLanguageResource.alarmTitle + ' (' + unack + '/' + total + ')';
        
        var html;
        if (realUnack > 0 && this.blinkOn) {
            html = '<span style="color:#dc2828;">● ' + text + '</span>';
        } else if (realUnack > 0) {
            html = '<span style="color:#dc2828;">○ ' + text + '</span>';
        } else {
            html = text;
        }
        this.win.setTitle(html);
    },

    getCheckedPendingKeys: function () {
        var tree = mini.get('pendingGrid');
        var checkedNodes = tree.getCheckedNodes();
        var keys = [];
        for (var i = 0; i < checkedNodes.length; i++) { if (checkedNodes[i].alarmKey) keys.push(checkedNodes[i].alarmKey); }
        return keys;
    },
    
    getCheckedAckedKeys: function () {
        var rows = mini.get("ackGrid").getSelecteds();
        var keys = [];
        for (var i = 0; i < rows.length; i++) { if (rows[i].alarmKey) keys.push(rows[i].alarmKey); }
        return keys;
    },

    getAllUnackKeys: function () {
        var data = mini.get("pendingGrid").getData();
        var keys = [];
        function traverse(nodes) {
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].children) traverse(nodes[i].children);
                else if (nodes[i].status === 'NEW') keys.push(nodes[i].alarmKey);
            }
        }
        traverse(data);
        return keys;
    },

    getAllAckedKeys: function () {
        var data = mini.get("ackGrid").getData();
        var keys = [];
        function traverse(nodes) {
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].children) traverse(nodes[i].children);
                else if (nodes[i].status === 'ACK') keys.push(nodes[i].alarmKey);
            }
        }
        traverse(data);
        return keys;
    },

    ack: function (keys, callback) {
        var self = this;
        var validKeys = [];
        keys.forEach(function (key) { if (self.alarms[key]) validKeys.push(key); });
        if (validKeys.length === 0) { if (callback) callback(true); return; }

        var withId = [], withoutId = [];
        validKeys.forEach(function (key) {
            var a = self.alarms[key];
            if (a.alertId) withId.push({ key: key, alertId: a.alertId });
            else withoutId.push(key);
        });

        var finish = function (ok, okIds) {
            if (ok) {
                withId.forEach(function (x) {
                    if (!okIds || okIds[x.alertId]) {
                        self.ackKeys[x.key] = true;
                        if (self.alarms[x.key]) self.alarms[x.key].ackStatus = 1;
                    }
                });
                withoutId.forEach(function (k) {
                    self.ackKeys[k] = true;
                    if (self.alarms[k]) self.alarms[k].ackStatus = 1;
                });
            }
            if (callback) callback(ok);
        };

        if (withId.length === 0) {
            if (!this._localAckWarned) {
                this._localAckWarned = true;
                mini.alert(_loginUserLanguageResource.localAckOnly);
            }
            finish(true, null);
            return;
        }

        $.ajax({
            url: context + '/alarmQueryController/batchAck',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ alertIds: withId.map(function(x){ return x.alertId; }) }),
            success: function (resp) {
                var r = typeof resp === 'string' ? JSON.parse(resp) : resp;
                if (r.success) {
                    var okIds = {};
                    var data = r.data || {};
                    (data.successIds || []).forEach(function(id){ okIds[id] = true; });
                    (data.alreadyAckedIds || []).forEach(function(id){ okIds[id] = true; });
                    finish(true, okIds);
                    if (data.failedIds && data.failedIds.length > 0) {
                        mini.alert(data.failedIds.length + ' ' + _loginUserLanguageResource.ackFailedCount);
                    }
                } else {
                    mini.alert(r.message || _loginUserLanguageResource.ackFailed);
                    finish(false);
                }
            },
            error: function () {
                mini.alert(_loginUserLanguageResource.ajaxError);
                finish(false);
            }
        });
    },

    _refreshSoundAfterAck: function () {
        var unackKeys = [];
        for (var k in this.alarms) {
            var a = this.alarms[k];
            if (a.suppressStatus == 2 || a.suppressStatus == 1) continue;
            if (a.pendingDelay === true) continue;
            if (a.ackStatus == 1) continue;
            if (a.alarmLevel <= 0) continue;
            unackKeys.push(k);
        }

        if (unackKeys.length === 0) {
            AlarmSoundManager.stop();
            this.stopBlink();
            return;
        }

        if (this.isMuted) return;

        var targetLevel = null;
        unackKeys.forEach(function (k) {
            var lv = AlarmManager.alarms[k].alarmLevel;
            if (targetLevel === null || lv < targetLevel) targetLevel = lv;
        });

        if (AlarmSoundManager.currentLevel === targetLevel) return;
        AlarmSoundManager.stop();
        AlarmSoundManager.play(targetLevel);
    },

    loadPendingAlarms: function () {
        var self = this;
        if (typeof alarmAlert !== 'undefined' && !alarmAlert) return;
        
        var orgIdCmp = mini.get('leftOrg_Id');
        var orgId = orgIdCmp ? orgIdCmp.getValue() : '';
        
        var deviceType = '';
        if (typeof getDeviceTypeFromTabId === 'function') {
            deviceType = getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
        }

        $.ajax({
            url: context + '/alarmQueryController/getPendingAlarms',
            type: 'GET',
            data: { orgId: orgId, deviceType: deviceType, limit: 200 },
            success: function (resp) {
                var r = typeof resp === 'string' ? JSON.parse(resp) : resp;
                if (!r || !r.success || !r.data || r.data.length === 0) return;

                r.data.forEach(function (item) {
                    var key = self.getAlarmKey(item);
                    var isSuppressed = (item.suppressStatus == 2 || item.suppressStatus == 1);
                    var isPendingDelay = (item.pendingDelay === true);

                    if (isSuppressed) {
                        // ignore
                    } else if (isPendingDelay) {
                        if (item.ackStatus == 1) self.ackKeys[key] = true;
                    } else if (item.ackStatus == 1) {
                        self.ackKeys[key] = true;
                    }

                    if (!self.alarms[key]) {
                        self.alarms[key] = {
                            key: key, alertId: item.alertId, deviceId: item.deviceId, deviceName: item.deviceName,
                            orgId: item.orgId, column: item.column, columnName: item.columnName,
                            value: item.value, alarmLevel: item.alarmLevel, acqTime: item.acqTime,
                            ackStatus: item.ackStatus == 1 ? 1 : 0, alarmType: item.alarmType, alarminfo: item.alarminfo,
                            suppressStatus: item.suppressStatus || 0, suppressReason: item.suppressReason || '',
                            suppressUser: item.suppressUser || '', suppressTime: item.suppressTime || '',
                            suppressEndTime: item.suppressEndTime || '',
                            triggerAlarm: item.triggerAlarm !== false, pendingDelay: isPendingDelay
                        };
                    }
                });
                self.updateUI();
                // 数据加载完毕后，检查并播放声音
                self._refreshSoundAfterAck();
            }
        });
    },
    
    openSuppressedList: function(isRefresh) {
        if (!isRefresh && this.alarms && Object.keys(this.alarms).length === 0) return;

        var suppMap = {};
        for (var key in this.alarms) {
            var a = this.alarms[key];
            if (a.ackStatus === 1 && a.suppressStatus == 2) {
                if (!suppMap[a.deviceId]) {
                    suppMap[a.deviceId] = {
                        id: a.deviceId, text: a.deviceName || a.deviceId,
                        deviceId: a.deviceId, expanded: true, children: []
                    };
                }
                suppMap[a.deviceId].children.push({
                    id: key, alarmKey: key, text: a.columnName + '：' + a.value,
                    alarmLevel: a.alarmLevel, suppressReason: a.suppressReason,
                    suppressUser: a.suppressUser, suppressTime: a.suppressTime, suppressEndTime: a.suppressEndTime
                });
            }
        }
        
        var suppData = [];
        for (var k in suppMap) suppData.push(suppMap[k]);
        
        if (suppData.length === 0) {
            if (!isRefresh) mini.alert(_loginUserLanguageResource.noSuppressedRecords || "暂无人工消警记录");
            if (this.suppListWin && this.suppListWin.getVisible()) this.suppListWin.hide();
            return;
        }
        
        var grid = mini.get("suppressedListGrid");
        grid.setData(suppData);
        this.suppListWin.setTitle((_loginUserLanguageResource.manualSuppressRecords || "人工消警记录") + " (" + suppData.length + ")");
        if (!isRefresh) this.suppListWin.show();
    },

    onRemoteAck: function (alertIds, ackUser, ackIp, ackTime) {
    	if (!this.win) this.win = mini.get("AlarmWindow");
        if (!this.win) return; 
        if (!alertIds || alertIds.length === 0) return;
        var idSet = {}; alertIds.forEach(function(id){ idSet[id] = true; });
        for (var key in this.alarms) {
            var alarm = this.alarms[key];
            if (alarm.alertId && idSet[alarm.alertId]) {
                this.ackKeys[key] = true;
                alarm.ackStatus = 1; alarm.ackUser = ackUser; alarm.ackIp = ackIp; alarm.ackTime = ackTime;
            }
        }
        this.updateUI();
        this._refreshSoundAfterAck();
    },

    onRemoteSuppress: function (alertIds, suppressReason, suppressUser, suppressIp, suppressTime, suppressEndTime) {
    	if (!this.win) this.win = mini.get("AlarmWindow");
        if (!this.win) return; 
        if (!alertIds || alertIds.length === 0) return;
        var idSet = {}; alertIds.forEach(function(id){ idSet[id] = true; });
        for (var key in this.alarms) {
            var alarm = this.alarms[key];
            if (alarm.alertId && idSet[alarm.alertId]) {
                alarm.suppressStatus = 2; alarm.suppressReason = suppressReason;
                alarm.suppressUser = suppressUser; alarm.suppressIp = suppressIp;
                alarm.suppressTime = suppressTime; alarm.suppressEndTime = suppressEndTime;
            }
        }
        this.updateUI();
        this._refreshSoundAfterAck();
    },

    onRemoteCancelSuppress: function (alertIds) {
    	if (!this.win) this.win = mini.get("AlarmWindow");
        if (!this.win) return; 
        if (!alertIds || alertIds.length === 0) return;
        var idSet = {}; alertIds.forEach(function(id){ idSet[id] = true; });
        for (var key in this.alarms) {
            var alarm = this.alarms[key];
            if (alarm.alertId && idSet[alarm.alertId]) {
                alarm.suppressStatus = 0; alarm.suppressReason = '';
                alarm.suppressUser = ''; alarm.suppressIp = '';
                alarm.suppressTime = ''; alarm.suppressEndTime = '';
            }
        }
        this.updateUI();
        this._refreshSoundAfterAck();
    },

    clearAllForPermissionLoss: function () {
        this.alarms = {}; this.ackKeys = {}; this._localAckWarned = false;
        if (window.AlarmSoundManager) AlarmSoundManager.stop();
        if (this.win) { this.stopBlink(); this.win.hide(); }
        if (this.suppListWin) this.suppListWin.hide();
    },

    alignToBottomRight: function (w, h) {
        var docW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        var docH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
        this.win.setX(Math.max(0, docW - w - 20));
        this.win.setY(Math.max(0, docH - h - 40));
    }
};

// --- 声音管理 ---
var AlarmSoundManager = {
    unlocked: false, pendingLevel: null, currentLevel: null,
    audioCtx: null, sourceNode: null, audioBufferCache: {}, htmlAudio: null,
    config: {}, baseUrl: '', _loaded: false, _playToken: 0,

    initUnlock: function () {
        var self = this;
        var unlock = function () {
            if (self.unlocked) return;
            self.unlocked = true;
            if (self.audioCtx && self.audioCtx.state === 'suspended') self.audioCtx.resume().catch(function(){});
            
            document.removeEventListener('click', unlock, true);
            document.removeEventListener('pointerdown', unlock, true);
            document.removeEventListener('keydown', unlock, true);

            var tip = document.getElementById("muteTip");
            if (tip) tip.style.display = "none";

            if (self.pendingLevel !== null) {
                var lv = self.pendingLevel;
                self.pendingLevel = null;
                if (!AlarmManager.isMuted) self.play(lv);
            }
        };
        document.addEventListener('click', unlock, true);
        document.addEventListener('pointerdown', unlock, true);
        document.addEventListener('keydown', unlock, true);
        
        try {
            var Ctx = window.AudioContext || window.webkitAudioContext;
            if (Ctx) this.audioCtx = new Ctx();
            if (this.audioCtx && this.audioCtx.state === 'suspended') {
                this.audioCtx.resume().then(function(){ self.unlocked = true; });
            } else if (this.audioCtx) {
                this.unlocked = true;
            }
        } catch (e) {}
    },

    loadConfig: function () {
        var self = this;
        $.ajax({
            url: context + '/alarmSetManagerController/getAlarmLevelColor',
            type: 'GET',
            success: function (resp) {
                var r = typeof resp === 'string' ? JSON.parse(resp) : resp;
                if (!r || !r.Data) return;
                self.config = {};
                self.baseUrl = context + '/alarmSetManagerController/playAlarmSound?fileName=';
                if (r.Data.FirstLevel && r.Data.FirstLevel.SoundFile) self.config[100] = { fileName: r.Data.FirstLevel.SoundFile };
                if (r.Data.SecondLevel && r.Data.SecondLevel.SoundFile) self.config[200] = { fileName: r.Data.SecondLevel.SoundFile };
                if (r.Data.ThirdLevel && r.Data.ThirdLevel.SoundFile) self.config[300] = { fileName: r.Data.ThirdLevel.SoundFile };
                self._loaded = true;
            }
        });
    },

    play: function (level) {
        if (!level || level <= 0 || AlarmManager.isMuted) return;

        var currentHighestLevel = null;
        for (var k in AlarmManager.alarms) {
            var a = AlarmManager.alarms[k];
            if (a.ackStatus !== 1 && a.pendingDelay !== true && a.alarmLevel > 0) {
                if (currentHighestLevel === null || a.alarmLevel < currentHighestLevel) currentHighestLevel = a.alarmLevel;
            }
        }
        if (!currentHighestLevel) return;
        level = currentHighestLevel;

        if (!this._loaded) {
            this.loadConfig();
            setTimeout(function(){ AlarmSoundManager.play(level); }, 500);
            return;
        }
        var cfg = this.config[level];
        if (!cfg || !cfg.fileName) return;
        if (this.currentLevel !== null && level > this.currentLevel) return;

        this.stop();
        this.currentLevel = level;
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
        if (this.audioBufferCache[url]) {
            if (this._playToken === token) this._doPlayBuffer(this.audioBufferCache[url], level, token);
            return;
        }
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.responseType = 'arraybuffer';
        xhr.onload = function () {
            if (xhr.status !== 200 || self._playToken !== token) return;
            self.audioCtx.decodeAudioData(xhr.response, function (buffer) {
                self.audioBufferCache[url] = buffer;
                if (self._playToken !== token) return;
                self._doPlayBuffer(buffer, level, token);
            }, function () {
                if (self._playToken === token) self._playWithHtmlAudio(url, level, token);
            });
        };
        xhr.send();
    },

    _doPlayBuffer: function (buffer, level, token) {
        if (this._playToken !== token || this.currentLevel !== level) return;
        try {
            this.sourceNode = this.audioCtx.createBufferSource();
            this.sourceNode.buffer = buffer;
            this.sourceNode.loop = true;
            this.sourceNode.connect(this.audioCtx.destination);
            this.sourceNode.start(0);
            var tip = document.getElementById("muteTip");
            if (tip) tip.style.display = "none";
        } catch (e) {}
    },

    _playWithHtmlAudio: function (url, level, token) {
        var self = this;
        this.stop();
        this.currentLevel = level;
        this._playToken = token;
        this.htmlAudio = new Audio(url);
        this.htmlAudio.loop = true;
        this.htmlAudio.play().then(function () {
            if (self._playToken !== token) {
                try { self.htmlAudio.pause(); } catch (e) {}
                self.htmlAudio = null;
                return;
            }
            self.unlocked = true;
            var tip = document.getElementById("muteTip");
            if (tip) tip.style.display = "none";
        }).catch(function () {
            if (self._playToken !== token) return;
            self.pendingLevel = level;
            var tip = document.getElementById("muteTip");
            if (tip) tip.style.display = "inline";
        });
    },

    stop: function () {
        this._playToken++;
        if (this.sourceNode) {
            try { this.sourceNode.stop(); this.sourceNode.disconnect(); } catch (e) {}
            this.sourceNode = null;
        }
        if (this.htmlAudio) {
            try { this.htmlAudio.pause(); this.htmlAudio.currentTime = 0; } catch (e) {}
            this.htmlAudio = null;
        }
        this.currentLevel = null;
    }
};

function checkColumnRenderer(e) {
    if (e.record.children && e.record.children.length > 0) return ""; // 如果是父节点，不返回勾选框
    return e.column.editor ? true : undefined;
}

function onTreeDrawnode(e){
//	if (e.node.classes != 1) e.showCheckBox = false;
	if(!e.isLeaf){
		e.showCheckBox = false;
	}
	
}

// ==========================================
// 页面辅助操作与渲染函数
// ==========================================

function textRenderer(e) {
    if (!e.isLeaf) return e.value;
    if (e.record.status === 'NEW') {
        var s = AlarmManager.levelStyle[e.record.alarmLevel];
        if (s) return '<span style="color:' + s.color + ';font-weight:bold;">' + e.value + '</span>';
    }
    return e.value;
}

function levelRenderer(e) {
    if (!e.isLeaf) return "";
    var s = AlarmManager.levelStyle[e.value];
    return s ? '<span style="color:' + s.color + ';">' + s.text + '</span>' : '';
}

function statusRenderer(e) {
    if (!e.isLeaf) return "";
    var suppStatus = e.record.suppressStatus;
    var triggerAlarm = e.record.triggerAlarm;
    var pendingDelay = e.record.pendingDelay;
    var status = e.record.status;
    
    var ackText = (suppStatus === 1) ? _loginUserLanguageResource.unconfirmed : ((status === 'ACK' || status === 'SUPPRESSED') ? _loginUserLanguageResource.confirmed : _loginUserLanguageResource.unconfirmed);
    var suppText = _loginUserLanguageResource.pendingSuppress;
    if (suppStatus == 2) suppText = _loginUserLanguageResource.manualSuppress;
    else if (suppStatus == 1) suppText = _loginUserLanguageResource.autoSuppress;
    
    var ackColor = (ackText === _loginUserLanguageResource.unconfirmed) ? '#dc2828' : '#888';
    var suppColor = '#1677ff';
    if (suppText === _loginUserLanguageResource.manualSuppress) suppColor = '#0e7490';
    if (suppText === _loginUserLanguageResource.autoSuppress) suppColor = '#52c41a';

    var persistTag = '';
    if (pendingDelay === true) {
        persistTag = '<span style="color:#8c8c8c;font-size:11px;font-style:italic;"> (' + _loginUserLanguageResource.persistentDetection + ')</span>';
    } else if (triggerAlarm === false && e.record.alarmLevel > 0) {
        persistTag = '<span style="color:#f09614;font-size:11px;"> (' + _loginUserLanguageResource.triggerInterval + ')</span>';
    }
    return '<span style="color:' + ackColor + ';">' + ackText + '</span> ' +
           '<span style="color:' + suppColor + ';">' + suppText + '</span>' + persistTag;
}

function actionRendererAck(e) {
    if (!e.isLeaf || e.record.status !== 'NEW') return "";
    return '<a href="javascript:void(0)" class="op-link" onclick="doAck([\'' + e.record.alarmKey + '\'])">' + _loginUserLanguageResource.confirm + '</a>';
}

function actionRendererSuppress(e) {
    if (!e.isLeaf || e.record.status !== 'ACK') return "";
    return '<a href="javascript:void(0)" class="op-link" onclick="openSuppressDialog([\'' + e.record.alarmKey + '\'])">' + _loginUserLanguageResource.manualSuppress + '</a>';
}

function actionRendererCancel(e) {
    if (!e.isLeaf) return "";
    return '<a href="javascript:void(0)" class="op-link" onclick="cancelSuppress(\'' + e.record.alarmKey + '\')">' + _loginUserLanguageResource.cancelSuppress + '</a>' +
           '<a href="javascript:void(0)" class="op-link" onclick="editSuppress(\'' + e.record.alarmKey + '\')">' + _loginUserLanguageResource.update + '</a>';
}
function actionRendererCancelWindow(e) {
    return actionRendererCancel(e);
}

function rowStyle(e) {
    var record = e.record;
    if (!record.isLeaf) return "background:#f4f6f8;font-weight:bold;";
    var suppStatus = record.suppressStatus;
    if (suppStatus == 2 || suppStatus == 1) return "background:#f0fdfa;color:#0e7490;";
    if (record.pendingDelay === true) return "background:#f5f5f5;color:#8c8c8c;font-style:italic;";
    if (record.status === 'ACK') return "color:#888;background:#fafafa;";
    return "background:#fff4f4;";
}

//$(document).ready(function(){
//    setTimeout(function(){
//        mini.get("pendingGrid").setRowStyle(rowStyle);
//        mini.get("ackGrid").setRowStyle(rowStyle);
//        mini.get("suppressedGrid").setRowStyle(rowStyle);
//        mini.get("suppressedListGrid").setRowStyle(rowStyle);
//    }, 100);
//});

function onTabChanged(e) {
	if (!AlarmManager.win) return;
    var pg = mini.get("pendingGrid"), ag = mini.get("ackGrid"), sg = mini.get("suppressedGrid");
    if (!pg || !ag || !sg) return;
	AlarmManager.updateToolbar(mini.get("pendingGrid").getData(), mini.get("ackGrid").getData(), mini.get("suppressedGrid").getData()); 
}
function onCheckChanged(e) {
    var el = document.getElementById("pendingSelText");
    if (el) el.innerHTML = loginUserLanguageResource.selected + "：" + AlarmManager.getCheckedPendingKeys().length;
    el = document.getElementById("ackedSelText");
    if (el) el.innerHTML = loginUserLanguageResource.selected + "：" + AlarmManager.getCheckedAckedKeys().length;
}

function onMuteChange(e) {
    AlarmManager.isMuted = (e.value === "true" || e.value === true);
    if (AlarmManager.isMuted) AlarmSoundManager.stop();
    else AlarmManager._refreshSoundAfterAck();
}
function unlockSound() {
    var evt = new MouseEvent('click', { bubbles: true, cancelable: true, view: window });
    document.dispatchEvent(evt);
}
function doAck(keys) {
    if (!keys || keys.length === 0) return;
    AlarmManager.ack(keys, function (ok) {
        if (!ok) return;
        AlarmManager.updateUI();
        AlarmManager._refreshSoundAfterAck();
    });
}
function doAckSelected() {
    var keys = AlarmManager.getCheckedPendingKeys();
    if (keys.length === 0) { mini.alert(_loginUserLanguageResource.checkOne); return; }
    doAck(keys);
}
function doAckAll() {
    var keys = AlarmManager.getAllUnackKeys();
    if (keys.length === 0) { mini.alert(_loginUserLanguageResource.noPendingAlarm); return; }
    doAck(keys);
}

// 消警对话框
var currentSuppressKeys = [];
var currentEditKey = null;
function openSuppressDialog(keys) {
    if (!keys || keys.length === 0) return;
    currentSuppressKeys = keys;
    currentEditKey = null;
    mini.get("suppressReason").setValue("");
    mini.get("suppressDuration").setValue(24);
    mini.get("suppressDialog").setTitle(_loginUserLanguageResource.manualSuppress);
    mini.get("suppressDialog").show();
}
function closeSuppressDialog() { mini.get("suppressDialog").hide(); }
function confirmSuppress() {
    var reason = mini.get("suppressReason").getValue();
    var duration = mini.get("suppressDuration").getValue();
    if (!reason) { mini.alert(_loginUserLanguageResource.suppressReasonEmptyText); return; }

    if (currentEditKey) {
        var a = AlarmManager.alarms[currentEditKey];
        if (!a || !a.alertId) return;
        $.ajax({
            url: context + '/alarmQueryController/suppressAlarm',
            type: 'POST', contentType: 'application/json',
            data: JSON.stringify({ alertIds: [a.alertId], reason: reason, durationHours: duration }),
            success: function(resp) {
                var r = typeof resp === 'string' ? JSON.parse(resp) : resp;
                if (r.success) {
                    a.suppressReason = reason;
                    a.suppressEndTime = r.data.suppressEndTime;
                    a.suppressTime = r.data.suppressTime;
                    a.suppressUser = r.data.suppressUser;
                    closeSuppressDialog();
                    AlarmManager.updateUI();
                    mini.alert("<font color=blue>"+_loginUserLanguageResource.modifiedSuccessfully+"</font>");
                } else {
                    mini.alert(r.message || _loginUserLanguageResource.suppressFailed);
                }
            }
        });
    } else {
        var alertIds = [];
        currentSuppressKeys.forEach(function(key) {
            var a = AlarmManager.alarms[key];
            if (a && a.alertId) alertIds.push(a.alertId);
        });
        if (alertIds.length === 0) { mini.alert(_loginUserLanguageResource.noSuppressibleAlarm); return; }
        
        $.ajax({
            url: context + '/alarmQueryController/suppressAlarm',
            type: 'POST', contentType: 'application/json',
            data: JSON.stringify({ alertIds: alertIds, reason: reason, durationHours: duration }),
            success: function (resp) {
                var r = typeof resp === 'string' ? JSON.parse(resp) : resp;
                if (r.success) {
                    currentSuppressKeys.forEach(function(key) {
                        var a = AlarmManager.alarms[key];
                        if (a) {
                            a.suppressStatus = 2; a.suppressReason = reason;
                            a.suppressUser = r.data.suppressUser; a.suppressTime = r.data.suppressTime;
                            a.suppressEndTime = r.data.suppressEndTime;
                        }
                    });
                    closeSuppressDialog();
                    AlarmManager.updateUI();
                    AlarmManager._refreshSoundAfterAck();
                } else {
                    mini.alert(r.message || _loginUserLanguageResource.suppressFailed);
                }
            }
        });
    }
}

function doSuppressSelected() {
    var keys = AlarmManager.getCheckedAckedKeys();
    if (keys.length === 0) { mini.alert(_loginUserLanguageResource.selectAlarmToSuppress); return; }
    openSuppressDialog(keys);
}
function doSuppressAll() {
    var keys = AlarmManager.getAllAckedKeys();
    if (keys.length === 0) { mini.alert(_loginUserLanguageResource.noPendingSuppressAlarm); return; }
    openSuppressDialog(keys);
}

function cancelSuppress(key) {
    mini.confirm(_loginUserLanguageResource.confirmCancelSuppress, _loginUserLanguageResource.confirm, function(action) {
        if (action !== "ok") return;
        var a = AlarmManager.alarms[key];
        if (!a || !a.alertId) { mini.alert(_loginUserLanguageResource.alarmIdNotFoundCancel); return; }
        
        $.ajax({
            url: context + '/alarmQueryController/cancelSuppress',
            type: 'POST', contentType: 'application/json',
            data: JSON.stringify({ alertIds: [a.alertId] }),
            success: function (resp) {
                var r = typeof resp === 'string' ? JSON.parse(resp) : resp;
                if (r.success) {
                    mini.alert(_loginUserLanguageResource.cancelSuppressSuccess);
                    a.suppressStatus = 0; a.suppressReason = '';
                    a.suppressUser = ''; a.suppressTime = ''; a.suppressEndTime = '';
                    AlarmManager.updateUI();
                } else {
                    mini.alert(r.message || _loginUserLanguageResource.cancelSuppressFailed);
                }
            }
        });
    });
}

function editSuppress(key) {
    var a = AlarmManager.alarms[key];
    if (!a || !a.alertId) { mini.alert(_loginUserLanguageResource.alarmIdNotFoundEdit); return; }
    
    currentSuppressKeys = [];
    currentEditKey = key;
    
    mini.get("suppressReason").setValue(a.suppressReason || "");
    mini.get("suppressDuration").setValue(24);
    mini.get("suppressDialog").setTitle(_loginUserLanguageResource.editManualSuppress);
    mini.get("suppressDialog").show();
}

function openSuppressedList() {
    AlarmManager.openSuppressedList();
}