/*==============================================================*/
/* ��ʼ��TBL_DEVICETYPEINFO����                                          */
/*==============================================================*/
insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (2, 1, 1, '���ͻ�');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (3, 1, 2, '�ݸ˱�');

insert into TBL_DEVICETYPEINFO (ID, PARENTID, SORTNUM, NAME)
values (1, 0, 0, '�豸���͸��ڵ�');

/*==============================================================*/
/* ��ʼ��tbl_module����                                          */
/*==============================================================*/
insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (9999, 0, '���ܵ���', '���ܵ���', '#', 'Navigation', 1, null, null, 'function', 0, '#', 'Navigation', '���ܵ���', 'Navigation', '���ܵ���');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (1998, 9999, 'ʵʱ���', 'ʵʱ���', 'AP.view.realTimeMonitoring.RealTimeMonitoringInfoView', 'DeviceRealTimeMonitoring', 1010010, null, null, 'realtime', 0, 'AP.controller.frame.MainIframeControl', 'RealTimeMonitoring', 'ʵʱ���', 'RealTimeMonitoring', 'ʵʱ���');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2018, 9999, '��ʷ��ѯ', '��ʷ��ѯ', 'AP.view.historyQuery.HistoryQueryInfoView', 'DeviceHistoryQuery', 1020010, null, null, 'history', 0, 'AP.controller.frame.MainIframeControl', 'HistoryQuery', '��ʷ��ѯ', 'HistoryQuery', '��ʷ��ѯ');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2158, 9999, '��������', '��������', 'AP.view.reportOut.ReportOutDailyReportView', 'DailyReport', 1030010, null, null, 'report', 0, 'AP.controller.frame.MainIframeControl', 'Report', '��������', 'Report', '��������');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2058, 9999, '���ϲ�ѯ', '���ϲ�ѯ', 'AP.view.alarmQuery.AlarmQueryInfoView', 'AlarmQuery', 1040010, null, null, 'alarm', 0, 'AP.controller.frame.MainIframeControl', 'AlarmQuery', '���ϲ�ѯ', 'AlarmQuery', '���ϲ�ѯ');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2038, 9999, '�豸��־', '�豸��־', 'AP.view.log.DeviceOperationLogInfoView', 'DeviceOperationLogQuery', 1050010, null, null, 'log', 0, 'AP.controller.frame.MainIframeControl', 'DeviceLog', '�豸��־', 'DeviceLog', '�豸��־');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2238, 9999, 'ϵͳ��־', 'ϵͳ��־', 'AP.view.log.SystemLogInfoView', 'SystemLogQuery', 1060010, null, null, 'log', 0, 'AP.controller.frame.MainIframeControl', 'SystemLog', 'ϵͳ��־', 'SystemLog', 'ϵͳ��־');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2179, 9999, '����ά��', '����ά��', 'AP.view.dataMaintaining.CalculateMaintainingInfoView', 'CalculateMaintaining', 1070010, null, null, 'calculate', 0, '#', 'CalculateMaintaining', '����ά��', 'CalculateMaintaining', '����ά��');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (1777, 9999, '��������', '��������', 'AP.view.acquisitionUnit.ProtocolConfigInfoView', 'DriverManagement', 1080010, null, null, 'driverConfig', 0, 'AP.controller.acquisitionUnit.AcquisitionUnitInfoControl', 'DriverConfig', '��������', 'DriverConfig', '��������');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (27, 9999, 'Ȩ�޹���', 'Ȩ�޹���', '#', 'AuthorityManagement', 2030000, null, null, 'right', 0, 'AP.controller.frame.MainIframeControl', 'AuthorityManagement', 'Ȩ�޹���', 'AuthorityManagement', 'Ȩ�޹���');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (24, 27, '��֯�û�', '��֯�û�', 'AP.view.orgAndUser.OrgAndUserInfoView', 'OrganizationAndUserManagement', 2030100, null, null, 'org', 0, 'AP.controller.orgAndUser.OrgAndUserInfoControl', 'OrganizationAndUser', '��֯�û�', 'OrganizationAndUser', '��֯�û�');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (29, 27, '��ɫ����', '��ɫ����', 'AP.view.role.RoleInfoView', 'RoleManagement', 2030300, null, null, 'role', 0, 'AP.controller.role.RoleInfoControl', 'RoleManagement', '��ɫ����', 'RoleManagement', '��ɫ����');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (31, 9999, '�豸����', '�豸����', '#', 'DeviceManagement', 2040000, null, null, 'dataConfig', 0, 'AP.controller.frame.MainIframeControl', 'DeviceManagement', '�豸����', 'DeviceManagement', '�豸����');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (34, 31, '������Ϣ', '������Ϣ', 'AP.view.well.DeviceManagerInfoView', 'WellInformation', 2040100, null, null, 'wellInformation', 0, 'AP.controller.well.WellInfoController', 'DeviceInformation', '������Ϣ', 'DeviceInformation', '������Ϣ');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2218, 31, '�����豸', '�����豸', 'AP.view.well.AuxiliaryDeviceInfoView', 'AuxiliaryDeviceManager', 2040200, null, null, 'auxiliaryDevice', 0, 'AP.controller.well.WellInfoController', 'AuxiliaryDeviceManagement', '�����豸', 'AuxiliaryDeviceManagement', '�����豸');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (2078, 31, '�����豸', '�����豸����', 'AP.view.well.SMSDeviceInfoView', 'SMSDeviceManager', 2040300, null, null, 'smsDevice', 0, 'AP.controller.well.WellInfoController', 'SMSDeviceManager', '�����豸', 'SMSDeviceManager', '�����豸����');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (23, 9999, 'ϵͳ����', 'ϵͳ����', '#', 'SystemManagement', 2090000, null, null, 'system', 0, 'AP.controller.frame.MainIframeControl', 'SystemManagement', 'ϵͳ����', 'SystemManagement', 'ϵͳ����');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (26, 23, 'ģ������', 'ģ������', 'AP.view.module.ModuleInfoView', 'ModuleManagement', 2090100, null, null, 'module', 0, 'AP.controller.module.ModuleInfoControl', 'ModuleManagement', 'ģ������', 'ModuleManagement', 'ģ������');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME_ZH_CN, MD_SHOWNAME_ZH_CN, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL, MD_NAME_EN, MD_NAME_RU, MD_SHOWNAME_EN, MD_SHOWNAME_RU)
values (894, 23, '�ֵ�����', '�ֵ�����', 'AP.view.data.SystemdataInfoView', 'DataDictionaryManagement', 2090200, null, null, 'dictionary', 0, 'AP.controller.data.SystemdataInfoControl', 'DataDictionaryManagement', '�ֵ�����', 'DataDictionaryManagement', '�ֵ�����');

/*==============================================================*/
/* ��ʼ��tbl_role����                                          */
/*==============================================================*/
insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (1, '��������Ա', 1, 1, 1, 'ȫ��Ȩ��');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (2, '�������Ա', 2, 1, 1, '���ݲ�ѯ���༭��Ȩ�޹���');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (3, 'Ӧ�÷���Ա', 3, 0, 0, '���ݲ�ѯ');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, SHOWLEVEL, ROLE_VIDEOKEYEDIT, REMARK)
values (4, 'һ�����Ա', 4, 2, 1, null);

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
values (11, 2078, 1, '1,1,0');

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
values (263, 9999, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (264, 1998, 2, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (265, 2018, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (266, 2158, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (267, 2058, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (268, 2038, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (269, 2179, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (270, 1777, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (271, 27, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (272, 24, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (273, 29, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (274, 31, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (275, 34, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (276, 2218, 2, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (277, 23, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (278, 26, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (279, 894, 2, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (243, 1998, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (244, 2018, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (245, 2158, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (246, 2058, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (247, 2038, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (248, 26, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (249, 894, 3, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (280, 1998, 4, '1,0,1');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (281, 2018, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (282, 2158, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (283, 2058, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (284, 2038, 4, '1,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (285, 2179, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (286, 1777, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (287, 34, 4, '1,1,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (288, 2218, 4, '1,1,0');

/*==============================================================*/
/* ��ʼ��TBL_DEVICETYPE2ROLE����                                          */
/*==============================================================*/
insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (1, 2, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (2, 3, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (3, 1, 1, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (4, 1, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (5, 2, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (6, 3, 2, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (7, 1, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (8, 2, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (9, 3, 3, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (10, 1, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (11, 2, 4, '0,0,0');

insert into TBL_DEVICETYPE2ROLE (RD_ID, RD_DEVICETYPEID, RD_ROLEID, RD_MATRIX)
values (12, 3, 4, '0,0,0');

/*==============================================================*/
/* ��ʼ��tbl_org����                                          */
/*==============================================================*/
insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1, '0000', '��֯���ڵ�', '��֯���ڵ�', 0, null);

/*==============================================================*/
/* ��ʼ��tbl_user����                                          */
/*==============================================================*/
insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL, USER_LANGUAGE)
values (1, 'system', '3c4c1fed0fb2b548f88eab0fcfe0b425', '��������Ա', null, null, 1, 1, to_date('17-03-2022 14:51:10', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0, 1);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL, USER_LANGUAGE)
values (2, 'admin', 'dbe745d59479077a7d5401c32e36caf1', '��������Ա', null, null, 1, 1, to_date('17-03-2022 14:51:10', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0, 1);