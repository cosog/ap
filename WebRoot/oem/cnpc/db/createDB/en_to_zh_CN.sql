/*==============================================================*/
/* TBL_ROLE                                                     */
/*==============================================================*/
update TBL_ROLE t set t.REMARK='全部权限' where t.ROLE_NAME = 'administrator';
update TBL_ROLE t set t.ROLE_NAME='超级管理员' where t.ROLE_NAME = 'administrator';
update TBL_ROLE t set t.REMARK='数据查询、编辑、权限管理' where t.ROLE_NAME = 'softwarevalet';
update TBL_ROLE t set t.ROLE_NAME='软件管理员' where t.ROLE_NAME = 'softwarevalet';
update TBL_ROLE t set t.REMARK='数据查询' where t.ROLE_NAME = 'analyst';
update TBL_ROLE t set t.ROLE_NAME='应用分析员' where t.ROLE_NAME = 'analyst';
update TBL_ROLE t set t.REMARK='数据查询' where t.ROLE_NAME = 'operator';
update TBL_ROLE t set t.ROLE_NAME='一般操作员' where t.ROLE_NAME = 'operator';


/*==============================================================*/
/* TBL_PROTOCOL                                                 */
/*==============================================================*/
DECLARE  
  clobValue TBL_PROTOCOL.ITEMS%TYPE;  
BEGIN  
  clobValue := '[{"Title":"油压","Addr":40300,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"套压","Addr":40302,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"回压","Addr":40304,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"MPa","ResolutionMode":2,"AcqMode":"passive"},{"Title":"井口温度","Addr":40306,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"℃","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"运行状态","Addr":40317,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":0,"Meaning":"停抽"},{"Value":1,"Meaning":"运行"}]},{"Title":"启停控制","Addr":40320,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive","Meaning":[{"Value":1,"Meaning":"运行"},{"Value":2,"Meaning":"停止"}]},{"Title":"含水率","Addr":40327,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"%","ResolutionMode":2,"AcqMode":"passive"},'||
  '{"Title":"动液面","Addr":40329,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"A相电流","Addr":40351,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B相电流","Addr":40353,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C相电流","Addr":40355,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"A相电压","Addr":40357,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"B相电压","Addr":40359,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"C相电压","Addr":40361,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"V","ResolutionMode":2,"AcqMode":"passive"},{"Title":"有功功耗","Addr":40363,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"无功功耗","Addr":40365,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar·h","ResolutionMode":2,"AcqMode":"passive"},{"Title":"有功功率","Addr":40367,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"无功功率","Addr":40369,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kVar","ResolutionMode":2,"AcqMode":"passive"},{"Title":"反向功率","Addr":40371,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功率因数","Addr":40373,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频设置频率","Addr":40402,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},{"Title":"变频运行频率","Addr":40404,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"Hz","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"螺杆泵转速","Addr":40430,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"r/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"螺杆泵扭矩","Addr":40432,"StoreDataType":"float32","IFDataType":"float32","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"kN·m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图采集间隔","Addr":40981,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"手动采集功图","Addr":40982,"StoreDataType":"int16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":1,"AcqMode":"passive"},{"Title":"功图设置点数","Addr":40983,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"rw","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图实测点数","Addr":40984,"StoreDataType":"uint16","IFDataType":"int","Prec":0,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图采集时间","Addr":40985,"StoreDataType":"bcd","IFDataType":"string","Prec":0,"Quantity":24,"Ratio":1.0,"RWType":"r","Unit":"","ResolutionMode":2,"AcqMode":"passive"},'
  ||'{"Title":"冲次","Addr":40991,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"次/min","ResolutionMode":2,"AcqMode":"passive"},{"Title":"冲程","Addr":40993,"StoreDataType":"float32","IFDataType":"float32","Prec":2,"Quantity":1,"Ratio":1.0,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-位移","Addr":41001,"StoreDataType":"int16","IFDataType":"float32","Prec":3,"Quantity":250,"Ratio":0.001,"RWType":"r","Unit":"m","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-载荷","Addr":41251,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kN","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-电流","Addr":41501,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"A","ResolutionMode":2,"AcqMode":"passive"},{"Title":"功图数据-功率","Addr":41751,"StoreDataType":"int16","IFDataType":"float32","Prec":2,"Quantity":250,"Ratio":0.01,"RWType":"r","Unit":"kW","ResolutionMode":2,"AcqMode":"passive","Meaning":[]}]';
  UPDATE TBL_PROTOCOL T SET t.NAME='A11协议', T.ITEMS = clobValue WHERE t.CODE='protocol1';  
  COMMIT;  
END;  
/

/*==============================================================*/
/* TBL_DATAMAPPING                                              */
/*==============================================================*/
update TBL_DATAMAPPING t set t.NAME='油压' where t.NAME='Tubing Pressure';
update TBL_DATAMAPPING t set t.NAME='套压' where t.NAME='Casing Pressure';
update TBL_DATAMAPPING t set t.NAME='回压' where t.NAME='Back Pressure';
update TBL_DATAMAPPING t set t.NAME='井口温度' where t.NAME='Well Head Temperature';
update TBL_DATAMAPPING t set t.NAME='运行状态' where t.NAME='Run Status';
update TBL_DATAMAPPING t set t.NAME='含水率' where t.NAME='Water Cut';
update TBL_DATAMAPPING t set t.NAME='动液面' where t.NAME='Producing Fluid Level';
update TBL_DATAMAPPING t set t.NAME='A相电流' where t.NAME='A Phase Current';
update TBL_DATAMAPPING t set t.NAME='B相电流' where t.NAME='B Phase Current';
update TBL_DATAMAPPING t set t.NAME='C相电流' where t.NAME='C Phase Current';
update TBL_DATAMAPPING t set t.NAME='A相电压' where t.NAME='A Phase Voltage';
update TBL_DATAMAPPING t set t.NAME='B相电压' where t.NAME='B Phase Voltage';
update TBL_DATAMAPPING t set t.NAME='C相电压' where t.NAME='C Phase Voltage';
update TBL_DATAMAPPING t set t.NAME='有功功耗' where t.NAME='Active Power Consumption';
update TBL_DATAMAPPING t set t.NAME='无功功耗' where t.NAME='Reactive Power Consumption';
update TBL_DATAMAPPING t set t.NAME='有功功率' where t.NAME='Active Power';
update TBL_DATAMAPPING t set t.NAME='无功功率' where t.NAME='Reactive Power';
update TBL_DATAMAPPING t set t.NAME='反向功率' where t.NAME='Reverse Power';
update TBL_DATAMAPPING t set t.NAME='功率因数' where t.NAME='Power Factor';
update TBL_DATAMAPPING t set t.NAME='变频运行频率' where t.NAME='Running Frequency';
update TBL_DATAMAPPING t set t.NAME='螺杆泵转速' where t.NAME='Screw Speed';
update TBL_DATAMAPPING t set t.NAME='螺杆泵扭矩' where t.NAME='Screw Torque';
update TBL_DATAMAPPING t set t.NAME='功图采集间隔' where t.NAME='Interval For Surface Diagram Collection';
update TBL_DATAMAPPING t set t.NAME='手动采集功图' where t.NAME='Manual Collection Of Surface Diagram';
update TBL_DATAMAPPING t set t.NAME='功图设置点数' where t.NAME='Surface Diagram Sets Points';
update TBL_DATAMAPPING t set t.NAME='功图实测点数' where t.NAME='Surface Diagram Measured Points';
update TBL_DATAMAPPING t set t.NAME='功图采集时间' where t.NAME='Surface Diagram Acquisition Time';
update TBL_DATAMAPPING t set t.NAME='冲次' where t.NAME='SPM';
update TBL_DATAMAPPING t set t.NAME='冲程' where t.NAME='Stroke';
update TBL_DATAMAPPING t set t.NAME='功图数据-位移' where t.NAME='Diagram Data Of Displacement';
update TBL_DATAMAPPING t set t.NAME='功图数据-载荷' where t.NAME='Diagram Data Of Load';
update TBL_DATAMAPPING t set t.NAME='功图数据-电流' where t.NAME='Diagram Data Of Current';
update TBL_DATAMAPPING t set t.NAME='功图数据-功率' where t.NAME='Diagram Data Of Power';
update TBL_DATAMAPPING t set t.NAME='启停控制' where t.NAME='Run/Stop';
update TBL_DATAMAPPING t set t.NAME='变频设置频率' where t.NAME='Setting Frequency';

/*==============================================================*/
/* TBL_ACQ_UNIT_CONF                                            */
/*==============================================================*/
update TBL_ACQ_UNIT_CONF t set t.PROTOCOL='A11协议',t.UNIT_NAME='抽油机井采集单元' where t.UNIT_CODE='unit1';
update TBL_ACQ_UNIT_CONF t set t.PROTOCOL='A11协议',t.UNIT_NAME='螺杆泵井采集单元' where t.UNIT_CODE='unit2';

/*==============================================================*/
/* TBL_ACQ_GROUP_CONF                                           */
/*==============================================================*/
update TBL_ACQ_GROUP_CONF t set t.PROTOCOL='A11协议',t.GROUP_NAME='采集组' where t.TYPE=0;
update TBL_ACQ_GROUP_CONF t set t.PROTOCOL='A11协议',t.GROUP_NAME='控制组' where t.TYPE=1;


/*==============================================================*/
/* TBL_ACQ_ITEM2GROUP_CONF                                      */
/*==============================================================*/
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='油压' where t.ITEMNAME='Tubing Pressure';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='套压' where t.ITEMNAME='Casing Pressure';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='回压' where t.ITEMNAME='Back Pressure';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='井口温度' where t.ITEMNAME='Well Head Temperature';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='运行状态' where t.ITEMNAME='Run Status';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='含水率' where t.ITEMNAME='Water Cut';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='动液面' where t.ITEMNAME='Producing Fluid Level';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='A相电流' where t.ITEMNAME='A Phase Current';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='B相电流' where t.ITEMNAME='B Phase Current';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='C相电流' where t.ITEMNAME='C Phase Current';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='A相电压' where t.ITEMNAME='A Phase Voltage';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='B相电压' where t.ITEMNAME='B Phase Voltage';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='C相电压' where t.ITEMNAME='C Phase Voltage';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='有功功耗' where t.ITEMNAME='Active Power Consumption';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='无功功耗' where t.ITEMNAME='Reactive Power Consumption';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='有功功率' where t.ITEMNAME='Active Power';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='无功功率' where t.ITEMNAME='Reactive Power';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='反向功率' where t.ITEMNAME='Reverse Power';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='功率因数' where t.ITEMNAME='Power Factor';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='变频运行频率' where t.ITEMNAME='Running Frequency';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='螺杆泵转速' where t.ITEMNAME='Screw Speed';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='螺杆泵扭矩' where t.ITEMNAME='Screw Torque';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='功图采集间隔' where t.ITEMNAME='Interval For Surface Diagram Collection';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='手动采集功图' where t.ITEMNAME='Manual Collection Of Surface Diagram';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='功图设置点数' where t.ITEMNAME='Surface Diagram Sets Points';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='功图实测点数' where t.ITEMNAME='Surface Diagram Measured Points';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='功图采集时间' where t.ITEMNAME='Surface Diagram Acquisition Time';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='冲次' where t.ITEMNAME='SPM';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='冲程' where t.ITEMNAME='Stroke';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='功图数据-位移' where t.ITEMNAME='Diagram Data Of Displacement';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='功图数据-载荷' where t.ITEMNAME='Diagram Data Of Load';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='功图数据-电流' where t.ITEMNAME='Diagram Data Of Current';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='功图数据-功率' where t.ITEMNAME='Diagram Data Of Power';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='启停控制' where t.ITEMNAME='Run/Stop';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='变频设置频率' where t.ITEMNAME='Setting Frequency';


/*==============================================================*/
/* TBL_ALARM_UNIT_CONF                                          */
/*==============================================================*/
update TBL_ALARM_UNIT_CONF set t.PROTOCOL='A11协议',t.UNIT_NAME='抽油机井报警单元' where t.UNIT_CODE='alarmunit1';
update TBL_ALARM_UNIT_CONF set t.PROTOCOL='A11协议',t.UNIT_NAME='螺杆泵井报警单元' where t.UNIT_CODE='alarmunit2';


/*==============================================================*/
/* TBL_ALARM_ITEM2UNIT_CONF                                     */
/*==============================================================*/
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='油压' where t.ITEMNAME='Tubing Pressure';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='套压' where t.ITEMNAME='Casing Pressure';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='回压' where t.ITEMNAME='Back Pressure';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='井口温度' where t.ITEMNAME='Well Head Temperature';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='运行状态' where t.ITEMNAME='Run Status';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='含水率' where t.ITEMNAME='Water Cut';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='动液面' where t.ITEMNAME='Producing Fluid Level';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='A相电流' where t.ITEMNAME='A Phase Current';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='B相电流' where t.ITEMNAME='B Phase Current';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='C相电流' where t.ITEMNAME='C Phase Current';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='A相电压' where t.ITEMNAME='A Phase Voltage';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='B相电压' where t.ITEMNAME='B Phase Voltage';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='C相电压' where t.ITEMNAME='C Phase Voltage';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='有功功耗' where t.ITEMNAME='Active Power Consumption';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='无功功耗' where t.ITEMNAME='Reactive Power Consumption';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='有功功率' where t.ITEMNAME='Active Power';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='无功功率' where t.ITEMNAME='Reactive Power';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='反向功率' where t.ITEMNAME='Reverse Power';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='功率因数' where t.ITEMNAME='Power Factor';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='变频运行频率' where t.ITEMNAME='Running Frequency';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='螺杆泵转速' where t.ITEMNAME='Screw Speed';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='螺杆泵扭矩' where t.ITEMNAME='Screw Torque';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='功图采集间隔' where t.ITEMNAME='Interval For Surface Diagram Collection';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='手动采集功图' where t.ITEMNAME='Manual Collection Of Surface Diagram';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='功图设置点数' where t.ITEMNAME='Surface Diagram Sets Points';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='功图实测点数' where t.ITEMNAME='Surface Diagram Measured Points';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='功图采集时间' where t.ITEMNAME='Surface Diagram Acquisition Time';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='冲次' where t.ITEMNAME='SPM';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='冲程' where t.ITEMNAME='Stroke';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='功图数据-位移' where t.ITEMNAME='Diagram Data Of Displacement';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='功图数据-载荷' where t.ITEMNAME='Diagram Data Of Load';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='功图数据-电流' where t.ITEMNAME='Diagram Data Of Current';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='功图数据-功率' where t.ITEMNAME='Diagram Data Of Power';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='启停控制' where t.ITEMNAME='Run/Stop';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='变频设置频率' where t.ITEMNAME='Setting Frequency';

/*==============================================================*/
/* TBL_DISPLAY_UNIT_CONF                                        */
/*==============================================================*/
update TBL_DISPLAY_UNIT_CONF set t.PROTOCOL='A11协议',t.UNIT_NAME='抽油机井显示单元',t.REMARK='抽油机井显示单元' where t.UNIT_CODE='unit1';
update TBL_DISPLAY_UNIT_CONF set t.PROTOCOL='A11协议',t.UNIT_NAME='螺杆泵井显示单元',t.REMARK='螺杆泵井显示单元' where t.UNIT_CODE='unit2';

/*==============================================================*/
/* TBL_DISPLAY_ITEMS2UNIT_CONF                                  */
/*==============================================================*/
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='油压' where t.ITEMNAME='Tubing Pressure' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='套压' where t.ITEMNAME='Casing Pressure' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='回压' where t.ITEMNAME='Back Pressure' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='井口温度' where t.ITEMNAME='Well Head Temperature' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='运行状态' where t.ITEMNAME='Run Status' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='含水率' where t.ITEMNAME='Water Cut' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='动液面' where t.ITEMNAME='Producing Fluid Level' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='A相电流' where t.ITEMNAME='A Phase Current' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='B相电流' where t.ITEMNAME='B Phase Current' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='C相电流' where t.ITEMNAME='C Phase Current' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='A相电压' where t.ITEMNAME='A Phase Voltage' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='B相电压' where t.ITEMNAME='B Phase Voltage' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='C相电压' where t.ITEMNAME='C Phase Voltage' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='有功功耗' where t.ITEMNAME='Active Power Consumption' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='无功功耗' where t.ITEMNAME='Reactive Power Consumption' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='有功功率' where t.ITEMNAME='Active Power' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='无功功率' where t.ITEMNAME='Reactive Power' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='反向功率' where t.ITEMNAME='Reverse Power' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='功率因数' where t.ITEMNAME='Power Factor' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='变频运行频率' where t.ITEMNAME='Running Frequency' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='螺杆泵转速' where t.ITEMNAME='Screw Speed' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='螺杆泵扭矩' where t.ITEMNAME='Screw Torque' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='功图采集间隔' where t.ITEMNAME='Interval For Surface Diagram Collection' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='手动采集功图' where t.ITEMNAME='Manual Collection Of Surface Diagram' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='功图设置点数' where t.ITEMNAME='Surface Diagram Sets Points' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='功图实测点数' where t.ITEMNAME='Surface Diagram Measured Points' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='功图采集时间' where t.ITEMNAME='Surface Diagram Acquisition Time' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='冲次' where t.ITEMNAME='SPM' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='冲程' where t.ITEMNAME='Stroke' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='功图数据-位移' where t.ITEMNAME='Diagram Data Of Displacement' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='功图数据-载荷' where t.ITEMNAME='Diagram Data Of Load' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='功图数据-电流' where t.ITEMNAME='Diagram Data Of Current' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='功图数据-功率' where t.ITEMNAME='Diagram Data Of Power' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='启停控制' where t.ITEMNAME='Run/Stop' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='变频设置频率' where t.ITEMNAME='Setting Frequency' and t.TYPE in (0,2);

/*==============================================================*/
/* TBL_REPORT_UNIT_CONF                                         */
/*==============================================================*/
update TBL_REPORT_UNIT_CONF set t.UNIT_NAME='抽油机井报表单元' where t.UNIT_CODE='unit1';
update TBL_REPORT_UNIT_CONF set t.UNIT_NAME='螺杆泵井报表单元' where t.UNIT_CODE='unit2';

/*==============================================================*/
/* TBL_PROTOCOLINSTANCE                                         */
/*==============================================================*/
update TBL_PROTOCOLINSTANCE t set t.NAME='抽油机井采控实例' where t.CODE='instance1';
update TBL_PROTOCOLINSTANCE t set t.NAME='螺杆泵井采控实例' where t.CODE='instance2';

/*==============================================================*/
/* tbl_protocolalarminstance                                    */
/*==============================================================*/
update TBL_PROTOCOLALARMINSTANCE t set t.NAME='抽油机井报警实例' where t.CODE='alarminstance1';
update TBL_PROTOCOLALARMINSTANCE t set t.NAME='螺杆泵井报警实例' where t.CODE='alarminstance2';

/*==============================================================*/
/* tbl_protocoldisplayinstance                                  */
/*==============================================================*/
update TBL_PROTOCOLINSTANCE t set t.NAME='抽油机井显示实例' where t.CODE='displayinstance1';
update TBL_PROTOCOLINSTANCE t set t.NAME='螺杆泵井显示实例' where t.CODE='displayinstance2';

/*==============================================================*/
/* TBL_PROTOCOLREPORTINSTANCE                                   */
/*==============================================================*/
update TBL_PROTOCOLINSTANCE t set t.NAME='抽油机井报表实例' where t.CODE='reportinstance1';
update TBL_PROTOCOLINSTANCE t set t.NAME='螺杆泵井报表实例' where t.CODE='reportinstance2';


/*==============================================================*/
/* TBL_AUXILIARYDEVICE                                          */
/*==============================================================*/
update TBL_AUXILIARYDEVICE set t.NAME='抽油机' where t.name='Pumping Unit';
update TBL_AUXILIARYDEVICE set t.MANUFACTURER='大庆' where t.MANUFACTURER='Daqing';



commit ;