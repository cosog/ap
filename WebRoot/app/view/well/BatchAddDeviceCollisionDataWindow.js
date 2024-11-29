var batchAddDeviceCollisionDataHandsontableHelper=null;
var batchAddDeviceOverlayDataHandsontableHelper=null;
Ext.define("AP.view.well.BatchAddDeviceCollisionDataWindow", {
    extend: 'Ext.window.Window',
    id:'BatchAddDeviceCollisionDataWindow_Id',
    alias: 'widget.batchAddDeviceCollisionDataWindow',
    layout: 'fit',
    title:loginUserLanguageResource.dataCollision,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1400,
    minWidth: 1400,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar: [{
                xtype: 'label',
                id: 'batchAddDeviceCollisionInfoLabel_Id',
                margin: '0 0 0 0',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.deviceType,
                id: 'batchAddCollisionDeviceType_Id',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: '所属组织',
                id: 'batchAddCollisionDeviceOrg_Id',
                value: ''
            },'->',{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                	var orgId = Ext.getCmp('batchAddCollisionDeviceOrg_Id').getValue();
                    var deviceType = Ext.getCmp('batchAddCollisionDeviceType_Id').getValue();
                    var isCheckout=0;
                    var saveDate={};
                    saveDate.updatelist=[];
                    if(batchAddDeviceCollisionDataHandsontableHelper!=null&&batchAddDeviceCollisionDataHandsontableHelper.hot!=null&&batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined){
                    	var batchAddData=batchAddDeviceCollisionDataHandsontableHelper.hot.getData();
                        for(var i=0;i<batchAddData.length;i++){
                        	if(isNotVal(batchAddData[i][1])){
                        		var data = "{";
                                for (var j = 0; j < batchAddDeviceCollisionDataHandsontableHelper.columns.length; j++) {
                                    data += batchAddDeviceCollisionDataHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                                    if (j < batchAddDeviceCollisionDataHandsontableHelper.columns.length - 1) {
                                        data += ",";
                                    }
                                }
                                data += "}";
                                var record=Ext.JSON.decode(data);
                                record.id=i;
                                saveDate.updatelist.push(record);
                        	}
                        }
                    }
                    if(batchAddDeviceOverlayDataHandsontableHelper!=null&&batchAddDeviceOverlayDataHandsontableHelper.hot!=null&&batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined){
                    	var batchAddData=batchAddDeviceOverlayDataHandsontableHelper.hot.getData();
                        for(var i=0;i<batchAddData.length;i++){
                        	if(isNotVal(batchAddData[i][1])){
                        		var data = "{";
                                for (var j = 0; j < batchAddDeviceOverlayDataHandsontableHelper.columns.length; j++) {
                                    data += batchAddDeviceOverlayDataHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                                    if (j < batchAddDeviceOverlayDataHandsontableHelper.columns.length - 1) {
                                        data += ",";
                                    }
                                }
                                data += "}";
                                var record=Ext.JSON.decode(data);
                                record.id=i;
                                saveDate.updatelist.push(record);
                        	}
                        }
                    }
                    
                    Ext.Ajax.request({
                        method: 'POST',
                        url: context + '/wellInformationManagerController/batchAddDevice',
                        success: function (response) {
                        	CreateAndLoadDeviceInfoTable();
                        	rdata = Ext.JSON.decode(response.responseText);
                            if (rdata.success&&rdata.collisionCount==0&&rdata.overlayCount==0) {
                            	Ext.getCmp("BatchAddDeviceCollisionDataWindow_Id").close();
                            	if(rdata.overCount>0){
                            		Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+rdata.overCount+"</font>口井超限，保存失败");
                            	}else{
                            		Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
                            	}
                            }else if(rdata.success&&(rdata.collisionCount>0 || rdata.overlayCount>0)){
                                Ext.getCmp("batchAddCollisionDeviceType_Id").setValue(deviceType);
                                Ext.getCmp("batchAddCollisionDeviceOrg_Id").setValue(orgId);
                                if(rdata.collisionCount==0){
                                	if(batchAddDeviceCollisionDataHandsontableHelper!=null){
                    					if(batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined){
                    						batchAddDeviceCollisionDataHandsontableHelper.hot.destroy();
                    					}
                    					batchAddDeviceCollisionDataHandsontableHelper=null;
                    				}
                                	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").hide();
                                	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").setHeight('100%');
                                	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").show();
                                }else if(rdata.overlayCount==0){
                                	if(batchAddDeviceOverlayDataHandsontableHelper!=null){
                    					if(batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined){
                    						batchAddDeviceOverlayDataHandsontableHelper.hot.destroy();
                    					}
                    					batchAddDeviceOverlayDataHandsontableHelper=null;
                    				}
                                	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").hide();
                                	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").setHeight('100%');
                                	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").show();
                                }
                                if(rdata.collisionCount>0){
                                	CreateAndLoadBatchAddDeviceCollisionDataTable(rdata);
                                }
                                if(rdata.overlayCount>0){
                                	CreateAndLoadBatchAddDeviceOverlayDataTable(rdata);
                                }
                                
                                var collisionInfo="冲突井数：<font color=red>"+rdata.collisionCount+"</font> ";
                                var overlayInfo="覆盖井数：<font color=red>"+rdata.overlayCount+"</font> ";
                                var overInfo="超限井数：<font color=red>"+rdata.overCount+"</font>";
                                
                                var info="";
                                if(rdata.collisionCount>0){
                                	info+=collisionInfo;
                                }
                                if(rdata.overlayCount>0){
                                	info+=overlayInfo;
                                }
                                if(rdata.overCount>0){
                                	info+=overInfo;
                                }
                                Ext.MessageBox.alert(loginUserLanguageResource.message, info);
                            } else {
                                Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
                            }
                        },
                        failure: function () {
                            Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.requestFailure);
                            batchAddDeviceHandsontableHelper.clearContainer();
                        },
                        params: {
                            data: JSON.stringify(saveDate),
                            orgId: orgId,
                            deviceType: deviceType,
                            isCheckout: isCheckout
                        }
                    });
                }
            }],
            layout: 'border',
            items: [{
            	region: 'north',
            	height: '50%',
            	id:'BatchAddDeviceCollisionDataPanel_Id',
            	title: loginUserLanguageResource.dataCollision,
            	html: '<div id="BatchAddDeviceCollisionDataTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(batchAddDeviceCollisionDataHandsontableHelper!=null&&batchAddDeviceCollisionDataHandsontableHelper.hot!=null&&batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined){
//                    		batchAddDeviceCollisionDataHandsontableHelper.hot.refreshDimensions();
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		batchAddDeviceCollisionDataHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
            	}
            },{
            	region: 'south',
            	height: '50%',
            	id:'BatchAddDeviceOverlayDataPanel_Id',
            	title: '已有记录(<font color=red>继续保存，表中数据将覆盖已有记录</font>)',
            	html: '<div id="BatchAddDeviceOverlayDataTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(batchAddDeviceOverlayDataHandsontableHelper!=null&&batchAddDeviceOverlayDataHandsontableHelper.hot!=null&&batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined){
//                    		batchAddDeviceOverlayDataHandsontableHelper.hot.refreshDimensions();
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		batchAddDeviceOverlayDataHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(batchAddDeviceCollisionDataHandsontableHelper!=null){
    					if(batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined){
    						batchAddDeviceCollisionDataHandsontableHelper.hot.destroy();
    					}
    					batchAddDeviceCollisionDataHandsontableHelper=null;
    				}
                	if(batchAddDeviceOverlayDataHandsontableHelper!=null){
    					if(batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined){
    						batchAddDeviceOverlayDataHandsontableHelper.hot.destroy();
    					}
    					batchAddDeviceOverlayDataHandsontableHelper=null;
    				}
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});
function CreateAndLoadBatchAddDeviceCollisionDataTable(result) {
	var deviceType = Ext.getCmp('batchAddCollisionDeviceType_Id').getValue();
	if (batchAddDeviceCollisionDataHandsontableHelper == null || batchAddDeviceCollisionDataHandsontableHelper.hot == null || batchAddDeviceCollisionDataHandsontableHelper.hot == undefined) {
        batchAddDeviceCollisionDataHandsontableHelper = BatchAddDeviceCollisionDataHandsontableHelper.createNew("BatchAddDeviceCollisionDataTableDiv_Id");
        var colHeaders = "[";
        var columns = "[";
        for (var i = 0; i < result.columns.length; i++) {
        	var colHeader="'" + result.columns[i].header + "'";
            var dataIndex=result.columns[i].dataIndex;
        	colHeaders += colHeader;
            
            if (dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                columns += "{data:'" + dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,batchAddDeviceCollisionDataHandsontableHelper);}}";
            } else if (dataIndex.toUpperCase() === "liftingTypeName".toUpperCase()) {
                if (pcpHidden) {
                    columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机井']}";
                } else {
                    columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机井', '螺杆泵井']}";
                }
            } else if (result.columns[i].dataIndex.toUpperCase() === "deviceTypeName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.deviceTypeDropdownData.length; j++) {
                    source += "\'" + result.deviceTypeDropdownData[j] + "\'";
                    if (j < result.deviceTypeDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "instanceName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.instanceDropdownData.length; j++) {
                    source += "\'" + result.instanceDropdownData[j] + "\'";
                    if (j < result.instanceDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "displayInstanceName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.displayInstanceDropdownData.length; j++) {
                    source += "\'" + result.displayInstanceDropdownData[j] + "\'";
                    if (j < result.displayInstanceDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "reportInstanceName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.reportInstanceDropdownData.length; j++) {
                    source += "\'" + result.reportInstanceDropdownData[j] + "\'";
                    if (j < result.reportInstanceDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "alarmInstanceName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.alarmInstanceDropdownData.length; j++) {
                    source += "\'" + result.alarmInstanceDropdownData[j] + "\'";
                    if (j < result.alarmInstanceDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            }else if (dataIndex.toUpperCase() === "applicationScenariosName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.applicationScenariosDropdownData.length; j++) {
                    source += "\'" + result.applicationScenariosDropdownData[j] + "\'";
                    if (j < result.applicationScenariosDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "manualInterventionResultName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.resultNameDropdownData.length; j++) {
                    source += "\'" + result.resultNameDropdownData[j] + "\'";
                    if (j < result.resultNameDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            }else if (dataIndex.toUpperCase() === "statusName".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"', '"+loginUserLanguageResource.disable+"']}";
            } else if (dataIndex.toUpperCase() === "tcpType".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['TCP Server', 'TCP Client']}";
            } else if (dataIndex.toUpperCase() === "pumpType".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['杆式泵', '管式泵']}";
            } else if (dataIndex.toUpperCase() === "barrelType".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['组合泵', '整筒泵']}";
            } else if (dataIndex.toUpperCase() === "pumpGrade".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['1', '2', '3', '4', '5']}";
            } else if (dataIndex.toUpperCase() === "rodGrade1".toUpperCase() || dataIndex.toUpperCase() === "rodGrade2".toUpperCase()
            		|| dataIndex.toUpperCase() === "rodGrade3".toUpperCase() || dataIndex.toUpperCase() === "rodGrade4".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['A', 'B', 'C', 'D', 'K', 'KD', 'HL', 'HY']}";
            } else if (dataIndex.toUpperCase() === "crankRotationDirection".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['顺时针', '逆时针']}";
            } else if (dataIndex.toUpperCase() === "manufacturer".toUpperCase()) {
            	var source = "[";
                for (var j = 0; j < result.pumpingModelInfo.manufacturerList.length; j++) {
                    source += "\'" + result.pumpingModelInfo.manufacturerList[j].manufacturer + "\'";
                    if (j < result.pumpingModelInfo.manufacturerList.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "model".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['']}";
            } else if (dataIndex.toUpperCase() === "stroke".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['']}";
            } else if (dataIndex.toUpperCase() === "ipPort".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_IpPort_Nullable(val, callback,this.row, this.col,batchAddDeviceCollisionDataHandsontableHelper);}}";
            } else if (dataIndex.toUpperCase() != "wellName".toUpperCase() 
            		&& dataIndex.toUpperCase() != "signInId".toUpperCase()
            		&& dataIndex.toUpperCase() != "videoUrl1".toUpperCase()
            		&& dataIndex.toUpperCase() != "videoKeyName1".toUpperCase()
            		&& dataIndex.toUpperCase() != "videoUrl2".toUpperCase()
            		&& dataIndex.toUpperCase() != "videoKeyName2".toUpperCase() ) {
                columns += "{data:'" + dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,batchAddDeviceCollisionDataHandsontableHelper);}}";
            } else {
                columns += "{data:'" + dataIndex + "'}";
            }
            if (i < result.columns.length - 1) {
                colHeaders += ",";
                columns += ",";
            }
        }
        colHeaders += ",'冲突信息'";
        columns += ",{data:'dataInfo'}";
        colHeaders += "]";
        columns += "]";
        batchAddDeviceCollisionDataHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
        batchAddDeviceCollisionDataHandsontableHelper.columns = Ext.JSON.decode(columns);
        
        if(parseInt(deviceType)<200){
        	batchAddDeviceCollisionDataHandsontableHelper.pumpingModelInfo=result.pumpingModelInfo;
        }
        
        batchAddDeviceCollisionDataHandsontableHelper.createTable(result.collisionList);
    } else {
    	batchAddDeviceCollisionDataHandsontableHelper.hot.loadData(result.collisionList);
    }
};

var BatchAddDeviceCollisionDataHandsontableHelper = {
    createNew: function (divid) {
        var batchAddDeviceCollisionDataHandsontableHelper = {};
        batchAddDeviceCollisionDataHandsontableHelper.hot = '';
        batchAddDeviceCollisionDataHandsontableHelper.divid = divid;
        batchAddDeviceCollisionDataHandsontableHelper.validresult = true; //数据校验
        batchAddDeviceCollisionDataHandsontableHelper.colHeaders = [];
        batchAddDeviceCollisionDataHandsontableHelper.columns = [];

        batchAddDeviceCollisionDataHandsontableHelper.AllData = {};
        batchAddDeviceCollisionDataHandsontableHelper.updatelist = [];
        batchAddDeviceCollisionDataHandsontableHelper.delidslist = [];
        batchAddDeviceCollisionDataHandsontableHelper.insertlist = [];
        
        batchAddDeviceCollisionDataHandsontableHelper.pumpingModelInfo = {};
        
        batchAddDeviceCollisionDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.color = '#ff0000';
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }
        
        batchAddDeviceCollisionDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }

        batchAddDeviceCollisionDataHandsontableHelper.createTable = function (data) {
            $('#' + batchAddDeviceCollisionDataHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddDeviceCollisionDataHandsontableHelper.divid);
            batchAddDeviceCollisionDataHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false,
                    copyPasteEnabled: false
                },
                columns: batchAddDeviceCollisionDataHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddDeviceCollisionDataHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                allowInsertRow:false,
                contextMenu: {
                    items: {
                        "row_above": {
                            name: '向上插入一行',
                        },
                        "row_below": {
                            name: '向下插入一行',
                        },
                        "col_left": {
                            name: '向左插入一列',
                        },
                        "col_right": {
                            name: '向右插入一列',
                        },
                        "remove_row": {
                            name: '删除行',
                        },
                        "remove_col": {
                            name: '删除列',
                        },
                        "merge_cell": {
                            name: '合并单元格',
                        },
                        "copy": {
                            name: '复制',
                        },
                        "cut": {
                            name: '剪切',
                        },
                        "paste": {
                            name: '粘贴',
                            disabled: function () {
                            },
                            callback: function () {
                            }
                        }
                    }
                }, //右键菜单展示
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var visualRowIndex = this.instance.toVisualRow(row);
                    var visualColIndex = this.instance.toVisualColumn(col);
                    if(batchAddDeviceCollisionDataHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='dataInfo'.toUpperCase()){
                    	cellProperties.readOnly = true;
                    	cellProperties.renderer = batchAddDeviceCollisionDataHandsontableHelper.addBoldBg;
                    }else{
                    	if(batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined && batchAddDeviceCollisionDataHandsontableHelper.hot.getDataAtCell!=undefined){
                        	var columns=batchAddDeviceCollisionDataHandsontableHelper.columns;
                            var pumpingManufacturerColIndex=-1;
                            var pumpingModelColIndex=-1;
                            var pumpingStrokeColIndex=-1;
                            var barrelTypeColIndex=-1;
                            var pumpGradeColIndex=-1;
                            for(var i=0;i<columns.length;i++){
                            	if(columns[i].data.toUpperCase() === "model".toUpperCase()){
                            		pumpingModelColIndex=i;
                            	}else if(columns[i].data.toUpperCase() === "manufacturer".toUpperCase()){
                            		pumpingManufacturerColIndex=i;
                            	}else if(columns[i].data.toUpperCase() === "stroke".toUpperCase()){
                            		pumpingStrokeColIndex=i;
                            	}else if(columns[i].data.toUpperCase() === "barrelType".toUpperCase()){
                            		barrelTypeColIndex=i;
                            	}else if(columns[i].data.toUpperCase() === "pumpGrade".toUpperCase()){
                            		pumpGradeColIndex=i;
                            	}
                            }
                            if(visualColIndex==pumpGradeColIndex && barrelTypeColIndex>0){
                            	var barrelType=batchAddDeviceCollisionDataHandsontableHelper.hot.getDataAtCell(row,barrelTypeColIndex);
                            	if(barrelType=='整筒泵'){
                            		this.source = ['1','2','3','4','5'];
                            	}else if(barrelType=='组合泵'){
                            		this.source = ['1','2','3'];
                            	}else if(barrelType==''){
                            		this.source = ['1','2','3','4','5'];
                            	}
                            }
                            if((visualColIndex==pumpingModelColIndex || visualColIndex==pumpingStrokeColIndex) && pumpingManufacturerColIndex>=0){
                            	var pumpingModelInfo=batchAddDeviceCollisionDataHandsontableHelper.pumpingModelInfo;
                            	if(visualColIndex==pumpingModelColIndex){
                                	var pumpingManufacturer=batchAddDeviceCollisionDataHandsontableHelper.hot.getDataAtCell(row,pumpingManufacturerColIndex);
                                	for(var i=0;i<pumpingModelInfo.manufacturerList.length;i++){
                                		if(pumpingManufacturer==pumpingModelInfo.manufacturerList[i].manufacturer){
                                			var modelList=[];
                                			for(var j=0;j<pumpingModelInfo.manufacturerList[i].modelList.length;j++){
                                				modelList.push(pumpingModelInfo.manufacturerList[i].modelList[j].model);
                                			}
                                			this.source = modelList;
                                			break;
                                		}
                                	}
                                }else if(visualColIndex==pumpingStrokeColIndex){
                                	var pumpingManufacturer=batchAddDeviceCollisionDataHandsontableHelper.hot.getDataAtCell(row,pumpingManufacturerColIndex);
                                	var pumpingModel=batchAddDeviceCollisionDataHandsontableHelper.hot.getDataAtCell(row,pumpingModelColIndex);
                                	for(var i=0;i<pumpingModelInfo.manufacturerList.length;i++){
                                		if(pumpingManufacturer==pumpingModelInfo.manufacturerList[i].manufacturer){
                                			for(var j=0;j<pumpingModelInfo.manufacturerList[i].modelList.length;j++){
                                				if(pumpingModel==pumpingModelInfo.manufacturerList[i].modelList[j].model){
                                					this.source = pumpingModelInfo.manufacturerList[i].modelList[j].stroke;
                                					break;
                                				}
                                			}
                                			
                                			break;
                                		}
                                	}
                            	}
                            }
                            
                        }
                    }
                    
                    if( !batchAddDeviceCollisionDataHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='dataInfo'.toUpperCase()  ){
                    	if(batchAddDeviceCollisionDataHandsontableHelper.columns[visualColIndex].type == undefined || batchAddDeviceCollisionDataHandsontableHelper.columns[visualColIndex].type!='dropdown'){
                    		cellProperties.renderer = batchAddDeviceCollisionDataHandsontableHelper.addCellStyle;
                    	}
                    }
                    
                    
                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(batchAddDeviceCollisionDataHandsontableHelper!=null&&batchAddDeviceCollisionDataHandsontableHelper.hot!=''&&batchAddDeviceCollisionDataHandsontableHelper.hot!=undefined && batchAddDeviceCollisionDataHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=batchAddDeviceCollisionDataHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
                		if(isNotVal(rawValue)){
            				var showValue=rawValue;
        					var rowChar=90;
        					var maxWidth=rowChar*10;
        					if(rawValue.length>rowChar){
        						showValue='';
        						let arr = [];
        						let index = 0;
        						while(index<rawValue.length){
        							arr.push(rawValue.slice(index,index +=rowChar));
        						}
        						for(var i=0;i<arr.length;i++){
        							showValue+=arr[i];
        							if(i<arr.length-1){
        								showValue+='<br>';
        							}
        						}
        					}
            				if(!isNotVal(TD.tip)){
            					var height=28;
            					TD.tip = Ext.create('Ext.tip.ToolTip', {
	                			    target: event.target,
	                			    maxWidth:maxWidth,
	                			    html: showValue,
	                			    listeners: {
	                			    	hide: function (thisTip, eOpts) {
	                                	},
	                                	close: function (thisTip, eOpts) {
	                                	}
	                                }
	                			});
            				}else{
            					TD.tip.setHtml(showValue);
            				}
            			}
                	}
                }
            });
        }
        //保存数据
        batchAddDeviceCollisionDataHandsontableHelper.saveData = function () {}

        batchAddDeviceCollisionDataHandsontableHelper.clearContainer = function () {
            batchAddDeviceCollisionDataHandsontableHelper.AllData = {};
            batchAddDeviceCollisionDataHandsontableHelper.updatelist = [];
            batchAddDeviceCollisionDataHandsontableHelper.delidslist = [];
            batchAddDeviceCollisionDataHandsontableHelper.insertlist = [];
        }

        return batchAddDeviceCollisionDataHandsontableHelper;
    }
};

function CreateAndLoadBatchAddDeviceOverlayDataTable(result) {
	var deviceType = Ext.getCmp('batchAddCollisionDeviceType_Id').getValue();
	if (batchAddDeviceOverlayDataHandsontableHelper == null || batchAddDeviceOverlayDataHandsontableHelper.hot == null || batchAddDeviceOverlayDataHandsontableHelper.hot == undefined) {
        batchAddDeviceOverlayDataHandsontableHelper = BatchAddDeviceOverlayDataHandsontableHelper.createNew("BatchAddDeviceOverlayDataTableDiv_Id");
        var colHeaders = "[";
        var columns = "[";
        for (var i = 0; i < result.columns.length; i++) {
        	var colHeader="'" + result.columns[i].header + "'";
            var dataIndex=result.columns[i].dataIndex;
        	colHeaders += colHeader;
            if (dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                columns += "{data:'" + dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,batchAddDeviceOverlayDataHandsontableHelper);}}";
            } else if (dataIndex.toUpperCase() === "liftingTypeName".toUpperCase()) {
                if (pcpHidden) {
                    columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机井']}";
                } else {
                    columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['抽油机井', '螺杆泵井']}";
                }
            } else if (result.columns[i].dataIndex.toUpperCase() === "deviceTypeName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.deviceTypeDropdownData.length; j++) {
                    source += "\'" + result.deviceTypeDropdownData[j] + "\'";
                    if (j < result.deviceTypeDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + result.columns[i].dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "instanceName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.instanceDropdownData.length; j++) {
                    source += "\'" + result.instanceDropdownData[j] + "\'";
                    if (j < result.instanceDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "displayInstanceName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.displayInstanceDropdownData.length; j++) {
                    source += "\'" + result.displayInstanceDropdownData[j] + "\'";
                    if (j < result.displayInstanceDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "reportInstanceName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.reportInstanceDropdownData.length; j++) {
                    source += "\'" + result.reportInstanceDropdownData[j] + "\'";
                    if (j < result.reportInstanceDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "alarmInstanceName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.alarmInstanceDropdownData.length; j++) {
                    source += "\'" + result.alarmInstanceDropdownData[j] + "\'";
                    if (j < result.alarmInstanceDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            }else if (dataIndex.toUpperCase() === "applicationScenariosName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.applicationScenariosDropdownData.length; j++) {
                    source += "\'" + result.applicationScenariosDropdownData[j] + "\'";
                    if (j < result.applicationScenariosDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "manualInterventionResultName".toUpperCase()) {
                var source = "[";
                for (var j = 0; j < result.resultNameDropdownData.length; j++) {
                    source += "\'" + result.resultNameDropdownData[j] + "\'";
                    if (j < result.resultNameDropdownData.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "sortNum".toUpperCase()) {
                columns += "{data:'" + dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,batchAddDeviceOverlayDataHandsontableHelper);}}";
            } else if (dataIndex.toUpperCase() === "tcpType".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['TCP Server', 'TCP Client']}";
            } else if (dataIndex.toUpperCase() === "pumpType".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['杆式泵', '管式泵']}";
            } else if (dataIndex.toUpperCase() === "barrelType".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['组合泵', '整筒泵']}";
            } else if (dataIndex.toUpperCase() === "pumpGrade".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['1', '2', '3', '4', '5']}";
            } else if (dataIndex.toUpperCase() === "rodGrade1".toUpperCase() || dataIndex.toUpperCase() === "rodGrade2".toUpperCase()
            		|| dataIndex.toUpperCase() === "rodGrade3".toUpperCase() || dataIndex.toUpperCase() === "rodGrade4".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['A', 'B', 'C', 'D', 'K', 'KD', 'HL', 'HY']}";
            } else if (dataIndex.toUpperCase() === "crankRotationDirection".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['顺时针', '逆时针']}";
            } else if (dataIndex.toUpperCase() === "manufacturer".toUpperCase()) {
            	var source = "[";
                for (var j = 0; j < result.pumpingModelInfo.manufacturerList.length; j++) {
                    source += "\'" + result.pumpingModelInfo.manufacturerList[j].manufacturer + "\'";
                    if (j < result.pumpingModelInfo.manufacturerList.length - 1) {
                        source += ",";
                    }
                }
                source += "]";
                columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:" + source + "}";
            } else if (dataIndex.toUpperCase() === "model".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['']}";
            } else if (dataIndex.toUpperCase() === "stroke".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['']}";
            } else if (dataIndex.toUpperCase() === "ipPort".toUpperCase()) {
            	columns += "{data:'" + dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_IpPort_Nullable(val, callback,this.row, this.col,batchAddDeviceOverlayDataHandsontableHelper);}}";
            } else if (dataIndex.toUpperCase() != "wellName".toUpperCase() 
            		&& dataIndex.toUpperCase() != "signInId".toUpperCase()
            		&& dataIndex.toUpperCase() != "videoUrl1".toUpperCase()
            		&& dataIndex.toUpperCase() != "videoKeyName1".toUpperCase()
            		&& dataIndex.toUpperCase() != "videoUrl2".toUpperCase()
            		&& dataIndex.toUpperCase() != "videoKeyName2".toUpperCase() ) {
                columns += "{data:'" + dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,batchAddDeviceOverlayDataHandsontableHelper);}}";
            }  else {
                columns += "{data:'" + dataIndex + "'}";
            }
            if (i < result.columns.length - 1) {
                colHeaders += ",";
                columns += ",";
            }
        }
        colHeaders += ",'覆盖信息'";
        columns += ",{data:'dataInfo'}";
        colHeaders += "]";
        columns += "]";
        batchAddDeviceOverlayDataHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
        batchAddDeviceOverlayDataHandsontableHelper.columns = Ext.JSON.decode(columns);
        if(parseInt(deviceType)<200){
        	batchAddDeviceOverlayDataHandsontableHelper.pumpingModelInfo=result.pumpingModelInfo;
        }
        batchAddDeviceOverlayDataHandsontableHelper.createTable(result.overlayList);
    } else {
    	batchAddDeviceOverlayDataHandsontableHelper.hot.loadData(result.overlayList);
    }
};

var BatchAddDeviceOverlayDataHandsontableHelper = {
    createNew: function (divid) {
        var batchAddDeviceOverlayDataHandsontableHelper = {};
        batchAddDeviceOverlayDataHandsontableHelper.hot = '';
        batchAddDeviceOverlayDataHandsontableHelper.divid = divid;
        batchAddDeviceOverlayDataHandsontableHelper.validresult = true; //数据校验
        batchAddDeviceOverlayDataHandsontableHelper.colHeaders = [];
        batchAddDeviceOverlayDataHandsontableHelper.columns = [];

        batchAddDeviceOverlayDataHandsontableHelper.AllData = {};
        batchAddDeviceOverlayDataHandsontableHelper.updatelist = [];
        batchAddDeviceOverlayDataHandsontableHelper.delidslist = [];
        batchAddDeviceOverlayDataHandsontableHelper.insertlist = [];
        
        batchAddDeviceOverlayDataHandsontableHelper.pumpingModelInfo = {};
        
        batchAddDeviceOverlayDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.color = '#ff0000';
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }
        
        batchAddDeviceOverlayDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }

        batchAddDeviceOverlayDataHandsontableHelper.createTable = function (data) {
            $('#' + batchAddDeviceOverlayDataHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddDeviceOverlayDataHandsontableHelper.divid);
            batchAddDeviceOverlayDataHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false,
                    copyPasteEnabled: false
                },
                columns: batchAddDeviceOverlayDataHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddDeviceOverlayDataHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
                allowInsertRow:false,
                contextMenu: {
                    items: {
                        "row_above": {
                            name: '向上插入一行',
                        },
                        "row_below": {
                            name: '向下插入一行',
                        },
                        "col_left": {
                            name: '向左插入一列',
                        },
                        "col_right": {
                            name: '向右插入一列',
                        },
                        "remove_row": {
                            name: '删除行',
                        },
                        "remove_col": {
                            name: '删除列',
                        },
                        "merge_cell": {
                            name: '合并单元格',
                        },
                        "copy": {
                            name: '复制',
                        },
                        "cut": {
                            name: '剪切',
                        },
                        "paste": {
                            name: '粘贴',
                            disabled: function () {
                            },
                            callback: function () {
                            }
                        }
                    }
                }, //右键菜单展示
                sortIndicator: true,
                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                filters: true,
                renderAllRows: true,
                search: true,
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var visualRowIndex = this.instance.toVisualRow(row);
                    var visualColIndex = this.instance.toVisualColumn(col);
                    if(batchAddDeviceOverlayDataHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='dataInfo'.toUpperCase()){
                    	cellProperties.readOnly = true;
                    	cellProperties.renderer = batchAddDeviceOverlayDataHandsontableHelper.addBoldBg;
                    }else{
                    	if(batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined && batchAddDeviceOverlayDataHandsontableHelper.hot.getDataAtCell!=undefined){
                        	var columns=batchAddDeviceOverlayDataHandsontableHelper.columns;
                            var pumpingManufacturerColIndex=-1;
                            var pumpingModelColIndex=-1;
                            var pumpingStrokeColIndex=-1;
                            var barrelTypeColIndex=-1;
                            var pumpGradeColIndex=-1;
                            for(var i=0;i<columns.length;i++){
                            	if(columns[i].data.toUpperCase() === "model".toUpperCase()){
                            		pumpingModelColIndex=i;
                            	}else if(columns[i].data.toUpperCase() === "manufacturer".toUpperCase()){
                            		pumpingManufacturerColIndex=i;
                            	}else if(columns[i].data.toUpperCase() === "stroke".toUpperCase()){
                            		pumpingStrokeColIndex=i;
                            	}else if(columns[i].data.toUpperCase() === "barrelType".toUpperCase()){
                            		barrelTypeColIndex=i;
                            	}else if(columns[i].data.toUpperCase() === "pumpGrade".toUpperCase()){
                            		pumpGradeColIndex=i;
                            	}
                            }
                            if(visualColIndex==pumpGradeColIndex && barrelTypeColIndex>0){
                            	var barrelType=batchAddDeviceOverlayDataHandsontableHelper.hot.getDataAtCell(row,barrelTypeColIndex);
                            	if(barrelType=='整筒泵'){
                            		this.source = ['1','2','3','4','5'];
                            	}else if(barrelType=='组合泵'){
                            		this.source = ['1','2','3'];
                            	}else if(barrelType==''){
                            		this.source = ['1','2','3','4','5'];
                            	}
                            }
                            if((visualColIndex==pumpingModelColIndex || visualColIndex==pumpingStrokeColIndex) && pumpingManufacturerColIndex>=0){
                            	var pumpingModelInfo=batchAddDeviceOverlayDataHandsontableHelper.pumpingModelInfo;
                            	if(visualColIndex==pumpingModelColIndex){
                                	var pumpingManufacturer=batchAddDeviceOverlayDataHandsontableHelper.hot.getDataAtCell(row,pumpingManufacturerColIndex);
                                	for(var i=0;i<pumpingModelInfo.manufacturerList.length;i++){
                                		if(pumpingManufacturer==pumpingModelInfo.manufacturerList[i].manufacturer){
                                			var modelList=[];
                                			for(var j=0;j<pumpingModelInfo.manufacturerList[i].modelList.length;j++){
                                				modelList.push(pumpingModelInfo.manufacturerList[i].modelList[j].model);
                                			}
                                			this.source = modelList;
                                			break;
                                		}
                                	}
                                }else if(visualColIndex==pumpingStrokeColIndex){
                                	var pumpingManufacturer=batchAddDeviceOverlayDataHandsontableHelper.hot.getDataAtCell(row,pumpingManufacturerColIndex);
                                	var pumpingModel=batchAddDeviceOverlayDataHandsontableHelper.hot.getDataAtCell(row,pumpingModelColIndex);
                                	for(var i=0;i<pumpingModelInfo.manufacturerList.length;i++){
                                		if(pumpingManufacturer==pumpingModelInfo.manufacturerList[i].manufacturer){
                                			for(var j=0;j<pumpingModelInfo.manufacturerList[i].modelList.length;j++){
                                				if(pumpingModel==pumpingModelInfo.manufacturerList[i].modelList[j].model){
                                					this.source = pumpingModelInfo.manufacturerList[i].modelList[j].stroke;
                                					break;
                                				}
                                			}
                                			
                                			break;
                                		}
                                	}
                                	
                                	
                            	}
                            }
                            
                        }
                    }
                    if(!batchAddDeviceOverlayDataHandsontableHelper.columns[visualColIndex].data.toUpperCase()=='dataInfo'.toUpperCase()){
                    	if(batchAddDeviceOverlayDataHandsontableHelper.columns[visualColIndex].type == undefined || batchAddDeviceOverlayDataHandsontableHelper.columns[visualColIndex].type!='dropdown'){
                    		cellProperties.renderer = batchAddDeviceOverlayDataHandsontableHelper.addCellStyle;
                    	}
                    }
                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(batchAddDeviceOverlayDataHandsontableHelper!=null&&batchAddDeviceOverlayDataHandsontableHelper.hot!=''&&batchAddDeviceOverlayDataHandsontableHelper.hot!=undefined && batchAddDeviceOverlayDataHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=batchAddDeviceOverlayDataHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
                		if(isNotVal(rawValue)){
            				var showValue=rawValue;
        					var rowChar=90;
        					var maxWidth=rowChar*10;
        					if(rawValue.length>rowChar){
        						showValue='';
        						let arr = [];
        						let index = 0;
        						while(index<rawValue.length){
        							arr.push(rawValue.slice(index,index +=rowChar));
        						}
        						for(var i=0;i<arr.length;i++){
        							showValue+=arr[i];
        							if(i<arr.length-1){
        								showValue+='<br>';
        							}
        						}
        					}
            				if(!isNotVal(TD.tip)){
            					var height=28;
            					TD.tip = Ext.create('Ext.tip.ToolTip', {
	                			    target: event.target,
	                			    maxWidth:maxWidth,
	                			    html: showValue,
	                			    listeners: {
	                			    	hide: function (thisTip, eOpts) {
	                                	},
	                                	close: function (thisTip, eOpts) {
	                                	}
	                                }
	                			});
            				}else{
            					TD.tip.setHtml(showValue);
            				}
            			}
                	}
                }
            });
        }
        //保存数据
        batchAddDeviceOverlayDataHandsontableHelper.saveData = function () {}

        batchAddDeviceOverlayDataHandsontableHelper.clearContainer = function () {
            batchAddDeviceOverlayDataHandsontableHelper.AllData = {};
            batchAddDeviceOverlayDataHandsontableHelper.updatelist = [];
            batchAddDeviceOverlayDataHandsontableHelper.delidslist = [];
            batchAddDeviceOverlayDataHandsontableHelper.insertlist = [];
        }

        return batchAddDeviceOverlayDataHandsontableHelper;
    }
};