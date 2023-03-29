/*==============================================================*/
/* 初始化TBL_ACQ_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', '抽油机RTU2.0', 'RTU2.0', '临县中澳');

/*==============================================================*/
/* 初始化TBL_ACQ_GROUP_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '采集组', 60, 60, 'RTU2.0', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '控制组', 0, 0, 'RTU2.0', 1, null);

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
values (1, null, '井下压力计-压力', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (2, null, '动液面', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (3, null, '井口套压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (4, null, '系统压力', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (5, null, '产气量累计', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (6, null, '产气量瞬时', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (7, null, '气体温度', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (8, null, '产水量累计', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (9, null, '产水量瞬时', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (10, null, '冲次', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (11, null, '气体流量计通讯状态', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (12, null, '水流量计通讯状态', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (13, null, '井下压力计/液面仪通讯状态', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (14, null, '变频器通讯状态', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (15, null, '变频器设置频率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (16, null, '变频器运行频率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (17, null, '变频器故障状态', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (18, null, '变频器输出电流', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (19, null, '变频器输出电压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (20, null, '变频器厂家代码', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (21, null, '变频器状态字1', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (22, null, '变频器状态字2', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (23, null, '母线电压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (24, null, '修正后井底流压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (25, null, '计算液柱高度', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (26, null, '计算近1小时液柱下降速度', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (27, null, '排采模式', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (28, null, '自动排采状态', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (29, null, '自动排采-最小频率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (30, null, '自动排采-最大频率', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (31, null, '最大步长幅度限制', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (32, null, '最短调整时间间隔', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (33, null, '自动重启延时', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (34, null, '自动重启次数', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (35, null, '井底流压波动报警值', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (36, null, '定降液-目标定降（每日）', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (37, null, '定降液-液柱低停机值', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (38, null, '定降液-液柱低报警值', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (39, null, '定降液-液柱重启值', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (40, null, '定降液-液柱高报警值', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (41, null, '定流压-目标流压', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (42, null, '定流压-流压低停机值', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (43, null, '定流压-流压低报警值', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (44, null, '定流压-流压重启值', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (45, null, '定流压-流压高报警值', null, 1, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (46, null, '煤层顶板深', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (47, null, '压力计安装深度', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (48, null, '启停控制', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (49, null, '变频器设置频率', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (50, null, '排采模式', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (51, null, '自动排采状态', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (52, null, '自动排采-最小频率', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (53, null, '自动排采-最大频率', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (54, null, '最大步长幅度限制', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (55, null, '最短调整时间间隔', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (56, null, '自动重启延时', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (57, null, '自动重启次数', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (58, null, '井底流压波动报警值', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (59, null, '定降液-目标定降（每日）', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (60, null, '定降液-液柱低停机值', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (61, null, '定降液-液柱低报警值', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (62, null, '定降液-液柱重启值', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (63, null, '定降液-液柱高报警值', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (64, null, '定流压-目标流压', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (65, null, '定流压-流压低停机值', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (66, null, '定流压-流压低报警值', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (67, null, '定流压-流压重启值', null, 2, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX)
values (68, null, '定流压-流压高报警值', null, 2, null, '0,0,0');

/*==============================================================*/
/* 初始化TBL_PROTOCOLINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, DEVICETYPE, SORT, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX)
values (1, 'RTU2.0实例', 'instance1', 'modbus-rtu', 'modbus-rtu', 1, 'CC01', '0C', 'CC01', '0C', 100, 1, 0, 1, 1, 1);

/*==============================================================*/
/* 初始化TBL_ALARM_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'alarmunit1', 'RTU2.0报警单元', 'RTU2.0', null);

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

/*==============================================================*/
/* 初始化tbl_protocolalarminstance数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLALARMINSTANCE (ID, NAME, CODE, ALARMUNITID, DEVICETYPE, SORT)
values (1, 'RTU2.0报警实例', 'alarminstance1', 1, 0, 1);

/*==============================================================*/
/* 初始化TBL_DISPLAY_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, REMARK)
values (1, 'unit1', 'RTU2.0显示单元', 'RTU2.0', 1, null);

/*==============================================================*/
/* 初始化TBL_DISPLAY_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (1, null, '井下压力计-压力', 'c_jxyljyl', 1, 1, null, null, 1, 1, 'c700af', 'c700af', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (2, null, '动液面', 'c_dym', 1, null, null, null, null, 2, '00ff00', '00ff00', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (3, null, '煤层顶板深', 'c_mcdbs', 1, 6, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (4, null, '井口套压', 'c_jkty', 1, 2, null, null, 2, 2, 'b47832', 'b47832', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (5, null, '压力计安装深度', 'c_yljazsd', 1, 7, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (6, null, '系统压力', 'c_xtyl', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (7, null, '启停控制', 'c_qtkz', 1, 1, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (8, null, '产气量累计', 'c_cqllj', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (9, null, '变频器设置频率', 'c_bpqszpl', 1, 2, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (10, null, '产气量瞬时', 'c_cqlss', 1, 3, null, null, 3, 3, 'ff0000', 'ff0000', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (11, null, '排采模式', 'c_pcms', 1, 3, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (12, null, '气体温度', 'c_qtwd', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (13, null, '产水量累计', 'c_csllj', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (14, null, '自动排采状态', 'c_zdpczt', 1, 4, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (15, null, '自动排采-最小频率', 'c_zdpczxpl', 1, 8, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (16, null, '产水量瞬时', 'c_cslss', 1, 4, null, null, 4, null, '0400ff', '0400ff', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (17, null, '自动排采-最大频率', 'c_zdpczdpl', 1, 9, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (18, null, '最大步长幅度限制', 'c_zdbcfdxz', 1, 10, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (19, null, '冲次', 'c_cc', 1, 7, null, null, 7, 7, '78a08c', '78a08c', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (20, null, '变频器运行频率', 'c_bpqyxpl', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (21, null, '最短调整时间间隔', 'c_zddzsjjg', 1, 11, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (22, null, '自动重启延时', 'c_zdzqys', 1, 12, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (23, null, '变频器输出电流', 'c_bpqscdl', 1, 5, null, null, 5, 5, 'dbda00', 'dbda00', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (24, null, '变频器输出电压', 'c_bpqscdy', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (25, null, '自动重启次数', 'c_zdzqcs', 1, 13, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (26, null, '井底流压波动报警值', 'c_jdlybdbjz', 1, 14, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (27, null, '变频器状态字1', 'c_bpqztz1', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (28, null, '定降液-目标定降（每日）', 'c_djymbdjmr', 1, 5, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (29, null, '母线电压', 'c_mxdy', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (30, null, '定降液-液柱低停机值', 'c_djyyzdtjz', 1, 15, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (31, null, '修正后井底流压', 'c_xzhjdly', 1, 6, null, null, 6, 6, '27701a', '27701a', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (32, null, '定降液-液柱低报警值', 'c_djyyzdbjz', 1, 16, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (33, null, '计算液柱高度', 'c_jsyzgd', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (34, null, '定降液-液柱重启值', 'c_djyyzzqz', 1, 17, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (35, null, '计算近1小时液柱下降速度', 'c_jsj1xsyzxjsd', 1, null, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, REALTIMECURVE, HISTORYCURVE, REALTIMECURVECOLOR, HISTORYCURVECOLOR, TYPE, MATRIX)
values (36, null, '定降液-液柱高报警值', 'c_djyyzgbjz', 1, 18, null, null, null, null, null, null, 2, '0,0,0');

/*==============================================================*/
/* 初始化tbl_protocoldisplayinstance数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLDISPLAYINSTANCE (ID, NAME, CODE, DISPLAYUNITID, DEVICETYPE, SORT)
values (1, 'RTU2.0显示实例', 'displayinstance1', 1, 0, 1);

/*==============================================================*/
/* 初始化TBL_REPORT_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, DEVICETYPE, SORT)
values (1, 'unit1', '煤层气井报表单元一', 'CBMWell_heichao', 'CBMWell_heichaoProductionReport', 0, 1);

/*==============================================================*/
/* 初始化TBL_REPORT_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (1, null, '日期', 'CalDate', 1, null, null, null, '0,0,0', 3, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (2, null, '运行时间', 'RunTime', 2, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (3, null, '冲程', 'Stroke', 4, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (4, null, '冲次', 'SPM', 3, null, 1, 'ecd211', '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (5, null, '日产水量', 'WaterVolumetricProduction', 7, null, 2, '00d8ff', '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (6, null, '日产气量', 'GasVolumetricProduction', 6, null, 3, '1424f1', '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (7, null, '累计产气量', 'TotalGasVolumetricProduction', 10, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (8, null, '累计产水量', 'TotalWaterVolumetricProduction', 11, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (9, null, '动液面', 'ProducingfluidLevel', 5, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (10, null, '套压', 'CasingPressure', 9, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (11, null, '井底压力', 'BottomHolePressure', 8, null, null, null, '0,0,0', 2, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (12, null, '备注', 'Remark', 12, null, null, null, '0,0,0', 1, 1, null, null, null, 0);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (13, null, '井名', 'WellName', 2, null, null, null, '0,0,0', 1, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (14, null, '日期', 'CalDate', 1, null, null, null, '0,0,0', 3, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (15, null, '运行时间', 'RunTime', 3, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (16, null, '冲程', 'Stroke', 5, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (17, null, '冲次', 'SPM', 4, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (18, null, '日产水量', 'WaterVolumetricProduction', 8, null, 1, '00d8ff', '0,0,0', 2, 1, 1, 1, 1, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (19, null, '日产气量', 'GasVolumetricProduction', 7, null, 2, '1424f1', '0,0,0', 2, 1, 1, 1, 1, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (20, null, '累计产气量', 'TotalGasVolumetricProduction', 11, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (21, null, '累计产水量', 'TotalWaterVolumetricProduction', 12, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (22, null, '动液面', 'ProducingfluidLevel', 6, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (23, null, '套压', 'CasingPressure', 10, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (24, null, '井底压力', 'BottomHolePressure', 9, null, null, null, '0,0,0', 2, 1, 0, 0, null, 1);

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, SORT, SHOWLEVEL, REPORTCURVE, REPORTCURVECOLOR, MATRIX, DATATYPE, UNITID, SUMSIGN, AVERAGESIGN, CURVESTATTYPE, REPORTTYPE)
values (25, null, '备注', 'Remark', 13, null, null, null, '0,0,0', 1, 1, 0, 0, null, 1);

/*==============================================================*/
/* 初始化TBL_PROTOCOLREPORTINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (1, '煤层气井报表实例', 'reportinstance1', 0, 1, 1);