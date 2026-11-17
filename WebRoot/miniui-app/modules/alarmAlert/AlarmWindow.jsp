<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!-- 报警主窗口 -->
<div id="AlarmWindow" class="mini-window" title="报警" style="width:900px;height:540px;display:none;" showShadow="true" allowResize="false" allowDrag="true" showCloseButton="false" showCollapseButton="true" showModal="false" headerStyle="cursor:pointer;">
    <div class="mini-tabs" id="alarmTabs" style="width:100%;height:100%;" onactivechanged="onTabChanged">
        <!-- 待确认面板 -->
        <div title="待确认" name="pending" showCloseButton="false">
            <div class="mini-panel" showHeader="false" showToolbar="true" style="width:100%;height:100%;" borderStyle="border:0;">
                <div property="toolbar" style="padding:2px 5px;">
                    <span id="muteTip" style="display:none; color:#dc2828; font-weight:bold; cursor:pointer; margin-right:10px;" onclick="unlockSound()">⚠️ 当前浏览器已静音，请点击此处开启声音提醒！</span>
                    <input id="muteCheckbox" class="mini-checkbox" text="静音" onvaluechanged="onMuteChange" />
                    <span id="pendingSelText" style="margin-left:10px;font-size:12px;">已选：0</span>
                    <div style="float:right;">
                        <a class="mini-button mini-button-primary" onclick="doAckSelected()" plain="true">确认选中</a>
                        <a class="mini-button" onclick="doAckAll()" plain="true">全部确认</a>
                    </div>
                </div>
                <div class="mini-treegrid" id="pendingGrid" style="width:100%;height:100%;" rowStyleRenderer="rowStyle" showTreeIcon="true" resultAsTree="true" treeColumn="text" allowAlternating="true" oncheckchanged="onCheckChanged" showHGridLines="false" showVGridLines="false" showCheckbox="true" ondrawnode=onTreeDrawnode>
                    <div property="columns">
                        <div field="text" name="text" width="200" headerAlign="center" align="left" renderer="textRenderer" treeColumn="true">设备报警内容</div>
                        <div field="alarmLevel" name="alarmLevel" width="60" headerAlign="center" align="center" renderer="levelRenderer">级别</div>
                        <div field="value" name="value" width="80" headerAlign="center" align="center">报警值</div>
                        <div field="alarminfo" name="alarminfo" width="100" headerAlign="center" align="center">报警信息</div>
                        <div field="acqTime" name="acqTime" width="140" headerAlign="center" align="center">发生时间</div>
                        <div field="status" name="status" width="160" headerAlign="center" align="center" renderer="statusRenderer">报警处理状态</div>
                        <div name="action" width="70" headerAlign="center" align="center" renderer="actionRendererAck">操作</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 待消警面板 -->
        <div title="待消警" name="acked" showCloseButton="false">
            <div class="mini-panel" showHeader="false" showToolbar="true" style="width:100%;height:100%;" borderStyle="border:0;">
                <div property="toolbar" style="padding:2px 5px;">
                    <span id="ackedSelText" style="font-size:12px;">已选：0</span>
                    <span id="ackedCountText" style="margin-left:10px;font-size:12px;">已确认：0</span>
                    <div style="float:right;">
                        <a class="mini-button mini-button-primary" onclick="doSuppressSelected()" plain="true">人工消警选中</a>
                        <a class="mini-button" onclick="doSuppressAll()" plain="true">全部人工消警</a>
                        <a class="mini-button" onclick="openSuppressedList()" plain="true">查看人工消警记录</a>
                    </div>
                </div>
                <div class="mini-treegrid" id="ackGrid" style="width:100%;height:100%;" rowStyleRenderer="rowStyle" showTreeIcon="true" resultAsTree="true" treeColumn="text" allowAlternating="true" oncheckchanged="onCheckChanged" showHGridLines="false" showVGridLines="false" showCheckbox="true" ondrawnode=onTreeDrawnode>>
                    <div property="columns">
                        <div field="text" name="text" width="200" headerAlign="center" align="left" renderer="textRenderer" treeColumn="true">设备报警内容</div>
                        <div field="alarmLevel" name="alarmLevel" width="60" headerAlign="center" align="center" renderer="levelRenderer">级别</div>
                        <div field="value" name="value" width="80" headerAlign="center" align="center">报警值</div>
                        <div field="alarminfo" name="alarminfo" width="100" headerAlign="center" align="center">报警信息</div>
                        <div field="acqTime" name="acqTime" width="140" headerAlign="center" align="center">发生时间</div>
                        <div field="status" name="status" width="160" headerAlign="center" align="center" renderer="statusRenderer">报警处理状态</div>
                        <div name="action" width="90" headerAlign="center" align="center" renderer="actionRendererSuppress">操作</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 人工消警面板 (默认隐藏) -->
        <div title="人工消警" name="suppressed" showCloseButton="false" visible="false">
            <div class="mini-panel" showHeader="false" showToolbar="true" style="width:100%;height:100%;" borderStyle="border:0;">
                <div property="toolbar" style="padding:2px 5px;">
                    <span id="suppressedCountText" style="font-size:12px;">已消警：0</span>
                </div>
                <div class="mini-treegrid" id="suppressedGrid" style="width:100%;height:100%;" rowStyleRenderer="rowStyle" showTreeIcon="true" resultAsTree="true" allowAlternating="true" showHGridLines="false" showVGridLines="false">
                    <div property="columns">
                        <div field="text" name="text" width="200" headerAlign="center" align="left" treeColumn="true">设备报警内容</div>
                        <div field="alarmLevel" name="alarmLevel" width="60" headerAlign="center" align="center" renderer="levelRenderer">级别</div>
                        <div field="suppressReason" name="suppressReason" width="120" headerAlign="center" align="center">消警原因</div>
                        <div field="suppressUser" name="suppressUser" width="80" headerAlign="center" align="center">消警人</div>
                        <div field="suppressTime" name="suppressTime" width="140" headerAlign="center" align="center">消警时间</div>
                        <div field="suppressEndTime" name="suppressEndTime" width="140" headerAlign="center" align="center">消警结束时间</div>
                        <div name="action" width="120" headerAlign="center" align="center" renderer="actionRendererCancel">操作</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 人工消警录入弹窗 (通用，修改时也复用) -->
<div id="suppressDialog" class="mini-window" title="人工消警" style="width:450px;height:230px;display:none;" modal="true">
    <div class="mini-panel" showHeader="false" showToolbar="true" style="width:100%;height:100%;" borderStyle="border:0;">
        <div property="toolbar" style="padding:2px 5px;text-align:right;">
            <a class="mini-button mini-button-primary" onclick="confirmSuppress() plain=" true"">确定</a>
            <a class="mini-button" onclick="closeSuppressDialog()" plain="true">取消</a>
        </div>
        <div style="padding:15px;">
            <table style="width:100%;line-height:35px;">
                <tr>
                    <td width="100" align="right">消警原因：</td>
                    <td><input id="suppressReason" class="mini-textarea" style="width:100%;height:60px;" required="true" emptyText="请填写消警原因" /></td>
                </tr>
                <tr>
                    <td align="right">消警时长(h)：</td>
                    <td><input id="suppressDuration" class="mini-spinner" minValue="0" maxValue="8760" value="24" style="width:100%;" required="true" /></td>
                </tr>
            </table>
        </div>
    </div>
</div>

<!-- ★ 独立的人工消警记录查看弹窗 -->
<div id="suppressedListWindow" class="mini-window" title="人工消警记录" style="width:900px;height:500px;display:none;" modal="true">
    <div class="mini-panel" showHeader="false" showToolbar="true" style="width:100%;height:100%;" borderStyle="border:0;">
        <div class="mini-treegrid" id="suppressedListGrid" style="width:100%;height:100%;" rowStyleRenderer="rowStyle" showTreeIcon="true" resultAsTree="true" allowAlternating="true" showHGridLines="false" showVGridLines="false">
            <div property="columns">
                <div field="text" name="text" width="200" headerAlign="center" align="left" treeColumn="true">设备报警内容</div>
                <div field="alarmLevel" name="alarmLevel" width="60" headerAlign="center" align="center" renderer="levelRenderer">级别</div>
                <div field="suppressReason" name="suppressReason" width="120" headerAlign="center" align="center">消警原因</div>
                <div field="suppressUser" name="suppressUser" width="80" headerAlign="center" align="center">消警人</div>
                <div field="suppressTime" name="suppressTime" width="140" headerAlign="center" align="center">消警时间</div>
                <div field="suppressEndTime" name="suppressEndTime" width="140" headerAlign="center" align="center">消警结束时间</div>
                <div name="action" width="120" headerAlign="center" align="center" renderer="actionRendererCancelWindow">操作</div>
            </div>
        </div>
    </div>
</div>