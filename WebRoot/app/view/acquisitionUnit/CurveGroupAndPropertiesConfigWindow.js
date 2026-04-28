Ext.define("AP.view.acquisitionUnit.CurveGroupAndPropertiesConfigWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.curveGroupAndPropertiesConfigWindow',
    id: 'curveGroupAndPropertiesConfigWindow_Id',
    title: loginUserLanguageResource.curveConfig,
    layout: 'fit',
    iframe: true,
    closeAction: 'destroy',
    shadow: 'sides',
    resizable: true,
    collapsible: true,
    constrain: true,
    width: 400,
    height: 500,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    
    initComponent: function () {
        var me = this;
        
        var curveGroupStore = new Ext.data.SimpleStore({
        	fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
			proxy : {
				url : context+ '/acquisitionUnitManagerController/getCurveGroupCombList',
				type : "ajax",
				actionMethods: {
                    read: 'POST'
                },
                reader: {
                	type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
			},
			autoLoad : true,
			listeners : {
				beforeload : function(store, options) {
					var curveConfigCurveType=Ext.getCmp("curveConfigCurveType_Id").getValue();
					var new_params = {
							type: curveConfigCurveType
					};
					Ext.apply(store.proxy.extraParams,new_params);
				},
				load :function( store, records, successful, operation, node, eOpts ) {
					
				}
			}
		});
        
        var curveGroupComb = Ext.create(
				'Ext.form.field.ComboBox', {
					fieldLabel : '曲线组'+'<font color=red>*</font>',
					id : 'curveGroupComb_Id',
					anchor : '100%',
					store: curveGroupStore,
					queryMode : 'remote',
					typeAhead : true,
					autoSelect : false,
					allowBlank : false,
					triggerAction : 'all',
					editable : false,
					displayField : "boxval",
					valueField : "boxkey",
					listeners : {
						select: function (v,o) {
							
						},
						expand: function (sm, selections) {
							curveGroupComb.getStore().load(); // 加载井下拉框的store
                        },
					}
				});
        
        var curveConfigWindowForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
				xtype : "hidden",
				id : 'curveConfigSelectedTableType_Id',
				value:0
			},{
				xtype : "hidden",
				id : 'curveConfigSelectedRow_Id',
				value:-1
			},{
				xtype : "hidden",
				id : 'curveConfigSelectedCol_Id',
				value:-1
			},curveGroupComb,{
            	xtype: 'numberfield',
            	id: "curveConfigSort_Id",
                fieldLabel: loginUserLanguageResource.curveSort,
                value:1,
                allowBlank: false,
                editable : false,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            },{
                id: 'curveConfigColor_id',
                fieldLabel: loginUserLanguageResource.curveColor,
                xtype: 'colorfield',
                anchor:'100%',
                allowBlank: false,
                editable : false,
                listeners : {
                	collapse: function (field,eOpts) {
                    	if(Ext.getCmp('curveConfigColor_id')!=undefined){
                    		var opacity=field.color.a;
                    		field.setValue(field.value);
            	        	var BackgroundColor0=field.color;
            	        	BackgroundColor0.a=opacity;
            	        	field.inputEl.applyStyles({
            	        		background: '#'+field.value,
            	        		opacity:opacity
            	        	});
            	        	field.setColor(BackgroundColor0);
                    	}
                    }
                }
        	}, {
            	xtype: 'numberfield',
            	id: "curveConfigLineWidth_Id",
                fieldLabel: loginUserLanguageResource.lineWidth,
                value:3,
                allowBlank: false,
                editable : false,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }, {
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.lineDash,
				id : 'curveConfigDashStyleComb_Id',
				anchor : '100%',
				value:'Solid',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [
								['Solid', 'Solid'],
								['ShortDash', 'ShortDash'],
								['ShortDot', 'ShortDot'],
								['ShortDashDot', 'ShortDashDot'],
								['ShortDashDotDot', 'ShortDashDotDot'],
								['Dot', 'Dot'],
								['Dash', 'Dash'],
								['LongDash', 'LongDash'],
								['DashDot', 'DashDot'],
								['LongDashDot', 'LongDashDot'],
								['LongDashDotDot', 'LongDashDotDot']
								]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : loginUserLanguageResource.selectLineDash,
				blankText : loginUserLanguageResource.selectLineDash,
				listeners : {
					select:function(v,o){
						
					}
				}
            },{
            	xtype : "combobox",
				fieldLabel : loginUserLanguageResource.yAxisPosition,
				id : 'curveConfigYAxisOppositeComb_Id',
				value:false,
				anchor : '100%',
				triggerAction : 'all',
				selectOnFocus : false,
			    forceSelection : true,
			    allowBlank: false,
				editable : false,
				store : new Ext.data.SimpleStore({
							fields : ['value', 'text'],
							data : [
								[false, loginUserLanguageResource.left],
								[true, loginUserLanguageResource.right]
								]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : loginUserLanguageResource.selectYAxisPosition,
				blankText : loginUserLanguageResource.selectYAxisPosition,
				listeners : {
					select:function(v,o){
						
					}
				}
            }],
            buttons: [{
            	xtype: 'button',
            	text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function () {
                	var groupId=Ext.getCmp("curveGroupComb_Id").getValue();
                	var groupName=Ext.getCmp("curveGroupComb_Id").rawValue;
                	
                	var sort=Ext.getCmp("curveConfigSort_Id").getValue();
					var lineWidth=Ext.getCmp("curveConfigLineWidth_Id").getValue();
					var dashStyle=Ext.getCmp("curveConfigDashStyleComb_Id").getValue();
					var yAxisOpposite=Ext.getCmp("curveConfigYAxisOppositeComb_Id").getValue();
		        	var color=Ext.getCmp('curveConfigColor_id').getValue();
		        	
		        	var row=Ext.getCmp('curveConfigSelectedRow_Id').getValue();
                	var col=Ext.getCmp('curveConfigSelectedCol_Id').getValue();
                	var tableType=Ext.getCmp('curveConfigSelectedTableType_Id').getValue();
                	if(parseInt(row)>=0 && parseInt(col)>=0){
                		if(tableType==0){
                			var showValue='曲线组:'+(isNotVal(groupName)?groupName:loginUserLanguageResource.nothing)+";"+
                				sort+";"+(yAxisOpposite?loginUserLanguageResource.right:loginUserLanguageResource.left)+';'+color;
                			protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),showValue);
                			var curveConfig={};
                			curveConfig.groupId=groupId;
                			curveConfig.groupName=groupName;
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==12){
                				protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),21,curveConfig);
                			}else if(parseInt(col)==19){
                				protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),22,curveConfig);
                			}
                		}else if(tableType==21){//单井区间报表汇总计算项
                			reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),sort+';'+(yAxisOpposite?loginUserLanguageResource.right:loginUserLanguageResource.left)+';'+color);
                			var curveConfig={};
                			curveConfig.groupId=groupId;
                			curveConfig.groupName=groupName;
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==8){
                				reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),9,curveConfig);
                			}
                		}else if(tableType==22){//区域报表汇总计算项
                			reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),sort+';'+(yAxisOpposite?loginUserLanguageResource.right:loginUserLanguageResource.left)+';'+color);
                			var curveConfig={};
                			curveConfig.groupId=groupId;
                			curveConfig.groupName=groupName;
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==10){
                				reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),12,curveConfig);
                			}
                		}else if(tableType==23){//单井单日报表汇总计算项
                			reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),sort+';'+(yAxisOpposite?loginUserLanguageResource.right:loginUserLanguageResource.left)+';'+color);
                			var curveConfig={};
                			curveConfig.groupId=groupId;
                			curveConfig.groupName=groupName;
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==8){
                				reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),9,curveConfig);
                			}
                		}
                	}
                	
                	Ext.getCmp("curveGroupAndPropertiesConfigWindow_Id").close();
                }
            },{
        	 	xtype: 'button',   
        	 	text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("curveGroupAndPropertiesConfigWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
        	items:[{
        		xtype: 'tabpanel',
            	border: false,
            	header: false,
                activeTab: 1,
                tbar:[{
    				xtype : "hidden",
    				id : 'curveConfigCurveType_Id',
    				value:0
    			}],
            	items:[{
                	title:'曲线组',
                	id:'curveGroupConfigPanel_Id',
                	layout: "border",
                	tbar: ['->',{
                    	xtype: 'button',
            			text: loginUserLanguageResource.save,
            			disabled: loginUserProtocolConfigModuleRight.editFlag!=1,
            			iconCls: 'save',
            			handler: function (v, o) {
            				saveCurveGroupData();
            			}
                    }],
                	items:[{
                		title:loginUserLanguageResource.realtimeMonitoring,
                		region: 'center',
                		layout: 'fit',
                    	id:"realtimeCurveGroupConfigPanel_Id"
                	},{
                		title:loginUserLanguageResource.historyQuery,
                		region: 'south',
                    	height:'50%',
                    	layout: 'fit',
                    	split: true,
                        collapsible: true,
                    	id:"historyCurveGroupConfigPanel_Id"
                    }]
            	},{
                	border: false,
                	id:"curvePropertiesConfigPanel_Id",
                    title:loginUserLanguageResource.curveProperty,
                    iconCls: 'check3',
                    layout: 'fit',
                    maximizable: false,
                    plain: true,
                    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
                    modal: true,
                    border: false,
                    items: curveConfigWindowForm
            	}],
            	listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                		if(oldCard!=undefined){
                			oldCard.setIconCls(null);
                		}
                		if(newCard!=undefined){
                			newCard.setIconCls('check3');
                		}
        			},
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
        				if(newCard.id=="curveGroupConfigPanel_Id"){
        					var realtimeCurveGroupConfigGridPanel = Ext.getCmp("realtimeCurveGroupConfigGridPanel_Id");
        					if(isNotVal(realtimeCurveGroupConfigGridPanel)){
        						realtimeCurveGroupConfigGridPanel.getStore().load();
        					}else{
        						Ext.create("AP.store.acquisitionUnit.CurveGroupRealtimeInfoStore");
        					}
        					
        					var historyCurveGroupConfigGridPanel = Ext.getCmp("historyCurveGroupConfigGridPanel_Id");
        					if(isNotVal(historyCurveGroupConfigGridPanel)){
        						historyCurveGroupConfigGridPanel.getStore().load();
        					}else{
        						Ext.create("AP.store.acquisitionUnit.CurveGroupHistoryInfoStore");
        					}
        				}else if(newCard.id=="curvePropertiesConfigPanel_Id"){
        					
        				}
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

function saveCurveGroupData(){
	var curveGroupConfigGridPanel = Ext.getCmp("realtimeCurveGroupConfigGridPanel_Id");
	var modifiedRecords = curveGroupConfigGridPanel.getStore().getModifiedRecords();
	
	var historyCurveGroupConfigGridPanel = Ext.getCmp("historyCurveGroupConfigGridPanel_Id");
	var historyCurveGroupModifiedRecords = historyCurveGroupConfigGridPanel.getStore().getModifiedRecords();
	
	var modifiedData=[];
	modifiedRecords.forEach(function(record) {
		var curveGroupData={};
		curveGroupData.id=record.data.groupId;
		curveGroupData.name=record.data.name;
		curveGroupData.sort=record.data.sort;
		curveGroupData.type=record.data.type;
		modifiedData.push(curveGroupData);
    });
	historyCurveGroupModifiedRecords.forEach(function(record) {
		var curveGroupData={};
		curveGroupData.id=record.data.groupId;
		curveGroupData.name=record.data.name;
		curveGroupData.sort=record.data.sort;
		curveGroupData.type=record.data.type;
		modifiedData.push(curveGroupData);
    });
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveCurveGroupData',
		success:function(response) {
			var data=Ext.JSON.decode(response.responseText);
			
			if (data.success) {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
            	curveGroupConfigGridPanel.getStore().commitChanges();
            	curveGroupConfigGridPanel.getStore().load();
            	
            	historyCurveGroupConfigGridPanel.getStore().commitChanges();
            	historyCurveGroupConfigGridPanel.getStore().load();
            	
            	var curveConfigCurveType=Ext.getCmp("curveConfigCurveType_Id").getValue();
            	var groupId=Ext.getCmp("curveGroupComb_Id").getValue();
            	
            	var row=Ext.getCmp('curveConfigSelectedRow_Id').getValue();
            	var col=Ext.getCmp('curveConfigSelectedCol_Id').getValue();
            	var tableType=Ext.getCmp('curveConfigSelectedTableType_Id').getValue();
            	
            	var realtimeCurveConfArr=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtProp('realtimeCurveConf');
            	var historyCurveConfArr=protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.getDataAtProp('historyCurveConf');
            	
            	
            	for (var i = 0; i < modifiedData.length; i++) {
            	    var record = modifiedData[i];
            	    if (groupId == record.id) {
            	        Ext.getCmp("curveGroupComb_Id").setRawValue(record.name);
            	    }
            	    if(record.type==1){
            	    	for(var j=0;j<realtimeCurveConfArr.length;j++){
            	    		if(isNotVal(realtimeCurveConfArr[j]) && realtimeCurveConfArr[j].groupId== record.id ){
            	    			realtimeCurveConfArr[j].groupName=record.name;
            	    			protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtRowProp(j,'realtimeCurveConf',realtimeCurveConfArr[j]);
            	    			
            	    			
            	    			var showValue='曲线组:'+(isNotVal(realtimeCurveConfArr[j].groupName)?realtimeCurveConfArr[j].groupName:loginUserLanguageResource.nothing)+";"+
            	    			realtimeCurveConfArr[j].sort+";"+(realtimeCurveConfArr[j].yAxisOpposite?loginUserLanguageResource.right:loginUserLanguageResource.left)+';'+realtimeCurveConfArr[j].color;
            	    			protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtRowProp(j,'realtimeCurveConfShowValue',showValue);
            	    			
            	    			break;
            	    		}
            	    	}
            	    }else if(record.type==2){
            	    	for(var j=0;j<historyCurveConfArr.length;j++){
            	    		if(isNotVal(historyCurveConfArr[j]) && historyCurveConfArr[j].groupId== record.id ){
            	    			historyCurveConfArr[j].groupName=record.name;
            	    			protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtRowProp(j,'historyCurveConf',historyCurveConfArr[j]);
            	    			
            	    			var showValue='曲线组:'+(isNotVal(historyCurveConfArr[j].groupName)?historyCurveConfArr[j].groupName:loginUserLanguageResource.nothing)+";"+
            	    			historyCurveConfArr[j].sort+";"+(historyCurveConfArr[j].yAxisOpposite?loginUserLanguageResource.right:loginUserLanguageResource.left)+';'+historyCurveConfArr[j].color;
            	    			protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtRowProp(j,'historyCurveConfShowValue',showValue);
            	    			break;
            	    		}
            	    	}
            	    }
            	}
            	
            } else {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			data: JSON.stringify(modifiedData)
        }
	});
}