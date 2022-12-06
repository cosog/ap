Ext.define('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitSwitchItemsStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.modbusProtocolAlarmUnitSwitchItemsStore',
    fields: ['id','title','code','itemAddr'],
    autoLoad: true,
    pageSize: 10000,
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
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
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
                    			if(isNotVal(selectedGroup.data.children) && selectedGroup.data.children.length>0){
                    				CreateProtocolAlarmUnitSwitchItemsConfigInfoTable(selectedGroup.data.children[0].text,selectedGroup.data.children[0].classes,selectedGroup.data.children[0].code,record.data.addr);
                    			}
                    			
                    		}else if(selectedGroup.data.classes==1){
                    			CreateProtocolAlarmUnitSwitchItemsConfigInfoTable(selectedGroup.data.text,selectedGroup.data.classes,selectedGroup.data.code,record.data.addr);
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
            	if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper!=null && protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined){
            		protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.loadData([]);
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
        		}else if(selectedProtocol.data.classes==0 && isNotVal(selectedProtocol.data.children) && selectedProtocol.data.children.length>0){
        			protocolCode=selectedProtocol.data.children[0].code;
        		}else if(selectedProtocol.data.classes==2||selectedProtocol.data.classes==3){
        			protocolCode=selectedProtocol.parentNode.data.code;
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