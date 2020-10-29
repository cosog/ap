var websocket = null;
Ext.define('AP.view.kafkaConfig.KafkaConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.kafkaConfigInfoView',
    layout: "fit",
    id:'kafkaConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var KafkaConfigWellListStore=Ext.create('AP.store.kafkaConfig.KafkaConfigWellListStore');
    	var KafkaConfigOpreationListStore=Ext.create('AP.store.kafkaConfig.KafkaConfigOperationListStore');
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
            	[6, '驱动配置'],
            	[7, '模型配置'],
            	[8, '设备重启']
            ]
        });
        var configTypeComboBox = new Ext.form.ComboBox({
            id: 'kafkaConfigTypeWellCom_Id',
            value: 1,
            fieldLabel: '操作',
            hidden:true,
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
                id:'KafkaConfigSendBtn_Id',
                pressed: true,
                hidden:false,
                handler: function (v, o) {
                	producerMsg();
//                	doSendUser();
                }
            }],
    		items: [{
                layout: "border",
                border: false,
                items: [{
                    region: 'west',
                    border: false,
                    layout: 'fit',
                    width: '40%',
                    layout: 'border',
                    collapsible: true,
                    header:false,
                    split: true,
                    items: [{
                    	region: 'west',
                    	width: '60%',
                    	title:'井列表',
                    	layout: 'fit',
                        id: "KafkaConfigWellListPanel_Id", // 井名列表
                        border: false,
                        collapsible: false, // 是否折叠
                        split: true // 竖折叠条
                    },{
                    	region: 'center',
                    	border: false,
                    	layout: 'fit',
                    	title:'操作列表',
                    	id: "KafkaConfigOperationListPanel_Id"
                    }]
                }, {
                    region: 'center',
                    xtype:'form',
            		layout: 'auto',
                    border: false,
                    collapsible: false,
//                    layout: 'fit',
//                    id: 'SelectSurfaceCardFilePanel_Id',
                    split: true,
                    autoScroll:true,
                    scrollable: true,
                    items: [{
                    	xtype:'textareafield',
                    	id:'KafkaConfigDataTextArea_Id',
                    	margin: '2 2 2 2',
                    	grow:true,
                    	width:'99%',
                        height: '99%',
                        anchor: '100%',
                        emptyText: '在此输入下行数据...',
                        autoScroll:true,
                        scrollable: true,
                        readOnly:false
                    }]
                }]
    		}],
    		listeners: {
    			beforeclose: function ( panel, eOpts) {
//    				alert("关闭");
    				websocketClose();
    			},
    			afterrender: function ( panel, eOpts) {
    				if ('WebSocket' in window) {
    				    websocket = new WebSocket("ws://localhost:16100/ap/websocket/socketServer?module_Code=kafkaConfig");
    				}
    				else if ('MozWebSocket' in window) {
    				    websocket = new MozWebSocket("ws://localhost:16100/ap/websocket/socketServer?module_Code=kafkaConfig");
    				}
    				else {
    				    websocket = new SockJS("http://localhost:16100/ap/sockjs/socketServer?module_Code=kafkaConfig");
    				}
    				websocket.onopen = onOpen;
    				websocket.onmessage = onMessage;
    				websocket.onerror = onError;
    				websocket.onclose = onClose;
    			}
    		}
        });
        this.callParent(arguments);

    }
});

function onOpen(openEvt) {
    alert(openEvt.Data);
}

function onMessage(evt) {
//    alert("接收到消息的回调方法");
//    alert("这是后台推送的消息："+evt.data);
	var activeId = Ext.getCmp("frame_center_ids").getActiveTab().id;
	if (activeId == "kafkaConfig_kafkaConfigGridPanel") {
		Ext.getCmp("KafkaConfigDataTextArea_Id").setValue("");
		
		var data=evt.data;
		var dataArr=data.split("##");
		var type=dataArr[0];
		var deviceId=dataArr[1];
		var jsonData=dataArr[2];
		var kafkaConfigGridPanel=Ext.getCmp("kafkaConfigGridPanel_Id");
		var kafkaConfigOperationGridPanel=Ext.getCmp("kafkaConfigOperationGridPanel_Id");
		if(isNotVal(kafkaConfigGridPanel) && isNotVal(kafkaConfigOperationGridPanel)
				&& kafkaConfigGridPanel.getSelectionModel().getSelection().length>0
				&& kafkaConfigOperationGridPanel.getSelectionModel().getSelection().length>0){
			var wellRecord = kafkaConfigGridPanel.getSelectionModel().getSelection();
			var operationRecord = kafkaConfigOperationGridPanel.getSelectionModel().getSelection();
			var selectedDeviceId=wellRecord[0].data.deviceId;
			var selectedOpType=operationRecord[0].data.id;
			if(type==selectedOpType && deviceId==selectedDeviceId){
				Ext.getCmp("KafkaConfigDataTextArea_Id").setValue(jsonData);
			}
		}
	}
}
function onOpen() {
//	alert("WebSocket连接成功");
}
function onError() {
//	alert("WebSocket连接发生错误");
}
function onClose() {
//	alert("WebSocket连接关闭");
}

function doSendUser() {
	alert(websocket.readyState + ":" + websocket.OPEN);
    if (websocket.readyState == websocket.OPEN) {
        var msg = "前台发送的数据";
        websocket.send("#anyone#"+msg);//调用后台handleTextMessage方法
//        alert("发送成功!");
    } else {
//        alert("连接失败!");
    }
}


function doSendUsers() {
    if (websocket.readyState == websocket.OPEN) {
        var msg = document.getElementById("inputMsg").value;
        websocket.send("#everyone#"+msg);//调用后台handleTextMessage方法
//        alert("发送成功!");
    } else {
//        alert("连接失败!");
    }
}


function websocketClose() {
	websocket.close();
}






function readDeviceInfo(deviceId,type){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/kafkaConfigController/readDeviceInfo',
		success:function(response) {
			var rdata=Ext.JSON.decode(response.responseText);
			if (rdata.success) {
            	
            } else {

            }
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
			type: type,
			deviceId:deviceId
        }
	}); 
};

function producerMsg(){
    var _record = Ext.getCmp("kafkaConfigGridPanel_Id").getSelectionModel().getSelection();
    var operationRecord = Ext.getCmp("kafkaConfigOperationGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0 && operationRecord.length>0){
//		var type = Ext.getCmp('kafkaConfigTypeWellCom_Id').getValue();
		var type = operationRecord[0].data.id;
		var operationName = operationRecord[0].data.operation;
		var wellName = _record[0].data.wellName;
		var deviceId = _record[0].data.deviceId;
		var data=Ext.getCmp('KafkaConfigDataTextArea_Id').getValue();
		
		var operaName="是否对设备<font color=red>"+deviceId+"</font>下行<font color=red>"+operationName+"</font>操作！";
		
		Ext.Msg.confirm("操作确认", operaName, function (btn) {
            if (btn == "yes") {
            	Ext.Ajax.request({
            		method:'POST',
            		url:context + '/kafkaConfigController/kafkaProducerMsg',
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
            		},
            		params: {
            			type: type,
            			wellName:wellName,
            			data,data
                    }
            	}); 
            }
        });
	}
}