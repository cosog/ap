var rpcFESDiagramCalculateMaintainingHandsontableHelper=null;
Ext.define("AP.view.dataMaintaining.RPCCalculateMaintainingInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.RPCCalculateMaintainingInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var bbar = new Ext.toolbar.Paging({
        	id:'RPCFESDiagramCalculateMaintainingBbar',
            pageSize: defaultPageSize,
            displayInfo: true,
            displayMsg: '当前 {0}~{1}条  共 {2} 条',
            emptyMsg: "没有记录可显示",
            prevText: "上一页",
            nextText: "下一页",
            refreshText: "刷新",
            lastText: "最后页",
            firstText: "第一页",
            beforePageText: "当前页",
            afterPageText: "共{0}页"
        });
        var wellListStore = new Ext.data.JsonStore({
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
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('RPCCalculateMaintainingWellListComBox_Id').getValue();
                    var deviceType=0;
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellName: wellName,
                        deviceType:0
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellListComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: cosog.string.wellName,
                    id: 'RPCCalculateMaintainingWellListComBox_Id',
                    store: wellListStore,
                    labelWidth: 35,
                    width: 125,
                    queryMode: 'remote',
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
                    typeAhead: true,
                    autoSelect: false,
                    allowBlank: true,
                    triggerAction: 'all',
                    editable: true,
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize: comboxPagingStatus,
                    minChars: 0,
                    listeners: {
                    	expand: function (sm, selections) {
                    		wellListComb.getStore().loadPage(1);
                        },
                        select: function (combo, record, index) {
                        	calculateSignComb.clearValue();
                			var activeId = Ext.getCmp("RPCCalculateMaintainingTabPanel").getActiveTab().id;
                			if(activeId=="RPCCalculateMaintainingPanel"){
                				var gridPanel = Ext.getCmp("RPCCalculateMaintainingWellListGridPanel_Id");
                				if (isNotVal(gridPanel)) {
                					gridPanel.getStore().load();
                				}else{
                					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingWellListStore');
                				}
                				
                				var bbar=Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar");
                				if (isNotVal(bbar)) {
                					if(bbar.getStore().isEmptyStore){
                						var RPCCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
                						bbar.setStore(RPCCalculateMaintainingDataStore);
                					}else{
                						bbar.getStore().loadPage(1);
                					}
                				}else{
                					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
                				}
                			}else if(activeId=="RPCTotalCalculateMaintainingPanel"){
                				var gridPanel = Ext.getCmp("RPCCalculateMaintainingWellListGridPanel_Id");
                				if (isNotVal(gridPanel)) {
                					gridPanel.getStore().load();
                				}else{
                					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingWellListStore');
                				}
                				var gridPanel = Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id");
                	            if (isNotVal(gridPanel)) {
                	            	gridPanel.getStore().loadPage(1);
                	            }else{
                	            	Ext.create("AP.store.dataMaintaining.RPCTotalCalculateMaintainingDataStore");
                	            }
                			}
                        }
                    }
                });
        
        var calculateSignStore = new Ext.data.JsonStore({
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/calculateManagerController/getCalculateStatusList',
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
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    var welName=Ext.getCmp('RPCCalculateMaintainingWellListComBox_Id').getValue();
                    var startDate=Ext.getCmp('RPCCalculateMaintainingStartDate_Id').rawValue;
                    var endDate=Ext.getCmp('RPCCalculateMaintainingEndDate_Id').rawValue;
                    var new_params = {
                    		orgId: orgId,
                    		welName: welName,
                            startDate:startDate,
                            endDate:endDate,
                            deviceType:0
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var calculateSignComb = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '计算状态',
                    id: 'RPCCalculateMaintainingCalculateSignComBox_Id',
                    store: calculateSignStore,
                    labelWidth: 60,
                    width: 200,
                    queryMode: 'remote',
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
                    typeAhead: false,
                    autoSelect: false,
                    allowBlank: true,
                    triggerAction: 'all',
                    editable: false,
                    displayField: "boxval",
                    valueField: "boxkey",
                    minChars: 0,
                    listeners: {
                    	expand: function (sm, selections) {
                    		calculateSignComb.clearValue();
                    		calculateSignComb.getStore().load(); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	var gridPanel = Ext.getCmp("RPCCalculateMaintainingWellListGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().load();
            				}else{
            					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingWellListStore');
            				}
            				
            				var bbar=Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar");
            				if (isNotVal(bbar)) {
            					if(bbar.getStore().isEmptyStore){
            						var RPCCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
            						bbar.setStore(RPCCalculateMaintainingDataStore);
            					}else{
            						bbar.getStore().loadPage(1);
            					}
            				}else{
            					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
            				}
                        }
                    }
        });
        Ext.apply(me, {
        	layout: 'border',
            border: false,
            tbar:[wellListComb
    			,"-",{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '',
                labelWidth: 0,
                width: 90,
                format: 'Y-m-d ',
                id: 'RPCCalculateMaintainingStartDate_Id',
                value: '',
                listeners: {
                	select: function (combo, record, index) {
                		calculateSignComb.clearValue();
                		var activeId = Ext.getCmp("RPCCalculateMaintainingTabPanel").getActiveTab().id;
            			if(activeId=="RPCCalculateMaintainingPanel"){
            				var bbar=Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar");
            				if (isNotVal(bbar)) {
            					if(bbar.getStore().isEmptyStore){
            						var RPCCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
            						bbar.setStore(RPCCalculateMaintainingDataStore);
            					}else{
            						bbar.getStore().loadPage(1);
            					}
            				}else{
            					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
            				}
            			}else if(activeId=="RPCTotalCalculateMaintainingPanel"){
            				var gridPanel = Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id");
            	            if (isNotVal(gridPanel)) {
            	            	gridPanel.getStore().loadPage(1);
            	            }else{
            	            	Ext.create("AP.store.dataMaintaining.RPCTotalCalculateMaintainingDataStore");
            	            }
            			}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'RPCCalculateMaintainingStartTime_Hour_Id',
                fieldLabel: '时',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 23,
                value:'',
                msgTarget: 'none',
                regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'RPCCalculateMaintainingStartTime_Minute_Id',
                fieldLabel: '分',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 59,
                value:'',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'RPCCalculateMaintainingStartTime_Second_Id',
                fieldLabel: '秒',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 59,
                value:'',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '至',
                labelWidth: 15,
                width: 105,
                format: 'Y-m-d ',
                id: 'RPCCalculateMaintainingEndDate_Id',
                value: '',
                listeners: {
                	select: function (combo, record, index) {
                		calculateSignComb.clearValue();
                		var activeId = Ext.getCmp("RPCCalculateMaintainingTabPanel").getActiveTab().id;
            			if(activeId=="RPCCalculateMaintainingPanel"){
            				var bbar=Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar");
            				if (isNotVal(bbar)) {
            					if(bbar.getStore().isEmptyStore){
            						var RPCCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
            						bbar.setStore(RPCCalculateMaintainingDataStore);
            					}else{
            						bbar.getStore().loadPage(1);
            					}
            				}else{
            					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
            				}
            			}else if(activeId=="RPCTotalCalculateMaintainingPanel"){
            				var gridPanel = Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id");
            	            if (isNotVal(gridPanel)) {
            	            	gridPanel.getStore().loadPage(1);
            	            }else{
            	            	Ext.create("AP.store.dataMaintaining.RPCTotalCalculateMaintainingDataStore");
            	            }
            			}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'RPCCalculateMaintainingEndTime_Hour_Id',
                fieldLabel: '时',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 23,
                value:'',
                msgTarget: 'none',
                regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'RPCCalculateMaintainingEndTime_Minute_Id',
                fieldLabel: '分',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 59,
                value:'',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'RPCCalculateMaintainingEndTime_Second_Id',
                fieldLabel: '秒',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 59,
                value:'',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },"-",calculateSignComb,'-',{
                xtype: 'button',
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
                pressed: true,
                hidden:false,
                handler: function (v, o) {
                	var activeId = Ext.getCmp("RPCCalculateMaintainingTabPanel").getActiveTab().id;
        			if(activeId=="RPCCalculateMaintainingPanel"){
        				var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                    	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                    	var startTime_Hour=Ext.getCmp('RPCCalculateMaintainingStartTime_Hour_Id').getValue();
                    	if(!r.test(startTime_Hour)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                    		Ext.getCmp('RPCCalculateMaintainingStartTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Minute=Ext.getCmp('RPCCalculateMaintainingStartTime_Minute_Id').getValue();
                    	if(!r2.test(startTime_Minute)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                    		Ext.getCmp('RPCCalculateMaintainingStartTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Second=Ext.getCmp('RPCCalculateMaintainingStartTime_Second_Id').getValue();
                    	if(!r2.test(startTime_Second)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                    		Ext.getCmp('RPCCalculateMaintainingStartTime_Second_Id').focus(true, 100);
                    		return;
                    	}
                    	
                    	var endTime_Hour=Ext.getCmp('RPCCalculateMaintainingEndTime_Hour_Id').getValue();
                    	if(!r.test(endTime_Hour)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                    		Ext.getCmp('RPCCalculateMaintainingEndTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Minute=Ext.getCmp('RPCCalculateMaintainingEndTime_Minute_Id').getValue();
                    	if(!r2.test(endTime_Minute)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                    		Ext.getCmp('RPCCalculateMaintainingEndTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Second=Ext.getCmp('RPCCalculateMaintainingEndTime_Second_Id').getValue();
                    	if(!r2.test(endTime_Second)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                    		Ext.getCmp('RPCCalculateMaintainingEndTime_Second_Id').focus(true, 100);
                    		return;
                    	}
        				
        				
        				
        				var bbar=Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar");
        				if (isNotVal(bbar)) {
        					if(bbar.getStore().isEmptyStore){
        						var RPCCalculateMaintainingDataStore=Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
        						bbar.setStore(RPCCalculateMaintainingDataStore);
        					}else{
        						bbar.getStore().loadPage(1);
        					}
        				}else{
        					Ext.create('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore');
        				}
        			}else if(activeId=="RPCTotalCalculateMaintainingPanel"){
        				var gridPanel = Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id");
        	            if (isNotVal(gridPanel)) {
        	            	gridPanel.getStore().loadPage(1);
        	            }else{
        	            	Ext.create("AP.store.dataMaintaining.RPCTotalCalculateMaintainingDataStore");
        	            }
        			}
                }
            
    		},'->',{
                xtype: 'button',
                text: '修改数据计算',
                id:'RPCCalculateMaintainingUpdateDataBtn',
                pressed: true,
                iconCls: 'edit',
                handler: function (v, o) {
                	rpcFESDiagramCalculateMaintainingHandsontableHelper.saveData();
                }
            },"-",{
                xtype: 'button',
                text: '关联数据计算',
                pressed: true,
                iconCls: 'save',
                id:'RPCCalculateMaintainingLinkedDataBtn',
                handler: function (v, o) {
                	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                	var startTime_Hour=Ext.getCmp('RPCCalculateMaintainingStartTime_Hour_Id').getValue();
                	if(!r.test(startTime_Hour)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                		Ext.getCmp('RPCCalculateMaintainingStartTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var startTime_Minute=Ext.getCmp('RPCCalculateMaintainingStartTime_Minute_Id').getValue();
                	if(!r2.test(startTime_Minute)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                		Ext.getCmp('RPCCalculateMaintainingStartTime_Minute_Id').focus(true, 100);
                		return;
                	}
                	var startTime_Second=Ext.getCmp('RPCCalculateMaintainingStartTime_Second_Id').getValue();
                	if(!r2.test(startTime_Second)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                		Ext.getCmp('RPCCalculateMaintainingStartTime_Second_Id').focus(true, 100);
                		return;
                	}
                	
                	var endTime_Hour=Ext.getCmp('RPCCalculateMaintainingEndTime_Hour_Id').getValue();
                	if(!r.test(endTime_Hour)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                		Ext.getCmp('RPCCalculateMaintainingEndTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var endTime_Minute=Ext.getCmp('RPCCalculateMaintainingEndTime_Minute_Id').getValue();
                	if(!r2.test(endTime_Minute)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                		Ext.getCmp('RPCCalculateMaintainingEndTime_Minute_Id').focus(true, 100);
                		return;
                	}
                	var endTime_Second=Ext.getCmp('RPCCalculateMaintainingEndTime_Second_Id').getValue();
                	if(!r2.test(endTime_Second)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                		Ext.getCmp('RPCCalculateMaintainingEndTime_Second_Id').focus(true, 100);
                		return;
                	}
                	
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName=Ext.getCmp('RPCCalculateMaintainingWellListComBox_Id').getValue();
                    var startDate=Ext.getCmp('RPCCalculateMaintainingStartDate_Id').rawValue;
                    var startTime_Hour=Ext.getCmp('RPCCalculateMaintainingStartTime_Hour_Id').getValue();
                	var startTime_Minute=Ext.getCmp('RPCCalculateMaintainingStartTime_Minute_Id').getValue();
                	var startTime_Second=Ext.getCmp('RPCCalculateMaintainingStartTime_Second_Id').getValue();
                    var endDate=Ext.getCmp('RPCCalculateMaintainingEndDate_Id').rawValue;
                    var endTime_Hour=Ext.getCmp('RPCCalculateMaintainingEndTime_Hour_Id').getValue();
                	var endTime_Minute=Ext.getCmp('RPCCalculateMaintainingEndTime_Minute_Id').getValue();
                	var endTime_Second=Ext.getCmp('RPCCalculateMaintainingEndTime_Second_Id').getValue();
                    var calculateSign=Ext.getCmp('RPCCalculateMaintainingCalculateSignComBox_Id').getValue();
                    var deviceType=0;
                    var showWellName=wellName;
                	if(wellName == '' || wellName == null){
                		if(deviceType==0){
                			showWellName='所选组织下全部抽油机井';
                		}else if(deviceType==1){
                			showWellName='所选组织下全部螺杆泵井';
                		}
                	}else{
//                		showWellName+='井';
                	}
                	var operaName="生效范围："+showWellName+" "+getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second)+"~"+getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)+" </br><font color=red>该操作将导致所选历史数据被当前生产数据覆盖，是否执行！</font>"
                	Ext.Msg.confirm("操作确认", operaName, function (btn) {
                        if (btn == "yes") {
                        	Ext.Ajax.request({
        	            		method:'POST',
        	            		url:context + '/calculateManagerController/recalculateByProductionData',
        	            		success:function(response) {
        	            			var rdata=Ext.JSON.decode(response.responseText);
        	            			if (rdata.success) {
        	                        	Ext.MessageBox.alert("信息","保存成功，开始重新计算，点击左下角刷新按钮查看计算状态列数值，无未计算时，计算完成。");
        	                            //保存以后重置全局容器
        	                            rpcFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
        	                            Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar").getStore().loadPage(1);
        	                        } else {
        	                        	Ext.MessageBox.alert("信息","操作失败");

        	                        }
        	            		},
        	            		failure:function(){
        	            			Ext.MessageBox.alert("信息","请求失败");
        	                        rpcFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
        	            		},
        	            		params: {
        	            			orgId: orgId,
        	            			wellName: wellName,
        	                        startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
        	                        endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
        	                        calculateSign:calculateSign,
        	                        deviceType:deviceType
        	                    }
        	            	}); 
                        }
                    });
                }
            },"-",{
                xtype: 'button',
                text: '导出请求数据',
                pressed: true,
                hidden: false,
                iconCls: 'export',
                id:'RPCCalculateMaintainingExportDataBtn',
                handler: function (v, o) {
                	if(rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.getSelected()){
                		var row=rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.getSelected()[0][0];
                		var recordId=rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRow(row)[0];
                		var wellName=rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRow(row)[1];
                		var acqTime=rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRow(row)[2];
                		var calculateType=1;//1-抽油机井诊断计产 2-螺杆泵井诊断计产 3-抽油机井汇总计算  4-螺杆泵井汇总计算 5-电参反演地面功图计算
                		var url=context + '/calculateManagerController/exportCalculateRequestData?recordId='+recordId+'&wellName='+URLencode(URLencode(wellName))+'&acqTime='+acqTime+'&calculateType='+calculateType;
                    	document.location.href = url;
                	}else{
                		Ext.MessageBox.alert("信息","未选择记录");
                	}
                }
            },{
                xtype: 'button',
                text: '重新汇总',
                id:'RPCCalculateMaintainingReTotalBtn',
                pressed: true,
                hidden:true,
                iconCls: 'edit',
                handler: function (v, o) {
                	ReTotalFESDiagramData();
                }
            },"-",{
                xtype: 'button',
                text: '导出请求数据',
                pressed: true,
                hidden: true,
                iconCls: 'export',
                id:'RPCTotalCalculateMaintainingExportDataBtn',
                handler: function (v, o) {
                	var gridPanel = Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id");
                    var selectionModel = gridPanel.getSelectionModel();
                    var _record = selectionModel.getSelection();
                    if (_record.length>0) {
                    	var recordId=_record[0].data.id;
                    	var wellId=_record[0].data.wellId;
                    	var wellName=_record[0].data.wellName;
                    	var calDate=_record[0].data.calDate;
                		var deviceType=0;
                		var url=context + '/calculateManagerController/exportTotalCalculateRequestData?recordId='+recordId
                		+'&wellId='+wellId
                		+'&wellName='+URLencode(URLencode(wellName))
                		+'&calDate='+calDate
                		+'&deviceType='+deviceType;
                    	document.location.href = url;
                    }else{
                    	Ext.MessageBox.alert("信息","未选择记录");
                    }
                }
            }],
        	items: [{
        		region: 'west',
            	width: '25%',
            	title: '井列表',
            	id: 'RPCCalculateMaintainingWellListPanel_Id',
            	collapsible: true, // 是否可折叠
                collapsed:false,//是否折叠
                split: true, // 竖折叠条
            	layout: "fit"
            },{
            	region: 'center',
            	xtype: 'tabpanel',
        		id:"RPCCalculateMaintainingTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        			title: '单条记录',
    				layout: "fit",
    				id:'RPCCalculateMaintainingPanel',
    				border: false,
    				bbar: bbar,
    				html:'<div class=RPCCalculateMaintainingContainer" style="width:100%;height:100%;"><div class="con" id="RPCCalculateMaintainingDiv_id"></div></div>',
    				listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	if(rpcFESDiagramCalculateMaintainingHandsontableHelper!=null && rpcFESDiagramCalculateMaintainingHandsontableHelper.hot!=undefined){
                        		rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.refreshDimensions();
                        	}
                        }
                    }
        		},{
        			title: '记录汇总',
    				layout: "fit",
    				id:'RPCTotalCalculateMaintainingPanel',
    				border: false
        		}],
        		listeners: {
    				tabchange: function (tabPanel, newCard,oldCard, obj) {
    					if(newCard.id=="RPCCalculateMaintainingPanel"){
    						Ext.getCmp("RPCCalculateMaintainingUpdateDataBtn").show();
    						Ext.getCmp("RPCCalculateMaintainingLinkedDataBtn").show();
    						Ext.getCmp("RPCCalculateMaintainingExportDataBtn").show();
    						Ext.getCmp("RPCCalculateMaintainingCalculateSignComBox_Id").show();
    						Ext.getCmp("RPCCalculateMaintainingReTotalBtn").hide();
    						Ext.getCmp("RPCTotalCalculateMaintainingExportDataBtn").hide();
    					}else if(newCard.id=="RPCTotalCalculateMaintainingPanel"){
    						Ext.getCmp("RPCCalculateMaintainingUpdateDataBtn").hide();
    						Ext.getCmp("RPCCalculateMaintainingLinkedDataBtn").hide();
    						Ext.getCmp("RPCCalculateMaintainingExportDataBtn").hide();
    						Ext.getCmp("RPCCalculateMaintainingCalculateSignComBox_Id").hide();
    						Ext.getCmp("RPCCalculateMaintainingReTotalBtn").show();
    						Ext.getCmp("RPCTotalCalculateMaintainingExportDataBtn").show();
    						var gridPanel = Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id");
    			            if (isNotVal(gridPanel)) {
    			            	gridPanel.getStore().loadPage(1);
    			            }else{
    			            	Ext.create("AP.store.dataMaintaining.RPCTotalCalculateMaintainingDataStore");
    			            }
    					}
    				}
    			}
            }]
        });
        me.callParent(arguments);
    }
});


function CreateAndLoadRPCCalculateMaintainingTable(isNew,result,divid){
	if(isNew&&rpcFESDiagramCalculateMaintainingHandsontableHelper!=null){
        rpcFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
        rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.destroy();
        rpcFESDiagramCalculateMaintainingHandsontableHelper=null;
	}
	
	if(rpcFESDiagramCalculateMaintainingHandsontableHelper==null){
		rpcFESDiagramCalculateMaintainingHandsontableHelper = RPCFESDiagramCalculateMaintainingHandsontableHelper.createNew(divid);
		var colHeaders="[";
        var columns="[";
        for(var i=0;i<result.columns.length;i++){
        	colHeaders+="'"+result.columns[i].header+"'";
        	columns+="{data:'"+result.columns[i].dataIndex+"'";
        	if(result.columns[i].dataIndex.toUpperCase()=="id".toUpperCase()){
        		columns+=",type: 'checkbox'";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="wellName".toUpperCase()||result.columns[i].dataIndex.toUpperCase()==="acqTime".toUpperCase()||result.columns[i].dataIndex.toUpperCase()==="resultName".toUpperCase()){
    			
    		}else if(result.columns[i].dataIndex==="anchoringStateName"){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['锚定', '未锚定']";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="barrelTypeName".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['组合泵', '整筒泵']";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="pumpTypeName".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['杆式泵', '管式泵']";
        	}else if(result.columns[i].dataIndex.toUpperCase()==="pumpGrade".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['1', '2','3', '4','5']";
        	}else if (result.columns[i].dataIndex.toUpperCase() === "manualInterventionResult".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.resultNameList.length; j++) {
                    source += "\'" + result.resultNameList[j] + "\'";
                    if (j < result.resultNameList.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns+=",type:'dropdown',strict:true,allowInvalid:false,source:" + source + "";
            }else if(result.columns[i].dataIndex.toUpperCase()==="rodGrade1".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="rodGrade2".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="rodGrade3".toUpperCase() || result.columns[i].dataIndex.toUpperCase()==="rodGrade4".toUpperCase()){
        		columns+=",type:'dropdown',strict:true,allowInvalid:false,source:['','A','B','C','D','K','KD','HL','HY'], validator: function(val, callback){return handsontableDataCheck_RodGrade(val, callback,this.row, this.col,rpcFESDiagramCalculateMaintainingHandsontableHelper);}";
        	}else{
    			columns+=",type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,rpcFESDiagramCalculateMaintainingHandsontableHelper);}";
    		}
        	columns+="}";
        	if(i<result.columns.length-1){
        		colHeaders+=",";
            	columns+=",";
        	}
        }
        colHeaders+="]";
    	columns+="]";
    	rpcFESDiagramCalculateMaintainingHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
    	rpcFESDiagramCalculateMaintainingHandsontableHelper.columns=Ext.JSON.decode(columns);
		rpcFESDiagramCalculateMaintainingHandsontableHelper.createTable(result.totalRoot);
	}else{
		rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.loadData(result.totalRoot);
	}
};


var RPCFESDiagramCalculateMaintainingHandsontableHelper = {
	    createNew: function (divid) {
	        var rpcFESDiagramCalculateMaintainingHandsontableHelper = {};
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.hot = '';
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.divid = divid;
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.validresult=true;//数据校验
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.colHeaders=[];
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.columns=[];
	        
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.AllData={};
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist=[];
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.delidslist=[];
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.insertlist=[];
	        
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.createTable = function (data) {
	        	$('#'+rpcFESDiagramCalculateMaintainingHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+rpcFESDiagramCalculateMaintainingHandsontableHelper.divid);
	        	rpcFESDiagramCalculateMaintainingHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		fixedColumnsLeft:4, //固定左侧多少列不能水平滚动
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: false
	                },
	                columns:rpcFESDiagramCalculateMaintainingHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:rpcFESDiagramCalculateMaintainingHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                outsideClickDeselects: false, // 点击到表格以外，表格就失去focus
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    if (visualColIndex >= 1 && visualColIndex <= 6) {
							cellProperties.readOnly = true;
							cellProperties.renderer = rpcFESDiagramCalculateMaintainingHandsontableHelper.addBoldBg;
		                }
	                    return cellProperties;
	                },
	                afterDestroy: function() {
	                },
	                beforeRemoveRow: function (index, amount) {
	                    var ids = [];
	                    //封装id成array传入后台
	                    if (amount != 0) {
	                        for (var i = index; i < amount + index; i++) {
	                            var rowdata = rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRow(i);
	                            ids.push(rowdata[0]);
	                        }
	                        rpcFESDiagramCalculateMaintainingHandsontableHelper.delExpressCount(ids);
	                        rpcFESDiagramCalculateMaintainingHandsontableHelper.screening();
	                    }
	                },
	                afterChange: function (changes, source) {
	                    if (changes != null) {
	                    	for(var i=0;i<changes.length;i++){
	                    		var params = [];
	                    		var index = changes[i][0]; //行号码
		                        var rowdata = rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRow(index);
		                        params.push(rowdata[0]);
		                        params.push(changes[i][1]);
		                        params.push(changes[i][2]);
		                        params.push(changes[i][3]);

		                        //仅当单元格发生改变的时候,id!=null,说明是更新
		                        if (params[2] != params[3] && params[0] != null && params[0] >0) {
		                        	var data="{";
		                        	for(var j=0;j<rpcFESDiagramCalculateMaintainingHandsontableHelper.columns.length;j++){
		                        		data+=rpcFESDiagramCalculateMaintainingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
		                        		if(j<rpcFESDiagramCalculateMaintainingHandsontableHelper.columns.length-1){
		                        			data+=","
		                        		}
		                        	}
		                        	data+="}"
		                            rpcFESDiagramCalculateMaintainingHandsontableHelper.updateExpressCount(Ext.JSON.decode(data));
		                        }
	                    	}
	                        
	                    }
	                }
	        	});
	        }
	      //插入的数据的获取
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.insertExpressCount=function() {
	            var idsdata = rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtCol(0); //所有的id
	            for (var i = 0; i < idsdata.length; i++) {
	                //id=null时,是插入数据,此时的i正好是行号
	                if (idsdata[i] == null||idsdata[i]<0) {
	                    //获得id=null时的所有数据封装进data
	                    var rowdata = rpcFESDiagramCalculateMaintainingHandsontableHelper.hot.getDataAtRow(i);
	                    //var collength = hot.countCols();
	                    if (rowdata != null) {
	                    	var data="{";
                        	for(var j=0;j<rpcFESDiagramCalculateMaintainingHandsontableHelper.columns.length;j++){
                        		data+=rpcFESDiagramCalculateMaintainingHandsontableHelper.columns[j].data+":'"+rowdata[j]+"'";
                        		if(j<rpcFESDiagramCalculateMaintainingHandsontableHelper.columns.length-1){
                        			data+=","
                        		}
                        	}
                        	data+="}"
	                        rpcFESDiagramCalculateMaintainingHandsontableHelper.insertlist.push(Ext.JSON.decode(data));
	                    }
	                }
	            }
	            if (rpcFESDiagramCalculateMaintainingHandsontableHelper.insertlist.length != 0) {
	            	rpcFESDiagramCalculateMaintainingHandsontableHelper.AllData.insertlist = rpcFESDiagramCalculateMaintainingHandsontableHelper.insertlist;
	            }
	        }
	        //保存数据
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.saveData = function () {
        		//插入的数据的获取
	        	rpcFESDiagramCalculateMaintainingHandsontableHelper.insertExpressCount();
	            if (JSON.stringify(rpcFESDiagramCalculateMaintainingHandsontableHelper.AllData) != "{}" && rpcFESDiagramCalculateMaintainingHandsontableHelper.validresult) {
	            	var bbarId="RPCFESDiagramCalculateMaintainingBbar";
	            	var deviceType=0;
	            	var calculateType=1;//1-抽油机诊断计产 2-螺杆泵诊断计产 3-抽油机汇总计算  4-螺杆泵汇总计算 5-电参反演地面功图计算
	            	Ext.Ajax.request({
	            		method:'POST',
	            		url:context + '/calculateManagerController/saveRecalculateData',
	            		success:function(response) {
	            			var rdata=Ext.JSON.decode(response.responseText);
	            			if (rdata.success) {
	                        	var successInfo='保存成功，开始重新计算，点击左下角刷新按钮查看计算状态列，无未计算记录时，计算完成。';
	                            //保存以后重置全局容器
	                            rpcFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
	                            Ext.MessageBox.alert("信息",successInfo);
	                            Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar").getStore().loadPage(1);
	                        } else {
	                        	Ext.MessageBox.alert("信息","数据保存失败");
	                        }
	            		},
	            		failure:function(){
	            			Ext.MessageBox.alert("信息","请求失败");
	                        rpcFESDiagramCalculateMaintainingHandsontableHelper.clearContainer();
	            		},
	            		params: {
	                    	data: JSON.stringify(rpcFESDiagramCalculateMaintainingHandsontableHelper.AllData),
	                    	deviceType: deviceType,
	                    	calculateType: calculateType
	                    }
	            	}); 
	            } else {
	                if (!rpcFESDiagramCalculateMaintainingHandsontableHelper.validresult) {
	                	Ext.MessageBox.alert("信息","数据类型错误");
	                } else {
	                	Ext.MessageBox.alert("信息","无数据变化");
	                }
	            }
	        }
	        
	        
	      //删除的优先级最高
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.delExpressCount=function(ids) {
	            //传入的ids.length不可能为0
	            $.each(ids, function (index, id) {
	                if (id != null) {
	                	rpcFESDiagramCalculateMaintainingHandsontableHelper.delidslist.push(id);
	                }
	            });
	            rpcFESDiagramCalculateMaintainingHandsontableHelper.AllData.delidslist = rpcFESDiagramCalculateMaintainingHandsontableHelper.delidslist;
	        }

	        //updatelist数据更新
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.screening=function() {
	            if (rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist.length != 0 && rpcFESDiagramCalculateMaintainingHandsontableHelper.delidslist.lentgh != 0) {
	                for (var i = 0; i < rpcFESDiagramCalculateMaintainingHandsontableHelper.delidslist.length; i++) {
	                    for (var j = 0; j < rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist.length; j++) {
	                        if (rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist[j].id == rpcFESDiagramCalculateMaintainingHandsontableHelper.delidslist[i]) {
	                            //更新updatelist
	                        	rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist.splice(j, 1);
	                        }
	                    }
	                }
	                //把updatelist封装进AllData
	                rpcFESDiagramCalculateMaintainingHandsontableHelper.AllData.updatelist = rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist;
	            }
	        }
	        
	      //更新数据
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.updateExpressCount=function(data) {
	            if (JSON.stringify(data) != "{}") {
	                var flag = true;
	                //判断记录是否存在,更新数据     
	                $.each(rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist, function (index, node) {
	                    if (node.id == data.id) {
	                        //此记录已经有了
	                        flag = false;
	                        //用新得到的记录替换原来的,不用新增
	                        rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist[index] = data;
	                    }
	                });
	                flag && rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist.push(data);
	                //封装
	                rpcFESDiagramCalculateMaintainingHandsontableHelper.AllData.updatelist = rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist;
	            }
	        }
	        
	        rpcFESDiagramCalculateMaintainingHandsontableHelper.clearContainer = function () {
	        	rpcFESDiagramCalculateMaintainingHandsontableHelper.AllData = {};
	        	rpcFESDiagramCalculateMaintainingHandsontableHelper.updatelist = [];
	        	rpcFESDiagramCalculateMaintainingHandsontableHelper.delidslist = [];
	        	rpcFESDiagramCalculateMaintainingHandsontableHelper.insertlist = [];
	        }
	        
	        return rpcFESDiagramCalculateMaintainingHandsontableHelper;
	    }
};

function ReTotalFESDiagramData(){
	var gridPanel = Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id");
    var selectionModel = gridPanel.getSelectionModel();
    var _record = selectionModel.getSelection();
    if (_record.length>0) {
    	var reCalculateData='';
    	Ext.Array.each(_record, function (name, index, countriesItSelf) {
    		reCalculateData+=_record[index].data.id+","+_record[index].data.wellId+","+_record[index].data.wellName+","+_record[index].data.calDate+";"
    	});
    	reCalculateData = reCalculateData.substring(0, reCalculateData.length - 1);
    	Ext.getCmp("RPCTotalCalculateMaintainingPanel").el.mask('重新计算中，请稍后...').show();
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/calculateManagerController/reTotalCalculate',
    		success:function(response) {
    			Ext.getCmp("RPCTotalCalculateMaintainingPanel").getEl().unmask();
    			Ext.MessageBox.alert("信息","重新计算完成。");
                Ext.getCmp("RPCTotalCalculateMaintainingDataGridPanel_Id").getStore().loadPage(1);
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			deviceType: 0,
    			reCalculateDate: reCalculateData
            }
    	}); 
    }else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
}
