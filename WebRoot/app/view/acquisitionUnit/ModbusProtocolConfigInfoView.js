//var protocolItemsConfigHandsontableHelper=null;
//var protocolPropertiesHandsontableHelper=null;
//var protocolItemsMeaningConfigHandsontableHelper=null;
Ext.define('AP.view.acquisitionUnit.ModbusProtocolConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolConfigInfoView',
    layout: "fit",
    id:'modbusProtocolConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolAddrMappingConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ModbusProtocolAddrMappingConfigSelectProtocolId_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ModbusProtocolAddNewProtocolName_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'ModbusProtocolAddrMappingItemsSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: loginUserLanguageResource.addProtocol,
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addModbusProtocolAddrMappingConfigData();
        			}
        		},"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolAddrMappingConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.columnMappingTable,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.DatabaseColumnMappingWindow", {
                            title: loginUserLanguageResource.columnMappingTable
                        });
                        window.show();
        			}
                },"-",{
                	xtype: 'button',
        			text: loginUserLanguageResource.exportData,
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolWindow");
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
        				if(selectedDeviceTypeId!=""){
        					var window = Ext.create("AP.view.acquisitionUnit.ImportProtocolWindow");
                            window.show();
        				}
        				Ext.getCmp("ImportProtocolWinTabLabel_Id").setHtml(loginUserLanguageResource.owningDeviceType+":<font color=red>"+selectedDeviceTypeName+"</font>,"+loginUserLanguageResource.pleaseConfirm+"<br/>&nbsp;");
        				Ext.getCmp("ImportProtocolWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportProtocolWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                },'-', {
        			xtype: 'button',
        			text:loginUserLanguageResource.protocoDeviceTypeChange,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'move',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ProtocoDeviceTypeChangeWindow", {
                            title: loginUserLanguageResource.protocoDeviceTypeChange
                        });
                        window.show();
                        Ext.create("AP.store.acquisitionUnit.ProtocolDeviceTypeChangeProtocolListStore");
                        Ext.create("AP.store.acquisitionUnit.ProtocolDeviceTypeChangeDeviceTypeListStore");
        			}
        		}],
                layout: "border",
                items: [{
                	region: 'west',
                	width:'17%',
                	title:loginUserLanguageResource.protocolConfig,
                	layout: 'fit',
                	id:"ModbusProtocolAddrMappingConfigPanel_Id",
                	border: false,
                	collapsible: true,
                    split: true
                },{
                	region: 'center',
                	border: false,
                	header: false,
                	xtype: 'tabpanel',
                    id:"ProtocolConfigRightTabPanel_Id",
                    activeTab: 1,
                    tabBar:{
                		items: [{
                            xtype: 'tbfill'
                        },{
                        	xtype: 'label',
                        	id: 'ProtocolConfigInformationLabel_Id',
                        	hidden:true,
                        	html: ''
                        },{
                        	xtype: 'label',
                        	hidden:false,
                        	html: '&nbsp;'
                        }]
                	},
                    items:[{
                    	id:"ProtocolPropertiesConfigRightTabPanel_Id",
                    	title:loginUserLanguageResource.properties,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolAddrMappingPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAddrMappingPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolPropertiesHandsontableHelper!=null && protocolPropertiesHandsontableHelper.hot!=undefined){
//                            		protocolPropertiesHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolPropertiesHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    },{
                    	id:"ProtocolContentConfigRightTabPanel_Id",
                    	border: false,
                        title:loginUserLanguageResource.config,
                        layout: "border",
                        iconCls: 'check3',
                        items: [{
                        	region: 'center',
                        	title:loginUserLanguageResource.acqAndCtrlItemConfig,
                        	layout: 'fit',
                        	header:false,
                        	id:'ModbusProtocolAddrMappingItemsConfigTabPanel_Id',
                            html:'<div class="ModbusProtocolAddrMappingItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAddrMappingItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolItemsConfigHandsontableHelper!=null && protocolItemsConfigHandsontableHelper.hot!=undefined){
//                                		protocolItemsConfigHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolItemsConfigHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                        },{
                        	region: 'east',
                        	width:'20%',
                        	title:loginUserLanguageResource.meaning,
                        	id:'ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id',
                        	header:false,
                        	collapsible: true,
                        	collapsed: false,
                            split: true,
                            layout: 'fit',
                            html:'<div class="ModbusProtocolAddrMappingItemsMeaningTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAddrMappingItemsMeaningTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolItemsMeaningConfigHandsontableHelper!=null && protocolItemsMeaningConfigHandsontableHelper.hot!=undefined){
//                                		protocolItemsMeaningConfigHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolItemsMeaningConfigHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                        	
                        }]
                    },{
                    	id:"ProtocolExtendedFieldConfigRightTabPanel_Id",
                    	title:loginUserLanguageResource.extendedField,
                    	hidden:false,
                    	layout: 'fit',
                        html:'<div class="ProtocolExtendedFieldTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolExtendedFieldTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolExtendedFieldConfigHandsontableHelper!=null && protocolExtendedFieldConfigHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolExtendedFieldConfigHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
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
            				var record= Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getSelectionModel().getSelection()[0];
                        	if(newCard.id=="ProtocolPropertiesConfigRightTabPanel_Id"){
                        		CreateProtocolConfigAddrMappingPropertiesInfoTable(record.data);
                        	}else if(newCard.id=="ProtocolContentConfigRightTabPanel_Id"){
                        		if(record.data.classes==0){
                            		if(protocolItemsConfigHandsontableHelper!=null){
                    					if(protocolItemsConfigHandsontableHelper.hot!=undefined){
                    						protocolItemsConfigHandsontableHelper.hot.destroy();
                    					}
                    					protocolItemsConfigHandsontableHelper=null;
                    				}
                            		if(protocolItemsMeaningConfigHandsontableHelper!=null){
                    					if(protocolItemsMeaningConfigHandsontableHelper.hot!=undefined){
                    						protocolItemsMeaningConfigHandsontableHelper.hot.destroy();
                    					}
                    					protocolItemsMeaningConfigHandsontableHelper=null;
                    				}
                            	}else if(record.data.classes==1){
                            		CreateModbusProtocolAddrMappingItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                            	}
                        	}else if(newCard.id=="ProtocolExtendedFieldConfigRightTabPanel_Id"){
                        		if(record.data.classes==0){
                            		if(protocolExtendedFieldConfigHandsontableHelper!=null){
                    					if(protocolExtendedFieldConfigHandsontableHelper.hot!=undefined){
                    						protocolExtendedFieldConfigHandsontableHelper.hot.destroy();
                    					}
                    					protocolExtendedFieldConfigHandsontableHelper=null;
                    				}
                            	}else if(record.data.classes==1){
                            		CreateProtocolExtendedFieldConfigInfoTable(record.data.text,record.data.classes,record.data.code);
                            	}
                        	}
                        }
                    }
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateModbusProtocolAddrMappingItemsConfigInfoTable(protocolName,classes,code){
	Ext.getCmp("ModbusProtocolAddrMappingItemsConfigTabPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	var tabPanel = Ext.getCmp("ProtocolConfigRightTabPanel_Id");
	var showInfo=tabPanel.getActiveTab().title;
	if(isNotVal(protocolName)){
		showInfo="【<font color=red>"+protocolName+"</font>】"+showInfo+"&nbsp;"
	}
	Ext.getCmp("ProtocolConfigInformationLabel_Id").setHtml(showInfo);
    Ext.getCmp("ProtocolConfigInformationLabel_Id").show();
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAddrMappingItemsConfigTabPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			var dataLength=result.totalRoot.length;
			var defultNullData=[];
			var defultDataLength=500;
			for(var i=0;i<defultDataLength;i++){
				var nullData={};
				defultNullData.push(nullData);
			}
			
			if(dataLength<defultDataLength){
				if(defultDataLength-dataLength<10){
					for(var i=0;i<10;i++){
						var nullData={};
						result.totalRoot.push(nullData);
					}
				}else{
					for(var i=dataLength;i<defultDataLength;i++){
						var nullData={};
						result.totalRoot.push(nullData);
					}
				}
			}else{
				for(var i=0;i<10;i++){
					var nullData={};
					result.totalRoot.push(nullData);
				}
			}
			
			if(protocolItemsConfigHandsontableHelper==null || protocolItemsConfigHandsontableHelper.hot==undefined){
				protocolItemsConfigHandsontableHelper = ProtocolItemsConfigHandsontableHelper.createNew("ModbusProtocolAddrMappingItemsConfigTableInfoDiv_id");
				var colHeaders="[" 
					+"['','',{label: '"+loginUserLanguageResource.lowerComputer+"', colspan: 5},{label: '"+loginUserLanguageResource.upperComputer+"', colspan: 5}]," 
					+"['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.startAddress+"','"+loginUserLanguageResource.storeDataType+"','"+loginUserLanguageResource.quantity+"','"+loginUserLanguageResource.RWType+"','"+loginUserLanguageResource.acqMode+"','"+loginUserLanguageResource.IFDataType+"','"+loginUserLanguageResource.prec+"','"+loginUserLanguageResource.ratio+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.resolutionMode+"']" 
					+"]";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolItemsConfigHandsontableHelper);}},"
					 	+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bit','byte','int16','uint16','float32','bcd']}," 
					 	+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolItemsConfigHandsontableHelper);}}," 
					 	+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.readOnly+"', '"+loginUserLanguageResource.writeOnly+"', '"+loginUserLanguageResource.readWrite+"']}," 
					 	+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.activeAcqModel+"', '"+loginUserLanguageResource.passiveAcqModel+"']}," 
						+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolItemsConfigHandsontableHelper);}}," 
						+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolItemsConfigHandsontableHelper);}}," 
						+"{data:'unit'}," 
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.switchingValue+"', '"+loginUserLanguageResource.enumValue+"','"+loginUserLanguageResource.numericValue+"']}" 
						+"]";
				protocolItemsConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolItemsConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolItemsConfigHandsontableHelper.Data=defultNullData;
					protocolItemsConfigHandsontableHelper.createTable(defultNullData);
				}else{
					protocolItemsConfigHandsontableHelper.Data=result.totalRoot;
					protocolItemsConfigHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				protocolItemsConfigHandsontableHelper.hot.deselectCell();
				
				if(result.totalRoot.length==0){
					protocolItemsConfigHandsontableHelper.Data=defultNullData;
					protocolItemsConfigHandsontableHelper.hot.loadData(defultNullData);
				}else{
					protocolItemsConfigHandsontableHelper.Data=result.totalRoot;
					protocolItemsConfigHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
			if(dataLength==0){
				protocolItemsConfigHandsontableHelper.hot.selectCell(0,'title');
				Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue('');
				CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable('','','',true);
			}else{
				protocolItemsConfigHandsontableHelper.hot.selectCell(0,'title');
				
				Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue(0);
				var protocolTreeGridPanelSelectRow= Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").getValue();
				var protocolCode="";
        		if(protocolTreeGridPanelSelectRow!=''){
        			var selectedProtocol=Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().getAt(protocolTreeGridPanelSelectRow);
            		if(selectedProtocol.data.classes==1){//选中的是协议
            			protocolCode=selectedProtocol.data.code;
            		}
            		
            		var itemAddr=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(0,'addr');
            		var resolutionMode=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(0,'resolutionMode');
            		CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode,itemAddr,resolutionMode,true);
        		}
			}
			protocolItemsConfigHandsontableHelper.initTitleDataMap(protocolItemsConfigHandsontableHelper.Data);
			protocolItemsConfigHandsontableHelper.initAddressDataMap(protocolItemsConfigHandsontableHelper.Data);
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAddrMappingItemsConfigTabPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ProtocolItemsConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolItemsConfigHandsontableHelper = {};
	        protocolItemsConfigHandsontableHelper.hot1 = '';
	        protocolItemsConfigHandsontableHelper.divid = divid;
	        protocolItemsConfigHandsontableHelper.validresult=true;//数据校验
	        protocolItemsConfigHandsontableHelper.colHeaders=[];
	        protocolItemsConfigHandsontableHelper.columns=[];
	        protocolItemsConfigHandsontableHelper.AllData=[];
	        protocolItemsConfigHandsontableHelper.Data=[];
	        protocolItemsConfigHandsontableHelper.titleDataMap=new Map();
	        protocolItemsConfigHandsontableHelper.addressDataMap=new Map();
	        
	        protocolItemsConfigHandsontableHelper.initTitleDataMap= function (data){
	        	protocolItemsConfigHandsontableHelper.titleDataMap.clear();
                data.forEach((row, index) => {
                    const value = row.title;
                    if (row.title==undefined || !value) return;
                    
                    if (!protocolItemsConfigHandsontableHelper.titleDataMap.has(value)) {
                        protocolItemsConfigHandsontableHelper.titleDataMap.set(value, [index]);
                    } else {
                        protocolItemsConfigHandsontableHelper.titleDataMap.get(value).push(index);
                    }
                });
	        }
	        
	        protocolItemsConfigHandsontableHelper.getDuplicateCount= function(){
	        	var duplicateCount = 0;
	        	for (const [value, indexes] of protocolItemsConfigHandsontableHelper.titleDataMap.entries()) {
                    if (indexes.length > 1) {
                        duplicateCount += indexes.length;
                    }
                }
	        	return duplicateCount;
	        }
	        
	        protocolItemsConfigHandsontableHelper.getDuplicateRowList= function(){
	        	var duplicateList = [];
	        	for (const [value, indexes] of protocolItemsConfigHandsontableHelper.titleDataMap.entries()) {
                    if (indexes.length > 1) {
                    	for(var i=0;i<indexes.length;i++){
                    		duplicateList.push(indexes[i]);
                    	}
                    }
                }
	        	return duplicateList;
	        }
	        
	        protocolItemsConfigHandsontableHelper.initAddressDataMap= function (data){
	        	protocolItemsConfigHandsontableHelper.addressDataMap.clear();
                data.forEach((row, index) => {
                    const value = row.addr+"";
                    if (row.addr==undefined || !value) return;
                    
                    if (!protocolItemsConfigHandsontableHelper.addressDataMap.has(value)) {
                        protocolItemsConfigHandsontableHelper.addressDataMap.set(value, [index]);
                    } else {
                        protocolItemsConfigHandsontableHelper.addressDataMap.get(value).push(index);
                    }
                });
	        }
	        
	        protocolItemsConfigHandsontableHelper.getAddrDuplicateCount= function(){
	        	var duplicateCount = 0;
	        	for (const [value, indexes] of protocolItemsConfigHandsontableHelper.addressDataMap.entries()) {
                    if (indexes.length > 1) {
                        duplicateCount += indexes.length;
                    }
                }
	        	return duplicateCount;
	        }
	        
	        protocolItemsConfigHandsontableHelper.getAddrDuplicateRowList= function(){
	        	var duplicateList = [];
	        	for (const [value, indexes] of protocolItemsConfigHandsontableHelper.addressDataMap.entries()) {
                    if (indexes.length > 1) {
                    	for(var i=0;i<indexes.length;i++){
                    		duplicateList.push(indexes[i]);
                    	}
                    }
                }
	        	return duplicateList;
	        }
	        
	        protocolItemsConfigHandsontableHelper.uniqueTitleRenderer = function (instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
                if (prop === 'title') {
                    // 检查重复
                    if (value && protocolItemsConfigHandsontableHelper.titleDataMap.has(value)) {
                        const rows = protocolItemsConfigHandsontableHelper.titleDataMap.get(value);
                        if (rows.length > 1 && rows.includes(row)) {
                            td.style.backgroundColor = '#FF4C42';
                        }
                    }
                }else if (prop === 'addr') {
                    // 检查重复
                    if (value+"" && protocolItemsConfigHandsontableHelper.addressDataMap.has(value+"")) {
                        const rows = protocolItemsConfigHandsontableHelper.addressDataMap.get(value+"");
                        if (rows.length > 1 && rows.includes(row)) {
                            td.style.backgroundColor = '#FF4C42';
                        }
                    }
                }
            }
	        
	        
	        
	        
	        protocolItemsConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolItemsConfigHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolItemsConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolItemsConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolItemsConfigHandsontableHelper.divid);
	        	protocolItemsConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [0],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [50,200,80,90,90,80,80,90,80,80,80,160],
	                columns:protocolItemsConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                bindRowsWithHeaders: 'strict',
//	                colHeaders:protocolItemsConfigHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:protocolItemsConfigHandsontableHelper.colHeaders,//显示列头
//	                nestedRows:true,
	                columnHeaderHeight: 28,
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                outsideClickDeselects:false,
	                contextMenu: {
	                    items: {
	                        "row_above": {
	                            name: loginUserLanguageResource.contextMenu_insertRowAbove,
	                        },
	                        "row_below": {
	                            name: loginUserLanguageResource.contextMenu_insertRowBelow,
	                        },
	                        "col_left": {
	                            name: loginUserLanguageResource.contextMenu_insertColumnLeft,
	                        },
	                        "col_right": {
	                            name: loginUserLanguageResource.contextMenu_insertColumnRight,
	                        },
	                        "remove_row": {
	                            name: loginUserLanguageResource.contextMenu_removeRow,
	                        },
	                        "remove_col": {
	                            name: loginUserLanguageResource.contextMenu_removeColumn,
	                        },
	                        "merge_cell": {
	                            name: loginUserLanguageResource.contextMenu_mergeCell,
	                        },
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy,
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut,
	                        },
	                        "paste": {
	                            name: loginUserLanguageResource.contextMenu_paste,
	                            disabled: function () {
	                            },
	                            callback: function () {
	                            }
	                        }
	                    }
	                },//右键菜单展示
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	if (visualColIndex ==0) {
								cellProperties.readOnly = true;
			                }
	                    }else{
	                    	cellProperties.readOnly = true;
	                    }
	                    
	                    if (visualColIndex ==0) {
	                    	cellProperties.renderer = protocolItemsConfigHandsontableHelper.addBoldBg;
	                    }else{
	                    	if(protocolItemsConfigHandsontableHelper.columns[visualColIndex].type == undefined || protocolItemsConfigHandsontableHelper.columns[visualColIndex].type!='dropdown'){
	                    		cellProperties.renderer = protocolItemsConfigHandsontableHelper.uniqueTitleRenderer;
	                    	}
	                    }
	                    return cellProperties;
	                },
	                afterChange: function (changes, source) {
	                	if (!changes) return;
	                	let needUpdateMap = false;
	                	changes.forEach(([row, prop, oldValue, newValue]) => {
	                        if (prop === 'title') {
	                            // 从映射中移除旧值
	                            if (oldValue && protocolItemsConfigHandsontableHelper.titleDataMap.has(oldValue)) {
	                                const rows = protocolItemsConfigHandsontableHelper.titleDataMap.get(oldValue);
	                                const index = rows.indexOf(row);
	                                if (index !== -1) {
	                                    rows.splice(index, 1);
	                                    if (rows.length === 0) {
	                                        protocolItemsConfigHandsontableHelper.titleDataMap.delete(oldValue);
	                                    }
	                                }
	                            }

	                            // 添加新值到映射
	                            if (newValue) {
	                                if (!protocolItemsConfigHandsontableHelper.titleDataMap.has(newValue)) {
	                                    protocolItemsConfigHandsontableHelper.titleDataMap.set(newValue, [row]);
	                                } else {
	                                    const rows = protocolItemsConfigHandsontableHelper.titleDataMap.get(newValue);
	                                    if (!rows.includes(row)) {
	                                        rows.push(row);
	                                    }
	                                }
	                            }
	                            if (oldValue !== newValue) {
	                            	needUpdateMap = true;
	                            }
	                        }else if (prop === 'addr') {
	                            // 从映射中移除旧值
	                            if (oldValue && protocolItemsConfigHandsontableHelper.addressDataMap.has(oldValue+"")) {
	                                const rows = protocolItemsConfigHandsontableHelper.addressDataMap.get(oldValue+"");
	                                const index = rows.indexOf(row);
	                                if (index !== -1) {
	                                    rows.splice(index, 1);
	                                    if (rows.length === 0) {
	                                        protocolItemsConfigHandsontableHelper.addressDataMap.delete(oldValue+"");
	                                    }
	                                }
	                            }

	                            // 添加新值到映射
	                            if (newValue) {
	                                if (!protocolItemsConfigHandsontableHelper.addressDataMap.has(newValue+"")) {
	                                    protocolItemsConfigHandsontableHelper.addressDataMap.set(newValue+"", [row]);
	                                } else {
	                                    const rows = protocolItemsConfigHandsontableHelper.addressDataMap.get(newValue+"");
	                                    if (!rows.includes(row)) {
	                                        rows.push(row);
	                                    }
	                                }
	                            }
	                            if (oldValue !== newValue) {
	                            	needUpdateMap = true;
	                            }
	                        }else if (prop === 'resolutionMode') {
	                        	var selectedRow=Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").getValue();
	                        	if(selectedRow==row 
	                        			&& protocolItemsMeaningConfigHandsontableHelper!=null
	                        			&& protocolItemsMeaningConfigHandsontableHelper.hot!=''
	                        			&& protocolItemsMeaningConfigHandsontableHelper.hot!=undefined){
	                        		var resolutionMode=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(row,'resolutionMode');
	                        		if(resolutionMode==loginUserLanguageResource.switchingValue){
	                        			protocolItemsMeaningConfigHandsontableHelper.hot.updateSettings({
	                        			    colHeaders: [loginUserLanguageResource.bit,loginUserLanguageResource.meaning]
	                        			});
	                        		}else{
	                        			protocolItemsMeaningConfigHandsontableHelper.hot.updateSettings({
	                        			    colHeaders: [loginUserLanguageResource.value,loginUserLanguageResource.meaning]
	                        			});
	                        		}
	                        	}
	                        }
	                    });
	                	if(needUpdateMap){
	                		protocolItemsConfigHandsontableHelper.hot.render();
	                	}
	                },
	                afterSelectionEnd : function (row, column, row2, column2, selectionLayerLevel) {
	                	if(row<0 && row2<0){//只选中表头
	                		Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue('');
	                		CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable('','','',true);
	                	}else{
	                		if(row<0){
	                    		row=0;
	                    	}
	                    	if(row2<0){
	                    		row2=0;
	                    	}
	                    	var startRow=row;
	                    	var endRow=row2;
	                    	if(row>row2){
	                    		startRow=row2;
	                        	endRow=row;
	                    	}
	                    	
	                    	var selectedRow=Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").getValue();
	                    	if(selectedRow!=startRow){
	                    		Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue(startRow);
		                		var protocolTreeGridPanelSelectRow= Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").getValue();
		                		var protocolCode="";
		                		if(protocolTreeGridPanelSelectRow!=''){
		                			var selectedProtocol=Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().getAt(protocolTreeGridPanelSelectRow);
		                    		if(selectedProtocol.data.classes==1){//选中的是协议
		                    			protocolCode=selectedProtocol.data.code;
		                    		}
		                    		var itemAddr=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(startRow,'addr');
		                    		var resolutionMode=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(startRow,'resolutionMode');
		                    		CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode,itemAddr,resolutionMode,true);
		                		}
	                    	}else{
	                    		Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue(startRow);
	                    	}
	                	}
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && protocolItemsConfigHandsontableHelper!=null&&protocolItemsConfigHandsontableHelper.hot!=''&&protocolItemsConfigHandsontableHelper.hot!=undefined && protocolItemsConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolItemsConfigHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolItemsConfigHandsontableHelper.saveData = function () {}
	        protocolItemsConfigHandsontableHelper.clearContainer = function () {
	        	protocolItemsConfigHandsontableHelper.AllData = [];
	        }
	        return protocolItemsConfigHandsontableHelper;
	    }
};


function CreateProtocolConfigAddrMappingPropertiesInfoTable(data){
	var root=[];
	if(data.classes==0){
		var item1={};
		item1.id=1;
		item1.title=loginUserLanguageResource.rootNode;
		item1.value=loginUserLanguageResource.protocolList;
		root.push(item1);
	}else if(data.classes==1){
		var item1={};
		item1.id=1;
		item1.title=loginUserLanguageResource.protocolName;
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title=loginUserLanguageResource.sortNum;
		item2.value=data.sort;
		root.push(item2);
		
		var item3={};
		item3.id=3;
		item3.title=loginUserLanguageResource.language;
		item3.value=data.languageName;
		root.push(item3);
		
		var item4={};
		item4.id=4;
		item4.title=loginUserLanguageResource.protocolBelongTo;
		item4.value=data.deviceTypeAllPath;
		root.push(item4);
		
		var tabPanel = Ext.getCmp("ProtocolConfigRightTabPanel_Id");
		var showInfo=tabPanel.getActiveTab().title;
		if(isNotVal(item1.title)){
			showInfo="【<font color=red>"+item1.title+"</font>】"+showInfo+"&nbsp;"
		}
		Ext.getCmp("ProtocolConfigInformationLabel_Id").setHtml(showInfo);
	    Ext.getCmp("ProtocolConfigInformationLabel_Id").show();
	}
	
	
	
	if(protocolPropertiesHandsontableHelper==null || protocolPropertiesHandsontableHelper.hot==undefined){
		protocolPropertiesHandsontableHelper = ProtocolPropertiesHandsontableHelper.createNew("ModbusProtocolAddrMappingPropertiesTableInfoDiv_id");
		var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
		var columns="[{data:'id'}," 
			+"{data:'title'}," 
			+"{data:'value'}]";
		protocolPropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolPropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolPropertiesHandsontableHelper.classes=data.classes;
		protocolPropertiesHandsontableHelper.createTable(root);
	}else{
		protocolPropertiesHandsontableHelper.classes=data.classes;
		protocolPropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ProtocolPropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolPropertiesHandsontableHelper = {};
	        protocolPropertiesHandsontableHelper.hot = '';
	        protocolPropertiesHandsontableHelper.classes =null;
	        protocolPropertiesHandsontableHelper.divid = divid;
	        protocolPropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolPropertiesHandsontableHelper.colHeaders=[];
	        protocolPropertiesHandsontableHelper.columns=[];
	        protocolPropertiesHandsontableHelper.AllData=[];
	        
	        protocolPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolPropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolPropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolPropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolPropertiesHandsontableHelper.divid);
	        	protocolPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [1,8,10],
	                columns:protocolPropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolPropertiesHandsontableHelper.colHeaders,//显示列头
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
	                    	if(protocolPropertiesHandsontableHelper.classes===0){
								cellProperties.readOnly = true;
								cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
		                    }else if(protocolPropertiesHandsontableHelper.classes===1){
		                    	if (visualColIndex ==0 || visualColIndex ==1) {
									cellProperties.readOnly = true;
									cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
				                }else if(visualColIndex === 2 && visualRowIndex===0){
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolPropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolPropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && visualRowIndex===1) {
			                    	this.validator=function (val, callback) {
			                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolPropertiesHandsontableHelper);
			                    	}
			                    	cellProperties.renderer = protocolPropertiesHandsontableHelper.addCellStyle;
			                    }else if (visualColIndex === 2 && (visualRowIndex===2||visualRowIndex===3) ) {
			                    	cellProperties.readOnly = true;
			                    	cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
			                    }
		                    }
	                    }else{
							cellProperties.readOnly = true;
							cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && protocolPropertiesHandsontableHelper!=null&&protocolPropertiesHandsontableHelper.hot!=''&&protocolPropertiesHandsontableHelper.hot!=undefined && protocolPropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolPropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(rawValue!="" && isNotVal(rawValue)){
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
	        protocolPropertiesHandsontableHelper.saveData = function () {}
	        protocolPropertiesHandsontableHelper.clearContainer = function () {
	        	protocolPropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolPropertiesHandsontableHelper;
	    }
};

function SaveModbusProtocolAddrMappingConfigTreeData(){
	var protocolTreeGridPanelSelectRow= Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").getValue();
	var AddrMappingItemsSelectRow= Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").getValue();
	if(protocolTreeGridPanelSelectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().getAt(protocolTreeGridPanelSelectRow);
		if(selectedItem.data.classes==1){
			var saveType=0;
			if(Ext.getCmp("ProtocolConfigRightTabPanel_Id").getActiveTab().id=='ProtocolPropertiesConfigRightTabPanel_Id'){
				saveType=0;
			}else if(Ext.getCmp("ProtocolConfigRightTabPanel_Id").getActiveTab().id=='ProtocolContentConfigRightTabPanel_Id'){
				saveType=1;
			}else if(Ext.getCmp("ProtocolConfigRightTabPanel_Id").getActiveTab().id=='ProtocolExtendedFieldConfigRightTabPanel_Id'){
				saveType=2;
			}
			
			var protocolConfigData=selectedItem.data;
			if(saveType==0 && protocolPropertiesHandsontableHelper!=null && protocolPropertiesHandsontableHelper.hot!=null){
				var propertiesData=protocolPropertiesHandsontableHelper.hot.getData();
				protocolConfigData.text=isNotVal(propertiesData[0][2])?propertiesData[0][2]:"";
				protocolConfigData.sort=isNotVal(propertiesData[1][2])?propertiesData[1][2]:"";
			}
			
			if(isNotVal(protocolConfigData.text)){
				var configInfo={};
				configInfo.ProtocolName=protocolConfigData.text;
				configInfo.ProtocolCode=protocolConfigData.code;
				configInfo.DeviceType=protocolConfigData.deviceType;
				configInfo.Sort=protocolConfigData.sort;
				configInfo.DataConfig=[];
				configInfo.ExtendedFieldConfig=[];
				if(saveType==1){
					if(protocolItemsConfigHandsontableHelper!=null && protocolItemsConfigHandsontableHelper.hot!=null){
						var driverConfigItemsData=protocolItemsConfigHandsontableHelper.hot.getData();
						for(var i=0;i<driverConfigItemsData.length;i++){
							if(isNotVal(driverConfigItemsData[i][1])){
								var item={};
								
								item.Title=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'title');
								item.Addr=parseInt(protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'addr'));
								item.StoreDataType=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'storeDataType');
								item.Quantity=parseInt(protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'quantity'));
								item.RWType=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'RWType');
								item.AcqMode=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'acqMode');
								item.IFDataType=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'IFDataType');
								
								var Prec=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'prec')+"";
								Prec=Prec!=''?Prec.replace(/\s/g, ""):Prec;
								item.Prec=(item.IFDataType!=null && item.IFDataType.toLowerCase().indexOf('float')>=0)?(isNumber(parseFloat(Prec))?parseFloat(Prec):0):0;
								
								item.Ratio=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'ratio');
								
								var Unit=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'unit')+"";
								Unit=Unit!=''?Unit.replace(/\s/g, ""):Unit;
								item.Unit=Unit;
								
								item.ResolutionMode=protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i,'resolutionMode');
								if(i==AddrMappingItemsSelectRow && protocolItemsMeaningConfigHandsontableHelper!=null && protocolItemsMeaningConfigHandsontableHelper.hot!=null){
									item.Meaning=[];
									var itemsMeaningData=protocolItemsMeaningConfigHandsontableHelper.hot.getData();
									for(var j=0;j<itemsMeaningData.length;j++){
										if(isNotVal(itemsMeaningData[j][0]) && isNotVal(itemsMeaningData[j][1])){
											var meaning={};
											meaning.Value=itemsMeaningData[j][0];
											meaning.Meaning=itemsMeaningData[j][1];
											item.Meaning.push(meaning);
										}
									}
								}
								configInfo.DataConfig.push(item);
							}
						}
					}
				}else if(saveType==2){
					if(protocolExtendedFieldConfigHandsontableHelper!=null && protocolExtendedFieldConfigHandsontableHelper.hot!=null){
						var extendedFieldData=protocolExtendedFieldConfigHandsontableHelper.hot.getData();
						
						for(var i=0;i<extendedFieldData.length;i++){
							var title=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i,'title');
							if(isNotVal(title)){
								var extendedField={};
								extendedField.Title=title;
								extendedField.Title1=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i,'title1');
								if(extendedField.Title1==(loginUserLanguageResource.doubleClickCellTip+'...')){
									extendedField.Title1='';
								}
								extendedField.Operation=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i,'operation');
								extendedField.Title2=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i,'title2');
								if(extendedField.Title2==(loginUserLanguageResource.doubleClickCellTip+'...')){
									extendedField.Title2='';
								}
								extendedField.Prec=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i,'prec');
								
								var Prec=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i,'prec')+"";
								Prec=Prec!=''?Prec.replace(/\s/g, ""):Prec;
								extendedField.Prec=isNumber(Prec)?parseFloat(Prec):0;
								
								extendedField.Ratio=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i,'ratio');
								
								var Unit=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i,'unit')+"";
								Unit=Unit!=''?Unit.replace(/\s/g, ""):Unit;
								extendedField.Unit=Unit;
								extendedField.AdditionalConditions=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i,'additionalConditions');
								
								configInfo.ExtendedFieldConfig.push(extendedField);
							}
						}
					}
				}
				

				var duplicateRowList=protocolItemsConfigHandsontableHelper.getDuplicateRowList();
				var addrDuplicateRowList=protocolItemsConfigHandsontableHelper.getAddrDuplicateRowList();
				if(duplicateRowList.length>0 || addrDuplicateRowList.length>0){
					Ext.MessageBox.confirm({
			            title: loginUserLanguageResource.confirm,
			            message: loginUserLanguageResource.protocolSaveConfirm,
			            buttons: Ext.MessageBox.YESNO,
			            icon: Ext.MessageBox.QUESTION,
			            callback: (btnId) => {
			                if (btnId === 'yes') {
			                	saveModbusProtocolAddrMappingConfigData(configInfo,saveType);
			                }
			            }
			        });
				}else{
					saveModbusProtocolAddrMappingConfigData(configInfo,saveType);
				}
				
			}else{
				Ext.MessageBox.alert(loginUserLanguageResource.tip,loginUserLanguageResource.protocolName+','+loginUserLanguageResource.canNotBeEmpty+"!");
			}
		}
	}
};

function saveModbusProtocolAddrMappingConfigData(configInfo,saveType){
	Ext.getCmp("modbusProtocolConfigInfoViewId").el.mask(loginUserLanguageResource.updateWait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/saveModbusProtocolAddrMappingConfigData',
		success:function(response) {
			var data=Ext.JSON.decode(response.responseText);
			protocolItemsConfigHandsontableHelper.clearContainer();
			if (data.success) {
				Ext.getCmp("modbusProtocolConfigInfoViewId").getEl().unmask();
				if(configInfo.delidslist!=undefined && configInfo.delidslist.length>0){
					Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.deleteSuccessfully);
				}else{
					Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.saveSuccessfully);
				}
				
				
				if(configInfo.delidslist!=undefined && configInfo.delidslist.length>0){//如果删除
					Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").setValue(0);
            		Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue(0);
            		Ext.getCmp("ModbusProtocolAddrMappingConfigSelectProtocolId_Id").setValue(0);
				}
				
            	Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().load();
            } else {
            	Ext.getCmp("modbusProtocolConfigInfoViewId").getEl().unmask();
            	Ext.MessageBox.alert(loginUserLanguageResource.message,"<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
            }
		},
		failure:function(){
			Ext.getCmp("modbusProtocolConfigInfoViewId").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.requestFailure);
		},
		params: {
			data:JSON.stringify(configInfo),
			saveType:saveType
        }
	});
}

function CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode,itemAddr,resolutionMode,isNew){
	if(isNew&&protocolItemsMeaningConfigHandsontableHelper!=null){
		if(protocolItemsMeaningConfigHandsontableHelper.hot!=undefined){
			protocolItemsMeaningConfigHandsontableHelper.hot.destroy();
		}
		protocolItemsMeaningConfigHandsontableHelper=null;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolItemMeaningConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id").setTitle(result.itemTitle+"含义");
			if(result.itemResolutionMode==2){
				Ext.getCmp("ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id").setTitle("");
			}
			
			
			var dataLength=result.totalRoot.length;
			var defultNullData=[];
			var defultDataLength=500;
			for(var i=0;i<defultDataLength;i++){
				var nullData={};
				defultNullData.push(nullData);
			}
			
			if(dataLength<defultDataLength){
				if(defultDataLength-dataLength<10){
					for(var i=0;i<10;i++){
						var nullData={};
						result.totalRoot.push(nullData);
					}
				}else{
					for(var i=dataLength;i<defultDataLength;i++){
						var nullData={};
						result.totalRoot.push(nullData);
					}
				}
			}else{
				for(var i=0;i<10;i++){
					var nullData={};
					result.totalRoot.push(nullData);
				}
			}
			
			
			if(protocolItemsMeaningConfigHandsontableHelper==null || protocolItemsMeaningConfigHandsontableHelper.hot==undefined){
				protocolItemsMeaningConfigHandsontableHelper = ProtocolItemsMeaningConfigHandsontableHelper.createNew("ModbusProtocolAddrMappingItemsMeaningTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.value+"','"+loginUserLanguageResource.meaning+"']";
//				if(result.itemResolutionMode==0){
//					colHeaders="['"+loginUserLanguageResource.bit+"','"+loginUserLanguageResource.meaning+"']";
//				}
				
				if(resolutionMode==loginUserLanguageResource.switchingValue){
					colHeaders="['"+loginUserLanguageResource.bit+"','"+loginUserLanguageResource.meaning+"']";
				}
				var columns="[{data:'value',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolItemsMeaningConfigHandsontableHelper);}},"
						+"{data:'meaning'}" 
						+"]";
				
				protocolItemsMeaningConfigHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolItemsMeaningConfigHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(dataLength==0){
					protocolItemsMeaningConfigHandsontableHelper.createTable(defultNullData);
				}else{
					protocolItemsMeaningConfigHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(dataLength==0){
					protocolItemsMeaningConfigHandsontableHelper.hot.loadData(defultNullData);
				}else{
					protocolItemsMeaningConfigHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolCode:protocolCode,
			itemAddr:itemAddr
        }
	});
};

var ProtocolItemsMeaningConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolItemsMeaningConfigHandsontableHelper = {};
	        protocolItemsMeaningConfigHandsontableHelper.hot1 = '';
	        protocolItemsMeaningConfigHandsontableHelper.divid = divid;
	        protocolItemsMeaningConfigHandsontableHelper.validresult=true;//数据校验
	        protocolItemsMeaningConfigHandsontableHelper.colHeaders=[];
	        protocolItemsMeaningConfigHandsontableHelper.columns=[];
	        protocolItemsMeaningConfigHandsontableHelper.AllData=[];
	        
	        protocolItemsMeaningConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolItemsMeaningConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolItemsMeaningConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolItemsMeaningConfigHandsontableHelper.divid);
	        	protocolItemsMeaningConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [1,3],
	                columns:protocolItemsMeaningConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolItemsMeaningConfigHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                contextMenu: {
	                    items: {
	                        "row_above": {
	                            name: loginUserLanguageResource.contextMenu_insertRowAbove,
	                        },
	                        "row_below": {
	                            name: loginUserLanguageResource.contextMenu_insertRowBelow,
	                        },
	                        "col_left": {
	                            name: loginUserLanguageResource.contextMenu_insertColumnLeft,
	                        },
	                        "col_right": {
	                            name: loginUserLanguageResource.contextMenu_insertColumnRight,
	                        },
	                        "remove_row": {
	                            name: loginUserLanguageResource.contextMenu_removeRow,
	                        },
	                        "remove_col": {
	                            name: loginUserLanguageResource.contextMenu_removeColumn,
	                        },
	                        "merge_cell": {
	                            name: loginUserLanguageResource.contextMenu_mergeCell,
	                        },
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy,
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut,
	                        }
//	                        ,
//	                        "paste": {
//	                            name: loginUserLanguageResource.contextMenu_paste,
//	                            disabled: function () {
//	                            },
//	                            callback: function () {
//	                            }
//	                        }
	                    }
	                },//右键菜单展示
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag!=1){
	                    	cellProperties.readOnly = true;
	                    }
	                    cellProperties.renderer = protocolItemsMeaningConfigHandsontableHelper.addCellStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && protocolItemsMeaningConfigHandsontableHelper!=null&&protocolItemsMeaningConfigHandsontableHelper.hot!=''&&protocolItemsMeaningConfigHandsontableHelper.hot!=undefined && protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolItemsMeaningConfigHandsontableHelper.saveData = function () {}
	        protocolItemsMeaningConfigHandsontableHelper.clearContainer = function () {
	        	protocolItemsMeaningConfigHandsontableHelper.AllData = [];
	        }
	        return protocolItemsMeaningConfigHandsontableHelper;
	    }
};

function CreateProtocolExtendedFieldConfigInfoTable(protocolName,classes,code){
	if(protocolExtendedFieldConfigHandsontableHelper!=null){
		if(protocolExtendedFieldConfigHandsontableHelper.hot!=undefined){
			protocolExtendedFieldConfigHandsontableHelper.hot.destroy();
		}
		protocolExtendedFieldConfigHandsontableHelper=null;
	}
	
	var tabPanel = Ext.getCmp("ProtocolConfigRightTabPanel_Id");
	var showInfo=tabPanel.getActiveTab().title;
	if(isNotVal(protocolName)){
		showInfo="【<font color=red>"+protocolName+"</font>】"+showInfo+"&nbsp;"
	}
	Ext.getCmp("ProtocolConfigInformationLabel_Id").setHtml(showInfo);
    Ext.getCmp("ProtocolConfigInformationLabel_Id").show();
    
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolExtendedFieldsConfigData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			var dataLength=result.totalRoot.length;
			var defultNullData=[];
			var defultDataLength=500;
			for(var i=0;i<defultDataLength;i++){
				var nullData={};
				defultNullData.push(nullData);
			}
			
			if(dataLength<defultDataLength){
				for(var i=dataLength;i<defultDataLength;i++){
					var nullData={};
					result.totalRoot.push(nullData);
				}
			}
			
			
			if(protocolExtendedFieldConfigHandsontableHelper==null || protocolExtendedFieldConfigHandsontableHelper.hot==undefined){
				protocolExtendedFieldConfigHandsontableHelper = ProtocolExtendedFieldConfigHandsontableHelper.createNew("ProtocolExtendedFieldTableInfoDiv_id");
				var operationList=result.operationList;
				var additionalConditionsList=result.additionalConditionsList;
				var colHeaders=[
					loginUserLanguageResource.idx,
					loginUserLanguageResource.name,
					loginUserLanguageResource.dataColumn+'1',
					loginUserLanguageResource.fourOperation,
					loginUserLanguageResource.dataColumn+'2',
					loginUserLanguageResource.prec,
					loginUserLanguageResource.ratio,
					loginUserLanguageResource.unit,
					loginUserLanguageResource.additionalConditions
					];
				
				var columns=[{
				    data: 'id'
				}, {
				    data: 'title'
				}, {
				    data: 'title1',
				    renderer: protocolExtendedFieldConfigHandsontableHelper.placeholderRenderer
				}, {
				    data: 'operation',
				    type: 'dropdown',
				    strict: true,
				    allowInvalid: false,
				    source: operationList
				}, {
				    data: 'title2',
				    renderer: protocolExtendedFieldConfigHandsontableHelper.placeholderRenderer
				}, {
				    data: 'prec',
				    type: 'text',
				    allowInvalid: true,
				    validator: function (val, callback) {
				        return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldConfigHandsontableHelper);
				    }
				}, {
					data: 'ratio',
				    type: 'text',
				    allowInvalid: true,
				    validator: function (val, callback) {
				        return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldConfigHandsontableHelper);
				    }
				}, {
				    data: 'unit'
				}, {
				    data: 'additionalConditions',
				    type: 'dropdown',
				    strict: true,
				    allowInvalid: false,
				    source: additionalConditionsList
				}];
				
				protocolExtendedFieldConfigHandsontableHelper.colHeaders=colHeaders;
				protocolExtendedFieldConfigHandsontableHelper.columns=columns;
				if(dataLength==0){
					protocolExtendedFieldConfigHandsontableHelper.createTable(defultNullData);
				}else{
					protocolExtendedFieldConfigHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(dataLength==0){
					protocolExtendedFieldConfigHandsontableHelper.hot.loadData(defultNullData);
				}else{
					protocolExtendedFieldConfigHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ProtocolExtendedFieldConfigHandsontableHelper = {
		createNew: function (divid) {
	        var protocolExtendedFieldConfigHandsontableHelper = {};
	        protocolExtendedFieldConfigHandsontableHelper.hot1 = '';
	        protocolExtendedFieldConfigHandsontableHelper.divid = divid;
	        protocolExtendedFieldConfigHandsontableHelper.validresult=true;//数据校验
	        protocolExtendedFieldConfigHandsontableHelper.colHeaders=[];
	        protocolExtendedFieldConfigHandsontableHelper.columns=[];
	        protocolExtendedFieldConfigHandsontableHelper.AllData=[];
	        
	        protocolExtendedFieldConfigHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolExtendedFieldConfigHandsontableHelper.placeholderRenderer = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (value === null || value === '') {
	                td.style.color = 'gray'; // 设置灰色文本作为占位符样式
	                td.style.fontStyle = 'italic'; // 可以添加其他样式如斜体
	                td.innerHTML = loginUserLanguageResource.doubleClickCellTip+'...'; // 显示占位符文本
	            }
	        }
	        
	        protocolExtendedFieldConfigHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolExtendedFieldConfigHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolExtendedFieldConfigHandsontableHelper.divid);
	        	protocolExtendedFieldConfigHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [0],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [50,200,200,80,200,80,80,80,150],
	                columns:protocolExtendedFieldConfigHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:protocolExtendedFieldConfigHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                contextMenu: {
	                    items: {
	                        "row_above": {
	                            name: loginUserLanguageResource.contextMenu_insertRowAbove,
	                        },
	                        "row_below": {
	                            name: loginUserLanguageResource.contextMenu_insertRowBelow,
	                        },
	                        "col_left": {
	                            name: loginUserLanguageResource.contextMenu_insertColumnLeft,
	                        },
	                        "col_right": {
	                            name: loginUserLanguageResource.contextMenu_insertColumnRight,
	                        },
	                        "remove_row": {
	                            name: loginUserLanguageResource.contextMenu_removeRow,
	                        },
	                        "remove_col": {
	                            name: loginUserLanguageResource.contextMenu_removeColumn,
	                        },
	                        "merge_cell": {
	                            name: loginUserLanguageResource.contextMenu_mergeCell,
	                        },
	                        "copy": {
	                            name: loginUserLanguageResource.contextMenu_copy,
	                        },
	                        "cut": {
	                            name: loginUserLanguageResource.contextMenu_cut,
	                        }
//	                        ,
//	                        "paste": {
//	                            name: loginUserLanguageResource.contextMenu_paste,
//	                            disabled: function () {
//	                            },
//	                            callback: function () {
//	                            }
//	                        }
	                    }
	                },//右键菜单展示
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag!=1){
	                    	cellProperties.readOnly = true;
	                    }
	                    return cellProperties;
	                },
	                afterBeginEditing: function (row, column) {
	                	var cellMeta = protocolExtendedFieldConfigHandsontableHelper.hot.getCellMeta(row, column);
	                	var selectedItemName=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtCell(row, column);
	                	if(cellMeta.prop.toUpperCase()=='title1'.toUpperCase() || cellMeta.prop.toUpperCase()=='title2'.toUpperCase()){
	                		var record= Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getSelectionModel().getSelection()[0];//record.data.text,record.data.classes,record.data.code
	                		if(record.data.classes==1){
	                			var ProtocolExtendedFieldSelectWindow = Ext.create("AP.view.acquisitionUnit.ProtocolExtendedFieldSelectWindow");
	                			ProtocolExtendedFieldSelectWindow.show();
	                			Ext.getCmp("protocolExtendedFieldSelectedRow_Id").setValue(row);
                                Ext.getCmp("protocolExtendedFieldSelectedCol_Id").setValue(column);
                                Ext.getCmp("protocolExtendedFieldSelectedItemName_Id").setValue(selectedItemName);
	                		}
	                	}
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 && protocolExtendedFieldConfigHandsontableHelper!=null&&protocolExtendedFieldConfigHandsontableHelper.hot!=''&&protocolExtendedFieldConfigHandsontableHelper.hot!=undefined && protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolExtendedFieldConfigHandsontableHelper.saveData = function () {}
	        protocolExtendedFieldConfigHandsontableHelper.clearContainer = function () {
	        	protocolExtendedFieldConfigHandsontableHelper.AllData = [];
	        }
	        return protocolExtendedFieldConfigHandsontableHelper;
	    }
};
