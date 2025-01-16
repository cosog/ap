/*==============================================================*/
/* TBL_ROLE                                                     */
/*==============================================================*/
update TBL_ROLE t set t.REMARK='the full set of rights' where t.ROLE_NAME = '超级管理员';
update TBL_ROLE t set t.ROLE_NAME='administrator' where t.ROLE_NAME = '超级管理员';
update TBL_ROLE t set t.REMARK='viewing and managing data' where t.ROLE_NAME = '软件管理员';
update TBL_ROLE t set t.ROLE_NAME='softwarevalet' where t.ROLE_NAME = '软件管理员';
update TBL_ROLE t set t.REMARK='viewing data' where t.ROLE_NAME = '应用分析员';
update TBL_ROLE t set t.ROLE_NAME='analyst' where t.ROLE_NAME = '应用分析员';
update TBL_ROLE t set t.REMARK='viewing data' where t.ROLE_NAME = '一般操作员';
update TBL_ROLE t set t.ROLE_NAME='operator' where t.ROLE_NAME = '一般操作员';


/*==============================================================*/
/* TBL_PROTOCOL                                                 */
/*==============================================================*/
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
  UPDATE TBL_PROTOCOL T SET t.NAME='A11 Protocol', T.ITEMS = clobValue WHERE t.CODE='protocol1';  
  COMMIT;  
END;  
/

/*==============================================================*/
/* TBL_DATAMAPPING                                              */
/*==============================================================*/
update TBL_DATAMAPPING t set t.NAME='Tubing Pressure' where t.NAME='油压';
update TBL_DATAMAPPING t set t.NAME='Casing Pressure' where t.NAME='套压';
update TBL_DATAMAPPING t set t.NAME='Back Pressure' where t.NAME='回压';
update TBL_DATAMAPPING t set t.NAME='Well Head Temperature' where t.NAME='井口温度';
update TBL_DATAMAPPING t set t.NAME='Run Status' where t.NAME='运行状态';
update TBL_DATAMAPPING t set t.NAME='Water Cut' where t.NAME='含水率';
update TBL_DATAMAPPING t set t.NAME='Producing Fluid Level' where t.NAME='动液面';
update TBL_DATAMAPPING t set t.NAME='A Phase Current' where t.NAME='A相电流';
update TBL_DATAMAPPING t set t.NAME='B Phase Current' where t.NAME='B相电流';
update TBL_DATAMAPPING t set t.NAME='C Phase Current' where t.NAME='C相电流';
update TBL_DATAMAPPING t set t.NAME='A Phase Voltage' where t.NAME='A相电压';
update TBL_DATAMAPPING t set t.NAME='B Phase Voltage' where t.NAME='B相电压';
update TBL_DATAMAPPING t set t.NAME='C Phase Voltage' where t.NAME='C相电压';
update TBL_DATAMAPPING t set t.NAME='Active Power Consumption' where t.NAME='有功功耗';
update TBL_DATAMAPPING t set t.NAME='Reactive Power Consumption' where t.NAME='无功功耗';
update TBL_DATAMAPPING t set t.NAME='Active Power' where t.NAME='有功功率';
update TBL_DATAMAPPING t set t.NAME='Reactive Power' where t.NAME='无功功率';
update TBL_DATAMAPPING t set t.NAME='Reverse Power' where t.NAME='反向功率';
update TBL_DATAMAPPING t set t.NAME='Power Factor' where t.NAME='功率因数';
update TBL_DATAMAPPING t set t.NAME='Running Frequency' where t.NAME='变频运行频率';
update TBL_DATAMAPPING t set t.NAME='Screw Speed' where t.NAME='螺杆泵转速';
update TBL_DATAMAPPING t set t.NAME='Screw Torque' where t.NAME='螺杆泵扭矩';
update TBL_DATAMAPPING t set t.NAME='Interval For Surface Diagram Collection' where t.NAME='功图采集间隔';
update TBL_DATAMAPPING t set t.NAME='Manual Collection Of Surface Diagram' where t.NAME='手动采集功图';
update TBL_DATAMAPPING t set t.NAME='Surface Diagram Sets Points' where t.NAME='功图设置点数';
update TBL_DATAMAPPING t set t.NAME='Surface Diagram Measured Points' where t.NAME='功图实测点数';
update TBL_DATAMAPPING t set t.NAME='Surface Diagram Acquisition Time' where t.NAME='功图采集时间';
update TBL_DATAMAPPING t set t.NAME='SPM' where t.NAME='冲次';
update TBL_DATAMAPPING t set t.NAME='Stroke' where t.NAME='冲程';
update TBL_DATAMAPPING t set t.NAME='Diagram Data Of Displacement' where t.NAME='功图数据-位移';
update TBL_DATAMAPPING t set t.NAME='Diagram Data Of Load' where t.NAME='功图数据-载荷';
update TBL_DATAMAPPING t set t.NAME='Diagram Data Of Current' where t.NAME='功图数据-电流';
update TBL_DATAMAPPING t set t.NAME='Diagram Data Of Power' where t.NAME='功图数据-功率';
update TBL_DATAMAPPING t set t.NAME='Run/Stop' where t.NAME='启停控制';
update TBL_DATAMAPPING t set t.NAME='Setting Frequency' where t.NAME='变频设置频率';

/*==============================================================*/
/* TBL_ACQ_UNIT_CONF                                            */
/*==============================================================*/
update TBL_ACQ_UNIT_CONF t set t.PROTOCOL='A11 Protocol',t.UNIT_NAME='SRP Acquisition Unit' where t.UNIT_CODE='unit1';
update TBL_ACQ_UNIT_CONF t set t.PROTOCOL='A11 Protocol',t.UNIT_NAME='PCP Acquisition Unit' where t.UNIT_CODE='unit2';

/*==============================================================*/
/* TBL_ACQ_GROUP_CONF                                           */
/*==============================================================*/
update TBL_ACQ_GROUP_CONF t set t.PROTOCOL='A11 Protocol',t.GROUP_NAME='AcquisitionGroup' where t.TYPE=0;
update TBL_ACQ_GROUP_CONF t set t.PROTOCOL='A11 Protocol',t.GROUP_NAME='ControlGroup' where t.TYPE=1;


/*==============================================================*/
/* TBL_ACQ_ITEM2GROUP_CONF                                      */
/*==============================================================*/
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Tubing Pressure' where t.ITEMNAME='油压';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Casing Pressure' where t.ITEMNAME='套压';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Back Pressure' where t.ITEMNAME='回压';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Well Head Temperature' where t.ITEMNAME='井口温度';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Run Status' where t.ITEMNAME='运行状态';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Water Cut' where t.ITEMNAME='含水率';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Producing Fluid Level' where t.ITEMNAME='动液面';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='A Phase Current' where t.ITEMNAME='A相电流';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='B Phase Current' where t.ITEMNAME='B相电流';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='C Phase Current' where t.ITEMNAME='C相电流';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='A Phase Voltage' where t.ITEMNAME='A相电压';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='B Phase Voltage' where t.ITEMNAME='B相电压';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='C Phase Voltage' where t.ITEMNAME='C相电压';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Active Power Consumption' where t.ITEMNAME='有功功耗';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Reactive Power Consumption' where t.ITEMNAME='无功功耗';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Active Power' where t.ITEMNAME='有功功率';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Reactive Power' where t.ITEMNAME='无功功率';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Reverse Power' where t.ITEMNAME='反向功率';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Power Factor' where t.ITEMNAME='功率因数';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Running Frequency' where t.ITEMNAME='变频运行频率';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Screw Speed' where t.ITEMNAME='螺杆泵转速';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Screw Torque' where t.ITEMNAME='螺杆泵扭矩';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Interval For Surface Diagram Collection' where t.ITEMNAME='功图采集间隔';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Manual Collection Of Surface Diagram' where t.ITEMNAME='手动采集功图';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Surface Diagram Sets Points' where t.ITEMNAME='功图设置点数';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Surface Diagram Measured Points' where t.ITEMNAME='功图实测点数';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Surface Diagram Acquisition Time' where t.ITEMNAME='功图采集时间';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='SPM' where t.ITEMNAME='冲次';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Stroke' where t.ITEMNAME='冲程';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Diagram Data Of Displacement' where t.ITEMNAME='功图数据-位移';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Diagram Data Of Load' where t.ITEMNAME='功图数据-载荷';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Diagram Data Of Current' where t.ITEMNAME='功图数据-电流';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Diagram Data Of Power' where t.ITEMNAME='功图数据-功率';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Run/Stop' where t.ITEMNAME='启停控制';
update TBL_ACQ_ITEM2GROUP_CONF t set t.ITEMNAME='Setting Frequency' where t.ITEMNAME='变频设置频率';


/*==============================================================*/
/* TBL_ALARM_UNIT_CONF                                          */
/*==============================================================*/
update TBL_ALARM_UNIT_CONF set t.PROTOCOL='A11 Protocol',t.UNIT_NAME='SRP Alarm Unit' where t.UNIT_CODE='alarmunit1';
update TBL_ALARM_UNIT_CONF set t.PROTOCOL='A11 Protocol',t.UNIT_NAME='PCP Alarm Unit' where t.UNIT_CODE='alarmunit2';


/*==============================================================*/
/* TBL_ALARM_ITEM2UNIT_CONF                                     */
/*==============================================================*/
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Tubing Pressure' where t.ITEMNAME='油压';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Casing Pressure' where t.ITEMNAME='套压';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Back Pressure' where t.ITEMNAME='回压';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Well Head Temperature' where t.ITEMNAME='井口温度';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Run Status' where t.ITEMNAME='运行状态';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Water Cut' where t.ITEMNAME='含水率';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Producing Fluid Level' where t.ITEMNAME='动液面';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='A Phase Current' where t.ITEMNAME='A相电流';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='B Phase Current' where t.ITEMNAME='B相电流';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='C Phase Current' where t.ITEMNAME='C相电流';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='A Phase Voltage' where t.ITEMNAME='A相电压';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='B Phase Voltage' where t.ITEMNAME='B相电压';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='C Phase Voltage' where t.ITEMNAME='C相电压';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Active Power Consumption' where t.ITEMNAME='有功功耗';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Reactive Power Consumption' where t.ITEMNAME='无功功耗';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Active Power' where t.ITEMNAME='有功功率';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Reactive Power' where t.ITEMNAME='无功功率';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Reverse Power' where t.ITEMNAME='反向功率';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Power Factor' where t.ITEMNAME='功率因数';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Running Frequency' where t.ITEMNAME='变频运行频率';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Screw Speed' where t.ITEMNAME='螺杆泵转速';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Screw Torque' where t.ITEMNAME='螺杆泵扭矩';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Interval For Surface Diagram Collection' where t.ITEMNAME='功图采集间隔';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Manual Collection Of Surface Diagram' where t.ITEMNAME='手动采集功图';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Surface Diagram Sets Points' where t.ITEMNAME='功图设置点数';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Surface Diagram Measured Points' where t.ITEMNAME='功图实测点数';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Surface Diagram Acquisition Time' where t.ITEMNAME='功图采集时间';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='SPM' where t.ITEMNAME='冲次';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Stroke' where t.ITEMNAME='冲程';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Diagram Data Of Displacement' where t.ITEMNAME='功图数据-位移';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Diagram Data Of Load' where t.ITEMNAME='功图数据-载荷';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Diagram Data Of Current' where t.ITEMNAME='功图数据-电流';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Diagram Data Of Power' where t.ITEMNAME='功图数据-功率';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Run/Stop' where t.ITEMNAME='启停控制';
update TBL_ALARM_ITEM2UNIT_CONF t set t.ITEMNAME='Setting Frequency' where t.ITEMNAME='变频设置频率';

/*==============================================================*/
/* TBL_DISPLAY_UNIT_CONF                                        */
/*==============================================================*/
update TBL_DISPLAY_UNIT_CONF set t.PROTOCOL='A11 Protocol',t.UNIT_NAME='SRP Display Unit',t.REMARK='SRP Display Unit' where t.UNIT_CODE='unit1';
update TBL_DISPLAY_UNIT_CONF set t.PROTOCOL='A11 Protocol',t.UNIT_NAME='PCP Display Unit',t.REMARK='SRP Display Unit' where t.UNIT_CODE='unit2';

/*==============================================================*/
/* TBL_DISPLAY_ITEMS2UNIT_CONF                                  */
/*==============================================================*/
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Tubing Pressure' where t.ITEMNAME='油压' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Casing Pressure' where t.ITEMNAME='套压' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Back Pressure' where t.ITEMNAME='回压' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Well Head Temperature' where t.ITEMNAME='井口温度' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Run Status' where t.ITEMNAME='运行状态' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Water Cut' where t.ITEMNAME='含水率' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Producing Fluid Level' where t.ITEMNAME='动液面' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='A Phase Current' where t.ITEMNAME='A相电流' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='B Phase Current' where t.ITEMNAME='B相电流' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='C Phase Current' where t.ITEMNAME='C相电流' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='A Phase Voltage' where t.ITEMNAME='A相电压' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='B Phase Voltage' where t.ITEMNAME='B相电压' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='C Phase Voltage' where t.ITEMNAME='C相电压' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Active Power Consumption' where t.ITEMNAME='有功功耗' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Reactive Power Consumption' where t.ITEMNAME='无功功耗' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Active Power' where t.ITEMNAME='有功功率' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Reactive Power' where t.ITEMNAME='无功功率' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Reverse Power' where t.ITEMNAME='反向功率' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Power Factor' where t.ITEMNAME='功率因数' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Running Frequency' where t.ITEMNAME='变频运行频率' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Screw Speed' where t.ITEMNAME='螺杆泵转速' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Screw Torque' where t.ITEMNAME='螺杆泵扭矩' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Interval For Surface Diagram Collection' where t.ITEMNAME='功图采集间隔' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Manual Collection Of Surface Diagram' where t.ITEMNAME='手动采集功图' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Surface Diagram Sets Points' where t.ITEMNAME='功图设置点数' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Surface Diagram Measured Points' where t.ITEMNAME='功图实测点数' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Surface Diagram Acquisition Time' where t.ITEMNAME='功图采集时间' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='SPM' where t.ITEMNAME='冲次' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Stroke' where t.ITEMNAME='冲程' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Diagram Data Of Displacement' where t.ITEMNAME='功图数据-位移' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Diagram Data Of Load' where t.ITEMNAME='功图数据-载荷' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Diagram Data Of Current' where t.ITEMNAME='功图数据-电流' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Diagram Data Of Power' where t.ITEMNAME='功图数据-功率' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Run/Stop' where t.ITEMNAME='启停控制' and t.TYPE in (0,2);
update TBL_DISPLAY_ITEMS2UNIT_CONF t set t.ITEMNAME='Setting Frequency' where t.ITEMNAME='变频设置频率' and t.TYPE in (0,2);

/*==============================================================*/
/* TBL_REPORT_UNIT_CONF                                         */
/*==============================================================*/
update TBL_REPORT_UNIT_CONF set t.UNIT_NAME='SRP Report Unit' where t.UNIT_CODE='unit1';
update TBL_REPORT_UNIT_CONF set t.UNIT_NAME='PCP Report Unit' where t.UNIT_CODE='unit2';

/*==============================================================*/
/* TBL_PROTOCOLINSTANCE                                         */
/*==============================================================*/
update TBL_PROTOCOLINSTANCE t set t.NAME='SRP Acquisition Instance' where t.CODE='instance1';
update TBL_PROTOCOLINSTANCE t set t.NAME='PCP Acquisition Instance' where t.CODE='instance2';

/*==============================================================*/
/* tbl_protocolalarminstance                                    */
/*==============================================================*/
update TBL_PROTOCOLALARMINSTANCE t set t.NAME='SRP Alarm Instance' where t.CODE='alarminstance1';
update TBL_PROTOCOLALARMINSTANCE t set t.NAME='PCP Alarm Instance' where t.CODE='alarminstance2';

/*==============================================================*/
/* tbl_protocoldisplayinstance                                  */
/*==============================================================*/
update TBL_PROTOCOLINSTANCE t set t.NAME='SRP Diplay Instance' where t.CODE='displayinstance1';
update TBL_PROTOCOLINSTANCE t set t.NAME='PCP Display Instance' where t.CODE='displayinstance2';

/*==============================================================*/
/* TBL_PROTOCOLREPORTINSTANCE                                   */
/*==============================================================*/
update TBL_PROTOCOLINSTANCE t set t.NAME='SRP Report Instance' where t.CODE='reportinstance1';
update TBL_PROTOCOLINSTANCE t set t.NAME='PCP Report Instance' where t.CODE='reportinstance2';


/*==============================================================*/
/* TBL_AUXILIARYDEVICE                                          */
/*==============================================================*/
update TBL_AUXILIARYDEVICE set t.NAME='Pumping Unit' where t.name='抽油机';
update TBL_AUXILIARYDEVICE set t.MANUFACTURER='Daqing' where t.MANUFACTURER='大庆';



commit ;