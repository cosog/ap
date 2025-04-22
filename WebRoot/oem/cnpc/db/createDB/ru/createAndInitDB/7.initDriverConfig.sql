/*==============================================================*/
/* ��ʼ��TBL_PROTOCOL����                                        */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, '�������ܧ�� ��11', 'protocol1', 1, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"���ѧӧݧ֧ߧڧ� �� ����ҧܧѧ�","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"���ѧӧݧ֧ߧڧ� �� �ܧ������","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"������ڧӧ�էѧӧݧ֧ߧڧ�","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���֧ާ�֧�ѧ���� �ߧ� ������ ��ܧӧѧاڧߧ�","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"��","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���ѧҧ��֧� �������ߧڧ�","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"�����ѧߧ�ӧݧ֧ߧߧ��"},'
  ||'{"Value":1,"Meaning":"���֧�"}]},{"Title":"�ҧ֧اѧ��/����֧ܧ�ѧ��","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"�ҧ֧اѧ��"},{"Value":2,"Meaning":"����֧ܧ�ѧ��"}]},{"Title":"�ӧ�է���է֧�اѧߧڧ�","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"�����ڧ٧ӧ�է��ӧ� �اڧէܧڧ� ���ӧ֧��ߧ���֧�","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���ѧ٧ߧ�� ����","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B-��ѧ٧ߧ�� ����","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����� ��� ��-��ѧ٧�","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"���ѧ���ا֧ߧڧ� ��ѧ٧� ��","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���ѧ���ا֧ߧڧ� ��ѧ٧� B","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��-��ѧ٧ߧ�� �ߧѧ���ا֧ߧڧ�","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���ܧ�ڧӧߧѧ� �����֧ҧݧ�֧ާѧ� �ާ��ߧ����","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW��h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"������֧ҧݧ�֧ާѧ� ��֧ѧܧ�ڧӧߧѧ� �ާ��ߧ����","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar��h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���ܧ�ڧӧߧѧ� �ާ��ߧ����","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���֧ѧܧ�ڧӧߧѧ� �ާ��ߧ����","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���֧ӧ֧��ڧӧߧѧ� �ާ��ߧ����","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"���ѧܧ���","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���ߧѧ�֧ߧڧ� �ߧѧ����ۧܧ� ��ѧ�����","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���֧�ڧ�էڧ�ߧ���� ��ѧҧ���","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���ܧ������ �ӧ�ѧ�֧ߧڧ�","Addr":40430,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"r/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����ѧ�ѧ��ڧ� �ާ�ާ֧ߧ�","Addr":40432,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kN��m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���ߧ�֧�ӧѧ� ��ҧ��� �էڧߧѧާ�ާ֧���","Addr":40981,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"min","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"���ҧ�� �էڧѧԧ�ѧާާ� �ާ��ߧ���� �ӧ���ߧ��","Addr":40982,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"Fixed Time"},{"Value":1,"Meaning":"Manual"}]},{"Title":"���ڧߧѧާ�ާ֧��ڧ�֧�ܧѧ� �էڧѧԧ�ѧާާ� �٧ѧէѧ֧� �ܧ�ݧڧ�֧��ӧ� ����֧�","Addr":40983,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����ݧڧ�֧��ӧ� ���ܧ��, ���ҧ�ѧߧߧ�� �� ���ާ���� �էڧߧѧާ�ާ֧��ڧ�֧�ܧ�� �էڧѧԧ�ѧާާ�","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"����֧ާ� ��ҧ��� �էڧߧѧާ�ԧ�ѧާާ�","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���էѧ��� �� �ާڧߧ���","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"1/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"��էѧ�","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},'
  ||'{"Title":"���ѧߧߧ�� �� ��֧�֧ާ֧�֧ߧڧ� �էڧߧѧާ�ާ֧���","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.001,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"���ѧߧߧ�� �� �ߧѧԧ��٧ܧ� �ߧ� �էڧߧѧާ�ާ֧��","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���֧ܧ��ڧ� �էѧߧߧ�� �ܧ�ڧӧ��","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"���ѧߧߧ�� �ܧ�ڧӧ�� �ާ��ߧ����","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive","Meaning":[]}]
';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.CODE='protocol1';  
  COMMIT;  
END;  
/



/*==============================================================*/
/* ��ʼ��TBL_DATAMAPPING����                                          */
/*==============================================================*/
insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (1, '���ѧӧݧ֧ߧڧ� �� ����ҧܧѧ�', 'C_CLOUMN1', 0, 'TubingPressure', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (2, '���ѧӧݧ֧ߧڧ� �� �ܧ������', 'C_CLOUMN2', 0, 'CasingPressure', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (3, '������ڧӧ�էѧӧݧ֧ߧڧ�', 'C_CLOUMN3', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (4, '���֧ާ�֧�ѧ���� �ߧ� ������ ��ܧӧѧاڧߧ�', 'C_CLOUMN4', 0, 'WellHeadTemperature', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (5, '���ѧҧ��֧� �������ߧڧ�', 'C_CLOUMN5', 0, 'RunStatus', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (6, '�ӧ�է���է֧�اѧߧڧ�', 'C_CLOUMN6', 0, 'VolumeWaterCut', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (7, '�����ڧ٧ӧ�է��ӧ� �اڧէܧڧ� ���ӧ֧��ߧ���֧�', 'C_CLOUMN7', 0, 'ProducingfluidLevel', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (8, '���ѧ٧ߧ�� ����', 'C_CLOUMN8', 0, 'IA', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (9, 'B-��ѧ٧ߧ�� ����', 'C_CLOUMN9', 0, 'IB', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (10, '����� ��� ��-��ѧ٧�', 'C_CLOUMN10', 0, 'IC', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (11, '���ѧ���ا֧ߧڧ� ��ѧ٧� ��', 'C_CLOUMN11', 0, 'VA', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (12, '���ѧ���ا֧ߧڧ� ��ѧ٧� B', 'C_CLOUMN12', 0, 'VB', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (13, '��-��ѧ٧ߧ�� �ߧѧ���ا֧ߧڧ�', 'C_CLOUMN13', 0, 'VC', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (14, '���ܧ�ڧӧߧѧ� �����֧ҧݧ�֧ާѧ� �ާ��ߧ����', 'C_CLOUMN14', 0, 'TotalKWattH', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (15, '������֧ҧݧ�֧ާѧ� ��֧ѧܧ�ڧӧߧѧ� �ާ��ߧ����', 'C_CLOUMN15', 0, 'TotalKVarH', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (16, '���ܧ�ڧӧߧѧ� �ާ��ߧ����', 'C_CLOUMN16', 0, 'Watt3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (17, '���֧ѧܧ�ڧӧߧѧ� �ާ��ߧ����', 'C_CLOUMN17', 0, 'Var3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (18, '���֧ӧ֧��ڧӧߧѧ� �ާ��ߧ����', 'C_CLOUMN18', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (19, '���ѧܧ���', 'C_CLOUMN19', 0, 'PF3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (20, '���֧�ڧ�էڧ�ߧ���� ��ѧҧ���', 'C_CLOUMN20', 0, 'RunFrequency', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (21, '���ܧ������ �ӧ�ѧ�֧ߧڧ�', 'C_CLOUMN21', 0, 'RPM', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (22, '����ѧ�ѧ��ڧ� �ާ�ާ֧ߧ�', 'C_CLOUMN22', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (23, '���ߧ�֧�ӧѧ� ��ҧ��� �էڧߧѧާ�ާ֧���', 'C_CLOUMN23', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (24, '���ҧ�� �էڧѧԧ�ѧާާ� �ާ��ߧ���� �ӧ���ߧ��', 'C_CLOUMN24', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (25, '���ڧߧѧާ�ާ֧��ڧ�֧�ܧѧ� �էڧѧԧ�ѧާާ� �٧ѧէѧ֧� �ܧ�ݧڧ�֧��ӧ� ����֧�', 'C_CLOUMN25', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (26, '����ݧڧ�֧��ӧ� ���ܧ��, ���ҧ�ѧߧߧ�� �� ���ާ���� �էڧߧѧާ�ާ֧��ڧ�֧�ܧ�� �էڧѧԧ�ѧާާ�', 'C_CLOUMN26', 0, 'FESDiagramAcqCount', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (27, '����֧ާ� ��ҧ��� �էڧߧѧާ�ԧ�ѧާާ�', 'C_CLOUMN27', 0, 'FESDiagramAcqtime', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (28, '���էѧ��� �� �ާڧߧ���', 'C_CLOUMN28', 0, 'SPM', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (29, '��էѧ�', 'C_CLOUMN29', 0, 'Stroke', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (30, '���ѧߧߧ�� �� ��֧�֧ާ֧�֧ߧڧ� �էڧߧѧާ�ާ֧���', 'C_CLOUMN30', 0, 'Position_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (31, '���ѧߧߧ�� �� �ߧѧԧ��٧ܧ� �ߧ� �էڧߧѧާ�ާ֧��', 'C_CLOUMN31', 0, 'Load_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (32, '���֧ܧ��ڧ� �էѧߧߧ�� �ܧ�ڧӧ��', 'C_CLOUMN32', 0, 'Current_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (33, '���ѧߧߧ�� �ܧ�ڧӧ�� �ާ��ߧ����', 'C_CLOUMN33', 0, 'Power_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (34, '�ҧ֧اѧ��/����֧ܧ�ѧ��', 'C_CLOUMN34', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (35, '���ߧѧ�֧ߧڧ� �ߧѧ����ۧܧ� ��ѧ�����', 'C_CLOUMN35', 0, null, null, 1, 0);


/*==============================================================*/
/* ��ʼ��TBL_RUNSTATUSCONFIG����                                          */
/*==============================================================*/
insert into TBL_RUNSTATUSCONFIG (ID, PROTOCOL, ITEMNAME, ITEMMAPPINGCOLUMN, RUNVALUE, STOPVALUE, PROTOCOLTYPE, RESOLUTIONMODE, RUNCONDITION, STOPCONDITION)
values (1, 'protocol1', '���ѧҧ��֧� �������ߧڧ�', 'C_CLOUMN5', '1', '0', 0, 1, null, null);

/*==============================================================*/
/* ��ʼ��TBL_ACQ_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, SORT)
values (1, 'unit1', '���ҧ��ߧ�� �ѧԧ�֧ԧѧ� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', '�������ܧ�� ��11', '���ҧ��ߧ�� �ѧԧ�֧ԧѧ� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', 1);

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, SORT)
values (2, 'unit2', '���ݧ�� ��ҧ��� �էѧߧߧ�� �� ����ԧ�֧��ڧӧߧ�� �ӧڧߧ��ӧ�� �ߧѧ�����', '�������ܧ�� ��11', '���ݧ�� ��ҧ��� �էѧߧߧ�� �� ����ԧ�֧��ڧӧߧ�� �ӧڧߧ��ӧ�� �ߧѧ�����', 2);

/*==============================================================*/
/* ��ʼ��TBL_ACQ_GROUP_CONF����                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '�������� ��� ���ڧ�ҧ�֧�֧ߧڧ�', 60, 60, '�������ܧ�� ��11', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '����ߧ���ݧ�ߧѧ� �ԧ�����', 0, 0, '�������ܧ�� ��11', 1, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (3, 'group3', '�������� ��� ���ڧ�ҧ�֧�֧ߧڧ�', 60, 60, '�������ܧ�� ��11', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (4, 'group4', '����ߧ���ݧ�ߧѧ� �ԧ�����', 0, 0, '�������ܧ�� ��11', 1, null);

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
values (1, null, '���ѧҧ��֧� �������ߧڧ�', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (2, null, '���ܧ�ڧӧߧѧ� �����֧ҧݧ�֧ާѧ� �ާ��ߧ����', null, 1, null, '0,0,0', 1, '���ا֧էߧ֧ӧߧ�� �����֧ҧݧ֧ߧڧ� ��ݧ֧ܧ����ߧ֧�ԧڧ�');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (3, null, '���ߧѧ�֧ߧڧ� �ߧѧ����ۧܧ� ��ѧ�����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (4, null, '���֧�ڧ�էڧ�ߧ���� ��ѧҧ���', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (5, null, '����ݧڧ�֧��ӧ� ���ܧ��, ���ҧ�ѧߧߧ�� �� ���ާ���� �էڧߧѧާ�ާ֧��ڧ�֧�ܧ�� �էڧѧԧ�ѧާާ�', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (6, null, '����֧ާ� ��ҧ��� �էڧߧѧާ�ԧ�ѧާާ�', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (7, null, '���էѧ��� �� �ާڧߧ���', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (8, null, '��էѧ�', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (9, null, '���ѧߧߧ�� �� ��֧�֧ާ֧�֧ߧڧ� �էڧߧѧާ�ާ֧���', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (10, null, '���ѧߧߧ�� �� �ߧѧԧ��٧ܧ� �ߧ� �էڧߧѧާ�ާ֧��', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (11, null, '���֧ܧ��ڧ� �էѧߧߧ�� �ܧ�ڧӧ��', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (12, null, '���ѧߧߧ�� �ܧ�ڧӧ�� �ާ��ߧ����', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (13, null, '�ҧ֧اѧ��/����֧ܧ�ѧ��', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (14, null, '���ߧѧ�֧ߧڧ� �ߧѧ����ۧܧ� ��ѧ�����', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (15, null, '���ѧҧ��֧� �������ߧڧ�', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (16, null, '���ܧ������ �ӧ�ѧ�֧ߧڧ�', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (17, null, '����ѧ�ѧ��ڧ� �ާ�ާ֧ߧ�', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (18, null, '�ҧ֧اѧ��/����֧ܧ�ѧ��', null, 4, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (19, null, '���ߧѧ�֧ߧڧ� �ߧѧ����ۧܧ� ��ѧ�����', null, 4, null, '0,0,0', 0, null);

/*==============================================================*/
/* ��ʼ��TBL_ALARM_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, CALCULATETYPE, SORT)
values (1, 'alarmunit1', '���ݧ�� ��ڧԧߧѧݧڧ٧ѧ�ڧ� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', '�������ܧ�� ��11', '���ݧ�� ��ڧԧߧѧݧڧ٧ѧ�ڧ� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', 1, 1);

insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, CALCULATETYPE, SORT)
values (2, 'alarmunit2', '���ݧ�� ��ڧԧߧѧݧڧ٧ѧ�ڧ� �ӧڧߧ��ӧ�ԧ� �ߧѧ����', '�������ܧ�� ��11', '���ݧ�� ��ڧԧߧѧݧڧ٧ѧ�ڧ� �ӧڧߧ��ӧ�ԧ� �ߧѧ����', 2, 2);

/*==============================================================*/
/* ��ʼ��TBL_ALARM_ITEM2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (1, 1, null, 'offline', 'offline', 0, 0.000, null, null, null, null, 100, 1, 3, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (2, 1, null, 'code1201', '1201', 0, 1201.000, null, null, null, 60, 0, 0, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (3, 1, null, 'code1202', '1202', 0, 1202.000, null, null, null, 60, 0, 0, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (4, 1, null, 'code1203', '1203', 0, 1203.000, null, null, null, 60, 0, 0, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (5, 1, null, 'code1204', '1204', 0, 1204.000, null, null, null, 60, 0, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (6, 1, null, 'code1205', '1205', 0, 1205.000, null, null, null, 60, 300, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (7, 1, null, 'code1206', '1206', 0, 1206.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (8, 1, null, 'code1207', '1207', 0, 1207.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (9, 1, null, 'code1208', '1208', 0, 1208.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (10, 1, null, 'code1209', '1209', 0, 1209.000, null, null, null, 60, 300, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (11, 1, null, 'code1210', '1210', 0, 1210.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (12, 1, null, 'code1211', '1211', 0, 1211.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (13, 1, null, 'code1212', '1212', 0, 1212.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (14, 1, null, 'code1213', '1213', 0, 1213.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (15, 1, null, 'code1214', '1214', 0, 1214.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (16, 1, null, 'code1215', '1215', 0, 1215.000, null, null, null, 60, 100, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (17, 1, null, 'code1216', '1216', 0, 1216.000, null, null, null, 60, 100, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (18, 1, null, 'code1217', '1217', 0, 1217.000, null, null, null, 60, 100, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (19, 1, null, 'code1218', '1218', 0, 1218.000, null, null, null, 60, 300, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (20, 1, null, 'code1219', '1219', 0, 1219.000, null, null, null, 60, 100, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (21, 1, null, 'code1220', '1220', 0, 1220.000, null, null, null, 60, 100, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (22, 1, null, 'code1221', '1221', 0, 1221.000, null, null, null, 60, 100, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (23, 1, null, 'code1222', '1222', 0, 1222.000, null, null, null, 60, 100, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (24, 1, null, 'code1223', '1223', 0, 1223.000, null, null, null, 60, 100, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (25, 1, null, 'code1224', '1224', 0, 1224.000, null, null, null, 60, 300, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (26, 1, null, 'code1225', '1225', 0, 1225.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (27, 1, null, 'code1226', '1226', 0, 1226.000, null, null, null, 60, 300, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (28, 1, null, 'code1227', '1227', 0, 1227.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (29, 1, null, 'code1230', '1230', 0, 1230.000, null, null, null, 60, 300, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (30, 1, null, 'code1231', '1231', 0, 1231.000, null, null, null, 60, 200, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (31, 1, null, 'code1232', '1232', 0, 1232.000, null, null, null, 60, 100, 1, 4, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (32, 1, null, 'Stopped', 'stop', 0, 0.000, null, null, null, null, 300, 1, 6, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (33, 2, null, 'offline', 'offline', 0, 0.000, null, null, null, null, 100, 1, 3, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (34, 2, null, 'Stopped', 'stop', 0, 0.000, null, null, null, null, 300, 1, 6, 0, 0, 0, null);

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_UNIT_CONF����                                */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK, CALCULATETYPE, SORT)
values (1, 'unit1', '���ݧ�� ����ҧ�ѧا֧ߧڧ� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', '�������ܧ�� ��11', 1, '���ݧ�� ����ҧ�ѧا֧ߧڧ� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', 1, 1);

insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK, CALCULATETYPE, SORT)
values (2, 'unit2', '���ݧ�� �ڧߧէڧܧѧ�ڧ� �ӧڧߧ��ӧ�ԧ� �ߧѧ����', '�������ܧ�� ��11', 2, '���ݧ�� �ڧߧէڧܧѧ�ڧ� �ӧڧߧ��ӧ�ԧ� �ߧѧ����', 2, 2);

/*==============================================================*/
/* ��ʼ��TBL_DISPLAY_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (1, null, '���ܧ�ڧӧߧѧ� �����֧ҧݧ�֧ާѧ� �ާ��ߧ����', 'C_CLOUMN14', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (2, null, '���֧�ڧ�էڧ�ߧ���� ��ѧҧ���', 'C_CLOUMN20', 1, null, null, null, '{"sort":6,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"a07474"}', '{"sort":6,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"a07474"}', 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (3, null, '����ݧڧ�֧��ӧ� ���ܧ��, ���ҧ�ѧߧߧ�� �� ���ާ���� �էڧߧѧާ�ާ֧��ڧ�֧�ܧ�� �էڧѧԧ�ѧާާ�', 'C_CLOUMN26', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (4, null, '����֧ާ� ��ҧ��� �էڧߧѧާ�ԧ�ѧާާ�', 'C_CLOUMN27', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (5, null, '���էѧ��� �� �ާڧߧ���', 'C_CLOUMN28', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (6, null, '��էѧ�', 'C_CLOUMN29', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (7, null, '����֧ާ� ��ߧݧѧۧ�', 'CommTime', 1, 1, null, null, null, null, 1, '0,0,0', 1, null, null, null, null, 1, 1, 1, 1, 1, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (8, null, '�����֧ܧ�ڧӧߧ���� �ӧ�֧ާ֧ߧ� �� �ڧߧ�֧�ߧ֧��', 'CommTimeEfficiency', 1, 2, null, null, null, null, 1, '0,0,0', 2, null, null, null, null, 1, 1, 1, 1, 2, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (9, null, '����֧ާ֧ߧߧ�� ��ѧާܧ� ��ߧݧѧۧ�', 'CommRange', 1, 3, null, null, null, null, 1, '0,0,0', 3, null, null, null, null, 1, 1, 1, 1, 3, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (10, null, '���ѧҧ��֧� �������ߧڧ�', 'RunStatusName', 1, 4, null, null, null, null, 1, '0,0,0', 4, null, null, null, null, 0, 1, 0, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (11, null, '�����է�ݧاڧ�֧ݧ�ߧ����', 'RunTime', 1, 7, null, null, null, null, 1, '0,0,0', 5, null, null, null, null, 1, 1, 1, 1, 5, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (12, null, '�����֧ܧ�ڧӧߧ���� �ӧ� �ӧ�֧ާ� �ӧ���ݧߧ֧ߧڧ�', 'RunTimeEfficiency', 1, 8, null, null, null, null, 1, '0,0,0', 6, null, null, null, null, 1, 1, 1, 1, 6, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (13, null, '���ڧѧ�ѧ٧�� �ӧ�֧ާ֧ߧ� �ӧ���ݧߧ֧ߧڧ�', 'RunRange', 1, 9, null, null, null, null, 1, '0,0,0', 7, null, null, null, null, 1, 1, 1, 1, 7, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (14, null, '����ݧ�ӧڧ� ��ѧҧ���', 'ResultName', 1, 10, null, null, null, null, 1, '0,0,0', 8, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (15, null, '���ѧܧ�ڧާѧݧ�ߧѧ� �ߧѧԧ��٧ܧ�', 'FMax', 1, 20, null, null, '{"sort":5,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b824e6"}', '{"sort":5,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"b824e6"}', 1, '0,0,0', 9, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (16, null, '���ڧߧڧާѧݧ�ߧѧ� �ߧѧԧ��٧ܧ�', 'FMin', 1, 23, null, null, '{"sort":6,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e25e1d"}', '{"sort":6,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e25e1d"}', 1, '0,0,0', 10, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (17, null, '����� ��ݧ�ߧا֧��', 'PlungerStroke', 1, 15, null, null, null, null, 1, '0,0,0', 11, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (18, null, '�����֧ܧ�ڧӧߧ�� ���� ��ݧ�ߧا֧��', 'AvailablePlungerStroke', 1, 18, null, null, null, null, 1, '0,0,0', 12, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (19, null, '�����֧ܧ�ڧӧߧ�� ���� �����ߧ� ���� ��������ӧڧ� �اڧէܧ����', 'NoLiquidAvailablePlungerStroke', 1, 27, null, null, null, null, 1, '0,0,0', 13, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (20, null, '�������ڧ�ڧ֧ߧ� �٧ѧ��ݧߧ֧ߧڧ�', 'FullnessCoefficient', 1, 21, null, null, null, null, 1, '0,0,0', 14, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (21, null, '�������ڧ�ڧ֧ߧ� �ߧѧ��ݧߧ֧ߧڧ� ���� ��������ӧڧ� �اڧէܧ����', 'NoLiquidFullnessCoefficient', 1, 24, null, null, null, null, 1, '0,0,0', 15, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (22, null, '���֧��֧�ڧ�֧�ܧѧ� �ާѧܧ�ڧާѧݧ�ߧѧ� �ݧڧߧڧ� �ߧѧԧ��٧ܧ�', 'UpperLoadLine', 1, 26, null, null, null, null, 1, '0,0,0', 16, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (23, null, '���֧��֧�ڧ�֧�ܧѧ� �ާڧߧڧާѧݧ�ߧѧ� �ԧ��٧�ӧѧ� �ݧڧߧڧ�', 'LowerLoadLine', 1, 29, null, null, null, null, 1, '0,0,0', 17, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (24, null, '���֧��֧�ڧ�֧�ܧ�� ����ڧ٧ӧ�է��ӧ� �اڧէܧ����', 'TheoreticalProduction', 1, 25, null, null, null, null, 1, '0,0,0', 18, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (25, null, '�����ڧ٧ӧ�է��ӧ� �اڧէܧ���֧� �� ��֧اڧާ� ��֧ѧݧ�ߧ�ԧ� �ӧ�֧ާ֧ߧ�', 'LiquidVolumetricProduction', 1, 13, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"2560d4"}', 1, '0,0,0', 19, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (26, null, '����ҧ��� �ߧ֧��� �� ��֧اڧާ� ��֧ѧݧ�ߧ�ԧ� �ӧ�֧ާ֧ߧ�', 'OilVolumetricProduction', 1, 16, null, null, '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"4fbfc4"}', '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"3fa8d2"}', 1, '0,0,0', 20, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (27, null, '����ҧ��� �ӧ�է� �� ��֧اڧާ� ��֧ѧݧ�ߧ�ԧ� �ӧ�֧ާ֧ߧ�', 'WaterVolumetricProduction', 1, 19, null, null, null, null, 1, '0,0,0', 21, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (28, null, '���� ����֧ܧ�ڧӧߧ�ާ� ���է� ��ݧ�ߧا֧�� ��ѧ���ڧ��ӧѧ֧��� �ӧ���էߧѧ� �ާ��ߧ����', 'AvailablePlungerStrokeProd_v', 1, 28, null, null, null, null, 1, '0,0,0', 22, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (29, null, '����ݧڧ�֧��ӧ� �اڧէܧ����, �ӧ��֧ܧѧ��֧� �ڧ� �٧ѧ٧��� �ߧѧ����', 'PumpClearanceleakProd_v', 1, 30, null, null, null, null, 1, '0,0,0', 23, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (30, null, '����ӧ�ܧ��ߧ�� ������ߧ�� ����ڧ٧ӧ�է��ӧ� �اڧէܧ����', 'LiquidVolumetricProduction_l', 1, 22, null, null, null, null, 1, '0,0,0', 24, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (31, null, '����֧էߧ�� �ѧܧ�ڧӧߧѧ� �ާ��ߧ����', 'AverageWatt', 1, 44, null, null, null, null, 1, '0,0,0', 25, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (32, null, '�����ߧ���� ��ӧ֧��ӧ�ԧ� ����ݧҧ�', 'PolishRodPower', 1, 47, null, null, null, null, 1, '0,0,0', 26, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (33, null, '���ڧէ���ߧ֧�ԧڧ�', 'WaterPower', 1, 41, null, null, null, null, 1, '0,0,0', 27, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (34, null, '�����֧ܧ�ڧӧߧ���� ��ѧҧ��� �ߧ� �ԧ��ߧ��', 'SurfaceSystemEfficiency', 1, 43, null, null, null, null, 1, '0,0,0', 28, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (35, null, '���ߧ���ڧ�ܧӧѧاڧߧߧѧ� ����֧ܧ�ڧӧߧ����', 'WellDownSystemEfficiency', 1, 46, null, null, null, null, 1, '0,0,0', 29, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (36, null, '�����֧ܧ�ڧӧߧ���� ��ڧ��֧ާ�', 'SystemEfficiency', 1, 40, null, null, null, null, 1, '0,0,0', 30, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (37, null, '���ݧ��ѧէ�', 'Area', 1, 42, null, null, null, null, 1, '0,0,0', 31, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (38, null, '������֧ҧݧ�֧ާѧ� �ާ��ߧ���� �ߧ� 100 �ާ֧���� ���է�֧ާ�', 'EnergyPer100mLift', 1, 45, null, null, null, null, 1, '0,0,0', 32, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (39, null, '���٧ާ֧ߧ֧ߧڧ� �էݧڧߧ� ��էڧݧڧ��', 'RodFlexLength', 1, 31, null, null, null, null, 1, '0,0,0', 33, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (40, null, '���ݧڧߧ� ����ҧܧ� �ӧѧ��ڧ��֧���', 'TubingFlexLength', 1, 32, null, null, null, null, 1, '0,0,0', 34, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (41, null, '����ڧ�ѧ�֧ߧڧ� �ڧߧ֧��ڧ�ߧߧ�� �ߧѧԧ��٧ܧ�', 'InertiaLength', 1, 33, null, null, null, null, 1, '0,0,0', 35, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (42, null, '���ѧܧ��� ����֧�� ���� �ڧߧ��ݧ���', 'PumpEff1', 1, 34, null, null, null, null, 1, '0,0,0', 36, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (43, null, '�������ڧ�ڧ֧ߧ� �٧ѧ��ݧߧ֧ߧڧ�', 'PumpEff2', 1, 35, null, null, null, null, 1, '0,0,0', 37, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (44, null, '�������ڧ�ڧ֧ߧ� ���֧�ܧ� �٧ѧ٧���', 'PumpEff3', 1, 36, null, null, null, null, 1, '0,0,0', 38, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (45, null, '�������ڧ�ڧ֧ߧ� ���ѧէܧ� �اڧէܧ���֧�', 'PumpEff4', 1, 37, null, null, null, null, 1, '0,0,0', 39, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (46, null, '�����֧ܧ�ڧӧߧ���� �ߧѧ����', 'PumpEff', 1, 38, null, null, null, null, 1, '0,0,0', 40, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (47, null, '���ѧӧݧ֧ߧڧ� �ߧ� �ӧ��է� �ߧѧ����', 'PumpIntakeP', 1, 61, null, null, null, null, 1, '0,0,0', 41, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (48, null, '���֧ާ�֧�ѧ���� �ߧ� �ӧ��է� �� �ߧѧ���', 'PumpIntakeT', 1, 62, null, null, null, null, 1, '0,0,0', 42, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (49, null, '������ߧ��֧ߧڧ� �ԧѧ٧� �� �اڧէܧ���� �ߧ� �ӧ��է� �ߧѧ����', 'PumpIntakeGOL', 1, 63, null, null, null, null, 1, '0,0,0', 43, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (50, null, '����٧ܧ���� �ߧ� �ӧ��է� �ߧѧ����', 'PumpIntakeVisl', 1, 64, null, null, null, null, 1, '0,0,0', 44, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (51, null, '���ҧ�֧ާߧ�� �ܧ����ڧ�ڧ֧ߧ� ������ �ߧ֧��� �ߧ� �ӧ��է� �ߧѧ����', 'PumpIntakeBo', 1, 65, null, null, null, null, 1, '0,0,0', 45, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (52, null, '���ѧӧݧ֧ߧڧ� �ߧ� �ӧ���է� �ߧѧ����', 'PumpOutletP', 1, 67, null, null, null, null, 1, '0,0,0', 46, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (53, null, '���֧ާ�֧�ѧ���� �ߧ� �ӧ���է� �ߧѧ����', 'PumpOutletT', 1, 68, null, null, null, null, 1, '0,0,0', 47, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (54, null, '����ߧ��֧ߧڧ� �ԧѧ٧� �� �اڧէܧ���� �ߧ� �ӧ���է� �ڧ� �ߧѧ����', 'PumpOutletGOL', 1, 69, null, null, null, null, 1, '0,0,0', 48, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (55, null, '����٧ܧ���� �ߧ� �ӧ���է� �ߧѧ����', 'PumpOutletVisl', 1, 70, null, null, null, null, 1, '0,0,0', 49, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (56, null, '���ҧ�֧ާߧ�� �ܧ����ڧ�ڧ֧ߧ� �ߧ֧��� �ߧ� �ӧ���է� �ڧ� �ߧѧ����', 'PumpOutletBo', 1, 71, null, null, null, null, 1, '0,0,0', 50, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (57, null, '���ѧܧ�ڧާѧݧ�ߧ�� ���� �ӧ����է��֧ԧ� ���է�', 'UpStrokeIMax', 1, 50, null, null, null, null, 1, '0,0,0', 51, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (58, null, '���ѧܧ�ڧާѧݧ�ߧ�� ���� �ߧڧ���է��֧ԧ� ���է�', 'DownStrokeIMax', 1, 51, null, null, null, null, 1, '0,0,0', 52, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (59, null, '���ѧܧ�ڧާѧݧ�ߧѧ� �ާ��ߧ���� �ߧ� �ӧ����է��֧� ���է�', 'UpStrokeWattMax', 1, 53, null, null, null, null, 1, '0,0,0', 53, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (60, null, '���ѧܧ�ڧާѧݧ�ߧѧ� �ާ��ߧ���� ���է� �ӧߧڧ�', 'DownStrokeWattMax', 1, 54, null, null, null, null, 1, '0,0,0', 54, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (61, null, '���֧ܧ��ڧ� �ҧѧݧѧߧ�', 'IDegreeBalance', 1, 49, null, null, null, null, 1, '0,0,0', 55, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (62, null, '���ѧݧѧߧ� �ާ��ߧ����', 'WattDegreeBalance', 1, 52, null, null, null, null, 1, '0,0,0', 56, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (63, null, '���ѧ�����ߧڧ� �� �����', 'DeltaRadius', 1, 55, null, null, null, null, 1, '0,0,0', 57, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (64, null, '���ѧ٧ߧڧ�� �ܧ���֧ܧ�ڧ� ����ӧߧ�', 'LevelDifferenceValue', 1, 59, null, null, null, null, 1, '0,0,0', 58, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (65, null, '���ߧӧ֧��ڧ��ӧѧߧߧ�� ����ӧ֧ߧ� ����ڧ٧ӧ�է��ӧ֧ߧߧ�� �اڧէܧ����', 'CalcProducingfluidLevel', 1, 58, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b89393"}', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"8f6b6b"}', 1, '0,0,0', 59, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (66, null, '���ا֧էߧ֧ӧߧ�� �����֧ҧݧ֧ߧڧ� ��ݧ֧ܧ����ߧ֧�ԧڧ�', 'C_CLOUMN14_TOTAL', 1, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (67, null, '�ҧ֧اѧ��/����֧ܧ�ѧ��', 'C_CLOUMN34', 1, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, null, null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (68, null, '���ߧѧ�֧ߧڧ� �ߧѧ����ۧܧ� ��ѧ�����', 'C_CLOUMN35', 1, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, null, null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (69, null, '���ݧ��ߧ���� ������ �ߧ֧���', 'CrudeOilDensity', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (70, null, '���ݧ��ߧ���� �ӧ�է�', 'WaterDensity', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (71, null, '����ߧ��ڧ�֧ݧ�ߧѧ� ��ݧ��ߧ���� ���ڧ��էߧ�ԧ� �ԧѧ٧�', 'NaturalGasRelativeDensity', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (72, null, '���ѧӧݧ֧ߧڧ� �ߧѧ���֧ߧڧ�', 'SaturationPressure', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (73, null, '���ݧ�ҧڧߧ� �ާѧ�ݧ�ߧ�ԧ� ��ݧ��', 'ReservoirDepth', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (74, null, '���֧ާ�֧�ѧ���� �ާѧ�ݧ�ߧ�ԧ� ��ݧ��', 'ReservoirTemperature', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (75, null, '���ѧӧݧ֧ߧڧ� �� ����ҧܧѧ�', 'TubingPressure', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (76, null, '���ѧӧݧ֧ߧڧ� �� �ܧ������', 'CasingPressure', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (77, null, '���֧ާ�֧�ѧ���� �ߧ� ������ ��ܧӧѧاڧߧ�', 'WellHeadTemperature', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (78, null, '����է���է֧�اѧߧڧ�', 'WaterCut', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (79, null, '������ߧ��֧ߧڧ� �ԧѧ٧� �� �ߧ֧���', 'ProductionGasOilRatio', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (80, null, '�����ڧ٧ӧ�է��ӧ� �اڧէܧڧ� ���ӧ֧��ߧ���֧�', 'ProducingfluidLevel', 1, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (81, null, '���ݧ�ҧڧߧ� �ߧѧ����', 'PumpSettingDepth', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (82, null, '���ڧѧާ֧�� �ҧ��ܧ�', 'PumpBoreDiameter', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (83, null, '���ߧѧ�֧ߧڧ� �ܧ���֧ܧ�ڧ� ����ӧߧ�', 'LevelCorrectValue', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (84, null, '���ѧҧ��֧� �������ߧڧ�', 'C_CLOUMN5', 2, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (85, null, '���ܧ������ �ӧ�ѧ�֧ߧڧ�', 'C_CLOUMN21', 2, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (86, null, '����ѧ�ѧ��ڧ� �ާ�ާ֧ߧ�', 'C_CLOUMN22', 2, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (87, null, '����֧ާ� ��ߧݧѧۧ�', 'CommTime', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (88, null, '�����֧ܧ�ڧӧߧ���� �ӧ�֧ާ֧ߧ� �� �ڧߧ�֧�ߧ֧��', 'CommTimeEfficiency', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (89, null, '����֧ާ֧ߧߧ�� ��ѧާܧ� ��ߧݧѧۧ�', 'CommRange', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (90, null, '�����է�ݧاڧ�֧ݧ�ߧ����', 'RunTime', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (91, null, '�����֧ܧ�ڧӧߧ���� �ӧ� �ӧ�֧ާ� �ӧ���ݧߧ֧ߧڧ�', 'RunTimeEfficiency', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (92, null, '���ڧѧ�ѧ٧�� �ӧ�֧ާ֧ߧ� �ӧ���ݧߧ֧ߧڧ�', 'RunRange', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (93, null, '���֧��֧�ڧ�֧�ܧ�� ����ڧ٧ӧ�է��ӧ� �اڧէܧ����', 'TheoreticalProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (94, null, '�����ڧ٧ӧ�է��ӧ� �اڧէܧ���֧� �� ��֧اڧާ� ��֧ѧݧ�ߧ�ԧ� �ӧ�֧ާ֧ߧ�', 'LiquidVolumetricProduction', 2, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"1fe4f8"}', '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"31d8e9"}', 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (95, null, '����ҧ��� �ߧ֧��� �� ��֧اڧާ� ��֧ѧݧ�ߧ�ԧ� �ӧ�֧ާ֧ߧ�', 'OilVolumetricProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (96, null, '����ҧ��� �ӧ�է� �� ��֧اڧާ� ��֧ѧݧ�ߧ�ԧ� �ӧ�֧ާ֧ߧ�', 'WaterVolumetricProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (97, null, '����ӧ�ܧ��ߧ�� ������ߧ�� ����ڧ٧ӧ�է��ӧ� �اڧէܧ����', 'LiquidVolumetricProduction_l', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (98, null, '�����ڧ٧ӧ�է��ӧ� �اڧէܧ���֧� �� ��֧اڧާ� ��֧ѧݧ�ߧ�ԧ� �ӧ�֧ާ֧ߧ�', 'LiquidWeightProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (99, null, '����ҧ��� �ߧ֧��� �� ��֧اڧާ� ��֧ѧݧ�ߧ�ԧ� �ӧ�֧ާ֧ߧ�', 'OilWeightProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (100, null, '����ҧ��� �ӧ�է� �� ��֧اڧާ� ��֧ѧݧ�ߧ�ԧ� �ӧ�֧ާ֧ߧ�', 'WaterWeightProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (101, null, '����֧էߧ�� �ѧܧ�ڧӧߧѧ� �ާ��ߧ����', 'AverageWatt', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (102, null, '���ڧէ���ߧ֧�ԧڧ�', 'WaterPower', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (103, null, '�����֧ܧ�ڧӧߧ���� ��ڧ��֧ާ�', 'SystemEfficiency', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (104, null, '���ҧ�֧ާߧ�� ������', 'PumpEff1', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (105, null, '�������ڧ�ڧ֧ߧ� ���ѧէܧ� �اڧէܧ���֧�', 'PumpEff2', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (106, null, '�����֧ܧ�ڧӧߧ���� �ߧѧ����', 'PumpEff', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (107, null, '���ѧӧݧ֧ߧڧ� �ߧ� �ӧ��է� �ߧѧ����', 'PumpIntakeP', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (108, null, '���֧ާ�֧�ѧ���� �ߧ� �ӧ��է� �� �ߧѧ���', 'PumpIntakeT', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (109, null, '������ߧ��֧ߧڧ� �ԧѧ٧� �� �اڧէܧ���� �ߧ� �ӧ��է� �ߧѧ����', 'PumpIntakeGOL', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (110, null, '����٧ܧ���� �ߧ� �ӧ��է� �ߧѧ����', 'PumpIntakeVisl', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (111, null, '���ҧ�֧ާߧ�� �ܧ����ڧ�ڧ֧ߧ� ������ �ߧ֧��� �ߧ� �ӧ��է� �ߧѧ����', 'PumpIntakeBo', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (112, null, '���ѧӧݧ֧ߧڧ� �ߧ� �ӧ���է� �ߧѧ����', 'PumpOutletP', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (113, null, '���֧ާ�֧�ѧ���� �ߧ� �ӧ���է� �ߧѧ����', 'PumpOutletT', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (114, null, '����ߧ��֧ߧڧ� �ԧѧ٧� �� �اڧէܧ���� �ߧ� �ӧ���է� �ڧ� �ߧѧ����', 'PumpOutletGOL', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (115, null, '����٧ܧ���� �ߧ� �ӧ���է� �ߧѧ����', 'PumpOutletVisl', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (116, null, '���ҧ�֧ާߧ�� �ܧ����ڧ�ڧ֧ߧ� �ߧ֧��� �ߧ� �ӧ���է� �ڧ� �ߧѧ����', 'PumpOutletBo', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (117, null, '�ҧ֧اѧ��/����֧ܧ�ѧ��', 'C_CLOUMN34', 2, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, null, null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (118, null, '���ߧѧ�֧ߧڧ� �ߧѧ����ۧܧ� ��ѧ�����', 'C_CLOUMN35', 2, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, null, null, null, null, null, null);

/*==============================================================*/
/* ��ʼ��TBL_REPORT_UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (1, 'unit1', '���ݧ�� ����֧�ߧ���� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 1, 'oilWell_PumpingDailyReport', 1);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (2, 'unit2', '���ݧ�� ����֧�ߧ���� �ӧڧߧ��ӧ�ԧ� �ߧѧ����', 'oilWell_ScrewPump', 'oilWell_ScrewPumpProductionReoirt', 2, 'oilWell_ScrewPumpDailyReport', 2);
/*==============================================================*/
/* ��ʼ��TBL_REPORT_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (1, null, 'Date', 'CalDate', 1, 2, null, null, null, null, null, 3, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (2, null, 'Comm Time', 'CommTime', 1, 3, null, null, null, null, null, 2, 0, '0,0,0', 1, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (3, null, 'Comm Range', 'CommRange', 1, 4, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (4, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (5, null, 'Run Time', 'RunTime', 1, 6, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (6, null, 'Run Range', 'RunRange', 1, 7, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (7, null, 'Run Time Efficiency', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (8, null, 'Result Name', 'ResultName', 1, 9, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (9, null, 'Optimization Suggestion', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (10, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 1, 11, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b96161"}', null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (11, null, 'Total Oil Weight Production', 'OilWeightProduction', 1, 12, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e99314"}', null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (12, null, 'Total Water Weight Production', 'WaterWeightProduction', 1, 13, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"19c2eb"}', null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (13, null, 'Weight Water Cut', 'WeightWaterCut', 1, 14, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (14, null, 'Fullness Coefficient', 'FullnessCoefficient', 1, 15, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (15, null, 'Pump Setting Depth', 'PumpSettingDepth', 1, 16, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (16, null, 'Producing Fluid Level', 'ProducingfluidLevel', 1, 17, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (17, null, 'Submergence', 'Submergence', 1, 18, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (18, null, 'Power Degree Balance', 'WattDegreeBalance', 1, 19, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (19, null, 'Current Degree Balance', 'IDegreeBalance', 1, 20, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (20, null, 'Delta Radius', 'DeltaRadius', 1, 21, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (21, null, 'System Efficiency', 'SystemEfficiency', 1, 22, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (22, null, 'Surface System Efficiency', 'SurfaceSystemEfficiency', 1, 23, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (23, null, 'Well Down System Efficiency', 'WellDownSystemEfficiency', 1, 24, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (24, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 1, 25, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (25, null, 'Active Power Consumption', 'C_CLOUMN14', 1, 26, null, null, null, null, null, 2, 0, '0,0,0', null, 6, '0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (26, null, 'Remark', 'Remark', 1, 27, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '2');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (27, null, 'Device', 'DeviceName', 1, 2, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '2');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (28, null, 'Date', 'CalDate', 1, 3, null, 0, 0, null, null, 3, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (29, null, 'Comm Time', 'CommTime', 1, 4, null, 0, 0, null, null, 2, 1, '0,0,0', 1, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (30, null, 'Comm Range', 'CommRange', 1, 5, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (31, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 1, 6, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (32, null, 'Run Time', 'RunTime', 1, 7, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (33, null, 'Run Range', 'RunRange', 1, 8, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (34, null, 'Run Time Efficiency', 'RunTimeEfficiency', 1, 9, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (35, null, 'Result Name', 'ResultName', 1, 10, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (36, null, 'Optimization Suggestion', 'OptimizationSuggestion', 1, 11, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (37, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 1, 12, null, 1, 1, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b66565"}', 1, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (38, null, 'Total Oil Weight Production', 'OilWeightProduction', 1, 13, null, 1, 1, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"ebbc1a"}', 1, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (39, null, 'Total Water Weight Production', 'WaterWeightProduction', 1, 14, null, 1, 1, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1beef3"}', 1, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (40, null, 'Weight Water Cut', 'WeightWaterCut', 1, 15, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (41, null, 'Fullness Coefficient', 'FullnessCoefficient', 1, 16, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (42, null, 'Pump Setting Depth', 'PumpSettingDepth', 1, 17, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (43, null, 'Producing Fluid Level', 'ProducingfluidLevel', 1, 18, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (44, null, 'Submergence', 'Submergence', 1, 19, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (45, null, 'Power Degree Balance', 'WattDegreeBalance', 1, 20, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (46, null, 'Current Degree Balance', 'IDegreeBalance', 1, 21, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (47, null, 'Delta Radius', 'DeltaRadius', 1, 22, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (48, null, 'System Efficiency', 'SystemEfficiency', 1, 23, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (49, null, 'Surface System Efficiency', 'SurfaceSystemEfficiency', 1, 24, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (50, null, 'Well Down System Efficiency', 'WellDownSystemEfficiency', 1, 25, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (51, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 1, 26, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (52, null, 'Active Power Consumption', 'C_CLOUMN14', 1, 27, null, 1, 1, null, null, 2, 1, '0,0,0', null, 6, '0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (53, null, 'Remark', 'Remark', 1, 28, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '2');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (54, null, 'ʱ��', 'CalTime', 1, 2, null, null, null, null, null, 4, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (55, null, '����ʱ��', 'CommTime', 1, 3, null, null, null, null, null, 2, 2, '0,0,0', 1, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (56, null, '��������', 'CommRange', 1, 4, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (57, null, '����ʱ��', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 2, '0,0,0', 2, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (58, null, '����ʱ��', 'RunTime', 1, 6, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (59, null, '��������', 'RunRange', 1, 7, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (60, null, '����ʱ��', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (61, null, '����', 'ResultName', 1, 9, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (62, null, '�Ż�����', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (63, null, '˲ʱ��Һ��', 'RealtimeLiquidWeightProduction', 1, 11, null, null, null, null, null, 2, 2, '0,0,0', null, null, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (64, null, '˲ʱ������', 'RealtimeOilWeightProduction', 1, 12, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (65, null, '˲ʱ��ˮ��', 'RealtimeWaterWeightProduction', 1, 13, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (66, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 1, 14, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"c18f8f"}', null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (67, null, '���ۼƲ�����', 'OilWeightProduction', 1, 15, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"232432"}', null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (68, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 1, 16, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"3abaca"}', null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (69, null, '������ˮ��', 'WeightWaterCut', 1, 17, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (70, null, '����ϵ��', 'FullnessCoefficient', 1, 18, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (71, null, '�ù�', 'PumpSettingDepth', 1, 19, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (72, null, '��Һ��', 'ProducingfluidLevel', 1, 20, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (73, null, '��û��', 'Submergence', 1, 21, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (74, null, '����ƽ���', 'WattDegreeBalance', 1, 22, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (75, null, '����ƽ���', 'IDegreeBalance', 1, 23, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (76, null, '�ƶ�����', 'DeltaRadius', 1, 24, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (77, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 25, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (78, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 26, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (79, null, '����Ч��', 'WellDownSystemEfficiency', 1, 27, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (80, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 28, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (81, null, '���ܧ�ڧӧߧѧ� �����֧ҧݧ�֧ާѧ� �ާ��ߧ����', 'C_CLOUMN14', 1, 29, null, null, null, null, null, 2, 2, '0,0,0', null, 6, '0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (82, null, '��ע', 'Remark', 1, 30, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '2');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (83, null, 'Date', 'CalDate', 2, 2, null, null, null, null, null, 3, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (84, null, 'Comm Time', 'CommTime', 2, 3, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (85, null, 'Comm Range', 'CommRange', 2, 4, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (86, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (87, null, 'Run Time', 'RunTime', 2, 6, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (88, null, 'Run Range', 'RunRange', 2, 7, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (89, null, 'Run Time Efficiency', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (90, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 2, 9, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (91, null, 'Total Oil Weight Production', 'OilWeightProduction', 2, 10, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"4f4444"}', null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (92, null, 'Total Water Weight Production', 'WaterWeightProduction', 2, 11, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"31bbbe"}', null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (93, null, 'Weight Water Cut', 'WeightWaterCut', 2, 12, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (94, null, 'RPM', 'RPM', 2, 13, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (95, null, 'Pump Setting Depth', 'PumpSettingDepth', 2, 14, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (96, null, 'Producing Fluid Level', 'ProducingfluidLevel', 2, 15, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (97, null, 'Submergence', 'Submergence', 2, 16, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (98, null, 'System Efficiency', 'SystemEfficiency', 2, 17, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (99, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 2, 18, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (100, null, 'Active Power Consumption', 'C_CLOUMN14', 2, 19, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (101, null, 'Remark', 'Remark', 2, 20, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '2');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (102, null, 'Device', 'DeviceName', 2, 2, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '2');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (103, null, 'Date', 'CalDate', 2, 3, null, 0, 0, null, null, 3, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (104, null, 'Comm Time', 'CommTime', 2, 4, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (105, null, 'Comm Range', 'CommRange', 2, 5, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (106, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 2, 6, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (107, null, 'Run Time', 'RunTime', 2, 7, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (108, null, 'Run Range', 'RunRange', 2, 8, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (109, null, 'Run Time Efficiency', 'RunTimeEfficiency', 2, 9, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (110, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 2, 10, null, 1, 1, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e40c54"}', 1, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (111, null, 'Total Oil Weight Production', 'OilWeightProduction', 2, 11, null, 1, 1, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d7bb14"}', 1, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (112, null, 'Total Water Weight Production', 'WaterWeightProduction', 2, 12, null, 1, 1, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1dbfb4"}', 1, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (113, null, 'Weight Water Cut', 'WeightWaterCut', 2, 13, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (114, null, 'RPM', 'RPM', 2, 14, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (115, null, 'Pump Setting Depth', 'PumpSettingDepth', 2, 15, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (116, null, 'Producing Fluid Level', 'ProducingfluidLevel', 2, 16, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (117, null, 'Submergence', 'Submergence', 2, 17, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (118, null, 'System Efficiency', 'SystemEfficiency', 2, 18, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (119, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 2, 19, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (120, null, 'Active Power Consumption', 'C_CLOUMN14', 2, 20, null, 1, 1, null, null, 2, 1, '0,0,0', null, 0, '0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (121, null, 'Remark', 'Remark', 2, 21, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '2');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (122, null, 'Time', 'CalTime', 2, 2, null, null, null, null, null, 4, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (123, null, 'Comm Time', 'CommTime', 2, 3, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (124, null, 'Comm Range', 'CommRange', 2, 4, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (125, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (126, null, 'Run Time', 'RunTime', 2, 6, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (127, null, 'Run Range', 'RunRange', 2, 7, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (128, null, 'Run Time Efficiency', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (129, null, 'Liquid Volumetric Production', 'RealtimeLiquidVolumetricProduction', 2, 9, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (130, null, 'Oil Volumetric Production', 'RealtimeOilVolumetricProduction', 2, 10, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (131, null, 'Water Volumetric Production', 'RealtimeWaterVolumetricProduction', 2, 11, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (132, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 2, 12, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (133, null, 'Total Oil Weight Production', 'OilWeightProduction', 2, 13, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"995353"}', null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (134, null, 'Total Water Weight Production', 'WaterWeightProduction', 2, 14, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"57a6a8"}', null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (135, null, 'Weight Water Cut', 'WeightWaterCut', 2, 15, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (136, null, 'RPM', 'RPM', 2, 16, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (137, null, 'Pump Setting Depth', 'PumpSettingDepth', 2, 17, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (138, null, 'Producing Fluid Level', 'ProducingfluidLevel', 2, 18, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (139, null, 'Submergence', 'Submergence', 2, 19, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (140, null, 'System Efficiency', 'SystemEfficiency', 2, 20, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (141, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 2, 21, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (142, null, 'Active Power Consumption', 'C_CLOUMN14', 2, 22, null, null, null, null, null, 2, 2, '0,0,0', null, 6, '0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (143, null, 'Remark', 'Remark', 2, 23, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '2');

/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLINSTANCE����                                 */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (1, '����ڧާ֧� ����ѧӧݧ֧ߧڧ� ����ڧ٧ӧ�է��ӧ�� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', 'instance1', 'modbus-tcp', 'modbus-tcp', 1, 'AA01', '0B', 0, 1, 'AA01', '0B', 100, 1, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (2, '����ڧާ֧� ����ѧӧݧ֧ߧڧ� �٧ѧ�ӧѧ��� �ӧڧߧ��ӧ�ԧ� �ߧѧ����', 'instance2', 'modbus-tcp', 'modbus-tcp', 1, 'AA01', '0B', 0, 1, 'AA01', '0B', 100, 2, 2);


/*==============================================================*/
/* ��ʼ��tbl_protocolalarminstance����                            */
/*==============================================================*/
insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, SORT)
values (1, '����ڧާ֧� �ѧӧѧ�ڧۧߧ�� ��ڧԧߧѧݧڧ٧ѧ�ڧ� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', 'alarminstance1', 1, 1);

insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, SORT)
values (2, '����ڧާ֧� �ѧӧѧ�ڧۧߧ�� ��ڧԧߧѧݧڧ٧ѧ�ڧ� �ӧڧߧ��ӧ�ԧ� �ߧѧ����', 'alarminstance2', 2, 2);

/*==============================================================*/
/* ��ʼ��tbl_protocoldisplayinstance����                                          */
/*==============================================================*/
insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (1, '����ڧާ֧� ����ҧ�ѧا֧ߧڧ� �ߧѧ���ߧ�ԧ� �ѧԧ�֧ԧѧ��', 'displayinstance1', 1, 1);

insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (2, '����ڧާ֧� ����ҧ�ѧا֧ߧڧ� �ӧڧߧ��ӧ�ԧ� �ߧѧ����', 'displayinstance2', 2, 2);

/*==============================================================*/
/* ��ʼ��TBL_PROTOCOLREPORTINSTANCE����                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (1, '����ڧާ֧� ����֧�� ��� �ߧѧ���ߧ�ާ� �ѧԧ�֧ԧѧ��', 'reportinstance1', 1, 1);

insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (2, '����ڧާ֧� ����֧�� �� �ӧڧߧ��ӧ�� �ߧѧ����', 'reportinstance2', 2, 2);