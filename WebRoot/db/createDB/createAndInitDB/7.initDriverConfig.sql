/*==============================================================*/
/* ��ʼ��TBL_PROTOCOL����                                          */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'A11-���ͻ�', 'protocol1', 0, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"��ѹ","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"��ѹ","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�����¶�","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����״̬","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"ͣ��"},{"Value":1,"Meaning":"����"}]},'
  ||'{"Title":"��ͼʵ�����","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ�ɼ�ʱ��","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ����-λ��","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"��ͼ����-�غ�","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ����-����","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ����-����","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive","Meaning":[]}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.NAME='A11-���ͻ�';  
  COMMIT;  
END;  
/

/*==============================================================*/
/* ��ʼ��TBL_ACQ_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', '���ͻ�A11�ɼ���Ԫ', 'A11-���ͻ�', null);

/*==============================================================*/
/* ��ʼ��TBL_ACQ_GROUP_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '�ɼ���', 60, 60, 'A11-���ͻ�', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '������', 0, 0, 'A11-���ͻ�', 1, null);

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
values (1, null, '����״̬', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (2, null, '��ͼʵ�����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (3, null, '��ͼ�ɼ�ʱ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (4, null, '���', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (5, null, '���', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (6, null, '��ͼ����-λ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (7, null, '��ͼ����-�غ�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (8, null, '��ͼ����-����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (9, null, '��ͼ����-����', null, 1, null, '0,0,0');

/*==============================================================*/
/* ��ʼ��TBL_ALARM_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'alarmunit1', 'A11-���ͻ�������Ԫ', 'A11-���ͻ�', null);

/*==============================================================*/
/* ��ʼ��TBL_ALARM_ITEM2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (1, 1, null, '����', '1201', 0, 1201.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (2, 1, null, '����', '1202', 0, 1202.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (3, 1, null, '��������', '1203', 0, 1203.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (4, 1, null, '��Һ����', '1204', 0, 1204.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (5, 1, null, '��Һ����', '1205', 0, 1205.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (6, 1, null, '���', '1206', 0, 1206.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (7, 1, null, '���¶�', '1207', 0, 1207.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (8, 1, null, '����', '1208', 0, 1208.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (9, 1, null, '��Ӱ��', '1209', 0, 1209.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (10, 1, null, '��϶©', '1210', 0, 1210.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (11, 1, null, '�͹�©', '1211', 0, 1211.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (12, 1, null, '�ζ�����©ʧ', '1212', 0, 1212.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (13, 1, null, '�̶�����©ʧ', '1213', 0, 1213.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (14, 1, null, '˫����©ʧ', '1214', 0, 1214.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (15, 1, null, '�ζ�����ʧ��/�͹�©', '1215', 0, 1215.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (16, 1, null, '�̶�����ʧ��', '1216', 0, 1216.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (17, 1, null, '˫����ʧ��', '1217', 0, 1217.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (18, 1, null, '���������', '1218', 0, 1218.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (19, 1, null, '����', '1219', 0, 1219.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (20, 1, null, '����/�ײ�����/δ�빤��Ͳ', '1220', 0, 1220.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (21, 1, null, '�����ѳ�����Ͳ', '1221', 0, 1221.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (22, 1, null, '�˶���', '1222', 0, 1222.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (23, 1, null, '��(��)��', '1223', 0, 1223.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (24, 1, null, '����', '1224', 0, 1224.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (25, 1, null, '���ؽ���', '1225', 0, 1225.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (26, 1, null, '��ɰ', '1226', 0, 1226.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (27, 1, null, '���س�ɰ', '1227', 0, 1227.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (28, 1, null, '�����غɴ�', '1230', 0, 1230.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (29, 1, null, 'Ӧ������', '1231', 0, 1231.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (30, 1, null, '�ɼ��쳣', '1232', 0, 1232.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (31, 1, null, 'ͣ��', 'stop', 0, 0.000, null, null, null, 60, 100, 1, 6, 0, 0, 0);

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK)
values (1, 'unit1', '���ͻ�A11��ʾ��Ԫ', 'A11-���ͻ�', 1, null);

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (1, null, '����״̬', 'c_yxzt', 1, 4, null, null, 0, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (2, null, '���', 'c_cc', 1, 17, null, null, 0, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (3, null, '���', 'c_cc1', 1, 14, null, null, 0, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (4, null, '����ʱ��', 'CommTime', 1, 1, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (5, null, '����ʱ��', 'CommTimeEfficiency', 1, 2, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (6, null, '��������', 'CommRange', 1, 3, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (7, null, '����ʱ��', 'RunTime', 1, 7, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (8, null, '����ʱ��', 'RunTimeEfficiency', 1, 8, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (9, null, '��������', 'RunRange', 1, 9, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (10, null, '����', 'ResultName', 1, 10, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (11, null, '����غ�', 'FMax', 1, 20, null, null, 1, '0,0,0', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b824e6"}', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"b824e6"}');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (12, null, '��С�غ�', 'FMin', 1, 23, null, null, 1, '0,0,0', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e25e1d"}', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e25e1d"}');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (13, null, '�������', 'PlungerStroke', 1, 15, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (14, null, '������Ч���', 'AvailablePlungerStroke', 1, 18, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (15, null, '���������Ч���', 'NoLiquidAvailablePlungerStroke', 1, 27, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (16, null, '����ϵ��', 'FullnessCoefficient', 1, 21, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (17, null, '��ճ���ϵ��', 'NoLiquidFullnessCoefficient', 1, 24, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (18, null, '�������غ�', 'UpperLoadLine', 1, 26, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (19, null, '�������غ�', 'LowerLoadLine', 1, 29, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (20, null, '��������', 'TheoreticalProduction', 1, 25, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (21, null, '��Һ��', 'LiquidVolumetricProduction', 1, 13, null, null, 1, '0,0,0', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (22, null, '������', 'OilVolumetricProduction', 1, 16, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (23, null, '��ˮ��', 'WaterVolumetricProduction', 1, 19, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (24, null, '������Ч��̼������', 'AvailablePlungerStrokeProd_v', 1, 28, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (25, null, '�ü�϶©ʧ��', 'PumpClearanceleakProd_v', 1, 30, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (26, null, '�ۼƲ�Һ��', 'LiquidVolumetricProduction_l', 1, 22, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (27, null, '�й�����', 'AverageWatt', 1, 44, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (28, null, '��˹���', 'PolishRodPower', 1, 47, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (29, null, 'ˮ����', 'WaterPower', 1, 41, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (30, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 43, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (31, null, '����Ч��', 'WellDownSystemEfficiency', 1, 46, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (32, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 40, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (33, null, '��ͼ���', 'Area', 1, 42, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (34, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 45, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (35, null, '���͸��쳤��', 'RodFlexLength', 1, 31, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (36, null, '�͹�������', 'TubingFlexLength', 1, 32, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (37, null, '�����غ�����', 'InertiaLength', 1, 33, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (38, null, '�����ʧϵ��', 'PumpEff1', 1, 34, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (39, null, '����ϵ��', 'PumpEff2', 1, 35, null, null, 1, '0,0,0', '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d4bf24"}', '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d4bf24"}');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (40, null, '��϶©ʧϵ��', 'PumpEff3', 1, 36, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (41, null, 'Һ������ϵ��', 'PumpEff4', 1, 37, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (42, null, '�ܱ�Ч', 'PumpEff', 1, 38, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (43, null, '�����ѹ��', 'PumpIntakeP', 1, 61, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (44, null, '������¶�', 'PumpIntakeT', 1, 62, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (45, null, '����ھ͵���Һ��', 'PumpIntakeGOL', 1, 63, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (46, null, '�����ճ��', 'PumpIntakeVisl', 1, 64, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (47, null, '�����ԭ�����ϵ��', 'PumpIntakeBo', 1, 65, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (48, null, '�ó���ѹ��', 'PumpOutletP', 1, 67, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (49, null, '�ó����¶�', 'PumpOutletT', 1, 68, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (50, null, '�ó��ھ͵���Һ��', 'PumpOutletGOL', 1, 69, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (51, null, '�ó���ճ��', 'PumpOutletVisl', 1, 70, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (52, null, '�ó���ԭ�����ϵ��', 'PumpOutletBo', 1, 71, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (53, null, '�ϳ��������', 'UpStrokeIMax', 1, 50, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (54, null, '�³��������', 'DownStrokeIMax', 1, 51, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (55, null, '�ϳ�������', 'UpStrokeWattMax', 1, 53, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (56, null, '�³�������', 'DownStrokeWattMax', 1, 54, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (57, null, '����ƽ���', 'IDegreeBalance', 1, 49, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (58, null, '����ƽ���', 'WattDegreeBalance', 1, 52, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (59, null, '�ƶ�����', 'DeltaRadius', 1, 55, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (60, null, 'Һ��У����ֵ', 'LevelDifferenceValue', 1, 59, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (61, null, '���ݶ�Һ��', 'CalcProducingfluidLevel', 1, 58, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (62, null, '���õ���', 'TodayKWattH', 1, 56, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (63, null, 'ԭ���ܶ�', 'CrudeOilDensity', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (64, null, 'ˮ�ܶ�', 'WaterDensity', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (65, null, '��Ȼ������ܶ�', 'NaturalGasRelativeDensity', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (66, null, '����ѹ��', 'SaturationPressure', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (67, null, '�Ͳ��в����', 'ReservoirDepth', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (68, null, '�Ͳ��в��¶�', 'ReservoirTemperature', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (69, null, '��ѹ', 'TubingPressure', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (70, null, '��ѹ', 'CasingPressure', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (71, null, '�����¶�', 'WellHeadTemperature', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (72, null, '��ˮ��', 'WaterCut', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (73, null, '�������ͱ�', 'ProductionGasOilRatio', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (74, null, '��Һ��', 'ProducingfluidLevel', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (75, null, '�ù�', 'PumpSettingDepth', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (76, null, '�þ�', 'PumpBoreDiameter', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (77, null, '����Һ��У��ֵ', 'LevelCorrectValue', 1, null, null, null, 3, '0,0,0', null, null);

/*==============================================================*/
/* ��ʼ��TBL_REPORT_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (1, 'unit1', '���ͻ�������Ԫһ', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 0, 1);

/*==============================================================*/
/* ��ʼ��TBL_REPORT_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (1, null, '����', 'WellName', 1, 2, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (2, null, '����', 'CalDate', 1, 3, null, 0, 0, null, 3, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (3, null, '����ʱ��', 'CommTime', 1, 4, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (4, null, '����ʱ��', 'CommTimeEfficiency', 1, 6, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (5, null, '��������', 'CommRange', 1, 5, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (6, null, '����ʱ��', 'RunTime', 1, 7, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (7, null, '����ʱ��', 'RunTimeEfficiency', 1, 9, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (8, null, '��������', 'RunRange', 1, 8, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (9, null, '����', 'ResultName', 1, 10, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (10, null, '�Ż�����', 'OptimizationSuggestion', 1, 11, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (11, null, '����ϵ��', 'FullnessCoefficient', 1, 16, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (12, null, '�ղ�Һ��', 'LiquidWeightProduction', 1, 12, null, 0, 0, 1, 2, 1, '0,0,0', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d42626"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (13, null, '�ղ�����', 'OilWeightProduction', 1, 13, null, 0, 0, 1, 2, 1, '0,0,0', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"ebbc1a"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (14, null, '�ղ�ˮ��', 'WaterWeightProduction', 1, 14, null, 0, 0, 1, 2, 1, '0,0,0', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1beef3"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (15, null, '������ˮ��', 'WeightWaterCut', 1, 15, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (16, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 24, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (17, null, '����Ч��', 'WellDownSystemEfficiency', 1, 25, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (18, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 23, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (19, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 26, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (20, null, '����ƽ���', 'IDegreeBalance', 1, 21, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (21, null, '����ƽ���', 'WattDegreeBalance', 1, 20, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (22, null, '�ƶ�����', 'DeltaRadius', 1, 22, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (23, null, '���õ���', 'TodayKWattH', 1, 27, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (24, null, '�ù�', 'PumpSettingDepth', 1, 17, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (25, null, '��Һ��', 'ProducingfluidLevel', 1, 18, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (26, null, '��û��', 'Submergence', 1, 19, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (27, null, '��ע', 'Remark', 1, 28, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (28, null, '����', 'WellName', 1, 2, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (29, null, '����', 'CalDate', 1, 3, null, null, null, null, 3, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (30, null, '����ʱ��', 'CommTime', 1, 4, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (31, null, '����ʱ��', 'CommTimeEfficiency', 1, 6, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (32, null, '��������', 'CommRange', 1, 5, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (33, null, '����ʱ��', 'RunTime', 1, 7, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (34, null, '����ʱ��', 'RunTimeEfficiency', 1, 9, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (35, null, '��������', 'RunRange', 1, 8, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (36, null, '����', 'ResultName', 1, 10, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (37, null, '�Ż�����', 'OptimizationSuggestion', 1, 11, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (38, null, '����ϵ��', 'FullnessCoefficient', 1, 16, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (39, null, '�ղ�Һ��', 'LiquidWeightProduction', 1, 12, null, null, null, null, 2, 0, '0,0,0', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d12b2b"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (40, null, '�ղ�����', 'OilWeightProduction', 1, 13, null, null, null, null, 2, 0, '0,0,0', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e99314"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (41, null, '�ղ�ˮ��', 'WaterWeightProduction', 1, 14, null, null, null, null, 2, 0, '0,0,0', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"19c2eb"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (42, null, '������ˮ��', 'WeightWaterCut', 1, 15, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (43, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 24, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (44, null, '����Ч��', 'WellDownSystemEfficiency', 1, 25, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (45, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 23, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (46, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 26, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (47, null, '����ƽ���', 'IDegreeBalance', 1, 21, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (48, null, '����ƽ���', 'WattDegreeBalance', 1, 20, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (49, null, '�ƶ�����', 'DeltaRadius', 1, 22, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (50, null, '���õ���', 'TodayKWattH', 1, 27, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (51, null, '�ù�', 'PumpSettingDepth', 1, 17, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (52, null, '���ݶ�Һ��', 'CalcProducingfluidLevel', 1, 18, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (53, null, '��û��', 'Submergence', 1, 19, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (54, null, '��ע', 'Remark', 1, 28, null, null, null, null, 1, 0, '0,0,0', null);

/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLINSTANCE����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT)
values (1, '���ͻ�A11MODBUSʵ��', 'instance1', 'modbus-tcp', 'modbus-tcp', 1, 'BB01', '0B', 0, 1, 'BB01', '0B', 100, 1, 0, 1);

/*==============================================================*/
/* ��ʼ��tbl_protocolalarminstance����                                          */
/*==============================================================*/
insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, DEVICETYPE, SORT)
values (1, '���ͻ�A11����ʵ��', 'alarminstance1', 1, 0, 1);

/*==============================================================*/
/* ��ʼ��tbl_protocoldisplayinstance����                                          */
/*==============================================================*/
insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, DEVICETYPE, SORT)
values (1, '���ͻ�A11��ʾʵ��', 'displayinstance1', 1, 0, 1);

/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLREPORTINSTANCE����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (1, '���ͻ�������ʵ��һ', 'reportinstance1', 0, 1, 1);