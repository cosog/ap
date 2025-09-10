/*==============================================================*/
/* View: viw_org                                         */
/*==============================================================*/
create or replace force view viw_org as
select t.org_id,t.org_code,
t.org_name_zh_cn,
substr(sys_connect_by_path(t.org_name_zh_cn,'/'),2) as allpath_zh_CN,
t.org_name_en,
substr(sys_connect_by_path(t.org_name_en,'/'),2) as allpath_en,
t.org_name_ru,
substr(sys_connect_by_path(t.org_name_ru,'/'),2) as allpath_ru,
t.org_memo,t.org_parent,t.org_seq
from tbl_org t
start with t.org_parent=0
connect by   t.org_parent= prior t.org_id;
/

/*==============================================================*/
/* View: viw_acqrawdata                                         */
/*==============================================================*/
create or replace force view viw_acqrawdata as
select t2.id,t2.deviceid,t.devicename,t.devicetype,
t3.name_zh_cn as deviceTypeName_zh_CN,t3.name_en as deviceTypeName_en,t3.name_ru as deviceTypeName_ru,
t.signinid,t.slave,t2.acqtime,t2.rawdata,t.orgid,
t4.allpath_zh_CN,t4.allpath_en,t4.allpath_ru
from tbl_device t,tbl_acqrawdata t2,tbl_devicetypeinfo t3,viw_org t4
where t.id=t2.deviceid and t.orgid=t4.org_id
and t3.id=t.devicetype;
/

/*==============================================================*/
/* View: viw_alarminfo_hist                                         */
/*==============================================================*/
create or replace force view viw_alarminfo_hist as
select t2.id,t2.deviceid,t.devicename,
t.devicetype,
t4.name_zh_cn as deviceTypeName_zh_CN,t4.name_en as deviceTypeName_en,t4.name_ru as deviceTypeName_ru,
t2.alarmtime,t2.itemname,t2.alarmtype,
t2.alarmvalue,t2.alarminfo,t2.alarmlimit,t2.hystersis,
t2.alarmlevel,
t2.issendmessage,t2.issendmail,
t2.recoverytime,t.orgid
 from tbl_device t,tbl_alarminfo_hist t2,
 tbl_devicetypeinfo t4
 where t2.deviceid=t.id
 and t4.id=t.devicetype;
 /

/*==============================================================*/
/* View: viw_alarminfo_latest                                         */
/*==============================================================*/
create or replace force view viw_alarminfo_latest as
select t2.id,t2.deviceid,t.devicename,
t.devicetype,
t4.name_zh_cn as deviceTypeName_zh_CN,t4.name_en as deviceTypeName_en,t4.name_ru as deviceTypeName_ru,
t2.alarmtime,t2.itemname,t2.alarmtype,
t2.alarmvalue,t2.alarminfo,t2.alarmlimit,t2.hystersis,
t2.alarmlevel,
t2.issendmessage,t2.issendmail,
t2.recoverytime,t.orgid
 from tbl_device t,tbl_alarminfo_latest t2,tbl_devicetypeinfo t4
 where t2.deviceid=t.id
 and t4.id=t.devicetype;
 /

/*==============================================================*/
/* View: viw_dailycalculationdata                                         */
/*==============================================================*/
create or replace view viw_dailycalculationdata as
select
 t.id,device.devicename,device.id as deviceid,device.devicetype,
 t.calDate,
 t.commstatus,decode(t.commstatus,1,'在线','离线') as commStatusName,t.commtime,t.commtimeefficiency,t.commrange,
 t.runStatus,
 case when t.commstatus=1 then
           decode(t.runstatus,1,'运行','停抽')
      else '离线' end as runStatusName,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,
 t.headerLabelInfo,t.caldata,
 device.reportinstancecode,
 device.sortnum,org.org_code,org.org_id,t.remark as remark,
 t.reservedcol1,t.reservedcol2,t.reservedcol3,t.reservedcol4,t.reservedcol5
from
tbl_device device
left outer join  tbl_org org  on device.orgid=org.org_id
left outer join  tbl_dailycalculationdata t  on t.deviceid=device.id;
/

/*==============================================================*/
/* View: viw_devicetypeinfo                                         */
/*==============================================================*/
create or replace force view viw_devicetypeinfo as
select t.id,t.parentid,t.sortnum,
t.name_zh_cn,
substr(sys_connect_by_path(t.name_zh_cn,'/'),2) as allpath_zh_cn,
t.name_en,
substr(sys_connect_by_path(t.name_en,'/'),2) as allpath_en,
t.name_ru,
substr(sys_connect_by_path(t.name_ru,'/'),2) as allpath_ru
from tbl_devicetypeinfo t
start with t.parentid=0
connect by   t.parentid= prior t.id;
/

/*==============================================================*/
/* View: viw_device                                         */
/*==============================================================*/
create or replace view viw_device as
select t.id,
org.org_id as orgid,
org.org_name_zh_CN as orgName_zh_CN,org.allpath_zh_CN,
org.org_name_en as orgName_en,org.allpath_en,
org.org_name_ru as orgName_ru,org.allpath_ru,
t.devicename,
t.devicetype,
t10.name_zh_cn as devicetypename_zh_cn,
t10.allpath_zh_cn as deviceTypeAllPath_zh_cn,
t10.name_en as devicetypename_en,
t10.allpath_en as deviceTypeAllPath_en,
t10.name_ru as devicetypename_ru,
t10.allpath_ru as deviceTypeAllPath_ru,
t.calculatetype,
t.applicationscenarios,
t.tcptype,t.signinid,t.ipport,t.slave,t.peakdelay,
t.videourl1,t.videokeyid1,t.videourl2,t.videokeyid2,t8.account as videoKeyName1,t9.account as videoKeyName2,
t.videoaccesstoken,
t.instancecode,
decode(t.devicetype,300,t4.name,t2.name) as instancename,
t.alarminstancecode,t3.name as alarminstancename,
t.displayinstancecode,t5.name as displayinstancename,
t.reportinstancecode,t7.name as reportinstancename,
t.status,decode(t.status,1,'使能','失效') as statusName,
t.productiondata,t.balanceinfo,t.stroke,
t.sortnum,t.productiondataupdatetime,
t.commissioningdate,
to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')-to_date(to_char(t.commissioningdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1 as operatingdays
from tbl_device t
left outer join  viw_org org  on t.orgid=org.org_id
left outer join tbl_protocolinstance t2 on t.instancecode=t2.code
left outer join tbl_protocolalarminstance t3 on t.alarminstancecode=t3.code
left outer join tbl_protocolsmsinstance t4 on t.instancecode =t4.code
left outer join tbl_protocoldisplayinstance t5 on t.displayinstancecode=t5.code
left outer join tbl_protocolreportinstance t7 on t.reportinstancecode=t7.code
left outer join tbl_videokey t8 on t.videokeyid1=t8.id
left outer join tbl_videokey t9 on t.videokeyid2=t9.id
left outer join viw_devicetypeinfo t10 on t.devicetype=t10.id;
/

/*==============================================================*/
/* View: viw_deviceoperationlog                                         */
/*==============================================================*/
create or replace view viw_deviceoperationlog as
select t.id,t.devicetype,
t5.name_zh_cn as deviceTypeName_zh_CN,t5.name_en as deviceTypeName_en,t5.name_ru as deviceTypeName_ru,
t.devicename,t.createtime,u.user_no,t.user_id,r.role_id,r.role_level,t.loginip,t.action,
decode(t.action,2,'',t.remark) as  remark,
decode(t.action,2,t.remark,(case when t.devicetype>=300 then t4.orgid else t2.orgid end)) as orgid
from tbl_deviceoperationlog t
left outer join tbl_user u on u.user_id=t.user_id
left outer join tbl_role r on r.role_id=u.user_type
left outer join tbl_device t2 on t.devicename=t2.devicename
left outer join tbl_smsdevice t4 on t.devicename=t4.devicename and t.devicetype>=300
left outer join tbl_devicetypeinfo t5 on t.devicetype=t5.id;
/

/*==============================================================*/
/* View: viw_pcpacqdata_hist                                         */
/*==============================================================*/
create or replace view viw_pcpacqdata_hist as
select t.id,t.deviceid,device.orgid,device.calculatetype,
t.acqtime,
t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,
t.runstatus,t.runtime,t.runtimeefficiency,t.runrange,
t.productiondata,
t.theoreticalproduction,
t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,
t.liquidvolumetricproduction_l,t.oilvolumetricproduction_l,t.watervolumetricproduction_l,
t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,
t.liquidweightproduction_l,t.oilweightproduction_l,t.waterweightproduction_l,
t.submergence,
t.averagewatt,t.waterpower,t.systemefficiency,
t.pumpeff1*100 as pumpeff1,t.pumpeff2*100 as pumpeff2,t.pumpeff*100 as pumpeff,
t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpintakevisl,t.pumpintakebo,
t.pumpoutletp,t.pumpoutlett,t.pumpoutletgol,t.pumpoutletvisl,t.pumpoutletbo
from tbl_device device,tbl_pcpacqdata_hist t
where device.id=t.deviceid and device.calculatetype=2;
/

/*==============================================================*/
/* View: viw_pcpdailycalculationdata                                         */
/*==============================================================*/
create or replace view viw_pcpdailycalculationdata as
select
 t.id,device.devicename,device.id as deviceid,device.devicetype,
 t.calDate,t.extendeddays,t.calDate-t.extendeddays as acquisitionDate,
 t.commstatus,decode(t.commstatus,1,'在线','离线') as commStatusName,t.commtime,t.commtimeefficiency,t.commrange,
 t.runStatus,
 case when t.commstatus=1 then
           decode(t.runstatus,1,'运行','停抽')
      else '离线' end as runStatusName,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,
 t.rpm,
 t.theoreticalproduction,
 t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,t.weightwatercut,
 t.liquidVolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,t.volumewatercut,
 t.pumpeff*100 as pumpeff,t.pumpeff1*100 as pumpeff1,t.pumpeff2*100 as pumpeff2,
 t.systemefficiency*100 as systemEfficiency,t.energyper100mlift,
 t.todayKWattH,
 t.tubingpressure,t.casingpressure,t.BottomHolePressure,
 t.pumpsettingdepth,t.producingfluidlevel,t.submergence,
 t.gasvolumetricproduction,t.totalgasvolumetricproduction,t.totalwatervolumetricproduction,
 t.headerLabelInfo,
 device.reportinstancecode,
 device.sortnum,org.org_code,org.org_id,t.remark as remark,
 t.reservedcol1,t.reservedcol2,t.reservedcol3,t.reservedcol4,t.reservedcol5
from
tbl_device device
left outer join  tbl_org org  on device.orgid=org.org_id
left outer join  tbl_pcpdailycalculationdata t  on t.deviceid=device.id
where device.calculatetype=2;
/

/*==============================================================*/
/* View: viw_pcptimingcalculationdata                                         */
/*==============================================================*/
create or replace force view viw_pcptimingcalculationdata as
select
 t.id,device.devicename,device.id as deviceid,device.devicetype,
 t.calTime,t.extendeddays,t.calTime-t.extendeddays as acquisitionDate,
 t.commstatus,decode(t.commstatus,1,'在线','离线') as commStatusName,t.commtime,t.commtimeefficiency,t.commrange,
 t.runStatus,
 case when t.commstatus=1 then
           decode(t.runstatus,1,'运行','停抽')
      else '离线' end as runStatusName,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,
 t.rpm,
 t.theoreticalproduction,
 t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,t.weightwatercut,
 t.liquidVolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,t.volumewatercut,
 t.pumpeff*100 as pumpeff,t.pumpeff1*100 as pumpeff1,t.pumpeff2*100 as pumpeff2,
 t.systemefficiency*100 as systemEfficiency,t.energyper100mlift,
 t.todayKWattH,
 t.tubingpressure,t.casingpressure,t.BottomHolePressure,
 t.pumpsettingdepth,t.producingfluidlevel,t.submergence,
 t.gasvolumetricproduction,t.totalgasvolumetricproduction,t.totalwatervolumetricproduction,
 t.realtimeliquidvolumetricproduction,t.realtimeoilvolumetricproduction,t.realtimewatervolumetricproduction,t.realtimegasvolumetricproduction,
 t.realtimeliquidweightproduction,t.realtimeoilweightproduction,t.realtimewaterweightproduction,
 t.headerLabelInfo,
 device.reportinstancecode,
 device.sortnum,org.org_code,org.org_id,t.remark as remark,
 t.reservedcol1,t.reservedcol2,t.reservedcol3,t.reservedcol4,t.reservedcol5
from
tbl_device device,tbl_org org ,tbl_pcptimingcalculationdata t
where device.orgid=org.org_id
and t.deviceid=device.id
and device.calculatetype=2;
/

/*==============================================================*/
/* View: viw_pcp_calculatemain                                         */
/*==============================================================*/
create or replace force view viw_pcp_calculatemain as
select t.id as id,
device.id as deviceid,device.deviceName,
t.acqtime,t.resultstatus,
t.liquidWeightProduction,t.oilWeightProduction,t.waterweightproduction,
t.liquidVolumetricProduction,t.oilVolumetricProduction,t.watervolumetricproduction,
t.rpm,
t.productiondata,
device.orgid
from tbl_pcpacqdata_hist t
left outer join tbl_device device on t.deviceid=device.id and device.calculatetype=2;
/

/*==============================================================*/
/* View: viw_role                                         */
/*==============================================================*/
create or replace force view viw_role as
select t.role_id,t.role_name,t.role_level,t.showlevel,t.role_videokeyedit,t.role_languageedit,t.remark,
rtrim(xmlagg(xmlparse(content t3.id || ',' wellformed) order by t3.id).getclobval(),',' ) as tabs
from tbl_role t
left outer join tbl_devicetype2role t2 on t.role_id=t2.rd_roleid
left outer join tbl_devicetypeinfo t3 on t3.id=t2.rd_devicetypeid
group by t.role_id,t.role_name,t.role_level,t.showlevel,t.role_videokeyedit,t.role_languageedit,t.remark;
/

/*==============================================================*/
/* View: viw_smsdevice                                         */
/*==============================================================*/
create or replace force view viw_smsdevice as
select t.id,
org.org_name_zh_CN as orgName_zh_CN,
org.org_name_en as orgName_en,
org.org_name_ru as orgName_ru,
org.org_id as orgid,
t.devicename,
t.signinid,
t.instancecode,
t2.name as instancename,
t.sortnum
from tbl_smsdevice t
left outer join  tbl_org org  on t.orgid=org.org_id
left outer join tbl_protocolsmsinstance t2 on t.instancecode =t2.code;
/

/*==============================================================*/
/* View: viw_srpacqdata_hist                                         */
/*==============================================================*/
create or replace force view viw_srpacqdata_hist as
select t.deviceid,device.orgid,device.calculatetype,
t.id,t.acqtime,
t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,
t.runstatus,t.runtime,t.runtimeefficiency,t.runrange,
t.productiondata,
t.resultcode,
t.stroke,t.spm,
t.fmax,t.fmin,
t.upstrokeimax,t.downstrokeimax,t.upstrokewattmax,t.downstrokewattmax,t.idegreebalance,t.wattdegreebalance,
t.deltaradius,
t.fullnesscoefficient,t.noliquidfullnesscoefficient,
t.plungerstroke,t.availableplungerstroke,t.noliquidavailableplungerstroke,
t.upperloadline,t.upperloadlineofexact,t.lowerloadline,
t.smaxindex,t.sminindex,
t.theoreticalproduction,
t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,
t.availableplungerstrokeprod_v,t.pumpclearanceleakprod_v,t.tvleakvolumetricproduction,t.svleakvolumetricproduction,t.gasinfluenceprod_v,
t.liquidvolumetricproduction_l,t.oilvolumetricproduction_l,t.watervolumetricproduction_l,
t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,
t.availableplungerstrokeprod_w,t.pumpclearanceleakprod_w,t.tvleakweightproduction,t.svleakweightproduction,t.gasinfluenceprod_w,
t.liquidweightproduction_l,t.oilweightproduction_l,t.waterweightproduction_l,
t.leveldifferencevalue,t.calcproducingfluidlevel,t.submergence,
t.averagewatt,t.polishrodpower,t.waterpower,
t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,
t.energyper100mlift,t.area,
t.rodflexlength,t.tubingflexlength,t.inertialength,
t.pumpeff1 as pumpeff1,t.pumpeff2 as pumpeff2,t.pumpeff3 as pumpeff3,t.pumpeff4 as pumpeff4,t.pumpeff as pumpeff,
t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpintakevisl,t.pumpintakebo,
t.pumpoutletp,t.pumpoutlett,t.pumpoutletgol,t.pumpoutletvisl,t.pumpoutletbo
from tbl_device device,tbl_srpacqdata_hist t
where device.id=t.deviceid and device.calculatetype=1;
/

/*==============================================================*/
/* View: viw_srpdailycalculationdata                                         */
/*==============================================================*/
create or replace view viw_srpdailycalculationdata as
select
 t.id,device.devicename,device.id as deviceid,device.devicetype,
 t.calDate,t.extendeddays,t.calDate-t.extendeddays as acquisitionDate,
 t.commstatus,decode(t.commstatus,1,'在线','离线') as commStatusName,t.commtime,t.commtimeefficiency,t.commrange,
 t.runStatus,
 case when t.commstatus=1 then
           decode(t.runstatus,1,'运行','停抽')
      else '离线' end as runStatusName,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,
 t.resultString,
 t.theoreticalproduction,
 t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,t.weightwatercut,
 t.liquidVolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,t.volumewatercut,
 t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,
 t.pumpeff*100 as pumpeff,t.pumpeff1*100 as pumpeff1,t.pumpeff2*100 as pumpeff2,t.pumpeff3*100 as pumpeff3,t.pumpeff4*100 as pumpeff4,
 t.systemefficiency*100 as systemEfficiency,t.surfacesystemefficiency*100 as surfaceSystemEfficiency,t.welldownsystemefficiency*100 as welldownSystemEfficiency,
 t.energyper100mlift,
 t.todayKWattH,
 t.iDegreeBalance,
 t.wattDegreeBalance,
 t.deltaradius*100 as deltaradius,
 t.tubingpressure,t.casingpressure,t.BottomHolePressure,
 t.pumpsettingdepth,
 t.producingfluidlevel,t.calcproducingfluidlevel,t.leveldifferencevalue,
 t.submergence,
 t.gasvolumetricproduction,t.totalgasvolumetricproduction,t.totalwatervolumetricproduction,
 t.rpm,
 t.headerLabelInfo,
 device.reportinstancecode,
 device.sortnum,org.org_code,org.org_id,t.remark as remark,
 t.reservedcol1,t.reservedcol2,t.reservedcol3,t.reservedcol4,t.reservedcol5
from
tbl_device device
left outer join  tbl_org org  on device.orgid=org.org_id
left outer join  tbl_srpdailycalculationdata t  on t.deviceid=device.id
and device.calculatetype=1;
/

/*==============================================================*/
/* View: viw_srptimingcalculationdata                                         */
/*==============================================================*/
create or replace force view viw_srptimingcalculationdata as
select
 t.id,device.devicename,device.id as deviceid,device.devicetype,
 t.calTime,t.extendeddays,t.calTime-t.extendeddays as acquisitionDate,
 t.commstatus,decode(t.commstatus,1,'在线','离线') as commStatusName,t.commtime,t.commtimeefficiency,t.commrange,
 t.runStatus,
 case when t.commstatus=1 then
           decode(t.runstatus,1,'运行','停抽')
      else '离线' end as runStatusName,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,
 t.resultString,
 t.theoreticalproduction,
 t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,t.weightwatercut,
 t.liquidVolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,t.volumewatercut,
 t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,
 t.pumpeff*100 as pumpeff,t.pumpeff1*100 as pumpeff1,t.pumpeff2*100 as pumpeff2,t.pumpeff3*100 as pumpeff3,t.pumpeff4*100 as pumpeff4,
 t.systemefficiency*100 as systemEfficiency,t.surfacesystemefficiency*100 as surfaceSystemEfficiency,t.welldownsystemefficiency*100 as welldownsystemefficiency,
 t.energyper100mlift,
 t.todayKWattH,
 t.iDegreeBalance,
 t.wattDegreeBalance,
 t.deltaradius*100 as deltaradius,
 t.tubingpressure,t.casingpressure,t.BottomHolePressure,
 t.pumpsettingdepth,
 t.producingfluidlevel,t.calcproducingfluidlevel,t.leveldifferencevalue,
 t.submergence,
 t.gasvolumetricproduction,t.totalgasvolumetricproduction,t.totalwatervolumetricproduction,
 t.realtimeliquidvolumetricproduction,t.realtimeoilvolumetricproduction,t.realtimewatervolumetricproduction,t.realtimegasvolumetricproduction,
 t.realtimeliquidweightproduction,t.realtimeoilweightproduction,t.realtimewaterweightproduction,
 t.rpm,
 t.headerLabelInfo,
 device.reportinstancecode,
 device.sortnum,org.org_code,org.org_id,t.remark as remark,
 t.reservedcol1,t.reservedcol2,t.reservedcol3,t.reservedcol4,t.reservedcol5
from
tbl_device device,tbl_org org,tbl_srptimingcalculationdata t
where device.orgid=org.org_id and t.deviceid=device.id
and device.calculatetype=1;
/

/*==============================================================*/
/* View: viw_srp_calculatemain                                         */
/*==============================================================*/
create or replace view viw_srp_calculatemain as
select t.id as id,
device.id as deviceid,device.deviceName,
t.acqtime,
t.fesdiagramacqtime,t.resultstatus,t.resultcode,
t.liquidWeightProduction,t.oilWeightProduction,t.waterweightproduction,
t.liquidVolumetricProduction,t.oilVolumetricProduction,t.watervolumetricproduction,
t.leveldifferencevalue,t.calcproducingfluidlevel,
t.productiondata,
device.orgid
from tbl_srpacqdata_hist t
left outer join tbl_device device on t.deviceid=device.id;
/

/*==============================================================*/
/* View: viw_systemlog                                         */
/*==============================================================*/
create or replace force view viw_systemlog as
select t.id,t.createtime,t2.user_no,t.user_id,
t4.role_id,t4.role_level,t4.tabs,
t.loginip,t.action,t.remark ,t2.user_orgid as orgid
from tbl_systemlog t,tbl_user t2,viw_role t4
where t.user_id=t2.user_id and t2.user_type=t4.role_id;
/

/*==============================================================*/
/* View: viw_timingcalculationdata                                         */
/*==============================================================*/
create or replace view viw_timingcalculationdata as
select
 t.id,device.devicename,device.id as deviceid,device.devicetype,
 t.calTime,
 t.commstatus,decode(t.commstatus,1,'在线','离线') as commStatusName,t.commtime,t.commtimeefficiency,t.commrange,
 t.runStatus,
 case when t.commstatus=1 then
           decode(t.runstatus,1,'运行','停抽')
      else '离线' end as runStatusName,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,
 t.headerLabelInfo,t.caldata,
 device.reportinstancecode,
 device.sortnum,org.org_code,org.org_id,t.remark as remark,
 t.reservedcol1,t.reservedcol2,t.reservedcol3,t.reservedcol4,t.reservedcol5
from
tbl_device device,tbl_org org,tbl_timingcalculationdata t
where  device.orgid=org.org_id and t.deviceid=device.id;
/
