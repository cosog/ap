Ext.define('AP.view.graphicalUpload.SurfaceCardUploadPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.surfaceCardUploadPanel',
    layout: "fit",
    id:'surfaceCardUploadPanelId',
    border: false,
    initComponent: function () {
    	var SurfaceCardTypeListStore=Ext.create('AP.store.graphicalUpload.SurfaceCardTypeListStore');
        Ext.apply(this, {
            items: [{
                layout: "border",
                border: false,
                items: [{
                    region: 'west',
                    border: false,
                    layout: 'fit',
                    id: "SurfaceCardTypeListPanel_Id", // 井名列表
                    title: '选择功图类型',
                    width: '15%',
                    collapsible: true, // 是否折叠
                    split: true // 竖折叠条
                }, {
                    region: 'center',
                    layout: 'border',
                    border: false,
                    collapsible: false,
//                    layout: 'fit',
//                    id: 'SelectSurfaceCardFilePanel_Id',
                    title:'选择功图文件', // 工况统计表
                    split: true,
                    items:[{
                    	region: 'center',
                    	layout: 'fit',
                    	border: false,
                    	id: 'SelectSurfaceCardFilePanel_Id'
                    },{
                    	region: 'east',
                        border: false,
                        layout: 'fit',
                        width: '40%',
                        collapsible: false, // 是否折叠
                        split: false, // 竖折叠条
                        id:'SurfaceCardUploadShowPanel_Id',
            			html:'<div id="SurfaceCardUploadShowDiv_Id" style="width:100%;height:100%;"></div>',
            			listeners: {
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	var GridPanel = Ext.getCmp("surfaceCardUploadGridpanelId");
                            	if (isNotVal(GridPanel)) {
                            		$("#SurfaceCardUploadShowDiv_Id").highcharts().setSize(adjWidth,adjHeight,true);
                            	}
                            }
                        }
                    }],
                    dockedItems: [{
                        xtype: 'toolbar',
                        dock: 'top',
                        id:'SelectSurfaceCardFilePanelToolbarId',
                        items:[{
                        		xtype:'form',
                        		id:'SelectSurfaceCardFilePanelFormId',
                        	    width: "100%",
                        	    bodyPadding: 10,
                        	    frame: true,
                        	    items: [{
                        	    	xtype: 'filefield',
                                	id:'SurfaceCardFilefieldId',
//                                	inputType:'file',
//                                    name: 'SurfaceCardFile',
                                    name: 'file',
                                    fieldLabel: '功图文件(<font color=red>建议一次上传文件不超过500个 </font>)',
                                    labelWidth: 230,
                                    width:'100%',
                                    msgTarget: 'side',
                                    allowBlank: false,
                                    anchor: '100%',
                                    draggable:true,
                                    buttonText: '请选择功图文件',
                                    accept:'.t',
                                    listeners:{
                                        afterrender:function(cmp){
                                            cmp.fileInputEl.set({
                                                multiple:'multiple'
                                            });
                                        },
                                        change:function(cmp){
                                        	submitSurfaceCardFile();
                                        }
                                    }
                        	    }]
                        }],
                    }],
                    bbar:[{
                    	xtype: 'button',
                        text: '选中上传',
                        pressed: true,
                        handler: function (v, o) {
                        	uploadSelectedSurfaceCardFile();
                        }
                    },"-",{
                    	xtype: 'button',
                        text: '全部上传',
                        pressed: true,
                        handler: function (v, o) {
                        	uploadAllSurfaceCardFile();
                        }
                    },'->',{
                    	xtype: 'button',
                        text: '下载Excel模板',
                        id:'loadFSdiagramModelBtn_Id',
                        hidden:true,
                        pressed: true,
                        handler: function (v, o) {
                        	var url=context + '/graphicalUploadController/downLoadFSdiagramUploadExcelModel';
                        	document.location.href = url;
                        }
                    }]
        }]
     }]
        });
        this.callParent(arguments);

    }
});