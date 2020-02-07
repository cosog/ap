Ext.define('AP.store.graphicalUpload.SurfaceCardTypeListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.surfaceCardTypeListStore',
    model: 'AP.model.graphicalUpload.SurfaceCardTypeListmodel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/graphicalUploadController/getSurfaceCardTypeList',
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
            var SurfaceCardTypeListGrid = Ext.getCmp("SurfaceCardTypeListGrid_Id");
            if (!isNotVal(SurfaceCardTypeListGrid)) {
                var column = createDiagStatisticsColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                SurfaceCardTypeListGrid = Ext.create('Ext.grid.Panel', {
                    id: "SurfaceCardTypeListGrid_Id",
                    border: false,
                    autoLoad: true,
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	itemclick:function( view , record , item , index , e , eOpts ) {
                    		Ext.getCmp("SelectSurfaceCardFilePanel_Id").removeAll();
                    		$("#SurfaceCardUploadShowDiv_Id").html('');
                    		Ext.getCmp("SelectSurfaceCardFilePanelFormId").destroy();
                    		var fieldLabel='功图文件';
                    		var labelWidth=70;
                    		if(record.data.gtlx==101){
                    			fieldLabel='功图文件(<font color=red>建议一次上传文件不超过500个 </font>)';
                    			labelWidth=230;
                    		}
                    		if(record.data.gtlx==121){
                    			Ext.getCmp("loadFSdiagramModelBtn_Id").show();
                    		}else{
                    			Ext.getCmp("loadFSdiagramModelBtn_Id").hide();
                    		}
                    		var SelectSurfaceCardFilePanelFormId=Ext.create('Ext.form.Panel',{
                        		xtype:'form',
                        		id:'SelectSurfaceCardFilePanelFormId',
                        	    width: "100%",
                        	    bodyPadding: 10,
                        	    frame: true,
                        	    items: [{
                        	    	xtype: 'filefield',
                                	id:'SurfaceCardFilefieldId',
                                    name: 'file',
                                    fieldLabel: fieldLabel,
                                    labelWidth: labelWidth,
                                    width:'100%',
                                    msgTarget: 'side',
                                    allowBlank: false,
                                    anchor: '100%',
                                    draggable:true,
                                    buttonText: '请选择功图文件',
                                    accept:record.data.filetype,
                                    listeners:{
                                        afterrender:function(cmp){
                                            cmp.fileInputEl.set({
                                                multiple:'multiple'
                                            });
                                        },
                                        change:function(cmp){
                                        	submitSurfaceCardFile();
                                        }
                                    }
                        	    }]
                    		});
                    		Ext.getCmp("SelectSurfaceCardFilePanelToolbarId").add(SelectSurfaceCardFilePanelFormId);
                    	}
                    }
                });
                var SurfaceCardTypeListPanel = Ext.getCmp("SurfaceCardTypeListPanel_Id");
                SurfaceCardTypeListPanel.add(SurfaceCardTypeListGrid);
            }
            SurfaceCardTypeListGrid.getSelectionModel().select(0,true);//选中第一行
        },
        beforeload: function (store, options) {
        	
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});