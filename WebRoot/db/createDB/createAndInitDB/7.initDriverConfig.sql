/*==============================================================*/
/* 初始化TBL_PROTOCOL数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, 'RTU2.0', 'protocol1', 0, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"煤层顶板深","Addr":40019,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":0.1,"RWType":"rw","Unit":"米","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"压力计安装深度","Addr":40020,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":0.1,"RWType":"rw","Unit":"米","ResolutionMode":2,"AcqMode":"passive"},{"Title":"井下压力计-压力","Addr":40400,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":0.001,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"动液面","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"井口套压","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":3,"Quantity":1,"Ratio":0.001,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"系统压力","Addr":40406,"StoreDataType":"float32","IFDataType":"float32","Prec":3,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"KPa","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"产气量累计","Addr":40408,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"产气量瞬时","Addr":40410,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"气体温度","Addr":40412,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"产水量累计","Addr":40414,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"产水量瞬时","Addr":40416,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"冲次","Addr":40418,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"冲/min","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"井下压力计-温度","Addr":40444,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"气体流量计通讯状态","Addr":40446,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"无响应"},{"Value":2,"Meaning":"站号错误"},{"Value":3,"Meaning":"命令错误"},{"Value":4,"Meaning":"CRC校验错误"}]},{"Title":"水流量计通讯状态","Addr":40447,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"无响应"},{"Value":2,"Meaning":"站号错误"},{"Value":3,"Meaning":"命令错误"},{"Value":4,"Meaning":"CRC校验错误"}]},'||
  '{"Title":"井下压力计/液面仪通讯状态","Addr":40448,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"无响应"},{"Value":2,"Meaning":"站号错误"},{"Value":3,"Meaning":"命令错误"},{"Value":4,"Meaning":"CRC校验错误"}]},{"Title":"变频器通讯状态","Addr":40450,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"无响应"},{"Value":2,"Meaning":"站号错误"},{"Value":3,"Meaning":"命令错误"},{"Value":4,"Meaning":"CRC校验错误"}]},{"Title":"频率控制方式","Addr":40481,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"冲次控制"},{"Value":1,"Meaning":"频率控制"}]},'||
  '{"Title":"启停控制","Addr":40482,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"启动"},{"Value":2,"Meaning":"停止"}]},{"Title":"变频器设置频率","Addr":40483,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频器运行频率","Addr":40485,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频器故障状态","Addr":40487,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"变频器输出电流","Addr":40490,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"变频器输出电压","Addr":40492,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频器厂家代码","Addr":40494,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"无变频器或变频器无通讯功能"},{"Value":1,"Meaning":"英威腾"},{"Value":2,"Meaning":"科陆新能"},{"Value":3,"Meaning":"步科"},{"Value":4,"Meaning":"汇川"},{"Value":5,"Meaning":"信宇（开封信达）"},{"Value":6,"Meaning":"科陆新能660V"},{"Value":7,"Meaning":"日业电气"},{"Value":8,"Meaning":"普传科技"},{"Value":9,"Meaning":"INVERTER"},{"Value":10,"Meaning":"易驱"}]},{"Title":"变频器状态字1","Addr":40495,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"运行"},{"Value":3,"Meaning":"停机"},{"Value":4,"Meaning":"故障"}]},'||
  '{"Title":"变频器状态字2","Addr":40496,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"本地旋钮位置","Addr":40497,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"母线电压","Addr":40498,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"设定频率反馈","Addr":40500,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"10Hz对应冲次/转速预设值","Addr":40502,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"次/100转","ResolutionMode":2,"AcqMode":"passive"},{"Title":"50Hz对应冲次/转速预设值","Addr":40504,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"次/100转","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"冲次/转速设定值","Addr":40506,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"次/100转","ResolutionMode":2,"AcqMode":"passive"},{"Title":"修正后井底流压","Addr":40512,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":0.001,"RWType":"r","Unit":"Mpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"计算液柱高度","Addr":40514,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"计算近1小时液柱下降速度","Addr":40516,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m/小时","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"排采模式","Addr":40522,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"自动排采状态","Addr":40523,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"流压波动报警"},{"Value":2,"Meaning":"定降液-液柱低停机"},{"Value":3,"Meaning":"定降液-液柱低报警"},{"Value":4,"Meaning":"自动重启达到最大次数"},{"Value":5,"Meaning":"定降液-液柱高报警"},{"Value":6,"Meaning":"定流压-流压低停机"},{"Value":7,"Meaning":"定流压-流压低报警"},{"Value":8,"Meaning":"定流压-流压高报警"},{"Value":9,"Meaning":"自动重启中"},{"Value":10,"Meaning":"自动排采时连续4次执行最大频率"},{"Value":11,"Meaning":"自动排采时连续4次执行最小频率"}]},'||
  '{"Title":"自动排采-最小频率","Addr":40524,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"自动排采-最大频率","Addr":40525,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"最大步长幅度限制","Addr":40526,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"最短调整时间间隔","Addr":40527,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"秒","ResolutionMode":2,"AcqMode":"passive"},{"Title":"自动重启延时","Addr":40528,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"秒","ResolutionMode":2,"AcqMode":"passive"},{"Title":"自动重启次数","Addr":40529,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"次","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"井底流压波动报警值","Addr":40530,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-目标定降（每日）","Addr":40534,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-液柱低停机值","Addr":40536,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-液柱低报警值","Addr":40538,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-液柱重启值","Addr":40540,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-液柱高报警值","Addr":40542,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"定降液-P参数","Addr":40544,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-I参数","Addr":40546,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-D参数","Addr":40548,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-目标流压","Addr":40562,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-流压低停机值","Addr":40564,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-流压低报警值","Addr":40566,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"定流压-流压重启值","Addr":40568,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-流压高报警值","Addr":40570,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-P参数","Addr":40572,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"定流压-I参数","Addr":40574,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-D参数","Addr":40576,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.NAME='RTU2.0';  
  COMMIT;  
END;  
/

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
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (1, null, '井下压力计-压力', 'c_jxyljyl', 1, 1, null, null, 0, '{"sort":1,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"8a7c93"}', '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"8a7c93"}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (2, null, '煤层顶板深', 'c_mcdbs', 1, 6, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (3, null, '井口套压', 'c_jkty', 1, 2, null, null, 0, '{"sort":2,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"804000"}', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"804000"}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (4, null, '压力计安装深度', 'c_yljazsd', 1, 7, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (5, null, '系统压力', 'c_xtyl', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (6, null, '启停控制', 'c_qtkz', 1, 1, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (7, null, '产气量累计', 'c_cqllj', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (8, null, '变频器设置频率', 'c_bpqszpl', 1, 2, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (9, null, '排采模式', 'c_pcms', 1, 3, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (10, null, '产气量瞬时', 'c_cqlss', 1, 3, null, null, 0, '{"sort":3,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', '{"sort":3,"color":"ff0000","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (11, null, '气体温度', 'c_qtwd', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (12, null, '自动排采状态', 'c_zdpczt', 1, 4, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (13, null, '自动排采-最小频率', 'c_zdpczxpl', 1, 8, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (14, null, '产水量累计', 'c_csllj', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (15, null, '产水量瞬时', 'c_cslss', 1, 4, null, null, 0, '{"sort":4,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"0400ff"}', '{"sort":4,"color":"0400ff","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (16, null, '自动排采-最大频率', 'c_zdpczdpl', 1, 9, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (17, null, '冲次', 'c_cc', 1, 7, null, null, 0, '{"sort":7,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"78a08c"}', '{"sort":7,"color":"78a08c","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (18, null, '最大步长幅度限制', 'c_zdbcfdxz', 1, 10, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (19, null, '变频器运行频率', 'c_bpqyxpl', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (20, null, '最短调整时间间隔', 'c_zddzsjjg', 1, 11, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (21, null, '变频器输出电流', 'c_bpqscdl', 1, 5, null, null, 0, '{"sort":5,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"dbda00"}', '{"sort":5,"color":"dbda00","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (22, null, '自动重启延时', 'c_zdzqys', 1, 12, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (23, null, '自动重启次数', 'c_zdzqcs', 1, 13, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (24, null, '变频器输出电压', 'c_bpqscdy', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (25, null, '变频器状态字1', 'c_bpqztz1', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (26, null, '井底流压波动报警值', 'c_jdlybdbjz', 1, 14, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (27, null, '定降液-目标定降（每日）', 'c_djymbdjmr', 1, 5, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (28, null, '母线电压', 'c_mxdy', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (29, null, '定降液-液柱低停机值', 'c_djyyzdtjz', 1, 15, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (30, null, '修正后井底流压', 'c_xzhjdly', 1, 6, null, null, 0, '{"sort":6,"lineWidth":1,"dashStyle":"Solid","yAxisOpposite":false,"color":"27701a"}', '{"sort":6,"color":"27701a","lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false}', '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (31, null, '定降液-液柱低报警值', 'c_djyyzdbjz', 1, 16, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (32, null, '计算液柱高度', 'c_jsyzgd', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (33, null, '计算近1小时液柱下降速度', 'c_jsj1xsyzxjsd', 1, null, null, null, 0, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (34, null, '定降液-液柱重启值', 'c_djyyzzqz', 1, 17, null, null, 2, null, null, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, BITINDEX, SHOWLEVEL, TYPE, REALTIMECURVECONF, HISTORYCURVECONF, MATRIX)
values (35, null, '定降液-液柱高报警值', 'c_djyyzgbjz', 1, 18, null, null, 2, null, null, '0,0,0');

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
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (1, null, '日期', 'CalDate', 1, 1, null, null, null, null, null, 3, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (2, null, '运行时间', 'RunTime', 1, 2, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (3, null, '冲程', 'Stroke', 1, 4, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (4, null, '冲次', 'SPM', 1, 3, null, null, null, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"ecd211"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (5, null, '日产水量', 'WaterVolumetricProduction', 1, 7, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"00d8ff"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (6, null, '日产气量', 'GasVolumetricProduction', 1, 6, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (7, null, '累计产气量', 'TotalGasVolumetricProduction', 1, 10, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (8, null, '累计产水量', 'TotalWaterVolumetricProduction', 1, 11, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (9, null, '动液面', 'ProducingfluidLevel', 1, 5, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (10, null, '套压', 'CasingPressure', 1, 9, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (11, null, '井底压力', 'BottomHolePressure', 1, 8, null, null, null, null, null, 2, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (12, null, '备注', 'Remark', 1, 12, null, null, null, null, null, 1, 0, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (13, null, '井名', 'WellName', 1, 2, null, 0, 0, null, null, 1, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (14, null, '日期', 'CalDate', 1, 1, null, 0, 0, null, null, 3, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (15, null, '运行时间', 'RunTime', 1, 3, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (16, null, '冲程', 'Stroke', 1, 5, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (17, null, '冲次', 'SPM', 1, 4, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (18, null, '日产水量', 'WaterVolumetricProduction', 1, 8, null, 1, 1, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"00d8ff"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (19, null, '日产气量', 'GasVolumetricProduction', 1, 7, null, 1, 1, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', 1, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (20, null, '累计产气量', 'TotalGasVolumetricProduction', 1, 11, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (21, null, '累计产水量', 'TotalWaterVolumetricProduction', 1, 12, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (22, null, '动液面', 'ProducingfluidLevel', 1, 6, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (23, null, '套压', 'CasingPressure', 1, 10, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (24, null, '井底压力', 'BottomHolePressure', 1, 9, null, 0, 0, null, null, 2, 1, '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX)
values (25, null, '备注', 'Remark', 1, 13, null, 0, 0, null, null, 1, 1, '0,0,0');

/*==============================================================*/
/* 初始化TBL_PROTOCOLREPORTINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, DEVICETYPE, SORT, UNITID)
values (1, '煤层气井报表实例', 'reportinstance1', 0, 1, 1);