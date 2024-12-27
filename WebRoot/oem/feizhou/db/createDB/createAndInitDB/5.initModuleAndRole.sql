/*==============================================================*/
/* 初始化TBL_DEVICETYPEINFO数据                                          */
/*==============================================================*/
insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (1, 0, 0, '设备类型根节点');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (2, 1, 1, '泵设备');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (3, 1, 2, '管设备');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (4, 2, 11, '隔膜泵');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (5, 2, 12, '螺杆泵');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (6, 2, 13, '直线电机泵');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (7, 2, 14, '电潜泵');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (8, 2, 15, '射流泵');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (9, 3, 21, '加热管');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (10, 3, 22, '采水管');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (11, 3, 23, '集输管');

/*==============================================================*/
/* 初始化tbl_module数据                                          */
/*==============================================================*/
insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (9999, 0, '功能导航', '功能导航', '#', 'Navigation', 1, null, null, 'function', 0, '#', 'Navigation', '功能导航', 'Navigation', '功能导航');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (1998, 9999, '实时监控', '实时监控', 'AP.view.realTimeMonitoring.RealTimeMonitoringInfoView', 'DeviceRealTimeMonitoring', 1010010, null, null, 'realtime', 0, 'AP.controller.frame.MainIframeControl', 'RealTimeMonitoring', '实时监控', 'RealTimeMonitoring', '实时监控');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2018, 9999, '历史查询', '历史查询', 'AP.view.historyQuery.HistoryQueryInfoView', 'DeviceHistoryQuery', 1020010, null, null, 'history', 0, 'AP.controller.frame.MainIframeControl', 'HistoryQuery', '历史查询', 'HistoryQuery', '历史查询');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2158, 9999, '生产报表', '生产报表', 'AP.view.reportOut.ReportOutDailyReportView', 'DailyReport', 1030010, null, null, 'report', 0, 'AP.controller.frame.MainIframeControl', 'Report', '生产报表', 'Report', '生产报表');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2058, 9999, '故障查询', '故障查询', 'AP.view.alarmQuery.AlarmQueryInfoView', 'AlarmQuery', 1040010, null, null, 'alarm', 0, 'AP.controller.frame.MainIframeControl', 'AlarmQuery', '故障查询', 'AlarmQuery', '故障查询');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2038, 9999, '设备日志', '设备日志', 'AP.view.log.DeviceOperationLogInfoView', 'DeviceOperationLogQuery', 1050010, null, null, 'log', 0, 'AP.controller.frame.MainIframeControl', 'DeviceLog', '设备日志', 'DeviceLog', '设备日志');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2238, 9999, '系统日志', '系统日志', 'AP.view.log.SystemLogInfoView', 'SystemLogQuery', 1060010, null, null, 'log', 0, 'AP.controller.frame.MainIframeControl', 'SystemLog', '系统日志', 'SystemLog', '系统日志');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2179, 9999, '计算维护', '计算维护', 'AP.view.dataMaintaining.CalculateMaintainingInfoView', 'CalculateMaintaining', 1070010, null, null, 'calculate', 0, '#', 'CalculateMaintaining', '计算维护', 'CalculateMaintaining', '计算维护');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (1777, 9999, '驱动配置', '驱动配置', 'AP.view.acquisitionUnit.ProtocolConfigInfoView', 'DriverManagement', 1080010, null, null, 'driverConfig', 0, 'AP.controller.acquisitionUnit.AcquisitionUnitInfoControl', 'DriverConfig', '驱动配置', 'DriverConfig', '驱动配置');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (27, 9999, '权限管理', '权限管理', '#', 'AuthorityManagement', 2030000, null, null, 'right', 0, 'AP.controller.frame.MainIframeControl', 'AuthorityManagement', '权限管理', 'AuthorityManagement', '权限管理');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (24, 27, '组织用户', '组织用户', 'AP.view.orgAndUser.OrgAndUserInfoView', 'OrganizationAndUserManagement', 2030100, null, null, 'org', 0, 'AP.controller.orgAndUser.OrgAndUserInfoControl', 'OrganizationAndUser', '组织用户', 'OrganizationAndUser', '组织用户');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (29, 27, '角色管理', '角色管理', 'AP.view.role.RoleInfoView', 'RoleManagement', 2030300, null, null, 'role', 0, 'AP.controller.role.RoleInfoControl', 'RoleManagement', '角色管理', 'RoleManagement', '角色管理');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (31, 9999, '设备管理', '设备管理', '#', 'DeviceManagement', 2040000, null, null, 'dataConfig', 0, 'AP.controller.frame.MainIframeControl', 'DeviceManagement', '设备管理', 'DeviceManagement', '设备管理');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (34, 31, '井名信息', '井名信息', 'AP.view.well.DeviceManagerInfoView', 'WellInformation', 2040100, null, null, 'wellInformation', 0, 'AP.controller.well.WellInfoController', 'DeviceInformation', '井名信息', 'DeviceInformation', '井名信息');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2218, 31, '辅件设备', '辅件设备', 'AP.view.well.AuxiliaryDeviceInfoView', 'AuxiliaryDeviceManager', 2040200, null, null, 'auxiliaryDevice', 0, 'AP.controller.well.WellInfoController', 'AuxiliaryDeviceManagement', '辅件设备', 'AuxiliaryDeviceManagement', '辅件设备');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2078, 31, '短信设备', '短信设备管理', 'AP.view.well.SMSDeviceInfoView', 'SMSDeviceManager', 2040300, null, null, 'smsDevice', 0, 'AP.controller.well.WellInfoController', 'SMSDeviceManager', '短信设备', 'SMSDeviceManager', '短信设备管理');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (23, 9999, '系统配置', '系统配置', '#', 'SystemManagement', 2090000, null, null, 'system', 0, 'AP.controller.frame.MainIframeControl', 'SystemManagement', '系统配置', 'SystemManagement', '系统配置');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (26, 23, '模块配置', '模块配置', 'AP.view.module.ModuleInfoView', 'ModuleManagement', 2090100, null, null, 'module', 0, 'AP.controller.module.ModuleInfoControl', 'ModuleManagement', '模块配置', 'ModuleManagement', '模块配置');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (894, 23, '字典配置', '字典配置', 'AP.view.data.SystemdataInfoView', 'DataDictionaryManagement', 2090200, null, null, 'dictionary', 0, 'AP.controller.data.SystemdataInfoControl', 'DataDictionaryManagement', '字典配置', 'DataDictionaryManagement', '字典配置');

/*==============================================================*/
/* 初始化tbl_role数据                                          */
/*==============================================================*/
insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (1, '超级管理员', 1, 1, 1, '全部权限');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (2, '应用管理员', 3, 3, 0, '设备管理、客户管理');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (3, '普通用户', 5, 5, 0, '数据查询');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (4, '软件管理员', 2, 2, 0, '设备管理、客户管理、驱动管理');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (44, '普通用户_带控制', 4, 4, 0, '数据查询');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (105, '管设备应用管理员', 3, 3, 0, null);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (110, '泵设备普通用户_带控制', 4, 4, 0, null);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (104, '泵设备应用管理员', 3, 3, 0, null);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (106, '泵设备普通用户', 5, 5, 0, null);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (107, '管设备普通用户', 5, 5, 0, null);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (108, '泵设备软件管理员', 2, 2, 0, null);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (109, '管设备软件管理员', 2, 2, 0, null);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (111, '管设备普通用户_带控制', 4, 4, 0, null);

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
values (13, 1777, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (14, 23, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (15, 26, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (16, 894, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (17, 2158, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (18, 2179, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (43, 2218, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (123, 2238, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (323, 2078, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (563, 9999, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (564, 1998, 2, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (565, 2018, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (566, 2058, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (567, 2038, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (568, 2238, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (569, 1777, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (570, 27, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (571, 24, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (572, 31, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (573, 34, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (574, 2218, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (575, 23, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (576, 894, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (463, 1998, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (464, 2018, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (465, 2058, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (466, 2038, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (467, 2238, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (577, 1998, 4, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (578, 2018, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (579, 2058, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (580, 2038, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (581, 2238, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (582, 1777, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (583, 34, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (468, 1998, 44, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (469, 2018, 44, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (470, 2058, 44, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (471, 2038, 44, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (472, 2238, 44, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (584, 9999, 104, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (585, 1998, 104, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (586, 2018, 104, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (587, 2058, 104, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (588, 2038, 104, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (589, 2238, 104, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (590, 1777, 104, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (591, 27, 104, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (592, 24, 104, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (593, 31, 104, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (594, 34, 104, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (595, 2218, 104, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (596, 23, 104, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (597, 894, 104, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (598, 9999, 105, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (599, 1998, 105, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (600, 2018, 105, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (601, 2058, 105, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (602, 2038, 105, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (603, 2238, 105, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (604, 1777, 105, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (605, 27, 105, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (606, 24, 105, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (607, 31, 105, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (608, 34, 105, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (609, 2218, 105, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (610, 23, 105, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (611, 894, 105, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (552, 1998, 106, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (553, 2018, 106, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (554, 2058, 106, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (555, 2038, 106, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (556, 2238, 106, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (557, 1998, 107, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (558, 2018, 107, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (559, 2058, 107, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (560, 2038, 107, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (561, 2238, 107, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (612, 1998, 108, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (613, 2018, 108, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (614, 2058, 108, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (615, 2038, 108, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (616, 2238, 108, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (617, 1777, 108, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (618, 34, 108, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (619, 1998, 109, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (620, 2018, 109, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (621, 2058, 109, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (622, 2038, 109, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (623, 2238, 109, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (624, 1777, 109, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (625, 34, 109, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (473, 1998, 110, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (474, 2018, 110, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (475, 2058, 110, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (476, 2038, 110, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (477, 2238, 110, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (478, 1998, 111, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (479, 2018, 111, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (480, 2058, 111, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (481, 2038, 111, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (482, 2238, 111, '1,0,0');

/*==============================================================*/
/* 初始化TBL_DEVICETYPE2ROLE数据                                          */
/*==============================================================*/
insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (1, 2, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (2, 3, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (162, 4, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (163, 5, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (164, 6, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (165, 7, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (166, 8, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (167, 9, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (168, 10, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (169, 11, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (3, 1, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (182, 2, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (188, 3, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (183, 4, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (184, 5, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (185, 6, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (186, 7, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (187, 8, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (189, 9, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (190, 10, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (191, 11, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (181, 1, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (193, 2, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (199, 3, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (194, 4, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (195, 5, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (196, 6, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (197, 7, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (198, 8, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (200, 9, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (201, 10, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (202, 11, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (192, 1, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (204, 2, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (210, 3, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (205, 4, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (206, 5, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (207, 6, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (208, 7, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (209, 8, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (211, 9, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (212, 10, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (213, 11, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (203, 1, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (215, 2, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (221, 3, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (216, 4, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (217, 5, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (218, 6, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (219, 7, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (220, 8, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (222, 9, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (223, 10, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (224, 11, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (214, 1, 44, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (241, 2, 104, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (242, 4, 104, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (243, 5, 104, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (244, 6, 104, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (245, 7, 104, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (246, 8, 104, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (247, 3, 105, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (248, 9, 105, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (249, 10, 105, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (250, 11, 105, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (251, 2, 106, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (252, 4, 106, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (253, 5, 106, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (254, 6, 106, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (255, 7, 106, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (256, 8, 106, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (257, 3, 107, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (258, 9, 107, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (259, 10, 107, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (260, 11, 107, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (261, 2, 108, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (262, 4, 108, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (263, 5, 108, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (264, 6, 108, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (265, 7, 108, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (266, 8, 108, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (267, 3, 109, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (268, 9, 109, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (269, 10, 109, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (270, 11, 109, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (271, 2, 110, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (272, 4, 110, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (273, 5, 110, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (274, 6, 110, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (275, 7, 110, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (276, 8, 110, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (277, 3, 111, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (278, 9, 111, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (279, 10, 111, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (280, 11, 111, '0,0,0');

/*==============================================================*/
/* 初始化tbl_org数据                                          */
/*==============================================================*/
insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1, '0000', '组织根节点', '组织根节点', 0, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (2, null, '中联煤层气', '中联煤层气', 1323, 2);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (3, null, '大庆油田', null, 1322, 7);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (4, null, '太仓工厂', null, 27, 1);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (5, null, '长庆油田公司', null, 1322, 6);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (6, null, '潘河', null, 2, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (7, null, '柿庄南', null, 2, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (8, null, '柿庄北', null, 2, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (9, null, '出厂测试', null, 4, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (10, null, '疲劳测试', null, 4, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (12, null, '十厂', null, 5, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (13, null, '三厂', null, 5, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (22, null, '海拉尔', null, 1323, 3);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (23, null, '蓝焰煤层气', null, 1323, 4);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (24, null, '华北煤层气', null, 1323, 5);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (25, null, '吉林油田', null, 1322, 8);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (26, null, '延长油田', null, 1323, 9);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (27, null, '厂内测试', null, 1, 2);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (28, null, '泵设备', null, 222, 2);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (29, null, '管设备', null, 222, 10);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (42, null, '待出厂', null, 1, 3);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (67, null, '集输加热管', null, 29, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (68, null, '华北五厂', null, 67, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (69, null, '束21', null, 68, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (72, null, '晋古', null, 68, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (76, null, '华北一厂', null, 67, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (77, null, '同口', null, 76, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (84, null, '高阳', null, 76, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (88, null, '鄚北', null, 76, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (90, null, '雁翔', null, 76, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (92, null, '南马', null, 76, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (97, null, '任北', null, 76, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (162, null, '【供应商】东昊', null, 29, 50);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (182, null, '【供应商】杉建', null, 29, 51);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (202, null, '回收站', null, 1, 4);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (203, null, '美中能源', null, 1323, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (204, null, '智能加热管', null, 29, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (205, null, '三厂', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (206, null, '延长', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (207, null, '南泥湾', null, 206, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (208, null, '地埋加热管', null, 29, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (222, null, '现场设备', null, 1, 1);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (242, null, '水源井', null, 29, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (243, null, '长庆一厂', null, 242, 1);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (244, null, '长庆二厂', null, 242, 2);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (245, null, '长庆四厂', null, 242, 4);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (246, null, '长庆五厂', null, 242, 5);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (247, null, '长庆九厂', null, 242, 9);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (248, null, '长庆十厂', null, 242, 10);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (249, null, '长庆十一厂', null, 242, 11);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (250, null, '长庆十二厂', null, 242, 12);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (251, null, '王南', null, 243, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (252, null, '华池', null, 244, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (253, null, '西峰', null, 244, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (254, null, '艾家湾', null, 245, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (255, null, '麻南', null, 246, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (256, null, '刘茆源', null, 247, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (257, null, '柔远', null, 248, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (258, null, '固城', null, 250, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (259, null, '方山', null, 249, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (260, null, '太白梁', null, 249, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (261, null, '城壕', null, 244, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (262, null, '五谷城', null, 247, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (263, null, '冯地坑', null, 246, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (264, null, '马家山东', null, 246, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (265, null, '麻黄山北', null, 246, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (266, null, '麻黄山西', null, 246, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (267, null, '营盘山', null, 246, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (268, null, '五蛟', null, 248, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (269, null, '南岭', null, 244, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (282, null, '现场参数更新', null, 29, 1);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (302, null, '【供应商】多欧', null, 29, 52);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (322, null, '五厂', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (323, null, '冯地坑采油作业区', null, 322, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (324, null, '堡子弯', null, 322, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (325, null, '麻北', null, 322, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (326, null, '马西', null, 322, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (327, null, '十厂', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (328, null, '乔河', null, 327, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (382, null, '大庆六厂  ', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (383, null, '四区', null, 382, 4);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (402, null, '长庆六厂', null, 242, 6);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (403, null, '砖井作业区', null, 402, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (422, null, '苏中天然气', null, 242, 21);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (442, null, '长东', null, 243, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (443, null, '长庆页岩油', null, 242, 20);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (444, null, '西十转', null, 443, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (445, null, '岭二转', null, 443, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (446, null, '桐川', null, 249, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (447, null, '南梁', null, 244, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (448, null, '彭阳', null, 249, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (449, null, '长庆三厂', null, 242, 3);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (450, null, '红井子', null, 449, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (451, null, '安五', null, 402, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (462, null, '长庆十厂', null, 208, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (463, null, '五蛟', null, 462, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (482, null, '大庆', null, 67, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (483, null, '华油3#环', null, 482, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (503, null, '岭北', null, 244, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (504, null, '吉岘', null, 250, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (505, null, '乔川', null, 248, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (506, null, '苏格里气田', null, 242, 22);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (507, null, '长庆十一厂', null, 208, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (508, null, '方山', null, 507, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (522, null, '海南', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (523, null, '海南项目', null, 522, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (562, null, '加热电缆项目', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (563, null, '加热电缆', null, 562, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (564, null, '五里湾', null, 205, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (582, null, '大庆八厂', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (602, null, '庆城联合站', null, 443, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (622, null, '长庆', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (623, null, '页岩油', null, 622, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (624, null, '大港油田', null, 67, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (625, null, '三厂', null, 624, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (642, null, '板桥', null, 250, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (662, null, '测试柜', null, 162, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (682, null, '胜利油田', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (683, null, '东辛', null, 682, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (702, null, '堡子湾', null, 246, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (722, null, '马岭南', null, 244, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (742, null, '未知井场', null, 443, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (764, null, '五平台', null, 582, 5);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (765, null, '六平台', null, 582, 6);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (782, null, '长庆七厂', null, 242, 7);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (783, null, '洪德', null, 782, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (802, null, '技术服务部', null, 29, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (803, null, 'KD93备用模块', null, 802, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (822, null, '兴庄作业区', null, 402, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (823, null, '薛岔', null, 247, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (842, null, '岭三', null, 443, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (862, null, '杨青', null, 247, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (882, null, '未知井场', null, 782, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (883, null, '未知井场', null, 402, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (902, null, '岭13联', null, 443, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (922, null, '兰花煤层气', null, 1323, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (942, null, '十二平台', null, 582, 12);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (962, null, '十五平台', null, 582, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (963, null, '三平台', null, 582, 3);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (982, null, '铜川', null, 507, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1002, null, '元城', null, 248, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1022, null, '长庆四厂', null, 204, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1023, null, '化子平', null, 1022, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1062, null, '大水坑', null, 449, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1082, null, '郑庄北区', null, 23, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1122, null, '嘉能项目部', null, 23, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1142, null, '十六平台', null, 582, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1162, null, '华庆', null, 462, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1182, null, '柔远', null, 462, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1202, null, '一区', null, 382, 1);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1222, null, '新集', null, 249, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1242, null, '产品柜', null, 162, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1262, null, '成庄工区', null, 23, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1264, null, '淮南矿业', null, 1323, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1265, null, '韩城煤层气', null, 1323, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1266, null, '富地柳林', null, 1323, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1282, null, '安边 ', null, 402, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1302, null, '铜川油气开发有限公司', null, 1322, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1303, null, '渭北油田', null, 1302, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1304, null, '玉门油田', null, 1322, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1305, null, '老君庙作业区L层西区', null, 1304, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1322, null, '油田隔膜泵', null, 28, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1323, null, '煤层气隔膜泵', null, 28, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1324, null, '元城', null, 462, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1342, null, '二区', null, 382, 2);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1362, null, '乔河', null, 462, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1382, null, '樊家川', null, 244, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1402, null, '【供应商】生产自制', null, 29, 53);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1422, null, '环江', null, 782, null);

insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1423, null, '六区', null, 382, 6);

/*==============================================================*/
/* 初始化tbl_user数据                                          */
/*==============================================================*/
insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (1, 'system', '3c4c1fed0fb2b548f88eab0fcfe0b425', '超级管理员', null, null, 1, 1, to_date('17-03-2022 14:51:10', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (2, 'lijule', '0af259c20b3cb21bc5bf610977998035', '超级管理员', 'lijule@jumaoil.com', '18962602301', 1, 1, to_date('11-02-2022 17:34:52', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 1);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (3, 'fangqifei', 'e78bf509e0daa5dfbe730e037538322f', '房其飞', 'fangqifei@jumaoil.com', '18151568990', 2, 222, to_date('12-02-2022 08:54:18', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (4, 'yangzhifei', '7c42d5f6e5f5e75b3aff3e9545764aa5', '杨智飞', 'yangzhifei@jumaoil.com', '15091502950', 4, 222, to_date('12-02-2022 08:55:38', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (5, 'songjiao', 'da6643b40815d976061a62d20bb1acde', '宋骄', 'songjiao@fbpipeline.com', '13701704615', 2, 29, to_date('12-02-2022 08:56:13', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 1);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (23, 'aichunfeng', '1a65986b11c2361e2130c2329832c680', '艾纯峰', 'aichunfeng@jumaoil.com', '18915772246', 2, 222, to_date('03-03-2022 13:18:55', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (24, 'wangxue', 'c01d14a45a8a8ac362a9f020f72b1c7a', '王雪', 'wangxue@jumaoil.com', '13140903046', 2, 1, to_date('03-03-2022 13:19:53', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (43, 'luzhenxian', '0dcd6218098ec268422944332f312b11', '陆振贤', null, null, 2, 1, to_date('11-05-2022 14:08:31', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (123, 'zhangmiao', 'd50e5a9881776e5bdb209567130c0512', '张淼', null, null, 107, 29, to_date('26-07-2022 16:58:51', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (143, 'tongkou', '8008ae5cdd13849563cae4a1fae248fa', '同口', null, null, 111, 77, to_date('27-07-2022 13:47:59', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (163, 'wuchang', 'c2d068e603d3757f96550dbee1ad4073', '华北五厂', null, null, 111, 68, to_date('29-07-2022 12:47:58', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (203, 'wangxuebo', '3d38226165ad6d53d3035430b1c933dd', '王学波', null, null, 111, 29, to_date('04-08-2022 09:37:57', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (204, 'ceshizhanghu', 'f2fe8b207f7225825cd82e9f11c4b8aa', '西安东昊', null, null, 111, 662, to_date('08-08-2022 11:05:18', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (225, 'laohe', '6eec0033bbf997f2d4145168ff1c598d', '技术服务部_老贺', null, null, 111, 29, to_date('08-08-2022 15:01:53', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (244, 'luyan', 'fdb991343811b0ef92cb36d0f52d2fc5', '陆燕', null, null, 104, 28, to_date('10-08-2022 14:59:16', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (245, 'luzhiyi', '22eacae46ea11a848aae2face7012d3e', '陆志毅', null, null, 2, 1, to_date('10-08-2022 15:00:52', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (247, 'shanjian', 'd02134a32f1df34fe909285852cbd71b', '李总', null, null, 107, 182, to_date('10-08-2022 16:12:26', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (264, 'duoou', 'e422c934bd5f31d77f6fcebf9b6db9b4', '多欧', null, null, 107, 302, to_date('17-08-2022 16:52:23', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (284, 'daqing6', 'e28a6fc709f96da0991af2cd9f1d082f', '大庆六厂', null, '13936903873', 111, 383, to_date('31-10-2022 16:20:37', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (324, 'suzhong1125', '3559233541c9a7fbc633375d4c009178', '苏中天然气', null, null, 111, 422, to_date('25-11-2022 08:39:38', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (344, 'huayou', '8ad36a1a090848b4353558a9a80d8c3a', '大庆华油', null, null, 111, 483, to_date('27-12-2022 09:56:20', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (364, 'yanchang', '6278d959ba748e732dde192b818f496c', '延长', null, null, 111, 207, to_date('30-12-2022 11:52:54', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (384, 'bachang', '3d719596803298145908afe73f8cc5af', '大庆八厂', null, null, 111, 582, to_date('11-01-2023 11:34:33', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (404, 'hainan', 'b1016b62df725a05e2b2a85d594b188a', '海南项目', null, null, 111, 523, to_date('12-01-2023 17:21:48', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (424, 'QWF', 'dd653689f154a8f34cbe86e630826a7c', '乔伟峰', null, null, 111, 29, to_date('09-02-2023 16:00:56', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (425, 'HYW', 'dd653689f154a8f34cbe86e630826a7c', '贺永伟', null, null, 111, 29, to_date('09-02-2023 16:02:15', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (426, 'LB', 'dd653689f154a8f34cbe86e630826a7c', '刘波', null, null, 111, 29, to_date('09-02-2023 16:02:57', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (427, 'LGQ', 'dd653689f154a8f34cbe86e630826a7c', '刘国强', null, null, 111, 29, to_date('09-02-2023 16:03:30', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (428, 'YZF', 'dd653689f154a8f34cbe86e630826a7c', '杨智飞', null, null, 111, 29, to_date('09-02-2023 16:04:02', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (444, 'WJT', 'dd653689f154a8f34cbe86e630826a7c', '王俊天', null, null, 111, 29, to_date('13-02-2023 11:35:53', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (464, 'dagang', '181234ffe3fe81ae1229c6a6ee582d3f', '大港油田三厂', null, null, 111, 624, to_date('15-03-2023 09:03:36', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (484, 'gaoyang', '5bf81172efb33e18fe5cd0cddbcc46ef', '高阳', null, null, 111, 84, to_date('15-03-2023 12:31:04', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (504, 'donghao', 'b50fa33a9134294a023db81dbd832fa3', '东昊技术员', null, null, 111, 162, to_date('10-04-2023 13:06:27', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (524, 'hbyc', '44dddcd1b18f313e912d3ea2d7bcdbff', '华北一厂', null, null, 107, 76, to_date('26-04-2023 14:43:28', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (526, 'yanxiang', '71e9bbd33b242919162d457c5521492f', '雁翔', null, null, 111, 90, to_date('26-04-2023 14:49:14', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (544, 'LZG', '845f1cf0b8ebcde80b159584bc5fdd47', '李志刚', null, null, 107, 29, to_date('16-05-2023 10:52:14', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (564, 'changqing', '1fdf651014e8e4793f7018f112f9976d', '长庆页岩油', null, null, 107, 623, to_date('06-06-2023 15:35:16', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (584, 'dingxueguang', '325a2cc052914ceeb8c19016c091d2ac', '丁学光', null, null, 2, 1, to_date('03-08-2023 09:21:48', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (604, 'huangal', '4ccf9f5245c6b1afbc8df7549426b6cc', '黄阿龙', null, null, 2, 1, to_date('14-09-2023 15:34:53', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (624, 'DZF', 'e2f0aacf416392ab72ced3c6fbc8c43e', '岭三_董', null, null, 111, 842, to_date('20-09-2023 16:45:51', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (644, 'luwantao', '54f6714c56fc99a4eed299d05b002148', '路万涛', null, null, 106, 1082, to_date('23-10-2023 14:24:06', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (664, 'liuchang', '0bf5bcd8f555fa2dd35c5f7dd64b9337', '长庆六厂-A8S4', null, null, 107, 883, to_date('30-10-2023 16:32:33', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (684, 'LYMCQ', 'f6205ed03184daa6fa71a18305745455', 'LYMCQ', null, null, 110, 1082, to_date('21-12-2023 11:01:18', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (704, 'luwantaolh', '5858864f53a244b4c80cc7bc70af5043', '路万涛', null, null, 106, 922, to_date('23-12-2023 19:59:35', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (724, 'huaqing', '9f23c134c717a6615c444c8fb0f8866f', '华庆', null, null, 111, 1162, to_date('22-01-2024 14:14:51', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (744, 'changqing10', '30cbbe3c14b530afabadd943f6040f5f', '长庆十厂', null, null, 107, 462, to_date('30-01-2024 13:14:55', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (764, 'ZLSZB', '196a1def54a00a89853261b76cd27ce8', '中联柿庄北', null, null, 110, 8, to_date('28-02-2024 17:28:00', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (786, '670991868@qq.com', '2b5a8d067b2b367961a1c1dfc4ce903a', '淮南矿业', null, null, 110, 1264, to_date('19-03-2024 10:30:23', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (804, 'zhouyueqi', 'e10adc3949ba59abbe56e057f20f883e', '铜川油气开发公司-周越骑', null, '15839907862', 106, 1303, to_date('08-05-2024 11:56:04', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (824, 'LYJN', 'bb5a26ddcb4356f0c0f74d7c1bb85965', '蓝焰嘉能', null, null, 110, 1122, to_date('13-06-2024 11:10:52', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (825, 'test', 'e10adc3949ba59abbe56e057f20f883e', 'test', null, null, 104, 1, to_date('01-08-2024 14:20:42', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);