/*==============================================================*/
/* ��ʼ��TBL_PROTOCOL����                                          */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'A11-���ͻ�', 'protocol1', 0, 1);

insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (2, 'A11-�ݸ˱�', 'protocol2', 1, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"���ⱨ������λ","Addr":3,"StoreDataType":"bit","IFDataType":"bool","Prec":0,"Quantity":3,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":0,"AcqMode":"passive","Meaning":[]},{"Title":"��ѹ","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ѹ","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ѹ","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�����¶�","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"����״̬","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"ͣ��"},{"Value":1,"Meaning":"����"}]},{"Title":"��ͣ����","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"����"},{"Value":2,"Meaning":"ͣ��"}]},{"Title":"��ˮ��","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Һ��","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"����¶�","Addr":40331,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�̸����¶�","Addr":40333,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A�����","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B�����","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C�����","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"A���ѹ","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B���ѹ","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C���ѹ","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�й�����","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW��h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�޹�����","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar��h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�й�����","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"�޹�����","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},{"Title":"������","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��������","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Ƶ����Ƶ��","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Ƶ����Ƶ��","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ�ɼ����","Addr":40981,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"min","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"��ͼ���õ���","Addr":40983,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼʵ�����","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ�ɼ�ʱ��","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"��ͼ����-λ��","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ����-�غ�","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ����-����","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ����-����","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive","Meaning":[]}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.NAME='A11-���ͻ�';  
  COMMIT;  
END;  
/

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"��ѹ","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"��ѹ","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ѹ","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�����¶�","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����״̬","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"ͣ��"},'
  ||'{"Value":1,"Meaning":"����"}]},{"Title":"��ͣ����","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"����"},{"Value":2,"Meaning":"ͣ��"}]},{"Title":"��ˮ��","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Һ��","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A�����","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"B�����","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C�����","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A���ѹ","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B���ѹ","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C���ѹ","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�й�����","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW��h","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"�޹�����","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar��h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�й�����","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�޹�����","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},{"Title":"������","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��������","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"��Ƶ����Ƶ��","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Ƶ����Ƶ��","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�ݸ˱�ת��","Addr":40430,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"r/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�ݸ˱�Ť��","Addr":40432,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kN��m","ResolutionMode":2,"AcqMode":"passive"}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.NAME='A11-���ͻ�';  
  COMMIT;  
END;  
/

/*==============================================================*/
/* ��ʼ��TBL_ACQ_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', '���ͻ�A11�ɼ���Ԫ', 'A11-���ͻ�', null);

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (2, 'unit2', '�ݸ˱�A11�ɼ���Ԫ', 'A11-�ݸ˱�', null);

/*==============================================================*/
/* ��ʼ��TBL_ACQ_GROUP_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '�ɼ���', 60, 60, 'A11-���ͻ�', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '������', 0, 0, 'A11-���ͻ�', 1, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (3, 'group3', '�ɼ���', 60, 60, 'A11-�ݸ˱�', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (4, 'group4', '������', 0, 0, 'A11-�ݸ˱�', 1, null);

/*==============================================================*/
/* ��ʼ��TBL_ACQ_GROUP2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, UNITID, MATRIX)
values (1, 1, 1, '0,0,0');

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, UNITID, MATRIX)
values (2, 2, 1, '0,0,0');

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, UNITID, MATRIX)
values (3, 3, 2, '0,0,0');

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, UNITID, MATRIX)
values (4, 4, 2, '0,0,0');

/*==============================================================*/
/* ��ʼ��TBL_ACQ_ITEM2GROUP_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (1, null, '��ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (2, null, '��ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (3, null, '��ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (4, null, '�����¶�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (5, null, '��ˮ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (6, null, '����״̬', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (7, null, '��ͣ����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (8, null, '����¶�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (9, null, '�̸����¶�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (10, null, 'A�����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (11, null, 'B�����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (12, null, 'C�����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (13, null, 'A���ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (14, null, 'B���ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (15, null, 'C���ѹ', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (16, null, '�й�����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (17, null, '�޹�����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (18, null, '�й�����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (19, null, '�޹�����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (20, null, '������', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (21, null, '��������', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (22, null, '��Ƶ����Ƶ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (23, null, '��Ƶ����Ƶ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (24, null, '��ͼ�ɼ����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (25, null, '��ͼ���õ���', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (26, null, '��ͼʵ�����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (27, null, '��ͼ�ɼ�ʱ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (28, null, '���', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (58, null, '���', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (59, null, '��ͼ����-λ��', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (60, null, '��ͼ����-�غ�', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (61, null, '��ͼ����-����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (62, null, '��ͼ����-����', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (29, null, '��ͣ����', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (30, null, '��Ƶ����Ƶ��', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (31, null, '��ͼ�ɼ����', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (32, null, '��ͼ���õ���', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (33, null, '��ѹ', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (34, null, '��ѹ', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (35, null, '��ѹ', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (36, null, '�����¶�', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (37, null, '��ˮ��', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (38, null, '����״̬', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (39, null, '��ͣ����', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (40, null, 'A�����', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (41, null, 'B�����', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (42, null, 'C�����', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (43, null, 'A���ѹ', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (44, null, 'B���ѹ', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (45, null, 'C���ѹ', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (46, null, '�й�����', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (47, null, '�޹�����', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (48, null, '�й�����', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (49, null, '�޹�����', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (50, null, '������', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (51, null, '��������', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (52, null, '��Ƶ����Ƶ��', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (53, null, '��Ƶ����Ƶ��', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (54, null, '�ݸ˱�ת��', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (55, null, '�ݸ˱�Ť��', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (56, null, '��ͣ����', null, 4, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (57, null, '��Ƶ����Ƶ��', null, 4, null, '0,0,0');

/*==============================================================*/
/* ��ʼ��TBL_ALARM_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'alarmunit1', 'A11-���ͻ�������Ԫ', 'A11-���ͻ�', null);

insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (2, 'alarmunit2', '�ݸ˱�A11������Ԫ', 'A11-�ݸ˱�', null);

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

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (5, 1, null, '����', '1201', 0, 1201.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (6, 1, null, '����', '1202', 0, 1202.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (7, 1, null, '��������', '1203', 0, 1203.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (8, 1, null, '��Һ����', '1204', 0, 1204.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (9, 1, null, '��Һ����', '1205', 0, 1205.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (10, 1, null, '���', '1206', 0, 1206.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (11, 1, null, '���¶�', '1207', 0, 1207.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (12, 1, null, '����', '1208', 0, 1208.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (13, 1, null, '��Ӱ��', '1209', 0, 1209.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (14, 1, null, '��϶©', '1210', 0, 1210.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (15, 1, null, '�͹�©', '1211', 0, 1211.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (16, 1, null, '�ζ�����©ʧ', '1212', 0, 1212.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (17, 1, null, '�̶�����©ʧ', '1213', 0, 1213.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (18, 1, null, '˫����©ʧ', '1214', 0, 1214.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (19, 1, null, '�ζ�����ʧ��/�͹�©', '1215', 0, 1215.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (20, 1, null, '�̶�����ʧ��', '1216', 0, 1216.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (21, 1, null, '˫����ʧ��', '1217', 0, 1217.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (22, 1, null, '���������', '1218', 0, 1218.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (23, 1, null, '����', '1219', 0, 1219.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (24, 1, null, '����/�ײ�����/δ�빤��Ͳ', '1220', 0, 1220.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (25, 1, null, '�����ѳ�����Ͳ', '1221', 0, 1221.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (26, 1, null, '�˶���', '1222', 0, 1222.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (27, 1, null, '��(��)��', '1223', 0, 1223.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (28, 1, null, '����', '1224', 0, 1224.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (29, 1, null, '���ؽ���', '1225', 0, 1225.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (30, 1, null, '��ɰ', '1226', 0, 1226.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (31, 1, null, '���س�ɰ', '1227', 0, 1227.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (32, 1, null, '�����غɴ�', '1230', 0, 1230.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (33, 1, null, 'Ӧ������', '1231', 0, 1231.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (34, 1, null, '�ɼ��쳣', '1232', 0, 1232.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (35, 2, null, '����', 'online', 0, 1.000, null, null, null, 60, 300, 1, 3, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (36, 2, null, '����', 'offline', 0, 0.000, null, null, null, 60, 100, 1, 3, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (37, 2, null, '����', 'run', 0, 1.000, null, null, null, 60, 300, 1, 6, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (38, 2, null, 'ͣ��', 'stop', 0, 0.000, null, null, null, 60, 100, 1, 6, 0, 0, 0);

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK)
values (1, 'unit1', '���ͻ�A11��ʾ��Ԫ', 'A11-���ͻ�', 1, null);

insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK)
values (2, 'unit2', '�ݸ˱�A11��ʾ��Ԫ', 'A11-�ݸ˱�', 2, null);

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (1, null, '��ѹ', 'c_yy', 1, 73, null, 1, 0, '{"sort":3,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"2500ff"}', '{"sort":3,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":true,"color":"2500ff"}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (2, null, '����ʱ��', 'CommTime', 1, 1, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (3, null, '��ѹ', 'c_ty', 1, 74, null, 1, 0, '{"sort":4,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"374140"}', '{"sort":4,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":true,"color":"374140"}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (4, null, '����ʱ��', 'CommTimeEfficiency', 1, 2, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (5, null, '��ͣ����', 'c_qtkz', 1, 1, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (6, null, '��ѹ', 'c_hy', 1, 75, null, 1, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (7, null, '��Ƶ����Ƶ��', 'c_bpszpl', 1, 2, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (8, null, '��������', 'CommRange', 1, 3, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (9, null, '�����¶�', 'c_jkwd', 1, 76, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (10, null, '����ʱ��', 'RunTime', 1, 7, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (11, null, '����״̬', 'c_yxzt', 1, 4, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (12, null, '����ʱ��', 'RunTimeEfficiency', 1, 8, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (13, null, '��ˮ��', 'c_hsl', 1, 79, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (14, null, '��������', 'RunRange', 1, 9, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (15, null, '����¶�', 'c_ggwd', 1, 77, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (16, null, '����', 'ResultName', 1, 10, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (17, null, '�̸����¶�', 'c_pghwd', 1, 78, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (18, null, '����غ�', 'FMax', 1, 20, null, null, 1, '{"sort":1,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"ae1919"}', '{"sort":1,"color":"ae1919","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (19, null, 'A�����', 'c_Axdl', 1, 82, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (20, null, '��С�غ�', 'FMin', 1, 23, null, null, 1, '{"sort":2,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"33a91f"}', '{"sort":2,"color":"33a91f","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (21, null, 'B�����', 'c_Bxdl', 1, 83, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (22, null, '�������', 'PlungerStroke', 1, 15, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (23, null, 'C�����', 'c_Cxdl', 1, 84, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (24, null, '������Ч���', 'AvailablePlungerStroke', 1, 18, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (25, null, 'A���ѹ', 'c_Axdy', 1, 85, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (26, null, 'B���ѹ', 'c_Bxdy', 1, 86, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (27, null, '���������Ч���', 'NoLiquidAvailablePlungerStroke', 1, 27, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (28, null, '����ϵ��', 'FullnessCoefficient', 1, 21, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (29, null, 'C���ѹ', 'c_Cxdy', 1, 87, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (30, null, '��ճ���ϵ��', 'NoLiquidFullnessCoefficient', 1, 24, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (31, null, '�й�����', 'c_yggh', 1, 88, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (32, null, '�������غ�', 'UpperLoadLine', 1, 26, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (33, null, '�޹�����', 'c_wggh', 1, 89, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (34, null, '�������غ�', 'LowerLoadLine', 1, 29, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (35, null, '�й�����', 'c_yggl', 1, 91, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (36, null, '��������', 'TheoreticalProduction', 1, 25, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (37, null, '�޹�����', 'c_wggl', 1, 92, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (38, null, '��Һ��', 'LiquidVolumetricProduction', 1, 13, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (39, null, '��������', 'c_glys', 1, 93, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (40, null, '������', 'OilVolumetricProduction', 1, 16, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (41, null, '��Ƶ����Ƶ��', 'c_bpszpl', 1, 94, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (42, null, '��ˮ��', 'WaterVolumetricProduction', 1, 19, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (43, null, '��Ƶ����Ƶ��', 'c_bpyxpl', 1, 95, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (44, null, '������Ч��̼������', 'AvailablePlungerStrokeProd_v', 1, 28, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (45, null, '���', 'c_cc', 1, 17, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (46, null, '���', 'c_cc1', 1, 14, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (47, null, '�ü�϶©ʧ��', 'PumpClearanceleakProd_v', 1, 30, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (48, null, '�ۼƲ�Һ��', 'LiquidVolumetricProduction_l', 1, 22, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (49, null, '�й�����', 'AverageWatt', 1, 44, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (50, null, '��˹���', 'PolishRodPower', 1, 47, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (51, null, 'ˮ����', 'WaterPower', 1, 41, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (52, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 43, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (53, null, '����Ч��', 'WellDownSystemEfficiency', 1, 46, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (54, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 40, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (55, null, '��ͼ���', 'Area', 1, 42, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (56, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 45, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (57, null, '���͸��쳤��', 'RodFlexLength', 1, 31, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (58, null, '�͹�������', 'TubingFlexLength', 1, 32, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (59, null, '�����غ�����', 'InertiaLength', 1, 33, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (60, null, '�����ʧϵ��', 'PumpEff1', 1, 34, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (61, null, '����ϵ��', 'PumpEff2', 1, 35, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (62, null, '��϶©ʧϵ��', 'PumpEff3', 1, 36, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (63, null, 'Һ������ϵ��', 'PumpEff4', 1, 37, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (64, null, '�ܱ�Ч', 'PumpEff', 1, 38, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (65, null, '�����ѹ��', 'PumpIntakeP', 1, 61, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (66, null, '������¶�', 'PumpIntakeT', 1, 62, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (67, null, '����ھ͵���Һ��', 'PumpIntakeGOL', 1, 63, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (68, null, '�����ճ��', 'PumpIntakeVisl', 1, 64, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (69, null, '�����ԭ�����ϵ��', 'PumpIntakeBo', 1, 65, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (70, null, '�ó���ѹ��', 'PumpOutletP', 1, 67, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (71, null, '�ó����¶�', 'PumpOutletT', 1, 68, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (72, null, '�ó��ھ͵���Һ��', 'PumpOutletGOL', 1, 69, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (73, null, '�ó���ճ��', 'PumpOutletVisl', 1, 70, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (74, null, '�ó���ԭ�����ϵ��', 'PumpOutletBo', 1, 71, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (75, null, '�ϳ��������', 'UpStrokeIMax', 1, 50, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (76, null, '�³��������', 'DownStrokeIMax', 1, 51, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (77, null, '�ϳ�������', 'UpStrokeWattMax', 1, 53, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (78, null, '�³�������', 'DownStrokeWattMax', 1, 54, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (79, null, '����ƽ���', 'IDegreeBalance', 1, 49, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (80, null, '����ƽ���', 'WattDegreeBalance', 1, 52, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (81, null, '�ƶ�����', 'DeltaRadius', 1, 55, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (82, null, '����Һ��У��ֵ', 'LevelCorrectValue', 1, 59, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (83, null, '��Һ��', 'InverProducingfluidLevel', 1, 58, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (84, null, '���õ���', 'TodayKWattH', 1, 56, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (85, null, '��ѹ', 'c_yy', 2, 37, null, 1, 0, '{"sort":1,"color":"ff0000","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":1,"color":"ff0000","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (86, null, '��ѹ', 'c_ty', 2, 38, null, 1, 0, '{"sort":2,"color":"0008ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":2,"color":"0008ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (87, null, '��ѹ', 'c_hy', 2, 39, null, 1, 0, '{"sort":3,"color":"0c3203","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":3,"color":"0c3203","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (88, null, '�����¶�', 'c_jkwd', 2, 40, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (89, null, '��ˮ��', 'c_hsl', 2, 41, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (90, null, '����״̬', 'c_yxzt', 2, 4, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (91, null, 'A�����', 'c_Axdl', 2, 43, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (92, null, 'B�����', 'c_Bxdl', 2, 44, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (93, null, 'C�����', 'c_Cxdl', 2, 45, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (94, null, 'A���ѹ', 'c_Axdy', 2, 46, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (95, null, 'B���ѹ', 'c_Bxdy', 2, 47, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (96, null, 'C���ѹ', 'c_Cxdy', 2, 48, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (97, null, '�й�����', 'c_yggh', 2, 49, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (98, null, '�޹�����', 'c_wggh', 2, 50, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (99, null, '�й�����', 'c_yggl', 2, 52, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (100, null, '�޹�����', 'c_wggl', 2, 53, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (101, null, '��������', 'c_glys', 2, 54, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (102, null, '��Ƶ����Ƶ��', 'c_bpszpl', 2, 55, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (103, null, '��Ƶ����Ƶ��', 'c_bpyxpl', 2, 56, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (104, null, '�ݸ˱�ת��', 'c_lgbzs', 2, 10, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (105, null, '�ݸ˱�Ť��', 'c_lgbnj', 2, 11, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (106, null, '����ʱ��', 'CommTime', 2, 1, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (107, null, '����ʱ��', 'CommTimeEfficiency', 2, 2, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (108, null, '��������', 'CommRange', 2, 3, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (109, null, '����ʱ��', 'RunTime', 2, 7, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (110, null, '����ʱ��', 'RunTimeEfficiency', 2, 8, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (111, null, '��������', 'RunRange', 2, 9, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (112, null, '��������', 'TheoreticalProduction', 2, 16, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (113, null, '��Һ��', 'LiquidVolumetricProduction', 2, 13, null, null, 1, '{"sort":4,"color":"ff0000","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":4,"color":"ff0000","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (114, null, '������', 'OilVolumetricProduction', 2, 14, null, null, 1, '{"sort":5,"color":"0008ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":5,"color":"0008ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (115, null, '��ˮ��', 'WaterVolumetricProduction', 2, 15, null, null, 1, '{"sort":6,"color":"0c3203","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":6,"color":"0c3203","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (116, null, '�ۼƲ�Һ��', 'LiquidVolumetricProduction_l', 2, 17, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (117, null, '�й�����', 'AverageWatt', 2, 23, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (118, null, 'ˮ����', 'WaterPower', 2, 24, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (119, null, 'ϵͳЧ��', 'SystemEfficiency', 2, 22, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (120, null, '�ݻ�Ч��', 'PumpEff1', 2, 20, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (121, null, 'Һ������ϵ��', 'PumpEff2', 2, 21, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (122, null, '�ܱ�Ч', 'PumpEff', 2, 19, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (123, null, '�����ѹ��', 'PumpIntakeP', 2, 25, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (124, null, '������¶�', 'PumpIntakeT', 2, 26, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (125, null, '����ھ͵���Һ��', 'PumpIntakeGOL', 2, 27, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (126, null, '�����ճ��', 'PumpIntakeVisl', 2, 28, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (127, null, '�����ԭ�����ϵ��', 'PumpIntakeBo', 2, 29, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (128, null, '�ó���ѹ��', 'PumpOutletP', 2, 31, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (129, null, '�ó����¶�', 'PumpOutletT', 2, 32, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (130, null, '�ó��ھ͵���Һ��', 'PumpOutletGOL', 2, 33, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (131, null, '�ó���ճ��', 'PumpOutletVisl', 2, 34, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (132, null, '�ó���ԭ�����ϵ��', 'PumpOutletBo', 2, 35, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (133, null, '���õ���', 'TodayKWattH', 2, 18, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (134, null, '��ͣ����', 'c_qtkz', 2, null, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (135, null, '��Ƶ����Ƶ��', 'c_bpszpl', 2, null, null, null, 2, null, null, '0,0,0');

/*==============================================================*/
/* ��ʼ��TBL_REPORT_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (1, 'unit1', '���ͻ�������Ԫһ', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 0, 1);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (2, 'unit2', 'ú����������Ԫһ', 'CBMWell_heichao', 'CBMWell_heichaoProductionReport', 0, 2);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (3, 'unit3', '�ݸ˱þ�����Ԫһ', 'oilWell_ScrewPump', 'oilWell_ScrewPumpProductionReoirt', 1, 1);
/*==============================================================*/
/* ��ʼ��TBL_REPORT_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (1, null, '����', 'WellName', 2, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (2, null, '����', 'CalDate', 3, null, '0,0,0', 3, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (3, null, '����ʱ��', 'CommTime', 4, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (4, null, '����ʱ��', 'CommTimeEfficiency', 6, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (5, null, '��������', 'CommRange', 5, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (6, null, '����ʱ��', 'RunTime', 7, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (7, null, '����ʱ��', 'RunTimeEfficiency', 9, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (8, null, '��������', 'RunRange', 8, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (9, null, '����', 'ResultName', 10, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (10, null, '�Ż�����', 'OptimizationSuggestion', 11, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (11, null, '����ϵ��', 'FullnessCoefficient', 16, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (12, null, '��Һ��', 'LiquidWeightProduction', 12, null, '0,0,0', 2, 1, 0, 0, null, 0, '{"sort":1,"color":"d72953","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (13, null, '������', 'OilWeightProduction', 13, null, '0,0,0', 2, 1, 0, 0, null, 0, '{"sort":2,"color":"e79819","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (14, null, '��ˮ��', 'WaterWeightProduction', 14, null, '0,0,0', 2, 1, 0, 0, null, 0, '{"sort":3,"color":"2ebdc0","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (15, null, '������ˮ��', 'WeightWaterCut', 15, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (16, null, '����Ч��', 'SurfaceSystemEfficiency', 21, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (17, null, '����Ч��', 'WellDownSystemEfficiency', 22, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (18, null, 'ϵͳЧ��', 'SystemEfficiency', 20, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (19, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 23, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (20, null, '����ƽ���', 'IDegreeBalance', 18, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (21, null, '����ƽ���', 'WattDegreeBalance', 17, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (22, null, '�ƶ�����', 'DeltaRadius', 19, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (23, null, '���õ���', 'TodayKWattH', 24, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (24, null, '��ע', 'Remark', 25, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (25, null, '����', 'WellName', 2, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (26, null, '����', 'CalDate', 3, null, '0,0,0', 3, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (27, null, '����ʱ��', 'CommTime', 4, null, '0,0,0', 2, 1, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (28, null, '����ʱ��', 'CommTimeEfficiency', 6, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (29, null, '��������', 'CommRange', 5, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (30, null, '����ʱ��', 'RunTime', 7, null, '0,0,0', 2, 1, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (31, null, '����ʱ��', 'RunTimeEfficiency', 9, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (32, null, '��������', 'RunRange', 8, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (33, null, '����', 'ResultName', 10, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (34, null, '�Ż�����', 'OptimizationSuggestion', 11, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (35, null, '����ϵ��', 'FullnessCoefficient', 16, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (36, null, '�ղ�Һ��', 'LiquidWeightProduction', 12, null, '0,0,0', 2, 1, 1, 1, 1, 1, '{"sort":1,"color":"d72953","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (37, null, '�ղ�����', 'OilWeightProduction', 13, null, '0,0,0', 2, 1, 1, 1, 1, 1, '{"sort":2,"color":"e79819","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (38, null, '�ղ�ˮ��', 'WaterWeightProduction', 14, null, '0,0,0', 2, 1, 1, 1, 1, 1, '{"sort":3,"color":"2ebdc0","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (39, null, '������ˮ��', 'WeightWaterCut', 15, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (40, null, '����Ч��', 'SurfaceSystemEfficiency', 21, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (41, null, '����Ч��', 'WellDownSystemEfficiency', 22, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (42, null, 'ϵͳЧ��', 'SystemEfficiency', 20, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (43, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 23, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (44, null, '����ƽ���', 'IDegreeBalance', 18, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (45, null, '����ƽ���', 'WattDegreeBalance', 17, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (45, null, '�ƶ�����', 'DeltaRadius', 19, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (47, null, '���õ���', 'TodayKWattH', 24, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (48, null, '��ע', 'Remark', 25, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (49, null, '����', 'CalDate', 1, null, '0,0,0', 3, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (50, null, '����ʱ��', 'RunTime', 2, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (51, null, '���', 'Stroke', 4, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (52, null, '���', 'SPM', 3, null, '0,0,0', 2, 2, null, null, null, 0, '{"sort":1,"color":"ecd211","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (53, null, '�ղ�ˮ��', 'WaterVolumetricProduction', 7, null, '0,0,0', 2, 2, null, null, null, 0, '{"sort":2,"color":"00d8ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (54, null, '�ղ�����', 'GasVolumetricProduction', 6, null, '0,0,0', 2, 2, null, null, null, 0, '{"sort":3,"color":"1424f1","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (55, null, '�ۼƲ�����', 'TotalGasVolumetricProduction', 10, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (56, null, '�ۼƲ�ˮ��', 'TotalWaterVolumetricProduction', 11, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (57, null, '��Һ��', 'ProducingfluidLevel', 5, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (58, null, '��ѹ', 'CasingPressure', 9, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (59, null, '����ѹ��', 'BottomHolePressure', 8, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (60, null, '��ע', 'Remark', 12, null, '0,0,0', 1, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (61, null, '����', 'WellName', 2, null, '0,0,0', 1, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (62, null, '����', 'CalDate', 1, null, '0,0,0', 3, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (63, null, '����ʱ��', 'RunTime', 3, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (64, null, '���', 'Stroke', 5, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (65, null, '���', 'SPM', 4, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (66, null, '�ղ�ˮ��', 'WaterVolumetricProduction', 8, null, '0,0,0', 2, 2, 1, 1, 1, 1, '{"sort":1,"color":"00d8ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (67, null, '�ղ�����', 'GasVolumetricProduction', 7, null, '0,0,0', 2, 2, 1, 1, 1, 1, '{"sort":2,"color":"1424f1","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (68, null, '�ۼƲ�����', 'TotalGasVolumetricProduction', 11, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (69, null, '�ۼƲ�ˮ��', 'TotalWaterVolumetricProduction', 12, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (70, null, '��Һ��', 'ProducingfluidLevel', 6, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (71, null, '��ѹ', 'CasingPressure', 10, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (72, null, '����ѹ��', 'BottomHolePressure', 9, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (73, null, '��ע', 'Remark', 13, null, '0,0,0', 1, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (74, null, '����', 'WellName', 2, null, '0,0,0', 1, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (75, null, '����', 'CalDate', 3, null, '0,0,0', 3, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (76, null, '����ʱ��', 'CommTime', 4, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (77, null, '����ʱ��', 'CommTimeEfficiency', 6, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (78, null, '��������', 'CommRange', 5, null, '0,0,0', 1, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (79, null, '����ʱ��', 'RunTime', 7, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (80, null, '����ʱ��', 'RunTimeEfficiency', 9, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (81, null, '��������', 'RunRange', 8, null, '0,0,0', 1, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (82, null, 'ת��', 'RPM', 14, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (83, null, '��Һ��', 'LiquidWeightProduction', 10, null, '0,0,0', 2, 3, 0, 0, null, 0, '{"sort":1,"color":"e40c54","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (84, null, '������', 'OilWeightProduction', 11, null, '0,0,0', 2, 3, 0, 0, null, 0, '{"sort":2,"color":"d7bb14","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (85, null, '��ˮ��', 'WaterWeightProduction', 12, null, '0,0,0', 2, 3, 0, 0, null, 0, '{"sort":3,"color":"1dbfb4","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (86, null, '������ˮ��', 'WeightWaterCut', 13, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (87, null, 'ϵͳЧ��', 'SystemEfficiency', 15, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (88, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 16, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (89, null, '���õ���', 'TodayKWattH', 17, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (90, null, '��ע', 'Remark', 18, null, '0,0,0', 1, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (91, null, '����', 'WellName', 2, null, '0,0,0', 1, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (92, null, '����', 'CalDate', 3, null, '0,0,0', 3, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (93, null, '����ʱ��', 'CommTime', 4, null, '0,0,0', 2, 3, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (94, null, '����ʱ��', 'CommTimeEfficiency', 6, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (95, null, '��������', 'CommRange', 5, null, '0,0,0', 1, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (96, null, '����ʱ��', 'RunTime', 7, null, '0,0,0', 2, 3, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (97, null, '����ʱ��', 'RunTimeEfficiency', 9, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (98, null, '��������', 'RunRange', 8, null, '0,0,0', 1, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (99, null, 'ת��', 'RPM', 14, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (100, null, '�ղ�Һ��', 'LiquidWeightProduction', 10, null, '0,0,0', 2, 3, 1, 1, 1, 1, '{"sort":1,"color":"e40c54","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (101, null, '�ղ�����', 'OilWeightProduction', 11, null, '0,0,0', 2, 3, 1, 1, 1, 1, '{"sort":2,"color":"d7bb14","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (102, null, '�ղ�ˮ��', 'WaterWeightProduction', 12, null, '0,0,0', 2, 3, 1, 1, 1, 1, '{"sort":3,"color":"1dbfb4","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (103, null, '������ˮ��', 'WeightWaterCut', 13, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (104, null, 'ϵͳЧ��', 'SystemEfficiency', 15, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (105, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 16, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (106, null, '���õ���', 'TodayKWattH', 17, null, '0,0,0', 2, 3, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (107, null, '��ע', 'Remark', 18, null, '0,0,0', 1, 3, 0, 0, null, 1, null);


/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLINSTANCE����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (1, '���ͻ�A11�ɿ�ʵ��', 'instance1', 'modbus-tcp', 'modbus-tcp', 1, 'AA01', '0D', 'AA01', '0D', 100, 1, 0, 1, 0, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (2, '�ݸ˱�A11�ɿ�ʵ��', 'instance2', 'modbus-tcp', 'modbus-tcp', 1, 'AA01', '0D', 'AA01', '0D', 100, 2, 1, 1, 0, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (3, '���ͻ�A11RPCʵ��', 'instance3', 'private-rpc', 'private-rpc', 1, null, null, null, null, 100, 1, 0, 2, 0, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (4, '���ͻ�MQTTʵ��', 'instance4', 'private-mqtt', 'private-mqtt', 0, null, null, null, null, 100, 1, 0, 3, 0, 1);


/*==============================================================*/
/* ��ʼ��tbl_protocolalarminstance����                                          */
/*==============================================================*/
insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, DEVICETYPE, SORT)
values (1, '���ͻ�A11����ʵ��', 'alarminstance1', 1, 0, 1);

insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, DEVICETYPE, SORT)
values (2, '�ݸ˱�A11����ʵ��', 'alarminstance2', 2, 1, 1);

/*==============================================================*/
/* ��ʼ��tbl_protocoldisplayinstance����                                          */
/*==============================================================*/
insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, DEVICETYPE, SORT)
values (1, '���ͻ�A11��ʾʵ��', 'displayinstance1', 1, 0, 1);

insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, DEVICETYPE, SORT)
values (2, '�ݸ˱�A11��ʾʵ��', 'displayinstance2', 2, 1, 1);

/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLREPORTINSTANCE����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (1, '���ͻ�������ʵ��һ', 'reportinstance1', 0, 1, 1);

insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (2, '�ݸ˱þ�����ʵ��һ', 'reportinstance21', 1, 1, 3);

insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (3, 'ú����������ʵ��', 'reportinstance41', 0, 2, 2);