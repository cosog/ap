Ext.define("AP.view.acquisitionUnit.ProtocolImportWindow", {
    extend: 'Ext.window.Window',
    id:'ProtocolImportWindow_Id',
    alias: 'widget.protocolImportWindow',
    layout: 'fit',
    title:'协议导入',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1500,
    minWidth: 1500,
    height: 700,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar: [{
        		xtype:'form',
        		id:'ProtocolImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'ProtocolImportFilefield_Id',
                    name: 'file',
                    fieldLabel: '上传文件',
                    labelWidth: 60,
                    width:'100%',
                    msgTarget: 'side',
                    allowBlank: false,
                    anchor: '100%',
                    draggable:true,
                    buttonText: '请选择上传文件',
                    accept:'.json',
                    listeners:{
                        change:function(cmp){
                        	submitImportedProtocolFile();
                        }
                    }
        	    }]
    		},'->',{
    	    	xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	
                }
    	    }],
            layout: 'border',
            items: [{
            	region: 'west',
            	width:'25%',
            	title:'协议相关内容',
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"importPootocolTreePanel_Id"
            },{
            	region: 'center',
            	html: '<div id="importedProtocolItemInfoTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
            			
            		}
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function submitImportedProtocolFile() {
	var form = Ext.getCmp("ProtocolImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/getImportedProtocolFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: '文件上传中...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert("提示", "上传成功 ");
            		Ext.getCmp("importPootocolTreePanel_Id").setTitle("<font color=red>"+result.protocolName+"</font>协议相关内容");
            		var importProtocolContentTreeGridPanel = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id");
                	if (isNotVal(importProtocolContentTreeGridPanel)) {
                		importProtocolContentTreeGridPanel.getStore().load();
                	}else{
                		Ext.create('AP.store.acquisitionUnit.ImportProtocolContentTreeInfoStore');
                	}
            	}else{
            		Ext.Msg.alert("提示", "上传数据格式有误！ ");
            		Ext.getCmp("importPootocolTreePanel_Id").setTitle("协议相关内容");
            	}
            	
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};