/*==============================================================*/
/* View: viw_commstatus                                         */
/*==============================================================*/
create or replace view viw_commstatus as
select well.id, well.wellName,greatest(decode(v.commstatus,null,0,v.commstatus),decode(v2.commstatus,null,0,v2.commstatus),decode(v3.commstatus,null,0,v3.commstatus)) as commstatus from
tbl_wellinformation well
left outer join
(select t.wellid, (sysdate- t.savetime)*24*60 as interval2,t.interval,
       case when t.commstatus=0 or t.savetime is null
       or (sysdate- t.savetime)*24*60 >decode(t.interval,null,1,t.interval)*1.5 then 0
       else 1 end as commstatus
from tbl_rpc_discrete_latest t,tbl_wellinformation t2 where t.wellid=t2.id) v on well.id=v.wellid
left outer join
(select t.wellid, (sysdate- t.savetime)*24*60 as interval2,t.interval,
       case when t.savetime is null
       or (sysdate- t.savetime)*24*60 >decode(t.interval,null,1,t.interval)*1.5 then 0
       else 1 end as commstatus
from tbl_rpc_diagram_latest t,tbl_wellinformation t2 where t.wellid=t2.id) v2 on well.id=v2.wellid
left outer join
(select t2.id as wellid, (sysdate- t.AcqTime)*24*60 as interval2,t.TransferIntervel,
       case when t.AcqTime is null
       or (sysdate- t.AcqTime)*24*60 >decode(t.TransferIntervel,null,1,t.TransferIntervel)*1.5 then 0
       else 1 end as commstatus
from tbl_a9rawdata_latest t,tbl_wellinformation t2 where t.deviceId=t2.signinid) v3 on well.id=v3.wellid
where well.protocolcode='MQTTDrive' or well.protocolcode='KafkaDrive';
/

/*==============================================================*/
/* View: viw_pcp_comprehensive_hist                             */
/*==============================================================*/
create or replace view viw_pcp_comprehensive_hist as
select
 t.id,well.wellName, well.id as wellid ,well.liftingType,
 t.AcqTime   as AcqTime,dis.AcqTime as AcqTime_d,
 dis.commStatus,decode(dis.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_8.alarmsign,0,0,t031_8.alarmlevel) as commAlarmLevel,
 case when prod.runtimeefficiencysource=0 and prod.runtime=0 then 0
      when prod.runtimeefficiencysource=0 and prod.runtime>0 then 1
      else dis.runstatus end as runStatus,
 case when dis.commstatus=1 then
           case when prod.runtimeefficiencysource=0 and prod.runtime=0 then '停抽'
                when prod.runtimeefficiencysource=0 and prod.runtime>0 then '运行'
                else decode(dis.runstatus,1,'运行','停抽') end
      else '离线' end as runStatusName,
 decode(t031_9.alarmsign,0,0,t031_9.alarmlevel) as runAlarmLevel,
 dis.commTime,dis.commRange,dis.commTimeEfficiency as commTimeEfficiency ,stat10.s_level as commtimeefficiencyLevel,
 dis.runTime,dis.runRange,dis.runTimeEfficiency as runTimeEfficiency,stat9.s_level as runtimeefficiencyLevel,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,status1.resultName,status1.optimizationSuggestion,decode(alarm1.alarmsign,0,0,alarm1.alarmlevel) as resultAlarmLevel,
 decode(dis.resultcode,null,1100,0,1100,dis.resultcode) as resultcode_E,
 dis.resultstring as resultString_E,status10.resultname as resultName_E,status10.optimizationsuggestion as optimizationsuggestion_e,
 decode(alarm10.alarmsign,0,0,alarm10.alarmlevel) as resultAlarmLevel_E,
 t.rpm,t.torque,
 t.theoreticalProduction,
 t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,
 prod.weightwatercut, stat4.s_level as liquidweightproductionlevel,
 t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,
 prod.volumewatercut,stat11.s_level as liquidvolumeproductionlevel,
 prod.productionGasOilRatio,prod.tubingPressure,prod.casingPressure,prod.wellheadFluidTemperature,
 prod.qpr,prod.barrellength,prod.barrelseries,prod.rotordiameter,
 prod.producingfluidLevel,prod.pumpSettingDepth,prod.pumpsettingdepth-prod.producingfluidLevel as submergence,prod.pumpBoreDiameter,
 prod.crudeoildensity,prod.netgrossratio,
 t.rodString,
 t.AverageWatt,t.waterPower,
 t.systemefficiency*100 as systemEfficiency,stat5.s_level as systemEfficiencyLevel,
 t.EnergyPer100mLift,
 t.pumpEff1*100 as pumpEff1,t.pumpEff2*100 as pumpEff2,t.pumpEff*100 as pumpEff,
 t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpIntakevisl,t.pumpIntakebo,
 t.pumpoutletp,t.pumpoutlett,t.pumpOutletGol,t.pumpoutletvisl,t.pumpoutletbo,
 dis.todayKWattH,stat8.s_level as todayKWattHLevel,dis.todaypKWattH,dis.todaynKWattH,
 dis.todayKVarH,dis.todaypKVarH,dis.todaynKVarH,dis.todayKVAH,
 dis.ia,dis.ib,dis.ic,dis.iavg,
 to_char(dis.ia,'fm999990.00') ||'/'||to_char(dis.ib,'fm999990.00')||'/'||to_char(dis.ic,'fm999990.00') as istr,
 dis.iauplimit,dis.iadownlimit,dis.iazero,
 dis.ibuplimit,dis.ibdownlimit,dis.ibzero,
 dis.icuplimit,dis.icdownlimit,dis.iczero,
 dis.va,dis.vb,dis.vc,dis.vavg,
 to_char(dis.va,'fm999990.00') ||'/'||to_char(dis.vb,'fm999990.00')||'/'||to_char(dis.vc,'fm999990.00') as vstr,
 dis.vauplimit,dis.vadownlimit,dis.vazero,
 dis.vbuplimit,dis.vbdownlimit,dis.vbzero,
 dis.vcuplimit,dis.vcdownlimit,dis.vczero,
 dis.totalKWattH,dis.totalpKWattH,dis.totalnKWattH,
 dis.totalKVarH,dis.totalpKVarH,dis.totalnKVarH,
 dis.totalKVAH,
 dis.watta,dis.wattb,dis.wattc,dis.watt3,
 to_char(dis.watta,'fm999990.00') ||'/'||to_char(dis.wattb,'fm999990.00')||'/'||to_char(dis.wattc,'fm999990.00') as wattstr,
 dis.vara,dis.varb,dis.varc,dis.var3,
 to_char(dis.vara,'fm999990.00') ||'/'||to_char(dis.varb,'fm999990.00')||'/'||to_char(dis.varc,'fm999990.00') as varstr,
 dis.reversePower,
 dis.vaa,dis.vab,dis.vac,dis.va3,
 to_char(dis.vaa,'fm999990.00') ||'/'||to_char(dis.vab,'fm999990.00')||'/'||to_char(dis.vac,'fm999990.00') as vastr,
 dis.pfa,dis.pfb,dis.pfc,dis.pf3,
 to_char(dis.pfa,'fm999990.00') ||'/'||to_char(dis.pfb,'fm999990.00')||'/'||to_char(dis.pfc,'fm999990.00') as pfstr,
 dis.Setfrequency,dis.Runfrequency,
 dis.signal,dis.interval,dis.devicever,
 well.videourl,org.org_id,org.org_code, well.sortnum
from
tbl_wellinformation well
left outer join  tbl_org org  on org.org_id=well.orgid
left outer join  tbl_pcp_rpm_hist t  on t.wellid=well.id
left outer join  tbl_pcp_discrete_hist dis  on dis.wellid=well.id and dis.id=t.discretedataid
left outer join  tbl_pcp_productiondata_hist prod on prod.id=t.productiondataid
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm1  on  alarm1.resultcode=status1.resultcode
left outer join  tbl_rpc_worktype status10  on  status10.resultcode=decode(dis.resultcode,null,1100,0,1100,dis.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm10  on  status10.resultcode=alarm10.resultcode
left outer join  tbl_rpc_worktype status8  on  status8.resultcode=decode(dis.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_8  on  status8.resultcode=t031_8.resultcode
left outer join  tbl_rpc_worktype status9  on  status9.resultcode= decode(dis.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_9  on  status9.resultcode=t031_9.resultcode
left outer join  tbl_rpc_statistics_conf stat4 on t.liquidweightproduction between stat4.s_min and stat4.s_max and stat4.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat11 on t.liquidvolumetricproduction between stat11.s_min and stat11.s_max and stat11.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat5 on t.systemefficiency*100 between stat5.s_min and stat5.s_max and stat5.s_type='XTXL'
left outer join  tbl_rpc_statistics_conf stat8 on dis.todayKWattH between stat8.s_min and stat8.s_max and stat8.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat9 on dis.runtimeefficiency between stat9.s_min and stat9.s_max and stat9.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat10 on dis.commtimeefficiency between stat10.s_min and stat10.s_max and stat10.s_type='TXSL'
where well.liftingtype>=400 and well.liftingtype<500;
/

/*==============================================================*/
/* View: viw_pcp_comprehensive_latest                           */
/*==============================================================*/
create or replace view viw_pcp_comprehensive_latest as
select
 t.id,well.wellName, well.id as wellid ,well.liftingType,
 t.AcqTime as AcqTime,dis.AcqTime as AcqTime_d,
 dis.commStatus,decode(dis.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_8.alarmsign,0,0,t031_8.alarmlevel) as commAlarmLevel,
 case when prod.runtimeefficiencysource=0 and prod.runtime=0 then 0
      when prod.runtimeefficiencysource=0 and prod.runtime>0 then 1
      else dis.runstatus end as runStatus,
 case when dis.commstatus=1 then
           case when prod.runtimeefficiencysource=0 and prod.runtime=0 then '停抽'
                when prod.runtimeefficiencysource=0 and prod.runtime>0 then '运行'
                else decode(dis.runstatus,1,'运行','停抽') end
      else '离线' end as runStatusName,
 decode(t031_9.alarmsign,0,0,t031_9.alarmlevel) as runAlarmLevel,
 dis.commTime,dis.commRange,dis.commTimeEfficiency as commTimeEfficiency ,stat10.s_level as commtimeefficiencyLevel,
 dis.runTime,dis.runRange,dis.runTimeEfficiency as runTimeEfficiency,stat9.s_level as runtimeefficiencyLevel,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,status1.resultName,status1.optimizationSuggestion,decode(alarm1.alarmsign,0,0,alarm1.alarmlevel) as resultAlarmLevel,
 decode(dis.resultcode,null,1100,0,1100,dis.resultcode) as resultcode_E,
 dis.resultstring as resultString_E,status10.resultname as resultName_E,status10.optimizationsuggestion as optimizationsuggestion_e,
 decode(alarm10.alarmsign,0,0,alarm10.alarmlevel) as resultAlarmLevel_E,
 t.rpm,t.torque,
 t.theoreticalProduction,
 t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,
 prod.weightwatercut, stat4.s_level as liquidweightproductionlevel,
 t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,
 prod.volumewatercut,stat11.s_level as liquidvolumeproductionlevel,
 prod.productionGasOilRatio,prod.tubingPressure,prod.casingPressure,prod.wellheadFluidTemperature,
 prod.qpr,prod.barrelLength,prod.barrelSeries,prod.rotorDiameter,
 prod.producingfluidLevel,prod.pumpSettingDepth,prod.pumpsettingdepth-prod.producingfluidLevel as submergence,prod.pumpBoreDiameter,
 prod.crudeoildensity,prod.netgrossratio,
 t.rodString,
 t.AverageWatt,t.waterPower,
 t.systemefficiency*100 as systemEfficiency,stat5.s_level as systemEfficiencyLevel,
 t.EnergyPer100mLift,
 t.pumpEff1*100 as pumpEff1,t.pumpEff2*100 as pumpEff2,t.pumpEff*100 as pumpEff,
 t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpIntakevisl,t.pumpIntakebo,
 t.pumpoutletp,t.pumpoutlett,t.pumpOutletGol,t.pumpoutletvisl,t.pumpoutletbo,
 dis.todayKWattH,stat8.s_level as todayKWattHLevel,dis.todaypKWattH,dis.todaynKWattH,
 dis.todayKVarH,dis.todaypKVarH,dis.todaynKVarH,dis.todayKVAH,
 dis.ia,dis.ib,dis.ic,dis.iavg,
 to_char(dis.ia,'fm999990.00') ||'/'||to_char(dis.ib,'fm999990.00')||'/'||to_char(dis.ic,'fm999990.00') as istr,
 dis.iauplimit,dis.iadownlimit,dis.iazero,
 dis.ibuplimit,dis.ibdownlimit,dis.ibzero,
 dis.icuplimit,dis.icdownlimit,dis.iczero,
 dis.va,dis.vb,dis.vc,dis.vavg,
 to_char(dis.va,'fm999990.00') ||'/'||to_char(dis.vb,'fm999990.00')||'/'||to_char(dis.vc,'fm999990.00') as vstr,
 dis.vauplimit,dis.vadownlimit,dis.vazero,
 dis.vbuplimit,dis.vbdownlimit,dis.vbzero,
 dis.vcuplimit,dis.vcdownlimit,dis.vczero,
 dis.totalKWattH,dis.totalpKWattH,dis.totalnKWattH,
 dis.totalKVarH,dis.totalpKVarH,dis.totalnKVarH,
 dis.totalKVAH,
 dis.watta,dis.wattb,dis.wattc,dis.watt3,
 to_char(dis.watta,'fm999990.00') ||'/'||to_char(dis.wattb,'fm999990.00')||'/'||to_char(dis.wattc,'fm999990.00') as wattstr,
 dis.vara,dis.varb,dis.varc,dis.var3,
 to_char(dis.vara,'fm999990.00') ||'/'||to_char(dis.varb,'fm999990.00')||'/'||to_char(dis.varc,'fm999990.00') as varstr,
 dis.reversePower,
 dis.vaa,dis.vab,dis.vac,dis.va3,
 to_char(dis.vaa,'fm999990.00') ||'/'||to_char(dis.vab,'fm999990.00')||'/'||to_char(dis.vac,'fm999990.00') as vastr,
 dis.pfa,dis.pfb,dis.pfc,dis.pf3,
 to_char(dis.pfa,'fm999990.00') ||'/'||to_char(dis.pfb,'fm999990.00')||'/'||to_char(dis.pfc,'fm999990.00') as pfstr,
 dis.Setfrequency,dis.Runfrequency,
 dis.signal,dis.interval,dis.devicever,
 well.videourl,org.org_id,org.org_code, well.sortnum
from
tbl_wellinformation well
left outer join  tbl_org org  on org.org_id=well.orgid
left outer join  tbl_pcp_rpm_latest t  on t.wellid=well.id
left outer join  tbl_pcp_discrete_latest dis  on dis.wellid=well.id
left outer join  tbl_pcp_productiondata_hist prod on prod.id=t.productiondataid
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm1  on  alarm1.resultcode=status1.resultcode
left outer join  tbl_rpc_worktype status10  on  status10.resultcode=decode(dis.resultcode,null,1100,0,1100,dis.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm10  on  status10.resultcode=alarm10.resultcode
left outer join  tbl_rpc_worktype status8  on  status8.resultcode=decode(dis.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_8  on  status8.resultcode=t031_8.resultcode
left outer join  tbl_rpc_worktype status9  on  status9.resultcode= decode(dis.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_9  on  status9.resultcode=t031_9.resultcode
left outer join  tbl_rpc_statistics_conf stat4 on t.liquidweightproduction between stat4.s_min and stat4.s_max and stat4.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat11 on t.liquidvolumetricproduction between stat11.s_min and stat11.s_max and stat11.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat5 on t.systemefficiency*100 between stat5.s_min and stat5.s_max and stat5.s_type='XTXL'
left outer join  tbl_rpc_statistics_conf stat8 on dis.todayKWattH between stat8.s_min and stat8.s_max and stat8.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat9 on dis.runtimeefficiency between stat9.s_min and stat9.s_max and stat9.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat10 on dis.commtimeefficiency between stat10.s_min and stat10.s_max and stat10.s_type='TXSL'
where well.liftingtype>=400 and well.liftingtype<500;
/

/*==============================================================*/
/* View: viw_pcp_discrete_hist                                  */
/*==============================================================*/
create or replace view viw_pcp_discrete_hist as
select
 t.id,well.wellname,well.liftingtype,code1.itemname as liftingTypeName, t.wellid ,
 t.commstatus,
 decode(t.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_2.alarmsign,0,0,t031_2.alarmlevel) as commAlarmLevel,
 case when prod.runtimeefficiencysource=0 and prod.runtime=0 then 0
      when prod.runtimeefficiencysource=0 and prod.runtime>0 then 1
      else t.runstatus end as runStatus,
 case when t.commstatus=1 then
           case when prod.runtimeefficiencysource=0 and prod.runtime=0 then '停抽'
                when prod.runtimeefficiencysource=0 and prod.runtime>0 then '运行'
                else decode(t.runstatus,1,'运行','停抽') end
      else '离线' end as runStatusName,
 decode(t031_3.alarmsign,0,0,t031_3.alarmlevel) as runAlarmLevel,
 t.commtime,t.commrange,t.commtimeefficiency as commtimeefficiency ,stat3.s_level as commtimeefficiencyLevel,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,stat2.s_level as runtimeefficiencyLevel,
 t.AcqTime,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode_elec,
 t.resultstring as resultstring_elec,status1.resultname as resultname_elec,status1.optimizationsuggestion as optimizationsuggestion_elec,
 decode(t031_1.alarmsign,0,0,t031_1.alarmlevel) as resultAlarmLevel,
 t.todayKWattH,stat1.s_level as todayKWattHLevel,t.todaypKWattH,t.todaynKWattH,
 t.todayKVarH,t.todaypKVarH,t.todaynKVarH,t.todayKVAH,
 t.ia,t.ib,t.ic,t.iavg,
 to_char(t.ia,'fm999990.00') ||'/'||to_char(t.ib,'fm999990.00')||'/'||to_char(t.ic,'fm999990.00') as istr,
 t.iauplimit,t.iadownlimit,t.iazero,
 t.ibuplimit,t.ibdownlimit,t.ibzero,
 t.icuplimit,t.icdownlimit,t.iczero,
 t.va,t.vb,t.vc,t.vavg,
 to_char(t.va,'fm999990.00') ||'/'||to_char(t.vb,'fm999990.00')||'/'||to_char(t.vc,'fm999990.00') as vstr,
 t.vauplimit,t.vadownlimit,t.vazero,
 t.vbuplimit,t.vbdownlimit,t.vbzero,
 t.vcuplimit,t.vcdownlimit,t.vczero,
 t.totalKWattH,t.totalpKWattH,t.totalnKWattH,
 t.totalKVarH,t.totalpKVarH,t.totalnKVarH,
 t.totalKVAH,
 t.watta,t.wattb,t.wattc,t.watt3,
 to_char(t.watta,'fm999990.00') ||'/'||to_char(t.wattb,'fm999990.00')||'/'||to_char(t.wattc,'fm999990.00') as wattstr,
 t.vara,t.varb,t.varc,t.var3,
 to_char(t.vara,'fm999990.00') ||'/'||to_char(t.varb,'fm999990.00')||'/'||to_char(t.varc,'fm999990.00') as varstr,
 t.reversepower,
 t.vaa,t.vab,t.vac,t.va3,
 to_char(t.vaa,'fm999990.00') ||'/'||to_char(t.vab,'fm999990.00')||'/'||to_char(t.vac,'fm999990.00') as vastr,
 t.pfa,t.pfb,t.pfc,t.pf3,
 to_char(t.pfa,'fm999990.00') ||'/'||to_char(t.pfb,'fm999990.00')||'/'||to_char(t.pfc,'fm999990.00') as pfstr,
 t.setfrequency,t.runfrequency,
 t.tubingpressure,t.casingpressure,t.backpressure,t.wellheadfluidtemperature,
 t.signal,t.interval,t.devicever,
 well.videourl, well.sortnum,org.org_code,org.org_id
from
tbl_wellinformation well
left outer join  tbl_org org  on well.orgid=org.org_id
left outer join  tbl_code code1  on  code1.itemvalue=well.liftingtype and code1.itemcode='LiftingType'
left outer join  tbl_pcp_discrete_hist t  on t.wellid=well.id
left outer join  tbl_pcp_productiondata_latest prod  on prod.wellid=well.id
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf t031_1  on  status1.resultcode=t031_1.resultcode
left outer join  tbl_rpc_worktype status2  on  status2.resultcode= decode(t.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_2  on  status2.resultcode=t031_2.resultcode
left outer join  tbl_rpc_worktype status3  on  status3.resultcode= decode(t.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_3  on  status3.resultcode=t031_3.resultcode
left outer join  tbl_rpc_statistics_conf stat1 on t.todayKWattH between stat1.s_min and stat1.s_max and stat1.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat2 on t.runtimeefficiency between stat2.s_min and stat2.s_max and stat2.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat3 on t.commtimeefficiency between stat3.s_min and stat3.s_max and stat3.s_type='TXSL'
where well.liftingtype>=400 and well.liftingtype<500;
/

/*==============================================================*/
/* View: viw_pcp_discrete_latest                                */
/*==============================================================*/
create or replace view viw_pcp_discrete_latest as
select
 t.id,well.wellname,well.liftingtype,code1.itemname as liftingTypeName, t.wellid ,
 comm.commstatus,decode(comm.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_2.alarmsign,0,0,t031_2.alarmlevel) as commAlarmLevel,
 case when prod.runtimeefficiencysource=0 and prod.runtime=0 then 0
      when prod.runtimeefficiencysource=0 and prod.runtime>0 then 1
      else t.runstatus end as runStatus,
 case when comm.commstatus=1 then
           case when prod.runtimeefficiencysource=0 and prod.runtime=0 then '停抽'
                when prod.runtimeefficiencysource=0 and prod.runtime>0 then '运行'
                else decode(t.runstatus,1,'运行','停抽') end
      else '离线' end as runStatusName,
 decode(t031_3.alarmsign,0,0,t031_3.alarmlevel) as runAlarmLevel,
 t.commtime,t.commrange,t.commtimeefficiency as commtimeefficiency ,stat3.s_level as commtimeefficiencyLevel,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,stat2.s_level as runtimeefficiencyLevel,
 t.AcqTime,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode_elec,
 t.resultstring as resultstring_elec,status1.resultname as resultname_elec,status1.optimizationsuggestion as optimizationsuggestion_elec,
 decode(t031_1.alarmsign,0,0,t031_1.alarmlevel) as resultAlarmLevel,
 t.todayKWattH,stat1.s_level as todayKWattHLevel,t.todaypKWattH,t.todaynKWattH,
 t.todayKVarH,t.todaypKVarH,t.todaynKVarH,t.todayKVAH,
 t.ia,t.ib,t.ic,t.iavg,
 to_char(t.ia,'fm999990.00') ||'/'||to_char(t.ib,'fm999990.00')||'/'||to_char(t.ic,'fm999990.00') as istr,
 t.iauplimit,t.iadownlimit,t.iazero,
 t.ibuplimit,t.ibdownlimit,t.ibzero,
 t.icuplimit,t.icdownlimit,t.iczero,
 t.va,t.vb,t.vc,t.vavg,
 to_char(t.va,'fm999990.00') ||'/'||to_char(t.vb,'fm999990.00')||'/'||to_char(t.vc,'fm999990.00') as vstr,
 t.vauplimit,t.vadownlimit,t.vazero,
 t.vbuplimit,t.vbdownlimit,t.vbzero,
 t.vcuplimit,t.vcdownlimit,t.vczero,
 t.totalKWattH,t.totalpKWattH,t.totalnKWattH,
 t.totalKVarH,t.totalpKVarH,t.totalnKVarH,
 t.totalKVAH,
 t.watta,t.wattb,t.wattc,t.watt3,
 to_char(t.watta,'fm999990.00') ||'/'||to_char(t.wattb,'fm999990.00')||'/'||to_char(t.wattc,'fm999990.00') as wattstr,
 t.vara,t.varb,t.varc,t.var3,
 to_char(t.vara,'fm999990.00') ||'/'||to_char(t.varb,'fm999990.00')||'/'||to_char(t.varc,'fm999990.00') as varstr,
 t.reversepower,
 t.vaa,t.vab,t.vac,t.va3,
 to_char(t.vaa,'fm999990.00') ||'/'||to_char(t.vab,'fm999990.00')||'/'||to_char(t.vac,'fm999990.00') as vastr,
 t.pfa,t.pfb,t.pfc,t.pf3,
 to_char(t.pfa,'fm999990.00') ||'/'||to_char(t.pfb,'fm999990.00')||'/'||to_char(t.pfc,'fm999990.00') as pfstr,
 t.setfrequency,t.runfrequency,
 t.tubingpressure,t.casingpressure,t.backpressure,t.wellheadfluidtemperature,
 t.signal,t.interval,t.devicever,
 well.videourl, well.sortnum,org.org_code,org.org_id
from
tbl_wellinformation well
left outer join  tbl_org org  on well.orgid=org.org_id
left outer join  tbl_code code1  on  code1.itemvalue=well.liftingtype and code1.itemcode='LiftingType'
left outer join  tbl_pcp_discrete_latest t  on t.wellid=well.id
left outer join  tbl_pcp_productiondata_latest prod  on prod.wellid=well.id
left outer join  viw_commstatus comm on comm.id=well.id
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf t031_1  on  status1.resultcode=t031_1.resultcode
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=decode(comm.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_2  on  status2.resultcode=t031_2.resultcode
left outer join  tbl_rpc_worktype status3  on  status3.resultcode= decode(t.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_3  on  status3.resultcode=t031_3.resultcode
left outer join  tbl_rpc_statistics_conf stat1 on t.todayKWattH between stat1.s_min and stat1.s_max and stat1.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat2 on t.runtimeefficiency between stat2.s_min and stat2.s_max and stat2.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat3 on t.commtimeefficiency between stat3.s_min and stat3.s_max and stat3.s_type='TXSL'
where well.liftingtype>=400 and well.liftingtype<500;
/

/*==============================================================*/
/* View: viw_pcp_productiondata_hist                            */
/*==============================================================*/
create or replace view viw_pcp_productiondata_hist as
select op.id,w.wellname,w.id as wellId,w.liftingtype,
       op.AcqTime,
       op.runtime,
       op.crudeOilDensity,op.waterDensity,op.naturalGasRelativeDensity,op.saturationPressure,op.reservoirDepth,op.reservoirTemperature,
       op.weightwatercut,op.volumewatercut,
       op.TubingPressure,op.CasingPressure, op.BackPressure,op.WellHeadFluidTemperature,op.ProducingfluidLevel,op.PumpSettingDepth,op.ProductionGasOilRatio,
       op.PumpBoreDiameter,op.PumpType,c3.itemname as PumpTypeName, op.PumpGrade,op.PlungerLength,op.BarrelType,c2.itemname as BarrelTypeName,
       op.BarrelLength,op.BarrelSeries,op.RotorDiameter,op.QPR,
       op.TubingStringInsideDiameter,op.CasingStringInsideDiameter,
       op.rodstring,
       op.AnchoringState,c1.itemname as AnchoringStateName,
       op.NetGrossRatio,op.manualintervention,
       op.runtimeefficiencysource,c5.itemname as RuntimeEfficiencySourceName,
       w.sortnum,o.org_id
from
       tbl_code c1 ,
       tbl_code c2 ,
       tbl_code c3 ,
       tbl_code c4,
       tbl_code c5,
       tbl_pcp_productiondata_hist op
left outer join  tbl_wellinformation  w  on w.id = op.wellid
left outer join  tbl_org o    on  o.org_id=w.orgid
where c1.itemcode='AnchoringState' and c1.itemvalue=op.AnchoringState
      and c2.itemcode='BarrelType' and c2.itemvalue=op.BarrelType
      and c3.itemcode='PumpType' and c3.itemvalue=op.PumpType
      and c4.itemcode='PumpGrade' and c4.itemvalue=op.PumpGrade
      and c5.itemcode='RuntimeEfficiencySource' and c5.itemvalue=op.runtimeefficiencysource
      and w.liftingtype>=400 and w.liftingtype<500;
/

/*==============================================================*/
/* View: viw_pcp_productiondata_latest                          */
/*==============================================================*/
create or replace view viw_pcp_productiondata_latest as
select op.id,w.wellname,w.id as wellId,w.liftingtype,
       op.AcqTime,
       op.runtime,
       op.crudeOilDensity,op.waterDensity,op.naturalGasRelativeDensity,op.saturationPressure,op.reservoirDepth,op.reservoirTemperature,
       op.weightwatercut,op.volumewatercut,
       op.TubingPressure,op.CasingPressure, op.BackPressure,op.WellHeadFluidTemperature,op.ProducingfluidLevel,op.PumpSettingDepth,op.ProductionGasOilRatio,
       op.PumpBoreDiameter,op.PumpType,c3.itemname as PumpTypeName, op.PumpGrade,op.PlungerLength,op.BarrelType,c2.itemname as BarrelTypeName,
       op.BarrelLength,op.BarrelSeries,op.RotorDiameter,op.QPR,
       op.TubingStringInsideDiameter,op.CasingStringInsideDiameter,
       op.rodstring,
       op.AnchoringState,c1.itemname as AnchoringStateName,
       op.NetGrossRatio,op.manualintervention,
       op.runtimeefficiencysource,c5.itemname as RuntimeEfficiencySourceName,
       w.sortnum,o.org_id
from
       tbl_code c1 ,
       tbl_code c2 ,
       tbl_code c3 ,
       tbl_code c4,
       tbl_code c5,
       tbl_pcp_productiondata_latest op
left outer join  tbl_wellinformation  w  on w.id = op.wellid
left outer join  tbl_org o    on  o.org_id=w.orgid
where c1.itemcode='AnchoringState' and c1.itemvalue=op.AnchoringState
      and c2.itemcode='BarrelType' and c2.itemvalue=op.BarrelType
      and c3.itemcode='PumpType' and c3.itemvalue=op.PumpType
      and c4.itemcode='PumpGrade' and c4.itemvalue=op.PumpGrade
      and c5.itemcode='RuntimeEfficiencySource' and c5.itemvalue=op.runtimeefficiencysource
      and w.liftingtype>=400 and w.liftingtype<500;
/

/*==============================================================*/
/* View: viw_pcp_rpm_hist                                       */
/*==============================================================*/
create or replace view viw_pcp_rpm_hist as
select
 t.id,well.wellname, well.id as wellid ,well.liftingtype,t.AcqTime,
 t.rpm,t.torque,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,status1.resultname,status1.optimizationsuggestion,decode(alarm1.alarmsign,0,0,alarm1.alarmlevel) as resultrunAlarmLevel,
 t.theoreticalproduction,
 t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,
 prod.weightwatercut, stat4.s_level as liquidweightproductionlevel,
 t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,
 prod.volumewatercut,stat6.s_level as liquidvolumeproductionlevel,
 prod.productiongasoilratio,prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature,
 prod.qpr,prod.barrellength,prod.barrelseries,prod.rotordiameter,
 prod.producingfluidlevel,prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,prod.pumpborediameter,
 prod.crudeoildensity,prod.netgrossratio,
 t.rodstring,
 t.averagewatt,t.waterpower,
 t.systemefficiency*100 as systemefficiency,stat5.s_level as systemefficiencyLevel,
 t.energyper100mlift,
 t.pumpEff1*100 as pumpEff1,t.pumpEff2*100 as pumpEff2,t.pumpEff*100 as pumpEff,
 t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpIntakevisl,t.pumpIntakebo,
 t.pumpoutletp,t.pumpoutlett,t.pumpoutletgol,t.pumpoutletvisl,t.pumpoutletbo,
 well.videourl,org.org_id,org.org_code, well.sortnum
from
tbl_wellinformation well
left outer join  tbl_org org  on org.org_id=well.orgid
left outer join  tbl_pcp_rpm_hist t  on t.wellid=well.id
left outer join  tbl_pcp_productiondata_hist prod on prod.id=t.productiondataid
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm1  on  alarm1.resultcode=status1.resultcode
left outer join  tbl_rpc_statistics_conf stat4 on t.liquidweightproduction*1 between stat4.s_min and stat4.s_max and stat4.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat6 on t.liquidvolumetricproduction between stat6.s_min and stat6.s_max and stat6.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat5 on t.systemefficiency*100 between stat5.s_min and stat5.s_max and stat5.s_type='XTXL'
where well.liftingtype>=400 and well.liftingtype<500;
/

/*==============================================================*/
/* View: viw_pcp_rpm_latest                                     */
/*==============================================================*/
create or replace view viw_pcp_rpm_latest as
select
 t.id,well.wellname, well.id as wellid ,well.liftingtype,t.AcqTime,
 t.rpm,t.torque,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,status1.resultname,status1.optimizationsuggestion,decode(alarm1.alarmsign,0,0,alarm1.alarmlevel) as resultrunAlarmLevel,
 t.theoreticalproduction,
 t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,
 prod.weightwatercut, stat4.s_level as liquidweightproductionlevel,
 t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,
 prod.volumewatercut,stat6.s_level as liquidvolumeproductionlevel,
 prod.productiongasoilratio,prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature,
 prod.qpr,prod.barrellength,prod.barrelseries,prod.rotordiameter,
 prod.producingfluidlevel,prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,prod.pumpborediameter,
 prod.crudeoildensity,prod.netgrossratio,
 t.rodstring,
 t.averagewatt,t.waterpower,
 t.systemefficiency*100 as systemefficiency,stat5.s_level as systemefficiencyLevel,
 t.energyper100mlift,
 t.pumpEff1*100 as pumpEff1,t.pumpEff2*100 as pumpEff2,t.pumpEff*100 as pumpEff,
 t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpIntakevisl,t.pumpIntakebo,
 t.pumpoutletp,t.pumpoutlett,t.pumpoutletgol,t.pumpoutletvisl,t.pumpoutletbo,
 well.videourl,org.org_id,org.org_code, well.sortnum
from
tbl_wellinformation well
left outer join  tbl_org org  on org.org_id=well.orgid
left outer join  tbl_pcp_rpm_latest t  on t.wellid=well.id
left outer join  tbl_pcp_productiondata_hist prod on prod.id=t.productiondataid
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm1  on  alarm1.resultcode=status1.resultcode
left outer join  tbl_rpc_statistics_conf stat4 on t.liquidweightproduction between stat4.s_min and stat4.s_max and stat4.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat6 on t.liquidvolumetricproduction between stat6.s_min and stat6.s_max and stat6.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat5 on t.systemefficiency*100 between stat5.s_min and stat5.s_max and stat5.s_type='XTXL'
where well.liftingtype>=400 and well.liftingtype<500;
/

/*==============================================================*/
/* View: viw_pcp_total_day                                      */
/*==============================================================*/
create or replace view viw_pcp_total_day as
select
 t.id,well.wellName,well.liftingtype,code1.itemname as liftingTypeName,well.id as wellid ,
 t.calculateDate,
 t.commstatus,decode(t.commstatus,1,'在线','离线') as commStatusName,decode(t031_2.alarmsign,0,0,t031_2.alarmlevel) as commAlarmLevel,
 runStatus,
 case when t.commstatus=1 then
           decode(t.runstatus,1,'运行','停抽')
      else '离线' end as runStatusName,
 decode(t031_3.alarmsign,0,0,t031_3.alarmlevel) as runAlarmLevel,
 t.commTime,t.commrange,t.commtimeefficiency as commtimeefficiency ,stat3.s_level as commtimeefficiencyLevel,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,stat2.s_level as runtimeefficiencyLevel,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,
 status.resultname as resultname,t.resultString,status.optimizationsuggestion,
 decode(alarm.alarmsign,0,0,alarm.alarmlevel) as resultAlarmLevel,
 t.liquidweightproduction,
 decode(stat7.s_level,null,'无数据',stat7.s_level) as liquidWeightProductionlevel,
 t.oilweightproduction,t.waterweightproduction,
 t.weightwatercut,
 t.liquidvolumetricproduction,
 decode(stat4.s_level,null,'无数据',stat4.s_level) as liquidVolumeProductionlevel,
 t.oilvolumetricproduction,t.watervolumetricproduction,
 t.volumewatercut,
 t.productiongasoilratio,t.tubingpressure,t.casingpressure,t.wellheadfluidtemperature,
 t.pumpeff*100 as pumpeff,
 t.pumpborediameter,t.pumpsettingdepth,t.producingfluidlevel,t.submergence,
 t.rpm,t.rpmmax,t.rpmmin,
 t.systemefficiency*100 as systemEfficiency,decode(stat8.s_level,null,'无数据',stat8.s_level) as systemEfficiencyLevel,
 t.energyper100mlift,
 t.todayKWattH,decode(stat1.s_level,null,'无数据',stat1.s_level) as todayKWattHLevel,
 t.todaypKWattH,t.todaynKWattH,t.todayKVarH,t.todaypKVarH,t.todaynKVarH,t.todayKVAH,
 t.ia,t.iamax,t.iamin,t.iamax||'/'||t.iamin||'/'||t.ia as iastr,
 t.ib,t.ibmax,t.ibmin,t.ibmax||'/'||t.ibmin||'/'||t.ib as ibstr,
 t.ic,t.icmax,t.icmin,t.icmax||'/'||t.icmin||'/'||t.ic as icstr,
 t.va,t.vamax,t.vamin,t.vamax||'/'||t.vamin||'/'||t.va as vastr,
 t.vb,t.vbmax,t.vbmin,t.vbmax||'/'||t.vbmin||'/'||t.vb as vbstr,
 t.vc,t.vcmax,t.vcmin,t.vcmax||'/'||t.vcmin||'/'||t.vc as vcstr,
 t.signal,t.signalmax,t.signalmin,t.signalmax||'/'||t.signalmin||'/'||t.signal as signalstr,
 well.videourl, well.sortnum,org.org_code,org.org_id,null as remark
from
tbl_wellinformation well
left outer join  tbl_org org  on well.orgid=org.org_id
left outer join  tbl_code code1  on  code1.itemvalue=well.liftingtype and code1.itemcode='LiftingType'
left outer join  tbl_pcp_total_day t  on t.wellid=well.id
left outer join  tbl_rpc_worktype status  on  status.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm  on  alarm.resultcode=status.resultcode
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=t.commstatus+1101
left outer join  tbl_rpc_alarmtype_conf t031_2  on  status2.resultcode=t031_2.resultcode
left outer join  tbl_rpc_worktype status3  on  status3.resultcode= decode(t.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_3  on  status3.resultcode=t031_3.resultcode
left outer join  tbl_rpc_statistics_conf stat1 on t.todayKWattH between stat1.s_min and stat1.s_max and stat1.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat2 on t.runtimeefficiency between stat2.s_min and stat2.s_max and stat2.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat3 on t.commtimeefficiency between stat3.s_min and stat3.s_max and stat3.s_type='TXSL'
left outer join  tbl_rpc_statistics_conf stat7 on t.liquidweightproduction between stat7.s_min and stat7.s_max and stat7.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat4 on t.liquidvolumetricproduction between stat4.s_min and stat4.s_max and stat4.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat8 on t.systemefficiency*100 between stat8.s_min and stat8.s_max and stat8.s_type='XTXL'
where well.liftingtype>=400 and well.liftingtype<500;
/

/*==============================================================*/
/* View: viw_rpc_calculatemain                                  */
/*==============================================================*/
create or replace view viw_rpc_calculatemain as
select t.id as id,
well.wellName,well.liftingType,
t.AcqTime,ws.resultName,
t.liquidWeightProduction,t.oilWeightProduction,
t.liquidVolumetricProduction,t.oilVolumetricProduction,
prod.crudeoilDensity,prod.waterDensity,prod.naturalGasRelativeDensity,
prod.saturationPressure,prod.reservoirDepth,prod.reservoirTemperature,
prod.tubingPressure,prod.casingPressure,prod.wellHeadFluidTemperature,prod.weightwatercut,prod.productionGasOilRatio,prod.producingFluidLevel,
prod.pumpSettingDepth,
prod.pumptype,code3.itemname as PumpTypeName,
prod.barreltype,code2.itemname as BarrelTypeName,
prod.pumpGrade,prod.pumpboreDiameter,prod.plungerLength,
prod.tubingStringInsideDiameter,prod.casingStringInsideDiameter,
prod.rodstring,
code.itemname as anchoringStateName, prod.netGrossRatio,t.resultStatus,
well.orgid
from tbl_rpc_diagram_hist t
left outer join tbl_wellinformation well on t.wellid=well.id
left outer join tbl_rpc_worktype ws on  t.resultcode=ws.resultcode
left outer join tbl_rpc_productiondata_hist prod on prod.id=t.productiondataid
left outer join tbl_code code on code.itemvalue=prod.anchoringstate and code.itemcode='AnchoringState'
left outer join tbl_code code2 on code2.itemvalue=prod.barreltype and code2.itemcode='BarrelType'
left outer join tbl_code code3 on code3.itemvalue=prod.pumptype and code3.itemcode='PumpType'
where well.liftingtype>=200 and well.liftingtype<300;
/

/*==============================================================*/
/* View: viw_rpc_calculatemain_elec                             */
/*==============================================================*/
create or replace view viw_rpc_calculatemain_elec as
select t.id as id,
well.wellName,
t.AcqTime,t.inverresultstatus as resultStatus,
t2.manufacturer,t2.model,t2.stroke,t2.crankrotationdirection,
t2.offsetangleofcrank,t2.crankgravityradius,t2.SingleCrankWeight,t2.singlecrankpinweight,t2.StructuralUnbalance,t2.BalancePosition,t2.BalanceWeight,
t3.OffsetAngleOfCrankPS,t3.SurfaceSystemEfficiency,t3.FS_LeftPercent,t3.FS_RightPercent,t3.WattAngle,
t3.FilterTime_Watt,t3.FilterTime_I,t3.FilterTime_RPM,t3.FilterTime_FSDiagram,t3.FilterTime_FSDiagram_L,t3.FilterTime_FSDiagram_R,
well.orgid
from tbl_rpc_diagram_hist t
left outer join tbl_wellinformation well on t.wellid=well.id
left outer join tbl_rpcinformation t2 on t2.wellid=well.id
left outer join tbl_rpc_inver_opt t3 on t3.wellid=well.id
where t.datasource=1;
/

/*==============================================================*/
/* View: viw_rpc_comprehensive_hist                             */
/*==============================================================*/
create or replace view viw_rpc_comprehensive_hist as
select
 t.id,well.wellName, well.id as wellid ,well.liftingType,
 t.AcqTime as AcqTime,dis.AcqTime as AcqTime_d,
 dis.commStatus,decode(dis.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_8.alarmsign,0,0,t031_8.alarmlevel) as commAlarmLevel,
 case when prod.runtimeefficiencysource=0 and prod.runtime=0 then 0
      when prod.runtimeefficiencysource=0 and prod.runtime>0 then 1
      else dis.runstatus end as runStatus,
 case when dis.commstatus=1 then
           case when prod.runtimeefficiencysource=0 and prod.runtime=0 then '停抽'
                when prod.runtimeefficiencysource=0 and prod.runtime>0 then '运行'
                else decode(dis.runstatus,1,'运行','停抽') end
      else '离线' end as runStatusName,
 decode(t031_9.alarmsign,0,0,t031_9.alarmlevel) as runAlarmLevel,
 dis.commTime,dis.commRange,dis.commTimeEfficiency as commTimeEfficiency ,stat10.s_level as commtimeefficiencyLevel,
 dis.runTime,dis.runRange,dis.runTimeEfficiency as runTimeEfficiency,stat9.s_level as runtimeefficiencyLevel,
 decode(t.datasource,0,'功图仪',1,'电参反演',null,'','人工上传') as datasource,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,
 status1.resultName,
 status1.optimizationSuggestion,
 decode(alarm1.alarmsign,0,0,alarm1.alarmlevel) as resultAlarmLevel,
 dis.resultcode as resultcode_E,
 status10.resultname as resultName_E,
 case when dis.resultcode=0 then 0
      else decode(alarm10.alarmsign,0,0,alarm10.alarmlevel) end as resultAlarmLevel_E,
 dis.resultstring as resultString_E,
 t.theoreticalProduction,
 t.liquidweightproduction,
 t.oilweightproduction,
 t.waterweightproduction,
 prod.weightwatercut, stat4.s_level as liquidweightproductionlevel,
 t.liquidvolumetricproduction,
 t.oilvolumetricproduction,
 t.watervolumetricproduction,
 prod.volumewatercut, stat11.s_level as liquidvolumeproductionlevel,
 total.liquidweightproduction as liquidweightproduction_l,total.oilweightproduction as oilweightproduction_l, total.waterweightproduction as waterweightproduction_l,
 total.liquidvolumetricproduction as liquidvolumetricproduction_l,total.oilvolumetricproduction as oilvolumetricproduction_l, total.watervolumetricproduction as watervolumetricproduction_l,
 prod.productionGasOilRatio,
 prod.tubingPressure,prod.casingPressure,
 prod.wellheadFluidTemperature,
 case when prod.producingfluidLevel>=0 then prod.producingfluidLevel
   when  prod.producingfluidLevel is null then prod.producingfluidLevel
   else t.inverproducingfluidlevel end as producingfluidLevel,
 case when prod.producingfluidLevel>=0 then '动液面仪'
   when prod.producingfluidLevel is null then ''
   else '泵功图反演' end as producingfluidLevelDataSource,
 t.levelcorrectvalue,
 prod.pumpSettingDepth,
 case when prod.producingfluidLevel>=0 then prod.pumpsettingdepth-prod.producingfluidLevel
   when  prod.producingfluidLevel is null then prod.pumpsettingdepth-prod.producingfluidLevel
   else prod.pumpsettingdepth-t.inverproducingfluidlevel end as submergence,
 prod.pumpBoreDiameter,
 prod.crudeoildensity,prod.netgrossratio,
 t.availableplungerstrokeprod_W,
 t.pumpclearanceleakprod_W,
 t.tvleakweightproduction,
 t.svleakweightproduction,
 t.gasinfluenceprod_W,
 t.availableplungerstrokeprod_v,
 t.pumpclearanceleakprod_v,
 t.tvleakvolumetricproduction,
 t.svleakvolumetricproduction,
 t.gasinfluenceprod_v,
 t.rodString,
 t.stroke,t.spm,
 t.upperloadline,t.upperLoadLineOfExact,t.lowerloadline,t.upperloadline-t.lowerloadline as deltaLoadLine,
 t.fmax,t.fmin,t.fmax-t.fmin as deltaF,
 t.fullnesscoEfficient,t.noliquidfullnesscoefficient,
 t.plungerstroke,t.availableplungerstroke,t.noliquidavailableplungerstroke,
 t.averagewatt,t.polishrodPower,t.waterPower,
 t.systemefficiency*100 as systemEfficiency,stat5.s_level as systemEfficiencyLevel,
 t.surfacesystemefficiency*100 as surfaceSystemEfficiency,stat6.s_level as surfaceSystemEfficiencyLevel,
 t.welldownsystemefficiency*100 as welldownSystemEfficiency,stat7.s_level as wellDownSystemEfficiencyLevel,
 t.area,t.energyper100mlift,
 t.iDegreeBalance,
 status2.resultname as iDegreeBalanceName,
 t.upstrokeimax,t.downstrokeimax,
 case when t.downstrokeimax is null or t.upstrokeimax is null then null else to_char(t.downstrokeimax,'fm999990.00') ||'/'||to_char(t.upstrokeimax,'fm999990.00') end as iRatio,
 decode(alarm2.alarmsign,0,0,alarm2.alarmlevel) as iDegreeBalanceAlarmLevel,
 t.wattDegreeBalance,
 status3.resultname as wattDegreeBalanceName,
 t.upstrokewattmax,t.downstrokewattmax,
 case when t.downstrokewattmax is null or t.upstrokewattmax is null then null else to_char(t.downstrokewattmax,'fm999990.00') ||'/'||to_char(t.upstrokewattmax,'fm999990.00') end as wattRatio,
 decode(alarm3.alarmsign,0,0,alarm3.alarmlevel) as wattDegreeBalanceAlarmlevel,
 t.deltaradius*100 as deltaradius,
 t.pumpEff1*100 as pumpEff1,t.pumpEff2*100 as pumpEff2,t.pumpEff3*100 as pumpEff3,t.pumpEff4*100 as pumpEff4,t.pumpEff*100 as pumpEff,
 t.rodFlexLength,t.tubingFlexLength,t.inertiaLength,
 t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpIntakevisl,t.pumpIntakebo,
 t.pumpoutletp,t.pumpoutlett,t.pumpOutletGol,t.pumpoutletvisl,t.pumpoutletbo,
 dis.acqcycle_diagram,dis.acqcycle_discrete,
 dis.todayKWattH,stat8.s_level as todayKWattHLevel,dis.todaypKWattH,dis.todaynKWattH,
 dis.todayKVarH,dis.todaypKVarH,dis.todaynKVarH,dis.todayKVAH,
 dis.ia,dis.ib,dis.ic,dis.iavg,
 to_char(dis.ia,'fm999990.00') ||'/'||to_char(dis.ib,'fm999990.00')||'/'||to_char(dis.ic,'fm999990.00') as istr,
 dis.iauplimit,dis.iadownlimit,dis.iazero,
 dis.ibuplimit,dis.ibdownlimit,dis.ibzero,
 dis.icuplimit,dis.icdownlimit,dis.iczero,
 dis.wattuplimit,dis.wattdownlimit,
 dis.iamax,dis.iamin,dis.ibmax,dis.ibmin,dis.icmax,dis.icmin,
 dis.va,dis.vb,dis.vc,dis.vavg,
 to_char(dis.va,'fm999990.00') ||'/'||to_char(dis.vb,'fm999990.00')||'/'||to_char(dis.vc,'fm999990.00') as vstr,
 dis.vauplimit,dis.vadownlimit,dis.vazero,
 dis.vbuplimit,dis.vbdownlimit,dis.vbzero,
 dis.vcuplimit,dis.vcdownlimit,dis.vczero,
 dis.totalKWattH,dis.totalpKWattH,dis.totalnKWattH,
 dis.totalKVarH,dis.totalpKVarH,dis.totalnKVarH,
 dis.totalKVAH,
 dis.watta,dis.wattb,dis.wattc,dis.watt3,
 to_char(dis.watta,'fm999990.00') ||'/'||to_char(dis.wattb,'fm999990.00')||'/'||to_char(dis.wattc,'fm999990.00') as wattstr,
 dis.vara,dis.varb,dis.varc,dis.var3,
 to_char(dis.vara,'fm999990.00') ||'/'||to_char(dis.varb,'fm999990.00')||'/'||to_char(dis.varc,'fm999990.00') as varstr,
 dis.reversePower,
 dis.vaa,dis.vab,dis.vac,dis.va3,
 to_char(dis.vaa,'fm999990.00') ||'/'||to_char(dis.vab,'fm999990.00')||'/'||to_char(dis.vac,'fm999990.00') as vastr,
 dis.pfa,dis.pfb,dis.pfc,dis.pf3,
 to_char(dis.pfa,'fm999990.00') ||'/'||to_char(dis.pfb,'fm999990.00')||'/'||to_char(dis.pfc,'fm999990.00') as pfstr,
 dis.Setfrequency,dis.Runfrequency,
 dis.signal,dis.interval,dis.devicever,
 decode(dis.balanceControlMode,0,'手动',1,'自动') as balanceControlMode,
 decode(dis.balanceCalculateMode,1,'下行程最大值/上行程最大值',2,'上行程最大值/下行程最大值') as balanceCalculateMode,
 dis.balanceAwayTime,dis.balanceCloseTime,
 dis.balanceAwayTimePerBeat,dis.balanceCloseTimePerBeat,
 dis.balanceStrokeCount,
 dis.balanceOperationUpLimit,dis.balanceOperationDownLimit,
 decode(dis.balanceAutoControl,0,'允许',1,'禁止') as balanceAutoControl,
 decode(dis.spmAutoControl,0,'允许',1,'禁止') as spmAutoControl,
 decode(dis.balanceFrontLimit,0,'限位',1,'未限位') as balanceFrontLimit,
 decode(dis.balanceAfterLimit,0,'限位',1,'未限位') as balanceAfterLimit,
 well.videourl,org.org_id,org.org_code, well.sortnum
from
tbl_wellinformation well
left outer join  tbl_org org  on org.org_id=well.orgid
left outer join  tbl_rpc_diagram_hist t  on t.wellid=well.id
left outer join  tbl_rpc_discrete_hist dis  on dis.wellid=well.id and dis.id=t.discretedataid
left outer join  tbl_rpc_productiondata_hist prod on prod.id=t.productiondataid
left outer join  tbl_rpc_diagram_total total on t.wellid=total.wellid and t.acqtime=total.acqtime
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm1  on  alarm1.resultcode=status1.resultcode
left outer join  tbl_rpc_worktype status10  on  status10.resultcode=decode(dis.resultcode,null,1100,0,1100,dis.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm10  on  status10.resultcode=alarm10.resultcode
left outer join  tbl_rpc_worktype status8  on  status8.resultcode=decode(dis.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_8  on  status8.resultcode=t031_8.resultcode
left outer join  tbl_rpc_worktype status9  on  status9.resultcode= decode(dis.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_9  on  status9.resultcode=t031_9.resultcode
left outer join  tbl_rpc_statistics_conf stat2 on t.idegreebalance between stat2.s_min and stat2.s_max and stat2.s_type='PHD'
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=decode(t.idegreebalance ,null,1100,0,1100,stat2.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm2  on  status2.resultcode=alarm2.resultcode
left outer join  tbl_rpc_statistics_conf stat3 on t.wattdegreebalance between stat3.s_min and stat3.s_max and stat3.s_type='GLPHD'
left outer join  tbl_rpc_worktype status3  on  status3.resultcode=decode(t.wattdegreebalance ,null,1100,0,1100,stat3.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm3  on  alarm3.resultcode=status3.resultcode
left outer join  tbl_rpc_statistics_conf stat4 on t.liquidweightproduction between stat4.s_min and stat4.s_max and stat4.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat11 on t.liquidvolumetricproduction between stat11.s_min and stat11.s_max and stat11.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat5 on t.systemefficiency*100 between stat5.s_min and stat5.s_max and stat5.s_type='XTXL'
left outer join  tbl_rpc_statistics_conf stat6 on t.surfacesystemefficiency*100 between stat6.s_min and stat6.s_max and stat6.s_type='DMXL'
left outer join  tbl_rpc_statistics_conf stat7 on t.welldownsystemefficiency*100 between stat7.s_min and stat7.s_max and stat7.s_type='JXXL'
left outer join  tbl_rpc_statistics_conf stat8 on dis.todayKWattH between stat8.s_min and stat8.s_max and stat8.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat9 on dis.runtimeefficiency between stat9.s_min and stat9.s_max and stat9.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat10 on dis.commtimeefficiency between stat10.s_min and stat10.s_max and stat10.s_type='TXSL'
where well.liftingtype>=200 and well.liftingtype<300;
/

/*==============================================================*/
/* View: viw_rpc_comprehensive_latest                           */
/*==============================================================*/
create or replace view viw_rpc_comprehensive_latest as
select
 t.id,well.wellName, well.id as wellid ,well.liftingType,
 t.AcqTime as AcqTime,dis.AcqTime as AcqTime_d,
 dis.commStatus,decode(dis.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_8.alarmsign,0,0,t031_8.alarmlevel) as commAlarmLevel,
 case when prod.runtimeefficiencysource=0 and prod.runtime=0 then 0
      when prod.runtimeefficiencysource=0 and prod.runtime>0 then 1
      else dis.runstatus end as runStatus,
 case when dis.commstatus=1 then
           case when prod.runtimeefficiencysource=0 and prod.runtime=0 then '停抽'
                when prod.runtimeefficiencysource=0 and prod.runtime>0 then '运行'
                else decode(dis.runstatus,1,'运行','停抽') end
      else '离线' end as runStatusName,
 decode(t031_9.alarmsign,0,0,t031_9.alarmlevel) as runAlarmLevel,
 dis.commTime,dis.commRange,dis.commTimeEfficiency as commTimeEfficiency ,stat10.s_level as commtimeefficiencyLevel,
 dis.runTime,dis.runRange,dis.runTimeEfficiency as runTimeEfficiency,stat9.s_level as runtimeefficiencyLevel,
 decode(t.datasource,0,'功图仪',1,'电参反演',null,'','人工上传') as datasource,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,
 status1.resultName,
 status1.optimizationSuggestion,
 decode(alarm1.alarmsign,0,0,alarm1.alarmlevel) as resultAlarmLevel,
 dis.resultcode as resultcode_E,
 status10.resultname as resultName_E,
 case when dis.resultcode=0 then 0
      else decode(alarm10.alarmsign,0,0,alarm10.alarmlevel) end as resultAlarmLevel_E,
 dis.resultstring as resultString_E,
 t.theoreticalProduction,
 t.liquidweightproduction,
 t.oilweightproduction,
 t.waterweightproduction,
 prod.weightwatercut, stat4.s_level as liquidweightproductionlevel,
 t.liquidvolumetricproduction,
 t.oilvolumetricproduction,
 t.watervolumetricproduction,
 prod.volumewatercut, stat11.s_level as liquidvolumeproductionlevel,
 total.liquidweightproduction as liquidweightproduction_l,total.oilweightproduction as oilweightproduction_l, total.waterweightproduction as waterweightproduction_l,
 total.liquidvolumetricproduction as liquidvolumetricproduction_l,total.oilvolumetricproduction as oilvolumetricproduction_l, total.watervolumetricproduction as watervolumetricproduction_l,
 prod.productionGasOilRatio,
 prod.tubingPressure,prod.casingPressure,
 prod.wellheadFluidTemperature,
 case when prod.producingfluidLevel>=0 then prod.producingfluidLevel
   when  prod.producingfluidLevel is null then prod.producingfluidLevel
   else t.inverproducingfluidlevel end as producingfluidLevel,
 case when prod.producingfluidLevel>=0 then '动液面仪'
   when prod.producingfluidLevel is null then ''
   else '泵功图反演' end as producingfluidLevelDataSource,
 t.levelcorrectvalue,
 prod.pumpSettingDepth,
 case when prod.producingfluidLevel>=0 then prod.pumpsettingdepth-prod.producingfluidLevel
   when  prod.producingfluidLevel is null then prod.pumpsettingdepth-prod.producingfluidLevel
   else prod.pumpsettingdepth-t.inverproducingfluidlevel end as submergence,
 prod.pumpBoreDiameter,
 prod.crudeoildensity,prod.netgrossratio,
 t.availableplungerstrokeprod_W,
 t.pumpclearanceleakprod_W,
 t.tvleakweightproduction,
 t.svleakweightproduction,
 t.gasinfluenceprod_W,
 t.availableplungerstrokeprod_v,
 t.pumpclearanceleakprod_v,
 t.tvleakvolumetricproduction,
 t.svleakvolumetricproduction,
 t.gasinfluenceprod_v,
 t.rodString,
 t.stroke,t.spm,
 t.upperloadline,t.upperLoadLineOfExact,t.lowerloadline,t.upperloadline-t.lowerloadline as deltaLoadLine,
 t.fmax,t.fmin,t.fmax-t.fmin as deltaF,
 t.fullnesscoEfficient,t.noliquidfullnesscoefficient,
 t.plungerstroke,t.availableplungerstroke,t.noliquidavailableplungerstroke,
 t.averagewatt,t.polishrodPower,t.waterPower,
 t.systemefficiency*100 as systemEfficiency,stat5.s_level as systemEfficiencyLevel,
 t.surfacesystemefficiency*100 as surfaceSystemEfficiency,stat6.s_level as surfaceSystemEfficiencyLevel,
 t.welldownsystemefficiency*100 as welldownSystemEfficiency,stat7.s_level as wellDownSystemEfficiencyLevel,
 t.area,t.energyper100mlift,
 t.iDegreeBalance,
 status2.resultname as iDegreeBalanceName,
 t.upstrokeimax,t.downstrokeimax,
 case when t.downstrokeimax is null or t.upstrokeimax is null then null else to_char(t.downstrokeimax,'fm999990.00') ||'/'||to_char(t.upstrokeimax,'fm999990.00') end as iRatio,
 decode(alarm2.alarmsign,0,0,alarm2.alarmlevel) as iDegreeBalanceAlarmLevel,
 t.wattDegreeBalance,
 status3.resultname as wattDegreeBalanceName,
 t.upstrokewattmax,t.downstrokewattmax,
 case when t.downstrokewattmax is null or t.upstrokewattmax is null then null else to_char(t.downstrokewattmax,'fm999990.00') ||'/'||to_char(t.upstrokewattmax,'fm999990.00') end as wattRatio,
 decode(alarm3.alarmsign,0,0,alarm3.alarmlevel) as wattDegreeBalanceAlarmlevel,
 t.deltaradius*100 as deltaradius,
 t.pumpEff1*100 as pumpEff1,t.pumpEff2*100 as pumpEff2,t.pumpEff3*100 as pumpEff3,t.pumpEff4*100 as pumpEff4,t.pumpEff*100 as pumpEff,
 t.rodFlexLength,t.tubingFlexLength,t.inertiaLength,
 t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpIntakevisl,t.pumpIntakebo,
 t.pumpoutletp,t.pumpoutlett,t.pumpOutletGol,t.pumpoutletvisl,t.pumpoutletbo,
 dis.acqcycle_diagram,dis.acqcycle_discrete,
 dis.todayKWattH,stat8.s_level as todayKWattHLevel,dis.todaypKWattH,dis.todaynKWattH,
 dis.todayKVarH,dis.todaypKVarH,dis.todaynKVarH,dis.todayKVAH,
 dis.ia,dis.ib,dis.ic,dis.iavg,
 to_char(dis.ia,'fm999990.00') ||'/'||to_char(dis.ib,'fm999990.00')||'/'||to_char(dis.ic,'fm999990.00') as istr,
 dis.iauplimit,dis.iadownlimit,dis.iazero,
 dis.ibuplimit,dis.ibdownlimit,dis.ibzero,
 dis.icuplimit,dis.icdownlimit,dis.iczero,
 dis.wattuplimit,dis.wattdownlimit,
 dis.iamax,dis.iamin,dis.ibmax,dis.ibmin,dis.icmax,dis.icmin,
 dis.va,dis.vb,dis.vc,dis.vavg,
 to_char(dis.va,'fm999990.00') ||'/'||to_char(dis.vb,'fm999990.00')||'/'||to_char(dis.vc,'fm999990.00') as vstr,
 dis.vauplimit,dis.vadownlimit,dis.vazero,
 dis.vbuplimit,dis.vbdownlimit,dis.vbzero,
 dis.vcuplimit,dis.vcdownlimit,dis.vczero,
 dis.totalKWattH,dis.totalpKWattH,dis.totalnKWattH,
 dis.totalKVarH,dis.totalpKVarH,dis.totalnKVarH,
 dis.totalKVAH,
 dis.watta,dis.wattb,dis.wattc,dis.watt3,
 to_char(dis.watta,'fm999990.00') ||'/'||to_char(dis.wattb,'fm999990.00')||'/'||to_char(dis.wattc,'fm999990.00') as wattstr,
 dis.vara,dis.varb,dis.varc,dis.var3,
 to_char(dis.vara,'fm999990.00') ||'/'||to_char(dis.varb,'fm999990.00')||'/'||to_char(dis.varc,'fm999990.00') as varstr,
 dis.reversePower,
 dis.vaa,dis.vab,dis.vac,dis.va3,
 to_char(dis.vaa,'fm999990.00') ||'/'||to_char(dis.vab,'fm999990.00')||'/'||to_char(dis.vac,'fm999990.00') as vastr,
 dis.pfa,dis.pfb,dis.pfc,dis.pf3,
 to_char(dis.pfa,'fm999990.00') ||'/'||to_char(dis.pfb,'fm999990.00')||'/'||to_char(dis.pfc,'fm999990.00') as pfstr,
 dis.Setfrequency,dis.Runfrequency,
 dis.signal,dis.interval,dis.devicever,
 decode(dis.balanceControlMode,0,'手动',1,'自动') as balanceControlMode,
 decode(dis.balanceCalculateMode,1,'下行程最大值/上行程最大值',2,'上行程最大值/下行程最大值') as balanceCalculateMode,
 dis.balanceAwayTime,dis.balanceCloseTime,
 dis.balanceAwayTimePerBeat,dis.balanceCloseTimePerBeat,
 dis.balanceStrokeCount,
 dis.balanceOperationUpLimit,dis.balanceOperationDownLimit,
 decode(dis.balanceAutoControl,0,'允许',1,'禁止') as balanceAutoControl,
 decode(dis.spmAutoControl,0,'允许',1,'禁止') as spmAutoControl,
 decode(dis.balanceFrontLimit,0,'限位',1,'未限位') as balanceFrontLimit,
 decode(dis.balanceAfterLimit,0,'限位',1,'未限位') as balanceAfterLimit,
 well.videourl,org.org_id,org.org_code, well.sortnum
from
tbl_wellinformation well
left outer join  tbl_org org  on org.org_id=well.orgid
left outer join  tbl_rpc_diagram_latest t  on t.wellid=well.id
left outer join  tbl_rpc_discrete_latest dis  on dis.wellid=well.id
left outer join  tbl_rpc_productiondata_hist prod on prod.id=t.productiondataid
left outer join  tbl_rpc_diagram_total total on t.wellid=total.wellid and t.acqtime=total.acqtime
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm1  on  alarm1.resultcode=status1.resultcode
left outer join  tbl_rpc_worktype status10  on  status10.resultcode=decode(dis.resultcode,null,1100,0,1100,dis.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm10  on  status10.resultcode=alarm10.resultcode
left outer join  tbl_rpc_worktype status8  on  status8.resultcode=decode(dis.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_8  on  status8.resultcode=t031_8.resultcode
left outer join  tbl_rpc_worktype status9  on  status9.resultcode= decode(dis.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_9  on  status9.resultcode=t031_9.resultcode
left outer join  tbl_rpc_statistics_conf stat2 on t.idegreebalance between stat2.s_min and stat2.s_max and stat2.s_type='PHD'
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=decode(t.idegreebalance ,null,1100,0,1100,stat2.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm2  on  status2.resultcode=alarm2.resultcode
left outer join  tbl_rpc_statistics_conf stat3 on t.wattdegreebalance between stat3.s_min and stat3.s_max and stat3.s_type='GLPHD'
left outer join  tbl_rpc_worktype status3  on  status3.resultcode=decode(t.wattdegreebalance ,null,1100,0,1100,stat3.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm3  on  alarm3.resultcode=status3.resultcode
left outer join  tbl_rpc_statistics_conf stat4 on t.liquidweightproduction between stat4.s_min and stat4.s_max and stat4.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat11 on t.liquidvolumetricproduction between stat11.s_min and stat11.s_max and stat11.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat5 on t.systemefficiency*100 between stat5.s_min and stat5.s_max and stat5.s_type='XTXL'
left outer join  tbl_rpc_statistics_conf stat6 on t.surfacesystemefficiency*100 between stat6.s_min and stat6.s_max and stat6.s_type='DMXL'
left outer join  tbl_rpc_statistics_conf stat7 on t.welldownsystemefficiency*100 between stat7.s_min and stat7.s_max and stat7.s_type='JXXL'
left outer join  tbl_rpc_statistics_conf stat8 on dis.todayKWattH between stat8.s_min and stat8.s_max and stat8.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat9 on dis.runtimeefficiency between stat9.s_min and stat9.s_max and stat9.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat10 on dis.commtimeefficiency between stat10.s_min and stat10.s_max and stat10.s_type='TXSL'
where well.liftingtype>=200 and well.liftingtype<300;
/

/*==============================================================*/
/* View: viw_rpc_diagramquery_hist                              */
/*==============================================================*/
create or replace view viw_rpc_diagramquery_hist as
select
 t.id,well.wellname,t.AcqTime,t.stroke,t.spm,t.fmax,t.fmin,
 t.position_curve,t.load_curve,t.power_curve,t.current_curve,t.rpm_curve,
 t.rawpower_curve,t.rawcurrent_curve,t.rawrpm_curve,
 t.resultcode,status.resultname,
 decode(alarm.alarmsign,0,0,alarm.alarmlevel) as resultAlarmLevel,
 t.upstrokeimax,t.downstrokeimax,t.idegreebalance,
 status2.resultname as idegreebalanceLevel,
 decode(alarm2.alarmsign,0,0,alarm2.alarmlevel) as idegreebalanceAlarmLevel,
 t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,
 status3.resultname as wattdegreebalanceLevel,
 decode(alarm3.alarmsign,0,0,alarm3.alarmlevel) as wattdegreebalanceAlarmlevel,
 t.datasource,
 t.upperloadline,t.lowerloadline,t.liquidweightproduction,t.liquidvolumetricproduction,
 total.liquidweightproduction as liquidweightproduction_l,total.liquidvolumetricproduction as liquidvolumetricproduction_l,
 t.signal,t.devicever,t.interval,well.orgid,well.sortnum
from tbl_wellinformation well
left outer join    tbl_rpc_diagram_hist t   on  well.id=t.wellid
left outer join  tbl_rpc_diagram_total total on t.wellid=total.wellid and t.acqtime=total.acqtime
left outer join    tbl_rpc_worktype status       on  status.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join    tbl_rpc_alarmtype_conf alarm  on  status.resultcode=alarm.resultcode
left outer join  tbl_rpc_statistics_conf stat2 on t.idegreebalance between stat2.s_min and stat2.s_max and stat2.s_type='PHD'
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=decode(t.idegreebalance ,null,1100,0,1100,stat2.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm2  on  status2.resultcode=alarm2.resultcode
left outer join  tbl_rpc_statistics_conf stat3 on t.wattdegreebalance between stat3.s_min and stat3.s_max and stat3.s_type='GLPHD'
left outer join  tbl_rpc_worktype status3  on  status3.resultcode=decode(t.wattdegreebalance ,null,1100,0,1100,stat3.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm3  on  alarm3.resultcode=status3.resultcode
where well.liftingtype between 200 and 299;
/

/*==============================================================*/
/* View: viw_rpc_diagramquery_latest                            */
/*==============================================================*/
create or replace view viw_rpc_diagramquery_latest as
select
 t.id,well.wellname,t.AcqTime,t.stroke,t.spm,t.fmax,t.fmin,
 t.position_curve,t.load_curve,t.power_curve,t.current_curve,t.rpm_curve,
 t.rawpower_curve,t.rawcurrent_curve,t.rawrpm_curve,
 t.resultcode,status.resultname,
 decode(alarm.alarmsign,0,0,alarm.alarmlevel) as resultAlarmLevel,
 t.upstrokeimax,t.downstrokeimax,t.idegreebalance,
 status2.resultname as idegreebalanceLevel,
 decode(alarm2.alarmsign,0,0,alarm2.alarmlevel) as idegreebalanceAlarmLevel,
 t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,
 status3.resultname as wattdegreebalanceLevel,
 decode(alarm3.alarmsign,0,0,alarm3.alarmlevel) as wattdegreebalanceAlarmlevel,
 t.datasource,
 t.upperloadline,t.lowerloadline,t.liquidweightproduction,t.liquidvolumetricproduction,
 total.liquidweightproduction as liquidweightproduction_l,total.liquidvolumetricproduction as liquidvolumetricproduction_l,
 t.signal,t.devicever,t.interval,well.orgid,well.sortnum
from tbl_wellinformation well
left outer join    tbl_rpc_diagram_latest t   on  well.id=t.wellid
left outer join  tbl_rpc_diagram_total total on t.wellid=total.wellid and t.acqtime=total.acqtime
left outer join    tbl_rpc_worktype status       on  status.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join    tbl_rpc_alarmtype_conf alarm  on  status.resultcode=alarm.resultcode
left outer join  tbl_rpc_statistics_conf stat2 on t.idegreebalance between stat2.s_min and stat2.s_max and stat2.s_type='PHD'
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=decode(t.idegreebalance ,null,1100,0,1100,stat2.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm2  on  status2.resultcode=alarm2.resultcode
left outer join  tbl_rpc_statistics_conf stat3 on t.wattdegreebalance between stat3.s_min and stat3.s_max and stat3.s_type='GLPHD'
left outer join  tbl_rpc_worktype status3  on  status3.resultcode=decode(t.wattdegreebalance ,null,1100,0,1100,stat3.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm3  on  alarm3.resultcode=status3.resultcode
where well.liftingtype between 200 and 299;
/

/*==============================================================*/
/* View: viw_rpc_diagram_hist                                   */
/*==============================================================*/
create or replace view viw_rpc_diagram_hist as
select
 t.id,well.wellname, well.id as wellid ,well.liftingtype,well.signinid,t.AcqTime,
 comm.commstatus,decode(comm.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_8.alarmsign,0,0,t031_8.alarmlevel) as commAlarmLevel,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,status1.resultname,status1.optimizationsuggestion,decode(alarm1.alarmsign,0,0,alarm1.alarmlevel) as resultrunAlarmLevel,
 t.theoreticalproduction,
 t.liquidweightproduction,
 t.oilweightproduction,
 t.waterweightproduction,
 prod.weightwatercut, stat4.s_level as liquidweightproductionlevel,
 t.liquidvolumetricproduction,
 t.oilvolumetricproduction,
 t.watervolumetricproduction,
 prod.volumewatercut, stat8.s_level as liquidvolumeproductionlevel,
 total.liquidweightproduction as liquidweightproduction_l,total.oilweightproduction as oilweightproduction_l, total.waterweightproduction as waterweightproduction_l,
 total.liquidvolumetricproduction as liquidvolumetricproduction_l,total.oilvolumetricproduction as oilvolumetricproduction_l, total.watervolumetricproduction as watervolumetricproduction_l,
 prod.productiongasoilratio,prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature,
 case when prod.producingfluidLevel>=0 then prod.producingfluidLevel
   else t.inverproducingfluidlevel end as producingfluidLevel,
 t.levelcorrectvalue,
 prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,prod.pumpborediameter,
 prod.crudeoildensity,prod.netgrossratio,
 t.availableplungerstrokeprod_w,
 t.pumpclearanceleakprod_w,
 t.tvleakweightproduction,
 t.svleakweightproduction,
 t.gasinfluenceprod_w,
 t.availableplungerstrokeprod_v,
 t.pumpclearanceleakprod_v,
 t.tvleakvolumetricproduction,
 t.svleakvolumetricproduction,
 t.gasinfluenceprod_v,
 t.rodstring,
 t.stroke,t.spm,
 t.upperloadline,t.upperLoadLineOfExact,t.lowerloadline,t.upperloadline-t.lowerloadline as deltaLoadLine,
 t.fmax,t.fmin,t.fmax-t.fmin as deltaF,
 t.fullnesscoEfficient,t.noliquidfullnesscoefficient,
 t.plungerstroke,t.availableplungerstroke,t.noliquidavailableplungerstroke,
 t.averagewatt,t.polishrodpower,t.waterpower,
 t.systemefficiency*100 as systemefficiency,stat5.s_level as systemefficiencyLevel,
 t.surfacesystemefficiency*100 as surfacesystemefficiency,stat6.s_level as surfacesystemefficiencyLevel,
 t.welldownsystemefficiency*100 as welldownsystemefficiency,stat7.s_level as welldownsystemefficiencyLevel,
 t.area,t.energyper100mlift,
 t.idegreebalance,
 status2.resultname as idegreebalanceName,
 t.upstrokeimax,t.downstrokeimax,
 case when t.downstrokeimax is null or t.upstrokeimax is null then null else to_char(t.downstrokeimax,'fm999990.00') ||'/'||to_char(t.upstrokeimax,'fm999990.00') end as iRatio,
 decode(alarm2.alarmsign,0,0,alarm2.alarmlevel) as idegreebalanceAlarmLevel,
 t.wattdegreebalance,
 status3.resultname as wattdegreebalanceName,
 t.upstrokewattmax,t.downstrokewattmax,
 case when t.downstrokewattmax is null or t.upstrokewattmax is null then null else to_char(t.downstrokewattmax,'fm999990.00') ||'/'||to_char(t.upstrokewattmax,'fm999990.00') end as wattRatio,
 decode(alarm3.alarmsign,0,0,alarm3.alarmlevel) as wattdegreebalanceAlarmlevel,
 t.deltaradius*100 as deltaradius,
 t.pumpEff1*100 as pumpEff1,t.pumpEff2*100 as pumpEff2,t.pumpEff3*100 as pumpEff3,t.pumpEff4*100 as pumpEff4,t.pumpEff*100 as pumpEff,
 t.rodflexlength,t.tubingflexlength,t.inertialength,
 t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpIntakevisl,t.pumpIntakebo,
 t.pumpoutletp,t.pumpoutlett,t.pumpoutletgol,t.pumpoutletvisl,t.pumpoutletbo,
 well.videourl,org.org_id,org.org_code, well.sortnum
from
tbl_wellinformation well
left outer join  tbl_org org  on org.org_id=well.orgid
left outer join  tbl_rpc_diagram_hist t  on t.wellid=well.id
left outer join  viw_commstatus comm on comm.id=well.id
left outer join  tbl_rpc_productiondata_hist prod on prod.id=t.productiondataid
left outer join  tbl_rpc_diagram_total total on t.wellid=total.wellid and t.acqtime=total.acqtime
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm1  on  alarm1.resultcode=status1.resultcode
left outer join  tbl_rpc_worktype status8  on  status8.resultcode=decode(comm.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_8  on  status8.resultcode=t031_8.resultcode
left outer join  tbl_rpc_statistics_conf stat2 on t.idegreebalance between stat2.s_min and stat2.s_max and stat2.s_type='PHD'
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=decode(t.idegreebalance ,null,1100,0,1100,stat2.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm2  on  status2.resultcode=alarm2.resultcode
left outer join  tbl_rpc_statistics_conf stat3 on t.wattdegreebalance between stat3.s_min and stat3.s_max and stat3.s_type='GLPHD'
left outer join  tbl_rpc_worktype status3  on  status3.resultcode=decode(t.wattdegreebalance ,null,1100,0,1100,stat3.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm3  on  alarm3.resultcode=status3.resultcode
left outer join  tbl_rpc_statistics_conf stat4 on t.liquidweightproduction between stat4.s_min and stat4.s_max and stat4.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat8 on t.liquidvolumetricproduction between stat8.s_min and stat8.s_max and stat8.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat5 on t.systemefficiency*100 between stat5.s_min and stat5.s_max and stat5.s_type='XTXL'
left outer join  tbl_rpc_statistics_conf stat6 on t.surfacesystemefficiency*100 between stat6.s_min and stat6.s_max and stat6.s_type='DMXL'
left outer join  tbl_rpc_statistics_conf stat7 on t.welldownsystemefficiency*100 between stat7.s_min and stat7.s_max and stat7.s_type='JXXL'
where well.liftingtype>=200 and well.liftingtype<300;
/

/*==============================================================*/
/* View: viw_rpc_diagram_latest                                 */
/*==============================================================*/
create or replace view viw_rpc_diagram_latest as
select
 t.id,well.wellname, well.id as wellid ,well.liftingtype,well.signinid,t.AcqTime,
 comm.commstatus,decode(comm.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_8.alarmsign,0,0,t031_8.alarmlevel) as commAlarmLevel,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,status1.resultname,status1.optimizationsuggestion,decode(alarm1.alarmsign,0,0,alarm1.alarmlevel) as resultrunAlarmLevel,
 t.theoreticalproduction,
 t.liquidweightproduction,
 t.oilweightproduction,
 t.waterweightproduction,
 prod.weightwatercut, stat4.s_level as liquidweightproductionlevel,
 t.liquidvolumetricproduction,
 t.oilvolumetricproduction,
 t.watervolumetricproduction,
 prod.volumewatercut, stat8.s_level as liquidvolumeproductionlevel,
 total.liquidweightproduction as liquidweightproduction_l,total.oilweightproduction as oilweightproduction_l, total.waterweightproduction as waterweightproduction_l,
 total.liquidvolumetricproduction as liquidvolumetricproduction_l,total.oilvolumetricproduction as oilvolumetricproduction_l, total.watervolumetricproduction as watervolumetricproduction_l,
 prod.productiongasoilratio,prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature,
 case when prod.producingfluidLevel>=0 then prod.producingfluidLevel
   else t.inverproducingfluidlevel end as producingfluidLevel,
 t.levelcorrectvalue,
 prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,prod.pumpborediameter,
 prod.crudeoildensity,prod.netgrossratio,
 t.availableplungerstrokeprod_w,
 t.pumpclearanceleakprod_w,
 t.tvleakweightproduction,
 t.svleakweightproduction,
 t.gasinfluenceprod_w,
 t.availableplungerstrokeprod_v,
 t.pumpclearanceleakprod_v,
 t.tvleakvolumetricproduction,
 t.svleakvolumetricproduction,
 t.gasinfluenceprod_v,
 t.rodstring,
 t.stroke,t.spm,
 t.upperloadline,t.upperLoadLineOfExact,t.lowerloadline,t.upperloadline-t.lowerloadline as deltaLoadLine,
 t.fmax,t.fmin,t.fmax-t.fmin as deltaF,
 t.fullnesscoEfficient,t.noliquidfullnesscoefficient,
 t.plungerstroke,t.availableplungerstroke,t.noliquidavailableplungerstroke,
 t.averagewatt,t.polishrodpower,t.waterpower,
 t.systemefficiency*100 as systemefficiency,stat5.s_level as systemefficiencyLevel,
 t.surfacesystemefficiency*100 as surfacesystemefficiency,stat6.s_level as surfacesystemefficiencyLevel,
 t.welldownsystemefficiency*100 as welldownsystemefficiency,stat7.s_level as welldownsystemefficiencyLevel,
 t.area,t.energyper100mlift,
 t.idegreebalance,
 status2.resultname as idegreebalanceName,
 t.upstrokeimax,t.downstrokeimax,
 case when t.downstrokeimax is null or t.upstrokeimax is null then null else to_char(t.downstrokeimax,'fm999990.00') ||'/'||to_char(t.upstrokeimax,'fm999990.00') end as iRatio,
 decode(alarm2.alarmsign,0,0,alarm2.alarmlevel) as idegreebalanceAlarmLevel,
 t.wattdegreebalance,
 status3.resultname as wattdegreebalanceName,
 t.upstrokewattmax,t.downstrokewattmax,
 case when t.downstrokewattmax is null or t.upstrokewattmax is null then null else to_char(t.downstrokewattmax,'fm999990.00') ||'/'||to_char(t.upstrokewattmax,'fm999990.00') end as wattRatio,
 decode(alarm3.alarmsign,0,0,alarm3.alarmlevel) as wattdegreebalanceAlarmlevel,
 t.deltaradius*100 as deltaradius,
 t.pumpEff1*100 as pumpEff1,t.pumpEff2*100 as pumpEff2,t.pumpEff3*100 as pumpEff3,t.pumpEff4*100 as pumpEff4,t.pumpEff*100 as pumpEff,
 t.rodflexlength,t.tubingflexlength,t.inertialength,
 t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpIntakevisl,t.pumpIntakebo,
 t.pumpoutletp,t.pumpoutlett,t.pumpoutletgol,t.pumpoutletvisl,t.pumpoutletbo,
 well.videourl,org.org_id,org.org_code, well.sortnum
from
tbl_wellinformation well
left outer join  tbl_org org  on org.org_id=well.orgid
left outer join  tbl_rpc_diagram_latest t  on t.wellid=well.id
left outer join  viw_commstatus comm on comm.id=well.id
left outer join  tbl_rpc_productiondata_hist prod on prod.id=t.productiondataid
left outer join  tbl_rpc_diagram_total total on t.wellid=total.wellid and t.acqtime=total.acqtime
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm1  on  alarm1.resultcode=status1.resultcode
left outer join  tbl_rpc_worktype status8  on  status8.resultcode=decode(comm.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_8  on  status8.resultcode=t031_8.resultcode
left outer join  tbl_rpc_statistics_conf stat2 on t.idegreebalance between stat2.s_min and stat2.s_max and stat2.s_type='PHD'
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=decode(t.idegreebalance ,null,1100,0,1100,stat2.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm2  on  status2.resultcode=alarm2.resultcode
left outer join  tbl_rpc_statistics_conf stat3 on t.wattdegreebalance between stat3.s_min and stat3.s_max and stat3.s_type='GLPHD'
left outer join  tbl_rpc_worktype status3  on  status3.resultcode=decode(t.wattdegreebalance ,null,1100,0,1100,stat3.s_code)
left outer join  tbl_rpc_alarmtype_conf alarm3  on  alarm3.resultcode=status3.resultcode
left outer join  tbl_rpc_statistics_conf stat4 on t.liquidweightproduction between stat4.s_min and stat4.s_max and stat4.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat8 on t.liquidvolumetricproduction between stat8.s_min and stat8.s_max and stat8.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat5 on t.systemefficiency*100 between stat5.s_min and stat5.s_max and stat5.s_type='XTXL'
left outer join  tbl_rpc_statistics_conf stat6 on t.surfacesystemefficiency*100 between stat6.s_min and stat6.s_max and stat6.s_type='DMXL'
left outer join  tbl_rpc_statistics_conf stat7 on t.welldownsystemefficiency*100 between stat7.s_min and stat7.s_max and stat7.s_type='JXXL'
where well.liftingtype>=200 and well.liftingtype<300;
/

/*==============================================================*/
/* View: viw_rpc_discrete_hist                                  */
/*==============================================================*/
create or replace view viw_rpc_discrete_hist as
select
 t.id,well.wellname,well.liftingtype,code1.itemname as liftingTypeName, t.wellid ,well.signinid,
 t.commstatus,
 decode(t.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_2.alarmsign,0,0,t031_2.alarmlevel) as commAlarmLevel,
 case when prod.runtimeefficiencysource=0 and prod.runtime=0 then 0
      when prod.runtimeefficiencysource=0 and prod.runtime>0 then 1
      else t.runstatus end as runStatus,
 case when t.commstatus=1 then
           case when prod.runtimeefficiencysource=0 and prod.runtime=0 then '停抽'
                when prod.runtimeefficiencysource=0 and prod.runtime>0 then '运行'
                else decode(t.runstatus,1,'运行','停抽') end
      else '离线' end as runStatusName,
 decode(t031_3.alarmsign,0,0,t031_3.alarmlevel) as runAlarmLevel,
 t.commtime,t.commrange,t.commtimeefficiency as commtimeefficiency ,stat3.s_level as commtimeefficiencyLevel,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,stat2.s_level as runtimeefficiencyLevel,
 t.AcqTime,
 t.acqcycle_diagram,t.acqcycle_discrete,
 decode(t.resultcode,null,1100,t.resultcode) as resultcode,
 t.resultstring as resultstring,
 status1.resultname,
 status1.optimizationsuggestion as optimizationsuggestion,
 case when t.resultcode=0 then 0
      else decode(t031_1.alarmsign,0,0,t031_1.alarmlevel) end as resultAlarmLevel,
 t.todayKWattH,stat1.s_level as todayKWattHLevel,t.todaypKWattH,t.todaynKWattH,
 t.todayKVarH,t.todaypKVarH,t.todaynKVarH,t.todayKVAH,
 t.ia,t.ib,t.ic,t.iavg,
 to_char(t.ia,'fm999990.00') ||'/'||to_char(t.ib,'fm999990.00')||'/'||to_char(t.ic,'fm999990.00') as istr,
 t.iauplimit,t.iadownlimit,t.iazero,
 t.ibuplimit,t.ibdownlimit,t.ibzero,
 t.icuplimit,t.icdownlimit,t.iczero,
 t.wattuplimit,t.wattdownlimit,
 t.iamax,t.iamin,t.ibmax,t.ibmin,t.icmax,t.icmin,
 t.va,t.vb,t.vc,t.vavg,
 to_char(t.va,'fm999990.00') ||'/'||to_char(t.vb,'fm999990.00')||'/'||to_char(t.vc,'fm999990.00') as vstr,
 t.vauplimit,t.vadownlimit,t.vazero,
 t.vbuplimit,t.vbdownlimit,t.vbzero,
 t.vcuplimit,t.vcdownlimit,t.vczero,
 t.totalKWattH,t.totalpKWattH,t.totalnKWattH,
 t.totalKVarH,t.totalpKVarH,t.totalnKVarH,
 t.totalKVAH,
 t.watta,t.wattb,t.wattc,t.watt3,
 to_char(t.watta,'fm999990.00') ||'/'||to_char(t.wattb,'fm999990.00')||'/'||to_char(t.wattc,'fm999990.00') as wattstr,
 t.vara,t.varb,t.varc,t.var3,
 to_char(t.vara,'fm999990.00') ||'/'||to_char(t.varb,'fm999990.00')||'/'||to_char(t.varc,'fm999990.00') as varstr,
 t.reversepower,
 t.vaa,t.vab,t.vac,t.va3,
 to_char(t.vaa,'fm999990.00') ||'/'||to_char(t.vab,'fm999990.00')||'/'||to_char(t.vac,'fm999990.00') as vastr,
 t.pfa,t.pfb,t.pfc,t.pf3,
 to_char(t.pfa,'fm999990.00') ||'/'||to_char(t.pfb,'fm999990.00')||'/'||to_char(t.pfc,'fm999990.00') as pfstr,
 t.setfrequency,t.runfrequency,
 t.tubingpressure,t.casingpressure,t.backpressure,t.wellheadfluidtemperature,
 t.signal,t.interval,t.devicever,
 well.videourl, well.sortnum,org.org_code,org.org_id
from
tbl_wellinformation well
left outer join  tbl_org org  on well.orgid=org.org_id
left outer join  tbl_code code1  on  code1.itemvalue=well.liftingtype and code1.itemcode='LiftingType'
left outer join  tbl_rpc_discrete_hist t  on t.wellid=well.id
left outer join  tbl_pcp_productiondata_latest prod  on prod.wellid=well.id
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf t031_1  on  status1.resultcode=t031_1.resultcode
left outer join  tbl_rpc_worktype status2  on  status2.resultcode= decode(t.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_2  on  status2.resultcode=t031_2.resultcode
left outer join  tbl_rpc_worktype status3  on  status3.resultcode= decode(t.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_3  on  status3.resultcode=t031_3.resultcode
left outer join  tbl_rpc_statistics_conf stat1 on t.todayKWattH between stat1.s_min and stat1.s_max and stat1.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat2 on t.runtimeefficiency between stat2.s_min and stat2.s_max and stat2.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat3 on t.commtimeefficiency between stat3.s_min and stat3.s_max and stat3.s_type='TXSL'
where well.liftingtype>=200 and well.liftingtype<300;

/*==============================================================*/
/* View: viw_rpc_discrete_latest                                */
/*==============================================================*/
create or replace view viw_rpc_discrete_latest as
select
 t.id,well.wellname,well.liftingtype,code1.itemname as liftingTypeName, t.wellid ,well.signinid,
 t.commstatus,
 decode(t.commstatus,1,'在线','离线') as commStatusName,
 decode(t031_2.alarmsign,0,0,t031_2.alarmlevel) as commAlarmLevel,
 case when prod.runtimeefficiencysource=0 and prod.runtime=0 then 0
      when prod.runtimeefficiencysource=0 and prod.runtime>0 then 1
      else t.runstatus end as runStatus,
 case when t.commstatus=1 then
           case when prod.runtimeefficiencysource=0 and prod.runtime=0 then '停抽'
                when prod.runtimeefficiencysource=0 and prod.runtime>0 then '运行'
                else decode(t.runstatus,1,'运行','停抽') end
      else '离线' end as runStatusName,
 decode(t031_3.alarmsign,0,0,t031_3.alarmlevel) as runAlarmLevel,
 t.commtime,t.commrange,t.commtimeefficiency as commtimeefficiency ,stat3.s_level as commtimeefficiencyLevel,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,stat2.s_level as runtimeefficiencyLevel,
 t.AcqTime,
 t.acqcycle_diagram,t.acqcycle_discrete,
 decode(t.resultcode,null,1100,t.resultcode) as resultcode,
 t.resultstring as resultstring,
 status1.resultname,
 status1.optimizationsuggestion as optimizationsuggestion,
 case when t.resultcode=0 then 0
      else decode(t031_1.alarmsign,0,0,t031_1.alarmlevel) end as resultAlarmLevel,
 t.todayKWattH,stat1.s_level as todayKWattHLevel,t.todaypKWattH,t.todaynKWattH,
 t.todayKVarH,t.todaypKVarH,t.todaynKVarH,t.todayKVAH,
 t.ia,t.ib,t.ic,t.iavg,
 to_char(t.ia,'fm999990.00') ||'/'||to_char(t.ib,'fm999990.00')||'/'||to_char(t.ic,'fm999990.00') as istr,
 t.iauplimit,t.iadownlimit,t.iazero,
 t.ibuplimit,t.ibdownlimit,t.ibzero,
 t.icuplimit,t.icdownlimit,t.iczero,
 t.wattuplimit,t.wattdownlimit,
 t.iamax,t.iamin,t.ibmax,t.ibmin,t.icmax,t.icmin,
 t.va,t.vb,t.vc,t.vavg,
 to_char(t.va,'fm999990.00') ||'/'||to_char(t.vb,'fm999990.00')||'/'||to_char(t.vc,'fm999990.00') as vstr,
 t.vauplimit,t.vadownlimit,t.vazero,
 t.vbuplimit,t.vbdownlimit,t.vbzero,
 t.vcuplimit,t.vcdownlimit,t.vczero,
 t.totalKWattH,t.totalpKWattH,t.totalnKWattH,
 t.totalKVarH,t.totalpKVarH,t.totalnKVarH,
 t.totalKVAH,
 t.watta,t.wattb,t.wattc,t.watt3,
 to_char(t.watta,'fm999990.00') ||'/'||to_char(t.wattb,'fm999990.00')||'/'||to_char(t.wattc,'fm999990.00') as wattstr,
 t.vara,t.varb,t.varc,t.var3,
 to_char(t.vara,'fm999990.00') ||'/'||to_char(t.varb,'fm999990.00')||'/'||to_char(t.varc,'fm999990.00') as varstr,
 t.reversepower,
 t.vaa,t.vab,t.vac,t.va3,
 to_char(t.vaa,'fm999990.00') ||'/'||to_char(t.vab,'fm999990.00')||'/'||to_char(t.vac,'fm999990.00') as vastr,
 t.pfa,t.pfb,t.pfc,t.pf3,
 to_char(t.pfa,'fm999990.00') ||'/'||to_char(t.pfb,'fm999990.00')||'/'||to_char(t.pfc,'fm999990.00') as pfstr,
 t.setfrequency,t.runfrequency,
 t.tubingpressure,t.casingpressure,t.backpressure,t.wellheadfluidtemperature,
 t.signal,t.interval,t.devicever,
 well.videourl, well.sortnum,org.org_code,org.org_id
from
tbl_wellinformation well
left outer join  tbl_org org  on well.orgid=org.org_id
left outer join  tbl_code code1  on  code1.itemvalue=well.liftingtype and code1.itemcode='LiftingType'
left outer join  tbl_rpc_discrete_latest t  on t.wellid=well.id
left outer join  tbl_pcp_productiondata_latest prod  on prod.wellid=well.id
left outer join  viw_commstatus comm on comm.id=well.id
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf t031_1  on  status1.resultcode=t031_1.resultcode
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=decode(comm.commstatus,1,1102,1101)
left outer join  tbl_rpc_alarmtype_conf t031_2  on  status2.resultcode=t031_2.resultcode
left outer join  tbl_rpc_worktype status3  on  status3.resultcode= decode(t.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_3  on  status3.resultcode=t031_3.resultcode
left outer join  tbl_rpc_statistics_conf stat1 on t.todayKWattH between stat1.s_min and stat1.s_max and stat1.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat2 on t.runtimeefficiency between stat2.s_min and stat2.s_max and stat2.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat3 on t.commtimeefficiency between stat3.s_min and stat3.s_max and stat3.s_type='TXSL'
where well.liftingtype>=200 and well.liftingtype<300;
/

/*==============================================================*/
/* View: viw_rpc_productiondata_hist                            */
/*==============================================================*/
create or replace view viw_rpc_productiondata_hist as
select op.id,w.wellname,w.id as wellId,w.liftingtype,
       op.AcqTime,
       op.runtime,
       op.crudeOilDensity,op.waterDensity,op.naturalGasRelativeDensity,op.saturationPressure,op.reservoirDepth,op.reservoirTemperature,
       op.weightwatercut,op.volumewatercut,
       op.TubingPressure,op.CasingPressure, op.BackPressure,op.WellHeadFluidTemperature,op.ProducingfluidLevel,op.PumpSettingDepth,op.ProductionGasOilRatio,
       op.PumpBoreDiameter,op.PumpType,c3.itemname as PumpTypeName, op.PumpGrade,op.PlungerLength,op.BarrelType,c2.itemname as BarrelTypeName,
       op.BarrelLength,op.BarrelSeries,op.RotorDiameter,op.QPR,
       op.TubingStringInsideDiameter,op.CasingStringInsideDiameter,
       op.rodstring,
       op.AnchoringState,c1.itemname as AnchoringStateName,
       op.NetGrossRatio,op.manualintervention,
       op.runtimeefficiencysource,c5.itemname as RuntimeEfficiencySourceName,
       w.sortnum,o.org_id
from
       tbl_code c1 ,
       tbl_code c2 ,
       tbl_code c3 ,
       tbl_code c4,
       tbl_code c5,
       tbl_rpc_productiondata_hist op
left outer join  tbl_wellinformation  w  on w.id = op.wellid
left outer join  tbl_org o    on  o.org_id=w.orgid
where c1.itemcode='AnchoringState' and c1.itemvalue=op.AnchoringState
      and c2.itemcode='BarrelType' and c2.itemvalue=op.BarrelType
      and c3.itemcode='PumpType' and c3.itemvalue=op.PumpType
      and c4.itemcode='PumpGrade' and c4.itemvalue=op.PumpGrade
      and c5.itemcode='RuntimeEfficiencySource' and c5.itemvalue=op.runtimeefficiencysource
      and w.liftingtype>=200 and w.liftingtype<300;
/

/*==============================================================*/
/* View: viw_rpc_productiondata_latest                          */
/*==============================================================*/
create or replace view viw_rpc_productiondata_latest as
select op.id,w.wellname,w.id as wellId,w.liftingtype,
       op.AcqTime,
       op.runtime,
       op.crudeOilDensity,op.waterDensity,op.naturalGasRelativeDensity,op.saturationPressure,op.reservoirDepth,op.reservoirTemperature,
       op.weightwatercut,op.volumewatercut,
       op.TubingPressure,op.CasingPressure, op.BackPressure,op.WellHeadFluidTemperature,op.ProducingfluidLevel,op.PumpSettingDepth,op.ProductionGasOilRatio,
       op.PumpBoreDiameter,op.PumpType,c3.itemname as PumpTypeName, op.PumpGrade,op.PlungerLength,op.BarrelType,c2.itemname as BarrelTypeName,
       op.BarrelLength,op.BarrelSeries,op.RotorDiameter,op.QPR,
       op.TubingStringInsideDiameter,op.CasingStringInsideDiameter,
       op.rodstring,
       op.AnchoringState,c1.itemname as AnchoringStateName,
       op.NetGrossRatio,op.manualintervention,
       op.runtimeefficiencysource,c5.itemname as RuntimeEfficiencySourceName,
       w.sortnum,o.org_id
from
       tbl_code c1 ,
       tbl_code c2 ,
       tbl_code c3 ,
       tbl_code c4,
       tbl_code c5,
       tbl_rpc_productiondata_latest op
left outer join  tbl_wellinformation  w  on w.id = op.wellid
left outer join  tbl_org o    on  o.org_id=w.orgid
where c1.itemcode='AnchoringState' and c1.itemvalue=op.AnchoringState
      and c2.itemcode='BarrelType' and c2.itemvalue=op.BarrelType
      and c3.itemcode='PumpType' and c3.itemvalue=op.PumpType
      and c4.itemcode='PumpGrade' and c4.itemvalue=op.PumpGrade
      and c5.itemcode='RuntimeEfficiencySource' and c5.itemvalue=op.runtimeefficiencysource
      and w.liftingtype>=200 and w.liftingtype<300;
/

/*==============================================================*/
/* View: viw_rpc_total_day                                      */
/*==============================================================*/
create or replace view viw_rpc_total_day as
select
 t.id,well.wellname,well.liftingtype,code1.itemname as liftingTypeName,well.id as wellid ,well.signinid,
 t.calculateDate,t.calculateDate-t.extendeddays as acquisitionDate,
 t.commstatus,decode(t.commstatus,1,'在线','离线') as commStatusName,decode(t031_2.alarmsign,0,0,t031_2.alarmlevel) as commAlarmLevel,
 runStatus,
 case when t.commstatus=1 then
           decode(t.runstatus,1,'运行','停抽')
      else '离线' end as runStatusName,
 decode(t031_3.alarmsign,0,0,t031_3.alarmlevel) as runAlarmLevel,
 t.commtime,t.commrange,t.commtimeefficiency as commtimeefficiency ,stat3.s_level as commtimeefficiencyLevel,
 t.runtime,t.runrange,t.runtimeefficiency as runtimeefficiency,stat2.s_level as runtimeefficiencyLevel,
 decode(t.resultcode,null,1100,0,1100,t.resultcode) as resultcode,
 status.resultname as resultname,t.resultString,status.optimizationsuggestion,
 decode(alarm.alarmsign,0,0,alarm.alarmlevel) as resultAlarmLevel,
 decode(t.resultcode_e,null,1100,0,1100,t.resultcode_e) as resultcode_e,
 status1.resultname as resultname_e,
 t.resultstring_e,
 case when t.resultcode_e=0 then 0
      else decode(t031_1.alarmsign,0,0,t031_1.alarmlevel) end as resultAlarmLevel_E,
 t.liquidweightproduction,
 stat7.s_level as liquidWeightProductionlevel,
 t.oilweightproduction,
 t.waterweightproduction,
 t.weightwatercut,
 t.liquidVolumetricproduction,
 stat11.s_level as liquidVolumeProductionlevel,
 t.oilvolumetricproduction,
 t.watervolumetricproduction,
 t.volumewatercut,
 t.ExtendedDays,
 t.productiongasoilratio,t.tubingpressure,t.casingpressure,t.wellheadfluidtemperature,
 t.stroke,t.strokemax,t.strokemin,t.strokemax||'/'||t.strokemin||'/'||t.stroke as strokestr,
 t.spm,t.spmmax,t.spmmin,t.spmmax||'/'||t.spmmin||'/'||t.spm as spmstr,
 t.f,t.fmax,t.fmin,t.fmax||'/'||t.fmin||'/'||t.f as fstr,
 t.plungerstroke,t.availableplungerstroke,t.noliquidavailablestroke,
 t.fullnesscoefficient,t.noliquidfullnesscoefficient,t.pumpeff*100 as pumpeff,
 t.pumpborediameter,t.pumpsettingdepth,t.producingfluidlevel,t.levelcorrectvalue,t.submergence,
 t.rpm,t.rpmmax,t.rpmmin,
 t.systemefficiency*100 as systemEfficiency,stat8.s_level as systemEfficiencyLevel,
 t.surfacesystemefficiency*100 as surfaceSystemEfficiency,stat9.s_level as surfaceSystemEfficiencyLevel,
 t.welldownsystemefficiency*100 as welldownSystemEfficiency,stat10.s_level as wellDownSystemEfficiencyLevel,
 t.energyper100mlift,
 t.todayKWattH,stat1.s_level as todayKWattHLevel,
 t.todaypKWattH,t.todaynKWattH,t.todayKVarH,t.todaypKVarH,t.todaynKVarH,t.todayKVAH,
 t.iDegreeBalance,t.idegreebalancemax,t.idegreebalancemin,
 t.idegreebalancemax||'/'||t.idegreebalancemin||'/'||t.idegreebalance as idegreebalancestr,
 status4.resultname as iDegreeBalanceLevel,decode(t031_4.alarmsign,0,0,t031_4.alarmlevel) as idegreebalanceAlarmLevel,
 t.wattDegreeBalance,t.wattdegreebalancemax,t.wattdegreebalancemin,
 t.wattdegreebalancemax||'/'||t.wattdegreebalancemin||'/'||t.wattdegreebalance as wattdegreebalancestr,
 status5.resultname as wattDegreeBalanceLevel,decode(t031_5.alarmsign,0,0,t031_5.alarmlevel) as wattdegreebalanceAlarmLevel,
 t.deltaradius*100 as deltaradius,t.deltaradiusmax*100 as deltaradiusmax,t.deltaradiusmin*100 as deltaradiusmin,
 t.ia,t.iamax,t.iamin,t.iamax||'/'||t.iamin||'/'||t.ia as iastr,
 t.ib,t.ibmax,t.ibmin,t.ibmax||'/'||t.ibmin||'/'||t.ib as ibstr,
 t.ic,t.icmax,t.icmin,t.icmax||'/'||t.icmin||'/'||t.ic as icstr,
 t.va,t.vamax,t.vamin,t.vamax||'/'||t.vamin||'/'||t.va as vastr,
 t.vb,t.vbmax,t.vbmin,t.vbmax||'/'||t.vbmin||'/'||t.vb as vbstr,
 t.vc,t.vcmax,t.vcmin,t.vcmax||'/'||t.vcmin||'/'||t.vc as vcstr,
 t.signal,t.signalmax,t.signalmin,t.signalmax||'/'||t.signalmin||'/'||t.signal as signalstr,
 well.videourl, well.sortnum,org.org_code,org.org_id,null as remark
from
tbl_wellinformation well
left outer join  tbl_org org  on well.orgid=org.org_id
left outer join  tbl_code code1  on  code1.itemvalue=well.liftingtype and code1.itemcode='LiftingType'
left outer join  tbl_rpc_total_day t  on t.wellid=well.id
left outer join  tbl_rpc_worktype status  on  status.resultcode=decode(t.resultcode,null,1100,0,1100,t.resultcode)
left outer join  tbl_rpc_alarmtype_conf alarm  on  alarm.resultcode=status.resultcode
left outer join  tbl_rpc_worktype status1  on  status1.resultcode=decode(t.resultcode_e,null,1100,0,1100,t.resultcode_e)
left outer join  tbl_rpc_alarmtype_conf t031_1  on  status1.resultcode=t031_1.resultcode
left outer join  tbl_rpc_worktype status2  on  status2.resultcode=t.commstatus+1101
left outer join  tbl_rpc_alarmtype_conf t031_2  on  status2.resultcode=t031_2.resultcode
left outer join  tbl_rpc_worktype status3  on  status3.resultcode= decode(t.runstatus,1,1104,1302)
left outer join  tbl_rpc_alarmtype_conf t031_3  on  status3.resultcode=t031_3.resultcode
left outer join  tbl_rpc_statistics_conf stat1 on t.todayKWattH between stat1.s_min and stat1.s_max and stat1.s_type='RYDL'
left outer join  tbl_rpc_statistics_conf stat2 on t.runtimeefficiency between stat2.s_min and stat2.s_max and stat2.s_type='SCSL'
left outer join  tbl_rpc_statistics_conf stat3 on t.commtimeefficiency between stat3.s_min and stat3.s_max and stat3.s_type='TXSL'
left outer join  tbl_rpc_statistics_conf stat4 on t.idegreebalance between stat4.s_min and stat4.s_max and stat4.s_type='PHD'
left outer join  tbl_rpc_worktype status4  on  status4.resultcode=decode(t.idegreebalance ,null,1100,0,1100,stat4.s_code)
left outer join  tbl_rpc_alarmtype_conf t031_4  on  status4.resultcode=t031_4.resultcode
left outer join  tbl_rpc_statistics_conf stat5 on t.wattdegreebalance between stat5.s_min and stat5.s_max and stat5.s_type='GLPHD'
left outer join  tbl_rpc_worktype status5  on  status5.resultcode=decode(t.wattdegreebalance ,null,1100,0,1100,stat5.s_code)
left outer join  tbl_rpc_alarmtype_conf t031_5  on  status5.resultcode=t031_5.resultcode
left outer join  tbl_rpc_statistics_conf stat7 on t.liquidvolumetricproduction between stat7.s_min and stat7.s_max and stat7.s_type='CYL'
left outer join  tbl_rpc_statistics_conf stat11 on t.liquidvolumetricproduction between stat11.s_min and stat11.s_max and stat11.s_type='CYLF'
left outer join  tbl_rpc_statistics_conf stat8 on t.systemefficiency*100 between stat8.s_min and stat8.s_max and stat8.s_type='XTXL'
left outer join  tbl_rpc_statistics_conf stat9 on t.surfacesystemefficiency*100 between stat9.s_min and stat9.s_max and stat9.s_type='DMXL'
left outer join  tbl_rpc_statistics_conf stat10 on t.welldownsystemefficiency*100 between stat10.s_min and stat10.s_max and stat10.s_type='JXXL'
where well.liftingtype>=200 and well.liftingtype<300;
/

/*==============================================================*/
/* View: viw_wellboretrajectory                                 */
/*==============================================================*/
create or replace view viw_wellboretrajectory as
select t.id,org.org_name as orgName,org.org_id as orgid,t.wellname,
t2.measuringdepth,t2.verticaldepth,t2.deviationangle,t2.azimuthangle,
t2.x,t2.y,t2.z,t2.savetime,t2.resultstatus,
t.sortnum
from tbl_wellinformation t
left outer join tbl_wellboretrajectory t2 on t2.wellid=t.id
left outer join  tbl_org org  on t.orgid=org.org_id;
/

/*==============================================================*/
/* View: viw_wellinformation                                    */
/*==============================================================*/
create or replace view viw_wellinformation as
select t.id,org.org_name as orgName,org.org_id as orgid,
t.resname,t.wellname,t.liftingtype,t.signinid,t.slave,
t.videourl,
c1.itemname as LiftingTypeName,t.protocolcode, t2.unit_name as AcquisitionUnit,
t.sortnum
from tbl_wellinformation t
left outer join tbl_code c1 on c1.itemcode='LiftingType' and c1.itemvalue=t.liftingtype
left outer join  tbl_org org  on t.orgid=org.org_id
left outer join tbl_acq_unit_conf t2 on t2.unit_code=t.unitcode;
/