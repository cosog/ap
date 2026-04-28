Ext.define('AP.store.acquisitionUnit.CurveGroupRealtimeInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.curveGroupRealtimeInfoStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getCurveGroupData',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: defaultPageSize,
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var gridPanel = Ext.getCmp("realtimeCurveGroupConfigGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "realtimeCurveGroupConfigGridPanel_Id",
                    selModel: 'cellmodel',//cellmodel rowmodel
                    plugins: [{
                        ptype: 'cellediting',//cellediting rowediting
                        clicksToEdit: 2
                    }],
                    border: false,
                    stateful: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
//                    selModel:{
//                    	selType: '',
//                    	mode:'SINGLE',//"SINGLE" / "SIMPLE" / "MULTI" 
//                    	checkOnly:false,
//                    	allowDeselect:false
//                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.idx,loginUserLanguage)+'px',
                        xtype: 'rownumberer'
                    }, {
                        header: loginUserLanguageResource.name,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'name',
                        flex:2,
                        editor: loginUserProtocolConfigModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserProtocolConfigModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value, o, p, e) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    },{
                        header: loginUserLanguageResource.sortNum,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'sort',
                        editor: loginUserProtocolConfigModuleRight.editFlag==1?{
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: false,
                            disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                            minValue: 1
                        }:""
                    },{
                    	header: 'groupId',
                    	hidden: true,
                    	dataIndex: 'groupId'
                    }],
                    listeners: {
                    	celldblclick : function( grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                    		
                    	},select( v, record, index, eOpts ){
                    		
                    	}
                    }
                });
                var panel = Ext.getCmp("realtimeCurveGroupConfigPanel_Id");
                if(isNotVal(panel)){
                	panel.add(gridPanel);
                }
            }
        },
        beforeload: function (store, options) {
            var new_params = {
            		type:1
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});