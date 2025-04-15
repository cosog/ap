/*==============================================================*/
/* 初始化TBL_PROTOCOL数据                                        */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'A11 Protocol', 'protocol1', 1, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"Tubing Pressure","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"Casing Pressure","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Back Pressure","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Well Head Temperature","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"Run Status","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"Stop"},{"Value":1,"Meaning":"Running"}]},{"Title":"Run/Stop","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"run"},{"Value":2,"Meaning":"stop"}]},{"Title":"Water Cut","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"Producing Fluid Level","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"A Phase Current","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B Phase Current","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C Phase Current","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A Phase Voltage","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B Phase Voltage","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"C Phase Voltage","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Active Power Consumption","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Reactive Power Consumption","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Active Power","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Reactive Power","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"Reverse Power","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Power Factor","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Setting Frequency","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Running Frequency","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"Screw Speed","Addr":40430,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"r/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Screw Torque","Addr":40432,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kN·m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Interval For Surface Diagram Collection","Addr":40981,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Manual Collection Of Surface Diagram","Addr":40982,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"Fixed Time"},{"Value":1,"Meaning":"Manual"}]},'
  ||'{"Title":"Surface Diagram Sets Points","Addr":40983,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Surface Diagram Measured Points","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Surface Diagram Acquisition Time","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"SPM","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"1/min","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"Stroke","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"Diagram Data Of Displacement","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.001,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"Diagram Data Of Load","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Diagram Data Of Current","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"Diagram Data Of Power","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive","Meaning":[]}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.CODE='protocol1';  
  COMMIT;  
END;  
/

/*==============================================================*/
/* 初始化TBL_DATAMAPPING数据                                          */
/*==============================================================*/
insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (1, 'Tubing Pressure', 'C_CLOUMN1', 0, 'TubingPressure', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (2, 'Casing Pressure', 'C_CLOUMN2', 0, 'CasingPressure', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (3, 'Back Pressure', 'C_CLOUMN3', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (4, 'Well Head Temperature', 'C_CLOUMN4', 0, 'WellHeadTemperature', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (5, 'Run Status', 'C_CLOUMN5', 0, 'RunStatus', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (6, 'Water Cut', 'C_CLOUMN6', 0, 'VolumeWaterCut', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (7, 'Producing Fluid Level', 'C_CLOUMN7', 0, 'ProducingfluidLevel', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (8, 'A Phase Current', 'C_CLOUMN8', 0, 'IA', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (9, 'B Phase Current', 'C_CLOUMN9', 0, 'IB', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (10, 'C Phase Current', 'C_CLOUMN10', 0, 'IC', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (11, 'A Phase Voltage', 'C_CLOUMN11', 0, 'VA', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (12, 'B Phase Voltage', 'C_CLOUMN12', 0, 'VB', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (13, 'C Phase Voltage', 'C_CLOUMN13', 0, 'VC', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (14, 'Active Power Consumption', 'C_CLOUMN14', 0, 'TotalKWattH', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (15, 'Reactive Power Consumption', 'C_CLOUMN15', 0, 'TotalKVarH', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (16, 'Active Power', 'C_CLOUMN16', 0, 'Watt3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (17, 'Reactive Power', 'C_CLOUMN17', 0, 'Var3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (18, 'Reverse Power', 'C_CLOUMN18', 0, null, null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (19, 'Power Factor', 'C_CLOUMN19', 0, 'PF3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (20, 'Running Frequency', 'C_CLOUMN20', 0, 'RunFrequency', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (21, 'Screw Speed', 'C_CLOUMN21', 0, 'RPM', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (22, 'Screw Torque', 'C_CLOUMN22', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (23, 'Interval For Surface Diagram Collection', 'C_CLOUMN23', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (24, 'Manual Collection Of Surface Diagram', 'C_CLOUMN24', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (25, 'Surface Diagram Sets Points', 'C_CLOUMN25', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (26, 'Surface Diagram Measured Points', 'C_CLOUMN26', 0, 'FESDiagramAcqCount', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (27, 'Surface Diagram Acquisition Time', 'C_CLOUMN27', 0, 'FESDiagramAcqtime', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (28, 'SPM', 'C_CLOUMN28', 0, 'SPM', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (29, 'Stroke', 'C_CLOUMN29', 0, 'Stroke', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (30, 'Diagram Data Of Displacement', 'C_CLOUMN30', 0, 'Position_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (31, 'Diagram Data Of Load', 'C_CLOUMN31', 0, 'Load_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (32, 'Diagram Data Of Current', 'C_CLOUMN32', 0, 'Current_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (33, 'Diagram Data Of Power', 'C_CLOUMN33', 0, 'Power_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (34, 'Run/Stop', 'C_CLOUMN34', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (35, 'Setting Frequency', 'C_CLOUMN35', 0, null, null, 1, 0);


/*==============================================================*/
/* 初始化TBL_RUNSTATUSCONFIG数据                                          */
/*==============================================================*/
insert into TBL_RUNSTATUSCONFIG (ID, PROTOCOL, ITEMNAME, ITEMMAPPINGCOLUMN, RUNVALUE, STOPVALUE, PROTOCOLTYPE, RESOLUTIONMODE, RUNCONDITION, STOPCONDITION)
values (1, 'protocol1', 'Run Status', 'C_CLOUMN5', '1', '0', 0, 1, null, null);

/*==============================================================*/
/* 初始化TBL_ACQ_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', 'SRP Acquisition Unit', 'A11 Protocol', 'SRP Acquisition Unit');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (2, 'unit2', 'PCP Acquisition Unit', 'A11 Protocol', 'PCP Acquisition Unit');

/*==============================================================*/
/* 初始化TBL_ACQ_GROUP_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', 'AcquisitionGroup', 60, 60, 'A11 Protocol', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', 'ControlGroup', 0, 0, 'A11 Protocol', 1, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (3, 'group3', 'AcquisitionGroup', 60, 60, 'A11 Protocol', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (4, 'group4', 'AcquisitionGroup', 0, 0, 'A11 Protocol', 1, null);

/*==============================================================*/
/* 初始化TBL_ACQ_GROUP2UNIT_CONF数据                                          */
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
/* 初始化TBL_ACQ_ITEM2GROUP_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (1, null, 'Run Status', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (2, null, 'Active Power Consumption', null, 1, null, '0,0,0', 1, 'Today KWattH');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (3, null, 'Setting Frequency', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (4, null, 'Running Frequency', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (5, null, 'Surface Diagram Measured Points', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (6, null, 'Surface Diagram Acquisition Time', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (7, null, 'SPM', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (8, null, 'Stroke', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (9, null, 'Diagram Data Of Displacement', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (10, null, 'Diagram Data Of Load', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (11, null, 'Diagram Data Of Current', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (12, null, 'Diagram Data Of Power', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (13, null, 'Run/Stop', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (14, null, 'Setting Frequency', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (15, null, 'Run Status', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (16, null, 'Screw Speed', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (17, null, 'Screw Torque', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (18, null, 'Run/Stop', null, 4, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (19, null, 'Setting Frequency', null, 4, null, '0,0,0', 0, null);

/*==============================================================*/
/* 初始化TBL_ALARM_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, CALCULATETYPE)
values (1, 'alarmunit1', 'SRP Alarm Unit', 'A11 Protocol', 'SRP Alarm Unit', 1);

insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, CALCULATETYPE)
values (2, 'alarmunit2', 'PCP Alarm Unit', 'A11 Protocol', 'PCP Alarm Unit', 2);

/*==============================================================*/
/* 初始化TBL_ALARM_ITEM2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (1, 1, null, 'Offline', 'offline', 0, 0.000, null, null, null, null, 100, 1, 3, 0, 0, 0, null);

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
values (32, 1, null, 'Stopped', 'stop', 0, 0.000, null, null, null, null, 100, 1, 6, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (33, 2, null, 'Offline', 'offline', 0, 0.000, null, null, null, null, 100, 1, 3, 0, 0, 0, null);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (34, 2, null, 'Stopped', 'stop', 0, 0.000, null, null, null, null, 100, 1, 6, 0, 0, 0, null);

/*==============================================================*/
/* 初始化TBL_DISPLAY_UNIT_CONF数据                                */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK, CALCULATETYPE)
values (1, 'unit1', 'SRPDisplayUnit', 'A11 Protocol', 1, 'SRPDisplayUnit', 1);

insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK, CALCULATETYPE)
values (2, 'unit2', 'PCPDisplayUnit', 'A11 Protocol', 2, 'PCPDisplayUnit', 2);

/*==============================================================*/
/* 初始化TBL_DISPLAY_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (1, null, 'Run Status', 'C_CLOUMN5', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (2, null, 'Active Power Consumption', 'C_CLOUMN14', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (3, null, 'Running Frequency', 'C_CLOUMN20', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (4, null, 'Surface Diagram Measured Points', 'C_CLOUMN26', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (5, null, 'Surface Diagram Acquisition Time', 'C_CLOUMN27', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (6, null, 'SPM', 'C_CLOUMN28', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (7, null, 'Stroke', 'C_CLOUMN29', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (8, null, 'Comm Time', 'CommTime', 1, 1, null, null, null, null, 1, '0,0,0', 1, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (9, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 1, 2, null, null, null, null, 1, '0,0,0', 2, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (10, null, 'Comm Range', 'CommRange', 1, 3, null, null, null, null, 1, '0,0,0', 3, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (11, null, 'Run Time', 'RunTime', 1, 7, null, null, null, null, 1, '0,0,0', 5, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (12, null, 'Run Time Efficiency', 'RunTimeEfficiency', 1, 8, null, null, null, null, 1, '0,0,0', 6, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (13, null, 'Run Range', 'RunRange', 1, 9, null, null, null, null, 1, '0,0,0', 7, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (14, null, 'Result Name', 'ResultName', 1, 10, null, null, null, null, 1, '0,0,0', 8, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (15, null, 'Maximum Load', 'FMax', 1, 20, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b824e6"}', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"b824e6"}', 1, '0,0,0', 9, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (16, null, 'Minimum Load', 'FMin', 1, 23, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e25e1d"}', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e25e1d"}', 1, '0,0,0', 10, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (17, null, 'Plunger Stroke', 'PlungerStroke', 1, 15, null, null, null, null, 1, '0,0,0', 11, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (18, null, 'Available Plunger Stroke', 'AvailablePlungerStroke', 1, 18, null, null, null, null, 1, '0,0,0', 12, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (19, null, 'No Liquid Available Plunger Stroke', 'NoLiquidAvailablePlungerStroke', 1, 27, null, null, null, null, 1, '0,0,0', 13, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (20, null, 'Fullness Coefficient', 'FullnessCoefficient', 1, 21, null, null, null, null, 1, '0,0,0', 14, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (21, null, 'No Liquid Fullness Coefficient', 'NoLiquidFullnessCoefficient', 1, 24, null, null, null, null, 1, '0,0,0', 15, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (22, null, 'Upper Load Line', 'UpperLoadLine', 1, 26, null, null, null, null, 1, '0,0,0', 16, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (23, null, 'Lower Load Line', 'LowerLoadLine', 1, 29, null, null, null, null, 1, '0,0,0', 17, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (24, null, 'Theoretical Production', 'TheoreticalProduction', 1, 25, null, null, null, null, 1, '0,0,0', 18, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (25, null, 'Liquid Volumetric Production', 'LiquidVolumetricProduction', 1, 13, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', 1, '0,0,0', 19, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (26, null, 'Oil Volumetric Production', 'OilVolumetricProduction', 1, 16, null, null, null, null, 1, '0,0,0', 20, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (27, null, 'Water Volumetric Production', 'WaterVolumetricProduction', 1, 19, null, null, null, null, 1, '0,0,0', 21, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (28, null, 'Available Plunger Stroke Volumetric Production', 'AvailablePlungerStrokeProd_v', 1, 28, null, null, null, null, 1, '0,0,0', 22, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (29, null, 'Pump Clearance Leak Volumetric Production', 'PumpClearanceleakProd_v', 1, 30, null, null, null, null, 1, '0,0,0', 23, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (30, null, 'Total Liquid Volumetric Production', 'LiquidVolumetricProduction_l', 1, 22, null, null, null, null, 1, '0,0,0', 24, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (31, null, 'Average Watt', 'AverageWatt', 1, 44, null, null, null, null, 1, '0,0,0', 25, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (32, null, 'Polish Rod Power', 'PolishRodPower', 1, 47, null, null, null, null, 1, '0,0,0', 26, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (33, null, 'Water Power', 'WaterPower', 1, 41, null, null, null, null, 1, '0,0,0', 27, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (34, null, 'Surface System Efficiency', 'SurfaceSystemEfficiency', 1, 43, null, null, null, null, 1, '0,0,0', 28, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (35, null, 'Well Down System Efficiency', 'WellDownSystemEfficiency', 1, 46, null, null, null, null, 1, '0,0,0', 29, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (36, null, 'System Efficiency', 'SystemEfficiency', 1, 40, null, null, null, null, 1, '0,0,0', 30, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (37, null, 'Area', 'Area', 1, 42, null, null, null, null, 1, '0,0,0', 31, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (38, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 1, 45, null, null, null, null, 1, '0,0,0', 32, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (39, null, 'Rod Flex Length', 'RodFlexLength', 1, 31, null, null, null, null, 1, '0,0,0', 33, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (40, null, 'Tubing Flex Length', 'TubingFlexLength', 1, 32, null, null, null, null, 1, '0,0,0', 34, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (41, null, 'Inertia Length', 'InertiaLength', 1, 33, null, null, null, null, 1, '0,0,0', 35, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (42, null, 'Stroke Loss', 'PumpEff1', 1, 34, null, null, null, null, 1, '0,0,0', 36, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (43, null, 'Coefficient', 'PumpEff2', 1, 35, null, null, '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d4bf24"}', '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d4bf24"}', 1, '0,0,0', 37, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (44, null, 'Plunger Leakage', 'PumpEff3', 1, 36, null, null, null, null, 1, '0,0,0', 38, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (45, null, 'Solids Contract', 'PumpEff4', 1, 37, null, null, null, null, 1, '0,0,0', 39, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (46, null, 'Pump Efficiency', 'PumpEff', 1, 38, null, null, null, null, 1, '0,0,0', 40, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (47, null, 'Pump Intake Pressure', 'PumpIntakeP', 1, 61, null, null, null, null, 1, '0,0,0', 41, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (48, null, 'Pump Intake Temperature', 'PumpIntakeT', 1, 62, null, null, null, null, 1, '0,0,0', 42, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (49, null, 'Pump Intake GOL', 'PumpIntakeGOL', 1, 63, null, null, null, null, 1, '0,0,0', 43, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (50, null, 'Pump Intake Visl', 'PumpIntakeVisl', 1, 64, null, null, null, null, 1, '0,0,0', 44, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (51, null, 'Pump Intake Bo', 'PumpIntakeBo', 1, 65, null, null, null, null, 1, '0,0,0', 45, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (52, null, 'Pump Outlet Pressure', 'PumpOutletP', 1, 67, null, null, null, null, 1, '0,0,0', 46, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (53, null, 'Pump Outlet Temperature', 'PumpOutletT', 1, 68, null, null, null, null, 1, '0,0,0', 47, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (54, null, 'Pump Outlet GOL', 'PumpOutletGOL', 1, 69, null, null, null, null, 1, '0,0,0', 48, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (55, null, 'Pump Outlet Visl', 'PumpOutletVisl', 1, 70, null, null, null, null, 1, '0,0,0', 49, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (56, null, 'Pump Outlet Bo', 'PumpOutletBo', 1, 71, null, null, null, null, 1, '0,0,0', 50, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (57, null, 'Up Stroke Max Current', 'UpStrokeIMax', 1, 50, null, null, null, null, 1, '0,0,0', 51, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (58, null, 'Down Stroke Max Current', 'DownStrokeIMax', 1, 51, null, null, null, null, 1, '0,0,0', 52, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (59, null, 'Up Stroke Max Power', 'UpStrokeWattMax', 1, 53, null, null, null, null, 1, '0,0,0', 53, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (60, null, 'Down Stroke Max Power', 'DownStrokeWattMax', 1, 54, null, null, null, null, 1, '0,0,0', 54, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (61, null, 'Current Degree Balance', 'IDegreeBalance', 1, 49, null, null, null, null, 1, '0,0,0', 55, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (62, null, 'Power Degree Balance', 'WattDegreeBalance', 1, 52, null, null, null, null, 1, '0,0,0', 56, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (63, null, 'Delta Radius', 'DeltaRadius', 1, 55, null, null, null, null, 1, '0,0,0', 57, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (64, null, 'Level Difference Value', 'LevelDifferenceValue', 1, 59, null, null, null, null, 1, '0,0,0', 58, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (65, null, 'Calculate Producingfluid Level', 'CalcProducingfluidLevel', 1, 58, null, null, null, null, 1, '0,0,0', 59, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (66, null, 'Today KWattH', 'C_CLOUMN14_TOTAL', 1, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (67, null, 'Run/Stop', 'C_CLOUMN34', 1, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (68, null, 'Setting Frequency', 'C_CLOUMN35', 1, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (69, null, 'Crude Oil Density', 'CrudeOilDensity', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (70, null, 'Water Density', 'WaterDensity', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (71, null, 'Natural Gas Relative Density', 'NaturalGasRelativeDensity', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (72, null, 'Saturation Pressure', 'SaturationPressure', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (73, null, 'Reservoir Depth', 'ReservoirDepth', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (74, null, 'Reservoir Temperature', 'ReservoirTemperature', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (75, null, 'Tubing Pressure', 'TubingPressure', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (76, null, 'Casing Pressure', 'CasingPressure', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (77, null, 'Well Head Temperature', 'WellHeadTemperature', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (78, null, 'Water Cut', 'WaterCut', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (79, null, 'Production Gas Oil Ratio', 'ProductionGasOilRatio', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (80, null, 'Producing Fluid Level', 'ProducingfluidLevel', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (81, null, 'Pump Setting Depth', 'PumpSettingDepth', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (82, null, 'Pump Bore Diameter', 'PumpBoreDiameter', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (83, null, 'Level Correct Value', 'LevelCorrectValue', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (84, null, 'Run Status', 'C_CLOUMN5', 2, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (85, null, 'Screw Speed', 'C_CLOUMN21', 2, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (86, null, 'Screw Torque', 'C_CLOUMN22', 2, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (87, null, 'Comm Time', 'CommTime', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (88, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (89, null, 'Comm Range', 'CommRange', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (90, null, 'Run Time', 'RunTime', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (91, null, 'Run Time Efficiency', 'RunTimeEfficiency', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (92, null, 'Run Range', 'RunRange', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (93, null, 'Theoretical Production', 'TheoreticalProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (94, null, 'Liquid Volumetric Production', 'LiquidVolumetricProduction', 2, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"1fe4f8"}', '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"31d8e9"}', 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (95, null, 'Oil Volumetric Production', 'OilVolumetricProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (96, null, 'Water Volumetric Production', 'WaterVolumetricProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (97, null, 'Total Liquid Volumetric Production', 'LiquidVolumetricProduction_l', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (98, null, 'Liquid Weight Production', 'LiquidWeightProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (99, null, 'Oil Weight Production', 'OilWeightProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (100, null, 'Water Weight Production', 'WaterWeightProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (101, null, 'Average Watt', 'AverageWatt', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (102, null, 'Water Power', 'WaterPower', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (103, null, 'System Efficiency', 'SystemEfficiency', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (104, null, 'Volumetric Efficiency', 'PumpEff1', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (105, null, 'Solids Contract', 'PumpEff2', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (106, null, 'Pump Efficiency', 'PumpEff', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (107, null, 'Pump Intake Pressure', 'PumpIntakeP', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (108, null, 'Pump Intake Temperature', 'PumpIntakeT', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (109, null, 'Pump Intake GOL', 'PumpIntakeGOL', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (110, null, 'Pump Intake Visl', 'PumpIntakeVisl', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (111, null, 'Pump Intake Bo', 'PumpIntakeBo', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (112, null, 'Pump Outlet Pressure', 'PumpOutletP', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (113, null, 'Pump Outlet Temperature', 'PumpOutletT', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (114, null, 'Pump Outlet GOL', 'PumpOutletGOL', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (115, null, 'Pump Outlet Visl', 'PumpOutletVisl', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (116, null, 'Pump Outlet Bo', 'PumpOutletBo', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (117, null, 'Run/Stop', 'C_CLOUMN34', 2, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, 1, 1);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEDATA, HISTORYDATA)
values (118, null, 'Setting Frequency', 'C_CLOUMN35', 2, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, 1, 1);

/*==============================================================*/
/* 初始化TBL_REPORT_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (1, 'unit1', 'SRP Report Unit', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 1, 'oilWell_PumpingDailyReport', 1);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (2, 'unit2', 'PCP Report Unit', 'oilWell_ScrewPump', 'oilWell_ScrewPumpProductionReoirt', 2, 'oilWell_ScrewPumpDailyReport', 2);
/*==============================================================*/
/* 初始化TBL_REPORT_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (1, null, 'Date', 'CalDate', 1, 2, null, null, null, null, null, 3, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (2, null, 'Comm Time', 'CommTime', 1, 3, null, null, null, null, null, 2, 0, '0,0,0', 1, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (3, null, 'Comm Range', 'CommRange', 1, 4, null, null, null, null, null, 1, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (4, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (5, null, 'Run Time', 'RunTime', 1, 6, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (6, null, 'Run Range', 'RunRange', 1, 7, null, null, null, null, null, 1, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (7, null, 'Run Time Efficiency', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (8, null, 'Result Name', 'ResultName', 1, 9, null, null, null, null, null, 1, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (9, null, 'Optimization Suggestion', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (10, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 1, 11, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b96161"}', null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (11, null, 'Total Oil Weight Production', 'OilWeightProduction', 1, 12, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e99314"}', null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (12, null, 'Total Water Weight Production', 'WaterWeightProduction', 1, 13, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"19c2eb"}', null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (13, null, 'Weight Water Cut', 'WeightWaterCut', 1, 14, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (14, null, 'Fullness Coefficient', 'FullnessCoefficient', 1, 15, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (15, null, 'Pump Setting Depth', 'PumpSettingDepth', 1, 16, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (16, null, 'Producing Fluid Level', 'ProducingfluidLevel', 1, 17, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (17, null, 'Submergence', 'Submergence', 1, 18, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (18, null, 'Power Degree Balance', 'WattDegreeBalance', 1, 19, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (19, null, 'Current Degree Balance', 'IDegreeBalance', 1, 20, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (20, null, 'Delta Radius', 'DeltaRadius', 1, 21, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (21, null, 'System Efficiency', 'SystemEfficiency', 1, 22, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (22, null, 'Surface System Efficiency', 'SurfaceSystemEfficiency', 1, 23, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (23, null, 'Well Down System Efficiency', 'WellDownSystemEfficiency', 1, 24, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (24, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 1, 25, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (25, null, 'Active Power Consumption', 'C_CLOUMN14', 1, 26, null, null, null, null, null, 2, 0, '0,0,0', null, 6, 'Acquisition');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (26, null, 'Remark', 'Remark', 1, 27, null, null, null, null, null, 1, 0, '0,0,0', null, 0, 'Input');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (27, null, 'Device', 'DeviceName', 1, 2, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Input');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (28, null, 'Date', 'CalDate', 1, 3, null, 0, 0, null, null, 3, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (29, null, 'Comm Time', 'CommTime', 1, 4, null, 0, 0, null, null, 2, 1, '0,0,0', 1, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (30, null, 'Comm Range', 'CommRange', 1, 5, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (31, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 1, 6, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (32, null, 'Run Time', 'RunTime', 1, 7, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (33, null, 'Run Range', 'RunRange', 1, 8, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (34, null, 'Run Time Efficiency', 'RunTimeEfficiency', 1, 9, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (35, null, 'Result Name', 'ResultName', 1, 10, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (36, null, 'Optimization Suggestion', 'OptimizationSuggestion', 1, 11, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (37, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 1, 12, null, 1, 1, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b66565"}', 1, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (38, null, 'Total Oil Weight Production', 'OilWeightProduction', 1, 13, null, 1, 1, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"ebbc1a"}', 1, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (39, null, 'Total Water Weight Production', 'WaterWeightProduction', 1, 14, null, 1, 1, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1beef3"}', 1, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (40, null, 'Weight Water Cut', 'WeightWaterCut', 1, 15, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (41, null, 'Fullness Coefficient', 'FullnessCoefficient', 1, 16, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (42, null, 'Pump Setting Depth', 'PumpSettingDepth', 1, 17, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (43, null, 'Producing Fluid Level', 'ProducingfluidLevel', 1, 18, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (44, null, 'Submergence', 'Submergence', 1, 19, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (45, null, 'Power Degree Balance', 'WattDegreeBalance', 1, 20, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (46, null, 'Current Degree Balance', 'IDegreeBalance', 1, 21, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (47, null, 'Delta Radius', 'DeltaRadius', 1, 22, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (48, null, 'System Efficiency', 'SystemEfficiency', 1, 23, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (49, null, 'Surface System Efficiency', 'SurfaceSystemEfficiency', 1, 24, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (50, null, 'Well Down System Efficiency', 'WellDownSystemEfficiency', 1, 25, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (51, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 1, 26, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (52, null, 'Active Power Consumption', 'C_CLOUMN14', 1, 27, null, 1, 1, null, null, 2, 1, '0,0,0', null, 6, 'Acquisition');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (53, null, 'Remark', 'Remark', 1, 28, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Input');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (54, null, 'Time', 'CalTime', 1, 2, null, null, null, null, null, 4, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (55, null, 'Comm Time', 'CommTime', 1, 3, null, null, null, null, null, 2, 2, '0,0,0', 1, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (56, null, 'Comm Range', 'CommRange', 1, 4, null, null, null, null, null, 1, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (57, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 2, '0,0,0', 2, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (58, null, 'Run Time', 'RunTime', 1, 6, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (59, null, 'Run Range', 'RunRange', 1, 7, null, null, null, null, null, 1, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (60, null, 'Run Time Efficiency', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (61, null, 'Result Name', 'ResultName', 1, 9, null, null, null, null, null, 1, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (62, null, 'Optimization Suggestion', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (63, null, 'Liquid Weight Production', 'RealtimeLiquidWeightProduction', 1, 11, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (64, null, 'Oil Weight Production', 'RealtimeOilWeightProduction', 1, 12, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (65, null, 'Water Weight Production', 'RealtimeWaterWeightProduction', 1, 13, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (66, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 1, 14, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"c18f8f"}', null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (67, null, 'Total Oil Weight Production', 'OilWeightProduction', 1, 15, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"232432"}', null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (68, null, 'Total Water Weight Production', 'WaterWeightProduction', 1, 16, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"3abaca"}', null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (69, null, 'Weight Water Cut', 'WeightWaterCut', 1, 17, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (70, null, 'Fullness Coefficient', 'FullnessCoefficient', 1, 18, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (71, null, 'Pump Setting Depth', 'PumpSettingDepth', 1, 19, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (72, null, 'Producing Fluid Level', 'ProducingfluidLevel', 1, 20, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (73, null, 'Submergence', 'Submergence', 1, 21, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (74, null, 'Power Degree Balance', 'WattDegreeBalance', 1, 22, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (75, null, 'Current Degree Balance', 'IDegreeBalance', 1, 23, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (76, null, 'Delta Radius', 'DeltaRadius', 1, 24, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (77, null, 'System Efficiency', 'SystemEfficiency', 1, 25, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (78, null, 'Surface System Efficiency', 'SurfaceSystemEfficiency', 1, 26, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (79, null, 'Well Down System Efficiency', 'WellDownSystemEfficiency', 1, 27, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (80, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 1, 28, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (81, null, 'Remark', 'Remark', 1, 30, null, null, null, null, null, 1, 2, '0,0,0', null, 0, 'Input');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (82, null, 'Active Power Consumption', 'C_CLOUMN14', 1, 29, null, null, null, null, null, 2, 2, '0,0,0', null, 6, 'Acquisition');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (83, null, 'Date', 'CalDate', 2, 2, null, null, null, null, null, 3, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (84, null, 'Comm Time', 'CommTime', 2, 3, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (85, null, 'Comm Range', 'CommRange', 2, 4, null, null, null, null, null, 1, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (86, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (87, null, 'Run Time', 'RunTime', 2, 6, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (88, null, 'Run Range', 'RunRange', 2, 7, null, null, null, null, null, 1, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (89, null, 'Run Time Efficiency', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (90, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 2, 9, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (91, null, 'Total Oil Weight Production', 'OilWeightProduction', 2, 10, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"4f4444"}', null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (92, null, 'Total Water Weight Production', 'WaterWeightProduction', 2, 11, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"31bbbe"}', null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (93, null, 'Weight Water Cut', 'WeightWaterCut', 2, 12, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (94, null, 'RPM', 'RPM', 2, 13, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (95, null, 'Pump Setting Depth', 'PumpSettingDepth', 2, 14, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (96, null, 'Producing Fluid Level', 'ProducingfluidLevel', 2, 15, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (97, null, 'Submergence', 'Submergence', 2, 16, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (98, null, 'System Efficiency', 'SystemEfficiency', 2, 17, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (99, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 2, 18, null, null, null, null, null, 2, 0, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (100, null, 'Active Power Consumption', 'C_CLOUMN14', 2, 19, null, null, null, null, null, 2, 0, '0,0,0', null, 6, 'Acquisition');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (101, null, 'Remark', 'Remark', 2, 20, null, null, null, null, null, 1, 0, '0,0,0', null, 0, 'Input');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (102, null, 'Device', 'DeviceName', 2, 2, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Input');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (103, null, 'Date', 'CalDate', 2, 3, null, 0, 0, null, null, 3, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (104, null, 'Comm Time', 'CommTime', 2, 4, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (105, null, 'Comm Range', 'CommRange', 2, 5, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (106, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 2, 6, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (107, null, 'Run Time', 'RunTime', 2, 7, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (108, null, 'Run Range', 'RunRange', 2, 8, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (109, null, 'Run Time Efficiency', 'RunTimeEfficiency', 2, 9, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (110, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 2, 10, null, 1, 1, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e40c54"}', 1, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (111, null, 'Total Oil Weight Production', 'OilWeightProduction', 2, 11, null, 1, 1, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d7bb14"}', 1, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (112, null, 'Total Water Weight Production', 'WaterWeightProduction', 2, 12, null, 1, 1, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1dbfb4"}', 1, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (113, null, 'Weight Water Cut', 'WeightWaterCut', 2, 13, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (114, null, 'RPM', 'RPM', 2, 14, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (115, null, 'Pump Setting Depth', 'PumpSettingDepth', 2, 15, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (116, null, 'Producing Fluid Level', 'ProducingfluidLevel', 2, 16, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (117, null, 'Submergence', 'Submergence', 2, 17, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (118, null, 'System Efficiency', 'SystemEfficiency', 2, 18, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (119, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 2, 19, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (120, null, 'Active Power Consumption', 'C_CLOUMN14', 2, 20, null, 1, 1, null, null, 2, 1, '0,0,0', null, 6, 'Acquisition');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (121, null, 'Remark', 'Remark', 2, 21, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, 'Input');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (122, null, 'Time', 'CalTime', 2, 2, null, null, null, null, null, 4, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (123, null, 'Comm Time', 'CommTime', 2, 3, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (124, null, 'Comm Range', 'CommRange', 2, 4, null, null, null, null, null, 1, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (125, null, 'Comm Time Efficiency', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (126, null, 'Run Time', 'RunTime', 2, 6, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (127, null, 'Run Range', 'RunRange', 2, 7, null, null, null, null, null, 1, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (128, null, 'Run Time Efficiency', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (129, null, 'Liquid Volumetric Production', 'RealtimeLiquidVolumetricProduction', 2, 9, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (130, null, 'Oil Volumetric Production', 'RealtimeOilVolumetricProduction', 2, 10, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (131, null, 'Water Volumetric Production', 'RealtimeWaterVolumetricProduction', 2, 11, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (132, null, 'Total Liquid Weight Production', 'LiquidWeightProduction', 2, 12, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (133, null, 'Total Oil Weight Production', 'OilWeightProduction', 2, 13, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"995353"}', null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (134, null, 'Total Water Weight Production', 'WaterWeightProduction', 2, 14, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"57a6a8"}', null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (135, null, 'Weight Water Cut', 'WeightWaterCut', 2, 15, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (136, null, 'RPM', 'RPM', 2, 16, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (137, null, 'Pump Setting Depth', 'PumpSettingDepth', 2, 17, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (138, null, 'Producing Fluid Level', 'ProducingfluidLevel', 2, 18, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (139, null, 'Submergence', 'Submergence', 2, 19, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (140, null, 'System Efficiency', 'SystemEfficiency', 2, 20, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (141, null, 'Energy Per 100m Lift', 'EnergyPer100mLift', 2, 21, null, null, null, null, null, 2, 2, '0,0,0', null, 0, 'Calculate');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (142, null, 'Active Power Consumption', 'C_CLOUMN14', 2, 22, null, null, null, null, null, 2, 2, '0,0,0', null, 6, 'Acquisition');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (143, null, 'Remark', 'Remark', 2, 23, null, null, null, null, null, 1, 2, '0,0,0', null, 0, 'Input');


/*==============================================================*/
/* 初始化TBL_PROTOCOLINSTANCE数据                                 */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (1, 'SRP Acquisition Instance', 'instance1', 'modbus-tcp', 'modbus-tcp', 1, 'BB01', '0B', 0, 1, 'BB01', '0B', 100, 1, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (2, 'PCP Acquisition Instance', 'instance2', 'modbus-tcp', 'modbus-tcp', 1, 'BB01', '0B', 0, 1, 'BB01', '0B', 100, 2, 2);


/*==============================================================*/
/* 初始化tbl_protocolalarminstance数据                            */
/*==============================================================*/
insert into TBL_PROTOCOLALARMINSTANCE (ID, NAME, CODE, ALARMUNITID, SORT)
values (1, 'SRP Alarm Instance', 'alarminstance1', 1, 1);

insert into TBL_PROTOCOLALARMINSTANCE (ID, NAME, CODE, ALARMUNITID, SORT)
values (2, 'PCP Alarm Instance', 'alarminstance2', 2, 2);

/*==============================================================*/
/* 初始化tbl_protocoldisplayinstance数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLDISPLAYINSTANCE (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (1, 'SRP Display Instance', 'displayinstance1', 1, 1);

insert into TBL_PROTOCOLDISPLAYINSTANCE (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (2, 'PCP Display Instance', 'displayinstance2', 2, 2);

/*==============================================================*/
/* 初始化TBL_PROTOCOLREPORTINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (1, 'SRP Report Instance', 'reportinstance1', 1, 1);

insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (2, 'PCP Report Instance', 'reportinstance2', 2, 2);