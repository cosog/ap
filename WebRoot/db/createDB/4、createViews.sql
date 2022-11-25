/*==============================================================*/
/* View: viw_org                                         */
/*==============================================================*/
create or replace view viw_org as
select t.org_id,t.org_code,t.org_name,t.org_memo,t.org_parent,t.org_seq,
substr(sys_connect_by_path(t.org_name,'/'),2) as allpath
from tbl_org t
start with t.org_parent=0
connect by   t.org_parent= prior t.org_id;
/


/*==============================================================*/
/* View: viw_rpcdevice                                         */
/*==============================================================*/
create or replace view viw_rpcdevice as
select t.id,org.org_name as orgName,org.org_id as orgid,org.allpath,
t.wellname,
t.devicetype,c2.itemname as devicetypename,
t.applicationscenarios,c1.itemname as applicationScenariosName,
t.tcptype,t.signinid,t.slave,t.peakdelay,
t.videourl,t.videoaccesstoken,
t.instancecode,
decode(t.devicetype,2,t4.name,t2.name) as instancename,
t.alarminstancecode,t3.name as alarminstancename,
t.displayinstancecode,t5.name as displayinstancename,
t.status,decode(t.status,1,'使能','失效') as statusName,
t.productiondata,t.balanceinfo,t.stroke,t.levelcorrectvalue,
t.pumpingmodelid,t6.manufacturer,t6.model,t6.crankrotationdirection,t6.offsetangleofcrank,t6.crankgravityradius,t6.singlecrankweight,t6.singlecrankpinweight,t6.structuralunbalance,
t.sortnum
from tbl_rpcdevice t
left outer join  viw_org org  on t.orgid=org.org_id
left outer join tbl_protocolinstance t2 on t.instancecode=t2.code
left outer join tbl_protocolalarminstance t3 on t.alarminstancecode=t3.code
left outer join tbl_protocolsmsinstance t4 on t.instancecode =t4.code
left outer join tbl_protocoldisplayinstance t5 on t.displayinstancecode=t5.code
left outer join tbl_pumpingmodel t6 on t.pumpingmodelid=t6.id
left outer join tbl_code c1 on c1.itemcode='APPLICATIONSCENARIOS' and t.applicationscenarios=c1.itemvalue
left outer join tbl_code c2 on c2.itemcode='DEVICETYPE' and t.devicetype=c2.itemvalue;
/

/*==============================================================*/
/* View: viw_pcpdevice                                         */
/*==============================================================*/
create or replace view viw_pcpdevice as
select t.id,org.org_name as orgName,org.org_id as orgid,org.allpath,
t.wellname,
t.devicetype,c2.itemname as devicetypename,
t.applicationscenarios,c1.itemname as applicationScenariosName,
t.tcptype,t.signinid,t.slave,t.peakdelay,
t.videourl,t.videoaccesstoken,
t.instancecode,
decode(t.devicetype,2,t4.name,t2.name) as instancename,
t.alarminstancecode,t3.name as alarminstancename,
t.displayinstancecode,t5.name as displayinstancename,
t.status,decode(t.status,1,'使能','失效') as statusName,
t.productiondata,
t.sortnum
from tbl_pcpdevice t
left outer join  viw_org org  on t.orgid=org.org_id
left outer join tbl_protocolinstance t2 on t.instancecode=t2.code
left outer join tbl_protocolalarminstance t3 on t.alarminstancecode=t3.code
left outer join tbl_protocolsmsinstance t4 on t.instancecode =t4.code
left outer join tbl_protocoldisplayinstance t5 on t.displayinstancecode=t5.code
left outer join tbl_code c1 on c1.itemcode='APPLICATIONSCENARIOS' and t.applicationscenarios=c1.itemvalue
left outer join tbl_code c2 on c2.itemcode='DEVICETYPE' and t.devicetype=c2.itemvalue;
/

/*==============================================================*/
/* View: viw_smsdevice                             */
/*==============================================================*/
create or replace view viw_smsdevice as
select t.id,org.org_name as orgName,org.org_id as orgid,
t.wellname,
t.signinid,
t.instancecode,
t2.name as instancename,
t.sortnum
from tbl_smsdevice t
left outer join  tbl_org org  on t.orgid=org.org_id
left outer join tbl_protocolsmsinstance t2 on t.instancecode =t2.code;
/

/*==============================================================*/
/* View: viw_rpcacqrawdata                             */
/*==============================================================*/
create or replace view viw_rpcacqrawdata as
select t2.id,t2.wellid,t.wellname,t.devicetype,t3.itemname as deviceTypeName,t.signinid,t.slave,t2.acqtime,t2.rawdata,t.orgid,t4.allpath
from tbl_rpcdevice t,tbl_rpcacqrawdata t2,tbl_code t3,viw_org t4
where t.id=t2.wellid and t.orgid=t4.org_id
and t3.itemcode='DEVICETYPE' and t3.itemvalue=t.devicetype;
/

/*==============================================================*/
/* View: viw_rpcalarminfo_hist                           */
/*==============================================================*/
create or replace view viw_rpcalarminfo_hist as
select t2.id,t2.wellid,t.wellname,
t.devicetype,t4.itemname as deviceTypeName,
t2.alarmtime,t2.itemname,t2.alarmtype,t5.itemname as alarmTypeName,
t2.alarmvalue,t2.alarminfo,t2.alarmlimit,t2.hystersis,
t2.alarmlevel,t3.itemname as alarmLevelName,
t2.issendmessage,t2.issendmail,
t2.recoverytime,t.orgid
 from tbl_rpcdevice t,tbl_rpcalarminfo_hist t2 ,tbl_code t3,tbl_code t4,tbl_code t5
 where t2.wellid=t.id
 and t3.itemcode='BJJB' and t3.itemvalue=t2.alarmlevel
 and t4.itemcode='DEVICETYPE' and t4.itemvalue=t.devicetype
 and t5.itemcode='alarmType' and t5.itemvalue=t2.alarmtype;
/

/*==============================================================*/
/* View: viw_rpcalarminfo_latest                                  */
/*==============================================================*/
create or replace view viw_rpcalarminfo_latest as
select t2.id,t2.wellid,t.wellname,
t.devicetype,t4.itemname as deviceTypeName,
t2.alarmtime,t2.itemname,t2.alarmtype,t5.itemname as alarmTypeName,
t2.alarmvalue,t2.alarminfo,t2.alarmlimit,t2.hystersis,
t2.alarmlevel,t3.itemname as alarmLevelName,
t2.issendmessage,t2.issendmail,
t2.recoverytime,t.orgid
 from tbl_rpcdevice t,tbl_rpcalarminfo_latest t2 ,tbl_code t3,tbl_code t4,tbl_code t5
 where t2.wellid=t.id
 and t3.itemcode='BJJB' and t3.itemvalue=t2.alarmlevel
 and t4.itemcode='DEVICETYPE' and t4.itemvalue=t.devicetype
 and t5.itemcode='alarmType' and t5.itemvalue=t2.alarmtype;
/

/*==============================================================*/
/* View: viw_rpcdailycalculationdata                                  */
/*==============================================================*/
create or replace view viw_rpcdailycalculationdata as
select
 t.id,well.wellname,well.id as wellid,
 t.calDate,t.extendeddays,t.calDate-t.extendeddays as acquisitionDate,
 t.commstatus,decode(t.commstatus,1,'在线','离线') as commStatusName,t.commtime,t.commtimeefficiency,t.commrange,
 t.runStatus,
 case when t.commstatus=1 then
           decode(t.runstatus,1,'运行','停抽')
      else '离线' end as runStatusName,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,
 status.resultname as resultname,t.resultString,status.optimizationsuggestion,
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
 well.sortnum,org.org_code,org.org_id,null as remark
from
tbl_rpcdevice well
left outer join  tbl_org org  on well.orgid=org.org_id
left outer join  tbl_rpcdailycalculationdata t  on t.wellid=well.id
left outer join  tbl_rpc_worktype status  on  status.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode);
/

/*==============================================================*/
/* View: viw_rpc_calculatemain                             */
/*==============================================================*/
create or replace view viw_rpc_calculatemain as
select t.id as id,
well.id as wellid,well.wellName,
t.fesdiagramacqtime,t.resultstatus,t.resultcode,ws.resultName,
t.liquidWeightProduction,t.oilWeightProduction,
t.liquidVolumetricProduction,t.oilVolumetricProduction,
t.levelcorrectvalue,t.inverproducingfluidlevel,
t.productiondata,
well.orgid
from tbl_rpcacqdata_hist t
left outer join tbl_rpcdevice well on t.wellid=well.id
left outer join tbl_rpc_worktype ws on  t.resultcode=ws.resultcode;
/

/*==============================================================*/
/* View: viw_pcpacqrawdata                             */
/*==============================================================*/
create or replace view viw_pcpacqrawdata as
select t2.id,t2.wellid,t.wellname,t.devicetype,t3.itemname as deviceTypeName,t.signinid,t.slave,t2.acqtime,t2.rawdata,t.orgid,t4.allpath
from tbl_pcpdevice t,tbl_pcpacqrawdata t2,tbl_code t3,viw_org t4
where t.id=t2.wellid and t.orgid=t4.org_id
and t3.itemcode='DEVICETYPE' and t3.itemvalue=t.devicetype;
/

/*==============================================================*/
/* View: viw_pcpalarminfo_hist                           */
/*==============================================================*/
create or replace view viw_pcpalarminfo_hist as
select t2.id,t2.wellid,t.wellname,
t.devicetype,t4.itemname as deviceTypeName,
t2.alarmtime,t2.itemname,t2.alarmtype,t5.itemname as alarmTypeName,
t2.alarmvalue,t2.alarminfo,t2.alarmlimit,t2.hystersis,
t2.alarmlevel,t3.itemname as alarmLevelName,
t2.issendmessage,t2.issendmail,
t2.recoverytime,t.orgid
 from tbl_pcpdevice t,tbl_pcpalarminfo_hist t2 ,tbl_code t3,tbl_code t4,tbl_code t5
 where t2.wellid=t.id
 and t3.itemcode='BJJB' and t3.itemvalue=t2.alarmlevel
 and t4.itemcode='DEVICETYPE' and t4.itemvalue=t.devicetype
 and t5.itemcode='alarmType' and t5.itemvalue=t2.alarmtype;
/

/*==============================================================*/
/* View: viw_rpcalarminfo_latest                                  */
/*==============================================================*/
create or replace view viw_pcpalarminfo_latest as
select t2.id,t2.wellid,t.wellname,
t.devicetype,t4.itemname as deviceTypeName,
t2.alarmtime,t2.itemname,t2.alarmtype,t5.itemname as alarmTypeName,
t2.alarmvalue,t2.alarminfo,t2.alarmlimit,t2.hystersis,
t2.alarmlevel,t3.itemname as alarmLevelName,
t2.issendmessage,t2.issendmail,
t2.recoverytime,t.orgid
 from tbl_pcpdevice t,tbl_pcpalarminfo_latest t2 ,tbl_code t3,tbl_code t4,tbl_code t5
 where t2.wellid=t.id
 and t3.itemcode='BJJB' and t3.itemvalue=t2.alarmlevel
 and t4.itemcode='DEVICETYPE' and t4.itemvalue=t.devicetype
 and t5.itemcode='alarmType' and t5.itemvalue=t2.alarmtype;
/

/*==============================================================*/
/* View: viw_pcpdailycalculationdata                                  */
/*==============================================================*/
create or replace view viw_pcpdailycalculationdata as
select
 t.id,well.wellname,well.id as wellid,
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
 well.sortnum,org.org_code,org.org_id,null as remark
from
tbl_pcpdevice well
left outer join  tbl_org org  on well.orgid=org.org_id
left outer join  tbl_pcpdailycalculationdata t  on t.wellid=well.id;
/

/*==============================================================*/
/* View: viw_pcp_calculatemain                                  */
/*==============================================================*/
create or replace view viw_pcp_calculatemain as
select t.id as id,
well.id as wellid,well.wellName,
t.acqtime,t.resultstatus,
t.liquidWeightProduction,t.oilWeightProduction,
t.liquidVolumetricProduction,t.oilVolumetricProduction,
t.rpm,
t.productiondata,
well.orgid
from tbl_pcpacqdata_hist t
left outer join tbl_pcpdevice well on t.wellid=well.id;
/

/*==============================================================*/
/* View: viw_deviceoperationlog                                  */
/*==============================================================*/
create or replace view viw_deviceoperationlog as
select t.id,t.devicetype,code1.itemname as deviceTypeName,
t.wellname,t.createtime,u.user_no,t.user_id,r.role_id,r.role_level,t.loginip,t.action,code2.itemname as actionname,t.remark ,
(case when t.devicetype>=100 and t.devicetype<200 then t2.orgid
      when t.devicetype>=200 and t.devicetype<300 then t3.orgid
      when t.devicetype>=300then t4.orgid end) as orgid
from tbl_deviceoperationlog t
left outer join tbl_user u on u.user_id=t.user_id
left outer join tbl_role r on r.role_id=u.user_type
left outer join tbl_rpcdevice t2 on t.wellname=t2.wellname and t.devicetype>=100 and t.devicetype<200
left outer join tbl_pcpdevice t3 on t.wellname=t3.wellname and t.devicetype>=200 and t.devicetype<300
left outer join tbl_smsdevice t4 on t.wellname=t4.wellname and t.devicetype>=300
left outer join tbl_code code1 on t.devicetype=code1.itemvalue and upper(code1.itemcode)=upper('devicetype')
left outer join tbl_code code2 on t.action=code2.itemvalue and upper(code2.itemcode)=upper('action');
/

/*==============================================================*/
/* View: viw_systemlog                                  */
/*==============================================================*/
create or replace view viw_systemlog as
select t.id,t.createtime,t2.user_no,t.user_id,t4.role_id,t4.role_level,t.loginip,t.action,t3.itemname as actionname,t.remark ,t2.user_orgid as orgid
from tbl_systemlog t,tbl_user t2,tbl_code t3,tbl_role t4
where t.user_id=t2.user_id and t2.user_type=t4.role_id
and t.action=t3.itemvalue and upper(t3.itemcode)=upper('systemAction');
/