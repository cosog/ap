Ext.define('AP.view.kafkaConfig.KafkaConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.kafkaConfigInfoView',
    layout: "fit",
    id:'kafkaConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var KafkaConfigWellListStore=Ext.create('AP.store.kafkaConfig.KafkaConfigWellListStore');
    	var wellComboBoxStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/wellInformationManagerController/loadWellComboxList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('kafkaConfigInfoWellCom_Id').getValue();
                    var new_params = {
                    	wellName: wellName,
                        orgId: org_Id,
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellComboBox = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.wellName,
            id: "kafkaConfigInfoWellCom_Id",
            store: wellComboBoxStore,
            labelWidth: 35,
            width: 125,
            queryMode: 'remote',
            typeAhead: true,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            pageSize: comboxPagingStatus,
            minChars: 0,
            multiSelect: false,
            listeners: {
                expand: function (sm, selections) {
                	wellComboBox.getStore().loadPage(1);
                },
                select: function (combo, record, index) {
                	Ext.getCmp("kafkaConfigGridPanel_Id").getStore().loadPage(1);
                	Ext.getCmp("KafkaConfigDataTextArea_Id").setValue("");
                }
            }
        });
        var configTypeStore = new Ext.data.SimpleStore({
            fields: ['boxkey', 'boxval'],
            data: [
            	[1, '时钟配置'], 
            	[2, '启抽'],
            	[3, '停抽'],
            	[4, '固定位置停抽'],
            	[5, '频率'],
            	[6, '数据配置'],
            	[7, '模型配置']
            ]
        });
        var configTypeComboBox = new Ext.form.ComboBox({
            id: 'kafkaConfigTypeWellCom_Id',
            value: 1,
            fieldLabel: '操作',
            allowBlank: false,
            emptyText: '请选择操作类型',
            triggerAction: 'all',
            store: configTypeStore,
            labelWidth: 35,
            width: 155,
            displayField: 'boxval',
            valueField: 'boxkey',
            mode: 'local',
            listeners: {
                select: function (combo, record, index) {
                	Ext.getCmp("KafkaConfigDataTextArea_Id").setValue("");
                }
            }
        });
    	Ext.apply(me, {
    		tbar: [wellComboBox,'-',configTypeComboBox,'->',{
                xtype: 'button',
                text: '发送',
                pressed: true,
                hidden:false,
                handler: function (v, o) {
                	producerMsg();
                }
            }],
    		items: [{
                layout: "border",
                border: false,
                items: [{
                    region: 'west',
                    border: false,
                    layout: 'fit',
                    id: "KafkaConfigWellListPanel_Id", // 井名列表
                    width: '25%',
                    collapsible: false, // 是否折叠
                    split: true // 竖折叠条
                }, {
                    region: 'center',
                    xtype:'form',
            		layout: 'auto',
                    border: false,
                    collapsible: false,
//                    layout: 'fit',
//                    id: 'SelectSurfaceCardFilePanel_Id',
                    split: true,
                    items: [{
                    	xtype:'textareafield',
                    	id:'KafkaConfigDataTextArea_Id',
                    	grow:true,
                    	width:'99.9%',
                        height: '100%',
                        anchor: '100%',
                        readOnly:false
                    }]
        }]
     }]
        });
        this.callParent(arguments);

    }
});

function producerMsg(){
    var _record = Ext.getCmp("kafkaConfigGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		var type = Ext.getCmp('kafkaConfigTypeWellCom_Id').getValue();
		var wellName = _record[0].data.wellName;
		var data=Ext.getCmp('KafkaConfigDataTextArea_Id').getValue();
		Ext.Ajax.request({
    		method:'POST',
    		url:context + '/graphicalUploadController/kafkaProducerMsg',
    		success:function(response) {
    			rdata=Ext.JSON.decode(response.responseText);
    			if (rdata.success) {
                	Ext.MessageBox.alert("信息","发送成功");
                    //保存以后重置全局容器
                    wellInfoHandsontableHelper.clearContainer();
                    CreateAndLoadWellInfoTable();
                } else {
                	Ext.MessageBox.alert("信息","发送失败");

                }
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
                wellInfoHandsontableHelper.clearContainer();
    		},
    		params: {
    			type: type,
    			wellName:wellName,
    			data,data
            }
    	}); 
	}
    
    
    
    
    
}