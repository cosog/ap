/*==============================================================*/
/* 初始化tbl_dist_name数据                                          */
/*==============================================================*/
insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('7f13446d19b4497986980fa16a750f95', null, '实时监控_设备概览', 'realTimeMonitoring_Overview', 11101, 0, '超级管理员', '超级管理员', to_date('02-11-2023 14:37:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2023 14:37:40', 'dd-mm-yyyy hh24:mi:ss'), 1998, 'realTimeMonitoring_Overview', '实时监控_设备概览');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('cd7b24562b924d19b556de31256e22a1', null, '历史查询_设备概览', 'historyQuery_Overview', 12101, 0, '超级管理员', '超级管理员', to_date('02-11-2023 14:36:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2023 14:36:33', 'dd-mm-yyyy hh24:mi:ss'), 2018, 'historyQuery_Overview', '历史查询_设备概览');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('0d4297cb4db44bb3a9a3d5d983007039', null, '历史查询_图形叠加', 'historyQuery_FESDiagramOverlay', 12103, 0, '超级管理员', '超级管理员', to_date('06-12-2024 14:03:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-12-2024 14:03:54', 'dd-mm-yyyy hh24:mi:ss'), 2018, 'historyQuery_FESDiagramOverlay', '历史查询_图形叠加');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('cdd198534d5849b7a27054e0f2593ff3', null, '故障查询_通信状态报警', 'alarmQuery_CommStatusAlarm', 14101, 0, '系统管理员', '系统管理员', to_date('02-11-2021 15:20:57', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2021 15:20:57', 'dd-mm-yyyy hh24:mi:ss'), 2058, 'alarmQuery_CommStatusAlarm', '故障查询_通信状态报警');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('e2924366ab174d4b9a096be969934985', 'system', '故障查询_数值量报警', 'alarmQuery_NumericValueAlarm', 14102, 0, '系统管理员', '系统管理员', to_date('16-09-2021 13:50:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2021 13:50:40', 'dd-mm-yyyy hh24:mi:ss'), 2058, 'alarmQuery_NumericValueAlarm', '故障查询_数值量报警');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('b09082f4272e4768994db398e14bc3f2', null, '故障查询_枚举量报警', 'alarmQuery_EnumValueAlarm', 14103, 0, '超级管理员', '超级管理员', to_date('05-03-2024 18:07:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:07:07', 'dd-mm-yyyy hh24:mi:ss'), 2058, 'alarmQuery_EnumValueAlarm', '故障查询_枚举量报警');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('b71c1a2c9d574fe482080a56c7c780a9', null, '故障查询_开关量报警', 'alarmQuery_SwitchingValueAlarm', 14104, 0, '系统管理员', '系统管理员', to_date('07-10-2021 19:06:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-10-2021 19:06:41', 'dd-mm-yyyy hh24:mi:ss'), 2058, 'alarmQuery_SwitchingValueAlarm', '故障查询_开关量报警');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('ad646d19fcaa4fbd9077dbf7a826b107', 'system', '日志查询_设备操作日志', 'logQuery_DeviceOperationLog', 15101, 0, '系统管理员', '系统管理员', to_date('07-09-2021 17:05:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-09-2021 17:05:31', 'dd-mm-yyyy hh24:mi:ss'), 2038, 'logQuery_DeviceOperationLog', '日志查询_设备操作日志');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('167aeb3aca384afda8655d63aedee484', 'system', '日志查询_系统日志', 'logQuery_SystemLog', 15102, 0, '系统管理员', '系统管理员', to_date('07-09-2021 19:04:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-09-2021 19:04:19', 'dd-mm-yyyy hh24:mi:ss'), 2038, 'logQuery_SystemLog', '日志查询_系统日志');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('436802a1c0074a79aafd00ce539166f4', 'system', '计算维护_抽油机井单条记录', 'calculateManager_SRPSingleRecord', 16101, 0, '超级管理员', '超级管理员', to_date('24-04-2023 09:45:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 09:45:19', 'dd-mm-yyyy hh24:mi:ss'), 2179, 'calculateManager_SRPSingleRecord', '计算维护_抽油机井单条记录');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('cf1c0981f31242f9b3e84810bdc0a19f', 'system', '计算维护_抽油机井记录汇总', 'calculateManager_SRPTotalRecord', 16102, 0, '超级管理员', '超级管理员', to_date('07-05-2022 19:56:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-05-2022 19:56:03', 'dd-mm-yyyy hh24:mi:ss'), 2179, 'calculateManager_SRPTotalRecord', '计算维护_抽油机井记录汇总');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('aad8b76fdaf84a1194de5ec0a4453631', null, '计算维护_螺杆泵井单条记录', 'calculateManager_PCPSingleRecord', 16103, 0, '系统管理员', '系统管理员', to_date('30-11-2018 16:40:57', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:40:57', 'dd-mm-yyyy hh24:mi:ss'), 2179, 'calculateManager_PCPSingleRecord', '计算维护_螺杆泵井单条记录');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('8122b170c0ca4deb87159c931ab251f3', 'system', '计算维护_螺杆泵井记录汇总', 'calculateManager_PCPTotalRecord', 16104, 0, '超级管理员', '超级管理员', to_date('07-05-2022 19:56:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-05-2022 19:56:38', 'dd-mm-yyyy hh24:mi:ss'), 2179, 'calculateManager_PCPTotalRecord', '计算维护_螺杆泵井记录汇总');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('8ab792e089494533be910699d426c7d5', null, '组织用户_单位列表', 'orgAndUser_OrgManage', 21101, 0, '系统管理员', '系统管理员', to_date('24-12-2021 09:33:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 09:33:43', 'dd-mm-yyyy hh24:mi:ss'), 24, 'orgAndUser_OrgManage', '组织用户_单位列表');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('5ba761c1383f498f9ac97c9a8ab6d847', null, '组织用户_用户列表', 'orgAndUser_UserManage', 21102, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:45:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:45:52', 'dd-mm-yyyy hh24:mi:ss'), 24, 'orgAndUser_UserManage', '组织用户_用户列表');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('220c349e246e47a39a818023f1c97a63', null, '角色管理_角色列表', 'role_RoleManage', 21103, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:46:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:46:02', 'dd-mm-yyyy hh24:mi:ss'), 29, 'role_RoleManage', '角色管理_角色列表');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('87808f225d7240f68c2ab879347d818a', null, '井名信息_设备列表', 'deviceInfo_DeviceManager', 22101, 0, '超级管理员', '超级管理员', to_date('09-10-2023 08:57:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-10-2023 08:57:41', 'dd-mm-yyyy hh24:mi:ss'), 34, 'deviceInfo_DeviceManager', '井名信息_设备列表');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('d8cd980aa8344c399b9cf11268b6ed8f', null, '井名信息_设备批量添加', 'deviceInfo_DeviceBatchAdd', 22102, 0, '超级管理员', '超级管理员', to_date('18-01-2024 11:39:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-01-2024 11:39:22', 'dd-mm-yyyy hh24:mi:ss'), 34, 'deviceInfo_DeviceBatchAdd', '井名信息_设备批量添加');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('2b4cd8cb8c6844769c66b038246c27bf', null, '短信设备管理', 'deviceInfo_SMSDeviceManager', 22104, 0, '系统管理员', '系统管理员', to_date('21-12-2021 20:19:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-12-2021 20:19:30', 'dd-mm-yyyy hh24:mi:ss'), 2078, 'SMSDeviceManager', '短信设备管理');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('1404100741bc42799be5b7cbebf4b649', null, '辅件设备_辅件设备列表', 'auxiliaryDeviceManager', 22105, 0, '超级管理员', '超级管理员', to_date('20-12-2024 09:42:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('20-12-2024 09:42:42', 'dd-mm-yyyy hh24:mi:ss'), 2218, 'auxiliaryDeviceManager', '辅件设备_辅件设备列表');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('b6ef8f3a49094768b3231d5678fc9cbc', null, '模块配置_模块列表', 'module_ModuleManage', 23101, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'), 26, 'module_ModuleManage', '模块配置_模块列表');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('b8a408839dd8498d9a19fc65f7406ed4', null, '字典配置_字典列表', 'dictionary_DataDictionaryManage', 23102, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'), 894, 'dictionary_DataDictionaryManage', '字典配置_字典列表');

/*==============================================================*/
/* 初始化tbl_dist_item数据                                          */
/*==============================================================*/
insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('118836', null, '7f13446d19b4497986980fa16a750f95', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('118837', null, '7f13446d19b4497986980fa16a750f95', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('22-02-2024 08:41:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-02-2024 08:41:59', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128173', null, '7f13446d19b4497986980fa16a750f95', '设备类型', 'deviceTypeName', null, 3, 1, null, null, to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'), 'deviceTypeName', '设备类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('118931', null, '7f13446d19b4497986980fa16a750f95', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 4, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), 'acqTime', '采集时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('118838', null, '7f13446d19b4497986980fa16a750f95', '通信状态', 'commStatusName', null, 5, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), 'commStatusName', '通信状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134390', null, '7f13446d19b4497986980fa16a750f95', '在线时间(h)', 'commTime', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'commTime', '在线时间(h)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134391', null, '7f13446d19b4497986980fa16a750f95', '在线时率(小数)', 'commTimeEfficiency', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'commTimeEfficiency', '在线时率(小数)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134392', null, '7f13446d19b4497986980fa16a750f95', '在线区间', 'commRange', null, 8, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'commRange', '在线区间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133835', null, '7f13446d19b4497986980fa16a750f95', '运行状态', 'runStatusName', null, 9, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), 'runStatusName', '运行状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134393', null, '7f13446d19b4497986980fa16a750f95', '运行时间(h)', 'runTime', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'runTime', '运行时间(h)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134394', null, '7f13446d19b4497986980fa16a750f95', '运行时率(小数)', 'runTimeEfficiency', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'runTimeEfficiency', '运行时率(小数)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134395', null, '7f13446d19b4497986980fa16a750f95', '运行区间', 'runRange', null, 12, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'runRange', '运行区间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119190', null, 'cd7b24562b924d19b556de31256e22a1', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119191', null, 'cd7b24562b924d19b556de31256e22a1', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('11-05-2024 16:05:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2024 16:05:06', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119193', null, 'cd7b24562b924d19b556de31256e22a1', '设备类型', 'deviceTypeName', null, 3, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), 'deviceTypeName', '设备类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119192', null, 'cd7b24562b924d19b556de31256e22a1', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 4, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), 'acqTime', '采集时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134402', null, 'cd7b24562b924d19b556de31256e22a1', '通信状态', 'commStatusName', null, 5, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'commStatusName', '通信状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134403', null, 'cd7b24562b924d19b556de31256e22a1', '在线时间(h)', 'commTime', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'commTime', '在线时间(h)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134404', null, 'cd7b24562b924d19b556de31256e22a1', '在线时率(小数)', 'commTimeEfficiency', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'commTimeEfficiency', '在线时率(小数)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133873', null, 'cd7b24562b924d19b556de31256e22a1', '在线区间', 'commRange', null, 8, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), 'commRange', '在线区间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134405', null, 'cd7b24562b924d19b556de31256e22a1', '运行状态', 'runStatusName', null, 9, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'runStatusName', '运行状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134406', null, 'cd7b24562b924d19b556de31256e22a1', '运行时间(h)', 'runTime', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'runTime', '运行时间(h)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134407', null, 'cd7b24562b924d19b556de31256e22a1', '运行时率(小数)', 'runTimeEfficiency', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'runTimeEfficiency', '运行时率(小数)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('149217', null, 'cd7b24562b924d19b556de31256e22a1', '运行区间', 'runRange', null, 12, 1, null, null, to_date('07-06-2024 09:01:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2024 09:01:30', 'dd-mm-yyyy hh24:mi:ss'), 'runRange', '运行区间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133335', null, '0d4297cb4db44bb3a9a3d5d983007039', '序号', 'id', 'width:50', 1, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133336', null, '0d4297cb4db44bb3a9a3d5d983007039', '采集时间', 'acqTime', 'width:150', 2, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'acqTime', '采集时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138735', null, '0d4297cb4db44bb3a9a3d5d983007039', '通信状态', 'commStatusName', null, 3, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), 'commStatusName', '通信状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138736', null, '0d4297cb4db44bb3a9a3d5d983007039', '在线时间(h)', 'commTime', null, 4, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'commTime', '在线时间(h)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138737', null, '0d4297cb4db44bb3a9a3d5d983007039', '在线时率(小数)', 'commTimeEfficiency', null, 5, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'commTimeEfficiency', '在线时率(小数)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138738', null, '0d4297cb4db44bb3a9a3d5d983007039', '在线区间', 'commRange', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'commRange', '在线区间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138739', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行状态', 'runStatusName', null, 7, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), 'runStatusName', '运行状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138740', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行时间(h)', 'runTime', null, 8, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'runTime', '运行时间(h)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138741', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行时率(小数)', 'runTimeEfficiency', null, 9, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'runTimeEfficiency', '运行时率(小数)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138742', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行区间', 'runRange', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'runRange', '运行区间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133337', null, '0d4297cb4db44bb3a9a3d5d983007039', '工况', 'resultName', 'width:130', 11, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'resultName', '工况');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138743', null, '0d4297cb4db44bb3a9a3d5d983007039', '优化建议', 'optimizationSuggestion', null, 12, 1, null, null, to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'), 'optimizationSuggestion', '优化建议');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133342', null, '0d4297cb4db44bb3a9a3d5d983007039', '冲程(m)', 'stroke', null, 13, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'stroke', '冲程(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133343', null, '0d4297cb4db44bb3a9a3d5d983007039', '冲次(1/min)', 'spm', null, 14, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'spm', '冲次(1/min)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133344', null, '0d4297cb4db44bb3a9a3d5d983007039', '最大载荷(kN)', 'fmax', null, 15, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'fmax', '最大载荷(kN)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133345', null, '0d4297cb4db44bb3a9a3d5d983007039', '最小载荷(kN)', 'fmin', null, 16, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'fmin', '最小载荷(kN)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138744', null, '0d4297cb4db44bb3a9a3d5d983007039', '充满系数', 'fullnessCoefficient', null, 17, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), 'fullnessCoefficient', '充满系数');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139533', null, '0d4297cb4db44bb3a9a3d5d983007039', '柱塞冲程(m)', 'plungerStroke', null, 18, 1, null, null, to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), 'plungerStroke', '柱塞冲程(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139534', null, '0d4297cb4db44bb3a9a3d5d983007039', '柱塞有效冲程(m)', 'availablePlungerStroke', null, 19, 1, null, null, to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), 'availablePlungerStroke', '柱塞有效冲程(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138745', null, '0d4297cb4db44bb3a9a3d5d983007039', '泵挂(m)', 'pumpSettingDepth', null, 20, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'pumpSettingDepth', '泵挂(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139722', null, '0d4297cb4db44bb3a9a3d5d983007039', '动液面(m)', 'producingfluidLevel', null, 21, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'producingfluidLevel', '动液面(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139723', null, '0d4297cb4db44bb3a9a3d5d983007039', '反演液面校正值(MPa)', 'levelCorrectValue', null, 22, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'levelCorrectValue', '反演液面校正值(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138746', null, '0d4297cb4db44bb3a9a3d5d983007039', '反演动液面(m)', 'calcProducingfluidLevel', null, 23, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'calcProducingfluidLevel', '反演动液面(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138747', null, '0d4297cb4db44bb3a9a3d5d983007039', '液面校正差值(MPa)', 'levelDifferenceValue', null, 24, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'levelDifferenceValue', '液面校正差值(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138748', null, '0d4297cb4db44bb3a9a3d5d983007039', '沉没度(m)', 'submergence', null, 25, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'submergence', '沉没度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133338', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产液量(t/d)', 'liquidWeightProduction', null, 26, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'liquidWeightProduction', '瞬时产液量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145265', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产油量(t/d)', 'oilWeightProduction', null, 27, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'oilWeightProduction', '瞬时产油量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145266', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产水量(t/d)', 'waterWeightProduction', null, 28, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'waterWeightProduction', '瞬时产水量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133339', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产液量(t/d))', 'liquidWeightProduction_L', null, 29, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'liquidWeightProduction_L', '日累计产液量(t/d))');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145267', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产油量(t/d))', 'oilWeightProduction_L', null, 30, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'oilWeightProduction_L', '日累计产油量(t/d))');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145268', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产水量(t/d))', 'waterWeightProduction_L', null, 31, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'waterWeightProduction_L', '日累计产水量(t/d))');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133340', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产液量(m^3/d)', 'liquidVolumetricProduction', null, 32, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'liquidVolumetricProduction', '瞬时产液量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145269', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产油量(m^3/d)', 'oilVolumetricProduction', null, 33, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'oilVolumetricProduction', '瞬时产油量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145270', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产水量(m^3/d)', 'waterVolumetricProduction', null, 34, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'waterVolumetricProduction', '瞬时产水量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133341', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产液量(m^3/d))', 'liquidVolumetricProduction_L', null, 35, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'liquidVolumetricProduction_L', '日累计产液量(m^3/d))');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145271', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产油量(m^3/d))', 'oilVolumetricProduction_L', null, 36, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'oilVolumetricProduction_L', '日累计产油量(m^3/d))');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145272', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产水量(m^3/d))', 'waterVolumetricProduction_L', null, 37, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'waterVolumetricProduction_L', '日累计产水量(m^3/d))');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138749', null, '0d4297cb4db44bb3a9a3d5d983007039', '有功功率(kW)', 'averageWatt', null, 38, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'averageWatt', '有功功率(kW)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138750', null, '0d4297cb4db44bb3a9a3d5d983007039', '光杆功率(kW)', 'polishrodPower', null, 39, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'polishrodPower', '光杆功率(kW)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138751', null, '0d4297cb4db44bb3a9a3d5d983007039', '水功率(kW)', 'waterPower', null, 40, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'waterPower', '水功率(kW)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138752', null, '0d4297cb4db44bb3a9a3d5d983007039', '地面效率(%)', 'surfaceSystemEfficiency', null, 41, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'surfaceSystemEfficiency', '地面效率(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138753', null, '0d4297cb4db44bb3a9a3d5d983007039', '井下效率(%)', 'welldownSystemEfficiency', null, 42, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'welldownSystemEfficiency', '井下效率(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138754', null, '0d4297cb4db44bb3a9a3d5d983007039', '系统效率(%)', 'systemEfficiency', null, 43, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'systemEfficiency', '系统效率(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138755', null, '0d4297cb4db44bb3a9a3d5d983007039', '吨液百米耗电量(kW· h/100m· t)', 'energyper100mlift', null, 44, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'energyper100mlift', '吨液百米耗电量(kW· h/100m· t)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138756', null, '0d4297cb4db44bb3a9a3d5d983007039', '总泵效(%)', 'pumpEff', null, 45, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'pumpEff', '总泵效(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139724', null, '0d4297cb4db44bb3a9a3d5d983007039', '上冲程最大电流(A)', 'upStrokeIMax', null, 46, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), 'upStrokeIMax', '上冲程最大电流(A)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139725', null, '0d4297cb4db44bb3a9a3d5d983007039', '下冲程最大电流(A)', 'downStrokeIMax', null, 47, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), 'downStrokeIMax', '下冲程最大电流(A)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138757', null, '0d4297cb4db44bb3a9a3d5d983007039', '电流平衡度(%)', 'iDegreeBalance', null, 48, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'iDegreeBalance', '电流平衡度(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139726', null, '0d4297cb4db44bb3a9a3d5d983007039', '上冲程最大功率(kW)', 'upStrokeWattMax', null, 49, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), 'upStrokeWattMax', '上冲程最大功率(kW)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139727', null, '0d4297cb4db44bb3a9a3d5d983007039', '下冲程最大功率(kW)', 'downStrokeWattMax', null, 50, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), 'downStrokeWattMax', '下冲程最大功率(kW)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138758', null, '0d4297cb4db44bb3a9a3d5d983007039', '功率平衡度(%)', 'wattDegreeBalance', null, 51, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'wattDegreeBalance', '功率平衡度(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138759', null, '0d4297cb4db44bb3a9a3d5d983007039', '移动距离(cm)', 'deltaradius', null, 52, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'deltaradius', '移动距离(cm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138760', null, '0d4297cb4db44bb3a9a3d5d983007039', '日用电量(kW·h)', 'todayKWattH', null, 53, 0, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), 'todayKWattH', '日用电量(kW·h)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119852', null, 'cdd198534d5849b7a27054e0f2593ff3', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119853', null, 'cdd198534d5849b7a27054e0f2593ff3', '设备名称', 'deviceName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119855', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:16:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:37', 'dd-mm-yyyy hh24:mi:ss'), 'alarmTime', '报警时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('121867', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:16:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:46', 'dd-mm-yyyy hh24:mi:ss'), 'itemName', '报警项');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119856', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:16:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:50', 'dd-mm-yyyy hh24:mi:ss'), 'alarmInfo', '报警信息');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119857', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警级别', 'alarmLevelName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), 'alarmLevelName', '报警级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119858', null, 'cdd198534d5849b7a27054e0f2593ff3', '恢复时间', 'recoveryTime', 'flex:3', 7, 0, null, '超级管理员', to_date('05-01-2022 14:17:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:00', 'dd-mm-yyyy hh24:mi:ss'), 'recoveryTime', '恢复时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119859', null, 'e2924366ab174d4b9a096be969934985', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119860', null, 'e2924366ab174d4b9a096be969934985', '设备名称', 'deviceName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-03-2024 18:06:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:06:52', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119862', null, 'e2924366ab174d4b9a096be969934985', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:17:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:48', 'dd-mm-yyyy hh24:mi:ss'), 'alarmTime', '报警时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119866', null, 'e2924366ab174d4b9a096be969934985', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:17:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:55', 'dd-mm-yyyy hh24:mi:ss'), 'itemName', '报警项');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119863', null, 'e2924366ab174d4b9a096be969934985', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:17:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:58', 'dd-mm-yyyy hh24:mi:ss'), 'alarmInfo', '报警信息');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119867', null, 'e2924366ab174d4b9a096be969934985', '报警值', 'alarmValue', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:18:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:01', 'dd-mm-yyyy hh24:mi:ss'), 'alarmValue', '报警值');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119868', null, 'e2924366ab174d4b9a096be969934985', '报警限值', 'alarmLimit', 'flex:2', 7, 1, null, '超级管理员', to_date('05-01-2022 14:18:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:04', 'dd-mm-yyyy hh24:mi:ss'), 'alarmLimit', '报警限值');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119869', null, 'e2924366ab174d4b9a096be969934985', '回差', 'hystersis', 'flex:2', 8, 1, null, '超级管理员', to_date('05-01-2022 14:18:08', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:08', 'dd-mm-yyyy hh24:mi:ss'), 'hystersis', '回差');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119864', null, 'e2924366ab174d4b9a096be969934985', '报警级别', 'alarmLevelName', 'flex:2', 9, 1, null, '超级管理员', to_date('05-01-2022 14:18:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:13', 'dd-mm-yyyy hh24:mi:ss'), 'alarmLevelName', '报警级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119865', null, 'e2924366ab174d4b9a096be969934985', '恢复时间', 'recoveryTime', 'flex:3', 10, 0, null, '超级管理员', to_date('05-01-2022 14:18:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:20', 'dd-mm-yyyy hh24:mi:ss'), 'recoveryTime', '恢复时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120115', null, 'b09082f4272e4768994db398e14bc3f2', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120116', null, 'b09082f4272e4768994db398e14bc3f2', '设备名称', 'deviceName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-03-2024 18:07:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:07:03', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120118', null, 'b09082f4272e4768994db398e14bc3f2', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:18:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:58', 'dd-mm-yyyy hh24:mi:ss'), 'alarmTime', '报警时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('121868', null, 'b09082f4272e4768994db398e14bc3f2', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:19:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:04', 'dd-mm-yyyy hh24:mi:ss'), 'itemName', '报警项');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120119', null, 'b09082f4272e4768994db398e14bc3f2', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:19:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:07', 'dd-mm-yyyy hh24:mi:ss'), 'alarmInfo', '报警信息');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120120', null, 'b09082f4272e4768994db398e14bc3f2', '报警级别', 'alarmLevelName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:19:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:10', 'dd-mm-yyyy hh24:mi:ss'), 'alarmLevelName', '报警级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120121', null, 'b09082f4272e4768994db398e14bc3f2', '恢复时间', 'recoveryTime', 'flex:3', 7, 0, null, '超级管理员', to_date('05-01-2022 14:19:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:16', 'dd-mm-yyyy hh24:mi:ss'), 'recoveryTime', '恢复时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119870', null, 'b71c1a2c9d574fe482080a56c7c780a9', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119871', null, 'b71c1a2c9d574fe482080a56c7c780a9', '设备名称', 'deviceName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-03-2024 18:07:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:07:20', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119873', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:19:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:31', 'dd-mm-yyyy hh24:mi:ss'), 'alarmTime', '报警时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('121869', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:19:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:35', 'dd-mm-yyyy hh24:mi:ss'), 'itemName', '报警项');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119874', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:19:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:38', 'dd-mm-yyyy hh24:mi:ss'), 'alarmInfo', '报警信息');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119875', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警级别', 'alarmLevelName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:19:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:43', 'dd-mm-yyyy hh24:mi:ss'), 'alarmLevelName', '报警级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119876', null, 'b71c1a2c9d574fe482080a56c7c780a9', '恢复时间', 'recoveryTime', 'flex:3', 7, 0, null, '超级管理员', to_date('05-01-2022 14:19:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:51', 'dd-mm-yyyy hh24:mi:ss'), 'recoveryTime', '恢复时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119371', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119372', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '设备名称', 'deviceName', 'flex:3', 2, 1, null, '超级管理员', to_date('06-03-2024 10:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 10:47:29', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119373', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '设备类型', 'deviceTypeName', 'flex:2', 3, 1, null, '超级管理员', to_date('05-01-2022 10:41:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:35', 'dd-mm-yyyy hh24:mi:ss'), 'deviceTypeName', '设备类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119375', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '登录IP', 'loginIp', 'flex:3', 4, 1, null, '超级管理员', to_date('05-01-2022 10:41:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:22', 'dd-mm-yyyy hh24:mi:ss'), 'loginIp', '登录IP');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119374', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作用户', 'user_id', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 10:41:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:29', 'dd-mm-yyyy hh24:mi:ss'), 'user_id', '操作用户');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119376', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作内容', 'actionName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 10:41:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:16', 'dd-mm-yyyy hh24:mi:ss'), 'actionName', '操作内容');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119378', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作时间', 'to_char(createTime@''yyyy-mm-dd hh24:mi:ss'') as createTime', 'flex:5', 7, 1, null, '超级管理员', to_date('05-01-2022 10:41:08', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:08', 'dd-mm-yyyy hh24:mi:ss'), 'createTime', '操作时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119377', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '备注', 'remark', 'flex:10', 8, 1, null, '超级管理员', to_date('05-01-2022 10:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:40:51', 'dd-mm-yyyy hh24:mi:ss'), 'remark', '备注');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119379', null, '167aeb3aca384afda8655d63aedee484', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119381', null, '167aeb3aca384afda8655d63aedee484', '登录IP', 'loginIp', 'flex:1', 2, 1, null, '超级管理员', to_date('05-01-2022 10:43:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:19', 'dd-mm-yyyy hh24:mi:ss'), 'loginIp', '登录IP');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119380', null, '167aeb3aca384afda8655d63aedee484', '操作用户', 'user_id', 'flex:1', 3, 1, null, '超级管理员', to_date('05-01-2022 10:43:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:15', 'dd-mm-yyyy hh24:mi:ss'), 'user_id', '操作用户');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119382', null, '167aeb3aca384afda8655d63aedee484', '操作内容', 'actionName', 'flex:1', 4, 1, null, '超级管理员', to_date('05-01-2022 10:43:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:22', 'dd-mm-yyyy hh24:mi:ss'), 'actionName', '操作内容');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119384', null, '167aeb3aca384afda8655d63aedee484', '操作时间', 'to_char(createTime@''yyyy-mm-dd hh24:mi:ss'') as createTime', 'flex:1', 5, 1, null, '超级管理员', to_date('05-01-2022 10:43:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:26', 'dd-mm-yyyy hh24:mi:ss'), 'createTime', '操作时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119383', null, '167aeb3aca384afda8655d63aedee484', '备注', 'remark', 'flex:1', 6, 1, null, '超级管理员', to_date('05-01-2022 10:43:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:30', 'dd-mm-yyyy hh24:mi:ss'), 'remark', '备注');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138676', null, '436802a1c0074a79aafd00ce539166f4', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138677', null, '436802a1c0074a79aafd00ce539166f4', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('06-03-2024 15:51:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:11', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138678', null, '436802a1c0074a79aafd00ce539166f4', '功图采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:150', 3, 1, null, '系统管理员', to_date('13-06-2018 15:27:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-06-2018 15:27:50', 'dd-mm-yyyy hh24:mi:ss'), 'acqTime', '功图采集时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138679', null, '436802a1c0074a79aafd00ce539166f4', '计算状态', 'resultStatus', null, 4, 1, null, '系统管理员', to_date('30-11-2018 10:21:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:04', 'dd-mm-yyyy hh24:mi:ss'), 'resultStatus', '计算状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138680', null, '436802a1c0074a79aafd00ce539166f4', '功图工况', 'resultName', 'width:130', 5, 1, null, '系统管理员', to_date('30-11-2018 18:11:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 18:11:49', 'dd-mm-yyyy hh24:mi:ss'), 'resultName', '功图工况');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138681', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产液量(t/d)', 'liquidWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'liquidWeightProduction', '瞬时产液量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138682', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产油量(t/d)', 'oilWeightProduction', null, 7, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'oilWeightProduction', '瞬时产油量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145273', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产水量(t/d)', 'waterWeightProduction', null, 8, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'waterWeightProduction', '瞬时产水量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138683', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产液量(m^3/d)', 'liquidVolumetricProduction', null, 9, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'liquidVolumetricProduction', '瞬时产液量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138684', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产油量(m^3/d)', 'oilVolumetricProduction', null, 10, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'oilVolumetricProduction', '瞬时产油量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145274', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产水量(m^3/d)', 'waterVolumetricProduction', null, 11, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'waterVolumetricProduction', '瞬时产水量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138685', null, '436802a1c0074a79aafd00ce539166f4', '原油密度(g/cm^3)', 'crudeoilDensity', null, 12, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'crudeoilDensity', '原油密度(g/cm^3)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138686', null, '436802a1c0074a79aafd00ce539166f4', '水密度(g/cm^3)', 'waterDensity', null, 13, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'waterDensity', '水密度(g/cm^3)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138687', null, '436802a1c0074a79aafd00ce539166f4', '天然气相对密度', 'naturalGasRelativeDensity', null, 14, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'naturalGasRelativeDensity', '天然气相对密度');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138688', null, '436802a1c0074a79aafd00ce539166f4', '饱和压力(MPa)', 'saturationPressure', null, 15, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'saturationPressure', '饱和压力(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138689', null, '436802a1c0074a79aafd00ce539166f4', '油层中部深度(m)', 'reservoirDepth', null, 16, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'reservoirDepth', '油层中部深度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138690', null, '436802a1c0074a79aafd00ce539166f4', '油层中部温度(℃)', 'reservoirTemperature', null, 17, 1, null, '系统管理员', to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'), 'reservoirTemperature', '油层中部温度(℃)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138691', null, '436802a1c0074a79aafd00ce539166f4', '油压(MPa)', 'tubingPressure', null, 18, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'tubingPressure', '油压(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138692', null, '436802a1c0074a79aafd00ce539166f4', '套压(MPa)', 'casingPressure', null, 19, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'casingPressure', '套压(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138693', null, '436802a1c0074a79aafd00ce539166f4', '井口油温(℃)', 'wellHeadFluidTemperature', null, 20, 1, null, '系统管理员', to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), 'wellHeadFluidTemperature', '井口油温(℃)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138694', null, '436802a1c0074a79aafd00ce539166f4', '含水率(%)', 'weightWaterCut', null, 21, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'weightWaterCut', '含水率(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138695', null, '436802a1c0074a79aafd00ce539166f4', '生产气油比(m^3/t)', 'productionGasOilRatio', 'width:120', 22, 1, null, '系统管理员', to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), 'productionGasOilRatio', '生产气油比(m^3/t)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138696', null, '436802a1c0074a79aafd00ce539166f4', '动液面(m)', 'producingFluidLevel', null, 23, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'producingFluidLevel', '动液面(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138697', null, '436802a1c0074a79aafd00ce539166f4', '泵挂(m)', 'pumpSettingDepth', null, 24, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'pumpSettingDepth', '泵挂(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138699', null, '436802a1c0074a79aafd00ce539166f4', '泵筒类型', 'barrelTypeName', null, 26, 1, null, null, to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'), 'barrelTypeName', '泵筒类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138700', null, '436802a1c0074a79aafd00ce539166f4', '泵级别', 'pumpGrade', null, 27, 1, null, '系统管理员', to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), 'pumpGrade', '泵级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138701', null, '436802a1c0074a79aafd00ce539166f4', '泵径(mm)', 'pumpboreDiameter', null, 28, 1, null, '系统管理员', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), 'pumpboreDiameter', '泵径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138702', null, '436802a1c0074a79aafd00ce539166f4', '柱塞长(m)', 'plungerLength', null, 29, 1, null, '系统管理员', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), 'plungerLength', '柱塞长(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138703', null, '436802a1c0074a79aafd00ce539166f4', '油管内径(mm)', 'tubingStringInsideDiameter', null, 30, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'tubingStringInsideDiameter', '油管内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138704', null, '436802a1c0074a79aafd00ce539166f4', '套管内径(mm)', 'casingStringInsideDiameter', 'width:120', 31, 1, null, '系统管理员', to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), 'casingStringInsideDiameter', '套管内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147197', null, '436802a1c0074a79aafd00ce539166f4', '一级杆类型', 'rodTypeName1', null, 32, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'rodTypeName1', '一级杆类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138705', null, '436802a1c0074a79aafd00ce539166f4', '一级杆级别', 'rodGrade1', null, 33, 1, null, '系统管理员', to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade1', '一级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138706', null, '436802a1c0074a79aafd00ce539166f4', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 34, 1, null, '系统管理员', to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter1', '一级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138707', null, '436802a1c0074a79aafd00ce539166f4', '一级杆内径(mm)', 'rodInsideDiameter1', null, 35, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter1', '一级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138708', null, '436802a1c0074a79aafd00ce539166f4', '一级杆长度(m)', 'rodLength1', null, 36, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodLength1', '一级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147198', null, '436802a1c0074a79aafd00ce539166f4', '二级杆类型', 'rodTypeName2', null, 37, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'rodTypeName2', '二级杆类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138709', null, '436802a1c0074a79aafd00ce539166f4', '二级杆级别', 'rodGrade2', null, 38, 1, null, '系统管理员', to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade2', '二级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138710', null, '436802a1c0074a79aafd00ce539166f4', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 39, 1, null, '系统管理员', to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter2', '二级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138711', null, '436802a1c0074a79aafd00ce539166f4', '二级杆内径(mm)', 'rodInsideDiameter2', null, 40, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter2', '二级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138712', null, '436802a1c0074a79aafd00ce539166f4', '二级杆长度(m)', 'rodLength2', null, 41, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodLength2', '二级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147199', null, '436802a1c0074a79aafd00ce539166f4', '三级杆类型', 'rodTypeName3', null, 42, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'rodTypeName3', '三级杆类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138713', null, '436802a1c0074a79aafd00ce539166f4', '三级杆级别', 'rodGrade3', null, 43, 1, null, '系统管理员', to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade3', '三级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138714', null, '436802a1c0074a79aafd00ce539166f4', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 44, 1, null, '系统管理员', to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter3', '三级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138715', null, '436802a1c0074a79aafd00ce539166f4', '三级杆内径(mm)', 'rodInsideDiameter3', null, 45, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter3', '三级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138716', null, '436802a1c0074a79aafd00ce539166f4', '三级杆长度(m)', 'rodLength3', null, 46, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodLength3', '三级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147200', null, '436802a1c0074a79aafd00ce539166f4', '四级杆类型', 'rodTypeName4', null, 47, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'rodTypeName4', '四级杆类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138717', null, '436802a1c0074a79aafd00ce539166f4', '四级杆级别', 'rodGrade4', null, 48, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade4', '四级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138718', null, '436802a1c0074a79aafd00ce539166f4', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 49, 1, null, '系统管理员', to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter4', '四级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138719', null, '436802a1c0074a79aafd00ce539166f4', '四级杆内径(mm)', 'rodInsideDiameter4', null, 50, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter4', '四级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138720', null, '436802a1c0074a79aafd00ce539166f4', '四级杆长度(m)', 'rodLength4', null, 51, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodLength4', '四级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138721', null, '436802a1c0074a79aafd00ce539166f4', '锚定状态', 'anchoringStateName', null, 52, 1, null, null, to_date('23-02-2019 14:46:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:46:06', 'dd-mm-yyyy hh24:mi:ss'), 'anchoringStateName', '锚定状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138722', null, '436802a1c0074a79aafd00ce539166f4', '工况干预', 'manualInterventionResult', null, 53, 1, null, null, to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'), 'manualInterventionResult', '工况干预');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138723', null, '436802a1c0074a79aafd00ce539166f4', '净毛比(小数)', 'netGrossRatio', null, 54, 1, null, '系统管理员', to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), 'netGrossRatio', '净毛比(小数)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138724', null, '436802a1c0074a79aafd00ce539166f4', '净毛值(m^3/d)', 'netGrossValue', null, 55, 1, null, null, to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), 'netGrossValue', '净毛值(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138761', null, '436802a1c0074a79aafd00ce539166f4', '反演液面校正值(MPa)', 'levelCorrectValue', null, 56, 1, null, null, to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'), 'levelCorrectValue', '反演液面校正值(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139255', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139256', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('06-03-2024 15:51:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:44', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139257', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日期', 'calDate', null, 3, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'calDate', '日期');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139258', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '功图工况', 'resultName', null, 4, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'resultName', '功图工况');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139259', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产液量(t/d)', 'liquidWeightProduction', null, 5, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'liquidWeightProduction', '日累计产液量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139260', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产油量(t/d)', 'oilWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'oilWeightProduction', '日累计产油量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145277', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产水量(t/d)', 'waterWeightProduction', null, 7, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'waterWeightProduction', '日累计产水量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139261', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产液量(m^3/d)', 'liquidVolumetricProduction', null, 8, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'liquidVolumetricProduction', '日累计产液量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139262', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产油量(m^3/d)', 'oilVolumetricProduction', null, 9, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'oilVolumetricProduction', '日累计产油量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145278', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产水量(m^3/d)', 'waterVolumetricProduction', null, 10, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'waterVolumetricProduction', '日累计产水量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139263', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '泵效(%)', 'pumpEff', null, 11, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'pumpEff', '泵效(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139264', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '系统效率(%)', 'systemEfficiency', null, 12, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'systemEfficiency', '系统效率(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139265', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '功率平衡度(%)', 'wattDegreeBalance', 'width:120', 13, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'wattDegreeBalance', '功率平衡度(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139266', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '电流平衡度(%)', 'iDegreeBalance', 'width:120', 14, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'iDegreeBalance', '电流平衡度(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139267', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日用电量(kw·h)', 'todayKWattH', 'width:120', 15, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'todayKWattH', '日用电量(kw·h)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139268', null, 'aad8b76fdaf84a1194de5ec0a4453631', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139269', null, 'aad8b76fdaf84a1194de5ec0a4453631', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('06-03-2024 15:51:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:29', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139270', null, 'aad8b76fdaf84a1194de5ec0a4453631', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:150', 3, 1, null, null, to_date('29-11-2018 18:48:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-11-2018 18:48:00', 'dd-mm-yyyy hh24:mi:ss'), 'acqTime', '采集时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139271', null, 'aad8b76fdaf84a1194de5ec0a4453631', '计算状态', 'resultStatus', null, 4, 1, null, '系统管理员', to_date('30-11-2018 10:21:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:36', 'dd-mm-yyyy hh24:mi:ss'), 'resultStatus', '计算状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139272', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产液量(t/d)', 'liquidWeightProduction', null, 5, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'liquidWeightProduction', '瞬时产液量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139275', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产油量(t/d)', 'oilWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'oilWeightProduction', '瞬时产油量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145275', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产水量(t/d)', 'waterWeightProduction', null, 7, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'waterWeightProduction', '瞬时产水量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139273', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产液量(m^3/d)', 'liquidVolumetricProduction', null, 8, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'liquidVolumetricProduction', '瞬时产液量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139274', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产油量(m^3/d)', 'oilVolumetricProduction', null, 9, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'oilVolumetricProduction', '瞬时产油量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145276', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产水量(m^3/d)', 'waterVolumetricProduction', null, 10, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'waterVolumetricProduction', '瞬时产水量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139276', null, 'aad8b76fdaf84a1194de5ec0a4453631', '转速(r/min)', 'rpm', null, 11, 1, null, null, to_date('30-11-2018 17:32:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 17:32:53', 'dd-mm-yyyy hh24:mi:ss'), 'rpm', '转速(r/min)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139277', null, 'aad8b76fdaf84a1194de5ec0a4453631', '原油密度(g/cm^3)', 'crudeoilDensity', null, 12, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'crudeoilDensity', '原油密度(g/cm^3)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139278', null, 'aad8b76fdaf84a1194de5ec0a4453631', '水密度(g/cm^3)', 'waterDensity', null, 13, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'waterDensity', '水密度(g/cm^3)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139279', null, 'aad8b76fdaf84a1194de5ec0a4453631', '天然气相对密度', 'naturalGasRelativeDensity', null, 14, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'naturalGasRelativeDensity', '天然气相对密度');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139280', null, 'aad8b76fdaf84a1194de5ec0a4453631', '饱和压力(MPa)', 'saturationPressure', null, 15, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'saturationPressure', '饱和压力(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139281', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油层中部深度(m)', 'reservoirDepth', null, 16, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'reservoirDepth', '油层中部深度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139282', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油层中部温度(摄氏度)', 'reservoirTemperature', null, 17, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'reservoirTemperature', '油层中部温度(摄氏度)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139283', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油压(MPa)', 'tubingPressure', null, 18, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'tubingPressure', '油压(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139284', null, 'aad8b76fdaf84a1194de5ec0a4453631', '套压(MPa)', 'casingPressure', null, 19, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'casingPressure', '套压(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139285', null, 'aad8b76fdaf84a1194de5ec0a4453631', '井口油温(℃)', 'wellHeadFluidTemperature', null, 20, 1, null, '系统管理员', to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), 'wellHeadFluidTemperature', '井口油温(℃)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139286', null, 'aad8b76fdaf84a1194de5ec0a4453631', '含水率(%)', 'weightWaterCut', null, 21, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'weightWaterCut', '含水率(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139287', null, 'aad8b76fdaf84a1194de5ec0a4453631', '生产气油比(m^3/t)', 'productionGasOilRatio', 'width:120', 22, 1, null, '系统管理员', to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), 'productionGasOilRatio', '生产气油比(m^3/t)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139288', null, 'aad8b76fdaf84a1194de5ec0a4453631', '动液面(m)', 'producingFluidLevel', null, 23, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'producingFluidLevel', '动液面(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139289', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵挂(m)', 'pumpSettingDepth', null, 24, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'pumpSettingDepth', '泵挂(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139290', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵筒长(m)', 'barrelLength', null, 25, 1, null, '系统管理员', to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), 'barrelLength', '泵筒长(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139291', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵级数', 'barrelSeries', null, 26, 1, null, '系统管理员', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), 'barrelSeries', '泵级数');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139292', null, 'aad8b76fdaf84a1194de5ec0a4453631', '转子直径(mm)', 'rotorDiameter', null, 27, 1, null, '系统管理员', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), 'rotorDiameter', '转子直径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139293', null, 'aad8b76fdaf84a1194de5ec0a4453631', '公称排量(ml/转)', 'qpr', null, 28, 1, null, null, to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'), 'qpr', '公称排量(ml/转)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139294', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油管内径(mm)', 'tubingStringInsideDiameter', null, 29, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'tubingStringInsideDiameter', '油管内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139295', null, 'aad8b76fdaf84a1194de5ec0a4453631', '套管内径(mm)', 'casingStringInsideDiameter', 'width:120', 30, 1, null, '系统管理员', to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), 'casingStringInsideDiameter', '套管内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147201', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆类型', 'rodTypeName1', null, 31, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'rodTypeName1', '一级杆类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139296', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆级别', 'rodGrade1', null, 32, 1, null, '系统管理员', to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade1', '一级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139297', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 33, 1, null, '系统管理员', to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter1', '一级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139298', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆内径(mm)', 'rodInsideDiameter1', null, 34, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter1', '一级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139299', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆长度(m)', 'rodLength1', null, 35, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodLength1', '一级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147202', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆类型', 'rodTypeName2', null, 36, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'rodTypeName2', '二级杆类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139300', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆级别', 'rodGrade2', null, 37, 1, null, '系统管理员', to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade2', '二级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139301', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 38, 1, null, '系统管理员', to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter2', '二级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139302', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆内径(mm)', 'rodInsideDiameter2', null, 39, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter2', '二级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139303', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆长度(m)', 'rodGrade2', null, 40, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade2', '二级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147203', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆类型', 'rodTypeName3', null, 41, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'rodTypeName3', '三级杆类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139304', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆级别', 'rodGrade3', null, 42, 1, null, '系统管理员', to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade3', '三级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139305', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 43, 1, null, '系统管理员', to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter3', '三级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139306', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆内径(mm)', 'rodInsideDiameter3', null, 44, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter3', '三级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139307', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆长度(m)', 'rodGrade3', null, 45, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade3', '三级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147204', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆类型', 'rodTypeName4', null, 46, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'rodTypeName4', '四级杆类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139308', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆级别', 'rodGrade4', null, 47, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade4', '四级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139309', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 48, 1, null, '系统管理员', to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter4', '四级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139310', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆内径(mm)', 'rodInsideDiameter4', null, 49, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter4', '四级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139311', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆长度(m)', 'rodGrade4', null, 50, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade4', '四级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139312', null, 'aad8b76fdaf84a1194de5ec0a4453631', '净毛比', 'netGrossRatio', null, 52, 1, null, '系统管理员', to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), 'netGrossRatio', '净毛比');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139313', null, 'aad8b76fdaf84a1194de5ec0a4453631', '净毛值(m^3/d)', 'netGrossValue', null, 53, 1, null, '??????????????í??±', to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), 'netGrossValue', '净毛值(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139314', null, '8122b170c0ca4deb87159c931ab251f3', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139315', null, '8122b170c0ca4deb87159c931ab251f3', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('06-03-2024 15:51:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:59', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139316', null, '8122b170c0ca4deb87159c931ab251f3', '日期', 'calDate', null, 3, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'calDate', '日期');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139317', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产液量(t/d)', 'liquidWeightProduction', null, 4, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'liquidWeightProduction', '日累计产液量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139318', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产油量(t/d)', 'oilWeightProduction', null, 5, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'oilWeightProduction', '日累计产油量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145279', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产水量(t/d)', 'waterWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'waterWeightProduction', '日累计产水量(t/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139319', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产液量(m^3/d)', 'liquidVolumetricProduction', null, 7, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'liquidVolumetricProduction', '日累计产液量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139320', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产油量(m^3/d)', 'oilVolumetricProduction', null, 8, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'oilVolumetricProduction', '日累计产油量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145280', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产水量(m^3/d)', 'waterVolumetricProduction', null, 9, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'waterVolumetricProduction', '日累计产水量(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139321', null, '8122b170c0ca4deb87159c931ab251f3', '泵效(%)', 'pumpEff', null, 10, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'pumpEff', '泵效(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139322', null, '8122b170c0ca4deb87159c931ab251f3', '系统效率(%)', 'systemEfficiency', null, 11, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'systemEfficiency', '系统效率(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139323', null, '8122b170c0ca4deb87159c931ab251f3', '日用电量(kw·h)', 'todayKWattH', 'width:120', 12, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'todayKWattH', '日用电量(kw·h)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114832', null, '8ab792e089494533be910699d426c7d5', '单位名称', 'text', 'flex:3', 1, 1, null, '系统管理员', to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'), 'text', '单位名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114834', null, '8ab792e089494533be910699d426c7d5', '排序编号', 'orgSeq', 'flex:1', 2, 1, null, '系统管理员', to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'), 'orgSeq', '排序编号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114885', null, '5ba761c1383f498f9ac97c9a8ab6d847', '序号', 'id', 'width:50', 1, 1, null, null, null, null, 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114845', null, '5ba761c1383f498f9ac97c9a8ab6d847', '用户名称', 'userName', null, 2, 1, null, '系统管理员', to_date('24-12-2021 13:39:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:27', 'dd-mm-yyyy hh24:mi:ss'), 'userName', '用户名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114847', null, '5ba761c1383f498f9ac97c9a8ab6d847', '用户账号', 'userId', null, 3, 1, null, '系统管理员', to_date('24-12-2021 13:39:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:31', 'dd-mm-yyyy hh24:mi:ss'), 'userId', '用户账号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114849', null, '5ba761c1383f498f9ac97c9a8ab6d847', '角色', 'userTypeName', null, 4, 1, null, '系统管理员', to_date('24-12-2021 13:39:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:34', 'dd-mm-yyyy hh24:mi:ss'), 'userTypeName', '角色');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114850', null, '5ba761c1383f498f9ac97c9a8ab6d847', '电话', 'userPhone', null, 5, 1, null, '系统管理员', to_date('24-12-2021 19:53:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:53:43', 'dd-mm-yyyy hh24:mi:ss'), 'userPhone', '电话');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114851', null, '5ba761c1383f498f9ac97c9a8ab6d847', '邮箱', 'userInEmail', null, 6, 1, null, '系统管理员', to_date('24-12-2021 19:53:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:53:15', 'dd-mm-yyyy hh24:mi:ss'), 'userInEmail', '邮箱');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('116092', null, '5ba761c1383f498f9ac97c9a8ab6d847', '快捷登录', 'userQuickLoginName', null, 7, 1, null, '系统管理员', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), 'userQuickLoginName', '快捷登录');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128635', null, '5ba761c1383f498f9ac97c9a8ab6d847', '接收报警短信', 'receiveSMSName', null, 8, 1, null, '系统管理员', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), 'receiveSMSName', '接收报警短信');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128636', null, '5ba761c1383f498f9ac97c9a8ab6d847', '接收报警邮件', 'receiveMailName', null, 9, 1, null, '系统管理员', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), 'receiveMailName', '接收报警邮件');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128604', null, '5ba761c1383f498f9ac97c9a8ab6d847', '状态', 'userEnableName', null, 10, 1, null, null, null, null, 'userEnableName', '状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128599', null, '5ba761c1383f498f9ac97c9a8ab6d847', '创建时间', 'userRegtime', 'width:150', 11, 1, null, null, null, null, 'userRegtime', '创建时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114886', null, '220c349e246e47a39a818023f1c97a63', '序号', 'id', 'width:50', 1, 1, null, null, null, null, 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114854', 'sys', '220c349e246e47a39a818023f1c97a63', '角色名称', 'roleName', 'flex:1', 2, 1, 'sys', '系统管理员', to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'), 'roleName', '角色名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128641', null, '220c349e246e47a39a818023f1c97a63', '角色等级', 'roleLevel', 'flex:1', 3, 1, null, null, to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'), 'roleLevel', '角色等级');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('122399', null, '220c349e246e47a39a818023f1c97a63', '数据显示级别', 'showLevel', 'flex:1', 4, 1, null, null, to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'), 'showLevel', '数据显示级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114856', null, '220c349e246e47a39a818023f1c97a63', '角色描述', 'remark', 'flex:3', 7, 1, null, '系统管理员', to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), 'remark', '角色描述');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114888', null, '87808f225d7240f68c2ab879347d818a', '序号', 'id', 'width:50', 1, 1, null, null, null, null, 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114867', 'sys', '87808f225d7240f68c2ab879347d818a', '设备名称', 'deviceName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150277', null, '87808f225d7240f68c2ab879347d818a', '应用场景', 'applicationScenariosName', null, 3, 1, null, null, to_date('29-11-2024 11:40:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-11-2024 11:40:42', 'dd-mm-yyyy hh24:mi:ss'), 'applicationScenariosName', '应用场景');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('116688', null, '87808f225d7240f68c2ab879347d818a', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'instanceName', '采控实例');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133098', null, '87808f225d7240f68c2ab879347d818a', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), 'displayInstanceName', '显示实例');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('136595', null, '87808f225d7240f68c2ab879347d818a', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), 'reportInstanceName', '报表实例');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119811', null, '87808f225d7240f68c2ab879347d818a', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'alarmInstanceName', '报警实例');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135315', null, '87808f225d7240f68c2ab879347d818a', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), 'tcpType', '下位机TCP类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114915', null, '87808f225d7240f68c2ab879347d818a', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'signInId', '注册包ID');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('136175', null, '87808f225d7240f68c2ab879347d818a', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('10-01-2023 09:00:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2023 09:00:47', 'dd-mm-yyyy hh24:mi:ss'), 'ipPort', '下位机IP端口');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114916', null, '87808f225d7240f68c2ab879347d818a', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'slave', '设备从地址');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135535', null, '87808f225d7240f68c2ab879347d818a', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), 'peakDelay', '错峰延时(s)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('132518', null, '87808f225d7240f68c2ab879347d818a', '状态', 'statusName', null, 13, 1, null, null, to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), 'statusName', '状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135215', null, '87808f225d7240f68c2ab879347d818a', '隶属单位', 'allPath', null, 14, 1, null, null, to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'), 'allPath', '隶属单位');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('116923', null, '87808f225d7240f68c2ab879347d818a', '排序编号', 'sortNum', null, 15, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), 'sortNum', '排序编号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145297', null, '87808f225d7240f68c2ab879347d818a', '更新时间', 'productionDataUpdateTime', 'width:120', 16, 1, null, null, to_date('17-11-2023 10:25:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-11-2023 10:25:42', 'dd-mm-yyyy hh24:mi:ss'), 'productionDataUpdateTime', '更新时间');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134035', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '序号', 'id', 'width:50', 1, 1, null, null, to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134037', 'sys', 'd8cd980aa8344c399b9cf11268b6ed8f', '设备名称', 'deviceName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), 'deviceName', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134039', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'instanceName', '采控实例');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134040', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), 'displayInstanceName', '显示实例');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('136600', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), 'reportInstanceName', '报表实例');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134041', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'alarmInstanceName', '报警实例');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135321', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), 'tcpType', '下位机TCP类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134042', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'signInId', '注册包ID');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('136195', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('11-01-2023 10:39:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-01-2023 10:39:54', 'dd-mm-yyyy hh24:mi:ss'), 'ipPort', '下位机IP端口');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134043', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'slave', '设备从地址');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135540', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), 'peakDelay', '错峰延时(s)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137915', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频监控路径1', 'videoUrl1', null, 13, 0, null, null, to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), 'videoUrl1', '视频监控路径1');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139579', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频密钥1', 'videoKeyName1', null, 14, 0, null, null, to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), 'videoKeyName1', '视频密钥1');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137916', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频监控路径2', 'videoUrl2', null, 15, 0, null, null, to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), 'videoUrl2', '视频监控路径2');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139580', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频密钥2', 'videoKeyName2', null, 16, 0, null, null, to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), 'videoKeyName2', '视频密钥2');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134044', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '状态', 'statusName', null, 17, 1, null, null, to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), 'statusName', '状态');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134045', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '排序编号', 'sortNum', null, 18, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), 'sortNum', '排序编号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137917', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '原油密度(g/cm^3)', 'crudeOilDensity', null, 19, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'crudeOilDensity', '原油密度(g/cm^3)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137918', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '水密度(g/cm^3)', 'waterDensity', null, 20, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'waterDensity', '水密度(g/cm^3)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137919', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '天然气相对密度', 'naturalGasRelativeDensity', null, 21, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'naturalGasRelativeDensity', '天然气相对密度');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137920', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '饱和压力(MPa)', 'saturationPressure', null, 22, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'saturationPressure', '饱和压力(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137921', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油层中部深度(m)', 'reservoirDepth', null, 23, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'reservoirDepth', '油层中部深度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137922', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油层中部温度(℃)', 'reservoirTemperature', null, 24, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'reservoirTemperature', '油层中部温度(℃)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137923', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油压(MPa)', 'tubingPressure', null, 25, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'tubingPressure', '油压(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137924', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '套压(MPa)', 'casingPressure', null, 26, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'casingPressure', '套压(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137925', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '井口温度(℃)', 'wellHeadTemperature', null, 27, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'wellHeadTemperature', '井口温度(℃)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137926', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '含水率(%)', 'waterCut', null, 28, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'waterCut', '含水率(%)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137927', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 29, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'productionGasOilRatio', '生产气油比(m^3/t)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137928', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '动液面(m)', 'producingfluidLevel', null, 30, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'producingfluidLevel', '动液面(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137929', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵挂(m)', 'pumpSettingDepth', null, 31, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'pumpSettingDepth', '泵挂(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137931', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵筒类型', 'barrelType', null, 33, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'barrelType', '泵筒类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137932', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵级别', 'pumpGrade', null, 34, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'pumpGrade', '泵级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137933', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵径(mm)', 'pumpBoreDiameter', null, 35, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'pumpBoreDiameter', '泵径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137934', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '柱塞长(m)', 'plungerLength', null, 36, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'plungerLength', '柱塞长(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137935', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油管内径(mm)', 'tubingStringInsideDiameter', null, 37, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'tubingStringInsideDiameter', '油管内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137936', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '套管内径(mm)', 'casingStringInsideDiameter', null, 38, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'casingStringInsideDiameter', '套管内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137937', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆级别', 'rodGrade1', null, 39, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade1', '一级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137938', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 40, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter1', '一级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137939', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆内径(mm)', 'rodInsideDiameter1', null, 41, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter1', '一级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137940', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆长度(m)', 'rodLength1', null, 42, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodLength1', '一级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137941', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆级别', 'rodGrade2', null, 43, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade2', '二级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137942', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 44, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter2', '二级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137943', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆内径(mm)', 'rodInsideDiameter2', null, 45, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter2', '二级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137944', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆长度(m)', 'rodLength2', null, 46, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodLength2', '二级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137945', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆级别', 'rodGrade3', null, 47, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade3', '三级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137946', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 48, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter3', '三级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137947', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆内径(mm)', 'rodInsideDiameter3', null, 49, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter3', '三级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137948', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆长度(m)', 'rodLength3', null, 50, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodLength3', '三级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137949', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆级别', 'rodGrade4', null, 51, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodGrade4', '四级杆级别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137950', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 52, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodOutsideDiameter4', '四级杆外径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137951', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆内径(mm)', 'rodInsideDiameter4', null, 53, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodInsideDiameter4', '四级杆内径(mm)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137952', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆长度(m)', 'rodLength4', null, 54, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'rodLength4', '四级杆长度(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137953', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '工况干预', 'manualInterventionResultName', null, 55, 0, null, null, to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), 'manualInterventionResultName', '工况干预');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137954', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '净毛比(小数)', 'netGrossRatio', null, 56, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'netGrossRatio', '净毛比(小数)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137955', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '净毛值(m^3/d)', 'netGrossValue', null, 57, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'netGrossValue', '净毛值(m^3/d)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137956', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '反演液面校正值(MPa)', 'levelCorrectValue', null, 58, 0, null, null, to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), 'levelCorrectValue', '反演液面校正值(MPa)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137957', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '抽油机厂家', 'manufacturer', null, 59, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'manufacturer', '抽油机厂家');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137958', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '抽油机型号', 'model', null, 60, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'model', '抽油机型号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137959', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '铭牌冲程', 'stroke', null, 61, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'stroke', '铭牌冲程');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137960', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '平衡块重量(kN)', 'balanceWeight', null, 68, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'balanceWeight', '平衡块重量(kN)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137961', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '平衡块位置(m)', 'balancePosition', null, 69, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'balancePosition', '平衡块位置(m)');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150711', null, '2b4cd8cb8c6844769c66b038246c27bf', '序号', 'id', 'width:50', 1, 1, null, null, to_date('17-09-2021 18:29:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2021 18:29:03', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150712', 'sys', '2b4cd8cb8c6844769c66b038246c27bf', '单位名称', 'orgName', null, 2, 0, null, '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), '
orgName', '
单位名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150713', null, '2b4cd8cb8c6844769c66b038246c27bf', '设备名称', 'deviceName', null, 3, 1, null, '系统管理员', to_date('13-10-2021 20:47:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 20:47:50', 'dd-mm-yyyy hh24:mi:ss'), '
deviceName', '
设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150714', null, '2b4cd8cb8c6844769c66b038246c27bf', '短信设备实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), '
instanceName', '
短信设备实例');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150715', null, '2b4cd8cb8c6844769c66b038246c27bf', '注册包ID', 'signInId', null, 5, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), '
signInId', '
注册包ID');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150716', null, '2b4cd8cb8c6844769c66b038246c27bf', '排序编号', 'sortNum', null, 6, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), '
sortNum', '
排序编号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146659', null, '1404100741bc42799be5b7cbebf4b649', '序号', 'id', 'width:50', 1, 1, null, null, to_date('10-11-2021 14:21:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-11-2021 14:21:46', 'dd-mm-yyyy hh24:mi:ss'), 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146660', null, '1404100741bc42799be5b7cbebf4b649', '设备名称', 'name', null, 2, 1, 'sys', null, to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), 'name', '设备名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146661', null, '1404100741bc42799be5b7cbebf4b649', '类型', 'type', null, 3, 1, 'sys', null, to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), 'type', '类型');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147076', null, '1404100741bc42799be5b7cbebf4b649', '厂家', 'manufacturer', null, 4, 1, null, null, to_date('22-03-2024 16:59:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2024 16:59:49', 'dd-mm-yyyy hh24:mi:ss'), 'manufacturer', '厂家');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146662', null, '1404100741bc42799be5b7cbebf4b649', '规格型号', 'model', null, 5, 1, null, null, to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'model', '规格型号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146663', null, '1404100741bc42799be5b7cbebf4b649', '备注', 'remark', null, 6, 1, null, null, to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'remark', '备注');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146664', null, '1404100741bc42799be5b7cbebf4b649', '排序编号', 'sort', null, 7, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'sort', '排序编号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114837', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块名称', 'text', 'flex:2', 1, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), 'text', '模块名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114838', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块简介', 'mdShowname', 'flex:2', 2, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), 'mdShowname', '模块简介');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114842', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块图标', 'mdIcon', 'flex:1', 6, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), 'mdIcon', '模块图标');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114843', null, 'b6ef8f3a49094768b3231d5678fc9cbc', '模块类别', 'mdTypeName', 'flex:1', 7, 1, null, '系统管理员', to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'), 'mdTypeName', '模块类别');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114844', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块排序', 'mdSeq', 'flex:1', 8, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), 'mdSeq', '模块排序');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114884', null, 'b8a408839dd8498d9a19fc65f7406ed4', '序号', 'id', 'width:50', 1, 1, null, null, null, null, 'id', '序号');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114827', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典名称', 'name', 'flex:2', 2, 1, null, '系统管理员', to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'), 'name', '字典名称');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114828', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典代码', 'code', 'flex:3', 3, 1, null, '系统管理员', to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'), 'code', '字典代码');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114829', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '字典顺序', 'sorts', 'flex:1', 4, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), 'sorts', '字典顺序');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147379', null, 'b8a408839dd8498d9a19fc65f7406ed4', '隶属模块', 'moduleName', 'flex:1', 5, 1, null, null, to_date('25-04-2024 14:06:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-04-2024 14:06:09', 'dd-mm-yyyy hh24:mi:ss'), 'moduleName', '隶属模块');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114830', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '创建人', 'creator', 'flex:1', 6, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), 'creator', '创建人');

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114831', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '创建时间', 'updatetime', 'flex:3', 7, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), 'updatetime', '创建时间');