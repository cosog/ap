var batchAddDeviceHandsontableHelper=null;
Ext.define("AP.view.well.BatchAddDeviceWindow", {
    extend: 'Ext.window.Window',
    id:'BatchAddDeviceWindow_Id',
    alias: 'widget.batchAddDeviceWindow',
    layout: 'fit',
    title:'设备批量添加',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1300,
    minWidth: 1200,
    height: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
//        Ext.create('AP.store.well.BatchAddDeviceApplicationScenariosListStore');
        Ext.apply(me, {
        	tbar: [{
                xtype: 'label',
                id: 'batchAddDeviceWinOrgLabel_Id',
                margin: '0 0 0 0',
                html: ''
            },{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.deviceType,
                id: 'batchAddDeviceType_Id',
                value: ''
            },{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.deviceType,
                id: 'batchAddDeviceOrg_Id',
                value: ''
            },'->',{
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: function (v, o) {
                	batchAddDeviceHandsontableHelper.saveData();
                }
            }],
            layout: 'border',
            items: [
//            	{
//            	title:'应用场景',
//            	region: 'west',
//            	width:'15%',
//            	hidden: sceneConfig!='all',
//            	layout: 'fit',
//            	header:false,
//        		split: true,
//                collapsible: true,
//        		id:'BatchAddDeviceApplicationScenariosInfoPanel_Id'
//            },
            {
            	region: 'center',
            	html: '<div id="BatchAddDeviceTableDiv_Id" style="width:100%;height:100%;"></div>',
            	listeners: {
            		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(batchAddDeviceHandsontableHelper!=null&&batchAddDeviceHandsontableHelper.hot!=null&&batchAddDeviceHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		batchAddDeviceHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}else{
                    		CreateAndLoadBatchAddDeviceTable();
                    	}
                    }
            	}
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	if(batchAddDeviceHandsontableHelper!=null){
    					if(batchAddDeviceHandsontableHelper.hot!=undefined){
    						batchAddDeviceHandsontableHelper.hot.destroy();
    					}
    					batchAddDeviceHandsontableHelper=null;
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
function CreateAndLoadBatchAddDeviceTable(isNew) {
	if(isNew&&batchAddDeviceHandsontableHelper!=null){
		if (batchAddDeviceHandsontableHelper.hot != undefined) {
			batchAddDeviceHandsontableHelper.hot.destroy();
		}
		batchAddDeviceHandsontableHelper = null;
	}
    var orgId = Ext.getCmp('batchAddDeviceOrg_Id').getValue();
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var deviceType = Ext.getCmp('batchAddDeviceType_Id').getValue();
    
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/wellInformationManagerController/getBatchAddDeviceTableInfo',
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (batchAddDeviceHandsontableHelper == null || batchAddDeviceHandsontableHelper.hot == null || batchAddDeviceHandsontableHelper.hot == undefined) {
                batchAddDeviceHandsontableHelper = BatchAddDeviceHandsontableHelper.createNew("BatchAddDeviceTableDiv_Id");
                var colHeaders = "[";
                var columns = "[";

                for (var i = 0; i < result.columns.length; i++) {
                    var colHeader="'" + result.columns[i].header + "'";
                    var dataIndex=result.columns[i].dataIndex;
                    
                	colHeaders += colHeader;
                    if (dataIndex.toUpperCase() === "orgName".toUpperCase()) {
                        columns += "{data:'" + dataIndex + "',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Org(val, callback,this.row, this.col,batchAddDeviceHandsontableHelper);}}";
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
                    	columns += "{data:'" + dataIndex + "',type:'dropdown',strict:true,allowInvalid:false,source:['使能', '失效']}";
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
                    	columns += "{data:'" + dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_IpPort_Nullable(val, callback,this.row, this.col,batchAddDeviceHandsontableHelper);}}";
                    } else if (dataIndex.toUpperCase() != "wellName".toUpperCase() 
                    		&& dataIndex.toUpperCase() != "deviceName".toUpperCase()
                    		&& dataIndex.toUpperCase() != "signInId".toUpperCase()
                    		&& dataIndex.toUpperCase() != "videoUrl1".toUpperCase()
                    		&& dataIndex.toUpperCase() != "videoKeyName1".toUpperCase()
                    		&& dataIndex.toUpperCase() != "videoUrl2".toUpperCase()
                    		&& dataIndex.toUpperCase() != "videoKeyName2".toUpperCase()) {
                        columns += "{data:'" + dataIndex + "',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,batchAddDeviceHandsontableHelper);}}";
                    }else {
                        columns += "{data:'" + dataIndex + "'}";
                    }
                    if (i < result.columns.length - 1) {
                        colHeaders += ",";
                        columns += ",";
                    }
                }
                colHeaders += "]";
                columns += "]";
                batchAddDeviceHandsontableHelper.colHeaders = Ext.JSON.decode(colHeaders);
                batchAddDeviceHandsontableHelper.columns = Ext.JSON.decode(columns);
                
                if(parseInt(deviceType)<200){
                	batchAddDeviceHandsontableHelper.pumpingModelInfo=result.pumpingModelInfo;
                }
                
                batchAddDeviceHandsontableHelper.createTable(result.totalRoot);
            } else {
            	batchAddDeviceHandsontableHelper.hot.loadData(result.totalRoot);
            }
        },
        failure: function () {
            Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
        },
        params: {
        	orgId: orgId,
        	orgIds: leftOrg_Id,
        	deviceType: deviceType,
            recordCount: 50,
//            applicationScenarios:applicationScenarios,
            page: 1,
            limit: 10000
        }
    });
};

var BatchAddDeviceHandsontableHelper = {
    createNew: function (divid) {
        var batchAddDeviceHandsontableHelper = {};
        batchAddDeviceHandsontableHelper.hot = '';
        batchAddDeviceHandsontableHelper.divid = divid;
        batchAddDeviceHandsontableHelper.validresult = true; //数据校验
        batchAddDeviceHandsontableHelper.colHeaders = [];
        batchAddDeviceHandsontableHelper.columns = [];

        batchAddDeviceHandsontableHelper.AllData = {};
        batchAddDeviceHandsontableHelper.updatelist = [];
        batchAddDeviceHandsontableHelper.delidslist = [];
        batchAddDeviceHandsontableHelper.insertlist = [];
        
        batchAddDeviceHandsontableHelper.pumpingModelInfo = {};

        batchAddDeviceHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace='nowrap'; //文本不换行
        	td.style.overflow='hidden';//超出部分隐藏
        	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
        }

        batchAddDeviceHandsontableHelper.createTable = function (data) {
            $('#' + batchAddDeviceHandsontableHelper.divid).empty();
            var hotElement = document.querySelector('#' + batchAddDeviceHandsontableHelper.divid);
            batchAddDeviceHandsontableHelper.hot = new Handsontable(hotElement, {
            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            	data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false,
                    copyPasteEnabled: false
                },
                columns: batchAddDeviceHandsontableHelper.columns,
                stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                autoWrapRow: true,
                rowHeaders: true, //显示行头
                colHeaders: batchAddDeviceHandsontableHelper.colHeaders, //显示列头
                columnSorting: true, //允许排序
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
                    if(batchAddDeviceHandsontableHelper.hot!=undefined && batchAddDeviceHandsontableHelper.hot.getDataAtCell!=undefined){
                    	var columns=batchAddDeviceHandsontableHelper.columns;
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
                        	var barrelType=batchAddDeviceHandsontableHelper.hot.getDataAtCell(row,barrelTypeColIndex);
                        	if(barrelType=='整筒泵'){
                        		this.source = ['1','2','3','4','5'];
                        	}else if(barrelType=='组合泵'){
                        		this.source = ['1','2','3'];
                        	}else if(barrelType==''){
                        		this.source = ['1','2','3','4','5'];
                        	}
                        }
                        if((visualColIndex==pumpingModelColIndex || visualColIndex==pumpingStrokeColIndex) && pumpingManufacturerColIndex>=0){
                        	var pumpingModelInfo=batchAddDeviceHandsontableHelper.pumpingModelInfo;
                        	if(visualColIndex==pumpingModelColIndex){
                            	var pumpingManufacturer=batchAddDeviceHandsontableHelper.hot.getDataAtCell(row,pumpingManufacturerColIndex);
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
                            	var pumpingManufacturer=batchAddDeviceHandsontableHelper.hot.getDataAtCell(row,pumpingManufacturerColIndex);
                            	var pumpingModel=batchAddDeviceHandsontableHelper.hot.getDataAtCell(row,pumpingModelColIndex);
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
                    
                    if(batchAddDeviceHandsontableHelper.columns[visualColIndex].type == undefined || batchAddDeviceHandsontableHelper.columns[visualColIndex].type!='dropdown'){
                		cellProperties.renderer = batchAddDeviceHandsontableHelper.addCellStyle;
                	}
                    
                    return cellProperties;
                },
                afterOnCellMouseOver: function(event, coords, TD){
                	if(batchAddDeviceHandsontableHelper!=null&&batchAddDeviceHandsontableHelper.hot!=''&&batchAddDeviceHandsontableHelper.hot!=undefined && batchAddDeviceHandsontableHelper.hot.getDataAtCell!=undefined){
                		var rawValue=batchAddDeviceHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
        batchAddDeviceHandsontableHelper.saveData = function () {
        	var orgId = Ext.getCmp('batchAddDeviceOrg_Id').getValue();
            var deviceType = Ext.getCmp('batchAddDeviceType_Id').getValue();
            var isCheckout=1;
            var saveDate={};
            saveDate.updatelist=[];
            var batchAddData=batchAddDeviceHandsontableHelper.hot.getData();
            for(var i=0;i<batchAddData.length;i++){
            	if(isNotVal(batchAddData[i][1])){
            		var data = "{";
                    for (var j = 0; j < batchAddDeviceHandsontableHelper.columns.length; j++) {
                        data += batchAddDeviceHandsontableHelper.columns[j].data + ":'" + batchAddData[i][j] + "'";
                        if (j < batchAddDeviceHandsontableHelper.columns.length - 1) {
                            data += ",";
                        }
                    }
                    data += "}";
                    var record=Ext.JSON.decode(data);
                    record.id=i;
                    saveDate.updatelist.push(record);
            	}
            }
        	Ext.Ajax.request({
                method: 'POST',
                url: context + '/wellInformationManagerController/batchAddDevice',
                success: function (response) {
                    Ext.getCmp("BatchAddDeviceWindow_Id").close();
                    CreateAndLoadDeviceInfoTable();
                	rdata = Ext.JSON.decode(response.responseText);
                	if (rdata.success&&rdata.collisionCount==0&&rdata.overlayCount==0) {
                    	if(rdata.overCount>0){
                    		Ext.MessageBox.alert(loginUserLanguageResource.message, "<font color=red>"+rdata.overCount+"</font>设备超限，保存失败");
                    	}else{
                    		Ext.MessageBox.alert(loginUserLanguageResource.message, loginUserLanguageResource.saveSuccessfully);
                    	}
                        //保存以后重置全局容器
                        batchAddDeviceHandsontableHelper.clearContainer();
                    }else if(rdata.success&&(rdata.collisionCount>0 || rdata.overlayCount>0)){
                    	var window = Ext.create("AP.view.well.BatchAddDeviceCollisionDataWindow", {
                            title: '异常数据处理'
                        });
                        Ext.getCmp("batchAddCollisionDeviceType_Id").setValue(deviceType);
                        Ext.getCmp("batchAddCollisionDeviceOrg_Id").setValue(orgId);
                        if(rdata.collisionCount==0){
                        	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").hide();
                        	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").setHeight('100%');
                        }else if(rdata.overlayCount==0){
                        	Ext.getCmp("BatchAddDeviceOverlayDataPanel_Id").hide();
                        	Ext.getCmp("BatchAddDeviceCollisionDataPanel_Id").setHeight('100%');
                        }
                        window.show();
                        
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
//                    applicationScenarios:applicationScenarios,
                    deviceType: deviceType,
                    isCheckout: isCheckout
                }
            });
        }

        batchAddDeviceHandsontableHelper.clearContainer = function () {
            batchAddDeviceHandsontableHelper.AllData = {};
            batchAddDeviceHandsontableHelper.updatelist = [];
            batchAddDeviceHandsontableHelper.delidslist = [];
            batchAddDeviceHandsontableHelper.insertlist = [];
        }

        return batchAddDeviceHandsontableHelper;
    }
};