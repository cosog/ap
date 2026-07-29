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
            window.location.href = context + '/login';
            return;
        }
        if (originalError) {
            originalError(jqXHR, textStatus, errorThrown);
        } else {
            mini.alert((loginUserLanguageResource && loginUserLanguageResource.ajaxError) || '请求失败');
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
        mini.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.exception + "</font> " + loginUserLanguageResource.objectNotFound);
    }
}

function refreshGrid(grid_id) {
    var grid = mini.get(grid_id);
    if (grid && grid.load) grid.load();
}

function LoadingWin(msg) {
    mini.showMessageBox({
        title: loginUserLanguageResource.tip || '提示',
        message: '<div style="padding-top:20px">' + msg + ',' + loginUserLanguageResource.loadingData + '</div>',
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
        url: context + '/' + space + '/' + action_name,
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
                mini.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteSuccessfully);
            }
            if (result.flag == false) {
                mini.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.deleteFailed + "</font>");
            }
        },
        error: function() {
            mini.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
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
                    mini.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteSuccessfully);
                }
                if (result.flag == false) {
                    mini.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.deleteFailed + "</font>");
                }
                var grid = mini.get(grid_id);
                if (grid && grid.load) grid.load();
            },
            error: function() {
                mini.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
        mini.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.deleteFailed + "</font>");
    }
    return false;
}

// ================================================================
// 12. Excel 导出
// ================================================================
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
    heads = (loginUserLanguageResource.idx || '序号') + "," + heads;
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
        return '<img src="' + context + '/images/icon/alarm-greed.png" style="cursor:pointer"/>';
    } else {
        return '<img src="' + context + '/images/icon/alarm-red.png" style="cursor:pointer"/>';
    }
}

function alarmType(val) {
    if (val == 0) {
        return "<img src='" + context + "/images/icon/normal.png' style='cursor:pointer'/>";
    } else {
        return "<img src='" + context + "/images/icon/exception.png' style='cursor:pointer'/>";
    }
}

function alarmLevelColor(val) {
    if (val == 0) {
        return "<img src='" + context + "/images/icon/alarmcolor/exception0.png' style='cursor:pointer'/>";
    } else if (val == 100) {
        return "<img src='" + context + "/images/icon/alarmcolor/exception1.png' style='cursor:pointer'/>";
    } else if (val == 200) {
        return "<img src='" + context + "/images/icon/alarmcolor/exception2.png' style='cursor:pointer'/>";
    } else if (val == 300) {
        return "<img src='" + context + "/images/icon/alarmcolor/exception3.png' style='cursor:pointer'/>";
    } else {
        return "<img src='" + context + "/images/icon/alarmcolor/exception4.png' style='cursor:pointer'/>";
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
    var alarmStyleInput = mini.get('AlarmShowStyle_Id');
    if (alarmStyleInput) {
        var val = alarmStyleInput.getValue();
        if (isNotVal(val)) {
            try {
                return JSON.parse(val);
            } catch(e) {
                return {};
            }
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
        url: context + '/realTimeMonitoringController/getCalculateTypeDeviceCount',
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
        url: context + '/roleManagerController/getRoleModuleRight',
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
// - initCurveChartFn(catagories, series, tickInterval, divId, title, ytitle, ytitle1)
// - initCurveChartFn1(catagories, series, tickInterval, divId, title, ytitle, ytitle1)
// - initCurveChart(years, values, tickInterval, divId)
// - CurveVFnChartFn(store, divId)
// - initContinuousDiagramChart(pointdata, divId, title, subtitle, xtext, ytext, color)
// - showSurfaceCard(result, divId)
// - showFSDiagramFromPumpcard(result, divId)
// - initSurfaceCardChart(pointdata, gtdata, divId, yAxisMin)
// - showRodPress(result, divId)
// - initRodPressChart(...)
// - showPumpCard(result, divId)
// - initMultiSurfaceCardChart(...)
// - showPumpEfficiency(bxzcData, divId)
// - initPumpEfficiencyChart(...)
// - showPSDiagram(result, divId, title)
// - initPSDiagramChart(...)
// - showASDiagram(result, divId, title)
// - initASDiagramChart(...)
// - showBalanceAnalysisCurveChart(...)
// - initBalanceCurveChart(...)
// - showBalanceAnalysisMotionCurveChart(...)
// - initBalanceCurveChartThreeY(...)
// - initBalanceCurveChartTowY(...)
// - showFSDiagramOverlayChart(...)
// - initFSDiagramOverlayChart(...)
// - initPSDiagramOverlayChart(...)
// - highchartsResize(divId)
// - SetEveryOnePointColor(chart)
// - SetRodStressEveryOnePointColor(chart)
// - initTimeAndDataCurveChartFn(...)
// ================================================================