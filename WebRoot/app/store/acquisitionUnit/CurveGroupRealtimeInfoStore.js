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
//                    	,validateedit: function(editor, context, eOpts) {
//                            // context 包含以下关键属性：
//                            //   record   - 当前编辑的记录
//                            //   field    - 被编辑的字段名 (这里是 'name')
//                            //   value    - 编辑后的新值
//                            //   original - 编辑前的原始值
//                            if (context.field === 'name') {
//                                var newName = context.value;
//                                var oldName = context.originalValue;
//                                // 如果新值与旧值相同，无需校验
//                                if (newName === oldName) {
//                                    return true;
//                                }
//                                var contextStore = context.grid.getStore();
//                                // 检查 store 中是否存在相同 name 的其他记录（排除当前记录）
//                                var existingRecord = contextStore.findRecord('name', newName, 0, false, true, true);
//                                if (existingRecord && existingRecord !== context.record) {
//                                	// 获取主窗口的 zIndex
//                                    var mainWindow = Ext.getCmp('curveGroupAndPropertiesConfigWindow_Id');
//                                    var winZIndex = mainWindow ? mainWindow.getZIndex() : 30000;
//                                    Ext.Msg.hide();
//                                    Ext.Msg.show({
//                                        title: loginUserLanguageResource.tip,
//                                        message: "<font color=blue>" + '曲线组已存在，请重新输入' + "</font>",
//                                        buttons: Ext.Msg.OK,
//                                        icon: Ext.Msg.WARNING,
//                                        zIndex: winZIndex + 5000
//                                    });
//                                    return false; // 校验失败，不保存编辑
//                                }
//                            }
//                            return true;
//                        }
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