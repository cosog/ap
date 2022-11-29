Ext.define('AP.view.frame.IframeView', {
    extend: 'Ext.tree.Panel',
    id: 'IframeView_Id',
    alias: 'widget.iframeView',
    border: false,
    viewConfig: {
        loadMask: false
    },
    layout: 'fit',
    useArrows: true,
    rootVisible: false,
    autoScroll: true,
    animate: true,
    initComponent: function () {
        var orgTree = this;
        var iframe_store;
        if (userSyncOrAsync == "1") {
            iframe_store = Ext.create("AP.store.frame.IframeStore");
        } else {
            iframe_store = Ext.create("AP.store.frame.IframeStore");
        }
        Ext.apply(orgTree, {
            store: iframe_store,
            tbar: {
                hidden: false,
                items: [{
                    iconCls: 'icon-collapse-all', // 收缩按钮
                    text: '收缩',
                    tooltip: {
                        text: '收缩全部'
                    },
                    handler: function () {
                        orgTree.collapseAll();
                    }
                }, '-', {
                    iconCls: 'icon-expand-all', // 展开按钮
                    tooltip: {
                        text: '展开全部'
                    },
                    text: '展开',
                    handler: function () {
                        orgTree.expandAll();
                    }
                }, '-', {
	                iconCls: 'note-refresh',
//	                text: cosog.string.refresh,
	                tooltip: {
	                    text: cosog.string.refresh
	                },
	                handler: function () {
	                	orgTree.getStore().load();
	                }
                }, {
                    id: 'leftOrg_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'leftOrg_Name',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'topModule_Id',
                    xtype: 'textfield',
                    value: 'monitor_MonitorPumpingUnit',
                    hidden: true
                }, {
                    id: 'bottomTab_Id',
                    xtype: 'textfield',
                    value: 'MonitorPumpingUnit',
                    hidden: true
                }, {
                    id: 'secondBottomTab_Id',
                    xtype: 'textfield',
                    text: '工况诊断第二个tab',
                    value: 'SingleDinagnosisAnalysis',
                    hidden: true
                }, {
                    id: 'productBottomTab_Id',
                    xtype: 'textfield',
                    text: '产量分析第二个tab',
                    value: 'ProductionStatistics',
                    hidden: true
                }, {
                    id: 'imageBottomTab_Id',
                    xtype: 'textfield',
                    text: '图形查询第二个tab',
                    value: 'gt',
                    hidden: true
                }, {
                    id: 'bottomTabToTopModule_Id',
                    xtype: 'textfield',
                    text: '第二个tab',
                    value: '',
                    hidden: true
                }, {
                    id: 'constructTab_Id', // 动态构建tab根据改值来动态拼接url
                    xtype: 'textfield',
                    value: 'index',
                    hidden: true
                }, {
                    id: 'imageQueryType_Id', // 图形查询类型实时还是定时
                    xtype: 'textfield',
                    value: 'ImagequeryInstant',
                    hidden: true
                }, {
                    id: 'graphicalQueryType_Id', // 图形查询是实时还是定时  li
                    xtype: 'textfield',
                    value: 'GraphicalRealtimequery',
                    hidden: true
                }, {
                    id: 'graphicalOnclickType_Id', // 存放图形点击查看时的图形类型  li
                    xtype: 'textfield',
                    value: 'gtsj',
                    hidden: true
                }, {
                    id: 'graphicalOnclick_Id', // 存放图形点击查看时的id  li
                    xtype: 'textfield',
                    value: '0',
                    hidden: true
                }, {
                    id: 'ContrastTimeOne_Id', // 功图对比一的时间
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'ContrastTimeTwo_Id', // 功图对比二的时间
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'ContrastDetailTwoDate_Id', // // 功图详细列表需要的当前时间
                    xtype: 'textfield',
                    value: 'new',
                    hidden: true
                }, {
                    id: 'txcxgtType_Id',
                    xtype: 'textfield',
                    value: 'gt',
                    hidden: true
                }, {
                    id: 'bztimageType_Id', // 当前是什么标准功图还是电能图
                    xtype: 'textfield',
                    value: 'gt',
                    hidden: true
                }, {
                    id: 'imageTypeJsonData_Id', // 后台返回的图形接送数据
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'imageTypebzgteachWidth_Id', // 标准功图每张的宽度
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'DiagnosisConstrastpanelwidth_Id', // 功图对比最外面panel的宽度
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'DiagnosisConstrastSGT_Id', // 功图对比示功图是否调用ajax
                    xtype: 'textfield',
                    value: 'all',
                    hidden: true
                }, {
                    id: 'DiagnosisConstrastSGTTwo_Id', // 功图对比示功图是否调用ajax
                    xtype: 'textfield',
                    value: 'all',
                    hidden: true
                }, {
                    id: 'leftJH_Id', // 采气井日报的JH
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'ComputeStatistics_ComputePumpingUnitHidden_Id', // 产量计算切换时需要的当前panel对象
                    xtype: 'textfield',
                    value: 'Instant',
                    hidden: true
                }, {
                    id: 'DiagnosisStatisticsChart_TypeId',
                    xtype: 'textfield',
                    value: 'DiagnosisPie_Id',
                    hidden: true
                }, {
                    id: 'ProductionStatisticsChart_TypeId',
                    xtype: 'textfield',
                    value: 'ComputePie_Id',
                    hidden: true
                }, {
                    id: 'ProductionStatisticsChartData_Ids',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    xtype: 'textfield',
                    id: 'alarmImageHidden_Id',
                    value: 'AlarmPie_Id',
                    hidden: true
                }, {
                    xtype: 'textfield',
                    id: 'ContrastBzgtbhOne_Id',
                    value: '',
                    hidden: true
                }, {
                    xtype: 'textfield',
                    id: 'ContrastBzgtbhTwo_Id',
                    value: '',
                    hidden: true
                }, {
                    xtype: 'textfield',
                    id: 'bjbhxiangxi_Id',
                    value: '',
                    hidden: true
                }, {
                    xtype: 'textfield',
                    id: 'monitorColumns_Id',
                    value: '',
                    hidden: true
                }, {
                    id: 'oldLeftOrg_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'tabNums_Id',
                    xtype: 'textfield',
                    value: '1',
                    hidden: true
                }, {
                    id: 'orgTreeSelectedCoordX_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'orgTreeSelectedCoordY_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'orgTreeSelectedShowLevel_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RightOrgInfo_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RightUserNo_Id', // 动态构建tab根据改值来动态拼接url
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RightOldRoleCodes_Id', // 动态构建tab根据改值来动态拼接url
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RightBottomRoleCodes_Id', // 分配权限时，存放当前选中的角色编码
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RightOldModuleIds_Id', // 分配权限时，存放当前选中的角色编码
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RightModuleRowIndex_Id', // 分配权限时，存放当前选中的模块的行索引值
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RighActionModule_Id', // 分配动作时，存放当前选中的模块Id
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RighActionOldActions_Id', // 分配动作时，存放原来当前选中的模块Id
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RighActionModulesIdActions_Id', // 分配权限时，存放当前选中的模块Id
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'RighCurrentRoleModules_Id', // 分配动作时，存放当前选中的角色的模块Id
                    xtype: 'textfield',
                    value: '[]',
                    hidden: true
                }, {
                    id: 'WellTrajectoryadd_Id', // 井身轨迹井名信息
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'ProductiondDataUIjbh_Id', // 存放生产数据标准功图标定时，当前的jbh
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'ProductiondDataGtTotalPages_Id', // 存放生产数据功图列表的总页数
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'AlarmShowStyle_Id', // 存放生产数据功图列表的总页数
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'realtimeTurnToHisyorySign_Id', //选择的统计项的类型
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }]
            },
            listeners: {
                afterrender: function (v, o) {
                    var MainIframeView = Ext.create("AP.view.frame.MainIframeView");
                    var MainModuleShow_Id = Ext.getCmp("MainModuleShow_Id");
                    MainModuleShow_Id.removeAll();
                    MainModuleShow_Id.add(MainIframeView);
                }
            }

        });
        this.callParent(arguments);
    }

});
var logtOutallBack = function () {
    LoadingWin("正在退出");
    // 动态返回当前用户拥有哪些角色信息
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/userLoginManagerController/userExit',
        success: function (response, opts) {
            // 处理后
            var obj = Ext.decode(response.responseText);
            if (isNotVal(obj)) {
                if (obj.flag) {
                    window.location.href = context + "/Login.jsp";
                }

            }
        },
        failure: function (response, opts) {
            Ext.Msg.alert("信息提示", "后台获取数据失败！");
        }
    });
};