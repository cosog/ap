Ext.define('AP.store.PSToFS.DiagramDataListStore', {
    extend: 'Ext.data.Store',
    fields: ['jlbh','gtcjsj'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/PSToFSController/getDiagramDataList',
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
        load: function (store, sEops) {
        	var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createElectricAcqAndInverColumn(arrColumns);
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
	        });
            var gridPanel = Ext.getCmp("ElectricAcqAndInverDiagramListGrid_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'ElectricAcqAndInverDiagramListGrid_Id',
                    bbar: bbar,
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    store:store,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    columns:newColumns,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length > 0) {
                            	var jlbh=selected[0].data.jlbh;
                            	Ext.Ajax.request({
                            		method:'POST',
                            		url:context + '/PSToFSController/getSingleInverDiagramData',
                            		success:function(response) {
                            			var result =  Ext.JSON.decode(response.responseText);
                            			showPContinuousDiagram(result,"inverDiagramDiv1_id");
                            			showAContinuousDiagram(result,"inverDiagramDiv2_id");
                            			
                            			var positionCurveData=result.positionCurveData.split(",");
                            			if(result.positionCurveData!=undefined && result.positionCurveData!="" && result.positionCurveData.split(",").length>0){
                            				showFSDiagram(result,"inverDiagramDiv3_id");
                                			showPSDiagram(result,"inverDiagramDiv4_id");
                                			showASDiagram(result,"inverDiagramDiv5_id");
                            			}else{
                                        	$("#inverDiagramDiv3_id").html('');
                                        	$("#inverDiagramDiv4_id").html('');
                                        	$("#inverDiagramDiv5_id").html('');
                                        }
                            		},
                            		failure:function(){
                            			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
                            		},
                            		params: {
                            			jlbh: jlbh
                                    }
                            	});
                            }
                        }
                    }
                })
            	Ext.getCmp("ElectricAcqAndInverDiagramDataPanel").add(gridPanel);
            }
            if(get_rawData.totalCount>0){
                if (isNotVal(gridPanel)) {
                	gridPanel.getSelectionModel().deselectAll(true);
                	gridPanel.getSelectionModel().select(0, true);
                }
            }else{
            	$("#inverDiagramDiv1_id").html('');
            	$("#inverDiagramDiv2_id").html('');
            	$("#inverDiagramDiv3_id").html('');
            	$("#inverDiagramDiv4_id").html('');
            	$("#inverDiagramDiv5_id").html('');
            }
        },
        beforeload: function (store, options) {
            var wellName = Ext.getCmp("ElectricAcqAndInverInfo_Id").getSelectionModel().getSelection()[0].data.wellName;
            var startDate=Ext.getCmp('PSToFSAcqAndInversionStartDate_Id').rawValue;
            var endDate=Ext.getCmp('PSToFSAcqAndInversionEndDate_Id').rawValue;
            var new_params = {
                wellName: wellName,
                startDate:startDate,
                endDate:endDate,
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});