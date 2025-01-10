/*==============================================================*/
/* ��ʼ��TBL_DEVICETYPEINFO����                                          */
/insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU)
values (1, 0, 0, '�豸���͸��ڵ�', 'Root', '�豸���͸��ڵ�');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU)
values (2, 1, 1, '��������', 'Lifting Type', '��������');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU)
values (3, 2, null, '���ͻ�', 'SRP', '���ͻ�');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME_ZH_CN, NAME_EN, NAME_RU)
values (4, 2, null, '�ݸ˱�', 'PCP', '�ݸ˱�');

/*==============================================================*/
/* ��ʼ��tbl_module����                                          */
/*==============================================================*/
insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (9999, 0, '���ܵ���', '���ܵ���', '#', 'Navigation', 1, null, null, 'function', 0, '#', 'Navigation', '���ܵ���', 'Navigation', '���ܵ���');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (1998, 9999, 'ʵʱ���', 'ʵʱ���', 'AP.view.realTimeMonitoring.RealTimeMonitoringInfoView', 'DeviceRealTimeMonitoring', 1010010, null, null, 'realtime', 0, 'AP.controller.frame.MainIframeControl', 'Realtime Monitoring', 'ʵʱ���', 'Realtime Monitoring', 'ʵʱ���');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2018, 9999, '��ʷ��ѯ', '��ʷ��ѯ', 'AP.view.historyQuery.HistoryQueryInfoView', 'DeviceHistoryQuery', 1020010, null, null, 'history', 0, 'AP.controller.frame.MainIframeControl', 'History Query', '��ʷ��ѯ', 'History Query', '��ʷ��ѯ');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2158, 9999, '��������', '��������', 'AP.view.reportOut.ReportOutDailyReportView', 'DailyReport', 1030010, null, null, 'report', 0, 'AP.controller.frame.MainIframeControl', 'Report', '��������', 'Report', '��������');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2058, 9999, '���ϲ�ѯ', '���ϲ�ѯ', 'AP.view.alarmQuery.AlarmQueryInfoView', 'AlarmQuery', 1040010, null, null, 'alarm', 0, 'AP.controller.frame.MainIframeControl', 'Alarm Query', '���ϲ�ѯ', 'Alarm Query', '���ϲ�ѯ');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2038, 9999, '�豸��־', '�豸��־', 'AP.view.log.DeviceOperationLogInfoView', 'DeviceOperationLogQuery', 1050010, null, null, 'log', 0, 'AP.controller.frame.MainIframeControl', 'Device Log', '�豸��־', 'Device Log', '�豸��־');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2238, 9999, 'ϵͳ��־', 'ϵͳ��־', 'AP.view.log.SystemLogInfoView', 'SystemLogQuery', 1060010, null, null, 'log', 0, 'AP.controller.frame.MainIframeControl', 'System Log', 'ϵͳ��־', 'System Log', 'ϵͳ��־');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2179, 9999, '����ά��', '����ά��', 'AP.view.dataMaintaining.CalculateMaintainingInfoView', 'CalculateMaintaining', 1070010, null, null, 'calculate', 0, '#', 'Calculate Maintaining', '����ά��', 'Calculate Maintaining', '����ά��');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (1777, 9999, '��������', '��������', 'AP.view.acquisitionUnit.ProtocolConfigInfoView', 'DriverManagement', 1080010, null, null, 'driverConfig', 0, 'AP.controller.acquisitionUnit.AcquisitionUnitInfoControl', 'Driver Config', '��������', 'Driver Config', '��������');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (27, 9999, 'Ȩ�޹���', 'Ȩ�޹���', '#', 'AuthorityManagement', 2030000, null, null, 'right', 0, 'AP.controller.frame.MainIframeControl', 'Authority Management', 'Ȩ�޹���', 'Authority Management', 'Ȩ�޹���');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (24, 27, '��֯�û�', '��֯�û�', 'AP.view.orgAndUser.OrgAndUserInfoView', 'OrganizationAndUserManagement', 2030100, null, null, 'org', 0, 'AP.controller.orgAndUser.OrgAndUserInfoControl', 'Organization And User', '��֯�û�', 'Organization And User', '��֯�û�');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (29, 27, '��ɫ����', '��ɫ����', 'AP.view.role.RoleInfoView', 'RoleManagement', 2030300, null, null, 'role', 0, 'AP.controller.role.RoleInfoControl', 'Role Management', '��ɫ����', 'Role Management', '��ɫ����');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (31, 9999, '�豸����', '�豸����', '#', 'DeviceManagement', 2040000, null, null, 'dataConfig', 0, 'AP.controller.frame.MainIframeControl', 'Device Management', '�豸����', 'Device Management', '�豸����');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (34, 31, '������Ϣ', '������Ϣ', 'AP.view.well.DeviceManagerInfoView', 'WellInformation', 2040100, null, null, 'wellInformation', 0, 'AP.controller.well.WellInfoController', 'Device Information', '������Ϣ', 'Device Information', '������Ϣ');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2218, 31, '�����豸', '�����豸', 'AP.view.well.AuxiliaryDeviceInfoView', 'AuxiliaryDeviceManager', 2040200, null, null, 'auxiliaryDevice', 0, 'AP.controller.well.WellInfoController', 'Auxiliary Device Management', '�����豸', 'Auxiliary Device Management', '�����豸');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2078, 31, '�����豸', '�����豸����', 'AP.view.well.SMSDeviceInfoView', 'SMSDeviceManager', 2040300, null, null, 'smsDevice', 0, 'AP.controller.well.WellInfoController', 'SMS Device Manager', '�����豸', 'SMS Device Manager', '�����豸����');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (23, 9999, 'ϵͳ����', 'ϵͳ����', '#', 'SystemManagement', 2090000, null, null, 'system', 0, 'AP.controller.frame.MainIframeControl', 'System Management', 'ϵͳ����', 'System Management', 'ϵͳ����');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (26, 23, 'ģ������', 'ģ������', 'AP.view.module.ModuleInfoView', 'ModuleManagement', 2090100, null, null, 'module', 0, 'AP.controller.module.ModuleInfoControl', 'Module Management', 'ģ������', 'Module Management', 'ģ������');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (894, 23, '�ֵ�����', '�ֵ�����', 'AP.view.data.SystemdataInfoView', 'DataDictionaryManagement', 2090200, null, null, 'dictionary', 0, 'AP.controller.data.SystemdataInfoControl', 'Data Dictionary Management', '�ֵ�����', 'Data Dictionary Management', '�ֵ�����');

/*==============================================================*/
/* ��ʼ��tbl_role����                                          */
/*==============================================================*/
insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK, ROLE_LANGUAGEEDIT)
values (1, 'administrator', 1, 1, 1, 'the full set of rights', 1);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK, ROLE_LANGUAGEEDIT)
values (2, 'softwarevalet', 2, 1, 1, 'viewing and managing data', 0);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK, ROLE_LANGUAGEEDIT)
values (3, 'analyst', 3, 0, 0, 'viewing data', 0);

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK, ROLE_LANGUAGEEDIT)
values (4, 'operator', 4, 2, 1, 'viewing data', 0);

/*==============================================================*/
/* ��ʼ��tbl_module2role����                                          */
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
values (17, 2218, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (18, 2238, 1, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (19, 2078, 1, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (20, 9999, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (21, 1998, 2, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (22, 2018, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (23, 2158, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (24, 2058, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (25, 2038, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (26, 2179, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (27, 1777, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (28, 27, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (29, 24, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (30, 29, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (31, 31, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (32, 34, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (33, 2218, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (34, 23, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (35, 26, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (36, 894, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (37, 1998, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (38, 2018, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (39, 2158, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (40, 2058, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (41, 2038, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (42, 26, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (43, 894, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (44, 1998, 4, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (45, 2018, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (46, 2158, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (47, 2058, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (48, 2038, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (49, 2179, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (50, 1777, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (51, 34, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (52, 2218, 4, '1,1,0');

/*==============================================================*/
/* ��ʼ��TBL_DEVICETYPE2ROLE����                                          */
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
values (5, 1, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (6, 2, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (7, 3, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (8, 4, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (9, 1, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (10, 2, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (11, 3, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (12, 4, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (13, 1, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (14, 2, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (15, 3, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (16, 4, 4, '0,0,0');

/*==============================================================*/
/* ��ʼ��tbl_org����                                          */
/*==============================================================*/
insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME_ZH_CN, ORG_NAME_EN, ORG_NAME_RU, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1, '0000', '��֯���ڵ�', 'Root', null, '��֯���ڵ�', 0, null);

/*==============================================================*/
/* ��ʼ��tbl_user����                                          */
/*==============================================================*/
insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL, USER_LANGUAGE)
values (1, 'system', '3c4c1fed0fb2b548f88eab0fcfe0b425', 'system', null, null, 1, 1, to_date('17-03-2022 14:51:10', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0, 2);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL, USER_LANGUAGE)
values (2, 'admin', 'dbe745d59479077a7d5401c32e36caf1', 'admin', null, null, 1, 1, to_date('17-03-2022 14:51:10', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0, 1);