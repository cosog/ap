/*==============================================================*/
/* 初始化TBL_DEVICETYPEINFO数据                                          */
/*==============================================================*/
insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (1, 0, 0, '设备类型根节点', 'Root', 'Корневой узел', '{"DeviceRealTimeMonitoring":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"DeviceHistoryQuery":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"AlarmQuery":{"FESDiagramResultAlarm":false,"RunStatusAlarm":true,"CommStatusAlarm":true,"NumericValueAlarm":true,"EnumValueAlarm":true,"SwitchingValueAlarm":true}}', 1);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (2, 1, 1, '举升类型', 'Lifting type', 'Тип подъёма', '{"DeviceRealTimeMonitoring":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"DeviceHistoryQuery":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"AlarmQuery":{"FESDiagramResultAlarm":false,"RunStatusAlarm":true,"CommStatusAlarm":true,"NumericValueAlarm":true,"EnumValueAlarm":true,"SwitchingValueAlarm":true}}', 1);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (3, 2, 10, '抽油机', 'SRP', 'Насос.', '{"DeviceRealTimeMonitoring":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"DeviceHistoryQuery":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"AlarmQuery":{"FESDiagramResultAlarm":false,"RunStatusAlarm":true,"CommStatusAlarm":true,"NumericValueAlarm":true,"EnumValueAlarm":true,"SwitchingValueAlarm":true}}', 1);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (4, 2, 11, '螺杆泵', 'PCP', 'Винтовой насос', '{"DeviceRealTimeMonitoring":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"DeviceHistoryQuery":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"AlarmQuery":{"FESDiagramResultAlarm":false,"RunStatusAlarm":true,"CommStatusAlarm":true,"NumericValueAlarm":true,"EnumValueAlarm":true,"SwitchingValueAlarm":true}}', 1);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (5, 2, 12, '电潜泵', 'ESP', 'электронасос', '{"DeviceRealTimeMonitoring":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"DeviceHistoryQuery":{"FESDiagramStatPie":false,"CommStatusStatPie":true,"RunStatusStatPie":true},"AlarmQuery":{"FESDiagramResultAlarm":false,"RunStatusAlarm":true,"CommStatusAlarm":true,"NumericValueAlarm":true,"EnumValueAlarm":true,"SwitchingValueAlarm":true}}', 1);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (6, 2, 13, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (7, 2, 14, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (8, 2, 15, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (9, 2, 16, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (10, 2, 17, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (11, 2, 18, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (12, 2, 19, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (13, 1, 2, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (14, 13, 20, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (15, 13, 21, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (16, 13, 22, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (17, 13, 23, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (18, 13, 24, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (19, 13, 25, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (20, 13, 26, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (21, 13, 27, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (22, 13, 28, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (23, 13, 29, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (24, 1, 3, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (25, 24, 30, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (26, 24, 31, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (27, 24, 32, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (28, 24, 33, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (29, 24, 34, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (30, 24, 35, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (31, 24, 36, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (32, 24, 37, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (33, 24, 38, '未命名', null, null, null, 0);

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU, CONFIG, STATUS)
values (34, 24, 39, '未命名', null, null, null, 0);

/*==============================================================*/
/* 初始化tbl_module数据                                          */
/*==============================================================*/
insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (9999, 0, '功能导航', '功能导航', '#', 'Navigation', 1, null, null, 'function', 0, '#', 'Navigation', 'Навигация по функциям', 'Navigation', 'Навигация по функциям');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (1998, 9999, '实时监控', '实时监控', 'AP.view.realTimeMonitoring.RealTimeMonitoringInfoView', 'DeviceRealTimeMonitoring', 1010010, null, null, 'realtime', 0, 'AP.controller.frame.MainIframeControl', 'Realtime monitoring', 'Мониторинг в режиме реального времени', 'Realtime monitoring', 'Мониторинг в режиме реального времени');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2018, 9999, '历史查询', '历史查询', 'AP.view.historyQuery.HistoryQueryInfoView', 'DeviceHistoryQuery', 1020010, null, null, 'history', 0, 'AP.controller.frame.MainIframeControl', 'History query', 'Исторические запросы', 'History query', 'Исторические запросы');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2158, 9999, '生产报表', '生产报表', 'AP.view.reportOut.ReportOutDailyReportView', 'DailyReport', 1030010, null, null, 'report', 0, 'AP.controller.frame.MainIframeControl', 'Report', 'Производственные отчеты', 'Report', 'Производственные отчеты');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2058, 9999, '故障查询', '故障查询', 'AP.view.alarmQuery.AlarmQueryInfoView', 'AlarmQuery', 1040010, null, null, 'alarm', 0, 'AP.controller.frame.MainIframeControl', 'Alarm query', 'Запрос сигнализации', 'Alarm query', 'Запрос сигнализации');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2038, 9999, '设备日志', '设备日志', 'AP.view.log.DeviceOperationLogInfoView', 'DeviceOperationLogQuery', 1050010, null, null, 'log', 0, 'AP.controller.frame.MainIframeControl', 'Device log', 'Журналы устройств', 'Device log', 'Журналы устройств');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2238, 9999, '系统日志', '系统日志', 'AP.view.log.SystemLogInfoView', 'SystemLogQuery', 1060010, null, null, 'systemLog', 0, 'AP.controller.frame.MainIframeControl', 'System log', 'Системные журналы', 'System log', 'Системные журналы');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2179, 9999, '数据维护', '数据维护', 'AP.view.dataMaintaining.CalculateMaintainingInfoView', 'CalculateMaintaining', 1070010, null, null, 'calculate', 0, '#', 'Calculate maintaining', 'Рассчитать техническое обслуживание', 'Calculate maintaining', 'Рассчитать техническое обслуживание');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (1777, 9999, '驱动配置', '驱动配置', 'AP.view.acquisitionUnit.ProtocolConfigInfoView', 'DriverManagement', 1080010, null, null, 'driverConfig', 0, 'AP.controller.acquisitionUnit.AcquisitionUnitInfoControl', 'Driver config', 'Конфигурация драйвера', 'Driver config', 'Конфигурация драйвера');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (27, 9999, '权限管理', '权限管理', '#', 'AuthorityManagement', 2030000, null, null, 'right', 0, 'AP.controller.frame.MainIframeControl', 'Authority management', 'Управление разрешениями', 'Authority management', 'Управление разрешениями');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (24, 27, '组织用户', '组织用户', 'AP.view.orgAndUser.OrgAndUserInfoView', 'OrganizationAndUserManagement', 2030100, null, null, 'org', 0, 'AP.controller.orgAndUser.OrgAndUserInfoControl', 'Organization and user', 'Организация пользователей', 'Organization and user', 'Организация пользователей');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (29, 27, '角色管理', '角色管理', 'AP.view.role.RoleInfoView', 'RoleManagement', 2030300, null, null, 'role', 0, 'AP.controller.role.RoleInfoControl', 'Role management', 'Управление ролями', 'Role management', 'Управление ролями');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (31, 9999, '设备管理', '设备管理', '#', 'DeviceManagement', 2040000, null, null, 'dataConfig', 0, 'AP.controller.frame.MainIframeControl', 'Device management', 'Управление устройствами', 'Device management', 'Управление устройствами');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (34, 31, '主设备', '主设备', 'AP.view.well.DeviceManagerInfoView', 'WellInformation', 2040100, null, null, 'wellInformation', 0, 'AP.controller.well.WellInfoController', 'Primary device', 'Основное устройство', 'Primary device', 'Основное устройство');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (23, 9999, '系统配置', '系统配置', '#', 'SystemManagement', 2090000, null, null, 'system', 0, 'AP.controller.frame.MainIframeControl', 'System management', 'Конфигурация системы', 'System management', 'Конфигурация системы');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (26, 23, '模块配置', '模块配置', 'AP.view.module.ModuleInfoView', 'ModuleManagement', 2090200, null, null, 'module', 0, 'AP.controller.module.ModuleInfoControl', 'Module management', 'Конфигурация модуля', 'Module management', 'Конфигурация модуля');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (894, 23, '字典配置', '字典配置', 'AP.view.data.SystemdataInfoView', 'DataDictionaryManagement', 2090100, null, null, 'dictionary', 0, 'AP.controller.data.SystemdataInfoControl', 'Data dictionary management', 'Настройка словаря', 'Data dictionary management', 'Настройка словаря');

insert into TBL_MODULE (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2278, 23, '运维配置', '运维配置', 'AP.view.operationMaintenance.OperationMaintenanceInfoView', 'OperationMaintenance', 2090300, null, null, 'operationMaintenance', 0, 'AP.controller.frame.MainIframeControl', 'Operation and Maintenance Configuration', 'Конфигурация эксплуатации и техобслуживания', 'Operation and Maintenance Configuration', 'Конфигурация эксплуатации и техобслуживания');


/*==============================================================*/
/* 初始化tbl_role数据                                          */
/*==============================================================*/
insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK, ROLE_LANGUAGEEDIT)
values (1, '超级管理员', 1, 1, 1, '全部权限', 1);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK, ROLE_LANGUAGEEDIT)
values (2, '软件管理员', 2, 1, 1, '数据查询、编辑、权限管理', 0);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK, ROLE_LANGUAGEEDIT)
values (3, '应用分析员', 3, 0, 0, '数据查询', 0);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK, ROLE_LANGUAGEEDIT)
values (4, '一般操作员', 4, 2, 1, '数据查询', 0);

/*==============================================================*/
/* 初始化tbl_module2role数据                                          */
/*==============================================================*/
insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (1, 9999, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (2, 1998, 1, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (3, 2018, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (4, 2058, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (5, 2038, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (6, 27, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (7, 24, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (8, 29, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (9, 31, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (10, 34, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (11, 1777, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (12, 23, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (13, 26, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (14, 894, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (15, 2158, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (16, 2179, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (17, 2238, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (18, 9999, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (19, 1998, 2, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (20, 2018, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (21, 2158, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (22, 2058, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (23, 2038, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (24, 2179, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (25, 1777, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (26, 27, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (27, 24, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (28, 29, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (29, 31, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (30, 34, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (31, 23, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (32, 26, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (33, 894, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (34, 1998, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (35, 2018, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (36, 2158, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (37, 2058, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (38, 2038, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (39, 2179, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (40, 26, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (41, 894, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (42, 1998, 4, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (43, 2018, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (44, 2158, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (45, 2058, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (46, 2038, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (47, 2179, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (48, 1777, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (49, 34, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (50, 2278, 1, '1,1,0');


/*==============================================================*/
/* 初始化TBL_DEVICETYPE2ROLE数据                                          */
/*==============================================================*/
insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (1, 1, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (2, 2, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (3, 3, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (4, 4, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (5, 5, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (6, 6, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (7, 7, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (8, 8, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (9, 9, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (10, 10, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (11, 11, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (12, 12, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (13, 13, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (14, 14, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (15, 15, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (16, 16, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (17, 17, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (18, 18, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (19, 19, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (20, 20, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (21, 21, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (22, 22, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (23, 23, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (24, 24, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (25, 25, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (26, 26, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (27, 27, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (28, 28, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (29, 29, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (30, 30, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (31, 31, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (32, 32, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (33, 33, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (34, 34, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (35, 1, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (36, 2, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (37, 3, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (38, 4, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (39, 5, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (40, 1, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (41, 2, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (42, 3, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (43, 4, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (44, 5, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (45, 1, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (46, 2, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (47, 3, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (48, 4, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (49, 5, 4, '0,0,0');

/*==============================================================*/
/* 初始化TBL_LANGUAGE2ROLE数据                                          */
/*==============================================================*/
insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (1, 1, 1, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (2, 2, 1, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (3, 3, 1, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (4, 1, 2, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (5, 2, 2, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (6, 3, 2, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (7, 1, 3, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (8, 2, 3, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (9, 3, 3, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (10, 1, 4, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (11, 2, 4, '0,0,0');

insert into TBL_LANGUAGE2ROLE (ID, LANGUAGE, ROLEID, MATRIX)
values (12, 3, 4, '0,0,0');

/*==============================================================*/
/* 初始化tbl_org数据                                          */
/*==============================================================*/
insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME_ZH_CN, ORG_MEMO, ORG_PARENT, ORG_SEQ, ORG_NAME_EN, ORG_NAME_RU)
values (1, '0000', '组织根节点', '组织根节点', 0, null, 'Root', 'Корневой узел');

/*==============================================================*/
/* 初始化tbl_user数据                                          */
/*==============================================================*/
insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL, USER_LANGUAGE)
values (1, 'system', '3c4c1fed0fb2b548f88eab0fcfe0b425', 'system', null, null, 1, 1, sysdate, 0, 1, 0, 0, 1);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL, USER_LANGUAGE)
values (2, 'admin', 'dbe745d59479077a7d5401c32e36caf1', 'admin', null, null, 1, 1, sysdate, 0, 1, 0, 0, 1);

/*==============================================================*/
/* 初始化tbl_tabmanager_device数据                                          */
/*==============================================================*/
insert into TBL_TABMANAGER_DEVICE (ID, NAME, CALCULATETYPE, CONFIG, SORT)
values (1, '电潜泵应用', 0, '{"DeviceRealTimeMonitoring":{"WellboreAnalysis":false,"SurfaceAnalysis":false,"TrendCurve":true,"DynamicData":true,"DeviceControl":true,"DeviceInformation":true},"DeviceHistoryQuery":{"TrendCurve":true,"TiledDiagram":false,"DiagramOverlay":false},"PrimaryDevice":{"AdditionalInformation":false,"AuxiliaryDevice":false,"VideoConfig":false,"CalculateDataConfig":false,"FSDiagramConstruction":false,"SystemParameterConfig":false}}', 1);