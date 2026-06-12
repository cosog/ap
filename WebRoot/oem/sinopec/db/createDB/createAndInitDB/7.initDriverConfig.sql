/*==============================================================*/
/* 初始化TBL_PROTOCOL数据                                        */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, LANGUAGE, SORT)
values (1, 'RTU2.0', 'protocol1', 3, 1, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"煤层顶板深","Addr":40019,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":0.1,"RWType":"rw","Unit":"米","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"压力计安装深度","Addr":40020,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":0.1,"RWType":"rw","Unit":"米","ResolutionMode":2,"AcqMode":"passive"},{"Title":"井下压力计-压力","Addr":40400,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":0.001,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"动液面","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"井口套压","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":3,"Quantity":1,"Ratio":0.001,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"系统压力","Addr":40406,"StoreDataType":"float32","IFDataType":"float32","Prec":3,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"KPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"产气量累计","Addr":40408,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"产气量瞬时","Addr":40410,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3/h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"气体温度","Addr":40412,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"产水量累计","Addr":40414,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"产水量瞬时","Addr":40416,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m3/h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"冲次","Addr":40418,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"冲/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A相电流","Addr":40420,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B相电流","Addr":40422,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"C相电流","Addr":40424,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A相电压","Addr":40426,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B相电压","Addr":40428,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C相电压","Addr":40430,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"井下压力计-温度","Addr":40444,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"气体流量计通讯状态","Addr":40446,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"无响应"},{"Value":2,"Meaning":"站号错误"},{"Value":3,"Meaning":"命令错误"},{"Value":4,"Meaning":"CRC校验错误"}]},{"Title":"水流量计通讯状态","Addr":40447,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"无响应"},{"Value":2,"Meaning":"站号错误"},{"Value":3,"Meaning":"命令错误"},{"Value":4,"Meaning":"CRC校验错误"}]},'
  ||'{"Title":"井下压力计/液面仪通讯状态","Addr":40448,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"无响应"},{"Value":2,"Meaning":"站号错误"},{"Value":3,"Meaning":"命令错误"},{"Value":4,"Meaning":"CRC校验错误"}]},{"Title":"变频器通讯状态","Addr":40450,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"无响应"},{"Value":2,"Meaning":"站号错误"},{"Value":3,"Meaning":"命令错误"},{"Value":4,"Meaning":"CRC校验错误"}]},{"Title":"当前目标值","Addr":40451,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"无响应"},{"Value":2,"Meaning":"站号错误"},{"Value":3,"Meaning":"命令错误"},{"Value":4,"Meaning":"CRC校验错误"}]},'
  ||'{"Title":"初始液柱","Addr":40453,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"频率控制方式","Addr":40481,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"冲次控制"},{"Value":1,"Meaning":"频率控制"}]},{"Title":"启停控制","Addr":40482,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"启动"},{"Value":2,"Meaning":"停止"}]},'
  ||'{"Title":"变频器设置频率","Addr":40483,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"变频器运行频率","Addr":40485,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"变频器故障状态","Addr":40487,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"变频器输出电流","Addr":40490,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频器输出电压","Addr":40492,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"变频器厂家代码","Addr":40494,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"无变频器或变频器无通讯功能"},{"Value":1,"Meaning":"英威腾"},{"Value":2,"Meaning":"科陆新能"},{"Value":3,"Meaning":"步科"},{"Value":4,"Meaning":"汇川"},{"Value":5,"Meaning":"信宇（开封信达）"},{"Value":6,"Meaning":"科陆新能660V"},{"Value":7,"Meaning":"日业电气"},{"Value":8,"Meaning":"普传科技"},{"Value":9,"Meaning":"INVERTER"},{"Value":10,"Meaning":"易驱"}]},{"Title":"变频器状态字1","Addr":40495,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"运行"},{"Value":3,"Meaning":"停机"},{"Value":4,"Meaning":"故障"}]},'
  ||'{"Title":"变频器状态字2","Addr":40496,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"本地旋钮位置","Addr":40497,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"母线电压","Addr":40498,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"设定频率反馈","Addr":40500,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"10Hz对应冲次/转速预设值","Addr":40502,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"次/100转","ResolutionMode":2,"AcqMode":"passive"},{"Title":"50Hz对应冲次/转速预设值","Addr":40504,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"次/100转","ResolutionMode":2,"AcqMode":"passive"},{"Title":"冲次/转速设定值","Addr":40506,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"次/100转","ResolutionMode":2,"AcqMode":"passive"},{"Title":"修正后井底流压","Addr":40512,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":0.001,"RWType":"r","Unit":"Mpa","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"计算液柱高度","Addr":40514,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"计算近1小时液柱下降速度","Addr":40516,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m/小时","ResolutionMode":2,"AcqMode":"passive"},{"Title":"排采模式","Addr":40522,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"手动"},{"Value":1,"Meaning":"定降液"}]},'
  ||'{"Title":"自动排采状态","Addr":40523,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"正常"},{"Value":1,"Meaning":"流压波动报警"},{"Value":2,"Meaning":"定降液-液柱低停机"},{"Value":3,"Meaning":"定降液-液柱低报警"},{"Value":4,"Meaning":"自动重启达到最大次数"},{"Value":5,"Meaning":"定降液-液柱高报警"},{"Value":6,"Meaning":"定流压-流压低停机"},{"Value":7,"Meaning":"定流压-流压低报警"},{"Value":8,"Meaning":"定流压-流压高报警"},{"Value":9,"Meaning":"自动重启中"},{"Value":10,"Meaning":"自动排采时连续4次执行最大频率"},{"Value":11,"Meaning":"自动排采时连续4次执行最小频率"}]},{"Title":"自动排采-最小频率","Addr":40524,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"自动排采-最大频率","Addr":40525,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"最大步长幅度限制","Addr":40526,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"最短调整时间间隔","Addr":40527,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"秒","ResolutionMode":2,"AcqMode":"passive"},{"Title":"自动重启延时","Addr":40528,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"秒","ResolutionMode":2,"AcqMode":"passive"},{"Title":"自动重启次数","Addr":40529,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"次","ResolutionMode":2,"AcqMode":"passive"},{"Title":"井底流压波动报警值","Addr":40530,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"定降液-目标定降（每日）","Addr":40534,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-液柱低停机值","Addr":40536,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-液柱低报警值","Addr":40538,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-液柱重启值","Addr":40540,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"定降液-液柱高报警值","Addr":40542,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-P参数","Addr":40544,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-I参数","Addr":40546,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定降液-D参数","Addr":40548,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-目标流压","Addr":40562,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"定流压-流压低停机值","Addr":40564,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-流压低报警值","Addr":40566,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-流压重启值","Addr":40568,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-流压高报警值","Addr":40570,"StoreDataType":"float32","IFDataType":"float32","Prec":1,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Kpa","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"定流压-P参数","Addr":40572,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive","Meaning":[]},{"Title":"定流压-I参数","Addr":40574,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"定流压-D参数","Addr":40576,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"}]';
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.CODE='protocol1';  
  COMMIT;  
END;  
/



/*==============================================================*/
/* 初始化TBL_DATAMAPPING数据                                          */
/*==============================================================*/
insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (1, '煤层顶板深', 'C_CLOUMN1', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (2, '压力计安装深度', 'C_CLOUMN2', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (3, '井下压力计-压力', 'C_CLOUMN3', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (4, '动液面', 'C_CLOUMN4', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (5, '井口套压', 'C_CLOUMN5', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (6, '系统压力', 'C_CLOUMN6', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (7, '产气量累计', 'C_CLOUMN7', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (8, '产气量瞬时', 'C_CLOUMN8', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (9, '气体温度', 'C_CLOUMN9', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (10, '产水量累计', 'C_CLOUMN10', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (11, '产水量瞬时', 'C_CLOUMN11', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (12, '冲次', 'C_CLOUMN12', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (13, 'A相电流', 'C_CLOUMN13', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (14, 'B相电流', 'C_CLOUMN14', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (15, 'C相电流', 'C_CLOUMN15', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (16, 'A相电压', 'C_CLOUMN16', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (17, 'B相电压', 'C_CLOUMN17', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (18, 'C相电压', 'C_CLOUMN18', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (19, '井下压力计-温度', 'C_CLOUMN19', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (20, '气体流量计通讯状态', 'C_CLOUMN20', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (21, '水流量计通讯状态', 'C_CLOUMN21', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (22, '井下压力计/液面仪通讯状态', 'C_CLOUMN22', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (23, '变频器通讯状态', 'C_CLOUMN23', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (24, '当前目标值', 'C_CLOUMN24', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (25, '初始液柱', 'C_CLOUMN25', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (26, '频率控制方式', 'C_CLOUMN26', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (27, '启停控制', 'C_CLOUMN27', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (28, '变频器设置频率', 'C_CLOUMN28', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (29, '变频器运行频率', 'C_CLOUMN29', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (30, '变频器故障状态', 'C_CLOUMN30', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (31, '变频器输出电流', 'C_CLOUMN31', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (32, '变频器输出电压', 'C_CLOUMN32', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (33, '变频器厂家代码', 'C_CLOUMN33', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (34, '变频器状态字1', 'C_CLOUMN34', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (35, '变频器状态字2', 'C_CLOUMN35', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (36, '本地旋钮位置', 'C_CLOUMN36', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (37, '母线电压', 'C_CLOUMN37', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (38, '设定频率反馈', 'C_CLOUMN38', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (39, '10Hz对应冲次/转速预设值', 'C_CLOUMN39', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (40, '50Hz对应冲次/转速预设值', 'C_CLOUMN40', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (41, '冲次/转速设定值', 'C_CLOUMN41', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (42, '修正后井底流压', 'C_CLOUMN42', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (43, '计算液柱高度', 'C_CLOUMN43', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (44, '计算近1小时液柱下降速度', 'C_CLOUMN44', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (45, '排采模式', 'C_CLOUMN45', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (46, '自动排采状态', 'C_CLOUMN46', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (47, '自动排采-最小频率', 'C_CLOUMN47', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (48, '自动排采-最大频率', 'C_CLOUMN48', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (49, '最大步长幅度限制', 'C_CLOUMN49', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (50, '最短调整时间间隔', 'C_CLOUMN50', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (51, '自动重启延时', 'C_CLOUMN51', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (52, '自动重启次数', 'C_CLOUMN52', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (53, '井底流压波动报警值', 'C_CLOUMN53', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (54, '定降液-目标定降（每日）', 'C_CLOUMN54', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (55, '定降液-液柱低停机值', 'C_CLOUMN55', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (56, '定降液-液柱低报警值', 'C_CLOUMN56', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (57, '定降液-液柱重启值', 'C_CLOUMN57', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (58, '定降液-液柱高报警值', 'C_CLOUMN58', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (59, '定降液-P参数', 'C_CLOUMN59', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (60, '定降液-I参数', 'C_CLOUMN60', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (61, '定降液-D参数', 'C_CLOUMN61', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (62, '定流压-目标流压', 'C_CLOUMN62', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (63, '定流压-流压低停机值', 'C_CLOUMN63', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (64, '定流压-流压低报警值', 'C_CLOUMN64', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (65, '定流压-流压重启值', 'C_CLOUMN65', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (66, '定流压-流压高报警值', 'C_CLOUMN66', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (67, '定流压-P参数', 'C_CLOUMN67', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (68, '定流压-I参数', 'C_CLOUMN68', null, null, null, 1, 0);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE, CALCULATEENABLE)
values (69, '定流压-D参数', 'C_CLOUMN69', null, null, null, 1, 0);


/*==============================================================*/
/* 初始化TBL_RUNSTATUSCONFIG数据                                          */
/*==============================================================*/
insert into TBL_RUNSTATUSCONFIG (ID, PROTOCOL, ITEMNAME, ITEMMAPPINGCOLUMN, RESOLUTIONMODE, RUNVALUE, STOPVALUE, RUNCONDITION, STOPCONDITION, BITINDEX, PROTOCOLTYPE)
values (1, 'protocol1', '变频器状态字1', 'C_CLOUMN34', 1, '1', '3', null, null, null, null);

/*==============================================================*/
/* 初始化TBL_ACQ_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, SORT, REMARK)
values (1, 'unit1', '抽油机RTU2.0采控单元', 'protocol1', 1, '抽油机RTU2.0采控单元');

/*==============================================================*/
/* 初始化TBL_ACQ_GROUP_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '采集组', 120, 120, 'protocol1', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '控制组', null, null, 'protocol1', 1, null);

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
insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (1, null, '煤层顶板深', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (2, null, '压力计安装深度', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (3, null, '井下压力计-压力', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (4, null, '动液面', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (5, null, '井口套压', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (6, null, '系统压力', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (7, null, '产气量累计', null, 1, null, 1, '日产气量', '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (8, null, '产气量瞬时', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (9, null, '气体温度', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (10, null, '产水量累计', null, 1, null, 1, '日产水量', '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (11, null, '产水量瞬时', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (12, null, '冲次', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (13, null, 'A相电流', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (14, null, 'B相电流', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (15, null, 'C相电流', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (16, null, 'A相电压', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (17, null, 'B相电压', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (18, null, 'C相电压', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (19, null, '井下压力计-温度', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (20, null, '气体流量计通讯状态', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (21, null, '水流量计通讯状态', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (22, null, '井下压力计/液面仪通讯状态', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (23, null, '变频器通讯状态', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (24, null, '当前目标值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (25, null, '初始液柱', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (26, null, '变频器设置频率', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (27, null, '变频器运行频率', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (28, null, '变频器故障状态', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (29, null, '变频器输出电流', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (30, null, '变频器输出电压', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (31, null, '变频器厂家代码', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (32, null, '变频器状态字1', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (33, null, '变频器状态字2', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (34, null, '母线电压', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (35, null, '10Hz对应冲次/转速预设值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (36, null, '50Hz对应冲次/转速预设值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (37, null, '修正后井底流压', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (38, null, '计算液柱高度', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (39, null, '计算近1小时液柱下降速度', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (40, null, '排采模式', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (41, null, '自动排采状态', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (42, null, '自动排采-最小频率', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (43, null, '自动排采-最大频率', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (44, null, '最大步长幅度限制', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (45, null, '最短调整时间间隔', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (46, null, '自动重启延时', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (47, null, '自动重启次数', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (48, null, '井底流压波动报警值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (49, null, '定降液-目标定降（每日）', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (50, null, '定降液-液柱低停机值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (51, null, '定降液-液柱低报警值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (52, null, '定降液-液柱重启值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (53, null, '定降液-液柱高报警值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (54, null, '定降液-P参数', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (55, null, '定降液-I参数', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (56, null, '定降液-D参数', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (57, null, '定流压-目标流压', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (58, null, '定流压-流压低停机值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (59, null, '定流压-流压低报警值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (60, null, '定流压-流压重启值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (61, null, '定流压-流压高报警值', null, 1, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (62, null, '煤层顶板深', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (63, null, '压力计安装深度', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (64, null, '频率控制方式', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (65, null, '启停控制', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (66, null, '变频器设置频率', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (67, null, '10Hz对应冲次/转速预设值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (68, null, '50Hz对应冲次/转速预设值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (69, null, '冲次/转速设定值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (70, null, '排采模式', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (71, null, '自动排采状态', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (72, null, '自动排采-最小频率', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (73, null, '自动排采-最大频率', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (74, null, '最大步长幅度限制', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (75, null, '最短调整时间间隔', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (76, null, '自动重启延时', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (77, null, '自动重启次数', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (78, null, '井底流压波动报警值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (79, null, '定降液-目标定降（每日）', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (80, null, '定降液-液柱低停机值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (81, null, '定降液-液柱低报警值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (82, null, '定降液-液柱重启值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (83, null, '定降液-液柱高报警值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (84, null, '定降液-P参数', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (85, null, '定降液-I参数', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (86, null, '定降液-D参数', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (87, null, '定流压-目标流压', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (88, null, '定流压-流压低停机值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (89, null, '定流压-流压低报警值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (90, null, '定流压-流压重启值', null, 2, null, 0, null, '0,0,0');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME, MATRIX)
values (91, null, '定流压-流压高报警值', null, 2, null, 0, null, '0,0,0');

/*==============================================================*/
/* 初始化TBL_ALARM_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, CALCULATETYPE, SORT, REMARK)
values (1, 'alarmunit1', '抽油机RTU2.0报警单元', 'protocol1', 0, 1, '抽油机RTU2.0报警单元');

/*==============================================================*/
/* 初始化TBL_ALARM_ITEM2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (1, 1, null, '停止', 'stop', 0, 0.000, null, null, null, null, 100, 1, 6, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (2, 1, null, '上线', 'goOnline', 0, 2.000, null, null, null, null, 300, 1, 3, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (3, 1, null, '离线', 'offline', 0, 0.000, null, null, null, null, 100, 1, 3, 0, 0, 0, 60);

/*==============================================================*/
/* 初始化TBL_DISPLAY_UNIT_CONF数据                                */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, CALCULATETYPE, SORT, REMARK)
values (1, 'unit1', '抽油机RTU2.0显示单元', 'protocol1', 1, 0, 1, '抽油机RTU2.0显示单元');

/*==============================================================*/
/* 初始化TBL_DISPLAY_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (1, null, '煤层顶板深', 'C_CLOUMN1', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (2, null, '压力计安装深度', 'C_CLOUMN2', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (3, null, '井下压力计-压力', 'C_CLOUMN3', 1, 1, null, null, 1, null, null, null, null, '{"sort":"1","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"8a7c93"}', '{"sort":"1","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"8a7c93"}', 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (4, null, '动液面', 'C_CLOUMN4', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (5, null, '井口套压', 'C_CLOUMN5', 1, 2, null, null, 2, null, null, null, null, '{"sort":"2","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"804000"}', '{"sort":"2","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"804000"}', 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (6, null, '系统压力', 'C_CLOUMN6', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (7, null, '产气量累计', 'C_CLOUMN7', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (8, null, '产气量瞬时', 'C_CLOUMN8', 1, 3, null, null, 3, null, null, null, null, '{"sort":"3","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', '{"sort":"3","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"ff0000"}', 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (9, null, '气体温度', 'C_CLOUMN9', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (10, null, '产水量累计', 'C_CLOUMN10', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (11, null, '产水量瞬时', 'C_CLOUMN11', 1, 4, null, null, 4, null, null, null, null, '{"sort":"4","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"0400ff"}', '{"sort":"4","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"0400ff"}', 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (12, null, '冲次', 'C_CLOUMN12', 1, 7, null, null, 7, null, null, null, null, '{"sort":"7","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"78a08c"}', '{"sort":"7","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"78a08c"}', 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (13, null, 'A相电流', 'C_CLOUMN13', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (14, null, 'B相电流', 'C_CLOUMN14', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (15, null, 'C相电流', 'C_CLOUMN15', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (16, null, 'A相电压', 'C_CLOUMN16', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (17, null, 'B相电压', 'C_CLOUMN17', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (18, null, 'C相电压', 'C_CLOUMN18', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (19, null, '井下压力计-温度', 'C_CLOUMN19', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (20, null, '气体流量计通讯状态', 'C_CLOUMN20', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (21, null, '水流量计通讯状态', 'C_CLOUMN21', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (22, null, '井下压力计/液面仪通讯状态', 'C_CLOUMN22', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (23, null, '变频器通讯状态', 'C_CLOUMN23', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (24, null, '当前目标值', 'C_CLOUMN24', 1, 8, null, null, 8, null, null, null, null, '{"sort":"8","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"dac7c7"}', '{"sort":"8","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"dac7c7"}', 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (25, null, '初始液柱', 'C_CLOUMN25', 1, 9, null, null, 9, null, null, null, null, '{"sort":"9","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff00ff"}', '{"sort":"9","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff00ff"}', 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (26, null, '变频器设置频率', 'C_CLOUMN28', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (27, null, '变频器运行频率', 'C_CLOUMN29', 1, null, null, null, null, null, null, null, null, '{"sort":"10","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"0b0b0b"}', null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (28, null, '变频器故障状态', 'C_CLOUMN30', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (29, null, '变频器输出电流', 'C_CLOUMN31', 1, 5, null, null, 5, null, null, null, null, '{"sort":"5","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"dbda00"}', '{"sort":"5","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"dadb00"}', 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (30, null, '变频器输出电压', 'C_CLOUMN32', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (31, null, '变频器厂家代码', 'C_CLOUMN33', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (32, null, '变频器状态字1', 'C_CLOUMN34', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (33, null, '变频器状态字2', 'C_CLOUMN35', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (34, null, '母线电压', 'C_CLOUMN37', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (35, null, '10Hz对应冲次/转速预设值', 'C_CLOUMN39', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (36, null, '50Hz对应冲次/转速预设值', 'C_CLOUMN40', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (37, null, '修正后井底流压', 'C_CLOUMN42', 1, 6, null, null, 6, null, null, null, null, '{"sort":"6","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"27701a"}', '{"sort":"6","lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"27701a"}', 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (38, null, '计算液柱高度', 'C_CLOUMN43', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (39, null, '计算近1小时液柱下降速度', 'C_CLOUMN44', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (40, null, '排采模式', 'C_CLOUMN45', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (41, null, '自动排采状态', 'C_CLOUMN46', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (42, null, '自动排采-最小频率', 'C_CLOUMN47', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (43, null, '自动排采-最大频率', 'C_CLOUMN48', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (44, null, '最大步长幅度限制', 'C_CLOUMN49', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (45, null, '最短调整时间间隔', 'C_CLOUMN50', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (46, null, '自动重启延时', 'C_CLOUMN51', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (47, null, '自动重启次数', 'C_CLOUMN52', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (48, null, '井底流压波动报警值', 'C_CLOUMN53', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (49, null, '定降液-目标定降（每日）', 'C_CLOUMN54', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (50, null, '定降液-液柱低停机值', 'C_CLOUMN55', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (51, null, '定降液-液柱低报警值', 'C_CLOUMN56', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (52, null, '定降液-液柱重启值', 'C_CLOUMN57', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (53, null, '定降液-液柱高报警值', 'C_CLOUMN58', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (54, null, '定降液-P参数', 'C_CLOUMN59', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (55, null, '定降液-I参数', 'C_CLOUMN60', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (56, null, '定降液-D参数', 'C_CLOUMN61', 1, null, null, null, null, null, null, null, null, null, null, 0, 0, 1, 0, 1, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (57, null, '煤层顶板深', 'C_CLOUMN1', 1, 6, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (58, null, '压力计安装深度', 'C_CLOUMN2', 1, 7, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (59, null, '频率控制方式', 'C_CLOUMN26', 1, 2, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (60, null, '启停控制', 'C_CLOUMN27', 1, 1, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (61, null, '变频器设置频率', 'C_CLOUMN28', 1, 3, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (62, null, '10Hz对应冲次/转速预设值', 'C_CLOUMN39', 1, null, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (63, null, '50Hz对应冲次/转速预设值', 'C_CLOUMN40', 1, null, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (64, null, '冲次/转速设定值', 'C_CLOUMN41', 1, 4, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (65, null, '排采模式', 'C_CLOUMN45', 1, 5, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (66, null, '自动排采状态', 'C_CLOUMN46', 1, 4, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (67, null, '自动排采-最小频率', 'C_CLOUMN47', 1, 8, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (68, null, '自动排采-最大频率', 'C_CLOUMN48', 1, 9, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (69, null, '最大步长幅度限制', 'C_CLOUMN49', 1, 10, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (70, null, '最短调整时间间隔', 'C_CLOUMN50', 1, 11, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (71, null, '自动重启延时', 'C_CLOUMN51', 1, 12, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (72, null, '自动重启次数', 'C_CLOUMN52', 1, 13, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (73, null, '井底流压波动报警值', 'C_CLOUMN53', 1, 14, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (74, null, '定降液-目标定降（每日）', 'C_CLOUMN54', 1, 5, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (75, null, '定降液-液柱低停机值', 'C_CLOUMN55', 1, 15, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (76, null, '定降液-液柱低报警值', 'C_CLOUMN56', 1, 16, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (77, null, '定降液-液柱重启值', 'C_CLOUMN57', 1, 17, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (78, null, '定降液-液柱高报警值', 'C_CLOUMN58', 1, 18, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (79, null, '定降液-P参数', 'C_CLOUMN59', 1, null, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (80, null, '定降液-I参数', 'C_CLOUMN60', 1, null, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYSORT, HISTORYCOLOR, HISTORYBGCOLOR, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, REALTIMEOVERVIEW, REALTIMEDATA, HISTORYOVERVIEW, HISTORYDATA, REALTIMEOVERVIEWSORT, HISTORYOVERVIEWSORT, SWITCHINGVALUESHOWTYPE, MATRIX)
values (81, null, '定降液-D参数', 'C_CLOUMN61', 1, null, null, null, null, null, null, null, null, null, null, 2, null, null, null, null, null, null, 0, '0,0,0');

/*==============================================================*/
/* 初始化TBL_REPORT_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (1, 'unit1', '煤层气井报表单元', 'CBMWell_heichao', 'CBMWell_heichaoProductionReport', 1, 'CBMWell_heichaoDailyReport', 0);

/*==============================================================*/
/* 初始化TBL_REPORT_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (1, null, '时间', 'CalTime', null, 1, 1, null, null, null, null, null, 4, 2, null, null, '1', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (2, null, '运行时间', 'RunTime', null, 1, 2, null, null, null, null, null, 2, 2, null, null, '1', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (3, null, '冲次', 'C_CLOUMN12', null, 1, 3, null, null, null, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"ecd211"}', null, 2, 2, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (4, null, '备用1', 'reservedcol1', null, 1, 4, null, null, null, null, null, 1, 2, null, null, '2', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (5, null, '动液面', 'C_CLOUMN4', null, 1, 5, null, null, null, null, null, 2, 2, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (6, null, '修正后井底流压', 'C_CLOUMN42', null, 1, 6, null, null, null, null, null, 2, 2, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (7, null, '井口套压', 'C_CLOUMN5', null, 1, 7, null, null, null, null, null, 2, 2, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (8, null, '产气量瞬时', 'C_CLOUMN8', null, 1, 8, null, null, null, null, null, 2, 2, null, 4, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (9, null, '产水量瞬时', 'C_CLOUMN11', null, 1, 9, null, null, null, null, null, 2, 2, null, 4, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (10, null, '产气量累计', 'C_CLOUMN7', null, 1, 10, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', null, 2, 2, null, 6, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (11, null, '产水量累计', 'C_CLOUMN10', null, 1, 11, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"00d8ff"}', null, 2, 2, null, 6, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (12, null, '备用2', 'reservedcol2', null, 1, 12, null, null, null, null, null, 1, 2, null, null, '2', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (13, null, '备用3', 'reservedcol3', null, 1, 13, null, null, null, null, null, 1, 2, null, null, '2', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (14, null, '备注', 'Remark', null, 1, 14, null, null, null, null, null, 1, 2, null, null, '2', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (15, null, '日期', 'CalDate', null, 1, 1, null, null, null, null, null, 3, 0, null, null, '1', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (16, null, '运行时间', 'RunTime', null, 1, 2, null, null, null, null, null, 2, 0, null, null, '1', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (17, null, '冲次', 'C_CLOUMN12', null, 1, 3, null, null, null, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"ecd211"}', null, 2, 0, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (18, null, '备用1', 'reservedcol1', null, 1, 4, null, null, null, null, null, 1, 0, null, null, '2', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (19, null, '动液面', 'C_CLOUMN4', null, 1, 5, null, null, null, null, null, 2, 0, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (20, null, '产气量累计', 'C_CLOUMN7', null, 1, 6, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', null, 2, 0, null, 6, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (21, null, '产水量累计', 'C_CLOUMN10', null, 1, 7, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"00d8ff"}', null, 2, 0, null, 6, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (22, null, '修正后井底流压', 'C_CLOUMN42', null, 1, 8, null, null, null, null, null, 2, 0, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (23, null, '井口套压', 'C_CLOUMN5', null, 1, 9, null, null, null, null, null, 2, 0, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (24, null, '产气量累计', 'C_CLOUMN7', null, 1, 10, null, null, null, null, null, 2, 0, null, 4, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (25, null, '产水量累计', 'C_CLOUMN10', null, 1, 11, null, null, null, null, null, 2, 0, null, 4, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (26, null, '备注', 'Remark', null, 1, 12, null, null, null, null, null, 1, 0, null, null, '2', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (27, null, '日期', 'CalDate', null, 1, 1, null, 0, 0, null, null, 3, 1, null, null, '1', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (28, null, '设备名称', 'DeviceName', null, 1, 2, null, 0, 0, null, null, 1, 1, null, null, '2', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (29, null, '运行时间', 'RunTime', null, 1, 3, null, 0, 0, null, null, 2, 1, null, null, '1', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (30, null, '冲次', 'C_CLOUMN12', null, 1, 4, null, 0, 0, null, null, 2, 1, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (31, null, '备用1', 'reservedcol1', null, 1, 5, null, 0, 0, null, null, 1, 1, null, null, '2', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (32, null, '动液面', 'C_CLOUMN4', null, 1, 6, null, 0, 0, null, null, 2, 1, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (33, null, '产气量累计', 'C_CLOUMN7', null, 1, 7, null, 1, 1, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"00d8ff"}', 1, 2, 1, null, 6, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (34, null, '产水量累计', 'C_CLOUMN10', null, 1, 8, null, 1, 1, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"1424f1"}', 1, 2, 1, null, 6, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (35, null, '修正后井底流压', 'C_CLOUMN42', null, 1, 9, null, 0, 0, null, null, 2, 1, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (36, null, '井口套压', 'C_CLOUMN5', null, 1, 10, null, 0, 0, null, null, 2, 1, null, 3, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (37, null, '产气量累计', 'C_CLOUMN7', null, 1, 11, null, 0, 0, null, null, 2, 1, null, 4, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (38, null, '产水量累计', 'C_CLOUMN10', null, 1, 12, null, 0, 0, null, null, 2, 1, null, 4, '0', '0,0,0');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, BITINDEX, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, PREC, TOTALTYPE, DATASOURCE, MATRIX)
values (39, null, '备注', 'Remark', null, 1, 13, null, 0, 0, null, null, 1, 1, null, null, '2', '0,0,0');

/*==============================================================*/
/* 初始化TBL_PROTOCOLINSTANCE数据                                 */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (1, '抽油机RTU2.0采控实例', 'instance1', 'modbus-rtu', 'modbus-rtu', 1, 'CC01', '0C', 1, 1, 'CC01', '0C', 500, 1, 1);

/*==============================================================*/
/* 初始化tbl_protocolalarminstance数据                            */
/*==============================================================*/
insert into tbl_protocolalarminstance (ID, NAME, CODE, ALARMUNITID, SORT)
values (1, '抽油机RTU2.0报警实例', 'alarminstance1', 1, 1);

/*==============================================================*/
/* 初始化tbl_protocoldisplayinstance数据                                          */
/*==============================================================*/
insert into tbl_protocoldisplayinstance (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (1, '抽油机RTU2.0显示实例', 'displayinstance1', 1, 1);

/*==============================================================*/
/* 初始化TBL_PROTOCOLREPORTINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (1, '煤层气井报表实例', 'reportinstance1', 1, 1);