/*==============================================================*/
/* 初始化tbl_dist_name数据                                          */
/*==============================================================*/
insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('7f13446d19b4497986980fa16a750f95', null, '实时监控_抽油机井概览', 'realTimeMonitoring_RPCOverview', 11101, 0, '超级管理员', '超级管理员', to_date('29-08-2022 16:08:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-08-2022 16:08:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('d4205cd00a994817a51c2cab6d5fa0ab', null, '实时监控_抽油机井设备信息', 'realTimeMonitoring_RPCDeviceInfo', 11102, 0, '超级管理员', '超级管理员', to_date('26-05-2022 20:07:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 20:07:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('e0f5f3ff8a1f46678c284fba9cc113e8', null, '实时监控_螺杆泵井概览', 'realTimeMonitoring_PCPOverview', 11103, 0, '超级管理员', '超级管理员', to_date('24-05-2022 08:35:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 08:35:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('ac7916c134074f39a27325eb7e076049', null, '实时监控_螺杆泵设备信息', 'realTimeMonitoring_PCPDeviceInfo', 11104, 0, '超级管理员', '超级管理员', to_date('26-05-2022 20:07:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 20:07:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('cd7b24562b924d19b556de31256e22a1', null, '历史查询_抽油机井数据', 'historyQuery_RPCHistoryData', 12101, 0, '超级管理员', '超级管理员', to_date('11-05-2022 16:12:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 16:12:16', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('fb7d070a349c403b8a26d71c12af7a05', null, '历史查询_螺杆泵井数据', 'historyQuery_PCPHistoryData', 12102, 0, '系统管理员', '系统管理员', to_date('25-11-2021 19:45:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-11-2021 19:45:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('0d4297cb4db44bb3a9a3d5d983007039', 'system', '历史查询_图形叠加', 'historyQuery_FESDiagramOverlay', 12103, 0, '超级管理员', '超级管理员', to_date('26-04-2022 10:17:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-04-2022 10:17:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('cdd198534d5849b7a27054e0f2593ff3', null, '故障查询_通信状态报警', 'alarmQuery_CommStatusAlarm', 14101, 0, '系统管理员', '系统管理员', to_date('02-11-2021 15:20:57', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2021 15:20:57', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('e2924366ab174d4b9a096be969934985', 'system', '故障查询_数值量报警', 'alarmQuery_NumericValueAlarm', 14102, 0, '系统管理员', '系统管理员', to_date('16-09-2021 13:50:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2021 13:50:40', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('b09082f4272e4768994db398e14bc3f2', 'system', '故障查询_枚举量报警', 'alarmQuery_EnumValueAlarm', 14103, 0, '系统管理员', '系统管理员', to_date('07-10-2021 19:07:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-10-2021 19:07:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('b71c1a2c9d574fe482080a56c7c780a9', null, '故障查询_开关量报警', 'alarmQuery_SwitchingValueAlarm', 14104, 0, '系统管理员', '系统管理员', to_date('07-10-2021 19:06:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-10-2021 19:06:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('ad646d19fcaa4fbd9077dbf7a826b107', 'system', '日志查询_设备操作日志', 'logQuery_DeviceOperationLog', 15101, 0, '系统管理员', '系统管理员', to_date('07-09-2021 17:05:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-09-2021 17:05:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('167aeb3aca384afda8655d63aedee484', 'system', '日志查询_系统日志', 'logQuery_SystemLog', 15102, 0, '系统管理员', '系统管理员', to_date('07-09-2021 19:04:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-09-2021 19:04:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('436802a1c0074a79aafd00ce539166f4', null, '计算维护_抽油机井单条记录', 'calculateManager_RPCSingleRecord', 16101, 0, '系统管理员', '系统管理员', to_date('11-06-2020 17:58:08', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-06-2020 17:58:08', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('cf1c0981f31242f9b3e84810bdc0a19f', 'system', '计算维护_抽油机井记录汇总', 'calculateManager_RPCTotalRecord', 16102, 0, '超级管理员', '超级管理员', to_date('07-05-2022 19:56:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-05-2022 19:56:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('aad8b76fdaf84a1194de5ec0a4453631', null, '计算维护_螺杆泵井单条记录', 'calculateManager_PCPSingleRecord', 16103, 0, '系统管理员', '系统管理员', to_date('30-11-2018 16:40:57', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:40:57', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('8122b170c0ca4deb87159c931ab251f3', 'system', '计算维护_螺杆泵井记录汇总', 'calculateManager_PCPTotalRecord', 16104, 0, '超级管理员', '超级管理员', to_date('07-05-2022 19:56:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-05-2022 19:56:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('8ab792e089494533be910699d426c7d5', null, '组织用户_单位列表', 'orgAndUser_OrgManage', 21101, 0, '系统管理员', '系统管理员', to_date('24-12-2021 09:33:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 09:33:43', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('5ba761c1383f498f9ac97c9a8ab6d847', null, '组织用户_用户列表', 'orgAndUser_UserManage', 21102, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:45:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:45:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('220c349e246e47a39a818023f1c97a63', null, '角色管理_角色列表', 'role_RoleManage', 21103, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:46:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:46:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('87808f225d7240f68c2ab879347d818a', null, '井名信息_抽油机井列表', 'deviceInfo_RPCDeviceManager', 22101, 0, '系统管理员', '系统管理员', to_date('21-12-2021 08:47:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-12-2021 08:47:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('d8cd980aa8344c399b9cf11268b6ed8f', 'system', '井名信息_抽油机井批量添加', 'deviceInfo_RPCDeviceBatchAdd', 22102, 0, '超级管理员', '超级管理员', to_date('26-05-2022 09:26:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:26:12', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('b14377621d74442eb1127de094dfc903', null, '井名信息_螺杆泵井列表', 'deviceInfo_PCPDeviceManager', 22103, 0, '系统管理员', '系统管理员', to_date('21-12-2021 17:41:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-12-2021 17:41:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('7b727b60e9114ebc9e35c4312c8c31c0', 'system', '井名信息_螺杆泵井批量添加', 'deviceInfo_PCPDeviceBatchAdd', 22104, 0, '超级管理员', '超级管理员', to_date('26-05-2022 09:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('1404100741bc42799be5b7cbebf4b649', null, '抽油机设备_抽油机设备列表', 'pumpingDevice_PumpingModelManager', 22105, 0, '超级管理员', '超级管理员', to_date('21-03-2022 17:44:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-03-2022 17:44:00', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('2b4cd8cb8c6844769c66b038246c27bf', null, '短信设备_短信设备列表', 'SMSDevice_SMSDeviceManager', 22106, 0, '系统管理员', '系统管理员', to_date('21-12-2021 20:19:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-12-2021 20:19:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('b6ef8f3a49094768b3231d5678fc9cbc', null, '模块配置_模块列表', 'module_ModuleManage', 23101, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('b8a408839dd8498d9a19fc65f7406ed4', null, '字典配置_字典列表', 'dictionary_DataDictionaryManage', 23102, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'));

/*==============================================================*/
/* 初始化tbl_dist_item数据                                          */
/*==============================================================*/
insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133335', null, '0d4297cb4db44bb3a9a3d5d983007039', '序号', 'id', 'width:50', 1, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133336', null, '0d4297cb4db44bb3a9a3d5d983007039', '采集时间', 'acqTime', 'width:150', 2, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138735', null, '0d4297cb4db44bb3a9a3d5d983007039', '通信状态', 'commStatusName', null, 3, 0, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138736', null, '0d4297cb4db44bb3a9a3d5d983007039', '在线时间', 'commTime', null, 4, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138737', null, '0d4297cb4db44bb3a9a3d5d983007039', '在线时率', 'commTimeEfficiency', null, 5, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138738', null, '0d4297cb4db44bb3a9a3d5d983007039', '在线区间', 'commRange', null, 6, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138739', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行状态', 'runStatusName', null, 7, 0, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138740', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行时间', 'runTime', null, 8, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138741', null, '0d4297cb4db44bb3a9a3d5d983007039', '运行时率', 'runTimeEfficiency', null, 9, 0, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('133338', null, '0d4297cb4db44bb3a9a3d5d983007039', '功图产液量(t/d)', 'liquidWeightProduction', null, 26, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133339', null, '0d4297cb4db44bb3a9a3d5d983007039', '累计产液量(t/d)', 'liquidWeightProduction_L', null, 27, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133340', null, '0d4297cb4db44bb3a9a3d5d983007039', '功图产液量(m^3/d)', 'liquidVolumetricProduction', null, 28, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133341', null, '0d4297cb4db44bb3a9a3d5d983007039', '累计产液量(m^3/d)', 'liquidVolumetricProduction_L', null, 29, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138749', null, '0d4297cb4db44bb3a9a3d5d983007039', '有功功率(kW)', 'averageWatt', null, 30, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138750', null, '0d4297cb4db44bb3a9a3d5d983007039', '光杆功率(kW)', 'polishrodPower', null, 31, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138751', null, '0d4297cb4db44bb3a9a3d5d983007039', '水功率(kW)', 'waterPower', null, 32, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138752', null, '0d4297cb4db44bb3a9a3d5d983007039', '地面效率(%)', 'surfaceSystemEfficiency', null, 33, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138753', null, '0d4297cb4db44bb3a9a3d5d983007039', '井下效率(%)', 'welldownSystemEfficiency', null, 34, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138754', null, '0d4297cb4db44bb3a9a3d5d983007039', '系统效率(%)', 'systemEfficiency', null, 35, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138755', null, '0d4297cb4db44bb3a9a3d5d983007039', '吨液百米耗电量(kW· h/100m· t)', 'energyper100mlift', null, 36, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138756', null, '0d4297cb4db44bb3a9a3d5d983007039', '总泵效(%)', 'pumpEff', null, 37, 0, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139724', null, '0d4297cb4db44bb3a9a3d5d983007039', '上冲程最大电流(A)', 'upStrokeIMax', null, 38, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139725', null, '0d4297cb4db44bb3a9a3d5d983007039', '下冲程最大电流(A)', 'downStrokeIMax', null, 39, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138757', null, '0d4297cb4db44bb3a9a3d5d983007039', '电流平衡度(%)', 'iDegreeBalance', null, 40, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139726', null, '0d4297cb4db44bb3a9a3d5d983007039', '上冲程最大功率(kW)', 'upStrokeWattMax', null, 41, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139727', null, '0d4297cb4db44bb3a9a3d5d983007039', '下冲程最大功率(kW)', 'downStrokeWattMax', null, 42, 1, null, null, to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138758', null, '0d4297cb4db44bb3a9a3d5d983007039', '功率平衡度(%)', 'wattDegreeBalance', null, 43, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138759', null, '0d4297cb4db44bb3a9a3d5d983007039', '移动距离(cm)', 'deltaradius', null, 44, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138760', null, '0d4297cb4db44bb3a9a3d5d983007039', '日用电量(kW·h)', 'todayKWattH', null, 45, 0, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('114886', null, '220c349e246e47a39a818023f1c97a63', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114854', 'sys', '220c349e246e47a39a818023f1c97a63', '角色名称', 'roleName', 'flex:1', 2, 1, 'sys', '系统管理员', to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128641', null, '220c349e246e47a39a818023f1c97a63', '角色等级', 'roleLevel', 'flex:1', 3, 1, null, null, to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('122399', null, '220c349e246e47a39a818023f1c97a63', '数据显示级别', 'showLevel', 'flex:1', 4, 1, null, null, to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('116763', null, '220c349e246e47a39a818023f1c97a63', '设备控制权限', 'roleFlagName', 'flex:1', 5, 1, null, '系统管理员', to_date('28-12-2021 17:23:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-12-2021 17:23:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136435', null, '220c349e246e47a39a818023f1c97a63', '报表编辑权限', 'roleReportEditName', 'flex:1', 6, 1, null, null, to_date('08-02-2023 10:27:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-02-2023 10:27:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114856', null, '220c349e246e47a39a818023f1c97a63', '角色描述', 'remark', 'flex:3', 7, 1, null, '系统管理员', to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138676', null, '436802a1c0074a79aafd00ce539166f4', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138677', null, '436802a1c0074a79aafd00ce539166f4', '井名', 'wellName', null, 2, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138678', null, '436802a1c0074a79aafd00ce539166f4', '功图采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:150', 3, 1, null, '系统管理员', to_date('13-06-2018 15:27:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-06-2018 15:27:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138679', null, '436802a1c0074a79aafd00ce539166f4', '计算状态', 'resultStatus', null, 4, 1, null, '系统管理员', to_date('30-11-2018 10:21:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138680', null, '436802a1c0074a79aafd00ce539166f4', '功图工况', 'resultName', 'width:130', 5, 1, null, '系统管理员', to_date('30-11-2018 18:11:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 18:11:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138681', null, '436802a1c0074a79aafd00ce539166f4', '产液量(t/d)', 'liquidWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138682', null, '436802a1c0074a79aafd00ce539166f4', '产油量(t/d)', 'oilWeightProduction', null, 7, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138683', null, '436802a1c0074a79aafd00ce539166f4', '产液量(m^3/d)', 'liquidVolumetricProduction', null, 8, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138684', null, '436802a1c0074a79aafd00ce539166f4', '产油量(m^3/d)', 'oilVolumetricProduction', null, 9, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138685', null, '436802a1c0074a79aafd00ce539166f4', '原油密度(g/cm^3)', 'crudeoilDensity', null, 10, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138686', null, '436802a1c0074a79aafd00ce539166f4', '水密度(g/cm^3)', 'waterDensity', null, 11, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138687', null, '436802a1c0074a79aafd00ce539166f4', '天然气相对密度', 'naturalGasRelativeDensity', null, 12, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138688', null, '436802a1c0074a79aafd00ce539166f4', '饱和压力(MPa)', 'saturationPressure', null, 13, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138689', null, '436802a1c0074a79aafd00ce539166f4', '油层中部深度(m)', 'reservoirDepth', null, 14, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138690', null, '436802a1c0074a79aafd00ce539166f4', '油层中部温度(℃)', 'reservoirTemperature', null, 15, 1, null, '系统管理员', to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138691', null, '436802a1c0074a79aafd00ce539166f4', '油压(MPa)', 'tubingPressure', null, 16, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138692', null, '436802a1c0074a79aafd00ce539166f4', '套压(MPa)', 'casingPressure', null, 17, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138693', null, '436802a1c0074a79aafd00ce539166f4', '井口油温(℃)', 'wellHeadFluidTemperature', null, 18, 1, null, '系统管理员', to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138694', null, '436802a1c0074a79aafd00ce539166f4', '含水率(%)', 'weightWaterCut', null, 19, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138695', null, '436802a1c0074a79aafd00ce539166f4', '生产气油比(m^3/t)', 'productionGasOilRatio', 'width:120', 20, 1, null, '系统管理员', to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138696', null, '436802a1c0074a79aafd00ce539166f4', '动液面(m)', 'producingFluidLevel', null, 21, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138697', null, '436802a1c0074a79aafd00ce539166f4', '泵挂(m)', 'pumpSettingDepth', null, 22, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138699', null, '436802a1c0074a79aafd00ce539166f4', '泵筒类型', 'barrelTypeName', null, 24, 1, null, null, to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138700', null, '436802a1c0074a79aafd00ce539166f4', '泵级别', 'pumpGrade', null, 25, 1, null, '系统管理员', to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138701', null, '436802a1c0074a79aafd00ce539166f4', '泵径(mm)', 'pumpboreDiameter', null, 26, 1, null, '系统管理员', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138702', null, '436802a1c0074a79aafd00ce539166f4', '柱塞长(m)', 'plungerLength', null, 27, 1, null, '系统管理员', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138703', null, '436802a1c0074a79aafd00ce539166f4', '油管内径(mm)', 'tubingStringInsideDiameter', null, 28, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138704', null, '436802a1c0074a79aafd00ce539166f4', '套管内径(mm)', 'casingStringInsideDiameter', 'width:120', 29, 1, null, '系统管理员', to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138705', null, '436802a1c0074a79aafd00ce539166f4', '一级杆级别', 'rodGrade1', null, 30, 1, null, '系统管理员', to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138706', null, '436802a1c0074a79aafd00ce539166f4', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 31, 1, null, '系统管理员', to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138707', null, '436802a1c0074a79aafd00ce539166f4', '一级杆内径(mm)', 'rodInsideDiameter1', null, 32, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138708', null, '436802a1c0074a79aafd00ce539166f4', '一级杆长度(m)', 'rodLength1', null, 33, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138709', null, '436802a1c0074a79aafd00ce539166f4', '二级杆级别', 'rodGrade2', null, 34, 1, null, '系统管理员', to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138710', null, '436802a1c0074a79aafd00ce539166f4', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 35, 1, null, '系统管理员', to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138711', null, '436802a1c0074a79aafd00ce539166f4', '二级杆内径(mm)', 'rodInsideDiameter2', null, 36, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138712', null, '436802a1c0074a79aafd00ce539166f4', '二级杆长度(m)', 'rodLength2', null, 37, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138713', null, '436802a1c0074a79aafd00ce539166f4', '三级杆级别', 'rodGrade3', null, 38, 1, null, '系统管理员', to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138714', null, '436802a1c0074a79aafd00ce539166f4', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 39, 1, null, '系统管理员', to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138715', null, '436802a1c0074a79aafd00ce539166f4', '三级杆内径(mm)', 'rodInsideDiameter3', null, 40, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138716', null, '436802a1c0074a79aafd00ce539166f4', '三级杆长度(m)', 'rodLength3', null, 41, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138717', null, '436802a1c0074a79aafd00ce539166f4', '四级杆级别', 'rodGrade4', null, 42, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138718', null, '436802a1c0074a79aafd00ce539166f4', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 43, 1, null, '系统管理员', to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138719', null, '436802a1c0074a79aafd00ce539166f4', '四级杆内径(mm)', 'rodInsideDiameter4', null, 44, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138720', null, '436802a1c0074a79aafd00ce539166f4', '四级杆长度(m)', 'rodLength4', null, 45, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138721', null, '436802a1c0074a79aafd00ce539166f4', '锚定状态', 'anchoringStateName', null, 46, 1, null, null, to_date('23-02-2019 14:46:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:46:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138722', null, '436802a1c0074a79aafd00ce539166f4', '工况干预', 'manualInterventionResult', null, 47, 1, null, null, to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138723', null, '436802a1c0074a79aafd00ce539166f4', '净毛比(小数)', 'netGrossRatio', null, 48, 1, null, '系统管理员', to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138724', null, '436802a1c0074a79aafd00ce539166f4', '净毛值(m^3/d)', 'netGrossValue', null, 49, 1, null, null, to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138761', null, '436802a1c0074a79aafd00ce539166f4', '反演液面校正值(MPa)', 'levelCorrectValue', null, 50, 1, null, null, to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('134094', null, '7b727b60e9114ebc9e35c4312c8c31c0', '序号', 'id', 'width:50', 1, 1, null, null, to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134096', 'sys', '7b727b60e9114ebc9e35c4312c8c31c0', '井名', 'wellName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134098', null, '7b727b60e9114ebc9e35c4312c8c31c0', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134099', null, '7b727b60e9114ebc9e35c4312c8c31c0', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136601', null, '7b727b60e9114ebc9e35c4312c8c31c0', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134100', null, '7b727b60e9114ebc9e35c4312c8c31c0', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135322', null, '7b727b60e9114ebc9e35c4312c8c31c0', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134101', null, '7b727b60e9114ebc9e35c4312c8c31c0', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136215', null, '7b727b60e9114ebc9e35c4312c8c31c0', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('11-01-2023 16:51:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-01-2023 16:51:12', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134102', null, '7b727b60e9114ebc9e35c4312c8c31c0', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135539', null, '7b727b60e9114ebc9e35c4312c8c31c0', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137962', null, '7b727b60e9114ebc9e35c4312c8c31c0', '视频监控路径1', 'videoUrl1', null, 13, 1, null, null, to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139581', null, '7b727b60e9114ebc9e35c4312c8c31c0', '视频密钥1', 'videoKeyName1', null, 14, 1, null, null, to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137963', null, '7b727b60e9114ebc9e35c4312c8c31c0', '视频监控路径2', 'videoUrl2', null, 15, 1, null, null, to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139582', null, '7b727b60e9114ebc9e35c4312c8c31c0', '视频密钥2', 'videoKeyName2', null, 16, 1, null, null, to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134103', null, '7b727b60e9114ebc9e35c4312c8c31c0', '状态', 'statusName', null, 17, 1, null, null, to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134104', null, '7b727b60e9114ebc9e35c4312c8c31c0', '排序编号', 'sortNum', null, 18, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137964', null, '7b727b60e9114ebc9e35c4312c8c31c0', '原油密度(g/cm^3)', 'crudeOilDensity', null, 19, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137965', null, '7b727b60e9114ebc9e35c4312c8c31c0', '水密度(g/cm^3)', 'waterDensity', null, 20, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137966', null, '7b727b60e9114ebc9e35c4312c8c31c0', '天然气相对密度', 'naturalGasRelativeDensity', null, 21, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137967', null, '7b727b60e9114ebc9e35c4312c8c31c0', '饱和压力(MPa)', 'saturationPressure', null, 22, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137968', null, '7b727b60e9114ebc9e35c4312c8c31c0', '油层中部深度(m)', 'reservoirDepth', null, 23, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137969', null, '7b727b60e9114ebc9e35c4312c8c31c0', '油层中部温度(℃)', 'reservoirTemperature', null, 24, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137970', null, '7b727b60e9114ebc9e35c4312c8c31c0', '油压(MPa)', 'tubingPressure', null, 25, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137971', null, '7b727b60e9114ebc9e35c4312c8c31c0', '套压(MPa)', 'casingPressure', null, 26, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137972', null, '7b727b60e9114ebc9e35c4312c8c31c0', '井口温度(℃)', 'wellHeadTemperature', null, 27, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137973', null, '7b727b60e9114ebc9e35c4312c8c31c0', '含水率(%)', 'waterCut', null, 28, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137974', null, '7b727b60e9114ebc9e35c4312c8c31c0', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 29, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137975', null, '7b727b60e9114ebc9e35c4312c8c31c0', '动液面(m)', 'producingfluidLevel', null, 30, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137976', null, '7b727b60e9114ebc9e35c4312c8c31c0', '泵挂(m)', 'pumpSettingDepth', null, 31, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137977', null, '7b727b60e9114ebc9e35c4312c8c31c0', '泵筒长(m)', 'barrelLength', null, 32, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137978', null, '7b727b60e9114ebc9e35c4312c8c31c0', '泵级数', 'barrelSeries', null, 33, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137979', null, '7b727b60e9114ebc9e35c4312c8c31c0', '转子直径(mm)', 'rotorDiameter', null, 34, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137980', null, '7b727b60e9114ebc9e35c4312c8c31c0', '每转公称排量(ml/r)', 'QPR', null, 35, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137981', null, '7b727b60e9114ebc9e35c4312c8c31c0', '油管内径(mm)', 'tubingStringInsideDiameter', null, 36, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137982', null, '7b727b60e9114ebc9e35c4312c8c31c0', '套管内径(mm)', 'casingStringInsideDiameter', null, 37, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137983', null, '7b727b60e9114ebc9e35c4312c8c31c0', '一级杆级别', 'rodGrade1', null, 38, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137984', null, '7b727b60e9114ebc9e35c4312c8c31c0', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 39, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137985', null, '7b727b60e9114ebc9e35c4312c8c31c0', '一级杆内径(mm)', 'rodInsideDiameter1', null, 40, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137986', null, '7b727b60e9114ebc9e35c4312c8c31c0', '一级杆长度(m)', 'rodLength1', null, 41, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137987', null, '7b727b60e9114ebc9e35c4312c8c31c0', '二级杆级别', 'rodGrade2', null, 42, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137988', null, '7b727b60e9114ebc9e35c4312c8c31c0', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 43, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137989', null, '7b727b60e9114ebc9e35c4312c8c31c0', '二级杆内径(mm)', 'rodInsideDiameter2', null, 44, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137990', null, '7b727b60e9114ebc9e35c4312c8c31c0', '二级杆长度(m)', 'rodLength2', null, 45, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137991', null, '7b727b60e9114ebc9e35c4312c8c31c0', '三级杆级别', 'rodGrade3', null, 46, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137992', null, '7b727b60e9114ebc9e35c4312c8c31c0', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 47, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137993', null, '7b727b60e9114ebc9e35c4312c8c31c0', '三级杆内径(mm)', 'rodInsideDiameter3', null, 48, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137994', null, '7b727b60e9114ebc9e35c4312c8c31c0', '三级杆长度(m)', 'rodLength3', null, 49, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137995', null, '7b727b60e9114ebc9e35c4312c8c31c0', '四级杆级别', 'rodGrade4', null, 50, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137996', null, '7b727b60e9114ebc9e35c4312c8c31c0', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 51, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137997', null, '7b727b60e9114ebc9e35c4312c8c31c0', '四级杆内径(mm)', 'rodInsideDiameter4', null, 52, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137998', null, '7b727b60e9114ebc9e35c4312c8c31c0', '四级杆长度(m)', 'rodLength4', null, 53, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137999', null, '7b727b60e9114ebc9e35c4312c8c31c0', '净毛比(小数)', 'netGrossRatio', null, 54, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138000', null, '7b727b60e9114ebc9e35c4312c8c31c0', '净毛值(m^3/d)', 'netGrossValue', null, 55, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118836', null, '7f13446d19b4497986980fa16a750f95', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118837', null, '7f13446d19b4497986980fa16a750f95', '井名', 'wellName', null, 2, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128173', null, '7f13446d19b4497986980fa16a750f95', '设备类型', 'deviceTypeName', null, 3, 1, null, null, to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118931', null, '7f13446d19b4497986980fa16a750f95', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 4, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118838', null, '7f13446d19b4497986980fa16a750f95', '通信状态', 'commStatusName', null, 5, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134390', null, '7f13446d19b4497986980fa16a750f95', '在线时间', 'commTime', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134391', null, '7f13446d19b4497986980fa16a750f95', '在线时率', 'commTimeEfficiency', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134392', null, '7f13446d19b4497986980fa16a750f95', '在线区间', 'commRange', null, 8, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133835', null, '7f13446d19b4497986980fa16a750f95', '运行状态', 'runStatusName', null, 9, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134393', null, '7f13446d19b4497986980fa16a750f95', '运行时间', 'runTime', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134394', null, '7f13446d19b4497986980fa16a750f95', '运行时率', 'runTimeEfficiency', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134395', null, '7f13446d19b4497986980fa16a750f95', '运行区间', 'runRange', null, 12, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139475', null, '7f13446d19b4497986980fa16a750f95', '工况', 'resultName', null, 13, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139476', null, '7f13446d19b4497986980fa16a750f95', '优化建议', 'optimizationSuggestion', null, 14, 1, null, null, to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139595', null, '7f13446d19b4497986980fa16a750f95', '理论排量(m^3/d)', 'theoreticalProduction', null, 15, 1, null, null, to_date('10-07-2023 13:42:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 13:42:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139477', null, '7f13446d19b4497986980fa16a750f95', '功图产液量(t/d)', 'liquidWeightProduction', null, 16, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139596', null, '7f13446d19b4497986980fa16a750f95', '功图产油量(t/d)', 'oilWeightProduction', null, 17, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139597', null, '7f13446d19b4497986980fa16a750f95', '功图产水量(t/d)', 'waterWeightProduction', null, 18, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139598', null, '7f13446d19b4497986980fa16a750f95', '重量含水率(%)', 'weightWaterCut', null, 19, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139478', null, '7f13446d19b4497986980fa16a750f95', '累计产液量(t)', 'liquidWeightProduction_L', null, 20, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139599', null, '7f13446d19b4497986980fa16a750f95', '柱塞有效冲程计算产量(t/d)', 'availablePlungerStrokeProd_w', null, 21, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139600', null, '7f13446d19b4497986980fa16a750f95', '泵间隙漏失量(t/d)', 'pumpClearanceleakProd_w', null, 22, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139601', null, '7f13446d19b4497986980fa16a750f95', '游动凡尔漏失量(t/d)', 'tvleakWeightProduction', null, 23, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139602', null, '7f13446d19b4497986980fa16a750f95', '固定凡尔漏失量(t/d)', 'svleakWeightProduction', null, 24, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139603', null, '7f13446d19b4497986980fa16a750f95', '气影响(t/d)', 'gasInfluenceProd_w', null, 25, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139479', null, '7f13446d19b4497986980fa16a750f95', '功图产液量(m^3/d)', 'liquidVolumetricProduction', null, 26, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139604', null, '7f13446d19b4497986980fa16a750f95', '功图产油量(m^3/d)', 'oilVolumetricProduction', null, 27, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139605', null, '7f13446d19b4497986980fa16a750f95', '功图产水量(m^3/d)', 'waterVolumetricProduction', null, 28, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139606', null, '7f13446d19b4497986980fa16a750f95', '体积含水率(%)', 'waterCut', null, 29, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139480', null, '7f13446d19b4497986980fa16a750f95', '累计产液量(m^3)', 'liquidVolumetricProduction_L', null, 30, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139607', null, '7f13446d19b4497986980fa16a750f95', '柱塞有效冲程计算产量(m^3/d)', 'availablePlungerStrokeProd_v', null, 31, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139608', null, '7f13446d19b4497986980fa16a750f95', '泵间隙漏失量(m^3/d)', 'pumpClearanceleakProd_v', null, 32, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139609', null, '7f13446d19b4497986980fa16a750f95', '游动凡尔漏失量(m^3/d)', 'tvleakVolumetricProduction', null, 33, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139610', null, '7f13446d19b4497986980fa16a750f95', '固定凡尔漏失量(m^3/d)', 'svleakVolumetricProduction', null, 34, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139611', null, '7f13446d19b4497986980fa16a750f95', '气影响(m^3/d)', 'gasInfluenceProd_v', null, 35, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139481', null, '7f13446d19b4497986980fa16a750f95', '最大载荷(kN)', 'FMax', null, 36, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139482', null, '7f13446d19b4497986980fa16a750f95', '最小载荷(kN)', 'FMin', null, 37, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139612', null, '7f13446d19b4497986980fa16a750f95', '冲程(m)', 'stroke', null, 38, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139613', null, '7f13446d19b4497986980fa16a750f95', '冲次(次/min)', 'SPM', null, 39, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139483', null, '7f13446d19b4497986980fa16a750f95', '充满系数', 'fullnessCoefficient', null, 40, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139502', null, '7f13446d19b4497986980fa16a750f95', '柱塞冲程(m)', 'plungerStroke', null, 41, 1, null, null, to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139503', null, '7f13446d19b4497986980fa16a750f95', '柱塞有效冲程(m)', 'availablePlungerStroke', null, 42, 1, null, null, to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139614', null, '7f13446d19b4497986980fa16a750f95', '理论上载荷(kN)', 'upperLoadLine', null, 43, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139615', null, '7f13446d19b4497986980fa16a750f95', '理论下载荷(kN)', 'lowerLoadLine', null, 44, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139488', null, '7f13446d19b4497986980fa16a750f95', '有功功率(kW)', 'averageWatt', null, 45, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139489', null, '7f13446d19b4497986980fa16a750f95', '光杆功率(kW)', 'polishrodPower', null, 46, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139490', null, '7f13446d19b4497986980fa16a750f95', '水功率(kW)', 'waterPower', null, 47, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139491', null, '7f13446d19b4497986980fa16a750f95', '地面效率(%)', 'surfaceSystemEfficiency', null, 48, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139492', null, '7f13446d19b4497986980fa16a750f95', '井下效率(%)', 'welldownSystemEfficiency', null, 49, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139493', null, '7f13446d19b4497986980fa16a750f95', '系统效率(%)', 'systemEfficiency', null, 50, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139494', null, '7f13446d19b4497986980fa16a750f95', '吨液百米耗电量(kW· h/100m· t)', 'energyper100mlift', null, 51, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139616', null, '7f13446d19b4497986980fa16a750f95', '功图面积', 'area', null, 52, 1, null, null, to_date('10-07-2023 14:25:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:25:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139621', null, '7f13446d19b4497986980fa16a750f95', '抽油杆伸长量(m)', 'rodFlexLength', null, 53, 1, null, null, to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139622', null, '7f13446d19b4497986980fa16a750f95', '油管伸缩量(m)', 'tubingFlexLength', null, 54, 1, null, null, to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139623', null, '7f13446d19b4497986980fa16a750f95', '惯性载荷增量(m)', 'inertiaLength', null, 55, 1, null, null, to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139617', null, '7f13446d19b4497986980fa16a750f95', '冲程损失系数(%)', 'pumpEff1', null, 56, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139618', null, '7f13446d19b4497986980fa16a750f95', '充满系数(%)', 'pumpEff2', null, 57, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139619', null, '7f13446d19b4497986980fa16a750f95', '间隙漏失系数(%)', 'pumpEff3', null, 58, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139620', null, '7f13446d19b4497986980fa16a750f95', '液体收缩系数(%)', 'pumpEff4', null, 59, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139495', null, '7f13446d19b4497986980fa16a750f95', '总泵效(%)', 'pumpEff', null, 60, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139624', null, '7f13446d19b4497986980fa16a750f95', '上冲程最大电流(A)', 'upStrokeIMax', null, 61, 1, null, null, to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139625', null, '7f13446d19b4497986980fa16a750f95', '下冲程最大电流(A)', 'downStrokeIMax', null, 62, 1, null, null, to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139496', null, '7f13446d19b4497986980fa16a750f95', '电流平衡度(%)', 'iDegreeBalance', null, 63, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139626', null, '7f13446d19b4497986980fa16a750f95', '上冲程最大功率(kW)', 'upStrokeWattMax', null, 64, 1, null, null, to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139627', null, '7f13446d19b4497986980fa16a750f95', '下冲程最大功率(kW)', 'downStrokeWattMax', null, 65, 1, null, null, to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139497', null, '7f13446d19b4497986980fa16a750f95', '功率平衡度(%)', 'wattDegreeBalance', null, 66, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139498', null, '7f13446d19b4497986980fa16a750f95', '移动距离(cm)', 'deltaradius', null, 67, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139501', null, '7f13446d19b4497986980fa16a750f95', '日用电量(kW·h)', 'todayKWattH', null, 68, 1, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139628', null, '7f13446d19b4497986980fa16a750f95', '动液面(m)', 'producingfluidLevel', null, 69, 1, null, null, to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139629', null, '7f13446d19b4497986980fa16a750f95', '反演液面校正值(MPa)', 'levelCorrectValue', null, 70, 1, null, null, to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139500', null, '7f13446d19b4497986980fa16a750f95', '反演动液面(m)', 'calcProducingfluidLevel', null, 71, 1, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139499', null, '7f13446d19b4497986980fa16a750f95', '液面校正差值(MPa)', 'levelDifferenceValue', null, 72, 1, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139644', null, '7f13446d19b4497986980fa16a750f95', '泵挂(m)', 'pumpSettingDepth', null, 73, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139645', null, '7f13446d19b4497986980fa16a750f95', '沉没度(m)', 'submergence', null, 74, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139646', null, '7f13446d19b4497986980fa16a750f95', '泵径(mm)', 'pumpBoreDiameter', null, 75, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139640', null, '7f13446d19b4497986980fa16a750f95', '油压(MPa)', 'tubingPressure', null, 76, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139641', null, '7f13446d19b4497986980fa16a750f95', '套压(MPa)', 'casingPressure', null, 77, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139642', null, '7f13446d19b4497986980fa16a750f95', '井口温度(℃)', 'wellHeadTemperature', null, 78, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139643', null, '7f13446d19b4497986980fa16a750f95', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 79, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139630', null, '7f13446d19b4497986980fa16a750f95', '泵入口压力(MPa)', 'pumpIntakeP', null, 80, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139631', null, '7f13446d19b4497986980fa16a750f95', '泵入口温度(℃)', 'pumpIntakeT', null, 81, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139632', null, '7f13446d19b4497986980fa16a750f95', '泵入口就地气液比(m^3/m^3)', 'pumpIntakeGOL', null, 82, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139633', null, '7f13446d19b4497986980fa16a750f95', '泵入口粘度(mPa·s)', 'pumpIntakeVisl', null, 83, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139634', null, '7f13446d19b4497986980fa16a750f95', '泵入口原油体积系数', 'pumpIntakeBo', null, 84, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139635', null, '7f13446d19b4497986980fa16a750f95', '泵出口压力(MPa)', 'pumpOutletP', null, 85, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139636', null, '7f13446d19b4497986980fa16a750f95', '泵出口温度(℃)', 'pumpOutletT', null, 86, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139637', null, '7f13446d19b4497986980fa16a750f95', '泵出口就地气液比(m^3/m^3)', 'pumpOutletGOL', null, 87, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139638', null, '7f13446d19b4497986980fa16a750f95', '泵出口粘度(mPa·s)', 'pumpOutletVisl', null, 88, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139639', null, '7f13446d19b4497986980fa16a750f95', '泵出口原油体积系数', 'pumpOutletBo', null, 89, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137237', null, '7f13446d19b4497986980fa16a750f95', '煤层顶板深(米)', 'c_mcdbs', null, 103, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137238', null, '7f13446d19b4497986980fa16a750f95', '压力计安装深度(米)', 'c_yljazsd', null, 104, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137239', null, '7f13446d19b4497986980fa16a750f95', '井下压力计-压力(MPa)', 'c_jxyljyl', null, 105, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137240', null, '7f13446d19b4497986980fa16a750f95', '动液面(m)', 'c_dym', null, 106, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137241', null, '7f13446d19b4497986980fa16a750f95', '井口套压(MPa)', 'c_jkty', null, 107, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137242', null, '7f13446d19b4497986980fa16a750f95', '系统压力(KPa)', 'c_xtyl', null, 108, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137243', null, '7f13446d19b4497986980fa16a750f95', '产气量累计(m3)', 'c_cqllj', null, 109, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137244', null, '7f13446d19b4497986980fa16a750f95', '产气量瞬时(m3)', 'c_cqlss', null, 110, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137245', null, '7f13446d19b4497986980fa16a750f95', '气体温度(℃)', 'c_qtwd', null, 111, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137246', null, '7f13446d19b4497986980fa16a750f95', '产水量累计(m3)', 'c_csllj', null, 112, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137247', null, '7f13446d19b4497986980fa16a750f95', '产水量瞬时(m3)', 'c_cslss', null, 113, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137248', null, '7f13446d19b4497986980fa16a750f95', '冲次(冲/min)', 'c_cc', null, 114, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137249', null, '7f13446d19b4497986980fa16a750f95', '井下压力计-温度(℃)', 'c_jxyljwd', null, 115, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137250', null, '7f13446d19b4497986980fa16a750f95', '气体流量计通讯状态', 'c_qtlljtxzt', null, 116, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137251', null, '7f13446d19b4497986980fa16a750f95', '水流量计通讯状态', 'c_slljtxzt', null, 117, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137252', null, '7f13446d19b4497986980fa16a750f95', '井下压力计/液面仪通讯状态', 'c_jxyljymytxzt', null, 118, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137253', null, '7f13446d19b4497986980fa16a750f95', '变频器通讯状态', 'c_bpqtxzt', null, 119, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137254', null, '7f13446d19b4497986980fa16a750f95', '频率控制方式', 'c_plkzfs', null, 120, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137255', null, '7f13446d19b4497986980fa16a750f95', '启停控制', 'c_qtkz', null, 121, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137256', null, '7f13446d19b4497986980fa16a750f95', '变频器设置频率(Hz)', 'c_bpqszpl', null, 122, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137257', null, '7f13446d19b4497986980fa16a750f95', '变频器运行频率(Hz)', 'c_bpqyxpl', null, 123, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137258', null, '7f13446d19b4497986980fa16a750f95', '变频器故障状态', 'c_bpqgzzt', null, 124, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137259', null, '7f13446d19b4497986980fa16a750f95', '变频器输出电流(A)', 'c_bpqscdl', null, 125, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137260', null, '7f13446d19b4497986980fa16a750f95', '变频器输出电压(V)', 'c_bpqscdy', null, 126, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137261', null, '7f13446d19b4497986980fa16a750f95', '变频器厂家代码', 'c_bpqcjdm', null, 127, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137262', null, '7f13446d19b4497986980fa16a750f95', '变频器状态字1', 'c_bpqztz1', null, 128, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137263', null, '7f13446d19b4497986980fa16a750f95', '变频器状态字2', 'c_bpqztz2', null, 129, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137264', null, '7f13446d19b4497986980fa16a750f95', '本地旋钮位置', 'c_bdxnwz', null, 130, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137265', null, '7f13446d19b4497986980fa16a750f95', '母线电压(V)', 'c_mxdy', null, 131, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137266', null, '7f13446d19b4497986980fa16a750f95', '设定频率反馈(Hz)', 'c_sdplfk', null, 132, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137267', null, '7f13446d19b4497986980fa16a750f95', '10Hz对应冲次/转速预设值(次/100转)', 'c_10Hzdycczsysz', null, 133, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137268', null, '7f13446d19b4497986980fa16a750f95', '50Hz对应冲次/转速预设值(次/100转)', 'c_50Hzdycczsysz', null, 134, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137269', null, '7f13446d19b4497986980fa16a750f95', '冲次/转速设定值(次/100转)', 'c_cczssdz', null, 135, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137270', null, '7f13446d19b4497986980fa16a750f95', '修正后井底流压(Mpa)', 'c_xzhjdly', null, 136, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137271', null, '7f13446d19b4497986980fa16a750f95', '计算液柱高度(m)', 'c_jsyzgd', null, 137, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137272', null, '7f13446d19b4497986980fa16a750f95', '计算近1小时液柱下降速度(m/小时)', 'c_jsj1xsyzxjsd', null, 138, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137273', null, '7f13446d19b4497986980fa16a750f95', '排采模式', 'c_pcms', null, 139, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137274', null, '7f13446d19b4497986980fa16a750f95', '自动排采状态', 'c_zdpczt', null, 140, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137275', null, '7f13446d19b4497986980fa16a750f95', '自动排采-最小频率(Hz)', 'c_zdpczxpl', null, 141, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137276', null, '7f13446d19b4497986980fa16a750f95', '自动排采-最大频率(Hz)', 'c_zdpczdpl', null, 142, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137277', null, '7f13446d19b4497986980fa16a750f95', '最大步长幅度限制(Hz)', 'c_zdbcfdxz', null, 143, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137278', null, '7f13446d19b4497986980fa16a750f95', '最短调整时间间隔(秒)', 'c_zddzsjjg', null, 144, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137279', null, '7f13446d19b4497986980fa16a750f95', '自动重启延时(秒)', 'c_zdzqys', null, 145, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137280', null, '7f13446d19b4497986980fa16a750f95', '自动重启次数(次)', 'c_zdzqcs', null, 146, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137281', null, '7f13446d19b4497986980fa16a750f95', '井底流压波动报警值(Kpa)', 'c_jdlybdbjz', null, 147, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137282', null, '7f13446d19b4497986980fa16a750f95', '定降液-目标定降（每日）(m)', 'c_djymbdjmr', null, 148, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137283', null, '7f13446d19b4497986980fa16a750f95', '定降液-液柱低停机值(m)', 'c_djyyzdtjz', null, 149, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137284', null, '7f13446d19b4497986980fa16a750f95', '定降液-液柱低报警值(m)', 'c_djyyzdbjz', null, 150, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137285', null, '7f13446d19b4497986980fa16a750f95', '定降液-液柱重启值(m)', 'c_djyyzzqz', null, 151, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137286', null, '7f13446d19b4497986980fa16a750f95', '定降液-液柱高报警值(m)', 'c_djyyzgbjz', null, 152, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137287', null, '7f13446d19b4497986980fa16a750f95', '定降液-P参数', 'c_djyPcs', null, 153, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137288', null, '7f13446d19b4497986980fa16a750f95', '定降液-I参数', 'c_djyIcs', null, 154, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137289', null, '7f13446d19b4497986980fa16a750f95', '定降液-D参数', 'c_djyDcs', null, 155, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137290', null, '7f13446d19b4497986980fa16a750f95', '定流压-目标流压(Kpa)', 'c_dlymbly', null, 156, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137291', null, '7f13446d19b4497986980fa16a750f95', '定流压-流压低停机值(Kpa)', 'c_dlylydtjz', null, 157, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137292', null, '7f13446d19b4497986980fa16a750f95', '定流压-流压低报警值(Kpa)', 'c_dlylydbjz', null, 158, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137293', null, '7f13446d19b4497986980fa16a750f95', '定流压-流压重启值(Kpa)', 'c_dlylyzqz', null, 159, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137294', null, '7f13446d19b4497986980fa16a750f95', '定流压-流压高报警值(Kpa)', 'c_dlylygbjz', null, 160, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137295', null, '7f13446d19b4497986980fa16a750f95', '定流压-P参数', 'c_dlyPcs', null, 161, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137296', null, '7f13446d19b4497986980fa16a750f95', '定流压-I参数', 'c_dlyIcs', null, 162, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137297', null, '7f13446d19b4497986980fa16a750f95', '定流压-D参数', 'c_dlyDcs', null, 163, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138455', null, '7f13446d19b4497986980fa16a750f95', '声光报警控制位', 'c_sgbjkzw', null, 164, 0, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138456', null, '7f13446d19b4497986980fa16a750f95', '油压(MPa)', 'c_yy', null, 165, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138457', null, '7f13446d19b4497986980fa16a750f95', '套压(MPa)', 'c_ty', null, 166, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138458', null, '7f13446d19b4497986980fa16a750f95', '回压(MPa)', 'c_hy', null, 167, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138459', null, '7f13446d19b4497986980fa16a750f95', '井口温度(℃)', 'c_jkwd', null, 168, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138460', null, '7f13446d19b4497986980fa16a750f95', '运行状态', 'c_yxzt', null, 169, 0, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138461', null, '7f13446d19b4497986980fa16a750f95', '含水率(%)', 'c_hsl', null, 170, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138462', null, '7f13446d19b4497986980fa16a750f95', '光杆温度(℃)', 'c_ggwd', null, 171, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138463', null, '7f13446d19b4497986980fa16a750f95', '盘根盒温度(℃)', 'c_pghwd', null, 172, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138464', null, '7f13446d19b4497986980fa16a750f95', 'A相电流(A)', 'c_Axdl', null, 173, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138465', null, '7f13446d19b4497986980fa16a750f95', 'B相电流(A)', 'c_Bxdl', null, 174, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138466', null, '7f13446d19b4497986980fa16a750f95', 'C相电流(V)', 'c_Cxdl', null, 175, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138467', null, '7f13446d19b4497986980fa16a750f95', 'A相电压(V)', 'c_Axdy', null, 176, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138468', null, '7f13446d19b4497986980fa16a750f95', 'B相电压(V)', 'c_Bxdy', null, 177, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138469', null, '7f13446d19b4497986980fa16a750f95', 'C相电压(V)', 'c_Cxdy', null, 178, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138470', null, '7f13446d19b4497986980fa16a750f95', '有功功耗(kW·h)', 'c_yggh', null, 179, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138471', null, '7f13446d19b4497986980fa16a750f95', '无功功耗(kVar·h)', 'c_wggh', null, 180, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138472', null, '7f13446d19b4497986980fa16a750f95', '有功功率(kW)', 'c_yggl', null, 181, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138473', null, '7f13446d19b4497986980fa16a750f95', '无功功率(kVar)', 'c_wggl', null, 182, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138474', null, '7f13446d19b4497986980fa16a750f95', '反向功率(kW)', 'c_fxgl', null, 183, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138475', null, '7f13446d19b4497986980fa16a750f95', '功率因数', 'c_glys', null, 184, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138476', null, '7f13446d19b4497986980fa16a750f95', '变频设置频率(Hz)', 'c_bpszpl', null, 185, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138477', null, '7f13446d19b4497986980fa16a750f95', '变频运行频率(Hz)', 'c_bpyxpl', null, 186, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138478', null, '7f13446d19b4497986980fa16a750f95', '功图采集间隔(min)', 'c_gtcjjg', null, 187, 0, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138479', null, '7f13446d19b4497986980fa16a750f95', '功图设置点数', 'c_gtszds', null, 188, 0, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138480', null, '7f13446d19b4497986980fa16a750f95', '功图实测点数', 'c_gtscds', null, 189, 0, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138481', null, '7f13446d19b4497986980fa16a750f95', '功图采集时间', 'c_gtcjsj', null, 190, 0, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138482', null, '7f13446d19b4497986980fa16a750f95', '冲程(m)', 'c_cc1', null, 191, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138483', null, '7f13446d19b4497986980fa16a750f95', '功图数据-位移(m)', 'c_gtsjwy', null, 192, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138484', null, '7f13446d19b4497986980fa16a750f95', '功图数据-载荷(kN)', 'c_gtsjzh', null, 193, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138485', null, '7f13446d19b4497986980fa16a750f95', '功图数据-电流(A)', 'c_gtsjdl', null, 194, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138486', null, '7f13446d19b4497986980fa16a750f95', '功图数据-功率(kW)', 'c_gtsjgl', null, 195, 1, null, null, to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139314', null, '8122b170c0ca4deb87159c931ab251f3', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139315', null, '8122b170c0ca4deb87159c931ab251f3', '井名', 'wellName', null, 2, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139316', null, '8122b170c0ca4deb87159c931ab251f3', '日期', 'calDate', null, 3, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139317', null, '8122b170c0ca4deb87159c931ab251f3', '产液量(t/d)', 'liquidWeightProduction', null, 4, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139318', null, '8122b170c0ca4deb87159c931ab251f3', '产油量(t/d)', 'oilWeightProduction', null, 5, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139319', null, '8122b170c0ca4deb87159c931ab251f3', '产液量(m^3/d)', 'liquidVolumetricProduction', null, 6, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139320', null, '8122b170c0ca4deb87159c931ab251f3', '产油量(m^3/d)', 'oilVolumetricProduction', null, 7, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139321', null, '8122b170c0ca4deb87159c931ab251f3', '泵效(%)', 'pumpEff', null, 8, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139322', null, '8122b170c0ca4deb87159c931ab251f3', '系统效率(%)', 'systemEfficiency', null, 9, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139323', null, '8122b170c0ca4deb87159c931ab251f3', '日用电量(kw·h)', 'todayKWattH', 'width:120', 10, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114888', null, '87808f225d7240f68c2ab879347d818a', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114867', 'sys', '87808f225d7240f68c2ab879347d818a', '井名', 'wellName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('122495', null, '87808f225d7240f68c2ab879347d818a', '应用场景', 'applicationScenariosName', null, 3, 1, null, null, to_date('15-11-2021 17:39:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-11-2021 17:39:28', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('114832', null, '8ab792e089494533be910699d426c7d5', '单位名称', 'text', 'flex:3', 1, 1, null, '系统管理员', to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114834', null, '8ab792e089494533be910699d426c7d5', '排序编号', 'orgSeq', 'flex:1', 2, 1, null, '系统管理员', to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139268', null, 'aad8b76fdaf84a1194de5ec0a4453631', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139269', null, 'aad8b76fdaf84a1194de5ec0a4453631', '井名', 'wellName', null, 2, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139270', null, 'aad8b76fdaf84a1194de5ec0a4453631', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:150', 3, 1, null, null, to_date('29-11-2018 18:48:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-11-2018 18:48:00', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139271', null, 'aad8b76fdaf84a1194de5ec0a4453631', '计算状态', 'resultStatus', null, 4, 1, null, '系统管理员', to_date('30-11-2018 10:21:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:36', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139272', null, 'aad8b76fdaf84a1194de5ec0a4453631', '产液量(t/d)', 'liquidWeightProduction', null, 5, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139273', null, 'aad8b76fdaf84a1194de5ec0a4453631', '产液量(m^3/d)', 'liquidVolumetricProduction', null, 5, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139274', null, 'aad8b76fdaf84a1194de5ec0a4453631', '产油量(m^3/d)', 'oilVolumetricProduction', null, 6, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139275', null, 'aad8b76fdaf84a1194de5ec0a4453631', '产油量(t/d)', 'oilWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139276', null, 'aad8b76fdaf84a1194de5ec0a4453631', '转速(r/min)', 'rpm', null, 7, 1, null, null, to_date('30-11-2018 17:32:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 17:32:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139277', null, 'aad8b76fdaf84a1194de5ec0a4453631', '原油密度(g/cm^3)', 'crudeoilDensity', null, 8, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139278', null, 'aad8b76fdaf84a1194de5ec0a4453631', '水密度(g/cm^3)', 'waterDensity', null, 9, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139279', null, 'aad8b76fdaf84a1194de5ec0a4453631', '天然气相对密度', 'naturalGasRelativeDensity', null, 10, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139280', null, 'aad8b76fdaf84a1194de5ec0a4453631', '饱和压力(MPa)', 'saturationPressure', null, 11, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139281', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油层中部深度(m)', 'reservoirDepth', null, 12, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139282', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油层中部温度(摄氏度)', 'reservoirTemperature', null, 13, 1, null, null, to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139283', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油压(MPa)', 'tubingPressure', null, 14, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139284', null, 'aad8b76fdaf84a1194de5ec0a4453631', '套压(MPa)', 'casingPressure', null, 15, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139285', null, 'aad8b76fdaf84a1194de5ec0a4453631', '井口油温(℃)', 'wellHeadFluidTemperature', null, 16, 1, null, '系统管理员', to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139286', null, 'aad8b76fdaf84a1194de5ec0a4453631', '含水率(%)', 'weightWaterCut', null, 17, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139287', null, 'aad8b76fdaf84a1194de5ec0a4453631', '生产气油比(m^3/t)', 'productionGasOilRatio', 'width:120', 18, 1, null, '系统管理员', to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139288', null, 'aad8b76fdaf84a1194de5ec0a4453631', '动液面(m)', 'producingFluidLevel', null, 19, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139289', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵挂(m)', 'pumpSettingDepth', null, 20, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139290', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵筒长(m)', 'barrelLength', null, 21, 1, null, '系统管理员', to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139291', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵级数', 'barrelSeries', null, 22, 1, null, '系统管理员', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139292', null, 'aad8b76fdaf84a1194de5ec0a4453631', '转子直径(mm)', 'rotorDiameter', null, 23, 1, null, '系统管理员', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139293', null, 'aad8b76fdaf84a1194de5ec0a4453631', '公称排量(ml/转)', 'qpr', null, 24, 1, null, null, to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139294', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油管内径(mm)', 'tubingStringInsideDiameter', null, 25, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139295', null, 'aad8b76fdaf84a1194de5ec0a4453631', '套管内径(mm)', 'casingStringInsideDiameter', 'width:120', 26, 1, null, '系统管理员', to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139296', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆级别', 'rodGrade1', null, 27, 1, null, '系统管理员', to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139297', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 28, 1, null, '系统管理员', to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139298', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆内径(mm)', 'rodInsideDiameter1', null, 29, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139299', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一级杆长度(m)', 'rodLength1', null, 30, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139300', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆级别', 'rodGrade2', null, 31, 1, null, '系统管理员', to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139301', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 32, 1, null, '系统管理员', to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139302', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆内径(mm)', 'rodInsideDiameter2', null, 33, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139303', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二级杆长度(m)', 'rodGrade2', null, 34, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139304', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆级别', 'rodGrade3', null, 35, 1, null, '系统管理员', to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139305', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 36, 1, null, '系统管理员', to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139306', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆内径(mm)', 'rodInsideDiameter3', null, 37, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139307', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三级杆长度(m)', 'rodGrade3', null, 38, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139308', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆级别', 'rodGrade4', null, 39, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139309', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 40, 1, null, '系统管理员', to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139310', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆内径(mm)', 'rodInsideDiameter4', null, 41, 1, null, null, to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139311', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四级杆长度(m)', 'rodGrade4', null, 42, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139312', null, 'aad8b76fdaf84a1194de5ec0a4453631', '净毛比', 'netGrossRatio', null, 44, 1, null, '系统管理员', to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139313', null, 'aad8b76fdaf84a1194de5ec0a4453631', '净毛值(m^3/d)', 'netGrossValue', null, 45, 1, null, '??????????????í??±', to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134006', null, 'ac7916c134074f39a27325eb7e076049', '原油密度(g/cm^3)', 'crudeOilDensity', null, 1, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134007', null, 'ac7916c134074f39a27325eb7e076049', '水密度(g/cm^3)', 'waterDensity', null, 2, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134008', null, 'ac7916c134074f39a27325eb7e076049', '天然气相对密度', 'naturalGasRelativeDensity', null, 3, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134009', null, 'ac7916c134074f39a27325eb7e076049', '饱和压力(MPa)', 'saturationPressure', null, 4, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134010', null, 'ac7916c134074f39a27325eb7e076049', '油层中部深度(m)', 'reservoirDepth', null, 5, 0, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134011', null, 'ac7916c134074f39a27325eb7e076049', '油层中部温度(℃)', 'reservoirTemperature', null, 6, 0, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134012', null, 'ac7916c134074f39a27325eb7e076049', '油压(MPa)', 'tubingPressure', null, 7, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134013', null, 'ac7916c134074f39a27325eb7e076049', '套压(MPa)', 'casingPressure', null, 8, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134014', null, 'ac7916c134074f39a27325eb7e076049', '井口温度(℃)', 'wellHeadTemperature', null, 9, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134015', null, 'ac7916c134074f39a27325eb7e076049', '含水率(%)', 'waterCut', null, 10, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134016', null, 'ac7916c134074f39a27325eb7e076049', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 11, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134017', null, 'ac7916c134074f39a27325eb7e076049', '动液面(m)', 'producingfluidLevel', null, 12, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134018', null, 'ac7916c134074f39a27325eb7e076049', '泵挂(m)', 'pumpSettingDepth', null, 13, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134019', null, 'ac7916c134074f39a27325eb7e076049', '泵筒长(m)', 'barrelLength', null, 14, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134020', null, 'ac7916c134074f39a27325eb7e076049', '泵级数', 'barrelSeries', null, 15, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134021', null, 'ac7916c134074f39a27325eb7e076049', '转子直径(mm)', 'rotorDiameter', null, 16, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134022', null, 'ac7916c134074f39a27325eb7e076049', '公称排量(ml/转)', 'QPR', null, 17, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134023', null, 'ac7916c134074f39a27325eb7e076049', '油管内径(mm)', 'tubingStringInsideDiameter', null, 18, 1, null, null, to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134024', null, 'ac7916c134074f39a27325eb7e076049', '套管内径(mm)', 'casingStringInsideDiameter', null, 19, 1, null, null, to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134025', null, 'ac7916c134074f39a27325eb7e076049', '抽油杆参数', 'rodString', null, 20, 1, null, null, to_date('24-05-2022 09:20:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:20:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119371', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119372', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '井名', 'wellName', 'flex:3', 2, 1, null, '超级管理员', to_date('05-01-2022 10:41:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:43', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('120115', null, 'b09082f4272e4768994db398e14bc3f2', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120116', null, 'b09082f4272e4768994db398e14bc3f2', '井名', 'wellName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:18:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:53', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('118801', null, 'b14377621d74442eb1127de094dfc903', '序号', 'id', 'width:50', 1, 1, null, null, to_date('19-08-2021 14:25:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-08-2021 14:25:11', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118803', 'sys', 'b14377621d74442eb1127de094dfc903', '井名', 'wellName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('122496', null, 'b14377621d74442eb1127de094dfc903', '应用场景', 'applicationScenariosName', null, 3, 1, null, null, to_date('15-11-2021 17:54:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-11-2021 17:54:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118804', null, 'b14377621d74442eb1127de094dfc903', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133099', null, 'b14377621d74442eb1127de094dfc903', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136596', null, 'b14377621d74442eb1127de094dfc903', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119812', null, 'b14377621d74442eb1127de094dfc903', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135316', null, 'b14377621d74442eb1127de094dfc903', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118806', null, 'b14377621d74442eb1127de094dfc903', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136176', null, 'b14377621d74442eb1127de094dfc903', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('10-01-2023 09:09:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2023 09:09:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118807', null, 'b14377621d74442eb1127de094dfc903', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135536', null, 'b14377621d74442eb1127de094dfc903', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:15:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:15:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132519', null, 'b14377621d74442eb1127de094dfc903', '状态', 'statusName', null, 13, 1, null, null, to_date('09-02-2022 10:22:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:22:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135216', null, 'b14377621d74442eb1127de094dfc903', '隶属单位', 'allPath', null, 14, 1, null, null, to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118816', null, 'b14377621d74442eb1127de094dfc903', '排序编号', 'sortNum', null, 15, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114837', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块名称', 'text', null, 1, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114838', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块简介', 'mdShowname', null, 2, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114839', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块编码', 'mdCode', null, 3, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114840', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块视图', 'mdUrl', null, 4, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114841', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块控制器', 'mdControl', null, 5, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114842', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块图标', 'mdIcon', null, 6, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114843', null, 'b6ef8f3a49094768b3231d5678fc9cbc', '模块类别', 'mdTypeName', null, 7, 1, null, '系统管理员', to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114844', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块排序', 'mdSeq', null, 8, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119870', null, 'b71c1a2c9d574fe482080a56c7c780a9', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119871', null, 'b71c1a2c9d574fe482080a56c7c780a9', '井名', 'wellName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:19:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:26', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('114884', null, 'b8a408839dd8498d9a19fc65f7406ed4', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114827', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典模块名称', 'cname', null, 2, 1, null, '系统管理员', to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114828', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典模块代码', 'ename', null, 3, 1, null, '系统管理员', to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114829', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '字典顺序', 'sorts', null, 4, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114830', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '创建人', 'creator', null, 5, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114831', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '创建时间', 'updatetime', null, 6, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119190', null, 'cd7b24562b924d19b556de31256e22a1', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119191', null, 'cd7b24562b924d19b556de31256e22a1', '井名', 'wellName', null, 2, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119193', null, 'cd7b24562b924d19b556de31256e22a1', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 3, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119192', null, 'cd7b24562b924d19b556de31256e22a1', '通信状态', 'commStatusName', null, 4, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134402', null, 'cd7b24562b924d19b556de31256e22a1', '在线时间', 'commTime', null, 5, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134403', null, 'cd7b24562b924d19b556de31256e22a1', '在线时率', 'commTimeEfficiency', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134404', null, 'cd7b24562b924d19b556de31256e22a1', '在线区间', 'commRange', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133873', null, 'cd7b24562b924d19b556de31256e22a1', '运行状态', 'runStatusName', null, 8, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134405', null, 'cd7b24562b924d19b556de31256e22a1', '运行时间', 'runTime', null, 9, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134406', null, 'cd7b24562b924d19b556de31256e22a1', '运行时率', 'runTimeEfficiency', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134407', null, 'cd7b24562b924d19b556de31256e22a1', '运行区间', 'runRange', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139504', null, 'cd7b24562b924d19b556de31256e22a1', '工况', 'resultName', null, 12, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139505', null, 'cd7b24562b924d19b556de31256e22a1', '优化建议', 'optimizationSuggestion', null, 13, 1, null, null, to_date('29-08-2022 13:20:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-08-2022 13:20:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139647', null, 'cd7b24562b924d19b556de31256e22a1', '理论排量(m^3/d)', 'theoreticalProduction', null, 14, 1, null, null, to_date('10-07-2023 13:42:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 13:42:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139648', null, 'cd7b24562b924d19b556de31256e22a1', '功图产液量(t/d)', 'liquidWeightProduction', null, 15, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139649', null, 'cd7b24562b924d19b556de31256e22a1', '功图产油量(t/d)', 'oilWeightProduction', null, 16, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139650', null, 'cd7b24562b924d19b556de31256e22a1', '功图产水量(t/d)', 'waterWeightProduction', null, 17, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139651', null, 'cd7b24562b924d19b556de31256e22a1', '重量含水率(%)', 'weightWaterCut', null, 18, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139652', null, 'cd7b24562b924d19b556de31256e22a1', '累计产液量(t)', 'liquidWeightProduction_L', null, 19, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139653', null, 'cd7b24562b924d19b556de31256e22a1', '柱塞有效冲程计算产量(t/d)', 'availablePlungerStrokeProd_w', null, 20, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139654', null, 'cd7b24562b924d19b556de31256e22a1', '泵间隙漏失量(t/d)', 'pumpClearanceleakProd_w', null, 21, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139655', null, 'cd7b24562b924d19b556de31256e22a1', '游动凡尔漏失量(t/d)', 'tvleakWeightProduction', null, 22, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139656', null, 'cd7b24562b924d19b556de31256e22a1', '固定凡尔漏失量(t/d)', 'svleakWeightProduction', null, 23, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139657', null, 'cd7b24562b924d19b556de31256e22a1', '气影响(t/d)', 'gasInfluenceProd_w', null, 24, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139658', null, 'cd7b24562b924d19b556de31256e22a1', '功图产液量(m^3/d)', 'liquidVolumetricProduction', null, 25, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139659', null, 'cd7b24562b924d19b556de31256e22a1', '功图产油量(m^3/d)', 'oilVolumetricProduction', null, 26, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139660', null, 'cd7b24562b924d19b556de31256e22a1', '功图产水量(m^3/d)', 'waterVolumetricProduction', null, 27, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139661', null, 'cd7b24562b924d19b556de31256e22a1', '体积含水率(%)', 'waterCut', null, 28, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139662', null, 'cd7b24562b924d19b556de31256e22a1', '累计产液量(m^3)', 'liquidVolumetricProduction_L', null, 29, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139663', null, 'cd7b24562b924d19b556de31256e22a1', '柱塞有效冲程计算产量(m^3/d)', 'availablePlungerStrokeProd_v', null, 30, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139664', null, 'cd7b24562b924d19b556de31256e22a1', '泵间隙漏失量(m^3/d)', 'pumpClearanceleakProd_v', null, 31, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139665', null, 'cd7b24562b924d19b556de31256e22a1', '游动凡尔漏失量(m^3/d)', 'tvleakVolumetricProduction', null, 32, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139666', null, 'cd7b24562b924d19b556de31256e22a1', '固定凡尔漏失量(m^3/d)', 'svleakVolumetricProduction', null, 33, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139667', null, 'cd7b24562b924d19b556de31256e22a1', '气影响(m^3/d)', 'gasInfluenceProd_v', null, 34, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139668', null, 'cd7b24562b924d19b556de31256e22a1', '最大载荷(kN)', 'FMax', null, 35, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139669', null, 'cd7b24562b924d19b556de31256e22a1', '最小载荷(kN)', 'FMin', null, 36, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139670', null, 'cd7b24562b924d19b556de31256e22a1', '冲程(m)', 'stroke', null, 37, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139671', null, 'cd7b24562b924d19b556de31256e22a1', '冲次(次/min)', 'SPM', null, 38, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139672', null, 'cd7b24562b924d19b556de31256e22a1', '充满系数', 'fullnessCoefficient', null, 39, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139673', null, 'cd7b24562b924d19b556de31256e22a1', '柱塞冲程(m)', 'plungerStroke', null, 40, 1, null, null, to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139674', null, 'cd7b24562b924d19b556de31256e22a1', '柱塞有效冲程(m)', 'availablePlungerStroke', null, 41, 1, null, null, to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 09:55:25', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139675', null, 'cd7b24562b924d19b556de31256e22a1', '理论上载荷(kN)', 'upperLoadLine', null, 42, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139676', null, 'cd7b24562b924d19b556de31256e22a1', '理论下载荷(kN)', 'lowerLoadLine', null, 43, 1, null, null, to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139677', null, 'cd7b24562b924d19b556de31256e22a1', '有功功率(kW)', 'averageWatt', null, 44, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139678', null, 'cd7b24562b924d19b556de31256e22a1', '光杆功率(kW)', 'polishrodPower', null, 45, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139679', null, 'cd7b24562b924d19b556de31256e22a1', '水功率(kW)', 'waterPower', null, 46, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139680', null, 'cd7b24562b924d19b556de31256e22a1', '地面效率(%)', 'surfaceSystemEfficiency', null, 47, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139681', null, 'cd7b24562b924d19b556de31256e22a1', '井下效率(%)', 'welldownSystemEfficiency', null, 48, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139682', null, 'cd7b24562b924d19b556de31256e22a1', '系统效率(%)', 'systemEfficiency', null, 49, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139683', null, 'cd7b24562b924d19b556de31256e22a1', '吨液百米耗电量(kW· h/100m· t)', 'energyper100mlift', null, 50, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139684', null, 'cd7b24562b924d19b556de31256e22a1', '功图面积', 'area', null, 51, 1, null, null, to_date('10-07-2023 14:25:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:25:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139685', null, 'cd7b24562b924d19b556de31256e22a1', '抽油杆伸长量(m)', 'rodFlexLength', null, 52, 1, null, null, to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139686', null, 'cd7b24562b924d19b556de31256e22a1', '油管伸缩量(m)', 'tubingFlexLength', null, 53, 1, null, null, to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139687', null, 'cd7b24562b924d19b556de31256e22a1', '惯性载荷增量(m)', 'inertiaLength', null, 54, 1, null, null, to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:28:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139688', null, 'cd7b24562b924d19b556de31256e22a1', '冲程损失系数(%)', 'pumpEff1', null, 55, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139689', null, 'cd7b24562b924d19b556de31256e22a1', '充满系数(%)', 'pumpEff2', null, 56, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139690', null, 'cd7b24562b924d19b556de31256e22a1', '间隙漏失系数(%)', 'pumpEff3', null, 57, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139691', null, 'cd7b24562b924d19b556de31256e22a1', '液体收缩系数(%)', 'pumpEff4', null, 58, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139692', null, 'cd7b24562b924d19b556de31256e22a1', '总泵效(%)', 'pumpEff', null, 59, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139693', null, 'cd7b24562b924d19b556de31256e22a1', '上冲程最大电流(A)', 'upStrokeIMax', null, 60, 1, null, null, to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139694', null, 'cd7b24562b924d19b556de31256e22a1', '下冲程最大电流(A)', 'downStrokeIMax', null, 61, 1, null, null, to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:30:12', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139695', null, 'cd7b24562b924d19b556de31256e22a1', '电流平衡度(%)', 'iDegreeBalance', null, 62, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139696', null, 'cd7b24562b924d19b556de31256e22a1', '上冲程最大功率(kW)', 'upStrokeWattMax', null, 63, 1, null, null, to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139697', null, 'cd7b24562b924d19b556de31256e22a1', '下冲程最大功率(kW)', 'downStrokeWattMax', null, 64, 1, null, null, to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:31:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139698', null, 'cd7b24562b924d19b556de31256e22a1', '功率平衡度(%)', 'wattDegreeBalance', null, 65, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139699', null, 'cd7b24562b924d19b556de31256e22a1', '移动距离(cm)', 'deltaradius', null, 66, 1, null, null, to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139700', null, 'cd7b24562b924d19b556de31256e22a1', '日用电量(kW·h)', 'todayKWattH', null, 67, 1, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139701', null, 'cd7b24562b924d19b556de31256e22a1', '动液面(m)', 'producingfluidLevel', null, 68, 1, null, null, to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139702', null, 'cd7b24562b924d19b556de31256e22a1', '反演液面校正值(MPa)', 'levelCorrectValue', null, 69, 1, null, null, to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:33:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139703', null, 'cd7b24562b924d19b556de31256e22a1', '反演动液面(m)', 'calcProducingfluidLevel', null, 70, 1, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139704', null, 'cd7b24562b924d19b556de31256e22a1', '液面校正差值(MPa)', 'levelDifferenceValue', null, 71, 1, null, null, to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139705', null, 'cd7b24562b924d19b556de31256e22a1', '泵挂(m)', 'pumpSettingDepth', null, 72, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139706', null, 'cd7b24562b924d19b556de31256e22a1', '沉没度(m)', 'submergence', null, 73, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139707', null, 'cd7b24562b924d19b556de31256e22a1', '泵径(mm)', 'pumpBoreDiameter', null, 74, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139708', null, 'cd7b24562b924d19b556de31256e22a1', '油压(MPa)', 'tubingPressure', null, 75, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139709', null, 'cd7b24562b924d19b556de31256e22a1', '套压(MPa)', 'casingPressure', null, 76, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139710', null, 'cd7b24562b924d19b556de31256e22a1', '井口温度(℃)', 'wellHeadTemperature', null, 77, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139711', null, 'cd7b24562b924d19b556de31256e22a1', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 78, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139712', null, 'cd7b24562b924d19b556de31256e22a1', '泵入口压力(MPa)', 'pumpIntakeP', null, 79, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139713', null, 'cd7b24562b924d19b556de31256e22a1', '泵入口温度(℃)', 'pumpIntakeT', null, 80, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139714', null, 'cd7b24562b924d19b556de31256e22a1', '泵入口就地气液比(m^3/m^3)', 'pumpIntakeGOL', null, 81, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139715', null, 'cd7b24562b924d19b556de31256e22a1', '泵入口粘度(mPa·s)', 'pumpIntakeVisl', null, 82, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139716', null, 'cd7b24562b924d19b556de31256e22a1', '泵入口原油体积系数', 'pumpIntakeBo', null, 83, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139717', null, 'cd7b24562b924d19b556de31256e22a1', '泵出口压力(MPa)', 'pumpOutletP', null, 84, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139718', null, 'cd7b24562b924d19b556de31256e22a1', '泵出口温度(℃)', 'pumpOutletT', null, 85, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139719', null, 'cd7b24562b924d19b556de31256e22a1', '泵出口就地气液比(m^3/m^3)', 'pumpOutletGOL', null, 86, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139720', null, 'cd7b24562b924d19b556de31256e22a1', '泵出口粘度(mPa·s)', 'pumpOutletVisl', null, 87, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139721', null, 'cd7b24562b924d19b556de31256e22a1', '泵出口原油体积系数', 'pumpOutletBo', null, 88, 1, null, null, to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 14:43:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137298', null, 'cd7b24562b924d19b556de31256e22a1', '煤层顶板深(米)', 'c_mcdbs', null, 103, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137299', null, 'cd7b24562b924d19b556de31256e22a1', '压力计安装深度(米)', 'c_yljazsd', null, 104, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137300', null, 'cd7b24562b924d19b556de31256e22a1', '井下压力计-压力(MPa)', 'c_jxyljyl', null, 105, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137301', null, 'cd7b24562b924d19b556de31256e22a1', '动液面(m)', 'c_dym', null, 106, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137302', null, 'cd7b24562b924d19b556de31256e22a1', '井口套压(MPa)', 'c_jkty', null, 107, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137303', null, 'cd7b24562b924d19b556de31256e22a1', '系统压力(KPa)', 'c_xtyl', null, 108, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137304', null, 'cd7b24562b924d19b556de31256e22a1', '产气量累计(m3)', 'c_cqllj', null, 109, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137305', null, 'cd7b24562b924d19b556de31256e22a1', '产气量瞬时(m3)', 'c_cqlss', null, 110, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137306', null, 'cd7b24562b924d19b556de31256e22a1', '气体温度(℃)', 'c_qtwd', null, 111, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137307', null, 'cd7b24562b924d19b556de31256e22a1', '产水量累计(m3)', 'c_csllj', null, 112, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137308', null, 'cd7b24562b924d19b556de31256e22a1', '产水量瞬时(m3)', 'c_cslss', null, 113, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137309', null, 'cd7b24562b924d19b556de31256e22a1', '冲次(冲/min)', 'c_cc', null, 114, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137310', null, 'cd7b24562b924d19b556de31256e22a1', '井下压力计-温度(℃)', 'c_jxyljwd', null, 115, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137311', null, 'cd7b24562b924d19b556de31256e22a1', '气体流量计通讯状态', 'c_qtlljtxzt', null, 116, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137312', null, 'cd7b24562b924d19b556de31256e22a1', '水流量计通讯状态', 'c_slljtxzt', null, 117, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137313', null, 'cd7b24562b924d19b556de31256e22a1', '井下压力计/液面仪通讯状态', 'c_jxyljymytxzt', null, 118, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137314', null, 'cd7b24562b924d19b556de31256e22a1', '变频器通讯状态', 'c_bpqtxzt', null, 119, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137315', null, 'cd7b24562b924d19b556de31256e22a1', '频率控制方式', 'c_plkzfs', null, 120, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137316', null, 'cd7b24562b924d19b556de31256e22a1', '启停控制', 'c_qtkz', null, 121, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137317', null, 'cd7b24562b924d19b556de31256e22a1', '变频器设置频率(Hz)', 'c_bpqszpl', null, 122, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137318', null, 'cd7b24562b924d19b556de31256e22a1', '变频器运行频率(Hz)', 'c_bpqyxpl', null, 123, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137319', null, 'cd7b24562b924d19b556de31256e22a1', '变频器故障状态', 'c_bpqgzzt', null, 124, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137320', null, 'cd7b24562b924d19b556de31256e22a1', '变频器输出电流(A)', 'c_bpqscdl', null, 125, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137321', null, 'cd7b24562b924d19b556de31256e22a1', '变频器输出电压(V)', 'c_bpqscdy', null, 126, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137322', null, 'cd7b24562b924d19b556de31256e22a1', '变频器厂家代码', 'c_bpqcjdm', null, 127, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137323', null, 'cd7b24562b924d19b556de31256e22a1', '变频器状态字1', 'c_bpqztz1', null, 128, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137324', null, 'cd7b24562b924d19b556de31256e22a1', '变频器状态字2', 'c_bpqztz2', null, 129, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137325', null, 'cd7b24562b924d19b556de31256e22a1', '本地旋钮位置', 'c_bdxnwz', null, 130, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137326', null, 'cd7b24562b924d19b556de31256e22a1', '母线电压(V)', 'c_mxdy', null, 131, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137327', null, 'cd7b24562b924d19b556de31256e22a1', '设定频率反馈(Hz)', 'c_sdplfk', null, 132, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137328', null, 'cd7b24562b924d19b556de31256e22a1', '10Hz对应冲次/转速预设值(次/100转)', 'c_10Hzdycczsysz', null, 133, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137329', null, 'cd7b24562b924d19b556de31256e22a1', '50Hz对应冲次/转速预设值(次/100转)', 'c_50Hzdycczsysz', null, 134, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137330', null, 'cd7b24562b924d19b556de31256e22a1', '冲次/转速设定值(次/100转)', 'c_cczssdz', null, 135, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137331', null, 'cd7b24562b924d19b556de31256e22a1', '修正后井底流压(Mpa)', 'c_xzhjdly', null, 136, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137332', null, 'cd7b24562b924d19b556de31256e22a1', '计算液柱高度(m)', 'c_jsyzgd', null, 137, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137333', null, 'cd7b24562b924d19b556de31256e22a1', '计算近1小时液柱下降速度(m/小时)', 'c_jsj1xsyzxjsd', null, 138, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137334', null, 'cd7b24562b924d19b556de31256e22a1', '排采模式', 'c_pcms', null, 139, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137335', null, 'cd7b24562b924d19b556de31256e22a1', '自动排采状态', 'c_zdpczt', null, 140, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137336', null, 'cd7b24562b924d19b556de31256e22a1', '自动排采-最小频率(Hz)', 'c_zdpczxpl', null, 141, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137337', null, 'cd7b24562b924d19b556de31256e22a1', '自动排采-最大频率(Hz)', 'c_zdpczdpl', null, 142, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137338', null, 'cd7b24562b924d19b556de31256e22a1', '最大步长幅度限制(Hz)', 'c_zdbcfdxz', null, 143, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137339', null, 'cd7b24562b924d19b556de31256e22a1', '最短调整时间间隔(秒)', 'c_zddzsjjg', null, 144, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137340', null, 'cd7b24562b924d19b556de31256e22a1', '自动重启延时(秒)', 'c_zdzqys', null, 145, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137341', null, 'cd7b24562b924d19b556de31256e22a1', '自动重启次数(次)', 'c_zdzqcs', null, 146, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137342', null, 'cd7b24562b924d19b556de31256e22a1', '井底流压波动报警值(Kpa)', 'c_jdlybdbjz', null, 147, 0, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137343', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-目标定降（每日）(m)', 'c_djymbdjmr', null, 148, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137344', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-液柱低停机值(m)', 'c_djyyzdtjz', null, 149, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137345', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-液柱低报警值(m)', 'c_djyyzdbjz', null, 150, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137346', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-液柱重启值(m)', 'c_djyyzzqz', null, 151, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137347', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-液柱高报警值(m)', 'c_djyyzgbjz', null, 152, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137348', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-P参数', 'c_djyPcs', null, 153, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137349', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-I参数', 'c_djyIcs', null, 154, 1, null, null, to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137350', null, 'cd7b24562b924d19b556de31256e22a1', '定降液-D参数', 'c_djyDcs', null, 155, 1, null, null, to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137351', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-目标流压(Kpa)', 'c_dlymbly', null, 156, 1, null, null, to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137352', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-流压低停机值(Kpa)', 'c_dlylydtjz', null, 157, 1, null, null, to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137353', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-流压低报警值(Kpa)', 'c_dlylydbjz', null, 158, 1, null, null, to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137354', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-流压重启值(Kpa)', 'c_dlylyzqz', null, 159, 1, null, null, to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137355', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-流压高报警值(Kpa)', 'c_dlylygbjz', null, 160, 1, null, null, to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137356', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-P参数', 'c_dlyPcs', null, 161, 1, null, null, to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137357', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-I参数', 'c_dlyIcs', null, 162, 1, null, null, to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137358', null, 'cd7b24562b924d19b556de31256e22a1', '定流压-D参数', 'c_dlyDcs', null, 163, 1, null, null, to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 10:46:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138487', null, 'cd7b24562b924d19b556de31256e22a1', '声光报警控制位', 'c_sgbjkzw', null, 164, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138488', null, 'cd7b24562b924d19b556de31256e22a1', '油压(MPa)', 'c_yy', null, 165, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138489', null, 'cd7b24562b924d19b556de31256e22a1', '套压(MPa)', 'c_ty', null, 166, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138490', null, 'cd7b24562b924d19b556de31256e22a1', '回压(MPa)', 'c_hy', null, 167, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138491', null, 'cd7b24562b924d19b556de31256e22a1', '井口温度(℃)', 'c_jkwd', null, 168, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138492', null, 'cd7b24562b924d19b556de31256e22a1', '运行状态', 'c_yxzt', null, 169, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138493', null, 'cd7b24562b924d19b556de31256e22a1', '含水率(%)', 'c_hsl', null, 170, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138494', null, 'cd7b24562b924d19b556de31256e22a1', '光杆温度(℃)', 'c_ggwd', null, 171, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138495', null, 'cd7b24562b924d19b556de31256e22a1', '盘根盒温度(℃)', 'c_pghwd', null, 172, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138496', null, 'cd7b24562b924d19b556de31256e22a1', 'A相电流(A)', 'c_Axdl', null, 173, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138497', null, 'cd7b24562b924d19b556de31256e22a1', 'B相电流(A)', 'c_Bxdl', null, 174, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138498', null, 'cd7b24562b924d19b556de31256e22a1', 'C相电流(V)', 'c_Cxdl', null, 175, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138499', null, 'cd7b24562b924d19b556de31256e22a1', 'A相电压(V)', 'c_Axdy', null, 176, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138500', null, 'cd7b24562b924d19b556de31256e22a1', 'B相电压(V)', 'c_Bxdy', null, 177, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138501', null, 'cd7b24562b924d19b556de31256e22a1', 'C相电压(V)', 'c_Cxdy', null, 178, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138502', null, 'cd7b24562b924d19b556de31256e22a1', '有功功耗(kW·h)', 'c_yggh', null, 179, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138503', null, 'cd7b24562b924d19b556de31256e22a1', '无功功耗(kVar·h)', 'c_wggh', null, 180, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138504', null, 'cd7b24562b924d19b556de31256e22a1', '有功功率(kW)', 'c_yggl', null, 181, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138505', null, 'cd7b24562b924d19b556de31256e22a1', '无功功率(kVar)', 'c_wggl', null, 182, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138506', null, 'cd7b24562b924d19b556de31256e22a1', '反向功率(kW)', 'c_fxgl', null, 183, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138507', null, 'cd7b24562b924d19b556de31256e22a1', '功率因数', 'c_glys', null, 184, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138508', null, 'cd7b24562b924d19b556de31256e22a1', '变频设置频率(Hz)', 'c_bpszpl', null, 185, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138509', null, 'cd7b24562b924d19b556de31256e22a1', '变频运行频率(Hz)', 'c_bpyxpl', null, 186, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138510', null, 'cd7b24562b924d19b556de31256e22a1', '功图采集间隔(min)', 'c_gtcjjg', null, 187, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138511', null, 'cd7b24562b924d19b556de31256e22a1', '功图设置点数', 'c_gtszds', null, 188, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138512', null, 'cd7b24562b924d19b556de31256e22a1', '功图实测点数', 'c_gtscds', null, 189, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138513', null, 'cd7b24562b924d19b556de31256e22a1', '功图采集时间', 'c_gtcjsj', null, 190, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138514', null, 'cd7b24562b924d19b556de31256e22a1', '冲程(m)', 'c_cc1', null, 191, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138515', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-位移(m)', 'c_gtsjwy', null, 192, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138516', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-载荷(kN)', 'c_gtsjzh', null, 193, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138517', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-电流(A)', 'c_gtsjdl', null, 194, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138518', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-功率(kW)', 'c_gtsjgl', null, 195, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119852', null, 'cdd198534d5849b7a27054e0f2593ff3', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119853', null, 'cdd198534d5849b7a27054e0f2593ff3', '井名', 'wellName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('139255', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '序号', 'id', 'width:50', 1, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139256', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '井名', 'wellName', null, 2, 1, null, null, to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139257', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日期', 'calDate', null, 3, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139258', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '功图工况', 'resultName', null, 4, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139259', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '产液量(t/d)', 'liquidWeightProduction', null, 5, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139260', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '产油量(t/d)', 'oilWeightProduction', null, 6, 0, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139261', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '产液量(m^3/d)', 'liquidVolumetricProduction', null, 7, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139262', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '产油量(m^3/d)', 'oilVolumetricProduction', null, 8, 1, null, null, to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139263', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '泵效(%)', 'pumpEff', null, 9, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139264', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '系统效率(%)', 'systemEfficiency', null, 10, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139265', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '功率平衡度(%)', 'wattDegreeBalance', 'width:120', 11, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139266', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '电流平衡度(%)', 'iDegreeBalance', 'width:120', 12, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139267', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日用电量(kw·h)', 'todayKWattH', 'width:120', 13, 1, null, null, to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('134035', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '序号', 'id', 'width:50', 1, 1, null, null, to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134037', 'sys', 'd8cd980aa8344c399b9cf11268b6ed8f', '井名', 'wellName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('137915', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频监控路径1', 'videoUrl1', null, 13, 1, null, null, to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139579', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频密钥1', 'videoKeyName1', null, 14, 1, null, null, to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137916', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频监控路径2', 'videoUrl2', null, 15, 1, null, null, to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('139580', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '视频密钥2', 'videoKeyName2', null, 16, 1, null, null, to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134044', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '状态', 'statusName', null, 17, 1, null, null, to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134045', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '排序编号', 'sortNum', null, 18, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137917', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '原油密度(g/cm^3)', 'crudeOilDensity', null, 19, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137918', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '水密度(g/cm^3)', 'waterDensity', null, 20, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137919', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '天然气相对密度', 'naturalGasRelativeDensity', null, 21, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137920', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '饱和压力(MPa)', 'saturationPressure', null, 22, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137921', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油层中部深度(m)', 'reservoirDepth', null, 23, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137922', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油层中部温度(℃)', 'reservoirTemperature', null, 24, 1, null, null, to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137923', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油压(MPa)', 'tubingPressure', null, 25, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137924', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '套压(MPa)', 'casingPressure', null, 26, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137925', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '井口温度(℃)', 'wellHeadTemperature', null, 27, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137926', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '含水率(%)', 'waterCut', null, 28, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137927', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 29, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137928', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '动液面(m)', 'producingfluidLevel', null, 30, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137929', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵挂(m)', 'pumpSettingDepth', null, 31, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137931', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵筒类型', 'barrelType', null, 33, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137932', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵级别', 'pumpGrade', null, 34, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137933', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵径(mm)', 'pumpBoreDiameter', null, 35, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137934', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '柱塞长(m)', 'plungerLength', null, 36, 1, null, null, to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137935', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油管内径(mm)', 'tubingStringInsideDiameter', null, 37, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137936', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '套管内径(mm)', 'casingStringInsideDiameter', null, 38, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137937', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆级别', 'rodGrade1', null, 39, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137938', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆外径(mm)', 'rodOutsideDiameter1', null, 40, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137939', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆内径(mm)', 'rodInsideDiameter1', null, 41, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137940', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一级杆长度(m)', 'rodLength1', null, 42, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137941', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆级别', 'rodGrade2', null, 43, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137942', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆外径(mm)', 'rodOutsideDiameter2', null, 44, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137943', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆内径(mm)', 'rodInsideDiameter2', null, 45, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137944', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二级杆长度(m)', 'rodLength2', null, 46, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137945', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆级别', 'rodGrade3', null, 47, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137946', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆外径(mm)', 'rodOutsideDiameter3', null, 48, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137947', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆内径(mm)', 'rodInsideDiameter3', null, 49, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137948', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三级杆长度(m)', 'rodLength3', null, 50, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137949', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆级别', 'rodGrade4', null, 51, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137950', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆外径(mm)', 'rodOutsideDiameter4', null, 52, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137951', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆内径(mm)', 'rodInsideDiameter4', null, 53, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137952', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四级杆长度(m)', 'rodLength4', null, 54, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137953', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '工况干预', 'manualInterventionResultName', null, 55, 1, null, null, to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137954', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '净毛比(小数)', 'netGrossRatio', null, 56, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137955', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '净毛值(m^3/d)', 'netGrossValue', null, 57, 1, null, null, to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137956', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '反演液面校正值(MPa)', 'levelCorrectValue', null, 58, 1, null, null, to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137957', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '抽油机厂家', 'manufacturer', null, 59, 1, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137958', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '抽油机型号', 'model', null, 60, 1, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137959', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '铭牌冲程', 'stroke', null, 61, 1, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137960', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '平衡块重量(kN)', 'balanceWeight', null, 68, 1, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137961', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '平衡块位置(m)', 'balancePosition', null, 69, 1, null, null, to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118932', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118933', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '井名', 'wellName', null, 2, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128174', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '设备类型', 'deviceTypeName', null, 3, 1, null, null, to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118935', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 4, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118934', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '通信状态', 'commStatusName', null, 5, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134396', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '在线时间', 'commTime', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134397', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '在线时率', 'commTimeEfficiency', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134398', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '在线区间', 'commRange', null, 8, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133859', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行状态', 'runStatusName', null, 9, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134399', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行时间', 'runTime', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134400', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行时率', 'runTimeEfficiency', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134401', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行区间', 'runRange', null, 12, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137361', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '动液面(m)', 'c_dym', null, 13, 1, null, null, to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137362', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '井口套压(MPa)', 'c_jkty', null, 14, 1, null, null, to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137363', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '系统压力(KPa)', 'c_xtyl', null, 15, 1, null, null, to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137364', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '产气量累计(m3)', 'c_cqllj', null, 16, 1, null, null, to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138519', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '油压(MPa)', 'c_yy', null, 17, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138520', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '套压(MPa)', 'c_ty', null, 18, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138521', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '回压(MPa)', 'c_hy', null, 19, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138522', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '井口温度(℃)', 'c_jkwd', null, 20, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138523', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行状态', 'c_yxzt', null, 21, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138524', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '启停控制', 'c_qtkz', null, 22, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138525', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '含水率(%)', 'c_hsl', null, 23, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138526', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'A相电流(A)', 'c_Axdl', null, 24, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138527', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'B相电流(A)', 'c_Bxdl', null, 25, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138528', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'C相电流(V)', 'c_Cxdl', null, 26, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138529', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'A相电压(V)', 'c_Axdy', null, 27, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138530', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'B相电压(V)', 'c_Bxdy', null, 28, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138531', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'C相电压(V)', 'c_Cxdy', null, 29, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138532', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '有功功耗(kW·h)', 'c_yggh', null, 30, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138533', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '无功功耗(kVar·h)', 'c_wggh', null, 31, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138534', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '有功功率(kW)', 'c_yggl', null, 32, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138535', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '无功功率(kVar)', 'c_wggl', null, 33, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138536', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '反向功率(kW)', 'c_fxgl', null, 34, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138537', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '功率因数', 'c_glys', null, 35, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138538', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '变频设置频率(Hz)', 'c_bpszpl', null, 36, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138539', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '变频运行频率(Hz)', 'c_bpyxpl', null, 37, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138540', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '螺杆泵转速(r/min)', 'c_lgbzs', null, 38, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138541', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '螺杆泵扭矩(kN·m)', 'c_lgbnj', null, 39, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119859', null, 'e2924366ab174d4b9a096be969934985', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119860', null, 'e2924366ab174d4b9a096be969934985', '井名', 'wellName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:17:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:52', 'dd-mm-yyyy hh24:mi:ss'));

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
values ('119194', null, 'fb7d070a349c403b8a26d71c12af7a05', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119195', null, 'fb7d070a349c403b8a26d71c12af7a05', '井名', 'wellName', null, 2, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119197', null, 'fb7d070a349c403b8a26d71c12af7a05', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 3, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119196', null, 'fb7d070a349c403b8a26d71c12af7a05', '通信状态', 'commStatusName', null, 4, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134408', null, 'fb7d070a349c403b8a26d71c12af7a05', '在线时间', 'commTime', null, 5, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134409', null, 'fb7d070a349c403b8a26d71c12af7a05', '在线时率', 'commTimeEfficiency', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134410', null, 'fb7d070a349c403b8a26d71c12af7a05', '在线区间', 'commRange', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133897', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行状态', 'runStatusName', null, 8, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134411', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行时间', 'runTime', null, 9, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134412', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行时率', 'runTimeEfficiency', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134413', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行区间', 'runRange', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137365', null, 'fb7d070a349c403b8a26d71c12af7a05', '动液面(m)', 'c_dym', null, 12, 1, null, null, to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137366', null, 'fb7d070a349c403b8a26d71c12af7a05', '井口套压(MPa)', 'c_jkty', null, 13, 1, null, null, to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137367', null, 'fb7d070a349c403b8a26d71c12af7a05', '系统压力(KPa)', 'c_xtyl', null, 14, 1, null, null, to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('137368', null, 'fb7d070a349c403b8a26d71c12af7a05', '产气量累计(m3)', 'c_cqllj', null, 15, 1, null, null, to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-04-2023 14:38:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138542', null, 'fb7d070a349c403b8a26d71c12af7a05', '油压(MPa)', 'c_yy', null, 16, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138543', null, 'fb7d070a349c403b8a26d71c12af7a05', '套压(MPa)', 'c_ty', null, 17, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138544', null, 'fb7d070a349c403b8a26d71c12af7a05', '回压(MPa)', 'c_hy', null, 18, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138545', null, 'fb7d070a349c403b8a26d71c12af7a05', '井口温度(℃)', 'c_jkwd', null, 19, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138546', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行状态', 'c_yxzt', null, 20, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138547', null, 'fb7d070a349c403b8a26d71c12af7a05', '启停控制', 'c_qtkz', null, 21, 0, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138548', null, 'fb7d070a349c403b8a26d71c12af7a05', '含水率(%)', 'c_hsl', null, 22, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138549', null, 'fb7d070a349c403b8a26d71c12af7a05', 'A相电流(A)', 'c_Axdl', null, 23, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138550', null, 'fb7d070a349c403b8a26d71c12af7a05', 'B相电流(A)', 'c_Bxdl', null, 24, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138551', null, 'fb7d070a349c403b8a26d71c12af7a05', 'C相电流(V)', 'c_Cxdl', null, 25, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138552', null, 'fb7d070a349c403b8a26d71c12af7a05', 'A相电压(V)', 'c_Axdy', null, 26, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138553', null, 'fb7d070a349c403b8a26d71c12af7a05', 'B相电压(V)', 'c_Bxdy', null, 27, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138554', null, 'fb7d070a349c403b8a26d71c12af7a05', 'C相电压(V)', 'c_Cxdy', null, 28, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138555', null, 'fb7d070a349c403b8a26d71c12af7a05', '有功功耗(kW·h)', 'c_yggh', null, 29, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138556', null, 'fb7d070a349c403b8a26d71c12af7a05', '无功功耗(kVar·h)', 'c_wggh', null, 30, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138557', null, 'fb7d070a349c403b8a26d71c12af7a05', '有功功率(kW)', 'c_yggl', null, 31, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138558', null, 'fb7d070a349c403b8a26d71c12af7a05', '无功功率(kVar)', 'c_wggl', null, 32, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138559', null, 'fb7d070a349c403b8a26d71c12af7a05', '反向功率(kW)', 'c_fxgl', null, 33, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138560', null, 'fb7d070a349c403b8a26d71c12af7a05', '功率因数', 'c_glys', null, 34, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138561', null, 'fb7d070a349c403b8a26d71c12af7a05', '变频设置频率(Hz)', 'c_bpszpl', null, 35, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138562', null, 'fb7d070a349c403b8a26d71c12af7a05', '变频运行频率(Hz)', 'c_bpyxpl', null, 36, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138563', null, 'fb7d070a349c403b8a26d71c12af7a05', '螺杆泵转速(r/min)', 'c_lgbzs', null, 37, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('138564', null, 'fb7d070a349c403b8a26d71c12af7a05', '螺杆泵扭矩(kN·m)', 'c_lgbnj', null, 38, 1, null, null, to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 08:54:53', 'dd-mm-yyyy hh24:mi:ss'));