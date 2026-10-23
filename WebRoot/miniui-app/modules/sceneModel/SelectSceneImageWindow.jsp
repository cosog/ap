<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#fff; font-family:"Microsoft YaHei", Arial, sans-serif; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-body { padding: 0 !important; }
        .footer { padding: 8px 10px; border-top: 1px solid #e8e8e8; background: #fafafa; text-align: right; }
        
        /* ============================================================
           画廊卡片样式（对齐 ExtJS DataView 效果）
           ============================================================ */
        .image-gallery {
            padding: 10px;
            display: flex;
            flex-wrap: wrap;
            align-content: flex-start;
            gap: 10px;
            overflow-y: auto;
            height: 100%; /* 由外层 flex 撑开 */
            box-sizing: border-box;
        }
        
        .scene-image-item {
            width: 140px;
            height: 170px;
            border: 2px solid #e9ecef;
            border-radius: 6px;
            padding: 8px;
            background: #fff;
            cursor: pointer;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: space-between;
            box-sizing: border-box;
            transition: all 0.2s ease;
            user-select: none;
        }
        
        .scene-image-item:hover {
            border-color: #66b1ff;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }
        
        /* 选中状态：对应 ExtJS 选中的蓝色粗边框 */
        .scene-image-item.selected {
            border-color: #4a6cf7;
            background-color: #f4f7ff;
            box-shadow: 0 0 6px rgba(74, 108, 247, 0.4);
        }
        
        .scene-image-item .img-wrapper {
            height: 110px;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }
        
        .scene-image-item img {
            max-width: 120px;
            max-height: 110px;
            object-fit: contain;
            /* 解决拖拽图片时选中变成系统蓝色的问题 */
            -webkit-user-drag: none; 
        }
        
        .scene-image-item .name {
            font-size: 12px;
            color: #666;
            margin-top: 6px;
            width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        
        .scene-image-item.selected .name {
            color: #4a6cf7;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div style="width:100%;height:100%;display:flex;flex-direction:column;">
    <!-- 工具栏 -->
    <div style="padding: 4px 8px; border-bottom: 1px solid #e8e8e8; background: #fafafa; display:flex; justify-content:space-between; align-items:center; flex-shrink:0;">
        <div>
            <input id="searchField" class="mini-textbox" style="width:200px;" onvaluechanged="onSearchChange" />
        </div>
        <div>
            <button id="btnUpload" class="mini-button" plain="true" onclick="onUploadClick()"></button>
            <button id="btnDeleteImg" class="mini-button" iconCls="delete" plain="true" onclick="onDeleteClick()" enabled="false"></button>
            <button id="btnRefresh" class="mini-button" iconCls="note-refresh" plain="true" onclick="loadImages()"></button>
        </div>
    </div>
    
    <!-- 画廊区域 -->
    <div style="flex:1; overflow:hidden; position:relative;">
        <div id="imageGallery" class="image-gallery">
            <!-- 动态渲染卡片 -->
        </div>
    </div>
    
    <!-- 底部按钮 -->
    <div class="footer">
        <button id="btnConfirm" class="mini-button" iconCls="save" onclick="onConfirm()"></button>
        <button id="btnCancel" class="mini-button" iconCls="cancel" onclick="onCancel()"></button>
    </div>
</div>

<input type="file" id="fileInput" accept="image/*,.svg" style="display:none;" onchange="uploadFile(this)" />

<script>
    var context = '<%=context%>';
    var currentImageUrl = '';
    var allImages = [];
    var currentFilteredImages = [];

    $(document).ready(function() {
        mini.parse();
        initI18n();
        loadImages();
    });

    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.selectSceneImage;
        
        var searchField = mini.get('searchField');
        if (searchField) searchField.setEmptyText(R.searchFileName + '...');
        
        var btnUpload = mini.get('btnUpload');
        if (btnUpload) btnUpload.setText('📤 ' + R.uploadLocalImage);
        
        var btnDeleteImg = mini.get('btnDeleteImg');
        if (btnDeleteImg) {
            btnDeleteImg.setText(R.deleteImage);
            btnDeleteImg.setTooltip(R.selectImgFirstTip);
        }
        
        var btnRefresh = mini.get('btnRefresh');
        if (btnRefresh) btnRefresh.setText(R.refresh);
        
        var btnConfirm = mini.get('btnConfirm');
        if (btnConfirm) btnConfirm.setText(R.confirm);
        
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(R.cancel);
    }

    // 父页面传参
    function setData(data) {
        currentImageUrl = data.currentImageUrl || '';
        
        // 如果数据已加载，立即执行渲染并高亮
        if (allImages.length > 0) {
            filterImages(mini.get('searchField').getValue() || '');
        }
    }

    // 加载图片列表
    function loadImages() {
        var R = _loginUserLanguageResource;
        $.ajax({
            url: context + '/sceneModelController/getSceneImageList',
            type: 'POST',
            dataType: 'json',
            success: function(result) {
                allImages = result.data || [];
                filterImages(mini.get('searchField').getValue() || '');
            },
            error: function() {
                mini.alert(R.requestFailed, R.error);
            }
        });
    }

    function filterImages(keyword) {
        var R = _loginUserLanguageResource;
        if (!keyword) {
            currentFilteredImages = allImages;
        } else {
            currentFilteredImages = allImages.filter(function(item) {
                return (item.name || '').toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
            });
        }
        renderGallery();
    }

    function onSearchChange(e) {
        filterImages(e.value);
    }

    // ============================================================
    // 核心渲染：构建画廊
    // ============================================================
    function renderGallery() {
        var container = document.getElementById('imageGallery');
        var R = _loginUserLanguageResource;
        container.innerHTML = '';

        if (currentFilteredImages.length === 0) {
            container.innerHTML = '<div style="width:100%; text-align:center; padding:40px; color:#aaa;">' + R.noImages + '</div>';
            return;
        }

        // 容错提取纯净的 URL（防止 _t 时间戳干扰匹配）
        var cleanTargetUrl = currentImageUrl ? currentImageUrl.split('&_t=')[0].split('?_t=')[0] : '';

        var html = '';
        for (var i = 0; i < currentFilteredImages.length; i++) {
            var item = currentFilteredImages[i];
            var itemUrl = item.url || '';
            var cleanItemUrl = itemUrl.split('&_t=')[0].split('?_t=')[0];
            var isSelected = (cleanItemUrl === cleanTargetUrl);

            var selectedClass = isSelected ? ' selected' : '';
            
            // 转义 name，防止带有单引号导致 HTML 解析错误
            var safeName = (item.name || '').replace(/'/g, "\\'").replace(/"/g, '&quot;');

            html += '<div class="scene-image-item' + selectedClass + '" data-url="' + itemUrl + '" data-name="' + safeName + '">';
            html += '  <div class="img-wrapper">';
            html += '    <img src="' + itemUrl + '" onerror="this.style.display=\'none\'" />';
            html += '  </div>';
            html += '  <div class="name" title="' + safeName + '">' + item.name + '</div>';
            html += '</div>';
        }
        
        container.innerHTML = html;

        // 绑定点击和双击事件
        var items = container.querySelectorAll('.scene-image-item');
        for (var j = 0; j < items.length; j++) {
            items[j].onclick = function() {
                // 移除其他选中态
                for(var k=0; k<items.length; k++) { items[k].classList.remove('selected'); }
                // 加上当前选中态
                this.classList.add('selected');
                
                // 更新当前操作的 URL
                currentImageUrl = this.getAttribute('data-url');
                onSelectionChanged();
            };
            
            items[j].ondblclick = function() {
                onConfirm();
            };
        }

        // 滚动到选中的卡片
        var selectedEl = container.querySelector('.scene-image-item.selected');
        if (selectedEl) {
            setTimeout(function() {
                selectedEl.scrollIntoView({ block: 'nearest', inline: 'nearest' });
            }, 100);
        } else {
            // 如果没有选中，默认选中第一个，方便用户操作删除
            if (items.length > 0) {
                items[0].click();
            } else {
                onSelectionChanged();
            }
        }
    }

    // 选中状态变化：控制删除按钮
    function onSelectionChanged() {
        var R = _loginUserLanguageResource;
        var btnDelete = mini.get('btnDeleteImg');

        if (!currentImageUrl) {
            btnDelete.setEnabled(false);
            btnDelete.setTooltip(R.selectImgFirstTip);
            return;
        }

        var cleanTargetUrl = currentImageUrl.split('&_t=')[0].split('?_t=')[0];
        
        // 因为 currentImageUrl 会在点击时变化，这里我们需要知道“当前画布真正使用”的图
        // 注意：传入的初始画布图我们存起来，不要被点击覆盖
        var canvasUrl = window.initialCanvasUrl || ''; 
        var cleanCanvasUrl = canvasUrl.split('&_t=')[0].split('?_t=')[0];

        if (cleanTargetUrl === cleanCanvasUrl) {
            btnDelete.setEnabled(false);
            btnDelete.setTooltip(R.cannotDeleteUsedImgTip);
        } else {
            btnDelete.setEnabled(true);
            btnDelete.setTooltip(R.deleteSelectedImgTip);
        }
    }

    // 需要把外部传进来的画布图片存一份，避免被点击覆盖
    var _tempSetData = setData;
    setData = function(data) {
        window.initialCanvasUrl = data.currentImageUrl || '';
        _tempSetData(data);
    };

    // ============================================================
    // 删除图片
    // ============================================================
    function onDeleteClick() {
        var R = _loginUserLanguageResource;
        if (!currentImageUrl) return;

        var canvasUrl = window.initialCanvasUrl || '';
        if (currentImageUrl.split('&_t=')[0].split('?_t=')[0] === canvasUrl.split('&_t=')[0].split('?_t=')[0]) {
            mini.alert(R.cannotDeleteUsedImgAlert, R.tip);
            return;
        }

        // 获取当前选中的名称
        var selectedEl = document.querySelector('.scene-image-item.selected');
        if (!selectedEl) return;
        var name = selectedEl.getAttribute('data-name');
        var url = selectedEl.getAttribute('data-url');

        var confirmInfo = R.confirmDeleteImage + ' [<font color="red">' + name + '</font>]?';

        mini.confirm(confirmInfo, R.tip, function(action) {
            if (action !== 'ok') return;
            mini.mask({ el: document.body, html: R.deleting + '...' });
            $.ajax({
                url: context + '/sceneModelController/deleteSceneImage',
                type: 'POST',
                data: { name: name },
                dataType: 'json',
                success: function(res) {
                    mini.unmask(document.body);
                    if (res.success) {
                        mini.alert(R.deleteSuccessfully, R.tip);
                        currentImageUrl = ''; // 重置当前选中
                        loadImages();
                    } else {
                        mini.alert(R.deleteFailed, R.tip);
                    }
                },
                error: function() {
                    mini.unmask(document.body);
                    mini.alert(R.networkErrorCannotDelete, R.error);
                }
            });
        });
    }

    // ============================================================
    // 上传图片
    // ============================================================
    function onUploadClick() {
        document.getElementById('fileInput').click();
    }

    function uploadFile(input) {
        var R = _loginUserLanguageResource;
        var file = input.files[0];
        if (!file) return;

        var formData = new FormData();
        formData.append('file', file);

        mini.mask({ el: document.body, html: R.uploading + '...' });

        $.ajax({
            url: context + '/sceneModelController/uploadSceneImage',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(res) {
                mini.unmask(document.body);
                input.value = ''; // 清空 input
                if (res.success) {
                    mini.alert(R.uploadSuccess, R.tip);
                    loadImages();
                } else {
                    mini.alert(R.uploadFail, R.tip);
                }
            },
            error: function() {
                mini.unmask(document.body);
                input.value = '';
                mini.alert(R.uploadFail, R.error);
            }
        });
    }

    // ============================================================
    // 确定与取消
    // ============================================================
    function onConfirm() {
        var R = _loginUserLanguageResource;
        if (!currentImageUrl) {
            mini.alert(R.selectImgFirst, R.tip);
            return;
        }
        
        if (window.onConfirmCallback) {
            window.onConfirmCallback(currentImageUrl);
        }
        window.CloseOwnerWindow('ok');
    }

    function onCancel() {
        window.CloseOwnerWindow('cancel');
    }
</script>
</body>
</html>