var selectionObjs; // 定义全局量
var module_matrixs = [];
var module_add = "";
var module_modify = "";
var module_delete = "";
Ext.define('AP.view.right.RightModuleInfoGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.rightModuleInfoGridPanel',
    layout: 'fit',
    border: false,
    autoScroll: true,
    forceFit: true,
    columnLines: true, // 列线
    stripeRows: true, // 条纹行
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    selType: 'checkboxmodel',
    multiSelect: true,
    initComponent: function () {
        var RightModuleInfoStore = Ext.create("AP.store.right.RightModuleInfoStore");
        Ext.apply(this, {
            id: "RightModuleInfoGridPanel_Id",
            store: RightModuleInfoStore,
            columns: [{
                header: 'mdId',
                hidden: false,
                dataIndex: 'mdId'
   }, {
                header: '功能列表',
                align: 'center',
                dataIndex: 'mdName',
                renderer: function (v, metaData, record, rowIndex, colIndex,
                    store) {
                    var RighCurrentRoleModules_Id = Ext.getCmp("RighCurrentRoleModules_Id").getValue();
                    var oldModuleIds_ = [];
                    // 处理后
                    if (RighCurrentRoleModules_Id == null || RighCurrentRoleModules_Id == "") {
                        RighCurrentRoleModules_Id = "[]";
                    }
                    //alert(""+RighCurrentRoleModules_Id);
                    //Ext.getCmp("RightModuleInfoGridPanel_Id").getSelectionModel().select(2, true);
                    var moduleIds = Ext.decode(RighCurrentRoleModules_Id);


                    if (isNotVal(moduleIds)) {
                        Ext.Array.each(moduleIds, function (name, index,
                            countriesItSelf) {
                            var modules_ = moduleIds[index];
                            var mdId_ = modules_.rmModuleid; // 当前tab对象
                            var rmMatrix_ = modules_.rmMatrix;
                            var curMdId = record.data['mdId'];
                            oldModuleIds_.push(mdId_);

                            if (curMdId == mdId_) { // 选择逻辑
                                //alert(rowIndex);
                                Ext.getCmp("RightModuleInfoGridPanel_Id").getSelectionModel().select(rowIndex);
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
                        // Ext.Msg.alert("msg",oldCodes_Ids);
                        var RightOldModuleIds_Id_hidden = Ext
                            .getCmp("RightOldModuleIds_Id")
                        RightOldModuleIds_Id_hidden.setValue(oldModuleIds_value);
                    }

                    return v;
                }
   }]
        });

        this.callParent(arguments);

    }
});