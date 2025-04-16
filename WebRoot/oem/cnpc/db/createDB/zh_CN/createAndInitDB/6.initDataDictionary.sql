/*==============================================================*/
/* 初始化tbl_dist_name初始化                                         */
/*==============================================================*/
insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('7f13446d19b4497986980fa16a750f95', null, '???控-??概?', 'realTimeMonitoring_Overview', 11101, 0, 'system', 'system', to_date('02-11-2023 14:37:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2023 14:37:40', 'dd-mm-yyyy hh24:mi:ss'), 1998, 'Real-time monitoring - an overview of the equipment', '?????????? ? ?????? ????????? ??????? - ????? ????????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('cd7b24562b924d19b556de31256e22a1', null, '?史查?-??概?', 'historyQuery_Overview', 12101, 0, 'system', 'system', to_date('02-11-2023 14:36:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2023 14:36:33', 'dd-mm-yyyy hh24:mi:ss'), 2018, 'Historical Query - Device Overview', '???????????? ?????? - ????? ??????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('0d4297cb4db44bb3a9a3d5d983007039', null, '?史查?-?形?加', 'historyQuery_FESDiagramOverlay', 12103, 0, 'system', 'system', to_date('06-12-2024 14:03:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-12-2024 14:03:54', 'dd-mm-yyyy hh24:mi:ss'), 2018, 'Historical Query - Graphical Overlay', '???????????? ?????? - ??????????? ?????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('cdd198534d5849b7a27054e0f2593ff3', null, '故障查?-通信???警', 'alarmQuery_CommStatusAlarm', 14101, 0, 'system', 'system', to_date('02-11-2021 15:20:57', 'dd-mm-yyyy hh24:mi:ss'), to_date('02-11-2021 15:20:57', 'dd-mm-yyyy hh24:mi:ss'), 2058, 'Communication status alarms', '???????????? ? ????????? ?????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('e2924366ab174d4b9a096be969934985', 'system', '故障查?-?值量?警', 'alarmQuery_NumericValueAlarm', 14102, 0, 'system', 'system', to_date('16-09-2021 13:50:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2021 13:50:40', 'dd-mm-yyyy hh24:mi:ss'), 2058, 'Numerical alarm', '???????? ??????? ???????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('b09082f4272e4768994db398e14bc3f2', null, '故障查?-枚?量?警', 'alarmQuery_EnumValueAlarm', 14103, 0, 'system', 'system', to_date('05-03-2024 18:07:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:07:07', 'dd-mm-yyyy hh24:mi:ss'), 2058, 'Enumeration alarms', '???????????? ????????? ????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('b71c1a2c9d574fe482080a56c7c780a9', null, '故障查?-??量?警', 'alarmQuery_SwitchingValueAlarm', 14104, 0, 'system', 'system', to_date('07-10-2021 19:06:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-10-2021 19:06:41', 'dd-mm-yyyy hh24:mi:ss'), 2058, 'Switching alarms', '???????????? ????????? ????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('ad646d19fcaa4fbd9077dbf7a826b107', 'system', '日志查?-??操作日志', 'logQuery_DeviceOperationLog', 15101, 0, 'system', 'system', to_date('07-09-2021 17:05:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-09-2021 17:05:31', 'dd-mm-yyyy hh24:mi:ss'), 2038, 'Device operation logs', '??????? ?????? ??????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('167aeb3aca384afda8655d63aedee484', 'system', '日志查?-系?日志', 'logQuery_SystemLog', 15102, 0, 'system', 'system', to_date('07-09-2021 19:04:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-09-2021 19:04:19', 'dd-mm-yyyy hh24:mi:ss'), 2038, 'System logs', '????????? ???????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('436802a1c0074a79aafd00ce539166f4', 'system', '?算??-抽油机井????', 'calculateManager_SRPSingleRecord', 16101, 0, 'system', 'system', to_date('24-04-2023 09:45:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-04-2023 09:45:19', 'dd-mm-yyyy hh24:mi:ss'), 2179, 'Single record of pumping unit wells', '????????? ???? ??????? ???????? ?????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('cf1c0981f31242f9b3e84810bdc0a19f', 'system', '?算??-抽油机井????', 'calculateManager_SRPTotalRecord', 16102, 0, 'system', 'system', to_date('07-05-2022 19:56:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-05-2022 19:56:03', 'dd-mm-yyyy hh24:mi:ss'), 2179, 'Summary of pumping well records', '?????? ??????? ? ???????? ?????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('aad8b76fdaf84a1194de5ec0a4453631', null, '?算??-螺杆泵井????', 'calculateManager_PCPSingleRecord', 16103, 0, 'system', 'system', to_date('30-11-2018 16:40:57', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:40:57', 'dd-mm-yyyy hh24:mi:ss'), 2179, 'A single record of the screw pump well', '????????? ?????? ???????? ????????? ??????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('8122b170c0ca4deb87159c931ab251f3', 'system', '?算??-螺杆泵井????', 'calculateManager_PCPTotalRecord', 16104, 0, 'system', 'system', to_date('07-05-2022 19:56:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-05-2022 19:56:38', 'dd-mm-yyyy hh24:mi:ss'), 2179, 'Summary of screw pump well records', '?????? ??????? ? ????????? ????????? ??????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('8ab792e089494533be910699d426c7d5', null, '??用?-?位列表', 'orgAndUser_OrgManage', 21101, 0, 'system', 'system', to_date('24-12-2021 09:33:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 09:33:43', 'dd-mm-yyyy hh24:mi:ss'), 24, 'Organization Users - Organization List', '???????????? ??????????? - ?????? ???????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('5ba761c1383f498f9ac97c9a8ab6d847', null, '??用?-用?列表', 'orgAndUser_UserManage', 21102, 0, 'system', 'system', to_date('03-09-2018 13:45:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:45:52', 'dd-mm-yyyy hh24:mi:ss'), 24, 'Organization Users - A list of users', '???????????? ??????????? - ?????? ?????????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('220c349e246e47a39a818023f1c97a63', null, '角色管理-角色列表', 'role_RoleManage', 21103, 0, 'system', 'system', to_date('03-09-2018 13:46:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:46:02', 'dd-mm-yyyy hh24:mi:ss'), 29, 'Role Management - List of roles', '?????????? ?????? - ?????? ?????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('87808f225d7240f68c2ab879347d818a', null, '主??-??列表', 'deviceInfo_DeviceManager', 22101, 0, 'system', 'system', to_date('09-10-2023 08:57:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-10-2023 08:57:41', 'dd-mm-yyyy hh24:mi:ss'), 34, 'List of primary devices', '?????? ???????? ?????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('d8cd980aa8344c399b9cf11268b6ed8f', null, '主??-??批量添加', 'deviceInfo_DeviceBatchAdd', 22102, 0, 'system', 'system', to_date('18-01-2024 11:39:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-01-2024 11:39:22', 'dd-mm-yyyy hh24:mi:ss'), 34, 'Primary devices are added in batches', '???????? ?????????? — ?????????? ????????? ? ???????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('2b4cd8cb8c6844769c66b038246c27bf', null, '短信??管理', 'deviceInfo_SMSDeviceManager', 22104, 0, 'system', 'system', to_date('21-12-2021 20:19:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-12-2021 20:19:30', 'dd-mm-yyyy hh24:mi:ss'), 2078, 'SMS device management', '?????????? SMS-????????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('1404100741bc42799be5b7cbebf4b649', null, '?件??-?件??列表', 'auxiliaryDeviceManager', 22105, 0, 'system', 'system', to_date('20-12-2024 09:42:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('20-12-2024 09:42:42', 'dd-mm-yyyy hh24:mi:ss'), 2218, 'List of accessories and devices', '???????? ??????????? ? ?????????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('b6ef8f3a49094768b3231d5678fc9cbc', null, '模?配置-模?列表', 'module_ModuleManage', 23101, 0, 'system', 'system', to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:38', 'dd-mm-yyyy hh24:mi:ss'), 26, 'List of modules', '?????? ???????');

insert into TBL_DIST_NAME (SYSDATAID, TENANTID, NAME_ZH_CN, CODE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, MODULEID, NAME_EN, NAME_RU)
values ('b8a408839dd8498d9a19fc65f7406ed4', null, '字典配置-字典列表', 'dictionary_DataDictionaryManage', 23102, 0, 'system', 'system', to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('03-09-2018 13:47:49', 'dd-mm-yyyy hh24:mi:ss'), 894, 'Dictionary list', '?????? ????????');

/*==============================================================*/
/* 初始化tbl_dist_item?据                                        */
/*==============================================================*/
insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('118836', null, '7f13446d19b4497986980fa16a750f95', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('118837', null, '7f13446d19b4497986980fa16a750f95', '??名?', 'deviceName', null, 2, 1, 'system', 'system', to_date('22-02-2024 08:41:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-02-2024 08:41:59', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128173', null, '7f13446d19b4497986980fa16a750f95', '???型', 'deviceTypeName', null, 3, 1, 'system', 'system', to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-12-2021 22:25:35', 'dd-mm-yyyy hh24:mi:ss'), 'Device type', '??? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('118931', null, '7f13446d19b4497986980fa16a750f95', '采集??', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 4, 1, 'system', 'system', to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), 'Time', '?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('118838', null, '7f13446d19b4497986980fa16a750f95', '通信??', 'commStatusName', null, 5, 1, 'system', 'system', to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), 'Communication status', '?????? ?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134390', null, '7f13446d19b4497986980fa16a750f95', '在???(h)', 'commTime', null, 6, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Online time(h)', '????? ??????(h)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134391', null, '7f13446d19b4497986980fa16a750f95', '在??率(小?)', 'commTimeEfficiency', null, 7, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Online time efficiency(decimal fraction)', '????????????? ??????? ? ?????????(?????????? ?????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134392', null, '7f13446d19b4497986980fa16a750f95', '在???', 'commRange', null, 8, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Online time frame', '????????? ????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133835', null, '7f13446d19b4497986980fa16a750f95', '?行??', 'runStatusName', null, 9, 1, 'system', 'system', to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), 'Operational status', '??????? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134393', null, '7f13446d19b4497986980fa16a750f95', '?行??(h)', 'runTime', null, 10, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Running time(h)', '?????????????????(h)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134394', null, '7f13446d19b4497986980fa16a750f95', '?行?率(小?)', 'runTimeEfficiency', null, 11, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Runtime efficiency(decimal fraction)', '????????????? ?? ????? ??????????(?????????? ?????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134395', null, '7f13446d19b4497986980fa16a750f95', '?行??', 'runRange', null, 12, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Run time range', '???????? ??????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151666', null, '7f13446d19b4497986980fa16a750f95', '原油密度(g/cm^3)', 'crudeoilDensity', null, 13, 0, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Crude oil density(g/cm^3)', '????????? ????? ?????(g/cm^3)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151667', null, '7f13446d19b4497986980fa16a750f95', '水密度(g/cm^3)', 'waterDensity', null, 14, 0, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Water density(g/cm^3)', '????????? ????(g/cm^3)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151668', null, '7f13446d19b4497986980fa16a750f95', '天然气相?密度', 'naturalGasRelativeDensity', null, 15, 0, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Relative density of natural gas', '????????????? ????????? ?????????? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151669', null, '7f13446d19b4497986980fa16a750f95', '?和?力(MPa)', 'saturationPressure', null, 16, 0, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Saturation pressure(MPa)', '???????? ?????????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151670', null, '7f13446d19b4497986980fa16a750f95', '油?中部深度(m)', 'reservoirDepth', null, 17, 0, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'The depth of the oil layer(m)', '??????? ????????? ????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151671', null, '7f13446d19b4497986980fa16a750f95', '油?中部?度(℃)', 'reservoirTemperature', null, 18, 0, 'system', 'system', to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'), 'The temperature of the oil layer(℃)', '??????????? ????????? ????(℃)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151672', null, '7f13446d19b4497986980fa16a750f95', '油?(MPa)', 'tubingPressure', null, 19, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Tubing pressure(MPa)', '???????? ? ???????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151673', null, '7f13446d19b4497986980fa16a750f95', '套?(MPa)', 'casingPressure', null, 20, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Casing pressure(MPa)', '???????? ? ???????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151674', null, '7f13446d19b4497986980fa16a750f95', '井口?度(℃)', 'wellHeadFluidTemperature', null, 21, 0, 'system', 'system', to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), 'The temperature at the wellhead(℃)', '??????????? ?? ????? ????????(℃)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151675', null, '7f13446d19b4497986980fa16a750f95', '含水率(%)', 'weightWaterCut', null, 22, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Water Cut(%)', '??????????????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151676', null, '7f13446d19b4497986980fa16a750f95', '生?气油比(m^3/t)', 'productionGasOilRatio', 'width:120', 23, 0, 'system', 'system', to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), 'The ratio of gas to oil(m^3/t)', '??????????? ???? ? ?????(m^3/t)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151677', null, '7f13446d19b4497986980fa16a750f95', '?液面(m)', 'producingFluidLevel', null, 24, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Production of liquid surfaces(m)', '???????????? ?????? ????????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151678', null, '7f13446d19b4497986980fa16a750f95', '泵挂(m)', 'pumpSettingDepth', null, 25, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Pump depth(m)', '??????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151679', null, '7f13446d19b4497986980fa16a750f95', '泵筒?型', 'barrelTypeName', null, 26, 0, 'system', 'system', to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'), 'Cartridge type', '??? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151680', null, '7f13446d19b4497986980fa16a750f95', '泵??', 'pumpGrade', null, 27, 0, 'system', 'system', to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), 'Pump level', '??????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151681', null, '7f13446d19b4497986980fa16a750f95', '泵?(mm)', 'pumpboreDiameter', null, 28, 0, 'system', 'system', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), 'Barrel diameter(mm)', '??????? ?????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151682', null, '7f13446d19b4497986980fa16a750f95', '柱塞?(m)', 'plungerLength', null, 29, 0, 'system', 'system', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), 'Plunger length(m)', '????? ????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151710', null, '7f13446d19b4497986980fa16a750f95', '泵??', 'barrelSeries', null, 30, 0, 'system', 'system', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), 'Number of pump stages', '?????????? ???????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151711', null, '7f13446d19b4497986980fa16a750f95', '?子直?(mm)', 'rotorDiameter', null, 31, 0, 'system', 'system', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), 'Rotor diameter(mm)', '??????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151712', null, '7f13446d19b4497986980fa16a750f95', '公?排量(ml/?)', 'qpr', null, 32, 0, 'system', 'system', to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'), 'Nominal displacement(ml/r)', '??????????? ??????? ?????(ml/r)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151683', null, '7f13446d19b4497986980fa16a750f95', '油管??(mm)', 'tubingStringInsideDiameter', null, 33, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Tubing inner diameter(mm)', '?????????? ??????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151684', null, '7f13446d19b4497986980fa16a750f95', '套管??(mm)', 'casingStringInsideDiameter', 'width:120', 34, 0, 'system', 'system', to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), 'Casing inner diameter(mm)', '?????????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151685', null, '7f13446d19b4497986980fa16a750f95', '一?杆?型', 'rodTypeName1', null, 35, 0, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'The type of first-stage rod', '??? ?????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151686', null, '7f13446d19b4497986980fa16a750f95', '一?杆??', 'rodGrade1', null, 36, 0, 'system', 'system', to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the first level of the rod', '??????? ??????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151687', null, '7f13446d19b4497986980fa16a750f95', '一?杆外?(mm)', 'rodOutsideDiameter1', null, 37, 0, 'system', 'system', to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the first stage rod(mm)', '???????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151688', null, '7f13446d19b4497986980fa16a750f95', '一?杆??(mm)', 'rodInsideDiameter1', null, 38, 0, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the first stage rod(mm)', '?????????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151689', null, '7f13446d19b4497986980fa16a750f95', '一?杆?度(m)', 'rodLength1', null, 39, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the first stage rod(m)', '????? ?????? ?????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151690', null, '7f13446d19b4497986980fa16a750f95', '二?杆?型', 'rodTypeName2', null, 40, 0, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Type of second-stage rod', '??? ?????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151691', null, '7f13446d19b4497986980fa16a750f95', '二?杆??', 'rodGrade2', null, 41, 0, 'system', 'system', to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the second level of the pole', '??????? ??????? ?????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151692', null, '7f13446d19b4497986980fa16a750f95', '二?杆外?(mm)', 'rodOutsideDiameter2', null, 42, 0, 'system', 'system', to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the second stage rod(mm)', '???????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151693', null, '7f13446d19b4497986980fa16a750f95', '二?杆??(mm)', 'rodInsideDiameter2', null, 43, 0, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the second stage rod(mm)', '?????????? ??????? ?????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151694', null, '7f13446d19b4497986980fa16a750f95', '二?杆?度(m)', 'rodLength2', null, 44, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the second stage rod(m)', '????? ?????? ?????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151695', null, '7f13446d19b4497986980fa16a750f95', '三?杆?型', 'rodTypeName3', null, 45, 0, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Type of tertiary rod', '??? ?????????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151696', null, '7f13446d19b4497986980fa16a750f95', '三?杆??', 'rodGrade3', null, 46, 0, 'system', 'system', to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the third pole', '??????? ???????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151697', null, '7f13446d19b4497986980fa16a750f95', '三?杆外?(mm)', 'rodOutsideDiameter3', null, 47, 0, 'system', 'system', to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the third stage rod(mm)', '???????? ??????? ??????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151698', null, '7f13446d19b4497986980fa16a750f95', '三?杆??(mm)', 'rodInsideDiameter3', null, 48, 0, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the third-stage rod(mm)', '?????????? ??????? ??????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151699', null, '7f13446d19b4497986980fa16a750f95', '三?杆?度(m)', 'rodLength3', null, 49, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the third-stage rod(m)', '????? ?????? ??????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151700', null, '7f13446d19b4497986980fa16a750f95', '四?杆?型', 'rodTypeName4', null, 50, 0, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Type of fourth-level rod', '??? ?????? ?????????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151701', null, '7f13446d19b4497986980fa16a750f95', '四?杆??', 'rodGrade4', null, 51, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the fourth level of the pole', '??????? ?????????? ?????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151702', null, '7f13446d19b4497986980fa16a750f95', '四?杆外?(mm)', 'rodOutsideDiameter4', null, 52, 0, 'system', 'system', to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the fourth-stage rod(mm)', '???????? ??????? ??????? ????????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151703', null, '7f13446d19b4497986980fa16a750f95', '四?杆??(mm)', 'rodInsideDiameter4', null, 53, 0, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the fourth-level rod(mm)', '?????????? ??????? ??????? ?????????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151704', null, '7f13446d19b4497986980fa16a750f95', '四?杆?度(m)', 'rodLength4', null, 54, 0, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the fourth-level rod(m)', '????? ??????? ?????????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151706', null, '7f13446d19b4497986980fa16a750f95', '工?干?', 'manualInterventionResult', null, 55, 0, 'system', 'system', to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'), 'Condition intervention', '????????????? ? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151707', null, '7f13446d19b4497986980fa16a750f95', '?毛比(小?)', 'netGrossRatio', null, 56, 0, 'system', 'system', to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), 'Net Gross Ratio (Decimal)', '??????????? ??????? ?????? (??????????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151708', null, '7f13446d19b4497986980fa16a750f95', '?毛值(m^3/d)', 'netGrossValue', null, 57, 0, 'system', 'system', to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), 'Net gross value(m^3/d)', '?????? ??????? ?????????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('151709', null, '7f13446d19b4497986980fa16a750f95', '反演液面校正值(MPa)', 'levelCorrectValue', null, 58, 0, 'system', 'system', to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'), 'Level correction value(MPa)', '???????? ????????? ??????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119190', null, 'cd7b24562b924d19b556de31256e22a1', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119191', null, 'cd7b24562b924d19b556de31256e22a1', '??名?', 'deviceName', null, 2, 1, 'system', 'system', to_date('11-05-2024 16:05:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2024 16:05:06', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119193', null, 'cd7b24562b924d19b556de31256e22a1', '???型', 'deviceTypeName', null, 3, 1, 'system', 'system', to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 14:47:29', 'dd-mm-yyyy hh24:mi:ss'), 'Device type', '??? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119192', null, 'cd7b24562b924d19b556de31256e22a1', '采集??', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:130', 4, 1, 'system', 'system', to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), 'Time', '?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134402', null, 'cd7b24562b924d19b556de31256e22a1', '通信??', 'commStatusName', null, 5, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Communication status', '?????? ?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134403', null, 'cd7b24562b924d19b556de31256e22a1', '在???(h)', 'commTime', null, 6, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Online time(h)', '????? ??????(h)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134404', null, 'cd7b24562b924d19b556de31256e22a1', '在??率(小?)', 'commTimeEfficiency', null, 7, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Online time efficiency(decimal fraction)', '????????????? ??????? ? ?????????(?????????? ?????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133873', null, 'cd7b24562b924d19b556de31256e22a1', '在???', 'commRange', null, 8, 1, 'system', 'system', to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), 'Online time frame', '????????? ????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134405', null, 'cd7b24562b924d19b556de31256e22a1', '?行??', 'runStatusName', null, 9, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Operational status', '??????? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134406', null, 'cd7b24562b924d19b556de31256e22a1', '?行??(h)', 'runTime', null, 10, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Running time(h)', '?????????????????(h)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134407', null, 'cd7b24562b924d19b556de31256e22a1', '?行?率(小?)', 'runTimeEfficiency', null, 11, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Runtime efficiency(decimal fraction)', '????????????? ?? ????? ??????????(?????????? ?????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('149217', null, 'cd7b24562b924d19b556de31256e22a1', '?行??', 'runRange', null, 12, 1, 'system', 'system', to_date('07-06-2024 09:01:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2024 09:01:30', 'dd-mm-yyyy hh24:mi:ss'), 'Run time range', '???????? ??????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133335', null, '0d4297cb4db44bb3a9a3d5d983007039', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133336', null, '0d4297cb4db44bb3a9a3d5d983007039', '功?采集??', 'acqTime', 'width:150', 2, 1, 'system', 'system', to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'The collection time of the dynamogram', '????? ????? ????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138735', null, '0d4297cb4db44bb3a9a3d5d983007039', '通信??', 'commStatusName', null, 3, 1, 'system', 'system', to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:29:42', 'dd-mm-yyyy hh24:mi:ss'), 'Communication status', '?????? ?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138736', null, '0d4297cb4db44bb3a9a3d5d983007039', '在???(h)', 'commTime', null, 4, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Online time(h)', '????? ??????(h)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138737', null, '0d4297cb4db44bb3a9a3d5d983007039', '在??率(小?)', 'commTimeEfficiency', null, 5, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Online time efficiency(decimal fraction)', '????????????? ??????? ? ?????????(?????????? ?????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138738', null, '0d4297cb4db44bb3a9a3d5d983007039', '在???', 'commRange', null, 6, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Online time frame', '????????? ????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138739', null, '0d4297cb4db44bb3a9a3d5d983007039', '?行??', 'runStatusName', null, 7, 1, 'system', 'system', to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2022 09:49:52', 'dd-mm-yyyy hh24:mi:ss'), 'Operational status', '??????? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138740', null, '0d4297cb4db44bb3a9a3d5d983007039', '?行??(h)', 'runTime', null, 8, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Running time(h)', '?????????????????(h)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138741', null, '0d4297cb4db44bb3a9a3d5d983007039', '?行?率(小?)', 'runTimeEfficiency', null, 9, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Runtime efficiency(decimal fraction)', '????????????? ?? ????? ??????????(?????????? ?????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138742', null, '0d4297cb4db44bb3a9a3d5d983007039', '?行??', 'runRange', null, 10, 1, 'system', 'system', to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('07-06-2022 17:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'Run time range', '???????? ??????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133337', null, '0d4297cb4db44bb3a9a3d5d983007039', '工?', 'resultName', 'width:130', 11, 1, 'system', 'system', to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Working conditions', '??????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138743', null, '0d4297cb4db44bb3a9a3d5d983007039', '优化建?', 'optimizationSuggestion', null, 12, 1, 'system', 'system', to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-08-2022 11:27:20', 'dd-mm-yyyy hh24:mi:ss'), 'Optimization suggestions', '??????????? ?? ???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133342', null, '0d4297cb4db44bb3a9a3d5d983007039', '?程(m)', 'stroke', null, 13, 1, 'system', 'system', to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Stroke(m)', '????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133343', null, '0d4297cb4db44bb3a9a3d5d983007039', '?次(1/min)', 'spm', null, 14, 1, 'system', 'system', to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Strokes per minute(1/min)', '?????? ? ??????(1/min)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133344', null, '0d4297cb4db44bb3a9a3d5d983007039', '最大?荷(kN)', 'fmax', null, 15, 1, 'system', 'system', to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Maximum Load(kN)', '???????????? ????????(kN)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133345', null, '0d4297cb4db44bb3a9a3d5d983007039', '最小?荷(kN)', 'fmin', null, 16, 1, 'system', 'system', to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-03-2020 15:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Minimum Load(kN)', '??????????? ????????(kN)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138744', null, '0d4297cb4db44bb3a9a3d5d983007039', '充?系?', 'fullnessCoefficient', null, 17, 1, 'system', 'system', to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:16:55', 'dd-mm-yyyy hh24:mi:ss'), 'Fill coefficient', '??????????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139533', null, '0d4297cb4db44bb3a9a3d5d983007039', '柱塞?程(m)', 'plungerStroke', null, 18, 1, 'system', 'system', to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), 'Plunger stroke(m)', '??? ????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139534', null, '0d4297cb4db44bb3a9a3d5d983007039', '柱塞有效?程(m)', 'availablePlungerStroke', null, 19, 1, 'system', 'system', to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-06-2023 10:22:47', 'dd-mm-yyyy hh24:mi:ss'), 'Effective plunger stroke(m)', '??????????? ??? ????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138745', null, '0d4297cb4db44bb3a9a3d5d983007039', '泵挂(m)', 'pumpSettingDepth', null, 20, 1, 'system', 'system', to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'Pump depth(m)', '??????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139722', null, '0d4297cb4db44bb3a9a3d5d983007039', '?液面(m)', 'producingfluidLevel', null, 21, 1, 'system', 'system', to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'Production of liquid surfaces(m)', '???????????? ?????? ????????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139723', null, '0d4297cb4db44bb3a9a3d5d983007039', '反演液面校正值(MPa)', 'levelCorrectValue', null, 22, 1, 'system', 'system', to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'Level correction value(MPa)', '???????? ????????? ??????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138746', null, '0d4297cb4db44bb3a9a3d5d983007039', '反演?液面(m)', 'calcProducingfluidLevel', null, 23, 1, 'system', 'system', to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'Inverted production liquid level(m)', '??????????????? ??????? ???????????????? ????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138747', null, '0d4297cb4db44bb3a9a3d5d983007039', '液面校正差值(MPa)', 'levelDifferenceValue', null, 24, 1, 'system', 'system', to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'Level correction difference(MPa)', '??????? ????????? ??????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138748', null, '0d4297cb4db44bb3a9a3d5d983007039', '沉?度(m)', 'submergence', null, 25, 1, 'system', 'system', to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 15:09:07', 'dd-mm-yyyy hh24:mi:ss'), 'Submerged depth(m)', '??????? ??????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133338', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬??液量(t/d)', 'liquidWeightProduction', null, 26, 0, 'system', 'system', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time liquid production(t/d)', '???????????? ????????? ? ?????? ????????? ???????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145265', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬??油量(t/d)', 'oilWeightProduction', null, 27, 0, 'system', 'system', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time oil production(t/d)', '?????? ????? ? ?????? ????????? ???????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145266', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬??水量(t/d)', 'waterWeightProduction', null, 28, 0, 'system', 'system', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time water production(t/d)', '?????? ???? ? ?????? ????????? ???????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133339', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累??液量(t/d)', 'liquidWeightProduction_L', null, 29, 0, 'system', 'system', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily fluid production(t/d)', '?????????? ???????? ???????????? ????????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145267', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累??油量(t/d)', 'oilWeightProduction_L', null, 30, 0, 'system', 'system', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily oil production(t/d)', '?????????? ???????? ?????? ?????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145268', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累??水量(t/d)', 'waterWeightProduction_L', null, 31, 0, 'system', 'system', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily water production(t/d)', '?????????? ???????? ?????????????????? ????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133340', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬??液量(m^3/d)', 'liquidVolumetricProduction', null, 32, 1, 'system', 'system', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time liquid production(m^3/d)', '???????????? ????????? ? ?????? ????????? ???????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145269', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬??油量(m^3/d)', 'oilVolumetricProduction', null, 33, 1, 'system', 'system', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time oil production(m^3/d)', '?????? ????? ? ?????? ????????? ???????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145270', null, '0d4297cb4db44bb3a9a3d5d983007039', '瞬??水量(m^3/d)', 'waterVolumetricProduction', null, 34, 1, 'system', 'system', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time water production(m^3/d)', '?????? ???? ? ?????? ????????? ???????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133341', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累??液量(m^3/d)', 'liquidVolumetricProduction_L', null, 35, 1, 'system', 'system', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily fluid production(m^3/d)', '?????????? ???????? ???????????? ????????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145271', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累??油量(m^3/d)', 'oilVolumetricProduction_L', null, 36, 1, 'system', 'system', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily oil production(m^3/d)', '?????????? ???????? ?????? ?????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145272', null, '0d4297cb4db44bb3a9a3d5d983007039', '日累??水量(m^3/d)', 'waterVolumetricProduction_L', null, 37, 1, 'system', 'system', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily water production(m^3/d)', '?????????? ???????? ?????????????????? ????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138749', null, '0d4297cb4db44bb3a9a3d5d983007039', '有功功率(kW)', 'averageWatt', null, 38, 0, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'Average active power(kW)', '??????? ???????? ????????(kW)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138750', null, '0d4297cb4db44bb3a9a3d5d983007039', '光杆功率(kW)', 'polishrodPower', null, 39, 0, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'Polishing rod power(kW)', '???????? ????????? ??????(kW)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138751', null, '0d4297cb4db44bb3a9a3d5d983007039', '水功率(kW)', 'waterPower', null, 40, 0, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'Water power(kW)', '????????????(kW)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138752', null, '0d4297cb4db44bb3a9a3d5d983007039', '地面效率(%)', 'surfaceSystemEfficiency', null, 41, 0, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'Ground efficiency(%)', '????????????? ?????? ?? ??????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138753', null, '0d4297cb4db44bb3a9a3d5d983007039', '井下效率(%)', 'welldownSystemEfficiency', null, 42, 0, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'Downhole efficiency(%)', '???????????????? ?????????????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138754', null, '0d4297cb4db44bb3a9a3d5d983007039', '系?效率(%)', 'systemEfficiency', null, 43, 0, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'System efficiency(%)', '????????????? ???????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138755', null, '0d4297cb4db44bb3a9a3d5d983007039', '吨液百米耗?量(kW· h/100m· t)', 'energyper100mlift', null, 44, 0, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'One ton of liquid consumes electricity per 100 meters lifted(kW· h/100m· t)', '???? ????? ???????? ?????????? ?????????????? ?? ?????? 100 ???????? ??????(kW· h/100m· t)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138756', null, '0d4297cb4db44bb3a9a3d5d983007039', '?泵效(%)', 'pumpEff', null, 45, 0, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'Pump efficiency(%)', '????????????? ??????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139724', null, '0d4297cb4db44bb3a9a3d5d983007039', '上?程最大?流(A)', 'upStrokeIMax', null, 46, 1, 'system', 'system', to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), 'The maximum current of the upstroke(A)', '???????????? ??? ??????????? ????(A)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139725', null, '0d4297cb4db44bb3a9a3d5d983007039', '下?程最大?流(A)', 'downStrokeIMax', null, 47, 1, 'system', 'system', to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), 'The maximum current of the downstroke(A)', '???????????? ??? ??????????? ????(A)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138757', null, '0d4297cb4db44bb3a9a3d5d983007039', '?流平衡度(%)', 'iDegreeBalance', null, 48, 1, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'Current balance(%)', '??????? ??????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139726', null, '0d4297cb4db44bb3a9a3d5d983007039', '上?程最大功率(kW)', 'upStrokeWattMax', null, 49, 1, 'system', 'system', to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), 'Maximum power on the upstroke(kW)', '???????????? ???????? ?? ?????????? ????(kW)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139727', null, '0d4297cb4db44bb3a9a3d5d983007039', '下?程最大功率(kW)', 'downStrokeWattMax', null, 50, 1, 'system', 'system', to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2023 17:13:49', 'dd-mm-yyyy hh24:mi:ss'), 'Maximum power of the downstroke(kW)', '???????????? ???????? ???? ????(kW)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138758', null, '0d4297cb4db44bb3a9a3d5d983007039', '功率平衡度(%)', 'wattDegreeBalance', null, 51, 1, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'Power balance(%)', '?????? ????????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138759', null, '0d4297cb4db44bb3a9a3d5d983007039', '移?距离(cm)', 'deltaradius', null, 52, 1, 'system', 'system', to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-05-2022 17:31:45', 'dd-mm-yyyy hh24:mi:ss'), 'Travel distance(cm)', '?????????? ? ????(cm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138760', null, '0d4297cb4db44bb3a9a3d5d983007039', '日用?量(kW·h)', 'todayKWattH', null, 53, 0, 'system', 'system', to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2022 15:33:33', 'dd-mm-yyyy hh24:mi:ss'), 'Daily electricity consumption(kW·h)', '???????? ??????????? ??????????????(kW·h)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119852', null, 'cdd198534d5849b7a27054e0f2593ff3', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119853', null, 'cdd198534d5849b7a27054e0f2593ff3', '??名?', 'deviceName', 'flex:2', 2, 1, 'system', 'system', to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:43', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119855', null, 'cdd198534d5849b7a27054e0f2593ff3', '?警??', 'alarmTime', 'flex:3', 3, 1, 'system', 'system', to_date('05-01-2022 14:16:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:37', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm time', '????? ???????????? ??????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('121867', null, 'cdd198534d5849b7a27054e0f2593ff3', '?警?', 'itemName', 'flex:2', 4, 1, 'system', 'system', to_date('05-01-2022 14:16:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:46', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm items', '????????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119856', null, 'cdd198534d5849b7a27054e0f2593ff3', '?警信息', 'alarmInfo', 'flex:2', 5, 1, 'system', 'system', to_date('05-01-2022 14:16:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:16:50', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm information', '?????????? ? ???????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119857', null, 'cdd198534d5849b7a27054e0f2593ff3', '?警??', 'alarmLevelName', 'flex:2', 6, 1, 'system', 'system', to_date('05-01-2022 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm Level', '??????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119858', null, 'cdd198534d5849b7a27054e0f2593ff3', '恢复??', 'recoveryTime', 'flex:3', 7, 0, 'system', 'system', to_date('05-01-2022 14:17:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:00', 'dd-mm-yyyy hh24:mi:ss'), 'Recovery time', '????? ??????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119859', null, 'e2924366ab174d4b9a096be969934985', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119860', null, 'e2924366ab174d4b9a096be969934985', '??名?', 'deviceName', 'flex:2', 2, 1, 'system', 'system', to_date('05-03-2024 18:06:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:06:52', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119862', null, 'e2924366ab174d4b9a096be969934985', '?警??', 'alarmTime', 'flex:3', 3, 1, 'system', 'system', to_date('05-01-2022 14:17:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:48', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm time', '????? ???????????? ??????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119866', null, 'e2924366ab174d4b9a096be969934985', '?警?', 'itemName', 'flex:2', 4, 1, 'system', 'system', to_date('05-01-2022 14:17:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:55', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm items', '????????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119863', null, 'e2924366ab174d4b9a096be969934985', '?警信息', 'alarmInfo', 'flex:2', 5, 1, 'system', 'system', to_date('05-01-2022 14:17:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:17:58', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm information', '?????????? ? ???????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119867', null, 'e2924366ab174d4b9a096be969934985', '?警值', 'alarmValue', 'flex:2', 6, 1, 'system', 'system', to_date('05-01-2022 14:18:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:01', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm value', '???????? ??????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119868', null, 'e2924366ab174d4b9a096be969934985', '?警限值', 'alarmLimit', 'flex:2', 7, 1, 'system', 'system', to_date('05-01-2022 14:18:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:04', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm limits', '?????????? ???????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119869', null, 'e2924366ab174d4b9a096be969934985', '回差', 'hystersis', 'flex:2', 8, 1, 'system', 'system', to_date('05-01-2022 14:18:08', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:08', 'dd-mm-yyyy hh24:mi:ss'), 'Return', '??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119864', null, 'e2924366ab174d4b9a096be969934985', '?警??', 'alarmLevelName', 'flex:2', 9, 1, 'system', 'system', to_date('05-01-2022 14:18:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:13', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm Level', '??????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119865', null, 'e2924366ab174d4b9a096be969934985', '恢复??', 'recoveryTime', 'flex:3', 10, 0, 'system', 'system', to_date('05-01-2022 14:18:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:20', 'dd-mm-yyyy hh24:mi:ss'), 'Recovery time', '????? ??????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120115', null, 'b09082f4272e4768994db398e14bc3f2', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120116', null, 'b09082f4272e4768994db398e14bc3f2', '??名?', 'deviceName', 'flex:2', 2, 1, 'system', 'system', to_date('05-03-2024 18:07:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:07:03', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120118', null, 'b09082f4272e4768994db398e14bc3f2', '?警??', 'alarmTime', 'flex:3', 3, 1, 'system', 'system', to_date('05-01-2022 14:18:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:18:58', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm time', '????? ???????????? ??????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('121868', null, 'b09082f4272e4768994db398e14bc3f2', '?警?', 'itemName', 'flex:2', 4, 1, 'system', 'system', to_date('05-01-2022 14:19:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:04', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm items', '????????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120119', null, 'b09082f4272e4768994db398e14bc3f2', '?警信息', 'alarmInfo', 'flex:2', 5, 1, 'system', 'system', to_date('05-01-2022 14:19:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:07', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm information', '?????????? ? ???????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120120', null, 'b09082f4272e4768994db398e14bc3f2', '?警??', 'alarmLevelName', 'flex:2', 6, 1, 'system', 'system', to_date('05-01-2022 14:19:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:10', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm Level', '??????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('120121', null, 'b09082f4272e4768994db398e14bc3f2', '恢复??', 'recoveryTime', 'flex:3', 7, 0, 'system', 'system', to_date('05-01-2022 14:19:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:16', 'dd-mm-yyyy hh24:mi:ss'), 'Recovery time', '????? ??????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119870', null, 'b71c1a2c9d574fe482080a56c7c780a9', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119871', null, 'b71c1a2c9d574fe482080a56c7c780a9', '??名?', 'deviceName', 'flex:2', 2, 1, 'system', 'system', to_date('05-03-2024 18:07:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-03-2024 18:07:20', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119873', null, 'b71c1a2c9d574fe482080a56c7c780a9', '?警??', 'alarmTime', 'flex:3', 3, 1, 'system', 'system', to_date('05-01-2022 14:19:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:31', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm time', '????? ???????????? ??????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('121869', null, 'b71c1a2c9d574fe482080a56c7c780a9', '?警?', 'itemName', 'flex:2', 4, 1, 'system', 'system', to_date('05-01-2022 14:19:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:35', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm items', '????????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119874', null, 'b71c1a2c9d574fe482080a56c7c780a9', '?警信息', 'alarmInfo', 'flex:2', 5, 1, 'system', 'system', to_date('05-01-2022 14:19:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:38', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm information', '?????????? ? ???????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119875', null, 'b71c1a2c9d574fe482080a56c7c780a9', '?警??', 'alarmLevelName', 'flex:2', 6, 1, 'system', 'system', to_date('05-01-2022 14:19:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:43', 'dd-mm-yyyy hh24:mi:ss'), 'Alarm Level', '??????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119876', null, 'b71c1a2c9d574fe482080a56c7c780a9', '恢复??', 'recoveryTime', 'flex:3', 7, 0, 'system', 'system', to_date('05-01-2022 14:19:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 14:19:51', 'dd-mm-yyyy hh24:mi:ss'), 'Recovery time', '????? ??????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119371', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119372', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '??名?', 'deviceName', 'flex:3', 2, 1, 'system', 'system', to_date('06-03-2024 10:47:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 10:47:29', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119373', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '???型', 'deviceTypeName', 'flex:2', 3, 1, 'system', 'system', to_date('05-01-2022 10:41:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:35', 'dd-mm-yyyy hh24:mi:ss'), 'Device type', '??? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119375', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '登?IP', 'loginIp', 'flex:3', 4, 1, 'system', 'system', to_date('05-01-2022 10:41:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:22', 'dd-mm-yyyy hh24:mi:ss'), 'Login IP address', 'IP-????? ??? ?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119374', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作用?', 'user_id', 'flex:2', 5, 1, 'system', 'system', to_date('05-01-2022 10:41:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:29', 'dd-mm-yyyy hh24:mi:ss'), 'The user who acted on it', '????????????, ??????? ?????????? ?? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119376', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作?容', 'actionName', 'flex:2', 6, 1, 'system', 'system', to_date('05-01-2022 10:41:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:16', 'dd-mm-yyyy hh24:mi:ss'), 'What to do', '??? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119378', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '操作??', 'to_char(createTime@''yyyy-mm-dd hh24:mi:ss'') as createTime', 'flex:5', 7, 1, 'system', 'system', to_date('05-01-2022 10:41:08', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:41:08', 'dd-mm-yyyy hh24:mi:ss'), 'Operating time', '?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119377', null, 'ad646d19fcaa4fbd9077dbf7a826b107', '?注', 'remark', 'flex:10', 8, 1, 'system', 'system', to_date('05-01-2022 10:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:40:51', 'dd-mm-yyyy hh24:mi:ss'), 'Remark', '?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119379', null, '167aeb3aca384afda8655d63aedee484', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-08-2021 18:20:01', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119381', null, '167aeb3aca384afda8655d63aedee484', '登?IP', 'loginIp', 'flex:1', 2, 1, 'system', 'system', to_date('05-01-2022 10:43:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:19', 'dd-mm-yyyy hh24:mi:ss'), 'Login IP address', 'IP-????? ??? ?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119380', null, '167aeb3aca384afda8655d63aedee484', '操作用?', 'user_id', 'flex:1', 3, 1, 'system', 'system', to_date('05-01-2022 10:43:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:15', 'dd-mm-yyyy hh24:mi:ss'), 'The user who acted on it', '????????????, ??????? ?????????? ?? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119382', null, '167aeb3aca384afda8655d63aedee484', '操作?容', 'actionName', 'flex:1', 4, 1, 'system', 'system', to_date('05-01-2022 10:43:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:22', 'dd-mm-yyyy hh24:mi:ss'), 'What to do', '??? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119384', null, '167aeb3aca384afda8655d63aedee484', '操作??', 'to_char(createTime@''yyyy-mm-dd hh24:mi:ss'') as createTime', 'flex:1', 5, 1, 'system', 'system', to_date('05-01-2022 10:43:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:26', 'dd-mm-yyyy hh24:mi:ss'), 'Operating time', '?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119383', null, '167aeb3aca384afda8655d63aedee484', '?注', 'remark', 'flex:1', 6, 1, 'system', 'system', to_date('05-01-2022 10:43:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2022 10:43:30', 'dd-mm-yyyy hh24:mi:ss'), 'Remark', '?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138676', null, '436802a1c0074a79aafd00ce539166f4', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138677', null, '436802a1c0074a79aafd00ce539166f4', '??名?', 'deviceName', null, 2, 1, 'system', 'system', to_date('06-03-2024 15:51:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:11', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138678', null, '436802a1c0074a79aafd00ce539166f4', '功?采集??', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:150', 3, 1, 'system', 'system', to_date('13-06-2018 15:27:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-06-2018 15:27:50', 'dd-mm-yyyy hh24:mi:ss'), 'The collection time of the dynamogram', '????? ????? ????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138679', null, '436802a1c0074a79aafd00ce539166f4', '?算??', 'resultStatus', null, 4, 1, 'system', 'system', to_date('30-11-2018 10:21:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:04', 'dd-mm-yyyy hh24:mi:ss'), 'Calculation results', '?????????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138680', null, '436802a1c0074a79aafd00ce539166f4', '功?工?', 'resultName', 'width:130', 5, 1, 'system', 'system', to_date('30-11-2018 18:11:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 18:11:49', 'dd-mm-yyyy hh24:mi:ss'), 'Working conditions', '??????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138681', null, '436802a1c0074a79aafd00ce539166f4', '瞬??液量(t/d)', 'liquidWeightProduction', null, 6, 0, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time liquid production(t/d)', '???????????? ????????? ? ?????? ????????? ???????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138682', null, '436802a1c0074a79aafd00ce539166f4', '瞬??油量(t/d)', 'oilWeightProduction', null, 7, 0, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time oil production(t/d)', '?????? ????? ? ?????? ????????? ???????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145273', null, '436802a1c0074a79aafd00ce539166f4', '瞬??水量(t/d)', 'waterWeightProduction', null, 8, 0, 'system', 'system', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time water production(t/d)', '?????? ???? ? ?????? ????????? ???????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138683', null, '436802a1c0074a79aafd00ce539166f4', '瞬??液量(m^3/d)', 'liquidVolumetricProduction', null, 9, 1, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time liquid production(m^3/d)', '???????????? ????????? ? ?????? ????????? ???????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138684', null, '436802a1c0074a79aafd00ce539166f4', '瞬??油量(m^3/d)', 'oilVolumetricProduction', null, 10, 1, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time oil production(m^3/d)', '?????? ????? ? ?????? ????????? ???????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145274', null, '436802a1c0074a79aafd00ce539166f4', '瞬??水量(m^3/d)', 'waterVolumetricProduction', null, 11, 1, 'system', 'system', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time water production(m^3/d)', '?????? ???? ? ?????? ????????? ???????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138685', null, '436802a1c0074a79aafd00ce539166f4', '原油密度(g/cm^3)', 'crudeoilDensity', null, 12, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Crude oil density(g/cm^3)', '????????? ????? ?????(g/cm^3)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138686', null, '436802a1c0074a79aafd00ce539166f4', '水密度(g/cm^3)', 'waterDensity', null, 13, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Water density(g/cm^3)', '????????? ????(g/cm^3)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138687', null, '436802a1c0074a79aafd00ce539166f4', '天然气相?密度', 'naturalGasRelativeDensity', null, 14, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Relative density of natural gas', '????????????? ????????? ?????????? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138688', null, '436802a1c0074a79aafd00ce539166f4', '?和?力(MPa)', 'saturationPressure', null, 15, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Saturation pressure(MPa)', '???????? ?????????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138689', null, '436802a1c0074a79aafd00ce539166f4', '油?中部深度(m)', 'reservoirDepth', null, 16, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'The depth of the oil layer(m)', '??????? ????????? ????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138690', null, '436802a1c0074a79aafd00ce539166f4', '油?中部?度(℃)', 'reservoirTemperature', null, 17, 1, 'system', 'system', to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-05-2020 16:17:59', 'dd-mm-yyyy hh24:mi:ss'), 'The temperature of the oil layer(℃)', '??????????? ????????? ????(℃)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138691', null, '436802a1c0074a79aafd00ce539166f4', '油?(MPa)', 'tubingPressure', null, 18, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Tubing pressure(MPa)', '???????? ? ???????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138692', null, '436802a1c0074a79aafd00ce539166f4', '套?(MPa)', 'casingPressure', null, 19, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Casing pressure(MPa)', '???????? ? ???????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138693', null, '436802a1c0074a79aafd00ce539166f4', '井口?度(℃)', 'wellHeadFluidTemperature', null, 20, 1, 'system', 'system', to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), 'The temperature at the wellhead(℃)', '??????????? ?? ????? ????????(℃)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138694', null, '436802a1c0074a79aafd00ce539166f4', '含水率(%)', 'weightWaterCut', null, 21, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Water Cut(%)', '??????????????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138695', null, '436802a1c0074a79aafd00ce539166f4', '生?气油比(m^3/t)', 'productionGasOilRatio', 'width:120', 22, 1, 'system', 'system', to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), 'The ratio of gas to oil(m^3/t)', '??????????? ???? ? ?????(m^3/t)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138696', null, '436802a1c0074a79aafd00ce539166f4', '?液面(m)', 'producingFluidLevel', null, 23, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Production of liquid surfaces(m)', '???????????? ?????? ????????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138697', null, '436802a1c0074a79aafd00ce539166f4', '泵挂(m)', 'pumpSettingDepth', null, 24, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Pump depth(m)', '??????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138699', null, '436802a1c0074a79aafd00ce539166f4', '泵筒?型', 'barrelTypeName', null, 26, 1, 'system', 'system', to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-04-2021 19:22:11', 'dd-mm-yyyy hh24:mi:ss'), 'Cartridge type', '??? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138700', null, '436802a1c0074a79aafd00ce539166f4', '泵??', 'pumpGrade', null, 27, 1, 'system', 'system', to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), 'Pump level', '??????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138701', null, '436802a1c0074a79aafd00ce539166f4', '泵?(mm)', 'pumpboreDiameter', null, 28, 1, 'system', 'system', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), 'Barrel diameter(mm)', '??????? ?????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138702', null, '436802a1c0074a79aafd00ce539166f4', '柱塞?(m)', 'plungerLength', null, 29, 1, 'system', 'system', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), 'Plunger length(m)', '????? ????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138703', null, '436802a1c0074a79aafd00ce539166f4', '油管??(mm)', 'tubingStringInsideDiameter', null, 30, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Tubing inner diameter(mm)', '?????????? ??????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138704', null, '436802a1c0074a79aafd00ce539166f4', '套管??(mm)', 'casingStringInsideDiameter', 'width:120', 31, 1, 'system', 'system', to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), 'Casing inner diameter(mm)', '?????????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147197', null, '436802a1c0074a79aafd00ce539166f4', '一?杆?型', 'rodTypeName1', null, 32, 1, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'The type of first-stage rod', '??? ?????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138705', null, '436802a1c0074a79aafd00ce539166f4', '一?杆??', 'rodGrade1', null, 33, 1, 'system', 'system', to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the first level of the rod', '??????? ??????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138706', null, '436802a1c0074a79aafd00ce539166f4', '一?杆外?(mm)', 'rodOutsideDiameter1', null, 34, 1, 'system', 'system', to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the first stage rod(mm)', '???????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138707', null, '436802a1c0074a79aafd00ce539166f4', '一?杆??(mm)', 'rodInsideDiameter1', null, 35, 1, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the first stage rod(mm)', '?????????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138708', null, '436802a1c0074a79aafd00ce539166f4', '一?杆?度(m)', 'rodLength1', null, 36, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the first stage rod(m)', '????? ?????? ?????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147198', null, '436802a1c0074a79aafd00ce539166f4', '二?杆?型', 'rodTypeName2', null, 37, 1, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Type of second-stage rod', '??? ?????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138709', null, '436802a1c0074a79aafd00ce539166f4', '二?杆??', 'rodGrade2', null, 38, 1, 'system', 'system', to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the second level of the pole', '??????? ??????? ?????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138710', null, '436802a1c0074a79aafd00ce539166f4', '二?杆外?(mm)', 'rodOutsideDiameter2', null, 39, 1, 'system', 'system', to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the second stage rod(mm)', '???????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138711', null, '436802a1c0074a79aafd00ce539166f4', '二?杆??(mm)', 'rodInsideDiameter2', null, 40, 1, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the second stage rod(mm)', '?????????? ??????? ?????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138712', null, '436802a1c0074a79aafd00ce539166f4', '二?杆?度(m)', 'rodLength2', null, 41, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the second stage rod(m)', '????? ?????? ?????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147199', null, '436802a1c0074a79aafd00ce539166f4', '三?杆?型', 'rodTypeName3', null, 42, 1, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Type of tertiary rod', '??? ?????????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138713', null, '436802a1c0074a79aafd00ce539166f4', '三?杆??', 'rodGrade3', null, 43, 1, 'system', 'system', to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the third pole', '??????? ???????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138714', null, '436802a1c0074a79aafd00ce539166f4', '三?杆外?(mm)', 'rodOutsideDiameter3', null, 44, 1, 'system', 'system', to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the third stage rod(mm)', '???????? ??????? ??????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138715', null, '436802a1c0074a79aafd00ce539166f4', '三?杆??(mm)', 'rodInsideDiameter3', null, 45, 1, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the third-stage rod(mm)', '?????????? ??????? ??????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138716', null, '436802a1c0074a79aafd00ce539166f4', '三?杆?度(m)', 'rodLength3', null, 46, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the third-stage rod(m)', '????? ?????? ??????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147200', null, '436802a1c0074a79aafd00ce539166f4', '四?杆?型', 'rodTypeName4', null, 47, 1, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Type of fourth-level rod', '??? ?????? ?????????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138717', null, '436802a1c0074a79aafd00ce539166f4', '四?杆??', 'rodGrade4', null, 48, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the fourth level of the pole', '??????? ?????????? ?????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138718', null, '436802a1c0074a79aafd00ce539166f4', '四?杆外?(mm)', 'rodOutsideDiameter4', null, 49, 1, 'system', 'system', to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the fourth-stage rod(mm)', '???????? ??????? ??????? ????????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138719', null, '436802a1c0074a79aafd00ce539166f4', '四?杆??(mm)', 'rodInsideDiameter4', null, 50, 1, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the fourth-level rod(mm)', '?????????? ??????? ??????? ?????????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138720', null, '436802a1c0074a79aafd00ce539166f4', '四?杆?度(m)', 'rodLength4', null, 51, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the fourth-level rod(m)', '????? ??????? ?????????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138721', null, '436802a1c0074a79aafd00ce539166f4', '?定??', 'anchoringStateName', null, 52, 1, 'system', 'system', to_date('23-02-2019 14:46:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:46:06', 'dd-mm-yyyy hh24:mi:ss'), 'Anchoring State', '?定??');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138722', null, '436802a1c0074a79aafd00ce539166f4', '工?干?', 'manualInterventionResult', null, 53, 1, 'system', 'system', to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-10-2022 15:47:27', 'dd-mm-yyyy hh24:mi:ss'), 'Condition intervention', '????????????? ? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138723', null, '436802a1c0074a79aafd00ce539166f4', '?毛比(小?)', 'netGrossRatio', null, 54, 1, 'system', 'system', to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), 'Net Gross Ratio (Decimal)', '??????????? ??????? ?????? (??????????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138724', null, '436802a1c0074a79aafd00ce539166f4', '?毛值(m^3/d)', 'netGrossValue', null, 55, 1, 'system', 'system', to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:20:58', 'dd-mm-yyyy hh24:mi:ss'), 'Net gross value(m^3/d)', '?????? ??????? ?????????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('138761', null, '436802a1c0074a79aafd00ce539166f4', '反演液面校正值(MPa)', 'levelCorrectValue', null, 56, 1, 'system', 'system', to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-05-2023 16:25:10', 'dd-mm-yyyy hh24:mi:ss'), 'Level correction value(MPa)', '???????? ????????? ??????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139255', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139256', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '??名?', 'deviceName', null, 2, 1, 'system', 'system', to_date('06-03-2024 15:51:44', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:44', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139257', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日期', 'calDate', null, 3, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'Date', '????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139258', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '功?工?', 'resultName', null, 4, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'Working conditions', '??????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139259', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累??液量(t/d)', 'liquidWeightProduction', null, 5, 0, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily fluid production(t/d)', '?????????? ???????? ???????????? ????????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139260', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累??油量(t/d)', 'oilWeightProduction', null, 6, 0, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily oil production(t/d)', '?????????? ???????? ?????? ?????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145277', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累??水量(t/d)', 'waterWeightProduction', null, 7, 0, 'system', 'system', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily water production(t/d)', '?????????? ???????? ?????????????????? ????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139261', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累??液量(m^3/d)', 'liquidVolumetricProduction', null, 8, 1, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily fluid production(m^3/d)', '?????????? ???????? ???????????? ????????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139262', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累??油量(m^3/d)', 'oilVolumetricProduction', null, 9, 1, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily oil production(m^3/d)', '?????????? ???????? ?????? ?????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145278', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日累??水量(m^3/d)', 'waterVolumetricProduction', null, 10, 1, 'system', 'system', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily water production(m^3/d)', '?????????? ???????? ?????????????????? ????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139263', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '泵效(%)', 'pumpEff', null, 11, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'Pump efficiency(%)', '????????????? ??????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139264', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '系?效率(%)', 'systemEfficiency', null, 12, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'System efficiency(%)', '????????????? ???????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139265', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '功率平衡度(%)', 'wattDegreeBalance', 'width:120', 13, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'Power balance(%)', '?????? ????????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139266', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '?流平衡度(%)', 'iDegreeBalance', 'width:120', 14, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'Current balance(%)', '??????? ??????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139267', null, 'cf1c0981f31242f9b3e84810bdc0a19f', '日用?量(kw·h)', 'todayKWattH', 'width:120', 15, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'Daily electricity consumption(kw·h)', '???????? ??????????? ??????????????(kw·h)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139268', null, 'aad8b76fdaf84a1194de5ec0a4453631', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139269', null, 'aad8b76fdaf84a1194de5ec0a4453631', '??名?', 'deviceName', null, 2, 1, 'system', 'system', to_date('06-03-2024 15:51:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:29', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139270', null, 'aad8b76fdaf84a1194de5ec0a4453631', '采集??', 'to_char(acqTime@''yyyy-mm-dd hh24:mi:ss'') as acqTime', 'width:150', 3, 1, 'system', 'system', to_date('29-11-2018 18:48:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-11-2018 18:48:00', 'dd-mm-yyyy hh24:mi:ss'), 'Time', '?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139271', null, 'aad8b76fdaf84a1194de5ec0a4453631', '?算??', 'resultStatus', null, 4, 1, 'system', 'system', to_date('30-11-2018 10:21:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:36', 'dd-mm-yyyy hh24:mi:ss'), 'Calculation results', '?????????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139272', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬??液量(t/d)', 'liquidWeightProduction', null, 5, 0, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time liquid production(t/d)', '???????????? ????????? ? ?????? ????????? ???????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139275', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬??油量(t/d)', 'oilWeightProduction', null, 6, 0, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time oil production(t/d)', '?????? ????? ? ?????? ????????? ???????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145275', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬??水量(t/d)', 'waterWeightProduction', null, 7, 0, 'system', 'system', to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:41', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time water production(t/d)', '?????? ???? ? ?????? ????????? ???????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139273', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬??液量(m^3/d)', 'liquidVolumetricProduction', null, 8, 1, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time liquid production(m^3/d)', '???????????? ????????? ? ?????? ????????? ???????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139274', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬??油量(m^3/d)', 'oilVolumetricProduction', null, 9, 1, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time oil production(m^3/d)', '?????? ????? ? ?????? ????????? ???????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145276', null, 'aad8b76fdaf84a1194de5ec0a4453631', '瞬??水量(m^3/d)', 'waterVolumetricProduction', null, 10, 1, 'system', 'system', to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-05-2020 17:37:53', 'dd-mm-yyyy hh24:mi:ss'), 'Real-time water production(m^3/d)', '?????? ???? ? ?????? ????????? ???????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139276', null, 'aad8b76fdaf84a1194de5ec0a4453631', '?速(r/min)', 'rpm', null, 11, 1, 'system', 'system', to_date('30-11-2018 17:32:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 17:32:53', 'dd-mm-yyyy hh24:mi:ss'), 'Rotation speed(r/min)', '???????? ????????(r/min)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139277', null, 'aad8b76fdaf84a1194de5ec0a4453631', '原油密度(g/cm^3)', 'crudeoilDensity', null, 12, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Crude oil density(g/cm^3)', '????????? ????? ?????(g/cm^3)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139278', null, 'aad8b76fdaf84a1194de5ec0a4453631', '水密度(g/cm^3)', 'waterDensity', null, 13, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Water density(g/cm^3)', '????????? ????(g/cm^3)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139279', null, 'aad8b76fdaf84a1194de5ec0a4453631', '天然气相?密度', 'naturalGasRelativeDensity', null, 14, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Relative density of natural gas', '????????????? ????????? ?????????? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139280', null, 'aad8b76fdaf84a1194de5ec0a4453631', '?和?力(MPa)', 'saturationPressure', null, 15, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'Saturation pressure(MPa)', '???????? ?????????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139281', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油?中部深度(m)', 'reservoirDepth', null, 16, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'The depth of the oil layer(m)', '??????? ????????? ????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139282', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油?中部?度(℃)', 'reservoirTemperature', null, 17, 1, 'system', 'system', to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-02-2019 14:26:46', 'dd-mm-yyyy hh24:mi:ss'), 'The temperature of the oil layer(℃)', '??????????? ????????? ????(℃)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139283', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油?(MPa)', 'tubingPressure', null, 18, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Tubing pressure(MPa)', '???????? ? ???????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139284', null, 'aad8b76fdaf84a1194de5ec0a4453631', '套?(MPa)', 'casingPressure', null, 19, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Casing pressure(MPa)', '???????? ? ???????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139285', null, 'aad8b76fdaf84a1194de5ec0a4453631', '井口?度(℃)', 'wellHeadFluidTemperature', null, 20, 1, 'system', 'system', to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 10:19:28', 'dd-mm-yyyy hh24:mi:ss'), 'The temperature at the wellhead(℃)', '??????????? ?? ????? ????????(℃)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139286', null, 'aad8b76fdaf84a1194de5ec0a4453631', '含水率(%)', 'weightWaterCut', null, 21, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Water Cut(%)', '??????????????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139287', null, 'aad8b76fdaf84a1194de5ec0a4453631', '生?气油比(m^3/t)', 'productionGasOilRatio', 'width:120', 22, 1, 'system', 'system', to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:09', 'dd-mm-yyyy hh24:mi:ss'), 'The ratio of gas to oil(m^3/t)', '??????????? ???? ? ?????(m^3/t)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139288', null, 'aad8b76fdaf84a1194de5ec0a4453631', '?液面(m)', 'producingFluidLevel', null, 23, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Production of liquid surfaces(m)', '???????????? ?????? ????????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139289', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵挂(m)', 'pumpSettingDepth', null, 24, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Pump depth(m)', '??????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139290', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵筒?(m)', 'barrelLength', null, 25, 1, 'system', 'system', to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-06-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), 'Barrel length(m)', '????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139291', null, 'aad8b76fdaf84a1194de5ec0a4453631', '泵??', 'barrelSeries', null, 26, 1, 'system', 'system', to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-11-2014 13:50:05', 'dd-mm-yyyy hh24:mi:ss'), 'Number of pump stages', '?????????? ???????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139292', null, 'aad8b76fdaf84a1194de5ec0a4453631', '?子直?(mm)', 'rotorDiameter', null, 27, 1, 'system', 'system', to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 14:10:33', 'dd-mm-yyyy hh24:mi:ss'), 'Rotor diameter(mm)', '??????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139293', null, 'aad8b76fdaf84a1194de5ec0a4453631', '公?排量(ml/?)', 'qpr', null, 28, 1, 'system', 'system', to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2018 15:18:27', 'dd-mm-yyyy hh24:mi:ss'), 'Nominal displacement(ml/r)', '??????????? ??????? ?????(ml/r)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139294', null, 'aad8b76fdaf84a1194de5ec0a4453631', '油管??(mm)', 'tubingStringInsideDiameter', null, 29, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Tubing inner diameter(mm)', '?????????? ??????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139295', null, 'aad8b76fdaf84a1194de5ec0a4453631', '套管??(mm)', 'casingStringInsideDiameter', 'width:120', 30, 1, 'system', 'system', to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2014 14:59:15', 'dd-mm-yyyy hh24:mi:ss'), 'Casing inner diameter(mm)', '?????????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147201', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一?杆?型', 'rodTypeName1', null, 31, 1, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'The type of first-stage rod', '??? ?????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139296', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一?杆??', 'rodGrade1', null, 32, 1, 'system', 'system', to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 10:59:52', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the first level of the rod', '??????? ??????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139297', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一?杆外?(mm)', 'rodOutsideDiameter1', null, 33, 1, 'system', 'system', to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:23', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the first stage rod(mm)', '???????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139298', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一?杆??(mm)', 'rodInsideDiameter1', null, 34, 1, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the first stage rod(mm)', '?????????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139299', null, 'aad8b76fdaf84a1194de5ec0a4453631', '一?杆?度(m)', 'rodLength1', null, 35, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the first stage rod(m)', '????? ?????? ?????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147202', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二?杆?型', 'rodTypeName2', null, 36, 1, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Type of second-stage rod', '??? ?????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139300', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二?杆??', 'rodGrade2', null, 37, 1, 'system', 'system', to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:14', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the second level of the pole', '??????? ??????? ?????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139301', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二?杆外?(mm)', 'rodOutsideDiameter2', null, 38, 1, 'system', 'system', to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:57:37', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the second stage rod(mm)', '???????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139302', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二?杆??(mm)', 'rodInsideDiameter2', null, 39, 1, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the second stage rod(mm)', '?????????? ??????? ?????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139303', null, 'aad8b76fdaf84a1194de5ec0a4453631', '二?杆?度(m)', 'rodGrade2', null, 40, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the second stage rod(m)', '????? ?????? ?????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147203', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三?杆?型', 'rodTypeName3', null, 41, 1, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Type of tertiary rod', '??? ?????????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139304', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三?杆??', 'rodGrade3', null, 42, 1, 'system', 'system', to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-08-2016 11:00:25', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the third pole', '??????? ???????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139305', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三?杆外?(mm)', 'rodOutsideDiameter3', null, 43, 1, 'system', 'system', to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:00', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the third stage rod(mm)', '???????? ??????? ??????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139306', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三?杆??(mm)', 'rodInsideDiameter3', null, 44, 1, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the third-stage rod(mm)', '?????????? ??????? ??????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139307', null, 'aad8b76fdaf84a1194de5ec0a4453631', '三?杆?度(m)', 'rodGrade3', null, 45, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the third-stage rod(m)', '????? ?????? ??????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147204', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四?杆?型', 'rodTypeName4', null, 46, 1, 'system', 'system', to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-03-2024 13:30:34', 'dd-mm-yyyy hh24:mi:ss'), 'Type of fourth-level rod', '??? ?????? ?????????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139308', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四?杆??', 'rodGrade4', null, 47, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the fourth level of the pole', '??????? ?????????? ?????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139309', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四?杆外?(mm)', 'rodOutsideDiameter4', null, 48, 1, 'system', 'system', to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2018 14:58:11', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the fourth-stage rod(mm)', '???????? ??????? ??????? ????????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139310', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四?杆??(mm)', 'rodInsideDiameter4', null, 49, 1, 'system', 'system', to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-06-2018 14:31:51', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the fourth-level rod(mm)', '?????????? ??????? ??????? ?????????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139311', null, 'aad8b76fdaf84a1194de5ec0a4453631', '四?杆?度(m)', 'rodGrade4', null, 50, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the fourth-level rod(m)', '????? ??????? ?????????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139312', null, 'aad8b76fdaf84a1194de5ec0a4453631', '?毛比(小?)', 'netGrossRatio', null, 52, 1, 'system', 'system', to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), 'Net Gross Ratio (Decimal)', '??????????? ??????? ?????? (??????????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139313', null, 'aad8b76fdaf84a1194de5ec0a4453631', '?毛值(m^3/d)', 'netGrossValue', null, 53, 1, 'system', 'system', to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 10:21:30', 'dd-mm-yyyy hh24:mi:ss'), 'Net gross value(m^3/d)', '?????? ??????? ?????????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139314', null, '8122b170c0ca4deb87159c931ab251f3', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2018 08:53:30', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139315', null, '8122b170c0ca4deb87159c931ab251f3', '??名?', 'deviceName', null, 2, 1, 'system', 'system', to_date('06-03-2024 15:51:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-03-2024 15:51:59', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139316', null, '8122b170c0ca4deb87159c931ab251f3', '日期', 'calDate', null, 3, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'Date', '????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139317', null, '8122b170c0ca4deb87159c931ab251f3', '日累??液量(t/d)', 'liquidWeightProduction', null, 4, 0, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily fluid production(t/d)', '?????????? ???????? ???????????? ????????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139318', null, '8122b170c0ca4deb87159c931ab251f3', '日累??油量(t/d)', 'oilWeightProduction', null, 5, 0, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily oil production(t/d)', '?????????? ???????? ?????? ?????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145279', null, '8122b170c0ca4deb87159c931ab251f3', '日累??水量(t/d)', 'waterWeightProduction', null, 6, 0, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily water production(t/d)', '?????????? ???????? ?????????????????? ????(t/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139319', null, '8122b170c0ca4deb87159c931ab251f3', '日累??液量(m^3/d)', 'liquidVolumetricProduction', null, 7, 1, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily fluid production(m^3/d)', '?????????? ???????? ???????????? ????????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139320', null, '8122b170c0ca4deb87159c931ab251f3', '日累??油量(m^3/d)', 'oilVolumetricProduction', null, 8, 1, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily oil production(m^3/d)', '?????????? ???????? ?????? ?????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145280', null, '8122b170c0ca4deb87159c931ab251f3', '日累??水量(m^3/d)', 'waterVolumetricProduction', null, 9, 1, 'system', 'system', to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-11-2018 16:32:20', 'dd-mm-yyyy hh24:mi:ss'), 'Cumulative daily water production(m^3/d)', '?????????? ???????? ?????????????????? ????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139321', null, '8122b170c0ca4deb87159c931ab251f3', '泵效(%)', 'pumpEff', null, 10, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'Pump efficiency(%)', '????????????? ??????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139322', null, '8122b170c0ca4deb87159c931ab251f3', '系?效率(%)', 'systemEfficiency', null, 11, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'System efficiency(%)', '????????????? ???????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139323', null, '8122b170c0ca4deb87159c931ab251f3', '日用?量(kw·h)', 'todayKWattH', 'width:120', 12, 1, 'system', 'system', to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-05-2022 16:10:19', 'dd-mm-yyyy hh24:mi:ss'), 'Daily electricity consumption(kw·h)', '???????? ??????????? ??????????????(kw·h)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114832', null, '8ab792e089494533be910699d426c7d5', '?位名?', 'text', 'flex:3', 1, 1, 'system', 'system', to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 09:46:32', 'dd-mm-yyyy hh24:mi:ss'), 'The name of the organization', '???????????? ???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114834', null, '8ab792e089494533be910699d426c7d5', '排序??', 'orgSeq', 'flex:1', 2, 1, 'system', 'system', to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 11:09:36', 'dd-mm-yyyy hh24:mi:ss'), 'Sort', '???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114885', null, '5ba761c1383f498f9ac97c9a8ab6d847', '序?', 'id', 'width:50', 1, 1, 'system', 'system', null, null, 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114845', null, '5ba761c1383f498f9ac97c9a8ab6d847', '用?名?', 'userName', null, 2, 1, 'system', 'system', to_date('24-12-2021 13:39:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:27', 'dd-mm-yyyy hh24:mi:ss'), 'Username', '??? ????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114847', null, '5ba761c1383f498f9ac97c9a8ab6d847', '用???', 'userId', null, 3, 1, 'system', 'system', to_date('24-12-2021 13:39:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:31', 'dd-mm-yyyy hh24:mi:ss'), 'User Accounts', '??????? ?????? ?????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114849', null, '5ba761c1383f498f9ac97c9a8ab6d847', '角色', 'userTypeName', null, 4, 1, 'system', 'system', to_date('24-12-2021 13:39:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 13:39:34', 'dd-mm-yyyy hh24:mi:ss'), 'role', '????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114850', null, '5ba761c1383f498f9ac97c9a8ab6d847', '??', 'userPhone', null, 5, 1, 'system', 'system', to_date('24-12-2021 19:53:43', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:53:43', 'dd-mm-yyyy hh24:mi:ss'), 'Phone', '???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114851', null, '5ba761c1383f498f9ac97c9a8ab6d847', '?箱', 'userInEmail', null, 6, 1, 'system', 'system', to_date('24-12-2021 19:53:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:53:15', 'dd-mm-yyyy hh24:mi:ss'), 'mailbox', '???????? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('116092', null, '5ba761c1383f498f9ac97c9a8ab6d847', '快捷登?', 'userQuickLoginName', null, 7, 1, 'system', 'system', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), 'Quick login', '??????? ???? ? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128635', null, '5ba761c1383f498f9ac97c9a8ab6d847', '接收?警短信', 'receiveSMSName', null, 8, 1, 'system', 'system', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), 'Receive alert text messages', '????????? ??????????????? ????????? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128636', null, '5ba761c1383f498f9ac97c9a8ab6d847', '接收?警?件', 'receiveMailName', null, 9, 1, 'system', 'system', to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('24-12-2021 19:13:10', 'dd-mm-yyyy hh24:mi:ss'), 'Receive alert emails', '????????? ??????????? ?? ??????????? ?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128604', null, '5ba761c1383f498f9ac97c9a8ab6d847', '??', 'userEnableName', null, 10, 1, 'system', 'system', null, null, 'state', '???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128599', null, '5ba761c1383f498f9ac97c9a8ab6d847', '?建??', 'userRegtime', 'width:150', 11, 1, 'system', 'system', null, null, 'Creation time', '????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114886', null, '220c349e246e47a39a818023f1c97a63', '序?', 'id', 'width:50', 1, 1, 'system', 'system', null, null, 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114854', 'sys', '220c349e246e47a39a818023f1c97a63', '角色名?', 'roleName', 'flex:1', 2, 1, 'system', 'system', to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 09:59:26', 'dd-mm-yyyy hh24:mi:ss'), 'The name of the role', '??? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('128641', null, '220c349e246e47a39a818023f1c97a63', '角色等?', 'roleLevel', 'flex:1', 3, 1, 'system', 'system', to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-12-2021 11:00:21', 'dd-mm-yyyy hh24:mi:ss'), 'Role level', '??????? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('122399', null, '220c349e246e47a39a818023f1c97a63', '?据?示??', 'showLevel', 'flex:1', 4, 1, 'system', 'system', to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-11-2021 16:09:29', 'dd-mm-yyyy hh24:mi:ss'), 'The data display level', '??????? ??????????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114856', null, '220c349e246e47a39a818023f1c97a63', '角色描述', 'remark', 'flex:3', 7, 1, 'system', 'system', to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2021 14:17:05', 'dd-mm-yyyy hh24:mi:ss'), 'Role description', '???????? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114888', null, '87808f225d7240f68c2ab879347d818a', '序?', 'id', 'width:50', 1, 1, 'system', 'system', null, null, 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114867', 'sys', '87808f225d7240f68c2ab879347d818a', '??名?', 'deviceName', null, 2, 1, 'system', 'system', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150277', null, '87808f225d7240f68c2ab879347d818a', '?用?景', 'applicationScenariosName', null, 3, 1, 'system', 'system', to_date('29-11-2024 11:40:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-11-2024 11:40:42', 'dd-mm-yyyy hh24:mi:ss'), 'Application scenarios', '???????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('116688', null, '87808f225d7240f68c2ab879347d818a', '采控?例', 'instanceName', 'width:120', 4, 1, 'system', 'system', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'Acquisition and control instances', '??????? ????? ? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('133098', null, '87808f225d7240f68c2ab879347d818a', '?示?例', 'displayInstanceName', 'width:120', 5, 1, 'system', 'system', to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), 'Displays instances', '?????????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('136595', null, '87808f225d7240f68c2ab879347d818a', '?表?例', 'reportInstanceName', 'width:120', 6, 1, 'system', 'system', to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), 'Report instance', '????????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('119811', null, '87808f225d7240f68c2ab879347d818a', '?警?例', 'alarmInstanceName', 'width:120', 7, 1, 'system', 'system', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'Alert instance', '????????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135315', null, '87808f225d7240f68c2ab879347d818a', '下位机TCP?型', 'tcpType', null, 8, 1, 'system', 'system', to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), 'TCP type of the lower computer', '??? TCP ??????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114915', null, '87808f225d7240f68c2ab879347d818a', '注?包ID', 'signInId', null, 9, 1, 'system', 'system', to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'The ID of the registration package', '????????????? ???????????????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('136175', null, '87808f225d7240f68c2ab879347d818a', '下位机IP端口', 'ipPort', null, 10, 1, 'system', 'system', to_date('10-01-2023 09:00:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2023 09:00:47', 'dd-mm-yyyy hh24:mi:ss'), 'IP address and port of the lower computer', 'IP-????? ? ???? ??????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114916', null, '87808f225d7240f68c2ab879347d818a', '???地址', 'slave', null, 11, 1, 'system', 'system', to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'The slave address of the device', 'Slave-????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135535', null, '87808f225d7240f68c2ab879347d818a', '?峰延?(s)', 'peakDelay', null, 12, 1, 'system', 'system', to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), 'Peak staggering delay(s)', '???????????? ???????? ????(s)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('132518', null, '87808f225d7240f68c2ab879347d818a', '??', 'statusName', null, 13, 1, 'system', 'system', to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), 'state', '???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135215', null, '87808f225d7240f68c2ab879347d818a', '???位', 'allPath', null, 14, 1, 'system', 'system', to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-06-2022 08:09:31', 'dd-mm-yyyy hh24:mi:ss'), 'Affiliations', '??????????????(s)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('116923', null, '87808f225d7240f68c2ab879347d818a', '排序??', 'sortNum', null, 15, 1, 'system', 'system', to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), 'Sort', '???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('145297', null, '87808f225d7240f68c2ab879347d818a', '更新??', 'productionDataUpdateTime', 'width:120', 16, 1, 'system', 'system', to_date('17-11-2023 10:25:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-11-2023 10:25:42', 'dd-mm-yyyy hh24:mi:ss'), 'The time of the update', '????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134035', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:30:23', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134037', 'sys', 'd8cd980aa8344c399b9cf11268b6ed8f', '??名?', 'deviceName', null, 2, 1, 'system', 'system', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134039', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '采控?例', 'instanceName', 'width:120', 4, 1, 'system', 'system', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'Acquisition and control instances', '??????? ????? ? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134040', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '?示?例', 'displayInstanceName', 'width:120', 5, 1, 'system', 'system', to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), 'Displays instances', '?????????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('136600', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '?表?例', 'reportInstanceName', 'width:120', 6, 1, 'system', 'system', to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-03-2022 15:54:13', 'dd-mm-yyyy hh24:mi:ss'), 'Report instance', '????????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134041', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '?警?例', 'alarmInstanceName', 'width:120', 7, 1, 'system', 'system', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'Alert instance', '????????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135321', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '下位机TCP?型', 'tcpType', null, 8, 1, 'system', 'system', to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-08-2022 08:59:02', 'dd-mm-yyyy hh24:mi:ss'), 'TCP type of the lower computer', '??? TCP ??????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134042', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '注?包ID', 'signInId', null, 9, 1, 'system', 'system', to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'The ID of the registration package', '????????????? ???????????????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('136195', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '下位机IP端口', 'ipPort', null, 10, 1, 'system', 'system', to_date('11-01-2023 10:39:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-01-2023 10:39:54', 'dd-mm-yyyy hh24:mi:ss'), 'IP address and port of the lower computer', 'IP-????? ? ???? ??????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134043', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '???地址', 'slave', null, 11, 1, 'system', 'system', to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'The slave address of the device', 'Slave-????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('135540', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '?峰延?(s)', 'peakDelay', null, 12, 1, 'system', 'system', to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-09-2022 11:14:06', 'dd-mm-yyyy hh24:mi:ss'), 'Peak staggering delay(s)', '???????????? ???????? ????(s)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137915', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '???控路?1', 'videoUrl1', null, 13, 0, 'system', 'system', to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), 'Video surveillance path 1', '????? ??????????????? 1');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139579', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '??密?1', 'videoKeyName1', null, 14, 0, 'system', 'system', to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), 'Video Key 1', '???? ????? 1');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137916', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '???控路?2', 'videoUrl2', null, 15, 0, 'system', 'system', to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2022 17:16:49', 'dd-mm-yyyy hh24:mi:ss'), 'Video surveillance path 2', '????? ??????????????? 2');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('139580', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '??密?2', 'videoKeyName2', null, 16, 0, 'system', 'system', to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-07-2023 13:21:51', 'dd-mm-yyyy hh24:mi:ss'), 'Video Key 2', '???? ????? 2');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134044', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '??', 'statusName', null, 17, 1, 'system', 'system', to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-02-2022 10:21:53', 'dd-mm-yyyy hh24:mi:ss'), 'state', '???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('134045', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '排序??', 'sortNum', null, 18, 1, 'system', 'system', to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), 'Sort', '???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137917', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '原油密度(g/cm^3)', 'crudeOilDensity', null, 19, 0, 'system', 'system', to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'Crude oil density(g/cm^3)', '????????? ????? ?????(g/cm^3)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137918', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '水密度(g/cm^3)', 'waterDensity', null, 20, 0, 'system', 'system', to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'Water density(g/cm^3)', '????????? ????(g/cm^3)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137919', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '天然气相?密度', 'naturalGasRelativeDensity', null, 21, 0, 'system', 'system', to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'Relative density of natural gas', '????????????? ????????? ?????????? ????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137920', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '?和?力(MPa)', 'saturationPressure', null, 22, 0, 'system', 'system', to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'Saturation pressure(MPa)', '???????? ?????????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137921', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油?中部深度(m)', 'reservoirDepth', null, 23, 0, 'system', 'system', to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'The depth of the oil layer(m)', '??????? ????????? ????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137922', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油?中部?度(℃)', 'reservoirTemperature', null, 24, 0, 'system', 'system', to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:32:18', 'dd-mm-yyyy hh24:mi:ss'), 'The temperature of the oil layer(℃)', '??????????? ????????? ????(℃)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137923', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油?(MPa)', 'tubingPressure', null, 25, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'Tubing pressure(MPa)', '???????? ? ???????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137924', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '套?(MPa)', 'casingPressure', null, 26, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'Casing pressure(MPa)', '???????? ? ???????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137925', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '井口?度(℃)', 'wellHeadTemperature', null, 27, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'The temperature at the wellhead(℃)', '??????????? ?? ????? ????????(℃)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137926', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '含水率(%)', 'waterCut', null, 28, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'Water Cut(%)', '??????????????(%)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137927', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '生?气油比(m^3/t)', 'productionGasOilRatio', null, 29, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'The ratio of gas to oil(m^3/t)', '??????????? ???? ? ?????(m^3/t)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137928', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '?液面(m)', 'producingfluidLevel', null, 30, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'Production of liquid surfaces(m)', '???????????? ?????? ????????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137929', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵挂(m)', 'pumpSettingDepth', null, 31, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'Pump depth(m)', '??????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137931', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵筒?型', 'barrelType', null, 33, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'Cartridge type', '??? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137932', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵??', 'pumpGrade', null, 34, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'Pump level', '??????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137933', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '泵?(mm)', 'pumpBoreDiameter', null, 35, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'Barrel diameter(mm)', '??????? ?????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137934', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '柱塞?(m)', 'plungerLength', null, 36, 0, 'system', 'system', to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:39:19', 'dd-mm-yyyy hh24:mi:ss'), 'Plunger length(m)', '????? ????????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137935', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '油管??(mm)', 'tubingStringInsideDiameter', null, 37, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'Tubing inner diameter(mm)', '?????????? ??????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137936', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '套管??(mm)', 'casingStringInsideDiameter', null, 38, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'Casing inner diameter(mm)', '?????????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137937', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一?杆??', 'rodGrade1', null, 39, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the first level of the rod', '??????? ??????? ?????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137938', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一?杆外?(mm)', 'rodOutsideDiameter1', null, 40, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the first stage rod(mm)', '???????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137939', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一?杆??(mm)', 'rodInsideDiameter1', null, 41, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the first stage rod(mm)', '?????????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137940', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '一?杆?度(m)', 'rodLength1', null, 42, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the first stage rod(m)', '????? ?????? ?????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137941', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二?杆??', 'rodGrade2', null, 43, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the second level of the pole', '??????? ??????? ?????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137942', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二?杆外?(mm)', 'rodOutsideDiameter2', null, 44, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the second stage rod(mm)', '???????? ??????? ??????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137943', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二?杆??(mm)', 'rodInsideDiameter2', null, 45, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the second stage rod(mm)', '?????????? ??????? ?????? ?????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137944', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '二?杆?度(m)', 'rodLength2', null, 46, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the second stage rod(m)', '????? ?????? ?????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137945', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三?杆??', 'rodGrade3', null, 47, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the third pole', '??????? ???????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137946', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三?杆外?(mm)', 'rodOutsideDiameter3', null, 48, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the third stage rod(mm)', '???????? ??????? ??????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137947', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三?杆??(mm)', 'rodInsideDiameter3', null, 49, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the third-stage rod(mm)', '?????????? ??????? ??????? ??????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137948', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '三?杆?度(m)', 'rodLength3', null, 50, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the third-stage rod(m)', '????? ?????? ??????? ???????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137949', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四?杆??', 'rodGrade4', null, 51, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The level of the fourth level of the pole', '??????? ?????????? ?????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137950', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四?杆外?(mm)', 'rodOutsideDiameter4', null, 52, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The outer diameter of the fourth-stage rod(mm)', '???????? ??????? ??????? ????????? ???????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137951', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四?杆??(mm)', 'rodInsideDiameter4', null, 53, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The inner diameter of the fourth-level rod(mm)', '?????????? ??????? ??????? ?????????? ??????(mm)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137952', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '四?杆?度(m)', 'rodLength4', null, 54, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'The length of the fourth-level rod(m)', '????? ??????? ?????????? ??????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137953', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '工?干?', 'manualInterventionResultName', null, 55, 0, 'system', 'system', to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), 'Condition intervention', '????????????? ? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137954', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '?毛比(小?)', 'netGrossRatio', null, 56, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'Net Gross Ratio (Decimal)', '??????????? ??????? ?????? (??????????)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137955', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '?毛值(m^3/d)', 'netGrossValue', null, 57, 0, 'system', 'system', to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:44:26', 'dd-mm-yyyy hh24:mi:ss'), 'Net gross value(m^3/d)', '?????? ??????? ?????????(m^3/d)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137956', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '反演液面校正值(MPa)', 'levelCorrectValue', null, 58, 0, 'system', 'system', to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2023 10:20:38', 'dd-mm-yyyy hh24:mi:ss'), 'Level correction value(MPa)', '???????? ????????? ??????(MPa)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137957', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '抽油机厂家', 'manufacturer', null, 59, 0, 'system', 'system', to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'Pumping unit manufacturers', '????????????? ???????? ?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137958', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '抽油机型?', 'model', null, 60, 0, 'system', 'system', to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'Pumping unit model', '?????? ????????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137959', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '?牌?程', 'stroke', null, 61, 0, 'system', 'system', to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'Nameplate stroke', '??? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137960', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '平衡?重量(kN)', 'balanceWeight', null, 68, 0, 'system', 'system', to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'Weight of Balance Weight(kN)', '??? ??????????? ?????(kN)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('137961', null, 'd8cd980aa8344c399b9cf11268b6ed8f', '平衡?位置(m)', 'balancePosition', null, 69, 0, 'system', 'system', to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-05-2022 09:47:26', 'dd-mm-yyyy hh24:mi:ss'), 'Balance Weight Position(m)', '????????? ???????????????? ?????(m)');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150711', null, '2b4cd8cb8c6844769c66b038246c27bf', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('17-09-2021 18:29:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-09-2021 18:29:03', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150712', 'sys', '2b4cd8cb8c6844769c66b038246c27bf', '?位名?', 'orgName', null, 2, 0, 'system', 'system', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), 'The name of the organization', '???????????? ???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150713', null, '2b4cd8cb8c6844769c66b038246c27bf', '??名?', 'deviceName', null, 3, 1, 'system', 'system', to_date('13-10-2021 20:47:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 20:47:50', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150714', null, '2b4cd8cb8c6844769c66b038246c27bf', '短信???例', 'instanceName', 'width:120', 4, 1, 'system', 'system', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'SMS device instance', '????????? ?????????? SMS');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150715', null, '2b4cd8cb8c6844769c66b038246c27bf', '注?包ID', 'signInId', null, 5, 1, 'system', 'system', to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'The ID of the registration package', '????????????? ???????????????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('150716', null, '2b4cd8cb8c6844769c66b038246c27bf', '排序??', 'sortNum', null, 6, 1, 'system', 'system', to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-12-2019 13:05:41', 'dd-mm-yyyy hh24:mi:ss'), 'Sort', '???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146659', null, '1404100741bc42799be5b7cbebf4b649', '序?', 'id', 'width:50', 1, 1, 'system', 'system', to_date('10-11-2021 14:21:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-11-2021 14:21:46', 'dd-mm-yyyy hh24:mi:ss'), 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146660', null, '1404100741bc42799be5b7cbebf4b649', '??名?', 'name', null, 2, 1, 'system', 'system', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), 'Device', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146661', null, '1404100741bc42799be5b7cbebf4b649', '?型', 'type', null, 3, 1, 'system', 'system', to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-06-2014 13:34:03', 'dd-mm-yyyy hh24:mi:ss'), 'type', '???');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147076', null, '1404100741bc42799be5b7cbebf4b649', '厂家', 'manufacturer', null, 4, 1, 'system', 'system', to_date('22-03-2024 16:59:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-03-2024 16:59:49', 'dd-mm-yyyy hh24:mi:ss'), 'Manufacturer', '????????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146662', null, '1404100741bc42799be5b7cbebf4b649', '?格型?', 'model', null, 5, 1, 'system', 'system', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'Model', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146663', null, '1404100741bc42799be5b7cbebf4b649', '?注', 'remark', null, 6, 1, 'system', 'system', to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-06-2020 11:32:24', 'dd-mm-yyyy hh24:mi:ss'), 'Remark', '?????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('146664', null, '1404100741bc42799be5b7cbebf4b649', '排序??', 'sort', null, 7, 1, 'system', 'system', to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-06-2018 14:07:37', 'dd-mm-yyyy hh24:mi:ss'), 'Sort', '???????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114837', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模?名?', 'text', 'flex:2', 1, 1, 'system', 'system', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), 'The name of the module', '??? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114838', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模??介', 'mdShowname', 'flex:2', 2, 1, 'system', 'system', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), 'Introduction to the module', '???????? ? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114842', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模???', 'mdIcon', 'flex:1', 6, 1, 'system', 'system', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), 'Module icon', '?????? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114843', null, 'b6ef8f3a49094768b3231d5678fc9cbc', '模???', 'mdTypeName', 'flex:1', 7, 1, 'system', 'system', to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('23-06-2014 11:12:29', 'dd-mm-yyyy hh24:mi:ss'), 'Module type', '??? ??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114844', 'sys', 'b6ef8f3a49094768b3231d5678fc9cbc', '模?排序', 'mdSeq', 'flex:1', 8, 1, 'system', 'system', to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 16:27:02', 'dd-mm-yyyy hh24:mi:ss'), 'Module sequence', '?????????????????? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114884', null, 'b8a408839dd8498d9a19fc65f7406ed4', '序?', 'id', 'width:50', 1, 1, 'system', 'system', null, null, 'Index', '??????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114827', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典名?', 'name', 'flex:2', 2, 1, 'system', 'system', to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:31', 'dd-mm-yyyy hh24:mi:ss'), 'Dictionary name', '????????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114828', null, 'b8a408839dd8498d9a19fc65f7406ed4', '字典代?', 'code', 'flex:3', 3, 1, 'system', 'system', to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2014 16:10:40', 'dd-mm-yyyy hh24:mi:ss'), 'Dictionary code', '??? ???????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114829', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '字典?序', 'sorts', 'flex:1', 4, 1, 'system', 'system', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), 'Dictionary order', '??????? ????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('147379', null, 'b8a408839dd8498d9a19fc65f7406ed4', '??模?', 'moduleName', 'flex:1', 5, 1, 'system', 'system', to_date('25-04-2024 14:06:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-04-2024 14:06:09', 'dd-mm-yyyy hh24:mi:ss'), 'Affiliation module', '?????? ??????????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114830', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '?建人', 'creator', 'flex:1', 6, 1, 'system', 'system', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), 'Created by', '???????? ?????');

insert into TBL_DIST_ITEM (DATAITEMID, TENANTID, SYSDATAID, NAME_ZH_CN, CODE, DATAVALUE, SORTS, STATUS, CREATOR, UPDATEUSER, UPDATETIME, CREATEDATE, NAME_EN, NAME_RU)
values ('114831', 'sys', 'b8a408839dd8498d9a19fc65f7406ed4', '?建??', 'updatetime', 'flex:3', 7, 1, 'system', 'system', to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-06-2014 10:54:21', 'dd-mm-yyyy hh24:mi:ss'), 'Creation time', '????? ????????');