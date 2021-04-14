var websocketTestClient = null;
Ext.define('AP.view.websocketTest.WebSocketTestInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.webSocketTestInfoView',
    id: 'WebSocketTestInfoView_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        Ext.apply(this, {
            tbar: [{
            	xtype: 'textfield',
    			id: 'webSocketTestSendData_Id',
    			hidden:false
            },'-',{
            	xtype: 'button',
            	id:'WebSocketTestSendBtn_Id',
    			text: '发送',
    			disabled: true,
                pressed: true,
    			handler: function (v, o) {
    				send();
    			}
            },'-',{
            	xtype: 'button',
    			text: '连接',
    			id:'WebSocketTestConnBtn_Id',
                pressed: true,
    			handler: function (v, o) {
    				if('WebSocket' in window){
    					var baseUrl=getBaseUrl().replace("https","ws").replace("http","ws");
    					websocketTestClient = new WebSocket(baseUrl+"/websocketTest/user000");
    					console.log("link success");
    					//连接发生错误的回调方法
      			      websocketTestClient.onerror = function(){
      			          setMessageInnerHTML("error");
      			      };
      			       
      			      //连接成功建立的回调方法
      			      websocketTestClient.onopen = function(event){
      			          setMessageInnerHTML("open");
      			      }
      			       console.log("-----")
      			      //接收到消息的回调方法
      			      websocketTestClient.onmessage = function(event){
      			            setMessageInnerHTML(event.data);
      			      }
      			       
      			      //连接关闭的回调方法
      			      websocketTestClient.onclose = function(){
      			          setMessageInnerHTML("close");
      			      }
      			       
      			      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      			      window.onbeforeunload = function(){
      			          websocketTestClient.close();
      			      }
      			      Ext.getCmp("WebSocketTestSendBtn_Id").enable();
      			      Ext.getCmp("WebSocketTestConnBtn_Id").disable();
      			      Ext.getCmp("WebSocketTestColseConnBtn_Id").enable();
    				}else{
    					alert('Not support websocket')
    				}
    			}
            },'-',{
            	xtype: 'button',
    			text: '关闭连接',
    			id:'WebSocketTestColseConnBtn_Id',
    			disabled: true,
                pressed: true,
    			handler: function (v, o) {
    				closeWebSocket();
    				Ext.getCmp("WebSocketTestSendBtn_Id").disable();
    				Ext.getCmp("WebSocketTestConnBtn_Id").enable();
    			      Ext.getCmp("WebSocketTestColseConnBtn_Id").disable();
    			}
            },'-',{
            	xtype: 'button',
    			text: '清除',
                pressed: true,
    			handler: function (v, o) {
    				Ext.getCmp("KafkaConfigDataTextArea_Id").setValue('');
    			}
            }],
            region: 'center',
            layout: 'border',
            items: [{
            	region: 'center',
            	width: '35%',
            	xtype:'form',
        		layout: 'auto',
                border: false,
                collapsible: false,
                split: true,
                autoScroll:true,
                scrollable: true,
                items: [{
                	xtype:'textareafield',
                	id:'KafkaConfigDataTextArea_Id',
                	margin: '0 0 0 0',
                	padding:0,
                	grow:false,//自动增长
                	border: false,
                	width:'100%',
                    height: '100%',
                    anchor: '100%',
                    emptyText: '...',
                    autoScroll:true,
                    scrollable: true,
                    readOnly:false
                }]
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	closeWebSocket();
    			},
    			afterrender: function ( panel, eOpts) {
    			}
            }
        })
        this.callParent(arguments);
    }
});

//将消息显示在网页上
function setMessageInnerHTML(innerHTML){
//    document.getElementById('message').innerHTML += innerHTML + '<br/>';
    var text=Ext.getCmp("KafkaConfigDataTextArea_Id").getValue()+innerHTML + '\r\n';
    Ext.getCmp("KafkaConfigDataTextArea_Id").setValue(text);
    
    
}
 
//关闭连接
function closeWebSocket(){
	if(websocketTestClient!=null){
		websocketTestClient.close();
	}
}
 
//发送消息
function send(){
    var message = Ext.getCmp("webSocketTestSendData_Id").getValue();
    websocketTestClient.send(message);
}
