/*==============================================================*/
/* ��ʼ��tbl_module����                                          */
/*==============================================================*/
insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (9999, 0, '���ܵ���', '���ܵ���', '#', 'Root', 1, null, null, 'function', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1998, 9999, 'ʵʱ���', 'ʵʱ���', 'AP.view.realTimeMonitoring.RealTimeMonitoringInfoView', 'DeviceRealTimeMonitoring', 1010010, null, null, 'realtime', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (2018, 9999, '��ʷ��ѯ', '��ʷ��ѯ', 'AP.view.historyQuery.HistoryQueryInfoView', 'DeviceHistoryQuery', 1020010, null, null, 'history', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (2158, 9999, '��������', '��������', 'AP.view.reportOut.ReportOutDailyReportView', 'DailyReport', 1030010, null, null, 'report', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (2058, 9999, '���ϲ�ѯ', '���ϲ�ѯ', 'AP.view.alarmQuery.AlarmQueryInfoView', 'AlarmQuery', 1040010, null, null, 'alarm', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (2038, 9999, '��־��ѯ', '��־��ѯ', 'AP.view.log.LogInfoView', 'LogQuery', 1050010, null, null, 'log', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (2179, 9999, '����ά��', '����ά��', 'AP.view.dataMaintaining.CalculateMaintainingInfoView', 'CalculateMaintaining', 1060010, null, null, 'calculate', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1777, 9999, '��������', '��������', 'AP.view.acquisitionUnit.ProtocolConfigInfoView', 'DataSource', 1070010, null, null, 'driverConfig', 0, 'AP.controller.acquisitionUnit.AcquisitionUnitInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (27, 9999, 'Ȩ�޹���', 'Ȩ�޹���', '#', 'right_Ids', 2030000, null, null, 'right', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (24, 27, '��֯�û�', '��֯�û�', 'AP.view.orgAndUser.OrgAndUserInfoView', 'org_OrgInfoTreeGridView', 2030100, null, null, 'org', 0, 'AP.controller.orgAndUser.OrgAndUserInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (29, 27, '��ɫ����', '��ɫ����', 'AP.view.role.RoleInfoView', 'role_Ids', 2030300, null, null, 'role', 0, 'AP.controller.role.RoleInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (31, 9999, '�豸����', '�豸����', '#', 'dataConfig', 2040000, null, null, 'dataConfig', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (34, 31, '������Ϣ', '������Ϣ', 'AP.view.well.DeviceManagerInfoView', 'WellInformation', 2040100, null, null, 'wellInformation', 0, 'AP.controller.well.WellInfoController');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (2118, 31, '���ͻ���Ϣ', '���ͻ���Ϣ', 'AP.view.well.PumpingModelInfoPanel', 'PumpingModelManager', 2040300, null, null, 'pumping', 0, 'AP.controller.well.WellInfoController');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (2078, 31, '�����豸', '�����豸����', 'AP.view.well.SMSDeviceInfoView', 'SMSDeviceManager', 2040400, null, null, 'smsDevice', 0, 'AP.controller.well.WellInfoController');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (23, 9999, 'ϵͳ����', 'ϵͳ����', '#', 'SystemManageent', 2090000, null, null, 'system', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (26, 23, 'ģ������', 'ģ������', 'AP.view.module.ModuleInfoView', 'ModuleConfig', 2090100, null, null, 'module', 0, 'AP.controller.module.ModuleInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (894, 23, '�ֵ�����', '�ֵ�����', 'AP.view.data.SystemdataInfoView', 'DataDictionary', 2090200, null, null, 'dictionary', 0, 'AP.controller.data.SystemdataInfoControl');

/*==============================================================*/
/* ��ʼ��tbl_role����                                          */
/*==============================================================*/
insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, ROLE_FLAG, SHOWLEVEL, REMARK)
values (1, '��������Ա', 1, 1, 1, 'ȫ��Ȩ��');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, ROLE_FLAG, SHOWLEVEL, REMARK)
values (2, '�������Ա', 2, 1, 2, '���ݲ�ѯ���༭��Ȩ�޹���');

insert into TBL_ROLE (ROLE_ID, ROLE_NAME, ROLE_LEVEL, ROLE_FLAG, SHOWLEVEL, REMARK)
values (3, 'Ӧ�÷���Ա', 3, 0, 3, '���ݲ�ѯ');

/*==============================================================*/
/* ��ʼ��tbl_module2role����                                          */
/*==============================================================*/
insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (1, 9999, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (2, 1998, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (3, 2018, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (4, 2058, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (5, 2038, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (6, 27, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (7, 24, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (8, 29, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (9, 31, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (10, 34, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (11, 2078, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (12, 2118, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (13, 1777, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (14, 23, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (15, 26, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (16, 894, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (17, 2158, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (18, 2179, 1, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (19, 9999, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (20, 1998, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (21, 2018, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (22, 2158, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (23, 2058, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (24, 2038, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (25, 2179, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (26, 1777, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (27, 27, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (28, 24, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (29, 29, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (30, 31, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (31, 34, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (32, 2118, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (33, 2078, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (34, 23, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (35, 26, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (36, 894, 2, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (37, 1998, 3, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (38, 2018, 3, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (39, 2158, 3, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (40, 2058, 3, '0,0,0');

insert into TBL_MODULE2ROLE (RM_ID, RM_MODULEID, RM_ROLEID, RM_MATRIX)
values (41, 2038, 3, '0,0,0');

/*==============================================================*/
/* ��ʼ��tbl_org����                                          */
/*==============================================================*/
insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1, '0000', '��֯���ڵ�', '��֯���ڵ�', 0, null);

/*==============================================================*/
/* ��ʼ��tbl_user����                                          */
/*==============================================================*/
insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (1, 'system', '91742dcf6ee79059583f6af36e37d9ff', '��������Ա', null, null, 1, 1, to_date('17-03-2022 14:51:10', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);

insert into TBL_USER (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_PHONE, USER_TYPE, USER_ORGID, USER_REGTIME, USER_QUICKLOGIN, USER_ENABLE, USER_RECEIVESMS, USER_RECEIVEMAIL)
values (2, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '��������Ա', null, null, 1, 1, to_date('17-03-2022 14:51:10', 'dd-mm-yyyy hh24:mi:ss'), 0, 1, 0, 0);