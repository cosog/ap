create or replace function BlobToClob(blob_in IN BLOB)
RETURN CLOB AS
    v_clob    CLOB;
    v_varchar VARCHAR2(32767);
    v_start   PLS_INTEGER := 1;
    v_buffer  PLS_INTEGER := 32767;
BEGIN
    DBMS_LOB.CREATETEMPORARY(v_clob, TRUE);
    FOR i IN 1 .. CEIL(DBMS_LOB.GETLENGTH(blob_in) / v_buffer) LOOP
        v_varchar := UTL_RAW.CAST_TO_VARCHAR2(DBMS_LOB.SUBSTR(blob_in,
                                                              v_buffer,
                                                              v_start));
        DBMS_LOB.WRITEAPPEND(v_clob, LENGTH(v_varchar), v_varchar);
        DBMS_OUTPUT.PUT_LINE(v_varchar);
        v_start := v_start + v_buffer;
    END LOOP;
    RETURN v_clob;
END BlobToClob;
/


create or replace function GETELEMENTFROMARRAYBYINDEX(Liststr in varchar2,sPlitVal in varchar2,iPos integer)
return varchar2 is
  /*
  Liststr--传入将要被分割的字符串
  sPlitVal--用来分割的字符串
  iPos--获取分割后的数组中该位置的元素值

  */
  type tt_type is table of varchar2(100) INDEX BY BINARY_INTEGER;
  V1 tt_type;
  --FieldNames转化为数组
  TmpStr varchar2(100);
  Str    varchar2(4000);
  j      integer;
begin
  Str := Liststr;
  j   := 0;
  IF Instr(Liststr, sPlitVal, 1, 1) = 0 THEN
    V1(j) := Liststr;
    j := j + 1;
  else
    While Instr(str, sPlitVal, 1, 1) > 0 Loop
      TmpStr := Substr(str, 1, Instr(str, sPlitVal, 1, 1) - 1);

      V1(j) := TmpStr;
      str := SubStr(Str,
                    Instr(str, sPlitVal, 1, 1) + length(sPlitVal),
                    length(str));
      j := j + 1;
    end loop;
    if not str is null then
      --将最后一个保存
      V1(j) := str;
      j := j + 1;
    end if;
  end if;
  if iPos > j - 1 or iPos < 0 then
    --超出数组长度
    return '';
  end if;
  return V1(ipos);
end;
/

CREATE OR REPLACE PROCEDURE prd_clearResourceProbeData is
begin
    delete from tbl_resourcemonitoring t where t.acqtime<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd');
    commit;
end prd_clearResourceProbeData;
/

CREATE OR REPLACE PROCEDURE prd_change_wellname (v_oldWellName    in varchar2,
                                                    v_newWellName    in varchar2,
                                                    v_orgId     in varchar2) as
  wellcount number :=0;
  newwellcount number :=0;
  oldwellcount number :=0;
  newWellId number :=0;
  oldWellId number :=0;
  p_msg varchar2(3000) := 'error';
  p_sql varchar2(3000);
begin
  --验证权限,查询新改井号是否已存在与其他组织
  p_sql:='select count(*)  from tbl_wellinformation t where t.wellname='''||v_oldWellName||''' and t.orgid not in ('||v_orgId||')';
  dbms_output.put_line('p_sql:' || p_sql);
  EXECUTE IMMEDIATE p_sql into wellcount;
  dbms_output.put_line('wellcount:' || wellcount);
  if wellcount=0 then
     select count(*) into newwellcount from tbl_wellinformation t where t.wellName=v_newWellName;
     if newwellcount>0 then
        select id into newWellId from tbl_wellinformation t where t.wellname=v_newWellName;
        select count(*) into oldwellcount from tbl_wellinformation t where t.wellname=v_oldWellName;
        if oldwellcount>0 then
           select id into oldWellId from tbl_wellinformation t where t.wellname=v_oldWellName;
           update tbl_rpc_diagram_hist t set t.wellid=newWellId where t.wellid=oldWellId;
           commit;
           update tbl_rpc_diagram_latest t set t.wellid=newWellId where t.wellid=oldWellId;
           commit;
           update tbl_rpc_productiondata_hist t set t.wellid=newWellId where t.wellid=oldWellId;
           commit;
           update tbl_rpc_productiondata_latest t set t.wellid=newWellId where t.wellid=oldWellId;
           commit;
           update tbl_rpc_total_day t set t.wellid=newWellId where t.wellid=oldWellId;
           commit;
           update tbl_rpc_discrete_hist t set t.wellid=newWellId where t.wellid=oldWellId;
           commit;
           delete tbl_wellinformation t where t.wellname=v_oldWellName;
           commit;
           p_msg := '新井名存在，修改成功';
        end if;
     elsif newwellcount=0 then
        update tbl_wellinformation t set t.wellname=v_newWellName where t.wellname=v_oldWellName;
        commit;
         p_msg := '新井名不存在，修改成功';
     end if;

  elsif wellcount>0 then
     p_msg := '该井号已存在于其他组织下';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);

Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_change_wellname;
/

CREATE OR REPLACE PROCEDURE prd_reset_sequence (sequencename IN VARCHAR2) as
  curr_val INTEGER;
BEGIN
  EXECUTE IMMEDIATE 'alter sequence ' || sequencename || ' MINVALUE 0';
  EXECUTE IMMEDIATE 'alter sequence ' || sequencename || ' cache 20';
  EXECUTE IMMEDIATE 'SELECT ' || sequencename || '.nextval FROM dual'
    INTO curr_val;
  EXECUTE IMMEDIATE 'alter sequence ' || sequencename || ' increment by -' ||
                    curr_val;
  EXECUTE IMMEDIATE 'SELECT ' || sequencename || '.nextval FROM dual'
    INTO curr_val;
  EXECUTE IMMEDIATE 'alter sequence ' || sequencename || ' increment by 1';
END prd_reset_sequence;
/

CREATE OR REPLACE PROCEDURE prd_clear_data is
begin
--清空所有数据
EXECUTE IMMEDIATE 'truncate table tbl_org';

EXECUTE IMMEDIATE 'truncate table tbl_rpc_productiondata_latest';
EXECUTE IMMEDIATE 'truncate table tbl_rpc_productiondata_hist';
EXECUTE IMMEDIATE 'truncate table tbl_rpc_discrete_latest';
EXECUTE IMMEDIATE 'truncate table tbl_rpc_discrete_hist';
EXECUTE IMMEDIATE 'truncate table tbl_rpc_diagram_latest';
EXECUTE IMMEDIATE 'truncate table tbl_rpc_diagram_hist';
EXECUTE IMMEDIATE 'truncate table tbl_a9rawdata_latest';
EXECUTE IMMEDIATE 'truncate table tbl_a9rawdata_hist';
EXECUTE IMMEDIATE 'truncate table tbl_rpc_motor';
EXECUTE IMMEDIATE 'truncate table tbl_rpcinformation';
EXECUTE IMMEDIATE 'truncate table tbl_rpc_inver_opt';
EXECUTE IMMEDIATE 'truncate table tbl_rpc_total_day';

EXECUTE IMMEDIATE 'truncate table tbl_pcp_productiondata_latest';
EXECUTE IMMEDIATE 'truncate table tbl_pcp_productiondata_hist';
EXECUTE IMMEDIATE 'truncate table tbl_pcp_discrete_latest';
EXECUTE IMMEDIATE 'truncate table tbl_pcp_discrete_hist';
EXECUTE IMMEDIATE 'truncate table tbl_pcp_rpm_latest';
EXECUTE IMMEDIATE 'truncate table tbl_pcp_rpm_hist';
EXECUTE IMMEDIATE 'truncate table tbl_pcp_total_day';

EXECUTE IMMEDIATE 'truncate table tbl_wellinformation';

--重置所有序列
 prd_reset_sequence('seq_org');

 prd_reset_sequence('seq_outputwellproduction_rt');
 prd_reset_sequence('seq_outputwellproduction');
 prd_reset_sequence('seq_discretedata_rt');
 prd_reset_sequence('seq_discretedata');
 prd_reset_sequence('seq_indicatordiagram_rt');
 prd_reset_sequence('seq_indicatordiagram');
 prd_reset_sequence('seq_a9rawdata_hist');
 prd_reset_sequence('seq_a9rawdata_latest');
 prd_reset_sequence('seq_inver_motor');
 prd_reset_sequence('seq_inver_pumpingunit');
 prd_reset_sequence('seq_inver_optimize');
 prd_reset_sequence('seq_outputwellaggregation');

 prd_reset_sequence('seq_pcp_productiondata_latest');
 prd_reset_sequence('seq_pcp_productiondata_hist');
 prd_reset_sequence('seq_pcp_discrete_latest');
 prd_reset_sequence('seq_pcp_discrete_hist');
 prd_reset_sequence('seq_pcp_rpm_latest');
 prd_reset_sequence('seq_pcp_rpm_hist');
 prd_reset_sequence('seq_pcp_total_day');

 prd_reset_sequence('seq_wellinformation');

end prd_clear_data;
/

CREATE OR REPLACE PROCEDURE prd_init_rpc_daily is
begin
    insert into tbl_rpc_total_day (wellid,calculatedate)
    select id, to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') from tbl_wellinformation well
    where well.liftingtype between 200 and 299 and  well.id not in ( select t2.wellid from tbl_rpc_total_day t2 where t2.calculatedate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'));
    commit;

    insert into tbl_pcp_total_day (wellid,calculatedate)
    select id, to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') from tbl_wellinformation well
    where well.liftingtype between 400 and 499 and  well.id not in ( select t2.wellid from tbl_pcp_total_day t2 where t2.calculatedate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'));
    commit;

end prd_init_rpc_daily;
/

CREATE OR REPLACE PROCEDURE prd_save_a9RawData (
       v_DeviceId in varchar2,
       v_AcqTime in varchar2,
       v_Ver in varchar2,
       v_Signal in NUMBER,
       v_TransferIntervel in NUMBER,
       v_Interval_CURVE in tbl_a9rawdata_hist.interval%TYPE,
       v_A_CURVE in tbl_a9rawdata_hist.a%TYPE,
       v_F_CURVE in tbl_a9rawdata_hist.f%TYPE,
       v_Watt_CURVE in tbl_a9rawdata_hist.watt%TYPE,
       v_I_CURVE in tbl_a9rawdata_hist.i%TYPE
       ) as
  p_recordNum     number :=0;
  p_msg varchar2(3000) := 'error';
begin
  select count(id) into p_recordNum from tbl_a9rawdata_hist t
  where t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss')
  and t.deviceid=v_DeviceId;

  if p_recordNum>0 then
      update tbl_a9rawdata_hist t
      set t.signal=v_Signal,t.devicever=v_Ver,
      t.transferintervel=v_TransferIntervel,
      t.interval=v_Interval_CURVE,t.a=v_A_CURVE,t.f=v_F_CURVE,
      t.watt=v_Watt_CURVE,t.i=v_I_CURVE
      where t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss')
      and t.deviceid=v_DeviceId;
      commit;
      p_msg := '修改成功';
  elsif p_recordNum=0 then
      insert into tbl_a9rawdata_hist(
          deviceid,AcqTime,
          signal,devicever,transferintervel,
          interval,a,f,watt,i
      )values(
          v_DeviceId,to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss'),
          v_Signal,v_Ver,v_TransferIntervel,
          v_Interval_CURVE,v_A_CURVE,v_F_CURVE,
          v_Watt_CURVE,v_I_CURVE
      );
      commit;
      p_msg := '添加成功';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);

Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_a9RawData;
/

CREATE OR REPLACE PROCEDURE prd_save_alarmcolor (    backgroundColor0   in varchar2,
                                                        backgroundColor1     in varchar2,
                                                        backgroundColor2    in varchar2,
                                                        backgroundColor3     in varchar2,
                                                        color0   in varchar2,
                                                        color1     in varchar2,
                                                        color2    in varchar2,
                                                        color3     in varchar2,
                                                        opacity0   in varchar2,
                                                        opacity1     in varchar2,
                                                        opacity2    in varchar2,
                                                        opacity3     in varchar2) is
  p_msg varchar2(30) := 'error';
begin
    Update tbl_code t1 set t1.itemname=backgroundColor0 where t1.itemcode='BJYS' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=backgroundColor1 where t1.itemcode='BJYS' and t1.itemvalue=100;
    Update tbl_code t1 set t1.itemname=backgroundColor2 where t1.itemcode='BJYS' and t1.itemvalue=200;
    Update tbl_code t1 set t1.itemname=backgroundColor3 where t1.itemcode='BJYS' and t1.itemvalue=300;

    Update tbl_code t1 set t1.itemname=color0 where t1.itemcode='BJQJYS' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=color1 where t1.itemcode='BJQJYS' and t1.itemvalue=100;
    Update tbl_code t1 set t1.itemname=color2 where t1.itemcode='BJQJYS' and t1.itemvalue=200;
    Update tbl_code t1 set t1.itemname=color3 where t1.itemcode='BJQJYS' and t1.itemvalue=300;

    Update tbl_code t1 set t1.itemname=opacity0 where t1.itemcode='BJYSTMD' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=opacity1 where t1.itemcode='BJYSTMD' and t1.itemvalue=100;
    Update tbl_code t1 set t1.itemname=opacity2 where t1.itemcode='BJYSTMD' and t1.itemvalue=200;
    Update tbl_code t1 set t1.itemname=opacity3 where t1.itemcode='BJYSTMD' and t1.itemvalue=300;
    commit;
    p_msg := '修改成功';

  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_alarmcolor;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_discretedaily (
  v_wellName in varchar2,
  v_Ia in number,v_IaMax in number,v_IaMin in number,
  v_Ib in number,v_IbMax in number,v_IbMin in number,
  v_Ic in number,v_IcMax in number,v_IcMin in number,
  v_Va in number,v_VaMax in number,v_VaMin in number,
  v_Vb in number,v_VbMax in number,v_VbMin in number,
  v_Vc in number,v_VcMax in number,v_VcMin in number,
  v_frequencyrunvalue in number,v_frequencyrunvalueMax in number,v_frequencyrunvalueMin in number,
  v_WattSum in number,v_WattSumMax in number,v_WattSumMin in number,
  v_VarSum in number,v_VarSumMax in number,v_VarSumMin in number,
  v_VASum in number,v_VASumMax in number,v_VASumMin in number,
  v_PFSum in number,v_PFSumMax in number,v_PFSumMin in number,
  v_calDate in varchar2
  ) is
  p_msg varchar2(3000) := 'error';
  p_wellid number:=0;
  p_wellcount number:=0;
  p_totalresultcount number:=0;
  p_totalresultid number:=0;
begin
  select count(*) into p_wellcount from tbl_wellinformation t where t.wellName=v_wellName ;
   if p_wellcount>0 then
     select id into p_wellid from tbl_wellinformation t where t.wellName=v_wellName and rownum=1;
     --查询是否已存在当天计算记录
    select count(*) into p_totalresultcount from tbl_pcp_total_day t
    where t.wellid =p_wellid and t.calculatedate=(to_date(v_calDate,'yyyy-mm-dd')-1);
    --如不存在
    if p_totalresultcount=0 then
      insert into tbl_pcp_total_day (
         calculatedate,wellid,
         ia,iamax,iamin,
         ib,ibmax,ibmin,
         ic,icmax,icmin,
         va,vamax,vamin,
         vb,vbmax,vbmin,
         vc,vcmax,vcmin,
         frequency,frequencymax,frequencymin,
         WattSum,WattSumMax,WattSumMin,
         VarSum,VarSumMax,VarSumMin,
         VASum,VASumMax,VASumMin,
         PFSum,PFSumMax,PFSumMin
      )values(
         to_date(v_calDate,'yyyy-mm-dd')-1,p_wellid,
         v_Ia,v_IaMax,v_IaMin,
         v_Ib,v_IbMax,v_IbMin,
         v_Ic,v_IcMax,v_IcMin,
         v_Va,v_VaMax,v_VaMin,
         v_Vb,v_VbMax,v_VbMin,
         v_Vc,v_VcMax,v_VcMin,
         v_frequencyrunvalue,v_frequencyrunvalueMax,v_frequencyrunvalueMin,
         v_WattSum,v_WattSumMax,v_WattSumMin,
         v_VarSum,v_VarSumMax,v_VarSumMin,
         v_VASum,v_VASumMax,v_VASumMin,
         v_PFSum,v_PFSumMax,v_PFSumMin
      );
      p_msg := '插入成功';
      commit;
    elsif p_totalresultcount>0 then
      select t.id into p_totalresultid from tbl_pcp_total_day t,tbl_wellinformation t007
      where t007.id=t.wellid and t.calculatedate=(to_date(v_calDate,'yyyy-mm-dd')-1) and t007.wellName=v_wellName and rownum=1;
      update tbl_pcp_total_day t set
      t.calculatedate=to_date(v_calDate,'yyyy-mm-dd')-1,
      t.ia=v_Ia,t.iamax=v_IaMax,t.iamin=v_IaMin,
      t.ib=v_Ib,t.ibmax=v_IbMax,t.ibmin=v_IbMin,
      t.ic=v_Ic,t.icmax=v_IcMax,t.icmin=v_IcMin,
      t.va=v_Va,t.vamax=v_VaMax,t.vamin=v_VaMin,
      t.vb=v_Vb,t.vbmax=v_VbMax,t.vbmin=v_VbMin,
      t.vc=v_Vc,t.vcmax=v_VcMax,t.vcmin=v_VcMin,
      t.frequency=v_frequencyrunvalue,t.frequencymax=v_frequencyrunvalueMax,t.frequencymin=v_frequencyrunvalueMin,
      WattSum=v_WattSum,WattSumMax=v_WattSumMax,WattSumMin=v_WattSumMin,
      VarSum=v_VarSum,VarSumMax=v_VarSumMax,VarSumMin=v_VarSumMin,
      VASum=v_VASum,VASumMax=v_VASumMax,VASumMin=v_VASumMin,
      PFSum=v_PFSum,PFSumMax=v_PFSumMax,PFSumMin=v_PFSumMin
      where t.id=p_totalresultid;
      commit;
      p_msg := '更新成功';
    end if;
  elsif p_wellcount=0 then
    p_msg := '井号不存在';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_discretedaily;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_productiondata (v_WellName   in varchar2,
                                                      v_RunTime   in NUMBER,
                                                      v_CrudeOilDensity    in NUMBER,
                                                      v_WaterDensity    in NUMBER,
                                                      v_NaturalGasRelativeDensity    in NUMBER,
                                                      v_SaturationPressure    in NUMBER,
                                                      v_ReservoirDepth    in NUMBER,
                                                      v_ReservoirTemperature    in NUMBER,
                                                      v_TubingPressure   in NUMBER,
                                                      v_CasingPressure   in NUMBER,
                                                      v_WellHeadFluidTemperature   in NUMBER,
                                                      v_WaterCut_W    in NUMBER,
                                                      v_WaterCut    in NUMBER,
                                                      v_ProductionGasOilRatio   in NUMBER,
                                                      v_ProducingfluidLevel    in NUMBER,
                                                      v_PumpSettingDepth    in NUMBER,
                                                      v_BarrelTypeName in varchar2,
                                                      v_PumpTypeName in varchar2,
                                                      v_PumpGrade    in NUMBER,
                                                      v_PumpBoreDiameter   in NUMBER,
                                                      v_PlungerLength in NUMBER,
                                                      v_BarrelLength    in NUMBER,
                                                      v_BarrelSeries    in NUMBER,
                                                      v_RotorDiameter   in NUMBER,
                                                      v_QPR    in NUMBER,
                                                      v_TubingStringInsideDiameter    in NUMBER,
                                                      v_CasingStringInsideDiameter    in NUMBER,
                                                      v_RodString    in varchar2,
                                                      v_AnchoringStateName in varchar2,
                                                      v_NetGrossRatio  in NUMBER,
                                                      v_AcqTime in varchar2,
                                                      v_ids    in varchar2) as
  p_wellcount number :=0;
  p_prodcount  number :=0;
  p_wellid     number :=0;
  p_msg varchar2(3000) := 'error';
  p_sql varchar2(3000);
  p_AnchoringState number :=2;
  p_AnchoringStateCount number :=0;
  p_BarrelType varchar2(20) :='L';
  p_BarrelTypeCount number :=0;
  p_PumpType varchar2(20) :='T';
  p_PumpTypeCount number :=0;
begin
  p_sql:='select count(*)  from tbl_wellinformation t where t.wellName='''||v_WellName||''' and t.orgid in ('||v_ids||')';
  dbms_output.put_line('p_sql:' || p_sql);
  EXECUTE IMMEDIATE p_sql into p_wellcount;
  if p_wellcount>0 then
    --获取井编号
    select t.id into p_wellid from tbl_wellinformation t where t.wellName=v_WellName;
    select count(1) into p_prodcount from tbl_pcp_productiondata_latest t where t.wellid=(select t2.id from tbl_wellinformation t2 where t2.wellName=v_WellName);
    --锚定状态
    select count(1) into p_AnchoringStateCount from tbl_code code5 where code5.itemcode='AnchoringState' and code5.itemname=v_AnchoringStateName;
    if p_AnchoringStateCount>0 then
      select code5.itemvalue into p_AnchoringState from tbl_code code5 where code5.itemcode='AnchoringState' and code5.itemname=v_AnchoringStateName;
    end if;
    --泵筒类型
    select count(1) into p_BarrelTypeCount from tbl_code code where code.itemcode='BarrelType' and code.itemname=v_BarrelTypeName;
    if p_BarrelTypeCount>0 then
      select code.itemvalue into p_BarrelType from tbl_code code where code.itemcode='BarrelType' and code.itemname=v_BarrelTypeName;
    end if;
    --泵类型
    select count(1) into p_PumpTypeCount from tbl_code code where code.itemcode='PumpType' and code.itemname=v_PumpTypeName;
    if p_PumpTypeCount>0 then
      select code.itemvalue into p_PumpType from tbl_code code where code.itemcode='PumpType' and code.itemname=v_PumpTypeName;
    end if;
    --更新数据
    if p_prodcount>0 then
      --开始更新
      update tbl_pcp_productiondata_latest t
      set t.RunTime=v_RunTime,
          t.CrudeOilDensity=v_CrudeOilDensity,t.WaterDensity=v_WaterDensity,t.NaturalGasRelativeDensity=v_NaturalGasRelativeDensity,t.SaturationPressure=v_SaturationPressure,t.ReservoirDepth=v_ReservoirDepth,t.ReservoirTemperature=v_ReservoirTemperature,
          t.TubingPressure=v_TubingPressure,t.CasingPressure=v_CasingPressure,t.WellHeadFluidTemperature=v_WellHeadFluidTemperature,
          t.WaterCut_W=v_WaterCut_W,t.watercut=v_WaterCut,
          t.ProductionGasOilRatio=v_ProductionGasOilRatio,
          t.ProducingfluidLevel=v_ProducingfluidLevel,t.PumpSettingDepth=v_PumpSettingDepth,
          t.barreltype=p_BarrelType,t.pumptype=p_PumpType,
          t.PumpGrade=v_PumpGrade,
          t.PumpBoreDiameter=v_PumpBoreDiameter,t.PlungerLength=v_PlungerLength,
          t.BarrelLength=v_BarrelLength,t.BarrelSeries=v_BarrelSeries,t.RotorDiameter=v_RotorDiameter,t.QPR=v_QPR,
          t.TubingStringInsideDiameter=v_TubingStringInsideDiameter,t.CasingStringInsideDiameter=v_CasingStringInsideDiameter,
          t.rodstring=v_RodString,
          t.AnchoringState=p_AnchoringState,
          t.NetGrossRatio=v_NetGrossRatio,t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss')
      where t.wellid=p_wellid;
      commit;
      p_msg := '修改成功';
    elsif p_prodcount=0 then
      insert into tbl_pcp_productiondata_latest(wellid,AcqTime,runtime,
             crudeoildensity,waterdensity,naturalgasrelativedensity,saturationpressure,reservoirdepth,reservoirtemperature,
             tubingpressure,casingpressure,wellheadfluidtemperature,
             watercut_w,watercut,
             productiongasoilratio,
             producingfluidlevel,pumpsettingdepth,
             barreltype,pumptype,
             pumpgrade,
             pumpborediameter,plungerlength,
             barrellength,barrelseries,rotordiameter,qpr,
             tubingstringinsidediameter,casingstringinsidediameter,
             rodstring,
             anchoringstate,netgrossratio) values (
             p_wellid,to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss'),v_RunTime,
             v_CrudeOilDensity,v_WaterDensity,v_NaturalGasRelativeDensity,v_SaturationPressure,v_ReservoirDepth,v_ReservoirTemperature,
             v_TubingPressure,v_CasingPressure,v_WellHeadFluidTemperature,
             v_WaterCut_W,v_WaterCut,
             v_ProductionGasOilRatio,
             v_ProducingfluidLevel,v_PumpSettingDepth,
             p_BarrelType,p_PumpType,
             v_PumpGrade,
             v_PumpBoreDiameter,v_PlungerLength,
             v_BarrelLength,v_BarrelSeries,v_RotorDiameter,v_QPR,
             v_TubingStringInsideDiameter,v_CasingStringInsideDiameter,
             v_RodString,
             p_AnchoringState,
             v_NetGrossRatio
      );
      commit;
      p_msg := '添加成功';
    end if;
  elsif p_wellcount=0 then
    p_msg := '井号不存在';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_productiondata;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_rpm (
       v_WellName in varchar2,v_AcqTime in varchar2,v_RPM in NUMBER,v_Torque in NUMBER,
       v_ProductionDataId in NUMBER,v_ResultStatus in NUMBER,
       v_ResultCode in NUMBER,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_MotorInputActivePower in NUMBER,v_WaterPower in NUMBER,
       v_SystemEfficiency in NUMBER,v_PowerConsumptionPerTHM in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2) as
  p_recordNum     number :=0;
  p_wellId number;
  p_msg varchar2(3000) := 'error';
begin
  select count(id) into p_recordNum from tbl_pcp_rpm_hist t where t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss') and t.wellid=( select t2.id from tbl_wellinformation t2 where t2.wellname=v_WellName );
  if p_recordNum>0 then
      update tbl_pcp_rpm_hist t
      set t.rpm=v_RPM,t.torque=v_Torque,
          t.productiondataid=v_ProductionDataId,t.resultstatus=v_ResultStatus,
          t.workingconditioncode=v_ResultCode,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_LiquidVolumetricProduction,t.oilvolumetricproduction=v_OilVolumetricProduction,t.watervolumetricproduction=v_WaterVolumetricProduction,
          t.liquidweightproduction=v_LiquidWeightProduction,t.oilweightproduction=v_OilWeightProduction,t.waterweightproduction=v_WaterWeightProduction,
          t.motorinputactivepower=v_MotorInputActivePower,t.waterpower=v_WaterPower,
          t.systemefficiency=v_SystemEfficiency,t.powerconsumptionperthm=v_PowerConsumptionPerTHM,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString
      where t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss') and t.wellid=( select t2.id from tbl_wellinformation t2 where t2.wellname=v_WellName );
      commit;
      p_msg := '修改成功';
  elsif p_recordNum=0 then
      select id into p_wellId from tbl_wellinformation t where t.wellname=v_WellName;
      insert into tbl_pcp_rpm_hist(
          wellId,AcqTime,
          rpm,torque,
          productiondataid,resultstatus,
          workingconditioncode,
          theoreticalproduction,
          liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,
          liquidweightproduction,oilweightproduction,waterweightproduction,
          motorinputactivepower,waterpower,
          systemefficiency,powerconsumptionperthm,
          pumpeff1,pumpeff2,pumpeff,
          pumpintakep,pumpintaket,pumpintakegol,pumpIntakevisl,pumpIntakebo,
          pumpoutletp,pumpoutlett,pumpoutletgol,pumpoutletvisl,pumpoutletbo,
          rodstring
      )values(
          p_wellId,to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss'),
          v_RPM,v_Torque,
          v_ProductionDataId,v_ResultStatus,
          v_ResultCode,
          v_TheoreticalProduction,
          v_LiquidVolumetricProduction,v_OilVolumetricProduction,v_WaterVolumetricProduction,
          v_LiquidWeightProduction,v_OilWeightProduction,v_WaterWeightProduction,
          v_MotorInputActivePower,v_WaterPower,
          v_SystemEfficiency,v_PowerConsumptionPerTHM,
          v_PumpEff1,v_PumpEff2,v_PumpEff,
          v_PumpIntakeP,v_PumpIntakeT,v_PumpIntakeGOL,v_PumpIntakeVisl,v_PumpIntakeBo,
          v_PumpOutletP,v_PumpOutletT,v_PumpOutletGOL,v_PumpOutletVisl,v_PumpOutletBo,
          v_RodString
      );
      commit;
      p_msg := '添加成功';
      update tbl_pcp_rpm_hist t
             set t.discretedataid=
             ( select id from (select id from tbl_pcp_discrete_hist dis where dis.wellid=p_wellId order by dis.AcqTime desc) where rownum=1 )
      where t.wellid=p_wellId and t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
      commit;
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_rpm;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_rpmdaily (
  v_wellName in varchar2,v_ResultStatus in number,
  v_rpm in number,v_rpmmax in number,v_rpmmin in number,
  v_tubingPressure in number,v_tubingPressuremax in number,v_tubingPressuremin in number,
  v_casingPressure in number,v_casingPressuremax in number,v_casingPressuremin in number,
  v_wellHeadFluidTemperature in number,v_wellHeadFluidTemperaturemax in number,v_wellHeadFluidTemperaturemin in number,
  v_productionGasOilRatio in number,v_productionGasOilRatiomax in number,v_productionGasOilRatiomin in number,
  v_TheoreticalProduction in number,v_TheoreticalProductionmax in number,v_TheoreticalProductionmin in number,
  v_liquidVolumetricProduction in number,v_liquidProductionmax_v in number,v_liquidProductionmin_v in number,
  v_oilVolumetricProduction in number,v_oilVolumetricProductionmax in number,v_oilVolumetricProductionmin in number,
  v_waterVolumetricProduction in number,v_waterVolumetricProductionmax in number,v_waterVolumetricProductionmin in number,
  v_waterCut in number,v_waterCutmax in number,v_waterCutmin in number,
  v_liquidWeightProduction in number,v_liquidWeightProductionmax in number,v_liquidWeightProductionmin in number,
  v_oilWeightProduction in number,v_oilWeightProductionmax in number,v_oilWeightProductionmin in number,
  v_waterWeightProduction in number,v_waterWeightProductionmax in number,v_waterWeightProductionmin in number,
  v_waterCut_w in number,v_waterCutmax_w in number,v_waterCutmin_w in number,
  v_pumpEff in number,v_pumpEffmax in number,v_pumpEffmin in number,
  v_pumpEff1 in number,v_pumpEff1max in number,v_pumpEff1min in number,
  v_pumpEff2 in number,v_pumpEff2max in number,v_pumpEff2min in number,
  v_pumpSettingDepth in number,v_pumpSettingDepthmax in number,v_pumpSettingDepthmin in number,
  v_producingfluidLevel in number,v_producingfluidLevelmax in number,v_producingfluidLevelmin in number,
  v_submergence in number,v_submergencemax in number,v_submergencemin in number,
  v_systemEfficiency in number,v_systemEfficiencymax in number,v_systemEfficiencymin in number,
  v_powerConsumptionPerTHM in number,v_powerConsumptionPerTHMmax in number,v_powerConsumptionPerTHMmin in number,
  v_AvgWatt in number,v_AvgWattmax in number,v_AvgWattmin in number,
  v_WaterPower in number,v_WaterPowermax in number,v_WaterPowermin in number,
  v_commStatus in number,v_commTime in number,v_commTimeEfficiency in number,
  v_commRange in tbl_pcp_total_day.commrange%TYPE,
  v_runStatus in number,v_runTime in number,v_runTimeEfficiency in number,
  v_runRange in tbl_pcp_total_day.runrange%TYPE,
  v_calDate in varchar2
  ) is
  p_msg varchar2(3000) := 'error';
  p_wellid number:=0;
  p_wellcount number:=0;
  p_totalresultcount number:=0;
  p_totalresultid number:=0;
begin
  select count(*) into p_wellcount from tbl_wellinformation t where t.wellName=v_wellName ;
   if p_wellcount>0 then
     select id into p_wellid from tbl_wellinformation t where t.wellName=v_wellName and rownum=1;

     --查询是否已存在当天计算记录
    select count(*) into p_totalresultcount from tbl_pcp_total_day t
    where t.wellid =p_wellid and t.calculatedate=(to_date(v_calDate,'yyyy-mm-dd')-1);
    --如不存在
    if p_totalresultcount=0 then
      insert into tbl_pcp_total_day (
         calculatedate,wellid,ResultStatus,
         rpm,rpmmax,rpmmin,
         commstatus,commtime,commtimeefficiency,commrange,
         runstatus,runtime,runtimeefficiency,runrange,
         tubingPressure,tubingPressuremax,tubingPressuremin,casingPressure,casingPressuremax,casingPressuremin,
         wellHeadFluidTemperature,wellHeadFluidTemperaturemax,wellHeadFluidTemperaturemin,
         productionGasOilRatio,productionGasOilRatiomax,productionGasOilRatiomin,
         TheoreticalProduction,TheoreticalProductionmax,TheoreticalProductionmin,
         liquidVolumetricProduction,liquidVolumetricProductionmax,liquidVolumetricProductionmin,
         oilVolumetricProduction,oilVolumetricProductionmax,oilVolumetricProductionmin,
         waterVolumetricProduction,waterVolumetricProductionmax,waterVolumetricProductionmin,
         waterCut,waterCutmax,waterCutmin,
         LiquidWeightProduction,LiquidWeightProductionmax,LiquidWeightProductionmin,
         oilWeightProduction,oilWeightProductionmax,oilWeightProductionmin,
         waterWeightProduction,waterWeightProductionmax,
         waterWeightProductionmin,waterCut_w,waterCutmax_w,waterCutmin_w,
         pumpEff,pumpEffmax,pumpEffmin,
         pumpEff1,pumpEff1max,pumpEff1min,
         pumpEff2,pumpEff2max,pumpEff2min,
         pumpSettingDepth,pumpSettingDepthmax,pumpSettingDepthmin,
         producingfluidLevel,producingfluidLevelmax,producingfluidLevelmin,
         submergence,submergencemax,submergencemin,
         systemEfficiency,systemEfficiencymax,systemEfficiencymin,
         powerConsumptionPerTHM,powerConsumptionPerTHMmax,powerConsumptionPerTHMmin,
         AvgWatt,AvgWattmax,AvgWattmin,
         WaterPower,WaterPowermax,WaterPowermin
      )values(
         to_date(v_calDate,'yyyy-mm-dd')-1,p_wellid,v_ResultStatus,
         v_rpm,v_rpmmax,v_rpmmin,
         v_commStatus,v_commTime,v_commTimeEfficiency,v_commRange,
         v_runStatus,v_runTime,v_runTimeEfficiency,v_runRange,
         v_tubingPressure,v_tubingPressuremax,v_tubingPressuremin,v_casingPressure,v_casingPressuremax,v_casingPressuremin,
         v_wellHeadFluidTemperature,v_wellHeadFluidTemperaturemax,v_wellHeadFluidTemperaturemin,
         v_productionGasOilRatio,v_productionGasOilRatiomax,v_productionGasOilRatiomin,
         v_TheoreticalProduction,v_TheoreticalProductionmax,v_TheoreticalProductionmin,
         v_liquidVolumetricProduction,v_liquidProductionmax_v,v_liquidProductionmin_v,
         v_oilVolumetricProduction,v_oilVolumetricProductionmax,v_oilVolumetricProductionmin,
         v_waterVolumetricProduction,v_waterVolumetricProductionmax,v_waterVolumetricProductionmin,
         v_waterCut,v_waterCutmax,v_waterCutmin,
         v_liquidWeightProduction,v_liquidWeightProductionmax,v_liquidWeightProductionmin,
         v_oilWeightProduction,v_oilWeightProductionmax,v_oilWeightProductionmin,
         v_waterWeightProduction,v_waterWeightProductionmax,v_waterWeightProductionmin,
         v_waterCut_w,v_waterCutmax_w,v_waterCutmin_w,
         v_pumpEff,v_pumpEffmax,v_pumpEffmin,
         v_pumpEff1,v_pumpEff1max,v_pumpEff1min,
         v_pumpEff2,v_pumpEff2max,v_pumpEff2min,
         v_pumpSettingDepth,v_pumpSettingDepthmax,v_pumpSettingDepthmin,
         v_producingfluidLevel,v_producingfluidLevelmax,v_producingfluidLevelmin,
         v_submergence,v_submergencemax,v_submergencemin,
         v_systemEfficiency,v_systemEfficiencymax,v_systemEfficiencymin,
         v_powerConsumptionPerTHM,v_powerConsumptionPerTHMmax,v_powerConsumptionPerTHMmin,
         v_AvgWatt,v_AvgWattmax,v_AvgWattmin,
         v_WaterPower,v_WaterPowermax,v_WaterPowermin
      );
      p_msg := '插入成功';
      commit;
    elsif p_totalresultcount>0 then
      select t.id into p_totalresultid from tbl_pcp_total_day t,tbl_wellinformation t007
      where t007.id=t.wellid and t.calculatedate=(to_date(v_calDate,'yyyy-mm-dd')-1) and t007.wellName=v_wellName and rownum=1;
      update tbl_pcp_total_day t set
      t.calculatedate=to_date(v_calDate,'yyyy-mm-dd')-1,
      t.ResultStatus=v_ResultStatus,
      t.commstatus=v_commStatus,t.commtime=v_commTime,t.commtimeefficiency=v_commTimeEfficiency,t.commrange=v_commRange,
      t.runstatus=v_runStatus,t.runtime=v_runTime,t.runtimeefficiency=v_runTimeEfficiency,t.runrange=v_runRange,
      t.rpm=v_rpm,t.rpmmax=v_rpmmax,t.rpmmin=v_rpmmin,
      t.tubingPressure=v_tubingPressure,t.tubingPressuremax=v_tubingPressuremax,t.tubingPressuremin=v_tubingPressuremin,
      t.casingPressure=v_casingPressure,t.casingPressuremax=v_casingPressuremax,t.casingPressuremin=v_casingPressuremin,
      t.wellHeadFluidTemperature=v_wellHeadFluidTemperature,t.wellHeadFluidTemperaturemax=v_wellHeadFluidTemperaturemax,t.wellHeadFluidTemperaturemin=v_wellHeadFluidTemperaturemin,
      t.productionGasOilRatio=v_productionGasOilRatio,t.productionGasOilRatiomax=v_productionGasOilRatiomax,t.productionGasOilRatiomin=v_productionGasOilRatiomin,
      TheoreticalProduction=v_TheoreticalProduction,TheoreticalProductionmax=v_TheoreticalProductionmax,TheoreticalProductionmin=v_TheoreticalProductionmin,
      t.liquidVolumetricProduction=v_liquidVolumetricProduction,t.liquidVolumetricProductionmax=v_liquidProductionmax_v,t.liquidVolumetricProductionmin=v_liquidProductionmin_v,
      t.oilVolumetricProduction=v_oilVolumetricProduction,t.oilVolumetricProductionmax=v_oilVolumetricProductionmax,t.oilVolumetricProductionmin=v_oilVolumetricProductionmin,
      t.waterVolumetricProduction=v_waterVolumetricProduction,t.waterVolumetricProductionmax=v_waterVolumetricProductionmax,t.waterVolumetricProductionmin=v_waterVolumetricProductionmin,
      t.waterCut=v_waterCut,t.waterCutmax=v_waterCutmax,t.waterCutmin=v_waterCutmin,
      t.liquidWeightProduction=v_liquidWeightProduction,t.liquidWeightProductionmax=v_liquidWeightProductionmax,t.liquidWeightProductionmin=v_liquidWeightProductionmin,
      t.oilWeightProduction=v_oilWeightProduction,t.oilWeightProductionmax=v_oilWeightProductionmax,t.oilWeightProductionmin=v_oilWeightProductionmin,
      t.waterWeightProduction=v_waterWeightProduction,t.waterWeightProductionmax=v_waterWeightProductionmax,t.waterWeightProductionmin=v_waterWeightProductionmin,
      t.waterCut_w=v_waterCut_w,t.waterCutmax_w=v_waterCutmax_w,t.waterCutmin_w=v_waterCutmin_w,
      t.pumpEff=v_pumpEff,t.pumpEffmax=v_pumpEffmax,t.pumpEffmin=v_pumpEffmin,
      t.pumpEff1=v_pumpEff1,t.pumpEff1max=v_pumpEff1max,t.pumpEff1min=v_pumpEff1min,
      t.pumpEff2=v_pumpEff2,t.pumpEff2max=v_pumpEff2max,t.pumpEff2min=v_pumpEff2min,
      t.pumpSettingDepth=v_pumpSettingDepth,t.pumpSettingDepthmax=v_pumpSettingDepthmax,t.pumpSettingDepthmin=v_pumpSettingDepthmin,
      t.producingfluidLevel=v_producingfluidLevel,t.producingfluidLevelmax=v_producingfluidLevelmax,t.producingfluidLevelmin=v_producingfluidLevelmin,
      t.submergence=v_submergence,t.submergencemax=v_submergencemax,t.submergencemin=v_submergencemin,
      t.systemEfficiency=v_systemEfficiency,t.systemEfficiencymax=v_systemEfficiencymax,t.systemEfficiencymin=v_systemEfficiencymin,
      t.powerConsumptionPerTHM=v_powerConsumptionPerTHM,t.powerConsumptionPerTHMmax=v_powerConsumptionPerTHMmax,t.powerConsumptionPerTHMmin=v_powerConsumptionPerTHMmin,
      AvgWatt=v_AvgWatt,AvgWattmax=v_AvgWattmax,AvgWattmin=v_AvgWattmin,
      WaterPower=v_WaterPower,WaterPowermax=v_WaterPowermax,WaterPowermin=v_WaterPowermin
      --t.jsdjrcyldbd=p_jsdjrcyldbd,t.jsdjrcylfbd=p_jsdjrcylfbd,t.jsdjrcyldbdbfb=p_jsdjrcyldbdbfb,t.jsdjrcylfbdbfb=p_jsdjrcylfbdbfb
      where t.id=p_totalresultid;
      commit;
      p_msg := '更新成功';
    end if;
  elsif p_wellcount=0 then
    p_msg := '井号不存在';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_rpmdaily;
/

CREATE OR REPLACE PROCEDURE prd_save_resourcemonitoring (
  v_acqTime in varchar2,
  v_appRunStatus in number,
  v_appVersion in varchar2,
  v_cpuUsedPercent in varchar2,
  v_memUsedPercent in number,
  v_tableSpaceSize in number
  ) is
  p_msg varchar2(3000) := 'error';
begin
  insert into tbl_resourcemonitoring (
         acqtime,apprunstatus,appversion,cpuusedpercent,memusedpercent,tablespacesize
      )values(
         to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
         v_appRunStatus,
         v_appVersion,
         v_cpuUsedPercent,
         v_memUsedPercent,
         v_tableSpaceSize
      );
      p_msg := '插入成功';
      commit;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_resourcemonitoring;
/

CREATE OR REPLACE PROCEDURE prd_save_rpcinformation (v_wellname  in varchar2,
                                              v_Manufacturer in varchar2,
                                              v_Model in varchar2,
                                              v_Stroke in varchar2,
                                              v_CrankRotationDirection in varchar2,
                                              v_OffsetAngleOfCrank in varchar2,
                                              v_CrankGravityRadius in varchar2,
                                              v_SingleCrankWeight in varchar2,
                                              v_StructuralUnbalance in varchar2,
                                              v_BalancePosition in varchar2,
                                              v_BalanceWeight in varchar2,
                                              v_selectedWellName  in varchar2,
                                              v_prtf  in tbl_rpcinformation.prtf%TYPE
                                              ) is
  p_msg varchar2(3000) := 'error';
  p_wellcount number:=0;
  p_wellId number:=0;
  p_recordcount number:=0;
begin
  select count(*) into p_wellcount from tbl_wellinformation t where t.wellname=v_wellname;
  if p_wellcount>0 then
    select id into p_wellId from tbl_wellinformation t where t.wellname=v_wellname;
    select count(*) into p_recordcount from tbl_rpcinformation t where t.wellid=p_wellId;
    if p_recordcount>0 then
       update tbl_rpcinformation t set
           t.manufacturer=v_Manufacturer,
           t.model=v_Model,
           t.stroke=v_Stroke,
           t.crankrotationdirection=v_CrankRotationDirection,
           t.offsetangleofcrank=v_OffsetAngleOfCrank,
           t.crankgravityradius=v_CrankGravityRadius,
           t.singlecrankweight=v_SingleCrankWeight,
           t.structuralunbalance=v_StructuralUnbalance,
           t.balanceposition=v_BalancePosition,
           t.balanceweight=v_BalanceWeight
       where t.wellid=p_wellId;
       commit;
    p_msg := '修改成功';
    elsif p_recordcount=0 then
       insert into tbl_rpcinformation(
             wellid,manufacturer,model,stroke,crankrotationdirection,offsetangleofcrank,
             crankgravityradius,singlecrankweight,structuralunbalance,
             balanceposition,balanceweight
             )
       values(p_wellId,v_Manufacturer,v_Model,v_Stroke,v_CrankRotationDirection,v_OffsetAngleOfCrank,
            v_CrankGravityRadius,v_SingleCrankWeight,v_StructuralUnbalance,
            v_BalancePosition,v_BalanceWeight
            );
       commit;
       p_msg := '添加成功';
    end if;
  end if;
  --更新位置扭矩因数
  update tbl_rpcinformation t set t.prtf=v_prtf  where t.wellid=( select id from tbl_wellinformation where wellname=v_selectedWellName );
  commit;
   p_msg := '更新位置扭矩因数成功';
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpcinformation;
/

CREATE OR REPLACE PROCEDURE prd_save_rpcinformationNoPTF (v_wellname  in varchar2,
                                              v_Manufacturer in varchar2,
                                              v_Model in varchar2,
                                              v_Stroke in varchar2,
                                              v_CrankRotationDirection in varchar2,
                                              v_OffsetAngleOfCrank in varchar2,
                                              v_CrankGravityRadius in varchar2,
                                              v_SingleCrankWeight in varchar2,
                                              v_StructuralUnbalance in varchar2,
                                              v_BalancePosition in varchar2,
                                              v_BalanceWeight in varchar2
                                              ) is
  p_msg varchar2(3000) := 'error';
  p_wellcount number:=0;
  p_wellId number:=0;
  p_recordcount number:=0;
begin
  select count(*) into p_wellcount from tbl_wellinformation t where t.wellname=v_wellname;
  if p_wellcount>0 then
    select id into p_wellId from tbl_wellinformation t where t.wellname=v_wellname;
    select count(*) into p_recordcount from tbl_rpcinformation t where t.wellid=p_wellId;
    if p_recordcount>0 then
       update tbl_rpcinformation t set
           t.manufacturer=v_Manufacturer,
           t.model=v_Model,
           t.stroke=v_Stroke,
           t.crankrotationdirection=v_CrankRotationDirection,
           t.offsetangleofcrank=v_OffsetAngleOfCrank,
           t.crankgravityradius=v_CrankGravityRadius,
           t.singlecrankweight=v_SingleCrankWeight,
           t.structuralunbalance=v_StructuralUnbalance,
           t.balanceposition=v_BalancePosition,
           t.balanceweight=v_BalanceWeight
       where t.wellid=p_wellId;
       commit;
    p_msg := '修改成功';
    elsif p_recordcount=0 then
       insert into tbl_rpcinformation(
             wellid,manufacturer,model,stroke,crankrotationdirection,offsetangleofcrank,
             crankgravityradius,singlecrankweight,structuralunbalance,
             balanceposition,balanceweight
             )
       values(p_wellId,v_Manufacturer,v_Model,v_Stroke,v_CrankRotationDirection,v_OffsetAngleOfCrank,
            v_CrankGravityRadius,v_SingleCrankWeight,v_StructuralUnbalance,
            v_BalancePosition,v_BalanceWeight
            );
       commit;
       p_msg := '添加成功';
    end if;
  end if;
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpcinformationNoPTF;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_diagram (
       v_WellName in varchar2,v_AcqTime in varchar2,v_STROKE in NUMBER,v_SPM in NUMBER,
       v_POSITION_CURVE in tbl_rpc_diagram_hist.POSITION_CURVE%TYPE,v_LOAD_CURVE in tbl_rpc_diagram_hist.LOAD_CURVE%TYPE,

       v_POSITION360_CURVE in tbl_rpc_diagram_hist.position360_curve%TYPE,v_ANGLE360_CURVE in tbl_rpc_diagram_hist.angle360_curve%TYPE,v_LOAD360_CURVE in tbl_rpc_diagram_hist.load360_curve%TYPE,

       v_POWER_CURVE in tbl_rpc_diagram_hist.POWER_CURVE%TYPE,
       v_CURRENT_CURVE in tbl_rpc_diagram_hist.CURRENT_CURVE%TYPE,

       v_RPM_CURVE in tbl_rpc_diagram_hist.RPM_CURVE%TYPE,
       v_RAWPOWER_CURVE in tbl_rpc_diagram_hist.RAWPOWER_CURVE%TYPE,
       v_RAWCURRENT_CURVE in tbl_rpc_diagram_hist.RAWCURRENT_CURVE%TYPE,
       v_RAWRPM_CURVE in tbl_rpc_diagram_hist.RAWRPM_CURVE%TYPE,

       v_Ia_CURVE in tbl_rpc_diagram_hist.ia_curve%TYPE,
       v_Ib_CURVE in tbl_rpc_diagram_hist.ib_curve%TYPE,
       v_Ic_CURVE in tbl_rpc_diagram_hist.ic_curve%TYPE,

       v_DataSource in NUMBER,v_ProductionDataId in NUMBER,v_ResultStatus in NUMBER,v_InverResultStatus in NUMBER,
       v_Fmax in NUMBER,v_Fmin in NUMBER,
       v_UpStrokeIMax in NUMBER,v_DownStrokeIMax in NUMBER,v_UPStrokeWattMax in NUMBER,v_DownStrokeWattMax in NUMBER,v_IDegreeBalance in NUMBER,v_WattDegreeBalance in NUMBER,
       v_DeltaRadius in NUMBER,
       v_ResultCode in NUMBER,
       v_FullnessCoefficient in NUMBER,
       v_NoLiquidFullnessCoefficient in NUMBER,
       v_PlungerStroke in NUMBER,v_AvailablePlungerStroke in NUMBER,v_NoLiquidAvailableStroke  in NUMBER,
       v_UpperLoadLine in NUMBER,v_UpperLoadLineOfExact in NUMBER,v_LowerLoadLine in NUMBER,
       v_SMaxIndex in NUMBER,v_SMinIndex in NUMBER,
       v_PumpFSDiagram in tbl_rpc_diagram_hist.PumpFSDiagram%TYPE,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_AvailablePlungerStrokeProd_V in NUMBER,v_PumpClearanceLeakProd_V in NUMBER,
       v_TVLeakVolumetricProduction in NUMBER,v_SVLeakVolumetricProduction in NUMBER,v_GasInfluenceProd_V in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_AvailablePlungerStrokeProd_W in NUMBER,v_PumpClearanceLeakProd_W in NUMBER,
       v_TVLeakWeightProduction in NUMBER,v_SVLeakWeightProduction in NUMBER,v_GasInfluenceProd_W in NUMBER,

       v_LevelCorrectValue in NUMBER,v_ProducingfluidLevel in NUMBER,

       v_MotorInputActivePower in NUMBER,v_PolishRodPower in NUMBER,v_WaterPower in NUMBER,
       v_SurfaceSystemEfficiency in NUMBER,v_WellDownSystemEfficiency in NUMBER,v_SystemEfficiency in NUMBER,
       v_PowerConsumptionPerTHM in NUMBER,v_FSDiagramArea in NUMBER,
       v_RodFlexLength in NUMBER,v_TubingFlexLength in NUMBER,v_InertiaLength in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff3 in NUMBER,v_PumpEff4 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2,
       v_crankAngle in tbl_rpc_diagram_hist.crankangle%TYPE,
       v_polishRodV in tbl_rpc_diagram_hist.polishrodv%TYPE,
       v_polishRodA in tbl_rpc_diagram_hist.polishroda%TYPE,
       v_PR in tbl_rpc_diagram_hist.pr%TYPE,
       v_TF in tbl_rpc_diagram_hist.tf%TYPE,
       v_loadTorque in tbl_rpc_diagram_hist.loadtorque%TYPE,
       v_crankTorque in tbl_rpc_diagram_hist.cranktorque%TYPE,
       v_currentBalanceTorque in tbl_rpc_diagram_hist.currentbalancetorque%TYPE,
       v_currentNetTorque in tbl_rpc_diagram_hist.currentnettorque%TYPE,
       v_expectedBalanceTorque in tbl_rpc_diagram_hist.expectedbalancetorque%TYPE,
       v_expectedNetTorque in tbl_rpc_diagram_hist.expectednettorque%TYPE,
       v_wellboreSlice in tbl_rpc_diagram_hist.wellboreslice%TYPE,
       v_Signal in NUMBER,v_InterVal in NUMBER,v_DeviceVer in varchar2) as
  p_recordNum     number :=0;
  p_wellId number;
  p_prodDataId number :=0;
  p_prodDataNum number :=0;
  p_msg varchar2(3000) := 'error';
begin
  select count(id) into p_recordNum from tbl_rpc_diagram_hist t where t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss') and t.wellid=( select t2.id from tbl_wellinformation t2 where t2.wellname=v_WellName );
  select count(1) into p_prodDataNum from tbl_rpc_productiondata_hist t where t.wellid=( select t2.id from tbl_wellinformation t2 where t2.wellname=v_WellName );
  if v_ResultStatus=0 and p_prodDataNum>0 then
    select id into p_prodDataId from (select id  from tbl_rpc_productiondata_hist t
    where t.wellid=( select t2.id from tbl_wellinformation t2 where t2.wellname=v_WellName )
    order by t.AcqTime desc) v
    where rownum<=1;
  else
    p_prodDataId := v_ProductionDataId;
  end if;

  if p_recordNum>0 then
      update tbl_rpc_diagram_hist t
      set t.stroke=v_STROKE,t.spm=v_SPM,
          t.position_curve=v_POSITION_CURVE,t.load_curve=v_LOAD_CURVE,
          t.position360_curve=v_POSITION360_CURVE,t.angle360_curve=v_ANGLE360_CURVE,t.load360_curve=v_LOAD360_CURVE,
          t.power_curve=v_POWER_CURVE,t.current_curve=v_CURRENT_CURVE,t.rpm_curve=v_RPM_CURVE,
          t.rawpower_curve=v_RAWPOWER_CURVE,t.rawcurrent_curve=v_RAWCURRENT_CURVE,t.rawrpm_curve=v_RAWRPM_CURVE,
          t.ia_curve=v_Ia_CURVE,t.ib_curve=v_Ib_CURVE,t.ic_curve=v_Ic_CURVE,
          t.datasource=v_DataSource,t.productiondataid=p_prodDataId,t.resultstatus=v_ResultStatus,t.inverresultstatus=v_InverResultStatus,
          t.fmax=v_Fmax,t.fmin=v_Fmin,
          t.upstrokeimax=v_UpStrokeIMax,t.downstrokeimax=v_DownStrokeIMax,t.upstrokewattmax=v_UPStrokeWattMax,t.downstrokewattmax=v_DownStrokeWattMax,t.idegreebalance=v_IDegreeBalance,t.wattdegreebalance=v_WattDegreeBalance,
          t.deltaradius=v_DeltaRadius,
          t.workingconditioncode=v_ResultCode,
          t.fullnesscoefficient=v_FullnessCoefficient,
          t.noliquidfullnesscoefficient=v_NoLiquidFullnessCoefficient,
          t.plungerstroke=v_PlungerStroke,t.availableplungerstroke=v_AvailablePlungerStroke,t.noliquidavailableplungerstroke=v_NoLiquidAvailableStroke,
          t.upperloadline=v_UpperLoadLine,t.upperloadlineofexact=v_UpperLoadLineOfExact,t.lowerloadline=v_LowerLoadLine,
          t.smaxindex=v_SMaxIndex,t.sminindex=v_SMinIndex,
          t.pumpfsdiagram=v_PumpFSDiagram,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_LiquidVolumetricProduction,t.oilvolumetricproduction=v_OilVolumetricProduction,t.watervolumetricproduction=v_WaterVolumetricProduction,
          t.availableplungerstrokeprod_v=v_AvailablePlungerStrokeProd_V,t.pumpclearanceleakprod_v=v_PumpClearanceLeakProd_V,
          t.tvleakvolumetricproduction=v_TVLeakVolumetricProduction,t.svleakvolumetricproduction=v_SVLeakVolumetricProduction,t.gasinfluenceprod_v=v_GasInfluenceProd_V,
          t.liquidweightproduction=v_LiquidWeightProduction,t.oilweightproduction=v_OilWeightProduction,t.waterweightproduction=v_WaterWeightProduction,
          t.availableplungerstrokeprod_w=v_AvailablePlungerStrokeProd_W,t.pumpclearanceleakprod_w=v_PumpClearanceLeakProd_W,
          t.tvleakweightproduction=v_TVLeakWeightProduction,t.svleakweightproduction=v_SVLeakWeightProduction,t.gasinfluenceprod_w=v_GasInfluenceProd_W,
          t.levelcorrectvalue=v_LevelCorrectValue,t.inverproducingfluidlevel=v_ProducingfluidLevel,
          t.motorinputactivepower=v_MotorInputActivePower,t.polishrodpower=v_PolishRodPower,t.waterpower=v_WaterPower,
          t.surfacesystemefficiency=v_SurfaceSystemEfficiency,t.welldownsystemefficiency=v_WellDownSystemEfficiency,t.systemefficiency=v_SystemEfficiency,
          t.powerconsumptionperthm=v_PowerConsumptionPerTHM,t.fsdiagramarea=v_FSDiagramArea,
          t.rodflexlength=v_RodFlexLength,t.tubingflexlength=v_TubingFlexLength,t.inertialength=v_InertiaLength,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff3=v_PumpEff3,t.pumpeff4=v_PumpEff4,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.crankangle=v_crankAngle,t.polishrodv=v_polishRodV,t.polishroda=v_polishRodA,t.pr=v_PR,t.tf=v_TF,
          t.loadtorque=v_loadTorque,t.cranktorque=v_crankTorque,t.currentbalancetorque=v_currentBalanceTorque,t.currentnettorque=v_currentNetTorque,
          t.expectedbalancetorque=v_expectedBalanceTorque,t.expectednettorque=v_expectedNetTorque,
          t.wellboreslice=v_wellboreSlice,
          t.signal=v_Signal,t.interval=v_InterVal,t.devicever=v_DeviceVer
      where t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss') and t.wellid=( select t2.id from tbl_wellinformation t2 where t2.wellname=v_WellName );
      commit;
      p_msg := '修改成功';
  elsif p_recordNum=0 then
      select id into p_wellId from tbl_wellinformation t where t.wellname=v_WellName;
      insert into tbl_rpc_diagram_hist(
          wellId,AcqTime,
          stroke,spm,
          position_curve,load_curve,
          position360_curve,angle360_curve,load360_curve,
          power_curve,current_curve,rpm_curve,
          rawpower_curve,rawcurrent_curve,rawrpm_curve,
          ia_curve,ib_curve,ic_curve,
          datasource,productiondataid,resultstatus,inverresultstatus,
          fmax,fmin,
          upstrokeimax,downstrokeimax,upstrokewattmax,downstrokewattmax,idegreebalance,wattdegreebalance,
          deltaradius,
          workingconditioncode,
          fullnesscoefficient,noliquidfullnesscoefficient,
          plungerstroke,availableplungerstroke,noliquidavailableplungerstroke,
          upperloadline,upperloadlineofexact,lowerloadline,
          smaxindex,sminindex,
          pumpfsdiagram,
          theoreticalproduction,
          liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,
          availableplungerstrokeprod_v,pumpclearanceleakprod_v,
          tvleakvolumetricproduction,svleakvolumetricproduction,gasinfluenceprod_v,
          liquidweightproduction,oilweightproduction,waterweightproduction,
          availableplungerstrokeprod_w,pumpclearanceleakprod_w,
          tvleakweightproduction,svleakweightproduction,gasinfluenceprod_w,
          levelcorrectvalue,inverproducingfluidlevel,
          motorinputactivepower,polishrodpower,waterpower,
          surfacesystemefficiency,welldownsystemefficiency,systemefficiency,
          powerconsumptionperthm,fsdiagramarea,
          rodflexlength,tubingflexlength,inertialength,
          pumpeff1,pumpeff2,pumpeff3,pumpeff4,pumpeff,
          pumpintakep,pumpintaket,pumpintakegol,pumpIntakevisl,pumpIntakebo,
          pumpoutletp,pumpoutlett,pumpoutletgol,pumpoutletvisl,pumpoutletbo,
          rodstring,
          crankangle,polishrodv,polishroda,pr,tf,
          loadtorque,cranktorque,currentbalancetorque,currentnettorque,
          expectedbalancetorque,expectednettorque,
          wellboreslice,
          signal,interval,devicever
      )values(
          p_wellId,to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss'),
          v_STROKE,v_SPM,
          v_POSITION_CURVE,v_LOAD_CURVE,
          v_POSITION360_CURVE,v_ANGLE360_CURVE,v_LOAD360_CURVE,
          v_POWER_CURVE,v_CURRENT_CURVE,v_RPM_CURVE,
          v_RAWPOWER_CURVE,v_RAWCURRENT_CURVE,v_RAWRPM_CURVE,
          v_Ia_CURVE,v_Ib_CURVE,v_Ic_CURVE,
          v_DataSource,p_prodDataId,v_ResultStatus,v_InverResultStatus,
          v_Fmax,v_Fmin,
          v_UpStrokeIMax,v_DownStrokeIMax,v_UPStrokeWattMax,v_DownStrokeWattMax,v_IDegreeBalance,v_WattDegreeBalance,
          v_DeltaRadius,
          v_ResultCode,
          v_FullnessCoefficient,v_NoLiquidFullnessCoefficient,
          v_PlungerStroke,v_AvailablePlungerStroke,v_NoLiquidAvailableStroke,
          v_UpperLoadLine,v_UpperLoadLineOfExact,v_LowerLoadLine,
          v_SMaxIndex,v_SMinIndex,
          v_PumpFSDiagram,
          v_TheoreticalProduction,
          v_LiquidVolumetricProduction,v_OilVolumetricProduction,v_WaterVolumetricProduction,
          v_AvailablePlungerStrokeProd_V,v_PumpClearanceLeakProd_V,
          v_TVLeakVolumetricProduction,v_SVLeakVolumetricProduction,v_GasInfluenceProd_V,
          v_LiquidWeightProduction,v_OilWeightProduction,v_WaterWeightProduction,
          v_AvailablePlungerStrokeProd_W,v_PumpClearanceLeakProd_W,
          v_TVLeakWeightProduction,v_SVLeakWeightProduction,v_GasInfluenceProd_W,
          v_LevelCorrectValue,v_ProducingfluidLevel,
          v_MotorInputActivePower,v_PolishRodPower,v_WaterPower,
          v_SurfaceSystemEfficiency,v_WellDownSystemEfficiency,v_SystemEfficiency,
          v_PowerConsumptionPerTHM,v_FSDiagramArea,
          v_RodFlexLength,v_TubingFlexLength,v_InertiaLength,
          v_PumpEff1,v_PumpEff2,v_PumpEff3,v_PumpEff4,v_PumpEff,
          v_PumpIntakeP,v_PumpIntakeT,v_PumpIntakeGOL,v_PumpIntakeVisl,v_PumpIntakeBo,
          v_PumpOutletP,v_PumpOutletT,v_PumpOutletGOL,v_PumpOutletVisl,v_PumpOutletBo,
          v_RodString,
          v_crankAngle,v_polishRodV,v_polishRodA,v_PR,v_TF,
          v_loadTorque,v_crankTorque,v_currentBalanceTorque,v_currentNetTorque,
          v_expectedBalanceTorque,v_expectedNetTorque,
          v_wellboreSlice,
          v_Signal,v_InterVal,v_DeviceVer
      );
      commit;
      p_msg := '添加成功';
      update tbl_rpc_diagram_hist t
             set t.discretedataid=
             ( select id from (select id from tbl_rpc_discrete_hist dis where dis.wellid=p_wellId order by dis.AcqTime desc) where rownum=1 )
      where t.wellid=p_wellId and t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
      commit;
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_diagram;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_diagramdaily (
  v_wellName in varchar2,v_ResultStatus in number,
  v_workingconditioncode in number,v_workingconditionString in tbl_rpc_total_day.workingconditionstring%TYPE,
  v_ExtendedDays in number,
  v_Stroke in number,v_Strokemax in number,v_Strokemin in number,
  v_SPM in number,v_SPMmax in number,v_SPMmin in number,
  v_UpperLoadLine in number,v_UpperLoadLinemax in number,v_UpperLoadLinemin in number,
  v_LowerLoadLine in number,v_LowerLoadLinemax in number,v_LowerLoadLinemin in number,
  v_UpperLoadLineOfExact in number,v_UpperLoadLineOfExactmax in number,v_UpperLoadLineOfExactmin in number,
  v_DeltaLoadLine in number,v_DeltaLoadLinemax in number,v_DeltaLoadLinemin in number,
  v_DeltaLoadLineOfExact in number,v_DeltaLoadLineOfExactmax in number,v_DeltaLoadLineOfExactmin in number,
  v_FMax_Avg in number,v_FMax_Max in number,v_FMax_Min in number,
  v_FMin_Avg in number,v_FMin_Max in number,v_FMin_Min in number,
  v_DeltaF in number,v_DeltaFmax in number,v_DeltaFmin in number,
  v_Area in number,v_Areamax in number,v_Areamin in number,
  v_PlungerStroke in number,v_PlungerStrokemax in number,v_PlungerStrokemin in number,
  v_AvailablePlungerStroke in number,v_AvailablePlungerStrokemax in number,v_AvailablePlungerStrokemin in number,
  v_NoLiquidAvailableStroke in number,v_NoLiquidAvailableStrokemax in number,v_NoLiquidAvailableStrokemin in number,
  v_fullnessCoefficient in number,v_fullnessCoefficientmax in number,v_fullnessCoefficientmin in number,
  v_NoLiquidFullnessCoeff in number,v_NoLiquidFullnessCoeffmax in number,v_NoLiquidFullnessCoeffmin in number,
  v_tubingPressure in number,v_tubingPressuremax in number,v_tubingPressuremin in number,
  v_casingPressure in number,v_casingPressuremax in number,v_casingPressuremin in number,
  v_wellHeadFluidTemperature in number,v_wellHeadFluidTemperaturemax in number,v_wellHeadFluidTemperaturemin in number,
  v_productionGasOilRatio in number,v_productionGasOilRatiomax in number,v_productionGasOilRatiomin in number,
  v_TheoreticalProduction in number,v_TheoreticalProductionmax in number,v_TheoreticalProductionmin in number,
  v_liquidVolumetricProduction in number,v_liquidProductionmax_v in number,v_liquidProductionmin_v in number,
  v_oilVolumetricProduction in number,v_oilVolumetricProductionmax in number,v_oilVolumetricProductionmin in number,
  v_waterVolumetricProduction in number,v_waterVolumetricProductionmax in number,v_waterVolumetricProductionmin in number,
  v_waterCut in number,v_waterCutmax in number,v_waterCutmin in number,
  v_availablestrokeprod_v in number,v_availablestrokeprod_v_max in number,v_availablestrokeprod_v_min in number,
  v_pumpclearanceleakprod_v in number,v_pumpclearanceleakprod_v_max in number,v_pumpclearanceleakprod_v_min in number,
  v_TVLeakProduction_v in number,v_TVLeakProduction_v_max in number,v_TVLeakProduction_v_min in number,
  v_SVLeakProduction_v in number,v_SVLeakProduction_v_max in number,v_SVLeakProduction_v_min in number,
  v_gasinfluenceprod_v in number,v_gasinfluenceprod_v_max in number,v_gasinfluenceprod_v_min in number,
  v_liquidWeightProduction in number,v_liquidWeightProductionmax in number,v_liquidWeightProductionmin in number,
  v_oilWeightProduction in number,v_oilWeightProductionmax in number,v_oilWeightProductionmin in number,
  v_waterWeightProduction in number,v_waterWeightProductionmax in number,v_waterWeightProductionmin in number,
  v_waterCut_w in number,v_waterCutmax_w in number,v_waterCutmin_w in number,
  v_availablestrokeprod_w in number,v_availablestrokeprod_w_max in number,v_availablestrokeprod_w_min in number,
  v_pumpclearanceleakprod_w in number,v_pumpclearanceleakprod_w_max in number,v_pumpclearanceleakprod_w_min in number,
  v_TVLeakProduction_w in number,v_TVLeakProduction_w_max in number,v_TVLeakProduction_w_min in number,
  v_SVLeakProduction_w in number,v_SVLeakProduction_w_max in number,v_SVLeakProduction_w_min in number,
  v_gasinfluenceprod_w in number,v_gasinfluenceprod_w_max in number,v_gasinfluenceprod_w_min in number,
  v_pumpEff in number,v_pumpEffmax in number,v_pumpEffmin in number,
  v_pumpEff1 in number,v_pumpEff1max in number,v_pumpEff1min in number,
  v_pumpEff2 in number,v_pumpEff2max in number,v_pumpEff2min in number,
  v_pumpEff3 in number,v_pumpEff3max in number,v_pumpEff3min in number,
  v_pumpEff4 in number,v_pumpEff4max in number,v_pumpEff4min in number,
  v_RodFlexLength in number,v_RodFlexLengthmax in number,v_RodFlexLengthmin in number,
  v_TubingFlexLength in number,v_TubingFlexLengthmax in number,v_TubingFlexLengthmin in number,
  v_InertiaLength in number,v_InertiaLengthmax in number,v_InertiaLengthmin in number,
  v_pumpBoreDiameter in number,v_pumpBoreDiametermax in number,v_pumpBoreDiametermin in number,
  v_pumpSettingDepth in number,v_pumpSettingDepthmax in number,v_pumpSettingDepthmin in number,
  v_producingfluidLevel in number,v_producingfluidLevelmax in number,v_producingfluidLevelmin in number,
  v_submergence in number,v_submergencemax in number,v_submergencemin in number,
  v_LevelCorrectValue in number,v_LevelCorrectValuemax in number,v_LevelCorrectValuemin in number,
  v_PumpIntakeP in number,v_PumpIntakePmax in number,v_PumpIntakePmin in number,
  v_PumpIntakeT in number,v_PumpIntakeTmax in number,v_PumpIntakeTmin in number,
  v_PumpIntakeGOL in number,v_PumpIntakeGOLmax in number,v_PumpIntakeGOLmin in number,
  v_PumpIntakeVisl in number,v_PumpIntakeVislmax in number,v_PumpIntakeVislmin in number,
  v_PumpIntakeBo in number,v_PumpIntakeBomax in number,v_PumpIntakeBomin in number,
  v_PumpOutletP in number,v_PumpOutletPmax in number,v_PumpOutletPmin in number,
  v_PumpOutletT in number,v_PumpOutletTmax in number,v_PumpOutletTmin in number,
  v_PumpOutletGOL in number,v_PumpOutletGOLmax in number,v_PumpOutletGOLmin in number,
  v_PumpOutletVisl in number,v_PumpOutletVislmax in number,v_PumpOutletVislmin in number,
  v_PumpOutletBo in number,v_PumpOutletBomax in number,v_PumpOutletBomin in number,
  v_wellDownSystemEfficiency in number,v_wellDownSystemEfficiencymax in number,v_wellDownSystemEfficiencymin in number,
  v_surfaceSystemEfficiency in number,v_surfaceSystemEfficiencymax in number,v_surfaceSystemEfficiencymin in number,
  v_systemEfficiency in number,v_systemEfficiencymax in number,v_systemEfficiencymin in number,
  v_powerConsumptionPerTHM in number,v_powerConsumptionPerTHMmax in number,v_powerConsumptionPerTHMmin in number,
  v_AvgWatt in number,v_AvgWattmax in number,v_AvgWattmin in number,
  v_PolishRodPower in number,v_PolishRodPowermax in number,v_PolishRodPowermin in number,
  v_WaterPower in number,v_WaterPowermax in number,v_WaterPowermin in number,
  v_iDegreeBalance in number,v_iDegreeBalancemax in number,v_iDegreeBalancemin in number,
  v_UpStrokeIMax_Avg in number,v_UpStrokeIMax_Max in number,v_UpStrokeIMax_Min in number,
  v_DownStrokeIMax_Avg in number,v_DownStrokeIMax_Max in number,v_DownStrokeIMax_Min in number,
  v_wattDegreeBalance in number,v_wattDegreeBalancemax in number,v_wattDegreeBalancemin in number,
  v_UpStrokeWattMax_Avg in number,v_UpStrokeWattMax_Max in number,v_UpStrokeWattMax_Min in number,
  v_DownStrokeWattMax_Avg in number,v_DownStrokeWattMax_Max in number,v_DownStrokeWattMax_Min in number,
  v_DeltaRadius in number,v_DeltaRadiusMax in number,v_DeltaRadiusMin in number,
  v_commStatus in number,v_commTime in number,v_commTimeEfficiency in number,
  v_commRange in tbl_rpc_total_day.commrange%TYPE,
  v_runStatus in number,v_runTime in number,v_runTimeEfficiency in number,
  v_runRange tbl_rpc_total_day.runrange%TYPE,
  v_calDate in varchar2
  ) is
  p_msg varchar2(3000) := 'error';
  p_wellid number:=0;
  p_wellcount number:=0;
  p_totalresultcount number:=0;
  p_totalresultid number:=0;
begin
  select count(*) into p_wellcount from tbl_wellinformation t where t.wellName=v_wellName ;
   if p_wellcount>0 then
     select id into p_wellid from tbl_wellinformation t where t.wellName=v_wellName and rownum=1;
     --查询是否已存在当天计算记录
    select count(*) into p_totalresultcount from tbl_rpc_total_day t
    where t.wellid =p_wellid and t.calculatedate=(to_date(v_calDate,'yyyy-mm-dd')-1);
    --如不存在
    if p_totalresultcount=0 then
      insert into tbl_rpc_total_day (
         calculatedate,wellid,ResultStatus,
         workingconditioncode,workingconditionString,ExtendedDays,
         commstatus,commtime,commtimeefficiency,commrange,
         runstatus,runtime,runtimeefficiency,runrange,
         Stroke,Strokemax,Strokemin,SPM,SPMmax,SPMmin,
         upperloadline,upperloadlinemax,upperloadlinemin,
         lowerloadline,lowerloadlinemax,lowerloadlinemin,
         upperloadlineofexact,upperloadlineofexactmax,upperloadlineofexactmin,
         deltaloadline,deltaloadlinemax,deltaloadlinemin,
         deltaloadlineofexact,deltaloadlineofexactmax,deltaloadlineofexactmin,
         fmax_avg,fmax_max,fmax_min,
         fmin_avg,fmin_max,fmin_min,
         deltaf,deltafmax,deltafmin,
         area,areamax,areamin,
         plungerstroke,plungerstrokemax,plungerstrokemin,
         availableplungerstroke,availableplungerstrokemax,availableplungerstrokemin,
         noliquidavailablestroke,noliquidavailablestrokemax,noliquidavailablestrokemin,
         fullnessCoefficient,fullnessCoefficientmax,fullnessCoefficientmin,
         noliquidfullnesscoefficient,noliquidfullnesscoefficientmax,noliquidfullnesscoefficientmin,
         tubingPressure,tubingPressuremax,tubingPressuremin,casingPressure,casingPressuremax,casingPressuremin,
         wellHeadFluidTemperature,wellHeadFluidTemperaturemax,wellHeadFluidTemperaturemin,
         productionGasOilRatio,productionGasOilRatiomax,productionGasOilRatiomin,
         theoreticalproduction,theoreticalproductionmax,theoreticalproductionmin,
         liquidVolumetricProduction,liquidVolumetricProductionmax,liquidVolumetricProductionmin,
         oilVolumetricProduction,oilVolumetricProductionmax,oilVolumetricProductionmin,
         waterVolumetricProduction,waterVolumetricProductionmax,waterVolumetricProductionmin,
         waterCut,waterCutmax,waterCutmin,
         availablestrokeprod_v,availablestrokeprod_v_max,availablestrokeprod_v_min,
         pumpclearanceleakprod_v,pumpclearanceleakprod_v_max,pumpclearanceleakprod_v_min,
         tvleakvolumetricproduction,tvleakvolumetricproductionmax,tvleakvolumetricproductionmin,
         svleakvolumetricproduction,svleakvolumetricproductionmax,svleakvolumetricproductionmin,
         gasinfluenceprod_v,gasinfluenceprod_v_max,gasinfluenceprod_v_min,
         LiquidWeightProduction,LiquidWeightProductionmax,LiquidWeightProductionmin,
         oilWeightProduction,oilWeightProductionmax,oilWeightProductionmin,waterWeightProduction,waterWeightProductionmax,waterWeightProductionmin,
         waterCut_w,waterCutmax_w,waterCutmin_w,
         availablestrokeprod_w,availablestrokeprod_w_max,availablestrokeprod_w_min,
         pumpclearanceleakprod_w,pumpclearanceleakprod_w_max,pumpclearanceleakprod_w_min,
         tvleakweightproduction,tvleakweightproductionmax,tvleakweightproductionmin,
         svleakweightproduction,svleakweightproductionmax,svleakweightproductionmin,
         gasinfluenceprod_w,gasinfluenceprod_w_max,gasinfluenceprod_w_min,
         pumpEff,pumpEffmax,pumpEffmin,
         pumpEff1,pumpEff1max,pumpEff1min,
         pumpEff2,pumpEff2max,pumpEff2min,
         pumpEff3,pumpEff3max,pumpEff3min,
         pumpEff4,pumpEff4max,pumpEff4min,
         rodflexlength,rodflexlengthmax,rodflexlengthmin,
         tubingflexlength,tubingflexlengthmax,tubingflexlengthmin,
         inertialength,inertialengthmax,inertialengthmin,
         pumpBoreDiameter,pumpBoreDiametermax,pumpBoreDiametermin,
         pumpSettingDepth,pumpSettingDepthmax,pumpSettingDepthmin,
         producingfluidLevel,producingfluidLevelmax,producingfluidLevelmin,
         submergence,submergencemax,submergencemin,
         levelcorrectvalue,levelcorrectvaluemax,levelcorrectvaluemin,
         PumpIntakeP,PumpIntakePmax,PumpIntakePmin,
         PumpIntakeT,PumpIntakeTmax,PumpIntakeTmin,
         PumpIntakeGOL,PumpIntakeGOLmax,PumpIntakeGOLmin,
         PumpIntakeVisl,PumpIntakeVislmax,PumpIntakeVislmin,
         PumpIntakeBo,PumpIntakeBomax,PumpIntakeBomin,
         PumpOutletP,PumpOutletPmax,PumpOutletPmin,
         PumpOutletT,PumpOutletTmax,PumpOutletTmin,
         PumpOutletGOL,PumpOutletGOLmax,PumpOutletGOLmin,
         PumpOutletVisl,PumpOutletVislmax,PumpOutletVislmin,
         PumpOutletBo,PumpOutletBomax,PumpOutletBomin,
         wellDownSystemEfficiency,wellDownSystemEfficiencymax,wellDownSystemEfficiencymin,
         surfaceSystemEfficiency,surfaceSystemEfficiencymax,surfaceSystemEfficiencymin,
         systemEfficiency,systemEfficiencymax,systemEfficiencymin,
         powerConsumptionPerTHM,powerConsumptionPerTHMmax,powerConsumptionPerTHMmin,
         AvgWatt,AvgWattmax,AvgWattmin,
         PolishRodPower,PolishRodPowermax,PolishRodPowermin,
         WaterPower,WaterPowermax,WaterPowermin,
         iDegreeBalance,iDegreeBalancemax,iDegreeBalancemin,
         UpStrokeIMax_Avg,UpStrokeIMax_Max,UpStrokeIMax_Min,
         DownStrokeIMax_Avg,DownStrokeIMax_Max,DownStrokeIMax_Min,
         wattDegreeBalance,wattDegreeBalancemax,wattDegreeBalancemin,
         UpStrokeWattMax_Avg,UpStrokeWattMax_Max,UpStrokeWattMax_Min,
         DownStrokeWattMax_Avg,DownStrokeWattMax_Max,DownStrokeWattMax_Min,
         deltaradius,deltaradiusmax,deltaradiusmin
      )values(
         to_date(v_calDate,'yyyy-mm-dd')-1,p_wellid,v_ResultStatus,
         v_workingconditioncode,v_workingconditionString, v_ExtendedDays,
         v_commStatus,v_commTime,v_commTimeEfficiency,v_commRange,
         v_runStatus,v_runTime,v_runTimeEfficiency,v_runRange,
         v_Stroke ,v_Strokemax ,v_Strokemin ,v_SPM ,v_SPMmax ,v_SPMmin ,
         v_UpperLoadLine,v_UpperLoadLinemax ,v_UpperLoadLinemin ,
         v_LowerLoadLine ,v_LowerLoadLinemax ,v_LowerLoadLinemin ,
         v_UpperLoadLineOfExact ,v_UpperLoadLineOfExactmax ,v_UpperLoadLineOfExactmin ,
         v_DeltaLoadLine ,v_DeltaLoadLinemax ,v_DeltaLoadLinemin ,
         v_DeltaLoadLineOfExact ,v_DeltaLoadLineOfExactmax ,v_DeltaLoadLineOfExactmin ,
         v_FMax_Avg ,v_FMax_Max ,v_FMax_Min ,
         v_FMin_Avg ,v_FMin_Max ,v_FMin_Min ,
         v_DeltaF ,v_DeltaFmax ,v_DeltaFmin ,
         v_Area ,v_Areamax ,v_Areamin ,
         v_PlungerStroke ,v_PlungerStrokemax ,v_PlungerStrokemin ,
         v_AvailablePlungerStroke ,v_AvailablePlungerStrokemax ,v_AvailablePlungerStrokemin ,
         v_NoLiquidAvailableStroke,v_NoLiquidAvailableStrokemax,v_NoLiquidAvailableStrokemin,
         v_fullnessCoefficient ,v_fullnessCoefficientmax ,v_fullnessCoefficientmin ,
         v_NoLiquidFullnessCoeff,v_NoLiquidFullnessCoeffmax,v_NoLiquidFullnessCoeffmin,
         v_tubingPressure,v_tubingPressuremax,v_tubingPressuremin,v_casingPressure,v_casingPressuremax,v_casingPressuremin,
         v_wellHeadFluidTemperature,v_wellHeadFluidTemperaturemax,v_wellHeadFluidTemperaturemin,
         v_productionGasOilRatio,v_productionGasOilRatiomax,v_productionGasOilRatiomin,
         v_TheoreticalProduction,v_TheoreticalProductionmax,v_TheoreticalProductionmin,
         v_liquidVolumetricProduction,v_liquidProductionmax_v,v_liquidProductionmin_v,
         v_oilVolumetricProduction,v_oilVolumetricProductionmax,v_oilVolumetricProductionmin,
         v_waterVolumetricProduction,v_waterVolumetricProductionmax,v_waterVolumetricProductionmin,
         v_waterCut,v_waterCutmax,v_waterCutmin,
         v_availablestrokeprod_v ,v_availablestrokeprod_v_max ,v_availablestrokeprod_v_min ,
         v_pumpclearanceleakprod_v ,v_pumpclearanceleakprod_v_max ,v_pumpclearanceleakprod_v_min ,
         v_TVLeakProduction_v ,v_TVLeakProduction_v_max ,v_TVLeakProduction_v_min ,
         v_SVLeakProduction_v ,v_SVLeakProduction_v_max ,v_SVLeakProduction_v_min ,
         v_gasinfluenceprod_v ,v_gasinfluenceprod_v_max ,v_gasinfluenceprod_v_min ,
         v_liquidWeightProduction,v_liquidWeightProductionmax,v_liquidWeightProductionmin,
         v_oilWeightProduction,v_oilWeightProductionmax,v_oilWeightProductionmin,
         v_waterWeightProduction,v_waterWeightProductionmax,v_waterWeightProductionmin,
         v_waterCut_w,v_waterCutmax_w,v_waterCutmin_w,
         v_availablestrokeprod_w ,v_availablestrokeprod_w_max ,v_availablestrokeprod_w_min ,
         v_pumpclearanceleakprod_w ,v_pumpclearanceleakprod_w_max ,v_pumpclearanceleakprod_w_min ,
         v_TVLeakProduction_w ,v_TVLeakProduction_w_max ,v_TVLeakProduction_w_min ,
         v_SVLeakProduction_w ,v_SVLeakProduction_w_max ,v_SVLeakProduction_w_min ,
         v_gasinfluenceprod_w ,v_gasinfluenceprod_w_max ,v_gasinfluenceprod_w_min ,
         v_pumpEff,v_pumpEffmax,v_pumpEffmin,
         v_pumpEff1,v_pumpEff1max,v_pumpEff1min,
         v_pumpEff2,v_pumpEff2max,v_pumpEff2min,
         v_pumpEff3,v_pumpEff3max,v_pumpEff3min,
         v_pumpEff4,v_pumpEff4max,v_pumpEff4min,
         v_RodFlexLength,v_RodFlexLengthmax,v_RodFlexLengthmin,
         v_TubingFlexLength,v_TubingFlexLengthmax,v_TubingFlexLengthmin,
         v_InertiaLength,v_InertiaLengthmax,v_InertiaLengthmin,
         v_pumpBoreDiameter,v_pumpBoreDiametermax,v_pumpBoreDiametermin,
         v_pumpSettingDepth,v_pumpSettingDepthmax,v_pumpSettingDepthmin,
         v_producingfluidLevel,v_producingfluidLevelmax,v_producingfluidLevelmin,
         v_submergence,v_submergencemax,v_submergencemin,
         v_LevelCorrectValue,v_LevelCorrectValuemax,v_LevelCorrectValuemin,
         v_PumpIntakeP,v_PumpIntakePmax,v_PumpIntakePmin,
         v_PumpIntakeT,v_PumpIntakeTmax,v_PumpIntakeTmin,
         v_PumpIntakeGOL,v_PumpIntakeGOLmax,v_PumpIntakeGOLmin,
         v_PumpIntakeVisl,v_PumpIntakeVislmax,v_PumpIntakeVislmin,
         v_PumpIntakeBo,v_PumpIntakeBomax,v_PumpIntakeBomin,
         v_PumpOutletP,v_PumpOutletPmax,v_PumpOutletPmin,
         v_PumpOutletT,v_PumpOutletTmax,v_PumpOutletTmin,
         v_PumpOutletGOL,v_PumpOutletGOLmax,v_PumpOutletGOLmin,
         v_PumpOutletVisl,v_PumpOutletVislmax,v_PumpOutletVislmin,
         v_PumpOutletBo,v_PumpOutletBomax,v_PumpOutletBomin,
         v_wellDownSystemEfficiency,v_wellDownSystemEfficiencymax,v_wellDownSystemEfficiencymin,
         v_surfaceSystemEfficiency,v_surfaceSystemEfficiencymax,v_surfaceSystemEfficiencymin,
         v_systemEfficiency,v_systemEfficiencymax,v_systemEfficiencymin,
         v_powerConsumptionPerTHM,v_powerConsumptionPerTHMmax,v_powerConsumptionPerTHMmin,
         v_AvgWatt,v_AvgWattmax,v_AvgWattmin,
         v_PolishRodPower,v_PolishRodPowermax,v_PolishRodPowermin,
         v_WaterPower,v_WaterPowermax,v_WaterPowermin,
         v_iDegreeBalance,v_iDegreeBalancemax,v_iDegreeBalancemin,
         v_UpStrokeIMax_Avg,v_UpStrokeIMax_Max,v_UpStrokeIMax_Min,
         v_DownStrokeIMax_Avg,v_DownStrokeIMax_Max,v_DownStrokeIMax_Min,
         v_wattDegreeBalance,v_wattDegreeBalancemax,v_wattDegreeBalancemin,
         v_UpStrokeWattMax_Avg,v_UpStrokeWattMax_Max,v_UpStrokeWattMax_Min,
         v_DownStrokeWattMax_Avg,v_DownStrokeWattMax_Max,v_DownStrokeWattMax_Min,
         v_DeltaRadius,v_DeltaRadiusMax,v_DeltaRadiusMin
      );
      p_msg := '插入成功';
      commit;
    elsif p_totalresultcount>0 then
      select t.id into p_totalresultid from tbl_rpc_total_day t,tbl_wellinformation t007
      where t007.id=t.wellid and t.calculatedate=(to_date(v_calDate,'yyyy-mm-dd')-1) and t007.wellName=v_wellName and rownum=1;
      update tbl_rpc_total_day t set
      t.calculatedate=to_date(v_calDate,'yyyy-mm-dd')-1,
      t.ResultStatus=v_ResultStatus,
      t.workingconditioncode=v_workingconditioncode,t.workingconditionString=v_workingconditionString, t.ExtendedDays=v_ExtendedDays,
      t.commstatus=v_commStatus,t.commtime=v_commTime,t.commtimeefficiency=v_commTimeEfficiency,t.commrange=v_commRange,
      t.runstatus=v_runStatus,t.runtime=v_runTime,t.runtimeefficiency=v_runTimeEfficiency,t.runrange=v_runRange,
      t.Stroke=v_Stroke,t.Strokemax=v_Strokemax,t.Strokemin=v_Strokemin,
      t.SPM=v_SPM,t.SPMmax=v_SPMmax,t.SPMmin=v_SPMmin,
      UpperLoadLine=v_UpperLoadLine,UpperLoadLinemax=v_UpperLoadLinemax,UpperLoadLinemin=v_UpperLoadLinemin,
      LowerLoadLine=v_LowerLoadLine,LowerLoadLinemax=v_LowerLoadLinemax,LowerLoadLinemin=v_LowerLoadLinemin,
      UpperLoadLineOfExact=v_UpperLoadLineOfExact,UpperLoadLineOfExactmax=v_UpperLoadLineOfExactmax,UpperLoadLineOfExactmin=v_UpperLoadLineOfExactmin,
      DeltaLoadLine=v_DeltaLoadLine,DeltaLoadLinemax=v_DeltaLoadLinemax,DeltaLoadLinemin=v_DeltaLoadLinemin,
      DeltaLoadLineOfExact=v_DeltaLoadLineOfExact,DeltaLoadLineOfExactmax=v_DeltaLoadLineOfExactmax,DeltaLoadLineOfExactmin=v_DeltaLoadLineOfExactmin,
      FMax_Avg=v_FMax_Avg,FMax_Max=v_FMax_Max,FMax_Min=v_FMax_Min,
      FMin_Avg=v_FMin_Avg,FMin_Max=v_FMin_Max,FMin_Min=v_FMin_Min,
      DeltaF=v_DeltaF,DeltaFmax=v_DeltaFmax,DeltaFmin=v_DeltaFmin,
      Area=v_Area,Areamax=v_Areamax,Areamin=v_Areamin,
      PlungerStroke=v_PlungerStroke,PlungerStrokemax=v_PlungerStrokemax,PlungerStrokemin=v_PlungerStrokemin,
      AvailablePlungerStroke=v_AvailablePlungerStroke,AvailablePlungerStrokemax=v_AvailablePlungerStrokemax,AvailablePlungerStrokemin=v_AvailablePlungerStrokemin,
      t.noliquidavailablestroke=v_NoLiquidAvailableStroke,t.noliquidavailablestrokemax=v_NoLiquidAvailableStrokemax,t.noliquidavailablestrokemin=v_NoLiquidAvailableStrokemin,
      t.fullnessCoefficient=v_fullnessCoefficient,t.fullnessCoefficientmax=v_fullnessCoefficientmax,t.fullnessCoefficientmin=v_fullnessCoefficientmin,
      t.noliquidfullnesscoefficient=v_NoLiquidFullnessCoeff,t.noliquidfullnesscoefficientmax=v_NoLiquidFullnessCoeffmax,t.noliquidfullnesscoefficientmin=v_NoLiquidFullnessCoeffmin,
      t.tubingPressure=v_tubingPressure,t.tubingPressuremax=v_tubingPressuremax,t.tubingPressuremin=v_tubingPressuremin,
      t.casingPressure=v_casingPressure,t.casingPressuremax=v_casingPressuremax,t.casingPressuremin=v_casingPressuremin,
      t.wellHeadFluidTemperature=v_wellHeadFluidTemperature,t.wellHeadFluidTemperaturemax=v_wellHeadFluidTemperaturemax,t.wellHeadFluidTemperaturemin=v_wellHeadFluidTemperaturemin,
      t.productionGasOilRatio=v_productionGasOilRatio,t.productionGasOilRatiomax=v_productionGasOilRatiomax,t.productionGasOilRatiomin=v_productionGasOilRatiomin,
      TheoreticalProduction=v_TheoreticalProduction,TheoreticalProductionmax=v_TheoreticalProductionmax,TheoreticalProductionmin=v_TheoreticalProductionmin,
      t.liquidVolumetricProduction=v_liquidVolumetricProduction,t.liquidVolumetricProductionmax=v_liquidProductionmax_v,t.liquidVolumetricProductionmin=v_liquidProductionmin_v,
      t.oilVolumetricProduction=v_oilVolumetricProduction,t.oilVolumetricProductionmax=v_oilVolumetricProductionmax,t.oilVolumetricProductionmin=v_oilVolumetricProductionmin,
      t.waterVolumetricProduction=v_waterVolumetricProduction,t.waterVolumetricProductionmax=v_waterVolumetricProductionmax,t.waterVolumetricProductionmin=v_waterVolumetricProductionmin,
      t.waterCut=v_waterCut,t.waterCutmax=v_waterCutmax,t.waterCutmin=v_waterCutmin,
      availablestrokeprod_v=v_availablestrokeprod_v ,availablestrokeprod_v_max=v_availablestrokeprod_v_max ,availablestrokeprod_v_min=v_availablestrokeprod_v_min ,
      pumpclearanceleakprod_v=v_pumpclearanceleakprod_v ,pumpclearanceleakprod_v_max=v_pumpclearanceleakprod_v_max ,pumpclearanceleakprod_v_min=v_pumpclearanceleakprod_v_min ,
      tvleakvolumetricproduction=v_TVLeakProduction_v ,tvleakvolumetricproductionmax=v_TVLeakProduction_v_max ,tvleakvolumetricproductionmin=v_TVLeakProduction_v_min ,
      svleakvolumetricproduction=v_SVLeakProduction_v ,svleakvolumetricproductionmax=v_SVLeakProduction_v_max ,svleakvolumetricproductionmin=v_SVLeakProduction_v_min ,
      gasinfluenceprod_v=v_gasinfluenceprod_v ,gasinfluenceprod_v_max=v_gasinfluenceprod_v_max ,gasinfluenceprod_v_min=v_gasinfluenceprod_v_min ,
      t.liquidWeightProduction=v_liquidWeightProduction,t.liquidWeightProductionmax=v_liquidWeightProductionmax,t.liquidWeightProductionmin=v_liquidWeightProductionmin,
      t.oilWeightProduction=v_oilWeightProduction,t.oilWeightProductionmax=v_oilWeightProductionmax,t.oilWeightProductionmin=v_oilWeightProductionmin,
      t.waterWeightProduction=v_waterWeightProduction,t.waterWeightProductionmax=v_waterWeightProductionmax,t.waterWeightProductionmin=v_waterWeightProductionmin,
      t.waterCut_w=v_waterCut_w,t.waterCutmax_w=v_waterCutmax_w,t.waterCutmin_w=v_waterCutmin_w,
      availablestrokeprod_w=v_availablestrokeprod_w ,availablestrokeprod_w_max=v_availablestrokeprod_w_max ,availablestrokeprod_w_min=v_availablestrokeprod_w_min ,
      pumpclearanceleakprod_w=v_pumpclearanceleakprod_w ,pumpclearanceleakprod_w_max=v_pumpclearanceleakprod_w_max ,pumpclearanceleakprod_w_min=v_pumpclearanceleakprod_w_min ,
      tvleakweightproduction=v_TVLeakProduction_v ,tvleakweightproductionmax=v_TVLeakProduction_v_max ,tvleakweightproductionmin=v_TVLeakProduction_v_min ,
      svleakweightproduction=v_SVLeakProduction_v ,svleakweightproductionmax=v_SVLeakProduction_v_max ,svleakweightproductionmin=v_SVLeakProduction_v_min ,
      gasinfluenceprod_w=v_gasinfluenceprod_w ,gasinfluenceprod_w_max=v_gasinfluenceprod_w_max ,gasinfluenceprod_w_min=v_gasinfluenceprod_w_min ,
      t.pumpEff=v_pumpEff,t.pumpEffmax=v_pumpEffmax,t.pumpEffmin=v_pumpEffmin,
      pumpEff1=v_pumpEff1,pumpEff1max=v_pumpEff1max,pumpEff1min=v_pumpEff1min,
      pumpEff2=v_pumpEff2,pumpEff2max=v_pumpEff2max,pumpEff2min=v_pumpEff2min,
      pumpEff3=v_pumpEff3,pumpEff3max=v_pumpEff3max,pumpEff3min=v_pumpEff3min,
      pumpEff4=v_pumpEff4,pumpEff4max=v_pumpEff4max,pumpEff4min=v_pumpEff4min,
      RodFlexLength=v_RodFlexLength,RodFlexLengthmax=v_RodFlexLengthmax,RodFlexLengthmin=v_RodFlexLengthmin,
      TubingFlexLength=v_TubingFlexLength,TubingFlexLengthmax=v_TubingFlexLengthmax,TubingFlexLengthmin=v_TubingFlexLengthmin,
      InertiaLength=v_InertiaLength,InertiaLengthmax=v_InertiaLengthmax,InertiaLengthmin=v_InertiaLengthmin,
      t.pumpBoreDiameter=v_pumpBoreDiameter,t.pumpBoreDiametermax=v_pumpBoreDiametermax,t.pumpBoreDiametermin=v_pumpBoreDiametermin,
      t.pumpSettingDepth=v_pumpSettingDepth,t.pumpSettingDepthmax=v_pumpSettingDepthmax,t.pumpSettingDepthmin=v_pumpSettingDepthmin,
      t.producingfluidLevel=v_producingfluidLevel,t.producingfluidLevelmax=v_producingfluidLevelmax,t.producingfluidLevelmin=v_producingfluidLevelmin,
      t.submergence=v_submergence,t.submergencemax=v_submergencemax,t.submergencemin=v_submergencemin,
      t.levelcorrectvalue=v_LevelCorrectValue,t.levelcorrectvaluemax=v_LevelCorrectValuemax,t.levelcorrectvaluemin=v_LevelCorrectValuemin,
      PumpIntakeP=v_PumpIntakeP,PumpIntakePmax=v_PumpIntakePmax,PumpIntakePmin=v_PumpIntakePmin,
      PumpIntakeT=v_PumpIntakeT,PumpIntakeTmax=v_PumpIntakeTmax,PumpIntakeTmin=v_PumpIntakeTmin,
      PumpIntakeGOL=v_PumpIntakeGOL,PumpIntakeGOLmax=v_PumpIntakeGOLmax,PumpIntakeGOLmin=v_PumpIntakeGOLmin,
      PumpIntakeVisl=v_PumpIntakeVisl,PumpIntakeVislmax=v_PumpIntakeVislmax,PumpIntakeVislmin=v_PumpIntakeVislmin,
      PumpIntakeBo=v_PumpIntakeBo,PumpIntakeBomax=v_PumpIntakeBomax,PumpIntakeBomin=v_PumpIntakeBomin,
      PumpOutletP=v_PumpOutletP,PumpOutletPmax=v_PumpOutletPmax,PumpOutletPmin=v_PumpOutletPmin,
      PumpOutletT=v_PumpOutletT,PumpOutletTmax=v_PumpOutletTmax,PumpOutletTmin=v_PumpOutletTmin,
      PumpOutletGOL=v_PumpOutletGOL,PumpOutletGOLmax=v_PumpOutletGOLmax,PumpOutletGOLmin=v_PumpOutletGOLmin,
      PumpOutletVisl=v_PumpOutletVisl,PumpOutletVislmax=v_PumpOutletVislmax,PumpOutletVislmin=v_PumpOutletVislmin,
      PumpOutletBo=v_PumpOutletBo,PumpOutletBomax=v_PumpOutletBomax,PumpOutletBomin=v_PumpOutletBomin,
      t.wellDownSystemEfficiency=v_wellDownSystemEfficiency,t.wellDownSystemEfficiencymax=v_wellDownSystemEfficiencymax,t.wellDownSystemEfficiencymin=v_wellDownSystemEfficiencymin,
      t.surfaceSystemEfficiency=v_surfaceSystemEfficiency,t.surfaceSystemEfficiencymax=v_surfaceSystemEfficiencymax,t.surfaceSystemEfficiencymin=v_surfaceSystemEfficiencymin,
      t.systemEfficiency=v_systemEfficiency,t.systemEfficiencymax=v_systemEfficiencymax,t.systemEfficiencymin=v_systemEfficiencymin,
      t.powerConsumptionPerTHM=v_powerConsumptionPerTHM,t.powerConsumptionPerTHMmax=v_powerConsumptionPerTHMmax,t.powerConsumptionPerTHMmin=v_powerConsumptionPerTHMmin,
      AvgWatt=v_AvgWatt,AvgWattmax=v_AvgWattmax,AvgWattmin=v_AvgWattmin,
      PolishRodPower=v_PolishRodPower,PolishRodPowermax=v_PolishRodPowermax,PolishRodPowermin=v_PolishRodPowermin,
      WaterPower=v_WaterPower,WaterPowermax=v_WaterPowermax,WaterPowermin=v_WaterPowermin,
      t.iDegreeBalance=v_iDegreeBalance,t.iDegreeBalancemax=v_iDegreeBalancemax,t.iDegreeBalancemin=v_iDegreeBalancemin,
      UpStrokeIMax_Avg=v_UpStrokeIMax_Avg,UpStrokeIMax_Max=v_UpStrokeIMax_Max,UpStrokeIMax_Min=v_UpStrokeIMax_Min,
      DownStrokeIMax_Avg=v_DownStrokeIMax_Avg,DownStrokeIMax_Max=v_DownStrokeIMax_Max,DownStrokeIMax_Min=v_DownStrokeIMax_Min,
      t.wattDegreeBalance=v_wattDegreeBalance,t.wattDegreeBalancemax=v_wattDegreeBalancemax,t.wattDegreeBalancemin=v_wattDegreeBalancemin,
      UpStrokeWattMax_Avg=v_UpStrokeWattMax_Avg,UpStrokeWattMax_Max=v_UpStrokeWattMax_Max,UpStrokeWattMax_Min=v_UpStrokeWattMax_Min,
      DownStrokeWattMax_Avg=v_DownStrokeWattMax_Avg,DownStrokeWattMax_Max=v_DownStrokeWattMax_Max,DownStrokeWattMax_Min=v_DownStrokeWattMax_Min,
      t.deltaradius=v_DeltaRadius,t.deltaradiusmax=v_DeltaRadiusMax,t.deltaradiusmin=v_DeltaRadiusMin
      where t.id=p_totalresultid;
      commit;
      p_msg := '更新成功';
    end if;
  elsif p_wellcount=0 then
    p_msg := '井号不存在';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_diagramdaily;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_diagramresult (
       v_ResultStatus in NUMBER,
       v_Fmax in NUMBER,v_Fmin in NUMBER,
       v_Stroke in NUMBER,v_SPM in NUMBER,
       v_UpStrokeIMax in NUMBER,v_DownStrokeIMax in NUMBER,v_UPStrokeWattMax in NUMBER,v_DownStrokeWattMax in NUMBER,v_IDegreeBalance in NUMBER,v_WattDegreeBalance in NUMBER,
       v_DeltaRadius in NUMBER,
       v_ResultCode in NUMBER,
       v_FullnessCoefficient in NUMBER,
       v_NoLiquidFullnessCoefficient in NUMBER,
       v_PlungerStroke in NUMBER,v_AvailablePlungerStroke in NUMBER,v_NoLiquidAvailableStroke  in NUMBER,
       v_UpperLoadLine in NUMBER,v_UpperLoadLineOfExact in NUMBER,v_LowerLoadLine in NUMBER,
       v_SMaxIndex in NUMBER,v_SMinIndex in NUMBER,
       v_PumpFSDiagram in tbl_rpc_diagram_hist.PumpFSDiagram%TYPE,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_AvailablePlungerStrokeProd_V in NUMBER,v_PumpClearanceLeakProd_V in NUMBER,
       v_TVLeakVolumetricProduction in NUMBER,v_SVLeakVolumetricProduction in NUMBER,v_GasInfluenceProd_V in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_AvailablePlungerStrokeProd_W in NUMBER,v_PumpClearanceLeakProd_W in NUMBER,
       v_TVLeakWeightProduction in NUMBER,v_SVLeakWeightProduction in NUMBER,v_GasInfluenceProd_W in NUMBER,

       v_LevelCorrectValue in NUMBER,v_ProducingfluidLevel in NUMBER,

       v_MotorInputActivePower in NUMBER,v_PolishRodPower in NUMBER,v_WaterPower in NUMBER,
       v_SurfaceSystemEfficiency in NUMBER,v_WellDownSystemEfficiency in NUMBER,v_SystemEfficiency in NUMBER,
       v_PowerConsumptionPerTHM in NUMBER,v_FSDiagramArea in NUMBER,
       v_RodFlexLength in NUMBER,v_TubingFlexLength in NUMBER,v_InertiaLength in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff3 in NUMBER,v_PumpEff4 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2,
       v_crankAngle in tbl_rpc_diagram_hist.crankangle%TYPE,
       v_polishRodV in tbl_rpc_diagram_hist.polishrodv%TYPE,
       v_polishRodA in tbl_rpc_diagram_hist.polishroda%TYPE,
       v_PR in tbl_rpc_diagram_hist.pr%TYPE,
       v_TF in tbl_rpc_diagram_hist.tf%TYPE,
       v_loadTorque in tbl_rpc_diagram_hist.loadtorque%TYPE,
       v_crankTorque in tbl_rpc_diagram_hist.cranktorque%TYPE,
       v_currentBalanceTorque in tbl_rpc_diagram_hist.currentbalancetorque%TYPE,
       v_currentNetTorque in tbl_rpc_diagram_hist.currentnettorque%TYPE,
       v_expectedBalanceTorque in tbl_rpc_diagram_hist.expectedbalancetorque%TYPE,
       v_expectedNetTorque in tbl_rpc_diagram_hist.expectednettorque%TYPE,
       v_wellboreSlice in tbl_rpc_diagram_hist.wellboreslice%TYPE,
       v_id in NUMBER) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_rpc_diagram_hist t
      set t.resultstatus=v_ResultStatus,
          t.fmax=v_Fmax,t.fmin=v_Fmin,
          t.stroke=v_Stroke,t.spm=v_SPM,
          t.upstrokeimax=v_UpStrokeIMax,t.downstrokeimax=v_DownStrokeIMax,t.upstrokewattmax=v_UPStrokeWattMax,t.downstrokewattmax=v_DownStrokeWattMax,t.idegreebalance=v_IDegreeBalance,t.wattdegreebalance=v_WattDegreeBalance,
          t.deltaradius=v_DeltaRadius,
          t.workingconditioncode=v_ResultCode,
          t.fullnesscoefficient=v_FullnessCoefficient,
          t.noliquidfullnesscoefficient=v_NoLiquidFullnessCoefficient,
          t.plungerstroke=v_PlungerStroke,t.availableplungerstroke=v_AvailablePlungerStroke,t.noliquidavailableplungerstroke=v_NoLiquidAvailableStroke,
          t.upperloadline=v_UpperLoadLine,t.upperloadlineofexact=v_UpperLoadLineOfExact,t.lowerloadline=v_LowerLoadLine,
          t.smaxindex=v_SMaxIndex,t.sminindex=v_SMinIndex,
          t.pumpfsdiagram=v_PumpFSDiagram,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_LiquidVolumetricProduction,t.oilvolumetricproduction=v_OilVolumetricProduction,t.watervolumetricproduction=v_WaterVolumetricProduction,
          t.availableplungerstrokeprod_v=v_AvailablePlungerStrokeProd_V,t.pumpclearanceleakprod_v=v_PumpClearanceLeakProd_V,
          t.tvleakvolumetricproduction=v_TVLeakVolumetricProduction,t.svleakvolumetricproduction=v_SVLeakVolumetricProduction,t.gasinfluenceprod_v=v_GasInfluenceProd_V,
          t.liquidweightproduction=v_LiquidWeightProduction,t.oilweightproduction=v_OilWeightProduction,t.waterweightproduction=v_WaterWeightProduction,
          t.availableplungerstrokeprod_w=v_AvailablePlungerStrokeProd_W,t.pumpclearanceleakprod_w=v_PumpClearanceLeakProd_W,
          t.tvleakweightproduction=v_TVLeakWeightProduction,t.svleakweightproduction=v_SVLeakWeightProduction,t.gasinfluenceprod_w=v_GasInfluenceProd_W,
           t.levelcorrectvalue=v_LevelCorrectValue,t.inverproducingfluidlevel=v_ProducingfluidLevel,
          t.motorinputactivepower=v_MotorInputActivePower,t.polishrodpower=v_PolishRodPower,t.waterpower=v_WaterPower,
          t.surfacesystemefficiency=v_SurfaceSystemEfficiency,t.welldownsystemefficiency=v_WellDownSystemEfficiency,t.systemefficiency=v_SystemEfficiency,
          t.powerconsumptionperthm=v_PowerConsumptionPerTHM,t.fsdiagramarea=v_FSDiagramArea,
          t.rodflexlength=v_RodFlexLength,t.tubingflexlength=v_TubingFlexLength,t.inertialength=v_InertiaLength,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff3=v_PumpEff3,t.pumpeff4=v_PumpEff4,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.crankangle=v_crankAngle,t.polishrodv=v_polishRodV,t.polishroda=v_polishRodA,t.pr=v_PR,t.tf=v_TF,
          t.loadtorque=v_loadTorque,t.cranktorque=v_crankTorque,t.currentbalancetorque=v_currentBalanceTorque,t.currentnettorque=v_currentNetTorque,
          t.expectedbalancetorque=v_expectedBalanceTorque,t.expectednettorque=v_expectedNetTorque,
          t.wellboreslice=v_wellboreSlice
      where t.id=v_id;
      commit;
      p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);

Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_diagramresult;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_discretedaily (
  v_wellName in varchar2,
  v_ETRestultCode in number,v_ETRestultStr in varchar2,
  v_Ia in number,v_IaMax in number,v_IaMin in number,
  v_Ib in number,v_IbMax in number,v_IbMin in number,
  v_Ic in number,v_IcMax in number,v_IcMin in number,
  v_Va in number,v_VaMax in number,v_VaMin in number,
  v_Vb in number,v_VbMax in number,v_VbMin in number,
  v_Vc in number,v_VcMax in number,v_VcMin in number,
  v_frequencyrunvalue in number,v_frequencyrunvalueMax in number,v_frequencyrunvalueMin in number,

  v_Signal in number,v_SignalMax in number,v_SignalMin in number,
  v_WattSum in number,v_WattSumMax in number,v_WattSumMin in number,
  v_VarSum in number,v_VarSumMax in number,v_VarSumMin in number,
  v_VASum in number,v_VASumMax in number,v_VASumMin in number,
  v_PFSum in number,v_PFSumMax in number,v_PFSumMin in number,
  v_calDate in varchar2
  ) is
  p_msg varchar2(3000) := 'error';
  p_wellid number:=0;
  p_wellcount number:=0;
  p_totalresultcount number:=0;
  p_totalresultid number:=0;
  --p_lastTotalResultCount number:=0;
  --p_jsdjrcyldbd number:=0;
  --p_jsdjrcylfbd number:=0;
  --p_jsdjrcyldbdbfb number:=0;
  --p_jsdjrcylfbdbfb number:=0;
begin
  select count(*) into p_wellcount from tbl_wellinformation t where t.wellName=v_wellName ;
   if p_wellcount>0 then
     select id into p_wellid from tbl_wellinformation t where t.wellName=v_wellName and rownum=1;
     --查询是否已存在当天计算记录
    select count(*) into p_totalresultcount from tbl_rpc_total_day t
    where t.wellid =p_wellid and t.calculatedate=(to_date(v_calDate,'yyyy-mm-dd')-1);
    --如不存在
    if p_totalresultcount=0 then
      insert into tbl_rpc_total_day (
         calculatedate,wellid,
         workingconditioncode_e,workingconditionstring_e,
         ia,iamax,iamin,
         ib,ibmax,ibmin,
         ic,icmax,icmin,
         va,vamax,vamin,
         vb,vbmax,vbmin,
         vc,vcmax,vcmin,
         frequency,frequencymax,frequencymin,
         Signal,SignalMax,SignalMin,
         WattSum,WattSumMax,WattSumMin,
         VarSum,VarSumMax,VarSumMin,
         VASum,VASumMax,VASumMin,
         PFSum,PFSumMax,PFSumMin
      )values(
         to_date(v_calDate,'yyyy-mm-dd')-1,p_wellid,
         v_ETRestultCode,v_ETRestultStr,
         v_Ia,v_IaMax,v_IaMin,
         v_Ib,v_IbMax,v_IbMin,
         v_Ic,v_IcMax,v_IcMin,
         v_Va,v_VaMax,v_VaMin,
         v_Vb,v_VbMax,v_VbMin,
         v_Vc,v_VcMax,v_VcMin,
         v_frequencyrunvalue,v_frequencyrunvalueMax,v_frequencyrunvalueMin,
         v_Signal,v_SignalMax,v_SignalMin,
         v_WattSum,v_WattSumMax,v_WattSumMin,
         v_VarSum,v_VarSumMax,v_VarSumMin,
         v_VASum,v_VASumMax,v_VASumMin,
         v_PFSum,v_PFSumMax,v_PFSumMin
      );
      p_msg := '插入成功';
      commit;
    elsif p_totalresultcount>0 then
      select t.id into p_totalresultid from tbl_rpc_total_day t,tbl_wellinformation t007
      where t007.id=t.wellid and t.calculatedate=(to_date(v_calDate,'yyyy-mm-dd')-1) and t007.wellName=v_wellName and rownum=1;
      update tbl_rpc_total_day t set
      t.calculatedate=to_date(v_calDate,'yyyy-mm-dd')-1,
      t.workingconditioncode_e=v_ETRestultCode,t.workingconditionstring_e=v_ETRestultStr,
      t.ia=v_Ia,t.iamax=v_IaMax,t.iamin=v_IaMin,
      t.ib=v_Ib,t.ibmax=v_IbMax,t.ibmin=v_IbMin,
      t.ic=v_Ic,t.icmax=v_IcMax,t.icmin=v_IcMin,
      t.va=v_Va,t.vamax=v_VaMax,t.vamin=v_VaMin,
      t.vb=v_Vb,t.vbmax=v_VbMax,t.vbmin=v_VbMin,
      t.vc=v_Vc,t.vcmax=v_VcMax,t.vcmin=v_VcMin,
      t.frequency=v_frequencyrunvalue,t.frequencymax=v_frequencyrunvalueMax,t.frequencymin=v_frequencyrunvalueMin,
      Signal=v_Signal,SignalMax=v_SignalMax,SignalMin=v_SignalMin,
      WattSum=v_WattSum,WattSumMax=v_WattSumMax,WattSumMin=v_WattSumMin,
      VarSum=v_VarSum,VarSumMax=v_VarSumMax,VarSumMin=v_VarSumMin,
      VASum=v_VASum,VASumMax=v_VASumMax,VASumMin=v_VASumMin,
      PFSum=v_PFSum,PFSumMax=v_PFSumMax,PFSumMin=v_PFSumMin
      where t.id=p_totalresultid;
      commit;
      p_msg := '更新成功';
    end if;
  elsif p_wellcount=0 then
    p_msg := '井号不存在';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_discretedaily;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_inver_daily (
  v_wellName in varchar2,v_workingConditionCode_E in number,v_workingConditionStr_E in varchar2,
  v_commStatus in number,v_commTime in number,v_commEfficiency in number,v_commRange in varchar2,
  v_runStatus in number,v_runTime in number,v_runEfficiency in number,v_runRange in varchar2,
  v_signal in number,v_signalMax in number,v_signalMin in number,
  v_todayKWattH in number,v_todaypKWattH in number,v_todaynKWattH in number,v_todayKVarH in number,v_todaypKVarH in number,v_todaynKVarH in number,v_todayKVAH in number,
  v_IaAvg in number,v_IaMax in number,v_IaMin in number,
  v_IbAvg in number,v_IbMax in number,v_IbMin in number,
  v_IcAvg in number,v_IcMax in number,v_IcMin in number,
  v_VaAvg in number,v_VaMax in number,v_VaMin in number,
  v_VbAvg in number,v_VbMax in number,v_VbMin in number,
  v_VcAvg in number,v_VcMax in number,v_VcMin in number,
  v_FAvg in number,v_FMax in number,v_FMin in number,
  v_SPMAvg in number,v_SPMMax in number,v_SPMMin in number,
  v_IDegreeBalanceAvg in number,v_IDegreeBalanceMax in number,v_IDegreeBalanceMin in number,
  v_WattDegreeBalanceAvg in number,v_WattDegreeBalanceMax in number,v_WattDegreeBalanceMin in number,
  v_date in varchar2
  ) is
  p_msg varchar2(3000) := 'error';
  p_wellId number:=0;
  p_wellcount number:=0;
  p_totalresultcount number:=0;
begin
  select count(*) into p_wellcount from tbl_wellinformation t where t.wellName=v_wellName ;
   if p_wellcount>0 then
     select t.id into p_wellId from tbl_wellinformation t where t.wellName=v_wellName and rownum=1;
     --查询是否已存在当天计算记录
    select count(*) into p_totalresultcount from tbl_rpc_total_day t
    where t.wellId =p_wellId and t.calculatedate=(to_date(v_date,'yyyy-mm-dd'));
    --如不存在
    if p_totalresultcount=0 then
      insert into tbl_rpc_total_day (
         wellId,calculatedate,workingconditioncode_e,workingconditionstring_e,
         commstatus,commtime,commtimeefficiency,commrange,
         runstatus,runtime,runtimeefficiency,runrange,
         signal,signalmax,signalmin,
         totalKWattH,totalpKWattH,totalnKWattH,totalKVarH,totalpKVarH,totalnKVarH,totalKVAH,
         ia,iamax,iamin,
         ib,ibmax,ibmin,
         ic,icmax,icmin,
         va,vamax,vamin,
         vb,vbmax,vbmin,
         vc,vcmax,vcmin,
         f,fmax,fmin,
         spm,spmmax,spmmin,
         idegreebalance,idegreebalancemax,idegreebalancemin,
         wattdegreebalance,wattdegreebalancemax,wattdegreebalancemin
      )values(
         p_wellId,to_date(v_date,'yyyy-mm-dd'),v_workingConditionCode_E,v_workingConditionStr_E,
         v_commStatus,v_commTime,v_commEfficiency,v_commRange,
         v_runStatus,v_runTime,v_runEfficiency,v_runRange,
         v_signal,v_signalMax,v_signalMin,
         v_todayKWattH,v_todaypKWattH,v_todaynKWattH,v_todayKVarH,v_todaypKVarH,v_todaynKVarH,v_todayKVAH,
         v_IaAvg,v_IaMax,v_IaMin,
         v_IbAvg,v_IbMax,v_IbMin,
         v_IcAvg,v_IcMax,v_IcMin,
         v_VaAvg,v_VaMax,v_VaMin,
         v_VbAvg,v_VbMax,v_VbMin,
         v_VcAvg,v_VcMax,v_VcMin,
         v_FAvg,v_FMax,v_FMin,
         v_SPMAvg,v_SPMMax,v_SPMMin,
         v_IDegreeBalanceAvg,v_IDegreeBalanceMax,v_IDegreeBalanceMin,
         v_WattDegreeBalanceAvg,v_WattDegreeBalanceMax,v_WattDegreeBalanceMin
      );
      p_msg := '插入成功';
      commit;
    elsif p_totalresultcount>0 then
      update tbl_rpc_total_day t set
             t.workingconditioncode_e=v_workingConditionCode_E,t.workingconditionstring_e=v_workingConditionStr_E,
             t.commstatus=v_commStatus,t.commtime=v_commTime,t.commtimeefficiency=v_commEfficiency,t.commrange=v_commRange,
             t.runstatus=v_runStatus,t.runtime=v_runTime,t.runtimeefficiency=v_runEfficiency,t.runrange=v_runRange,
             t.signal=v_signal,t.signalmax=v_signalMax,t.signalmin=v_signalMin,
             todayKWattH=v_todayKWattH,todaypKWattH=v_todaypKWattH,todaynKWattH=v_todaynKWattH,todayKVarH=v_todayKVarH,todaypKVarH=v_todaypKVarH,todaynKVarH=v_todaynKVarH,todayKVAH=v_todayKVAH,
             ia=v_IaAvg,iamax=v_IaMax,iamin=v_IaMin,
             ib=v_IbAvg,ibmax=v_IbMax,ibmin=v_IbMin,
             ic=v_IcAvg,icmax=v_IcMax,icmin=v_IcMin,
             va=v_VaAvg,vamax=v_VaMax,vamin=v_VaMin,
             vb=v_VbAvg,vbmax=v_VbMax,vbmin=v_VbMin,
             vc=v_VcAvg,vcmax=v_VcMax,vcmin=v_VcMin,
             f=v_FAvg,fmax=v_FMax,fmin=v_FMin,
             spm=v_SPMAvg,spmmax=v_SPMMax,spmmin=v_SPMMin,
             t.idegreebalance=v_IDegreeBalanceAvg,t.idegreebalancemax=v_IDegreeBalanceMax,t.idegreebalancemin=v_IDegreeBalanceMin,
             t.wattdegreebalance=v_WattDegreeBalanceAvg,t.wattdegreebalancemax=v_WattDegreeBalanceMax,t.wattdegreebalancemin=v_WattDegreeBalanceMin,
             t.savetime=sysdate
      where t.wellId =p_wellId and t.calculatedate=(to_date(v_date,'yyyy-mm-dd'));
      commit;
      p_msg := '更新成功';
    end if;
    commit;
  elsif p_wellcount=0 then
    p_msg := '井号不存在';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_inver_daily;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_inver_opt (v_WellName   in varchar2,
                                                    v_OffsetAngleOfCrankPS    in NUMBER,
                                                    v_SurfaceSystemEfficiency   in NUMBER,
                                                    v_FS_LeftPercent     in NUMBER,
                                                    v_FS_RightPercent    in NUMBER,
                                                    v_WattAngle    in NUMBER,
                                                    v_FilterTime_Watt    in NUMBER,
                                                    v_FilterTime_I   in NUMBER,
                                                    v_FilterTime_RPM in NUMBER,
                                                    v_FilterTime_FSDiagram in NUMBER,
                                                    v_FilterTime_FSDiagram_L in NUMBER,
                                                    v_FilterTime_FSDiagram_R in NUMBER,
                                                    v_ids    in varchar2) as
  wellcount number :=0;
  p_msg varchar2(3000) := 'error';
  p_sql varchar2(3000);
begin
  p_sql:='select count(*)  from tbl_wellinformation t, tbl_org org where t.orgid=org.org_id and   t.wellname='''||v_WellName||''' and org.org_id in ('||v_ids||')';
  dbms_output.put_line('p_sql:' || p_sql);
  EXECUTE IMMEDIATE p_sql into wellcount;
  --select count(*) into wellcount from tbl_wellinformation t where t.jh=v_jh;
  if wellcount>0 then
    update tbl_rpc_inver_opt t set
           t.offsetangleofcrankps=v_OffsetAngleOfCrankPS,
           t.surfacesystemefficiency=v_SurfaceSystemEfficiency,
           t.fs_leftpercent=v_FS_LeftPercent,t.fs_rightpercent=v_FS_RightPercent,
           t.wattangle=v_WattAngle,
           t.filtertime_watt=v_FilterTime_Watt,t.filtertime_i=v_FilterTime_I,t.filtertime_rpm=v_FilterTime_RPM,
           t.filtertime_fsdiagram=v_FilterTime_FSDiagram,
           t.filtertime_fsdiagram_l=v_FilterTime_FSDiagram_L,
           t.filtertime_fsdiagram_r=v_FilterTime_FSDiagram_R
    where t.wellid=(select id from tbl_wellinformation where wellname=v_WellName);
    commit;
    p_msg := '更新成功';
  elsif wellcount=0 then
    p_msg := '井号不存在';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);

Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_inver_opt;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_motor (v_wellname  in varchar2,
                                              v_Manufacturer in varchar2,
                                              v_Model in varchar2,
                                              v_BeltPulleyDiameter in varchar2,
                                              v_SynchroSpeed in varchar2,
                                              v_selectedjh  in varchar2,
                                              v_PerformanceCurver  in tbl_rpc_motor.performancecurver%TYPE
                                              ) is
  p_msg varchar2(3000) := 'error';
  p_wellcount number:=0;
  p_wellId number:=0;
  p_recordcount number:=0;
begin
  select count(*) into p_wellcount from tbl_wellinformation t where t.wellName=v_wellname;
  if p_wellcount>0 then
    select id into p_wellId from tbl_wellinformation t where t.wellName=v_wellname;
    select count(*) into p_recordcount from tbl_rpc_motor t where t.wellId=p_wellId;
    if p_recordcount>0 then
       update tbl_rpc_motor t set
           t.manufacturer=v_Manufacturer,
           t.model=v_Model,
           t.synchrospeed=v_SynchroSpeed,
           t.beltpulleydiameter=v_BeltPulleyDiameter
       where t.wellId=p_wellId;
       commit;
       p_msg := '修改成功';
    elsif p_recordcount=0 then
        insert into tbl_rpc_motor(wellId,manufacturer,model,synchrospeed,beltpulleydiameter)
        values(p_wellId,v_Manufacturer,v_Model,v_SynchroSpeed,v_BeltPulleyDiameter);
        commit;
        p_msg := '添加成功';
    end if;
  end if;
  --更新位置扭矩因数
  update tbl_rpc_motor t set t.performancecurver=v_PerformanceCurver  where t.wellId=( select id from tbl_wellinformation where wellname= v_selectedjh);
  commit;
   p_msg := '更新位置扭矩因数成功';
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_motor;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_productiondata (v_WellName   in varchar2,
                                                      v_RunTime   in NUMBER,
                                                      v_CrudeOilDensity    in NUMBER,
                                                      v_WaterDensity    in NUMBER,
                                                      v_NaturalGasRelativeDensity    in NUMBER,
                                                      v_SaturationPressure    in NUMBER,
                                                      v_ReservoirDepth    in NUMBER,
                                                      v_ReservoirTemperature    in NUMBER,
                                                      v_TubingPressure   in NUMBER,
                                                      v_CasingPressure   in NUMBER,
                                                      v_WellHeadFluidTemperature   in NUMBER,
                                                      v_WaterCut_W    in NUMBER,
                                                      v_WaterCut    in NUMBER,
                                                      v_ProductionGasOilRatio   in NUMBER,
                                                      v_ProducingfluidLevel    in NUMBER,
                                                      v_PumpSettingDepth    in NUMBER,
                                                      v_BarrelTypeName in varchar2,
                                                      v_PumpTypeName in varchar2,
                                                      v_PumpGrade    in NUMBER,
                                                      v_PumpBoreDiameter   in NUMBER,
                                                      v_PlungerLength in NUMBER,
                                                      v_BarrelLength    in NUMBER,
                                                      v_BarrelSeries    in NUMBER,
                                                      v_RotorDiameter   in NUMBER,
                                                      v_QPR    in NUMBER,
                                                      v_TubingStringInsideDiameter    in NUMBER,
                                                      v_CasingStringInsideDiameter    in NUMBER,
                                                      v_RodString    in varchar2,
                                                      v_AnchoringStateName in varchar2,
                                                      v_NetGrossRatio  in NUMBER,
                                                      v_AcqTime in varchar2,
                                                      v_ids    in varchar2) as
  p_wellcount number :=0;
  p_prodcount  number :=0;
  p_wellid     number :=0;
  p_msg varchar2(3000) := 'error';
  p_sql varchar2(3000);
  p_AnchoringState number :=1;
  p_AnchoringStateCount number :=0;
  p_BarrelType varchar2(20) :='L';
  p_BarrelTypeCount number :=0;
  p_PumpType varchar2(20) :='T';
  p_PumpTypeCount number :=0;
begin
  p_sql:='select count(*)  from tbl_wellinformation t where t.wellName='''||v_WellName||''' and t.orgid in ('||v_ids||')';
  EXECUTE IMMEDIATE p_sql into p_wellcount;
  if p_wellcount>0 then
    --获取井编号
    select t.id into p_wellid from tbl_wellinformation t where t.wellName=v_WellName;
    select count(1) into p_prodcount from tbl_rpc_productiondata_latest t where t.wellid=(select t2.id from tbl_wellinformation t2 where t2.wellName=v_WellName);

    --锚定状态
    select count(1) into p_AnchoringStateCount from tbl_code code5 where code5.itemcode='AnchoringState' and code5.itemname=v_AnchoringStateName;
    if p_AnchoringStateCount>0 then
      select code5.itemvalue into p_AnchoringState from tbl_code code5 where code5.itemcode='AnchoringState' and code5.itemname=v_AnchoringStateName;
    end if;
    --泵筒类型
    select count(1) into p_BarrelTypeCount from tbl_code code where code.itemcode='BarrelType' and code.itemname=v_BarrelTypeName;
    if p_BarrelTypeCount>0 then
      select code.itemvalue into p_BarrelType from tbl_code code where code.itemcode='BarrelType' and code.itemname=v_BarrelTypeName;
    end if;
    --泵类型
    select count(1) into p_PumpTypeCount from tbl_code code where code.itemcode='PumpType' and code.itemname=v_PumpTypeName;
    if p_PumpTypeCount>0 then
      select code.itemvalue into p_PumpType from tbl_code code where code.itemcode='PumpType' and code.itemname=v_PumpTypeName;
    end if;
    --更新数据
    if p_prodcount>0 then
      --开始更新
      update tbl_rpc_productiondata_latest t
      set t.RunTime=v_RunTime,
          t.CrudeOilDensity=v_CrudeOilDensity,t.WaterDensity=v_WaterDensity,t.NaturalGasRelativeDensity=v_NaturalGasRelativeDensity,t.SaturationPressure=v_SaturationPressure,t.ReservoirDepth=v_ReservoirDepth,t.ReservoirTemperature=v_ReservoirTemperature,
          t.TubingPressure=v_TubingPressure,t.CasingPressure=v_CasingPressure,t.WellHeadFluidTemperature=v_WellHeadFluidTemperature,
          t.WaterCut_W=v_WaterCut_W,t.watercut=v_WaterCut,
          t.ProductionGasOilRatio=v_ProductionGasOilRatio,
          t.ProducingfluidLevel=v_ProducingfluidLevel,t.PumpSettingDepth=v_PumpSettingDepth,
          t.barreltype=p_BarrelType,t.pumptype=p_PumpType,
          t.PumpGrade=v_PumpGrade,
          t.PumpBoreDiameter=v_PumpBoreDiameter,t.PlungerLength=v_PlungerLength,
          t.BarrelLength=v_BarrelLength,t.BarrelSeries=v_BarrelSeries,t.RotorDiameter=v_RotorDiameter,t.QPR=v_QPR,
          t.TubingStringInsideDiameter=v_TubingStringInsideDiameter,t.CasingStringInsideDiameter=v_CasingStringInsideDiameter,
          t.rodstring=v_RodString,
          t.AnchoringState=p_AnchoringState,
          t.NetGrossRatio=v_NetGrossRatio,t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss')
      where t.wellid=p_wellid;
      commit;
      p_msg := '修改成功';
    elsif p_prodcount=0 then
      insert into tbl_rpc_productiondata_latest(wellid,AcqTime,runtime,
             crudeoildensity,waterdensity,naturalgasrelativedensity,saturationpressure,reservoirdepth,reservoirtemperature,
             tubingpressure,casingpressure,wellheadfluidtemperature,
             watercut_w,watercut,
             productiongasoilratio,
             producingfluidlevel,pumpsettingdepth,
             barreltype,pumptype,
             pumpgrade,
             pumpborediameter,plungerlength,
             barrellength,barrelseries,rotordiameter,qpr,
             tubingstringinsidediameter,casingstringinsidediameter,
             rodstring,
             anchoringstate,netgrossratio) values (
             p_wellid,to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss'),v_RunTime,
             v_CrudeOilDensity,v_WaterDensity,v_NaturalGasRelativeDensity,v_SaturationPressure,v_ReservoirDepth,v_ReservoirTemperature,
             v_TubingPressure,v_CasingPressure,v_WellHeadFluidTemperature,
             v_WaterCut_W,v_WaterCut,
             v_ProductionGasOilRatio,
             v_ProducingfluidLevel,v_PumpSettingDepth,
             p_BarrelType,p_PumpType,
             v_PumpGrade,
             v_PumpBoreDiameter,v_PlungerLength,
             v_BarrelLength,v_BarrelSeries,v_RotorDiameter,v_QPR,
             v_TubingStringInsideDiameter,v_CasingStringInsideDiameter,
             v_RodString,
             p_AnchoringState,
             v_NetGrossRatio
      );
      commit;
      p_msg := '添加成功';
    end if;
  elsif p_wellcount=0 then
    p_msg := '井号不存在';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_productiondata;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_recalculateparam (v_id   in varchar2,
                                                      v_CrudeOilDensity    in NUMBER,
                                                      v_WaterDensity    in NUMBER,
                                                      v_NaturalGasRelativeDensity    in NUMBER,
                                                      v_SaturationPressure    in NUMBER,
                                                      v_ReservoirDepth    in NUMBER,
                                                      v_ReservoirTemperature    in NUMBER,
                                                      v_TubingPressure    in NUMBER,
                                                      v_CasingPressure    in NUMBER,
                                                      v_WellHeadFluidTemperature    in NUMBER,
                                                      v_WaterCut_W    in NUMBER,
                                                      v_ProductionGasOilRatio    in NUMBER,
                                                      v_ProducingfluidLevel    in NUMBER,
                                                      v_PumpSettingDepth    in NUMBER,
                                                      v_PumpGrade    in NUMBER,
                                                      v_PumpBoreDiameter    in NUMBER,
                                                      v_PlungerLength    in NUMBER,
                                                      v_TubingStringInsideDiameter    in NUMBER,
                                                      v_CasingStringInsideDiameter    in NUMBER,
                                                      v_RodString    in varchar2,
                                                      v_AnchoringStateName    in varchar2,
                                                      v_NetGrossRatio  in NUMBER) as
p_msg varchar2(3000) := 'error';
v_WaterCut number(8,2);
begin
      if v_CrudeOilDensity<0.00001 or v_WaterDensity<0.00001 then
         v_WaterCut:=0;
      else
         v_WaterCut:=100/(1+v_WaterDensity/v_CrudeOilDensity*(100-v_WaterCut_W)/v_WaterCut_W);
      end if;
      update tbl_rpc_productiondata_hist t
      set t.CrudeOilDensity=v_CrudeOilDensity,t.WaterDensity=v_WaterDensity,t.NaturalGasRelativeDensity=v_NaturalGasRelativeDensity,t.SaturationPressure=v_SaturationPressure,t.ReservoirDepth=v_ReservoirDepth,t.ReservoirTemperature=v_ReservoirTemperature,
          t.TubingPressure=v_TubingPressure,t.CasingPressure=v_CasingPressure,t.WellHeadFluidTemperature=v_WellHeadFluidTemperature,
          t.WaterCut_W=v_WaterCut_W,t.WaterCut=v_WaterCut,t.ProductionGasOilRatio=v_ProductionGasOilRatio,
          t.ProducingfluidLevel=v_ProducingfluidLevel,t.PumpSettingDepth=v_PumpSettingDepth,
          t.PumpGrade=v_PumpGrade,t.PumpBoreDiameter=v_PumpBoreDiameter,t.PlungerLength=v_PlungerLength,
          t.TubingStringInsideDiameter=v_TubingStringInsideDiameter,t.CasingStringInsideDiameter=v_CasingStringInsideDiameter,
          t.rodstring=v_RodString,
          t.AnchoringState=(select code5.itemvalue from tbl_code code5 where code5.itemcode='AnchoringState' and code5.itemname=v_AnchoringStateName),
          t.NetGrossRatio=v_NetGrossRatio
      where t.id=( select t2.productiondataid from tbl_rpc_diagram_hist t2 where t2.id=v_id );
      commit;

      update tbl_rpc_diagram_hist t
      set t.resultstatus=2
      where t.productiondataid=( select t2.productiondataid from tbl_rpc_diagram_hist t2 where t2.id=v_id );
      commit;
      p_msg := '修改成功';
      dbms_output.put_line('p_msg:' || p_msg);

Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_recalculateparam;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_reinverdiagram (v_recordId  in varchar2,
                                              v_maxF in number,
                                              v_minF in number,
                                              v_position_curve tbl_rpc_diagram_hist.position_curve%TYPE,
                                              v_load_curve tbl_rpc_diagram_hist.load_curve%TYPE,
                                              v_position360_curve tbl_rpc_diagram_hist.position360_curve%TYPE,
                                              v_angle360_curve tbl_rpc_diagram_hist.angle360_curve%TYPE,
                                              v_load360_curve tbl_rpc_diagram_hist.load360_curve%TYPE,
                                              v_power_curve tbl_rpc_diagram_hist.power_curve%TYPE,
                                              v_current_curve tbl_rpc_diagram_hist.current_curve%TYPE,
                                              v_rpm_curve in tbl_rpc_diagram_hist.rpm_curve%TYPE,

                                              v_UpstrokeIMax in number,
                                              v_DownstrokeIMax in number,
                                              v_UpstrokeWattMax in number,
                                              v_DownstrokeWattMax in number,
                                              v_IDegreeBalance in number,
                                              v_WattDegreeBalance in number,

                                              v_ResultStatus in number
                                              ) is
  p_msg varchar2(3000) := 'error';
begin
  if v_ResultStatus=1 then
     update tbl_rpc_diagram_hist t set
     t.fmax=v_maxF,t.fmin=v_minF,
     t.position_curve=v_position_curve,t.load_curve=v_load_curve,
     t.position360_curve=v_position360_curve,t.angle360_curve=v_angle360_curve,t.load360_curve=v_load360_curve,
     t.power_curve=v_power_curve,t.current_curve=v_current_curve,t.rpm_curve=v_rpm_curve,
     t.upstrokeimax=v_UpstrokeIMax,t.downstrokeimax=v_DownstrokeIMax,t.idegreebalance=v_IDegreeBalance,
     t.upstrokewattmax=v_UpstrokeWattMax,t.downstrokewattmax=v_DownstrokeWattMax,t.wattdegreebalance=v_WattDegreeBalance,
     t.inverresultstatus=v_ResultStatus,
     t.resultstatus=2
     where t.id=v_recordId;
     commit;
     p_msg:='更新成功并重置诊断计产标志';
     dbms_output.put_line('p_msg:' || p_msg);
  elsif v_ResultStatus<>1 then
     update tbl_rpc_diagram_hist t set
     t.inverresultstatus=v_ResultStatus
     where t.id=v_recordId;
     commit;
     p_msg:='更新反演计算失败结果';
     dbms_output.put_line('p_msg:' || p_msg);
  end if;
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_reinverdiagram;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_uploaddiagram (v_wellName  in varchar2,
                                              v_AcqTime in varchar2,
                                              v_stroke in NUMBER,
                                              v_spm in NUMBER,
                                              v_sData  in tbl_rpc_diagram_hist.position_curve%TYPE,
                                              v_fData  in tbl_rpc_diagram_hist.load_curve%TYPE,
                                              v_wattData  in tbl_rpc_diagram_hist.power_curve%TYPE,
                                              v_iData  in tbl_rpc_diagram_hist.current_curve%TYPE) is
  p_msg varchar2(3000) := 'error';
  p_wellId number:=0;
  p_wellCount number:=0;
  p_diagramCount number:=0;
  p_productiondataId number:=0;
begin
  select count(*) into p_wellCount from tbl_wellinformation where wellname=v_wellName;
  if p_wellCount>0 then
    select id into p_wellId from tbl_wellinformation where wellname=v_wellName;
    select count(*)into p_diagramCount from tbl_rpc_diagram_hist t where t.wellid=p_wellId and t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
    if p_diagramCount=0 then
      select t.id into p_productiondataId from tbl_rpc_productiondata_hist t
      where t.wellid=p_wellId and t.AcqTime=(select max(t2.AcqTime) from tbl_rpc_productiondata_hist t2 where t2.wellid=t.wellid );
      insert into tbl_rpc_diagram_hist(wellid,AcqTime,stroke,spm,position_curve,load_curve,power_curve,current_curve,datasource,productiondataid,resultstatus)
      values(p_wellId,to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss'),v_stroke,v_spm,v_sData,v_fData,v_wattData,v_iData,2,p_productiondataId,0);
      commit;
      p_msg := '添加成功';
    elsif p_diagramCount>0 then
      update tbl_rpc_diagram_hist t
      set t.stroke=v_stroke,t.spm=v_spm,
          t.position_curve=v_sData,t.load_curve=v_fData,
          t.power_curve=v_wattData,t.current_curve=v_iData,
          t.datasource=2,
          t.resultstatus=2
      where t.wellid=p_wellId and t.AcqTime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
      commit;
      p_msg := '更新成功';
    end if;
  elsif p_wellCount=0 then
    p_msg:='井号不存在';
  end if;
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_uploaddiagram;
/

CREATE OR REPLACE PROCEDURE prd_save_WellboreTrajectory (v_wellname  in varchar2,
                                              v_ResultStatus in varchar2,
                                              v_measuringDepth in tbl_wellboretrajectory.measuringdepth%TYPE,
                                              v_verticalDepth in tbl_wellboretrajectory.verticalDepth%TYPE,
                                              v_deviationAngle in tbl_wellboretrajectory.deviationAngle%TYPE,
                                              v_azimuthAngle in tbl_wellboretrajectory.azimuthAngle%TYPE,
                                              v_X in tbl_wellboretrajectory.X%TYPE,
                                              v_Y in tbl_wellboretrajectory.Y%TYPE,
                                              v_Z in tbl_wellboretrajectory.Z%TYPE
                                              ) is
  p_msg varchar2(3000) := 'error';
  p_wellcount number:=0;
  p_wellId number:=0;
  p_recordcount number:=0;
begin
  select count(*) into p_wellcount from tbl_wellinformation t where t.wellName=v_wellname;
  if p_wellcount>0 then
    select id into p_wellId from tbl_wellinformation t where t.wellName=v_wellname;
    select count(*) into p_recordcount from tbl_wellboretrajectory t where t.wellId=p_wellId;
    if p_recordcount>0 then
       update tbl_wellboretrajectory t set
           t.resultstatus=v_ResultStatus,
           t.measuringdepth=v_measuringDepth,t.verticaldepth=v_verticalDepth,t.deviationangle=v_deviationAngle,t.azimuthangle=v_azimuthAngle,
           t.x=v_X,t.y=v_Y,t.z=v_Z,t.savetime=sysdate
       where t.wellId=p_wellId;
       commit;
       p_msg := '修改成功';
    elsif p_recordcount=0 then
        insert into tbl_wellboretrajectory(wellId,resultstatus,
               measuringdepth,verticaldepth,deviationangle,azimuthangle,
               x,y,z)
        values(p_wellId,v_ResultStatus,v_measuringDepth,v_verticalDepth,v_deviationAngle,v_azimuthAngle,v_X,v_Y,v_Z);
        commit;
        p_msg := '添加成功';
    end if;
  end if;

Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_WellboreTrajectory;
/

CREATE OR REPLACE PROCEDURE prd_save_wellinformation (v_orgname   in varchar2,
                                                    v_resname    in varchar2,
                                                    v_wellName    in varchar2,
                                                    v_liftingTypeName   in varchar2,
                                                    v_driverCode    in varchar2,
                                                    v_protocol    in varchar2,
                                                    v_acquisitionUnit   in varchar2,
                                                    v_driverAddr    in varchar2,
                                                    v_driverId   in varchar2,
                                                    v_runtimeEfficiencySource  in varchar2,
                                                    v_videoUrl   in varchar2,
                                                    v_sortNum  in NUMBER,
                                                    v_ids    in varchar2,
                                                    v_orgId in varchar2) as
  wellcount number :=0;
  wellamount number :=0;
  orgcount number :=0;
  p_orgName    varchar2(30):='';
  p_msg varchar2(3000) := 'error';
  p_sql varchar2(3000);
  p_prototal number :=null;
  p_prototalCount number :=0;
begin
  --验证权限
  p_sql:='select count(*)  from tbl_org t where t.org_name is not null and  t.org_id='||v_orgId||' and t.org_id in ('||v_ids||')';
  dbms_output.put_line('p_sql:' || p_sql);
  select count(1) into wellamount from tbl_wellinformation;
     EXECUTE IMMEDIATE p_sql into orgcount;
     select t.org_name into p_orgName from tbl_org t where t.org_id=v_orgId;
     if orgcount>0 and p_orgName=v_orgname then
        select count(*) into wellcount from tbl_wellinformation t where t.wellName=v_wellName;
        select count(*) into p_prototalCount from tbl_code code where code.itemcode='PROTOCOL' and code.itemname=v_protocol;
        if p_prototalCount>0 then
           select code.itemvalue into p_prototal from tbl_code code where code.itemcode='PROTOCOL' and code.itemname=v_protocol;
        end if;
        if wellcount>0 then
           Update tbl_wellinformation t
           Set t.orgid   = v_orgId,
               t.resname  =v_resname,
               t.liftingtype   = (select code2.itemvalue from tbl_code code2 where code2.itemcode='LiftingType' and code2.itemname=v_liftingTypeName),
               t.drivercode=v_driverCode,
               t.protocol=p_prototal,
               t.unitcode=(select t046.unit_code from tbl_acq_unit_conf t046 where t046.unit_name=v_acquisitionUnit and rownum=1),
               t.driveraddr=v_driverAddr,t.driverid=v_driverId,
               t.videourl=v_videourl,
               t.runtimeefficiencysource   = (select code3.itemvalue from tbl_code code3 where code3.itemcode='RuntimeEfficiencySource' and code3.itemname=v_runtimeEfficiencySource),
               t.sortnum=v_sortNum
           Where t.wellName=v_wellName;
           commit;
           p_msg := '修改成功';
        elsif wellcount=0 then
              if wellamount<200 then
                  insert into tbl_wellinformation(wellName,drivercode,protocol,driveraddr,driverid,
                  videourl,Sortnum)
                  values(v_wellName,v_driverCode,p_prototal,v_driverAddr,v_driverId,v_videourl,v_sortNum);
                  commit;
                  update tbl_wellinformation t set
                     orgId   = v_orgId,
                     resname  =v_resname,
                     t.liftingtype   = (select code2.itemvalue from tbl_code code2 where code2.itemcode='LiftingType' and code2.itemname=v_liftingTypeName),
                     t.runtimeefficiencysource   = (select code3.itemvalue from tbl_code code3 where code3.itemcode='RuntimeEfficiencySource' and code3.itemname=v_runtimeEfficiencySource),
                     t.unitcode=(select t046.unit_code from tbl_acq_unit_conf t046 where t046.unit_name=v_acquisitionUnit and rownum=1)
                  Where t.wellName=v_wellName;
                  commit;
                  p_msg := '添加成功';
              else
                  p_msg := '超出井数限制';
              end if;
           end if;
        elsif orgcount=0 then
           p_msg := '无权限';
     end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_wellinformation;
/