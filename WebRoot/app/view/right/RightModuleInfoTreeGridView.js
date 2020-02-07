module_add = "";
module_modify = "";
module_delete = "";
moduleIndex_ = [];
Ext.define('AP.view.right.RightModuleInfoTreeGridView', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.rightModuleInfoTreeGridView',
    layout: "fit",
    border: false,
    animate: true,
    enableDD: false,
    useArrows: false,
    rootVisible: false,
    autoScroll: true,
    forceFit: true,
    id: "RightModuleTreeInfoGridPanel_Id", // 模块编码加id，定义的命名规则moduleCode是从库里取的值对应
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },

    initComponent: function () {
        var moduleTree = this;
        var moduleStore = Ext.create("AP.store.right.RightModuleTreeInfoStore");
        // LoadingWin("正在加载数据");
        Ext.apply(moduleTree, {
            store: moduleStore,
            columns: [
                {
                    xtype: 'treecolumn',
                    text: cosog.string.funcList,
                    flex: 8,
                    align: 'left',
                    dataIndex: 'text'
                        /*,
                                                				renderer : function(v, metaData, record, rowIndex, colIndex,
                                                						store) {
                                                					
                                                					var RighCurrentRoleModules_Id = Ext.getCmp("RighCurrentRoleModules_Id").getValue();
                                                					var oldModuleIds_ = [];
                                                					// 处理后
                                                					if(RighCurrentRoleModules_Id==null||RighCurrentRoleModules_Id==""){
                                                					   RighCurrentRoleModules_Id="[]";
                                                					}
                                                					var moduleIds = Ext.decode(RighCurrentRoleModules_Id);
                                                					if (isNotVal(moduleIds)) {
                                                						Ext.Array.each(moduleIds, function(name, index,
                                                										countriesItSelf) {
                                                									var modules_ = moduleIds[index];
                                                									var mdId_ = modules_.rmModuleid;// 当前tab对象
                                                									var rmMatrix_ = modules_.rmMatrix;
                                                									oldModuleIds_.push(mdId_);
                                                									if (record.data['mdId'] == mdId_) { // 选择逻辑
                                                										moduleIndex_.push(rowIndex);
                                                										var selectModel_=Ext.getCmp("RightModuleTreeInfoGridPanel_Id").getSelectionModel();
                                                										//selectModel_.select(rowIndex, true);
                                                										module_matrixs = rmMatrix_.split(',');
                                                										module_add = "";
                                                										module_modify = "";
                                                										module_delete = "";
                                                										module_add = module_matrixs[0];
                                                										module_modify = module_matrixs[1];
                                                										module_delete = module_matrixs[2];

                                                									}

                                                								});
                                                						var oldModuleIds_value = "" + oldModuleIds_.join(",");
                                                						var RightModuleRowIndex_Id = Ext.getCmp("RightModuleRowIndex_Id")
                                                						RightModuleRowIndex_Id.setValue("");
                                                						RightModuleRowIndex_Id.setValue(moduleIndex_);
                                                						var moduleIndex_value = "" + moduleIndex_.join(",");
                                                						// Ext.Msg.alert("msg",oldCodes_Ids);
                                                						
                                                						var RightOldModuleIds_Id_hidden = Ext.getCmp("RightOldModuleIds_Id")
                                                						RightOldModuleIds_Id_hidden.setValue(oldModuleIds_value);
                                                						
                                                					}

                                                					return v;
                                                				}*/      },
                {
                    header: 'mdIdaa',
                    hidden: true,
                    dataIndex: 'mdId'
   }, {
                    id: 'module_add_Id',
                    header: cosog.string.add,
                    flex: 2,
                    hidden: true,
                    value: '0',
                    align: 'left',
                    renderer: function (v, metaData, record, rowIndex, colIndex,
                        store) {
                        var select_ = Ext.getCmp("RightModuleTreeInfoGridPanel_Id")
                            .getSelectionModel().isSelected(rowIndex);
                        var md_Id = record.data['mdId'];
                        var view_ = "&nbsp;&nbsp;&nbsp;<input type='checkbox' id='" + md_Id + "&0'   ";
                        if (select_ == true && module_add == '1') {
                            view_ += "checked='checked' ";
                        }
                        view_ += " name='module_action' />";
                        return view_;
                    }
   }, {
                    id: 'module_modify_Id',
                    header: cosog.string.update,
                    value: '0',
                    hidden: true,
                    flex: 2,
                    align: 'left',
                    renderer: function (v, metaData, record, rowIndex, colIndex,
                        store) {
                        var select_ = Ext.getCmp("RightModuleTreeInfoGridPanel_Id")
                            .getSelectionModel().isSelected(rowIndex);
                        var md_Id = record.data['mdId'];
                        var view_ = " &nbsp;&nbsp;&nbsp;<input type='checkbox' id='" + md_Id + "&1' ";
                        if (select_ == true && module_modify == '1') {
                            view_ += "checked='checked'";
                        }
                        view_ += " name='module_action' />";
                        return view_;
                    }
   }, {
                    id: 'module_delete_Id',
                    header: cosog.string.del,
                    hidden: true,
                    value: '0',
                    align: 'left',
                    flex: 2,
                    renderer: function (v, metaData, record, rowIndex, colIndex,
                        store) {
                        var select_ = Ext.getCmp("RightModuleTreeInfoGridPanel_Id")
                            .getSelectionModel().isSelected(rowIndex);
                        var md_Id = record.data['mdId'];
                        var view_ = "&nbsp;&nbsp;&nbsp;<input type='checkbox' id='" + md_Id + "&2'   ";
                        if (select_ == true && module_delete == '1') {
                            view_ += "checked='checked'";
                        }
                        view_ += " name='module_action' />";
                        return view_;
                    }
   }]
        });

        this.callParent(arguments);
    },
    listeners: {
        checkchange: function (node, checked) {
            listenerCheck(node, checked);
        }
    }
});