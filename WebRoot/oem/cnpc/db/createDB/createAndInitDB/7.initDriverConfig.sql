/*==============================================================*/
/* ��ʼ��TBL_PROTOCOL����                                        */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'A11Э��', 'protocol1', 1, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"��ѹ","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"��ѹ","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ѹ","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�����¶�","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"����״̬","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"ͣ��"},{"Value":1,"Meaning":"����"}]},{"Title":"��ͣ����","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"����"},{"Value":2,"Meaning":"ͣ��"}]},{"Title":"��ˮ��","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Һ��","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"A�����","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B�����","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C�����","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A���ѹ","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B���ѹ","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C���ѹ","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"�й�����","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW��h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�޹�����","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar��h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�й�����","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�޹�����","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},{"Title":"������","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��������","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"��Ƶ����Ƶ��","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��Ƶ����Ƶ��","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�ݸ˱�ת��","Addr":40430,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"r/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�ݸ˱�Ť��","Addr":40432,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kN��m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ�ɼ����","Addr":40981,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"�ֶ��ɼ���ͼ","Addr":40982,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"�̶�ʱ��ɼ�"},{"Value":1,"Meaning":"�ֶ��ɼ�"}]},'
  ||'{"Title":"��ͼ���õ���","Addr":40983,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼʵ�����","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ�ɼ�ʱ��","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"��ͼ����-λ��","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},'
  ||'{"Title":"��ͼ����-�غ�","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ����-����","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��ͼ����-����","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive","Meaning":[]}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.CODE='protocol1';  
  COMMIT;  
END;  
/



/*==============================================================*/
/* ��ʼ��TBL_DATAMAPPING����                                          */
/*==============================================================*/
insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (1, '��ѹ', 'C_CLOUMN1', 0, 'TubingPressure', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (2, '��ѹ', 'C_CLOUMN2', 0, 'CasingPressure', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (3, '��ѹ', 'C_CLOUMN3', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (4, '�����¶�', 'C_CLOUMN4', 0, 'WellHeadTemperature', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (5, '����״̬', 'C_CLOUMN5', 0, 'RunStatus', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (6, '��ˮ��', 'C_CLOUMN6', 0, 'VolumeWaterCut', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (7, '��Һ��', 'C_CLOUMN7', 0, 'ProducingfluidLevel', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (8, 'A�����', 'C_CLOUMN8', 0, 'IA', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (9, 'B�����', 'C_CLOUMN9', 0, 'IB', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (10, 'C�����', 'C_CLOUMN10', 0, 'IC', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (11, 'A���ѹ', 'C_CLOUMN11', 0, 'VA', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (12, 'B���ѹ', 'C_CLOUMN12', 0, 'VB', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (13, 'C���ѹ', 'C_CLOUMN13', 0, 'VC', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (14, '�й�����', 'C_CLOUMN14', 0, 'TotalKWattH', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (15, '�޹�����', 'C_CLOUMN15', 0, 'TotalKVarH', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (16, '�й�����', 'C_CLOUMN16', 0, 'Watt3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (17, '�޹�����', 'C_CLOUMN17', 0, 'Var3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (18, '������', 'C_CLOUMN18', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (19, '��������', 'C_CLOUMN19', 0, 'PF3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (20, '��Ƶ����Ƶ��', 'C_CLOUMN20', 0, 'RunFrequency', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (21, '�ݸ˱�ת��', 'C_CLOUMN21', 0, 'RPM', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (22, '�ݸ˱�Ť��', 'C_CLOUMN22', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (23, '��ͼ�ɼ����', 'C_CLOUMN23', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (24, '�ֶ��ɼ���ͼ', 'C_CLOUMN24', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (25, '��ͼ���õ���', 'C_CLOUMN25', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (26, '��ͼʵ�����', 'C_CLOUMN26', 0, 'FESDiagramAcqCount', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (27, '��ͼ�ɼ�ʱ��', 'C_CLOUMN27', 0, 'FESDiagramAcqtime', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (28, '���', 'C_CLOUMN28', 0, 'SPM', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (29, '���', 'C_CLOUMN29', 0, 'Stroke', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (30, '��ͼ����-λ��', 'C_CLOUMN30', 0, 'Position_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (31, '��ͼ����-�غ�', 'C_CLOUMN31', 0, 'Load_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (32, '��ͼ����-����', 'C_CLOUMN32', 0, 'Current_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (33, '��ͼ����-����', 'C_CLOUMN33', 0, 'Power_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (34, '��ͣ����', 'C_CLOUMN34', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (35, '��Ƶ����Ƶ��', 'C_CLOUMN35', 0, null, null, 1, 0);


/*==============================================================*/
/* ��ʼ��TBL_RUNSTATUSCONFIG����                                          */
/*==============================================================*/
insert into TBL_RUNSTATUSCONFIG (ID, PROTOCOL, ITEMNAME, ITEMMAPPINGCOLUMN, RUNVALUE, STOPVALUE, PROTOCOLTYPE, RESOLUTIONMODE, RUNCONDITION, STOPCONDITION)
values (1, 'protocol1', '����״̬', 'C_CLOUMN5', '1', '0', 0, 1, null, null);

/*==============================================================*/
/* ��ʼ��TBL_ACQ_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, SORT, REMARK)
values (1, 'unit1', '���ͻ�A11�ɼ���Ԫ', 'A11Э��', 1, '���ͻ�A11�ɼ���Ԫ');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, SORT, REMARK)
values (2, 'unit2', '�ݸ˱�A11�ɼ���Ԫ', 'A11Э��', 2,'�ݸ˱�A11�ɼ���Ԫ');

/*==============================================================*/
/* ��ʼ��TBL_ACQ_GROUP_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '�ɼ���', 60, 0, 'A11Э��', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '������', 0, 0, 'A11Э��', 1, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (3, 'group5', '�ɼ���', 60, 60, 'A11Э��', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (4, 'group6', '������', 0, 0, 'A11Э��', 1, null);

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

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (2, null, '����״̬', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (3, null, '��ͣ����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (4, null, 'A�����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (5, null, 'B�����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (6, null, 'C�����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (7, null, 'A���ѹ', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (8, null, 'B���ѹ', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (9, null, 'C���ѹ', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (10, null, '�й�����', null, 1, null, '0,0,0', 1, '���õ���');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (11, null, '�޹�����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (12, null, '�й�����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (13, null, '�޹�����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (14, null, '������', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (15, null, '��������', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (16, null, '��Ƶ����Ƶ��', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (17, null, '��Ƶ����Ƶ��', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (18, null, '��ͼ�ɼ����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (19, null, '�ֶ��ɼ���ͼ', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (20, null, '��ͼ���õ���', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (21, null, '��ͼʵ�����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (22, null, '��ͼ�ɼ�ʱ��', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (23, null, '���', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (24, null, '���', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (25, null, '��ͼ����-λ��', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (26, null, '��ͼ����-�غ�', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (27, null, '��ͼ����-����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (28, null, '��ͼ����-����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (29, null, '��ͣ����', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (30, null, '��Ƶ����Ƶ��', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (31, null, '��ѹ', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (32, null, '��ѹ', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (33, null, '��ѹ', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (34, null, '�����¶�', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (35, null, '����״̬', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (36, null, '��ͣ����', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (37, null, '��ˮ��', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (38, null, '��Һ��', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (39, null, 'A�����', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (40, null, 'B�����', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (41, null, 'C�����', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (42, null, 'A���ѹ', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (43, null, 'B���ѹ', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (44, null, 'C���ѹ', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (45, null, '�й�����', null, 3, null, '0,0,0', 1, '���õ���');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (46, null, '�޹�����', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (47, null, '�й�����', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (48, null, '�޹�����', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (49, null, '������', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (50, null, '��������', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (51, null, '��Ƶ����Ƶ��', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (52, null, '��Ƶ����Ƶ��', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (53, null, '�ݸ˱�ת��', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (54, null, '�ݸ˱�Ť��', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (55, null, '��ͣ����', null, 4, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (56, null, '��Ƶ����Ƶ��', null, 4, null, '0,0,0', 0, null);

/*==============================================================*/
/* ��ʼ��TBL_ALARM_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, SORT, REMARK)
values (1, 'alarmunit1', 'A11-���ͻ�������Ԫ', 'A11Э��', 1, 'A11-���ͻ�������Ԫ');

insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, SORT, REMARK)
values (2, 'alarmunit2', 'A11-�ݸ˱ñ�����Ԫ', 'A11Э��', 2, 'A11-�ݸ˱ñ�����Ԫ');

/*==============================================================*/
/* ��ʼ��TBL_ALARM_ITEM2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (1, 1, null, '����', 'online', 0, 1.000, null, null, null, null, 300, 1, 3, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (2, 1, null, '����', 'offline', 0, 0.000, null, null, null, null, 100, 1, 3, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (3, 1, null, '����', '1201', 0, 1201.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (4, 1, null, '����', '1202', 0, 1202.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (5, 1, null, '��������', '1203', 0, 1203.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (6, 1, null, '��Һ����', '1204', 0, 1204.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (7, 1, null, '��Һ����', '1205', 0, 1205.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (8, 1, null, '���', '1206', 0, 1206.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (9, 1, null, '���¶�', '1207', 0, 1207.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (10, 1, null, '����', '1208', 0, 1208.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (11, 1, null, '��Ӱ��', '1209', 0, 1209.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (12, 1, null, '��϶©', '1210', 0, 1210.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (13, 1, null, '�͹�©', '1211', 0, 1211.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (14, 1, null, '�ζ�����©ʧ', '1212', 0, 1212.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (15, 1, null, '�̶�����©ʧ', '1213', 0, 1213.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (16, 1, null, '˫����©ʧ', '1214', 0, 1214.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (17, 1, null, '�ζ�����ʧ��/�͹�©', '1215', 0, 1215.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (18, 1, null, '�̶�����ʧ��', '1216', 0, 1216.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (19, 1, null, '˫����ʧ��', '1217', 0, 1217.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (20, 1, null, '���������', '1218', 0, 1218.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (21, 1, null, '����', '1219', 0, 1219.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (22, 1, null, '����/�ײ�����/δ�빤��Ͳ', '1220', 0, 1220.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (23, 1, null, '�����ѳ�����Ͳ', '1221', 0, 1221.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (24, 1, null, '�˶���', '1222', 0, 1222.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (25, 1, null, '��(��)��', '1223', 0, 1223.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (26, 1, null, '����', '1224', 0, 1224.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (27, 1, null, '���ؽ���', '1225', 0, 1225.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (28, 1, null, '��ɰ', '1226', 0, 1226.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (29, 1, null, '���س�ɰ', '1227', 0, 1227.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (30, 1, null, '�����غɴ�', '1230', 0, 1230.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (31, 1, null, 'Ӧ������', '1231', 0, 1231.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (32, 1, null, '�ɼ��쳣', '1232', 0, 1232.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (33, 1, null, 'ֹͣ', 'stop', 0, 0.000, null, null, null, null, 100, 1, 6, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (34, 2, null, '����', 'online', 0, 1.000, null, null, null, null, 300, 1, 3, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (35, 2, null, '����', 'offline', 0, 0.000, null, null, null, null, 100, 1, 3, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (36, 2, null, 'ֹͣ', 'stop', 0, 0.000, null, null, null, null, 100, 1, 6, 0, 0, 0, 60);

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_UNIT_CONF����                                */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, CALCULATETYPE, SORT, REMARK)
values (1, 'unit1', '���ͻ�A11��ʾ��Ԫ', 'A11Э��', 1, 1, 1, '���ͻ�A11��ʾ��Ԫ');

insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, CALCULATETYPE, SORT, REMARK)
values (2, 'unit2', '�ݸ˱�A11��ʾ��Ԫ', 'A11Э��', 2, 2, 2, '�ݸ˱�A11��ʾ��Ԫ');

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (1, null, '����״̬', 'C_CLOUMN5', 1, 4, 4, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (2, null, 'A�����', 'C_CLOUMN9', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (3, null, 'B�����', 'C_CLOUMN10', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (4, null, 'C�����', 'C_CLOUMN11', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (5, null, 'A���ѹ', 'C_CLOUMN12', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (6, null, 'B���ѹ', 'C_CLOUMN13', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (7, null, 'C���ѹ', 'C_CLOUMN14', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (8, null, '�й�����', 'C_CLOUMN15', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (9, null, '�޹�����', 'C_CLOUMN16', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (10, null, '�й�����', 'C_CLOUMN17', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (11, null, '�޹�����', 'C_CLOUMN18', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (12, null, '������', 'C_CLOUMN19', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (13, null, '��������', 'C_CLOUMN20', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (14, null, '��Ƶ����Ƶ��', 'C_CLOUMN21', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (15, null, '��Ƶ����Ƶ��', 'C_CLOUMN22', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (16, null, '��ͼ���õ���', 'C_CLOUMN27', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (17, null, '��ͼʵ�����', 'C_CLOUMN28', 1, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (18, null, '���', 'C_CLOUMN30', 1, 17, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (19, null, '���', 'C_CLOUMN31', 1, 14, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (20, null, '����ʱ��', 'CommTime', 1, 1, 1, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (21, null, '����ʱ��', 'CommTimeEfficiency', 1, 2, 2, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (22, null, '��������', 'CommRange', 1, 3, 3, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (23, null, '����ʱ��', 'RunTime', 1, 7, 5, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (24, null, '����ʱ��', 'RunTimeEfficiency', 1, 8, 6, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (25, null, '��������', 'RunRange', 1, 9, 7, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (26, null, '����', 'ResultName', 1, 10, 8, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (27, null, '����غ�', 'FMax', 1, 20, 9, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b824e6"}', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"b824e6"}', 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (28, null, '��С�غ�', 'FMin', 1, 23, 10, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e25e1d"}', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e25e1d"}', 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (29, null, '�������', 'PlungerStroke', 1, 15, 11, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (30, null, '������Ч���', 'AvailablePlungerStroke', 1, 18, 12, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (31, null, '���������Ч���', 'NoLiquidAvailablePlungerStroke', 1, 27, 13, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (32, null, '����ϵ��', 'FullnessCoefficient', 1, 21, 14, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (33, null, '��ճ���ϵ��', 'NoLiquidFullnessCoefficient', 1, 24, 15, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (34, null, '�������غ�', 'UpperLoadLine', 1, 26, 16, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (35, null, '�������غ�', 'LowerLoadLine', 1, 29, 17, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (36, null, '��������', 'TheoreticalProduction', 1, 25, 18, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (37, null, '˲ʱ��Һ��', 'LiquidVolumetricProduction', 1, 13, 19, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (38, null, '˲ʱ������', 'OilVolumetricProduction', 1, 16, 20, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (39, null, '˲ʱ��ˮ��', 'WaterVolumetricProduction', 1, 19, 21, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (40, null, '������Ч��̼������', 'AvailablePlungerStrokeProd_v', 1, 28, 22, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (41, null, '�ü�϶©ʧ��', 'PumpClearanceleakProd_v', 1, 30, 23, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (42, null, '���ۼƲ�Һ��', 'LiquidVolumetricProduction_l', 1, 22, 24, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (43, null, '�й�����', 'AverageWatt', 1, 44, 25, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (44, null, '��˹���', 'PolishRodPower', 1, 47, 26, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (45, null, 'ˮ����', 'WaterPower', 1, 41, 27, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (46, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 43, 28, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (47, null, '����Ч��', 'WellDownSystemEfficiency', 1, 46, 29, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (48, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 40, 30, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (49, null, '��ͼ���', 'Area', 1, 42, 31, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (50, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 45, 32, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (51, null, '���͸��쳤��', 'RodFlexLength', 1, 31, 33, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (52, null, '�͹�������', 'TubingFlexLength', 1, 32, 34, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (53, null, '�����غ�����', 'InertiaLength', 1, 33, 35, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (54, null, '�����ʧϵ��', 'PumpEff1', 1, 34, 36, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (55, null, '����ϵ��', 'PumpEff2', 1, 35, 37, null, null, '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d4bf24"}', '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d4bf24"}', 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (56, null, '��϶©ʧϵ��', 'PumpEff3', 1, 36, 38, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (57, null, 'Һ������ϵ��', 'PumpEff4', 1, 37, 39, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (58, null, '�ܱ�Ч', 'PumpEff', 1, 38, 40, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (59, null, '�����ѹ��', 'PumpIntakeP', 1, 61, 41, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (60, null, '������¶�', 'PumpIntakeT', 1, 62, 42, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (61, null, '����ھ͵���Һ��', 'PumpIntakeGOL', 1, 63, 43, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (62, null, '�����ճ��', 'PumpIntakeVisl', 1, 64, 44, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (63, null, '�����ԭ�����ϵ��', 'PumpIntakeBo', 1, 65, 45, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (64, null, '�ó���ѹ��', 'PumpOutletP', 1, 67, 46, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (65, null, '�ó����¶�', 'PumpOutletT', 1, 68, 47, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (66, null, '�ó��ھ͵���Һ��', 'PumpOutletGOL', 1, 69, 48, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (67, null, '�ó���ճ��', 'PumpOutletVisl', 1, 70, 49, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (68, null, '�ó���ԭ�����ϵ��', 'PumpOutletBo', 1, 71, 50, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (69, null, '�ϳ��������', 'UpStrokeIMax', 1, 50, 51, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (70, null, '�³��������', 'DownStrokeIMax', 1, 51, 52, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (71, null, '�ϳ�������', 'UpStrokeWattMax', 1, 53, 53, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (72, null, '�³�������', 'DownStrokeWattMax', 1, 54, 54, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (73, null, '����ƽ���', 'IDegreeBalance', 1, 49, 55, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (74, null, '����ƽ���', 'WattDegreeBalance', 1, 52, 56, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (75, null, '�ƶ�����', 'DeltaRadius', 1, 55, 57, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (76, null, 'Һ��У����ֵ', 'LevelDifferenceValue', 1, 59, 58, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (77, null, '���ݶ�Һ��', 'CalcProducingfluidLevel', 1, 58, 59, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (78, null, '���õ���', 'C_CLOUMN15_TOTAL', 1, null, 60, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (79, null, '��ͣ����', 'C_CLOUMN6', 1, 1, null, null, null, null, null, 2, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (80, null, '��Ƶ����Ƶ��', 'C_CLOUMN21', 1, 2, null, null, null, null, null, 2, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (81, null, 'ԭ���ܶ�', 'CrudeOilDensity', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (82, null, 'ˮ�ܶ�', 'WaterDensity', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (83, null, '��Ȼ������ܶ�', 'NaturalGasRelativeDensity', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (84, null, '����ѹ��', 'SaturationPressure', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (85, null, '�Ͳ��в����', 'ReservoirDepth', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (86, null, '�Ͳ��в��¶�', 'ReservoirTemperature', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (87, null, '��ѹ', 'TubingPressure', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (88, null, '��ѹ', 'CasingPressure', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (89, null, '�����¶�', 'WellHeadTemperature', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (90, null, '��ˮ��', 'WaterCut', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (91, null, '�������ͱ�', 'ProductionGasOilRatio', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (92, null, '��Һ��', 'ProducingfluidLevel', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (93, null, '�ù�', 'PumpSettingDepth', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (94, null, '�þ�', 'PumpBoreDiameter', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (95, null, '����Һ��У��ֵ', 'LevelCorrectValue', 1, null, null, null, null, null, null, 3, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (96, null, '��ѹ', 'C_CLOUMN1', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (97, null, '��ѹ', 'C_CLOUMN2', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (98, null, '��ѹ', 'C_CLOUMN3', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (99, null, '�����¶�', 'C_CLOUMN4', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (100, null, '����״̬', 'C_CLOUMN5', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (101, null, '��ˮ��', 'C_CLOUMN7', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (102, null, 'A�����', 'C_CLOUMN9', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (103, null, 'B�����', 'C_CLOUMN10', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (104, null, 'C�����', 'C_CLOUMN11', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (105, null, 'A���ѹ', 'C_CLOUMN12', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (106, null, 'B���ѹ', 'C_CLOUMN13', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (107, null, 'C���ѹ', 'C_CLOUMN14', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (108, null, '�й�����', 'C_CLOUMN15', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (109, null, '�޹�����', 'C_CLOUMN16', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (110, null, '�й�����', 'C_CLOUMN17', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (111, null, '�޹�����', 'C_CLOUMN18', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (112, null, '������', 'C_CLOUMN19', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (113, null, '��������', 'C_CLOUMN20', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (114, null, '��Ƶ����Ƶ��', 'C_CLOUMN21', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (115, null, '��Ƶ����Ƶ��', 'C_CLOUMN22', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (116, null, '�ݸ˱�ת��', 'C_CLOUMN23', 2, null, null, null, null, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"3100ff"}', '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"0008ff"}', 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (117, null, '�ݸ˱�Ť��', 'C_CLOUMN24', 2, null, null, null, null, null, null, 0, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (118, null, '����ʱ��', 'CommTime', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (119, null, '����ʱ��', 'CommTimeEfficiency', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (120, null, '��������', 'CommRange', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (121, null, '����ʱ��', 'RunTime', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (122, null, '����ʱ��', 'RunTimeEfficiency', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (123, null, '��������', 'RunRange', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (124, null, '��������', 'TheoreticalProduction', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (125, null, '˲ʱ��Һ��', 'LiquidVolumetricProduction', 2, null, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"1fe4f8"}', '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"31d8e9"}', 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (126, null, '˲ʱ������', 'OilVolumetricProduction', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (127, null, '˲ʱ��ˮ��', 'WaterVolumetricProduction', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (128, null, '���ۼƲ�Һ��', 'LiquidVolumetricProduction_l', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (129, null, '˲ʱ��Һ��', 'LiquidWeightProduction', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (130, null, '˲ʱ������', 'OilWeightProduction', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (131, null, '˲ʱ��ˮ��', 'WaterWeightProduction', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (132, null, '���ۼƲ�Һ��', 'LiquidWeightProduction_l', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (133, null, '�й�����', 'AverageWatt', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (134, null, 'ˮ����', 'WaterPower', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (135, null, 'ϵͳЧ��', 'SystemEfficiency', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (136, null, '�����ʧϵ��', 'PumpEff1', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (137, null, '����ϵ��', 'PumpEff2', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (138, null, '�ܱ�Ч', 'PumpEff', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (139, null, '�����ѹ��', 'PumpIntakeP', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (140, null, '������¶�', 'PumpIntakeT', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (141, null, '����ھ͵���Һ��', 'PumpIntakeGOL', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (142, null, '�����ճ��', 'PumpIntakeVisl', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (143, null, '�����ԭ�����ϵ��', 'PumpIntakeBo', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (144, null, '�ó���ѹ��', 'PumpOutletP', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (145, null, '�ó����¶�', 'PumpOutletT', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (146, null, '�ó��ھ͵���Һ��', 'PumpOutletGOL', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (147, null, '�ó���ճ��', 'PumpOutletVisl', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (148, null, '�ó���ԭ�����ϵ��', 'PumpOutletBo', 2, null, null, null, null, null, null, 1, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (149, null, '��ͣ����', 'C_CLOUMN6', 2, null, null, null, null, null, null, 2, '0,0,0', 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, REALTIMEDATA, HISTORYDATA)
values (150, null, '��Ƶ����Ƶ��', 'C_CLOUMN21', 2, null, null, null, null, null, null, 2, '0,0,0', 1, 1);

/*==============================================================*/
/* ��ʼ��TBL_REPORT_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (1, 'unit1', '���ͻ�������Ԫһ', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 1, 'oilWell_PumpingDailyReport', 1);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (2, 'unit2', '�ݸ˱þ�����Ԫһ', 'oilWell_ScrewPump', 'oilWell_ScrewPumpProductionReoirt', 2, 'oilWell_ScrewPumpDailyReport', 2);
/*==============================================================*/
/* ��ʼ��TBL_REPORT_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (1, null, '����', 'CalDate', 1, 2, null, null, null, null, null, 3, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (2, null, '����ʱ��', 'CommTime', 1, 3, null, null, null, null, null, 2, 0, '0,0,0', 1, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (3, null, '��������', 'CommRange', 1, 4, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (4, null, '����ʱ��', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (5, null, '����ʱ��', 'RunTime', 1, 6, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (6, null, '��������', 'RunRange', 1, 7, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (7, null, '����ʱ��', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (8, null, '����', 'ResultName', 1, 9, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (9, null, '�Ż�����', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (10, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 1, 11, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b96161"}', null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (11, null, '���ۼƲ�����', 'OilWeightProduction', 1, 12, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e99314"}', null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (12, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 1, 13, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"19c2eb"}', null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (13, null, '������ˮ��', 'WeightWaterCut', 1, 14, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (14, null, '����ϵ��', 'FullnessCoefficient', 1, 15, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (15, null, '�ù�', 'PumpSettingDepth', 1, 16, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (16, null, '��Һ��', 'ProducingfluidLevel', 1, 17, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (17, null, '��û��', 'Submergence', 1, 18, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (18, null, '����ƽ���', 'WattDegreeBalance', 1, 19, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (19, null, '����ƽ���', 'IDegreeBalance', 1, 20, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (20, null, '�ƶ�����', 'DeltaRadius', 1, 21, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (21, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 22, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (22, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 23, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (23, null, '����Ч��', 'WellDownSystemEfficiency', 1, 24, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (24, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 25, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (25, null, '�й�����', 'C_CLOUMN14', 1, 26, null, null, null, null, null, 2, 0, '0,0,0', null, 6, '�ɼ�');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (26, null, '��ע', 'Remark', 1, 27, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '¼��');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (27, null, '����', 'DeviceName', 1, 2, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '¼��');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (28, null, '����', 'CalDate', 1, 3, null, 0, 0, null, null, 3, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (29, null, '����ʱ��', 'CommTime', 1, 4, null, 0, 0, null, null, 2, 1, '0,0,0', 1, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (30, null, '��������', 'CommRange', 1, 5, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (31, null, '����ʱ��', 'CommTimeEfficiency', 1, 6, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (32, null, '����ʱ��', 'RunTime', 1, 7, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (33, null, '��������', 'RunRange', 1, 8, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (34, null, '����ʱ��', 'RunTimeEfficiency', 1, 9, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (35, null, '����', 'ResultName', 1, 10, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (36, null, '�Ż�����', 'OptimizationSuggestion', 1, 11, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (37, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 1, 12, null, 1, 1, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b66565"}', 1, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (38, null, '���ۼƲ�����', 'OilWeightProduction', 1, 13, null, 1, 1, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"ebbc1a"}', 1, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (39, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 1, 14, null, 1, 1, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1beef3"}', 1, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (40, null, '������ˮ��', 'WeightWaterCut', 1, 15, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (41, null, '����ϵ��', 'FullnessCoefficient', 1, 16, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (42, null, '�ù�', 'PumpSettingDepth', 1, 17, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (43, null, '��Һ��', 'ProducingfluidLevel', 1, 18, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (44, null, '��û��', 'Submergence', 1, 19, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (45, null, '����ƽ���', 'WattDegreeBalance', 1, 20, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (46, null, '����ƽ���', 'IDegreeBalance', 1, 21, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (47, null, '�ƶ�����', 'DeltaRadius', 1, 22, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (48, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 23, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (49, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 24, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (50, null, '����Ч��', 'WellDownSystemEfficiency', 1, 25, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (51, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 26, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (52, null, '�й�����', 'C_CLOUMN14', 1, 27, null, 1, 1, null, null, 2, 1, '0,0,0', null, 6, '�ɼ�');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (53, null, '��ע', 'Remark', 1, 28, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '¼��');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (54, null, 'ʱ��', 'CalTime', 1, 2, null, null, null, null, null, 4, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (55, null, '����ʱ��', 'CommTime', 1, 3, null, null, null, null, null, 2, 2, '0,0,0', 1, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (56, null, '��������', 'CommRange', 1, 4, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (57, null, '����ʱ��', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 2, '0,0,0', 2, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (58, null, '����ʱ��', 'RunTime', 1, 6, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (59, null, '��������', 'RunRange', 1, 7, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (60, null, '����ʱ��', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (61, null, '����', 'ResultName', 1, 9, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (62, null, '�Ż�����', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (63, null, '˲ʱ��Һ��', 'RealtimeLiquidWeightProduction', 1, 11, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (64, null, '˲ʱ������', 'RealtimeOilWeightProduction', 1, 12, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (65, null, '˲ʱ��ˮ��', 'RealtimeWaterWeightProduction', 1, 13, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (66, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 1, 14, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"c18f8f"}', null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (67, null, '���ۼƲ�����', 'OilWeightProduction', 1, 15, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"232432"}', null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (68, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 1, 16, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"3abaca"}', null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (69, null, '������ˮ��', 'WeightWaterCut', 1, 17, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (70, null, '����ϵ��', 'FullnessCoefficient', 1, 18, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (71, null, '�ù�', 'PumpSettingDepth', 1, 19, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (72, null, '��Һ��', 'ProducingfluidLevel', 1, 20, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (73, null, '��û��', 'Submergence', 1, 21, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (74, null, '����ƽ���', 'WattDegreeBalance', 1, 22, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (75, null, '����ƽ���', 'IDegreeBalance', 1, 23, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (76, null, '�ƶ�����', 'DeltaRadius', 1, 24, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (77, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 25, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (78, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 26, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (79, null, '����Ч��', 'WellDownSystemEfficiency', 1, 27, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (80, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 28, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (81, null, '�й�����', 'C_CLOUMN14', 1, 29, null, null, null, null, null, 2, 2, '0,0,0', null, 6, '�ɼ�');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (82, null, '��ע', 'Remark', 1, 30, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '¼��');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (83, null, '����', 'CalDate', 2, 2, null, null, null, null, null, 3, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (84, null, '����ʱ��', 'CommTime', 2, 3, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (85, null, '��������', 'CommRange', 2, 4, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (86, null, '����ʱ��', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (87, null, '����ʱ��', 'RunTime', 2, 6, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (88, null, '��������', 'RunRange', 2, 7, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (89, null, '����ʱ��', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (90, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 2, 9, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (91, null, '���ۼƲ�����', 'OilWeightProduction', 2, 10, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"4f4444"}', null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (92, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 2, 11, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"31bbbe"}', null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (93, null, '������ˮ��', 'WeightWaterCut', 2, 12, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (94, null, 'ת��', 'RPM', 2, 13, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (95, null, '�ù�', 'PumpSettingDepth', 2, 14, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (96, null, '��Һ��', 'ProducingfluidLevel', 2, 15, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (97, null, '��û��', 'Submergence', 2, 16, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (98, null, 'ϵͳЧ��', 'SystemEfficiency', 2, 17, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (99, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 2, 18, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (100, null, '�й�����', 'C_CLOUMN14', 2, 19, null, null, null, null, null, 2, 0, '0,0,0', null, 6, '�ɼ�');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (101, null, '��ע', 'Remark', 2, 20, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '¼��');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (102, null, '����', 'DeviceName', 2, 2, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '¼��');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (103, null, '����', 'CalDate', 2, 3, null, 0, 0, null, null, 3, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (104, null, '����ʱ��', 'CommTime', 2, 4, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (105, null, '��������', 'CommRange', 2, 5, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (106, null, '����ʱ��', 'CommTimeEfficiency', 2, 6, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (107, null, '����ʱ��', 'RunTime', 2, 7, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (108, null, '��������', 'RunRange', 2, 8, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (109, null, '����ʱ��', 'RunTimeEfficiency', 2, 9, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (110, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 2, 10, null, 1, 1, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e40c54"}', 1, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (111, null, '���ۼƲ�����', 'OilWeightProduction', 2, 11, null, 1, 1, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d7bb14"}', 1, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (112, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 2, 12, null, 1, 1, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1dbfb4"}', 1, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (113, null, '������ˮ��', 'WeightWaterCut', 2, 13, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (114, null, 'ת��', 'RPM', 2, 14, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (115, null, '�ù�', 'PumpSettingDepth', 2, 15, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (116, null, '��Һ��', 'ProducingfluidLevel', 2, 16, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (117, null, '��û��', 'Submergence', 2, 17, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (118, null, 'ϵͳЧ��', 'SystemEfficiency', 2, 18, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (119, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 2, 19, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (120, null, '�й�����', 'C_CLOUMN14', 2, 20, null, 1, 0, null, null, 2, 1, '0,0,0', null, 6, '�ɼ�');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (121, null, '��ע', 'Remark', 2, 21, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '¼��');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (122, null, 'ʱ��', 'CalTime', 2, 2, null, null, null, null, null, 4, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (123, null, '����ʱ��', 'CommTime', 2, 3, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (124, null, '��������', 'CommRange', 2, 4, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (125, null, '����ʱ��', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (126, null, '����ʱ��', 'RunTime', 2, 6, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (127, null, '��������', 'RunRange', 2, 7, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (128, null, '����ʱ��', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (129, null, '˲ʱ��Һ��', 'RealtimeLiquidVolumetricProduction', 2, 9, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (130, null, '˲ʱ������', 'RealtimeOilVolumetricProduction', 2, 10, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (131, null, '˲ʱ��ˮ��', 'RealtimeWaterVolumetricProduction', 2, 11, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (132, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 2, 12, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (133, null, '���ۼƲ�����', 'OilWeightProduction', 2, 13, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"995353"}', null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (134, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 2, 14, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"57a6a8"}', null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (135, null, '������ˮ��', 'WeightWaterCut', 2, 15, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (136, null, 'ת��', 'RPM', 2, 16, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (137, null, '�ù�', 'PumpSettingDepth', 2, 17, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (138, null, '��Һ��', 'ProducingfluidLevel', 2, 18, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (139, null, '��û��', 'Submergence', 2, 19, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (140, null, 'ϵͳЧ��', 'SystemEfficiency', 2, 20, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (141, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 2, 21, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '����');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (142, null, '�й�����', 'C_CLOUMN14', 2, 22, null, null, null, null, null, 2, 2, '0,0,0', null, 6, '�ɼ�');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (143, null, '��ע', 'Remark', 2, 23, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '¼��');


/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLINSTANCE����                                 */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (1, '���ͻ�A11MODBUSʵ��', 'instance1', 'modbus-tcp', 'modbus-tcp', 1, 'BB01', '0B', 0, 1, 'BB01', '0B', 100, 1, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (2, '�ݸ˱�A11MODBUSʵ��', 'instance2', 'modbus-tcp', 'modbus-tcp', 1, 'BB01', '0B', 0, 1, 'BB01', '0B', 100, 2, 2);


/*==============================================================*/
/* ��ʼ��tbl_protocolalarminstance����                            */
/*==============================================================*/
insert into TBL_PROTOCOLALARMINSTANCE (ID, NAME, CODE, ALARMUNITID, SORT)
values (1, '���ͻ�A11����ʵ��', 'alarminstance1', 1, 1);

insert into TBL_PROTOCOLALARMINSTANCE (ID, NAME, CODE, ALARMUNITID, SORT)
values (2, '�ݸ˱�A11����ʵ��', 'alarminstance2', 2, 2);

/*==============================================================*/
/* ��ʼ��tbl_protocoldisplayinstance����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLDISPLAYINSTANCE (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (1, '���ͻ�A11��ʾʵ��', 'displayinstance1', 1, 1);

insert into TBL_PROTOCOLDISPLAYINSTANCE (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (2, '�ݸ˱�A11��ʾʵ��', 'displayinstance2', 2, 2);

/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLREPORTINSTANCE����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (1, '���ͻ�������ʵ��һ', 'reportinstance1', 1, 1);

insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (2, '�ݸ˱þ�����ʵ��һ', 'reportinstance2', 2, 2);