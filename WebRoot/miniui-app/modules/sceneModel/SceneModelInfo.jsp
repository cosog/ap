<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <!-- 统一引入 tags-miniui.jsp，里面已经包含了 canvas-select 的相关资源 -->
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    
    <!-- 引入独立的业务逻辑 JS -->
    <script src="js/sceneModelInfo.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f0f2f5; font-family:"Microsoft YaHei", Arial, sans-serif; }
        
        /* ============================================================
           去除 MiniUI 默认的边框、Padding，让组件完美贴合 Splitter
           ============================================================ */
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; background: #fafafa !important; }
        .mini-panel-body { border: 0 !important; }
        .mini-splitter-border { border: 0 !important; }
        .mini-splitter-pane { padding: 0 !important; }
        .mini-splitter-handler { background: transparent !important; border-left: 1px solid #e8e8e8 !important; }
        
        /* 工具条统一背景与边距 */
        .mini-panel-toolbar { background: #fafafa !important; border-bottom: 1px solid #e8e8e8 !important; padding: 4px 8px !important; }
        
        /* 画布容器 */
        .canvas-wrapper { position: relative; width: 100%; height: 100%; background: #f8f9fa; border: 2px dashed #dee6e6; border-radius: 8px; overflow: hidden; box-sizing: border-box; }
        .scene-canvas { display: block; width: 100%; height: 100%; }
        .scene-label-layer { position: absolute; top: 0; left: 0; right: 0; bottom: 0; pointer-events: none; overflow: hidden; z-index: 10; }
        .scene-canvas-label { position: absolute; padding: 4px 10px; border-radius: 4px; white-space: nowrap; font-weight: 600; transform: translate(-50%, -100%); box-shadow: 0 2px 6px rgba(0,0,0,0.18); pointer-events: auto; cursor: pointer; }
        
        /* ============================================================
           样式设置表单行 - 调小间距，提升紧凑度
           ============================================================ */
        .style-form-row {
            display: flex;
            align-items: center;
            margin-bottom: 5px;
        }
        
        .style-form-row label {
            display: inline-block;
            width: 105px;
            text-align: left; 
            font-size: 13px;
            color: #333;
            flex-shrink: 0;
            padding-right: 8px;
            box-sizing: border-box;
        }
        
        .style-form-row .input-wrapper {
            flex: 1;
            min-width: 0;
        }

        .style-form-row .input-wrapper > .mini-textbox,
        .style-form-row .input-wrapper > .mini-spinner,
        .style-form-row .input-wrapper > .mini-combobox {
            width: 100% !important;
            box-sizing: border-box;
        }

        .style-form-row .input-wrapper > .sp-replacer {
            width: 100% !important;
            box-sizing: border-box;
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 2px;
        }
        
        .style-form-row .input-wrapper > .sp-replacer .sp-preview {
            width: calc(100% - 20px);
            height: 20px;
            margin-right: 0;
            border-radius: 1px;
        }

        .style-form-row .input-wrapper > .mini-checkbox {
            margin-top: 2px;
        }

        /* ★ fieldset 样式调小内边距和外边距 */
        fieldset { 
            border: 1px solid #ddd; 
            padding: 8px 10px;
            margin-bottom: 8px;
            border-radius: 4px; 
            background: #fff; 
        }
        fieldset legend { 
            font-weight: bold; 
            color: #333; 
            padding: 0 4px; 
            font-size: 13px;
        }
    </style>
    <script>
    	var context = '<%=context%>';
	</script>
</head>
<body>

<div id="mainSplitter" class="mini-splitter" style="width:100%;height:100%;" vertical="false">

    <!-- ================= 左侧：场景模型列表 (25%) ================= -->
    <div size="25%" showCollapseButton="true" collapseDirection="left" minSize="200" maxSize="500">
        <div id="sceneModelPanel" class="mini-panel" style="width:100%;height:100%;"
             showHeader="false" showToolbar="true" showCloseButton="false" showCollapseButton="false"
             bodyStyle="padding:0;">
            <!-- 工具条：左刷新，右新增/删除/保存 -->
            <div property="toolbar">
                <table style="width:100%;border-collapse:collapse;">
                    <tr>
                        <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                            <button id="btnRefreshScene" class="mini-button" iconCls="note-refresh" plain="true" onclick="onSceneRefresh()"></button>
                        </td>
                        <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                            <button id="btnAddScene" class="mini-button" iconCls="add" plain="true" onclick="onSceneAdd()"></button>
                            <button id="btnDelScene" class="mini-button" iconCls="delete" plain="true" onclick="onSceneDel()"></button>
                            <button id="btnSaveScene" class="mini-button" iconCls="save" plain="true" onclick="onSceneSave()"></button>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="sceneModelGrid" class="mini-datagrid" style="width:100%;height:100%;"
			     idField="modelId"
			     allowResize="false"
			     allowAlternating="true"
			     showPager="false"
			     pageSize="100"
			     showPageInfo="false"
			     multiSelect="false"
			     allowCellEdit="false"
			     allowCellSelect="false"
			     showEmptyText="true"
			     dataField="totalRoot"
			     totalField="totalCount"
			     cellEditAction="celldblclick">
			    <div property="columns"></div>
			    <div property="emptyText" class="empty-msg"></div>
			</div>
        </div>
    </div>

    <!-- ================= 右侧：包含统一工具栏的容器 (75%) ================= -->
    <div size="75%" showCollapseButton="false">
        <div id="rightTopPanel" class="mini-panel" style="width:100%;height:100%;"
             showHeader="false" showToolbar="true" showCloseButton="false" showCollapseButton="false"
             bodyStyle="padding:0;">
             
            <!-- 统管右侧（画布+属性）的工具栏 -->
            <div property="toolbar">
                <table style="width:100%;border-collapse:collapse;">
                    <tr>
                        <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                            <button id="btnSelect" class="mini-button" plain="true" enableToggle="true" style="margin-right:2px;" onclick="onCanvasSelect()"></button>
                            <button id="btnRect" class="mini-button" plain="true" enableToggle="true" style="margin-right:2px;" onclick="onCanvasRect()"></button>
                            <button id="btnPoint" class="mini-button" plain="true" enableToggle="true" style="margin-right:2px;" onclick="onCanvasPoint()"></button>
                            <span class="mini-toolbar-sep" style="display:inline-block;width:1px;height:16px;background:#ddd;margin:0 6px;vertical-align:middle;"></span>
                            <button id="btnZoomIn" class="mini-button" plain="true" style="margin-right:2px;" onclick="onCanvasZoomIn()"></button>
                            <button id="btnZoomOut" class="mini-button" plain="true" style="margin-right:2px;" onclick="onCanvasZoomOut()"></button>
                            <button id="btnFitZoom" class="mini-button" plain="true" style="margin-right:2px;" onclick="onCanvasFitZoom()"></button>
                            <span class="mini-toolbar-sep" style="display:inline-block;width:1px;height:16px;background:#ddd;margin:0 6px;vertical-align:middle;"></span>
                            <button id="btnSelectImg" class="mini-button" plain="true" style="margin-right:2px;" onclick="onSelectServerImage()"></button>
                            <button id="btnUploadImg" class="mini-button" plain="true" style="margin-right:2px;" onclick="onUploadLocalImage()"></button>
                        </td>
                        <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                            <button id="btnSaveAnnotation" class="mini-button" iconCls="save" plain="true" onclick="onSaveAnnotations()"></button>
                        </td>
                    </tr>
                </table>
            </div>
            
            <!-- 内部 Splitter：左画布，右属性 -->
            <div id="rightSplitter" class="mini-splitter" style="width:100%;height:100%;" vertical="false">

                <!-- 中间：画布面板 (66%) -->
                <div size="66%" showCollapseButton="false" minSize="400">
                    <div id="canvasPanel" class="mini-panel" style="width:100%;height:100%;"
                         showHeader="false" showToolbar="false" showCloseButton="false" showCollapseButton="false"
                         bodyStyle="padding:0;overflow:hidden;">
                        <div class="canvas-wrapper">
                            <canvas id="sceneCanvas" class="scene-canvas"></canvas>
                            <div id="sceneLabelLayer" class="scene-label-layer"></div>
                        </div>
                    </div>
                </div>

                <!-- 右侧：innerSplitter (34%) -->
                <div size="34%" showCollapseButton="true" collapseDirection="right" minSize="260">
                    <div id="innerSplitter" class="mini-splitter" style="width:100%;height:100%;" vertical="true">

                        <!-- 上半：样式设置 (50%) -->
                        <div size="50%" showCollapseButton="false" minSize="150">
                            <div id="stylePanel" class="mini-panel" style="width:100%;height:100%;"
                                 showHeader="true" showToolbar="false" showCloseButton="false" showCollapseButton="false"
                                 bodyStyle="padding:8px;overflow-y:auto;background:#f5f7fa;">
                                 
                                <fieldset>
                                    <legend id="legendGraphicStyle"></legend>
                                    <div class="style-form-row">
                                        <label id="lblStrokeStyle"></label>
                                        <div class="input-wrapper"><input id="strokeColor" type="text" /></div>
                                    </div>
                                    <div class="style-form-row">
                                        <label id="lblLineWidth"></label>
                                        <div class="input-wrapper"><input id="lineWidth" class="mini-spinner" minValue="0" maxValue="10" value="1" /></div>
                                    </div>
                                    <div class="style-form-row">
                                        <label id="lblFillStyle"></label>
                                        <div class="input-wrapper"><input id="fillColor" type="text" /></div>
                                    </div>
                                    <div class="style-form-row">
                                        <label id="lblCtrlRadius"></label>
                                        <div class="input-wrapper"><input id="ctrlRadius" class="mini-spinner" minValue="2" maxValue="10" value="3" /></div>
                                    </div>
                                </fieldset>
                                
                                <fieldset>
                                    <legend id="legendLabelStyle"></legend>
                                    <div class="style-form-row">
                                        <label id="lblLabelFont"></label>
                                        <div class="input-wrapper"><input id="labelFont" class="mini-combobox" data="['11px','12px','13px','14px']" value="12px" allowInput="false" /></div>
                                    </div>
                                    <div class="style-form-row">
                                        <label id="lblLabelBg"></label>
                                        <div class="input-wrapper"><input id="labelBgColor" type="text" /></div>
                                    </div>
                                    <div class="style-form-row">
                                        <label id="lblLabelText"></label>
                                        <div class="input-wrapper"><input id="labelTextColor" type="text" /></div>
                                    </div>
                                    <div class="style-form-row">
                                        <label id="lblLabelUp"></label>
                                        <div class="input-wrapper"><input id="labelUp" class="mini-checkbox" value="true" /></div>
                                    </div>
                                    <div class="style-form-row">
                                        <label id="lblHideDeviceName"></label>
                                        <div class="input-wrapper"><input id="hideDeviceName" class="mini-checkbox" value="true" /></div>
                                    </div>
                                    <div class="style-form-row">
                                        <label id="lblHideFieldName"></label>
                                        <div class="input-wrapper"><input id="hideFieldName" class="mini-checkbox" value="false" /></div>
                                    </div>
                                    <!-- ★ 隐藏标签：默认隐藏此行 -->
                                    <div class="style-form-row" style="display: none;">
                                        <label id="lblHideLabel"></label>
                                        <div class="input-wrapper"><input id="hideLabel" class="mini-checkbox" value="false" /></div>
                                    </div>
                                </fieldset>
                                
                            </div>
                        </div>

                        <!-- 下半：标注列表 (50%) -->
                        <div size="50%" showCollapseButton="true" collapseDirection="bottom" minSize="150">
                            <div id="annotationPanel" class="mini-panel" style="width:100%;height:100%;"
                                 showHeader="true" showToolbar="false" showCloseButton="false" showCollapseButton="false"
                                 bodyStyle="padding:0;">
                                <div id="bindingTree" class="mini-tree" style="width:100%;height:100%;"
                                     showTreeIcon="true" textField="text" idField="id" parentField="pid" resultAsTree="true"
                                     showCheckBox="false"></div>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
            
        </div>
    </div>

</div>

<script>
    $(document).ready(function () {
        mini.parse();
        initSceneModelPage();
    });
</script>
</body>
</html>