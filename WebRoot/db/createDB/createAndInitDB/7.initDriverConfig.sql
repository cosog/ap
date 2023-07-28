/*==============================================================*/
/* 初始化TBL_PROTOCOL数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'A11-抽油机', 'protocol1', 0, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"油压","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"套压","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"井口温度","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"运行状态","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"停抽"},{"Value":1,"Meaning":"运行"}]},'
  ||'{"Title":"功图实测点数","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图采集时间","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"冲次","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"次/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"冲程","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-位移","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图数据-载荷","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-电流","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-功率","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive","Meaning":[]}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.NAME='A11-抽油机';  
  COMMIT;  
END;  
/

/*==============================================================*/
/* 初始化TBL_ACQ_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', '抽油机A11采集单元', 'A11-抽油机', null);

/*==============================================================*/
/* 初始化TBL_ACQ_GROUP_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '采集组', 60, 60, 'A11-抽油机', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '控制组', 0, 0, 'A11-抽油机', 1, null);

/*==============================================================*/
/* 初始化TBL_ACQ_GROUP2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, UNITID, MATRIX)
values (1, 1, 1, '0,0,0');

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, UNITID, MATRIX)
values (2, 2, 1, '0,0,0');

/*==============================================================*/
/* 初始化TBL_ACQ_ITEM2GROUP_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (1, null, '运行状态', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (2, null, '功图实测点数', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (3, null, '功图采集时间', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (4, null, '冲次', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (5, null, '冲程', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (6, null, '功图数据-位移', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (7, null, '功图数据-载荷', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (8, null, '功图数据-电流', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (9, null, '功图数据-功率', null, 1, null, '0,0,0');

/*==============================================================*/
/* 初始化TBL_ALARM_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'alarmunit1', 'A11-抽油机报警单元', 'A11-抽油机', null);

/*==============================================================*/
/* 初始化TBL_ALARM_ITEM2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (1, 1, null, '抽喷', '1201', 0, 1201.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (2, 1, null, '正常', '1202', 0, 1202.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (3, 1, null, '充满不足', '1203', 0, 1203.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (4, 1, null, '供液不足', '1204', 0, 1204.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (5, 1, null, '供液极差', '1205', 0, 1205.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (6, 1, null, '抽空', '1206', 0, 1206.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (7, 1, null, '泵下堵', '1207', 0, 1207.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (8, 1, null, '气锁', '1208', 0, 1208.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (9, 1, null, '气影响', '1209', 0, 1209.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (10, 1, null, '间隙漏', '1210', 0, 1210.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (11, 1, null, '油管漏', '1211', 0, 1211.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (12, 1, null, '游动凡尔漏失', '1212', 0, 1212.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (13, 1, null, '固定凡尔漏失', '1213', 0, 1213.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (14, 1, null, '双凡尔漏失', '1214', 0, 1214.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (15, 1, null, '游动凡尔失灵/油管漏', '1215', 0, 1215.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (16, 1, null, '固定凡尔失灵', '1216', 0, 1216.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (17, 1, null, '双凡尔失灵', '1217', 0, 1217.000, null, null, null, 60, 0, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (18, 1, null, '上死点别、碰', '1218', 0, 1218.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (19, 1, null, '碰泵', '1219', 0, 1219.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (20, 1, null, '活塞/底部断脱/未入工作筒', '1220', 0, 1220.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (21, 1, null, '柱塞脱出工作筒', '1221', 0, 1221.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (22, 1, null, '杆断脱', '1222', 0, 1222.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (23, 1, null, '杆(泵)卡', '1223', 0, 1223.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (24, 1, null, '结蜡', '1224', 0, 1224.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (25, 1, null, '严重结蜡', '1225', 0, 1225.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (26, 1, null, '出砂', '1226', 0, 1226.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (27, 1, null, '严重出砂', '1227', 0, 1227.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (28, 1, null, '惯性载荷大', '1230', 0, 1230.000, null, null, null, 60, 300, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (29, 1, null, '应力超标', '1231', 0, 1231.000, null, null, null, 60, 200, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (30, 1, null, '采集异常', '1232', 0, 1232.000, null, null, null, 60, 100, 1, 4, 0, 0, 0);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL)
values (31, 1, null, '停抽', 'stop', 0, 0.000, null, null, null, 60, 100, 1, 6, 0, 0, 0);

/*==============================================================*/
/* 初始化TBL_DISPLAY_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK)
values (1, 'unit1', '抽油机A11显示单元', 'A11-抽油机', 1, null);

/*==============================================================*/
/* 初始化TBL_DISPLAY_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (1, null, '运行状态', 'c_yxzt', 1, 4, null, null, 0, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (2, null, '冲次', 'c_cc', 1, 17, null, null, 0, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (3, null, '冲程', 'c_cc1', 1, 14, null, null, 0, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (4, null, '在线时间', 'CommTime', 1, 1, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (5, null, '在线时率', 'CommTimeEfficiency', 1, 2, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (6, null, '在线区间', 'CommRange', 1, 3, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (7, null, '运行时间', 'RunTime', 1, 7, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (8, null, '运行时率', 'RunTimeEfficiency', 1, 8, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (9, null, '运行区间', 'RunRange', 1, 9, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (10, null, '工况', 'ResultName', 1, 10, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (11, null, '最大载荷', 'FMax', 1, 20, null, null, 1, '0,0,0', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b824e6"}', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"b824e6"}');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (12, null, '最小载荷', 'FMin', 1, 23, null, null, 1, '0,0,0', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e25e1d"}', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e25e1d"}');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (13, null, '柱塞冲程', 'PlungerStroke', 1, 15, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (14, null, '柱塞有效冲程', 'AvailablePlungerStroke', 1, 18, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (15, null, '抽空柱塞有效冲程', 'NoLiquidAvailablePlungerStroke', 1, 27, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (16, null, '充满系数', 'FullnessCoefficient', 1, 21, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (17, null, '抽空充满系数', 'NoLiquidFullnessCoefficient', 1, 24, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (18, null, '理论上载荷', 'UpperLoadLine', 1, 26, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (19, null, '理论下载荷', 'LowerLoadLine', 1, 29, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (20, null, '理论排量', 'TheoreticalProduction', 1, 25, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (21, null, '产液量', 'LiquidVolumetricProduction', 1, 13, null, null, 1, '0,0,0', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (22, null, '产油量', 'OilVolumetricProduction', 1, 16, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (23, null, '产水量', 'WaterVolumetricProduction', 1, 19, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (24, null, '柱塞有效冲程计算产量', 'AvailablePlungerStrokeProd_v', 1, 28, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (25, null, '泵间隙漏失量', 'PumpClearanceleakProd_v', 1, 30, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (26, null, '累计产液量', 'LiquidVolumetricProduction_l', 1, 22, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (27, null, '有功功率', 'AverageWatt', 1, 44, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (28, null, '光杆功率', 'PolishRodPower', 1, 47, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (29, null, '水功率', 'WaterPower', 1, 41, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (30, null, '地面效率', 'SurfaceSystemEfficiency', 1, 43, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (31, null, '井下效率', 'WellDownSystemEfficiency', 1, 46, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (32, null, '系统效率', 'SystemEfficiency', 1, 40, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (33, null, '功图面积', 'Area', 1, 42, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (34, null, '吨液百米耗电量', 'EnergyPer100mLift', 1, 45, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (35, null, '抽油杆伸长量', 'RodFlexLength', 1, 31, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (36, null, '油管伸缩量', 'TubingFlexLength', 1, 32, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (37, null, '惯性载荷增量', 'InertiaLength', 1, 33, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (38, null, '冲程损失系数', 'PumpEff1', 1, 34, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (39, null, '充满系数', 'PumpEff2', 1, 35, null, null, 1, '0,0,0', '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d4bf24"}', '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d4bf24"}');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (40, null, '间隙漏失系数', 'PumpEff3', 1, 36, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (41, null, '液体收缩系数', 'PumpEff4', 1, 37, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (42, null, '总泵效', 'PumpEff', 1, 38, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (43, null, '泵入口压力', 'PumpIntakeP', 1, 61, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (44, null, '泵入口温度', 'PumpIntakeT', 1, 62, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (45, null, '泵入口就地气液比', 'PumpIntakeGOL', 1, 63, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (46, null, '泵入口粘度', 'PumpIntakeVisl', 1, 64, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (47, null, '泵入口原油体积系数', 'PumpIntakeBo', 1, 65, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (48, null, '泵出口压力', 'PumpOutletP', 1, 67, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (49, null, '泵出口温度', 'PumpOutletT', 1, 68, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (50, null, '泵出口就地气液比', 'PumpOutletGOL', 1, 69, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (51, null, '泵出口粘度', 'PumpOutletVisl', 1, 70, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (52, null, '泵出口原油体积系数', 'PumpOutletBo', 1, 71, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (53, null, '上冲程最大电流', 'UpStrokeIMax', 1, 50, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (54, null, '下冲程最大电流', 'DownStrokeIMax', 1, 51, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (55, null, '上冲程最大功率', 'UpStrokeWattMax', 1, 53, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (56, null, '下冲程最大功率', 'DownStrokeWattMax', 1, 54, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (57, null, '电流平衡度', 'IDegreeBalance', 1, 49, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (58, null, '功率平衡度', 'WattDegreeBalance', 1, 52, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (59, null, '移动距离', 'DeltaRadius', 1, 55, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (60, null, '液面校正差值', 'LevelDifferenceValue', 1, 59, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (61, null, '反演动液面', 'CalcProducingfluidLevel', 1, 58, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (62, null, '日用电量', 'TodayKWattH', 1, 56, null, null, 1, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (63, null, '原油密度', 'CrudeOilDensity', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (64, null, '水密度', 'WaterDensity', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (65, null, '天然气相对密度', 'NaturalGasRelativeDensity', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (66, null, '饱和压力', 'SaturationPressure', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (67, null, '油层中部深度', 'ReservoirDepth', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (68, null, '油层中部温度', 'ReservoirTemperature', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (69, null, '油压', 'TubingPressure', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (70, null, '套压', 'CasingPressure', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (71, null, '井口温度', 'WellHeadTemperature', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (72, null, '含水率', 'WaterCut', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (73, null, '生产气油比', 'ProductionGasOilRatio', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (74, null, '动液面', 'ProducingfluidLevel', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (75, null, '泵挂', 'PumpSettingDepth', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (76, null, '泵径', 'PumpBoreDiameter', 1, null, null, null, 3, '0,0,0', null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, MATRIX, REALTIMECURVECONF, HISTORYCURVECONF)
values (77, null, '反演液面校正值', 'LevelCorrectValue', 1, null, null, null, 3, '0,0,0', null, null);

/*==============================================================*/
/* 初始化TBL_REPORT_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (1, 'unit1', '抽油机井报表单元一', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 0, 1);

/*==============================================================*/
/* 初始化TBL_REPORT_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (1, null, '井名', 'WellName', 1, 2, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (2, null, '日期', 'CalDate', 1, 3, null, 0, 0, null, 3, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (3, null, '在线时间', 'CommTime', 1, 4, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (4, null, '在线时率', 'CommTimeEfficiency', 1, 6, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (5, null, '在线区间', 'CommRange', 1, 5, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (6, null, '运行时间', 'RunTime', 1, 7, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (7, null, '运行时率', 'RunTimeEfficiency', 1, 9, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (8, null, '运行区间', 'RunRange', 1, 8, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (9, null, '工况', 'ResultName', 1, 10, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (10, null, '优化建议', 'OptimizationSuggestion', 1, 11, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (11, null, '充满系数', 'FullnessCoefficient', 1, 16, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (12, null, '日产液量', 'LiquidWeightProduction', 1, 12, null, 0, 0, 1, 2, 1, '0,0,0', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d42626"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (13, null, '日产油量', 'OilWeightProduction', 1, 13, null, 0, 0, 1, 2, 1, '0,0,0', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"ebbc1a"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (14, null, '日产水量', 'WaterWeightProduction', 1, 14, null, 0, 0, 1, 2, 1, '0,0,0', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1beef3"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (15, null, '重量含水率', 'WeightWaterCut', 1, 15, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (16, null, '地面效率', 'SurfaceSystemEfficiency', 1, 24, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (17, null, '井下效率', 'WellDownSystemEfficiency', 1, 25, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (18, null, '系统效率', 'SystemEfficiency', 1, 23, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (19, null, '吨液百米耗电量', 'EnergyPer100mLift', 1, 26, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (20, null, '电流平衡度', 'IDegreeBalance', 1, 21, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (21, null, '功率平衡度', 'WattDegreeBalance', 1, 20, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (22, null, '移动距离', 'DeltaRadius', 1, 22, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (23, null, '日用电量', 'TodayKWattH', 1, 27, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (24, null, '泵挂', 'PumpSettingDepth', 1, 17, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (25, null, '动液面', 'ProducingfluidLevel', 1, 18, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (26, null, '沉没度', 'Submergence', 1, 19, null, 0, 0, null, 2, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (27, null, '备注', 'Remark', 1, 28, null, 0, 0, null, 1, 1, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (28, null, '井名', 'WellName', 1, 2, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (29, null, '日期', 'CalDate', 1, 3, null, null, null, null, 3, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (30, null, '在线时间', 'CommTime', 1, 4, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (31, null, '在线时率', 'CommTimeEfficiency', 1, 6, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (32, null, '在线区间', 'CommRange', 1, 5, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (33, null, '运行时间', 'RunTime', 1, 7, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (34, null, '运行时率', 'RunTimeEfficiency', 1, 9, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (35, null, '运行区间', 'RunRange', 1, 8, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (36, null, '工况', 'ResultName', 1, 10, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (37, null, '优化建议', 'OptimizationSuggestion', 1, 11, null, null, null, null, 1, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (38, null, '充满系数', 'FullnessCoefficient', 1, 16, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (39, null, '日产液量', 'LiquidWeightProduction', 1, 12, null, null, null, null, 2, 0, '0,0,0', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d12b2b"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (40, null, '日产油量', 'OilWeightProduction', 1, 13, null, null, null, null, 2, 0, '0,0,0', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e99314"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (41, null, '日产水量', 'WaterWeightProduction', 1, 14, null, null, null, null, 2, 0, '0,0,0', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"19c2eb"}');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (42, null, '重量含水率', 'WeightWaterCut', 1, 15, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (43, null, '地面效率', 'SurfaceSystemEfficiency', 1, 24, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (44, null, '井下效率', 'WellDownSystemEfficiency', 1, 25, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (45, null, '系统效率', 'SystemEfficiency', 1, 23, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (46, null, '吨液百米耗电量', 'EnergyPer100mLift', 1, 26, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (47, null, '电流平衡度', 'IDegreeBalance', 1, 21, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (48, null, '功率平衡度', 'WattDegreeBalance', 1, 20, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (49, null, '移动距离', 'DeltaRadius', 1, 22, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (50, null, '日用电量', 'TodayKWattH', 1, 27, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (51, null, '泵挂', 'PumpSettingDepth', 1, 17, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (52, null, '反演动液面', 'CalcProducingfluidLevel', 1, 18, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (53, null, '沉没度', 'Submergence', 1, 19, null, null, null, null, 2, 0, '0,0,0', null);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, REPORTCURVECONF)
values (54, null, '备注', 'Remark', 1, 28, null, null, null, null, 1, 0, '0,0,0', null);

/*==============================================================*/
/* 初始化TBL_PROTOCOLINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT)
values (1, '抽油机A11MODBUS实例', 'instance1', 'modbus-tcp', 'modbus-tcp', 1, 'BB01', '0B', 0, 1, 'BB01', '0B', 100, 1, 0, 1);

/*==============================================================*/
/* 初始化tbl_protocolalarminstance数据                                          */
/*==============================================================*/
insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, DEVICETYPE, SORT)
values (1, '抽油机A11报警实例', 'alarminstance1', 1, 0, 1);

/*==============================================================*/
/* 初始化tbl_protocoldisplayinstance数据                                          */
/*==============================================================*/
insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, DEVICETYPE, SORT)
values (1, '抽油机A11显示实例', 'displayinstance1', 1, 0, 1);

/*==============================================================*/
/* 初始化TBL_PROTOCOLREPORTINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (1, '抽油机井报表实例一', 'reportinstance1', 0, 1, 1);