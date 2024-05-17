Ext.define("AP.view.acquisitionUnit.CurveConfigWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.curveConfigWindow',
    layout: 'fit',
    title: '曲线属性',
    iframe: true,
    id: 'curveConfigWindow_Id',
    closeAction: 'destroy',
    width: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        
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
			},{
            	xtype: 'numberfield',
            	id: "curveConfigSort_Id",
                fieldLabel: '曲线顺序',
                value:1,
                allowBlank: false,
                editable : false,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            },{
                id: 'curveConfigColor_id',
                fieldLabel: '曲线颜色',
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
                fieldLabel: '线宽',
                value:3,
                allowBlank: false,
                editable : false,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            }, {
            	xtype : "combobox",
				fieldLabel : '线型',
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
				emptyText : '请选择线型',
				blankText : '请选择线型',
				listeners : {
					select:function(v,o){
						
					}
				}
            },{
            	xtype : "combobox",
				fieldLabel : 'Y轴位置',
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
								[false, '左'],
								[true, '右']
								]
						}),
				displayField : 'text',
				valueField : 'value',
				queryMode : 'local',
				emptyText : '请选择Y轴位置',
				blankText : '请选择Y轴位置',
				listeners : {
					select:function(v,o){
						
					}
				}
            }],
            buttons: [{
            	xtype: 'button',
            	text: cosog.string.save,
                iconCls: 'save',
                handler: function () {
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
                			protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),sort+';'+color);
                			var curveConfig={};
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==6){
                				protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),8,curveConfig);
                			}else if(parseInt(col)==7){
                				protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),9,curveConfig);
                			}
                		}else if(tableType==1){
                			protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),sort+';'+color);
                			var curveConfig={};
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==6){
                				protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),8,curveConfig);
                			}else if(parseInt(col)==7){
                				protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),9,curveConfig);
                			}
                		}else if(tableType==3){
                			protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),sort+';'+color);
                			var curveConfig={};
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==6){
                				protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),8,curveConfig);
                			}else if(parseInt(col)==7){
                				protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),9,curveConfig);
                			}
                		}else if(tableType==21){//单井区间报表汇总计算项
                			reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),sort+';'+color);
                			var curveConfig={};
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==8){
                				reportUnitContentConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),9,curveConfig);
                			}
                		}else if(tableType==22){//区域报表汇总计算项
                			productionReportTemplateContentHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),sort+';'+color);
                			var curveConfig={};
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==11){
                				productionReportTemplateContentHandsontableHelper.hot.setDataAtCell(parseInt(row),13,curveConfig);
                			}
                		}else if(tableType==23){//单井单日报表汇总计算项
                			singleWellDailyReportTemplateContentHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),sort+';'+color);
                			var curveConfig={};
                			curveConfig.sort=sort;
                			curveConfig.lineWidth=lineWidth;
                			curveConfig.dashStyle=dashStyle;
                			curveConfig.yAxisOpposite=yAxisOpposite;
                			curveConfig.color=color;
                			if(parseInt(col)==9){
                				singleWellDailyReportTemplateContentHandsontableHelper.hot.setDataAtCell(parseInt(row),10,curveConfig);
                			}
                		}
                	}
                	
                	Ext.getCmp("curveConfigWindow_Id").close();
                }
            },{
        	 	xtype: 'button',   
        	 	text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("curveConfigWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: curveConfigWindowForm
        });
        me.callParent(arguments);
    }

});