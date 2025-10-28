//var protocolConfigInstancePropertiesHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolAcqInstanceConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolAcqInstanceConfigInfoView',
    layout: "fit",
    id:'ModbusProtocolAcqInstanceConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolAcqInstanceProtocolSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ScadaProtocolModbusInstanceConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treePanel=Ext.getCmp("AcqInstanceProtocolTreeGridPanel_Id");
                		if(isNotVal(treePanel)){
                			treePanel.getStore().load();
                		}else{
                			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqInstanceProtocolTreeInfoStore');
                		}
                    }
        		},'->',{
        			xtype: 'button',
                    text: loginUserLanguageResource.addAcqInstance,
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
        				addModbusProtocolInstanceConfigData();
        			}
        		}, "-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolInstanceConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolAcqInstanceWindow");
                        window.show();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.importData,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'import',
        			handler: function (v, o) {
        				var selectedDeviceTypeName="";
        				var selectedDeviceTypeId="";
        				var tabTreeStore = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getStore();
        				var count=tabTreeStore.getCount();
        				var tabTreeSelection = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        				var rec=null;
        				if (tabTreeSelection.length > 0) {
        					rec=tabTreeSelection[0];
        					selectedDeviceTypeName=foreachAndSearchTabAbsolutePath(tabTreeStore.data.items,tabTreeSelection[0].data.deviceTypeId);
        					selectedDeviceTypeId=tabTreeSelection[0].data.deviceTypeId;
        				} else {
        					if(count>0){
        						rec=orgTreeStore.getAt(0);
        						selectedDeviceTypeName=orgTreeStore.getAt(0).data.text;
        						selectedDeviceTypeId=orgTreeStore.getAt(0).data.deviceTypeId;
        					}
        				}
        				var window = Ext.create("AP.view.acquisitionUnit.ImportAcqInstanceWindow");
                        window.show();
        				Ext.getCmp("ImportAcqInstanceWinTabLabel_Id").setHtml(loginUserLanguageResource.targetType+":【<font color=red>"+selectedDeviceTypeName+"</font>】,"+loginUserLanguageResourceFirstLower.pleaseConfirm+"<br/>&nbsp;");
//        			    Ext.getCmp("ImportAcqInstanceWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportAcqInstanceWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                }],
                layout: "border",
                items: [{
                	region: 'west',
                	width:'20%',
                    layout: "fit",
                    id:'ModbusProtocolAcqInstanceProtocolListPanel_Id',
                    border: false,
                    title:loginUserLanguageResource.protocolList,
                    collapsible: true,
                    split: true,
                    collapseDirection: 'left'
                },{
                	region: 'center',
                	border: false,
                	layout: "border",
                	items:[{
                		region: 'west',
                		width:'33%',
                		collapsible: true,
                        split: true,
                        border: false,
                        layout: 'fit',
                    	title:loginUserLanguageResource.acqInstanceList,
                    	id:"ModbusProtocolInstanceConfigPanel_Id"
                	},{
                    	region: 'center',
                    	title:loginUserLanguageResource.properties,
                    	border: false,
                    	layout: 'fit',
                        html:'<div class="ProtocolConfigInstancePropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolConfigInstancePropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolConfigInstancePropertiesHandsontableHelper!=null && protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolConfigInstancePropertiesHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    }]
                }]
            }]
    	});
        this.callParent(arguments);
    }
});
function CreateProtocolInstanceConfigPropertiesInfoTable(data){
	var root=[];
	var unitList=[];
	var unitIdNameList=[];
	var protocolList=[];
	var protocolTreeGridPanelSelection= Ext.getCmp("AcqInstanceProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
	if(protocolTreeGridPanelSelection.length>0){
		if(protocolTreeGridPanelSelection[0].data.classes==1){
			protocolList.push(protocolTreeGridPanelSelection[0].data.code);
		}else{
			if(isNotVal(protocolTreeGridPanelSelection[0].data.children)){
				for(var i=0;i<protocolTreeGridPanelSelection[0].data.children.length;i++){
					protocolList.push(protocolTreeGridPanelSelection[0].data.children[i].code);
				}
			}
		}
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getAcqUnitList',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			unitList=result.unitList;
			unitIdNameList=result.unitIdNameList;
			var hiddenRows=[];
			if(data.classes==0){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.rootNode;
				item1.value=loginUserLanguageResource.instanceList;
				root.push(item1);
			}else if(data.classes==1){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.instanceName;
				item1.value=data.text;
				root.push(item1);
				
				var item2={};
				item2.id=2;
				item2.title=loginUserLanguageResource.acqUnit;
				item2.value=data.protocol+'/'+data.unitName;
				root.push(item2);
				
				var item3={};
				item3.id=3;
				item3.title=loginUserLanguageResource.acqProtocolType;
				item3.value=data.acqProtocolType;
				root.push(item3);
				if(data.acqProtocolType.startsWith('private-')){
					hiddenRows=[4,5,6,7,8,9,10,11];
				}
				
				var item4={};
				item4.id=4;
				item4.title=loginUserLanguageResource.ctrlProtocolType;
				item4.value=data.ctrlProtocolType;
				root.push(item4);
				
				
				var item5={};
				item5.id=5;
				item5.title=loginUserLanguageResource.signInPrefixSuffixHex;
				if(parseInt(data.signInPrefixSuffixHex)==1){
					item5.value=true;
				}else{
					item5.value=false;
				}
				root.push(item5);
				
				var item6={};
				item6.id=6;
				item6.title=loginUserLanguageResource.signInPrefix+'(HEX/ASC)';
				item6.value=data.signInPrefix;
				root.push(item6);
				
				var item7={};
				item7.id=7;
				item7.title=loginUserLanguageResource.signInSuffix+'(HEX/ASC)';
				item7.value=data.signInSuffix;
				root.push(item7);
				
				var item8={};
				item8.id=8;
				item8.title=loginUserLanguageResource.signInIDHex;
				if(parseInt(data.signInIDHex)==1){
					item8.value=true;
				}else{
					item8.value=false;
				}
				root.push(item8);
				
				var item9={};
				item9.id=9;
				item9.title=loginUserLanguageResource.heartbeatPrefixSuffixHex;
				if(parseInt(data.heartbeatPrefixSuffixHex)==1){
					item9.value=true;
				}else{
					item9.value=false;
				}
				root.push(item9);
				
				var item10={};
				item10.id=10;
				item10.title=loginUserLanguageResource.heartbeatPrefix+'(HEX/ASC)';
				item10.value=data.heartbeatPrefix;
				root.push(item10);
				
				var item11={};
				item11.id=11;
				item11.title=loginUserLanguageResource.heartbeatSuffix+'(HEX/ASC)';
				item11.value=data.heartbeatSuffix;
				root.push(item11);
				
				
				var item12={};
				item12.id=12;
				item12.title=loginUserLanguageResource.packetSendInterval+'(ms)';
				item12.value=data.packetSendInterval;
				root.push(item12);
				
				var item13={};
				item13.id=13;
				if(hiddenRows.length>0){
					item13.id=item13.id-hiddenRows.length;
				}
				item13.title=loginUserLanguageResource.sortNum;
				item13.value=data.sort;
				root.push(item13);
			}else if(data.classes==2){
				var item1={};
				item1.id=1;
				item1.title=loginUserLanguageResource.acqUnit;
				item1.value=data.text;
				root.push(item1);
			}
			
			if(protocolConfigInstancePropertiesHandsontableHelper==null || protocolConfigInstancePropertiesHandsontableHelper.hot==undefined){
				protocolConfigInstancePropertiesHandsontableHelper = ProtocolConfigInstancePropertiesHandsontableHelper.createNew("ProtocolConfigInstancePropertiesTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
				var columns="[{data:'id'},{data:'title'},{data:'value'}]";
				protocolConfigInstancePropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolConfigInstancePropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolConfigInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolConfigInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolConfigInstancePropertiesHandsontableHelper.unitIdNameList=unitIdNameList;
				protocolConfigInstancePropertiesHandsontableHelper.createTable(root);
			}else{
				protocolConfigInstancePropertiesHandsontableHelper.classes=data.classes;
				protocolConfigInstancePropertiesHandsontableHelper.unitList=unitList;
				protocolConfigInstancePropertiesHandsontableHelper.unitIdNameList=unitIdNameList;
				protocolConfigInstancePropertiesHandsontableHelper.hot.loadData(root);
			}
			
			const plugin = protocolConfigInstancePropertiesHandsontableHelper.hot.getPlugin('hiddenRows');
			if(hiddenRows.length>0){
				plugin.hideRows(hiddenRows);
			}else{
				plugin.showRows(hiddenRows);
			}
			protocolConfigInstancePropertiesHandsontableHelper.hot.render();
		},
		failure:function(){
//			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocol: protocolList.join(",")
        }
	});
};

var ProtocolConfigInstancePropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolConfigInstancePropertiesHandsontableHelper = {};
	        protocolConfigInstancePropertiesHandsontableHelper.hot = '';
	        protocolConfigInstancePropertiesHandsontableHelper.classes =null;
	        protocolConfigInstancePropertiesHandsontableHelper.divid = divid;
	        protocolConfigInstancePropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolConfigInstancePropertiesHandsontableHelper.colHeaders=[];
	        protocolConfigInstancePropertiesHandsontableHelper.columns=[];
	        protocolConfigInstancePropertiesHandsontableHelper.AllData=[];
	        protocolConfigInstancePropertiesHandsontableHelper.unitList=[];
	        protocolConfigInstancePropertiesHandsontableHelper.unitIdNameList=[];
	        
	        protocolConfigInstancePropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolConfigInstancePropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolConfigInstancePropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolConfigInstancePropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolConfigInstancePropertiesHandsontableHelper.divid);
	        	protocolConfigInstancePropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [1,4,5],
	        		hiddenRows: {
	                    rows: [],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                columns:protocolConfigInstancePropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolConfigInstancePropertiesHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                contextMenu: {
	                    items: {
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut
	                        }
	                    }
	                }, 
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	if (visualColIndex ==0 || visualColIndex ==1) {
								cellProperties.readOnly = true;
								cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addBoldBg;
			                }
		                    if(protocolConfigInstancePropertiesHandsontableHelper.classes===0 || protocolConfigInstancePropertiesHandsontableHelper.classes===2){
		                    	cellProperties.readOnly = true;
								cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addBoldBg;
		                    }else if(protocolConfigInstancePropertiesHandsontableHelper.classes===1){
		                    	if(visualColIndex === 2 && visualRowIndex===0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolConfigInstancePropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
		                    		this.type = 'dropdown';
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    	this.source = protocolConfigInstancePropertiesHandsontableHelper.unitList;
			                    	
			                    	
//			                    	this.source = protocolConfigInstancePropertiesHandsontableHelper.unitIdNameList;
//			                    	this.renderer= function(instance, td, row, col, prop, value, cellProperties) {
//			                            const source = cellProperties.source;
//			                            const item = source.find(item => item.value === value);
//			                            td.textContent = item ? item.label : value;
//			                            return td;
//			                          }
			                    }else if (visualColIndex === 2 && visualRowIndex===2) {
			                    	this.type = 'dropdown';
			                    	this.source = ['modbus-tcp','modbus-rtu','private-kd93','private-lq1000','private-g771'];
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }else if (visualColIndex === 2 && visualRowIndex===3) {
			                    	this.type = 'dropdown';
			                    	this.source = ['modbus-tcp','modbus-rtu'];
			                    	this.strict = true;
			                    	this.allowInvalid = false;
			                    }else if (visualColIndex === 2 && (visualRowIndex===4 ||visualRowIndex===7 || visualRowIndex===8) ) {
			                    	this.type = 'checkbox';
			                    }else if(visualColIndex === 2 && (visualRowIndex===11 || visualRowIndex===12)){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolConfigInstancePropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && (visualRowIndex===5 || visualRowIndex===6) ) {
			                    	if(protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined && protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
			                    		var signInPrefixSuffixHex=protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell(4,2);
			                    		if(signInPrefixSuffixHex){
			                    			this.validator=function (val, callback) {
					                    	    return handsontableDataCheck_HexStr_Nullable(val, callback, row, col, protocolConfigInstancePropertiesHandsontableHelper);
					                    	}
			                    		}else{
			                    			this.validator=function (val, callback) {
			                    				return callback(true);
			                    			}
			                    		}
			                    	}
			                    	cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && (visualRowIndex===9 || visualRowIndex===10) ) {
			                    	if(protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined && protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
			                    		var heartbeatPrefixSuffixHex=protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell(8,2);
			                    		if(heartbeatPrefixSuffixHex){
			                    			this.validator=function (val, callback) {
					                    	    return handsontableDataCheck_HexStr_Nullable(val, callback, row, col, protocolConfigInstancePropertiesHandsontableHelper);
					                    	}
			                    		}else{
			                    			this.validator=function (val, callback) {
			                    				return callback(true);
			                    			}
			                    		}
			                    	}
			                    	cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addCellStyle;
			                    }
		                    }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolConfigInstancePropertiesHandsontableHelper.addBoldBg;
		                }
	                    
	                    return cellProperties;
	                },
	                afterChange: function (changes, source) {
	                	if (!changes) return;
	                	changes.forEach(([row, prop, oldValue, newValue]) => {
	                		if(row==2 && prop==='value' ){
	                			const plugin = protocolConfigInstancePropertiesHandsontableHelper.hot.getPlugin('hiddenRows');
	                			var hiddenRows=[4,5,6,7,8,9,10,11];
	                			
	                			if(newValue.startsWith('private-')){
	                				plugin.hideRows(hiddenRows);
	                				protocolConfigInstancePropertiesHandsontableHelper.hot.setDataAtRowProp(12,'id',5);
	            				}else{
	            					plugin.showRows(hiddenRows);
	            					protocolConfigInstancePropertiesHandsontableHelper.hot.setDataAtRowProp(12,'id',13);
	            				}
	                			protocolConfigInstancePropertiesHandsontableHelper.hot.render();
	                		}
	                	});
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if((coords.col<2||(coords.col==2&&coords.row!=4&&coords.row!=7&&coords.row!=8  ))
	                		&& protocolConfigInstancePropertiesHandsontableHelper!=null
	                		&& protocolConfigInstancePropertiesHandsontableHelper.hot!=''
	                		&& protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined 
	                		&& protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolConfigInstancePropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolConfigInstancePropertiesHandsontableHelper.saveData = function () {}
	        protocolConfigInstancePropertiesHandsontableHelper.clearContainer = function () {
	        	protocolConfigInstancePropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolConfigInstancePropertiesHandsontableHelper;
	    }
};

function SaveModbusProtocolInstanceConfigTreeData(){
	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ScadaProtocolModbusInstanceConfigSelectRow_Id").getValue();
	if(ScadaDriverModbusConfigSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
		
		var protocolCode='';
		var protocolTreeGridPanelSelection= Ext.getCmp("AcqInstanceProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
    	if(protocolTreeGridPanelSelection.length>0){
    		if(protocolTreeGridPanelSelection[0].data.classes==1){
    			protocolCode=protocolTreeGridPanelSelection[0].data.code;
    		}
    	}
		
		
		var propertiesData=protocolConfigInstancePropertiesHandsontableHelper.hot.getData();
		if(selectedItem.data.classes==1){//选中的是实例
			var saveData={};
			saveData.id=selectedItem.data.id;
			saveData.code=selectedItem.data.code;
			saveData.oldName=selectedItem.data.text;
			saveData.protocol=selectedItem.data.protocol;
			saveData.protocolDeviceTypeAllPath=selectedItem.data.protocolDeviceTypeAllPath;
			
			saveData.name=propertiesData[0][2];
			saveData.unitId=selectedItem.data.unitId;
			saveData.unitName=propertiesData[1][2];
			saveData.acqProtocolType=propertiesData[2][2];
			saveData.ctrlProtocolType=propertiesData[3][2];
			
			if(propertiesData[4][2]==true){
				saveData.signInPrefixSuffixHex=1;
			}else{
				saveData.signInPrefixSuffixHex=0;
			}
			saveData.signInPrefix=propertiesData[5][2];
			saveData.signInSuffix=propertiesData[6][2];
			if(propertiesData[7][2]==true){
				saveData.signInIDHex=1;
			}else{
				saveData.signInIDHex=0;
			}
			
			if(propertiesData[8][2]==true){
				saveData.heartbeatPrefixSuffixHex=1;
			}else{
				saveData.heartbeatPrefixSuffixHex=0;
			}
			saveData.heartbeatPrefix=propertiesData[9][2];
			saveData.heartbeatSuffix=propertiesData[10][2];
			
			saveData.packetSendInterval=propertiesData[11][2];
			
			saveData.sort=propertiesData[12][2];
			
			SaveModbusProtocolAcqInstanceData(saveData);
		}
	}
};

function SaveModbusProtocolAcqInstanceData(saveData){
	var protocolList=[];
	var protocolTreeGridPanelSelection= Ext.getCmp("AcqInstanceProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
	if(protocolTreeGridPanelSelection.length>0){
		if(protocolTreeGridPanelSelection[0].data.classes==1){
			protocolList.push(protocolTreeGridPanelSelection[0].data.code);
		}else{
			if(isNotVal(protocolTreeGridPanelSelection[0].data.children)){
				for(var i=0;i<protocolTreeGridPanelSelection[0].data.children.length;i++){
					protocolList.push(protocolTreeGridPanelSelection[0].data.children[i].code);
				}
			}
		}
	}
	var protocols=protocolList.join(",");
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveProtocolInstanceData',
		success:function(response) {
			data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ScadaProtocolModbusInstanceConfigSelectRow_Id").setValue(0);
					Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.deleteSuccessfully);
				}else{
					Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
				}
				Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id").getStore().load();
            	
            } else {
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			data: JSON.stringify(saveData),
			protocols:protocols
        }
	});
}

