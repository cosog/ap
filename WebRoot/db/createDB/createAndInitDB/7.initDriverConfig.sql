/*==============================================================*/
/* ��ʼ��TBL_PROTOCOL����                                          */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'RTU2.0', 'protocol1', 0, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"ú�㶥����","Addr":40019,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":0.1,"RWType":"rw","Unit":"��","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"ѹ���ư�װ���","Addr":40020,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":0.1,"RWType":"rw","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����ѹ����-ѹ��","Addr":40400,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":0.001,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Һ��","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"������ѹ","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":3,"Quantity":1,"Ratio":0.001,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"ϵͳѹ��","Addr":40406,"StoreDataType":"float32","IFDataType":"float32","Prec":3,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"KPa","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"�������ۼ�","Addr":40408,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"������˲ʱ","Addr":40410,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�����¶�","Addr":40412,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ˮ���ۼ�","Addr":40414,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ˮ��˲ʱ","Addr":40416,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���","Addr":40418,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��/min","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"����ѹ����-�¶�","Addr":40444,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����������ͨѶ״̬","Addr":40446,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"����"},{"Value":1,"Meaning":"����Ӧ"},{"Value":2,"Meaning":"վ�Ŵ���"},{"Value":3,"Meaning":"�������"},{"Value":4,"Meaning":"CRCУ�����"}]},{"Title":"ˮ������ͨѶ״̬","Addr":40447,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"����"},{"Value":1,"Meaning":"����Ӧ"},{"Value":2,"Meaning":"վ�Ŵ���"},{"Value":3,"Meaning":"�������"},{"Value":4,"Meaning":"CRCУ�����"}]},'||
  '{"Title":"����ѹ����/Һ����ͨѶ״̬","Addr":40448,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"����"},{"Value":1,"Meaning":"����Ӧ"},{"Value":2,"Meaning":"վ�Ŵ���"},{"Value":3,"Meaning":"�������"},{"Value":4,"Meaning":"CRCУ�����"}]},{"Title":"��Ƶ��ͨѶ״̬","Addr":40450,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"����"},{"Value":1,"Meaning":"����Ӧ"},{"Value":2,"Meaning":"վ�Ŵ���"},{"Value":3,"Meaning":"�������"},{"Value":4,"Meaning":"CRCУ�����"}]},{"Title":"Ƶ�ʿ��Ʒ�ʽ","Addr":40481,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"��ο���"},{"Value":1,"Meaning":"Ƶ�ʿ���"}]},'||
  '{"Title":"��ͣ����","Addr":40482,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"����"},{"Value":2,"Meaning":"ֹͣ"}]},{"Title":"��Ƶ������Ƶ��","Addr":40483,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Ƶ������Ƶ��","Addr":40485,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Ƶ������״̬","Addr":40487,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"��Ƶ���������","Addr":40490,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"��Ƶ�������ѹ","Addr":40492,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Ƶ�����Ҵ���","Addr":40494,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"�ޱ�Ƶ�����Ƶ����ͨѶ����"},{"Value":1,"Meaning":"Ӣ����"},{"Value":2,"Meaning":"��½����"},{"Value":3,"Meaning":"����"},{"Value":4,"Meaning":"�㴨"},{"Value":5,"Meaning":"��������Ŵ"},{"Value":6,"Meaning":"��½����660V"},{"Value":7,"Meaning":"��ҵ����"},{"Value":8,"Meaning":"�մ��Ƽ�"},{"Value":9,"Meaning":"INVERTER"},{"Value":10,"Meaning":"����"}]},{"Title":"��Ƶ��״̬��1","Addr":40495,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"����"},{"Value":3,"Meaning":"ͣ��"},{"Value":4,"Meaning":"����"}]},'||
  '{"Title":"��Ƶ��״̬��2","Addr":40496,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"������ťλ��","Addr":40497,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"ĸ�ߵ�ѹ","Addr":40498,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�趨Ƶ�ʷ���","Addr":40500,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"10Hz��Ӧ���/ת��Ԥ��ֵ","Addr":40502,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"��/100ת","ResolutionMode":2,"AcqMode":"passive"},{"Title":"50Hz��Ӧ���/ת��Ԥ��ֵ","Addr":40504,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"��/100ת","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"���/ת���趨ֵ","Addr":40506,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"��/100ת","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�����󾮵���ѹ","Addr":40512,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":0.001,"RWType":"r","Unit":"Mpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����Һ���߶�","Addr":40514,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�����1СʱҺ���½��ٶ�","Addr":40516,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m/Сʱ","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"�Ų�ģʽ","Addr":40522,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"�Զ��Ų�״̬","Addr":40523,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"����"},{"Value":1,"Meaning":"��ѹ��������"},{"Value":2,"Meaning":"����Һ-Һ����ͣ��"},{"Value":3,"Meaning":"����Һ-Һ���ͱ���"},{"Value":4,"Meaning":"�Զ������ﵽ������"},{"Value":5,"Meaning":"����Һ-Һ���߱���"},{"Value":6,"Meaning":"����ѹ-��ѹ��ͣ��"},{"Value":7,"Meaning":"����ѹ-��ѹ�ͱ���"},{"Value":8,"Meaning":"����ѹ-��ѹ�߱���"},{"Value":9,"Meaning":"�Զ�������"},{"Value":10,"Meaning":"�Զ��Ų�ʱ����4��ִ�����Ƶ��"},{"Value":11,"Meaning":"�Զ��Ų�ʱ����4��ִ����СƵ��"}]},'||
  '{"Title":"�Զ��Ų�-��СƵ��","Addr":40524,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�Զ��Ų�-���Ƶ��","Addr":40525,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��󲽳���������","Addr":40526,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��̵���ʱ����","Addr":40527,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�Զ�������ʱ","Addr":40528,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�Զ���������","Addr":40529,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"������ѹ��������ֵ","Addr":40530,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����Һ-Ŀ�궨����ÿ�գ�","Addr":40534,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����Һ-Һ����ͣ��ֵ","Addr":40536,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����Һ-Һ���ͱ���ֵ","Addr":40538,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����Һ-Һ������ֵ","Addr":40540,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����Һ-Һ���߱���ֵ","Addr":40542,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"����Һ-P����","Addr":40544,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����Һ-I����","Addr":40546,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����Һ-D����","Addr":40548,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����ѹ-Ŀ����ѹ","Addr":40562,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����ѹ-��ѹ��ͣ��ֵ","Addr":40564,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����ѹ-��ѹ�ͱ���ֵ","Addr":40566,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"����ѹ-��ѹ����ֵ","Addr":40568,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����ѹ-��ѹ�߱���ֵ","Addr":40570,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����ѹ-P����","Addr":40572,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"����ѹ-I����","Addr":40574,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����ѹ-D����","Addr":40576,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.NAME='RTU2.0';  
  COMMIT;  
END;  
/

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
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (1, null, '����ѹ����-ѹ��', 'c_jxyljyl', 1, 1, null, null, 0, '{"sort":1,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"8a7c93"}', '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"8a7c93"}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (2, null, 'ú�㶥����', 'c_mcdbs', 1, 6, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (3, null, '������ѹ', 'c_jkty', 1, 2, null, null, 0, '{"sort":2,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"804000"}', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"804000"}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (4, null, 'ѹ���ư�װ���', 'c_yljazsd', 1, 7, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (5, null, 'ϵͳѹ��', 'c_xtyl', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (6, null, '��ͣ����', 'c_qtkz', 1, 1, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (7, null, '�������ۼ�', 'c_cqllj', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (8, null, '��Ƶ������Ƶ��', 'c_bpqszpl', 1, 2, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (9, null, '�Ų�ģʽ', 'c_pcms', 1, 3, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (10, null, '������˲ʱ', 'c_cqlss', 1, 3, null, null, 0, '{"sort":3,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', '{"sort":3,"color":"ff0000","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (11, null, '�����¶�', 'c_qtwd', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (12, null, '�Զ��Ų�״̬', 'c_zdpczt', 1, 4, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (13, null, '�Զ��Ų�-��СƵ��', 'c_zdpczxpl', 1, 8, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (14, null, '��ˮ���ۼ�', 'c_csllj', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (15, null, '��ˮ��˲ʱ', 'c_cslss', 1, 4, null, null, 0, '{"sort":4,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"0400ff"}', '{"sort":4,"color":"0400ff","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (16, null, '�Զ��Ų�-���Ƶ��', 'c_zdpczdpl', 1, 9, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (17, null, '���', 'c_cc', 1, 7, null, null, 0, '{"sort":7,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"78a08c"}', '{"sort":7,"color":"78a08c","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (18, null, '��󲽳���������', 'c_zdbcfdxz', 1, 10, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (19, null, '��Ƶ������Ƶ��', 'c_bpqyxpl', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (20, null, '��̵���ʱ����', 'c_zddzsjjg', 1, 11, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (21, null, '��Ƶ���������', 'c_bpqscdl', 1, 5, null, null, 0, '{"sort":5,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"dbda00"}', '{"sort":5,"color":"dbda00","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (22, null, '�Զ�������ʱ', 'c_zdzqys', 1, 12, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (23, null, '�Զ���������', 'c_zdzqcs', 1, 13, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (24, null, '��Ƶ�������ѹ', 'c_bpqscdy', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (25, null, '��Ƶ��״̬��1', 'c_bpqztz1', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (26, null, '������ѹ��������ֵ', 'c_jdlybdbjz', 1, 14, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (27, null, '����Һ-Ŀ�궨����ÿ�գ�', 'c_djymbdjmr', 1, 5, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (28, null, 'ĸ�ߵ�ѹ', 'c_mxdy', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (29, null, '����Һ-Һ����ͣ��ֵ', 'c_djyyzdtjz', 1, 15, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (30, null, '�����󾮵���ѹ', 'c_xzhjdly', 1, 6, null, null, 0, '{"sort":6,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"27701a"}', '{"sort":6,"color":"27701a","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (31, null, '����Һ-Һ���ͱ���ֵ', 'c_djyyzdbjz', 1, 16, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (32, null, '����Һ���߶�', 'c_jsyzgd', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (33, null, '�����1СʱҺ���½��ٶ�', 'c_jsj1xsyzxjsd', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (34, null, '����Һ-Һ������ֵ', 'c_djyyzzqz', 1, 17, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (35, null, '����Һ-Һ���߱���ֵ', 'c_djyyzgbjz', 1, 18, null, null, 2, null, null, '0,0,0');

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
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (1, null, '����', 'CalDate', 1, 1, null, null, null, null, null, 3, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (2, null, '����ʱ��', 'RunTime', 1, 2, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (3, null, '���', 'Stroke', 1, 4, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (4, null, '���', 'SPM', 1, 3, null, null, null, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"ecd211"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (5, null, '�ղ�ˮ��', 'WaterVolumetricProduction', 1, 7, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"00d8ff"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (6, null, '�ղ�����', 'GasVolumetricProduction', 1, 6, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (7, null, '�ۼƲ�����', 'TotalGasVolumetricProduction', 1, 10, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (8, null, '�ۼƲ�ˮ��', 'TotalWaterVolumetricProduction', 1, 11, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (9, null, '��Һ��', 'ProducingfluidLevel', 1, 5, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (10, null, '��ѹ', 'CasingPressure', 1, 9, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (11, null, '����ѹ��', 'BottomHolePressure', 1, 8, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (12, null, '��ע', 'Remark', 1, 12, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (13, null, '����', 'WellName', 1, 2, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (14, null, '����', 'CalDate', 1, 1, null, 0, 0, null, null, 3, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (15, null, '����ʱ��', 'RunTime', 1, 3, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (16, null, '���', 'Stroke', 1, 5, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (17, null, '���', 'SPM', 1, 4, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (18, null, '�ղ�ˮ��', 'WaterVolumetricProduction', 1, 8, null, 1, 1, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"00d8ff"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (19, null, '�ղ�����', 'GasVolumetricProduction', 1, 7, null, 1, 1, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (20, null, '�ۼƲ�����', 'TotalGasVolumetricProduction', 1, 11, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (21, null, '�ۼƲ�ˮ��', 'TotalWaterVolumetricProduction', 1, 12, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (22, null, '��Һ��', 'ProducingfluidLevel', 1, 6, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (23, null, '��ѹ', 'CasingPressure', 1, 10, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (24, null, '����ѹ��', 'BottomHolePressure', 1, 9, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (25, null, '��ע', 'Remark', 1, 13, null, 0, 0, null, null, 1, 1, '0,0,0');

/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLREPORTINSTANCE����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (1, 'ú����������ʵ��', 'reportinstance1', 0, 1, 1);