/*==============================================================*/
/* ��ʼ��TBL_ACQ_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', '���ͻ�RTU2.0', 'RTU2.0', '�����а�');

/*==============================================================*/
/* ��ʼ��TBL_ACQ_GROUP_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '�ɼ���', 60, 60, 'RTU2.0', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '������', 0, 0, 'RTU2.0', 1, null);

/*==============================================================*/
/* ��ʼ��TBL_ACQ_GROUP2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, UNITID, MATRIX)
values (1, 1, 1, '0,0,0');

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, UNITID, MATRIX)
values (2, 2, 1, '0,0,0');

/*==============================================================*/
/* ��ʼ��TBL_ACQ_ITEM2GROUP_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (1, null, '����ѹ����-ѹ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (2, null, '��Һ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (3, null, '������ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (4, null, 'ϵͳѹ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (5, null, '�������ۼ�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (6, null, '������˲ʱ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (7, null, '�����¶�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (8, null, '��ˮ���ۼ�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (9, null, '��ˮ��˲ʱ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (10, null, '���', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (11, null, '����������ͨѶ״̬', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (12, null, 'ˮ������ͨѶ״̬', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (13, null, '����ѹ����/Һ����ͨѶ״̬', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (14, null, '��Ƶ��ͨѶ״̬', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (15, null, '��Ƶ������Ƶ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (16, null, '��Ƶ������Ƶ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (17, null, '��Ƶ������״̬', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (18, null, '��Ƶ���������', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (19, null, '��Ƶ�������ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (20, null, '��Ƶ�����Ҵ���', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (21, null, '��Ƶ��״̬��1', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (22, null, '��Ƶ��״̬��2', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (23, null, 'ĸ�ߵ�ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (24, null, '�����󾮵���ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (25, null, '����Һ���߶�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (26, null, '�����1СʱҺ���½��ٶ�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (27, null, '�Ų�ģʽ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (28, null, '�Զ��Ų�״̬', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (29, null, '�Զ��Ų�-��СƵ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (30, null, '�Զ��Ų�-���Ƶ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (31, null, '��󲽳���������', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (32, null, '��̵���ʱ����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (33, null, '�Զ�������ʱ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (34, null, '�Զ���������', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (35, null, '������ѹ��������ֵ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (36, null, '����Һ-Ŀ�궨����ÿ�գ�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (37, null, '����Һ-Һ����ͣ��ֵ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (38, null, '����Һ-Һ���ͱ���ֵ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (39, null, '����Һ-Һ������ֵ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (40, null, '����Һ-Һ���߱���ֵ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (41, null, '����ѹ-Ŀ����ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (42, null, '����ѹ-��ѹ��ͣ��ֵ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (43, null, '����ѹ-��ѹ�ͱ���ֵ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (44, null, '����ѹ-��ѹ����ֵ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (45, null, '����ѹ-��ѹ�߱���ֵ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (46, null, 'ú�㶥����', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (47, null, 'ѹ���ư�װ���', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (48, null, '��ͣ����', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (49, null, '��Ƶ������Ƶ��', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (50, null, '�Ų�ģʽ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (51, null, '�Զ��Ų�״̬', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (52, null, '�Զ��Ų�-��СƵ��', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (53, null, '�Զ��Ų�-���Ƶ��', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (54, null, '��󲽳���������', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (55, null, '��̵���ʱ����', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (56, null, '�Զ�������ʱ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (57, null, '�Զ���������', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (58, null, '������ѹ��������ֵ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (59, null, '����Һ-Ŀ�궨����ÿ�գ�', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (60, null, '����Һ-Һ����ͣ��ֵ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (61, null, '����Һ-Һ���ͱ���ֵ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (62, null, '����Һ-Һ������ֵ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (63, null, '����Һ-Һ���߱���ֵ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (64, null, '����ѹ-Ŀ����ѹ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (65, null, '����ѹ-��ѹ��ͣ��ֵ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (66, null, '����ѹ-��ѹ�ͱ���ֵ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (67, null, '����ѹ-��ѹ����ֵ', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (68, null, '����ѹ-��ѹ�߱���ֵ', null, 2, null, '0,0,0');

/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLINSTANCE����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (1, 'RTU2.0ʵ��', 'instance1', 'modbus-rtu', 'modbus-rtu', 1, 'CC01', '0C', 'CC01', '0C', 100, 1, 0, 1, 1, 1);

/*==============================================================*/
/* ��ʼ��TBL_ALARM_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'alarmunit1', 'RTU2.0������Ԫ', 'RTU2.0', null);

/*==============================================================*/
/* ��ʼ��TBL_ALARM_ITEM2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (1, 1, null, '����', 'online', 0, 1.000, null, null, null, 60, 300, 1, 3, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (2, 1, null, '����', 'offline', 0, 0.000, null, null, null, 60, 100, 1, 3, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (3, 1, null, '����', 'run', 0, 1.000, null, null, null, 60, 300, 1, 6, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (4, 1, null, 'ͣ��', 'stop', 0, 0.000, null, null, null, 60, 100, 1, 6, 0, 0, 0);

/*==============================================================*/
/* ��ʼ��tbl_protocolalarminstance����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLALARMINSTANCE (ID, NAME, CODE, ALARMUNITID, DEVICETYPE, SORT)
values (1, 'RTU2.0����ʵ��', 'alarminstance1', 1, 0, 1);

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK)
values (1, 'unit1', 'RTU2.0��ʾ��Ԫ', 'RTU2.0', 1, null);

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (1, null, '����ѹ����-ѹ��', 'c_jxyljyl', 1, 1, null, null, 1, 1, 'c700af', 'c700af', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (2, null, '��Һ��', 'c_dym', 1, null, null, null, null, 2, '00ff00', '00ff00', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (3, null, 'ú�㶥����', 'c_mcdbs', 1, 6, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (4, null, '������ѹ', 'c_jkty', 1, 2, null, null, 2, 2, 'b47832', 'b47832', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (5, null, 'ѹ���ư�װ���', 'c_yljazsd', 1, 7, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (6, null, 'ϵͳѹ��', 'c_xtyl', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (7, null, '��ͣ����', 'c_qtkz', 1, 1, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (8, null, '�������ۼ�', 'c_cqllj', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (9, null, '��Ƶ������Ƶ��', 'c_bpqszpl', 1, 2, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (10, null, '������˲ʱ', 'c_cqlss', 1, 3, null, null, 3, 3, 'ff0000', 'ff0000', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (11, null, '�Ų�ģʽ', 'c_pcms', 1, 3, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (12, null, '�����¶�', 'c_qtwd', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (13, null, '��ˮ���ۼ�', 'c_csllj', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (14, null, '�Զ��Ų�״̬', 'c_zdpczt', 1, 4, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (15, null, '�Զ��Ų�-��СƵ��', 'c_zdpczxpl', 1, 8, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (16, null, '��ˮ��˲ʱ', 'c_cslss', 1, 4, null, null, 4, null, '0400ff', '0400ff', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (17, null, '�Զ��Ų�-���Ƶ��', 'c_zdpczdpl', 1, 9, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (18, null, '��󲽳���������', 'c_zdbcfdxz', 1, 10, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (19, null, '���', 'c_cc', 1, 7, null, null, 7, 7, '78a08c', '78a08c', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (20, null, '��Ƶ������Ƶ��', 'c_bpqyxpl', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (21, null, '��̵���ʱ����', 'c_zddzsjjg', 1, 11, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (22, null, '�Զ�������ʱ', 'c_zdzqys', 1, 12, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (23, null, '��Ƶ���������', 'c_bpqscdl', 1, 5, null, null, 5, 5, 'dbda00', 'dbda00', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (24, null, '��Ƶ�������ѹ', 'c_bpqscdy', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (25, null, '�Զ���������', 'c_zdzqcs', 1, 13, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (26, null, '������ѹ��������ֵ', 'c_jdlybdbjz', 1, 14, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (27, null, '��Ƶ��״̬��1', 'c_bpqztz1', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (28, null, '����Һ-Ŀ�궨����ÿ�գ�', 'c_djymbdjmr', 1, 5, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (29, null, 'ĸ�ߵ�ѹ', 'c_mxdy', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (30, null, '����Һ-Һ����ͣ��ֵ', 'c_djyyzdtjz', 1, 15, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (31, null, '�����󾮵���ѹ', 'c_xzhjdly', 1, 6, null, null, 6, 6, '27701a', '27701a', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (32, null, '����Һ-Һ���ͱ���ֵ', 'c_djyyzdbjz', 1, 16, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (33, null, '����Һ���߶�', 'c_jsyzgd', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (34, null, '����Һ-Һ������ֵ', 'c_djyyzzqz', 1, 17, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (35, null, '�����1СʱҺ���½��ٶ�', 'c_jsj1xsyzxjsd', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (36, null, '����Һ-Һ���߱���ֵ', 'c_djyyzgbjz', 1, 18, null, null, null, null, null, null, 2, '0,0,0');

/*==============================================================*/
/* ��ʼ��tbl_protocoldisplayinstance����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLDISPLAYINSTANCE (ID, NAME, CODE, DISPLAYUNITID, DEVICETYPE, SORT)
values (1, 'RTU2.0��ʾʵ��', 'displayinstance1', 1, 0, 1);

/*==============================================================*/
/* ��ʼ��TBL_REPORT_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (1, 'unit1', 'ú����������Ԫһ', 'CBMWell_heichao', 'CBMWell_heichaoProductionReport', 0, 1);

/*==============================================================*/
/* ��ʼ��TBL_REPORT_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (1, null, '����', 'CalDate', 1, null, null, null, '0,0,0', 3, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (2, null, '����ʱ��', 'RunTime', 2, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (3, null, '���', 'Stroke', 4, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (4, null, '���', 'SPM', 3, null, 1, 'ecd211', '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (5, null, '�ղ�ˮ��', 'WaterVolumetricProduction', 7, null, 2, '00d8ff', '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (6, null, '�ղ�����', 'GasVolumetricProduction', 6, null, 3, '1424f1', '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (7, null, '�ۼƲ�����', 'TotalGasVolumetricProduction', 10, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (8, null, '�ۼƲ�ˮ��', 'TotalWaterVolumetricProduction', 11, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (9, null, '��Һ��', 'ProducingfluidLevel', 5, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (10, null, '��ѹ', 'CasingPressure', 9, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (11, null, '����ѹ��', 'BottomHolePressure', 8, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (12, null, '��ע', 'Remark', 12, null, null, null, '0,0,0', 1, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (13, null, '����', 'WellName', 2, null, null, null, '0,0,0', 1, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (14, null, '����', 'CalDate', 1, null, null, null, '0,0,0', 3, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (15, null, '����ʱ��', 'RunTime', 3, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (16, null, '���', 'Stroke', 5, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (17, null, '���', 'SPM', 4, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (18, null, '�ղ�ˮ��', 'WaterVolumetricProduction', 8, null, 1, '00d8ff', '0,0,0', 2, 1, 1, 1, 1, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (19, null, '�ղ�����', 'GasVolumetricProduction', 7, null, 2, '1424f1', '0,0,0', 2, 1, 1, 1, 1, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (20, null, '�ۼƲ�����', 'TotalGasVolumetricProduction', 11, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (21, null, '�ۼƲ�ˮ��', 'TotalWaterVolumetricProduction', 12, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (22, null, '��Һ��', 'ProducingfluidLevel', 6, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (23, null, '��ѹ', 'CasingPressure', 10, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (24, null, '����ѹ��', 'BottomHolePressure', 9, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (25, null, '��ע', 'Remark', 13, null, null, null, '0,0,0', 1, 1, 0, 0, null, 1);

/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLREPORTINSTANCE����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (1, 'ú����������ʵ��', 'reportinstance1', 0, 1, 1);