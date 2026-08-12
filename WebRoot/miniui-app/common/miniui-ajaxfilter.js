/**
 * MiniUI 全局 Ajax 拦截器（替代 ExtJS ajaxfilter.js）
 * 处理 HTTP 状态码、业务错误、会话过期等
 */

(function() {
    // ---------- 工具函数 ----------
    function getContext() {
        return window.context || '';
    }

    // 获取国际化资源（动态获取，优先从父窗口）
    function getLoginUserLanguageResource() {
        // 优先从父窗口获取（适用于 iframe 子页面）
        try {
            if (window.parent && window.parent !== window) {
                var parentRes = window.parent.loginUserLanguageResource;
                if (parentRes && typeof parentRes === 'object') {
                    return parentRes;
                }
            }
        } catch(e) {}
        // 其次从当前窗口获取
        if (window.loginUserLanguageResource && typeof window.loginUserLanguageResource === 'object') {
            return window.loginUserLanguageResource;
        }
        // 最后尝试 miniui-commutils 中定义的变量
        if (typeof _loginUserLanguageResource !== 'undefined') {
            return _loginUserLanguageResource;
        }
        return {};
    }

    // 安全转字符串（提取常见字段，不做默认值处理）
    function safeString(val) {
        if (val === undefined || val === null) return '';
        if (typeof val === 'string') return val;
        if (typeof val === 'object') {
            var props = ['text', 'message', 'msg', 'value', 'html', 'content', 'detail'];
            for (var i = 0; i < props.length; i++) {
                if (val[props[i]] !== undefined && val[props[i]] !== null) {
                    var sub = val[props[i]];
                    if (typeof sub === 'string') return sub;
                    if (typeof sub === 'object') return safeString(sub);
                }
            }
            try {
                var str = String(val);
                if (str !== '[object Object]') return str;
            } catch(e) {}
            return '';
        }
        return String(val);
    }

    // 判断业务是否成功（兼容布尔值和字符串）
    function isSuccess(val) {
        if (val === undefined || val === null) return false;
        if (typeof val === 'boolean') return val === true;
        if (typeof val === 'string') {
            var lower = val.toLowerCase();
            return lower === 'true' || lower === '1';
        }
        return false;
    }

    // 显示错误信息（mini.alert 标准调用：message, title, callback）
    function showErrorMsg(title, msg, callback) {
        var lang = getLoginUserLanguageResource(); // 动态获取
        var msgText = safeString(msg);
        var titleText = safeString(title);
        // 如果 title 为空，传 undefined 让 mini.alert 使用默认标题
        mini.alert(msgText, titleText || undefined, callback || null);
    }

    // 显示详细错误窗口
    function showDetailError(title, code, msg, errorDetail) {
        var lang = getLoginUserLanguageResource(); // 动态获取
        var html = '<div style="padding:10px;">';
        html += '<p><b>' + safeString(title) + '</b></p>';
        if (safeString(code) !== '') {
            html += '<p>' + safeString(lang.exceptionCode) + ': ' + safeString(code) + '</p>';
        }
        if (safeString(msg) !== '') {
            html += '<p>' + safeString(lang.errotCode) + ': ' + safeString(msg) + '</p>';
        }
        if (safeString(errorDetail) !== '') {
            html += '<p>' + safeString(lang.detailedInformation) + ': <pre style="max-height:200px;overflow:auto;">' + safeString(errorDetail) + '</pre></p>';
        }
        html += '</div>';
        mini.showMessageBox({
            title: safeString(lang.tip) || undefined,
            message: html,
            buttons: ['ok'],
            iconCls: 'mini-messagebox-error',
            html: true
        });
    }

    // 跳转登录（使用顶层窗口，避免 iframe 内嵌）
    function redirectLogin() {
        window.top.location.href = getContext() + '/login';
    }

    // 跳转错误页（使用顶层窗口）
    function redirectError() {
        window.top.location.href = getContext() + '/error.jsp';
    }

    // ---------- 全局 Ajax 错误拦截 ----------
    $(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {
        var httpStatus = parseInt(jqXHR.status, 10);
        var responseText = jqXHR.responseText || '';
        var lang = getLoginUserLanguageResource(); // 动态获取

        console.warn('Ajax错误：', {
            url: ajaxSettings.url,
            status: httpStatus,
            statusText: jqXHR.statusText,
            responseText: responseText
        });

        switch (httpStatus) {
            case 400:
                showErrorMsg(lang.tip, lang.ajaxError400);
                break;
            case 404:
                var httpError = jqXHR.getResponseHeader('X-Requested-With') || '';
                if (httpError === 'XMLHttpRequest' && responseText.indexOf('login') > -1) {
                    showErrorMsg(lang.tip, lang.sessionTimedOutInfo, function() {
                        redirectLogin();
                    });
                } else {
                    showErrorMsg(lang.tip, lang.ajaxError404);
                }
                break;
            case 500:
                showErrorMsg(lang.tip, lang.ajaxError500);
                break;
            case 505:
                showErrorMsg(lang.tip, lang.ajaxError505);
                break;
            case 888:
                showErrorMsg(lang.tip, '[' + safeString(lang.unauthorized) + ']' + safeString(lang.contactSupplier), function() {
                    redirectError();
                });
                break;
            case 999:
                showErrorMsg(lang.tip, lang.sessionTimedOutInfo, function() {
                    redirectLogin();
                });
                break;
            default:
                if (jqXHR.statusText === 'abort') {
                    return;
                }
                if (httpStatus !== 200 && responseText.indexOf('login') > -1) {
                    showErrorMsg(lang.tip, lang.sessionTimedOutInfo, function() {
                        redirectLogin();
                    });
                } else {
                    showErrorMsg(lang.tip, jqXHR.statusText || lang.requestFailed);
                }
        }
    });

    // ---------- 处理业务成功标志 ----------
    $(document).ajaxComplete(function(event, jqXHR, ajaxSettings) {
        if (jqXHR.status === 200) {
            var responseText = jqXHR.responseText;
            if (responseText) {
                try {
                    var json = JSON.parse(responseText);
                    var errorOut = jqXHR.getResponseHeader('errorOut');
                    if (errorOut) {
                        var title = safeString(json.title);
                        var code = safeString(json.code);
                        var msg = safeString(json.msg) || safeString(json.message);
                        var errorDetail = safeString(json.error);
                        showDetailError(title, code, msg, errorDetail);
                        if (ajaxSettings.success) {
                            ajaxSettings.success = null;
                        }
                        return;
                    }

                    var success = json.success;
                    if (success !== undefined && success !== null) {
                        if (!isSuccess(success)) {
                            var title = safeString(json.title);
                            var code = safeString(json.code);
                            var msg = safeString(json.msg) || safeString(json.message);
                            var errorDetail = safeString(json.error);
                            showDetailError(title, code, msg, errorDetail);
                            if (ajaxSettings.success) {
                                ajaxSettings.success = null;
                            }
                            return;
                        }
                    }
                } catch (e) {
                    // 非 JSON 响应，忽略
                }
            }
        }
    });

})();