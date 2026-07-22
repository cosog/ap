Ext.define("AP.view.role.ImportRoleWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportRoleWindow',
    alias: 'widget.ImportRoleWindow',
    layout: 'fit',
    title: loginUserLanguageResource.importRole,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader: true, //True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
    //    constrain: true,
    closable: false,          // 禁用内置按钮，完全自定义
    maximizable: false,
    minimizable: false,
    collapsible: false,
    width: '65%',
    minWidth: 600,
    height: '70%',
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            tbar: [{
                xtype: 'form',
                id: 'RoleImportForm_Id',
                width: 300,
                bodyPadding: 0,
                frame: true,
                items: [{
                    xtype: 'filefield',
                    id: 'RoleImportFilefield_Id',
                    disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                    name: 'file',
                    fieldLabel: loginUserLanguageResource.uploadFile,
                    labelWidth: (getLabelWidth(loginUserLanguageResource.uploadFile, loginUserLanguage)),
                    width: '100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable: true,
                    buttonText: loginUserLanguageResource.selectUploadFile,
                    accept: '.json',
                    listeners: {
                        change: function (cmp) {
                        	submitImportedRoleFile();
                        }
                    }
        	    }, {
                    id: 'ImportRoleSelectItemId_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		}, {
                xtype: 'label',
                id: 'ImportRoleWinTabLabel_Id',
                hidden: true,
                html: ''
            }, '->', {
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                handler: function (v, o) {
                    var treeStore = Ext.getCmp("ImportRoleContentTreeGridPanel_Id").getStore();
                    var count = treeStore.getCount();
                    var overlayCount = 0;
                    var collisionCount = 0;
                    for (var i = 0; i < count; i++) {
                        if (treeStore.getAt(i).data.saveSign == 1) {
                            overlayCount++;
                        } else if (treeStore.getAt(i).data.saveSign == 2) {
                            collisionCount++;
                        }
                    }
                    if (overlayCount > 0 || collisionCount > 0) {
                        var info = loginUserLanguageResource.collisionInfo4+"?";
                        Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
                            if (btn == "yes") {
                                saveAllImportedRole();
                            }
                        });
                    } else {
                        saveAllImportedRole();
                    }
                }
    	    }],
            layout: 'border',
            items: [{
                region: 'center',
                title: loginUserLanguageResource.roleList,
                layout: 'fit',
                split: true,
                collapsible: true,
                id: "importRoleTreePanel_Id"
            }],
            listeners: {
                beforeclose: function (panel) {
                	
                },
                // 最大化时，确保窗口处于展开状态，并刷新表格
                maximize: function (win) {
                    win._minimized = false;
                    win.setTitle(win.originalTitle);
                    if (win._panel) win._panel.show();
                    if (win._toolbar) win._toolbar.show();
                    var minBtn = win._minimizeBtn;
                    var expandBtn = win._expandBtn;
                    if (minBtn) minBtn.style.display = 'inline-block';
                    if (expandBtn) expandBtn.style.display = 'none';
                    var maxBtn = win._maximizeBtn;
                    var restoreBtn = win._restoreBtn;
                    if (maxBtn) maxBtn.style.display = 'none';
                    if (restoreBtn) restoreBtn.style.display = 'inline-block';
                    var panel = Ext.getCmp('importRoleTreePanel_Id');
                    if (panel) {
                        Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 50);
                    }
                },
                restore: function (win) {
                    if (win._minimized) {
                        win._minimized = false;
                        win.setTitle(win.originalTitle);
                        if (win._panel) win._panel.show();
                        if (win._toolbar) win._toolbar.show();
                        var minBtn = win._minimizeBtn;
                        var expandBtn = win._expandBtn;
                        if (minBtn) minBtn.style.display = 'inline-block';
                        if (expandBtn) expandBtn.style.display = 'none';
                    }
                    var maxBtn = win._maximizeBtn;
                    var restoreBtn = win._restoreBtn;
                    if (maxBtn) maxBtn.style.display = 'inline-block';
                    if (restoreBtn) restoreBtn.style.display = 'none';
                    if (win._savedHeight && !win.maximized) {
                        win.setHeight(win._savedHeight);
                        win._savedHeight = null;
                    }
                    win._minimized = false;
                    win.setTitle(win.originalTitle);
                    var panel = Ext.getCmp('importRoleTreePanel_Id');
                    if (panel) {
                        Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 50);
                    }
                },
                afterrender: function (panel) {
                    var win = panel;
                    win.originalTitle = win.title;
                    win._savedHeight = null;
                    win._minimized = false;
                    win._defaultHeight = win.getHeight();

                    win._panel = win.down('#importRoleTreePanel_Id');
                    win._toolbar = win.down('toolbar');
                    if (win._toolbar) win._toolbar.show();

                    var header = win.getHeader();
                    if (!header) return;

                    var headerEl = header.el.dom;
                    var btnContainer = document.createElement('div');
                    btnContainer.style.cssText = 
                        'position:absolute; right:2px; top:0; height:100%;' +
                        'display:flex; align-items:center; gap:2px; z-index:10;' +
                        'padding:0 4px;';
                    headerEl.style.position = 'relative';
                    headerEl.appendChild(btnContainer);

                    function createButton(text, title, clickHandler, isClose) {
                        var btn = document.createElement('button');
                        btn.textContent = text;
                        btn.title = title;
                        var defaultColor = '#404040';
                        btn.style.cssText = 
                            'background:transparent; border:none; font-size:16px; font-weight:300;' +
                            'cursor:pointer; padding:0 8px;' +
                            'display:flex; align-items:center; justify-content:center; border-radius:2px;' +
                            'transition:background 0.15s, color 0.15s;' +
                            'color:' + defaultColor + '; touch-action:manipulation;' +
                            'font-family:sans-serif; line-height:1;' +
                            'min-width:30px; min-height:30px;';

                        // 使用 pointerdown 事件（统一鼠标和触摸）
                        btn.addEventListener('pointerdown', function(e) {
                            e.preventDefault();
                            e.stopPropagation();
                            // 视觉反馈
                            if (isClose) {
                                this.style.background = '#e81123';
                                this.style.color = '#ffffff';
                            } else {
                                this.style.background = 'rgba(0,0,0,0.15)';
                            }
                            // 执行业务逻辑
                            clickHandler(e);
                            // 释放指针捕获（如果有）
                            this.releasePointerCapture(e.pointerId);
                        }, { passive: false });

                        // 指针释放时恢复样式
                        btn.addEventListener('pointerup', function(e) {
                            this.style.background = 'transparent';
                            this.style.color = isClose ? defaultColor : defaultColor;
                        }, { passive: false });

                        btn.addEventListener('pointerleave', function(e) {
                            this.style.background = 'transparent';
                            this.style.color = isClose ? defaultColor : defaultColor;
                        }, { passive: false });

                        return btn;
                    }

                    // ---- 创建各按钮 ----
                    var minBtn = createButton('─', '', function () {
                        win._savedHeight = win.getHeight();
                        var headerH = win.getHeader() ? win.getHeader().getHeight() : 30;
                        if (win._panel) win._panel.hide();
                        if (win._toolbar) win._toolbar.hide();
                        win.setHeight(headerH);
                        win._minimized = true;
//                        win.setTitle(win.originalTitle + ' (已最小化)');
                        minBtn.style.display = 'none';
                        expandBtn.style.display = 'inline-block';
                    });
                    btnContainer.appendChild(minBtn);
                    win._minimizeBtn = minBtn;

                    var expandBtn = createButton('⤢', '', function () {
                        if (win._savedHeight) {
                            win.setHeight(win._savedHeight);
                            win._savedHeight = null;
                        } else {
                            win.setHeight(win._defaultHeight || '80%');
                        }
                        win._minimized = false;
                        win.setTitle(win.originalTitle);
                        if (win._panel) win._panel.show();
                        if (win._toolbar) win._toolbar.show();
                        win.updateLayout();
                        expandBtn.style.display = 'none';
                        minBtn.style.display = 'inline-block';
                        var panel = Ext.getCmp('importRoleTreePanel_Id');
                        if (panel) {
                            Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 100);
                        }
                    });
                    expandBtn.style.display = 'none';
                    btnContainer.appendChild(expandBtn);
                    win._expandBtn = expandBtn;

                    var maxBtn = createButton('☐', '', function () {
                        if (!win.maximized) {
                            win.maximize();
                        }
                    });
                    btnContainer.appendChild(maxBtn);
                    win._maximizeBtn = maxBtn;

                    var restoreBtn = createButton('⧉', '', function () {
                        if (win.maximized) {
                            win.restore();
                        }
                    });
                    restoreBtn.style.display = 'none';
                    btnContainer.appendChild(restoreBtn);
                    win._restoreBtn = restoreBtn;

                    var closeBtn = createButton('✕', '', function () {
                        win.close();
                    }, true);
                    btnContainer.appendChild(closeBtn);

                    // 标题栏点击展开（忽略按钮区域）
                    header.el.on('click', function (e) {
                        if (btnContainer.contains(e.target)) {
                            return;
                        }
                        if (win._minimized && !win.maximized) {
                            e.stopEvent();
                            if (win._savedHeight) {
                                win.setHeight(win._savedHeight);
                                win._savedHeight = null;
                            } else {
                                win.setHeight(win._defaultHeight || '80%');
                            }
                            win._minimized = false;
                            win.setTitle(win.originalTitle);
                            win.updateLayout();
                            if (win._panel) win._panel.show();
                            if (win._toolbar) win._toolbar.show();
                            expandBtn.style.display = 'none';
                            minBtn.style.display = 'inline-block';
                            var panel = Ext.getCmp('importRoleTreePanel_Id');
                            if (panel) {
                                Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 100);
                            }
                        }
                    });

                    win.on('destroy', function () {
                        if (btnContainer.parentNode) {
                            btnContainer.parentNode.removeChild(btnContainer);
                        }
                    });
                }
            }
        });
        me.callParent(arguments);
    }
});


function submitImportedRoleFile() {
    var form = Ext.getCmp("RoleImportForm_Id");
    if (form.isValid()) {
        form.submit({
            url: context + '/roleManagerController/uploadImportedRoleFile',
            timeout: 1000 * 60 * 10,
            method: 'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function (response, action) {
                var result = action.result;
                if (result.flag == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
                } else {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
                }

                var importRoleContentTreeGridPanel = Ext.getCmp("ImportRoleContentTreeGridPanel_Id");
                if (isNotVal(importRoleContentTreeGridPanel)) {
                    importRoleContentTreeGridPanel.getStore().load();
                } else {
                    Ext.create('AP.store.role.ImportRoleContentTreeInfoStore');
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.uploadFail + "</font>】");
            }
        });
    }
    return false;
};

adviceImportRoleCollisionInfoColor = function (val, o, p, e) {
    var saveSign = p.data.saveSign;
    var tipval = val;
    var backgroundColor = '#FFFFFF';
    var color = '#DC2828';
    if (saveSign == 0) {
        color = '#000000';
    }
    var opacity = 0;
    var rgba = color16ToRgba(backgroundColor, opacity);
    o.style = 'background-color:' + rgba + ';color:' + color + ';';
    if (isNotVal(tipval)) {
        return Ext.String.format('<span data-qtip="{0}">{1}</span>',Ext.String.htmlEncode(tipval),Ext.String.htmlEncode(val));
    }
}

function saveAllImportedRole() {
	Ext.Ajax.request({
        url: context + '/roleManagerController/saveAllImportedRole',
        method: "POST",
        params: {
        	
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.success == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
            } else {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailed + "</font>");
            }
            Ext.getCmp("ImportRoleContentTreeGridPanel_Id").getStore().load();

            var treePanel = Ext.getCmp("RoleInfoGridPanel_Id");
            if (isNotVal(treePanel)) {
                treePanel.getStore().load();
            } else {
                Ext.create('AP.store.role.RoleInfoStore');
            }
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】" + loginUserLanguageResource.contactAdmin);
        }
    });
}

iconImportSingleUserAction = function (value, e, record) {
    var resultstring = '';
    if (record.data.classes == 1 && record.data.saveSign != 2) {
        var instanceName = record.data.text;
        var unitName = record.data.unitName;
        var protocolName = record.data.protocol;

        var saveSign = record.data.saveSign;
        var msg = record.data.msg;

        instanceName = encodeURIComponent(instanceName || '');
        unitName = encodeURIComponent(unitName || '');
        protocolName = encodeURIComponent(protocolName || '');

        saveSign = encodeURIComponent(saveSign || '');
        msg = encodeURIComponent(msg || '');

        resultstring = "<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
            "onclick=saveSingelImportedUser('" + instanceName + "','" + unitName + "','" + protocolName + "','" + saveSign + "','" + msg + "')>" + loginUserLanguageResource.save + "...</a>";
    }
    return resultstring;
}
