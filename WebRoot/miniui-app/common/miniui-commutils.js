// ================================================================
// miniui-commutils.js - MiniUI 适配版 CommUtils
// 从原 CommUtils.js 完整迁移，适配所有 ExtJS 依赖
// ================================================================

// ================================================================
// 1. 全局变量
// ================================================================
var sfycjhh = true;
var copyright = (typeof cosog !== 'undefined' && cosog.string) 
    ? cosog.string.copy + "&nbsp;<a href='" + cosog.string.linkaddress + "' target='_blank'>" + cosog.string.linkshow + "</a> " 
    : '';

var normalStyle = {
    fill: '#999',
    fontWeight: 'normal',
    fontSize: '12px',
    cursor: 'pointer'
};
var activeStyle = {
    fill: '#2caffe',
    fontWeight: 'bold',
    fontSize: '12px',
    cursor: 'pointer'
};

var realtimeInterval, realtimeGraphicalInterval;
var chartTitleFontSize = '14px';


//================================================================
//安全获取全局变量（优先当前页面，其次父页面）
//================================================================
function getGlobalVar(name, defaultValue) {
 // 1. 尝试当前窗口
 if (typeof window[name] !== 'undefined') {
     return window[name];
 }
 // 2. 尝试父窗口（适用于 iframe）
 try {
     if (window.parent && typeof window.parent[name] !== 'undefined') {
         return window.parent[name];
     }
 } catch(e) {}
 // 3. 返回默认值
 return defaultValue;
}

//================================================================
//一次性初始化所有常用全局变量（方便后续直接使用）
//================================================================
var _context = getGlobalVar('context', '');
//var _loginUserLanguageResource = getGlobalVar('loginUserLanguageResource', {});
var _loginUserLanguageResource = (function() {
    try {
        // 优先从父窗口获取
        var res = window.parent.loginUserLanguageResource;
        // 如果父窗口没有，再尝试当前窗口
        if (typeof res === 'undefined') {
            res = window.loginUserLanguageResource;
        }
        // 如果值是字符串，尝试解析为对象
        if (typeof res === 'string') {
            try { res = JSON.parse(res); } catch(e) { res = {}; }
        }
        // 确保返回对象
        return (res && typeof res === 'object') ? res : {};
    } catch(e) {
        return {};
    }
})();
var _configFile = getGlobalVar('configFile', { ap: { others: {} } });
var _productionUnit = _configFile.ap.others.productionUnit || getGlobalVar('productionUnit', '');
var _loginUserLanguage = getGlobalVar('loginUserLanguage', '');
var _defaultPageSize = getGlobalVar('defaultPageSize', '');




// ================================================================
// 2. String 扩展
// ================================================================
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

// ================================================================
// 3. 核心判断函数
// ================================================================
function isNotVal(val) {
    if (val == undefined || val == null 
        || val == "" || val == "null" || val == "undefined"
        || val.length == 0
        || (val.replace != undefined && val.replace(/\s+/g, "") == "")
        || (val.replace != undefined && val.replace(/\s+/g, "") == "null")
        || (val.replace != undefined && val.replace(/\s+/g, "") == "undefined")
    ) {
        return false;
    }
    return true;
}

function isNullVal(val) {
    var result = "";
    if (val == "" || val == "null" || val == "undefined" || val == undefined || val == null) {
        result = '';
    } else {
        result = val;
    }
    return result;
}

function isEquals(v1, v2) {
    if ((!isNotVal(v1)) && (!isNotVal(v2))) return true;
    if (v1 == v2) return true;
    if (JSON.stringify(v1) == JSON.stringify(v2)) return true;
    return false;
}

function isNumber(val) {
    var regPos = /^\d+(\.\d+)?$/;
    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/;
    return regPos.test(val) || regNeg.test(val);
}

function isNumber2(val) {
    return !isNaN(Number(val));
}

function isNotBank(val) {
    if (val != null && val != "" && val != "null") {
        return true;
    }
    return false;
}

function stringEndWith(sourceStr, endStr) {
    var d = sourceStr.length - endStr.length;
    return (d >= 0 && sourceStr.lastIndexOf(endStr) == d);
}

// ================================================================
// 4. 对象/JSON 转换
// ================================================================
function obj2str(o) {
    var r = [];
    if (typeof o == "string" || o == null) return o;
    if (typeof o == "object") {
        if (!o.sort) {
            r[0] = "{";
            for (var i in o) {
                r[r.length] = i;
                r[r.length] = ":";
                r[r.length] = obj2str(o[i]);
                r[r.length] = ",";
            }
            r[r.length - 1] = "}";
        } else {
            r[0] = "[";
            for (var i = 0; i < o.length; i++) {
                r[r.length] = obj2str(o[i]);
                r[r.length] = ",";
            }
            r[r.length - 1] = "]";
        }
        return r.join("");
    }
    return o.toString();
}

function strToObj(json) {
    try {
        return JSON.parse(json);
    } catch(e) {
        return null;
    }
}

function miniDecode(json) {
    try { return JSON.parse(json); } catch(e) { return null; }
}

function miniEncode(obj) {
    try { return JSON.stringify(obj); } catch(e) { return ''; }
}

// ================================================================
// 5. 日期格式化
// ================================================================
function dateFormat(value) {
    if (!value) return '';
    try {
        var d = new Date(value);
        if (isNaN(d.getTime())) return value;
        var y = d.getFullYear();
        var m = String(d.getMonth() + 1).padStart(2, '0');
        var day = String(d.getDate()).padStart(2, '0');
        var h = String(d.getHours()).padStart(2, '0');
        var min = String(d.getMinutes()).padStart(2, '0');
        var s = String(d.getSeconds()).padStart(2, '0');
        return y + '-' + m + '-' + day + ' ' + h + ':' + min + ':' + s;
    } catch(e) {
        return value;
    }
}

function dateTimeFormat(value) {
    return dateFormat(value);
}

function dateFormatNotDa(value) {
    if (!value) return '';
    try {
        var d = new Date(value);
        if (isNaN(d.getTime())) return value;
        var y = d.getFullYear();
        var m = String(d.getMonth() + 1).padStart(2, '0');
        var day = String(d.getDate()).padStart(2, '0');
        return y + '-' + m + '-' + day;
    } catch(e) {
        return value;
    }
}

function getFormatDate(value) {
    if (value != null && value != "" && value != "null") {
        return dateFormatNotDa(value);
    }
    return "";
}

// ================================================================
// 6. 数值/颜色/字符串处理
// ================================================================
function changeTwoDecimal(x) {
    if (isNaN(x)) return "";
    if (x === "") return "";
    return Math.round(x * 100) / 100;
}

function color16ToRgba(sColor, Opacity) {
    sColor = sColor.toLowerCase();
    var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
    if (sColor && reg.test(sColor)) {
        if (sColor.length === 4) {
            var sColorNew = "#";
            for (var i = 1; i < 4; i += 1) {
                sColorNew += sColor.slice(i, i + 1).concat(sColor.slice(i, i + 1));
            }
            sColor = sColorNew;
        }
        var sColorChange = [];
        for (var i = 1; i < 7; i += 2) {
            sColorChange.push(parseInt("0x" + sColor.slice(i, i + 2)));
        }
        return "rgba(" + sColorChange.join(",") + "," + Opacity + ")";
    }
    return sColor;
}

function trim(str) {
    str = str.replace(/^(\s|\u00A0)+/, '');
    for (var i = str.length - 1; i >= 0; i--) {
        if (/\S/.test(str.charAt(i))) {
            str = str.substring(0, i + 1);
            break;
        }
    }
    return str;
}

function URLencode(sStr) {
    return encodeURI(sStr).replace(/\+/g, '%2B').replace(/\"/g, '%22').replace(/\'/g, '%27').replace(/\//g, '%2F').replace(/\#/g, '%23');
}

function getStringLength(str) {
    var realLength = 0;
    if (isNotVal(str)) {
        var len = str.length;
        for (var i = 0; i < len; i++) {
            var charCode = str.charCodeAt(i);
            realLength += (charCode >= 0 && charCode <= 128) ? 1 : 2;
        }
    }
    return realLength;
}

// ================================================================
// 7. 浏览器/URL 工具
// ================================================================
function getBaseUrl() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return localhostPaht + projectName;
}

function getCurrentTime() {
    var now = new Date();
    var year = now.getFullYear();
    var month = String(now.getMonth() + 1).padStart(2, '0');
    var day = String(now.getDate()).padStart(2, '0');
    var hours = String(now.getHours()).padStart(2, '0');
    var minutes = String(now.getMinutes()).padStart(2, '0');
    var seconds = String(now.getSeconds()).padStart(2, '0');
    var ms = String(now.getMilliseconds()).padStart(3, '0');
    return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
}

function getBrowserType() {
    var explorer = window.navigator.userAgent;
    if (explorer.indexOf("MSIE") >= 0) return 1;
    if (explorer.indexOf("Firefox") >= 0) return 2;
    if (explorer.indexOf("Chrome") >= 0) return 3;
    if (explorer.indexOf("Opera") >= 0) return 4;
    if (explorer.indexOf("Safari") >= 0) return 5;
    return 6;
}

function isBrowserFullScreen() {
    return document.fullScreen || document.mozFullScreen || document.webkitIsFullScreen;
}

function getScrollWidth() {
    var noScroll, scroll, oDiv = document.createElement("DIV");
    oDiv.style.cssText = "margin:0px;padding:0px;border:0px;position:absolute; top:-1000px; width:100px; height:100px; overflow:hidden;";
    noScroll = document.body.appendChild(oDiv).clientWidth;
    oDiv.style.overflowY = "scroll";
    scroll = oDiv.clientWidth;
    document.body.removeChild(oDiv);
    return noScroll - scroll;
}

function isExist(arr, data) {
    var r = 0;
    if (isNotVal(arr) && arr.length > 0) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] === data) {
                r += 1;
            }
        }
    }
    return r;
}

function isMacOS() {
    return /Macintosh/i.test(navigator.userAgent);
}

function isMobileOS() {
    if (window.location.search.indexOf('forceMobile=true') !== -1) return true;
    var hasTouch = 'ontouchstart' in window || 
                   navigator.maxTouchPoints > 0 || 
                   window.matchMedia("(pointer: coarse)").matches;
    return hasTouch;
}

function jsonFormat(txt, compress) {
    var indentChar = '    ';
    if (/^\s*$/.test(txt)) return;
    txt = txt.replace(/\\r/g, "CRAPAPI_R");
    txt = txt.replace(/\\n/g, "CRAPAPI_N");
    txt = txt.replace(/\\t/g, "CRAPAPI_T");
    var data;
    try {
        data = $.parseJSON(txt);
    } catch (e) {
        return;
    }
    var draw = [],
        last = false,
        line = compress ? '' : '\n',
        nodeCount = 0,
        maxDepth = 0;

    var notify = function(name, value, isLast, indent, formObj) {
        nodeCount++;
        for (var i = 0, tab = ''; i < indent; i++)
            tab += indentChar;
        tab = compress ? '' : tab;
        maxDepth = ++indent;
        if (value && value.constructor == Array) {
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + '[' + line);
            for (var i = 0; i < value.length; i++)
                notify(i, value[i], i == value.length - 1, indent, false);
            draw.push(tab + ']' + (isLast ? line : (',' + line)));
        } else if (value && typeof value == 'object') {
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + '{' + line);
            var len = 0, i = 0;
            for (var key in value)
                len++;
            for (var key in value)
                notify(key, value[key], ++i == len, indent, true);
            draw.push(tab + '}' + (isLast ? line : (',' + line)));
        } else {
            if (typeof value == 'string') {
                value = value.replace(/\"/gm, '\\"');
                value = value.replace(/CRAPAPI_R/g, "\\r");
                value = value.replace(/CRAPAPI_N/g, "\\n");
                value = value.replace(/CRAPAPI_T/g, "\\t");
                value = '"' + value + '"';
            }
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + value +
                (isLast ? '' : ',') + line);
        }
    };
    var isLast = true, indent = 0;
    notify('', data, isLast, indent, false);
    return draw.join('');
}

function timeStr2TimeStamp(timeStr) {
    timeStr = timeStr.replace(/-/g, '/');
    return Date.parse(timeStr);
}

function timestamp2Str(timestamp) {
    var time = new Date(timestamp);
    var year = time.getFullYear();
    var month = String(time.getMonth() + 1).padStart(2, '0');
    var date = String(time.getDate()).padStart(2, '0');
    var hours = String(time.getHours()).padStart(2, '0');
    var minute = String(time.getMinutes()).padStart(2, '0');
    var second = String(time.getSeconds()).padStart(2, '0');
    var millisecond = String(time.getMilliseconds()).padStart(3, '0');
    return year + '-' + month + '-' + date + ' ' + hours + ':' + minute + ':' + second + '.' + millisecond;
}

function getDateAndTime(dateStr, h, m, s) {
    if (!isNotVal(dateStr)) return '';
    if (!isNotVal(h)) h = 0;
    if (!isNotVal(m)) m = 0;
    if (!isNotVal(s)) s = 0;
    var hStr = String(h).padStart(2, '0');
    var mStr = String(m).padStart(2, '0');
    var sStr = String(s).padStart(2, '0');
    return dateStr + ' ' + hStr + ':' + mStr + ':' + sStr;
}

// ================================================================
// 8. 替代 Ext 核心函数
// ================================================================
function miniGet(id) {
    return mini.get(id);
}

function miniAjax(options) {
    var defaults = {
        type: 'POST',
        dataType: 'json',
        cache: false,
        timeout: 30000
    };
    var opts = $.extend({}, defaults, options);
    var token = window.__accessToken || '';
    if (token) {
        opts.headers = opts.headers || {};
        opts.headers['X-Access-Token'] = token;
    }
    var originalError = opts.error;
    opts.error = function(jqXHR, textStatus, errorThrown) {
        if (jqXHR.status === 401 || jqXHR.status === 999) {
            window.location.href = _context + '/login';
            return;
        }
        if (originalError) {
            originalError(jqXHR, textStatus, errorThrown);
        } else {
            mini.alert((_loginUserLanguageResource && _loginUserLanguageResource.ajaxError) || '请求失败');
        }
    };
    $.ajax(opts);
}

function miniAlert(msg, title) {
    mini.alert(msg, title || '提示');
}

function miniConfirm(msg, callback, title) {
    mini.confirm(msg, title || '提示', function(action) {
        callback(action === 'ok');
    });
}

function miniIsEmpty(obj) {
    return !isNotVal(obj);
}

function miniIsIE() {
    return navigator.userAgent.indexOf("MSIE") >= 0 || navigator.userAgent.indexOf("Trident") >= 0;
}

// ================================================================
// 9. 替代 Ext.TaskManager
// ================================================================
var miniTaskManager = {
    tasks: [],
    newTask: function(config) {
        var id = setInterval(config.run, config.interval);
        this.tasks.push(id);
        return id;
    },
    clearAll: function() {
        for (var i = 0; i < this.tasks.length; i++) {
            clearInterval(this.tasks[i]);
        }
        this.tasks = [];
    }
};

// ================================================================
// 10. 刷新/关闭操作
// ================================================================
function RefreshEnter(e, name) {
    if (e.keyCode == 13 || e.which == 13) {
        refreshGrid(name);
    }
    return false;
}

function closeWindow(o) {
    var win = mini.get(o.closewin);
    if (isNotBank(win)) {
        win.destroy();
    } else {
        mini.alert(_loginUserLanguageResource.tip, "<font color=red>" + _loginUserLanguageResource.exception + "</font> " + _loginUserLanguageResource.objectNotFound);
    }
}

function refreshGrid(grid_id) {
    var grid = mini.get(grid_id);
    if (grid && grid.load) grid.load();
}

function LoadingWin(msg) {
    mini.showMessageBox({
        title: _loginUserLanguageResource.tip || '提示',
        message: '<div style="padding-top:20px">' + msg + ',' + _loginUserLanguageResource.loadingData + '</div>',
        buttons: ['ok'],
        iconCls: 'mini-messagebox-info',
        timeout: 3000
    });
}

function clearOrgHiddenValue() {
    var obj = mini.get("orgName_Parent_Id");
    if (isNotVal(obj)) obj.setValue("0");
    var obj_module = mini.get("mdName_Parent_Id");
    if (isNotVal(obj_module)) obj_module.setValue("0");
}

// ================================================================
// 11. 删除操作
// ================================================================
function ExtDelspace_ObjectInfo(space, grid_id, row, data_id, action_name) {
    var deletejson = [];
    for (var index = 0; index < row.length; index++) {
        deletejson.push(row[index].get ? row[index].get(data_id) : row[index][data_id]);
    }
    var delparamsId = "" + deletejson.join(",");
    $.ajax({
        url: _context + '/' + space + '/' + action_name,
        type: 'POST',
        data: { paramsId: delparamsId },
        dataType: 'json',
        success: function(result) {
            if (result.flag == true) {
                var g_spl = grid_id.split(",");
                if (isNotBank(g_spl)) {
                    for (var g = 0; g < g_spl.length; g++) {
                        var grid = mini.get(g_spl[g]);
                        if (grid && grid.load) grid.load();
                    }
                }
                mini.alert(_loginUserLanguageResource.tip, _loginUserLanguageResource.deleteSuccessfully);
            }
            if (result.flag == false) {
                mini.alert(_loginUserLanguageResource.tip, "<font color=red>" + _loginUserLanguageResource.deleteFailed + "</font>");
            }
        },
        error: function() {
            mini.alert(_loginUserLanguageResource.tip, "【<font color=red>" + _loginUserLanguageResource.exceptionThrow + "</font>】" + _loginUserLanguageResource.contactAdmin);
        }
    });
    return false;
}

function ExtDel_ObjectInfo(grid_id, row, data_id, action_name) {
    var deletejson = [];
    for (var index = 0; index < row.length; index++) {
        var val = row[index].get ? row[index].get(data_id) : row[index][data_id];
        if (val > 0) {
            deletejson.push(val);
        }
    }
    if (deletejson.length > 0) {
        var delparamsId = "" + deletejson.join(",");
        $.ajax({
            url: action_name,
            type: 'POST',
            data: { paramsId: delparamsId },
            dataType: 'json',
            success: function(result) {
                if (result.flag == true) {
                    mini.alert(_loginUserLanguageResource.tip, _loginUserLanguageResource.deleteSuccessfully);
                }
                if (result.flag == false) {
                    mini.alert(_loginUserLanguageResource.tip, "<font color=red>" + _loginUserLanguageResource.deleteFailed + "</font>");
                }
                var grid = mini.get(grid_id);
                if (grid && grid.load) grid.load();
            },
            error: function() {
                mini.alert(_loginUserLanguageResource.tip, "【<font color=red>" + _loginUserLanguageResource.exceptionThrow + "</font>】" + _loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
        mini.alert(_loginUserLanguageResource.tip, "<font color=red>" + _loginUserLanguageResource.deleteFailed + "</font>");
    }
    return false;
}

// ================================================================
// 导出
// ================================================================
/**
 * 导出数据时显示遮罩，并轮询检查导出完成状态
 * @param {string} key 导出任务唯一标识（由调用方生成）
 * @param {string|HTMLElement} container 遮罩容器（DOM元素 或 元素ID，默认 document.body）
 * @param {string} msg 遮罩提示文字
 * @param {number} interval 轮询间隔（毫秒，默认 1000）
 * @param {number} timeout 超时时间（毫秒，默认 60000），超时后自动取消遮罩并停止轮询
 * @returns {object} 返回 { mask, checkInterval, stop } 便于外部控制
 */
function exportDataMask(key, container, msg, interval, timeout) {
    msg = msg || (_loginUserLanguageResource.loadingData || '数据导出中，请稍候...');
    interval = interval || 1000;
    timeout = timeout || 60000;

    // 容器处理
    var containerEl = container;
    if (typeof container === 'string') {
        containerEl = document.getElementById(container);
        if (!containerEl) {
            console.warn('容器 "' + container + '" 未找到，使用 document.body');
            containerEl = document.body;
        }
    }
    if (!containerEl) {
        containerEl = document.body;
    }

    // 显示遮罩
    var mask = mini.mask({
        el: containerEl,
        cls: 'mini-mask-loading',
        html: msg
    });

    // 轮询检查导出状态
    var flagUrl = context + '/reportDataMamagerController/getSessionFlag?key=' + key;
    var checkInterval = setInterval(function () {
        $.ajax({
            url: flagUrl,
            type: 'POST',
            dataType: 'json',
            timeout: 5000,
            success: function (result) {
                if (result && result.flag == '1') {
                    clearInterval(checkInterval);
                    clearTimeout(timeoutId);
                    mini.unmask(containerEl);
                    console.log('导出完成，遮罩已移除, key:', key);
                }
            },
            error: function (xhr, status) {
                console.warn('导出状态检查请求失败:', status, 'key:', key);
                // 不停止轮询，继续尝试
            }
        });
    }, interval);

    // 超时自动取消遮罩（防止永久卡住）
    var timeoutId = setTimeout(function () {
        clearInterval(checkInterval);
        mini.unmask(mask);
        console.warn('导出超时，遮罩已强制移除, key:', key);
    }, timeout);

    // 返回控制对象，方便外部手动停止
    return {
        mask: mask,
        checkInterval: checkInterval,
        timeoutId: timeoutId,
        stop: function () {
            clearInterval(checkInterval);
            clearTimeout(timeoutId);
            mini.unmask(mask);
        }
    };
}

/**
 * 打开导出文件下载链接（不刷新当前页面）
 * @param {string} url 完整下载链接
 */
function openExcelWindow(url) {
	document.location.href = url;
}

function exportExcelWindow(url) {
    var appWindow = window.open(url);
    appWindow.focus();
}

function downloadFile(url, fileName) {
    var a = document.createElement('a');
    a.style.display = 'none';
    a.href = url;
    a.download = fileName;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
}

function exportGridPanelExcelData(gridId, url, fileName, title) {
    var grid = mini.get(gridId);
    if (!grid) return;
    var data = grid.getData();
    var fields = "";
    var heads = "";
    var columns = grid.getColumns();
    for (var index = 0; index < columns.length; index++) {
        var col = columns[index];
        if (index > 0 && col.hidden !== true) {
            fields += col.field + ",";
            heads += col.header + ",";
        }
    }
    if (isNotVal(fields)) {
        fields = fields.substring(0, fields.length - 1);
        heads = heads.substring(0, heads.length - 1);
    }
    fields = "id," + fields;
    heads = (_loginUserLanguageResource.idx || '序号') + "," + heads;
    var param = "&heads=" + heads + "&fields=" + fields + "&data=" + JSON.stringify(data) + "&fileName=" + fileName + "&title=" + title;
    param = param.replace(/#/g, "%23").replace(/%/g, "%25");
    openExcelWindow(url + '?flag=true&' + param);
}

// ================================================================
// 13. 回车事件
// ================================================================
function onEnterKeyDownFN(field, e, panelId) {
    if (e.keyCode == 13 || e.which == 13) {
        var grid = mini.get(panelId);
        if (grid && grid.load) grid.load();
    }
}

function onTreeEnterKeyDownFN(field, e, panelId) {
    if (e.keyCode == 13 || e.which == 13) {
        var tree = mini.get(panelId);
        if (tree && tree.load) tree.load();
    }
}

// ================================================================
// 14. 报警图标函数
// ================================================================
function alarmSetType(val) {
    if (val == 0) {
        return '<img src="' + _context + '/images/icon/alarm-greed.png" style="cursor:pointer"/>';
    } else {
        return '<img src="' + _context + '/images/icon/alarm-red.png" style="cursor:pointer"/>';
    }
}

function alarmType(val) {
    if (val == 0) {
        return "<img src='" + _context + "/images/icon/normal.png' style='cursor:pointer'/>";
    } else {
        return "<img src='" + _context + "/images/icon/exception.png' style='cursor:pointer'/>";
    }
}

function alarmLevelColor(val) {
    if (val == 0) {
        return "<img src='" + _context + "/images/icon/alarmcolor/exception0.png' style='cursor:pointer'/>";
    } else if (val == 100) {
        return "<img src='" + _context + "/images/icon/alarmcolor/exception1.png' style='cursor:pointer'/>";
    } else if (val == 200) {
        return "<img src='" + _context + "/images/icon/alarmcolor/exception2.png' style='cursor:pointer'/>";
    } else if (val == 300) {
        return "<img src='" + _context + "/images/icon/alarmcolor/exception3.png' style='cursor:pointer'/>";
    } else {
        return "<img src='" + _context + "/images/icon/alarmcolor/exception4.png' style='cursor:pointer'/>";
    }
}

// ================================================================
// 15. 树级联操作
// ================================================================
function treeComBox(node, checked) {
    if (!checked) {
        node.expand();
        node.checked = false;
        if (node.children) {
            for (var i = 0; i < node.children.length; i++) {
                var child = node.children[i];
                child.checked = false;
                childTree(child, false);
            }
        }
        parentTree(node, false);
    } else {
        node.expand();
        node.checked = true;
        if (node.children) {
            for (var i = 0; i < node.children.length; i++) {
                var child = node.children[i];
                child.checked = true;
                childTree(child, true);
            }
        }
        parentTree(node, true);
    }
}

function childTree(chlidArray, checked) {
    if (isNotVal(chlidArray)) {
        for (var i = 0; i < chlidArray.length; i++) {
            var child = chlidArray[i];
            child.checked = checked;
            if (child.children && child.children.length > 0) {
                childTree(child.children, checked);
            }
        }
    }
}

function parentTree(rootTree, checked) {
    var node = rootTree.parentNode;
    if (node && node.data && node.data.mdId != "0") {
        if (checked) {
            node.checked = checked;
            if (node.parentNode) {
                parentTree(node, checked);
            }
        } else {
            var falsestr = [];
            var arr = node.children || [];
            for (var i = 0; i < arr.length; i++) {
                if (!arr[i].checked) {
                    falsestr.push(false);
                }
            }
            if (falsestr.length == arr.length) {
                node.checked = checked;
                if (node.parentNode) {
                    parentTree(node, checked);
                }
            }
        }
    }
}

function checkedNode(node, checked) {
    node.expand();
    node.checked = checked;
    if (node.children) {
        for (var i = 0; i < node.children.length; i++) {
            node.children[i].checked = checked;
            checkedNode(node.children[i], checked);
        }
    }
}

// ================================================================
// 16. 告警颜色渲染（适配 Ext.getCmp/Ext.JSON.decode）
// ================================================================
function getAlarmShowStyle() {
    var val = null;
    try {
        var input = mini.get('AlarmShowStyle_Id');
        if (input) val = input.getValue();
    } catch (e) {}
    if (!isNotVal(val) && window.parent && window.parent.mini) {
        try {
            var parentInput = window.parent.mini.get('AlarmShowStyle_Id');
            if (parentInput) val = parentInput.getValue();
        } catch (e) {}
    }
    if (isNotVal(val) && typeof val === 'string') {
        try {
            return JSON.parse(val);
        } catch (e) {
            return {};
        }
    }
    return {};
}

function adviceColor(val, o, p, e) {
    if (val == undefined || val == "undefined") val = "";
    var AlarmShowStyle = getAlarmShowStyle();
    var alarmLevel = p.data ? p.data.resultAlarmLevel : 0;
    var tipval = val;
    if (p.data && p.data.resultString != undefined && p.data.resultString != '') {
        tipval = p.data.resultString;
    }
    var backgroundColor = '#FFFFFF';
    var Colorr = '#000000';
    var Opacity = 1;
    if (isNotVal(AlarmShowStyle) && AlarmShowStyle !== {}) {
        if (alarmLevel == 0) {
            backgroundColor = '#' + (AlarmShowStyle.Data && AlarmShowStyle.Data.Normal ? AlarmShowStyle.Data.Normal.BackgroundColor : 'FFFFFF');
            Colorr = '#' + (AlarmShowStyle.Data && AlarmShowStyle.Data.Normal ? AlarmShowStyle.Data.Normal.Color : '000000');
            Opacity = AlarmShowStyle.Data && AlarmShowStyle.Data.Normal ? AlarmShowStyle.Data.Normal.Opacity : 1;
        } else if (alarmLevel == 100) {
            backgroundColor = '#' + (AlarmShowStyle.Data && AlarmShowStyle.Data.FirstLevel ? AlarmShowStyle.Data.FirstLevel.BackgroundColor : 'FF0000');
            Colorr = '#' + (AlarmShowStyle.Data && AlarmShowStyle.Data.FirstLevel ? AlarmShowStyle.Data.FirstLevel.Color : 'FFFFFF');
            Opacity = AlarmShowStyle.Data && AlarmShowStyle.Data.FirstLevel ? AlarmShowStyle.Data.FirstLevel.Opacity : 1;
        } else if (alarmLevel == 200) {
            backgroundColor = '#' + (AlarmShowStyle.Data && AlarmShowStyle.Data.SecondLevel ? AlarmShowStyle.Data.SecondLevel.BackgroundColor : 'FFA500');
            Colorr = '#' + (AlarmShowStyle.Data && AlarmShowStyle.Data.SecondLevel ? AlarmShowStyle.Data.SecondLevel.Color : '000000');
            Opacity = AlarmShowStyle.Data && AlarmShowStyle.Data.SecondLevel ? AlarmShowStyle.Data.SecondLevel.Opacity : 1;
        } else if (alarmLevel == 300) {
            backgroundColor = '#' + (AlarmShowStyle.Data && AlarmShowStyle.Data.ThirdLevel ? AlarmShowStyle.Data.ThirdLevel.BackgroundColor : '8B0000');
            Colorr = '#' + (AlarmShowStyle.Data && AlarmShowStyle.Data.ThirdLevel ? AlarmShowStyle.Data.ThirdLevel.Color : 'FFFFFF');
            Opacity = AlarmShowStyle.Data && AlarmShowStyle.Data.ThirdLevel ? AlarmShowStyle.Data.ThirdLevel.Opacity : 1;
        }
        var rgba = color16ToRgba(backgroundColor, Opacity);
        o.style = 'background-color:' + rgba + ';color:' + Colorr + ';';
    }
    if (isNotVal(tipval)) {
        return '<span data-qtip="' + String(tipval).replace(/"/g, '&quot;') + '">' + String(val).replace(/"/g, '&quot;') + '</span>';
    }
    return val;
}

// ... (其他 advice 函数：adviceTimeFormat, adviceStatTableCommStatusColor, 
// adviceCommStatusColor, adviceUpCommStatusColor, adviceDownCommStatusColor,
// adviceRunStatusColor, adviceResultStatusColor, adviceDataColor,
// adviceDeviceOverviewDeviceNameColor, adviceRealtimeMonitoringDataColor
// 这些函数从原 CommUtils.js 复制，将 Ext.getCmp 替换为 mini.get，
// 将 Ext.JSON.decode 替换为 JSON.parse)

// ================================================================
// 17. createSmartDot
// ================================================================
function createSmartDot(color, number, options) {
    options = options || {};
    var height = options.height || 11;
    var numberStr = number.toString();
    var textLength = numberStr.length;
    var width = textLength === 1 ? height : height + (textLength - 1) * 5.5;
    var radius = height / 2;
    var capsulePath = 'M ' + radius + ',0 a ' + radius + ',' + radius + ' 0 0 1 0,' + height + ' h ' + (width - height) + ' a ' + radius + ',' + radius + ' 0 0 1 0,-' + height + ' z';
    var textX = width / 2;
    var textY = height / 2 + 3;
    var fontSize = Math.floor(height * 0.7);
    var isIE = navigator.userAgent.indexOf("MSIE") >= 0 || navigator.userAgent.indexOf("Trident") >= 0;
    return '<svg width="' + width + '" height="' + height + '" style="display: inline-block; vertical-align: middle; margin-right: 2px;">' +
        '  <path d="' + capsulePath + '" fill="' + color + '" stroke="' + (isIE ? 'none' : 'rgba(0,0,0,0.2)') + '" stroke-width="1"/>' +
        '  <text x="' + textX + '" y="' + textY + '" text-anchor="middle" font-size="' + fontSize + '" font-weight="normal" fill="white">' + numberStr + '</text>' +
        '</svg>';
}

// ================================================================
// 18. 树形路径查找
// ================================================================
function foreachAndSearchOrgAbsolutePath(orgStoreData, orgId) {
    var rtnArr = [];
    var rtnStr = "";
    function foreachAndSearchOrgAbsolutePathname(storeData, id) {
        if (storeData) {
            for (var i = 0; i < storeData.length; i++) {
                var record = storeData[i];
                if (record.data && record.data.orgId === id) {
                    if (record.parentNode) {
                        foreachAndSearchOrgAbsolutePathname(storeData, record.parentNode.data.orgId);
                    }
                    rtnArr.push(record.data.text);
                }
            }
        }
    }
    foreachAndSearchOrgAbsolutePathname(orgStoreData, orgId);
    for (var i = 0; i < rtnArr.length; i++) {
        rtnStr += rtnArr[i];
        if (i < rtnArr.length - 1) {
            rtnStr += "/";
        }
    }
    return rtnStr;
}

function foreachAndSearchOrgAbsolutePathId(orgStoreData, orgId) {
    var rtnArr = [];
    var rtnStr = "";
    function foreachAndSearchOrgAbsolutePathId(storeData, id) {
        if (storeData) {
            for (var i = 0; i < storeData.length; i++) {
                var record = storeData[i];
                if (record.data && record.data.orgId === id) {
                    if (record.parentNode) {
                        foreachAndSearchOrgAbsolutePathId(storeData, record.parentNode.data.orgId);
                    }
                    rtnArr.push(record.data.orgId);
                }
            }
        }
    }
    foreachAndSearchOrgAbsolutePathId(orgStoreData, orgId);
    rtnStr = "" + rtnArr.join(",");
    return rtnStr;
}

function foreachAndSearchOrgChildId(rec) {
    var rtnArr = [];
    function recursionOrgChildId(chlidArray) {
        var ch_length;
        var ch_node = chlidArray.childNodes;
        if (isNotVal(ch_node)) {
            ch_length = ch_node.length;
        } else {
            ch_length = chlidArray.length;
        }
        if (ch_length > 0) {
            if (isNotVal(chlidArray)) {
                for (var index = 0; index < chlidArray.length; index++) {
                    var childArrNode = chlidArray[index];
                    var x_node_seId = childArrNode.data ? childArrNode.data.orgId : childArrNode.orgId;
                    rtnArr.push(x_node_seId);
                    if (childArrNode.childNodes != null) {
                        recursionOrgChildId(childArrNode.childNodes);
                    }
                }
            }
        } else {
            if (isNotVal(chlidArray)) {
                var x_node_seId = chlidArray.data ? chlidArray.data.orgId : chlidArray.orgId;
                rtnArr.push(x_node_seId);
            }
        }
    }
    recursionOrgChildId(rec);
    return rtnArr.join(",");
}

/**
 * 递归获取组织机构节点下所有子节点的 ID（包括自身），以逗号分隔
 * 对应原有 ExtJS 函数：foreachAndSearchOrgChildId
 */
function getOrgNodeIds(node) {
    if (!node) return '';
    var ids = [];

    // 递归遍历函数
    function traverse(currentNode) {
        if (!currentNode) return;
        // 收集当前节点的 ID（MiniUI 树节点使用 id 字段，对应 orgId）
        var id = currentNode.id || currentNode.orgId;
        if (id !== undefined && id !== null && id !== '') {
            ids.push(id);
        }
        // 如果有子节点，递归遍历
        if (currentNode.children && currentNode.children.length > 0) {
            for (var i = 0; i < currentNode.children.length; i++) {
                traverse(currentNode.children[i]);
            }
        }
    }

    traverse(node);
    return ids.join(',');
}

/**
 * 递归获取组织机构节点下所有子节点的名称（包括自身），以逗号分隔
 * 对应原有 ExtJS 函数：selectEachTreeText
 */
function getOrgNodeNames(node) {
    if (!node) return '';
    var names = [];

    // 递归遍历函数
    function traverse(currentNode) {
        if (!currentNode) return;
        // 收集当前节点的名称（MiniUI 树节点使用 text 字段）
        var name = currentNode.text || currentNode.name || '';
        if (name !== '') {
            names.push(name);
        }
        // 如果有子节点，递归遍历
        if (currentNode.children && currentNode.children.length > 0) {
            for (var i = 0; i < currentNode.children.length; i++) {
                traverse(currentNode.children[i]);
            }
        }
    }

    traverse(node);
    return names.join(',');
}

// ================================================================
// 19. Tab/DeviceType 工具函数
// ================================================================
function getTabPanelActiveId(tabPanelId) {
    var activeId = '';
    var tabPanel = mini.get(tabPanelId);
    if (isNotVal(tabPanel)) {
        var activeTab = tabPanel.getActiveTab();
        if (activeTab) {
            activeId = activeTab.name || activeTab.id;
        }
    }
    return activeId;
}

function getDeviceTypeFromTabId(tabPanelId) {
    var deviceType = 0;
    var activeId = getTabPanelActiveId(tabPanelId);
    if (isNotVal(activeId)) {
        var parts = activeId.split('_');
        if (parts.length >= 2) {
            deviceType = parseInt(parts[parts.length - 1]) || 0;
        }
    }
    return deviceType;
}

function getDeviceTypeActiveId() {
    var globalDeviceTypeInput = mini.get('selectedDeviceType_global');
    var globalDeviceType = globalDeviceTypeInput ? globalDeviceTypeInput.getValue() : 0;
    var firstActiveTab = 0;
    var secondActiveTab = 0;
    if (globalDeviceType != 0 && globalDeviceType != '' && globalDeviceType != '0') {
        if (tabInfo && tabInfo.children != undefined && tabInfo.children != null && tabInfo.children.length > 0) {
            for (var i = 0; i < tabInfo.children.length; i++) {
                var exit = false;
                if (tabInfo.children[i].children != undefined && tabInfo.children[i].children != null && tabInfo.children[i].children.length > 0) {
                    var allSecondIds = '';
                    var childrenLength = tabInfo.children[i].children.length;
                    for (var j = 0; j < tabInfo.children[i].children.length; j++) {
                        if (j == 0) {
                            allSecondIds += tabInfo.children[i].children[j].deviceTypeId;
                        } else {
                            allSecondIds += (',' + tabInfo.children[i].children[j].deviceTypeId);
                        }
                        if (isNumber(globalDeviceType) && parseInt(globalDeviceType) == tabInfo.children[i].children[j].deviceTypeId) {
                            firstActiveTab = i;
                            secondActiveTab = childrenLength > 1 ? j + 1 : j;
                            exit = true;
                        }
                    }
                    if (!exit) {
                        if (globalDeviceType == allSecondIds) {
                            firstActiveTab = i;
                            secondActiveTab = 0;
                            exit = true;
                        }
                    }
                } else {
                    if (isNumber(globalDeviceType) && parseInt(globalDeviceType) == tabInfo.children[i].deviceTypeId) {
                        firstActiveTab = i;
                        secondActiveTab = 0;
                        exit = true;
                    }
                }
                if (exit) {
                    break;
                }
            }
        }
    }
    var deviceTypeActiveId = {};
    deviceTypeActiveId.firstActiveTab = firstActiveTab;
    deviceTypeActiveId.secondActiveTab = secondActiveTab;
    return deviceTypeActiveId;
}

function getDefaultActiveDeviceTypeTab() {
    var activeDeviceType = 0;
    if (tabInfo && tabInfo.children != undefined && tabInfo.children != null && tabInfo.children.length > 0) {
        if (tabInfo.children[0].children != undefined && tabInfo.children[0].children != null && tabInfo.children[0].children.length > 0) {
            var allSecondIds = '';
            for (var j = 0; j < tabInfo.children[0].children.length; j++) {
                if (j == 0) {
                    allSecondIds += tabInfo.children[0].children[j].deviceTypeId;
                } else {
                    allSecondIds += (',' + tabInfo.children[0].children[j].deviceTypeId);
                }
            }
            activeDeviceType = allSecondIds;
        } else {
            activeDeviceType = tabInfo.children[0].deviceTypeId;
        }
    }
    return activeDeviceType;
}

// ================================================================
// 20. 权限/计数工具
// ================================================================
function getCalculateTypeDeviceCount(orgId, deviceType, calculateType) {
    var deviceCount = 0;
    $.ajax({
        url: _context + '/realTimeMonitoringController/getCalculateTypeDeviceCount',
        type: 'POST',
        async: false,
        data: {
            orgId: orgId,
            deviceType: deviceType,
            calculateType: calculateType
        },
        dataType: 'json',
        success: function(response) {
            deviceCount = response.deviceCount || 0;
        },
        error: function() {
            deviceCount = 0;
        }
    });
    return deviceCount;
}

function getRoleModuleRight(moduleCode) {
    var moduleRight = null;
    $.ajax({
        url: _context + '/roleManagerController/getRoleModuleRight',
        type: 'POST',
        async: false,
        data: { moduleCode: moduleCode },
        dataType: 'json',
        success: function(response) {
            moduleRight = response;
        },
        error: function() {
            moduleRight = null;
        }
    });
    return moduleRight;
}

// ================================================================
// 21. 通用工具
// ================================================================
function getLabelWidth(str, language) {
    if (!str || str === '') return 0;
    try {
        var canvas = document.createElement('canvas');
        var ctx = canvas.getContext('2d');
        ctx.font = '12px tahoma,arial,verdana,sans-serif';
        var width = ctx.measureText(str).width;
        return width + 5;
    } catch(e) {
        var measureDiv = document.createElement('div');
        measureDiv.style.cssText = 'position:absolute;visibility:hidden;height:auto;width:auto;white-space:nowrap;font-size:12px;font-family:tahoma,arial,verdana,sans-serif;';
        measureDiv.textContent = str;
        document.body.appendChild(measureDiv);
        var width = measureDiv.offsetWidth;
        document.body.removeChild(measureDiv);
        return width + 5;
    }
}

function isNumByCalculateItemCode(code) {
    var r = false;
    if (code && (code.toUpperCase() == "CommRange".toUpperCase() ||
        code.toUpperCase() == "RunRange".toUpperCase() ||
        code.toUpperCase() == "RunStatus".toUpperCase() ||
        code.toUpperCase() == "RunStatusName".toUpperCase() ||
        code.toUpperCase() == "ResultName".toUpperCase() ||
        code.toUpperCase() == "optimizationSuggestion".toUpperCase())) {
        r = false;
    } else {
        r = true;
    }
    return r;
}

// ================================================================
// 22. Highcharts 绘图函数说明
// ================================================================
// 以下函数直接从原 CommUtils.js 复制，做以下替换：
// 1. Ext.JSON.decode → JSON.parse
// 2. Ext.Array.each → for 循环
// 3. Ext.getCmp → mini.get
// 4. Ext.isEmpty → !isNotVal
//
// 函数列表：
function initCurveChartFn(catagories, series, tickInterval, divId, title, ytitle, ytitle1) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		var mychart = new Highcharts.Chart({
			chart : {
				type : 'spline',
				renderTo : divId,
				shadow : false,
				borderWidth : 0,
				zoomType : 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
			},
			credits : {
				enabled : false
			},
			title : {
				text : title,
				style: {
	            	fontSize: chartTitleFontSize
	            },
				x : -20
			},
			xAxis : {
				categories : catagories,
				tickInterval : tickInterval,
				title : {
					text : _loginUserLanguageResource.date
				}
			},
			yAxis : [{
						lineWidth : 1,
						min:0,
						title : {
							text : ytitle
						},
						labels : {
							formatter : function() {
								return Highcharts.numberFormat(this.value,
										2);
							}
						},
						plotLines : [{
									value : 0,
									width : 1,
									zIndex:2,
									color : '#808080'
								}]
					}, {
						lineWidth : 1,
						min:0,
						max:100,
						opposite : true,
						labels : {
							formatter : function() {
								return Highcharts.numberFormat(this.value,
										2);
							}
						},
						title : {
							text : ytitle1
						}
					}],
			tooltip : {
				crosshairs : true,
				enabled : true,
				style : {
					color : '#333333',
					fontSize : '12px',
					padding : '8px'
				},
				formatter : function() {
					return '<b>' + this.series.name + '</b><br/>' + this.x
							+ ': ' + this.y;
				},
				valueSuffix : ''
			},
			exporting:{    
                enabled:true,    
                filename:title,  
                fallbackToExportServer: false,
                sourceWidth: $("#"+divId)[0].offsetWidth,
                sourceHeight: $("#"+divId)[0].offsetHeight,
                buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
           },
			plotOptions : {
				 spline: {  
			            lineWidth: 1,  
			            fillOpacity: 0.3,  
			             marker: {  
			             enabled: true,  
			              radius: 3,  //曲线点半径，默认是4
			                states: {  
			                   hover: {  
			                        enabled: true,  
			                        radius: 6
			                    }  
			                }  
		            },  
		            shadow: true  
		        } 
			},
			legend: {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
				borderWidth : 1,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
			},
			series : series
		});
	}
}
function initCurveChartFn1(catagories, series, tickInterval, divId, title, ytitle, ytitle1) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		var mychart = new Highcharts.Chart({
			chart : {
				type : 'spline',
				renderTo : divId,
				shadow : false,
				borderWidth : 0,
				zoomType : 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
			},
			credits : {
				enabled : false
			},
			title : {
				text : title,
				style: {
	            	fontSize: chartTitleFontSize
	            },
				x : -20
			},
			xAxis : {
				categories : catagories,
				tickInterval : tickInterval,
				title : {
					text : _loginUserLanguageResource.date
				}
			},
			yAxis : [{
						lineWidth : 1,
						min:0,
						title : {
							text : ytitle
						},
						labels : {
							formatter : function() {
								return Highcharts.numberFormat(this.value,
										2);
							}
						},
						plotLines : [{
									value : 0,
									width : 1,
									zIndex:2,
									color : '#808080'
								}]
					}, {
						lineWidth : 1,
						min:0,
						max:1,
						opposite : true,
						labels : {
							formatter : function() {
								return Highcharts.numberFormat(this.value,
										2);
							}
						},
						title : {
							text : ytitle1
						}
					}],
			tooltip : {
				crosshairs : true,
				enabled : true,
				style : {
					color : '#333333',
					fontSize : '12px',
					padding : '8px'
				},
				formatter : function() {
					return '<b>' + this.series.name + '</b><br/>' + this.x
							+ ': ' + this.y;
				},
				valueSuffix : ''
			},
			exporting:{    
                enabled:true,    
                filename:title,  
                fallbackToExportServer: false,
                sourceWidth: $("#"+divId)[0].offsetWidth,
                sourceHeight: $("#"+divId)[0].offsetHeight,
                buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
           },
			plotOptions : {
				 spline: {  
			            lineWidth: 1,  
			            fillOpacity: 0.3,  
			             marker: {  
			             enabled: true,  
			              radius: 3,  //曲线点半径，默认是4
			                states: {  
			                   hover: {  
			                        enabled: true,  
			                        radius: 6
			                    }  
			                }  
		            },  
		            shadow: true  
		        } 
			},
			legend: {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
				borderWidth : 1,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
			},
			series : series
		});
	}
}
function initCurveChart(years, values, tickInterval, divId) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		var mychart = new Highcharts.Chart({
			chart : {
				renderTo : divId,
				type : 'spline',
				shadow : false,
				borderWidth : 0,
				zoomType : 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
			},
			credits : {
				enabled : false
			},
			title : {
				text :cosog.string.clqx,
				x : -20,
				style: {
	            	fontSize: chartTitleFontSize
	            }
				// center
			},
			colors : ['#800000',// 红
					'#008C00',// 绿
					'#000000',// 黑
					'#0000FF',// 蓝
					'#F4BD82',// 黄
					'#FF00FF'// 紫
			],
			xAxis : {
				categories : years,
				tickInterval : tickInterval,
				title : {
					text : _loginUserLanguageResource.date
				}
			},
			yAxis : [{
						lineWidth : 1,
						min:0,
						title : {
							text : cosog.string.cl
						},
						labels : {
							formatter : function() {
								return Highcharts.numberFormat(this.value,
										2);
							}
						},
						plotLines : [{
									value : 0,
									width : 1,
									zIndex:2,
									color : '#808080'
								}]
					}, {
						lineWidth : 1,
						min:0,
						max:100,
						opposite : true,
						labels : {
							formatter : function() {
								return Highcharts.numberFormat(this.value,
										2);
							}
						},
						title : {
							text : cosog.string.hsl
						}
					}],
			tooltip : {
				crosshairs : true,
				enabled : true,
				style : {
					color : '#333333',
					fontSize : '12px',
					padding : '8px'
				},
				formatter : function() {
					return '<b>' + this.series.name + '</b><br/>' + this.x
							+ ': ' + this.y;
				},
				valueSuffix : ''
			},
			exporting:{    
                enabled:true,   
                fallbackToExportServer: false,
                filename:title,    
                sourceWidth: $("#"+divId)[0].offsetWidth,
                sourceHeight: $("#"+divId)[0].offsetHeight,
                buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
           },
			plotOptions : {
				 spline: {  
			            lineWidth: 1,  
			            fillOpacity: 0.3,  
			             marker: {  
			             enabled: true,  
			              radius: 3,  //曲线点半径，默认是4
                         //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
			                states: {  
			                   hover: {  
			                        enabled: true,  
			                        radius: 6
			                    }  
			                }  
		            },  
		            shadow: true  
		        } 
			},
			legend: {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
				borderWidth : 1,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
			},
			series : values
		});
	}
}
CurveVFnChartFn = function(store, divId) {
	var items = store.data.items;
	var tickInterval = 1;
	tickInterval = Math.floor(items.length / 4) + 1;
	var catagories = "[";
	for (var i = 0; i < items.length; i++) {
		catagories += "\"" + getFormatDate(items[i].data.jssj) + "\"";
		if (i < items.length - 1) {
			catagories += ",";
		}
	}
	catagories += "]";
	var legendName = [cosog.string.rpzsl, cosog.string.sjrzsl];
	var series = "[";
	for (var i = 0; i < legendName.length; i++) {
		series += "{\"name\":\"" + legendName[i] + "\",";
		series += "\"data\":[";
		for (var j = 0; j < items.length; j++) {
			if (i == 0) {
				series += items[j].data.rpzrl;
			} else if (i == 1) {
				series += items[j].data.sjrzrl;
			}
			if (j != items.length - 1) {
				series += ",";
			}
		}
		series += "]}";
		if (i != legendName.length - 1) {
			series += ",";
		}
	}
	series += "]";
	var cat = JSON.parse(catagories);
	var ser = JSON.parse(series);
	initWaterCurveChart(cat, ser, tickInterval, divId);
}
function initContinuousDiagramChart(pointdata, divId,title,subtitle,xtext,ytext,color) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		var mychart = new Highcharts.Chart({
			chart: {                                                                             
	            type: 'scatter',     // 散点图   
	            renderTo : divId,
	            borderWidth : 0,
	            zoomType: 'xy',
	            reflow: true,
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
	        },                                                                                   
	        title: {
	        	text: title,
	        	style: {
	            	fontSize: chartTitleFontSize
	            }
	        },                                                                                   
	        subtitle: {
	        	text: subtitle                                                      
	        },
	        credits: {
	            enabled: false
	        },
	        xAxis: {                                                                             
	            title: {                                                                         
	                text: xtext,    // 坐标+显示文字
	                useHTML: false,
	                margin:5,
                    style: {
                    	fontSize: '12px',
                        padding: '5px'
                    }
	            }, 
	            startOnTick: false,      //是否强制轴线在标线处开始
	            endOnTick: false,        //是否强制轴线在标线处结束                                                        
	            showLastLabel: true,
	            allowDecimals: false,    // 刻度值是否为小数
//	            min:0,
	            minorTickInterval: ''    // 最小刻度间隔
	        },                                                                                   
	        yAxis: {                                                                             
	            title: {                                                                         
	                text: ytext   // 载荷（kN） 
                },
                lineWidth: 1,
	        	tickWidth: 1,      // 刻度线宽度
                tickLength: 5,     // 刻度线长度（可选）
	            allowDecimals: false,    // 刻度值是否为小数
	            minorTickInterval: ''   // 不显示次刻度线
//	            min: 0                  // 最小值
	        },
	        exporting:{
                enabled:true,    
                filename:title,
                fallbackToExportServer: false,
                sourceWidth: $("#"+divId)[0].offsetWidth,
                sourceHeight: $("#"+divId)[0].offsetHeight,
                buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
           },
	        legend: {
	        	itemStyle:{
	        		fontSize: '8px'
	        	},
	            enabled: false,
	            layout: 'vertical',
				align: 'right',
				verticalAlign: 'top',
				x: 0,
				y: 55,
				floating: true,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },                                                                                   
	        plotOptions: {                                                                       
	            scatter: {                                                                       
	                marker: {                                                                    
	                    radius: 0,                                                               
	                    states: {                                                                
	                        hover: {                                                             
	                            enabled: true,                                                   
	                            lineColor: '#646464'                                    
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                states: {                                                                    
	                    hover: {                                                                 
	                        marker: {                                                            
	                            enabled: false                                                   
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                tooltip: {                                                                   
	                    headerFormat: '',                                
	                    pointFormat: '{point.x},{point.y}'                                
	                }                                                                            
	            }                                                                                
	        },
	        series: [{                                                                           
	            name: '',                                                                  
	            color: color,   
	            lineWidth:3,
	            data:  pointdata                                                                                  
	        }]
		});
	}
}
showSurfaceCard = function(result, divId) {
    var positionCurveData=result.positionCurveData.split(","); 
    var loadCurveData=result.loadCurveData.split(",");
	var data = "["; // 功图data
	var yAxisMin=0;
	var minLoadValue=0;
	var gtcount=positionCurveData.length;
	if(gtcount>loadCurveData.length){
		gtcount=loadCurveData.length;
	}
	
	if(result.positionCurveData!="" && positionCurveData.length>0 && result.loadCurveData!="" && loadCurveData.length>0){
		for (var i=0; i <= gtcount; i++) {
			if(i<gtcount){
				data += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(loadCurveData[i])+"],";
				
				if(changeTwoDecimal(loadCurveData[i])<minLoadValue){
					minLoadValue=changeTwoDecimal(loadCurveData[i]);
				}
			}else{
				data += "[" + changeTwoDecimal(positionCurveData[0]) + ","+changeTwoDecimal(loadCurveData[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
			}
		}
	}
	data+="]";
	var pointdata = JSON.parse(data);
	if(minLoadValue<0){
		yAxisMin=null;
	}
	
	initSurfaceCardChart(pointdata, result, divId,yAxisMin);
	return false;
}
showFSDiagramFromPumpcard = function(result, divId) {
	var pumpFSDiagramData=result.pumpFSDiagramData.split("#")[0];
    var gt=pumpFSDiagramData.split(","); // 功图数据：功图点数，位移1，载荷1，位移2，载荷2...
    var gtcount=(gt.length)/2; // 功图点数
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	var yAxisMin=0;
	var minLoadValue=0;
	if(gt.length>0){
		for (var i=0; i <= gt.length; i+=2) {
			if(i<gt.length){
				data += "[" + changeTwoDecimal(gt[i]) + ","+changeTwoDecimal(gt[i+1])+"],";
				if(changeTwoDecimal(gt[i+1])<minLoadValue){
					minLoadValue=changeTwoDecimal(gt[i+1]);
				}
			}else{
				data += "[" + changeTwoDecimal(gt[0]) + ","+changeTwoDecimal(gt[1])+"]";//将图形的第一个点拼到最后面，使图形闭合
			}
		}
		
		var minPos=100,maxPos=0;
		for (var i=0; i < gtcount; i++) {
			if(parseFloat(gt[i*2])<parseFloat(minPos)){
				minPos=changeTwoDecimal(gt[i*2]);
				minIndex=i;
			}
			if(parseFloat(gt[i*2])>parseFloat(maxPos)){
				maxPos=changeTwoDecimal(gt[i*2]);
				maxIndex=i;
			}
		}
		if(minIndex<=maxIndex){//如果最小值索引小于最大值索引
			for(var i=minIndex;i<=maxIndex;i++){
				upStrokeData += "[" + changeTwoDecimal(gt[i*2]) + ","+changeTwoDecimal(gt[i*2+1])+"]";
				if(i<maxIndex){
					upStrokeData+=",";
				}
			}
			var upStrokeCount=maxIndex-minIndex+1;//上冲程点数
			var downStrokeCount=gtcount-upStrokeCount;
			for(var i=0;i<downStrokeCount+2;i++){
				var index=i+maxIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				downStrokeData += "[" + changeTwoDecimal(gt[index*2]) + ","+changeTwoDecimal(gt[index*2+1])+"]";
				if(i<downStrokeCount+1){
					downStrokeData+=",";
				}
			}
		}else{//如果最小值索引大于最大值索引
			for(var i=maxIndex;i<=minIndex;i++){
				downStrokeData += "[" + changeTwoDecimal(gt[i*2]) + ","+changeTwoDecimal(gt[i*2+1])+"]";
				if(i<minIndex){
					downStrokeData+=",";
				}
			}
			var downStrokeCount=minIndex-maxIndex+1;//下冲程点数
			var upStrokeCount=gtcount-downStrokeCount;
			for(var i=0;i<upStrokeCount+2;i++){
				var index=i+minIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				upStrokeData += "[" + changeTwoDecimal(gt[index*2]) + ","+(gt[index*2+1])+"]";
				if(i<upStrokeCount+1){
					upStrokeData+=",";
				}
			}
		}
	}
	data+="]";
	upStrokeData+="]";
	downStrokeData+="]";
	
	if(minLoadValue<0){
		yAxisMin=null;
	}
	
	var pointdata = JSON.parse(data);
	var upStrokePointdata = JSON.parse(upStrokeData);
	var downStrokePointdata = JSON.parse(downStrokeData);
	initSurfaceCardChart(pointdata,result, divId,yAxisMin);
	return false;
}
function initSurfaceCardChart(pointdata, gtdata, divId, yAxisMin) {
	var deviceName=gtdata.deviceName;         // 井名
	var acqTime=gtdata.acqTime;     // 采集时间
	var upperLoadLine=gtdata.upperLoadLine;   // 理论上载荷
	var lowerLoadLine=gtdata.lowerLoadLine;   // 理论下载荷
	var pointCount=gtdata.pointCount;//曲线点数
	var fmax=gtdata.fmax;     // 最大载荷
	var fmin=gtdata.fmin;     // 最小载荷
	var deltaF=gtdata.deltaF;     // 交变载荷
	var stroke=gtdata.stroke;       // 冲程
	var spm=gtdata.spm;       // 冲次
	var liquidProduction=gtdata.liquidProduction;     // 日产液量
	var resultName=gtdata.resultName;     // 工况类型
	var optimizationSuggestion=gtdata.optimizationSuggestion;     // 优化建议
	var xtext='<span style="text-align:center;">'+_loginUserLanguageResource.displacement+'(m)'+'<br />';
	var productionUnitStr='t/d';
    if(_productionUnit.toUpperCase()=='stere'.toUpperCase()){
    	productionUnitStr='m^3/d';
    }
//    xtext+=_loginUserLanguageResource.pointCount+':'+pointCount+" ";
    xtext+=_loginUserLanguageResource.fMax+':'+fmax+'kN ';
    xtext+=_loginUserLanguageResource.fMin+':'+fmin+'kN ';
    xtext+=_loginUserLanguageResource.deltaF+':'+deltaF+'kN ';
    xtext+=_loginUserLanguageResource.stroke+':'+stroke+'m ';
    xtext+=_loginUserLanguageResource.SPM+':'+spm+'/min ';
    xtext+=_loginUserLanguageResource.liquidProduction+':'+liquidProduction+productionUnitStr+' ';
    xtext+=_loginUserLanguageResource.FSDiagramWorkType+':'+resultName;
    if(isNotVal(optimizationSuggestion)){
    	xtext+=' '+_loginUserLanguageResource.optimizationSuggestion+':'+optimizationSuggestion;
    }
    
    var upperlimit=parseFloat(fmax)+10;
    if(parseFloat(upperLoadLine)>=parseFloat(fmax)){
    	upperlimit=parseFloat(upperLoadLine)+10;
    }
    if(isNaN(upperlimit)){
    	upperlimit=null;
    }
    
    var pointFormat=_loginUserLanguageResource.displacement+': {point.x} m <br/> '+_loginUserLanguageResource.load+': {point.y} kN'
    
    if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
    	var chart = new Highcharts.Chart({
			chart: {
	            renderTo : divId,
	            zoomType: 'xy',
	            borderWidth : 0,
	            reflow: true,
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
	        },                                                                                   
	        title: {
	        	text: _loginUserLanguageResource.FSDiagram,  // 光杆功图       
	        	style: {
	            	fontSize: chartTitleFontSize
	            }
	        },                                                                                   
	        subtitle: {
	        	text: deviceName+' ['+acqTime+']'                                                      
	        },
	        credits: {
	            enabled: false
	        },
	        xAxis: {                                                                           
	            title: {                                                                         
	                text: xtext,    // 坐标+显示文字
	                useHTML: false,
	                margin:5,
                    style: {
                    	fontSize: '12px',
                        padding: '5px'
                    }
	            },                                                                               
	            startOnTick: false,      //是否强制轴线在标线处开始
	            endOnTick: false,        //是否强制轴线在标线处结束                                                        
	            showLastLabel: true,
	            allowDecimals: false,    // 刻度值是否为小数
	            minorTickInterval: ''    // 最小刻度间隔
	        },                                                                                   
	        yAxis: {                                                                             
	            title: {                                                                         
	                text: _loginUserLanguageResource.load+'(kN)'   // 载荷（kN） 
                },
                lineWidth: 1,
	        	tickWidth: 1,      // 刻度线宽度
                tickLength: 5,     // 刻度线长度（可选）
	            allowDecimals: false,    // 刻度值是否为小数
	            minorTickInterval: '',   // 不显示次刻度线
	            min: yAxisMin                  // 最小值
	        },
	        exporting:{
                enabled:true, 
                fallbackToExportServer: false,
                filename:deviceName+_loginUserLanguageResource.FSDiagram+'-'+acqTime,    
                sourceWidth: $("#"+divId)[0].offsetWidth,
                sourceHeight: $("#"+divId)[0].offsetHeight,
                buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
           },
	        legend: {                                                                            
	            layout: 'vertical',                                                              
	            align: 'left',                                                                   
	            verticalAlign: 'top',                                                            
	            x: 100,                                                                          
	            y: 70,                                                                           
	            floating: true,                                                                  
	            backgroundColor: '#FFFFFF',                                                      
	            borderWidth: 1  ,
	            enabled: false,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },                                                                                   
	        plotOptions: {                                                                       
	            scatter: {                                                                       
	                marker: {                                                                    
	                    radius: 0,                                                               
	                    states: {                                                                
	                        hover: {                                                             
	                            enabled: true,                                                   
	                            lineColor: '#646464'                                    
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                states: {                                                                    
	                    hover: {                                                                 
	                        marker: {                                                            
	                            enabled: false                                                   
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                tooltip: {                                                                   
	                    headerFormat: '',                                
	                    pointFormat: pointFormat
	                }                                                                            
	            }                                                                                
	        }, 
	        series: [{
	    		type: 'line',
	    		color: '#d12',
	    		dashStyle: 'Dash', //Dash,Dot,Solid,shortdash,默认Solid
	    		lineWidth:2,
	    		name: _loginUserLanguageResource.upperLoadLine,
	    		data: [[0, parseFloat(upperLoadLine)], [parseFloat(stroke), parseFloat(upperLoadLine)]],
	    		marker: {
	    			enabled: false
	    		},
	    		states: {
	    			hover: {
	    				lineWidth: 0
	    			}
	    		},
	    		enableMouseTracking: true
	    	},{
	    		type: 'line',
	    		color: '#d12',
	    		dashStyle: 'Dash', //Dash,Dot,Solid,shortdash,默认Solid
	    		lineWidth:2,
	    		name: _loginUserLanguageResource.lowerLoadLine,
	    		data: [[0, parseFloat(lowerLoadLine)], [parseFloat(stroke), parseFloat(lowerLoadLine)]],
	    		marker: {
	    			enabled: false
	    		},
	    		states: {
	    			hover: {
	    				lineWidth: 0
	    			}
	    		},
	    		enableMouseTracking: true
	    	},{                                                                           
	            name: _loginUserLanguageResource.load+'(kN)',   
	            type: 'scatter',     // 散点图   scatter
	            color: '#00ff00',   
	            lineWidth:3,
	            data:  pointdata                                                                                  
	        }]
    	});
    }
}
showRodPress = function(result, divId) {
    var deviceName = result.deviceName;
    var acqTime = result.acqTime;

    var rodCNT = result.rodCNT;
    var rodStressRatio1 = changeTwoDecimal(parseFloat(result.rodStressRatio1) * 100);
    var rodStressRatio2 = changeTwoDecimal(parseFloat(result.rodStressRatio2) * 100);
    var rodStressRatio3 = changeTwoDecimal(parseFloat(result.rodStressRatio3) * 100);
    var rodStressRatio4 = changeTwoDecimal(parseFloat(result.rodStressRatio4) * 100);
    var rodStressRangeRatio1 = changeTwoDecimal(parseFloat(result.rodStressRangeRatio1) * 100);
    var rodStressRangeRatio2 = changeTwoDecimal(parseFloat(result.rodStressRangeRatio2) * 100);
    var rodStressRangeRatio3 = changeTwoDecimal(parseFloat(result.rodStressRangeRatio3) * 100);
    var rodStressRangeRatio4 = changeTwoDecimal(parseFloat(result.rodStressRangeRatio4) * 100);

    var rod1 = _loginUserLanguageResource.rod1;
    var rod2 = _loginUserLanguageResource.rod2;
    var rod3 = _loginUserLanguageResource.rod3;
    var rod4 = _loginUserLanguageResource.rod4;

//    var showMaxRodStress = mini.get("rodStressChart_ShowMaxRodStress_Id").getValue();
//    var showRodStressRange = mini.get("rodStressChart_ShowRodStressRange_Id").getValue();
//
//    showMaxRodStress = (isNumber(showMaxRodStress) && parseInt(showMaxRodStress) === 1);
//    showRodStressRange = (isNumber(showRodStressRange) && parseInt(showRodStressRange) === 1);
//
//    if (!(showMaxRodStress || showRodStressRange)) {
//        showMaxRodStress = true;
//    }

    var showMaxRodStress = true;
    var showRodStressRange = false;
    
    // ★ 改用数组，不使用字符串拼接
    var categories_X = [];
    var seriesData1 = [];
    var seriesData2 = [];

    if (rodCNT >= 1) {
        categories_X.push(rod1);
        seriesData1.push(rodStressRatio1);
        seriesData2.push(rodStressRangeRatio1);
        if (rodCNT >= 2) {
            categories_X.push(rod2);
            seriesData1.push(rodStressRatio2);
            seriesData2.push(rodStressRangeRatio2);
            if (rodCNT >= 3) {
                categories_X.push(rod3);
                seriesData1.push(rodStressRatio3);
                seriesData2.push(rodStressRangeRatio3);
                if (rodCNT >= 4) {
                    categories_X.push(rod4);
                    seriesData1.push(rodStressRatio4);
                    seriesData2.push(rodStressRangeRatio4);
                }
            }
        }
    }

    // 直接传递数组给绘图函数（不再需要 JSON.parse）
    initRodPressChart(categories_X, seriesData1, seriesData2, deviceName, acqTime, divId, showMaxRodStress, showRodStressRange);
    return false;
};
function initRodPressChart(categories_X, seriesData1,seriesData2, deviceName, acqTime, divId,showMaxRodStress,showRodStressRange) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		var yAxisMax=100;
		for(var i=0;i<seriesData1.length;i++){
			if(parseFloat(seriesData1[i])>=100 || parseFloat(seriesData2[i])>=100){
				yAxisMax=null;
				break;
			}
		}
		var rodStressChart = new Highcharts.Chart({
					chart: {                                                                             
			            type: 'column',                      // 柱状图
			            renderTo : divId,                    // 图形放置的位置
			            zoomType: 'xy',                    // 沿xy轴放大
			            borderWidth : 0,
			            zooming: {
		                    mouseWheel: {
		                        enabled: false
		                    }
		                },
			            options3d: {                         // 3D效果
			                enabled: false,                   // 是否显示3D效果
			                alpha: 0,                        // 内旋角度
			                beta: 0,                         // 外旋角度
			                depth: 100,                       // 图形的全深比
			                frame: {
			                	back: {                      // X与Y形成的背面面板
			                		color: 'transparent',    // 面板颜色
			                		size: 1                  // 面板厚度
			                	},
			                	bottom: {                    // X与Z形成的底部面板
			                		color: '#fdfdfd',        // 面板颜色
			                		size: 0                  // 面板厚度
			                	},
			                	side: {                      // Y与Z形成的侧面面板
			                		color: '#fdfdfd',        // 面板颜色
			                		size: 2                  // 面板厚度
			                	}
			                },
			                viewDistance: 10                 // 图形前面看图的距离
			            }
			        },                                                                                   
			        title: {                                                                             
			            text: _loginUserLanguageResource.rodStress,              // 杆柱应力      
			            style: {
			            	fontSize: chartTitleFontSize
			            }
			        },                                                                                   
			        subtitle: {                                                                          
			            text: deviceName+' ['+acqTime+']'
			        },
//			        colors: ['#00bc00','#006837', '#00FF00','#006837', '#00FF00','#006837', '#00FF00','#006837'],
			        colors: ['#00e272','#fe6a35','#028142','#a22b01'],
//			        colors: ['#2caffe','#544fc5','#2caffe','#544fc5'],
			        credits: {
			            enabled: false
			        },
			        xAxis: { 
			        	categories: categories_X,
			            labels: {
			                rotation: 0,
			                align: 'center',
			                style: {
			                    fontSize: '12px',
			                    fontFamily: 'Verdana, sans-serif'
			                }
			            },
			            gridLineWidth: 0          // 网格线宽度
			        },                                                                                   
			        yAxis: {    
			        	min: 0,
			        	max: yAxisMax,
			            title: {                                                                         
			                text: _loginUserLanguageResource.percent+'(%)'  // 应力百分比(%)                                                          
			            },
			            lineWidth: 1,
			        	tickWidth: 1,      // 刻度线宽度
		                tickLength: 5,     // 刻度线长度（可选）
			            allowDecimals: false,    // 刻度值是否为小数
			            minorTickInterval: ''    // 不显示次刻度线
			        },
			        exporting:{    
	                    enabled:true, 
	                    fallbackToExportServer: false,
	                    filename:deviceName+_loginUserLanguageResource.rodStress+"-"+acqTime,    
	                    sourceWidth: $("#"+divId)[0].offsetWidth,
	                    sourceHeight: $("#"+divId)[0].offsetHeight,
	                    buttons: {
	    	    	    	contextButton: {
	    	    	    		menuItems: [
	    	    	    			'viewFullscreen',
	    	    	    			'printChart',
	    	    	    			'separator',
	    	    	    			'downloadPNG',
	    	    	    			'downloadJPEG',
	    	    	    			'downloadSVG',
	    	    	    			'separator',
	    	    	    			'downloadCSV',
	    	    	    			'downloadXLS'
	    	    	    			]
	    	    	    		}
	    	    	    }
	               },
			        legend: {                                                                            
			            enabled: true,
			            itemHiddenStyle: {
			                textDecoration: 'none'
			            }
			        }, 
			        plotOptions : {
			        	column: {  
//			        		pointWidth: 40,                     // 柱子宽度
			        		maxPointWidth:40,
			        		borderWidth: 2
//			        		color: '#000000'
				        } 
					},
			        series: [{
			            name: _loginUserLanguageResource.maxRodStress,  // 应力百分比(%)
			            data: seriesData1,
			            visible: showMaxRodStress,
			            dataLabels: {
			                enabled: true,
			                rotation: 0,
			                color: '#0066cc',
			                align: 'center',
			                x: 0,
			                y: 0,
//			                zIndex:0,
			                style: {
			                    fontSize: '13px',
			                    fontFamily: 'SimSun'
			                }
			            }
			        },{
			            name: _loginUserLanguageResource.rodStressRange,  // 应力范围百分比(%)
			            data: seriesData2,
			            visible: showRodStressRange,
			            dataLabels: {
			                enabled: true,
			                rotation: 0,
			                color: '#0066cc',
			                align: 'center',
			                x: 0,
			                y: 0,
//			                zIndex:0,
			                style: {
			                    fontSize: '13px',
			                    fontFamily: 'SimSun'
			                }
			            }
			        }] 
		});
		SetRodStressEveryOnePointColor(rodStressChart);           //设置每一个数据点的颜色值
	}
}
showPumpCard = function(result, divId) {
    var color = ["#00ff00", "#ff0000", "#ff8000", "#ff06c5", "#0000ff"];
    var deviceName = result.deviceName;
    var acqTime = result.acqTime;
    var resultCode = result.resultCode;
    var pumpFSDiagramData = result.pumpFSDiagramData ? result.pumpFSDiagramData.split("#") : [];

    var series = [];

    if (pumpFSDiagramData.length > 0 && resultCode != 1232) {
        for (var i = 0; i < pumpFSDiagramData.length; i++) {
            var everyDiagramData = pumpFSDiagramData[i].split(",");
            var data = [];
            // 注意：数据每两个一组，可能有多组点
            for (var j = 0; j < everyDiagramData.length; j += 2) {
                var x = parseFloat(everyDiagramData[j]);
                var y = parseFloat(everyDiagramData[j + 1]);
                if (!isNaN(x) && !isNaN(y)) {
                    data.push([x, y]);
                }
            }
            // 闭合：如果有点，将第一个点再次加入末尾
            if (data.length > 0) {
                data.push(data[0]);
            }
            // 构建 series 项
            series.push({
                name: '',
                color: color[i % color.length],
                lineWidth: 3,
                data: data
            });
        }
    }

    // 如果没有数据，提供一个空占位避免绘图错误
    var yAxisMax = null;
    var yAxisMin = null;
    if (series.length === 0) {
        series.push({});
        yAxisMax = 100;
        yAxisMin = 0;
    }

    var title = _loginUserLanguageResource.pumpFSDiagram || '泵功图';
    initMultiSurfaceCardChart(series, title, deviceName, acqTime, divId, yAxisMax, yAxisMin);
    return false;
};
function initMultiSurfaceCardChart(series, title, deviceName, acqTime, divId,yAxisMax,yAxisMin,upperLoadLine,lowerLoadLine) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		mychart = new Highcharts.Chart({
			chart: {                                                                             
	            type: 'scatter',
	            renderTo : divId,
	            borderWidth : 0,
	            zoomType: 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
	        },                                                                                   
	        title: {  
	        	text: title,
	        	style: {
	            	fontSize: chartTitleFontSize
	            }
	        },                                                                                   
	        subtitle: {                                                                          
	            text: deviceName+' ['+acqTime+']'                                                      
	        },
	        credits: {
	            enabled: false
	        },
	        xAxis: {                                                                             
	            title: {                                                                         
	                enabled: true,                                                               
	                text: _loginUserLanguageResource.displacement+'(m)',    // 位移（m）
	                align:'middle',//"low"，"middle" 和 "high"，分别表示于最小值对齐、居中对齐、与最大值对齐
	                style: {
	                	fontSize: '12px',
	                	padding: '5px'
                  }
	            },  
	            startOnTick: false,      //是否强制轴线在标线处开始
	            endOnTick: false,        //是否强制轴线在标线处结束                                                                  
	            showLastLabel: true,
	            minorTickInterval: ''    // 最小刻度间隔
	            //min:0                                                            
	        },                                                                                   
	        yAxis: {                                                                             
	            title: {                                                                         
	                text: _loginUserLanguageResource.load+'(kN)'
	            },
	            lineWidth: 1,
	        	tickWidth: 1,      // 刻度线宽度
                tickLength: 5,     // 刻度线长度（可选）
	            allowDecimals: false, 
	            minorTickInterval: '',
	            min:yAxisMin,
	            max:yAxisMax,
	            plotLines: [{
                    color: '#d12',
                    dashStyle: 'Dash', //Dash,Dot,Solid,默认Solid
                    label: {
                        text: upperLoadLine,
                        align: 'right',
                        x: -10
                    },
                    width: 3,
                    value: upperLoadLine,  //y轴显示位置
                    zIndex: 10
                },{
                    color: '#d12',
                    dashStyle: 'Dash',
                    label: {
                        text: lowerLoadLine,
                        align: 'right',
                        x: -10
                    },
                    width: 3,
                    value: lowerLoadLine,  //y轴显示位置
                    zIndex: 10
                }]
	        },
	        exporting:{    
                enabled:true,
                fallbackToExportServer: false,
                filename:deviceName+_loginUserLanguageResource.pumpFSDiagram+"-"+acqTime,    
                sourceWidth: $("#"+divId)[0].offsetWidth,
                sourceHeight: $("#"+divId)[0].offsetHeight,
                buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
           },
	        legend: {                                                                            
	            layout: 'vertical',                                                              
	            align: 'left',                                                                   
	            verticalAlign: 'top',                                                            
	            x: 100,                                                                          
	            y: 70,                                                                           
	            floating: true,                                                                  
	            backgroundColor: '#FFFFFF',                                                      
	            borderWidth: 1  ,
	            enabled: false,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },                                                                                   
	        plotOptions: {                                                                       
	            scatter: {                                                                       
	                marker: {                                                                    
	                    radius: 0,                                                               
	                    states: {                                                                
	                        hover: {                                                             
	                            enabled: true,                                                   
	                            lineColor: '#646464'                                    
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                states: {                                                                    
	                    hover: {                                                                 
	                        marker: {                                                            
	                            enabled: false                                                   
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                tooltip: {                                                                   
	                    headerFormat: '',                                
	                    pointFormat: _loginUserLanguageResource.displacement+': {point.x} m <br/> '+_loginUserLanguageResource.load+': {point.y} kN'
	                }                                                                            
	            }                                                                                
	        }, 
	        series: series 
		});
	}
}
showPumpEfficiency = function(bxzcData, divId) {
	var deviceName=bxzcData.deviceName;           // 井名
	var acqTime=bxzcData.acqTime;       // 时间
	var pumpEff1=bxzcData.pumpEff1;   // 冲程损失系数
	var pumpEff2=bxzcData.pumpEff2;       // 充满系数
	var pumpEff3=bxzcData.pumpEff3;       // 漏失系数
	var pumpEff4=bxzcData.pumpEff4;   // 液体收缩系数
	
	var ydata=[];
	ydata.push(isNumber(pumpEff1)?parseFloat(pumpEff1):0);
	ydata.push(isNumber(pumpEff2)?parseFloat(pumpEff2):0);
	ydata.push(isNumber(pumpEff3)?parseFloat(pumpEff3):0);
	ydata.push(isNumber(pumpEff4)?parseFloat(pumpEff4):0);
	
	initPumpEfficiencyChart(ydata, deviceName, acqTime, divId);
	return false;
}

function initPumpEfficiencyChart(ydata, deviceName, acqTime, divId, title, yname) {
	var yAxisMax=100;
	var dataLabelsEnable=false;
	for(var i=0;i<ydata.length;i++){
		if(parseFloat(ydata[i])>=100){
			yAxisMax=null;
		}
		
		if(parseFloat(ydata[i])>0){
			dataLabelsEnable=true;
		}
	}
	
	$('#'+divId).highcharts({
				chart: {                                                                             
		            type: 'column',      
		            borderWidth : 0,
		            zoomType: 'xy',
		            zooming: {
		                mouseWheel: {
		                    enabled: false // 禁用鼠标滚轮缩放
		                }
		            }                   
		        },                                                                                   
		        title: {                                                                                      
		            text: _loginUserLanguageResource.pumpEfficiencyComposition,
		            style: {
		            	fontSize: chartTitleFontSize
		            }
		        },
		        subtitle: {                                                                                   
		            text: deviceName+' ['+acqTime+']'                                                      
		        },
		        colors: ['#66ffcc', '#009999', '#ffcc33', '#ff6633', '#00ffff', '#3366cc', '#ffccff', '#cc0000', '#6AF9C4'],
		        credits: {            
		            enabled: false
		        },
		        xAxis: { 
		        	categories: [
		        		_loginUserLanguageResource.pumpEffChart_pumpEff1,
		        		_loginUserLanguageResource.pumpEffChart_pumpEff2,
		        		_loginUserLanguageResource.pumpEffChart_pumpEff3,
		        		_loginUserLanguageResource.pumpEffChart_pumpEff4
		        	],
		        	gridLineWidth: 0
		        }, 
		        tooltip: {
		            enabled: false
		        },
		        yAxis: {    
		        	min: 0,
		        	max: yAxisMax,
		        	lineWidth: 1,
		        	tickWidth: 1,      // 刻度线宽度
	                tickLength: 5,     // 刻度线长度（可选）
		            title: {                                                                         
		                text: _loginUserLanguageResource.percent+'(%)'                                          
		            },
		            minorTickInterval: ''
		        },
		        exporting:{    
                    enabled:true, 
                    fallbackToExportServer: false,
                    filename:deviceName+_loginUserLanguageResource.pumpEfficiencyComposition+"-"+acqTime,    
                    sourceWidth: $("#"+divId)[0].offsetWidth,
                    sourceHeight: $("#"+divId)[0].offsetHeight,
                    buttons: {
    	    	    	contextButton: {
    	    	    		menuItems: [
    	    	    			'viewFullscreen',
    	    	    			'printChart',
    	    	    			'separator',
    	    	    			'downloadPNG',
    	    	    			'downloadJPEG',
    	    	    			'downloadSVG',
    	    	    			'separator',
    	    	    			'downloadCSV',
    	    	    			'downloadXLS'
    	    	    			]
    	    	    		}
    	    	    }
               },
		        legend: {                                                                            
		            enabled: false,
		            itemHiddenStyle: {
		                textDecoration: 'none'
		            }
		        },  
		        series: [{
		            data: ydata,
		            dataLabels: {
		                enabled: dataLabelsEnable,
		                rotation: 0,
		                color: '#0066cc',
		                align: 'center',
		                x: 0,
		                y: 0,
		                style: {
		                    fontSize: '13px',
		                    fontFamily: 'SimSun'
		                }
		            }
		        }]
		        
	}, function (chart) {
        SetEveryOnePointColor(chart);
    });
}
showPSDiagram = function(result, divId,title) {
	if (!isNotVal(title)){
		title=_loginUserLanguageResource.PSDiagram;
	}
	var positionCurveData=result.positionCurveData.split(",");
	var powerCurveData=result.powerCurveData.split(",");
	var xtext='<span style="text-align:center;">'+_loginUserLanguageResource.displacement+'(m)'+'<br />';
	if(result.upStrokeWattMax!=undefined && result.downStrokeWattMax!=undefined){
		xtext+=_loginUserLanguageResource.upStrokeMaxValue+':' + result.upStrokeWattMax + 'kW '+_loginUserLanguageResource.downStrokeMaxValue+':'  + result.downStrokeWattMax + 'kW<br />';
	}
	if(result.wattDegreeBalance!=undefined){
		xtext+=_loginUserLanguageResource.degreeBalance+':' + result.wattDegreeBalance + '%<br /></span>';
	}
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	var gtcount=positionCurveData.length; // 功图点数
	if(gtcount>powerCurveData.length){
		gtcount=powerCurveData.length;
	}
	var yAxisMin=0;
	if(result.positionCurveData!="" && positionCurveData.length>0 && result.powerCurveData!="" && powerCurveData.length>0){
		for (var i=0; i <= gtcount; i++) {
			if(i<gtcount){
				data += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(powerCurveData[i])+"],";
				if(changeTwoDecimal(powerCurveData[i])<yAxisMin){
					yAxisMin=changeTwoDecimal(powerCurveData[i]);
				}
			}else{
				data += "[" + changeTwoDecimal(positionCurveData[0]) + ","+changeTwoDecimal(powerCurveData[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
			}
		}
		//获取最大位移和最小位移点数索引
		var minPos=100,maxPos=0;
		for (var i=0; i < gtcount; i++) {
			if(parseFloat(positionCurveData[i])<parseFloat(minPos)){
				minPos=parseFloat(positionCurveData[i]);
				minIndex=i;
			}
			if(parseFloat(positionCurveData[i])>parseFloat(maxPos)){
				maxPos=parseFloat(positionCurveData[i]);
				maxIndex=i;
			}
		}
		
		if(minIndex<=maxIndex){//如果最小值索引小于最大值索引
			for(var i=minIndex;i<=maxIndex;i++){
				upStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(powerCurveData[i])+"]";
				if(i<maxIndex){
					upStrokeData+=",";
				}
			}
			var upStrokeCount=maxIndex-minIndex+1;//上冲程点数
			var downStrokeCount=gtcount-upStrokeCount;
			for(var i=0;i<downStrokeCount+2;i++){
				var index=i+maxIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(powerCurveData[index])+"]";
				if(i<downStrokeCount+1){
					downStrokeData+=",";
				}
			}
		}else{//如果最小值索引大于最大值索引
			for(var i=maxIndex;i<=minIndex;i++){
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(powerCurveData[i])+"]";
				if(i<minIndex){
					downStrokeData+=",";
				}
			}
			var downStrokeCount=minIndex-maxIndex+1;//下冲程点数
			var upStrokeCount=gtcount-downStrokeCount;
			for(var i=0;i<upStrokeCount+2;i++){
				var index=i+minIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				upStrokeData +="[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(powerCurveData[index])+"]";
				if(i<upStrokeCount+1){
					upStrokeData+=",";
				}
			}
		}
		
	}
	data+="]";
	upStrokeData+="]";
	downStrokeData+="]";
	var pointdata = JSON.parse(data);
	var upStrokePointdata = JSON.parse(upStrokeData);
	var downStrokePointdata = JSON.parse(downStrokeData);
	initPSDiagramChart(upStrokePointdata,downStrokePointdata, result, divId,title,xtext,_loginUserLanguageResource.activePower+"(kW)",['#FF6633','#009999'],yAxisMin);
	return false;
}
function initPSDiagramChart(upStrokePointdata,downStrokePointdata, gtdata, divId,title,xtext,ytext,color,yAxisMin) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		var deviceName=gtdata.deviceName;         // 井名
		var acqTime=gtdata.acqTime;     // 采集时间
		mychart = new Highcharts.Chart({
					chart: {                                                                             
			            type: 'scatter',     // 散点图   
			            renderTo : divId,
			            borderWidth : 0,
			            zoomType: 'xy',
			            zooming: {
		                    mouseWheel: {
		                        enabled: false
		                    }
		                },
			            reflow: true
			        },                                                                                   
			        title: {
			        	text: title,
			        	style: {
			            	fontSize: chartTitleFontSize
			            }
			        },                                                                                   
			        subtitle: {
			        	text: deviceName+' ['+acqTime+']'                                                      
			        },
			        credits: {
			            enabled: false
			        },
			        xAxis: {                                                                             
			            title: {                                                                         
			                text: xtext,    // 坐标+显示文字
			                useHTML: false,
			                margin:5,
	                        style: {
	                        	fontSize: '12px',
	                            padding: '5px'
	                        }
			            }, 
			            startOnTick: false,      //是否强制轴线在标线处开始
			            endOnTick: false,        //是否强制轴线在标线处结束                                                        
			            showLastLabel: true,
			            allowDecimals: false,    // 刻度值是否为小数
//			            min:0,
			            minorTickInterval: ''    // 最小刻度间隔
			        },                                                                                   
			        yAxis: {                                                                             
			            title: {                                                                         
			                text: ytext   // 载荷（kN） 
	                    },
	                    lineWidth: 1,
	    	        	tickWidth: 1,      // 刻度线宽度
	                    tickLength: 5,     // 刻度线长度（可选）
			            allowDecimals: false,    // 刻度值是否为小数
//			            min: yAxisMin<0?null:0,                  // 最小值
			            minorTickInterval: ''   // 不显示次刻度线
			        },
			        exporting:{
	                    enabled:true,  
	                    fallbackToExportServer: false,
	                    filename: deviceName+''+title+'-'+acqTime,
	                    sourceWidth: $("#"+divId)[0].offsetWidth,
	                    sourceHeight: $("#"+divId)[0].offsetHeight,
	                    buttons: {
	    	    	    	contextButton: {
	    	    	    		menuItems: [
	    	    	    			'viewFullscreen',
	    	    	    			'printChart',
	    	    	    			'separator',
	    	    	    			'downloadPNG',
	    	    	    			'downloadJPEG',
	    	    	    			'downloadSVG',
	    	    	    			'separator',
	    	    	    			'downloadCSV',
	    	    	    			'downloadXLS'
	    	    	    			]
	    	    	    		}
	    	    	    }
	               },
			        legend: {
			        	itemStyle:{
			        		fontSize: '8px'
			        	},
			            enabled: true,
			            layout: 'vertical',
						align: 'right',
						verticalAlign: 'top',
						x: 0,
						y: 55,
						floating: true,
			            itemHiddenStyle: {
			                textDecoration: 'none'
			            }
			        },                                                                                   
			        plotOptions: {                                                                       
			            scatter: {                                                                       
			                marker: {                                                                    
			                    radius: 0,                                                               
			                    states: {                                                                
			                        hover: {                                                             
			                            enabled: true,                                                   
			                            lineColor: '#646464'                                    
			                        }                                                                    
			                    }                                                                        
			                },                                                                           
			                states: {                                                                    
			                    hover: {                                                                 
			                        marker: {                                                            
			                            enabled: false                                                   
			                        }                                                                    
			                    }                                                                        
			                },                                                                           
			                tooltip: {                                                                   
			                	headerFormat: '<b>{series.name}</b><br/>',
			                    pointFormat: _loginUserLanguageResource.displacement+': {point.x} m <br/> '+_loginUserLanguageResource.activePower+': {point.y} kW'
			                }                                                                            
			            }                                                                                
			        },
			        series: [{                                                                           
			            name: _loginUserLanguageResource.upStroke,                                                                  
			            color: color[0],   
			            lineWidth:3,
			            data:  upStrokePointdata                                                                                  
			        },{                                                                           
			            name: _loginUserLanguageResource.downStroke,                                                                  
			            color: color[1],   
			            lineWidth:3,
			            data:  downStrokePointdata                                                                                  
			        }]
		});
	}
}
showASDiagram = function(result, divId,title) {
	if (!isNotVal(title)){
		title=_loginUserLanguageResource.ISDiagram;
	}
	var positionCurveData=result.positionCurveData.split(",");
	var currentCurveData=result.currentCurveData.split(",");
	
	var xtext='<span style="text-align:center;">'+_loginUserLanguageResource.displacement+'(m)'+'<br />';
    
	if(result.upStrokeIMax!=undefined && result.downStrokeIMax!=undefined){
		xtext+=_loginUserLanguageResource.upStrokeMaxValue+':' + result.upStrokeIMax + 'A '
		+_loginUserLanguageResource.downStrokeMaxValue+':'  + result.downStrokeIMax + 'A<br />';
	}
	if(result.iDegreeBalance!=undefined){
		xtext+=_loginUserLanguageResource.degreeBalance+':' + result.iDegreeBalance + '%<br /></span>';
	}
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	var gtcount=positionCurveData.length; // 功图点数
	if(gtcount>currentCurveData.length){
		gtcount=currentCurveData.length;
	}
	if(result.positionCurveData!="" && positionCurveData.length>0 && result.currentCurveData!="" && currentCurveData.length>0){
		for (var i=0; i <= gtcount; i++) {
			if(i<gtcount){
				data += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(currentCurveData[i])+"],";
			}else{
				data += "[" + changeTwoDecimal(positionCurveData[0]) + ","+changeTwoDecimal(currentCurveData[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
			}
		}
		
		//获取最大位移和最小位移点数索引
		var minPos=100,maxPos=0;
		for (var i=0; i < gtcount; i++) {
			if(parseFloat(positionCurveData[i])<parseFloat(minPos)){
				minPos=positionCurveData[i];
				minIndex=i;
			}
			if(positionCurveData[i]>parseFloat(maxPos)){
				maxPos=parseFloat(positionCurveData[i]);
				maxIndex=i;
			}
		}
		
		if(minIndex<=maxIndex){//如果最小值索引小于最大值索引
			for(var i=minIndex;i<=maxIndex;i++){
				upStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(currentCurveData[i])+"]";
				if(i<maxIndex){
					upStrokeData+=",";
				}
			}
			var upStrokeCount=maxIndex-minIndex+1;//上冲程点数
			var downStrokeCount=gtcount-upStrokeCount;
			for(var i=0;i<downStrokeCount+2;i++){
				var index=i+maxIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(currentCurveData[index])+"]";
				if(i<downStrokeCount+1){
					downStrokeData+=",";
				}
			}
		}else{//如果最小值索引大于最大值索引
			for(var i=maxIndex;i<=minIndex;i++){
				downStrokeData += "[" + changeTwoDecimal(positionCurveData[i]) + ","+changeTwoDecimal(currentCurveData[i])+"]";
				if(i<minIndex){
					downStrokeData+=",";
				}
			}
			var downStrokeCount=minIndex-maxIndex+1;//下冲程点数
			var upStrokeCount=gtcount-downStrokeCount;
			for(var i=0;i<upStrokeCount+2;i++){
				var index=i+minIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				upStrokeData +="[" + changeTwoDecimal(positionCurveData[index]) + ","+changeTwoDecimal(currentCurveData[index])+"]";
				if(i<upStrokeCount+1){
					upStrokeData+=",";
				}
			}
		}
	}
	data+="]";
	upStrokeData+="]";
	downStrokeData+="]";
	var pointdata = JSON.parse(data);
	var upStrokePointdata = JSON.parse(upStrokeData);
	var downStrokePointdata = JSON.parse(downStrokeData);
	initASDiagramChart(upStrokePointdata,downStrokePointdata, result, divId,title,xtext,_loginUserLanguageResource.electricity+"(A)",['#CC0000','#0033FF']);
	return false;
}

function initASDiagramChart(upStrokePointdata,downStrokePointdata, gtdata, divId,title,xtext,ytext,color) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		var deviceName=gtdata.deviceName;         // 井名
		var acqTime=gtdata.acqTime;     // 采集时间
		mychart = new Highcharts.Chart({
					chart: {                                                                             
			            type: 'scatter',     // 散点图   
			            renderTo : divId,
			            borderWidth : 0,
			            zoomType: 'xy',
			            zooming: {
		                    mouseWheel: {
		                        enabled: false
		                    }
		                },
			            reflow: true
			        },                                                                                   
			        title: {
			        	text: title,
			        	style: {
			            	fontSize: chartTitleFontSize
			            }
			        },                                                                                   
			        subtitle: {
			        	text: deviceName+' ['+acqTime+']'                                                      
			        },
			        credits: {
			            enabled: false
			        },
			        xAxis: {                                                                             
			            title: {                                                                         
			                text: xtext,    // 坐标+显示文字
			                useHTML: false,
			                margin:5,
	                        style: {
	                        	fontSize: '12px',
	                            padding: '5px'
	                        }
			            }, 
			            startOnTick: false,      //是否强制轴线在标线处开始
			            endOnTick: false,        //是否强制轴线在标线处结束                                                        
			            showLastLabel: true,
			            allowDecimals: false,    // 刻度值是否为小数
//			            min:0,
			            minorTickInterval: ''    // 最小刻度间隔
			        },                                                                                   
			        yAxis: {                                                                             
			            title: {                                                                         
			                text: ytext   // 载荷（kN） 
	                    },
	                    lineWidth: 1,
	    	        	tickWidth: 1,      // 刻度线宽度
	                    tickLength: 5,     // 刻度线长度（可选）
			            allowDecimals: false,    // 刻度值是否为小数
			            minorTickInterval: ''   // 不显示次刻度线
//			            min: 0                  // 最小值
			        },
			        exporting:{
	                    enabled:true,
	                    fallbackToExportServer: false,
	                    filename: deviceName+''+title+'-'+acqTime,   
	                    sourceWidth: $("#"+divId)[0].offsetWidth,
	                    sourceHeight: $("#"+divId)[0].offsetHeight,
	                    buttons: {
	    	    	    	contextButton: {
	    	    	    		menuItems: [
	    	    	    			'viewFullscreen',
	    	    	    			'printChart',
	    	    	    			'separator',
	    	    	    			'downloadPNG',
	    	    	    			'downloadJPEG',
	    	    	    			'downloadSVG',
	    	    	    			'separator',
	    	    	    			'downloadCSV',
	    	    	    			'downloadXLS'
	    	    	    			]
	    	    	    		}
	    	    	    }
	               },
			        legend: {
			        	itemStyle:{
			        		fontSize: '8px'
			        	},
			            enabled: true,
			            layout: 'vertical',
						align: 'right',
						verticalAlign: 'top',
						x: 0,
						y: 55,
						floating: true,
			            itemHiddenStyle: {
			                textDecoration: 'none'
			            }
			        },                                                                                   
			        plotOptions: {                                                                       
			            scatter: {                                                                       
			                marker: {                                                                    
			                    radius: 0,                                                               
			                    states: {                                                                
			                        hover: {                                                             
			                            enabled: true,                                                   
			                            lineColor: '#646464'                                    
			                        }                                                                    
			                    }                                                                        
			                },                                                                           
			                states: {                                                                    
			                    hover: {                                                                 
			                        marker: {                                                            
			                            enabled: false                                                   
			                        }                                                                    
			                    }                                                                        
			                },                                                                           
			                tooltip: {                                                                   
			                	headerFormat: '<b>{series.name}</b><br/>',                       
			                    pointFormat: _loginUserLanguageResource.displacement+': {point.x} m <br/> '+_loginUserLanguageResource.electricity+': {point.y} A'                      
			                }                                                                            
			            }                                                                                
			        },
			        series: [{                                                                           
			            name: _loginUserLanguageResource.upStroke,                                                                  
			            color: color[0],   
			            lineWidth:3,
			            data:  upStrokePointdata                                                                                  
			        },{                                                                           
			            name: _loginUserLanguageResource.downStroke,                                                                  
			            color: color[1],   
			            lineWidth:3,
			            data:  downStrokePointdata                                                                                  
			        }]
		});
	}
}
showBalanceAnalysisCurveChart = function(crankAngle,loadRorque,crankTorque,balanceTorque,netTorque,title,deviceName,acqTime,divId) {
	var crankAngleArr=crankAngle.split(",");
	var loadRorqueArr=loadRorque.split(",");
	var crankTorqueArr=crankTorque.split(",");
	var balanceTorqueArr=balanceTorque.split(",");
	var netTorqueArr=netTorque.split(",");
	
	var legendName = [_loginUserLanguageResource.load,_loginUserLanguageResource.crankTorque,_loginUserLanguageResource.balanceTorque,_loginUserLanguageResource.netTorque];
	var catagories1 = "[";
    var series1 = "[";
    if(crankAngleArr.length>0){
    	var loadData="{\"name\":\""+_loginUserLanguageResource.load+"\",\"visible\":false,\"data\":[";
    	var crankData="{\"name\":\""+_loginUserLanguageResource.crankTorque+"\",\"visible\":false,\"data\":[";
    	var balanceData="{\"name\":\""+_loginUserLanguageResource.balanceTorque+"\",\"visible\":false,\"data\":[";
    	var netData="{\"name\":\""+_loginUserLanguageResource.netTorque+"\",\"data\":[";
        for(var i=0;i<crankAngleArr.length;i++){
        	catagories1+=crankAngleArr[i];
        	loadData+=changeTwoDecimal(loadRorqueArr[i]);
        	crankData+=changeTwoDecimal(crankTorqueArr[i]);
        	balanceData+=changeTwoDecimal(balanceTorqueArr[i]);
        	netData+=changeTwoDecimal(netTorqueArr[i]);
        	if(i<crankAngleArr.length-1){
        		catagories1+=",";
            	loadData+=",";
            	crankData+=",";
            	balanceData+=",";
            	netData+=",";
        	}
        }
    	loadData+="]}";
    	crankData+="]}";
    	balanceData+="]}";
    	netData+="]}";
    	series1+=loadData+","+crankData+","+balanceData+","+netData;
    }
    
    catagories1+="]";
    series1+="]";
    
    var cat1 = JSON.parse(catagories1);
	var ser1 = JSON.parse(series1);
	initBalanceCurveChart(cat1,ser1, divId,title,deviceName,acqTime,_loginUserLanguageResource.torque+"(kN*m)",_loginUserLanguageResource.crankAngle+"(°)");
	return false;
}

function initBalanceCurveChart(catagories,series,divId,title,deviceName,acqTime,ytext,xtext) {
	$('#'+divId).highcharts({
				chart : {
//					renderTo : divId,
					type : 'spline',
					shadow : false,
					borderWidth : 0,
					zoomType : 'xy',
		            zooming: {
		                mouseWheel: {
		                    enabled: false // 禁用鼠标滚轮缩放
		                }
		            }
				},
				exporting:{ 
		            enabled:true, 
		            fallbackToExportServer: false,
		            filename: deviceName+''+title+'-'+acqTime,   
		            sourceWidth: $("#"+divId)[0].offsetWidth,
		            sourceHeight: $("#"+divId)[0].offsetHeight,
		            buttons: {
    	    	    	contextButton: {
    	    	    		menuItems: [
    	    	    			'viewFullscreen',
    	    	    			'printChart',
    	    	    			'separator',
    	    	    			'downloadPNG',
    	    	    			'downloadJPEG',
    	    	    			'downloadSVG',
    	    	    			'separator',
    	    	    			'downloadCSV',
    	    	    			'downloadXLS'
    	    	    			]
    	    	    		}
    	    	    }
				},
				credits : {
					enabled : false
				},
				title : {
					text : title,
					style: {
		            	fontSize: chartTitleFontSize
		            }
				},
				subtitle: {
					text: deviceName+' ['+acqTime+']'                                                  
		        },
				colors : ['#000000',// 黑
						'#0000FF',// 蓝
						'#008C00',// 绿
						'#800000',// 红
						'#F4BD82',// 黄
						'#FF00FF'// 紫
				],
				xAxis : {
					categories : catagories,
					tickInterval : 40,
					tickWidth: 1,      // 刻度线宽度
	                tickLength: 10,     // 刻度线长度（可选）
					title : {
						text :xtext
					}
				},
				yAxis : {
//					min: 0,
					lineWidth: 1,      // Y 轴主线宽度
	                tickWidth: 1,      // 刻度线宽度
	                tickLength: 5,     // 刻度线长度（可选）
					title : {
						text :ytext
//						,
//						style : {
//							color : '#000000',
//							fontWeight : 'bold'
//						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				},
				tooltip : {
					crosshairs : true,
					enabled : true,
					style : {
						color : '#333333',
						fontSize : '12px',
						padding : '8px'
					},
					formatter : function() {
						var seriesName=this.series.name;
//						return '<b>' + seriesName + '</b><br/>'+_loginUserLanguageResource.torque+': ' + this.y+' kN*m';
						return '<b>' + seriesName + '</b><br/>'+_loginUserLanguageResource.crankAngle+': ' + this.point.category+' ° <br/>'+_loginUserLanguageResource.torque+': ' + this.y+' kN*m';
					},
					valueSuffix : ''
				},
				plotOptions : {
					 spline: {
						 lineWidth: 3,  
				         fillOpacity: 0.3,  
				         marker: {
				        	 enabled: true,
				        	 radius: 0,  //曲线点半径，默认是4
				            //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
				             states: {
				            	 hover: {
				            		 enabled: true,  
				                     radius: 6
				                }  
				            }  
				        },  
				        shadow: true  
			        } 
				},
				legend: {
					itemDistance:10,
					align : 'center',
					verticalAlign : 'bottom',
					layout : 'horizontal', //vertical 竖直 horizontal-水平
		            itemHiddenStyle: {
		                textDecoration: 'none'
		            }
				},
				series : series
			});
}
showBalanceAnalysisMotionCurveChart = function(crankAngle,position,polishrodV,polishrodA,title,subtitle,divId,type) {
	var crankAngleArr=crankAngle.split(","); 
    var positionArr=position.split(","); 
    var polishrodVArr=polishrodV.split(","); 
    var polishrodAArr=polishrodA.split(","); 
	var catagories = "[";
    var series = "[";
    var sData="{\"name\":\"位移\",\"yAxis\":0,\"data\":[";
	var vData="{\"name\":\"速度\",\"yAxis\":1,\"data\":[";
	var aData="{\"name\":\"加速度\",\"yAxis\":2,\"data\":[";
    if(crankAngleArr.length>0){
        for(var i=0;i<crankAngleArr.length;i++){
        	catagories+=crankAngleArr[i];
        	sData+=changeTwoDecimal(positionArr[i]);
        	vData+=changeTwoDecimal(polishrodVArr[i]);
        	aData+=changeTwoDecimal(polishrodAArr[i]);
        	if(i<crankAngleArr.length-1){
        		catagories+=",";
        		sData+=",";
        		vData+=",";
        		aData+=",";
        	}
        }
    }
    sData+="]}";
	vData+="]}";
	aData+="]}";
	if(type===1){
		series+=sData+","+vData+","+aData;
	}else{
		series+=sData+","+vData;
	}
    catagories+="]";
    series+="]";
    var cat = JSON.parse(catagories);
	var ser = JSON.parse(series);
	if(type===1){
		initBalanceCurveChartThreeY(cat,ser, divId,title,subtitle,"值","曲柄转角(°)");
	}else{
		initBalanceCurveChartTowY(cat,ser, divId,title,subtitle,"值","曲柄转角(°)");
	}
	
	return false;
}

function initBalanceCurveChartThreeY(catagories,series,divId,titletext,subtitle,ytext,xtext) {
	$('#'+divId).highcharts({
				chart : {
//					renderTo : divId,
					type : 'spline',
					shadow : false,
					borderWidth : 0,
					zoomType : 'xy',
		            zooming: {
		                mouseWheel: {
		                    enabled: false // 禁用鼠标滚轮缩放
		                }
		            }
				},
				credits : {
					enabled : false
				},
				title : {
					text : titletext,
					style: {
		            	fontSize: chartTitleFontSize
		            }
					// center
				},
				subtitle: {
		        	text: subtitle                                                   
		        },
				colors : ['#000000',// 黑
						'#0000FF',// 蓝
						'#008C00',// 绿
						'#800000',// 红
						'#F4BD82',// 黄
						'#FF00FF'// 紫
				],
				xAxis : {
					categories : catagories,
					tickInterval : 100,
					title : {
						text :xtext
					}
				},
				yAxis : [{
//					min: 0,
					lineWidth : 1,
					tickPosition:'inside',
					title : {
						text :'位移(m)'
//						,
//						style : {
//							color : '#000000',
//							fontWeight : 'bold'
//						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				},{
//					min: 0,
					lineWidth : 1,
					tickPosition:'inside',
					title : {
						text :'速度(m/s)'
//						,
//						style : {
//							color : '#000000',
//							fontWeight : 'bold'
//						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				},{
//					min: 0,
					lineWidth : 1,
					tickPosition:'inside',
					title : {
						text :'加速度(m/s²)'
//							,
//						style : {
//							color : '#000000',
//							fontWeight : 'bold'
//						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				}],
				tooltip : {
					crosshairs : true,
					enabled : true,
					style : {
						color : '#333333',
						fontSize : '12px',
						padding : '8px'
					},
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
				plotOptions : {
					 spline: {  
				            lineWidth: 3,  
				            fillOpacity: 0.3,  
				             marker: {  
				             enabled: true,  
				              radius: 0,  //曲线点半径，默认是4
                             //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
				                states: {  
				                   hover: {  
				                        enabled: true,  
				                        radius: 6
				                    }  
				                }  
			            },  
			            shadow: true  
			        } 
				},
				legend: {
					align : 'center',
					verticalAlign : 'bottom',
					layout : 'horizontal', //vertical 竖直 horizontal-水平
		            itemHiddenStyle: {
		                textDecoration: 'none'
		            }
				},
				series : series
			});
}
function initBalanceCurveChartTowY(catagories,series,divId,titletext,subtitle,ytext,xtext) {
	$('#'+divId).highcharts({
				chart : {
//					renderTo : divId,
					type : 'spline',
					shadow : false,
					borderWidth : 0,
					zoomType : 'xy',
		            zooming: {
		                mouseWheel: {
		                    enabled: false // 禁用鼠标滚轮缩放
		                }
		            }
				},
				credits : {
					enabled : false
				},
				title : {
					text : titletext,
					style: {
		            	fontSize: chartTitleFontSize
		            }
					// center
				},
				subtitle: {
		        	text: subtitle                                                   
		        },
				colors : ['#000000',// 黑
						'#0000FF',// 蓝
						'#008C00',// 绿
						'#800000',// 红
						'#F4BD82',// 黄
						'#FF00FF'// 紫
				],
				xAxis : {
					categories : catagories,
					tickInterval : 100,
					title : {
						text :xtext
					}
				},
				yAxis : [{
//					min: 0,
					lineWidth : 1,
//					tickPosition:'inside',
					title : {
						text : _loginUserLanguageResource.displacement+'(m)'
//							,
//						style : {
//							color : '#000000',
//							fontWeight : 'bold'
//						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				},{
//					min: 0,
					lineWidth : 1,
//					tickPosition:'inside',
					opposite:true,
					title : {
						text :'速度(m/s)'
//							,
//						style : {
//							color : '#000000',
//							fontWeight : 'bold'
//						}
					},
					labels : {
						formatter : function() {
//							return Highcharts.numberFormat(this.value, 2);
							return this.value;
						}
					},
					plotLines : [{
								value : 0,
								width : 1,
								zIndex:2,
								color : '#808080'
							}]
				}],
				tooltip : {
					crosshairs : true,
					enabled : true,
					style : {
						color : '#333333',
						fontSize : '12px',
						padding : '8px'
					},
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					},
					valueSuffix : ''
				},
				plotOptions : {
					 spline: {  
				            lineWidth: 3,  
				            fillOpacity: 0.3,  
				             marker: {  
				             enabled: true,  
				              radius: 0,  //曲线点半径，默认是4
                             //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
				                states: {  
				                   hover: {  
				                        enabled: true,  
				                        radius: 6
				                    }  
				                }  
			            },  
			            shadow: true  
			        } 
				},
				legend: {
					align : 'center',
					verticalAlign : 'bottom',
					layout : 'horizontal', //vertical 竖直 horizontal-水平
		            itemHiddenStyle: {
		                textDecoration: 'none'
		            }
				},
				series : series
			});
}
showFSDiagramOverlayChart = function(get_rawData,divId,visible,diagramType) {
	var color=new Array("#000000","#00ff00"); // 线条颜色
	var list=get_rawData.totalRoot;
	var upperLoadLine=null;
	var lowerLoadLine=null;
	var fmax=null;
	var fmin=null;
	var yAxisMin=0;
	var minYValue=0;
    var xAxisMin=0;
    var minXValue=0;
    
    
	var strokeMax=0;
	var visiblestr='';
	if(!visible){
		visiblestr='visible:false,';
	};
	if(list.length>0){
		fmax=list[0].fmax;
		fmin=list[0].fmin;
	}
	var title='';
	var ytext='';
	var color=new Array("#000000","#00ff00"); // 线条颜色
	var subtitle=get_rawData.deviceName+"["+get_rawData.start_date+"~"+get_rawData.end_date+"]";
	var pointFormat='{point.x}, {point.y}';
	if(diagramType===0){//如果是功图
		title=_loginUserLanguageResource.FSDiagramOverlay;
		ytext=_loginUserLanguageResource.load+'(kN)';
		pointFormat=_loginUserLanguageResource.displacement+': {point.x} m <br/> '+_loginUserLanguageResource.load+': {point.y} kN';
	}else if(diagramType===1){//电功图
		title= _loginUserLanguageResource.WSDiagramOverlay;
		ytext=_loginUserLanguageResource.activePower+"(kW)";
		pointFormat=_loginUserLanguageResource.displacement+': {point.x} m <br/> '+_loginUserLanguageResource.activePower+': {point.y} kW';
	}else if(diagramType===2){//电流图
		title= _loginUserLanguageResource.ISDiagramOverlay;
		ytext=_loginUserLanguageResource.electricity+"(A)";
		pointFormat=_loginUserLanguageResource.displacement+': {point.x} m <br/> '+_loginUserLanguageResource.electricity+': {point.y} A';
	}
	
	var minValue=null;
	var series = "[";
	for (var i =0; i < list.length; i++){
		if(i==0){
			if(list[i].upperLoadLine!="" && parseFloat(list[i].upperLoadLine)>0){
				upperLoadLine=list[i].upperLoadLine;
			}
			if(list[i].lowerLoadLine!="" && parseFloat(list[i].lowerLoadLine)>0){
				lowerLoadLine=list[i].lowerLoadLine;
			}
		}
		
		if(parseFloat(list[i].fmax)>fmax){
			fmax=parseFloat(list[i].fmax);
		}
		if(parseFloat(list[i].fmin)<fmin){
			fmin=parseFloat(list[i].fmin);
		}
		if(parseFloat(list[i].stroke)>strokeMax){
			strokeMax=parseFloat(list[i].stroke);
		}
		var xData = list[i].positionCurveData;
		var xDataArr=xData.split(",");
		var yData;
		var yDataArr=[];
		var diagramPoint=xDataArr.length;
		if(diagramType===0){//如果是功图
			yData = list[i].loadCurveData;
			color=new Array("#000000","#00ff00");
			minValue=0;
		}else if(diagramType===1){//电功图
			yData = list[i].powerCurveData;
			color=new Array("#000000","#CC0000");
		}else if(diagramType===2){//电流图
			yData = list[i].currentCurveData;
			color=new Array("#000000","#0033FF");
		}
		yDataArr=yData.split(",");
		if(diagramPoint>yDataArr.length){
			diagramPoint=yDataArr.length;
		}
		var data = "[";
		
		if(xData!="" && yData!="" && diagramPoint>0){
			for (var j=0; j <= diagramPoint; j++) {
				if(j<diagramPoint){
					data += "[" + changeTwoDecimal(xDataArr[j]) + ","+changeTwoDecimal(yDataArr[j])+"],";
					if(changeTwoDecimal(yDataArr[j])<minYValue){
						minYValue=changeTwoDecimal(yDataArr[j]);
					}
                    if(changeTwoDecimal(xDataArr[j])<minXValue){
						minXValue=changeTwoDecimal(xDataArr[j]);
					}
				}else{
					data += "[" + changeTwoDecimal(xDataArr[0]) + ","+changeTwoDecimal(yDataArr[0])+"]";//将图形的第一个点拼到最后面，使图形闭合
				}
			}
		}
		
		data+="]";
		if(list.length==1){
			    series+="{name: '"+list[i].id+"',visible:"+visible + ",color: '" + color[1] + " ' , " + "lineWidth:2," + "data:" + data + "}";
		}else{
			if(i==0){
				series+="{name: '"+list[i].id+"',visible:"+visible + ",color: '" + color[1] + " ' , " + "lineWidth:2," + "data:" + data + "},";
			}else if((i>0)&&(i<(list.length-1))){
	            series+="{name: '"+list[i].id+"',visible:"+visible + ",color: '" + color[1] + " ' , " + "lineWidth:2," + "data:" + data + "},";
	        }else{
				series+="{name: '"+list[i].id+"',visible:"+visible + ",color: '" + color[1] + " ' , " + "lineWidth:2," + "data:" + data + "}";
			}
		}
	}
	
	if(strokeMax>0 && diagramType===0){//如果是功图
		series+=",{type: 'line',color: '#d12',dashStyle: 'Dash',lineWidth:2,name: '"+_loginUserLanguageResource.upperLoadLine+"',data: [[0," +parseFloat(upperLoadLine)+"], ["+parseFloat(strokeMax)+", "+parseFloat(upperLoadLine)+"]],marker: {enabled: false},states: {hover: {lineWidth: 0}},enableMouseTracking: true}";
		series+=",{type: 'line',color: '#d12',dashStyle: 'Dash',lineWidth:2,name: '"+_loginUserLanguageResource.lowerLoadLine+"',data: [[0," +parseFloat(lowerLoadLine)+"], ["+parseFloat(strokeMax)+", "+parseFloat(lowerLoadLine)+"]],marker: {enabled: false},states: {hover: {lineWidth: 0}},enableMouseTracking: true}";
	}
	
	series+="]";
	
	var pointdata = JSON.parse(series);
	
	var upperlimit=parseFloat(fmax)+5;
    if(parseFloat(upperLoadLine)==0||parseFloat(fmax)==0){
    	upperlimit=null;
    }else if(parseFloat(upperLoadLine)>=parseFloat(fmax)){
    	upperlimit=parseFloat(upperLoadLine)+5;
    }
    var underlimit=parseFloat(fmin)-5;
    if(parseFloat(lowerLoadLine)==0||parseFloat(fmin)==0){
    	underlimit=null;
    }else if(parseFloat(lowerLoadLine)<=parseFloat(fmin)){
    	underlimit=parseFloat(lowerLoadLine)-5;
    }
    if(underlimit<0){
    	underlimit=0;
    }
    underlimit=0;
    if(isNaN(upperlimit)){
    	upperlimit=null;
    }
	if(minYValue<0){
		yAxisMin=null;
	}
    
    if(minXValue<0){
		xAxisMin=null;
	}
    
    if(diagramType===0){//如果是功图
    	initFSDiagramOverlayChart(pointdata, title,subtitle,ytext,get_rawData.deviceName, get_rawData.calculateDate, divId,upperLoadLine,lowerLoadLine,upperlimit,underlimit,strokeMax,xAxisMin,yAxisMin,pointFormat);
	}else {
		initPSDiagramOverlayChart(pointdata, title,subtitle,ytext,get_rawData.deviceName, get_rawData.calculateDate, divId,xAxisMin,yAxisMin,pointFormat);
	}
	
	return false;
}
function initFSDiagramOverlayChart(series, title,subtitle,ytext, deviceName, acqTime, divId,upperLoadLine,lowerLoadLine,upperlimit,underlimit,strokeMax,xAxisMin,yAxisMin,pointFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		mychart = new Highcharts.Chart({
			chart: {                                                                             
	            type: 'scatter',      // 散点图   
	            renderTo : divId,
	            borderWidth : 0,
	            zoomType: 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
	        },                                                                                   
	        title: {  
	        	text: title,
	        	style: {
	            	fontSize: chartTitleFontSize
	            }
	        },                                                                                   
	        subtitle: {                                                                          
	            text: subtitle//+' ['+acqTime+']'                                                      
	        },
	        credits: {
	            enabled: false
	        },
	        xAxis: {                                                                             
	            title: {                                                                         
	                enabled: true,                                                               
	                text: _loginUserLanguageResource.displacement+'(m)',    // 位移（m）
	                align:'middle',//"low"，"middle" 和 "high"，分别表示于最小值对齐、居中对齐、与最大值对齐
	                style: {
//                      color: '#000',
//                      fontWeight: 'normal',
	                	fontSize: '12px',
	                	padding: '5px'
                  }
	            },  
	            startOnTick: false,      //是否强制轴线在标线处开始
	            endOnTick: false,        //是否强制轴线在标线处结束                                                                  
	            showLastLabel: true,
	            minorTickInterval: '',    // 最小刻度间隔
	            min:xAxisMin                                                            
	        },                                                                                   
	        yAxis: {                                                                             
	            title: {                                                                         
	                text: ytext    // 载荷（kN）                                                          
	            },
	            lineWidth: 1,
	        	tickWidth: 1,      // 刻度线宽度
                tickLength: 5,     // 刻度线长度（可选）
	            allowDecimals: false,    // 刻度值是否为小数
	            //endOnTick: false,        //是否强制轴线在标线处结束   
	            minorTickInterval: '',    // 不显示次刻度线
	            min:yAxisMin
	        },
	        exporting:{    
                enabled:true,    
                filename:title,  
                fallbackToExportServer: false,
                sourceWidth: $("#"+divId)[0].offsetWidth,
                sourceHeight: $("#"+divId)[0].offsetHeight,
                buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
           },
	        legend: {                                                                            
	            layout: 'vertical',                                                              
	            align: 'left',                                                                   
	            verticalAlign: 'top',                                                            
	            x: 100,                                                                          
	            y: 70,                                                                           
	            floating: true,                                                                  
	            backgroundColor: '#FFFFFF',                                                      
	            borderWidth: 1  ,
	            enabled: false,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },                                                                                   
	        plotOptions: {                                                                       
	            scatter: {                                                                       
	                marker: {                                                                    
	                    radius: 0,                                                               
	                    states: {                                                                
	                        hover: {                                                             
	                            enabled: true,                                                   
	                            lineColor: '#646464'                                    
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                states: {                                                                    
	                    hover: {                                                                 
	                        marker: {                                                            
	                            enabled: false                                                   
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                tooltip: {                                                                   
	                    headerFormat: '',                                
	                    pointFormat: pointFormat
	                }                                                                            
	            }                                                                                
	        }, 
	        series: series 
		});
	}
}

function initPSDiagramOverlayChart(series, title,subtitle,ytext, deviceName, acqTime, divId,xAxisMin,yAxisMin,pointFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		mychart = new Highcharts.Chart({
			chart: {                                                                             
	            type: 'scatter',      // 散点图   
	            renderTo : divId,
	            borderWidth : 0,
	            zoomType: 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
	        },                                                                                   
	        title: {  
	        	text: title,
	        	style: {
	            	fontSize: chartTitleFontSize
	            }
	        },                                                                                   
	        subtitle: {                                                                          
	            text: subtitle//+' ['+acqTime+']'                                                      
	        },
	        credits: {
	            enabled: false
	        },
	        xAxis: {                                                                             
	            title: {                                                                         
	                enabled: true,                                                               
	                text: _loginUserLanguageResource.displacement+'(m)',    // 位移（m）
	                align:'middle',//"low"，"middle" 和 "high"，分别表示于最小值对齐、居中对齐、与最大值对齐
	                style: {
	                	fontSize: '12px',
	                	padding: '5px'
                  }
	            },  
	            startOnTick: false,      //是否强制轴线在标线处开始
	            endOnTick: false,        //是否强制轴线在标线处结束                                                                  
	            showLastLabel: true,
	            minorTickInterval: '',    // 最小刻度间隔
	            min:xAxisMin                                                            
	        },                                                                                   
	        yAxis: {                                                                             
	            title: {                                                                         
	                text: ytext                                             
	            },
	            lineWidth: 1,
	        	tickWidth: 1,      // 刻度线宽度
                tickLength: 5,     // 刻度线长度（可选）
	            allowDecimals: false,    // 刻度值是否为小数
	            //endOnTick: false,        //是否强制轴线在标线处结束   
	            minorTickInterval: ''    // 不显示次刻度线
	        },
	        exporting:{    
                enabled:true,    
                filename:title,  
                fallbackToExportServer: false,
                sourceWidth: $("#"+divId)[0].offsetWidth,
                sourceHeight: $("#"+divId)[0].offsetHeight,
                buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
           },
	        legend: {
	            enabled: false,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },                                                                                   
	        plotOptions: {                                                                       
	            scatter: {                                                                       
	                marker: {                                                                    
	                    radius: 0,                                                               
	                    states: {                                                                
	                        hover: {                                                             
	                            enabled: true,                                                   
	                            lineColor: '#646464'                                    
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                states: {                                                                    
	                    hover: {                                                                 
	                        marker: {                                                            
	                            enabled: false                                                   
	                        }                                                                    
	                    }                                                                        
	                },                                                                           
	                tooltip: {                                                                   
	                    headerFormat: '',                                
	                    pointFormat: pointFormat
	                }                                                                            
	            }                                                                                
	        }, 
	        series: series 
		});
	}
};
function highchartsResize(divId){
	if(isNotVal($("#"+divId))){
		var charts=$("#"+divId).highcharts();
		if(charts!=undefined){
			var isFullScreen = isBrowserFullScreen();
			var browserType=getBrowserType();
			if(browserType==5||(!isFullScreen)){
				charts.setSize($("#"+divId).offsetWidth, $("#"+divId).offsetHeight,true);
//				charts.reflow();
//				charts.redraw();
			}
		}
	}
}
function SetEveryOnePointColor(chart) {      // 设置每一个数据点的颜色横向渐变
	var colors = chart.options.colors;
	var pointsList = chart.series[0].points;         //获得第一个序列的所有数据点
    for (var i = 0; i < pointsList.length; i++) {    //遍历设置每一个数据点颜色
        chart.series[0].points[i].update({
            color: {
                linearGradient: { x1: 0, y1: 0, x2: 1, y2: 0 },     //横向渐变效果 如果将x2和y2值交换将会变成纵向渐变效果
                stops: [
                            [0, Highcharts.color(colors[i*2]).setOpacity(1).get('rgba')],
//                            [0.5, 'rgb(255, 255, 255)'],
//                            [0.5, Highcharts.color(colors[i*2]).setOpacity(1).get('rgba')],
                            [1, Highcharts.color(colors[i*2+1]).setOpacity(1).get('rgba')]
                        ]  
            }
        });
    }
}
function SetRodStressEveryOnePointColor(chart) {      // 设置每一个数据点的颜色横向渐变
	var colors = chart.options.colors;
	for(var s=0;s<chart.series.length;s++){
		var pointsList = chart.series[s].points;         //获得第一个序列的所有数据点
		
		var seriesStartColorIndex;
		var seriesEndColorIndex;
		if(s==0){
			seriesStartColorIndex=0;
			seriesEndColorIndex=2;
    	}else{
    		seriesStartColorIndex=1;
			seriesEndColorIndex=3;
    	}
		
		for (var i = 0; i < pointsList.length; i++) {    //遍历设置每一个数据点颜色
			try {
				chart.series[s].points[i].update({
		            color: {
		                linearGradient: { x1: 0, y1: 0, x2: 1, y2: 0 },     //横向渐变效果 如果将x2和y2值交换将会变成纵向渐变效果
		                stops: [
		                            [0, Highcharts.color(colors[seriesStartColorIndex]).setOpacity(1).get('rgba')],
		                            [1, Highcharts.color(colors[seriesEndColorIndex]).setOpacity(1).get('rgba')]
		                        ]  
		            }
		        });
		    } catch (e) {
		    	continue;
		    };
	    }
	}
}
function initTimeAndDataCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, ytitle, color,legend,timeFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
	    Highcharts.setOptions({
	        global: {
	            useUTC: false
	        }
	    });

	    var mychart = new Highcharts.Chart({
	        chart: {
	            renderTo: divId,
	            type: 'spline',
	            shadow: false,
	            borderWidth: 0,
	            zoomType: 'xy',
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
	        },
	        credits: {
	            enabled: false
	        },
	        title: {
	            text: title,
	            style: {
	            	fontSize: chartTitleFontSize
	            }
	        },
	        subtitle: {
	            text: subtitle
	        },
	        colors: color,
	        xAxis: {
	            type: 'datetime',
	            title: {
	                text: xtitle
	            },
	            tickPixelInterval: tickInterval,
	            labels: {
	                formatter: function () {
	                    return Highcharts.dateFormat(timeFormat, this.value);
	                },
	                rotation: 0, //倾斜度，防止数量过多显示不全  
	                step: 2
	            }
	        },
	        yAxis: [{
	            lineWidth: 1,
	        	tickWidth: 1,      // 刻度线宽度
                tickLength: 5,     // 刻度线长度（可选）
	            title: {
	                text: ytitle
//	                ,
//	                style: {
//	                    color: '#000000',
//	                    fontWeight: 'bold'
//	                }
	            },
	            labels: {
	                formatter: function () {
	                    return Highcharts.numberFormat(this.value, 2);
	                }
	            }
	      }],
	        tooltip: {
	            crosshairs: true, //十字准线
	            style: {
	                color: '#333333',
	                fontSize: '12px',
	                padding: '8px'
	            },
	            dateTimeLabelFormats: {
	                millisecond: '%Y-%m-%d %H:%M:%S.%L',
	                second: '%Y-%m-%d %H:%M:%S',
	                minute: '%Y-%m-%d %H:%M',
	                hour: '%Y-%m-%d %H',
	                day: '%Y-%m-%d',
	                week: '%m-%d',
	                month: '%Y-%m',
	                year: '%Y'
	            }
	        },
	        exporting: {
	            enabled: true,
	            filename: title,
	            fallbackToExportServer: false,
	            sourceWidth: $("#"+divId)[0].offsetWidth,
	            sourceHeight: $("#"+divId)[0].offsetHeight,
	            buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadSVG',
	    	    			'separator',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
	        },
	        plotOptions: {
	            spline: {
	                lineWidth: 1,
	                fillOpacity: 0.3,
	                marker: {
	                    enabled: true,
	                    radius: 3, //曲线点半径，默认是4
	                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
	                    states: {
	                        hover: {
	                            enabled: true,
	                            radius: 6
	                        }
	                    }
	                },
	                shadow: true
	            }
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            enabled: legend,
	            borderWidth: 0,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },
	        series: series
	    });
	}
};

window.closeAllTips = function() {
    var tips = document.querySelectorAll('.mini-tips');
    for (var i = tips.length - 1; i >= 0; i--) {
        var tip = tips[i];
        if (tip.parentNode) {
            tip.parentNode.removeChild(tip);
        }
    }
};

/**
 * 根据设备类型获取项目配置（统计标签显示/隐藏）
 * @param {string} deviceType 设备类型ID（可能为逗号分隔）
 * @returns {object} 配置对象，结构同原 ExtJS 版本
 */
function getProjectTabInstanceInfoByDeviceType(deviceType) {
    var r = {
        DeviceRealTimeMonitoring: {
            FESDiagramStatPie: false,
            CommStatusStatPie: false,
            RunStatusStatPie: false,
            NumStatusStatPie: false
        },
        DeviceHistoryQuery: {
            FESDiagramStatPie: false,
            CommStatusStatPie: false,
            RunStatusStatPie: false,
            NumStatusStatPie: false
        },
        AlarmQuery: {
            FESDiagramResultAlarm: false,
            RunStatusAlarm: false,
            CommStatusAlarm: false,
            NumericValueAlarm: false,
            EnumValueAlarm: false,
            SwitchingValueAlarm: false
        }
    };

    // 使用同步 Ajax 请求（保持与原有行为一致）
    $.ajax({
        url: context + '/operationMaintenanceController/getProjectTabInstanceInfoByDeviceType',
        type: 'POST',
        data: { deviceType: deviceType },
        dataType: 'json',
        async: false,          // 关键：同步请求
        timeout: 10000,
        success: function(result) {
            if (result && result.config && result.config.length > 0) {
                for (var i = 0; i < result.config.length; i++) {
                    var item = result.config[i];

                    // ---- DeviceRealTimeMonitoring ----
                    if (item.DeviceRealTimeMonitoring) {
                        var d = item.DeviceRealTimeMonitoring;
                        if (d.FESDiagramStatPie !== undefined && r.DeviceRealTimeMonitoring.FESDiagramStatPie === false) {
                            r.DeviceRealTimeMonitoring.FESDiagramStatPie = d.FESDiagramStatPie;
                        }
                        if (d.CommStatusStatPie !== undefined && r.DeviceRealTimeMonitoring.CommStatusStatPie === false) {
                            r.DeviceRealTimeMonitoring.CommStatusStatPie = d.CommStatusStatPie;
                        }
                        if (d.RunStatusStatPie !== undefined && r.DeviceRealTimeMonitoring.RunStatusStatPie === false) {
                            r.DeviceRealTimeMonitoring.RunStatusStatPie = d.RunStatusStatPie;
                        }
                        if (d.NumStatusStatPie !== undefined && r.DeviceRealTimeMonitoring.NumStatusStatPie === false) {
                            r.DeviceRealTimeMonitoring.NumStatusStatPie = d.NumStatusStatPie;
                        }
                    }

                    // ---- DeviceHistoryQuery ----
                    if (item.DeviceHistoryQuery) {
                        var h = item.DeviceHistoryQuery;
                        if (h.FESDiagramStatPie !== undefined && r.DeviceHistoryQuery.FESDiagramStatPie === false) {
                            r.DeviceHistoryQuery.FESDiagramStatPie = h.FESDiagramStatPie;
                        }
                        if (h.CommStatusStatPie !== undefined && r.DeviceHistoryQuery.CommStatusStatPie === false) {
                            r.DeviceHistoryQuery.CommStatusStatPie = h.CommStatusStatPie;
                        }
                        if (h.RunStatusStatPie !== undefined && r.DeviceHistoryQuery.RunStatusStatPie === false) {
                            r.DeviceHistoryQuery.RunStatusStatPie = h.RunStatusStatPie;
                        }
                        if (h.NumStatusStatPie !== undefined && r.DeviceHistoryQuery.NumStatusStatPie === false) {
                            r.DeviceHistoryQuery.NumStatusStatPie = h.NumStatusStatPie;
                        }
                    }

                    // ---- AlarmQuery ----
                    if (item.AlarmQuery) {
                        var a = item.AlarmQuery;
                        if (a.FESDiagramResultAlarm !== undefined && r.AlarmQuery.FESDiagramResultAlarm === false) {
                            r.AlarmQuery.FESDiagramResultAlarm = a.FESDiagramResultAlarm;
                        }
                        if (a.RunStatusAlarm !== undefined && r.AlarmQuery.RunStatusAlarm === false) {
                            r.AlarmQuery.RunStatusAlarm = a.RunStatusAlarm;
                        }
                        if (a.CommStatusAlarm !== undefined && r.AlarmQuery.CommStatusAlarm === false) {
                            r.AlarmQuery.CommStatusAlarm = a.CommStatusAlarm;
                        }
                        if (a.NumericValueAlarm !== undefined && r.AlarmQuery.NumericValueAlarm === false) {
                            r.AlarmQuery.NumericValueAlarm = a.NumericValueAlarm;
                        }
                        if (a.EnumValueAlarm !== undefined && r.AlarmQuery.EnumValueAlarm === false) {
                            r.AlarmQuery.EnumValueAlarm = a.EnumValueAlarm;
                        }
                        if (a.SwitchingValueAlarm !== undefined && r.AlarmQuery.SwitchingValueAlarm === false) {
                            r.AlarmQuery.SwitchingValueAlarm = a.SwitchingValueAlarm;
                        }
                    }
                }
            }
        },
        error: function(xhr, status, errorThrown) {
            console.warn('获取项目配置失败，将使用默认全禁用配置:', status, errorThrown);
            // 保持 r 中所有值为 false（已初始化）
        }
    });

    return r;
}

function getDeviceTabInstanceInfoByDeviceId(deviceId) {
    var result = {};
    $.ajax({
        url: context + '/operationMaintenanceController/getDeviceTabInstanceInfoByDeviceId',
        type: 'POST',
        data: { deviceId: deviceId },
        dataType: 'json',
        async: false,
        timeout: 10000,
        success: function(data) {
            result = data;
        },
        error: function() {
            console.warn('获取设备标签配置失败，使用默认配置');
            result = { config: { DeviceRealTimeMonitoring: {} }, calculateType: 0 };
        }
    });
    return result;
}

/**
 * 创建报警数量徽章（CSS 实现，高度固定，避免锁定列行高问题）
 * @param {number} number 报警数量（如 3, 12, 105）
 * @param {string} bgColor 背景色（支持 "dc2828" 或 "#dc2828" 格式）
 * @param {string} textColor 文本颜色（可选，默认白色）
 * @returns {string} HTML 字符串
 */
function createAlarmBadge(number, bgColor, textColor) {
    if (!number || number <= 0) return '';
    
    // 统一格式：确保 # 前缀
    bgColor = (bgColor && bgColor.charAt(0) === '#') ? bgColor : '#' + bgColor;
    textColor = (textColor && textColor.charAt(0) === '#') ? textColor : (textColor ? '#' + textColor : '#ffffff');
    
    // 固定高度 10px，line-height 相等，边框半径设为 10px 可保证圆形/胶囊自适应
    // 内边距左右各 3px，最小宽度 10px，数字字体 7px（可读性）
    var style = 'display: inline-block;' +
                'background-color: ' + bgColor + ';' +
                'color: ' + textColor + ';' +
                'border-radius: 10px;' +           // 高度一半，单数圆形多数胶囊
                'padding: 0 3px;' +
                'min-width: 10px;' +               // 最小宽度等于高度
                'height: 10px;' +
                'line-height: 10px;' +
                'text-align: center;' +
                'font-size: 7px;' +                // 字体缩小适应 10px 高度
                'font-weight: normal;' +
                'margin-right: 3px;' +
                'vertical-align: middle;' +
                'box-sizing: border-box;';
    
    return '<span style="' + style + '">' + number + '</span>';
}

function extractPieData(result, tabKey, alarmShowStyle) {
    if (!result) return [{ name: _loginUserLanguageResource.emptyMsg, y: 1 }];
    var list = result.totalRoot || [];
    var data = [];
    for (var i = 0; i < list.length; i++) {
        var item = list[i];
        if (item.itemCode !== 'all' && item.count > 0) {
            var point = {
                name: item.item || item.text || '未知',
                y: item.count || 0
            };

            // ★★★ 根据统计类型和 AlarmShowStyle 设置颜色 ★★★
            if (tabKey === 'CommStatus' && alarmShowStyle && alarmShowStyle.Comm) {
                var comm = alarmShowStyle.Comm;
                if (item.itemCode === 'online') {
                    point.color = '#' + (comm.online ? comm.online.Color : '52c41a');
                } else if (item.itemCode === 'goOnline') {
                    point.color = '#' + (comm.goOnline ? comm.goOnline.Color : 'faad14');
                } else if (item.itemCode === 'offline') {
                    point.color = '#' + (comm.offline ? comm.offline.Color : 'ff4d4f');
                }
            } else if (tabKey === 'RunStatus' && alarmShowStyle && alarmShowStyle.Run) {
                var run = alarmShowStyle.Run;
                var comm = alarmShowStyle.Comm;
                if (item.itemCode === 'run') {
                    point.color = '#' + (run.run ? run.run.Color : '52c41a');
                } else if (item.itemCode === 'stop') {
                    point.color = '#' + (run.stop ? run.stop.Color : 'ff4d4f');
                } else if (item.itemCode === 'noData') {
                    point.color = '#' + (run.noData ? run.noData.Color : '999999');
                } else if (item.itemCode === 'goOnline') {
                    point.color = '#' + (comm.goOnline ? comm.goOnline.Color : 'faad14');
                } else if (item.itemCode === 'offline') {
                    point.color = '#' + (comm.offline ? comm.offline.Color : 'ff4d4f');
                }
            } else if (tabKey === 'NumStatus' && alarmShowStyle && alarmShowStyle.Data) {
                var dataStyle = alarmShowStyle.Data;
                var level = item.level;
                if (level === 0) {
                    point.color = '#' + (dataStyle.Normal ? dataStyle.Normal.BackgroundColor : 'FFFFFF');
                } else if (level === 100) {
                    point.color = '#' + (dataStyle.FirstLevel ? dataStyle.FirstLevel.BackgroundColor : 'DC2828');
                } else if (level === 200) {
                    point.color = '#' + (dataStyle.SecondLevel ? dataStyle.SecondLevel.BackgroundColor : 'F09614');
                } else if (level === 300) {
                    point.color = '#' + (dataStyle.ThirdLevel ? dataStyle.ThirdLevel.BackgroundColor : 'FAE600');
                }
            }
            // FESdiagramResult 不设置颜色，使用 Highcharts 默认
            data.push(point);
        }
    }
    return data.length > 0 ? data : [{ name: _loginUserLanguageResource.emptyMsg, y: 1 }];
}
