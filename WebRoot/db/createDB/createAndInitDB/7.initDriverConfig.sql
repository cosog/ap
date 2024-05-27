/*==============================================================*/
/* ��ʼ��TBL_PROTOCOL����                                          */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'A11-���ͻ�', 'protocol1', 1, 1);

insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (2, 'A11-�ݸ˱�', 'protocol2', 2, 1);

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
/* ��ʼ��TBL_DATAMAPPING����                                          */
/*==============================================================*/
insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (769, 'ת��', 'C_CLOUMN1', 0, 'RPM', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (770, '��ѹ', 'C_CLOUMN2', 0, 'TubingPressure', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (771, '��ѹ', 'C_CLOUMN3', 0, 'CasingPressure', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (772, '�����¶�', 'C_CLOUMN4', 0, 'WellHeadTemperature', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (773, '����״̬', 'C_CLOUMN5', 0, 'RunStatus', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (774, '��ͣ����', 'C_CLOUMN6', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (775, '��ˮ��', 'C_CLOUMN7', 0, 'VolumeWaterCut', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (776, '��Һ��', 'C_CLOUMN8', 0, 'ProducingfluidLevel', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (777, '����¶�', 'C_CLOUMN9', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (778, '�̸����¶�', 'C_CLOUMN10', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (779, 'A�����', 'C_CLOUMN11', 0, 'IA', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (780, 'B�����', 'C_CLOUMN12', 0, 'IB', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (781, 'C�����', 'C_CLOUMN13', 0, 'IC', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (782, 'A���ѹ', 'C_CLOUMN14', 0, 'VA', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (783, 'B���ѹ', 'C_CLOUMN15', 0, 'VB', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (784, 'C���ѹ', 'C_CLOUMN16', 0, 'VC', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (785, '�й�����', 'C_CLOUMN17', 0, 'TotalKWattH', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (786, '�޹�����', 'C_CLOUMN18', 0, 'TotalKVarH', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (787, '�й�����', 'C_CLOUMN19', 0, 'Watt3', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (788, '�޹�����', 'C_CLOUMN20', 0, 'Var3', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (789, '������', 'C_CLOUMN21', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (790, '��������', 'C_CLOUMN22', 0, 'PF3', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (791, '��Ƶ����Ƶ��', 'C_CLOUMN23', 0, 'SetFrequency', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (792, '��Ƶ����Ƶ��', 'C_CLOUMN24', 0, 'RunFrequency', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (793, '��ͼ�ɼ����', 'C_CLOUMN25', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (794, '�ֶ��ɼ���ͼ', 'C_CLOUMN26', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (795, '��ͼ���õ���', 'C_CLOUMN27', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (796, '��ͼʵ�����', 'C_CLOUMN28', 0, 'FESDiagramAcqCount', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (797, '��ͼ�ɼ�ʱ��', 'C_CLOUMN29', 0, 'FESDiagramAcqtime', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (798, '���', 'C_CLOUMN30', 0, 'SPM', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (799, '���', 'C_CLOUMN31', 0, 'Stroke', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (800, '��ͼ����-λ��', 'C_CLOUMN32', 0, 'Position_Curve', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (801, '��ͼ����-�غ�', 'C_CLOUMN33', 0, 'Load_Curve', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (802, '��ͼ����-����', 'C_CLOUMN34', 0, 'Current_Curve', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (803, '��ͼ����-����', 'C_CLOUMN35', 0, 'Power_Curve', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (804, 'ú�㶥����', 'C_CLOUMN36', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (805, 'ѹ���ư�װ���', 'C_CLOUMN37', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (806, '����ѹ����-ѹ��', 'C_CLOUMN38', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (807, '������ѹ', 'C_CLOUMN39', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (808, 'ϵͳѹ��', 'C_CLOUMN40', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (809, '�������ۼ�', 'C_CLOUMN41', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (810, '������˲ʱ', 'C_CLOUMN42', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (811, '�����¶�', 'C_CLOUMN43', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (812, '��ˮ���ۼ�', 'C_CLOUMN44', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (813, '��ˮ��˲ʱ', 'C_CLOUMN45', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (814, '����ѹ����-�¶�', 'C_CLOUMN46', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (815, '����������ͨѶ״̬', 'C_CLOUMN47', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (816, 'ˮ������ͨѶ״̬', 'C_CLOUMN48', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (817, '����ѹ����/Һ����ͨѶ״̬', 'C_CLOUMN49', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (818, '��Ƶ��ͨѶ״̬', 'C_CLOUMN50', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (819, '��ǰĿ��ֵ', 'C_CLOUMN51', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (820, 'Ƶ�ʿ��Ʒ�ʽ', 'C_CLOUMN52', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (821, '��Ƶ������Ƶ��', 'C_CLOUMN53', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (822, '��Ƶ������Ƶ��', 'C_CLOUMN54', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (823, '��Ƶ������״̬', 'C_CLOUMN55', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (824, '��Ƶ���������', 'C_CLOUMN56', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (825, '��Ƶ�������ѹ', 'C_CLOUMN57', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (826, '��Ƶ�����Ҵ���', 'C_CLOUMN58', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (827, '��Ƶ��״̬��1', 'C_CLOUMN59', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (828, '��Ƶ��״̬��2', 'C_CLOUMN60', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (829, '������ťλ��', 'C_CLOUMN61', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (830, 'ĸ�ߵ�ѹ', 'C_CLOUMN62', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (831, '�趨Ƶ�ʷ���', 'C_CLOUMN63', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (832, '10Hz��Ӧ���/ת��Ԥ��ֵ', 'C_CLOUMN64', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (833, '50Hz��Ӧ���/ת��Ԥ��ֵ', 'C_CLOUMN65', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (834, '���/ת���趨ֵ', 'C_CLOUMN66', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (835, '�����󾮵���ѹ', 'C_CLOUMN67', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (836, '����Һ���߶�', 'C_CLOUMN68', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (837, '�����1СʱҺ���½��ٶ�', 'C_CLOUMN69', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (838, '�Ų�ģʽ', 'C_CLOUMN70', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (839, '�Զ��Ų�״̬', 'C_CLOUMN71', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (840, '�Զ��Ų�-��СƵ��', 'C_CLOUMN72', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (841, '�Զ��Ų�-���Ƶ��', 'C_CLOUMN73', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (842, '��󲽳���������', 'C_CLOUMN74', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (843, '��̵���ʱ����', 'C_CLOUMN75', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (844, '�Զ�������ʱ', 'C_CLOUMN76', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (845, '�Զ���������', 'C_CLOUMN77', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (846, '������ѹ��������ֵ', 'C_CLOUMN78', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (847, '����Һ-Ŀ�궨����ÿ�գ�', 'C_CLOUMN79', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (848, '����Һ-Һ����ͣ��ֵ', 'C_CLOUMN80', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (849, '����Һ-Һ���ͱ���ֵ', 'C_CLOUMN81', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (850, '����Һ-Һ������ֵ', 'C_CLOUMN82', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (851, '����Һ-Һ���߱���ֵ', 'C_CLOUMN83', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (852, '����Һ-P����', 'C_CLOUMN84', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (853, '����Һ-I����', 'C_CLOUMN85', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (854, '����Һ-D����', 'C_CLOUMN86', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (855, '����ѹ-Ŀ����ѹ', 'C_CLOUMN87', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (856, '����ѹ-��ѹ��ͣ��ֵ', 'C_CLOUMN88', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (857, '����ѹ-��ѹ�ͱ���ֵ', 'C_CLOUMN89', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (858, '����ѹ-��ѹ����ֵ', 'C_CLOUMN90', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (859, '����ѹ-��ѹ�߱���ֵ', 'C_CLOUMN91', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (860, '����ѹ-P����', 'C_CLOUMN92', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (861, '����ѹ-I����', 'C_CLOUMN93', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (862, '����ѹ-D����', 'C_CLOUMN94', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (863, 'IGBT �¶�', 'C_CLOUMN95', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (864, '�������', 'C_CLOUMN96', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (865, '�����ѹ', 'C_CLOUMN97', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (866, '�������', 'C_CLOUMN98', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (867, 'ת��', 'C_CLOUMN99', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (868, '��Ƶ��ͨ��״̬', 'C_CLOUMN100', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (869, 'ת��ϵ��', 'C_CLOUMN101', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (870, '�ղ�Һ��(m^3/d)', 'C_CLOUMN102', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (871, '�ղ�����(m^3/d)', 'C_CLOUMN103', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (872, '�ղ�ˮ��(m^3/d)', 'C_CLOUMN104', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (873, '�ղ�Һ��(t/d)', 'C_CLOUMN105', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (874, '�ղ�����(t/d)', 'C_CLOUMN106', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (875, '�ղ�ˮ��(t/d)', 'C_CLOUMN107', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (876, '��������', 'C_CLOUMN108', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (877, '�ݻ�Ч��', 'C_CLOUMN109', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (878, '����ϵ��', 'C_CLOUMN110', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (879, '�����Ч', 'C_CLOUMN111', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (880, 'ԭ���ܶ�', 'C_CLOUMN112', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (881, '�ز�ˮ�ܶ�', 'C_CLOUMN113', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (882, '��Ȼ������ܶ�', 'C_CLOUMN114', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (883, '����ѹ��', 'C_CLOUMN115', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (884, '�Ͳ��в����', 'C_CLOUMN116', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (885, '�Ͳ��в��¶�', 'C_CLOUMN117', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (886, '�������ͱ�', 'C_CLOUMN118', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (887, '�ù�', 'C_CLOUMN119', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (888, 'ÿת��������', 'C_CLOUMN120', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (889, '�͹��ھ�', 'C_CLOUMN121', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (890, '�׹��ھ�', 'C_CLOUMN122', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (891, 'һ���˼���', 'C_CLOUMN123', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (892, 'һ�����⾶', 'C_CLOUMN124', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (893, 'һ�����ھ�', 'C_CLOUMN125', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (894, 'һ���˳�', 'C_CLOUMN126', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (895, '�����˼���', 'C_CLOUMN127', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (896, '�������⾶', 'C_CLOUMN128', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (897, '�������ھ�', 'C_CLOUMN129', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (898, '�����˳�', 'C_CLOUMN130', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (899, '�����˼���', 'C_CLOUMN131', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (900, '�������⾶', 'C_CLOUMN132', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (901, '�������ھ�', 'C_CLOUMN133', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (902, '�����˳�', 'C_CLOUMN134', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (903, '��ë��', 'C_CLOUMN135', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (904, '��ëֵ', 'C_CLOUMN136', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (905, 'ϵͳʱ��-ʱ', 'C_CLOUMN137', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (906, 'ϵͳʱ��-��', 'C_CLOUMN138', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (907, 'ϵͳʱ��-��', 'C_CLOUMN139', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (908, 'ϵͳʱ��-��', 'C_CLOUMN140', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (909, 'ϵͳʱ��-��', 'C_CLOUMN141', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (910, 'ϵͳʱ��-��', 'C_CLOUMN142', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (911, 'RTU��ַ', 'C_CLOUMN143', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (912, '����汾��', 'C_CLOUMN144', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (913, '����', 'C_CLOUMN145', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (914, '��ѹ', 'C_CLOUMN146', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (915, '�ݸ˱�ת��', 'C_CLOUMN147', 0, 'RPM', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (916, '�ݸ˱�Ť��', 'C_CLOUMN148', 0, null, null, 1);


/*==============================================================*/
/* ��ʼ��TBL_RUNSTATUSCONFIG����                                          */
/*==============================================================*/
insert into TBL_RUNSTATUSCONFIG (ID, PROTOCOL, ITEMNAME, ITEMMAPPINGCOLUMN, RUNVALUE, STOPVALUE, PROTOCOLTYPE, RESOLUTIONMODE, RUNCONDITION, STOPCONDITION)
values (1, 'protocol1', '����״̬', 'C_CLOUMN5', '1', '0', 0, 1, null, null);

insert into TBL_RUNSTATUSCONFIG (ID, PROTOCOL, ITEMNAME, ITEMMAPPINGCOLUMN, RUNVALUE, STOPVALUE, PROTOCOLTYPE, RESOLUTIONMODE, RUNCONDITION, STOPCONDITION)
values (2, 'protocol2', '����״̬', 'C_CLOUMN5', '1', '0', 1, 1, null, null);

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
values (82, null, 'Һ��У����ֵ', 'LevelDifferenceValue', 1, 59, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (83, null, '���ݶ�Һ��', 'CalcProducingfluidLevel', 1, 58, null, null, 1, null, null, '0,0,0');

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
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE ,SINGLEWELLDAILYREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (1, 'unit1', '���ͻ�������Ԫһ', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 'oilWell_PumpingDailyReport', 0, 1);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE ,SINGLEWELLDAILYREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (2, 'unit2', 'ú����������Ԫһ', 'CBMWell_heichao', 'CBMWell_heichaoDailyReport', 'CBMWell_heichaoProductionReport', 0, 2);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE ,SINGLEWELLDAILYREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (3, 'unit3', '�ݸ˱þ�����Ԫһ', 'oilWell_ScrewPump', 'oilWell_PumpingDailyReport', 'oilWell_ScrewPumpProductionReoirt', 1, 1);
/*==============================================================*/
/* ��ʼ��TBL_REPORT_ITEMS2UNIT_CONF����                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (1, null, '����', 'CalDate', 1, 2, null, null, null, null, null, 3, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (2, null, '����ʱ��', 'CommTime', 1, 3, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (3, null, '����ʱ��', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (4, null, '��������', 'CommRange', 1, 4, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (5, null, '����ʱ��', 'RunTime', 1, 6, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (6, null, '����ʱ��', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (7, null, '��������', 'RunRange', 1, 7, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (8, null, '����', 'ResultName', 1, 9, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (9, null, '�Ż�����', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (10, null, '����ϵ��', 'FullnessCoefficient', 1, 15, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (11, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 1, 11, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d12b2b"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (12, null, '���ۼƲ�����', 'OilWeightProduction', 1, 12, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e99314"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (13, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 1, 13, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"19c2eb"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (14, null, '������ˮ��', 'WeightWaterCut', 1, 14, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (15, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 23, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (16, null, '����Ч��', 'WellDownSystemEfficiency', 1, 24, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (17, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 22, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (18, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 25, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (19, null, '����ƽ���', 'IDegreeBalance', 1, 20, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (20, null, '����ƽ���', 'WattDegreeBalance', 1, 19, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (21, null, '�ƶ�����', 'DeltaRadius', 1, 21, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (22, null, '���õ���', 'TodayKWattH', 1, 26, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (23, null, '�ù�', 'PumpSettingDepth', 1, 16, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (24, null, '���ݶ�Һ��', 'CalcProducingfluidLevel', 1, 17, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (25, null, '��û��', 'Submergence', 1, 18, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (26, null, '��ע', 'Remark', 1, 27, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (27, null, '����', 'WellName', 1, 2, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (28, null, '����', 'CalDate', 1, 3, null, 0, 0, null, null, 3, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (29, null, '����ʱ��', 'CommTime', 1, 4, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (30, null, '����ʱ��', 'CommTimeEfficiency', 1, 6, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (31, null, '��������', 'CommRange', 1, 5, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (32, null, '����ʱ��', 'RunTime', 1, 7, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (33, null, '����ʱ��', 'RunTimeEfficiency', 1, 9, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (34, null, '��������', 'RunRange', 1, 8, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (35, null, '����', 'ResultName', 1, 10, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (36, null, '�Ż�����', 'OptimizationSuggestion', 1, 11, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (37, null, '����ϵ��', 'FullnessCoefficient', 1, 16, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (38, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 1, 12, null, 1, 1, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d42626"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (39, null, '���ۼƲ�����', 'OilWeightProduction', 1, 13, null, 1, 1, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"ebbc1a"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (40, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 1, 14, null, 1, 1, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1beef3"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (41, null, '������ˮ��', 'WeightWaterCut', 1, 15, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (42, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 24, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (43, null, '����Ч��', 'WellDownSystemEfficiency', 1, 25, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (44, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 23, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (45, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 26, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (46, null, '����ƽ���', 'IDegreeBalance', 1, 21, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (47, null, '����ƽ���', 'WattDegreeBalance', 1, 20, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (48, null, '�ƶ�����', 'DeltaRadius', 1, 22, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (49, null, '���õ���', 'TodayKWattH', 1, 27, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (50, null, '�ù�', 'PumpSettingDepth', 1, 17, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (51, null, '��Һ��', 'ProducingfluidLevel', 1, 18, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (52, null, '��û��', 'Submergence', 1, 19, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (53, null, '��ע', 'Remark', 1, 28, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (54, null, 'ʱ��', 'CalTime', 1, 2, null, null, null, null, null, 4, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (55, null, '����ʱ��', 'CommTime', 1, 3, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (56, null, '����ʱ��', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (57, null, '��������', 'CommRange', 1, 4, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (58, null, '����ʱ��', 'RunTime', 1, 6, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (59, null, '����ʱ��', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (60, null, '��������', 'RunRange', 1, 7, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (61, null, '����', 'ResultName', 1, 9, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (62, null, '�Ż�����', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (63, null, '����ϵ��', 'FullnessCoefficient', 1, 15, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (64, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 1, 11, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (65, null, '���ۼƲ�����', 'OilWeightProduction', 1, 12, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"232432"}', null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (66, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 1, 13, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"3ba4ac"}', null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (67, null, '������ˮ��', 'WeightWaterCut', 1, 14, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (68, null, '����Ч��', 'SurfaceSystemEfficiency', 1, 23, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (69, null, '����Ч��', 'WellDownSystemEfficiency', 1, 24, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (70, null, 'ϵͳЧ��', 'SystemEfficiency', 1, 22, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (71, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 1, 25, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (72, null, '����ƽ���', 'IDegreeBalance', 1, 20, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (73, null, '����ƽ���', 'WattDegreeBalance', 1, 19, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (74, null, '�ƶ�����', 'DeltaRadius', 1, 21, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (75, null, '���õ���', 'TodayKWattH', 1, 26, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (76, null, '�ù�', 'PumpSettingDepth', 1, 16, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (77, null, '���ݶ�Һ��', 'CalcProducingfluidLevel', 1, 17, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (78, null, '��û��', 'Submergence', 1, 18, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (79, null, '��ע', 'Remark', 1, 27, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (80, null, '����', 'CalDate', 3, 1, null, null, null, null, null, 3, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (81, null, '����ʱ��', 'RunTime', 3, 2, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (82, null, '���', 'Stroke', 3, 4, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (83, null, '���', 'SPM', 3, 3, null, null, null, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"ecd211"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (84, null, '���ۼƲ�ˮ��', 'WaterVolumetricProduction', 3, 7, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"00d8ff"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (85, null, '���ۼƲ�����', 'GasVolumetricProduction', 3, 6, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (86, null, '�ۼƲ�����', 'TotalGasVolumetricProduction', 3, 10, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (87, null, '�ۼƲ�ˮ��', 'TotalWaterVolumetricProduction', 3, 11, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (88, null, '��Һ��', 'ProducingfluidLevel', 3, 5, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (89, null, '��ѹ', 'CasingPressure', 3, 9, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (90, null, '����ѹ��', 'BottomHolePressure', 3, 8, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (91, null, '��ע', 'Remark', 3, 12, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (92, null, '����', 'WellName', 3, 2, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (93, null, '����', 'CalDate', 3, 1, null, 0, 0, null, null, 3, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (94, null, '����ʱ��', 'RunTime', 3, 3, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (95, null, '���', 'Stroke', 3, 5, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (96, null, '���', 'SPM', 3, 4, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (97, null, '���ۼƲ�ˮ��', 'WaterVolumetricProduction', 3, 8, null, 1, 1, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"00d8ff"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (98, null, '���ۼƲ�����', 'GasVolumetricProduction', 3, 7, null, 1, 1, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (99, null, '�ۼƲ�����', 'TotalGasVolumetricProduction', 3, 11, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (100, null, '�ۼƲ�ˮ��', 'TotalWaterVolumetricProduction', 3, 12, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (101, null, '��Һ��', 'ProducingfluidLevel', 3, 6, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (102, null, '��ѹ', 'CasingPressure', 3, 10, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (103, null, '����ѹ��', 'BottomHolePressure', 3, 9, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (104, null, '��ע', 'Remark', 3, 13, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (105, null, 'ʱ��', 'CalTime', 3, 1, null, null, null, null, null, 4, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (106, null, '����ʱ��', 'RunTime', 3, 2, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (107, null, '���', 'Stroke', 3, 4, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (108, null, '���', 'SPM', 3, 3, null, null, null, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"ecd211"}', null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (109, null, '���ۼƲ�ˮ��', 'WaterVolumetricProduction', 3, 7, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"00d8ff"}', null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (110, null, '���ۼƲ�����', 'GasVolumetricProduction', 3, 6, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (111, null, '�ۼƲ�����', 'TotalGasVolumetricProduction', 3, 10, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (112, null, '�ۼƲ�ˮ��', 'TotalWaterVolumetricProduction', 3, 11, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (113, null, '��Һ��', 'ProducingfluidLevel', 3, 5, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (114, null, '��ѹ', 'CasingPressure', 3, 9, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (115, null, '����ѹ��', 'BottomHolePressure', 3, 8, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (116, null, '��ע', 'Remark', 3, 14, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (117, null, '����1', 'reservedcol1', 3, 12, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (118, null, '����2', 'reservedcol2', 3, 13, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (119, null, '����', 'CalDate', 2, 2, null, null, null, null, null, 3, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (120, null, '����ʱ��', 'CommTime', 2, 3, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (121, null, '����ʱ��', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (122, null, '��������', 'CommRange', 2, 4, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (123, null, '����ʱ��', 'RunTime', 2, 6, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (124, null, '����ʱ��', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (125, null, '��������', 'RunRange', 2, 7, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (126, null, 'ת��', 'RPM', 2, 13, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (127, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 2, 9, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (128, null, '���ۼƲ�����', 'OilWeightProduction', 2, 10, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"4f4444"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (129, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 2, 11, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"31bbbe"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (130, null, '������ˮ��', 'WeightWaterCut', 2, 12, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (131, null, 'ϵͳЧ��', 'SystemEfficiency', 2, 17, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (132, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 2, 18, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (133, null, '���õ���', 'TodayKWattH', 2, 19, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (134, null, '�ù�', 'PumpSettingDepth', 2, 14, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (135, null, '��Һ��', 'ProducingfluidLevel', 2, 15, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (136, null, '��û��', 'Submergence', 2, 16, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (137, null, '��ע', 'Remark', 2, 20, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (138, null, '����', 'WellName', 2, 2, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (139, null, '����', 'CalDate', 2, 3, null, 0, 0, null, null, 3, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (140, null, '����ʱ��', 'CommTime', 2, 4, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (141, null, '����ʱ��', 'CommTimeEfficiency', 2, 6, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (142, null, '��������', 'CommRange', 2, 5, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (143, null, '����ʱ��', 'RunTime', 2, 7, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (144, null, '����ʱ��', 'RunTimeEfficiency', 2, 9, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (145, null, '��������', 'RunRange', 2, 8, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (146, null, 'ת��', 'RPM', 2, 14, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (147, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 2, 10, null, 0, 0, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e40c54"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (148, null, '���ۼƲ�����', 'OilWeightProduction', 2, 11, null, 0, 0, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d7bb14"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (149, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 2, 12, null, 0, 0, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1dbfb4"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (150, null, '������ˮ��', 'WeightWaterCut', 2, 13, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (151, null, 'ϵͳЧ��', 'SystemEfficiency', 2, 18, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (152, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 2, 19, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (153, null, '���õ���', 'TodayKWattH', 2, 20, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (154, null, '�ù�', 'PumpSettingDepth', 2, 15, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (155, null, '��Һ��', 'ProducingfluidLevel', 2, 16, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (156, null, '��û��', 'Submergence', 2, 17, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (157, null, '��ע', 'Remark', 2, 21, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (158, null, 'ʱ��', 'CalTime', 2, 2, null, null, null, null, null, 4, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (159, null, '����ʱ��', 'CommTime', 2, 3, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (160, null, '����ʱ��', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (161, null, '��������', 'CommRange', 2, 4, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (162, null, '����ʱ��', 'RunTime', 2, 6, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (163, null, '����ʱ��', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (164, null, '��������', 'RunRange', 2, 7, null, null, null, null, null, 1, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (165, null, 'ת��', 'RPM', 2, 13, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (166, null, '���ۼƲ�Һ��', 'LiquidWeightProduction', 2, 9, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (167, null, '���ۼƲ�����', 'OilWeightProduction', 2, 10, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"995353"}', null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (168, null, '���ۼƲ�ˮ��', 'WaterWeightProduction', 2, 11, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"57a6a8"}', null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (169, null, '������ˮ��', 'WeightWaterCut', 2, 12, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (170, null, 'ϵͳЧ��', 'SystemEfficiency', 2, 17, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (171, null, '��Һ���׺ĵ���', 'EnergyPer100mLift', 2, 18, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (172, null, '���õ���', 'TodayKWattH', 2, 19, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (173, null, '�ù�', 'PumpSettingDepth', 2, 14, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (174, null, '��Һ��', 'ProducingfluidLevel', 2, 15, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (175, null, '��û��', 'Submergence', 2, 16, null, null, null, null, null, 2, 2, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (176, null, '��ע', 'Remark', 2, 20, null, null, null, null, null, 1, 2, '0,0,0');


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