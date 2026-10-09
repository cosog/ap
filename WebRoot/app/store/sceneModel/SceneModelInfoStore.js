Ext.define('AP.store.sceneModel.SceneModelInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.dceneModelInfoStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/sceneModelController/getSceneModelList',
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
            var showChineseName=get_rawData.showChineseName;
            var showEnglishName=get_rawData.showEnglishName;
            var showRussianName=get_rawData.showRussianName;
            var gridPanel = Ext.getCmp("SceneModelGridView_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "SceneModelGridView_Id",
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
                    selModel:{
                    	selType: (loginUserSceneModelRight.editFlag==1?'checkboxmodel':''),
                    	mode:'SINGLE',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly:false,
                    	allowDeselect:false
                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'>" + Ext.String.htmlEncode("<" + loginUserLanguageResource.emptyMsg + ">") + "</div>",
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
                        header: loginUserLanguageResource.language_zh_CN,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'name_zh_CN',
                        hidden:!showChineseName,
                        flex:2,
                        editor: loginUserSceneModelRight.editFlag==1?{
                            allowBlank: (loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                            disabled:loginUserSceneModelRight.editFlag!=1
                        }:"",
                        renderer: function (value, o, p, e) {
                        	if(isNotVal(value)){
                        		return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
                        	}
                        }
                    }, {
                        header: loginUserLanguageResource.language_en,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'name_en',
                        hidden:!showEnglishName,
                        flex:2,
                        editor: loginUserSceneModelRight.editFlag==1?{
                            allowBlank: (loginUserLanguage.toUpperCase()=='EN'?false:true),
                            disabled:loginUserSceneModelRight.editFlag!=1
                        }:"",
                        renderer: function (value, o, p, e) {
                        	if(isNotVal(value)){
                        		return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
                        	}
                        }
                    }, {
                        header: loginUserLanguageResource.language_ru,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'name_ru',
                        hidden:!showRussianName,
                        flex:2,
                        editor: loginUserSceneModelRight.editFlag==1?{
                            allowBlank: (loginUserLanguage.toUpperCase()=='RU'?false:true),
                            disabled:loginUserSceneModelRight.editFlag!=1
                        }:"",
                        renderer: function (value, o, p, e) {
                        	if(isNotVal(value)){
                        		return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
                        	}
                        }
                    }, {
                        header: loginUserLanguageResource.sequenceNumber,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'sort',
                        editor: loginUserSceneModelRight.editFlag==1?{
                            allowBlank: true,
                            xtype: 'numberfield',
                            editable: true,
                            disabled:loginUserSceneModelRight.editFlag!=1,
                            minValue: 1
                        }:""
                    }],
                    listeners: {
                    	selectionchange: function (sm, selected) {
                    		if(selected.length>0){
                    			var configPanel = Ext.getCmp('SceneModelConfigPanel_Id');
                    	        if (configPanel && configPanel.loadScene) {
                    	            configPanel.loadScene(selected[0]);
                    	        }
                    		}
                    	},
                    	celldblclick : function( grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                    		
                    	},select( v, record, index, eOpts ){
                    		
                    	}
                    }
                });
                var panel = Ext.getCmp("SceneModelInfoPanel_Id");
                if(isNotVal(panel)){
                	panel.add(gridPanel);
                }
            }
            var selectedRow=0;
            gridPanel.getSelectionModel().deselectAll(true);
            gridPanel.getSelectionModel().select(selectedRow, true);
        },
        beforeload: function (store, options) {
        	var orgId=Ext.getCmp('leftOrg_Id').getValue();
            var new_params = {
            	orgId: orgId
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});