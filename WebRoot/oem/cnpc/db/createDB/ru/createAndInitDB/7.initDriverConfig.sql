/*==============================================================*/
/* 初始化TBL_PROTOCOL数据                                        */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'Протокол А11', 'protocol1', 1, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"Давление в трубках","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"Давление в корпусе","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Противодавление","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Температура на устье скважины","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Рабочее состояние","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"Остановленный"},'
  ||'{"Value":1,"Meaning":"Бег"}]},{"Title":"бежать/Прекрати","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"бежать"},{"Value":2,"Meaning":"Прекрати"}]},{"Title":"водосодержание","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"Производство жидких поверхностей","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Фазный ток","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B-фазный ток","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Ток по С-фазе","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"Напряжение фазы А","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Напряжение фазы B","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"С-фазное напряжение","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Активная потребляемая мощность","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Потребляемая реактивная мощность","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Активная мощность","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Реактивная мощность","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Реверсивная мощность","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"Фактор","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Значение настройки частоты","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Периодичность работы","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Скорость вращения","Addr":40430,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"r/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Вращающий момент","Addr":40432,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kN·m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Интервал сбора динамометра","Addr":40981,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"min","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"Сбор диаграммы мощности вручную","Addr":40982,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"Fixed Time"},{"Value":1,"Meaning":"Manual"}]},{"Title":"Динамометрическая диаграмма задает количество точек","Addr":40983,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Количество очков, собранных с помощью динамометрической диаграммы","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Время сбора динамограммы","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Ударов в минуту","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"1/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"удар","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},'
  ||'{"Title":"Данные о перемещении динамометра","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.001,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"Данные о нагрузке на динамометр","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Текущие данные кривой","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"Данные кривой мощности","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive","Meaning":[]}]
';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.CODE='protocol1';  
  COMMIT;  
END;  
/



/*==============================================================*/
/* 初始化TBL_DATAMAPPING数据                                          */
/*==============================================================*/
insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (1, 'Давление в трубках', 'C_CLOUMN1', 0, 'TubingPressure', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (2, 'Давление в корпусе', 'C_CLOUMN2', 0, 'CasingPressure', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (3, 'Противодавление', 'C_CLOUMN3', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (4, 'Температура на устье скважины', 'C_CLOUMN4', 0, 'WellHeadTemperature', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (5, 'Рабочее состояние', 'C_CLOUMN5', 0, 'RunStatus', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (6, 'водосодержание', 'C_CLOUMN6', 0, 'VolumeWaterCut', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (7, 'Производство жидких поверхностей', 'C_CLOUMN7', 0, 'ProducingfluidLevel', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (8, 'Фазный ток', 'C_CLOUMN8', 0, 'IA', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (9, 'B-фазный ток', 'C_CLOUMN9', 0, 'IB', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (10, 'Ток по С-фазе', 'C_CLOUMN10', 0, 'IC', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (11, 'Напряжение фазы А', 'C_CLOUMN11', 0, 'VA', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (12, 'Напряжение фазы B', 'C_CLOUMN12', 0, 'VB', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (13, 'С-фазное напряжение', 'C_CLOUMN13', 0, 'VC', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (14, 'Активная потребляемая мощность', 'C_CLOUMN14', 0, 'TotalKWattH', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (15, 'Потребляемая реактивная мощность', 'C_CLOUMN15', 0, 'TotalKVarH', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (16, 'Активная мощность', 'C_CLOUMN16', 0, 'Watt3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (17, 'Реактивная мощность', 'C_CLOUMN17', 0, 'Var3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (18, 'Реверсивная мощность', 'C_CLOUMN18', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (19, 'Фактор', 'C_CLOUMN19', 0, 'PF3', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (20, 'Периодичность работы', 'C_CLOUMN20', 0, 'RunFrequency', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (21, 'Скорость вращения', 'C_CLOUMN21', 0, 'RPM', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (22, 'Вращающий момент', 'C_CLOUMN22', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (23, 'Интервал сбора динамометра', 'C_CLOUMN23', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (24, 'Сбор диаграммы мощности вручную', 'C_CLOUMN24', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (25, 'Динамометрическая диаграмма задает количество точек', 'C_CLOUMN25', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (26, 'Количество очков, собранных с помощью динамометрической диаграммы', 'C_CLOUMN26', 0, 'FESDiagramAcqCount', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (27, 'Время сбора динамограммы', 'C_CLOUMN27', 0, 'FESDiagramAcqtime', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (28, 'Ударов в минуту', 'C_CLOUMN28', 0, 'SPM', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (29, 'удар', 'C_CLOUMN29', 0, 'Stroke', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (30, 'Данные о перемещении динамометра', 'C_CLOUMN30', 0, 'Position_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (31, 'Данные о нагрузке на динамометр', 'C_CLOUMN31', 0, 'Load_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (32, 'Текущие данные кривой', 'C_CLOUMN32', 0, 'Current_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (33, 'Данные кривой мощности', 'C_CLOUMN33', 0, 'Power_Curve', null, 1, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (34, 'бежать/Прекрати', 'C_CLOUMN34', 0, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (35, 'Значение настройки частоты', 'C_CLOUMN35', 0, null, null, 1, 0);


/*==============================================================*/
/* 初始化TBL_RUNSTATUSCONFIG数据                                          */
/*==============================================================*/
insert into TBL_RUNSTATUSCONFIG (ID, PROTOCOL, ITEMNAME, ITEMMAPPINGCOLUMN, RUNVALUE, STOPVALUE, PROTOCOLTYPE, RESOLUTIONMODE, RUNCONDITION, STOPCONDITION)
values (1, 'protocol1', 'Рабочее состояние', 'C_CLOUMN5', '1', '0', 0, 1, null, null);

/*==============================================================*/
/* 初始化TBL_ACQ_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, SORT)
values (1, 'unit1', 'Сборный агрегат насосного агрегата', 'Протокол А11', 'Сборный агрегат насосного агрегата', 1);

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, SORT)
values (2, 'unit2', 'Блок сбора данных с прогрессивным винтовым насосом', 'Протокол А11', 'Блок сбора данных с прогрессивным винтовым насосом', 2);

/*==============================================================*/
/* 初始化TBL_ACQ_GROUP_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', 'Группа по приобретению', 60, 60, 'Протокол А11', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', 'Контрольная группа', 0, 0, 'Протокол А11', 1, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (3, 'group3', 'Группа по приобретению', 60, 60, 'Протокол А11', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (4, 'group4', 'Контрольная группа', 0, 0, 'Протокол А11', 1, null);

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
values (1, null, 'Рабочее состояние', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (2, null, 'Активная потребляемая мощность', null, 1, null, '0,0,0', 1, 'Ежедневное потребление электроэнергии');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (3, null, 'Значение настройки частоты', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (4, null, 'Периодичность работы', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (5, null, 'Количество очков, собранных с помощью динамометрической диаграммы', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (6, null, 'Время сбора динамограммы', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (7, null, 'Ударов в минуту', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (8, null, 'удар', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (9, null, 'Данные о перемещении динамометра', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (10, null, 'Данные о нагрузке на динамометр', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (11, null, 'Текущие данные кривой', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (12, null, 'Данные кривой мощности', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (13, null, 'бежать/Прекрати', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (14, null, 'Значение настройки частоты', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (15, null, 'Рабочее состояние', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (16, null, 'Скорость вращения', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (17, null, 'Вращающий момент', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (18, null, 'бежать/Прекрати', null, 4, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (19, null, 'Значение настройки частоты', null, 4, null, '0,0,0', 0, null);

/*==============================================================*/
/* 初始化TBL_ALARM_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, CALCULATETYPE, SORT)
values (1, 'alarmunit1', 'Блок сигнализации насосного агрегата', 'Протокол А11', 'Блок сигнализации насосного агрегата', 1, 1);

insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK, CALCULATETYPE, SORT)
values (2, 'alarmunit2', 'Блок сигнализации винтового насоса', 'Протокол А11', 'Блок сигнализации винтового насоса', 2, 2);

/*==============================================================*/
/* 初始化TBL_ALARM_ITEM2UNIT_CONF数据                                          */
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
/* 初始化TBL_DISPLAY_UNIT_CONF数据                                */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK, CALCULATETYPE, SORT)
values (1, 'unit1', 'Блок отображения насосного агрегата', 'Протокол А11', 1, 'Блок отображения насосного агрегата', 1, 1);

insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK, CALCULATETYPE, SORT)
values (2, 'unit2', 'Блок индикации винтового насоса', 'Протокол А11', 2, 'Блок индикации винтового насоса', 2, 2);

/*==============================================================*/
/* 初始化TBL_DISPLAY_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (1, null, 'Активная потребляемая мощность', 'C_CLOUMN14', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (2, null, 'Периодичность работы', 'C_CLOUMN20', 1, null, null, null, '{"sort":6,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"a07474"}', '{"sort":6,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"a07474"}', 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (3, null, 'Количество очков, собранных с помощью динамометрической диаграммы', 'C_CLOUMN26', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (4, null, 'Время сбора динамограммы', 'C_CLOUMN27', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (5, null, 'Ударов в минуту', 'C_CLOUMN28', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (6, null, 'удар', 'C_CLOUMN29', 1, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (7, null, 'Время онлайн', 'CommTime', 1, 1, null, null, null, null, 1, '0,0,0', 1, null, null, null, null, 1, 1, 1, 1, 1, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (8, null, 'Эффективность времени в интернете', 'CommTimeEfficiency', 1, 2, null, null, null, null, 1, '0,0,0', 2, null, null, null, null, 1, 1, 1, 1, 2, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (9, null, 'Временные рамки онлайн', 'CommRange', 1, 3, null, null, null, null, 1, '0,0,0', 3, null, null, null, null, 1, 1, 1, 1, 3, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (10, null, 'Рабочее состояние', 'RunStatusName', 1, 4, null, null, null, null, 1, '0,0,0', 4, null, null, null, null, 0, 1, 0, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (11, null, 'Продолжительность', 'RunTime', 1, 7, null, null, null, null, 1, '0,0,0', 5, null, null, null, null, 1, 1, 1, 1, 5, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (12, null, 'Эффективность во время выполнения', 'RunTimeEfficiency', 1, 8, null, null, null, null, 1, '0,0,0', 6, null, null, null, null, 1, 1, 1, 1, 6, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (13, null, 'Диапазон времени выполнения', 'RunRange', 1, 9, null, null, null, null, 1, '0,0,0', 7, null, null, null, null, 1, 1, 1, 1, 7, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (14, null, 'Условия работы', 'ResultName', 1, 10, null, null, null, null, 1, '0,0,0', 8, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (15, null, 'Максимальная нагрузка', 'FMax', 1, 20, null, null, '{"sort":5,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b824e6"}', '{"sort":5,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"b824e6"}', 1, '0,0,0', 9, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (16, null, 'Минимальная нагрузка', 'FMin', 1, 23, null, null, '{"sort":6,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e25e1d"}', '{"sort":6,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e25e1d"}', 1, '0,0,0', 10, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (17, null, 'Ход плунжера', 'PlungerStroke', 1, 15, null, null, null, null, 1, '0,0,0', 11, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (18, null, 'Эффективный ход плунжера', 'AvailablePlungerStroke', 1, 18, null, null, null, null, 1, '0,0,0', 12, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (19, null, 'Эффективный ход поршня при отсутствии жидкости', 'NoLiquidAvailablePlungerStroke', 1, 27, null, null, null, null, 1, '0,0,0', 13, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (20, null, 'Коэффициент заполнения', 'FullnessCoefficient', 1, 21, null, null, null, null, 1, '0,0,0', 14, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (21, null, 'Коэффициент наполнения при отсутствии жидкости', 'NoLiquidFullnessCoefficient', 1, 24, null, null, null, null, 1, '0,0,0', 15, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (22, null, 'Теоретическая максимальная линия нагрузки', 'UpperLoadLine', 1, 26, null, null, null, null, 1, '0,0,0', 16, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (23, null, 'Теоретическая минимальная грузовая линия', 'LowerLoadLine', 1, 29, null, null, null, null, 1, '0,0,0', 17, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (24, null, 'Теоретическое производство жидкости', 'TheoreticalProduction', 1, 25, null, null, null, null, 1, '0,0,0', 18, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (25, null, 'Производство жидкостей в режиме реального времени', 'LiquidVolumetricProduction', 1, 13, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"2560d4"}', 1, '0,0,0', 19, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (26, null, 'Добыча нефти в режиме реального времени', 'OilVolumetricProduction', 1, 16, null, null, '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"4fbfc4"}', '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"3fa8d2"}', 1, '0,0,0', 20, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (27, null, 'Добыча воды в режиме реального времени', 'WaterVolumetricProduction', 1, 19, null, null, null, null, 1, '0,0,0', 21, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (28, null, 'По эффективному ходу плунжера рассчитывается выходная мощность', 'AvailablePlungerStrokeProd_v', 1, 28, null, null, null, null, 1, '0,0,0', 22, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (29, null, 'Количество жидкости, вытекающей из зазора насоса', 'PumpClearanceleakProd_v', 1, 30, null, null, null, null, 1, '0,0,0', 23, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (30, null, 'Совокупное суточное производство жидкости', 'LiquidVolumetricProduction_l', 1, 22, null, null, null, null, 1, '0,0,0', 24, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (31, null, 'Средняя активная мощность', 'AverageWatt', 1, 44, null, null, null, null, 1, '0,0,0', 25, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (32, null, 'Мощность светового столба', 'PolishRodPower', 1, 47, null, null, null, null, 1, '0,0,0', 26, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (33, null, 'Гидроэнергия', 'WaterPower', 1, 41, null, null, null, null, 1, '0,0,0', 27, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (34, null, 'Эффективность работы на грунте', 'SurfaceSystemEfficiency', 1, 43, null, null, null, null, 1, '0,0,0', 28, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (35, null, 'Внутрискважинная эффективность', 'WellDownSystemEfficiency', 1, 46, null, null, null, null, 1, '0,0,0', 29, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (36, null, 'Эффективность системы', 'SystemEfficiency', 1, 40, null, null, null, null, 1, '0,0,0', 30, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (37, null, 'Площадь', 'Area', 1, 42, null, null, null, null, 1, '0,0,0', 31, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (38, null, 'Потребляемая мощность на 100 метров подъема', 'EnergyPer100mLift', 1, 45, null, null, null, null, 1, '0,0,0', 32, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (39, null, 'Изменение длины удилища', 'RodFlexLength', 1, 31, null, null, null, null, 1, '0,0,0', 33, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (40, null, 'Длина трубки варьируется', 'TubingFlexLength', 1, 32, null, null, null, null, 1, '0,0,0', 34, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (41, null, 'Приращение инерционной нагрузки', 'InertiaLength', 1, 33, null, null, null, null, 1, '0,0,0', 35, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (42, null, 'Фактор потери при инсульте', 'PumpEff1', 1, 34, null, null, null, null, 1, '0,0,0', 36, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (43, null, 'Коэффициент заполнения', 'PumpEff2', 1, 35, null, null, null, null, 1, '0,0,0', 37, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (44, null, 'Коэффициент утечки зазора', 'PumpEff3', 1, 36, null, null, null, null, 1, '0,0,0', 38, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (45, null, 'Коэффициент усадки жидкостей', 'PumpEff4', 1, 37, null, null, null, null, 1, '0,0,0', 39, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (46, null, 'Эффективность насоса', 'PumpEff', 1, 38, null, null, null, null, 1, '0,0,0', 40, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (47, null, 'Давление на входе насоса', 'PumpIntakeP', 1, 61, null, null, null, null, 1, '0,0,0', 41, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (48, null, 'Температура на входе в насос', 'PumpIntakeT', 1, 62, null, null, null, null, 1, '0,0,0', 42, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (49, null, 'Соотношение газа и жидкости на входе насоса', 'PumpIntakeGOL', 1, 63, null, null, null, null, 1, '0,0,0', 43, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (50, null, 'Вязкость на входе насоса', 'PumpIntakeVisl', 1, 64, null, null, null, null, 1, '0,0,0', 44, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (51, null, 'Объемный коэффициент сырой нефти на входе насоса', 'PumpIntakeBo', 1, 65, null, null, null, null, 1, '0,0,0', 45, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (52, null, 'Давление на выходе насоса', 'PumpOutletP', 1, 67, null, null, null, null, 1, '0,0,0', 46, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (53, null, 'Температура на выходе насоса', 'PumpOutletT', 1, 68, null, null, null, null, 1, '0,0,0', 47, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (54, null, 'Отношение газа к жидкости на выходе из насоса', 'PumpOutletGOL', 1, 69, null, null, null, null, 1, '0,0,0', 48, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (55, null, 'Вязкость на выходе насоса', 'PumpOutletVisl', 1, 70, null, null, null, null, 1, '0,0,0', 49, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (56, null, 'Объемный коэффициент нефти на выходе из насоса', 'PumpOutletBo', 1, 71, null, null, null, null, 1, '0,0,0', 50, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (57, null, 'Максимальный ток восходящего хода', 'UpStrokeIMax', 1, 50, null, null, null, null, 1, '0,0,0', 51, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (58, null, 'Максимальный ток нисходящего хода', 'DownStrokeIMax', 1, 51, null, null, null, null, 1, '0,0,0', 52, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (59, null, 'Максимальная мощность на восходящем ходу', 'UpStrokeWattMax', 1, 53, null, null, null, null, 1, '0,0,0', 53, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (60, null, 'Максимальная мощность хода вниз', 'DownStrokeWattMax', 1, 54, null, null, null, null, 1, '0,0,0', 54, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (61, null, 'Текущий баланс', 'IDegreeBalance', 1, 49, null, null, null, null, 1, '0,0,0', 55, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (62, null, 'Баланс мощности', 'WattDegreeBalance', 1, 52, null, null, null, null, 1, '0,0,0', 56, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (63, null, 'Расстояние в пути', 'DeltaRadius', 1, 55, null, null, null, null, 1, '0,0,0', 57, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (64, null, 'Разница коррекции уровня', 'LevelDifferenceValue', 1, 59, null, null, null, null, 1, '0,0,0', 58, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (65, null, 'Инвертированный уровень производственной жидкости', 'CalcProducingfluidLevel', 1, 58, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b89393"}', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"8f6b6b"}', 1, '0,0,0', 59, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (66, null, 'Ежедневное потребление электроэнергии', 'C_CLOUMN14_TOTAL', 1, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (67, null, 'бежать/Прекрати', 'C_CLOUMN34', 1, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, null, null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (68, null, 'Значение настройки частоты', 'C_CLOUMN35', 1, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, null, null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (69, null, 'Плотность сырой нефти', 'CrudeOilDensity', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (70, null, 'Плотность воды', 'WaterDensity', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (71, null, 'Относительная плотность природного газа', 'NaturalGasRelativeDensity', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (72, null, 'Давление насыщения', 'SaturationPressure', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (73, null, 'Глубина масляного слоя', 'ReservoirDepth', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (74, null, 'Температура масляного слоя', 'ReservoirTemperature', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (75, null, 'Давление в трубках', 'TubingPressure', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (76, null, 'Давление в корпусе', 'CasingPressure', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (77, null, 'Температура на устье скважины', 'WellHeadTemperature', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (78, null, 'Водосодержание', 'WaterCut', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (79, null, 'Соотношение газа и нефти', 'ProductionGasOilRatio', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (80, null, 'Производство жидких поверхностей', 'ProducingfluidLevel', 1, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (81, null, 'Глубина насоса', 'PumpSettingDepth', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (82, null, 'Диаметр бочки', 'PumpBoreDiameter', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (83, null, 'Значение коррекции уровня', 'LevelCorrectValue', 1, null, null, null, null, null, 3, '0,0,0', null, null, null, null, null, 0, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (84, null, 'Рабочее состояние', 'C_CLOUMN5', 2, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (85, null, 'Скорость вращения', 'C_CLOUMN21', 2, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (86, null, 'Вращающий момент', 'C_CLOUMN22', 2, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (87, null, 'Время онлайн', 'CommTime', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (88, null, 'Эффективность времени в интернете', 'CommTimeEfficiency', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (89, null, 'Временные рамки онлайн', 'CommRange', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (90, null, 'Продолжительность', 'RunTime', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (91, null, 'Эффективность во время выполнения', 'RunTimeEfficiency', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (92, null, 'Диапазон времени выполнения', 'RunRange', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (93, null, 'Теоретическое производство жидкости', 'TheoreticalProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (94, null, 'Производство жидкостей в режиме реального времени', 'LiquidVolumetricProduction', 2, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"1fe4f8"}', '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"31d8e9"}', 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (95, null, 'Добыча нефти в режиме реального времени', 'OilVolumetricProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (96, null, 'Добыча воды в режиме реального времени', 'WaterVolumetricProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (97, null, 'Совокупное суточное производство жидкости', 'LiquidVolumetricProduction_l', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (98, null, 'Производство жидкостей в режиме реального времени', 'LiquidWeightProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (99, null, 'Добыча нефти в режиме реального времени', 'OilWeightProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (100, null, 'Добыча воды в режиме реального времени', 'WaterWeightProduction', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (101, null, 'Средняя активная мощность', 'AverageWatt', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (102, null, 'Гидроэнергия', 'WaterPower', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (103, null, 'Эффективность системы', 'SystemEfficiency', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (104, null, 'Объемный КПД', 'PumpEff1', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (105, null, 'Коэффициент усадки жидкостей', 'PumpEff2', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (106, null, 'Эффективность насоса', 'PumpEff', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (107, null, 'Давление на входе насоса', 'PumpIntakeP', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (108, null, 'Температура на входе в насос', 'PumpIntakeT', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (109, null, 'Соотношение газа и жидкости на входе насоса', 'PumpIntakeGOL', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (110, null, 'Вязкость на входе насоса', 'PumpIntakeVisl', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (111, null, 'Объемный коэффициент сырой нефти на входе насоса', 'PumpIntakeBo', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (112, null, 'Давление на выходе насоса', 'PumpOutletP', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (113, null, 'Температура на выходе насоса', 'PumpOutletT', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (114, null, 'Отношение газа к жидкости на выходе из насоса', 'PumpOutletGOL', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (115, null, 'Вязкость на выходе насоса', 'PumpOutletVisl', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (116, null, 'Объемный коэффициент нефти на выходе из насоса', 'PumpOutletBo', 2, null, null, null, null, null, 1, '0,0,0', null, null, null, null, null, 1, 1, 1, 1, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (117, null, 'бежать/Прекрати', 'C_CLOUMN34', 2, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, null, null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT)
values (118, null, 'Значение настройки частоты', 'C_CLOUMN35', 2, null, null, null, null, null, 2, '0,0,0', null, null, null, null, null, null, null, null, null, null, null);

/*==============================================================*/
/* 初始化TBL_REPORT_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (1, 'unit1', 'Блок отчетности насосного агрегата', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 1, 'oilWell_PumpingDailyReport', 1);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (2, 'unit2', 'Блок отчетности винтового насоса', 'oilWell_ScrewPump', 'oilWell_ScrewPumpProductionReoirt', 2, 'oilWell_ScrewPumpDailyReport', 2);
/*==============================================================*/
/* 初始化TBL_REPORT_ITEMS2UNIT_CONF数据                                          */
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
values (54, null, '时间', 'CalTime', 1, 2, null, null, null, null, null, 4, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (55, null, '在线时间', 'CommTime', 1, 3, null, null, null, null, null, 2, 2, '0,0,0', 1, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (56, null, '在线区间', 'CommRange', 1, 4, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (57, null, '在线时率', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 2, '0,0,0', 2, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (58, null, '运行时间', 'RunTime', 1, 6, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (59, null, '运行区间', 'RunRange', 1, 7, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (60, null, '运行时率', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (61, null, '工况', 'ResultName', 1, 9, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (62, null, '优化建议', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (63, null, '瞬时产液量', 'RealtimeLiquidWeightProduction', 1, 11, null, null, null, null, null, 2, 2, '0,0,0', null, null, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (64, null, '瞬时产油量', 'RealtimeOilWeightProduction', 1, 12, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (65, null, '瞬时产水量', 'RealtimeWaterWeightProduction', 1, 13, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (66, null, '日累计产液量', 'LiquidWeightProduction', 1, 14, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"c18f8f"}', null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (67, null, '日累计产油量', 'OilWeightProduction', 1, 15, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"232432"}', null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (68, null, '日累计产水量', 'WaterWeightProduction', 1, 16, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"3abaca"}', null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (69, null, '重量含水率', 'WeightWaterCut', 1, 17, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (70, null, '充满系数', 'FullnessCoefficient', 1, 18, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (71, null, '泵挂', 'PumpSettingDepth', 1, 19, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (72, null, '动液面', 'ProducingfluidLevel', 1, 20, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (73, null, '沉没度', 'Submergence', 1, 21, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (74, null, '功率平衡度', 'WattDegreeBalance', 1, 22, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (75, null, '电流平衡度', 'IDegreeBalance', 1, 23, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (76, null, '移动距离', 'DeltaRadius', 1, 24, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (77, null, '系统效率', 'SystemEfficiency', 1, 25, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (78, null, '地面效率', 'SurfaceSystemEfficiency', 1, 26, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (79, null, '井下效率', 'WellDownSystemEfficiency', 1, 27, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (80, null, '吨液百米耗电量', 'EnergyPer100mLift', 1, 28, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '1');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (81, null, 'Активная потребляемая мощность', 'C_CLOUMN14', 1, 29, null, null, null, null, null, 2, 2, '0,0,0', null, 6, '0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (82, null, '备注', 'Remark', 1, 30, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '2');

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
/* 初始化TBL_PROTOCOLINSTANCE数据                                 */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (1, 'Пример управления производством насосного агрегата', 'instance1', 'modbus-tcp', 'modbus-tcp', 1, 'AA01', '0B', 0, 1, 'AA01', '0B', 100, 1, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (2, 'Пример управления захватом винтового насоса', 'instance2', 'modbus-tcp', 'modbus-tcp', 1, 'AA01', '0B', 0, 1, 'AA01', '0B', 100, 2, 2);


/*==============================================================*/
/* 初始化tbl_protocolalarminstance数据                            */
/*==============================================================*/
insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, SORT)
values (1, 'Пример аварийной сигнализации насосного агрегата', 'alarminstance1', 1, 1);

insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, SORT)
values (2, 'Пример аварийной сигнализации винтового насоса', 'alarminstance2', 2, 2);

/*==============================================================*/
/* 初始化tbl_protocoldisplayinstance数据                                          */
/*==============================================================*/
insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (1, 'Пример отображения насосного агрегата', 'displayinstance1', 1, 1);

insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (2, 'Пример отображения винтового насоса', 'displayinstance2', 2, 2);

/*==============================================================*/
/* 初始化TBL_PROTOCOLREPORTINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (1, 'Пример отчета по насосному агрегату', 'reportinstance1', 1, 1);

insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (2, 'Пример отчета о винтовом насосе', 'reportinstance2', 2, 2);