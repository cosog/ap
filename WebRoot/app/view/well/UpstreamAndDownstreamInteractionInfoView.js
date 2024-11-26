Ext.define("AP.view.well.UpstreamAndDownstreamInteractionInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.upstreamAndDownstreamInteractionInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var rpcCombStore = new Ext.data.JsonStore({
        	pageSize:defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
            	url: context + '/wellInformationManagerController/loadRPCDeviceComboxList',
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
                	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('UpstreamAndDownstreamInteractionDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 0,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var rpcDeviceCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: deviceShowName,
                    id: "UpstreamAndDownstreamInteractionDeviceListComb_Id",
                    labelWidth: 8*deviceShowNameLength,
                    width: (8*deviceShowNameLength+110),
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: rpcCombStore,
                    autoSelect: false,
                    editable: true,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize:comboxPagingStatus,
                    minChars:0,
                    emptyText: '--'+loginUserLanguageResource.all+'--',
                    blankText: '--'+loginUserLanguageResource.all+'--',
                    listeners: {
                        expand: function (sm, selections) {
                            rpcDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getStore().loadPage(1);
                        }
                    }
                });
        Ext.applyIf(me, {
            items: [{
                border: false,
                layout: 'border',
                items: [{
                	region: 'west',
                    width: '35%',
                    header:false,
                	border: false,
                	collapsible: true,
                    split: true,
                    layout: 'border',
                    items: [{
                    	region: 'center',
                    	title:loginUserLanguageResource.deviceList,
                    	layout: 'fit',
                    	border: false,
                        id:'UpstreamAndDownstreamInteractionDeviceListPanel_Id'
                    }],
                    tbar:[{
                    	id: 'UpstreamAndDownstreamInteractionDeviceListSelectRow_Id',
                    	xtype: 'textfield',
                        value: -1,
                        hidden: true
                     },{
                         xtype: 'button',
                         text: loginUserLanguageResource.refresh,
                         iconCls: 'note-refresh',
                         hidden:false,
                         handler: function (v, o) {
                        	 Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getStore().loadPage(1);
                         }
             		},'-',rpcDeviceCombo]
                }, {
                	
                    region: 'center',
                    header: false,
                    xtype: 'tabpanel',
                    id:"UpstreamAndDownstreamInteractionConfigTabpanel_Id",
                    activeTab: 0,
            		border: false,
            		tabPosition: 'bottom',
            		items: [{
            			title: '下行数据',
            			id:"UpstreamAndDownstreamInteractionConfigPanel1_Id",
            			layout: 'border',
            			tbar:[{
                             xtype: 'radiogroup',
                             fieldLabel: loginUserLanguageResource.operation,
                             labelWidth: getStringLength(loginUserLanguageResource.operation)*8,
                             id: 'UpstreamAndDownstreamInteractionOperation_Id',
                             cls: 'x-check-group-alt',
                             name: 'operation',
                             items: [
                                 {boxLabel: '模型下行',width: 70, inputValue: 1, checked: true},
                                 {boxLabel: '配置下行',width: 70, inputValue: 2},
                                 {boxLabel: '时钟下行',width: 70, inputValue: 3},
                                 {boxLabel: '看门狗重启',width: 85, inputValue: 4},
                                 {boxLabel: '上死点停抽',width: 85, inputValue: 5},
                                 {boxLabel: '下死点停抽',width: 85, inputValue: 6},
                                 {boxLabel: '即时停抽',width: 70, inputValue: 7}
                             ],
                             listeners: {
                            	 change: function (radiogroup, newValue, oldValue, eOpts) {
                            		 if(newValue.operation==1){
                            			 Ext.getCmp("UpstreamAndDownstreamInteractionSyncBtn_Id").show();
                            		 }else{
                            			 Ext.getCmp("UpstreamAndDownstreamInteractionSyncBtn_Id").hide();
                            		 }
                            		 if(newValue.operation==3){
                            			 Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").setText("时钟同步");
                            		 }else{
                            			 Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").setText("发送");
                            		 }
                            		 var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
                            		 if(_record.length>0){
                            			 var commStatus = _record[0].data.commStatus;
                            			 if(parseInt(commStatus)==0){
                            				 Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
                            				 Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue('');
                            			 }else{
                            				 Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").enable();
                            				 requestConfigData();
                            			 }
                            		 }
                            	 }
                             }
                         },'->',{
                             xtype: 'button',
                             text: '模型同步',
                             iconCls: 'sync',
                             id:'UpstreamAndDownstreamInteractionSyncBtn_Id',
                             pressed: false,
                             hidden: false,
                             handler: function (v, o) {
                             	syncModelData();
                             }
                         },{
                             xtype: 'button',
                             text: '发送',
                             iconCls: 'send',
                             id:'UpstreamAndDownstreamInteractionSendBtn_Id',
                             pressed: false,
                             hidden:false,
                             handler: function (v, o) {
                             	producerMsg();
                             }
                         }],
                        items: [{
                        	region: 'center',
                        	title:'下行数据',
                        	xtype:'form',
                    		layout: 'auto',
                            border: false,
                            autoScroll:true,
                            scrollable: true,
                            items: [{
                            	xtype:'textareafield',
                            	id:'UpstreamAndDownstreamInteractionConfigDataTextArea_Id',
                            	margin: '0 0 0 0',
                            	padding:0,
                            	grow:false,//自动增长
                            	border: false,
                            	width:'100%',
                                height: '100%',
                                anchor: '100%',
                                emptyText: '在此输入下行数据...',
                                autoScroll:true,
                                scrollable: true,
                                readOnly:false
                            }]
                        },{
                        	region: 'east',
                            width: '50%',
                            header: true,
                            collapsible: true,
                            split: true,
                            border: false,
                            layout: 'fit',
                            title:'帮助',
                            id:'UpstreamAndDownstreamInteractionConfigHelpDocPanel_Id',
                			autoScroll: true,
                			html:''
                        }]
            		},{
            			title: '含水仪数据',
            			id:"UpstreamAndDownstreamInteractionConfigPanel2_Id",
            			layout: 'border',
            			tbar:[{
                            id: 'UpstreamAndDownstreamInteractionWaterCutRawDataColumnStr_Id',
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },'->',{
                            xtype: 'button',
                            text: loginUserLanguageResource.exportData,
                            id:'UpstreamAndDownstreamInteractionExportWaterCutBtn_Id',
                            iconCls: 'export',
                            hidden: false,
                            handler: function (v, o) {
                            	var timestamp=new Date().getTime();
                            	var key='exportWaterCutRawData'+timestamp;
                            	var maskPanelId='UpstreamAndDownstreamInteractionConfigPanel2_Id';
                            	var fields = "";
                                var heads = "";
                                var lockedheads = "";
                                var unlockedheads = "";
                                var lockedfields = "";
                                var unlockedfields = "";
                                var wellName ='';
                        		var wellId = '';
                        		var signinId ='';
                        		var slave = '';
                            	var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
                            	if(_record.length>0){
                            		wellName = _record[0].data.wellName;
                            		wellId = _record[0].data.id;
                            		signinId = _record[0].data.signinId;
                            		slave = _record[0].data.slave;
                            	}
                                var url = context + '/wellInformationManagerController/exportWaterCutRawData';
                                var param = "&fields=" + fields 
                                + "&heads=" + URLencode(URLencode(heads)) 
                                + "&signinId=" + signinId 
                                + "&slave=" + slave
                                + "&fileName=" + URLencode(URLencode(wellName+"含水数据")) 
                                + "&title=" + URLencode(URLencode(wellName+"含水数据"))
                                + '&key='+key;
                                exportDataMask(key,maskPanelId,cosog.string.loading);
                                openExcelWindow(url + '?flag=true' + param);
                            }
                        }, '-',{
                             xtype: 'button',
                             text: '读含水',
                             iconCls: 'send',
                             id:'UpstreamAndDownstreamInteractionReadWaterCutBtn_Id',
                             pressed: false,
                             hidden:false,
                             handler: function (v, o) {
                            	 readWaterCutRawData();
                             }
                         }],
                         items: [{
                        	region: 'north',
                         	height: '50%',
                         	title:'含水仪曲线',
                         	layout: 'fit',
                         	border: false,
                         	split: true,
                            collapsible: true,
                            id:'UpstreamAndDownstreamInteractionWaterCutRawDataCurvePanel_Id',
                            html: '<div id="UpstreamAndDownstreamInteractionWaterCutRawDataCurveDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    if ($("#UpstreamAndDownstreamInteractionWaterCutRawDataCurveDiv_Id").highcharts() != undefined) {
                                    	highchartsResize("UpstreamAndDownstreamInteractionWaterCutRawDataCurveDiv_Id");
                                    }
                                }
                            }
                         },{
                        	region: 'center',
                        	title:'含水仪数据',
                         	border: false,
                            id:'UpstreamAndDownstreamInteractionWaterCutRawDataPanel_Id',
                        	layout: 'fit'
                        }],
            		}],
            		listeners: {
            			tabchange: function (tabPanel, newCard,oldCard, obj) {
               			 	var commStatus = 0;
            				var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
            				if(_record.length>0){
            					commStatus = _record[0].data.commStatus;
            				}
            				
            				if(newCard.id=="UpstreamAndDownstreamInteractionConfigPanel1_Id"){
            					if(parseInt(commStatus)==0){
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
               			 			Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue('');
               			 		}else{
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").enable();
               			 			requestConfigData();
               			 		}
        					}else if(newCard.id=="UpstreamAndDownstreamInteractionConfigPanel2_Id"){
        						Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataPanel_Id").removeAll();
        						$("#UpstreamAndDownstreamInteractionWaterCutRawDataCurveDiv_Id").html('');
        						Ext.getCmp("UpstreamAndDownstreamInteractionExportWaterCutBtn_Id").disable();
               			 		if(parseInt(commStatus)==0){
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionReadWaterCutBtn_Id").disable();
               			 		}else{
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionReadWaterCutBtn_Id").enable();
               			 		}
        					}
        				}
            		}
                }],
                listeners: {
                	afterrender: function ( panel, eOpts) {
                		//加载帮助文档
        				Ext.Ajax.request({
        		    		method:'POST',
        		    		url:context + '/wellInformationManagerController/getHelpDocHtml',
        		    		success:function(response) {
        		    			var p =Ext.getCmp("UpstreamAndDownstreamInteractionConfigHelpDocPanel_Id");
        		    			p.body.update(response.responseText);
        		    		},
        		    		failure:function(){
        		    			Ext.MessageBox.alert("信息","帮助文档加载失败");
        		    		},
        		    		params: {
        		            }
        		    	}); 
                	}
                }
            }]
        });
        me.callParent(arguments);
    }
});

function createUpstreamAndDownstreamInteractionDeviceListColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        var flex_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_+flex_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }
        else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='upCommStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceUpCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='downCommStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceDownCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        }
        else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

function syncModelData(){
    var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		
		var wellName = _record[0].data.wellName;
		var wellId = _record[0].data.id;
		var signinId = _record[0].data.signinId;
		var slave = _record[0].data.slave;

    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/wellInformationManagerController/getDeviceModelData',
    		success:function(response) {
    			Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue(response.responseText);
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			wellId:wellId,
    			signinId:signinId,
    			slave:slave
            }
    	}); 
	}
}

function producerMsg(){
    var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		var type = Ext.getCmp("UpstreamAndDownstreamInteractionOperation_Id").getValue().operation;
		var operationName ='';
		if(parseInt(type)==1){
			operationName='模型下行';
		}else if(parseInt(type)==2){
			operationName='配置下行';
		}else if(parseInt(type)==3){
			operationName='时钟下行';
		}else if(parseInt(type)==4){
			operationName='看门狗重启下行';
		}else if(parseInt(type)==5){
			operationName='上死点停抽下行';
		}else if(parseInt(type)==6){
			operationName='下死点停抽下行';
		}
		var wellName = _record[0].data.wellName;
		var wellId = _record[0].data.id;
		var signinId = _record[0].data.signinId;
		var slave = _record[0].data.slave;
		var data=Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').getValue();
		var operaName="是否对设备<font color=red>"+wellName+"</font>执行<font color=red>"+operationName+"</font>操作！";
		Ext.Msg.confirm("操作确认", operaName, function (btn) {
            if (btn == "yes") {
            	Ext.Ajax.request({
            		method:'POST',
            		url:context + '/wellInformationManagerController/downstreamRPCData',
            		success:function(response) {
            			rdata=Ext.JSON.decode(response.responseText);
            			if (rdata.success && rdata.msg==1 ) {
                        	Ext.MessageBox.alert("信息","下行成功。");
                        } else if (rdata.success && rdata.msg==0 ) {
                        	Ext.MessageBox.alert("信息","<font color=red>下行失败<font color=red>!");
                        }else if (!rdata.success){
                        	Ext.MessageBox.alert("信息","<font color=red>发送失败<font color=red>!");
                        }
            		},
            		failure:function(){
            			Ext.MessageBox.alert("信息","请求失败");
            		},
            		params: {
            			type: type,
            			wellId:wellId,
            			signinId:signinId,
            			slave:slave,
            			data,data
                    }
            	}); 
            }
        });
	}
}

function requestConfigData(){
	Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue('');
	var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		var type = Ext.getCmp("UpstreamAndDownstreamInteractionOperation_Id").getValue().operation;
		var wellName = _record[0].data.wellName;
		var wellId = _record[0].data.id;
		var signinId = _record[0].data.signinId;
		var slave = _record[0].data.slave;
		Ext.Ajax.request({
    		method:'POST',
    		url:context + '/wellInformationManagerController/requestConfigData',
    		success:function(response) {
    			if (isNotVal(response.responseText)) {
    				rdata=Ext.JSON.decode(response.responseText);
    				if(rdata.ResultStatus==1){
    					Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue(jsonFormat(JSON.stringify(rdata.Message)));
    				}
    			}
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			type: type,
    			wellId:wellId,
    			signinId:signinId,
    			slave:slave
            }
    	});
	}
}

function showWaterCutRawDataCurve(result){
	var divId="UpstreamAndDownstreamInteractionWaterCutRawDataCurveDiv_Id";
	var wellName ='';
	var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		wellName = _record[0].data.wellName;
	}
	var data = result.totalRoot;
	var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
	var tickInterval = 1;
    tickInterval = Math.floor(data.length / 10) + 1;
    if(tickInterval<100){
    	tickInterval=100;
    }
    var title = wellName + "趋势曲线";
    var subtitle=result.acqTime;
    var legendName=['含水率(%)','压力(MPa)'];
    var color=['#ae1919','#33a91f'];
    var series = "[";
    var yAxis= [];
    for (var i = 0; i < legendName.length; i++) {
    	series += "{\"name\":\"" + legendName[i] + "\",marker:{enabled: false},"+"\"yAxis\":"+i+",";
        series += "\"data\":[";
        for (var j = 0; j < data.length; j++) {
        	if(i==0){
//        		series += data[j].waterCut;
        		series += "[" + data[j].timeStamp + "," + parseFloat(data[j].waterCut) + "]";
        	}else{
//        		series += data[j].tubingPressure;
        		series += "[" + data[j].timeStamp + "," + parseFloat(data[j].tubingPressure) + "]";
        	}
            if (j != data.length - 1) {
                series += ",";
            }
        }
        series += "]}";
        if (i != legendName.length - 1) {
            series += ",";
        }
        var opposite=false;
        if(i>0){
        	opposite=true;
        }
        
        var singleAxis={
        		title: {
                    text: legendName[i],
                    style: {
                        color: color[i],
                    }
                },
                labels: {
                	style: {
                        color: color[i],
                    }
                },
                opposite:opposite
          };
        yAxis.push(singleAxis);
        
    }
    series += "]";
    
    var ser = Ext.JSON.decode(series);
    var timeFormat='%m-%d';
    initWaterCutRawDataCurveChartFn(ser, tickInterval, divId, title, subtitle, '', yAxis, color,true,timeFormat);
}

function showWaterCutRawDataCurve2(result){
	var divId="UpstreamAndDownstreamInteractionWaterCutRawDataCurveDiv_Id";
	var wellName ='';
	var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		wellName = _record[0].data.wellName;
	}
	var totals=0;
	var acqTime='';
	var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
	var tickInterval = 1;
	
	var subtitle='';
    var legendName=['含水率(%)','压力(MPa)'];
    var color=['#ae1919','#33a91f'];
    var series = "";
    var waterCutSeries="{\"name\":\"" + legendName[0] + "\",marker:{enabled: false},"+"\"yAxis\":"+0+",\"data\":[";
    var tubingPressureSeries="{\"name\":\"" + legendName[1] + "\",marker:{enabled: false},"+"\"yAxis\":"+1+",\"data\":[";
    var yAxis= [];
    var waterCutAxis={
    		title: {
                text: legendName[0],
                style: {
                    color: color[0],
                }
            },
            labels: {
            	style: {
                    color: color[0],
                }
            },
            opposite:false
      };
    var tubingPressureAxis={
    		title: {
                text: legendName[1],
                style: {
                    color: color[1],
                }
            },
            labels: {
            	style: {
                    color: color[1],
                }
            },
            opposite:true
      };
    yAxis.push(waterCutAxis);
    yAxis.push(tubingPressureAxis);
    
	if(isNotVal(result)){
		if(result.ResultStatus==1){

			totals=result.Message.WaterCut.length;
			acqTime=result.Message.AcqTime;
			subtitle=acqTime;
			var startTime=acqTime.split("~")[0];
			var timeStamp=timeStr2TimeStamp(startTime,"yyyy-MM-dd HH:mm:ss");
			for(var i=0;i<totals;i++){
				if(i>0){
					timeStamp+=result.Message.Interval[i];
				}
				waterCutSeries += "[" + timeStamp + "," + parseFloat(result.Message.WaterCut[i]) + "]";
				tubingPressureSeries += "[" + timeStamp + "," + parseFloat(result.Message.TubingPressure[i]) + "]";
				if (i != totals - 1) {
					waterCutSeries += ",";
					tubingPressureSeries += ",";
	            }
			}
			
		}
	}
	waterCutSeries += "]}";
	tubingPressureSeries += "]}";
	series ="["+waterCutSeries+","+tubingPressureSeries+ "]";
	
	
    tickInterval = Math.floor(totals / 10) + 1;
    if(tickInterval>100){
    	tickInterval=100;
    }else if(tickInterval<10){
    	tickInterval=10;
    }
    var title = wellName + "趋势曲线";
    
    
    var ser = Ext.JSON.decode(series);
    var timeFormat='%H:%M';//%Y-%m-%d %H:%M:%S.%L
    initWaterCutRawDataCurveChartFn(ser, tickInterval, divId, title, subtitle, '', yAxis, color,true,timeFormat);
}

function initWaterCutRawDataCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
	var dafaultMenuItem = Highcharts.getOptions().exporting.buttons.contextButton.menuItems;
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    var mychart = new Highcharts.stockChart({
        chart: {
            renderTo: divId,
            type: 'spline',
            shadow: true,
            borderWidth: 0,
            zoomType: 'xy'
        },
        credits: {
            enabled: false
        },
        scrollbar: {
    		enabled: false
    	},
        title: {
            text: title
        },
        subtitle: {
            text: subtitle
        },
        rangeSelector: {
    		buttons: [{
    			count: 10,
    			type: 'minute',//minute hour week month all
    			text: '10分钟'
    		},{
    			count: 1,
    			type: 'hour',//minute hour week month all
    			text: '1小时'
    		}, {
    			count: 6,
    			type: 'hour',
    			text: '6小时'
    		}, {
    			count: 12,
    			type: 'hour',
    			text: '12小时'
    		}, {
    			type: 'all',
    			text: loginUserLanguageResource.all
    		}],
    		inputEnabled: false,
    		selected: 0
    	},
        colors: color,
        xAxis: {
            type: 'datetime',
            title: {
                text: ''
            },
            tickPixelInterval:tickInterval,
            labels: {
                formatter: function () {
                    return Highcharts.dateFormat(timeFormat, this.value);
                },
                autoRotation:true,//自动旋转
                rotation: -45 //倾斜度，防止数量过多显示不全  
//                step: 2
            }
        },
        yAxis: yAxis,
        tooltip: {
            crosshairs: true, //十字准线
            shared: true,
            style: {
                color: '#333333',
                fontSize: '12px',
                padding: '8px'
            },
            dateTimeLabelFormats: {
                millisecond: '%Y-%m-%d %H:%M:%S.%L',
                second: '%Y-%m-%d %H:%M:%S',
                minute: '%Y-%m-%d %H:%M',
                hour: '%Y-%m-%d %H',
                day: '%Y-%m-%d',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            }
        },
        exporting: {
            enabled: true,
            filename: title,
            sourceWidth: $("#"+divId)[0].offsetWidth,
            sourceHeight: $("#"+divId)[0].offsetHeight
        },
        plotOptions: {
            spline: {
//                lineWidth: 1,
                fillOpacity: 0.3,
                marker: {
                    enabled: true,
                    radius: 3, //曲线点半径，默认是4
                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                    states: {
                        hover: {
                            enabled: true,
                            radius: 6
                        }
                    }
                },
                shadow: true,
                events: {
                	legendItemClick: function(e){
//                		alert("第"+this.index+"个图例被点击，是否可见："+!this.visible);
//                		return true;
                	}
                }
            }
        },
        legend: {
            layout: 'vertical',//horizontal水平 vertical 垂直
            align: 'right',  //left，center 和 right
            verticalAlign: 'top',//top，middle 和 bottom
            floating:true,
            x: 0,
            y: -50,
            enabled: legend,
            borderWidth: 0
        },
        series: series
    });
};

function readWaterCutRawData(){
    var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		var wellName = _record[0].data.wellName;
		var wellId = _record[0].data.id;
		var signinId = _record[0].data.signinId;
		var slave = _record[0].data.slave;
		Ext.getCmp("UpstreamAndDownstreamInteractionConfigPanel2_Id").el.mask("含水数据读取中...").show();
		Ext.Ajax.timeout=180000;
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/wellInformationManagerController/getWaterCutRawData2',
    		success:function(response) {
    			Ext.getCmp("UpstreamAndDownstreamInteractionConfigPanel2_Id").getEl().unmask();
    			var result=null;
    			if(isNotVal(response.responseText)){
    				result=Ext.decode(response.responseText);
    			}
    			
    			var data=[];
    			var totals=0;
    			var acqTime="";
    			if(isNotVal(result)){
    				if(result.ResultStatus==1){
    					Ext.getCmp("UpstreamAndDownstreamInteractionExportWaterCutBtn_Id").enable();
    					totals=result.Message.WaterCut.length;
    					acqTime=result.Message.AcqTime;
    					var startTime=acqTime.split("~")[0];
    					var timeStamp=timeStr2TimeStamp(startTime,"yyyy-MM-dd HH:mm:ss");
    					for(var i=0;i<totals;i++){
    						if(i>0){
    							timeStamp+=result.Message.Interval[i];
    						}
    						var pointAcqTime=timestamp2Str(timeStamp);
    						
    						var waterCutData={};
    						waterCutData.id=i+1;
    						waterCutData.pointAcqTime=pointAcqTime;
    						waterCutData.timeStamp=timeStamp;
    						waterCutData.interval=result.Message.Interval[i]+'';
    						waterCutData.waterCut=result.Message.WaterCut[i]+'';
    						waterCutData.tubingPressure=result.Message.TubingPressure[i]+'';
    						waterCutData.position=result.Message.Position[i]+'';
    						data.push(waterCutData);
    					}
    				}else if(result.OutOfMemory){
    					Ext.MessageBox.alert("信息", "数据过大，加载失败，请将配置下行中StoreAcqWaterCut的值修改位更小值！");
    				}
    			}
    			
    			var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataGridPanel_Id");
    			if (!isNotVal(gridPanel)) {
    				var store=Ext.create('Ext.data.Store', {
    				    fields:['id','interval','waterCut','tubingPressure','position'],
    				    data:data
    				});
        			
        			gridPanel = Ext.create('Ext.grid.Panel', {
                        id: "UpstreamAndDownstreamInteractionWaterCutRawDataGridPanel_Id",
                        border: false,
                        autoLoad: false,
                        columnLines: true,
                        forceFit: false,
                        viewConfig: {
                        	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                        },
                        store: store,
                        columns: [{
                            text: '序号',
                            lockable: true,
                            align: 'center',
                            width: 50,
                            xtype: 'rownumberer',
                            sortable: false,
                            locked: false
                        }, {
                            text: '采样时间',
                            lockable: true,
                            align: 'center',
                            flex: 2,
                            sortable: false,
                            dataIndex: 'pointAcqTime',
                            renderer: function (value) {
                                if (isNotVal(value)) {
                                    return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                                }
                            }
                        }, {
                            text: '采样间隔(ms)',
                            lockable: true,
                            align: 'center',
                            flex: 1,
                            sortable: false,
                            dataIndex: 'interval',
                            renderer: function (value) {
                                if (isNotVal(value)) {
                                    return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                                }
                            }
                        }, {
                            text: '含水率(%)',
                            lockable: true,
                            align: 'center',
                            flex: 1,
                            sortable: false,
                            dataIndex: 'waterCut',
                            renderer: function (value) {
                                if (isNotVal(value)) {
                                    return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                                }
                            }
                        }, {
                            text: '压力(MPa)',
                            lockable: true,
                            align: 'center',
                            flex: 1,
                            sortable: false,
                            dataIndex: 'tubingPressure',
                            renderer: function (value) {
                                if (isNotVal(value)) {
                                    return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                                }
                            }
                        }, {
                            text: '位置',
                            lockable: true,
                            align: 'center',
                            flex: 1,
                            sortable: false,
                            dataIndex: 'position',
                            renderer: function (value) {
                                if (isNotVal(value)) {
                                    return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                                }
                            }
                        }],
                        listeners: {
                        	selectionchange: function (view, selected, o) {},
                        	itemdblclick: function (view,record,item,index,e,eOpts) {},
                        	select: function(grid, record, index, eOpts) {}
                        }
                    });
                    var panel = Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataPanel_Id");
                    panel.add(gridPanel);
    			}else{
    				gridPanel.getStore().loadData(data);
    			}
    			
    			showWaterCutRawDataCurve2(result);
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			wellId:wellId,
    			signinId:signinId,
    			slave:slave
            }
    	}); 
	}
}
