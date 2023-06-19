/*==============================================================*/
/* 初始化TBL_PROTOCOL数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'A11-抽油机', 'protocol1', 0, 1);

insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (2, 'A11-螺杆泵', 'protocol2', 1, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"声光报警控制位","Addr":3,"StoreDataType":"bit","IFDataType":"bool","Prec":0,"Quantity":3,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":0,"AcqMode":"passive","Meaning":[]},{"Title":"油压","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"套压","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"回压","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"井口温度","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"运行状态","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"停抽"},{"Value":1,"Meaning":"运行"}]},{"Title":"启停控制","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"启抽"},{"Value":2,"Meaning":"停抽"}]},{"Title":"含水率","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"动液面","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"光杆温度","Addr":40331,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"盘根盒温度","Addr":40333,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A相电流","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B相电流","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C相电流","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"A相电压","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B相电压","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C相电压","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"有功功耗","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"无功功耗","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"有功功率","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"无功功率","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},{"Title":"反向功率","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功率因数","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频设置频率","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频运行频率","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图采集间隔","Addr":40981,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"min","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图设置点数","Addr":40983,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图实测点数","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图采集时间","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"冲次","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"次/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"冲程","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图数据-位移","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-载荷","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-电流","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-功率","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive","Meaning":[]}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.NAME='A11-抽油机';  
  COMMIT;  
END;  
/

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"油压","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"套压","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"回压","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"井口温度","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"运行状态","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"停抽"},'
  ||'{"Value":1,"Meaning":"运行"}]},{"Title":"启停控制","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"启抽"},{"Value":2,"Meaning":"停抽"}]},{"Title":"含水率","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"动液面","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A相电流","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"B相电流","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C相电流","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A相电压","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B相电压","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C相电压","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"有功功耗","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW·h","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"无功功耗","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"有功功率","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"无功功率","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},{"Title":"反向功率","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功率因数","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"变频设置频率","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频运行频率","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"螺杆泵转速","Addr":40430,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"r/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"螺杆泵扭矩","Addr":40432,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kN·m","ResolutionMode":2,"AcqMode":"passive"}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.NAME='A11-抽油机';  
  COMMIT;  
END;  
/

/*==============================================================*/
/* 初始化TBL_ACQ_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', '抽油机A11采集单元', 'A11-抽油机', null);

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (2, 'unit2', '螺杆泵A11采集单元', 'A11-螺杆泵', null);

/*==============================================================*/
/* 初始化TBL_ACQ_GROUP_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '采集组', 60, 60, 'A11-抽油机', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '控制组', 0, 0, 'A11-抽油机', 1, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (3, 'group3', '采集组', 60, 60, 'A11-螺杆泵', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (4, 'group4', '控制组', 0, 0, 'A11-螺杆泵', 1, null);

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
insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (1, null, '油压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (2, null, '套压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (3, null, '回压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (4, null, '井口温度', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (5, null, '含水率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (6, null, '运行状态', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (7, null, '启停控制', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (8, null, '光杆温度', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (9, null, '盘根盒温度', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (10, null, 'A相电流', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (11, null, 'B相电流', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (12, null, 'C相电流', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (13, null, 'A相电压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (14, null, 'B相电压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (15, null, 'C相电压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (16, null, '有功功耗', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (17, null, '无功功耗', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (18, null, '有功功率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (19, null, '无功功率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (20, null, '反向功率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (21, null, '功率因数', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (22, null, '变频设置频率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (23, null, '变频运行频率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (24, null, '功图采集间隔', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (25, null, '功图设置点数', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (26, null, '功图实测点数', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (27, null, '功图采集时间', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (28, null, '冲次', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (58, null, '冲程', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (59, null, '功图数据-位移', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (60, null, '功图数据-载荷', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (61, null, '功图数据-电流', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (62, null, '功图数据-功率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (29, null, '启停控制', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (30, null, '变频设置频率', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (31, null, '功图采集间隔', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (32, null, '功图设置点数', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (33, null, '油压', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (34, null, '套压', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (35, null, '回压', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (36, null, '井口温度', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (37, null, '含水率', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (38, null, '运行状态', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (39, null, '启停控制', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (40, null, 'A相电流', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (41, null, 'B相电流', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (42, null, 'C相电流', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (43, null, 'A相电压', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (44, null, 'B相电压', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (45, null, 'C相电压', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (46, null, '有功功耗', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (47, null, '无功功耗', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (48, null, '有功功率', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (49, null, '无功功率', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (50, null, '反向功率', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (51, null, '功率因数', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (52, null, '变频设置频率', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (53, null, '变频运行频率', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (54, null, '螺杆泵转速', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (55, null, '螺杆泵扭矩', null, 3, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (56, null, '启停控制', null, 4, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (57, null, '变频设置频率', null, 4, null, '0,0,0');

/*==============================================================*/
/* 初始化TBL_ALARM_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'alarmunit1', 'A11-抽油机报警单元', 'A11-抽油机', null);

insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (2, 'alarmunit2', '螺杆泵A11报警单元', 'A11-螺杆泵', null);

/*==============================================================*/
/* 初始化TBL_ALARM_ITEM2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (1, 1, null, '上线', 'online', 0, 1.000, null, null, null, 60, 300, 1, 3, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (2, 1, null, '离线', 'offline', 0, 0.000, null, null, null, 60, 100, 1, 3, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (3, 1, null, '运行', 'run', 0, 1.000, null, null, null, 60, 300, 1, 6, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (4, 1, null, '停抽', 'stop', 0, 0.000, null, null, null, 60, 100, 1, 6, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (5, 1, null, '抽喷', '1201', 0, 1201.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (6, 1, null, '正常', '1202', 0, 1202.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (7, 1, null, '充满不足', '1203', 0, 1203.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (8, 1, null, '供液不足', '1204', 0, 1204.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (9, 1, null, '供液极差', '1205', 0, 1205.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (10, 1, null, '抽空', '1206', 0, 1206.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (11, 1, null, '泵下堵', '1207', 0, 1207.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (12, 1, null, '气锁', '1208', 0, 1208.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (13, 1, null, '气影响', '1209', 0, 1209.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (14, 1, null, '间隙漏', '1210', 0, 1210.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (15, 1, null, '油管漏', '1211', 0, 1211.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (16, 1, null, '游动凡尔漏失', '1212', 0, 1212.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (17, 1, null, '固定凡尔漏失', '1213', 0, 1213.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (18, 1, null, '双凡尔漏失', '1214', 0, 1214.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (19, 1, null, '游动凡尔失灵/油管漏', '1215', 0, 1215.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (20, 1, null, '固定凡尔失灵', '1216', 0, 1216.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (21, 1, null, '双凡尔失灵', '1217', 0, 1217.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (22, 1, null, '上死点别、碰', '1218', 0, 1218.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (23, 1, null, '碰泵', '1219', 0, 1219.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (24, 1, null, '活塞/底部断脱/未入工作筒', '1220', 0, 1220.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (25, 1, null, '柱塞脱出工作筒', '1221', 0, 1221.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (26, 1, null, '杆断脱', '1222', 0, 1222.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (27, 1, null, '杆(泵)卡', '1223', 0, 1223.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (28, 1, null, '结蜡', '1224', 0, 1224.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (29, 1, null, '严重结蜡', '1225', 0, 1225.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (30, 1, null, '出砂', '1226', 0, 1226.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (31, 1, null, '严重出砂', '1227', 0, 1227.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (32, 1, null, '惯性载荷大', '1230', 0, 1230.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (33, 1, null, '应力超标', '1231', 0, 1231.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (34, 1, null, '采集异常', '1232', 0, 1232.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (35, 2, null, '上线', 'online', 0, 1.000, null, null, null, 60, 300, 1, 3, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (36, 2, null, '离线', 'offline', 0, 0.000, null, null, null, 60, 100, 1, 3, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (37, 2, null, '运行', 'run', 0, 1.000, null, null, null, 60, 300, 1, 6, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (38, 2, null, '停抽', 'stop', 0, 0.000, null, null, null, 60, 100, 1, 6, 0, 0, 0);

/*==============================================================*/
/* 初始化TBL_DISPLAY_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK)
values (1, 'unit1', '抽油机A11显示单元', 'A11-抽油机', 1, null);

insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK)
values (2, 'unit2', '螺杆泵A11显示单元', 'A11-螺杆泵', 2, null);

/*==============================================================*/
/* 初始化TBL_DISPLAY_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (1, null, '油压', 'c_yy', 1, 73, null, 1, 0, '{"sort":3,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"2500ff"}', '{"sort":3,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":true,"color":"2500ff"}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (2, null, '在线时间', 'CommTime', 1, 1, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (3, null, '套压', 'c_ty', 1, 74, null, 1, 0, '{"sort":4,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"374140"}', '{"sort":4,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":true,"color":"374140"}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (4, null, '在线时率', 'CommTimeEfficiency', 1, 2, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (5, null, '启停控制', 'c_qtkz', 1, 1, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (6, null, '回压', 'c_hy', 1, 75, null, 1, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (7, null, '变频设置频率', 'c_bpszpl', 1, 2, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (8, null, '在线区间', 'CommRange', 1, 3, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (9, null, '井口温度', 'c_jkwd', 1, 76, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (10, null, '运行时间', 'RunTime', 1, 7, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (11, null, '运行状态', 'c_yxzt', 1, 4, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (12, null, '运行时率', 'RunTimeEfficiency', 1, 8, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (13, null, '含水率', 'c_hsl', 1, 79, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (14, null, '运行区间', 'RunRange', 1, 9, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (15, null, '光杆温度', 'c_ggwd', 1, 77, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (16, null, '工况', 'ResultName', 1, 10, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (17, null, '盘根盒温度', 'c_pghwd', 1, 78, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (18, null, '最大载荷', 'FMax', 1, 20, null, null, 1, '{"sort":1,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"ae1919"}', '{"sort":1,"color":"ae1919","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (19, null, 'A相电流', 'c_Axdl', 1, 82, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (20, null, '最小载荷', 'FMin', 1, 23, null, null, 1, '{"sort":2,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"33a91f"}', '{"sort":2,"color":"33a91f","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (21, null, 'B相电流', 'c_Bxdl', 1, 83, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (22, null, '柱塞冲程', 'PlungerStroke', 1, 15, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (23, null, 'C相电流', 'c_Cxdl', 1, 84, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (24, null, '柱塞有效冲程', 'AvailablePlungerStroke', 1, 18, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (25, null, 'A相电压', 'c_Axdy', 1, 85, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (26, null, 'B相电压', 'c_Bxdy', 1, 86, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (27, null, '抽空柱塞有效冲程', 'NoLiquidAvailablePlungerStroke', 1, 27, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (28, null, '充满系数', 'FullnessCoefficient', 1, 21, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (29, null, 'C相电压', 'c_Cxdy', 1, 87, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (30, null, '抽空充满系数', 'NoLiquidFullnessCoefficient', 1, 24, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (31, null, '有功功耗', 'c_yggh', 1, 88, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (32, null, '理论上载荷', 'UpperLoadLine', 1, 26, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (33, null, '无功功耗', 'c_wggh', 1, 89, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (34, null, '理论下载荷', 'LowerLoadLine', 1, 29, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (35, null, '有功功率', 'c_yggl', 1, 91, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (36, null, '理论排量', 'TheoreticalProduction', 1, 25, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (37, null, '无功功率', 'c_wggl', 1, 92, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (38, null, '产液量', 'LiquidVolumetricProduction', 1, 13, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (39, null, '功率因数', 'c_glys', 1, 93, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (40, null, '产油量', 'OilVolumetricProduction', 1, 16, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (41, null, '变频设置频率', 'c_bpszpl', 1, 94, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (42, null, '产水量', 'WaterVolumetricProduction', 1, 19, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (43, null, '变频运行频率', 'c_bpyxpl', 1, 95, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (44, null, '柱塞有效冲程计算产量', 'AvailablePlungerStrokeProd_v', 1, 28, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (45, null, '冲次', 'c_cc', 1, 17, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (46, null, '冲程', 'c_cc1', 1, 14, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (47, null, '泵间隙漏失量', 'PumpClearanceleakProd_v', 1, 30, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (48, null, '累计产液量', 'LiquidVolumetricProduction_l', 1, 22, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (49, null, '有功功率', 'AverageWatt', 1, 44, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (50, null, '光杆功率', 'PolishRodPower', 1, 47, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (51, null, '水功率', 'WaterPower', 1, 41, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (52, null, '地面效率', 'SurfaceSystemEfficiency', 1, 43, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (53, null, '井下效率', 'WellDownSystemEfficiency', 1, 46, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (54, null, '系统效率', 'SystemEfficiency', 1, 40, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (55, null, '功图面积', 'Area', 1, 42, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (56, null, '吨液百米耗电量', 'EnergyPer100mLift', 1, 45, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (57, null, '抽油杆伸长量', 'RodFlexLength', 1, 31, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (58, null, '油管伸缩量', 'TubingFlexLength', 1, 32, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (59, null, '惯性载荷增量', 'InertiaLength', 1, 33, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (60, null, '冲程损失系数', 'PumpEff1', 1, 34, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (61, null, '充满系数', 'PumpEff2', 1, 35, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (62, null, '间隙漏失系数', 'PumpEff3', 1, 36, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (63, null, '液体收缩系数', 'PumpEff4', 1, 37, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (64, null, '总泵效', 'PumpEff', 1, 38, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (65, null, '泵入口压力', 'PumpIntakeP', 1, 61, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (66, null, '泵入口温度', 'PumpIntakeT', 1, 62, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (67, null, '泵入口就地气液比', 'PumpIntakeGOL', 1, 63, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (68, null, '泵入口粘度', 'PumpIntakeVisl', 1, 64, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (69, null, '泵入口原油体积系数', 'PumpIntakeBo', 1, 65, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (70, null, '泵出口压力', 'PumpOutletP', 1, 67, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (71, null, '泵出口温度', 'PumpOutletT', 1, 68, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (72, null, '泵出口就地气液比', 'PumpOutletGOL', 1, 69, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (73, null, '泵出口粘度', 'PumpOutletVisl', 1, 70, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (74, null, '泵出口原油体积系数', 'PumpOutletBo', 1, 71, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (75, null, '上冲程最大电流', 'UpStrokeIMax', 1, 50, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (76, null, '下冲程最大电流', 'DownStrokeIMax', 1, 51, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (77, null, '上冲程最大功率', 'UpStrokeWattMax', 1, 53, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (78, null, '下冲程最大功率', 'DownStrokeWattMax', 1, 54, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (79, null, '电流平衡度', 'IDegreeBalance', 1, 49, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (80, null, '功率平衡度', 'WattDegreeBalance', 1, 52, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (81, null, '移动距离', 'DeltaRadius', 1, 55, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (82, null, '反演液面校正值', 'LevelCorrectValue', 1, 59, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (83, null, '动液面', 'InverProducingfluidLevel', 1, 58, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (84, null, '日用电量', 'TodayKWattH', 1, 56, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (85, null, '油压', 'c_yy', 2, 37, null, 1, 0, '{"sort":1,"color":"ff0000","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":1,"color":"ff0000","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (86, null, '套压', 'c_ty', 2, 38, null, 1, 0, '{"sort":2,"color":"0008ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":2,"color":"0008ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (87, null, '回压', 'c_hy', 2, 39, null, 1, 0, '{"sort":3,"color":"0c3203","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":3,"color":"0c3203","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (88, null, '井口温度', 'c_jkwd', 2, 40, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (89, null, '含水率', 'c_hsl', 2, 41, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (90, null, '运行状态', 'c_yxzt', 2, 4, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (91, null, 'A相电流', 'c_Axdl', 2, 43, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (92, null, 'B相电流', 'c_Bxdl', 2, 44, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (93, null, 'C相电流', 'c_Cxdl', 2, 45, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (94, null, 'A相电压', 'c_Axdy', 2, 46, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (95, null, 'B相电压', 'c_Bxdy', 2, 47, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (96, null, 'C相电压', 'c_Cxdy', 2, 48, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (97, null, '有功功耗', 'c_yggh', 2, 49, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (98, null, '无功功耗', 'c_wggh', 2, 50, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (99, null, '有功功率', 'c_yggl', 2, 52, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (100, null, '无功功率', 'c_wggl', 2, 53, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (101, null, '功率因数', 'c_glys', 2, 54, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (102, null, '变频设置频率', 'c_bpszpl', 2, 55, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (103, null, '变频运行频率', 'c_bpyxpl', 2, 56, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (104, null, '螺杆泵转速', 'c_lgbzs', 2, 10, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (105, null, '螺杆泵扭矩', 'c_lgbnj', 2, 11, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (106, null, '在线时间', 'CommTime', 2, 1, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (107, null, '在线时率', 'CommTimeEfficiency', 2, 2, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (108, null, '在线区间', 'CommRange', 2, 3, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (109, null, '运行时间', 'RunTime', 2, 7, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (110, null, '运行时率', 'RunTimeEfficiency', 2, 8, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (111, null, '运行区间', 'RunRange', 2, 9, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (112, null, '理论排量', 'TheoreticalProduction', 2, 16, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (113, null, '产液量', 'LiquidVolumetricProduction', 2, 13, null, null, 1, '{"sort":4,"color":"ff0000","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":4,"color":"ff0000","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (114, null, '产油量', 'OilVolumetricProduction', 2, 14, null, null, 1, '{"sort":5,"color":"0008ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":5,"color":"0008ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (115, null, '产水量', 'WaterVolumetricProduction', 2, 15, null, null, 1, '{"sort":6,"color":"0c3203","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}', '{"sort":6,"color":"0c3203","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (116, null, '累计产液量', 'LiquidVolumetricProduction_l', 2, 17, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (117, null, '有功功率', 'AverageWatt', 2, 23, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (118, null, '水功率', 'WaterPower', 2, 24, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (119, null, '系统效率', 'SystemEfficiency', 2, 22, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (120, null, '容积效率', 'PumpEff1', 2, 20, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (121, null, '液体收缩系数', 'PumpEff2', 2, 21, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (122, null, '总泵效', 'PumpEff', 2, 19, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (123, null, '泵入口压力', 'PumpIntakeP', 2, 25, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (124, null, '泵入口温度', 'PumpIntakeT', 2, 26, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (125, null, '泵入口就地气液比', 'PumpIntakeGOL', 2, 27, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (126, null, '泵入口粘度', 'PumpIntakeVisl', 2, 28, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (127, null, '泵入口原油体积系数', 'PumpIntakeBo', 2, 29, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (128, null, '泵出口压力', 'PumpOutletP', 2, 31, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (129, null, '泵出口温度', 'PumpOutletT', 2, 32, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (130, null, '泵出口就地气液比', 'PumpOutletGOL', 2, 33, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (131, null, '泵出口粘度', 'PumpOutletVisl', 2, 34, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (132, null, '泵出口原油体积系数', 'PumpOutletBo', 2, 35, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (133, null, '日用电量', 'TodayKWattH', 2, 18, null, null, 1, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (134, null, '启停控制', 'c_qtkz', 2, null, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (135, null, '变频设置频率', 'c_bpszpl', 2, null, null, null, 2, null, null, '0,0,0');

/*==============================================================*/
/* 初始化TBL_REPORT_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (1, 'unit1', '抽油机井报表单元一', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 0, 1);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (2, 'unit2', '煤层气井报表单元一', 'CBMWell_heichao', 'CBMWell_heichaoProductionReport', 0, 2);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (3, 'unit3', '螺杆泵井报表单元一', 'oilWell_ScrewPump', 'oilWell_ScrewPumpProductionReoirt', 1, 1);
/*==============================================================*/
/* 初始化TBL_REPORT_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (1, null, '井名', 'WellName', 2, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (2, null, '日期', 'CalDate', 3, null, '0,0,0', 3, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (3, null, '在线时间', 'CommTime', 4, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (4, null, '在线时率', 'CommTimeEfficiency', 6, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (5, null, '在线区间', 'CommRange', 5, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (6, null, '运行时间', 'RunTime', 7, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (7, null, '运行时率', 'RunTimeEfficiency', 9, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (8, null, '运行区间', 'RunRange', 8, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (9, null, '工况', 'ResultName', 10, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (10, null, '优化建议', 'OptimizationSuggestion', 11, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (11, null, '充满系数', 'FullnessCoefficient', 16, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (12, null, '产液量', 'LiquidWeightProduction', 12, null, '0,0,0', 2, 1, 0, 0, null, 0, '{"sort":1,"color":"d72953","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (13, null, '产油量', 'OilWeightProduction', 13, null, '0,0,0', 2, 1, 0, 0, null, 0, '{"sort":2,"color":"e79819","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (14, null, '产水量', 'WaterWeightProduction', 14, null, '0,0,0', 2, 1, 0, 0, null, 0, '{"sort":3,"color":"2ebdc0","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (15, null, '重量含水率', 'WeightWaterCut', 15, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (16, null, '地面效率', 'SurfaceSystemEfficiency', 21, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (17, null, '井下效率', 'WellDownSystemEfficiency', 22, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (18, null, '系统效率', 'SystemEfficiency', 20, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (19, null, '吨液百米耗电量', 'EnergyPer100mLift', 23, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (20, null, '电流平衡度', 'IDegreeBalance', 18, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (21, null, '功率平衡度', 'WattDegreeBalance', 17, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (22, null, '移动距离', 'DeltaRadius', 19, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (23, null, '日用电量', 'TodayKWattH', 24, null, '0,0,0', 2, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (24, null, '备注', 'Remark', 25, null, '0,0,0', 1, 1, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (25, null, '井名', 'WellName', 2, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (26, null, '日期', 'CalDate', 3, null, '0,0,0', 3, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (27, null, '在线时间', 'CommTime', 4, null, '0,0,0', 2, 1, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (28, null, '在线时率', 'CommTimeEfficiency', 6, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (29, null, '在线区间', 'CommRange', 5, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (30, null, '运行时间', 'RunTime', 7, null, '0,0,0', 2, 1, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (31, null, '运行时率', 'RunTimeEfficiency', 9, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (32, null, '运行区间', 'RunRange', 8, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (33, null, '工况', 'ResultName', 10, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (34, null, '优化建议', 'OptimizationSuggestion', 11, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (35, null, '充满系数', 'FullnessCoefficient', 16, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (36, null, '日产液量', 'LiquidWeightProduction', 12, null, '0,0,0', 2, 1, 1, 1, 1, 1, '{"sort":1,"color":"d72953","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (37, null, '日产油量', 'OilWeightProduction', 13, null, '0,0,0', 2, 1, 1, 1, 1, 1, '{"sort":2,"color":"e79819","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (38, null, '日产水量', 'WaterWeightProduction', 14, null, '0,0,0', 2, 1, 1, 1, 1, 1, '{"sort":3,"color":"2ebdc0","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (39, null, '重量含水率', 'WeightWaterCut', 15, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (40, null, '地面效率', 'SurfaceSystemEfficiency', 21, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (41, null, '井下效率', 'WellDownSystemEfficiency', 22, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (42, null, '系统效率', 'SystemEfficiency', 20, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (43, null, '吨液百米耗电量', 'EnergyPer100mLift', 23, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (44, null, '电流平衡度', 'IDegreeBalance', 18, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (45, null, '功率平衡度', 'WattDegreeBalance', 17, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (45, null, '移动距离', 'DeltaRadius', 19, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (47, null, '日用电量', 'TodayKWattH', 24, null, '0,0,0', 2, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (48, null, '备注', 'Remark', 25, null, '0,0,0', 1, 1, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (49, null, '日期', 'CalDate', 1, null, '0,0,0', 3, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (50, null, '运行时间', 'RunTime', 2, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (51, null, '冲程', 'Stroke', 4, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (52, null, '冲次', 'SPM', 3, null, '0,0,0', 2, 2, null, null, null, 0, '{"sort":1,"color":"ecd211","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (53, null, '日产水量', 'WaterVolumetricProduction', 7, null, '0,0,0', 2, 2, null, null, null, 0, '{"sort":2,"color":"00d8ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (54, null, '日产气量', 'GasVolumetricProduction', 6, null, '0,0,0', 2, 2, null, null, null, 0, '{"sort":3,"color":"1424f1","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (55, null, '累计产气量', 'TotalGasVolumetricProduction', 10, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (56, null, '累计产水量', 'TotalWaterVolumetricProduction', 11, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (57, null, '动液面', 'ProducingfluidLevel', 5, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (58, null, '套压', 'CasingPressure', 9, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (59, null, '井底压力', 'BottomHolePressure', 8, null, '0,0,0', 2, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (60, null, '备注', 'Remark', 12, null, '0,0,0', 1, 2, null, null, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (61, null, '井名', 'WellName', 2, null, '0,0,0', 1, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (62, null, '日期', 'CalDate', 1, null, '0,0,0', 3, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (63, null, '运行时间', 'RunTime', 3, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (64, null, '冲程', 'Stroke', 5, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (65, null, '冲次', 'SPM', 4, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (66, null, '日产水量', 'WaterVolumetricProduction', 8, null, '0,0,0', 2, 2, 1, 1, 1, 1, '{"sort":1,"color":"00d8ff","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (67, null, '日产气量', 'GasVolumetricProduction', 7, null, '0,0,0', 2, 2, 1, 1, 1, 1, '{"sort":2,"color":"1424f1","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (68, null, '累计产气量', 'TotalGasVolumetricProduction', 11, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (69, null, '累计产水量', 'TotalWaterVolumetricProduction', 12, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (70, null, '动液面', 'ProducingfluidLevel', 6, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (71, null, '套压', 'CasingPressure', 10, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (72, null, '井底压力', 'BottomHolePressure', 9, null, '0,0,0', 2, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (73, null, '备注', 'Remark', 13, null, '0,0,0', 1, 2, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (74, null, '井名', 'WellName', 2, null, '0,0,0', 1, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (75, null, '日期', 'CalDate', 3, null, '0,0,0', 3, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (76, null, '在线时间', 'CommTime', 4, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (77, null, '在线时率', 'CommTimeEfficiency', 6, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (78, null, '在线区间', 'CommRange', 5, null, '0,0,0', 1, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (79, null, '运行时间', 'RunTime', 7, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (80, null, '运行时率', 'RunTimeEfficiency', 9, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (81, null, '运行区间', 'RunRange', 8, null, '0,0,0', 1, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (82, null, '转速', 'RPM', 14, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (83, null, '产液量', 'LiquidWeightProduction', 10, null, '0,0,0', 2, 3, 0, 0, null, 0, '{"sort":1,"color":"e40c54","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (84, null, '产油量', 'OilWeightProduction', 11, null, '0,0,0', 2, 3, 0, 0, null, 0, '{"sort":2,"color":"d7bb14","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (85, null, '产水量', 'WaterWeightProduction', 12, null, '0,0,0', 2, 3, 0, 0, null, 0, '{"sort":3,"color":"1dbfb4","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (86, null, '重量含水率', 'WeightWaterCut', 13, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (87, null, '系统效率', 'SystemEfficiency', 15, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (88, null, '吨液百米耗电量', 'EnergyPer100mLift', 16, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (89, null, '日用电量', 'TodayKWattH', 17, null, '0,0,0', 2, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (90, null, '备注', 'Remark', 18, null, '0,0,0', 1, 3, 0, 0, null, 0, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (91, null, '井名', 'WellName', 2, null, '0,0,0', 1, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (92, null, '日期', 'CalDate', 3, null, '0,0,0', 3, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (93, null, '在线时间', 'CommTime', 4, null, '0,0,0', 2, 3, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (94, null, '在线时率', 'CommTimeEfficiency', 6, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (95, null, '在线区间', 'CommRange', 5, null, '0,0,0', 1, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (96, null, '运行时间', 'RunTime', 7, null, '0,0,0', 2, 3, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (97, null, '运行时率', 'RunTimeEfficiency', 9, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (98, null, '运行区间', 'RunRange', 8, null, '0,0,0', 1, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (99, null, '转速', 'RPM', 14, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (100, null, '日产液量', 'LiquidWeightProduction', 10, null, '0,0,0', 2, 3, 1, 1, 1, 1, '{"sort":1,"color":"e40c54","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (101, null, '日产油量', 'OilWeightProduction', 11, null, '0,0,0', 2, 3, 1, 1, 1, 1, '{"sort":2,"color":"d7bb14","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (102, null, '日产水量', 'WaterWeightProduction', 12, null, '0,0,0', 2, 3, 1, 1, 1, 1, '{"sort":3,"color":"1dbfb4","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (103, null, '重量含水率', 'WeightWaterCut', 13, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (104, null, '系统效率', 'SystemEfficiency', 15, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (105, null, '吨液百米耗电量', 'EnergyPer100mLift', 16, null, '0,0,0', 2, 3, 0, 0, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (106, null, '日用电量', 'TodayKWattH', 17, null, '0,0,0', 2, 3, 1, 1, null, 1, null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE, REPORTCURVECONF)
values (107, null, '备注', 'Remark', 18, null, '0,0,0', 1, 3, 0, 0, null, 1, null);


/*==============================================================*/
/* 初始化TBL_PROTOCOLINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (1, '抽油机A11采控实例', 'instance1', 'modbus-tcp', 'modbus-tcp', 1, 'AA01', '0D', 'AA01', '0D', 100, 1, 0, 1, 0, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (2, '螺杆泵A11采控实例', 'instance2', 'modbus-tcp', 'modbus-tcp', 1, 'AA01', '0D', 'AA01', '0D', 100, 2, 1, 1, 0, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (3, '抽油机A11RPC实例', 'instance3', 'private-rpc', 'private-rpc', 1, null, null, null, null, 100, 1, 0, 2, 0, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (4, '抽油机MQTT实例', 'instance4', 'private-mqtt', 'private-mqtt', 0, null, null, null, null, 100, 1, 0, 3, 0, 1);


/*==============================================================*/
/* 初始化tbl_protocolalarminstance数据                                          */
/*==============================================================*/
insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, DEVICETYPE, SORT)
values (1, '抽油机A11报警实例', 'alarminstance1', 1, 0, 1);

insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, DEVICETYPE, SORT)
values (2, '螺杆泵A11报警实例', 'alarminstance2', 2, 1, 1);

/*==============================================================*/
/* 初始化tbl_protocoldisplayinstance数据                                          */
/*==============================================================*/
insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, DEVICETYPE, SORT)
values (1, '抽油机A11显示实例', 'displayinstance1', 1, 0, 1);

insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, DEVICETYPE, SORT)
values (2, '螺杆泵A11显示实例', 'displayinstance2', 2, 1, 1);

/*==============================================================*/
/* 初始化TBL_PROTOCOLREPORTINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (1, '抽油机井报表实例一', 'reportinstance1', 0, 1, 1);

insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (2, '螺杆泵井报表实例一', 'reportinstance21', 1, 1, 3);

insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (3, '煤层气井报表实例', 'reportinstance41', 0, 2, 2);