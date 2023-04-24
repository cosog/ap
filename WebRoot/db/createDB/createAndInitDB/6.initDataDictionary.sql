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
values ('b6ef8f3a49094768b3231d5678fc9cbc', null, '模块配置_模块列表', 'module_ModuleManage', 23101, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, CNAME, ENAME, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('b8a408839dd8498d9a19fc65f7406ed4', null, '字典配置_字典列表', 'dictionary_DataDictionaryManage', 23102, 0, '系统管理员', '系统管理员', to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'));

/*==============================================================*/
/* 初始化tbl_dist_item数据                                          */
/*==============================================================*/
insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118836', null, '7f13446d19b4497986980fa16a750f95', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118837', null, '7f13446d19b4497986980fa16a750f95', '井名', 'wellName', null, 2, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128173', null, '7f13446d19b4497986980fa16a750f95', '设备类型', 'deviceTypeName', null, 3, 1, null, null, to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118931', null, '7f13446d19b4497986980fa16a750f95', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 4, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118838', null, '7f13446d19b4497986980fa16a750f95', '通信状态', 'commStatusName', null, 5, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134390', null, '7f13446d19b4497986980fa16a750f95', '在线时间', 'commTime', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134391', null, '7f13446d19b4497986980fa16a750f95', '在线时率', 'commTimeEfficiency', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134392', null, '7f13446d19b4497986980fa16a750f95', '在线区间', 'commRange', null, 8, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133835', null, '7f13446d19b4497986980fa16a750f95', '运行状态', 'runStatusName', null, 9, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134393', null, '7f13446d19b4497986980fa16a750f95', '运行时间', 'runTime', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134394', null, '7f13446d19b4497986980fa16a750f95', '运行时率', 'runTimeEfficiency', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134395', null, '7f13446d19b4497986980fa16a750f95', '运行区间', 'runRange', null, 12, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132535', null, '7f13446d19b4497986980fa16a750f95', '运行状态', 'c_yxzt', null, 53, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132536', null, '7f13446d19b4497986980fa16a750f95', '启停控制', 'c_qtkz', null, 54, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132537', null, '7f13446d19b4497986980fa16a750f95', 'A相电流(A)', 'c_Axdl', null, 55, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132538', null, '7f13446d19b4497986980fa16a750f95', 'B相电流(A)', 'c_Bxdl', null, 56, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132539', null, '7f13446d19b4497986980fa16a750f95', 'C相电流(V)', 'c_Cxdl', null, 57, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132540', null, '7f13446d19b4497986980fa16a750f95', 'A相电压(V)', 'c_Axdy', null, 58, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132541', null, '7f13446d19b4497986980fa16a750f95', 'B相电压(V)', 'c_Bxdy', null, 59, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132542', null, '7f13446d19b4497986980fa16a750f95', 'C相电压(V)', 'c_Cxdy', null, 60, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132543', null, '7f13446d19b4497986980fa16a750f95', '有功功耗(kW·h)', 'c_yggh', null, 61, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132544', null, '7f13446d19b4497986980fa16a750f95', '无功功耗(kVar·h)', 'c_wggh', null, 62, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132545', null, '7f13446d19b4497986980fa16a750f95', '有功功率(kW)', 'c_yggl', null, 63, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132546', null, '7f13446d19b4497986980fa16a750f95', '无功功率(kVar)', 'c_wggl', null, 64, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132547', null, '7f13446d19b4497986980fa16a750f95', '反向功率(kW)', 'c_fxgl', null, 65, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132548', null, '7f13446d19b4497986980fa16a750f95', '功率因数', 'c_glys', null, 66, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132549', null, '7f13446d19b4497986980fa16a750f95', '油压(MPa)', 'c_yy', null, 67, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132550', null, '7f13446d19b4497986980fa16a750f95', '套压(MPa)', 'c_ty', null, 68, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132551', null, '7f13446d19b4497986980fa16a750f95', '回压(MPa)', 'c_hy', null, 69, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132553', null, '7f13446d19b4497986980fa16a750f95', '动液面(m)', 'c_dym', null, 71, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132554', null, '7f13446d19b4497986980fa16a750f95', '含水率(%)', 'c_hsl', null, 72, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132555', null, '7f13446d19b4497986980fa16a750f95', '变频设置频率(Hz)', 'c_bpszpl', null, 73, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132556', null, '7f13446d19b4497986980fa16a750f95', '变频运行频率(Hz)', 'c_bpyxpl', null, 74, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132557', null, '7f13446d19b4497986980fa16a750f95', '功图采集间隔(min)', 'c_gtcjjg', null, 75, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132558', null, '7f13446d19b4497986980fa16a750f95', '功图设置点数', 'c_gtszds', null, 76, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132559', null, '7f13446d19b4497986980fa16a750f95', '功图实测点数', 'c_gtscds', null, 77, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132560', null, '7f13446d19b4497986980fa16a750f95', '功图采集时间', 'c_gtcjsj', null, 78, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132561', null, '7f13446d19b4497986980fa16a750f95', '冲次(次/min)', 'c_cc', null, 79, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132562', null, '7f13446d19b4497986980fa16a750f95', '冲程(m)', 'c_cc1', null, 80, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132563', null, '7f13446d19b4497986980fa16a750f95', '功图数据-位移(m)', 'c_gtsjwy', null, 81, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132564', null, '7f13446d19b4497986980fa16a750f95', '功图数据-载荷(kN)', 'c_gtsjzh', null, 82, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132565', null, '7f13446d19b4497986980fa16a750f95', '功图数据-电流(A)', 'c_gtsjdl', null, 83, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132566', null, '7f13446d19b4497986980fa16a750f95', '功图数据-功率(kW)', 'c_gtsjgl', null, 84, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132567', null, '7f13446d19b4497986980fa16a750f95', '声光报警控制位', 'c_sgbjkzw', null, 85, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132707', null, '7f13446d19b4497986980fa16a750f95', '井口温度(℃)', 'c_jkwd', null, 86, 1, null, null, to_date('22-03-2022 20:16:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2022 20:16:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132774', null, '7f13446d19b4497986980fa16a750f95', '光杆温度(℃)', 'c_ggwd', null, 87, 1, null, null, to_date('24-03-2022 19:14:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-03-2022 19:14:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132775', null, '7f13446d19b4497986980fa16a750f95', '盘根盒温度(℃)', 'c_pghwd', null, 88, 1, null, null, to_date('24-03-2022 19:14:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-03-2022 19:14:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133975', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '抽油机厂家', 'manufacturer', null, 1, 1, null, null, to_date('24-05-2022 08:52:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 08:52:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133976', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '抽油机型号', 'model', null, 2, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133977', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '铭牌冲程(m)', 'stroke', null, 3, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133978', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '曲柄旋转方向', 'crankRotationDirection', null, 4, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133979', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '曲柄偏置角(°)', 'offsetAngleOfCrank', null, 5, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133980', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '曲柄重心半径(m)', 'crankGravityRadius', null, 6, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133981', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '单块曲柄重量(kN)', 'singleCrankWeight', null, 7, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133982', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '单块曲柄销重量(kN)', 'singleCrankPinWeight', null, 8, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133983', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '结构不平衡重(kN)', 'structuralUnbalance', null, 9, 1, null, null, to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:04:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133984', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '平衡块位置重量', 'balance', null, 10, 1, null, null, to_date('24-05-2022 09:07:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:07:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133985', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '原油密度(g/cm^3)', 'crudeOilDensity', null, 11, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133986', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '水密度(g/cm^3)', 'waterDensity', null, 12, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133987', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '天然气相对密度', 'naturalGasRelativeDensity', null, 13, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133988', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '饱和压力(MPa)', 'saturationPressure', null, 14, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133989', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '油层中部深度(m)', 'reservoirDepth', null, 15, 0, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133990', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '油层中部温度(℃)', 'reservoirTemperature', null, 16, 0, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133991', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '油压(MPa)', 'tubingPressure', null, 17, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133992', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '套压(MPa)', 'casingPressure', null, 18, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133993', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '井口温度(℃)', 'wellHeadTemperature', null, 19, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133994', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '含水率(%)', 'waterCut', null, 20, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133995', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 21, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133996', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '动液面(m)', 'producingfluidLevel', null, 22, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133997', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '泵挂(m)', 'pumpSettingDepth', null, 23, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133999', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '泵筒类型', 'barrelType', null, 25, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134000', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '泵级别', 'pumpGrade', null, 26, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134001', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '泵径(mm)', 'pumpBoreDiameter', null, 27, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134002', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '柱塞长(m)', 'plungerLength', null, 28, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134003', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '油管内径(mm)', 'tubingStringInsideDiameter', null, 29, 1, null, null, to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134004', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '套管内径(mm)', 'casingStringInsideDiameter', null, 30, 1, null, null, to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134005', null, 'd4205cd00a994817a51c2cab6d5fa0ab', '抽油杆参数', 'rodString', null, 31, 1, null, null, to_date('24-05-2022 09:20:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:20:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118932', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118933', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '井名', 'wellName', null, 2, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128174', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '设备类型', 'deviceTypeName', null, 3, 1, null, null, to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118935', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 4, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118934', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '通信状态', 'commStatusName', null, 5, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134396', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '在线时间', 'commTime', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134397', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '在线时率', 'commTimeEfficiency', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134398', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '在线区间', 'commRange', null, 8, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133859', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行状态', 'runStatusName', null, 9, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134399', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行时间', 'runTime', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134400', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行时率', 'runTimeEfficiency', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134401', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行区间', 'runRange', null, 12, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132601', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '运行状态', 'c_yxzt', null, 52, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132602', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '启停控制', 'c_qtkz', null, 53, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132603', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'A相电流(A)', 'c_Axdl', null, 54, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132604', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'B相电流(A)', 'c_Bxdl', null, 55, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132605', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'C相电流(V)', 'c_Cxdl', null, 56, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132606', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'A相电压(V)', 'c_Axdy', null, 57, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132607', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'B相电压(V)', 'c_Bxdy', null, 58, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132608', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', 'C相电压(V)', 'c_Cxdy', null, 59, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132609', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '有功功耗(kW·h)', 'c_yggh', null, 60, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132610', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '无功功耗(kVar·h)', 'c_wggh', null, 61, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132611', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '有功功率(kW)', 'c_yggl', null, 62, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132612', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '无功功率(kVar)', 'c_wggl', null, 63, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132613', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '反向功率(kW)', 'c_fxgl', null, 64, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132614', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '功率因数', 'c_glys', null, 65, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132615', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '油压(MPa)', 'c_yy', null, 66, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132616', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '套压(MPa)', 'c_ty', null, 67, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132617', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '回压(MPa)', 'c_hy', null, 68, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132619', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '动液面(m)', 'c_dym', null, 70, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132620', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '含水率(%)', 'c_hsl', null, 71, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132621', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '变频设置频率(Hz)', 'c_bpszpl', null, 72, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132622', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '变频运行频率(Hz)', 'c_bpyxpl', null, 73, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132623', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '螺杆泵转速(r/min)', 'c_lgbzs', null, 74, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132624', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '螺杆泵扭矩(kN·m)', 'c_lgbnj', null, 75, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132709', null, 'e0f5f3ff8a1f46678c284fba9cc113e8', '井口温度(℃)', 'c_jkwd', null, 76, 1, null, null, to_date('22-03-2022 20:16:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2022 20:16:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134006', null, 'ac7916c134074f39a27325eb7e076049', '原油密度(g/cm^3)', 'crudeOilDensity', null, 1, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134007', null, 'ac7916c134074f39a27325eb7e076049', '水密度(g/cm^3)', 'waterDensity', null, 2, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134008', null, 'ac7916c134074f39a27325eb7e076049', '天然气相对密度', 'naturalGasRelativeDensity', null, 3, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134009', null, 'ac7916c134074f39a27325eb7e076049', '饱和压力(MPa)', 'saturationPressure', null, 4, 0, null, null, to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:09:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134010', null, 'ac7916c134074f39a27325eb7e076049', '油层中部深度(m)', 'reservoirDepth', null, 5, 0, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134011', null, 'ac7916c134074f39a27325eb7e076049', '油层中部温度(℃)', 'reservoirTemperature', null, 6, 0, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134012', null, 'ac7916c134074f39a27325eb7e076049', '油压(MPa)', 'tubingPressure', null, 7, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134013', null, 'ac7916c134074f39a27325eb7e076049', '套压(MPa)', 'casingPressure', null, 8, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134014', null, 'ac7916c134074f39a27325eb7e076049', '井口温度(℃)', 'wellHeadTemperature', null, 9, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134015', null, 'ac7916c134074f39a27325eb7e076049', '含水率(%)', 'waterCut', null, 10, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134016', null, 'ac7916c134074f39a27325eb7e076049', '生产气油比(m^3/t)', 'productionGasOilRatio', null, 11, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134017', null, 'ac7916c134074f39a27325eb7e076049', '动液面(m)', 'producingfluidLevel', null, 12, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134018', null, 'ac7916c134074f39a27325eb7e076049', '泵挂(m)', 'pumpSettingDepth', null, 13, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134019', null, 'ac7916c134074f39a27325eb7e076049', '泵筒长(m)', 'barrelLength', null, 14, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134020', null, 'ac7916c134074f39a27325eb7e076049', '泵级数', 'barrelSeries', null, 15, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134021', null, 'ac7916c134074f39a27325eb7e076049', '转子直径(mm)', 'rotorDiameter', null, 16, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134022', null, 'ac7916c134074f39a27325eb7e076049', '公称排量(ml/转)', 'QPR', null, 17, 1, null, null, to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:16:39', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134023', null, 'ac7916c134074f39a27325eb7e076049', '油管内径(mm)', 'tubingStringInsideDiameter', null, 18, 1, null, null, to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134024', null, 'ac7916c134074f39a27325eb7e076049', '套管内径(mm)', 'casingStringInsideDiameter', null, 19, 1, null, null, to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:18:49', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134025', null, 'ac7916c134074f39a27325eb7e076049', '抽油杆参数', 'rodString', null, 20, 1, null, null, to_date('24-05-2022 09:20:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-05-2022 09:20:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119190', null, 'cd7b24562b924d19b556de31256e22a1', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119191', null, 'cd7b24562b924d19b556de31256e22a1', '井名', 'wellName', null, 2, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119193', null, 'cd7b24562b924d19b556de31256e22a1', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 3, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119192', null, 'cd7b24562b924d19b556de31256e22a1', '通信状态', 'commStatusName', null, 4, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134402', null, 'cd7b24562b924d19b556de31256e22a1', '在线时间', 'commTime', null, 5, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134403', null, 'cd7b24562b924d19b556de31256e22a1', '在线时率', 'commTimeEfficiency', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134404', null, 'cd7b24562b924d19b556de31256e22a1', '在线区间', 'commRange', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133873', null, 'cd7b24562b924d19b556de31256e22a1', '运行状态', 'runStatusName', null, 8, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134405', null, 'cd7b24562b924d19b556de31256e22a1', '运行时间', 'runTime', null, 9, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134406', null, 'cd7b24562b924d19b556de31256e22a1', '运行时率', 'runTimeEfficiency', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134407', null, 'cd7b24562b924d19b556de31256e22a1', '运行区间', 'runRange', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132568', null, 'cd7b24562b924d19b556de31256e22a1', '运行状态', 'c_yxzt', null, 53, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132569', null, 'cd7b24562b924d19b556de31256e22a1', '启停控制', 'c_qtkz', null, 54, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132570', null, 'cd7b24562b924d19b556de31256e22a1', 'A相电流(A)', 'c_Axdl', null, 55, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132571', null, 'cd7b24562b924d19b556de31256e22a1', 'B相电流(A)', 'c_Bxdl', null, 56, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132572', null, 'cd7b24562b924d19b556de31256e22a1', 'C相电流(V)', 'c_Cxdl', null, 57, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132573', null, 'cd7b24562b924d19b556de31256e22a1', 'A相电压(V)', 'c_Axdy', null, 58, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132574', null, 'cd7b24562b924d19b556de31256e22a1', 'B相电压(V)', 'c_Bxdy', null, 59, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132575', null, 'cd7b24562b924d19b556de31256e22a1', 'C相电压(V)', 'c_Cxdy', null, 60, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132576', null, 'cd7b24562b924d19b556de31256e22a1', '有功功耗(kW·h)', 'c_yggh', null, 61, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132577', null, 'cd7b24562b924d19b556de31256e22a1', '无功功耗(kVar·h)', 'c_wggh', null, 62, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132578', null, 'cd7b24562b924d19b556de31256e22a1', '有功功率(kW)', 'c_yggl', null, 63, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132579', null, 'cd7b24562b924d19b556de31256e22a1', '无功功率(kVar)', 'c_wggl', null, 64, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132580', null, 'cd7b24562b924d19b556de31256e22a1', '反向功率(kW)', 'c_fxgl', null, 65, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132581', null, 'cd7b24562b924d19b556de31256e22a1', '功率因数', 'c_glys', null, 66, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132582', null, 'cd7b24562b924d19b556de31256e22a1', '油压(MPa)', 'c_yy', null, 67, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132583', null, 'cd7b24562b924d19b556de31256e22a1', '套压(MPa)', 'c_ty', null, 68, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132584', null, 'cd7b24562b924d19b556de31256e22a1', '回压(MPa)', 'c_hy', null, 69, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132586', null, 'cd7b24562b924d19b556de31256e22a1', '动液面(m)', 'c_dym', null, 71, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132587', null, 'cd7b24562b924d19b556de31256e22a1', '含水率(%)', 'c_hsl', null, 72, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132588', null, 'cd7b24562b924d19b556de31256e22a1', '变频设置频率(Hz)', 'c_bpszpl', null, 73, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132589', null, 'cd7b24562b924d19b556de31256e22a1', '变频运行频率(Hz)', 'c_bpyxpl', null, 74, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132590', null, 'cd7b24562b924d19b556de31256e22a1', '功图采集间隔(min)', 'c_gtcjjg', null, 75, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132591', null, 'cd7b24562b924d19b556de31256e22a1', '功图设置点数', 'c_gtszds', null, 76, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132592', null, 'cd7b24562b924d19b556de31256e22a1', '功图实测点数', 'c_gtscds', null, 77, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132593', null, 'cd7b24562b924d19b556de31256e22a1', '功图采集时间', 'c_gtcjsj', null, 78, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132594', null, 'cd7b24562b924d19b556de31256e22a1', '冲次(次/min)', 'c_cc', null, 79, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132595', null, 'cd7b24562b924d19b556de31256e22a1', '冲程(m)', 'c_cc1', null, 80, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132596', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-位移(m)', 'c_gtsjwy', null, 81, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132597', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-载荷(kN)', 'c_gtsjzh', null, 82, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132598', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-电流(A)', 'c_gtsjdl', null, 83, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132599', null, 'cd7b24562b924d19b556de31256e22a1', '功图数据-功率(kW)', 'c_gtsjgl', null, 84, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132600', null, 'cd7b24562b924d19b556de31256e22a1', '声光报警控制位', 'c_sgbjkzw', null, 85, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132708', null, 'cd7b24562b924d19b556de31256e22a1', '井口温度(℃)', 'c_jkwd', null, 86, 1, null, null, to_date('22-03-2022 20:16:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2022 20:16:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132776', null, 'cd7b24562b924d19b556de31256e22a1', '光杆温度(℃)', 'c_ggwd', null, 87, 1, null, null, to_date('24-03-2022 19:14:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-03-2022 19:14:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132777', null, 'cd7b24562b924d19b556de31256e22a1', '盘根盒温度(℃)', 'c_pghwd', null, 88, 1, null, null, to_date('24-03-2022 19:14:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-03-2022 19:14:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119194', null, 'fb7d070a349c403b8a26d71c12af7a05', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119195', null, 'fb7d070a349c403b8a26d71c12af7a05', '井名', 'wellName', null, 2, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119197', null, 'fb7d070a349c403b8a26d71c12af7a05', '采集时间', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 3, 1, null, null, to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119196', null, 'fb7d070a349c403b8a26d71c12af7a05', '通信状态', 'commStatusName', null, 4, 1, null, null, to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134408', null, 'fb7d070a349c403b8a26d71c12af7a05', '在线时间', 'commTime', null, 5, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134409', null, 'fb7d070a349c403b8a26d71c12af7a05', '在线时率', 'commTimeEfficiency', null, 6, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134410', null, 'fb7d070a349c403b8a26d71c12af7a05', '在线区间', 'commRange', null, 7, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133897', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行状态', 'runStatusName', null, 8, 1, null, null, to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134411', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行时间', 'runTime', null, 9, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134412', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行时率', 'runTimeEfficiency', null, 10, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134413', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行区间', 'runRange', null, 11, 1, null, null, to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132625', null, 'fb7d070a349c403b8a26d71c12af7a05', '运行状态', 'c_yxzt', null, 51, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132626', null, 'fb7d070a349c403b8a26d71c12af7a05', '启停控制', 'c_qtkz', null, 52, 0, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132627', null, 'fb7d070a349c403b8a26d71c12af7a05', 'A相电流(A)', 'c_Axdl', null, 53, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132628', null, 'fb7d070a349c403b8a26d71c12af7a05', 'B相电流(A)', 'c_Bxdl', null, 54, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132629', null, 'fb7d070a349c403b8a26d71c12af7a05', 'C相电流(V)', 'c_Cxdl', null, 55, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132630', null, 'fb7d070a349c403b8a26d71c12af7a05', 'A相电压(V)', 'c_Axdy', null, 56, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132631', null, 'fb7d070a349c403b8a26d71c12af7a05', 'B相电压(V)', 'c_Bxdy', null, 57, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132632', null, 'fb7d070a349c403b8a26d71c12af7a05', 'C相电压(V)', 'c_Cxdy', null, 58, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132633', null, 'fb7d070a349c403b8a26d71c12af7a05', '有功功耗(kW·h)', 'c_yggh', null, 59, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132634', null, 'fb7d070a349c403b8a26d71c12af7a05', '无功功耗(kVar·h)', 'c_wggh', null, 60, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132635', null, 'fb7d070a349c403b8a26d71c12af7a05', '有功功率(kW)', 'c_yggl', null, 61, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132636', null, 'fb7d070a349c403b8a26d71c12af7a05', '无功功率(kVar)', 'c_wggl', null, 62, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132637', null, 'fb7d070a349c403b8a26d71c12af7a05', '反向功率(kW)', 'c_fxgl', null, 63, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132638', null, 'fb7d070a349c403b8a26d71c12af7a05', '功率因数', 'c_glys', null, 64, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132639', null, 'fb7d070a349c403b8a26d71c12af7a05', '油压(MPa)', 'c_yy', null, 65, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132640', null, 'fb7d070a349c403b8a26d71c12af7a05', '套压(MPa)', 'c_ty', null, 66, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132641', null, 'fb7d070a349c403b8a26d71c12af7a05', '回压(MPa)', 'c_hy', null, 67, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132643', null, 'fb7d070a349c403b8a26d71c12af7a05', '动液面(m)', 'c_dym', null, 69, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132644', null, 'fb7d070a349c403b8a26d71c12af7a05', '含水率(%)', 'c_hsl', null, 70, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132645', null, 'fb7d070a349c403b8a26d71c12af7a05', '变频设置频率(Hz)', 'c_bpszpl', null, 71, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132646', null, 'fb7d070a349c403b8a26d71c12af7a05', '变频运行频率(Hz)', 'c_bpyxpl', null, 72, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132647', null, 'fb7d070a349c403b8a26d71c12af7a05', '螺杆泵转速(r/min)', 'c_lgbzs', null, 73, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132648', null, 'fb7d070a349c403b8a26d71c12af7a05', '螺杆泵扭矩(kN·m)', 'c_lgbnj', null, 74, 1, null, null, to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-03-2022 19:22:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132710', null, 'fb7d070a349c403b8a26d71c12af7a05', '井口温度(℃)', 'c_jkwd', null, 75, 1, null, null, to_date('22-03-2022 20:16:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2022 20:16:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133335', null, '0d4297cb4db44bb3a9a3d5d983007039', '序号', 'id', 'width:50', 1, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133336', null, '0d4297cb4db44bb3a9a3d5d983007039', '采集时间', 'acqTime', 'width:150', 2, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133337', null, '0d4297cb4db44bb3a9a3d5d983007039', '工况', 'resultName', 'width:130', 3, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133338', null, '0d4297cb4db44bb3a9a3d5d983007039', '功图产液量(t/d)', 'liquidWeightProduction', null, 4, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133339', null, '0d4297cb4db44bb3a9a3d5d983007039', '累计产液量(t/d)', 'liquidWeightProduction_L', null, 5, 0, null, '系统管理员', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133340', null, '0d4297cb4db44bb3a9a3d5d983007039', '功图产液量(m^3/d)', 'liquidVolumetricProduction', null, 6, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133341', null, '0d4297cb4db44bb3a9a3d5d983007039', '累计产液量(m^3/d)', 'liquidVolumetricProduction_L', null, 7, 1, null, '系统管理员', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133342', null, '0d4297cb4db44bb3a9a3d5d983007039', '冲程(m)', 'stroke', null, 8, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133343', null, '0d4297cb4db44bb3a9a3d5d983007039', '冲次(1/min)', 'spm', null, 9, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133344', null, '0d4297cb4db44bb3a9a3d5d983007039', '最大载荷(kN)', 'fmax', null, 10, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133345', null, '0d4297cb4db44bb3a9a3d5d983007039', '最小载荷(kN)', 'fmin', null, 11, 1, null, null, to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133346', null, '0d4297cb4db44bb3a9a3d5d983007039', '功率平衡度(%)', 'wattDegreeBalance', null, 12, 1, null, '系统管理员', to_date('10-06-2020 11:11:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-06-2020 11:11:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133347', null, '0d4297cb4db44bb3a9a3d5d983007039', '电流平衡度(%)', 'iDegreeBalance', null, 13, 1, null, '系统管理员', to_date('10-06-2020 11:11:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-06-2020 11:11:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119852', null, 'cdd198534d5849b7a27054e0f2593ff3', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119853', null, 'cdd198534d5849b7a27054e0f2593ff3', '井名', 'wellName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119855', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:16:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('121867', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:16:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119856', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:16:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119857', null, 'cdd198534d5849b7a27054e0f2593ff3', '报警级别', 'alarmLevelName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119858', null, 'cdd198534d5849b7a27054e0f2593ff3', '恢复时间', 'recoveryTime', 'flex:3', 7, 0, null, '超级管理员', to_date('05-01-2022 14:17:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:00', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119859', null, 'e2924366ab174d4b9a096be969934985', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119860', null, 'e2924366ab174d4b9a096be969934985', '井名', 'wellName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:17:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:52', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119862', null, 'e2924366ab174d4b9a096be969934985', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:17:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119866', null, 'e2924366ab174d4b9a096be969934985', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:17:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119863', null, 'e2924366ab174d4b9a096be969934985', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:17:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119867', null, 'e2924366ab174d4b9a096be969934985', '报警值', 'alarmValue', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:18:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119868', null, 'e2924366ab174d4b9a096be969934985', '报警限值', 'alarmLimit', 'flex:2', 7, 1, null, '超级管理员', to_date('05-01-2022 14:18:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119869', null, 'e2924366ab174d4b9a096be969934985', '回差', 'hystersis', 'flex:2', 8, 1, null, '超级管理员', to_date('05-01-2022 14:18:08', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:08', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119864', null, 'e2924366ab174d4b9a096be969934985', '报警级别', 'alarmLevelName', 'flex:2', 9, 1, null, '超级管理员', to_date('05-01-2022 14:18:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119865', null, 'e2924366ab174d4b9a096be969934985', '恢复时间', 'recoveryTime', 'flex:3', 10, 0, null, '超级管理员', to_date('05-01-2022 14:18:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120115', null, 'b09082f4272e4768994db398e14bc3f2', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120116', null, 'b09082f4272e4768994db398e14bc3f2', '井名', 'wellName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:18:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120118', null, 'b09082f4272e4768994db398e14bc3f2', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:18:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('121868', null, 'b09082f4272e4768994db398e14bc3f2', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:19:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120119', null, 'b09082f4272e4768994db398e14bc3f2', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:19:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:07', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120120', null, 'b09082f4272e4768994db398e14bc3f2', '报警级别', 'alarmLevelName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:19:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('120121', null, 'b09082f4272e4768994db398e14bc3f2', '恢复时间', 'recoveryTime', 'flex:3', 7, 0, null, '超级管理员', to_date('05-01-2022 14:19:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:16', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119870', null, 'b71c1a2c9d574fe482080a56c7c780a9', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119871', null, 'b71c1a2c9d574fe482080a56c7c780a9', '井名', 'wellName', 'flex:2', 2, 1, null, '超级管理员', to_date('05-01-2022 14:19:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119873', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警时间', 'alarmTime', 'flex:3', 3, 1, null, '超级管理员', to_date('05-01-2022 14:19:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('121869', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警项', 'itemName', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 14:19:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119874', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警信息', 'alarmInfo', 'flex:2', 5, 1, null, '超级管理员', to_date('05-01-2022 14:19:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119875', null, 'b71c1a2c9d574fe482080a56c7c780a9', '报警级别', 'alarmLevelName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 14:19:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:43', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119876', null, 'b71c1a2c9d574fe482080a56c7c780a9', '恢复时间', 'recoveryTime', 'flex:3', 7, 0, null, '超级管理员', to_date('05-01-2022 14:19:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119371', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119372', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '井名', 'wellName', 'flex:3', 2, 1, null, '超级管理员', to_date('05-01-2022 10:41:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:43', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119373', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '设备类型', 'deviceTypeName', 'flex:2', 3, 1, null, '超级管理员', to_date('05-01-2022 10:41:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119374', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作用户', 'user_id', 'flex:2', 4, 1, null, '超级管理员', to_date('05-01-2022 10:41:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119375', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '登录IP', 'loginIp', 'flex:3', 5, 1, null, '超级管理员', to_date('05-01-2022 10:41:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:22', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119376', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作', 'actionName', 'flex:2', 6, 1, null, '超级管理员', to_date('05-01-2022 10:41:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:16', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119377', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '备注', 'remark', 'flex:10', 7, 1, null, '超级管理员', to_date('05-01-2022 10:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:40:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119378', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作时间', 'to_char(createTime@''yyyy-mm-dd hh24:mi:ss'') as createTime', 'flex:5', 8, 1, null, '超级管理员', to_date('05-01-2022 10:41:08', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:08', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119379', null, '167aeb3aca384afda8655d63aedee484', '序号', 'id', 'width:50', 1, 1, null, null, to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119380', null, '167aeb3aca384afda8655d63aedee484', '操作用户', 'user_id', 'flex:1', 2, 1, null, '超级管理员', to_date('05-01-2022 10:43:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119381', null, '167aeb3aca384afda8655d63aedee484', '登录IP', 'loginIp', 'flex:1', 3, 1, null, '超级管理员', to_date('05-01-2022 10:43:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119382', null, '167aeb3aca384afda8655d63aedee484', '操作', 'actionName', 'flex:1', 4, 1, null, '超级管理员', to_date('05-01-2022 10:43:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:22', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119383', null, '167aeb3aca384afda8655d63aedee484', '备注', 'remark', 'flex:1', 5, 1, null, '超级管理员', to_date('05-01-2022 10:43:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119384', null, '167aeb3aca384afda8655d63aedee484', '操作时间', 'to_char(createTime@''yyyy-mm-dd hh24:mi:ss'') as createTime', 'flex:1', 6, 1, null, '超级管理员', to_date('05-01-2022 10:43:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114832', null, '8ab792e089494533be910699d426c7d5', '单位名称', 'text', 'flex:3', 1, 1, null, '系统管理员', to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114834', null, '8ab792e089494533be910699d426c7d5', '排序编号', 'orgSeq', 'flex:1', 2, 1, null, '系统管理员', to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114885', null, '5ba761c1383f498f9ac97c9a8ab6d847', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114845', null, '5ba761c1383f498f9ac97c9a8ab6d847', '用户名称', 'userName', null, 2, 1, null, '系统管理员', to_date('24-12-2021 13:39:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:27', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114847', null, '5ba761c1383f498f9ac97c9a8ab6d847', '用户账号', 'userId', null, 3, 1, null, '系统管理员', to_date('24-12-2021 13:39:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114849', null, '5ba761c1383f498f9ac97c9a8ab6d847', '角色', 'userTypeName', null, 4, 1, null, '系统管理员', to_date('24-12-2021 13:39:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:34', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114850', null, '5ba761c1383f498f9ac97c9a8ab6d847', '电话', 'userPhone', null, 5, 1, null, '系统管理员', to_date('24-12-2021 19:53:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:53:43', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114851', null, '5ba761c1383f498f9ac97c9a8ab6d847', '邮箱', 'userInEmail', null, 6, 1, null, '系统管理员', to_date('24-12-2021 19:53:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:53:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('116092', null, '5ba761c1383f498f9ac97c9a8ab6d847', '快捷登录', 'userQuickLoginName', null, 7, 1, null, '系统管理员', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128635', null, '5ba761c1383f498f9ac97c9a8ab6d847', '接收报警短信', 'receiveSMSName', null, 8, 1, null, '系统管理员', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128636', null, '5ba761c1383f498f9ac97c9a8ab6d847', '接收报警邮件', 'receiveMailName', null, 9, 1, null, '系统管理员', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128604', null, '5ba761c1383f498f9ac97c9a8ab6d847', '状态', 'userEnableName', null, 10, 1, null, null, null, null);

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128599', null, '5ba761c1383f498f9ac97c9a8ab6d847', '创建时间', 'userRegtime', 'width:150', 11, 1, null, null, null, null);

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114886', null, '220c349e246e47a39a818023f1c97a63', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114854', 'sys', '220c349e246e47a39a818023f1c97a63', '角色名称', 'roleName', 'flex:1', 2, 1, 'sys', '系统管理员', to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('128641', null, '220c349e246e47a39a818023f1c97a63', '角色等级', 'roleLevel', 'flex:1', 3, 1, null, null, to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('122399', null, '220c349e246e47a39a818023f1c97a63', '数据显示级别', 'showLevel', 'flex:1', 4, 1, null, null, to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('116763', null, '220c349e246e47a39a818023f1c97a63', '设备控制权限', 'roleFlagName', 'flex:1', 5, 1, null, '系统管理员', to_date('28-12-2021 17:23:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-12-2021 17:23:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136435', null, '220c349e246e47a39a818023f1c97a63', '报表编辑权限', 'roleReportEditName', 'flex:1', 6, 1, null, null, to_date('08-02-2023 10:27:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-02-2023 10:27:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114856', null, '220c349e246e47a39a818023f1c97a63', '角色描述', 'remark', 'flex:3', 7, 1, null, '系统管理员', to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114888', null, '87808f225d7240f68c2ab879347d818a', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114867', 'sys', '87808f225d7240f68c2ab879347d818a', '井名', 'wellName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('122495', null, '87808f225d7240f68c2ab879347d818a', '应用场景', 'applicationScenariosName', null, 3, 1, null, null, to_date('15-11-2021 17:39:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-11-2021 17:39:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('116688', null, '87808f225d7240f68c2ab879347d818a', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133098', null, '87808f225d7240f68c2ab879347d818a', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136595', null, '87808f225d7240f68c2ab879347d818a', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119811', null, '87808f225d7240f68c2ab879347d818a', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135315', null, '87808f225d7240f68c2ab879347d818a', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114915', null, '87808f225d7240f68c2ab879347d818a', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136175', null, '87808f225d7240f68c2ab879347d818a', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('10-01-2023 09:00:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2023 09:00:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114916', null, '87808f225d7240f68c2ab879347d818a', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135535', null, '87808f225d7240f68c2ab879347d818a', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132518', null, '87808f225d7240f68c2ab879347d818a', '状态', 'statusName', null, 13, 1, null, null, to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135215', null, '87808f225d7240f68c2ab879347d818a', '隶属单位', 'allPath', null, 14, 1, null, null, to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('116923', null, '87808f225d7240f68c2ab879347d818a', '排序编号', 'sortNum', null, 15, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134035', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '序号', 'id', 'width:50', 1, 1, null, null, to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134037', 'sys', 'd8cd980aa8344c399b9cf11268b6ed8f', '井名', 'wellName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134039', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134040', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136600', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134041', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135321', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134042', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136195', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('11-01-2023 10:39:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-01-2023 10:39:54', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134043', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135540', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134044', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '状态', 'statusName', null, 15, 1, null, null, to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134045', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '排序编号', 'sortNum', null, 16, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118801', null, 'b14377621d74442eb1127de094dfc903', '序号', 'id', 'width:50', 1, 1, null, null, to_date('19-08-2021 14:25:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-08-2021 14:25:11', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118803', 'sys', 'b14377621d74442eb1127de094dfc903', '井名', 'wellName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('122496', null, 'b14377621d74442eb1127de094dfc903', '应用场景', 'applicationScenariosName', null, 3, 1, null, null, to_date('15-11-2021 17:54:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-11-2021 17:54:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118804', null, 'b14377621d74442eb1127de094dfc903', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('133099', null, 'b14377621d74442eb1127de094dfc903', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136596', null, 'b14377621d74442eb1127de094dfc903', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('119812', null, 'b14377621d74442eb1127de094dfc903', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135316', null, 'b14377621d74442eb1127de094dfc903', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118806', null, 'b14377621d74442eb1127de094dfc903', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136176', null, 'b14377621d74442eb1127de094dfc903', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('10-01-2023 09:09:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2023 09:09:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118807', null, 'b14377621d74442eb1127de094dfc903', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135536', null, 'b14377621d74442eb1127de094dfc903', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:15:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:15:01', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('132519', null, 'b14377621d74442eb1127de094dfc903', '状态', 'statusName', null, 13, 1, null, null, to_date('09-02-2022 10:22:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:22:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135216', null, 'b14377621d74442eb1127de094dfc903', '隶属单位', 'allPath', null, 14, 1, null, null, to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('118816', null, 'b14377621d74442eb1127de094dfc903', '排序编号', 'sortNum', null, 15, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134094', null, '7b727b60e9114ebc9e35c4312c8c31c0', '序号', 'id', 'width:50', 1, 1, null, null, to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134096', 'sys', '7b727b60e9114ebc9e35c4312c8c31c0', '井名', 'wellName', null, 2, 1, 'sys', '系统管理员', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134098', null, '7b727b60e9114ebc9e35c4312c8c31c0', '采控实例', 'instanceName', 'width:120', 4, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134099', null, '7b727b60e9114ebc9e35c4312c8c31c0', '显示实例', 'displayInstanceName', 'width:120', 5, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136601', null, '7b727b60e9114ebc9e35c4312c8c31c0', '报表实例', 'reportInstanceName', 'width:120', 6, 1, null, null, to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134100', null, '7b727b60e9114ebc9e35c4312c8c31c0', '报警实例', 'alarmInstanceName', 'width:120', 7, 1, null, '系统管理员', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135322', null, '7b727b60e9114ebc9e35c4312c8c31c0', '下位机TCP类型', 'tcpType', null, 8, 1, null, null, to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134101', null, '7b727b60e9114ebc9e35c4312c8c31c0', '注册包ID', 'signInId', null, 9, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('136215', null, '7b727b60e9114ebc9e35c4312c8c31c0', '下位机IP端口', 'ipPort', null, 10, 1, null, null, to_date('11-01-2023 16:51:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-01-2023 16:51:12', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134102', null, '7b727b60e9114ebc9e35c4312c8c31c0', '设备从地址', 'slave', null, 11, 1, null, null, to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('135539', null, '7b727b60e9114ebc9e35c4312c8c31c0', '错峰延时(s)', 'peakDelay', null, 12, 1, null, null, to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134103', null, '7b727b60e9114ebc9e35c4312c8c31c0', '状态', 'statusName', null, 15, 1, null, null, to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('134104', null, '7b727b60e9114ebc9e35c4312c8c31c0', '排序编号', 'sortNum', null, 16, 1, null, null, to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114837', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块名称', 'text', null, 1, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114838', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块简介', 'mdShowname', null, 2, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114839', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块编码', 'mdCode', null, 3, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114840', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块视图', 'mdUrl', null, 4, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114841', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块控制器', 'mdControl', null, 5, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114842', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块图标', 'mdIcon', null, 6, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114843', null, 'b6ef8f3a49094768b3231d5678fc9cbc', '模块类别', 'mdTypeName', null, 7, 1, null, '系统管理员', to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114844', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模块排序', 'mdSeq', null, 8, 1, 'sys', '系统管理员', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114884', null, 'b8a408839dd8498d9a19fc65f7406ed4', '序号', 'id', 'width:50', 1, 1, null, null, null, null);

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114827', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典模块名称', 'cname', null, 2, 1, null, '系统管理员', to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114828', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典模块代码', 'ename', null, 3, 1, null, '系统管理员', to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114829', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '字典顺序', 'sorts', null, 4, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114830', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '创建人', 'creator', null, 5, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into tbl_dist_item (DATAITEMID, TENANTID, SYSDATAID, CNAME, ENAME, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE)
values ('114831', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '创建时间', 'updatetime', null, 6, 1, 'sys', '系统管理员', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'));