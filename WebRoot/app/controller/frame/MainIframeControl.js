Ext.define('AP.controller.frame.MainIframeControl', {
    extend: 'Ext.app.Controller',
    views: ['frame.MainIframeView'],
    refs: [{
        ref: 'mainIframeView',
        selector: 'mainIframeView'
     }],
    init: function () {
        this.control({
            'mainIframeView': {
                itemclick: extFuncTreeItemsClk
            }
        })
    }
});

function extFuncTreeItemsClk(view, rec, item, index, e) {
    try {
        if (rec.isLeaf()) {
        	Ext.getCmp("frame_center_ids").el.mask(loginUserLanguageResource.loading).show();
        	
        	var module_Id_ = rec.data.id;
            var tabPanel = Ext.getCmp("frame_center_ids");
            if (module_Id_ == "backAdmin") {
                if (user_Type == 1 || user_Type == 2 || user_Type == 3) {
                    window.location.href = context + "/login";
                } else {
                	window.location.href = context + "/login";
                }
            } else {
                var getTabId = tabPanel.getComponent(module_Id_);
                // tab是否已打开panl
                if (getTabId) {
                    Ext.getCmp("topModule_Id").setValue(rec.data.mdCode);
                    tabPanel.setActiveTab(module_Id_);
                } else {
                    var app = this.application;
                    var getControlName = rec.data.controlsrc;
                    var ViewUrl=rec.data.viewsrc;
                    var controllerUrl = "";
                    if (getControlName != "#") {
                        Ext.require(getControlName, function () {
                            controllerUrl = app.getController(getControlName);
                            controllerUrl.init(app);
                        }, self);
                    }
                    if (ViewUrl != "#") {
                    	var all_loading = new Ext.LoadMask({
                            msg: loginUserLanguageResource.loading,
                            target: Ext.getBody().component
                        });
                        all_loading.show();
                        tabPanel.add(Ext.create(rec.data.viewsrc, {
                            id: module_Id_,
                            closable: true,
                            iconCls: rec.data.md_icon,
                            closeAction: 'destroy',
                            title: rec.data.text,
                            listeners: {
                                afterrender: function () {
                                    all_loading.hide();
                                },
                                delay: 150
                            }
                        })).show();
                        tabPanel.setActiveTab(module_Id_);
                        Ext.getCmp("topModule_Id").setValue(rec.data.mdCode);
                    }
                    
                }
            }
            Ext.getCmp("frame_center_ids").getEl().unmask();
        }
    } catch (e) {
        Ext.Msg.alert("exception", " name: " + e.name + "\n message: " + e.message + " \n lineNumber: " + e.lineNumber + " \n fileName: " + e.fileName + " \n stack: " + e.stack);
        all_loading.hide();
        return false;
    }
}