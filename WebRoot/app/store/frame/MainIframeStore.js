Ext.define('AP.store.frame.MainIframeStore', {
    extend: 'Ext.data.TreeStore',
    storeId: 'MainIframeStore_Id',
    //folderSort : false,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: context + '/moduleMenuController/obtainFunctionModuleList',
        reader: {
            type: 'json',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
        	var tabPanel = Ext.getCmp("frame_center_ids");
        	for(var i=0;i<store.data.items.length;i++){
        		if(store.data.items[i].isLeaf()){
        			var rec=store.data.items[i];
                	var getTabId = tabPanel.getComponent(rec.data.id);
                	if(!getTabId){
                		tabPanel.add(Ext.create(rec.data.viewsrc, {
                            id: rec.data.id,
                            closable: false,
                            iconCls: rec.data.md_icon,
                            closeAction: 'destroy',
                            title: rec.data.text,
                            listeners: {
                                afterrender: function () {
                                    //all_loading.hide();
                                },
                                delay: 150
                            }
                        })).show();
                	}
                	tabPanel.setActiveTab(rec.data.id);
                	break;
        		}
        	}
        }
    }
});

