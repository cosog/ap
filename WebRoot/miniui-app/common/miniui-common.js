// ================================================================
// miniui-common.js - MiniUI 全局配置
// ================================================================

(function() {
    // 1. 确保全局变量存在
    window.context = window.context || '';
    window.loginUserLanguageResource = window.loginUserLanguageResource || {};
    window.configFile = window.configFile || { ap: { others: {} } };
    window.__accessToken = window.__accessToken || '';

    // 2. MiniUI 加载器配置
    if (typeof mini !== 'undefined' && mini.loader) {
        mini.loader = {
            base: context + '/scripts/miniui/',
            skin: 'metro-blue',
            locale: loginUserLanguage || 'zh_CN'
        };
    }

    // 3. 全局 AJAX 拦截
    if (typeof mini !== 'undefined' && mini.ajax) {
        var originalAjax = mini.ajax;
        mini.ajax = function(options) {
            options = options || {};
            options.type = options.type || 'post';
            options.dataType = options.dataType || 'json';
            options.timeout = options.timeout || 30000;

            var token = window.__accessToken || '';
            if (token) {
                options.headers = options.headers || {};
                options.headers['X-Access-Token'] = token;
            }

            var originalError = options.error;
            options.error = function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401 || jqXHR.status === 999) {
                    window.location.href = context + '/login';
                    return;
                }
                if (originalError) {
                    originalError(jqXHR, textStatus, errorThrown);
                } else {
                    var msg = (loginUserLanguageResource && loginUserLanguageResource.ajaxError) 
                        || '请求失败，请稍后重试';
                    if (typeof mini !== 'undefined' && mini.alert) {
//                        mini.alert(msg);
                    } else {
//                        alert(msg);
                    }
                }
            };

            return originalAjax(options);
        };
    }

    // 4. 兼容 ExtJS 的 Message 风格（过渡用）
    window.miniAlert = function(msg, title) {
        if (typeof mini !== 'undefined' && mini.alert) {
//            mini.alert(msg, title || '提示');
        } else {
//            alert(msg);
        }
    };

    window.miniConfirm = function(msg, callback, title) {
        if (typeof mini !== 'undefined' && mini.confirm) {
            mini.confirm(msg, title || '提示', function(action) {
                if (action === 'ok') {
                    callback(true);
                } else {
                    callback(false);
                }
            });
        } else {
            if (confirm(msg)) {
                callback(true);
            } else {
                callback(false);
            }
        }
    };

    console.log('miniui-common.js 加载完成');
})();