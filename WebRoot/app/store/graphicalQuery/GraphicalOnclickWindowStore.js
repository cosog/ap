Ext.define('AP.store.graphicalQuery.GraphicalOnclickWindowStore', {
    extend: 'Ext.data.Store',
    id: "GraphicalOnclickWindowStore_Id",
    alias: 'widget.GraphicalOnclickWindowStore',
    model: 'AP.model.graphical.X_Y_Model',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/graphicalOnclickManagerController/graphicalQuery',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            rootProperty: 'list',
            keepRawData:true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
        	var get_rawData=store.proxy.reader.rawData;  // 获取store数据
        	var type = Ext.getCmp('graphicalOnclickType_Id').getValue();
        	var id = Ext.getCmp('graphicalOnclick_Id').getValue();
        	// 图形类型+数据id作为打开窗口div的id
        	var divid = type + id;
        	if (type=="gtsj"){
        		showSurfaceCard(get_rawData,divid);    // 调用画光杆功图的函数
        	}else if (type=="bgt"){
        		showPumpCard(get_rawData,divid);    // 调用画泵功图的函数
        	}else if (type=="dlqx"){
        		
        	}
        },
    beforeload: function (store, options) {
        var graphicalOnclickType_Id = Ext.getCmp('graphicalOnclickType_Id');
        var graphicalOnclick_Id = Ext.getCmp('graphicalOnclick_Id');
        if (!Ext.isEmpty(graphicalOnclickType_Id)) {
            graphicalOnclickType_Id = graphicalOnclickType_Id.getValue(); // 获取图形类型
        }
        if (!Ext.isEmpty(graphicalOnclick_Id)) {
            graphicalOnclick_Id = graphicalOnclick_Id.getValue(); // 获取图形数据id
        }
        var new_params = { // 将图形类型、数据id作为参数传给后台
            type: graphicalOnclickType_Id,
            id: graphicalOnclick_Id
        };
        Ext.apply(store.proxy.extraParams, new_params);
    }
}
})