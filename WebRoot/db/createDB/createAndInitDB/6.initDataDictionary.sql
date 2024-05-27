/*==============================================================*/
/* 初始化tbl_dist_name数据                                          */
/*==============================================================*/
insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('7f13446d19b4497986980fa16a750f95', null, '实时监控_设备概览', 'realTimeMonitoring_Overview', 11101, 0, '超级管理员', '超级管理员', to_date('02-11-2023 14:37:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2023 14:37:40', 'dd-mm-yyyy hh24:mi:ss'), 1998);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('d4205cd00a994817a51c2cab6d5fa0ab', null, '实时监控_设备信息', 'realTimeMonitoring_DeviceInfo', 11102, 0, '超级管理员', '超级管理员', to_date('26-05-2022 20:07:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 20:07:26', 'dd-mm-yyyy hh24:mi:ss'), 1998);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('cd7b24562b924d19b556de31256e22a1', null, '历史查询_设备历史数据', 'historyQuery_HistoryData', 12101, 0, '超级管理员', '超级管理员', to_date('02-11-2023 14:36:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2023 14:36:33', 'dd-mm-yyyy hh24:mi:ss'), 2018);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('0d4297cb4db44bb3a9a3d5d983007039', 'system', '历史查询_图形叠加', 'historyQuery_FESDiagramOverlay', 12103, 0, '超级管理员', '超级管理员', to_date('26-04-2022 10:17:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-04-2022 10:17:42', 'dd-mm-yyyy hh24:mi:ss'), 2018);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('cdd198534d5849b7a27054e0f2593ff3', null, '故障查询_通信状态报警', 'alarmQuery_CommStatusAlarm', 14101, 0, '系统管理员', '系统管理员', to_date('02-11-2021 15:20:57', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2021 15:20:57', 'dd-mm-yyyy hh24:mi:ss'), 2058);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('e2924366ab174d4b9a096be969934985', 'system', '故障查询_数值量报警', 'alarmQuery_NumericValueAlarm', 14102, 0, '系统管理员', '系统管理员', to_date('16-09-2021 13:50:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2021 13:50:40', 'dd-mm-yyyy hh24:mi:ss'), 2058);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('b09082f4272e4768994db398e14bc3f2', null, '故障查询_枚举量报警', 'alarmQuery_EnumValueAlarm', 14103, 0, '超级管理员', '超级管理员', to_date('05-03-2024 18:07:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:07:07', 'dd-mm-yyyy hh24:mi:ss'), 2058);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('b71c1a2c9d574fe482080a56c7c780a9', null, '故障查询_开关量报警', 'alarmQuery_SwitchingValueAlarm', 14104, 0, '系统管理员', '系统管理员', to_date('07-10-2021 19:06:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-10-2021 19:06:41', 'dd-mm-yyyy hh24:mi:ss'), 2058);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('ad646d19fcaa4fbd9077dbf7a826b107', 'system', '日志查询_设备操作日志', 'logQuery_DeviceOperationLog', 15101, 0, '系统管理员', '系统管理员', to_date('07-09-2021 17:05:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-09-2021 17:05:31', 'dd-mm-yyyy hh24:mi:ss'), 2038);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('167aeb3aca384afda8655d63aedee484', 'system', '日志查询_系统日志', 'logQuery_SystemLog', 15102, 0, '系统管理员', '系统管理员', to_date('07-09-2021 19:04:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-09-2021 19:04:19', 'dd-mm-yyyy hh24:mi:ss'), 2038);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('436802a1c0074a79aafd00ce539166f4', 'system', '计算维护_抽油机井单条记录', 'calculateManager_RPCSingleRecord', 16101, 0, '超级管理员', '超级管理员', to_date('24-04-2023 09:45:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 09:45:19', 'dd-mm-yyyy hh24:mi:ss'), 2179);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('cf1c0981f31242f9b3e84810bdc0a19f', 'system', '计算维护_抽油机井记录汇总', 'calculateManager_RPCTotalRecord', 16102, 0, '超级管理员', '超级管理员', to_date('07-05-2022 19:56:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-05-2022 19:56:03', 'dd-mm-yyyy hh24:mi:ss'), 2179);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('aad8b76fdaf84a1194de5ec0a4453631', null, '计算维护_螺杆泵井单条记录', 'calculateManager_PCPSingleRecord', 16103, 0, '系统管理员', '系统管理员', to_date('30-11-2018 16:40:57', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:40:57', 'dd-mm-yyyy hh24:mi:ss'), 2179);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('8122b170c0ca4deb87159c931ab251f3', 'system', '计算维护_螺杆泵井记录汇总', 'calculateManager_PCPTotalRecord', 16104, 0, '超级管理员', '超级管理员', to_date('07-05-2022 19:56:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-05-2022 19:56:38', 'dd-mm-yyyy hh24:mi:ss'), 2179);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('8ab792e089494533be910699d426c7d5', null, '组织用户_单位列表', 'orgAndUser_OrgManage', 21101, 0, '系统管理员', '系统管理员', to_date('24-12-2021 09:33:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 09:33:43', 'dd-mm-yyyy hh24:mi:ss'), 24);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('5ba761c1383f498f9ac97c9a8ab6d847', null, '组织用户_用户列表', 'orgAndUser_UserManage', 21102, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:45:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:45:52', 'dd-mm-yyyy hh24:mi:ss'), 24);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('220c349e246e47a39a818023f1c97a63', null, '角色管理_角色列表', 'role_RoleManage', 21103, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:46:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:46:02', 'dd-mm-yyyy hh24:mi:ss'), 29);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('87808f225d7240f68c2ab879347d818a', null, '井名信息_设备列表', 'deviceInfo_DeviceManager', 22101, 0, '超级管理员', '超级管理员', to_date('09-10-2023 08:57:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-10-2023 08:57:41', 'dd-mm-yyyy hh24:mi:ss'), 34);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('d8cd980aa8344c399b9cf11268b6ed8f', null, '井名信息_设备批量添加', 'deviceInfo_DeviceBatchAdd', 22102, 0, '超级管理员', '超级管理员', to_date('18-01-2024 11:39:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-01-2024 11:39:22', 'dd-mm-yyyy hh24:mi:ss'), 34);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('1404100741bc42799be5b7cbebf4b649', 'system', '辅件设备_辅件设备列表', 'auxiliaryDeviceManager', 22105, 0, '超级管理员', '超级管理员', to_date('08-01-2024 17:00:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-01-2024 17:00:55', 'dd-mm-yyyy hh24:mi:ss'), 34);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('b6ef8f3a49094768b3231d5678fc9cbc', null, '模块配置_模块列表', 'module_ModuleManage', 23101, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'), 26);

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID)
values ('b8a408839dd8498d9a19fc65f7406ed4', null, '字典配置_字典列表', 'dictionary_DataDictionaryManage', 23102, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'), 894);

/*==============================================================*/
/* 初始化tbl_dist_item数据                                          */
/*==============================================================*/
insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118836', null, '7f13446d19b4497986980fa16a750f95', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118837', null, '7f13446d19b4497986980fa16a750f95', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('22-02-2024 08:41:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-02-2024 08:41:59', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128173', null, '7f13446d19b4497986980fa16a750f95', '设备类型', 'deviceTypeName', null, 3, 1, null, null, to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118931', null, '7f13446d19b4497986980fa16a750f95', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 4, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118838', null, '7f13446d19b4497986980fa16a750f95', '通信状态', 'commStatusName', null, 5, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134390', null, '7f13446d19b4497986980fa16a750f95', '在线时间(h)', 'commTime', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134391', null, '7f13446d19b4497986980fa16a750f95', '在线时率(小数)', 'commTimeEfficiency', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134392', null, '7f13446d19b4497986980fa16a750f95', '在线区间', 'commRange', null, 8, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133835', null, '7f13446d19b4497986980fa16a750f95', '运行状态', 'runStatusName', null, 9, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134393', null, '7f13446d19b4497986980fa16a750f95', '运行时间(h)', 'runTime', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134394', null, '7f13446d19b4497986980fa16a750f95', '运行时率(小数)', 'runTimeEfficiency', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134395', null, '7f13446d19b4497986980fa16a750f95', '运行区间', 'runRange', null, 12, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139475', null, '7f13446d19b4497986980fa16a750f95', '工况', 'resultName', null, 13, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139476', null, '7f13446d19b4497986980fa16a750f95', '优化建议', 'optimizationSuggestion', null, 14, 1, null, null, to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139595', null, '7f13446d19b4497986980fa16a750f95', '理论排量(m^3/d)', 'theoreticalProduction', null, 15, 1, null, null, to_date('10-07-2023 13:42:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 13:42:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139477', null, '7f13446d19b4497986980fa16a750f95', '瞬时产液量(t/d)', 'liquidWeightProduction', null, 16, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139596', null, '7f13446d19b4497986980fa16a750f95', '瞬时产油量(t/d)', 'oilWeightProduction', null, 17, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139597', null, '7f13446d19b4497986980fa16a750f95', '瞬时产水量(t/d)', 'waterWeightProduction', null, 18, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139478', null, '7f13446d19b4497986980fa16a750f95', '日累计产液量(t/d))', 'liquidWeightProduction_L', null, 19, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145259', null, '7f13446d19b4497986980fa16a750f95', '日累计产油量(t/d))', 'oilWeightProduction_L', null, 20, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145260', null, '7f13446d19b4497986980fa16a750f95', '日累计产水量(t/d))', 'waterWeightProduction_L', null, 21, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139598', null, '7f13446d19b4497986980fa16a750f95', '重量含水率(%)', 'weightWaterCut', null, 22, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139599', null, '7f13446d19b4497986980fa16a750f95', '柱塞有效冲程计算产量(t/d)', 'availablePlungerStrokeProd_w', null, 23, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139600', null, '7f13446d19b4497986980fa16a750f95', '泵间隙漏失量(t/d)', 'pumpClearanceleakProd_w', null, 24, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139601', null, '7f13446d19b4497986980fa16a750f95', '游动凡尔漏失量(t/d)', 'tvleakWeightProduction', null, 25, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139602', null, '7f13446d19b4497986980fa16a750f95', '固定凡尔漏失量(t/d)', 'svleakWeightProduction', null, 26, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139603', null, '7f13446d19b4497986980fa16a750f95', '气影响(t/d)', 'gasInfluenceProd_w', null, 27, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139479', null, '7f13446d19b4497986980fa16a750f95', '瞬时产液量(m^3/d)', 'liquidVolumetricProduction', null, 28, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139604', null, '7f13446d19b4497986980fa16a750f95', '瞬时产油量(m^3/d)', 'oilVolumetricProduction', null, 29, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139605', null, '7f13446d19b4497986980fa16a750f95', '瞬时产水量(m^3/d)', 'waterVolumetricProduction', null, 30, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139480', null, '7f13446d19b4497986980fa16a750f95', '日累计产液量(m^3/d))', 'liquidVolumetricProduction_L', null, 31, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145257', null, '7f13446d19b4497986980fa16a750f95', '日累计产油量(m^3/d))', 'oilVolumetricProduction_L', null, 32, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145258', null, '7f13446d19b4497986980fa16a750f95', '日累计产水量(m^3/d))', 'waterVolumetricProduction_L', null, 33, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139606', null, '7f13446d19b4497986980fa16a750f95', '体积含水率(%)', 'waterCut', null, 34, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139607', null, '7f13446d19b4497986980fa16a750f95', '柱塞有效冲程计算产量(m^3/d)', 'availablePlungerStrokeProd_v', null, 35, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139608', null, '7f13446d19b4497986980fa16a750f95', '泵间隙漏失量(m^3/d)', 'pumpClearanceleakProd_v', null, 36, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139609', null, '7f13446d19b4497986980fa16a750f95', '游动凡尔漏失量(m^3/d)', 'tvleakVolumetricProduction', null, 37, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139610', null, '7f13446d19b4497986980fa16a750f95', '固定凡尔漏失量(m^3/d)', 'svleakVolumetricProduction', null, 38, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139611', null, '7f13446d19b4497986980fa16a750f95', '气影响(m^3/d)', 'gasInfluenceProd_v', null, 39, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139481', null, '7f13446d19b4497986980fa16a750f95', '最大载荷(kN)', 'FMax', null, 40, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139482', null, '7f13446d19b4497986980fa16a750f95', '最小载荷(kN)', 'FMin', null, 41, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139612', null, '7f13446d19b4497986980fa16a750f95', '冲程(m)', 'stroke', null, 42, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139613', null, '7f13446d19b4497986980fa16a750f95', '冲次(次/min)', 'SPM', null, 43, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139483', null, '7f13446d19b4497986980fa16a750f95', '充满系数', 'fullnessCoefficient', null, 44, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139502', null, '7f13446d19b4497986980fa16a750f95', '柱塞冲程(m)', 'plungerStroke', null, 45, 1, null, null, to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139503', null, '7f13446d19b4497986980fa16a750f95', '柱塞有效冲程(m)', 'availablePlungerStroke', null, 46, 1, null, null, to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139614', null, '7f13446d19b4497986980fa16a750f95', '理论上载荷(kN)', 'upperLoadLine', null, 47, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139615', null, '7f13446d19b4497986980fa16a750f95', '理论下载荷(kN)', 'lowerLoadLine', null, 48, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139488', null, '7f13446d19b4497986980fa16a750f95', '有功功率(kW)', 'averageWatt', null, 49, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139489', null, '7f13446d19b4497986980fa16a750f95', '光杆功率(kW)', 'polishrodPower', null, 50, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139490', null, '7f13446d19b4497986980fa16a750f95', '水功率(kW)', 'waterPower', null, 51, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139491', null, '7f13446d19b4497986980fa16a750f95', '地面效率(%)', 'surfaceSystemEfficiency', null, 52, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139492', null, '7f13446d19b4497986980fa16a750f95', '井下效率(%)', 'welldownSystemEfficiency', null, 53, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139493', null, '7f13446d19b4497986980fa16a750f95', '系统效率(%)', 'systemEfficiency', null, 54, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139494', null, '7f13446d19b4497986980fa16a750f95', '吨液百米耗电量(kW· h/100m· t)', 'energyper100mlift', null, 55, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139616', null, '7f13446d19b4497986980fa16a750f95', '功图面积', 'area', null, 56, 1, null, null, to_date('10-07-2023 14:25:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:25:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139621', null, '7f13446d19b4497986980fa16a750f95', '抽油杆伸长量(m)', 'rodFlexLength', null, 57, 1, null, null, to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139622', null, '7f13446d19b4497986980fa16a750f95', '油管伸缩量(m)', 'tubingFlexLength', null, 58, 1, null, null, to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139623', null, '7f13446d19b4497986980fa16a750f95', '惯性载荷增量(m)', 'inertiaLength', null, 59, 1, null, null, to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139617', null, '7f13446d19b4497986980fa16a750f95', '冲程损失系数(%)', 'pumpEff1', null, 60, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139618', null, '7f13446d19b4497986980fa16a750f95', '充满系数(%)', 'pumpEff2', null, 61, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139619', null, '7f13446d19b4497986980fa16a750f95', '间隙漏失系数(%)', 'pumpEff3', null, 62, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139620', null, '7f13446d19b4497986980fa16a750f95', '液体收缩系数(%)', 'pumpEff4', null, 63, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139495', null, '7f13446d19b4497986980fa16a750f95', '总泵效(%)', 'pumpEff', null, 64, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139624', null, '7f13446d19b4497986980fa16a750f95', '上冲程最大电流(A)', 'upStrokeIMax', null, 65, 1, null, null, to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139625', null, '7f13446d19b4497986980fa16a750f95', '下冲程最大电流(A)', 'downStrokeIMax', null, 66, 1, null, null, to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139496', null, '7f13446d19b4497986980fa16a750f95', '电流平衡度(%)', 'iDegreeBalance', null, 67, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139626', null, '7f13446d19b4497986980fa16a750f95', '上冲程最大功率(kW)', 'upStrokeWattMax', null, 68, 1, null, null, to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139627', null, '7f13446d19b4497986980fa16a750f95', '下冲程最大功率(kW)', 'downStrokeWattMax', null, 69, 1, null, null, to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139497', null, '7f13446d19b4497986980fa16a750f95', '功率平衡度(%)', 'wattDegreeBalance', null, 70, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139498', null, '7f13446d19b4497986980fa16a750f95', '移动距离(cm)', 'deltaradius', null, 71, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139501', null, '7f13446d19b4497986980fa16a750f95', '日用电量(kW·h)', 'todayKWattH', null, 72, 1, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139628', null, '7f13446d19b4497986980fa16a750f95', '动液面(m)', 'producingfluidLevel', null, 73, 1, null, null, to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139629', null, '7f13446d19b4497986980fa16a750f95', '反演液面校正值(MPa)', 'levelCorrectValue', null, 74, 1, null, null, to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139500', null, '7f13446d19b4497986980fa16a750f95', '反演动液面(m)', 'calcProducingfluidLevel', null, 75, 1, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139499', null, '7f13446d19b4497986980fa16a750f95', '液面校正差值(MPa)', 'levelDifferenceValue', null, 76, 1, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139644', null, '7f13446d19b4497986980fa16a750f95', '泵挂(m)', 'pumpSettingDepth', null, 77, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139645', null, '7f13446d19b4497986980fa16a750f95', '沉没度(m)', 'submergence', null, 78, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139646', null, '7f13446d19b4497986980fa16a750f95', '泵径(mm)', 'pumpBoreDiameter', null, 79, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139640', null, '7f13446d19b4497986980fa16a750f95', '油压(MPa)', 'tubingPressure', null, 80, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139641', null, '7f13446d19b4497986980fa16a750f95', '套压(MPa)', 'casingPressure', null, 81, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139642', null, '7f13446d19b4497986980fa16a750f95', '井口温度(℃)', 'wellHeadTemperature', null, 82, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139643', null, '7f13446d19b4497986980fa16a750f95', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 83, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139630', null, '7f13446d19b4497986980fa16a750f95', '泵入口压力(MPa)', 'pumpIntakeP', null, 84, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139631', null, '7f13446d19b4497986980fa16a750f95', '泵入口温度(℃)', 'pumpIntakeT', null, 85, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139632', null, '7f13446d19b4497986980fa16a750f95', '泵入口就地气液比(m^3/m^3)', 'pumpIntakeGOL', null, 86, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139633', null, '7f13446d19b4497986980fa16a750f95', '泵入口粘度(mPa·s)', 'pumpIntakeVisl', null, 87, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139634', null, '7f13446d19b4497986980fa16a750f95', '泵入口原油体积系数', 'pumpIntakeBo', null, 88, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139635', null, '7f13446d19b4497986980fa16a750f95', '泵出口压力(MPa)', 'pumpOutletP', null, 89, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139636', null, '7f13446d19b4497986980fa16a750f95', '泵出口温度(℃)', 'pumpOutletT', null, 90, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139637', null, '7f13446d19b4497986980fa16a750f95', '泵出口就地气液比(m^3/m^3)', 'pumpOutletGOL', null, 91, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139638', null, '7f13446d19b4497986980fa16a750f95', '泵出口粘度(mPa·s)', 'pumpOutletVisl', null, 92, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139639', null, '7f13446d19b4497986980fa16a750f95', '泵出口原油体积系数', 'pumpOutletBo', null, 93, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146057', null, '7f13446d19b4497986980fa16a750f95', '转速(1rpm)', 'C_CLOUMN1', null, 337, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146058', null, '7f13446d19b4497986980fa16a750f95', '油压(MPa)', 'C_CLOUMN2', null, 338, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146059', null, '7f13446d19b4497986980fa16a750f95', '套压(MPa)', 'C_CLOUMN3', null, 339, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146060', null, '7f13446d19b4497986980fa16a750f95', '井口温度(℃)', 'C_CLOUMN4', null, 340, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146061', null, '7f13446d19b4497986980fa16a750f95', '运行状态', 'C_CLOUMN5', null, 341, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146062', null, '7f13446d19b4497986980fa16a750f95', '启停控制', 'C_CLOUMN6', null, 342, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146063', null, '7f13446d19b4497986980fa16a750f95', '含水率(%)', 'C_CLOUMN7', null, 343, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146064', null, '7f13446d19b4497986980fa16a750f95', '动液面(m)', 'C_CLOUMN8', null, 344, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146065', null, '7f13446d19b4497986980fa16a750f95', '光杆温度(℃)', 'C_CLOUMN9', null, 345, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146066', null, '7f13446d19b4497986980fa16a750f95', '盘根盒温度(℃)', 'C_CLOUMN10', null, 346, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146067', null, '7f13446d19b4497986980fa16a750f95', 'A相电流(A)', 'C_CLOUMN11', null, 347, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146068', null, '7f13446d19b4497986980fa16a750f95', 'B相电流(A)', 'C_CLOUMN12', null, 348, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146069', null, '7f13446d19b4497986980fa16a750f95', 'C相电流(V)', 'C_CLOUMN13', null, 349, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146070', null, '7f13446d19b4497986980fa16a750f95', 'A相电压(V)', 'C_CLOUMN14', null, 350, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146071', null, '7f13446d19b4497986980fa16a750f95', 'B相电压(V)', 'C_CLOUMN15', null, 351, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146072', null, '7f13446d19b4497986980fa16a750f95', 'C相电压(V)', 'C_CLOUMN16', null, 352, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146073', null, '7f13446d19b4497986980fa16a750f95', '有功功耗(kW·h)', 'C_CLOUMN17', null, 353, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146074', null, '7f13446d19b4497986980fa16a750f95', '无功功耗(kVar·h)', 'C_CLOUMN18', null, 354, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146075', null, '7f13446d19b4497986980fa16a750f95', '有功功率(kW)', 'C_CLOUMN19', null, 355, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146076', null, '7f13446d19b4497986980fa16a750f95', '无功功率(kVar)', 'C_CLOUMN20', null, 356, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146077', null, '7f13446d19b4497986980fa16a750f95', '反向功率(kW)', 'C_CLOUMN21', null, 357, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146078', null, '7f13446d19b4497986980fa16a750f95', '功率因数', 'C_CLOUMN22', null, 358, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146079', null, '7f13446d19b4497986980fa16a750f95', '变频设置频率(Hz)', 'C_CLOUMN23', null, 359, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146080', null, '7f13446d19b4497986980fa16a750f95', '变频运行频率(Hz)', 'C_CLOUMN24', null, 360, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146081', null, '7f13446d19b4497986980fa16a750f95', '功图采集间隔(min)', 'C_CLOUMN25', null, 361, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146082', null, '7f13446d19b4497986980fa16a750f95', '手动采集功图', 'C_CLOUMN26', null, 362, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146083', null, '7f13446d19b4497986980fa16a750f95', '功图设置点数', 'C_CLOUMN27', null, 363, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146084', null, '7f13446d19b4497986980fa16a750f95', '功图实测点数', 'C_CLOUMN28', null, 364, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146085', null, '7f13446d19b4497986980fa16a750f95', '功图采集时间', 'C_CLOUMN29', null, 365, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146086', null, '7f13446d19b4497986980fa16a750f95', '冲次(次/min)', 'C_CLOUMN30', null, 366, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146087', null, '7f13446d19b4497986980fa16a750f95', '冲程(m)', 'C_CLOUMN31', null, 367, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146088', null, '7f13446d19b4497986980fa16a750f95', '功图数据-位移(m)', 'C_CLOUMN32', null, 368, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146089', null, '7f13446d19b4497986980fa16a750f95', '功图数据-载荷(kN)', 'C_CLOUMN33', null, 369, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146090', null, '7f13446d19b4497986980fa16a750f95', '功图数据-电流(A)', 'C_CLOUMN34', null, 370, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146091', null, '7f13446d19b4497986980fa16a750f95', '功图数据-功率(kW)', 'C_CLOUMN35', null, 371, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146092', null, '7f13446d19b4497986980fa16a750f95', '煤层顶板深(米)', 'C_CLOUMN36', null, 372, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146093', null, '7f13446d19b4497986980fa16a750f95', '压力计安装深度(米)', 'C_CLOUMN37', null, 373, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146094', null, '7f13446d19b4497986980fa16a750f95', '井下压力计-压力(MPa)', 'C_CLOUMN38', null, 374, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146095', null, '7f13446d19b4497986980fa16a750f95', '井口套压(MPa)', 'C_CLOUMN39', null, 375, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146096', null, '7f13446d19b4497986980fa16a750f95', '系统压力(KPa)', 'C_CLOUMN40', null, 376, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146097', null, '7f13446d19b4497986980fa16a750f95', '产气量累计(m3)', 'C_CLOUMN41', null, 377, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146098', null, '7f13446d19b4497986980fa16a750f95', '产气量瞬时(m3/h)', 'C_CLOUMN42', null, 378, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146099', null, '7f13446d19b4497986980fa16a750f95', '气体温度(℃)', 'C_CLOUMN43', null, 379, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146100', null, '7f13446d19b4497986980fa16a750f95', '产水量累计(m3)', 'C_CLOUMN44', null, 380, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146101', null, '7f13446d19b4497986980fa16a750f95', '产水量瞬时(m3/h)', 'C_CLOUMN45', null, 381, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146102', null, '7f13446d19b4497986980fa16a750f95', '井下压力计-温度(℃)', 'C_CLOUMN46', null, 382, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146103', null, '7f13446d19b4497986980fa16a750f95', '气体流量计通讯状态', 'C_CLOUMN47', null, 383, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146104', null, '7f13446d19b4497986980fa16a750f95', '水流量计通讯状态', 'C_CLOUMN48', null, 384, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146105', null, '7f13446d19b4497986980fa16a750f95', '井下压力计/液面仪通讯状态', 'C_CLOUMN49', null, 385, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146106', null, '7f13446d19b4497986980fa16a750f95', '变频器通讯状态', 'C_CLOUMN50', null, 386, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146107', null, '7f13446d19b4497986980fa16a750f95', '当前目标值(m)', 'C_CLOUMN51', null, 387, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146108', null, '7f13446d19b4497986980fa16a750f95', '频率控制方式', 'C_CLOUMN52', null, 388, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146109', null, '7f13446d19b4497986980fa16a750f95', '变频器设置频率(Hz)', 'C_CLOUMN53', null, 389, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146110', null, '7f13446d19b4497986980fa16a750f95', '变频器运行频率(Hz)', 'C_CLOUMN54', null, 390, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146111', null, '7f13446d19b4497986980fa16a750f95', '变频器故障状态', 'C_CLOUMN55', null, 391, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146112', null, '7f13446d19b4497986980fa16a750f95', '变频器输出电流(A)', 'C_CLOUMN56', null, 392, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146113', null, '7f13446d19b4497986980fa16a750f95', '变频器输出电压(V)', 'C_CLOUMN57', null, 393, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146114', null, '7f13446d19b4497986980fa16a750f95', '变频器厂家代码', 'C_CLOUMN58', null, 394, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146115', null, '7f13446d19b4497986980fa16a750f95', '变频器状态字1', 'C_CLOUMN59', null, 395, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146116', null, '7f13446d19b4497986980fa16a750f95', '变频器状态字2', 'C_CLOUMN60', null, 396, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146117', null, '7f13446d19b4497986980fa16a750f95', '本地旋钮位置', 'C_CLOUMN61', null, 397, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146118', null, '7f13446d19b4497986980fa16a750f95', '母线电压(V)', 'C_CLOUMN62', null, 398, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146119', null, '7f13446d19b4497986980fa16a750f95', '设定频率反馈(Hz)', 'C_CLOUMN63', null, 399, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146120', null, '7f13446d19b4497986980fa16a750f95', '10Hz对应冲次/转速预设值(次/100转)', 'C_CLOUMN64', null, 400, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146121', null, '7f13446d19b4497986980fa16a750f95', '50Hz对应冲次/转速预设值(次/100转)', 'C_CLOUMN65', null, 401, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146122', null, '7f13446d19b4497986980fa16a750f95', '冲次/转速设定值(次/100转)', 'C_CLOUMN66', null, 402, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146123', null, '7f13446d19b4497986980fa16a750f95', '修正后井底流压(Mpa)', 'C_CLOUMN67', null, 403, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146124', null, '7f13446d19b4497986980fa16a750f95', '计算液柱高度(m)', 'C_CLOUMN68', null, 404, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146125', null, '7f13446d19b4497986980fa16a750f95', '计算近1小时液柱下降速度(m/小时)', 'C_CLOUMN69', null, 405, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146126', null, '7f13446d19b4497986980fa16a750f95', '排采模式', 'C_CLOUMN70', null, 406, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146127', null, '7f13446d19b4497986980fa16a750f95', '自动排采状态', 'C_CLOUMN71', null, 407, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146128', null, '7f13446d19b4497986980fa16a750f95', '自动排采-最小频率(Hz)', 'C_CLOUMN72', null, 408, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146129', null, '7f13446d19b4497986980fa16a750f95', '自动排采-最大频率(Hz)', 'C_CLOUMN73', null, 409, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146130', null, '7f13446d19b4497986980fa16a750f95', '最大步长幅度限制(Hz)', 'C_CLOUMN74', null, 410, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146131', null, '7f13446d19b4497986980fa16a750f95', '最短调整时间间隔(秒)', 'C_CLOUMN75', null, 411, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146132', null, '7f13446d19b4497986980fa16a750f95', '自动重启延时(秒)', 'C_CLOUMN76', null, 412, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146133', null, '7f13446d19b4497986980fa16a750f95', '自动重启次数(次)', 'C_CLOUMN77', null, 413, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146134', null, '7f13446d19b4497986980fa16a750f95', '井底流压波动报警值(Kpa)', 'C_CLOUMN78', null, 414, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146135', null, '7f13446d19b4497986980fa16a750f95', '定降液-目标定降（每日）(m)', 'C_CLOUMN79', null, 415, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146136', null, '7f13446d19b4497986980fa16a750f95', '定降液-液柱低停机值(m)', 'C_CLOUMN80', null, 416, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146137', null, '7f13446d19b4497986980fa16a750f95', '定降液-液柱低报警值(m)', 'C_CLOUMN81', null, 417, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146138', null, '7f13446d19b4497986980fa16a750f95', '定降液-液柱重启值(m)', 'C_CLOUMN82', null, 418, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146139', null, '7f13446d19b4497986980fa16a750f95', '定降液-液柱高报警值(m)', 'C_CLOUMN83', null, 419, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146140', null, '7f13446d19b4497986980fa16a750f95', '定降液-P参数', 'C_CLOUMN84', null, 420, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146141', null, '7f13446d19b4497986980fa16a750f95', '定降液-I参数', 'C_CLOUMN85', null, 421, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146142', null, '7f13446d19b4497986980fa16a750f95', '定降液-D参数', 'C_CLOUMN86', null, 422, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146143', null, '7f13446d19b4497986980fa16a750f95', '定流压-目标流压(Kpa)', 'C_CLOUMN87', null, 423, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146144', null, '7f13446d19b4497986980fa16a750f95', '定流压-流压低停机值(Kpa)', 'C_CLOUMN88', null, 424, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146145', null, '7f13446d19b4497986980fa16a750f95', '定流压-流压低报警值(Kpa)', 'C_CLOUMN89', null, 425, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146146', null, '7f13446d19b4497986980fa16a750f95', '定流压-流压重启值(Kpa)', 'C_CLOUMN90', null, 426, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146147', null, '7f13446d19b4497986980fa16a750f95', '定流压-流压高报警值(Kpa)', 'C_CLOUMN91', null, 427, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146148', null, '7f13446d19b4497986980fa16a750f95', '定流压-P参数', 'C_CLOUMN92', null, 428, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146149', null, '7f13446d19b4497986980fa16a750f95', '定流压-I参数', 'C_CLOUMN93', null, 429, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146150', null, '7f13446d19b4497986980fa16a750f95', '定流压-D参数', 'C_CLOUMN94', null, 430, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146151', null, '7f13446d19b4497986980fa16a750f95', 'IGBT 温度(1℃)', 'C_CLOUMN95', null, 431, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146152', null, '7f13446d19b4497986980fa16a750f95', '输出电流(0.1A)', 'C_CLOUMN96', null, 432, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146153', null, '7f13446d19b4497986980fa16a750f95', '输出电压(1V)', 'C_CLOUMN97', null, 433, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146154', null, '7f13446d19b4497986980fa16a750f95', '输出功率(0.1KW)', 'C_CLOUMN98', null, 434, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146155', null, '7f13446d19b4497986980fa16a750f95', '转矩(1N·M)', 'C_CLOUMN99', null, 435, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146156', null, '7f13446d19b4497986980fa16a750f95', '变频器通信状态', 'C_CLOUMN100', null, 436, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146157', null, '7f13446d19b4497986980fa16a750f95', '转矩系数', 'C_CLOUMN101', null, 437, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146158', null, '7f13446d19b4497986980fa16a750f95', '日产液量(m^3/d)', 'C_CLOUMN102', null, 438, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146159', null, '7f13446d19b4497986980fa16a750f95', '日产油量(m^3/d)', 'C_CLOUMN103', null, 439, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146160', null, '7f13446d19b4497986980fa16a750f95', '日产水量(m^3/d)', 'C_CLOUMN104', null, 440, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146161', null, '7f13446d19b4497986980fa16a750f95', '日产液量(t/d)', 'C_CLOUMN105', null, 441, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146162', null, '7f13446d19b4497986980fa16a750f95', '日产油量(t/d)', 'C_CLOUMN106', null, 442, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146163', null, '7f13446d19b4497986980fa16a750f95', '日产水量(t/d)', 'C_CLOUMN107', null, 443, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146164', null, '7f13446d19b4497986980fa16a750f95', '理论排量(m^3/d)', 'C_CLOUMN108', null, 444, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146165', null, '7f13446d19b4497986980fa16a750f95', '容积效率(小数)', 'C_CLOUMN109', null, 445, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146166', null, '7f13446d19b4497986980fa16a750f95', '收缩系数(小数)', 'C_CLOUMN110', null, 446, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146167', null, '7f13446d19b4497986980fa16a750f95', '计算泵效(小数)', 'C_CLOUMN111', null, 447, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146168', null, '7f13446d19b4497986980fa16a750f95', '原油密度(g/cm^3)', 'C_CLOUMN112', null, 448, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146169', null, '7f13446d19b4497986980fa16a750f95', '地层水密度(g/cm^3)', 'C_CLOUMN113', null, 449, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146170', null, '7f13446d19b4497986980fa16a750f95', '天然气相对密度', 'C_CLOUMN114', null, 450, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146171', null, '7f13446d19b4497986980fa16a750f95', '饱和压力(Mpa)', 'C_CLOUMN115', null, 451, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146172', null, '7f13446d19b4497986980fa16a750f95', '油层中部深度(m)', 'C_CLOUMN116', null, 452, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146173', null, '7f13446d19b4497986980fa16a750f95', '油层中部温度(℃)', 'C_CLOUMN117', null, 453, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146174', null, '7f13446d19b4497986980fa16a750f95', '生产气油比(m^3/t)', 'C_CLOUMN118', null, 454, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146175', null, '7f13446d19b4497986980fa16a750f95', '泵挂(m)', 'C_CLOUMN119', null, 455, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146176', null, '7f13446d19b4497986980fa16a750f95', '每转公称排量(ml/r)', 'C_CLOUMN120', null, 456, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146177', null, '7f13446d19b4497986980fa16a750f95', '油管内径(mm)', 'C_CLOUMN121', null, 457, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146178', null, '7f13446d19b4497986980fa16a750f95', '套管内径(mm)', 'C_CLOUMN122', null, 458, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146179', null, '7f13446d19b4497986980fa16a750f95', '一级杆级别', 'C_CLOUMN123', null, 459, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146180', null, '7f13446d19b4497986980fa16a750f95', '一级杆外径(mm)', 'C_CLOUMN124', null, 460, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146181', null, '7f13446d19b4497986980fa16a750f95', '一级杆内径(mm)', 'C_CLOUMN125', null, 461, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146182', null, '7f13446d19b4497986980fa16a750f95', '一级杆长(m)', 'C_CLOUMN126', null, 462, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146183', null, '7f13446d19b4497986980fa16a750f95', '二级杆级别', 'C_CLOUMN127', null, 463, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146184', null, '7f13446d19b4497986980fa16a750f95', '二级杆外径(mm)', 'C_CLOUMN128', null, 464, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146185', null, '7f13446d19b4497986980fa16a750f95', '二级杆内径(mm)', 'C_CLOUMN129', null, 465, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146186', null, '7f13446d19b4497986980fa16a750f95', '二级杆长(m)', 'C_CLOUMN130', null, 466, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146187', null, '7f13446d19b4497986980fa16a750f95', '三级杆级别', 'C_CLOUMN131', null, 467, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146188', null, '7f13446d19b4497986980fa16a750f95', '三级杆外径(mm)', 'C_CLOUMN132', null, 468, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146189', null, '7f13446d19b4497986980fa16a750f95', '三级杆内径(mm)', 'C_CLOUMN133', null, 469, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146190', null, '7f13446d19b4497986980fa16a750f95', '三级杆长(m)', 'C_CLOUMN134', null, 470, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146191', null, '7f13446d19b4497986980fa16a750f95', '净毛比', 'C_CLOUMN135', null, 471, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146192', null, '7f13446d19b4497986980fa16a750f95', '净毛值(m^3/d)', 'C_CLOUMN136', null, 472, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146193', null, '7f13446d19b4497986980fa16a750f95', '系统时间-时', 'C_CLOUMN137', null, 473, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146194', null, '7f13446d19b4497986980fa16a750f95', '系统时间-分', 'C_CLOUMN138', null, 474, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146195', null, '7f13446d19b4497986980fa16a750f95', '系统时间-秒', 'C_CLOUMN139', null, 475, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146196', null, '7f13446d19b4497986980fa16a750f95', '系统时间-年', 'C_CLOUMN140', null, 476, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146197', null, '7f13446d19b4497986980fa16a750f95', '系统时间-月', 'C_CLOUMN141', null, 477, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146198', null, '7f13446d19b4497986980fa16a750f95', '系统时间-日', 'C_CLOUMN142', null, 478, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146199', null, '7f13446d19b4497986980fa16a750f95', 'RTU地址', 'C_CLOUMN143', null, 479, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146200', null, '7f13446d19b4497986980fa16a750f95', '程序版本号', 'C_CLOUMN144', null, 480, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146201', null, '7f13446d19b4497986980fa16a750f95', '井号', 'C_CLOUMN145', null, 481, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146202', null, '7f13446d19b4497986980fa16a750f95', '回压(MPa)', 'C_CLOUMN146', null, 482, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146203', null, '7f13446d19b4497986980fa16a750f95', '螺杆泵转速(r/min)', 'C_CLOUMN147', null, 483, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146204', null, '7f13446d19b4497986980fa16a750f95', '螺杆泵扭矩(kN·m)', 'C_CLOUMN148', null, 484, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133975', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '抽油机厂家', 'manufacturer', null, 1, 1, null, null, to_date('24-05-2022 08:52:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 08:52:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133976', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '抽油机型号', 'model', null, 2, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133977', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '铭牌冲程(m)', 'stroke', null, 3, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133978', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '曲柄旋转方向', 'crankRotationDirection', null, 4, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133979', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '曲柄偏置角(°)', 'offsetAngleOfCrank', null, 5, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133980', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '曲柄重心半径(m)', 'crankGravityRadius', null, 6, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133981', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '单块曲柄重量(kN)', 'singleCrankWeight', null, 7, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133982', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '单块曲柄销重量(kN)', 'singleCrankPinWeight', null, 8, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133983', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '结构不平衡重(kN)', 'structuralUnbalance', null, 9, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133984', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '平衡块位置重量', 'balance', null, 10, 1, null, null, to_date('24-05-2022 09:07:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:07:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133985', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '原油密度(g/cm^3)', 'crudeOilDensity', null, 11, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133986', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '水密度(g/cm^3)', 'waterDensity', null, 12, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133987', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '天然气相对密度', 'naturalGasRelativeDensity', null, 13, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133988', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '饱和压力(MPa)', 'saturationPressure', null, 14, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133989', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '油层中部深度(m)', 'reservoirDepth', null, 15, 0, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133990', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '油层中部温度(℃)', 'reservoirTemperature', null, 16, 0, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133991', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '油压(MPa)', 'tubingPressure', null, 17, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133992', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '套压(MPa)', 'casingPressure', null, 18, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133993', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '井口温度(℃)', 'wellHeadTemperature', null, 19, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133994', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '含水率(%)', 'waterCut', null, 20, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133995', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 21, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133996', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '动液面(m)', 'producingfluidLevel', null, 22, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133997', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '泵挂(m)', 'pumpSettingDepth', null, 23, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133999', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '泵筒类型', 'barrelType', null, 25, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134000', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '泵级别', 'pumpGrade', null, 26, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134001', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '泵径(mm)', 'pumpBoreDiameter', null, 27, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134002', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '柱塞长(m)', 'plungerLength', null, 28, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134003', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '油管内径(mm)', 'tubingStringInsideDiameter', null, 29, 1, null, null, to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134004', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '套管内径(mm)', 'casingStringInsideDiameter', null, 30, 1, null, null, to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134005', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '抽油杆参数', 'rodString', null, 31, 1, null, null, to_date('24-05-2022 09:20:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:20:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119190', null, 'cd7b24562b924d19b556de31256e22a1', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119191', null, 'cd7b24562b924d19b556de31256e22a1', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('11-05-2024 16:05:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2024 16:05:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119193', null, 'cd7b24562b924d19b556de31256e22a1', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 3, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119192', null, 'cd7b24562b924d19b556de31256e22a1', '通信状态', 'commStatusName', null, 4, 0, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134402', null, 'cd7b24562b924d19b556de31256e22a1', '在线时间(h)', 'commTime', null, 5, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134403', null, 'cd7b24562b924d19b556de31256e22a1', '在线时率(小数)', 'commTimeEfficiency', null, 6, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134404', null, 'cd7b24562b924d19b556de31256e22a1', '在线区间', 'commRange', null, 7, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133873', null, 'cd7b24562b924d19b556de31256e22a1', '运行状态', 'runStatusName', null, 8, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134405', null, 'cd7b24562b924d19b556de31256e22a1', '运行时间(h)', 'runTime', null, 9, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134406', null, 'cd7b24562b924d19b556de31256e22a1', '运行时率(小数)', 'runTimeEfficiency', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134407', null, 'cd7b24562b924d19b556de31256e22a1', '运行区间', 'runRange', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146205', null, 'cd7b24562b924d19b556de31256e22a1', '转速(1rpm)', 'C_CLOUMN1', null, 341, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146206', null, 'cd7b24562b924d19b556de31256e22a1', '油压(MPa)', 'C_CLOUMN2', null, 342, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146207', null, 'cd7b24562b924d19b556de31256e22a1', '套压(MPa)', 'C_CLOUMN3', null, 343, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146208', null, 'cd7b24562b924d19b556de31256e22a1', '井口温度(℃)', 'C_CLOUMN4', null, 344, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146209', null, 'cd7b24562b924d19b556de31256e22a1', '运行状态', 'C_CLOUMN5', null, 345, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146210', null, 'cd7b24562b924d19b556de31256e22a1', '启停控制', 'C_CLOUMN6', null, 346, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146211', null, 'cd7b24562b924d19b556de31256e22a1', '含水率(%)', 'C_CLOUMN7', null, 347, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146212', null, 'cd7b24562b924d19b556de31256e22a1', '动液面(m)', 'C_CLOUMN8', null, 348, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146213', null, 'cd7b24562b924d19b556de31256e22a1', '光杆温度(℃)', 'C_CLOUMN9', null, 349, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146214', null, 'cd7b24562b924d19b556de31256e22a1', '盘根盒温度(℃)', 'C_CLOUMN10', null, 350, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146215', null, 'cd7b24562b924d19b556de31256e22a1', 'A相电流(A)', 'C_CLOUMN11', null, 351, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146216', null, 'cd7b24562b924d19b556de31256e22a1', 'B相电流(A)', 'C_CLOUMN12', null, 352, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146217', null, 'cd7b24562b924d19b556de31256e22a1', 'C相电流(V)', 'C_CLOUMN13', null, 353, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146218', null, 'cd7b24562b924d19b556de31256e22a1', 'A相电压(V)', 'C_CLOUMN14', null, 354, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146219', null, 'cd7b24562b924d19b556de31256e22a1', 'B相电压(V)', 'C_CLOUMN15', null, 355, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146220', null, 'cd7b24562b924d19b556de31256e22a1', 'C相电压(V)', 'C_CLOUMN16', null, 356, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146221', null, 'cd7b24562b924d19b556de31256e22a1', '有功功耗(kW·h)', 'C_CLOUMN17', null, 357, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146222', null, 'cd7b24562b924d19b556de31256e22a1', '无功功耗(kVar·h)', 'C_CLOUMN18', null, 358, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146223', null, 'cd7b24562b924d19b556de31256e22a1', '有功功率(kW)', 'C_CLOUMN19', null, 359, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146224', null, 'cd7b24562b924d19b556de31256e22a1', '无功功率(kVar)', 'C_CLOUMN20', null, 360, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146225', null, 'cd7b24562b924d19b556de31256e22a1', '反向功率(kW)', 'C_CLOUMN21', null, 361, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146226', null, 'cd7b24562b924d19b556de31256e22a1', '功率因数', 'C_CLOUMN22', null, 362, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146227', null, 'cd7b24562b924d19b556de31256e22a1', '变频设置频率(Hz)', 'C_CLOUMN23', null, 363, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146228', null, 'cd7b24562b924d19b556de31256e22a1', '变频运行频率(Hz)', 'C_CLOUMN24', null, 364, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146229', null, 'cd7b24562b924d19b556de31256e22a1', '功图采集间隔(min)', 'C_CLOUMN25', null, 365, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146230', null, 'cd7b24562b924d19b556de31256e22a1', '手动采集功图', 'C_CLOUMN26', null, 366, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146231', null, 'cd7b24562b924d19b556de31256e22a1', '功图设置点数', 'C_CLOUMN27', null, 367, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146232', null, 'cd7b24562b924d19b556de31256e22a1', '功图实测点数', 'C_CLOUMN28', null, 368, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146233', null, 'cd7b24562b924d19b556de31256e22a1', '功图采集时间', 'C_CLOUMN29', null, 369, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146234', null, 'cd7b24562b924d19b556de31256e22a1', '冲次(次/min)', 'C_CLOUMN30', null, 370, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146235', null, 'cd7b24562b924d19b556de31256e22a1', '冲程(m)', 'C_CLOUMN31', null, 371, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146236', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-位移(m)', 'C_CLOUMN32', null, 372, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146237', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-载荷(kN)', 'C_CLOUMN33', null, 373, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146238', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-电流(A)', 'C_CLOUMN34', null, 374, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146239', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-功率(kW)', 'C_CLOUMN35', null, 375, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146240', null, 'cd7b24562b924d19b556de31256e22a1', '煤层顶板深(米)', 'C_CLOUMN36', null, 376, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146241', null, 'cd7b24562b924d19b556de31256e22a1', '压力计安装深度(米)', 'C_CLOUMN37', null, 377, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146242', null, 'cd7b24562b924d19b556de31256e22a1', '井下压力计-压力(MPa)', 'C_CLOUMN38', null, 378, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146243', null, 'cd7b24562b924d19b556de31256e22a1', '井口套压(MPa)', 'C_CLOUMN39', null, 379, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146244', null, 'cd7b24562b924d19b556de31256e22a1', '系统压力(KPa)', 'C_CLOUMN40', null, 380, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146245', null, 'cd7b24562b924d19b556de31256e22a1', '产气量累计(m3)', 'C_CLOUMN41', null, 381, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146246', null, 'cd7b24562b924d19b556de31256e22a1', '产气量瞬时(m3/h)', 'C_CLOUMN42', null, 382, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146247', null, 'cd7b24562b924d19b556de31256e22a1', '气体温度(℃)', 'C_CLOUMN43', null, 383, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146248', null, 'cd7b24562b924d19b556de31256e22a1', '产水量累计(m3)', 'C_CLOUMN44', null, 384, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146249', null, 'cd7b24562b924d19b556de31256e22a1', '产水量瞬时(m3/h)', 'C_CLOUMN45', null, 385, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146250', null, 'cd7b24562b924d19b556de31256e22a1', '井下压力计-温度(℃)', 'C_CLOUMN46', null, 386, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146251', null, 'cd7b24562b924d19b556de31256e22a1', '气体流量计通讯状态', 'C_CLOUMN47', null, 387, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146252', null, 'cd7b24562b924d19b556de31256e22a1', '水流量计通讯状态', 'C_CLOUMN48', null, 388, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146253', null, 'cd7b24562b924d19b556de31256e22a1', '井下压力计/液面仪通讯状态', 'C_CLOUMN49', null, 389, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146254', null, 'cd7b24562b924d19b556de31256e22a1', '变频器通讯状态', 'C_CLOUMN50', null, 390, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146255', null, 'cd7b24562b924d19b556de31256e22a1', '当前目标值(m)', 'C_CLOUMN51', null, 391, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146256', null, 'cd7b24562b924d19b556de31256e22a1', '频率控制方式', 'C_CLOUMN52', null, 392, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146257', null, 'cd7b24562b924d19b556de31256e22a1', '变频器设置频率(Hz)', 'C_CLOUMN53', null, 393, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146258', null, 'cd7b24562b924d19b556de31256e22a1', '变频器运行频率(Hz)', 'C_CLOUMN54', null, 394, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146259', null, 'cd7b24562b924d19b556de31256e22a1', '变频器故障状态', 'C_CLOUMN55', null, 395, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146260', null, 'cd7b24562b924d19b556de31256e22a1', '变频器输出电流(A)', 'C_CLOUMN56', null, 396, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146261', null, 'cd7b24562b924d19b556de31256e22a1', '变频器输出电压(V)', 'C_CLOUMN57', null, 397, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146262', null, 'cd7b24562b924d19b556de31256e22a1', '变频器厂家代码', 'C_CLOUMN58', null, 398, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146263', null, 'cd7b24562b924d19b556de31256e22a1', '变频器状态字1', 'C_CLOUMN59', null, 399, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146264', null, 'cd7b24562b924d19b556de31256e22a1', '变频器状态字2', 'C_CLOUMN60', null, 400, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146265', null, 'cd7b24562b924d19b556de31256e22a1', '本地旋钮位置', 'C_CLOUMN61', null, 401, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146266', null, 'cd7b24562b924d19b556de31256e22a1', '母线电压(V)', 'C_CLOUMN62', null, 402, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146267', null, 'cd7b24562b924d19b556de31256e22a1', '设定频率反馈(Hz)', 'C_CLOUMN63', null, 403, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146268', null, 'cd7b24562b924d19b556de31256e22a1', '10Hz对应冲次/转速预设值(次/100转)', 'C_CLOUMN64', null, 404, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146269', null, 'cd7b24562b924d19b556de31256e22a1', '50Hz对应冲次/转速预设值(次/100转)', 'C_CLOUMN65', null, 405, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146270', null, 'cd7b24562b924d19b556de31256e22a1', '冲次/转速设定值(次/100转)', 'C_CLOUMN66', null, 406, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146271', null, 'cd7b24562b924d19b556de31256e22a1', '修正后井底流压(Mpa)', 'C_CLOUMN67', null, 407, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146272', null, 'cd7b24562b924d19b556de31256e22a1', '计算液柱高度(m)', 'C_CLOUMN68', null, 408, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146273', null, 'cd7b24562b924d19b556de31256e22a1', '计算近1小时液柱下降速度(m/小时)', 'C_CLOUMN69', null, 409, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146274', null, 'cd7b24562b924d19b556de31256e22a1', '排采模式', 'C_CLOUMN70', null, 410, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146275', null, 'cd7b24562b924d19b556de31256e22a1', '自动排采状态', 'C_CLOUMN71', null, 411, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146276', null, 'cd7b24562b924d19b556de31256e22a1', '自动排采-最小频率(Hz)', 'C_CLOUMN72', null, 412, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146277', null, 'cd7b24562b924d19b556de31256e22a1', '自动排采-最大频率(Hz)', 'C_CLOUMN73', null, 413, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146278', null, 'cd7b24562b924d19b556de31256e22a1', '最大步长幅度限制(Hz)', 'C_CLOUMN74', null, 414, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146279', null, 'cd7b24562b924d19b556de31256e22a1', '最短调整时间间隔(秒)', 'C_CLOUMN75', null, 415, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146280', null, 'cd7b24562b924d19b556de31256e22a1', '自动重启延时(秒)', 'C_CLOUMN76', null, 416, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146281', null, 'cd7b24562b924d19b556de31256e22a1', '自动重启次数(次)', 'C_CLOUMN77', null, 417, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146282', null, 'cd7b24562b924d19b556de31256e22a1', '井底流压波动报警值(Kpa)', 'C_CLOUMN78', null, 418, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146283', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-目标定降（每日）(m)', 'C_CLOUMN79', null, 419, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146284', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-液柱低停机值(m)', 'C_CLOUMN80', null, 420, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146285', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-液柱低报警值(m)', 'C_CLOUMN81', null, 421, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146286', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-液柱重启值(m)', 'C_CLOUMN82', null, 422, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146287', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-液柱高报警值(m)', 'C_CLOUMN83', null, 423, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146288', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-P参数', 'C_CLOUMN84', null, 424, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146289', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-I参数', 'C_CLOUMN85', null, 425, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146290', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-D参数', 'C_CLOUMN86', null, 426, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146291', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-目标流压(Kpa)', 'C_CLOUMN87', null, 427, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146292', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-流压低停机值(Kpa)', 'C_CLOUMN88', null, 428, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146293', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-流压低报警值(Kpa)', 'C_CLOUMN89', null, 429, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146294', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-流压重启值(Kpa)', 'C_CLOUMN90', null, 430, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146295', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-流压高报警值(Kpa)', 'C_CLOUMN91', null, 431, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146296', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-P参数', 'C_CLOUMN92', null, 432, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146297', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-I参数', 'C_CLOUMN93', null, 433, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146298', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-D参数', 'C_CLOUMN94', null, 434, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146299', null, 'cd7b24562b924d19b556de31256e22a1', 'IGBT 温度(1℃)', 'C_CLOUMN95', null, 435, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146300', null, 'cd7b24562b924d19b556de31256e22a1', '输出电流(0.1A)', 'C_CLOUMN96', null, 436, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146301', null, 'cd7b24562b924d19b556de31256e22a1', '输出电压(1V)', 'C_CLOUMN97', null, 437, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146302', null, 'cd7b24562b924d19b556de31256e22a1', '输出功率(0.1KW)', 'C_CLOUMN98', null, 438, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146303', null, 'cd7b24562b924d19b556de31256e22a1', '转矩(1N·M)', 'C_CLOUMN99', null, 439, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146304', null, 'cd7b24562b924d19b556de31256e22a1', '变频器通信状态', 'C_CLOUMN100', null, 440, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146305', null, 'cd7b24562b924d19b556de31256e22a1', '转矩系数', 'C_CLOUMN101', null, 441, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146306', null, 'cd7b24562b924d19b556de31256e22a1', '日产液量(m^3/d)', 'C_CLOUMN102', null, 442, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146307', null, 'cd7b24562b924d19b556de31256e22a1', '日产油量(m^3/d)', 'C_CLOUMN103', null, 443, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146308', null, 'cd7b24562b924d19b556de31256e22a1', '日产水量(m^3/d)', 'C_CLOUMN104', null, 444, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146309', null, 'cd7b24562b924d19b556de31256e22a1', '日产液量(t/d)', 'C_CLOUMN105', null, 445, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146310', null, 'cd7b24562b924d19b556de31256e22a1', '日产油量(t/d)', 'C_CLOUMN106', null, 446, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146311', null, 'cd7b24562b924d19b556de31256e22a1', '日产水量(t/d)', 'C_CLOUMN107', null, 447, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146312', null, 'cd7b24562b924d19b556de31256e22a1', '理论排量(m^3/d)', 'C_CLOUMN108', null, 448, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146313', null, 'cd7b24562b924d19b556de31256e22a1', '容积效率(小数)', 'C_CLOUMN109', null, 449, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146314', null, 'cd7b24562b924d19b556de31256e22a1', '收缩系数(小数)', 'C_CLOUMN110', null, 450, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146315', null, 'cd7b24562b924d19b556de31256e22a1', '计算泵效(小数)', 'C_CLOUMN111', null, 451, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146316', null, 'cd7b24562b924d19b556de31256e22a1', '原油密度(g/cm^3)', 'C_CLOUMN112', null, 452, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146317', null, 'cd7b24562b924d19b556de31256e22a1', '地层水密度(g/cm^3)', 'C_CLOUMN113', null, 453, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146318', null, 'cd7b24562b924d19b556de31256e22a1', '天然气相对密度', 'C_CLOUMN114', null, 454, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146319', null, 'cd7b24562b924d19b556de31256e22a1', '饱和压力(Mpa)', 'C_CLOUMN115', null, 455, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146320', null, 'cd7b24562b924d19b556de31256e22a1', '油层中部深度(m)', 'C_CLOUMN116', null, 456, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146321', null, 'cd7b24562b924d19b556de31256e22a1', '油层中部温度(℃)', 'C_CLOUMN117', null, 457, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146322', null, 'cd7b24562b924d19b556de31256e22a1', '生产气油比(m^3/t)', 'C_CLOUMN118', null, 458, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146323', null, 'cd7b24562b924d19b556de31256e22a1', '泵挂(m)', 'C_CLOUMN119', null, 459, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146324', null, 'cd7b24562b924d19b556de31256e22a1', '每转公称排量(ml/r)', 'C_CLOUMN120', null, 460, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146325', null, 'cd7b24562b924d19b556de31256e22a1', '油管内径(mm)', 'C_CLOUMN121', null, 461, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146326', null, 'cd7b24562b924d19b556de31256e22a1', '套管内径(mm)', 'C_CLOUMN122', null, 462, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146327', null, 'cd7b24562b924d19b556de31256e22a1', '一级杆级别', 'C_CLOUMN123', null, 463, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146328', null, 'cd7b24562b924d19b556de31256e22a1', '一级杆外径(mm)', 'C_CLOUMN124', null, 464, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146329', null, 'cd7b24562b924d19b556de31256e22a1', '一级杆内径(mm)', 'C_CLOUMN125', null, 465, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146330', null, 'cd7b24562b924d19b556de31256e22a1', '一级杆长(m)', 'C_CLOUMN126', null, 466, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146331', null, 'cd7b24562b924d19b556de31256e22a1', '二级杆级别', 'C_CLOUMN127', null, 467, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146332', null, 'cd7b24562b924d19b556de31256e22a1', '二级杆外径(mm)', 'C_CLOUMN128', null, 468, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146333', null, 'cd7b24562b924d19b556de31256e22a1', '二级杆内径(mm)', 'C_CLOUMN129', null, 469, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146334', null, 'cd7b24562b924d19b556de31256e22a1', '二级杆长(m)', 'C_CLOUMN130', null, 470, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146335', null, 'cd7b24562b924d19b556de31256e22a1', '三级杆级别', 'C_CLOUMN131', null, 471, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146336', null, 'cd7b24562b924d19b556de31256e22a1', '三级杆外径(mm)', 'C_CLOUMN132', null, 472, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146337', null, 'cd7b24562b924d19b556de31256e22a1', '三级杆内径(mm)', 'C_CLOUMN133', null, 473, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146338', null, 'cd7b24562b924d19b556de31256e22a1', '三级杆长(m)', 'C_CLOUMN134', null, 474, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146339', null, 'cd7b24562b924d19b556de31256e22a1', '净毛比', 'C_CLOUMN135', null, 475, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146340', null, 'cd7b24562b924d19b556de31256e22a1', '净毛值(m^3/d)', 'C_CLOUMN136', null, 476, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146341', null, 'cd7b24562b924d19b556de31256e22a1', '系统时间-时', 'C_CLOUMN137', null, 477, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146342', null, 'cd7b24562b924d19b556de31256e22a1', '系统时间-分', 'C_CLOUMN138', null, 478, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146343', null, 'cd7b24562b924d19b556de31256e22a1', '系统时间-秒', 'C_CLOUMN139', null, 479, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146344', null, 'cd7b24562b924d19b556de31256e22a1', '系统时间-年', 'C_CLOUMN140', null, 480, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146345', null, 'cd7b24562b924d19b556de31256e22a1', '系统时间-月', 'C_CLOUMN141', null, 481, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146346', null, 'cd7b24562b924d19b556de31256e22a1', '系统时间-日', 'C_CLOUMN142', null, 482, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146347', null, 'cd7b24562b924d19b556de31256e22a1', 'RTU地址', 'C_CLOUMN143', null, 483, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146348', null, 'cd7b24562b924d19b556de31256e22a1', '程序版本号', 'C_CLOUMN144', null, 484, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146349', null, 'cd7b24562b924d19b556de31256e22a1', '井号', 'C_CLOUMN145', null, 485, 0, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146350', null, 'cd7b24562b924d19b556de31256e22a1', '回压(MPa)', 'C_CLOUMN146', null, 486, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146351', null, 'cd7b24562b924d19b556de31256e22a1', '螺杆泵转速(r/min)', 'C_CLOUMN147', null, 487, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146352', null, 'cd7b24562b924d19b556de31256e22a1', '螺杆泵扭矩(kN·m)', 'C_CLOUMN148', null, 488, 1, null, null, to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-01-2024 15:19:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133335', null, '0d4297cb4db44bb3a9a3d5d983007039', '序号', 'id', 'width:50', 1, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133336', null, '0d4297cb4db44bb3a9a3d5d983007039', '采集时间', 'acqTime', 'width:150', 2, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138735', null, '0d4297cb4db44bb3a9a3d5d983007039', '通信状态', 'commStatusName', null, 3, 0, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138736', null, '0d4297cb4db44bb3a9a3d5d983007039', '在线时间(h)', 'commTime', null, 4, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138737', null, '0d4297cb4db44bb3a9a3d5d983007039', '在线时率(小数)', 'commTimeEfficiency', null, 5, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138738', null, '0d4297cb4db44bb3a9a3d5d983007039', '在线区间', 'commRange', null, 6, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138739', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行状态', 'runStatusName', null, 7, 0, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138740', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行时间(h)', 'runTime', null, 8, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138741', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行时率(小数)', 'runTimeEfficiency', null, 9, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138742', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行区间', 'runRange', null, 10, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133337', null, '0d4297cb4db44bb3a9a3d5d983007039', '工况', 'resultName', 'width:130', 11, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138743', null, '0d4297cb4db44bb3a9a3d5d983007039', '优化建议', 'optimizationSuggestion', null, 12, 1, null, null, to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133342', null, '0d4297cb4db44bb3a9a3d5d983007039', '冲程(m)', 'stroke', null, 13, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133343', null, '0d4297cb4db44bb3a9a3d5d983007039', '冲次(1/min)', 'spm', null, 14, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133344', null, '0d4297cb4db44bb3a9a3d5d983007039', '最大载荷(kN)', 'fmax', null, 15, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133345', null, '0d4297cb4db44bb3a9a3d5d983007039', '最小载荷(kN)', 'fmin', null, 16, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138744', null, '0d4297cb4db44bb3a9a3d5d983007039', '充满系数', 'fullnessCoefficient', null, 17, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139533', null, '0d4297cb4db44bb3a9a3d5d983007039', '柱塞冲程(m)', 'plungerStroke', null, 18, 1, null, null, to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139534', null, '0d4297cb4db44bb3a9a3d5d983007039', '柱塞有效冲程(m)', 'availablePlungerStroke', null, 19, 1, null, null, to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138745', null, '0d4297cb4db44bb3a9a3d5d983007039', '泵挂(m)', 'pumpSettingDepth', null, 20, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139722', null, '0d4297cb4db44bb3a9a3d5d983007039', '动液面(m)', 'producingfluidLevel', null, 21, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139723', null, '0d4297cb4db44bb3a9a3d5d983007039', '反演液面校正值(MPa)', 'levelCorrectValue', null, 22, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138746', null, '0d4297cb4db44bb3a9a3d5d983007039', '反演动液面(m)', 'calcProducingfluidLevel', null, 23, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138747', null, '0d4297cb4db44bb3a9a3d5d983007039', '液面校正差值(MPa)', 'levelDifferenceValue', null, 24, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138748', null, '0d4297cb4db44bb3a9a3d5d983007039', '沉没度(m)', 'submergence', null, 25, 1, null, null, to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133338', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产液量(t/d)', 'liquidWeightProduction', null, 26, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145265', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产油量(t/d)', 'oilWeightProduction', null, 27, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145266', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产水量(t/d)', 'waterWeightProduction', null, 28, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133339', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产液量(t/d))', 'liquidWeightProduction_L', null, 29, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145267', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产油量(t/d))', 'oilWeightProduction_L', null, 30, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145268', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产水量(t/d))', 'waterWeightProduction_L', null, 31, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133340', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产液量(m^3/d)', 'liquidVolumetricProduction', null, 32, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145269', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产油量(m^3/d)', 'oilVolumetricProduction', null, 33, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145270', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬时产水量(m^3/d)', 'waterVolumetricProduction', null, 34, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133341', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产液量(m^3/d))', 'liquidVolumetricProduction_L', null, 35, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145271', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产油量(m^3/d))', 'oilVolumetricProduction_L', null, 36, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145272', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累计产水量(m^3/d))', 'waterVolumetricProduction_L', null, 37, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138749', null, '0d4297cb4db44bb3a9a3d5d983007039', '有功功率(kW)', 'averageWatt', null, 38, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138750', null, '0d4297cb4db44bb3a9a3d5d983007039', '光杆功率(kW)', 'polishrodPower', null, 39, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138751', null, '0d4297cb4db44bb3a9a3d5d983007039', '水功率(kW)', 'waterPower', null, 40, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138752', null, '0d4297cb4db44bb3a9a3d5d983007039', '地面效率(%)', 'surfaceSystemEfficiency', null, 41, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138753', null, '0d4297cb4db44bb3a9a3d5d983007039', '井下效率(%)', 'welldownSystemEfficiency', null, 42, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138754', null, '0d4297cb4db44bb3a9a3d5d983007039', '系统效率(%)', 'systemEfficiency', null, 43, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138755', null, '0d4297cb4db44bb3a9a3d5d983007039', '吨液百米耗电量(kW· h/100m· t)', 'energyper100mlift', null, 44, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138756', null, '0d4297cb4db44bb3a9a3d5d983007039', '总泵效(%)', 'pumpEff', null, 45, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139724', null, '0d4297cb4db44bb3a9a3d5d983007039', '上冲程最大电流(A)', 'upStrokeIMax', null, 46, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139725', null, '0d4297cb4db44bb3a9a3d5d983007039', '下冲程最大电流(A)', 'downStrokeIMax', null, 47, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138757', null, '0d4297cb4db44bb3a9a3d5d983007039', '电流平衡度(%)', 'iDegreeBalance', null, 48, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139726', null, '0d4297cb4db44bb3a9a3d5d983007039', '上冲程最大功率(kW)', 'upStrokeWattMax', null, 49, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139727', null, '0d4297cb4db44bb3a9a3d5d983007039', '下冲程最大功率(kW)', 'downStrokeWattMax', null, 50, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138758', null, '0d4297cb4db44bb3a9a3d5d983007039', '功率平衡度(%)', 'wattDegreeBalance', null, 51, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138759', null, '0d4297cb4db44bb3a9a3d5d983007039', '移动距离(cm)', 'deltaradius', null, 52, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138760', null, '0d4297cb4db44bb3a9a3d5d983007039', '日用电量(kW·h)', 'todayKWattH', null, 53, 0, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119852', null, 'cdd198534d5849b7a27054e0f2593ff3', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119853', null, 'cdd198534d5849b7a27054e0f2593ff3', '设备名称', 'deviceName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119855', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:16:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('121867', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:16:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119856', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:16:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119857', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警级别', 'alarmLevelName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119858', null, 'cdd198534d5849b7a27054e0f2593ff3', '恢复时间', 'recoveryTime', 'flex:3', 7, 0, null, '超级管理员', to_date('05-01-2022 14:17:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:00', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119859', null, 'e2924366ab174d4b9a096be969934985', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119860', null, 'e2924366ab174d4b9a096be969934985', '设备名称', 'deviceName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-03-2024 18:06:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:06:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119862', null, 'e2924366ab174d4b9a096be969934985', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:17:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119866', null, 'e2924366ab174d4b9a096be969934985', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:17:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119863', null, 'e2924366ab174d4b9a096be969934985', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:17:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119867', null, 'e2924366ab174d4b9a096be969934985', '报警值', 'alarmValue', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:18:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119868', null, 'e2924366ab174d4b9a096be969934985', '报警限值', 'alarmLimit', 'flex:2', 7, 1, null, '超级管理员', to_date('05-01-2022 14:18:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119869', null, 'e2924366ab174d4b9a096be969934985', '回差', 'hystersis', 'flex:2', 8, 1, null, '超级管理员', to_date('05-01-2022 14:18:08', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:08', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119864', null, 'e2924366ab174d4b9a096be969934985', '报警级别', 'alarmLevelName', 'flex:2', 9, 1, null, '超级管理员', to_date('05-01-2022 14:18:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119865', null, 'e2924366ab174d4b9a096be969934985', '恢复时间', 'recoveryTime', 'flex:3', 10, 0, null, '超级管理员', to_date('05-01-2022 14:18:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120115', null, 'b09082f4272e4768994db398e14bc3f2', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120116', null, 'b09082f4272e4768994db398e14bc3f2', '设备名称', 'deviceName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-03-2024 18:07:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:07:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120118', null, 'b09082f4272e4768994db398e14bc3f2', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:18:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('121868', null, 'b09082f4272e4768994db398e14bc3f2', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:19:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120119', null, 'b09082f4272e4768994db398e14bc3f2', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:19:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:07', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120120', null, 'b09082f4272e4768994db398e14bc3f2', '报警级别', 'alarmLevelName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:19:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120121', null, 'b09082f4272e4768994db398e14bc3f2', '恢复时间', 'recoveryTime', 'flex:3', 7, 0, null, '超级管理员', to_date('05-01-2022 14:19:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:16', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119870', null, 'b71c1a2c9d574fe482080a56c7c780a9', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119871', null, 'b71c1a2c9d574fe482080a56c7c780a9', '设备名称', 'deviceName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-03-2024 18:07:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:07:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119873', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:19:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('121869', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:19:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119874', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:19:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119875', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警级别', 'alarmLevelName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:19:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:43', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119876', null, 'b71c1a2c9d574fe482080a56c7c780a9', '恢复时间', 'recoveryTime', 'flex:3', 7, 0, null, '超级管理员', to_date('05-01-2022 14:19:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119371', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119372', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '设备名称', 'deviceName', 'flex:3', 2, 1, null, '超级管理员', to_date('06-03-2024 10:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 10:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119373', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '设备类型', 'deviceTypeName', 'flex:2', 3, 1, null, '超级管理员', to_date('05-01-2022 10:41:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119374', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作用户', 'user_id', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 10:41:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119375', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '登录IP', 'loginIp', 'flex:3', 5, 1, null, '超级管理员', to_date('05-01-2022 10:41:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:22', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119376', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作', 'actionName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 10:41:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:16', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119377', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '备注', 'remark', 'flex:10', 7, 1, null, '超级管理员', to_date('05-01-2022 10:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:40:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119378', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作时间', 'to_char(createTime@''yyyy-mm-dd hh24:mi:ss'') as createTime', 'flex:5', 8, 1, null, '超级管理员', to_date('05-01-2022 10:41:08', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:08', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119379', null, '167aeb3aca384afda8655d63aedee484', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119380', null, '167aeb3aca384afda8655d63aedee484', '操作用户', 'user_id', 'flex:1', 2, 1, null, '超级管理员', to_date('05-01-2022 10:43:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119381', null, '167aeb3aca384afda8655d63aedee484', '登录IP', 'loginIp', 'flex:1', 3, 1, null, '超级管理员', to_date('05-01-2022 10:43:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119382', null, '167aeb3aca384afda8655d63aedee484', '操作', 'actionName', 'flex:1', 4, 1, null, '超级管理员', to_date('05-01-2022 10:43:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:22', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119383', null, '167aeb3aca384afda8655d63aedee484', '备注', 'remark', 'flex:1', 5, 1, null, '超级管理员', to_date('05-01-2022 10:43:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119384', null, '167aeb3aca384afda8655d63aedee484', '操作时间', 'to_char(createTime@''yyyy-mm-dd hh24:mi:ss'') as createTime', 'flex:1', 6, 1, null, '超级管理员', to_date('05-01-2022 10:43:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138676', null, '436802a1c0074a79aafd00ce539166f4', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138677', null, '436802a1c0074a79aafd00ce539166f4', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('06-03-2024 15:51:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:11', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138678', null, '436802a1c0074a79aafd00ce539166f4', '功图采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:150', 3, 1, null, '系统管理员', to_date('13-06-2018 15:27:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-06-2018 15:27:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138679', null, '436802a1c0074a79aafd00ce539166f4', '计算状态', 'resultStatus', null, 4, 1, null, '系统管理员', to_date('30-11-2018 10:21:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138680', null, '436802a1c0074a79aafd00ce539166f4', '功图工况', 'resultName', 'width:130', 5, 1, null, '系统管理员', to_date('30-11-2018 18:11:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 18:11:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138681', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产液量(t/d)', 'liquidWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138682', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产油量(t/d)', 'oilWeightProduction', null, 7, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145273', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产水量(t/d)', 'waterWeightProduction', null, 8, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138683', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产液量(m^3/d)', 'liquidVolumetricProduction', null, 9, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138684', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产油量(m^3/d)', 'oilVolumetricProduction', null, 10, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145274', null, '436802a1c0074a79aafd00ce539166f4', '瞬时产水量(m^3/d)', 'waterVolumetricProduction', null, 11, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138685', null, '436802a1c0074a79aafd00ce539166f4', '原油密度(g/cm^3)', 'crudeoilDensity', null, 12, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138686', null, '436802a1c0074a79aafd00ce539166f4', '水密度(g/cm^3)', 'waterDensity', null, 13, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138687', null, '436802a1c0074a79aafd00ce539166f4', '天然气相对密度', 'naturalGasRelativeDensity', null, 14, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138688', null, '436802a1c0074a79aafd00ce539166f4', '饱和压力(MPa)', 'saturationPressure', null, 15, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138689', null, '436802a1c0074a79aafd00ce539166f4', '油层中部深度(m)', 'reservoirDepth', null, 16, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138690', null, '436802a1c0074a79aafd00ce539166f4', '油层中部温度(℃)', 'reservoirTemperature', null, 17, 1, null, '系统管理员', to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138691', null, '436802a1c0074a79aafd00ce539166f4', '油压(MPa)', 'tubingPressure', null, 18, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138692', null, '436802a1c0074a79aafd00ce539166f4', '套压(MPa)', 'casingPressure', null, 19, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138693', null, '436802a1c0074a79aafd00ce539166f4', '井口油温(℃)', 'wellHeadFluidTemperature', null, 20, 1, null, '系统管理员', to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138694', null, '436802a1c0074a79aafd00ce539166f4', '含水率(%)', 'weightWaterCut', null, 21, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138695', null, '436802a1c0074a79aafd00ce539166f4', '生产气油比(m^3/t)', 'productionGasOilRatio', 'width:120', 22, 1, null, '系统管理员', to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138696', null, '436802a1c0074a79aafd00ce539166f4', '动液面(m)', 'producingFluidLevel', null, 23, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138697', null, '436802a1c0074a79aafd00ce539166f4', '泵挂(m)', 'pumpSettingDepth', null, 24, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138699', null, '436802a1c0074a79aafd00ce539166f4', '泵筒类型', 'barrelTypeName', null, 26, 1, null, null, to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138700', null, '436802a1c0074a79aafd00ce539166f4', '泵级别', 'pumpGrade', null, 27, 1, null, '系统管理员', to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138701', null, '436802a1c0074a79aafd00ce539166f4', '泵径(mm)', 'pumpboreDiameter', null, 28, 1, null, '系统管理员', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138702', null, '436802a1c0074a79aafd00ce539166f4', '柱塞长(m)', 'plungerLength', null, 29, 1, null, '系统管理员', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138703', null, '436802a1c0074a79aafd00ce539166f4', '油管内径(mm)', 'tubingStringInsideDiameter', null, 30, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138704', null, '436802a1c0074a79aafd00ce539166f4', '套管内径(mm)', 'casingStringInsideDiameter', 'width:120', 31, 1, null, '系统管理员', to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147197', null, '436802a1c0074a79aafd00ce539166f4', '一级杆类型', 'rodTypeName1', null, 32, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138705', null, '436802a1c0074a79aafd00ce539166f4', '一级杆级别', 'rodGrade1', null, 33, 1, null, '系统管理员', to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138706', null, '436802a1c0074a79aafd00ce539166f4', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 34, 1, null, '系统管理员', to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138707', null, '436802a1c0074a79aafd00ce539166f4', '一级杆内径(mm)', 'rodInsideDiameter1', null, 35, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138708', null, '436802a1c0074a79aafd00ce539166f4', '一级杆长度(m)', 'rodLength1', null, 36, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147198', null, '436802a1c0074a79aafd00ce539166f4', '二级杆类型', 'rodTypeName2', null, 37, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138709', null, '436802a1c0074a79aafd00ce539166f4', '二级杆级别', 'rodGrade2', null, 38, 1, null, '系统管理员', to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138710', null, '436802a1c0074a79aafd00ce539166f4', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 39, 1, null, '系统管理员', to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138711', null, '436802a1c0074a79aafd00ce539166f4', '二级杆内径(mm)', 'rodInsideDiameter2', null, 40, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138712', null, '436802a1c0074a79aafd00ce539166f4', '二级杆长度(m)', 'rodLength2', null, 41, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147199', null, '436802a1c0074a79aafd00ce539166f4', '三级杆类型', 'rodTypeName3', null, 42, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138713', null, '436802a1c0074a79aafd00ce539166f4', '三级杆级别', 'rodGrade3', null, 43, 1, null, '系统管理员', to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138714', null, '436802a1c0074a79aafd00ce539166f4', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 44, 1, null, '系统管理员', to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138715', null, '436802a1c0074a79aafd00ce539166f4', '三级杆内径(mm)', 'rodInsideDiameter3', null, 45, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138716', null, '436802a1c0074a79aafd00ce539166f4', '三级杆长度(m)', 'rodLength3', null, 46, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147200', null, '436802a1c0074a79aafd00ce539166f4', '四级杆类型', 'rodTypeName4', null, 47, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138717', null, '436802a1c0074a79aafd00ce539166f4', '四级杆级别', 'rodGrade4', null, 48, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138718', null, '436802a1c0074a79aafd00ce539166f4', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 49, 1, null, '系统管理员', to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138719', null, '436802a1c0074a79aafd00ce539166f4', '四级杆内径(mm)', 'rodInsideDiameter4', null, 50, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138720', null, '436802a1c0074a79aafd00ce539166f4', '四级杆长度(m)', 'rodLength4', null, 51, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138721', null, '436802a1c0074a79aafd00ce539166f4', '锚定状态', 'anchoringStateName', null, 52, 1, null, null, to_date('23-02-2019 14:46:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:46:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138722', null, '436802a1c0074a79aafd00ce539166f4', '工况干预', 'manualInterventionResult', null, 53, 1, null, null, to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138723', null, '436802a1c0074a79aafd00ce539166f4', '净毛比(小数)', 'netGrossRatio', null, 54, 1, null, '系统管理员', to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138724', null, '436802a1c0074a79aafd00ce539166f4', '净毛值(m^3/d)', 'netGrossValue', null, 55, 1, null, null, to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138761', null, '436802a1c0074a79aafd00ce539166f4', '反演液面校正值(MPa)', 'levelCorrectValue', null, 56, 1, null, null, to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139255', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139256', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('06-03-2024 15:51:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139257', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日期', 'calDate', null, 3, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139258', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '功图工况', 'resultName', null, 4, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139259', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产液量(t/d)', 'liquidWeightProduction', null, 5, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139260', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产油量(t/d)', 'oilWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145277', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产水量(t/d)', 'waterWeightProduction', null, 7, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139261', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产液量(m^3/d)', 'liquidVolumetricProduction', null, 8, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139262', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产油量(m^3/d)', 'oilVolumetricProduction', null, 9, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145278', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累计产水量(m^3/d)', 'waterVolumetricProduction', null, 10, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139263', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '泵效(%)', 'pumpEff', null, 11, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139264', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '系统效率(%)', 'systemEfficiency', null, 12, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139265', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '功率平衡度(%)', 'wattDegreeBalance', 'width:120', 13, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139266', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '电流平衡度(%)', 'iDegreeBalance', 'width:120', 14, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139267', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日用电量(kw·h)', 'todayKWattH', 'width:120', 15, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139268', null, 'aad8b76fdaf84a1194de5ec0a4453631', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139269', null, 'aad8b76fdaf84a1194de5ec0a4453631', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('06-03-2024 15:51:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139270', null, 'aad8b76fdaf84a1194de5ec0a4453631', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:150', 3, 1, null, null, to_date('29-11-2018 18:48:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-11-2018 18:48:00', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139271', null, 'aad8b76fdaf84a1194de5ec0a4453631', '计算状态', 'resultStatus', null, 4, 1, null, '系统管理员', to_date('30-11-2018 10:21:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:36', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139272', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产液量(t/d)', 'liquidWeightProduction', null, 5, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139275', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产油量(t/d)', 'oilWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145275', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产水量(t/d)', 'waterWeightProduction', null, 7, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139273', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产液量(m^3/d)', 'liquidVolumetricProduction', null, 8, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139274', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产油量(m^3/d)', 'oilVolumetricProduction', null, 9, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145276', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬时产水量(m^3/d)', 'waterVolumetricProduction', null, 10, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139276', null, 'aad8b76fdaf84a1194de5ec0a4453631', '转速(r/min)', 'rpm', null, 11, 1, null, null, to_date('30-11-2018 17:32:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 17:32:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139277', null, 'aad8b76fdaf84a1194de5ec0a4453631', '原油密度(g/cm^3)', 'crudeoilDensity', null, 12, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139278', null, 'aad8b76fdaf84a1194de5ec0a4453631', '水密度(g/cm^3)', 'waterDensity', null, 13, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139279', null, 'aad8b76fdaf84a1194de5ec0a4453631', '天然气相对密度', 'naturalGasRelativeDensity', null, 14, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139280', null, 'aad8b76fdaf84a1194de5ec0a4453631', '饱和压力(MPa)', 'saturationPressure', null, 15, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139281', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油层中部深度(m)', 'reservoirDepth', null, 16, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139282', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油层中部温度(摄氏度)', 'reservoirTemperature', null, 17, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139283', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油压(MPa)', 'tubingPressure', null, 18, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139284', null, 'aad8b76fdaf84a1194de5ec0a4453631', '套压(MPa)', 'casingPressure', null, 19, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139285', null, 'aad8b76fdaf84a1194de5ec0a4453631', '井口油温(℃)', 'wellHeadFluidTemperature', null, 20, 1, null, '系统管理员', to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139286', null, 'aad8b76fdaf84a1194de5ec0a4453631', '含水率(%)', 'weightWaterCut', null, 21, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139287', null, 'aad8b76fdaf84a1194de5ec0a4453631', '生产气油比(m^3/t)', 'productionGasOilRatio', 'width:120', 22, 1, null, '系统管理员', to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139288', null, 'aad8b76fdaf84a1194de5ec0a4453631', '动液面(m)', 'producingFluidLevel', null, 23, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139289', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵挂(m)', 'pumpSettingDepth', null, 24, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139290', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵筒长(m)', 'barrelLength', null, 25, 1, null, '系统管理员', to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139291', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵级数', 'barrelSeries', null, 26, 1, null, '系统管理员', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139292', null, 'aad8b76fdaf84a1194de5ec0a4453631', '转子直径(mm)', 'rotorDiameter', null, 27, 1, null, '系统管理员', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139293', null, 'aad8b76fdaf84a1194de5ec0a4453631', '公称排量(ml/转)', 'qpr', null, 28, 1, null, null, to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139294', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油管内径(mm)', 'tubingStringInsideDiameter', null, 29, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139295', null, 'aad8b76fdaf84a1194de5ec0a4453631', '套管内径(mm)', 'casingStringInsideDiameter', 'width:120', 30, 1, null, '系统管理员', to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147201', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆类型', 'rodTypeName1', null, 31, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139296', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆级别', 'rodGrade1', null, 32, 1, null, '系统管理员', to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139297', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 33, 1, null, '系统管理员', to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139298', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆内径(mm)', 'rodInsideDiameter1', null, 34, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139299', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆长度(m)', 'rodLength1', null, 35, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147202', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆类型', 'rodTypeName2', null, 36, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139300', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆级别', 'rodGrade2', null, 37, 1, null, '系统管理员', to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139301', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 38, 1, null, '系统管理员', to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139302', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆内径(mm)', 'rodInsideDiameter2', null, 39, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139303', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆长度(m)', 'rodGrade2', null, 40, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147203', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆类型', 'rodTypeName3', null, 41, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139304', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆级别', 'rodGrade3', null, 42, 1, null, '系统管理员', to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139305', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 43, 1, null, '系统管理员', to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139306', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆内径(mm)', 'rodInsideDiameter3', null, 44, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139307', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆长度(m)', 'rodGrade3', null, 45, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147204', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆类型', 'rodTypeName4', null, 46, 1, null, null, to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139308', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆级别', 'rodGrade4', null, 47, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139309', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 48, 1, null, '系统管理员', to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139310', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆内径(mm)', 'rodInsideDiameter4', null, 49, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139311', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆长度(m)', 'rodGrade4', null, 50, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139312', null, 'aad8b76fdaf84a1194de5ec0a4453631', '净毛比', 'netGrossRatio', null, 52, 1, null, '系统管理员', to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139313', null, 'aad8b76fdaf84a1194de5ec0a4453631', '净毛值(m^3/d)', 'netGrossValue', null, 53, 1, null, '??????????????í??±', to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139314', null, '8122b170c0ca4deb87159c931ab251f3', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139315', null, '8122b170c0ca4deb87159c931ab251f3', '设备名称', 'deviceName', null, 2, 1, null, '超级管理员', to_date('06-03-2024 15:51:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:59', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139316', null, '8122b170c0ca4deb87159c931ab251f3', '日期', 'calDate', null, 3, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139317', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产液量(t/d)', 'liquidWeightProduction', null, 4, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139318', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产油量(t/d)', 'oilWeightProduction', null, 5, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145279', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产水量(t/d)', 'waterWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139319', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产液量(m^3/d)', 'liquidVolumetricProduction', null, 7, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139320', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产油量(m^3/d)', 'oilVolumetricProduction', null, 8, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145280', null, '8122b170c0ca4deb87159c931ab251f3', '日累计产水量(m^3/d)', 'waterVolumetricProduction', null, 9, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139321', null, '8122b170c0ca4deb87159c931ab251f3', '泵效(%)', 'pumpEff', null, 10, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139322', null, '8122b170c0ca4deb87159c931ab251f3', '系统效率(%)', 'systemEfficiency', null, 11, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139323', null, '8122b170c0ca4deb87159c931ab251f3', '日用电量(kw·h)', 'todayKWattH', 'width:120', 12, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114832', null, '8ab792e089494533be910699d426c7d5', '单位名称', 'text', 'flex:3', 1, 1, null, '系统管理员', to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114834', null, '8ab792e089494533be910699d426c7d5', '排序编号', 'orgSeq', 'flex:1', 2, 1, null, '系统管理员', to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114885', null, '5ba761c1383f498f9ac97c9a8ab6d847', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114845', null, '5ba761c1383f498f9ac97c9a8ab6d847', '用户名称', 'userName', null, 2, 1, null, '系统管理员', to_date('24-12-2021 13:39:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:27', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114847', null, '5ba761c1383f498f9ac97c9a8ab6d847', '用户账号', 'userId', null, 3, 1, null, '系统管理员', to_date('24-12-2021 13:39:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114849', null, '5ba761c1383f498f9ac97c9a8ab6d847', '角色', 'userTypeName', null, 4, 1, null, '系统管理员', to_date('24-12-2021 13:39:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114850', null, '5ba761c1383f498f9ac97c9a8ab6d847', '电话', 'userPhone', null, 5, 1, null, '系统管理员', to_date('24-12-2021 19:53:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:53:43', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114851', null, '5ba761c1383f498f9ac97c9a8ab6d847', '邮箱', 'userInEmail', null, 6, 1, null, '系统管理员', to_date('24-12-2021 19:53:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:53:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('116092', null, '5ba761c1383f498f9ac97c9a8ab6d847', '快捷登录', 'userQuickLoginName', null, 7, 1, null, '系统管理员', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128635', null, '5ba761c1383f498f9ac97c9a8ab6d847', '接收报警短信', 'receiveSMSName', null, 8, 1, null, '系统管理员', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128636', null, '5ba761c1383f498f9ac97c9a8ab6d847', '接收报警邮件', 'receiveMailName', null, 9, 1, null, '系统管理员', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128604', null, '5ba761c1383f498f9ac97c9a8ab6d847', '状态', 'userEnableName', null, 10, 1, null, null, null, null);

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128599', null, '5ba761c1383f498f9ac97c9a8ab6d847', '创建时间', 'userRegtime', 'width:150', 11, 1, null, null, null, null);

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114886', null, '220c349e246e47a39a818023f1c97a63', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114854', 'sys', '220c349e246e47a39a818023f1c97a63', '角色名称', 'roleName', 'flex:1', 2, 1, 'sys', '系统管理员', to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128641', null, '220c349e246e47a39a818023f1c97a63', '角色等级', 'roleLevel', 'flex:1', 3, 1, null, null, to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('122399', null, '220c349e246e47a39a818023f1c97a63', '数据显示级别', 'showLevel', 'flex:1', 4, 1, null, null, to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114856', null, '220c349e246e47a39a818023f1c97a63', '角色描述', 'remark', 'flex:3', 7, 1, null, '系统管理员', to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114888', null, '87808f225d7240f68c2ab879347d818a', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114867', 'sys', '87808f225d7240f68c2ab879347d818a', '设备名称', 'deviceName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('116688', null, '87808f225d7240f68c2ab879347d818a', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133098', null, '87808f225d7240f68c2ab879347d818a', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136595', null, '87808f225d7240f68c2ab879347d818a', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119811', null, '87808f225d7240f68c2ab879347d818a', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135315', null, '87808f225d7240f68c2ab879347d818a', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114915', null, '87808f225d7240f68c2ab879347d818a', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136175', null, '87808f225d7240f68c2ab879347d818a', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('10-01-2023 09:00:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2023 09:00:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114916', null, '87808f225d7240f68c2ab879347d818a', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135535', null, '87808f225d7240f68c2ab879347d818a', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132518', null, '87808f225d7240f68c2ab879347d818a', '状态', 'statusName', null, 13, 1, null, null, to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135215', null, '87808f225d7240f68c2ab879347d818a', '隶属单位', 'allPath', null, 14, 1, null, null, to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('116923', null, '87808f225d7240f68c2ab879347d818a', '排序编号', 'sortNum', null, 15, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('145297', null, '87808f225d7240f68c2ab879347d818a', '更新时间', 'productionDataUpdateTime', 'width:120', 16, 1, null, null, to_date('17-11-2023 10:25:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-11-2023 10:25:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134035', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '序号', 'id', 'width:50', 1, 1, null, null, to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134037', 'sys', 'd8cd980aa8344c399b9cf11268b6ed8f', '设备名称', 'deviceName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134039', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134040', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136600', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134041', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135321', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134042', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136195', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('11-01-2023 10:39:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-01-2023 10:39:54', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134043', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135540', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137915', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频监控路径1', 'videoUrl1', null, 13, 0, null, null, to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139579', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频密钥1', 'videoKeyName1', null, 14, 0, null, null, to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137916', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频监控路径2', 'videoUrl2', null, 15, 0, null, null, to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139580', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频密钥2', 'videoKeyName2', null, 16, 0, null, null, to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134044', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '状态', 'statusName', null, 17, 1, null, null, to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134045', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '排序编号', 'sortNum', null, 18, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137917', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '原油密度(g/cm^3)', 'crudeOilDensity', null, 19, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137918', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '水密度(g/cm^3)', 'waterDensity', null, 20, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137919', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '天然气相对密度', 'naturalGasRelativeDensity', null, 21, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137920', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '饱和压力(MPa)', 'saturationPressure', null, 22, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137921', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油层中部深度(m)', 'reservoirDepth', null, 23, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137922', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油层中部温度(℃)', 'reservoirTemperature', null, 24, 0, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137923', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油压(MPa)', 'tubingPressure', null, 25, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137924', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '套压(MPa)', 'casingPressure', null, 26, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137925', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '井口温度(℃)', 'wellHeadTemperature', null, 27, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137926', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '含水率(%)', 'waterCut', null, 28, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137927', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 29, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137928', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '动液面(m)', 'producingfluidLevel', null, 30, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137929', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵挂(m)', 'pumpSettingDepth', null, 31, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137931', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵筒类型', 'barrelType', null, 33, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137932', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵级别', 'pumpGrade', null, 34, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137933', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵径(mm)', 'pumpBoreDiameter', null, 35, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137934', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '柱塞长(m)', 'plungerLength', null, 36, 0, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137935', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油管内径(mm)', 'tubingStringInsideDiameter', null, 37, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137936', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '套管内径(mm)', 'casingStringInsideDiameter', null, 38, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137937', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆级别', 'rodGrade1', null, 39, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137938', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 40, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137939', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆内径(mm)', 'rodInsideDiameter1', null, 41, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137940', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆长度(m)', 'rodLength1', null, 42, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137941', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆级别', 'rodGrade2', null, 43, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137942', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 44, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137943', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆内径(mm)', 'rodInsideDiameter2', null, 45, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137944', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆长度(m)', 'rodLength2', null, 46, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137945', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆级别', 'rodGrade3', null, 47, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137946', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 48, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137947', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆内径(mm)', 'rodInsideDiameter3', null, 49, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137948', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆长度(m)', 'rodLength3', null, 50, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137949', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆级别', 'rodGrade4', null, 51, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137950', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 52, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137951', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆内径(mm)', 'rodInsideDiameter4', null, 53, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137952', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆长度(m)', 'rodLength4', null, 54, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137953', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '工况干预', 'manualInterventionResultName', null, 55, 0, null, null, to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137954', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '净毛比(小数)', 'netGrossRatio', null, 56, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137955', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '净毛值(m^3/d)', 'netGrossValue', null, 57, 0, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137956', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '反演液面校正值(MPa)', 'levelCorrectValue', null, 58, 0, null, null, to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137957', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '抽油机厂家', 'manufacturer', null, 59, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137958', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '抽油机型号', 'model', null, 60, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137959', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '铭牌冲程', 'stroke', null, 61, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137960', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '平衡块重量(kN)', 'balanceWeight', null, 68, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137961', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '平衡块位置(m)', 'balancePosition', null, 69, 0, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146659', null, '1404100741bc42799be5b7cbebf4b649', '序号', 'id', 'width:50', 1, 1, null, null, to_date('10-11-2021 14:21:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-11-2021 14:21:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146660', null, '1404100741bc42799be5b7cbebf4b649', '设备名称', 'name', null, 2, 1, 'sys', null, to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146661', null, '1404100741bc42799be5b7cbebf4b649', '类型', 'type', null, 3, 1, 'sys', null, to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147076', null, '1404100741bc42799be5b7cbebf4b649', '厂家', 'manufacturer', null, 4, 1, null, null, to_date('22-03-2024 16:59:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2024 16:59:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146662', null, '1404100741bc42799be5b7cbebf4b649', '规格型号', 'model', null, 5, 1, null, null, to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146663', null, '1404100741bc42799be5b7cbebf4b649', '备注', 'remark', null, 6, 1, null, null, to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('146664', null, '1404100741bc42799be5b7cbebf4b649', '排序编号', 'sort', null, 7, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114837', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块名称', 'text', 'flex:2', 1, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114838', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块简介', 'mdShowname', 'flex:2', 2, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114839', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块编码', 'mdCode', 'flex:2', 3, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114840', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块视图', 'mdUrl', 'flex:3', 4, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114841', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块控制器', 'mdControl', 'flex:3', 5, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114842', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块图标', 'mdIcon', 'flex:1', 6, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114843', null, 'b6ef8f3a49094768b3231d5678fc9cbc', '模块类别', 'mdTypeName', 'flex:1', 7, 1, null, '系统管理员', to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114844', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块排序', 'mdSeq', 'flex:1', 8, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114884', null, 'b8a408839dd8498d9a19fc65f7406ed4', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114827', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典名称', 'cname', 'flex:2', 2, 1, null, '系统管理员', to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114828', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典代码', 'ename', 'flex:3', 3, 1, null, '系统管理员', to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114829', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '字典顺序', 'sorts', 'flex:1', 4, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('147379', null, 'b8a408839dd8498d9a19fc65f7406ed4', '隶属模块', 'moduleName', 'flex:1', 5, 1, null, null, to_date('25-04-2024 14:06:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-04-2024 14:06:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114830', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '创建人', 'creator', 'flex:1', 6, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114831', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '创建时间', 'updatetime', 'flex:3', 7, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'));