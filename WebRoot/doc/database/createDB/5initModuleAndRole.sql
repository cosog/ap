/*==============================================================*/
/* ��ʼ��tbl_module����                                          */
/*==============================================================*/
insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (9999, 0, '���ܵ���', '���ܵ���', '#', 'root', 1, null, null, 'gongneng', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1757, 9999, '��ͼ���', '��ͼ���', '#', 'productionWell_Root', 10100, null, null, 'ProductionWell', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (5, 1757, 'ʵʱ����', '�������', 'AP.view.diagnosis.SingleDetailsInfoView', 'FSDiagramAnalysis_FSDiagramAnalysisSingleDetails', 1010010, null, null, 'Diagnosis', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1678, 1757, 'ȫ������', '������ȫ�����', 'AP.view.diagnosisTotal.DiagnosisTotalInfoView', 'diagnosisTotal_DiagnosisTotalData', 1010020, null, null, 'Compute', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (554, 1757, '��������', '�ɳ����ձ�', 'AP.view.reportOut.ReportOutDayReportView', 'report_DiagnosisDayReport', 1010030, null, null, 'Report', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1315, 1757, 'ͼ�β�ѯ', '���湦ͼ', 'AP.view.graphicalQuery.SurfaceCardQueryView', 'graphicalQuery_SurfaceCardQuery', 1010040, null, null, 'Image', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (2001, 1757, 'WebSocket����', 'WebSocket����', 'AP.view.websocketTest.WebSocketTestInfoView', 'WebSocketTest', 1010050, null, null, 'Device', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1958, 9999, '���ز��', '���ز��', '#', 'a9DeviceTest_Root', 10200, null, null, 'WellInfo', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1938, 1958, '��������', '��������', 'AP.view.kafkaConfig.KafkaConfigInfoView', 'kafkaConfig_kafkaConfigGridPanel', 1020010, null, null, 'Measure', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1959, 1958, 'ԭʼ����', 'ԭʼ����', 'AP.view.kafkaConfig.A9RawDataInfoView', 'kafkaConfig_A9RawDataGridPanel', 1020020, null, null, 'Curve', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (27, 9999, 'Ȩ�޹���', 'Ȩ�޹���', '#', 'right_Ids', 2030000, null, null, 'Right', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (24, 27, '��λ����', '��λ����', 'AP.view.org.OrgInfoView', 'org_OrgInfoTreeGridView', 2030100, null, null, 'Org', 0, 'AP.controller.org.OrgInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (28, 27, '�û�����', '�û�����', 'AP.view.user.UserPanelInfoView', 'user_UserInfoGridPanel', 2030200, null, null, 'User', 0, 'AP.controller.user.UserPanelInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (29, 27, '��ɫ����', '��ɫ����', 'AP.view.role.RoleInfoView', 'role_Ids', 2030300, null, null, 'Role', 0, 'AP.controller.role.RoleInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (31, 9999, '��������', '��������', '#', 'basicdata_Ids', 2040000, null, null, 'DataConfig', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1777, 31, '����Դ', '����Դ', 'AP.view.acquisitionUnit.ScadaConfigInfoView', 'ScadaConfigInfoView_Id', 2040100, null, null, 'DataConfig', 0, 'AP.controller.acquisitionUnit.AcquisitionUnitInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (34, 31, '������Ϣ', '������Ϣ', 'AP.view.well.WellInfoView', 'well_wellPanel', 2040200, null, null, 'SingleWell', 0, 'AP.controller.well.WellInfoController');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (35, 31, '��������', '��������', 'AP.view.productionData.ProductiondDataUIInfoView', 'outWellProduce_ProductionOutInfoGridPanel', 2040300, null, null, 'Res', 0, 'AP.controller.productionData.ProductionOutInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (36, 31, '����켣', '����켣', 'AP.view.wellboreTrajectory.WellboreTrajectoryInfoView', 'well_WellboreTrajectory', 2040400, null, null, 'bdbj', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1718, 31, '���ͻ���Ϣ', '�繦ͼ����-���ͻ���Ϣ', 'AP.view.PSToFS.PumpingUnitInfoView', 'PSToFS_PumpingUnitInfo', 2040500, null, null, 'Pumping', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (2000, 9999, '����ά��', '����ά��', '#', 'dataMaintenance_root', 2070000, null, null, 'MeasureProData', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1797, 2000, '����ά��', '����ά��', 'AP.view.calculateManager.CalculateManagerInfoView', 'calculate_calculateManager', 2070100, null, null, 'Calculate', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1515, 2000, '��ͼ�ϴ�', '��ͼ�ϴ�', 'AP.view.graphicalUpload.GraphicalUploadInfoView', 'graphicalUpload', 2070200, null, null, 'Upload', 0, 'AP.controller.graphicalUpload.GraphicalUploadController');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1877, 2000, 'ͼ���Ż�', '��η��ݲ����Ż�', 'AP.view.electricAnalysis.InverOptimizeInfoView', 'PSToFS_InverOptimizeInfo', 2070300, null, null, 'Compute', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (23, 9999, 'ϵͳ����', 'ϵͳ����', '#', 'SystemManageent', 2090000, null, null, 'System', 0, 'AP.controller.frame.MainIframeControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (26, 23, 'ģ������', 'ģ������', 'AP.view.module.ModuleInfoView', 'module_Ids', 2090100, null, null, 'Module', 0, 'AP.controller.module.ModuleInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (894, 23, '�ֵ�����', '�ֵ�����', 'AP.view.data.SystemdataInfoView', 'dataqaz', 2090200, null, null, 'Dictionary', 0, 'AP.controller.data.SystemdataInfoControl');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (1697, 23, 'ͳ������', 'ͳ����ֵ����', 'AP.view.statSet.StatItemsSetInfoView', 'statSet_statItemsSet', 2090300, null, null, 'Stat', 0, '#');

insert into tbl_module (MD_ID, MD_PARENTID, MD_NAME, MD_SHOWNAME, MD_URL, MD_CODE, MD_SEQ, MD_LEVEL, MD_FLAG, MD_ICON, MD_TYPE, MD_CONTROL)
values (47, 23, '��������', '��������', 'AP.view.alarmSet.AlarmSetInfoView', 'alarmSet_Alarm', 2090400, null, null, 'Alarm', 0, 'AP.controller.alarmSet.AlarmSetInfoController');

/*==============================================================*/
/* ��ʼ��tbl_role����                                          */
/*==============================================================*/
insert into tbl_role (ROLE_CODE, ROLE_NAME, ROLE_FLAG, ROLE_ID, REMARK)
values ('fsdiagramSystem', '��ͼ�������Ա', 1, 302, '��ͼ������ݲ�ѯ�����ݱ༭��Ȩ�޹���Ȩ��');

insert into tbl_role (ROLE_CODE, ROLE_NAME, ROLE_FLAG, ROLE_ID, REMARK)
values ('fsdiagranAnalysis', '��ͼ�������Ա', 0, 322, '��ͼ������ݲ�ѯȨ��');

insert into tbl_role (ROLE_CODE, ROLE_NAME, ROLE_FLAG, ROLE_ID, REMARK)
values ('sysAdmin', 'ϵͳ����', 1, 141, '�������Ȩ��');

/*==============================================================*/
/* ��ʼ��tbl_module2role����                                          */
/*==============================================================*/
insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (9999, '0,0,0', 118456, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1757, '0,0,0', 118457, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (5, '0,0,0', 118458, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1678, '0,0,0', 118459, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (554, '0,0,0', 118460, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1315, '0,0,0', 118461, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (2001, '0,0,0', 118462, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1958, '0,0,0', 118463, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1938, '0,0,0', 118464, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1959, '0,0,0', 118465, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (27, '0,0,0', 118466, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (24, '0,0,0', 118467, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (28, '0,0,0', 118468, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (29, '0,0,0', 118469, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (31, '0,0,0', 118470, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (34, '0,0,0', 118471, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (35, '0,0,0', 118472, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (36, '0,0,0', 118473, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1718, '0,0,0', 118474, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1777, '0,0,0', 118478, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (2000, '0,0,0', 118479, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1797, '0,0,0', 118480, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1515, '0,0,0', 118481, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1877, '0,0,0', 118482, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (23, '0,0,0', 118483, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (26, '0,0,0', 118484, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (894, '0,0,0', 118485, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1697, '0,0,0', 118486, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (47, '0,0,0', 118487, 141);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1757, '0,0,0', 119215, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (5, '0,0,0', 119216, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1678, '0,0,0', 119217, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (554, '0,0,0', 119218, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1315, '0,0,0', 119219, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (27, '0,0,0', 119220, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (24, '0,0,0', 119221, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (28, '0,0,0', 119222, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (31, '0,0,0', 119223, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1777, '0,0,0', 119224, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (34, '0,0,0', 119225, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (35, '0,0,0', 119226, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (36, '0,0,0', 119227, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1718, '0,0,0', 119228, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (2000, '0,0,0', 119229, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1797, '0,0,0', 119230, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (23, '0,0,0', 119231, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (894, '0,0,0', 119232, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1697, '0,0,0', 119233, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (47, '0,0,0', 119234, 302);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1757, '0,0,0', 119136, 322);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (5, '0,0,0', 119137, 322);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1678, '0,0,0', 119138, 322);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (554, '0,0,0', 119139, 322);

insert into TBL_MODULE2ROLE (RM_MODULEID, RM_MATRIX, RM_ID, RM_ROLEID)
values (1315, '0,0,0', 119140, 322);

/*==============================================================*/
/* ��ʼ��tbl_user����                                          */
/*==============================================================*/
insert into tbl_user (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_OUT_EMAIL, USER_PHONE, USER_MOBILE, USER_ADDRESS, USER_POSTCODE, USER_TITLE, USER_TYPE, USER_ORGID, USER_ISLEADER, USER_REGTIME, USER_STYLE, USER_QUICKLOGIN)
values (4, 'admin', 'dow9Pmhb0jN6c', '����Ա', '', '', '', '', '', '', '0', 302, 0, '', to_date('30-11-2020 17:25:43', 'dd-mm-yyyy hh24:mi:ss'), '', 0);

insert into tbl_user (USER_NO, USER_ID, USER_PWD, USER_NAME, USER_IN_EMAIL, USER_OUT_EMAIL, USER_PHONE, USER_MOBILE, USER_ADDRESS, USER_POSTCODE, USER_TITLE, USER_TYPE, USER_ORGID, USER_ISLEADER, USER_REGTIME, USER_STYLE, USER_QUICKLOGIN)
values (1, 'system', 'doRhNKMI28DnI', 'ϵͳ����Ա', '', '', '', '', '', '', '5', 141, 0, '', to_date('15-10-2013 17:21:16', 'dd-mm-yyyy hh24:mi:ss'), '', 0);