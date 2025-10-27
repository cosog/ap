Ext.define('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitSwitchItemsStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.modbusProtocolAlarmUnitSwitchItemsStore',
    fields: ['id','title','code','itemAddr'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getProtocolEnumOrSwitchItemsConfigData',
        actionMethods: {
            read: 'POST'
        },
    reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, record, f, op, o) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var gridPanel = Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var column = createModbusProtocolAddrMappingEnumOrSwitchItemsColumn(arrColumns);
                var newColumns = Ext.JSON.decode(column);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ModbusProtocolAlarmUnitSwitchItemsGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		if(selected.length>0){
                    			
                    		}
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsSelectRow_Id").setValue(index);
                    		var selectGroupRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
                    		var selectedGroup=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectGroupRow);
                    		
                    		if(selectedGroup.data.classes==0){
                    			if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper!=null){
                					if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined){
                						protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.destroy();
                					}
                					protocolAlarmUnitConfigSwitchItemsHandsontableHelper=null;
                				}
                    		}else if(selectedGroup.data.classes==1){
                    			CreateProtocolAlarmUnitSwitchItemsConfigInfoTable(selectedGroup.data.code,selectedGroup.data.classes,selectedGroup.data.code,record.data.addr);
                        	}else if(selectedGroup.data.classes==2||selectedGroup.data.classes==3){
                        		CreateProtocolAlarmUnitSwitchItemsConfigInfoTable(selectedGroup.data.protocol,selectedGroup.data.classes,selectedGroup.data.code,record.data.addr);
                        	}
                    	}
                    }
                });
                var panel = Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsPanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalRoot.length>0){
            	gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(0, true);
            }else{
            	if(isNotVal(get_rawData.protocolCode)){
            		if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper!=null && protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined){
                		protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.loadData([]);
                	}
            	}else{
            		if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper!=null){
    					if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmUnitConfigSwitchItemsHandsontableHelper=null;
    				}
            	}
            }
        },
        beforeload: function (store, options) {
        	var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
        	var protocolCode="";
        	if(ScadaDriverModbusConfigSelectRow!=''){
        		var selectedProtocol=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
        		if(selectedProtocol.data.classes==1){//选中的是协议
        			protocolCode=selectedProtocol.data.code;
        		}else if(selectedProtocol.data.classes==0){
        			protocolCode='';
        		}else if(selectedProtocol.data.classes==2){
        			protocolCode=selectedProtocol.data.protocolCode;
            	}else if(selectedProtocol.data.classes==3){
        			protocolCode=selectedProtocol.data.protocolCode;
            	}
        	}
            var new_params = {
            		protocolCode: protocolCode,
            		resolutionMode: 0
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});