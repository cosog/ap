/*==============================================================*/
/* 初始化TBL_PROTOCOL数据                                        */
/*==============================================================*/
insert into TBL_PROTOCOL (ID, NAME, CODE, DEVICETYPE, SORT)
values (1, '永磁电机电参反演协议', 'protocol1', 1, 1);

DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"油压","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"套压","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"回压","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"井口温度","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"运行状态","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive"},'
  ||'{"Title":"启停控制","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"含水率","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"动液面","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A相电流","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B相电流","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C相电流","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"A相电压","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B相电压","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C相电压","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"有功功耗","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"无功功耗","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"有功功率","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"无功功率","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},{"Title":"反向功率","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功率因数","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频设置频率","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频运行频率","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"螺杆泵转速","Addr":40430,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"r/min","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"螺杆泵扭矩","Addr":40432,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kN·m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图采集间隔","Addr":40981,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"手动采集功图","Addr":40982,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图设置点数","Addr":40983,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图实测点数","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图采集时间","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"冲次","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"次/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"冲程","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-位移","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":3,"Quantity":250,"Ratio":0.001,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-载荷","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-电流","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-功率","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"电参反演握手协议-从机写主机读状态码","Addr":44981,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"电参反演握手协议-主机写从机读状态码","Addr":44982,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"电参反演采集参数-实测点数","Addr":44991,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演采集参数-采集时间","Addr":44992,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演采集参数-冲次","Addr":44998,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"1/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演采集参数-电机输出功率","Addr":45000,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":500,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"电参反演采集参数-电机输出电流","Addr":45500,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":500,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演采集参数-电机输出扭矩","Addr":46000,"StoreDataType":"int16","IFDataType":"float32","Prec":1,"Quantity":500,"Ratio":0.1,"RWType":"r","Unit":"牛米","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演采集参数-曲柄角度","Addr":46500,"StoreDataType":"int16","IFDataType":"float32","Prec":4,"Quantity":500,"Ratio":2.0E-4,"RWType":"r","Unit":"弧度","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演采集参数-电机转速","Addr":47000,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":500,"Ratio":0.01,"RWType":"r","Unit":"rpm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演采集参数-采样间隔","Addr":47500,"StoreDataType":"int16","IFDataType":"float32","Prec":1,"Quantity":500,"Ratio":0.1,"RWType":"r","Unit":"ms","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-最大载荷","Addr":49500,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算结果-最小载荷","Addr":49502,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-理论上载荷","Addr":49504,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-理论下载荷","Addr":49506,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-充满系数","Addr":49508,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"小数","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-抽空充满系数","Addr":49510,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"小数","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-产液量","Addr":49518,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"t/d","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算结果-产油量","Addr":49520,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"t/d","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-产水量","Addr":49522,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"t/d","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-理论排量","Addr":49524,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m^3/d","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-泵效","Addr":49526,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"小数","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-工况代码","Addr":49528,"StoreDataType":"float32","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算结果-反演动液面","Addr":49529,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算结果-液面校正差值","Addr":49531,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-沉没度","Addr":49533,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-下冲程最大电流","Addr":49535,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-上冲程最大电流","Addr":49537,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-电流平衡度","Addr":49539,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-下冲程最大功率","Addr":49541,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算结果-上冲程最大功率","Addr":49543,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-功率平衡度","Addr":49545,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-移动距离","Addr":49547,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-井下系统效率","Addr":49549,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"小数","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-地面系统效率","Addr":49551,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"小数","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算结果-系统效率","Addr":49553,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"小数","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算结果-吨液百米耗电量","Addr":49555,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kW·h/100m·t","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-原油密度","Addr":49700,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"g/cm^3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-地层水密度","Addr":49702,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"g/cm^3","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-天然气相对密度","Addr":49704,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-饱和压力","Addr":49706,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Mpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-油层中部深度","Addr":49708,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算辅助参数-油层中部温度","Addr":49710,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-油压","Addr":49712,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Mpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-套压","Addr":49714,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Mpa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-井口温度","Addr":49716,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-含水率","Addr":49718,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-生产气油比","Addr":49720,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m^3/t","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算辅助参数-动液面","Addr":49722,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-泵挂","Addr":49724,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-泵筒类型","Addr":49726,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-泵级别","Addr":49727,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-泵径","Addr":49728,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-柱塞长","Addr":49730,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算辅助参数-油管内径","Addr":49732,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-套管内径","Addr":49734,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-一级杆类型","Addr":49736,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-一级杆级别","Addr":49737,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-一级杆外径","Addr":49738,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-一级杆内径","Addr":49740,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算辅助参数-一级杆长","Addr":49742,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-二级杆类型","Addr":49744,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-二级杆级别","Addr":49745,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-二级杆外径","Addr":49746,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-二级杆内径","Addr":49748,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-二级杆长","Addr":49750,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算辅助参数-三级杆类型","Addr":49752,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-三级杆级别","Addr":49753,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-三级杆外径","Addr":49754,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-三级杆内径","Addr":49756,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-三级杆长","Addr":49758,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-四级杆类型","Addr":49760,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},'
  ||'{"Title":"功图计算辅助参数-四级杆级别","Addr":49761,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-四级杆外径","Addr":49762,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-四级杆内径","Addr":49764,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"mm","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-四级杆长","Addr":49766,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-曲柄旋转方向","Addr":49768,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-曲柄偏置角","Addr":49769,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"度","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算辅助参数-平衡块重量1","Addr":49771,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-平衡块重量2","Addr":49773,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-平衡块重量3","Addr":49775,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-平衡块重量4","Addr":49777,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-平衡块重量5","Addr":49779,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算辅助参数-平衡块重量6","Addr":49781,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-平衡块重量7","Addr":49783,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-平衡块重量8","Addr":49785,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-工况干预代码","Addr":49787,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图计算辅助参数-净毛比","Addr":49788,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"小数","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图计算辅助参数-净毛值","Addr":49790,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m^3/d","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"功图计算辅助参数-反演液面校正值","Addr":49792,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-抽油机冲程","Addr":49800,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-曲柄旋转方向","Addr":49802,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"电参反演辅助参数-曲柄偏置角","Addr":49803,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"度","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-曲柄重心半径","Addr":49805,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-单块曲柄重量","Addr":49807,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"电参反演辅助参数-单块曲柄销重量","Addr":49809,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-结构不平衡重","Addr":49811,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块位置1","Addr":49813,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块位置2","Addr":49815,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块位置3","Addr":49817,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"电参反演辅助参数-平衡块位置4","Addr":49819,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块位置5","Addr":49821,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块位置6","Addr":49823,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块位置7","Addr":49825,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块位置8","Addr":49827,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"电参反演辅助参数-平衡块重量1","Addr":49829,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块重量2","Addr":49831,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块重量3","Addr":49833,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块重量4","Addr":49835,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块重量5","Addr":49837,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块重量6","Addr":49839,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"电参反演辅助参数-平衡块重量7","Addr":49841,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-平衡块重量8","Addr":49843,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-减速箱传动比","Addr":49845,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-曲柄DI初始角度","Addr":49847,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"度","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-反演功图插值点数","Addr":49849,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-地面系统效率","Addr":49850,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"小数","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"电参反演辅助参数-功率中值滤波次数","Addr":49852,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-电流中指滤波次数","Addr":49853,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-功图全图滤波次数","Addr":49854,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-功图左侧滤波次数","Addr":49855,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-功图右侧滤波次数","Addr":49856,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-功图左侧截取百分比","Addr":49857,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-功图右侧截取百分比","Addr":49859,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"电参反演辅助参数-位置扭矩因数点数","Addr":49869,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-曲柄转角","Addr":49870,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":37,"Ratio":1.0,"RWType":"rw","Unit":"度","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-光杆位置因数","Addr":49907,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":37,"Ratio":1.0,"RWType":"rw","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},{"Title":"电参反演辅助参数-扭矩因数","Addr":49944,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":37,"Ratio":1.0,"RWType":"rw","Unit":"m","ResolutionMode":2,"AcqMode":"passive"}]'; 
  UPDATE TBL_PROTOCOL T SET T.ITEMS = clobValue WHERE t.CODE='protocol1';
  COMMIT;  
END;  
/

/*==============================================================*/
/* 初始化TBL_DATAMAPPING数据                                          */
/*==============================================================*/
insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (1, '油压', 'C_CLOUMN1', 0, 'TubingPressure', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (2, '套压', 'C_CLOUMN2', 0, 'CasingPressure', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (3, '回压', 'C_CLOUMN3', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (4, '井口温度', 'C_CLOUMN4', 0, 'WellHeadTemperature', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (5, '运行状态', 'C_CLOUMN5', 0, 'RunStatus', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (6, '启停控制', 'C_CLOUMN6', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (7, '含水率', 'C_CLOUMN7', 0, 'VolumeWaterCut', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (8, '动液面', 'C_CLOUMN8', 0, 'ProducingfluidLevel', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (9, 'A相电流', 'C_CLOUMN9', 0, 'IA', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (10, 'B相电流', 'C_CLOUMN10', 0, 'IB', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (11, 'C相电流', 'C_CLOUMN11', 0, 'IC', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (12, 'A相电压', 'C_CLOUMN12', 0, 'VA', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (13, 'B相电压', 'C_CLOUMN13', 0, 'VB', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (14, 'C相电压', 'C_CLOUMN14', 0, 'VC', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (15, '有功功耗', 'C_CLOUMN15', 0, 'TotalKWattH', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (16, '无功功耗', 'C_CLOUMN16', 0, 'TotalKVarH', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (17, '有功功率', 'C_CLOUMN17', 0, 'Watt3', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (18, '无功功率', 'C_CLOUMN18', 0, 'Var3', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (19, '反向功率', 'C_CLOUMN19', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (20, '功率因数', 'C_CLOUMN20', 0, 'PF3', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (21, '变频设置频率', 'C_CLOUMN21', 0, 'SetFrequency', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (22, '变频运行频率', 'C_CLOUMN22', 0, 'RunFrequency', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (23, '螺杆泵转速', 'C_CLOUMN23', 0, 'RPM', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (24, '螺杆泵扭矩', 'C_CLOUMN24', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (25, '功图采集间隔', 'C_CLOUMN25', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (26, '手动采集功图', 'C_CLOUMN26', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (27, '功图设置点数', 'C_CLOUMN27', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (28, '功图实测点数', 'C_CLOUMN28', 0, 'FESDiagramAcqCount', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (29, '功图采集时间', 'C_CLOUMN29', 0, 'FESDiagramAcqtime', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (30, '冲次', 'C_CLOUMN30', 0, 'SPM', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (31, '冲程', 'C_CLOUMN31', 0, 'Stroke', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (32, '功图数据-位移', 'C_CLOUMN32', 0, 'Position_Curve', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (33, '功图数据-载荷', 'C_CLOUMN33', 0, 'Load_Curve', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (34, '功图数据-电流', 'C_CLOUMN34', 0, 'Current_Curve', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (35, '功图数据-功率', 'C_CLOUMN35', 0, 'Power_Curve', null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (36, '电参反演握手协议-从机写主机读状态码', 'C_CLOUMN36', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (37, '电参反演握手协议-主机写从机读状态码', 'C_CLOUMN37', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (38, '电参反演采集参数-实测点数', 'C_CLOUMN38', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (39, '电参反演采集参数-采集时间', 'C_CLOUMN39', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (40, '电参反演采集参数-冲次', 'C_CLOUMN40', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (41, '电参反演采集参数-电机输出功率', 'C_CLOUMN41', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (42, '电参反演采集参数-电机输出电流', 'C_CLOUMN42', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (43, '电参反演采集参数-电机输出扭矩', 'C_CLOUMN43', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (44, '电参反演采集参数-曲柄角度', 'C_CLOUMN44', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (45, '电参反演采集参数-电机转速', 'C_CLOUMN45', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (46, '电参反演采集参数-采样间隔', 'C_CLOUMN46', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (47, '功图计算结果-最大载荷', 'C_CLOUMN47', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (48, '功图计算结果-最小载荷', 'C_CLOUMN48', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (49, '功图计算结果-理论上载荷', 'C_CLOUMN49', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (50, '功图计算结果-理论下载荷', 'C_CLOUMN50', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (51, '功图计算结果-充满系数', 'C_CLOUMN51', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (52, '功图计算结果-抽空充满系数', 'C_CLOUMN52', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (53, '功图计算结果-产液量', 'C_CLOUMN53', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (54, '功图计算结果-产油量', 'C_CLOUMN54', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (55, '功图计算结果-产水量', 'C_CLOUMN55', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (56, '功图计算结果-理论排量', 'C_CLOUMN56', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (57, '功图计算结果-泵效', 'C_CLOUMN57', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (58, '功图计算结果-工况代码', 'C_CLOUMN58', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (59, '功图计算结果-反演动液面', 'C_CLOUMN59', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (60, '功图计算结果-液面校正差值', 'C_CLOUMN60', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (61, '功图计算结果-沉没度', 'C_CLOUMN61', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (62, '功图计算结果-下冲程最大电流', 'C_CLOUMN62', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (63, '功图计算结果-上冲程最大电流', 'C_CLOUMN63', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (64, '功图计算结果-电流平衡度', 'C_CLOUMN64', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (65, '功图计算结果-下冲程最大功率', 'C_CLOUMN65', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (66, '功图计算结果-上冲程最大功率', 'C_CLOUMN66', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (67, '功图计算结果-功率平衡度', 'C_CLOUMN67', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (68, '功图计算结果-移动距离', 'C_CLOUMN68', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (69, '功图计算结果-井下系统效率', 'C_CLOUMN69', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (70, '功图计算结果-地面系统效率', 'C_CLOUMN70', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (71, '功图计算结果-系统效率', 'C_CLOUMN71', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (72, '功图计算结果-吨液百米耗电量', 'C_CLOUMN72', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (73, '功图计算辅助参数-原油密度', 'C_CLOUMN73', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (74, '功图计算辅助参数-地层水密度', 'C_CLOUMN74', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (75, '功图计算辅助参数-天然气相对密度', 'C_CLOUMN75', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (76, '功图计算辅助参数-饱和压力', 'C_CLOUMN76', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (77, '功图计算辅助参数-油层中部深度', 'C_CLOUMN77', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (78, '功图计算辅助参数-油层中部温度', 'C_CLOUMN78', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (79, '功图计算辅助参数-油压', 'C_CLOUMN79', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (80, '功图计算辅助参数-套压', 'C_CLOUMN80', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (81, '功图计算辅助参数-井口温度', 'C_CLOUMN81', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (82, '功图计算辅助参数-含水率', 'C_CLOUMN82', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (83, '功图计算辅助参数-生产气油比', 'C_CLOUMN83', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (84, '功图计算辅助参数-动液面', 'C_CLOUMN84', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (85, '功图计算辅助参数-泵挂', 'C_CLOUMN85', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (86, '功图计算辅助参数-泵筒类型', 'C_CLOUMN86', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (87, '功图计算辅助参数-泵级别', 'C_CLOUMN87', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (88, '功图计算辅助参数-泵径', 'C_CLOUMN88', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (89, '功图计算辅助参数-柱塞长', 'C_CLOUMN89', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (90, '功图计算辅助参数-油管内径', 'C_CLOUMN90', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (91, '功图计算辅助参数-套管内径', 'C_CLOUMN91', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (92, '功图计算辅助参数-一级杆类型', 'C_CLOUMN92', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (93, '功图计算辅助参数-一级杆级别', 'C_CLOUMN93', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (94, '功图计算辅助参数-一级杆外径', 'C_CLOUMN94', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (95, '功图计算辅助参数-一级杆内径', 'C_CLOUMN95', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (96, '功图计算辅助参数-一级杆长', 'C_CLOUMN96', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (97, '功图计算辅助参数-二级杆类型', 'C_CLOUMN97', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (98, '功图计算辅助参数-二级杆级别', 'C_CLOUMN98', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (99, '功图计算辅助参数-二级杆外径', 'C_CLOUMN99', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (100, '功图计算辅助参数-二级杆内径', 'C_CLOUMN100', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (101, '功图计算辅助参数-二级杆长', 'C_CLOUMN101', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (102, '功图计算辅助参数-三级杆类型', 'C_CLOUMN102', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (103, '功图计算辅助参数-三级杆级别', 'C_CLOUMN103', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (104, '功图计算辅助参数-三级杆外径', 'C_CLOUMN104', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (105, '功图计算辅助参数-三级杆内径', 'C_CLOUMN105', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (106, '功图计算辅助参数-三级杆长', 'C_CLOUMN106', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (107, '功图计算辅助参数-四级杆类型', 'C_CLOUMN107', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (108, '功图计算辅助参数-四级杆级别', 'C_CLOUMN108', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (109, '功图计算辅助参数-四级杆外径', 'C_CLOUMN109', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (110, '功图计算辅助参数-四级杆内径', 'C_CLOUMN110', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (111, '功图计算辅助参数-四级杆长', 'C_CLOUMN111', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (112, '功图计算辅助参数-曲柄旋转方向', 'C_CLOUMN112', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (113, '功图计算辅助参数-曲柄偏置角', 'C_CLOUMN113', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (114, '功图计算辅助参数-平衡块重量1', 'C_CLOUMN114', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (115, '功图计算辅助参数-平衡块重量2', 'C_CLOUMN115', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (116, '功图计算辅助参数-平衡块重量3', 'C_CLOUMN116', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (117, '功图计算辅助参数-平衡块重量4', 'C_CLOUMN117', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (118, '功图计算辅助参数-平衡块重量5', 'C_CLOUMN118', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (119, '功图计算辅助参数-平衡块重量6', 'C_CLOUMN119', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (120, '功图计算辅助参数-平衡块重量7', 'C_CLOUMN120', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (121, '功图计算辅助参数-平衡块重量8', 'C_CLOUMN121', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (122, '功图计算辅助参数-工况干预代码', 'C_CLOUMN122', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (123, '功图计算辅助参数-净毛比', 'C_CLOUMN123', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (124, '功图计算辅助参数-净毛值', 'C_CLOUMN124', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (125, '功图计算辅助参数-反演液面校正值', 'C_CLOUMN125', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (126, '电参反演辅助参数-抽油机冲程', 'C_CLOUMN126', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (127, '电参反演辅助参数-曲柄旋转方向', 'C_CLOUMN127', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (128, '电参反演辅助参数-曲柄偏置角', 'C_CLOUMN128', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (129, '电参反演辅助参数-曲柄重心半径', 'C_CLOUMN129', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (130, '电参反演辅助参数-单块曲柄重量', 'C_CLOUMN130', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (131, '电参反演辅助参数-单块曲柄销重量', 'C_CLOUMN131', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (132, '电参反演辅助参数-结构不平衡重', 'C_CLOUMN132', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (133, '电参反演辅助参数-平衡块位置1', 'C_CLOUMN133', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (134, '电参反演辅助参数-平衡块位置2', 'C_CLOUMN134', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (135, '电参反演辅助参数-平衡块位置3', 'C_CLOUMN135', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (136, '电参反演辅助参数-平衡块位置4', 'C_CLOUMN136', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (137, '电参反演辅助参数-平衡块位置5', 'C_CLOUMN137', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (138, '电参反演辅助参数-平衡块位置6', 'C_CLOUMN138', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (139, '电参反演辅助参数-平衡块位置7', 'C_CLOUMN139', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (140, '电参反演辅助参数-平衡块位置8', 'C_CLOUMN140', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (141, '电参反演辅助参数-平衡块重量1', 'C_CLOUMN141', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (142, '电参反演辅助参数-平衡块重量2', 'C_CLOUMN142', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (143, '电参反演辅助参数-平衡块重量3', 'C_CLOUMN143', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (144, '电参反演辅助参数-平衡块重量4', 'C_CLOUMN144', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (145, '电参反演辅助参数-平衡块重量5', 'C_CLOUMN145', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (146, '电参反演辅助参数-平衡块重量6', 'C_CLOUMN146', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (147, '电参反演辅助参数-平衡块重量7', 'C_CLOUMN147', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (148, '电参反演辅助参数-平衡块重量8', 'C_CLOUMN148', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (149, '电参反演辅助参数-减速箱传动比', 'C_CLOUMN149', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (150, '电参反演辅助参数-曲柄DI初始角度', 'C_CLOUMN150', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (151, '电参反演辅助参数-反演功图插值点数', 'C_CLOUMN151', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (152, '电参反演辅助参数-地面系统效率', 'C_CLOUMN152', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (153, '电参反演辅助参数-功率中值滤波次数', 'C_CLOUMN153', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (154, '电参反演辅助参数-电流中指滤波次数', 'C_CLOUMN154', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (155, '电参反演辅助参数-功图全图滤波次数', 'C_CLOUMN155', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (156, '电参反演辅助参数-功图左侧滤波次数', 'C_CLOUMN156', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (157, '电参反演辅助参数-功图右侧滤波次数', 'C_CLOUMN157', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (158, '电参反演辅助参数-功图左侧截取百分比', 'C_CLOUMN158', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (159, '电参反演辅助参数-功图右侧截取百分比', 'C_CLOUMN159', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (160, '电参反演辅助参数-位置扭矩因数点数', 'C_CLOUMN160', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (161, '电参反演辅助参数-曲柄转角', 'C_CLOUMN161', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (162, '电参反演辅助参数-光杆位置因数', 'C_CLOUMN162', 0, null, null, 1);

insert into TBL_DATAMAPPING (ID, NAME, MAPPINGCOLUMN, PROTOCOLTYPE, CALCOLUMN, REPETITIONTIMES, MAPPINGMODE)
values (163, '电参反演辅助参数-扭矩因数', 'C_CLOUMN163', 0, null, null, 1);


/*==============================================================*/
/* 初始化TBL_RUNSTATUSCONFIG数据                                          */
/*==============================================================*/
insert into TBL_RUNSTATUSCONFIG (ID, PROTOCOL, ITEMNAME, ITEMMAPPINGCOLUMN, RUNVALUE, STOPVALUE, PROTOCOLTYPE, RESOLUTIONMODE, RUNCONDITION, STOPCONDITION)
values (1, 'protocol1', '运行状态', 'C_CLOUMN5', '1', '0', 0, 1, null, null);

/*==============================================================*/
/* 初始化TBL_ACQ_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', '抽油机A11采集单元', '永磁电机电参反演协议', '抽油机A11采集单元');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (2, 'unit2', '螺杆泵A11采集单元', '永磁电机电参反演协议', '螺杆泵A11采集单元');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (3, 'unit3', '永磁电机采集单元', '永磁电机电参反演协议', '永磁电机采集单元');


/*==============================================================*/
/* 初始化TBL_ACQ_GROUP_CONF数据                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (1, 'group1', '采集组', 60, 0, '永磁电机电参反演协议', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (2, 'group2', '控制组', 0, 0, '永磁电机电参反演协议', 1, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (3, 'group3', '采集组', 60, 60, '永磁电机电参反演协议', 0, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (4, 'group4', '控制组', 0, 0, '永磁电机电参反演协议', 1, null);

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, GROUPTIMINGINTERVAL, GROUPSAVINGINTERVAL, PROTOCOL, TYPE, REMARK)
values (5, 'group5', '采集组', 60, 60, '永磁电机电参反演协议', 0, null);

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

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, UNITID, MATRIX)
values (5, 5, 3, '0,0,0');

/*==============================================================*/
/* 初始化TBL_ACQ_ITEM2GROUP_CONF数据                                          */
/*==============================================================*/

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (2, null, '运行状态', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (3, null, '启停控制', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (4, null, 'A相电流', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (5, null, 'B相电流', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (6, null, 'C相电流', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (7, null, 'A相电压', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (8, null, 'B相电压', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (9, null, 'C相电压', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (10, null, '有功功耗', null, 1, null, '0,0,0', 1, '日用电量');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (11, null, '无功功耗', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (12, null, '有功功率', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (13, null, '无功功率', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (14, null, '反向功率', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (15, null, '功率因数', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (16, null, '变频设置频率', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (17, null, '变频运行频率', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (18, null, '功图采集间隔', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (19, null, '手动采集功图', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (20, null, '功图设置点数', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (21, null, '功图实测点数', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (22, null, '功图采集时间', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (23, null, '冲次', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (24, null, '冲程', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (25, null, '功图数据-位移', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (26, null, '功图数据-载荷', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (27, null, '功图数据-电流', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (28, null, '功图数据-功率', null, 1, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (29, null, '启停控制', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (30, null, '变频设置频率', null, 2, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (31, null, '油压', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (32, null, '套压', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (33, null, '回压', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (34, null, '井口温度', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (35, null, '运行状态', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (36, null, '启停控制', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (37, null, '含水率', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (38, null, '动液面', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (39, null, 'A相电流', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (40, null, 'B相电流', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (41, null, 'C相电流', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (42, null, 'A相电压', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (43, null, 'B相电压', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (44, null, 'C相电压', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (45, null, '有功功耗', null, 3, null, '0,0,0', 1, '日用电量');

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (46, null, '无功功耗', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (47, null, '有功功率', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (48, null, '无功功率', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (49, null, '反向功率', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (50, null, '功率因数', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (51, null, '变频设置频率', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (52, null, '变频运行频率', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (53, null, '螺杆泵转速', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (54, null, '螺杆泵扭矩', null, 3, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (55, null, '启停控制', null, 4, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (56, null, '变频设置频率', null, 4, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (57, null, '功图实测点数', null, 5, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (58, null, '功图采集时间', null, 5, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (59, null, '冲次', null, 5, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (60, null, '冲程', null, 5, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (61, null, '功图数据-位移', null, 5, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (62, null, '功图数据-载荷', null, 5, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (63, null, '功图数据-电流', null, 5, null, '0,0,0', 0, null);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, GROUPID, BITINDEX, MATRIX, DAILYTOTALCALCULATE, DAILYTOTALCALCULATENAME)
values (64, null, '功图数据-功率', null, 5, null, '0,0,0', 0, null);

/*==============================================================*/
/* 初始化TBL_ALARM_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'alarmunit1', 'A11-抽油机报警单元', '永磁电机电参反演协议', 'A11-抽油机报警单元');

insert into TBL_ALARM_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (2, 'alarmunit2', 'A11-螺杆泵报警单元', '永磁电机电参反演协议', 'A11-螺杆泵报警单元');

/*==============================================================*/
/* 初始化TBL_ALARM_ITEM2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (1, 1, null, '上线', 'online', 0, 1.000, null, null, null, null, 300, 1, 3, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (2, 1, null, '离线', 'offline', 0, 0.000, null, null, null, null, 100, 1, 3, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (3, 1, null, '抽喷', '1201', 0, 1201.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (4, 1, null, '正常', '1202', 0, 1202.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (5, 1, null, '充满不足', '1203', 0, 1203.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (6, 1, null, '供液不足', '1204', 0, 1204.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (7, 1, null, '供液极差', '1205', 0, 1205.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (8, 1, null, '抽空', '1206', 0, 1206.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (9, 1, null, '泵下堵', '1207', 0, 1207.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (10, 1, null, '气锁', '1208', 0, 1208.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (11, 1, null, '气影响', '1209', 0, 1209.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (12, 1, null, '间隙漏', '1210', 0, 1210.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (13, 1, null, '油管漏', '1211', 0, 1211.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (14, 1, null, '游动凡尔漏失', '1212', 0, 1212.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (15, 1, null, '固定凡尔漏失', '1213', 0, 1213.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (16, 1, null, '双凡尔漏失', '1214', 0, 1214.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (17, 1, null, '游动凡尔失灵/油管漏', '1215', 0, 1215.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (18, 1, null, '固定凡尔失灵', '1216', 0, 1216.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (19, 1, null, '双凡尔失灵', '1217', 0, 1217.000, null, null, null, null, 0, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (20, 1, null, '上死点别、碰', '1218', 0, 1218.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (21, 1, null, '碰泵', '1219', 0, 1219.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (22, 1, null, '活塞/底部断脱/未入工作筒', '1220', 0, 1220.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (23, 1, null, '柱塞脱出工作筒', '1221', 0, 1221.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (24, 1, null, '杆断脱', '1222', 0, 1222.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (25, 1, null, '杆(泵)卡', '1223', 0, 1223.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (26, 1, null, '结蜡', '1224', 0, 1224.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (27, 1, null, '严重结蜡', '1225', 0, 1225.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (28, 1, null, '出砂', '1226', 0, 1226.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (29, 1, null, '严重出砂', '1227', 0, 1227.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (30, 1, null, '惯性载荷大', '1230', 0, 1230.000, null, null, null, null, 300, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (31, 1, null, '应力超标', '1231', 0, 1231.000, null, null, null, null, 200, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (32, 1, null, '采集异常', '1232', 0, 1232.000, null, null, null, null, 100, 1, 4, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (33, 1, null, '停止', 'stop', 0, 0.000, null, null, null, null, 100, 1, 6, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (34, 2, null, '上线', 'online', 0, 1.000, null, null, null, null, 300, 1, 3, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (35, 2, null, '离线', 'offline', 0, 0.000, null, null, null, null, 100, 1, 3, 0, 0, 0, 60);

insert into TBL_ALARM_ITEM2UNIT_CONF (ID, UNITID, ITEMID, ITEMNAME, ITEMCODE, ITEMADDR, VALUE, UPPERLIMIT, LOWERLIMIT, HYSTERSIS, DELAY, ALARMLEVEL, ALARMSIGN, TYPE, BITINDEX, ISSENDMESSAGE, ISSENDMAIL, RETRIGGERTIME)
values (36, 2, null, '停止', 'stop', 0, 0.000, null, null, null, null, 100, 1, 6, 0, 0, 0, 60);

/*==============================================================*/
/* 初始化TBL_DISPLAY_UNIT_CONF数据                                */
/*==============================================================*/
insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, CALCULATETYPE, REMARK)
values (1, 'unit1', '抽油机A11显示单元', '永磁电机电参反演协议', 1, 1, '抽油机A11显示单元');

insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, CALCULATETYPE, REMARK)
values (2, 'unit2', '螺杆泵A11显示单元', '永磁电机电参反演协议', 2, 2, '螺杆泵A11显示单元');

insert into TBL_DISPLAY_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, ACQUNITID, CALCULATETYPE, REMARK)
values (3, 'unit3', '永磁电机显示单元', '永磁电机电参反演协议', 3, 1, '永磁电机显示单元');

/*==============================================================*/
/* 初始化TBL_DISPLAY_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (1, null, '运行状态', 'C_CLOUMN5', 1, 4, 4, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (2, null, 'A相电流', 'C_CLOUMN9', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (3, null, 'B相电流', 'C_CLOUMN10', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (4, null, 'C相电流', 'C_CLOUMN11', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (5, null, 'A相电压', 'C_CLOUMN12', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (6, null, 'B相电压', 'C_CLOUMN13', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (7, null, 'C相电压', 'C_CLOUMN14', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (8, null, '有功功耗', 'C_CLOUMN15', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (9, null, '无功功耗', 'C_CLOUMN16', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (10, null, '有功功率', 'C_CLOUMN17', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (11, null, '无功功率', 'C_CLOUMN18', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (12, null, '反向功率', 'C_CLOUMN19', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (13, null, '功率因数', 'C_CLOUMN20', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (14, null, '变频设置频率', 'C_CLOUMN21', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (15, null, '变频运行频率', 'C_CLOUMN22', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (16, null, '功图设置点数', 'C_CLOUMN27', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (17, null, '功图实测点数', 'C_CLOUMN28', 1, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (18, null, '冲次', 'C_CLOUMN30', 1, 17, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (19, null, '冲程', 'C_CLOUMN31', 1, 14, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (20, null, '在线时间', 'CommTime', 1, 1, 1, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (21, null, '在线时率', 'CommTimeEfficiency', 1, 2, 2, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (22, null, '在线区间', 'CommRange', 1, 3, 3, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (23, null, '运行时间', 'RunTime', 1, 7, 5, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (24, null, '运行时率', 'RunTimeEfficiency', 1, 8, 6, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (25, null, '运行区间', 'RunRange', 1, 9, 7, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (26, null, '工况', 'ResultName', 1, 10, 8, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (27, null, '最大载荷', 'FMax', 1, 20, 9, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b824e6"}', '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"b824e6"}', 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (28, null, '最小载荷', 'FMin', 1, 23, 10, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e25e1d"}', '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e25e1d"}', 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (29, null, '柱塞冲程', 'PlungerStroke', 1, 15, 11, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (30, null, '柱塞有效冲程', 'AvailablePlungerStroke', 1, 18, 12, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (31, null, '抽空柱塞有效冲程', 'NoLiquidAvailablePlungerStroke', 1, 27, 13, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (32, null, '充满系数', 'FullnessCoefficient', 1, 21, 14, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (33, null, '抽空充满系数', 'NoLiquidFullnessCoefficient', 1, 24, 15, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (34, null, '理论上载荷', 'UpperLoadLine', 1, 26, 16, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (35, null, '理论下载荷', 'LowerLoadLine', 1, 29, 17, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (36, null, '理论排量', 'TheoreticalProduction', 1, 25, 18, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (37, null, '瞬时产液量', 'LiquidVolumetricProduction', 1, 13, 19, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"2560d4"}', 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (38, null, '瞬时产油量', 'OilVolumetricProduction', 1, 16, 20, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (39, null, '瞬时产水量', 'WaterVolumetricProduction', 1, 19, 21, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (40, null, '柱塞有效冲程计算产量', 'AvailablePlungerStrokeProd_v', 1, 28, 22, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (41, null, '泵间隙漏失量', 'PumpClearanceleakProd_v', 1, 30, 23, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (42, null, '日累计产液量', 'LiquidVolumetricProduction_l', 1, 22, 24, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (43, null, '有功功率', 'AverageWatt', 1, 44, 25, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (44, null, '光杆功率', 'PolishRodPower', 1, 47, 26, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (45, null, '水功率', 'WaterPower', 1, 41, 27, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (46, null, '地面效率', 'SurfaceSystemEfficiency', 1, 43, 28, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (47, null, '井下效率', 'WellDownSystemEfficiency', 1, 46, 29, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (48, null, '系统效率', 'SystemEfficiency', 1, 40, 30, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (49, null, '功图面积', 'Area', 1, 42, 31, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (50, null, '吨液百米耗电量', 'EnergyPer100mLift', 1, 45, 32, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (51, null, '抽油杆伸长量', 'RodFlexLength', 1, 31, 33, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (52, null, '油管伸缩量', 'TubingFlexLength', 1, 32, 34, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (53, null, '惯性载荷增量', 'InertiaLength', 1, 33, 35, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (54, null, '冲程损失系数', 'PumpEff1', 1, 34, 36, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (55, null, '充满系数', 'PumpEff2', 1, 35, 37, null, null, '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"d4bf24"}', '{"sort":4,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d4bf24"}', 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (56, null, '间隙漏失系数', 'PumpEff3', 1, 36, 38, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (57, null, '液体收缩系数', 'PumpEff4', 1, 37, 39, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (58, null, '总泵效', 'PumpEff', 1, 38, 40, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (59, null, '泵入口压力', 'PumpIntakeP', 1, 61, 41, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (60, null, '泵入口温度', 'PumpIntakeT', 1, 62, 42, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (61, null, '泵入口就地气液比', 'PumpIntakeGOL', 1, 63, 43, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (62, null, '泵入口粘度', 'PumpIntakeVisl', 1, 64, 44, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (63, null, '泵入口原油体积系数', 'PumpIntakeBo', 1, 65, 45, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (64, null, '泵出口压力', 'PumpOutletP', 1, 67, 46, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (65, null, '泵出口温度', 'PumpOutletT', 1, 68, 47, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (66, null, '泵出口就地气液比', 'PumpOutletGOL', 1, 69, 48, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (67, null, '泵出口粘度', 'PumpOutletVisl', 1, 70, 49, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (68, null, '泵出口原油体积系数', 'PumpOutletBo', 1, 71, 50, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (69, null, '上冲程最大电流', 'UpStrokeIMax', 1, 50, 51, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (70, null, '下冲程最大电流', 'DownStrokeIMax', 1, 51, 52, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (71, null, '上冲程最大功率', 'UpStrokeWattMax', 1, 53, 53, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (72, null, '下冲程最大功率', 'DownStrokeWattMax', 1, 54, 54, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (73, null, '电流平衡度', 'IDegreeBalance', 1, 49, 55, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (74, null, '功率平衡度', 'WattDegreeBalance', 1, 52, 56, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (75, null, '移动距离', 'DeltaRadius', 1, 55, 57, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (76, null, '液面校正差值', 'LevelDifferenceValue', 1, 59, 58, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (77, null, '反演动液面', 'CalcProducingfluidLevel', 1, 58, 59, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (78, null, '日用电量', 'C_CLOUMN15_TOTAL', 1, null, 60, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (79, null, '启停控制', 'C_CLOUMN6', 1, 1, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (80, null, '变频设置频率', 'C_CLOUMN21', 1, 2, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (81, null, '原油密度', 'CrudeOilDensity', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (82, null, '水密度', 'WaterDensity', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (83, null, '天然气相对密度', 'NaturalGasRelativeDensity', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (84, null, '饱和压力', 'SaturationPressure', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (85, null, '油层中部深度', 'ReservoirDepth', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (86, null, '油层中部温度', 'ReservoirTemperature', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (87, null, '油压', 'TubingPressure', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (88, null, '套压', 'CasingPressure', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (89, null, '井口温度', 'WellHeadTemperature', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (90, null, '含水率', 'WaterCut', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (91, null, '生产气油比', 'ProductionGasOilRatio', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (92, null, '动液面', 'ProducingfluidLevel', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (93, null, '泵挂', 'PumpSettingDepth', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (94, null, '泵径', 'PumpBoreDiameter', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (95, null, '反演液面校正值', 'LevelCorrectValue', 1, null, null, null, null, null, null, 3, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (96, null, '油压', 'C_CLOUMN1', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (97, null, '套压', 'C_CLOUMN2', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (98, null, '回压', 'C_CLOUMN3', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (99, null, '井口温度', 'C_CLOUMN4', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (100, null, '运行状态', 'C_CLOUMN5', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (101, null, '含水率', 'C_CLOUMN7', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (102, null, 'A相电流', 'C_CLOUMN9', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (103, null, 'B相电流', 'C_CLOUMN10', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (104, null, 'C相电流', 'C_CLOUMN11', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (105, null, 'A相电压', 'C_CLOUMN12', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (106, null, 'B相电压', 'C_CLOUMN13', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (107, null, 'C相电压', 'C_CLOUMN14', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (108, null, '有功功耗', 'C_CLOUMN15', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (109, null, '无功功耗', 'C_CLOUMN16', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (110, null, '有功功率', 'C_CLOUMN17', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (111, null, '无功功率', 'C_CLOUMN18', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (112, null, '反向功率', 'C_CLOUMN19', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (113, null, '功率因数', 'C_CLOUMN20', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (114, null, '变频设置频率', 'C_CLOUMN21', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (115, null, '变频运行频率', 'C_CLOUMN22', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (116, null, '螺杆泵转速', 'C_CLOUMN23', 2, null, null, null, null, '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"3100ff"}', '{"sort":1,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"0008ff"}', 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (117, null, '螺杆泵扭矩', 'C_CLOUMN24', 2, null, null, null, null, null, null, 0, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (118, null, '在线时间', 'CommTime', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (119, null, '在线时率', 'CommTimeEfficiency', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (120, null, '在线区间', 'CommRange', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (121, null, '运行时间', 'RunTime', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (122, null, '运行时率', 'RunTimeEfficiency', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (123, null, '运行区间', 'RunRange', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (124, null, '理论排量', 'TheoreticalProduction', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (125, null, '瞬时产液量', 'LiquidVolumetricProduction', 2, null, null, null, null, '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"1fe4f8"}', '{"sort":2,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":false,"color":"31d8e9"}', 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (126, null, '瞬时产油量', 'OilVolumetricProduction', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (127, null, '瞬时产水量', 'WaterVolumetricProduction', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (128, null, '日累计产液量', 'LiquidVolumetricProduction_l', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (129, null, '瞬时产液量', 'LiquidWeightProduction', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (130, null, '瞬时产油量', 'OilWeightProduction', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (131, null, '瞬时产水量', 'WaterWeightProduction', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (132, null, '日累计产液量', 'LiquidWeightProduction_l', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (133, null, '有功功率', 'AverageWatt', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (134, null, '水功率', 'WaterPower', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (135, null, '系统效率', 'SystemEfficiency', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (136, null, '冲程损失系数', 'PumpEff1', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (137, null, '充满系数', 'PumpEff2', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (138, null, '总泵效', 'PumpEff', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (139, null, '泵入口压力', 'PumpIntakeP', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (140, null, '泵入口温度', 'PumpIntakeT', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (141, null, '泵入口就地气液比', 'PumpIntakeGOL', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (142, null, '泵入口粘度', 'PumpIntakeVisl', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (143, null, '泵入口原油体积系数', 'PumpIntakeBo', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (144, null, '泵出口压力', 'PumpOutletP', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (145, null, '泵出口温度', 'PumpOutletT', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (146, null, '泵出口就地气液比', 'PumpOutletGOL', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (147, null, '泵出口粘度', 'PumpOutletVisl', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (148, null, '泵出口原油体积系数', 'PumpOutletBo', 2, null, null, null, null, null, null, 1, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (149, null, '启停控制', 'C_CLOUMN6', 2, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, HISTORYSORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX)
values (150, null, '变频设置频率', 'C_CLOUMN21', 2, null, null, null, null, null, null, 2, '0,0,0');

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR)
values (151, null, '功图实测点数', 'C_CLOUMN28', 3, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR)
values (152, null, '功图采集时间', 'C_CLOUMN29', 3, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR)
values (153, null, '冲次', 'C_CLOUMN30', 3, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR)
values (154, null, '冲程', 'C_CLOUMN31', 3, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR)
values (155, null, '功图数据-位移', 'C_CLOUMN32', 3, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR)
values (156, null, '功图数据-载荷', 'C_CLOUMN33', 3, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR)
values (157, null, '功图数据-电流', 'C_CLOUMN34', 3, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null);

insert into TBL_DISPLAY_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, REALTIMESORT, BITINDEX, SHOWLEVEL, REALTIMECURVECONF, HISTORYCURVECONF, TYPE, MATRIX, HISTORYSORT, REALTIMECOLOR, REALTIMEBGCOLOR, HISTORYCOLOR, HISTORYBGCOLOR)
values (158, null, '功图数据-功率', 'C_CLOUMN35', 3, null, null, null, null, null, 0, '0,0,0', null, null, null, null, null);

/*==============================================================*/
/* 初始化TBL_REPORT_UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (1, 'unit1', '抽油机井报表单元一', 'oilWell_Pumping', 'oilWell_PumpingProductionReport', 1, 'oilWell_PumpingDailyReport', 1);

insert into TBL_REPORT_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, SINGLEWELLRANGEREPORTTEMPLATE, PRODUCTIONREPORTTEMPLATE, SORT, SINGLEWELLDAILYREPORTTEMPLATE, CALCULATETYPE)
values (2, 'unit2', '螺杆泵井报表单元一', 'oilWell_ScrewPump', 'oilWell_ScrewPumpProductionReoirt', 2, 'oilWell_ScrewPumpDailyReport', 2);
/*==============================================================*/
/* 初始化TBL_REPORT_ITEMS2UNIT_CONF数据                                          */
/*==============================================================*/
insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (1, null, '日期', 'CalDate', 1, 2, null, null, null, null, null, 3, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (2, null, '在线时间', 'CommTime', 1, 3, null, null, null, null, null, 2, 0, '0,0,0', 1, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (3, null, '在线区间', 'CommRange', 1, 4, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (4, null, '在线时率', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (5, null, '运行时间', 'RunTime', 1, 6, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (6, null, '运行区间', 'RunRange', 1, 7, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (7, null, '运行时率', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (8, null, '工况', 'ResultName', 1, 9, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (9, null, '优化建议', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (10, null, '日累计产液量', 'LiquidWeightProduction', 1, 11, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b96161"}', null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (11, null, '日累计产油量', 'OilWeightProduction', 1, 12, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"e99314"}', null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (12, null, '日累计产水量', 'WaterWeightProduction', 1, 13, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"19c2eb"}', null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (13, null, '重量含水率', 'WeightWaterCut', 1, 14, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (14, null, '充满系数', 'FullnessCoefficient', 1, 15, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (15, null, '泵挂', 'PumpSettingDepth', 1, 16, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (16, null, '动液面', 'ProducingfluidLevel', 1, 17, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (17, null, '沉没度', 'Submergence', 1, 18, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (18, null, '功率平衡度', 'WattDegreeBalance', 1, 19, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (19, null, '电流平衡度', 'IDegreeBalance', 1, 20, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (20, null, '移动距离', 'DeltaRadius', 1, 21, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (21, null, '系统效率', 'SystemEfficiency', 1, 22, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (22, null, '地面效率', 'SurfaceSystemEfficiency', 1, 23, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (23, null, '井下效率', 'WellDownSystemEfficiency', 1, 24, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (24, null, '吨液百米耗电量', 'EnergyPer100mLift', 1, 25, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (25, null, '有功功耗', 'C_CLOUMN15', 1, 26, null, null, null, null, null, 2, 0, '0,0,0', null, 6, '采集');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (26, null, '备注', 'Remark', 1, 27, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '录入');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (27, null, '井名', 'DeviceName', 1, 2, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '录入');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (28, null, '日期', 'CalDate', 1, 3, null, 0, 0, null, null, 3, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (29, null, '在线时间', 'CommTime', 1, 4, null, 0, 0, null, null, 2, 1, '0,0,0', 1, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (30, null, '在线区间', 'CommRange', 1, 5, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (31, null, '在线时率', 'CommTimeEfficiency', 1, 6, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (32, null, '运行时间', 'RunTime', 1, 7, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (33, null, '运行区间', 'RunRange', 1, 8, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (34, null, '运行时率', 'RunTimeEfficiency', 1, 9, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (35, null, '工况', 'ResultName', 1, 10, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (36, null, '优化建议', 'OptimizationSuggestion', 1, 11, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (37, null, '日累计产液量', 'LiquidWeightProduction', 1, 12, null, 1, 1, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"b66565"}', 1, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (38, null, '日累计产油量', 'OilWeightProduction', 1, 13, null, 1, 1, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"ebbc1a"}', 1, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (39, null, '日累计产水量', 'WaterWeightProduction', 1, 14, null, 1, 1, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1beef3"}', 1, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (40, null, '重量含水率', 'WeightWaterCut', 1, 15, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (41, null, '充满系数', 'FullnessCoefficient', 1, 16, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (42, null, '泵挂', 'PumpSettingDepth', 1, 17, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (43, null, '动液面', 'ProducingfluidLevel', 1, 18, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (44, null, '沉没度', 'Submergence', 1, 19, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (45, null, '功率平衡度', 'WattDegreeBalance', 1, 20, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (46, null, '电流平衡度', 'IDegreeBalance', 1, 21, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (47, null, '移动距离', 'DeltaRadius', 1, 22, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (48, null, '系统效率', 'SystemEfficiency', 1, 23, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (49, null, '地面效率', 'SurfaceSystemEfficiency', 1, 24, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (50, null, '井下效率', 'WellDownSystemEfficiency', 1, 25, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (51, null, '吨液百米耗电量', 'EnergyPer100mLift', 1, 26, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (52, null, '有功功耗', 'C_CLOUMN15', 1, 27, null, 1, 1, null, null, 2, 1, '0,0,0', null, 6, '采集');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (53, null, '备注', 'Remark', 1, 28, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '录入');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (54, null, '时间', 'CalTime', 1, 2, null, null, null, null, null, 4, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (55, null, '在线时间', 'CommTime', 1, 3, null, null, null, null, null, 2, 2, '0,0,0', 1, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (56, null, '在线区间', 'CommRange', 1, 4, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (57, null, '在线时率', 'CommTimeEfficiency', 1, 5, null, null, null, null, null, 2, 2, '0,0,0', 2, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (58, null, '运行时间', 'RunTime', 1, 6, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (59, null, '运行区间', 'RunRange', 1, 7, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (60, null, '运行时率', 'RunTimeEfficiency', 1, 8, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (61, null, '工况', 'ResultName', 1, 9, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (62, null, '优化建议', 'OptimizationSuggestion', 1, 10, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (63, null, '瞬时产液量', 'RealtimeLiquidWeightProduction', 1, 11, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (64, null, '瞬时产油量', 'RealtimeOilWeightProduction', 1, 12, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (65, null, '瞬时产水量', 'RealtimeWaterWeightProduction', 1, 13, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (66, null, '日累计产液量', 'LiquidWeightProduction', 1, 14, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"c18f8f"}', null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (67, null, '日累计产油量', 'OilWeightProduction', 1, 15, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"232432"}', null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (68, null, '日累计产水量', 'WaterWeightProduction', 1, 16, null, null, null, '{"sort":3,"lineWidth":2,"dashStyle":"Solid","yAxisOpposite":true,"color":"3abaca"}', null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (69, null, '重量含水率', 'WeightWaterCut', 1, 17, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (70, null, '充满系数', 'FullnessCoefficient', 1, 18, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (71, null, '泵挂', 'PumpSettingDepth', 1, 19, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (72, null, '动液面', 'ProducingfluidLevel', 1, 20, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (73, null, '沉没度', 'Submergence', 1, 21, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (74, null, '功率平衡度', 'WattDegreeBalance', 1, 22, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (75, null, '电流平衡度', 'IDegreeBalance', 1, 23, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (76, null, '移动距离', 'DeltaRadius', 1, 24, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (77, null, '系统效率', 'SystemEfficiency', 1, 25, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (78, null, '地面效率', 'SurfaceSystemEfficiency', 1, 26, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (79, null, '井下效率', 'WellDownSystemEfficiency', 1, 27, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (80, null, '吨液百米耗电量', 'EnergyPer100mLift', 1, 28, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (81, null, '有功功耗', 'C_CLOUMN15', 1, 29, null, null, null, null, null, 2, 2, '0,0,0', null, 6, '采集');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (82, null, '备注', 'Remark', 1, 30, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '录入');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (83, null, '日期', 'CalDate', 2, 2, null, null, null, null, null, 3, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (84, null, '在线时间', 'CommTime', 2, 3, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (85, null, '在线区间', 'CommRange', 2, 4, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (86, null, '在线时率', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (87, null, '运行时间', 'RunTime', 2, 6, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (88, null, '运行区间', 'RunRange', 2, 7, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (89, null, '运行时率', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (90, null, '日累计产液量', 'LiquidWeightProduction', 2, 9, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (91, null, '日累计产油量', 'OilWeightProduction', 2, 10, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"4f4444"}', null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (92, null, '日累计产水量', 'WaterWeightProduction', 2, 11, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"31bbbe"}', null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (93, null, '重量含水率', 'WeightWaterCut', 2, 12, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (94, null, '转速', 'RPM', 2, 13, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (95, null, '泵挂', 'PumpSettingDepth', 2, 14, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (96, null, '动液面', 'ProducingfluidLevel', 2, 15, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (97, null, '沉没度', 'Submergence', 2, 16, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (98, null, '系统效率', 'SystemEfficiency', 2, 17, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (99, null, '吨液百米耗电量', 'EnergyPer100mLift', 2, 18, null, null, null, null, null, 2, 0, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (100, null, '有功功耗', 'C_CLOUMN15', 2, 19, null, null, null, null, null, 2, 0, '0,0,0', null, 6, '采集');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (101, null, '备注', 'Remark', 2, 20, null, null, null, null, null, 1, 0, '0,0,0', null, 0, '录入');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (102, null, '井名', 'DeviceName', 2, 2, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '录入');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (103, null, '日期', 'CalDate', 2, 3, null, 0, 0, null, null, 3, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (104, null, '在线时间', 'CommTime', 2, 4, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (105, null, '在线区间', 'CommRange', 2, 5, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (106, null, '在线时率', 'CommTimeEfficiency', 2, 6, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (107, null, '运行时间', 'RunTime', 2, 7, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (108, null, '运行区间', 'RunRange', 2, 8, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (109, null, '运行时率', 'RunTimeEfficiency', 2, 9, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (110, null, '日累计产液量', 'LiquidWeightProduction', 2, 10, null, 1, 1, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"e40c54"}', 1, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (111, null, '日累计产油量', 'OilWeightProduction', 2, 11, null, 1, 1, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"d7bb14"}', 1, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (112, null, '日累计产水量', 'WaterWeightProduction', 2, 12, null, 1, 1, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"1dbfb4"}', 1, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (113, null, '重量含水率', 'WeightWaterCut', 2, 13, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (114, null, '转速', 'RPM', 2, 14, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (115, null, '泵挂', 'PumpSettingDepth', 2, 15, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (116, null, '动液面', 'ProducingfluidLevel', 2, 16, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (117, null, '沉没度', 'Submergence', 2, 17, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (118, null, '系统效率', 'SystemEfficiency', 2, 18, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (119, null, '吨液百米耗电量', 'EnergyPer100mLift', 2, 19, null, 0, 0, null, null, 2, 1, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (120, null, '有功功耗', 'C_CLOUMN15', 2, 20, null, 1, 0, null, null, 2, 1, '0,0,0', null, 6, '采集');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (121, null, '备注', 'Remark', 2, 21, null, 0, 0, null, null, 1, 1, '0,0,0', null, 0, '录入');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (122, null, '时间', 'CalTime', 2, 2, null, null, null, null, null, 4, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (123, null, '在线时间', 'CommTime', 2, 3, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (124, null, '在线区间', 'CommRange', 2, 4, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (125, null, '在线时率', 'CommTimeEfficiency', 2, 5, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (126, null, '运行时间', 'RunTime', 2, 6, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (127, null, '运行区间', 'RunRange', 2, 7, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (128, null, '运行时率', 'RunTimeEfficiency', 2, 8, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (129, null, '瞬时产液量', 'RealtimeLiquidVolumetricProduction', 2, 9, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (130, null, '瞬时产油量', 'RealtimeOilVolumetricProduction', 2, 10, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (131, null, '瞬时产水量', 'RealtimeWaterVolumetricProduction', 2, 11, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (132, null, '日累计产液量', 'LiquidWeightProduction', 2, 12, null, null, null, '{"sort":1,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":false,"color":"ff0000"}', null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (133, null, '日累计产油量', 'OilWeightProduction', 2, 13, null, null, null, '{"sort":2,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"995353"}', null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (134, null, '日累计产水量', 'WaterWeightProduction', 2, 14, null, null, null, '{"sort":3,"lineWidth":3,"dashStyle":"Solid","yAxisOpposite":true,"color":"57a6a8"}', null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (135, null, '重量含水率', 'WeightWaterCut', 2, 15, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (136, null, '转速', 'RPM', 2, 16, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (137, null, '泵挂', 'PumpSettingDepth', 2, 17, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (138, null, '动液面', 'ProducingfluidLevel', 2, 18, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (139, null, '沉没度', 'Submergence', 2, 19, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (140, null, '系统效率', 'SystemEfficiency', 2, 20, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (141, null, '吨液百米耗电量', 'EnergyPer100mLift', 2, 21, null, null, null, null, null, 2, 2, '0,0,0', null, 0, '计算');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (142, null, '有功功耗', 'C_CLOUMN15', 2, 22, null, null, null, null, null, 2, 2, '0,0,0', null, 6, '采集');

insert into TBL_REPORT_ITEMS2UNIT_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, UNITID, SORT, SHOWLEVEL, SUMSIGN, AVERAGESIGN, REPORTCURVECONF, CURVESTATTYPE, DATATYPE, REPORTTYPE, MATRIX, PREC, TOTALTYPE, DATASOURCE)
values (143, null, '备注', 'Remark', 2, 23, null, null, null, null, null, 1, 2, '0,0,0', null, 0, '录入');


/*==============================================================*/
/* 初始化TBL_PROTOCOLINSTANCE数据                                 */
/*==============================================================*/
insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (1, '抽油机A11MODBUS实例', 'instance1', 'modbus-tcp', 'modbus-tcp', 1, 'BB01', '0B', 0, 1, 'BB01', '0B', 100, 1, 1);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (2, '螺杆泵A11MODBUS实例', 'instance2', 'modbus-tcp', 'modbus-tcp', 1, 'BB01', '0B', 0, 1, 'BB01', '0B', 100, 2, 2);

insert into TBL_PROTOCOLINSTANCE (ID, NAME, CODE, ACQPROTOCOLTYPE, CTRLPROTOCOLTYPE, SIGNINPREFIXSUFFIXHEX, SIGNINPREFIX, SIGNINSUFFIX, SIGNINIDHEX, HEARTBEATPREFIXSUFFIXHEX, HEARTBEATPREFIX, HEARTBEATSUFFIX, PACKETSENDINTERVAL, UNITID, SORT)
values (3, '永磁电机采控实例', 'instance3', 'modbus-rtu', 'modbus-rtu', 1, 'AA01', '0D', 1, 1, 'AA01', '0D', 100, 3, 3);


/*==============================================================*/
/* 初始化tbl_protocolalarminstance数据                            */
/*==============================================================*/
insert into TBL_PROTOCOLALARMINSTANCE (ID, NAME, CODE, ALARMUNITID, SORT)
values (1, '抽油机A11报警实例', 'alarminstance1', 1, 1);

insert into TBL_PROTOCOLALARMINSTANCE (ID, NAME, CODE, ALARMUNITID, SORT)
values (2, '螺杆泵A11报警实例', 'alarminstance2', 2, 2);

/*==============================================================*/
/* 初始化tbl_protocoldisplayinstance数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLDISPLAYINSTANCE (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (1, '抽油机A11显示实例', 'displayinstance1', 1, 1);

insert into TBL_PROTOCOLDISPLAYINSTANCE (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (2, '螺杆泵A11显示实例', 'displayinstance2', 2, 2);

insert into TBL_PROTOCOLDISPLAYINSTANCE (ID, NAME, CODE, DISPLAYUNITID, SORT)
values (3, '永磁电机显示实例', 'displayinstance3', 3, 3);

/*==============================================================*/
/* 初始化TBL_PROTOCOLREPORTINSTANCE数据                                          */
/*==============================================================*/
insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (1, '抽油机井报表实例一', 'reportinstance1', 1, 1);

insert into TBL_PROTOCOLREPORTINSTANCE (ID, NAME, CODE, UNITID, SORT)
values (2, '螺杆泵井报表实例一', 'reportinstance2', 2, 2);