Ext.define('AP.view.diagnosis.SingleAnalysisPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.SingleAnalysisPanel',
//    id: "SingleAnalysisPanel_Id",
    border: false,
    layout: {
        type: 'hbox',
        pack: 'start',
        align: 'stretch'
    },
    initComponent: function () {
        Ext.apply(this, {
            items: [
                {
                    border: false,
                    flex: 2,
                    margin: '0 0 0 0',
                    padding: 0,
                    autoScroll:true,
                    scrollable: true,
                    layout: {
                        type: 'vbox',
                        pack: 'start',
                        align: 'stretch'
                    },
                    items: [
                    	{
                    		border: false,
                    		margin: '0 0 1 0',
                    		layout: {
                    	        type: 'hbox',
                    	        pack: 'start',
                    	        align: 'stretch'
                    	    },
                    	    items:[{
                    	    	border: false,
                    	    	margin: '0 1 0 0',
                                flex: 1,
                                height:300,
                                html: '<div id=\"singleAnalysisDiagramDiv1_id\" style="width:100%;height:100%;"></div>',
                                listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        var gridPanel = Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id");
                                        if (isNotVal(gridPanel)) {
                                        	if($("#singleAnalysisDiagramDiv1_id").highcharts()!=undefined){
                                    			$("#singleAnalysisDiagramDiv1_id").highcharts().setSize($("#singleAnalysisDiagramDiv1_id").offsetWidth, $("#singleAnalysisDiagramDiv1_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                                }
                    	    },{
                    	    	border: false,
                                flex: 1,
                                height:300,
                                html: '<div id=\"singleAnalysisDiagramDiv2_id\" style="width:100%;height:100%;"></div>',
                                listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	var gridPanel = Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id");
                                        if (isNotVal(gridPanel)) {
                                        	if($("#singleAnalysisDiagramDiv2_id").highcharts()!=undefined){
                                    			$("#singleAnalysisDiagramDiv2_id").highcharts().setSize($("#singleAnalysisDiagramDiv2_id").offsetWidth, $("#singleAnalysisDiagramDiv2_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                                }
                    	    }]
                    	},{
                    		border: false,
                    		layout: {
                    	        type: 'hbox',
                    	        pack: 'start',
                    	        align: 'stretch'
                    	    },
                    	    items:[{
                    	    	border: false, // 泵功图
                    	    	margin: '0 1 0 0',
                                flex: 1,
                                height:300,
                                html: '<div id=\"singleAnalysisDiagramDiv3_id\" style="width:100%;height:100%;"></div>',
                                listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	var gridPanel = Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id");
                                        if (isNotVal(gridPanel)) {
                                        	if($("#singleAnalysisDiagramDiv3_id").highcharts()!=undefined){
                                    			$("#singleAnalysisDiagramDiv3_id").highcharts().setSize($("#singleAnalysisDiagramDiv3_id").offsetWidth, $("#singleAnalysisDiagramDiv3_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                                }
                    	    },{
                    	    	border: false, // 泵功图
                                flex: 1,
                                height:300,
                                html: '<div id=\"singleAnalysisDiagramDiv4_id\" style="width:100%;height:100%;"></div>',
                                listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	var gridPanel = Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id");
                                        if (isNotVal(gridPanel)) {
                                        	if($("#singleAnalysisDiagramDiv4_id").highcharts()!=undefined){
                                    			$("#singleAnalysisDiagramDiv4_id").highcharts().setSize($("#singleAnalysisDiagramDiv4_id").offsetWidth, $("#singleAnalysisDiagramDiv4_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                                }
                    	    }]
                    	},{
                    		border: false,
                    		margin: '1 0 0 0',
                    		layout: {
                    	        type: 'hbox',
                    	        pack: 'start',
                    	        align: 'stretch'
                    	    },
                    	    items:[{
                    	    	border: false,
                    	    	margin: '0 1 0 0',
                                flex: 1,
                                height:300,
                                html: '<div id=\"singleAnalysisDiagramDiv5_id\" style="width:100%;height:100%;"></div>',
                                listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	var gridPanel = Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id");
                                        if (isNotVal(gridPanel)) {
                                        	if($("#singleAnalysisDiagramDiv5_id").highcharts()!=undefined){
                                    			$("#singleAnalysisDiagramDiv5_id").highcharts().setSize($("#singleAnalysisDiagramDiv5_id").offsetWidth, $("#singleAnalysisDiagramDiv5_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                                }
                    	    },{
                    	    	border: false,
                                flex: 1,
                                height:300,
                                html: '<div id=\"singleAnalysisDiagramDiv6_id\" style="width:100%;height:100%;"></div>',
                                listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	var gridPanel = Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id");
                                        if (isNotVal(gridPanel)) {
                                        	if($("#singleAnalysisDiagramDiv6_id").highcharts()!=undefined){
                                    			$("#singleAnalysisDiagramDiv6_id").highcharts().setSize($("#singleAnalysisDiagramDiv6_id").offsetWidth, $("#singleAnalysisDiagramDiv6_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                                }
                    	    }]
                    	}
                    ]
                },
                {
        			flex: 1,
        			xtype: 'tabpanel',
        			id:'DiagnosisAnalysisAndAcqDataTabpanel_Id',
        			activeTab: 0,
        			border: true,
        			header: false,
                    collapsible: true,
                    split: true,
                    collapseDirection: 'right',
        			hideMode:'offsets',
        			tabPosition: 'top',
        			items: [{
        				title:'分析',
        				id:'DiagnosisAnalysisDataPanel_Id',
        				border: false,
                        layout: 'fit',
                        autoScroll:true,
                        scrollable: true
        			},{
        				title:'采集',
        				id:'DiagnosisAcqDataPanel_Id',
        				border: false,
                        layout: 'fit',
                        autoScroll:true,
                        scrollable: true
        			},{
        				title:'控制',
        				border: false,
                        layout: 'border',
                        hideMode:'offsets',
                        id:'DiagnosisControlDataTabPanel_Id',
                        items: [{
                        	region: 'north',
                        	layout: 'fit',
                        	height: '40%',
                        	id:'DiagnosisControlVideoPanel_Id',
                        	collapsible: true, // 是否折叠
                        	header: false,
                            split: true, // 竖折叠条
                            autoRender:true,
                        	html: ''
                        },{
                        	region: 'center',
                            height: '60%',
                            id:'DiagnosisControlDataPanel_Id',
            				border: false,
            				autoScroll:true,
                            scrollable: true,
                            layout: 'fit',
                            listeners: {
                            	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                }
                            }
                        }]
        			}
        			],
        			listeners: {
                    	tabchange: function (tabPanel, newCard, oldCard,obj) {
                    		var selectedLength=Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id").getSelectionModel().getSelection().length;
                    		if(newCard.id=="DiagnosisControlDataTabPanel_Id"){
                    			if(selectedLength>0){
                        			var videoUrl=Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id").getSelectionModel().getSelection()[0].data.videourl;
                                    if(videoUrl!=undefined&&videoUrl!=""){
                                    	if($("#myPlayer")!=null){
                                    		$("#myPlayer").html('');
                                    	}
                                    	var videoUrl_rtmp=""; 
                                    	var videoUrl_hls=""; 
                                    	if(videoUrl.indexOf("http")>=0){//hls模式
                                    		videoUrl_hls=videoUrl;
                                    		videoUrl_rtmp=videoUrl.replace("https","http").replace("http://hls","rtmp://rtmp").replace(".m3u8","");
                                    	}else{
                                    		videoUrl_hls=videoUrl.replace("rtmp://rtmp","http://hls")+".m3u8";
                                    		videoUrl_rtmp=videoUrl;
                                    	}
                                    	Ext.getCmp("DiagnosisControlVideoPanel_Id").expand(true);
                                    	var videohtml='<video id="myPlayer" style="width:100%;height:100%;"  poster="" controls playsInline webkit-playsinline autoplay><source src="'+videoUrl_rtmp+'" type="rtmp/flv" /><source src="'+videoUrl_hls+'" type="application/x-mpegURL" /></video>';   
                                    	Ext.getCmp("DiagnosisControlVideoPanel_Id").update(videohtml);
                                    	if(document.getElementById("myPlayer")!=null){
                                    		var player = new EZUIPlayer('myPlayer');
                                    	}
                                    }else{
                                    	var videohtml=''
                                    	Ext.getCmp("DiagnosisControlVideoPanel_Id").update(videohtml);
                                    	Ext.getCmp("DiagnosisControlVideoPanel_Id").collapse();
                                    }
                        		}else{
                    				Ext.getCmp("DiagnosisControlVideoPanel_Id").removeAll();
                    				if($("#myPlayer")!=null){
                                		$("#myPlayer").html('');
                                	}
                    			}
                    			
                    		}else{
                    			var videohtml='';
                                Ext.getCmp("DiagnosisControlVideoPanel_Id").update(videohtml);
                    		}
                        }
                    }
                    
                }
        	]
        })
        this.callParent(arguments);
    }
});