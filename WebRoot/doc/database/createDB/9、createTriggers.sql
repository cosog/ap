CREATE OR REPLACE TRIGGER "BEF_HIBERNATE_SEQUENCE_INSERT"
BEFORE INSERT ON TBL_DIST_ITEM
FOR EACH ROW
BEGIN
  SELECT to_char(HIBERNATE_SEQUENCE.nextval)INTO :new.DATAITEMID FROM dual;
end;
/

create or replace trigger trg_a_a9rawdata_hist_i_u
    before update or insert  on tbl_a9rawdata_hist
    for each row
declare
    recordCount number(8,2);
begin
    if 1=1 then
       select count(id) into recordCount from tbl_a9rawdata_latest t where t.deviceid=:new.deviceid;
       if recordCount=0 then
          insert into tbl_a9rawdata_latest (
              deviceid,acqtime,signal,devicever,
              interval,a,f,watt,i,transferintervel
          )values(
              :new.deviceid,:new.acqtime,:new.signal,:new.devicever,
              :new.interval,:new.a,:new.f,:new.watt,:new.i,:new.transferintervel
          );
       elsif recordCount>0  then
          update tbl_a9rawdata_latest t set
              t.AcqTime=:new.AcqTime,t.signal=:new.signal,t.devicever=:new.devicever,
              t.interval=:new.interval,t.a=:new.a,t.f=:new.f,t.watt=:new.watt,t.i=:new.i,
              t.transferintervel=:new.transferintervel
           where t.deviceid=:new.deviceid;
       end if;
    end if;
end;
/

CREATE OR REPLACE TRIGGER trg_a_a9rawwatercut_hist_i_u
    before update or insert  on tbl_a9rawwatercutdata_hist
    for each row
declare
    recordCount number(8,2);
begin
    if 1=1 then
       select count(id) into recordCount from tbl_a9rawwatercutdata_latest t where t.deviceid=:new.deviceid;
       if recordCount=0 then
          insert into tbl_a9rawwatercutdata_latest (
              deviceid,acqtime,signal,devicever,transferintervel,
              interval,watercut
          )values(
              :new.deviceid,:new.acqtime,:new.signal,:new.devicever,:new.transferintervel,
              :new.interval,:new.watercut
          );
       elsif recordCount>0  then
          update tbl_a9rawwatercutdata_latest t set
              t.AcqTime=:new.AcqTime,t.signal=:new.signal,t.devicever=:new.devicever,
              t.transferintervel=:new.transferintervel,
              t.interval=:new.interval,t.watercut=:new.watercut
           where t.deviceid=:new.deviceid;
       end if;
    end if;
end;
/

CREATE OR REPLACE TRIGGER trg_a_pcp_rpm_hist_i_u
    before update or insert  on TBL_pcp_rpm_HIST
    for each row
declare
    recordCount number(8,2);
    realtime date;
begin
    if :new.resultstatus =1 then
       select count(id) into recordCount from tbl_pcp_rpm_latest t where t.wellid=:new.wellid;
       if recordCount=0 then
          insert into tbl_pcp_rpm_latest select * from tbl_pcp_rpm_hist t2 where t2.wellid=:new.wellid and t2.AcqTime=:new.AcqTime;
       elsif recordCount>0  then
         select decode(t.AcqTime,null,to_date('1980-01-01','yyyy-mm-dd'),t.AcqTime) into realtime from tbl_pcp_rpm_latest t where t.wellid=:new.wellid;
           if realtime<=:new.AcqTime then
             dbms_output.put_line('更新实时数据');
             update tbl_pcp_rpm_latest t set
              t.AcqTime=:new.AcqTime,
              t.rpm=:new.rpm,t.torque=:new.torque,
              t.resultcode=:new.resultcode,
              t.theoreticalproduction=:new.theoreticalproduction,
              t.liquidvolumetricproduction=:new.liquidvolumetricproduction,t.oilvolumetricproduction=:new.oilvolumetricproduction,t.watervolumetricproduction=:new.watervolumetricproduction,
              t.liquidweightproduction=:new.liquidweightproduction,t.oilweightproduction=:new.oilweightproduction,t.waterweightproduction=:new.waterweightproduction,
              t.averagewatt=:new.averagewatt,t.waterpower=:new.waterpower,
              t.systemefficiency=:new.systemefficiency,t.energyper100mlift=:new.energyper100mlift,
              t.pumpeff1=:new.pumpeff1,t.pumpeff2=:new.pumpeff2,t.pumpeff=:new.pumpeff,
              t.pumpintakep=:new.pumpintakep,t.pumpintaket=:new.pumpintaket,t.pumpintakegol=:new.pumpintakegol,t.pumpIntakevisl=:new.pumpIntakevisl,t.pumpIntakebo=:new.pumpIntakebo,
              t.pumpoutletp=:new.pumpoutletp,t.pumpoutlett=:new.pumpoutlett,t.pumpoutletgol=:new.pumpoutletgol,t.pumpoutletvisl=:new.pumpoutletvisl,t.pumpoutletbo=:new.pumpoutletbo,
              t.rodstring=:new.rodstring,t.savetime=:new.savetime,
              t.productiondataid=:new.productiondataid,
              t.resultstatus=:new.resultstatus,
              t.remark=:new.remark
           where t.wellid=:new.wellid;
         end if;
       end if;
    end if;
end;
/

CREATE OR REPLACE TRIGGER trg_a_rpc_diagram_hist_i_u
    before update or insert  on TBL_RPC_DIAGRAM_HIST
    for each row
declare
    recordCount number(8,2);
    realtime date;
    productiondataid number;
begin
    if :new.resultstatus =1 or (:new.DATASOURCE=1 and :new.inverresultstatus=1) then
    if 1=1 then
       select count(id) into recordCount from tbl_rpc_diagram_latest t where t.wellid=:new.wellid;
       if recordCount=0 then
          insert into tbl_rpc_diagram_latest (
              wellid,AcqTime,
              stroke,spm,fmax,fmin,smaxindex,sminindex,
              position_curve,angle_curve,load_curve,power_curve,current_curve,rpm_curve,
              rawpower_curve,rawcurrent_curve,rawrpm_curve,
              position360_curve,angle360_curve,load360_curve,
              ia_curve,ib_curve,ic_curve,
              upstrokeimax,downstrokeimax,upstrokewattmax,downstrokewattmax,idegreebalance,wattdegreebalance,deltaradius,
              datasource,
              resultcode,
              fullnesscoefficient,plungerstroke,availableplungerstroke,
              levelcorrectvalue,inverproducingfluidlevel,noliquidfullnesscoefficient,noliquidavailableplungerstroke,
              upperloadline,upperloadlineofexact,lowerloadline,
              pumpfsdiagram,theoreticalproduction,
              liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,
              availableplungerstrokeprod_v,pumpclearanceleakprod_v,
              tvleakvolumetricproduction,svleakvolumetricproduction,gasinfluenceprod_v,
              liquidweightproduction,oilweightproduction,waterweightproduction,
              availableplungerstrokeprod_w,pumpclearanceleakprod_w,
              tvleakweightproduction,svleakweightproduction,gasinfluenceprod_w,
              averagewatt,polishrodpower,waterpower,
              surfacesystemefficiency,welldownsystemefficiency,systemefficiency,
              energyper100mlift,area,
              rodflexlength,tubingflexlength,inertialength,
              pumpeff1,pumpeff2,pumpeff3,pumpeff4,pumpeff,
              pumpintakep,pumpintaket,pumpintakegol,pumpIntakevisl,pumpIntakebo,
              pumpoutletp,pumpoutlett,pumpoutletgol,pumpoutletvisl,pumpoutletbo,
              rodstring,savetime,
              productiondataid,discretedataid,
              resultstatus,
              inverresultstatus,
              signal,interval,devicever,
              crankangle,polishrodv,polishroda,pr,tf,
              loadtorque,cranktorque,currentbalancetorque,currentnettorque,expectedbalancetorque,expectednettorque,
              wellboreslice,
              remark
          )values(
              :new.wellid,:new.AcqTime,
              :new.stroke,:new.spm,:new.fmax,:new.fmin,:new.smaxindex,:new.sminindex,
              :new.position_curve,:new.angle_curve,:new.load_curve,:new.power_curve,:new.current_curve,:new.rpm_curve,
              :new.rawpower_curve,:new.rawcurrent_curve,:new.rawrpm_curve,
              :new.position360_curve,:new.angle360_curve,:new.load360_curve,
              :new.ia_curve,:new.ib_curve,:new.ic_curve,
              :new.upstrokeimax,:new.downstrokeimax,:new.upstrokewattmax,:new.downstrokewattmax,:new.idegreebalance,:new.wattdegreebalance,:new.deltaradius,
              :new.datasource,
              :new.resultcode,
              :new.fullnesscoefficient,:new.plungerstroke,:new.availableplungerstroke,
              :new.levelcorrectvalue,:new.inverproducingfluidlevel,:new.noliquidfullnesscoefficient,:new.noliquidavailableplungerstroke,
              :new.upperloadline,:new.upperloadlineofexact,:new.lowerloadline,
              :new.pumpfsdiagram,:new.theoreticalproduction,
              :new.liquidvolumetricproduction,:new.oilvolumetricproduction,:new.watervolumetricproduction,
              :new.availableplungerstrokeprod_v,:new.pumpclearanceleakprod_v,
              :new.tvleakvolumetricproduction,:new.svleakvolumetricproduction,:new.gasinfluenceprod_v,
              :new.liquidweightproduction,:new.oilweightproduction,:new.waterweightproduction,
              :new.availableplungerstrokeprod_w,:new.pumpclearanceleakprod_w,
              :new.tvleakweightproduction,:new.svleakweightproduction,:new.gasinfluenceprod_w,
              :new.averagewatt,:new.polishrodpower,:new.waterpower,
              :new.surfacesystemefficiency,:new.welldownsystemefficiency,:new.systemefficiency,
              :new.energyper100mlift,:new.area,
              :new.rodflexlength,:new.tubingflexlength,:new.inertialength,
              :new.pumpeff1,:new.pumpeff2,:new.pumpeff3,:new.pumpeff4,:new.pumpeff,
              :new.pumpintakep,:new.pumpintaket,:new.pumpintakegol,:new.pumpIntakevisl,:new.pumpIntakebo,
              :new.pumpoutletp,:new.pumpoutlett,:new.pumpoutletgol,:new.pumpoutletvisl,:new.pumpoutletbo,
              :new.rodstring,:new.savetime,
              :new.productiondataid,:new.discretedataid,
              :new.resultstatus,
              :new.inverresultstatus,
              :new.signal,:new.interval,:new.devicever,
              :new.crankangle,:new.polishrodv,:new.polishroda,:new.pr,:new.tf,
              :new.loadtorque,:new.cranktorque,:new.currentbalancetorque,:new.currentnettorque,:new.expectedbalancetorque,:new.expectednettorque,
              :new.wellboreslice,
              :new.remark
          );
       elsif recordCount>0  then
         select decode(t.AcqTime,null,to_date('1980-01-01','yyyy-mm-dd'),t.AcqTime) into realtime from tbl_rpc_diagram_latest t where t.wellid=:new.wellid;
           if realtime<=:new.AcqTime then
             dbms_output.put_line('更新实时数据');
             update tbl_rpc_diagram_latest t set
              t.AcqTime=:new.AcqTime,
              t.stroke=:new.stroke,t.spm=:new.spm,t.fmax=:new.fmax,t.fmin=:new.fmin,
              t.smaxindex=:new.smaxindex,t.sminindex=:new.sminindex,
              t.position_curve=:new.position_curve,t.angle_curve=:new.angle_curve,t.load_curve=:new.load_curve,t.power_curve=:new.power_curve,t.current_curve=:new.current_curve,t.rpm_curve=:new.rpm_curve,
              t.rawpower_curve=:new.rawpower_curve,t.rawcurrent_curve=:new.rawcurrent_curve,t.rawrpm_curve=:new.rawrpm_curve,
              t.position360_curve=:new.position360_curve,t.angle360_curve=:new.angle360_curve,t.load360_curve=:new.load360_curve,
              t.ia_curve=:new.ia_curve,t.ib_curve=:new.ib_curve,t.ic_curve=:new.ic_curve,
              t.upstrokeimax=:new.upstrokeimax,t.downstrokeimax=:new.downstrokeimax,t.upstrokewattmax=:new.upstrokewattmax,t.downstrokewattmax=:new.downstrokewattmax,t.idegreebalance=:new.idegreebalance,t.wattdegreebalance=:new.wattdegreebalance,t.deltaradius=:new.deltaradius,
              t.datasource=:new.datasource,
              t.resultcode=:new.resultcode,
              t.fullnesscoefficient=:new.fullnesscoefficient,t.plungerstroke=:new.plungerstroke,t.availableplungerstroke=:new.availableplungerstroke,
              t.levelcorrectvalue=:new.levelcorrectvalue,t.inverproducingfluidlevel=:new.inverproducingfluidlevel,
              t.noliquidfullnesscoefficient=:new.noliquidfullnesscoefficient,t.noliquidavailableplungerstroke=:new.noliquidavailableplungerstroke,
              t.upperloadline=:new.upperloadline,t.upperloadlineofexact=:new.upperloadlineofexact,t.lowerloadline=:new.lowerloadline,
              t.pumpfsdiagram=:new.pumpfsdiagram,t.theoreticalproduction=:new.theoreticalproduction,
              t.liquidvolumetricproduction=:new.liquidvolumetricproduction,t.oilvolumetricproduction=:new.oilvolumetricproduction,t.watervolumetricproduction=:new.watervolumetricproduction,
              t.availableplungerstrokeprod_v=:new.availableplungerstrokeprod_v,t.pumpclearanceleakprod_v=:new.pumpclearanceleakprod_v,
              t.tvleakvolumetricproduction=:new.tvleakvolumetricproduction,t.svleakvolumetricproduction=:new.svleakvolumetricproduction,t.gasinfluenceprod_v=:new.gasinfluenceprod_v,
              t.liquidweightproduction=:new.liquidweightproduction,t.oilweightproduction=:new.oilweightproduction,t.waterweightproduction=:new.waterweightproduction,
              t.availableplungerstrokeprod_w=:new.availableplungerstrokeprod_w,t.pumpclearanceleakprod_w=:new.pumpclearanceleakprod_w,
              t.tvleakweightproduction=:new.tvleakweightproduction,t.svleakweightproduction=:new.svleakweightproduction,t.gasinfluenceprod_w=:new.gasinfluenceprod_w,
              t.averagewatt=:new.averagewatt,t.polishrodpower=:new.polishrodpower,t.waterpower=:new.waterpower,
              t.surfacesystemefficiency=:new.surfacesystemefficiency,t.welldownsystemefficiency=:new.welldownsystemefficiency,t.systemefficiency=:new.systemefficiency,
              t.energyper100mlift=:new.energyper100mlift,t.area=:new.area,
              t.rodflexlength=:new.rodflexlength,t.tubingflexlength=:new.tubingflexlength,t.inertialength=:new.inertialength,
              t.pumpeff1=:new.pumpeff1,t.pumpeff2=:new.pumpeff2,t.pumpeff3=:new.pumpeff3,t.pumpeff4=:new.pumpeff4,t.pumpeff=:new.pumpeff,
              t.pumpintakep=:new.pumpintakep,t.pumpintaket=:new.pumpintaket,t.pumpintakegol=:new.pumpintakegol,t.pumpIntakevisl=:new.pumpIntakevisl,t.pumpIntakebo=:new.pumpIntakebo,
              t.pumpoutletp=:new.pumpoutletp,t.pumpoutlett=:new.pumpoutlett,t.pumpoutletgol=:new.pumpoutletgol,t.pumpoutletvisl=:new.pumpoutletvisl,t.pumpoutletbo=:new.pumpoutletbo,
              t.rodstring=:new.rodstring,t.savetime=:new.savetime,
              t.productiondataid=:new.productiondataid,
              t.discretedataid=:new.discretedataid,
              t.resultstatus=:new.resultstatus,
              t.inverresultstatus=:new.inverresultstatus,
              t.signal=:new.signal,t.interval=:new.interval,t.devicever=:new.devicever,
              t.crankangle=:new.crankangle,t.polishrodv=:new.polishrodv,t.polishroda=:new.polishroda,t.pr=:new.pr,t.tf=:new.tf,
              t.loadtorque=:new.loadtorque,t.cranktorque=:new.cranktorque,t.currentbalancetorque=:new.currentbalancetorque,t.currentnettorque=:new.currentnettorque,t.expectedbalancetorque=:new.expectedbalancetorque,t.expectednettorque=:new.expectednettorque,
              t.wellboreslice=:new.wellboreslice,
              t.remark=:new.remark
           where t.wellid=:new.wellid;
         end if;
       end if;
       --更新井信息表动液面校正值
       if :new.resultstatus=1 and :new.resultcode<>1232 then
          update tbl_wellinformation t set t.levelcorrectvalue=:new.levelcorrectvalue
          where t.id=:new.wellid;
       end if;
    end if;
    end if;
end;
/

CREATE OR REPLACE TRIGGER trg_a_rpc_discrete_latest_i_u
    before update or insert  on TBL_RPC_DISCRETE_LATEST
    for each row
declare
    recordCount number(8,2);
begin
    if :new.AcqTime is not null then
       :new.savetime:=sysdate;
       select count(id) into recordCount from tbl_rpc_discrete_hist t where t.wellid=:new.wellid and t.AcqTime=:new.AcqTime;
       if recordCount=0 then
          insert into tbl_rpc_discrete_hist(
              wellid,AcqTime,
              commstatus,commtime,commtimeefficiency,commrange,
              runstatus,runtime,runtimeefficiency,runrange,
              ia,ib,ic,iavg,va,vb,vc,vavg,
              totalKWattH,totalpKWattH,totalnKWattH,totalKVarH,totalpKVarH,totalnKVarH,totalKVAH,
              watta,wattb,wattc,watt3,
              vara,varb,varc,var3,
              vaa,vab,vac,va3,
              reversepower,
              pfa,pfb,pfc,pf3,
              acqcycle_diagram,setfrequency,runfrequency,
              tubingpressure,casingpressure,backpressure,wellheadfluidtemperature,
              todayKWattH,todaypKWattH,todaynKWattH,todayKVarH,todaypKVarH,todaynKVarH,todayKVAH,
              resultcode,
              iaalarm,ibalarm,icalarm,
              vaalarm,vbalarm,vcalarm,
              resultstring,
              iauplimit,iadownlimit,iazero,
              ibuplimit,ibdownlimit,ibzero,
              icuplimit,icdownlimit,iczero,
              vauplimit,vadownlimit,vazero,
              vbuplimit,vbdownlimit,vbzero,
              vcuplimit,vcdownlimit,vczero,
              signal,interval,devicever,
              balancecontrolmode,balancecalculatemode,
              balanceawaytime,balanceclosetime,balanceawaytimeperbeat,balanceclosetimeperbeat,
              balancestrokecount,
              balanceoperationuplimit,balanceoperationdownlimit,balanceautocontrol,spmautocontrol,balancefrontlimit,balanceafterlimit,
              acqcycle_discrete,wattuplimit,wattdownlimit,
              iamax,iamin,ibmax,ibmin,icmax,icmin,savetime
          )values(
              :new.wellid,:new.AcqTime,
              :new.commstatus,:new.commtime,:new.commtimeefficiency,:new.commrange,
              :new.runstatus,:new.runtime,:new.runtimeefficiency,:new.runrange,
              :new.ia,:new.ib,:new.ic,:new.iavg,:new.va,:new.vb,:new.vc,:new.vavg,
              :new.totalKWattH,
              :new.totalpKWattH,
              :new.totalnKWattH,
              :new.totalKVarH,
              :new.totalpKVarH,
              :new.totalnKVarH,
              :new.totalKVAH,
              :new.watta,:new.wattb,:new.wattc,:new.watt3,
              :new.vara,:new.varb,:new.varc,:new.var3,
              :new.vaa,:new.vab,:new.vac,:new.va3,
              :new.reversepower,
              :new.pfa,:new.pfb,:new.pfc,:new.pf3,
              :new.acqcycle_diagram,:new.setfrequency,:new.runfrequency,
              :new.tubingpressure,:new.casingpressure,:new.backpressure,:new.wellheadfluidtemperature,
              :new.todayKWattH,
              :new.todaypKWattH,
              :new.todaynKWattH,
              :new.todayKVarH,
              :new.todaypKVarH,
              :new.todaynKVarH,
              :new.todayKVAH,
              :new.resultcode,
              :new.iaalarm,:new.ibalarm,:new.icalarm,
              :new.vaalarm,:new.vbalarm,:new.vcalarm,
              :new.resultstring,
              :new.iauplimit,:new.iadownlimit,:new.iazero,
              :new.ibuplimit,:new.ibdownlimit,:new.ibzero,
              :new.icuplimit,:new.icdownlimit,:new.iczero,
              :new.vauplimit,:new.vadownlimit,:new.vazero,
              :new.vbuplimit,:new.vbdownlimit,:new.vbzero,
              :new.vcuplimit,:new.vcdownlimit,:new.vczero,
              :new.signal,:new.interval,:new.devicever,
              :new.balancecontrolmode,:new.balancecalculatemode,
              :new.balanceawaytime,:new.balanceclosetime,:new.balanceawaytimeperbeat,:new.balanceclosetimeperbeat,
              :new.balancestrokecount,
              :new.balanceoperationuplimit,:new.balanceoperationdownlimit,
              :new.balanceautocontrol,:new.spmautocontrol,:new.balancefrontlimit,:new.balanceafterlimit,
              :new.acqcycle_discrete,:new.wattuplimit,:new.wattdownlimit,
              :new.iamax,:new.iamin,:new.ibmax,:new.ibmin,:new.icmax,:new.icmin,
              :new.savetime
          );
       elsif recordCount>0 then
           update tbl_rpc_discrete_hist t set
              t.commstatus=:new.commstatus,t.commtime=:new.commtime,t.commtimeefficiency=:new.commtimeefficiency,t.commrange=:new.commrange,
              t.runstatus=:new.runstatus,t.runtime=:new.runtime,t.runtimeefficiency=:new.runtimeefficiency,t.runrange=:new.runrange,
              t.ia=:new.ia,t.ib=:new.ib,t.ic=:new.ic,t.iavg=:new.iavg,
              t.va=:new.va,t.vb=:new.vb,t.vc=:new.vc,t.vavg=:new.vavg,
              t.totalKWattH=:new.totalKWattH,t.totalpKWattH=:new.totalpKWattH,t.totalnKWattH=:new.totalnKWattH,
              t.totalKVarH=:new.totalKVarH,t.totalpKVarH=:new.totalpKVarH,t.totalnKVarH=:new.totalnKVarH,
              t.totalKVAH=:new.totalKVAH,
              t.watta=:new.watta,t.wattb=:new.wattb,t.wattc=:new.wattc,t.watt3=:new.watt3,
              t.vara=:new.vara,t.varb=:new.varb,t.varc=:new.varc,t.var3=:new.var3,
              t.vaa=:new.vaa,t.vab=:new.vab,t.vac=:new.vac,t.va3=:new.va3,
              t.reversepower=:new.reversepower,
              t.pfa=:new.pfa,t.pfb=:new.pfb,t.pfc=:new.pfc,t.pf3=:new.pf3,
              t.acqcycle_diagram=:new.acqcycle_diagram,t.setfrequency=:new.setfrequency,t.runfrequency=:new.runfrequency,
              t.tubingpressure=:new.tubingpressure,t.casingpressure=:new.casingpressure,t.backpressure=:new.backpressure,t.wellheadfluidtemperature=:new.wellheadfluidtemperature,
              t.todayKWattH=:new.todayKWattH,t.todaypKWattH=:new.todaypKWattH,t.todaynKWattH=:new.todaynKWattH,
              t.todayKVarH=:new.todayKVarH,t.todaypKVarH=:new.todaypKVarH,t.todaynKVarH=:new.todaynKVarH,
              t.todayKVAH=:new.todayKVAH,
              t.resultcode=:new.resultcode,
              t.iaalarm=:new.iaalarm,t.ibalarm=:new.ibalarm,t.icalarm=:new.icalarm,
              t.vaalarm=:new.vaalarm,t.vbalarm=:new.vbalarm,t.vcalarm=:new.vcalarm,
              t.resultstring=:new.resultstring,
              t.iauplimit=:new.iauplimit,t.iadownlimit=:new.iadownlimit,t.iazero=:new.iazero,
              t.ibuplimit=:new.ibuplimit,t.ibdownlimit=:new.ibdownlimit,t.ibzero=:new.ibzero,
              t.icuplimit=:new.icuplimit,t.icdownlimit=:new.icdownlimit,t.iczero=:new.iczero,
              t.vauplimit=:new.vauplimit,t.vadownlimit=:new.vadownlimit,t.vazero=:new.vazero,
              t.vbuplimit=:new.vbuplimit,t.vbdownlimit=:new.vbdownlimit,t.vbzero=:new.vbzero,
              t.vcuplimit=:new.vcuplimit,t.vcdownlimit=:new.vcdownlimit,t.vczero=:new.vczero,
              t.signal=:new.signal,t.interval=:new.interval,t.devicever=:new.devicever,
              t.balancecontrolmode=:new.balancecontrolmode,
              t.balancecalculatemode=:new.balancecalculatemode,
              t.balanceawaytime=:new.balanceawaytime,
              t.balanceclosetime=:new.balanceclosetime,
              t.balancestrokecount=:new.balancestrokecount,
              t.balanceoperationuplimit=:new.balanceoperationuplimit,
              t.balanceoperationdownlimit=:new.balanceoperationdownlimit,
              t.balanceawaytimeperbeat=:new.balanceawaytimeperbeat,
              t.balanceclosetimeperbeat=:new.balanceclosetimeperbeat,
              t.balanceautocontrol=:new.balanceautocontrol,
              t.spmautocontrol=:new.spmautocontrol,
              t.balancefrontlimit=:new.balancefrontlimit,
              t.balanceafterlimit=:new.balanceafterlimit,
              t.acqcycle_discrete=:new.acqcycle_discrete,t.wattuplimit=:new.wattuplimit,t.wattdownlimit=:new.wattdownlimit,
              t.iamax=:new.iamax,t.iamin=:new.iamin,t.ibmax=:new.ibmax,t.ibmin=:new.ibmin,t.icmax=:new.icmax,t.icmin=:new.icmin,
              t.savetime=:new.savetime
           where t.wellid=:new.wellid and t.AcqTime=:new.AcqTime;
       end if;
       update tbl_rpc_total_day t
       set t.commstatus=:new.commstatus,t.commtime=:new.commtime,t.commtimeefficiency=:new.commtimeefficiency,t.commrange=:new.commrange,
              t.runstatus=:new.runstatus,t.runtime=:new.runtime,t.runtimeefficiency=:new.runtimeefficiency,t.runrange=:new.runrange,
              t.todayKWattH=:new.todayKWattH,t.todaypKWattH=:new.todaypKWattH,t.todaynKWattH=:new.todaynKWattH,
              t.todayKVarH=:new.todayKVarH,t.todaypKVarH=:new.todaypKVarH,t.todaynKVarH=:new.todaynKVarH,
              t.todayKVAH=:new.todayKVAH,
              t.totalKWattH=:new.totalKWattH,t.totalpKWattH=:new.totalpKWattH,t.totalnKWattH=:new.totalnKWattH,
              t.totalKVarH=:new.totalKVarH,t.totalpKVarH=:new.totalpKVarH,t.totalnKVarH=:new.totalnKVarH,
              t.totalKVAH=:new.totalKVAH
       where t.wellid=:new.wellid and t.calculatedate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd');
    end if;
end;
/

CREATE OR REPLACE TRIGGER trg_a_rpc_proddata_latest_i_u
    before update or insert  on TBL_RPC_PRODUCTIONDATA_LATEST
    for each row
declare
    waterDensity number(8,2);
    crudeOilDensity number(8,2);
    weightwatercut number(8,2);
    volumewaterCut number(8,2);
begin
    
    waterDensity:=:new.waterDensity;
    crudeOilDensity:=:new.crudeOilDensity;
    --由重量含水率计算体积含水率;
    if (:new.weightwatercut is not null and :new.weightwatercut != :old.weightwatercut) or :new.volumewaterCut is null then
       weightwatercut:=:new.weightwatercut;
       if crudeOilDensity<0.00001 or weightwatercut<0.00001 then
          :new.volumewaterCut:=0;
          else
          :new.volumewaterCut:=100/(1+waterDensity/crudeOilDensity*(100-weightwatercut)/weightwatercut);
       end if;
    --由体积含水率计算重量含水率;
    elsif (:new.volumewaterCut is not null and :new.volumewaterCut != :old.volumewaterCut) or :new.weightwatercut is null then 
       volumewaterCut:=:new.volumewaterCut;
       if crudeOilDensity<0.00001 or volumewaterCut<0.00001 then
          :new.weightwatercut:=0;
          else
          :new.weightwatercut:=100*volumewaterCut*waterDensity/(volumewaterCut*waterDensity+(100-volumewaterCut)*crudeOilDensity);
       end if;
    end if;

    --同时向生产数据历史表中插入数据
    insert into tbl_rpc_productiondata_hist(
        wellid,AcqTime,liftingtype,displacementtype,runtime,
        crudeoildensity,waterdensity,naturalgasrelativedensity,saturationpressure,reservoirdepth,reservoirtemperature,
        volumewaterCut,weightwatercut,tubingpressure,casingpressure,backpressure,wellheadfluidtemperature,
        producingfluidlevel,pumpsettingdepth,productiongasoilratio,
        tubingstringinsidediameter,casingstringinsidediameter,rodstring,
        pumpgrade,pumpborediameter,plungerlength,pumptype,barreltype,barrellength,barrelseries,rotordiameter,qpr,
        manualintervention,netgrossratio,anchoringstate,runtimeefficiencysource,remark
      )values(
        :new.wellid,:new.AcqTime,:new.liftingtype,:new.displacementtype,:new.runtime,
        :new.crudeoildensity,:new.waterdensity,:new.naturalgasrelativedensity,:new.saturationpressure,:new.reservoirdepth,:new.reservoirtemperature,
        :new.volumewaterCut,:new.weightwatercut,:new.tubingpressure,:new.casingpressure,:new.backpressure,:new.wellheadfluidtemperature,
        :new.producingfluidlevel,:new.pumpsettingdepth,:new.productiongasoilratio,
        :new.tubingstringinsidediameter,:new.casingstringinsidediameter,:new.rodstring,
        :new.pumpgrade,:new.pumpborediameter,:new.plungerlength,:new.pumptype,:new.barreltype,:new.barrellength,:new.barrelseries,:new.rotordiameter,:new.qpr,
        :new.manualintervention,:new.netgrossratio,:new.anchoringstate,:new.runtimeefficiencysource,:new.remark
      );
end;
/

create or replace trigger trg_a_wellinformation_i   after  insert  on TBL_WELLINFORMATION FOR EACH ROW
declare
  prodCount_RT   number(10);
  diagramCount_RT   number(10);
  discreteCount_RT   number(10);
  inverOptiCount   number(10);
  dailyCount   number(10);
begin
  if :new.liftingtype>=200 and :new.liftingtype<300 then
    --向生产数据实时表中添加记录
    select count(id) into prodCount_RT from tbl_rpc_productiondata_latest t where t.wellid = :new.id;
    if prodCount_RT = 0  then
       insert into tbl_rpc_productiondata_latest (wellId) (select :new.id from dual);
    end if;

    --向功图实时表中添加记录
    select count(id) into diagramCount_RT from tbl_rpc_diagram_latest t where t.wellid = :new.id;
    if diagramCount_RT = 0 then
       insert into tbl_rpc_diagram_latest (wellId) (select :new.id from dual);
    end if;

    --向离散数据实时表中添加记录
    select count(id) into discreteCount_RT from tbl_rpc_discrete_latest t where t.wellid = :new.id;
    if discreteCount_RT = 0 then
       insert into tbl_rpc_discrete_latest (wellId) (select :new.id from dual);
    end if;

    --向反演参数优化表中添加记录
    select count(id) into inverOptiCount from tbl_rpc_inver_opt t where t.wellid = :new.id;
    if inverOptiCount = 0 then
       insert into tbl_rpc_inver_opt (wellId) (select :new.id from dual);
    end if;

    --向日汇总表中添加记录
    select count(id) into dailyCount from tbl_rpc_total_day t
    where t.wellid = :new.id and t.calculatedate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd');
    if dailyCount = 0 then
       insert into tbl_rpc_total_day (wellId,calculatedate) (select :new.id,to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') from dual);
    end if;

  elsif :new.liftingtype>=400 and :new.liftingtype<500 then
    select count(id) into prodCount_RT from tbl_pcp_productiondata_latest t where t.wellid = :new.id;
    if prodCount_RT = 0  then
       insert into tbl_pcp_productiondata_latest (wellId) (select :new.id from dual);
    end if;

    select count(id) into diagramCount_RT from tbl_pcp_rpm_latest t where t.wellid = :new.id;
    if diagramCount_RT = 0 then
       insert into tbl_pcp_rpm_latest (wellId) (select :new.id from dual);
    end if;
  end if;


end;
/

create or replace trigger trg_b_a9rawdata_hist_i   before  insert on tbl_a9rawdata_hist FOR EACH ROW
BEGIN
  SELECT Seq_A9rawdata_hist.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_a9rawdata_latest_i   before  insert on tbl_a9rawdata_latest FOR EACH ROW
BEGIN
  SELECT Seq_A9rawdata_Latest.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_a9rawwatercut_hist_i   before  insert on TBL_A9RAWWATERCUTDATA_HIST FOR EACH ROW
BEGIN
  SELECT SEQ_A9RAWWATERCUTDATA_HIST.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_a9rawwatercut_latest_i   before  insert on TBL_A9RAWWATERCUTDATA_LATEST FOR EACH ROW
BEGIN
  SELECT SEQ_A9RAWWATERCUTDATA_LATEST.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_acq_group2unit_conf_i   before  insert on TBL_ACQ_group2unit_conf FOR EACH ROW
BEGIN
  SELECT SEQ_ACQ_UNIT_GROUP.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_acq_group_conf_i   before  insert on TBL_ACQ_GROUP_CONF FOR EACH ROW
BEGIN
  SELECT SEQ_ACQUISITIONGROUP.nextval,'group' || SEQ_ACQUISITIONGROUP.nextval INTO :new.id, :new.group_code FROM dual;
end;
/

create or replace trigger trg_b_acq_item2group_conf_i   before  insert on TBL_ACQ_ITEM2GROUP_CONF FOR EACH ROW
BEGIN
  SELECT SEQ_ACQ_GROUP_ITEM.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_acq_unit_conf_i   before  insert on TBL_ACQ_UNIT_CONF FOR EACH ROW
BEGIN
  SELECT SEQ_ACQUISITIONUNIT.nextval,'unit' || SEQ_ACQUISITIONUNIT.nextval INTO :new.id, :new.unit_code FROM dual;
end;
/

create or replace trigger trg_b_code_i   before  insert on TBL_CODE FOR EACH ROW
BEGIN
  SELECT SEQ_code.nextval INTO :new.id FROM dual;
END;
/

create or replace trigger trg_b_module_i   before  insert on TBL_MODULE FOR EACH ROW
BEGIN
  SELECT SEQ_MODULE.nextval INTO :new.MD_id FROM dual;
END;
/

create or replace trigger trg_b_org_i_u   before  insert or update  on TBL_ORG FOR EACH ROW
BEGIN
  case
       when inserting then
            SELECT SEQ_ORG.nextval INTO :new.ORG_ID FROM dual;
       when updating then
            update Tbl_WellInformation t set t.orgid=:new.org_id where t.orgid=:old.org_id;
  end case;
END;
/

create or replace trigger trg_b_pcp_discrete_hist_i   before  insert on tbl_pcp_discrete_hist FOR EACH ROW
BEGIN
  SELECT seq_pcp_discrete_hist.nextval INTO :new.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_b_pcp_discrete_latest_i   before  insert or update on tbl_pcp_discrete_latest
FOR EACH ROW

declare
    recordCount number(8,2);
BEGIN
  if inserting then
      SELECT seq_pcp_discrete_latest.nextval INTO :new.id FROM dual;
  end if;

  if :new.AcqTime is not null then
       select count(id) into recordCount from tbl_pcp_discrete_hist t where t.wellid=:new.wellid and t.AcqTime=:new.AcqTime;
       if recordCount=0 then
          insert into tbl_pcp_discrete_hist(
              wellid,AcqTime,
              commstatus,commtime,commtimeefficiency,commrange,
              runstatus,runtime,runtimeefficiency,runrange,
              ia,ib,ic,iavg,va,vb,vc,vavg,
              totalKWattH,totalpKWattH,totalnKWattH,totalKVarH,totalpKVarH,totalnKVarH,totalKVAH,
              watta,wattb,wattc,watt3,
              vara,varb,varc,var3,
              vaa,vab,vac,va3,
              reversepower,
              pfa,pfb,pfc,pf3,
              setfrequency,runfrequency,
              tubingpressure,casingpressure,backpressure,wellheadfluidtemperature,
              todayKWattH,todaypKWattH,todaynKWattH,todayKVarH,todaypKVarH,todaynKVarH,todayKVAH,
              resultcode,
              iaalarm,ibalarm,icalarm,
              vaalarm,vbalarm,vcalarm,
              resultstring,
              iauplimit,iadownlimit,iazero,
              ibuplimit,ibdownlimit,ibzero,
              icuplimit,icdownlimit,iczero,
              vauplimit,vadownlimit,vazero,
              vbuplimit,vbdownlimit,vbzero,
              vcuplimit,vcdownlimit,vczero,
              signal,interval,devicever
          )values(
              :new.wellid,:new.AcqTime,
              :new.commstatus,:new.commtime,:new.commtimeefficiency,:new.commrange,
              :new.runstatus,:new.runtime,:new.runtimeefficiency,:new.runrange,
              :new.ia,:new.ib,:new.ic,:new.iavg,:new.va,:new.vb,:new.vc,:new.vavg,
              :new.totalKWattH,:new.totalpKWattH,:new.totalnKWattH,:new.totalKVarH,:new.totalpKVarH,:new.totalnKVarH,:new.totalKVAH,
              :new.watta,:new.wattb,:new.wattc,:new.watt3,
              :new.vara,:new.varb,:new.varc,:new.var3,
              :new.vaa,:new.vab,:new.vac,:new.va3,
              :new.reversepower,
              :new.pfa,:new.pfb,:new.pfc,:new.pf3,
              :new.setfrequency,:new.runfrequency,
              :new.tubingpressure,:new.casingpressure,:new.backpressure,:new.wellheadfluidtemperature,
              :new.todayKWattH,:new.todaypKWattH,:new.todaynKWattH,:new.todayKVarH,:new.todaypKVarH,:new.todaynKVarH,:new.todayKVAH,
              :new.resultcode,
              :new.iaalarm,:new.ibalarm,:new.icalarm,
              :new.vaalarm,:new.vbalarm,:new.vcalarm,
              :new.resultstring,
              :new.iauplimit,:new.iadownlimit,:new.iazero,
              :new.ibuplimit,:new.ibdownlimit,:new.ibzero,
              :new.icuplimit,:new.icdownlimit,:new.iczero,
              :new.vauplimit,:new.vadownlimit,:new.vazero,
              :new.vbuplimit,:new.vbdownlimit,:new.vbzero,
              :new.vcuplimit,:new.vcdownlimit,:new.vczero,
              :new.signal,:new.interval,:new.devicever
          );
          update tbl_rpc_diagram_hist t set t.discretedataid=( select id from tbl_pcp_discrete_hist dis where dis.wellid=:new.wellid and dis.AcqTime=:new.AcqTime )
          where t.wellid=:new.wellid and t.AcqTime=( select max(t2.AcqTime) from tbl_rpc_diagram_hist t2 where t2.wellid=t.wellid );
       elsif recordCount>0 then
           update tbl_pcp_discrete_hist t set
              t.commstatus=:new.commstatus,t.commtime=:new.commtime,t.commtimeefficiency=:new.commtimeefficiency,t.commrange=:new.commrange,
              t.runstatus=:new.runstatus,t.runtime=:new.runtime,t.runtimeefficiency=:new.runtimeefficiency,t.runrange=:new.runrange,
              t.ia=:new.ia,t.ib=:new.ib,t.ic=:new.ic,t.iavg=:new.iavg,
              t.va=:new.va,t.vb=:new.vb,t.vc=:new.vc,t.vavg=:new.vavg,
              t.totalKWattH=:new.totalKWattH,t.totalpKWattH=:new.totalpKWattH,t.totalnKWattH=:new.totalnKWattH,
              t.totalKVarH=:new.totalKVarH,t.totalpKVarH=:new.totalpKVarH,t.totalnKVarH=:new.totalnKVarH,
              t.totalKVAH=:new.totalKVAH,
              t.watta=:new.watta,t.wattb=:new.wattb,t.wattc=:new.wattc,t.watt3=:new.watt3,
              t.vara=:new.vara,t.varb=:new.varb,t.varc=:new.varc,t.var3=:new.var3,
              t.vaa=:new.vaa,t.vab=:new.vab,t.vac=:new.vac,t.va3=:new.va3,
              t.reversepower=:new.reversepower,
              t.pfa=:new.pfa,t.pfb=:new.pfb,t.pfc=:new.pfc,t.pf3=:new.pf3,
              t.setfrequency=:new.setfrequency,t.runfrequency=:new.runfrequency,
              t.tubingpressure=:new.tubingpressure,t.casingpressure=:new.casingpressure,t.backpressure=:new.backpressure,t.wellheadfluidtemperature=:new.wellheadfluidtemperature,
              t.todayKWattH=:new.todayKWattH,t.todaypKWattH=:new.todaypKWattH,t.todaynKWattH=:new.todaynKWattH,
              t.todayKVarH=:new.todayKVarH,t.todaypKVarH=:new.todaypKVarH,t.todaynKVarH=:new.todaynKVarH,
              t.todayKVAH=:new.todayKVAH,
              t.resultcode=:new.resultcode,
              t.iaalarm=:new.iaalarm,t.ibalarm=:new.ibalarm,t.icalarm=:new.icalarm,
              t.vaalarm=:new.vaalarm,t.vbalarm=:new.vbalarm,t.vcalarm=:new.vcalarm,
              t.resultstring=:new.resultstring,
              t.iauplimit=:new.iauplimit,t.iadownlimit=:new.iadownlimit,t.iazero=:new.iazero,
              t.ibuplimit=:new.ibuplimit,t.ibdownlimit=:new.ibdownlimit,t.ibzero=:new.ibzero,
              t.icuplimit=:new.icuplimit,t.icdownlimit=:new.icdownlimit,t.iczero=:new.iczero,
              t.vauplimit=:new.vauplimit,t.vadownlimit=:new.vadownlimit,t.vazero=:new.vazero,
              t.vbuplimit=:new.vbuplimit,t.vbdownlimit=:new.vbdownlimit,t.vbzero=:new.vbzero,
              t.vcuplimit=:new.vcuplimit,t.vcdownlimit=:new.vcdownlimit,t.vczero=:new.vczero,
              t.signal=:new.signal,t.interval=:new.interval,t.devicever=:new.devicever
           where t.wellid=:new.wellid and t.AcqTime=:new.AcqTime;
       end if;
       update tbl_pcp_total_day t
       set t.commstatus=:new.commstatus,t.commtime=:new.commtime,t.commtimeefficiency=:new.commtimeefficiency,t.commrange=:new.commrange,
              t.runstatus=:new.runstatus,t.runtime=:new.runtime,t.runtimeefficiency=:new.runtimeefficiency,t.runrange=:new.runrange,
              t.todayKWattH=:new.todayKWattH,t.todaypKWattH=:new.todaypKWattH,t.todaynKWattH=:new.todaynKWattH,
              t.todayKVarH=:new.todayKVarH,t.todaypKVarH=:new.todaypKVarH,t.todaynKVarH=:new.todaynKVarH,
              t.todayKVAH=:new.todayKVAH,
              t.totalKWattH=:new.totalKWattH,t.totalpKWattH=:new.totalpKWattH,t.totalnKWattH=:new.totalnKWattH,
              t.totalKVarH=:new.totalKVarH,t.totalpKVarH=:new.totalpKVarH,t.totalnKVarH=:new.totalnKVarH,
              t.totalKVAH=:new.totalKVAH
       where t.wellid=:new.wellid and t.calculatedate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd');
    end if;
END;
/

create or replace trigger trg_b_pcp_proddata_hist_i   before  insert on tbl_pcp_productiondata_hist FOR EACH ROW
BEGIN
  SELECT seq_pcp_productiondata_hist.nextval INTO :new.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_b_pcp_proddata_latest_i
before  insert or update on tbl_pcp_productiondata_latest
FOR EACH ROW
  
declare
    waterDensity number(8,2);
    crudeOilDensity number(8,2);
    weightwatercut number(8,2);
    volumewatercut number(8,2);
BEGIN
  if inserting then
      SELECT seq_pcp_productiondata_latest.nextval INTO :new.id FROM dual;
  end if;
  --由重量含水率计算体积含水率;
    waterDensity:=:new.waterDensity;
    crudeOilDensity:=:new.crudeOilDensity;
    --由重量含水率计算体积含水率;
    if (:new.weightwatercut is not null and :new.weightwatercut != :old.weightwatercut) or :new.volumewatercut is null then
       weightwatercut:=:new.weightwatercut;
       if crudeOilDensity<0.00001 or weightwatercut<0.00001 then
          :new.volumewatercut:=0;
          else
          :new.volumewatercut:=100/(1+waterDensity/crudeOilDensity*(100-weightwatercut)/weightwatercut);
       end if;
    --由体积含水率计算重量含水率;
    elsif (:new.volumewatercut is not null and :new.volumewatercut != :old.volumewatercut) or :new.weightwatercut is null then 
       volumewatercut:=:new.volumewatercut;
       if crudeOilDensity<0.00001 or volumewatercut<0.00001 then
          :new.weightwatercut:=0;
          else
          :new.weightwatercut:=100*volumewatercut*waterDensity/(volumewatercut*waterDensity+(100-volumewatercut)*crudeOilDensity);
       end if;
    end if;
    --同时向生产数据历史表中插入数据
    insert into tbl_pcp_productiondata_hist(
        wellid,AcqTime,liftingtype,displacementtype,runtime,
        crudeoildensity,waterdensity,naturalgasrelativedensity,saturationpressure,reservoirdepth,reservoirtemperature,
        volumewatercut,weightwatercut,tubingpressure,casingpressure,backpressure,wellheadfluidtemperature,
        producingfluidlevel,pumpsettingdepth,productiongasoilratio,
        tubingstringinsidediameter,casingstringinsidediameter,rodstring,
        pumpgrade,pumpborediameter,plungerlength,pumptype,barreltype,barrellength,barrelseries,rotordiameter,qpr,
        manualintervention,netgrossratio,anchoringstate,runtimeefficiencysource,remark
      )values(
        :new.wellid,:new.AcqTime,:new.liftingtype,:new.displacementtype,:new.runtime,
        :new.crudeoildensity,:new.waterdensity,:new.naturalgasrelativedensity,:new.saturationpressure,:new.reservoirdepth,:new.reservoirtemperature,
        :new.volumewatercut,:new.weightwatercut,:new.tubingpressure,:new.casingpressure,:new.backpressure,:new.wellheadfluidtemperature,
        :new.producingfluidlevel,:new.pumpsettingdepth,:new.productiongasoilratio,
        :new.tubingstringinsidediameter,:new.casingstringinsidediameter,:new.rodstring,
        :new.pumpgrade,:new.pumpborediameter,:new.plungerlength,:new.pumptype,:new.barreltype,:new.barrellength,:new.barrelseries,:new.rotordiameter,:new.qpr,
        :new.manualintervention,:new.netgrossratio,:new.anchoringstate,:new.runtimeefficiencysource,:new.remark
      );
END;
/

create or replace trigger trg_b_pcp_rpm_hist_i   before  insert on tbl_pcp_rpm_hist FOR EACH ROW
BEGIN
  SELECT seq_pcp_rpm_hist.nextval INTO :new.id FROM dual;
END;
/

create or replace trigger trg_b_pcp_rpm_latest_i   before  insert on tbl_pcp_rpm_latest FOR EACH ROW
BEGIN
  SELECT seq_pcp_rpm_latest.nextval INTO :new.id FROM dual;
END;
/

create or replace trigger trg_b_pcp_total_day_i   before  insert on tbl_pcp_total_day FOR EACH ROW
BEGIN
  SELECT seq_pcp_total_day.nextval INTO :new.id FROM dual;
END;
/

create or replace trigger trg_b_resourcemonitoring_i   before  insert on tbl_resourcemonitoring FOR EACH ROW
BEGIN
  SELECT seq_resourcemonitoring.nextval INTO :new.id FROM dual;
END;
/

create or replace trigger trg_b_role_i   before  insert  on TBL_ROLE FOR EACH ROW
BEGIN
       SELECT SEQ_ROLE.nextval INTO :new.ROLE_ID FROM dual;
END;
/

create or replace trigger trg_b_rpcinformation_i   before  insert on TBL_RPCINFORMATION FOR EACH ROW
BEGIN
  SELECT SEQ_INVER_PUMPINGUNIT.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_alarmtype_conf_i   before  insert on TBL_RPC_ALARMTYPE_CONF FOR EACH ROW
BEGIN
  SELECT SEQ_WORKSTATUSALARM.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_diagram_hist_i   before  insert on TBL_RPC_DIAGRAM_HIST FOR EACH ROW
BEGIN
  SELECT SEQ_indicatordiagram.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_diagram_latest_i   before  insert on TBL_RPC_DIAGRAM_LATEST FOR EACH ROW
BEGIN
  SELECT SEQ_indicatordiagram_RT.nextval INTO :new.id FROM dual;
end;
/
CREATE OR REPLACE TRIGGER trg_b_rpc_diagram_total_i   before  insert on TBL_RPC_DIAGRAM_TOTAL FOR EACH ROW
BEGIN
  SELECT seq_rpc_diagram_total.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_discrete_hist_i   before  insert on TBL_RPC_DISCRETE_HIST FOR EACH ROW
BEGIN
  SELECT SEQ_DISCRETEDATA.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_discrete_latest_i   before  insert on TBL_RPC_DISCRETE_LATEST FOR EACH ROW
BEGIN
  SELECT SEQ_DISCRETEDATA_RT.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_inver_opt_i   before  insert on TBL_RPC_INVER_OPT FOR EACH ROW
BEGIN
  SELECT SEQ_INVER_optimize.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_motor_i   before  insert on TBL_RPC_MOTOR FOR EACH ROW
BEGIN
  SELECT SEQ_INVER_motor.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_proddata_hist_i   before  insert on TBL_RPC_PRODUCTIONDATA_HIST FOR EACH ROW
BEGIN
  SELECT SEQ_OUTPUTWELLPRODUCTION.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_proddata_latest_i   before  insert on TBL_RPC_PRODUCTIONDATA_LATEST FOR EACH ROW
BEGIN
  SELECT SEQ_OUTPUTWELLPRODUCTION_RT.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_statistics_conf_i   before  insert on TBL_RPC_STATISTICS_CONF FOR EACH ROW
BEGIN
  SELECT SEQ_OUTPUTSTATISTICS.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_total_day_i   before  insert on TBL_RPC_TOTAL_DAY FOR EACH ROW
BEGIN
  SELECT SEQ_OUTPUTWELLAGGREGATION.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_worktype_i   before  insert on TBL_RPC_WORKTYPE FOR EACH ROW
BEGIN
  SELECT SEQ_WORKSTATUS.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_user_i   before  insert on TBL_USER FOR EACH ROW
BEGIN
  SELECT SEQ_USER.nextval INTO :new.USER_NO FROM dual;
END;
/

create or replace trigger trg_b_WellboreTrajectory_i   before  insert on tbl_WellboreTrajectory  FOR EACH ROW
BEGIN
  SELECT SEQ_WellboreTrajectory.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_wellinformation_i   before  insert on TBL_WELLINFORMATION FOR EACH ROW
BEGIN
  SELECT SEQ_WELLINFORMATION.nextval INTO :new.id FROM dual;
end;
/
