create or replace package MYPACKAGE as
   type MY_CURSOR is REF CURSOR;
end MYPACKAGE;
/

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
EXECUTE IMMEDIATE 'truncate table tbl_acqdata_latest';
EXECUTE IMMEDIATE 'truncate table tbl_acqdata_hist';
EXECUTE IMMEDIATE 'truncate table tbl_alarminfo_latest';
EXECUTE IMMEDIATE 'truncate table tbl_alarminfo_hist';
EXECUTE IMMEDIATE 'truncate table tbl_acqrawdata';

EXECUTE IMMEDIATE 'truncate table tbl_dailytotalcalculate_latest';
EXECUTE IMMEDIATE 'truncate table tbl_dailytotalcalculate_hist';
EXECUTE IMMEDIATE 'truncate table tbl_dailycalculationdata';
EXECUTE IMMEDIATE 'truncate table tbl_timingcalculationdata';

EXECUTE IMMEDIATE 'truncate table tbl_srpacqdata_latest';
EXECUTE IMMEDIATE 'truncate table tbl_srpacqdata_hist';
EXECUTE IMMEDIATE 'truncate table tbl_srpdailycalculationdata';
EXECUTE IMMEDIATE 'truncate table tbl_srptimingcalculationdata';


EXECUTE IMMEDIATE 'truncate table tbl_pcpacqdata_latest';
EXECUTE IMMEDIATE 'truncate table tbl_pcpacqdata_hist';
EXECUTE IMMEDIATE 'truncate table tbl_pcpdailycalculationdata';
EXECUTE IMMEDIATE 'truncate table tbl_pcptimingcalculationdata';

EXECUTE IMMEDIATE 'truncate table tbl_acqdata_vacuate';
EXECUTE IMMEDIATE 'truncate table tbl_srpacqdata_vacuate';
EXECUTE IMMEDIATE 'truncate table tbl_pcpacqdata_vacuate';

EXECUTE IMMEDIATE 'truncate table tbl_deviceoperationlog';
EXECUTE IMMEDIATE 'truncate table tbl_systemlog';
EXECUTE IMMEDIATE 'truncate table tbl_resourcemonitoring';
EXECUTE IMMEDIATE 'truncate table tbl_dbmonitoring';

--EXECUTE IMMEDIATE 'truncate table tbl_deviceaddinfo';
--EXECUTE IMMEDIATE 'truncate table tbl_auxiliary2master';
--EXECUTE IMMEDIATE 'truncate table tbl_devicegraphicset';

--EXECUTE IMMEDIATE 'truncate table tbl_device';
--EXECUTE IMMEDIATE 'truncate table tbl_smsdevice';

--EXECUTE IMMEDIATE 'truncate table tbl_protocolinstance';
--EXECUTE IMMEDIATE 'truncate table tbl_protocoldisplayinstance';
--EXECUTE IMMEDIATE 'truncate table tbl_protocolalarminstance';
--EXECUTE IMMEDIATE 'truncate table tbl_protocolreportinstance';
--EXECUTE IMMEDIATE 'truncate table tbl_protocolsmsinstance';

--EXECUTE IMMEDIATE 'truncate table tbl_display_items2unit_conf';
--EXECUTE IMMEDIATE 'truncate table tbl_display_unit_conf';

--EXECUTE IMMEDIATE 'truncate table tbl_acq_item2group_conf';
--EXECUTE IMMEDIATE 'truncate table tbl_acq_group2unit_conf';
--EXECUTE IMMEDIATE 'truncate table tbl_acq_group_conf';
--EXECUTE IMMEDIATE 'truncate table tbl_acq_unit_conf';

--EXECUTE IMMEDIATE 'truncate table tbl_alarm_item2unit_conf';
--EXECUTE IMMEDIATE 'truncate table tbl_alarm_unit_conf';

--EXECUTE IMMEDIATE 'truncate table tbl_report_items2unit_conf';
--EXECUTE IMMEDIATE 'truncate table tbl_report_unit_conf';

--EXECUTE IMMEDIATE 'truncate table tbl_runstatusconfig';
--EXECUTE IMMEDIATE 'truncate table tbl_protocol';

--EXECUTE IMMEDIATE 'truncate table tbl_datamapping';


--重置所有序列
 prd_reset_sequence('seq_acqdata_latest');
 prd_reset_sequence('seq_acqdata_hist');
 prd_reset_sequence('seq_alarminfo_latest');
 prd_reset_sequence('seq_alarminfo_hist');
 prd_reset_sequence('seq_acqrawdata');

 prd_reset_sequence('seq_dailytotalcalculate_latest');
 prd_reset_sequence('seq_dailytotalcalculate_hist');
 prd_reset_sequence('seq_dailycalculationdata');
 prd_reset_sequence('seq_timingcalculationdata');


 prd_reset_sequence('seq_srpacqdata_latest');
 prd_reset_sequence('seq_srpacqdata_hist');
 prd_reset_sequence('seq_srpdailycalculationdata');
 prd_reset_sequence('seq_srptimingcalculationdata');

 prd_reset_sequence('seq_pcpacqdata_latest');
 prd_reset_sequence('seq_pcpacqdata_hist');
 prd_reset_sequence('seq_pcpdailycalculationdata');
 prd_reset_sequence('seq_pcptimingcalculationdata');
 
 prd_reset_sequence('seq_acqdata_vacuate');
 prd_reset_sequence('seq_srpacqdata_vacuate');
 prd_reset_sequence('seq_pcpacqdata_vacuate');

 prd_reset_sequence('seq_deviceoperationlog');
 prd_reset_sequence('seq_systemlog');
 prd_reset_sequence('seq_resourcemonitoring');
 prd_reset_sequence('seq_dbmonitoring');
 
-- prd_reset_sequence('seq_deviceaddinfo');
-- prd_reset_sequence('seq_auxiliary2master');
-- prd_reset_sequence('seq_devicegraphicset');

-- prd_reset_sequence('seq_device');
-- prd_reset_sequence('seq_smsdevice');
 
-- prd_reset_sequence('seq_protocolinstance');
-- prd_reset_sequence('seq_protocoldisplayinstance');
-- prd_reset_sequence('seq_protocolalarminstance');
-- prd_reset_sequence('seq_protocoldisplayinstance');
-- prd_reset_sequence('seq_protocolsmsinstance');

-- prd_reset_sequence('seq_display_items2unit_conf');
-- prd_reset_sequence('seq_display_unit_conf'); 
 
-- prd_reset_sequence('seq_acq_group_item'); 
-- prd_reset_sequence('seq_acq_unit_group'); 
-- prd_reset_sequence('seq_acquisitiongroup'); 
-- prd_reset_sequence('seq_acquisitionunit'); 
 
-- prd_reset_sequence('seq_alarm_item2unit_conf'); 
-- prd_reset_sequence('seq_alarm_unit_conf'); 
 
-- prd_reset_sequence('seq_report_items2unit_conf'); 
-- prd_reset_sequence('seq_report_unit_conf'); 
 
-- prd_reset_sequence('seq_runstatusconfig'); 
-- prd_reset_sequence('seq_protocol'); 
-- prd_reset_sequence('seq_datamapping'); 
 
-- delete from TBL_ORG t where t.org_id<>1;
-- commit;
-- delete from tbl_user t where t.user_orgid not in(select t2.org_id from TBL_ORG t2);
-- commit;
 
-- delete from tbl_devicetype2role t where t.rd_devicetypeid not in( select t2.id from tbl_devicetypeinfo t2 );
-- commit;
 
end prd_clear_data;
/

CREATE OR REPLACE PROCEDURE prd_init_device_daily is
begin
    insert into tbl_dailycalculationdata (deviceid,caldate)
    select id, to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') from tbl_device well
    where well.id not in ( select t2.deviceid from tbl_dailycalculationdata t2 where t2.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'));
    commit;

    update tbl_dailycalculationdata t set t.headerlabelinfo=
    ( select t2.headerlabelinfo from  tbl_dailycalculationdata t2
    where t2.deviceid=t.deviceid and t2.caldate=
    ( select max(t3.caldate) from tbl_dailycalculationdata t3 where t3.deviceid=t2.deviceid and t3.headerlabelinfo is not null ))
    where t.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')
    and t.headerlabelinfo is null;
    commit;

/*
    insert into tbl_pcpdailycalculationdata (wellid,caldate)
    select id, to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') from tbl_pcpdevice well
    where well.id not in ( select t2.wellid from tbl_pcpdailycalculationdata t2 where t2.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'));
    commit;

    update tbl_pcpdailycalculationdata t set t.headerlabelinfo=
    ( select t2.headerlabelinfo from  tbl_pcpdailycalculationdata t2
    where t2.wellid=t.wellid and t2.caldate=
    ( select max(t3.caldate) from tbl_pcpdailycalculationdata t3 where t3.wellid=t2.wellid and t3.headerlabelinfo is not null ))
    where t.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')
    and t.headerlabelinfo is null;
    commit;
*/
end prd_init_device_daily;
/

CREATE OR REPLACE PROCEDURE prd_init_device_reportdate(v_offsetHour  in NUMBER,
                                                       v_interval  in NUMBER
) as
begin
    insert into tbl_srpdailycalculationdata (deviceid,caldate)
    select id, to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') from tbl_device well
    where well.calculatetype=1 and well.id not in ( select t2.deviceid from tbl_srpdailycalculationdata t2 where t2.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'));
    commit;

    update tbl_srpdailycalculationdata t set t.headerlabelinfo=
    ( select t2.headerlabelinfo from  tbl_srpdailycalculationdata t2
    where t2.deviceid=t.deviceid and t2.caldate=
    ( select max(t3.caldate) from tbl_srpdailycalculationdata t3 where t3.deviceid=t2.deviceid and t3.headerlabelinfo is not null ))
    where t.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')
    and t.headerlabelinfo is null;
    commit;

    insert into tbl_pcpdailycalculationdata (deviceid,caldate)
    select id, to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') from tbl_device well
    where well.calculatetype=1 and well.id not in ( select t2.deviceid from tbl_pcpdailycalculationdata t2 where t2.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'));
    commit;

    update tbl_pcpdailycalculationdata t set t.headerlabelinfo=
    ( select t2.headerlabelinfo from  tbl_pcpdailycalculationdata t2
    where t2.deviceid=t.deviceid and t2.caldate=
    ( select max(t3.caldate) from tbl_pcpdailycalculationdata t3 where t3.deviceid=t2.deviceid and t3.headerlabelinfo is not null ))
    where t.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')
    and t.headerlabelinfo is null;
    commit;

end prd_init_device_reportdate;
/

CREATE OR REPLACE PROCEDURE prd_init_device_timingreportdate(
       v_deviceId  in NUMBER,
       v_timeStr  in varchar2,
       v_dateStr  in varchar2,
       v_calculateType  in NUMBER
       ) is
  recordCount number :=0;
  p_msg varchar2(3000) := 'error';
begin
    if v_calculateType=1 then
      select count(1) into recordCount from tbl_srptimingcalculationdata t  where t.deviceid=v_deviceId and t.caltime=to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss');
      if recordCount=0 then
        insert into tbl_srptimingcalculationdata (deviceid,caltime)values(v_deviceId,to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss'));
        commit;
      end if;
      --更新采集生产数据
      update tbl_srptimingcalculationdata t set
        (stroke,spm,tubingpressure,casingpressure,producingfluidlevel,bottomholepressure)
        =(
          select t2.stroke,t2.spm,t2.tubingpressure,t2.casingpressure,t2.producingfluidlevel,t2.bottomholepressure
          from TBL_srpDAILYCALCULATIONDATA t2
           where t2.caldate=to_date(v_dateStr,'yyyy-mm-dd')
           and t2.deviceid=v_deviceId
           and rownum=1
        )
        where t.deviceid=v_deviceId and t.caltime=to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss');
      commit;
      --更新采集实时产量
      update tbl_srptimingcalculationdata t set
           (t.realtimewatervolumetricproduction,t.realtimegasvolumetricproduction) =
           ( select t2.realtimewatervolumetricproduction,t2.realtimegasvolumetricproduction
           from tbl_srpacqdata_hist t2
           where t2.id=(
                 select v2.id from
                 (select v.id,rownum r from
                 (select t3.id from  tbl_srpacqdata_hist t3
                 where t3.commstatus=1 and t3.realtimewatervolumetricproduction is not null
                 and t3.acqtime between to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss')-1 and  to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss')
                 and t3.deviceid=v_deviceId
                 order by t3.acqtime desc) v
                 ) v2
                 where r=1
            )
            and t2.deviceid=v_deviceId )
            where t.deviceid=v_deviceId and t.caltime=to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss');
      commit;
    elsif v_calculateType=2 then
      select count(1) into recordCount from tbl_pcptimingcalculationdata t  where t.deviceid=v_deviceId and t.caltime=to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss');
      if recordCount=0 then
        insert into tbl_pcptimingcalculationdata (deviceid,caltime)values(v_deviceId,to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss'));
        commit;
      end if;
      --更新采集生产数据
      update tbl_pcptimingcalculationdata t set
        (tubingpressure,casingpressure,producingfluidlevel,bottomholepressure)
        =(
          select t2.tubingpressure,t2.casingpressure,t2.producingfluidlevel,t2.bottomholepressure
          from TBL_PCPDAILYCALCULATIONDATA t2
           where t2.caldate=to_date(v_dateStr,'yyyy-mm-dd')
           and t2.deviceid=v_deviceId
           and rownum=1
        )
        where t.deviceid=v_deviceId and t.caltime=to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss');
      commit;
      --更新采集实时产量
      update tbl_pcptimingcalculationdata t set
           (t.realtimewatervolumetricproduction,t.realtimegasvolumetricproduction) =
           ( select t2.realtimewatervolumetricproduction,t2.realtimegasvolumetricproduction
           from tbl_pcpacqdata_hist t2
           where t2.id=(
                 select v2.id from
                 (select v.id,rownum r from
                 (select t3.id from  tbl_pcpacqdata_hist t3
                 where t3.commstatus=1 and t3.realtimewatervolumetricproduction is not null
                 and t3.acqtime between to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss')-1 and  to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss')
                 and t3.deviceid=v_deviceId
                 order by t3.acqtime desc) v
                 ) v2
                 where r=1
            )
            and t2.deviceid=v_deviceId )
            where t.deviceid=v_deviceId and t.caltime=to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss');
      commit;
    elsif v_calculateType=0 then
      select count(1) into recordCount from tbl_timingcalculationdata t  where t.deviceid=v_deviceId and t.caltime=to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss');
      if recordCount=0 then
        insert into tbl_timingcalculationdata (deviceid,caltime)values(v_deviceId,to_date(v_timeStr,'yyyy-mm-dd hh24:mi:ss'));
        commit;
      end if;
    end if;
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_init_device_timingreportdate;
/

CREATE OR REPLACE PROCEDURE prd_save_alarmcolor (    overviewBackgroundColor0   in varchar2,
                                                        overviewBackgroundColor1     in varchar2,
                                                        overviewBackgroundColor2    in varchar2,
                                                        overviewBackgroundColor3     in varchar2,
                                                        overviewColor0   in varchar2,
                                                        overviewColor1     in varchar2,
                                                        overviewColor2    in varchar2,
                                                        overviewColor3     in varchar2,
                                                        overviewOpacity0   in varchar2,
                                                        overviewOpacity1     in varchar2,
                                                        overviewOpacity2    in varchar2,
                                                        overviewOpacity3     in varchar2,

                                                        goOnlineBackgroundColor   in varchar2,
                                                        onlineBackgroundColor   in varchar2,
                                                        offlineBackgroundColor     in varchar2,
                                                        goOnlineColor   in varchar2,
                                                        onlineColor   in varchar2,
                                                        offlineColor     in varchar2,
                                                        goOnlineOpacity   in varchar2,
                                                        onlineOpacity   in varchar2,
                                                        offlineOpacity     in varchar2,

                                                        runBackgroundColor   in varchar2,
                                                        stopBackgroundColor     in varchar2,
                                                        noDataBackgroundColor     in varchar2,
                                                        runColor   in varchar2,
                                                        stopColor     in varchar2,
                                                        noDataColor     in varchar2,
                                                        runOpacity   in varchar2,
                                                        stopOpacity     in varchar2,
                                                        noDataOpacity     in varchar2) is
  p_msg varchar2(30) := 'error';
begin
    --数据报警
    Update tbl_code t1 set t1.itemname=overviewBackgroundColor0 where t1.itemcode='ALARMBACKCOLOR' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=overviewBackgroundColor1 where t1.itemcode='ALARMBACKCOLOR' and t1.itemvalue=100;
    Update tbl_code t1 set t1.itemname=overviewBackgroundColor2 where t1.itemcode='ALARMBACKCOLOR' and t1.itemvalue=200;
    Update tbl_code t1 set t1.itemname=overviewBackgroundColor3 where t1.itemcode='ALARMBACKCOLOR' and t1.itemvalue=300;

    Update tbl_code t1 set t1.itemname=overviewColor0 where t1.itemcode='ALARMFORECOLOR' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=overviewColor1 where t1.itemcode='ALARMFORECOLOR' and t1.itemvalue=100;
    Update tbl_code t1 set t1.itemname=overviewColor2 where t1.itemcode='ALARMFORECOLOR' and t1.itemvalue=200;
    Update tbl_code t1 set t1.itemname=overviewColor3 where t1.itemcode='ALARMFORECOLOR' and t1.itemvalue=300;

    Update tbl_code t1 set t1.itemname=overviewOpacity0 where t1.itemcode='ALARMCOLOROPACITY' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=overviewOpacity1 where t1.itemcode='ALARMCOLOROPACITY' and t1.itemvalue=100;
    Update tbl_code t1 set t1.itemname=overviewOpacity2 where t1.itemcode='ALARMCOLOROPACITY' and t1.itemvalue=200;
    Update tbl_code t1 set t1.itemname=overviewOpacity3 where t1.itemcode='ALARMCOLOROPACITY' and t1.itemvalue=300;

    --通信
    Update tbl_code t1 set t1.itemname=goOnlineBackgroundColor where t1.itemcode='COMMALARMBACKCOLOR' and t1.itemvalue=2;
    Update tbl_code t1 set t1.itemname=onlineBackgroundColor where t1.itemcode='COMMALARMBACKCOLOR' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=offlineBackgroundColor where t1.itemcode='COMMALARMBACKCOLOR' and t1.itemvalue=0;

    Update tbl_code t1 set t1.itemname=goOnlineColor where t1.itemcode='COMMALARMFORECOLOR' and t1.itemvalue=2;
    Update tbl_code t1 set t1.itemname=onlineColor where t1.itemcode='COMMALARMFORECOLOR' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=offlineColor where t1.itemcode='COMMALARMFORECOLOR' and t1.itemvalue=0;

    Update tbl_code t1 set t1.itemname=goOnlineOpacity where t1.itemcode='COMMALARMCOLOROPACITY' and t1.itemvalue=2;
    Update tbl_code t1 set t1.itemname=onlineOpacity where t1.itemcode='COMMALARMCOLOROPACITY' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=offlineOpacity where t1.itemcode='COMMALARMCOLOROPACITY' and t1.itemvalue=0;

    --运行
    Update tbl_code t1 set t1.itemname=runBackgroundColor where t1.itemcode='RUNALARMBACKCOLOR' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=stopBackgroundColor where t1.itemcode='RUNALARMBACKCOLOR' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=noDataBackgroundColor where t1.itemcode='RUNALARMBACKCOLOR' and t1.itemvalue=2;

    Update tbl_code t1 set t1.itemname=runColor where t1.itemcode='RUNALARMFORECOLOR' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=stopColor where t1.itemcode='RUNALARMFORECOLOR' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=noDataColor where t1.itemcode='RUNALARMFORECOLOR' and t1.itemvalue=2;

    Update tbl_code t1 set t1.itemname=runOpacity where t1.itemcode='RUNALARMCOLOROPACITY' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=stopOpacity where t1.itemcode='RUNALARMCOLOROPACITY' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=noDataOpacity where t1.itemcode='RUNALARMCOLOROPACITY' and t1.itemvalue=2;
    commit;
    p_msg := '修改成功';

  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_alarmcolor;
/

CREATE OR REPLACE PROCEDURE prd_save_alarminfo (
  v_deviceId in number,
  v_deviceType in number,
  v_acqTime in varchar2,
  v_alarmTime in varchar2,
  v_itemName in varchar2,
  v_alarmType in number,
  v_alarmValue in number,
  v_alarmInfo in varchar2,
  v_alarmLimit in number,
  v_hystersis in number,
  v_alarmLevel in number,
  v_delay in number,
  v_retriggerTime in number,
  v_isSendMessage in number,
  v_isSendMail in number,
  v_itemCode in varchar2,
  v_bitIndex in number
  ) is
  p_msg varchar2(3000) := 'error';
  counts number :=0;
  counts_latest number :=0;
begin
  select count(1) into counts from tbl_alarminfo_hist t
  where t.deviceid=v_deviceId
  and t.alarmtime=to_date(v_alarmTime,'yyyy-mm-dd hh24:mi:ss')
  and t.itemname=v_itemName;

  select count(1) into counts_latest from tbl_alarminfo_latest t
  where t.deviceid=v_deviceId
  and t.alarmtype=v_alarmType;

  if counts_latest=0 and v_deviceId>0 then
    insert into tbl_alarminfo_latest (deviceid,acqtime,alarmtime,itemname,alarmtype,alarmvalue,alarminfo,alarmlimit,
    hystersis,alarmlevel,delay,retriggertime,
    issendmessage,issendmail,itemcode,bitindex)
    values(
         v_deviceId,
         to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
         to_date(v_alarmTime,'yyyy-mm-dd hh24:mi:ss'),
         v_itemName,
         v_alarmType,
         v_alarmValue,
         v_alarmInfo,
         v_alarmLimit,
         v_hystersis,
         v_alarmLevel,
         v_delay,
         v_retriggerTime,
         v_isSendMessage,
         v_isSendMail,
         v_itemCode,
         v_bitIndex
      );
    commit;
    p_msg := '实时数据插入成功';
  elsif counts_latest>0 then
    update tbl_alarminfo_latest t set alarmvalue=v_alarmValue,
    alarminfo=v_alarmInfo,alarmlimit=v_alarmLimit,hystersis=v_hystersis,alarmlevel=v_alarmLevel,
    delay=v_delay,retriggertime=v_retriggerTime,
    issendmessage=v_isSendMessage,issendmail=v_isSendMail,
    t.acqtime=to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
    t.alarmtime=to_date(v_alarmTime,'yyyy-mm-dd hh24:mi:ss'),
    t.itemname=v_itemName,
    t.itemcode=v_itemCode,
    t.bitindex=v_bitIndex
    where t.deviceid=v_deviceId
    and t.alarmtype=v_alarmType;
    commit;
    p_msg := '实时数据更新成功';
  end if;

  if counts=0 and v_deviceId>0 then
    insert into tbl_alarminfo_hist (deviceid,acqtime,alarmtime,itemname,alarmtype,alarmvalue,alarminfo,alarmlimit,
    hystersis,alarmlevel,delay,retriggertime,
    issendmessage,issendmail,itemcode,bitindex)
    values(
         v_deviceId,
         to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
         to_date(v_alarmTime,'yyyy-mm-dd hh24:mi:ss'),
         v_itemName,
         v_alarmType,
         v_alarmValue,
         v_alarmInfo,
         v_alarmLimit,
         v_hystersis,
         v_alarmLevel,
         v_delay,
         v_retriggerTime,
         v_isSendMessage,
         v_isSendMail,
         v_itemCode,
         v_bitIndex
      );
    commit;
    p_msg := '插入成功';
  elsif counts>0 then
    update tbl_alarminfo_hist t set t.alarmtype=v_alarmType,alarmvalue=v_alarmValue,
    alarminfo=v_alarmInfo,alarmlimit=v_alarmLimit,hystersis=v_hystersis,alarmlevel=v_alarmLevel,
    delay=v_delay,retriggertime=v_retriggerTime,
    issendmessage=v_isSendMessage,issendmail=v_isSendMail,
    t.itemcode=v_itemCode,
    t.bitindex=v_bitIndex,
    t.acqtime=to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss')
    where t.deviceid=v_deviceId
    and t.alarmtime=to_date(v_alarmTime,'yyyy-mm-dd hh24:mi:ss')
    and t.itemname=v_itemName;
    commit;
    p_msg := '更新成功';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_alarminfo;
/

CREATE OR REPLACE PROCEDURE prd_save_auxiliarydevice (
                                                    v_name in varchar2,
                                                    v_type in number,
                                                    v_model in varchar2,
                                                    v_remark in varchar2,
                                                    v_sort in number,
                                                    v_isCheckout in NUMBER,
                                                    v_result out NUMBER,
                                                    v_resultstr out varchar2
  ) is
  p_msg varchar2(3000) := 'error';
  counts number :=0;
begin
  select count(1) into counts from tbl_auxiliarydevice t where t.name=v_name and t.type=v_type and t.model=v_model;
  if counts=0 then
    insert into tbl_auxiliarydevice (name,type,model,remark,sort)
    values(v_name,v_type,v_model,v_remark,v_sort);
    commit;
    v_result:=0;
    v_resultstr := '添加成功';
    p_msg := '添加成功';
  elsif counts>0 then
    if v_isCheckout=1 then
      v_result:=-33;
      v_resultstr := '设备已存在';
      p_msg := '设备已存在';
    elsif v_isCheckout=0 then
      update tbl_auxiliarydevice t set t.remark=v_remark,t.sort=v_sort
      where t.name=v_name and t.type=v_type and t.model=v_model;
      commit;
      v_result:=1;
      v_resultstr := '修改成功';
      p_msg := '修改成功';
    end if;
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_auxiliarydevice;
/

CREATE OR REPLACE PROCEDURE prd_save_dailytotalcalculate (
  v_deviceId in number,
  v_itemColumn in varchar2,
  v_itemName in varchar2,
  v_acqTime in varchar2,
  v_totalValue in number,
  v_todayValue in number
  ) is
  p_msg varchar2(3000) := 'error';
  counts number :=0;
  histCounts number :=0;
begin
  select count(1) into counts from tbl_dailytotalcalculate_latest t
  where t.deviceid=v_deviceId and t.itemcolumn=v_itemColumn;
  if counts=0 then
    insert into tbl_dailytotalcalculate_latest (deviceid,acqtime,itemcolumn,itemname,totalvalue,todayvalue)
    values(v_deviceId,
         to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
         v_itemColumn,
         v_itemName,
         v_totalValue,
         v_todayValue
      );
    commit;
    p_msg := '插入成功';
  elsif counts>0 then
    update tbl_dailytotalcalculate_latest t
    set  t.acqtime=to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),t.totalvalue=v_totalValue,t.todayvalue=v_todayValue,t.itemname=v_itemName
    where t.deviceid=v_deviceId and t.itemcolumn=v_itemColumn;
    commit;
    p_msg := '更新成功';
  end if;

  select count(1) into histCounts from tbl_dailytotalcalculate_hist t
  where t.deviceid=v_deviceId and t.acqtime=to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss') and t.itemcolumn=v_itemColumn;
  if histCounts=0 then
    insert into tbl_dailytotalcalculate_hist (deviceid,acqtime,itemcolumn,itemname,totalvalue,todayvalue)
    values(v_deviceId,
         to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
         v_itemColumn,
         v_itemName,
         v_totalValue,
         v_todayValue
      );
    commit;
    p_msg := '插入成功';
  elsif histCounts>0 then
    update tbl_dailytotalcalculate_hist t set t.totalvalue=v_totalValue,t.todayvalue=v_todayValue,t.itemname=v_itemName
    where t.deviceid=v_deviceId and t.acqtime=to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss') and t.itemcolumn=v_itemColumn;
    commit;
    p_msg := '更新成功';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_dailytotalcalculate;
/

CREATE OR REPLACE PROCEDURE prd_save_device (
                                                    v_orgId  in NUMBER,
                                                    v_deviceName    in varchar2,
                                                    v_devicetype in NUMBER,
                                                    v_applicationScenarios    in NUMBER,
                                                    v_instance    in varchar2,
                                                    v_displayInstance    in varchar2,
                                                    v_reportInstance    in varchar2,
                                                    v_alarmInstance    in varchar2,
                                                    v_tcpType    in varchar2,
                                                    v_signInId    in varchar2,
                                                    v_ipPort    in varchar2,
                                                    v_slave   in varchar2,
                                                    v_peakDelay in NUMBER,
                                                    v_status in NUMBER,
                                                    v_commissioningDate in varchar2,
                                                    v_sortNum  in NUMBER,
                                                    v_isCheckout in NUMBER,
                                                    v_license in NUMBER,
                                                    v_result out NUMBER,
                                                    v_resultstr out varchar2) as
  wellcount number :=0;
  wellId number :=0;
  othercount number :=0;
  totalCount number :=0;
  otherDeviceAllPath varchar2(3000) := '';
  p_msg varchar2(3000) := 'error';
begin
  select count(1) into wellcount from tbl_device t where t.devicename=v_deviceName and t.orgid=v_orgId;
  select count(1) into totalCount from tbl_device t;
  if v_isCheckout=0 then
    if wellcount>0 then
      select t.id into wellId from tbl_device t where t.devicename=v_deviceName and t.orgid=v_orgId;
      --判断signinid和slave是否已存在
      select count(1) into othercount from tbl_device t
      where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave)
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null
        and t.id<>wellId;
      if othercount=0 then
        Update tbl_device t
        Set t.orgid   = v_orgId,t.devicetype=v_devicetype,
          t.applicationscenarios=v_applicationScenarios,
          t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and rownum=1),
          t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and rownum=1),
          t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and rownum=1),
          t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and rownum=1),
          t.tcptype=v_tcpType,t.signinid=v_signInId,t.ipport=v_ipPort,t.slave=v_slave,t.peakdelay=v_peakDelay,
          t.commissioningdate=to_date(v_commissioningDate,'yyyy-mm-dd'),t.status=v_status, t.sortnum=v_sortNum,
          t.productiondataupdatetime=sysdate
        Where t.deviceName=v_deviceName and t.orgid=v_orgId;
        commit;
        v_result:=1;
        v_resultstr := '修改成功';
        p_msg := '修改成功';
      else
        select substr(v.path||'/'||t.devicename,2) into otherDeviceAllPath  from tbl_device t, (select org.org_id, sys_connect_by_path(org.org_name_zh_cn,'/') as path
          from tbl_org org
          start with org.org_parent=0
          connect by   org.org_parent= prior org.org_id) v
          where t.orgid=v.org_id
          and t.id=(select t2.id from tbl_device t2
            where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
            and to_number(t2.slave)=to_number(v_slave)
            and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null
            and t2.id<>wellId);
        v_result:=-22;
        v_resultstr := '注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
        p_msg := '注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
      end if;
    elsif wellcount=0 then
      --判断signinid和slave是否已存在
        select count(1) into othercount from tbl_device t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave)
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        if othercount=0 then
          if totalCount<v_license then
            insert into tbl_device(orgId,deviceName,devicetype,tcptype,signinid,ipport,slave,peakdelay,status,commissioningdate,Sortnum,productiondataupdatetime)
            values(v_orgId,v_deviceName,v_devicetype,v_tcpType,v_signInId,v_ipPort,v_slave,v_peakDelay,v_status,to_date(v_commissioningDate,'yyyy-mm-dd'),v_sortNum,sysdate);
            commit;
            update tbl_device t
            set t.applicationscenarios=v_applicationScenarios,
                t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and rownum=1),
                t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and rownum=1),
                t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and rownum=1),
                t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and rownum=1)
            Where t.deviceName=v_deviceName and t.orgid=v_orgId;
            commit;
            v_result:=0;
            v_resultstr := '添加成功';
            p_msg := '添加成功';
          else
            v_result:=-66;
            v_resultstr := '井数许可超限';
            p_msg := '井数许可超限';
          end if;
        else
          select substr(v.path||'/'||t.devicename,2) into otherDeviceAllPath  from tbl_device t, (select org.org_id, sys_connect_by_path(org.org_name_zh_cn,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_device t2
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave)
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          v_result:=-22;
          v_resultstr := '注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
          p_msg := '注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
        end if;
    end if;
  else
    p_msg := '需要校验';
    if wellcount>0 then
      v_result:=-33;
      v_resultstr := '所选组织下存在同名设备';
      p_msg := '所选组织下存在同名设备';
    elsif wellcount=0 then
      --判断signinid和slave是否已存在
        select count(1) into othercount from tbl_device t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave)
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        if othercount=0 then
          if totalCount<v_license then
            insert into tbl_device(orgId,deviceName,devicetype,tcptype,signinid,ipport,slave,peakdelay,status,commissioningdate,Sortnum,productiondataupdatetime)
            values(v_orgId,v_deviceName,v_devicetype,v_tcpType,v_signInId,v_ipPort,v_slave,v_peakDelay,v_status,to_date(v_commissioningDate,'yyyy-mm-dd'),v_sortNum,sysdate);
            commit;
            update tbl_device t
            set t.applicationscenarios=v_applicationScenarios,
                t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and rownum=1),
                t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and rownum=1),
                t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and rownum=1),
                t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and rownum=1)
            Where t.deviceName=v_deviceName and t.orgid=v_orgId;
            commit;
            v_result:=0;
            v_resultstr := '添加成功';
            p_msg := '添加成功';
          else
            v_result:=-66;
            v_resultstr := '井数许可超限';
            p_msg := '井数许可超限';
          end if;
        else
          select substr(v.path||'/'||t.devicename||'抽油机',2) into otherDeviceAllPath  from tbl_device t, (select org.org_id, sys_connect_by_path(org.org_name_zh_cn,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_device t2
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave)
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          v_result:=-22;
          v_resultstr := '注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
          p_msg := '注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
        end if;
    end if;
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
  Exception
    When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_device;
/

CREATE OR REPLACE PROCEDURE prd_save_deviceOperationLog (
  v_time in varchar2,
  v_wellName in varchar2,
  v_deviceType in number,
  v_action in number,
  v_userId in varchar2,
  v_loginIp in varchar2,
  v_remark in varchar2
  ) is
  p_msg varchar2(3000) := 'error';
begin
  insert into tbl_deviceoperationlog (createtime,devicename,devicetype,action,user_id,loginip,remark)
  values(
         to_date(v_time,'yyyy-mm-dd hh24:mi:ss'),
         v_wellName,
         v_deviceType,
         v_action,
         v_userId,
         v_loginIp,
         v_remark
      );
      commit;
      p_msg := '插入成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_deviceOperationLog;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_rpm (
       v_wellId in NUMBER,v_AcqTime in varchar2,v_RPM in NUMBER,v_productionData in varchar2,
       v_ResultStatus in NUMBER,
       v_ResultCode in NUMBER,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_Submergence in NUMBER,
       v_averagewatt in NUMBER,v_WaterPower in NUMBER,
       v_SystemEfficiency in NUMBER,v_energyper100mlift in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_pcpacqdata_latest t
      set t.rpm=v_RPM,
          t.productiondata=v_productionData,t.resultstatus=v_ResultStatus,
          t.resultcode=v_ResultCode,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_LiquidVolumetricProduction,t.oilvolumetricproduction=v_OilVolumetricProduction,t.watervolumetricproduction=v_WaterVolumetricProduction,
          t.liquidweightproduction=v_LiquidWeightProduction,t.oilweightproduction=v_OilWeightProduction,t.waterweightproduction=v_WaterWeightProduction,
          t.submergence=v_Submergence,
          t.averagewatt=v_averagewatt,t.waterpower=v_WaterPower,
          t.systemefficiency=v_SystemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.realtimeliquidvolumetricproduction=v_LiquidVolumetricProduction,
          t.realtimeoilvolumetricproduction=v_OilVolumetricProduction,t.realtimewatervolumetricproduction=v_WaterVolumetricProduction,
          t.realtimeliquidweightproduction=v_LiquidWeightProduction,
          t.realtimeoilweightproduction=v_OilWeightProduction,t.realtimewaterweightproduction=v_WaterWeightProduction
      where t.deviceid=v_wellId;
  commit;
  update tbl_pcpacqdata_hist t
      set t.rpm=v_RPM,
          t.productiondata=v_productionData,t.resultstatus=v_ResultStatus,
          t.resultcode=v_ResultCode,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_LiquidVolumetricProduction,t.oilvolumetricproduction=v_OilVolumetricProduction,t.watervolumetricproduction=v_WaterVolumetricProduction,
          t.liquidweightproduction=v_LiquidWeightProduction,t.oilweightproduction=v_OilWeightProduction,t.waterweightproduction=v_WaterWeightProduction,
          t.submergence=v_Submergence,
          t.averagewatt=v_averagewatt,t.waterpower=v_WaterPower,
          t.systemefficiency=v_SystemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.realtimeliquidvolumetricproduction=v_LiquidVolumetricProduction,
          t.realtimeoilvolumetricproduction=v_OilVolumetricProduction,t.realtimewatervolumetricproduction=v_WaterVolumetricProduction,
          t.realtimeliquidweightproduction=v_LiquidWeightProduction,
          t.realtimeoilweightproduction=v_OilWeightProduction,t.realtimewaterweightproduction=v_WaterWeightProduction
      where t.deviceid=v_wellId and t.acqtime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_rpm;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_rpmcaldata (
       v_recordId in NUMBER,
       v_ResultStatus in NUMBER,
       v_ResultCode in NUMBER,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_Submergence in NUMBER,
       v_averagewatt in NUMBER,v_WaterPower in NUMBER,
       v_SystemEfficiency in NUMBER,v_energyper100mlift in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_pcpacqdata_hist t
      set t.resultstatus=v_ResultStatus,
          t.resultcode=v_ResultCode,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_LiquidVolumetricProduction,t.oilvolumetricproduction=v_OilVolumetricProduction,t.watervolumetricproduction=v_WaterVolumetricProduction,
          t.liquidweightproduction=v_LiquidWeightProduction,t.oilweightproduction=v_OilWeightProduction,t.waterweightproduction=v_WaterWeightProduction,
          t.submergence=v_Submergence,
          t.averagewatt=v_averagewatt,t.waterpower=v_WaterPower,
          t.systemefficiency=v_SystemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.realtimeliquidvolumetricproduction=v_LiquidVolumetricProduction,
          t.realtimeoilvolumetricproduction=v_OilVolumetricProduction,t.realtimewatervolumetricproduction=v_WaterVolumetricProduction,
          t.realtimeliquidweightproduction=v_LiquidWeightProduction,
          t.realtimeoilweightproduction=v_OilWeightProduction,t.realtimewaterweightproduction=v_WaterWeightProduction
      where t.id=v_recordId;
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_rpmcaldata;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_rpmdaily (
  v_wellId in number,v_ResultStatus in number,
  v_ExtendedDays in number,
  v_rpm in number,
  v_TheoreticalProduction in number,
  v_liquidVolumetricProduction in number,v_oilVolumetricProduction in number,v_waterVolumetricProduction in number,v_volumewatercut in number,
  v_liquidWeightProduction in number,v_oilWeightProduction in number,v_waterWeightProduction in number,v_weightwatercut in number,
  v_pumpEff in number,v_pumpEff1 in number,v_pumpEff2 in number,
  v_systemEfficiency in number,v_energyper100mlift in number,
  v_pumpSettingDepth in number,v_producingfluidLevel in number,v_submergence in number,
  v_casingPressure in number,v_tubingPressure in number,
  v_commStatus in number,v_commTime in number,v_commTimeEfficiency in number,
  v_commRange in tbl_pcpdailycalculationdata.commrange%TYPE,
  v_runStatus in number,v_runTime in number,v_runTimeEfficiency in number,
  v_runRange in tbl_pcpdailycalculationdata.runrange%TYPE,
  v_calDate in varchar2,v_recordCount in number
  ) is
  p_msg varchar2(3000) := 'error';
  p_count number:=0;
begin
  select count(*) into p_count from tbl_pcpdailycalculationdata t where t.deviceid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd');
  if p_count>0 then
    p_msg := '记录存在';
    update tbl_pcpdailycalculationdata t
    set t.commstatus=v_commStatus,t.commtime=v_commTime,t.commtimeefficiency=v_commTimeEfficiency,t.commrange=v_commRange,
    t.runstatus=v_runStatus,t.runtime=v_runTime,t.runtimeefficiency=v_runTimeEfficiency,t.runrange=v_runRange
    where t.deviceid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd');
    commit;
    if v_recordCount>0 then
      update tbl_pcpdailycalculationdata t
      set t.resultstatus=v_ResultStatus,t.extendeddays=v_ExtendedDays,
          t.rpm=v_rpm,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_liquidVolumetricProduction,t.oilvolumetricproduction=v_oilVolumetricProduction,
          t.watervolumetricproduction=v_waterVolumetricProduction,t.volumewatercut=v_volumewatercut,
          t.liquidweightproduction=v_liquidWeightProduction,t.oilweightproduction=v_oilWeightProduction,
          t.waterweightproduction=v_waterWeightProduction,t.weightwatercut=v_weightwatercut,
          t.pumpeff=v_pumpEff,t.pumpeff1=v_pumpEff1,t.pumpeff2=v_pumpEff2,
          t.systemefficiency=v_systemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.pumpsettingdepth=v_pumpSettingDepth,t.producingfluidlevel=v_producingfluidLevel,t.submergence=v_submergence,
          t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure
       where t.deviceid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd');
       commit;
    end if;
    p_msg := '更新成功';
  elsif p_count=0 then
    p_msg := '记录不存在';
    insert into tbl_pcpdailycalculationdata(
           deviceid,caldate,resultstatus,extendeddays,rpm,
    theoreticalproduction,
    liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,volumewatercut,
    liquidweightproduction,oilweightproduction,waterweightproduction,weightwatercut,
    pumpeff,pumpeff1,pumpeff2,
    systemefficiency,energyper100mlift,
    producingfluidlevel,casingpressure,tubingpressure,
    commstatus,commtime,commtimeefficiency,commrange,
    runstatus,runtime,runtimeefficiency,runrange
    )values(
    v_wellId,to_date(v_calDate,'yyyy-mm-dd'),
    v_ResultStatus,v_ExtendedDays,v_rpm,
    v_TheoreticalProduction,
    v_liquidVolumetricProduction,v_oilVolumetricProduction,v_waterVolumetricProduction,v_volumewatercut,
    v_liquidWeightProduction,v_oilWeightProduction,v_waterWeightProduction,v_weightwatercut,
    v_pumpEff,v_pumpEff1,v_pumpEff2,
    v_systemEfficiency,v_energyper100mlift,
    v_producingfluidLevel,v_casingPressure,v_tubingPressure,
    v_commStatus,v_commTime,v_commTimeEfficiency,v_commRange,
    v_runStatus,v_runTime,v_runTimeEfficiency,v_runRange
    );
    commit;
    p_msg := '添加成功';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_rpmdaily;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_rpmdailyrecal (
  v_recordId in number,v_ResultStatus in number,
  v_ExtendedDays in number,
  v_rpm in number,
  v_TheoreticalProduction in number,
  v_liquidVolumetricProduction in number,v_oilVolumetricProduction in number,v_waterVolumetricProduction in number,v_volumewatercut in number,
  v_liquidWeightProduction in number,v_oilWeightProduction in number,v_waterWeightProduction in number,v_weightwatercut in number,
  v_pumpEff in number,v_pumpEff1 in number,v_pumpEff2 in number,
  v_systemEfficiency in number,v_energyper100mlift in number,
  v_pumpSettingDepth in number,v_producingfluidLevel in number,v_submergence in number,
  v_casingPressure in number,v_tubingPressure in number,
  v_recordCount in number
  ) is
  p_msg varchar2(3000) := 'error';
begin
  if v_recordCount>0 then
    update tbl_pcpdailycalculationdata t
    set t.resultstatus=v_ResultStatus,t.extendeddays=v_ExtendedDays,
    t.rpm=v_rpm,
    t.theoreticalproduction=v_TheoreticalProduction,
    t.liquidvolumetricproduction=v_liquidVolumetricProduction,t.oilvolumetricproduction=v_oilVolumetricProduction,
    t.watervolumetricproduction=v_waterVolumetricProduction,t.volumewatercut=v_volumewatercut,
    t.liquidweightproduction=v_liquidWeightProduction,t.oilweightproduction=v_oilWeightProduction,
    t.waterweightproduction=v_waterWeightProduction,t.weightwatercut=v_weightwatercut,
    t.pumpeff=v_pumpEff,t.pumpeff1=v_pumpEff1,t.pumpeff2=v_pumpEff2,
    t.systemefficiency=v_systemEfficiency,t.energyper100mlift=v_energyper100mlift,
    t.pumpsettingdepth=v_pumpSettingDepth,t.producingfluidlevel=v_producingfluidLevel,t.submergence=v_submergence,
    t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure
    where t.id=v_recordId ;
    commit;
    p_msg := '更新成功';
    dbms_output.put_line('p_msg:' || p_msg);
  end if;
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_rpmdailyrecal;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_rpmtimingtotal (
  v_wellId in number,v_ResultStatus in number,
  v_ExtendedDays in number,
  v_rpm in number,
  v_TheoreticalProduction in number,
  v_liquidVolumetricProduction in number,v_oilVolumetricProduction in number,v_waterVolumetricProduction in number,v_volumewatercut in number,
  v_liquidWeightProduction in number,v_oilWeightProduction in number,v_waterWeightProduction in number,v_weightwatercut in number,
  v_pumpEff in number,v_pumpEff1 in number,v_pumpEff2 in number,
  v_systemEfficiency in number,v_energyper100mlift in number,
  v_pumpSettingDepth in number,v_producingfluidLevel in number,v_submergence in number,
  v_casingPressure in number,v_tubingPressure in number,
  v_commStatus in number,v_commTime in number,v_commTimeEfficiency in number,
  v_commRange in tbl_pcptimingcalculationdata.commrange%TYPE,
  v_runStatus in number,v_runTime in number,v_runTimeEfficiency in number,
  v_runRange in tbl_pcptimingcalculationdata.runrange%TYPE,
  v_calTime in varchar2,v_recordCount in number
  ) is
  p_msg varchar2(3000) := 'error';
  p_count number:=0;
begin
  select count(*) into p_count from tbl_pcptimingcalculationdata t where t.deviceid=v_wellId and t.caltime=to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss');
  if p_count>0 then
    p_msg := '记录存在';
    update tbl_pcptimingcalculationdata t
    set t.commstatus=v_commStatus,t.commtime=v_commTime,t.commtimeefficiency=v_commTimeEfficiency,t.commrange=v_commRange,
    t.runstatus=v_runStatus,t.runtime=v_runTime,t.runtimeefficiency=v_runTimeEfficiency,t.runrange=v_runRange
    where t.deviceid=v_wellId and t.caltime=to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss');
    commit;
    if v_recordCount>0 then
      update tbl_pcptimingcalculationdata t
      set t.resultstatus=v_ResultStatus,t.extendeddays=v_ExtendedDays,
          t.rpm=v_rpm,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_liquidVolumetricProduction,t.oilvolumetricproduction=v_oilVolumetricProduction,
          t.watervolumetricproduction=v_waterVolumetricProduction,t.volumewatercut=v_volumewatercut,
          t.liquidweightproduction=v_liquidWeightProduction,t.oilweightproduction=v_oilWeightProduction,
          t.waterweightproduction=v_waterWeightProduction,t.weightwatercut=v_weightwatercut,
          t.pumpeff=v_pumpEff,t.pumpeff1=v_pumpEff1,t.pumpeff2=v_pumpEff2,
          t.systemefficiency=v_systemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.pumpsettingdepth=v_pumpSettingDepth,t.producingfluidlevel=v_producingfluidLevel,t.submergence=v_submergence,
          t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure
       where t.deviceid=v_wellId and t.caltime=to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss');
       commit;
       dbms_output.put_line('更新汇总结果');
       update tbl_pcptimingcalculationdata t set
             (
             t.theoreticalproduction,
             t.realtimeliquidvolumetricproduction,t.realtimeoilvolumetricproduction,t.realtimewatervolumetricproduction,
             t.realtimeliquidweightproduction,t.realtimeoilweightproduction,t.realtimewaterweightproduction,
             t.pumpeff,t.pumpeff1,t.pumpeff2,
             t.systemefficiency,t.energyper100mlift,
             t.submergence,
             t.rpm
             )
             =(
             select
             t2.theoreticalproduction,
             t2.realtimeliquidvolumetricproduction,t2.realtimeoilvolumetricproduction,t2.realtimewatervolumetricproduction,
             t2.realtimeliquidweightproduction,t2.realtimeoilweightproduction,t2.realtimewaterweightproduction,
             t2.pumpeff,t2.pumpeff1,t2.pumpeff2,
             t2.systemefficiency,t2.energyper100mlift,
             t2.submergence,
             t2.rpm
             from tbl_pcpacqdata_hist t2
             where t2.id=(
                   select v2.id from
                   (select v.id,rownum r from
                   (select t3.id from  tbl_pcpacqdata_hist t3
                   where t3.commstatus=1 and t3.resultstatus=1
                   and t3.acqtime between to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss')-1 and  to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss')
                   and t3.deviceid=v_wellId
                   order by t3.acqtime desc) v
                   ) v2
                   where r=1
             )
             and t2.deviceid=v_wellId )
             where t.deviceid=v_wellId and t.caltime=to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss');
        commit;
        dbms_output.put_line('更新实时结果');
    end if;
    p_msg := '更新成功';
  elsif p_count=0 then
    p_msg := '记录不存在';
    insert into tbl_pcptimingcalculationdata(
           deviceid,caltime,resultstatus,extendeddays,rpm,
    theoreticalproduction,
    liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,volumewatercut,
    liquidweightproduction,oilweightproduction,waterweightproduction,weightwatercut,
    pumpeff,pumpeff1,pumpeff2,
    systemefficiency,energyper100mlift,
    producingfluidlevel,casingpressure,tubingpressure,
    commstatus,commtime,commtimeefficiency,commrange,
    runstatus,runtime,runtimeefficiency,runrange
    )values(
    v_wellId,to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss'),
    v_ResultStatus,v_ExtendedDays,v_rpm,
    v_TheoreticalProduction,
    v_liquidVolumetricProduction,v_oilVolumetricProduction,v_waterVolumetricProduction,v_volumewatercut,
    v_liquidWeightProduction,v_oilWeightProduction,v_waterWeightProduction,v_weightwatercut,
    v_pumpEff,v_pumpEff1,v_pumpEff2,
    v_systemEfficiency,v_energyper100mlift,
    v_producingfluidLevel,v_casingPressure,v_tubingPressure,
    v_commStatus,v_commTime,v_commTimeEfficiency,v_commRange,
    v_runStatus,v_runTime,v_runTimeEfficiency,v_runRange
    );
    commit;
    p_msg := '添加成功';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_rpmtimingtotal;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_rpm_vacuate (
       v_wellId in NUMBER,v_AcqTime in varchar2,v_RPM in NUMBER,v_productionData in varchar2,
       v_ResultStatus in NUMBER,
       v_ResultCode in NUMBER,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_Submergence in NUMBER,
       v_averagewatt in NUMBER,v_WaterPower in NUMBER,
       v_SystemEfficiency in NUMBER,v_energyper100mlift in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_pcpacqdata_vacuate t
      set t.rpm=v_RPM,
          t.productiondata=v_productionData,t.resultstatus=v_ResultStatus,
          t.resultcode=v_ResultCode,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_LiquidVolumetricProduction,t.oilvolumetricproduction=v_OilVolumetricProduction,t.watervolumetricproduction=v_WaterVolumetricProduction,
          t.liquidweightproduction=v_LiquidWeightProduction,t.oilweightproduction=v_OilWeightProduction,t.waterweightproduction=v_WaterWeightProduction,
          t.submergence=v_Submergence,
          t.averagewatt=v_averagewatt,t.waterpower=v_WaterPower,
          t.systemefficiency=v_SystemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.realtimeliquidvolumetricproduction=v_LiquidVolumetricProduction,
          t.realtimeoilvolumetricproduction=v_OilVolumetricProduction,t.realtimewatervolumetricproduction=v_WaterVolumetricProduction,
          t.realtimeliquidweightproduction=v_LiquidWeightProduction,
          t.realtimeoilweightproduction=v_OilWeightProduction,t.realtimewaterweightproduction=v_WaterWeightProduction
      where t.deviceid=v_wellId and t.acqtime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcp_rpm_vacuate;
/

CREATE OR REPLACE PROCEDURE prd_save_resourcemonitoring (
  v_acqTime in varchar2,
  v_acRunStatus in number,
  v_acVersion in varchar2,
  v_adRunStatus in number,
  v_adVersion in varchar2,
  v_cpuUsedPercent in varchar2,
  v_memUsedPercent in number,
  v_tableSpaceSize in number,
  v_jedisStatus in number,
  v_cacheMaxMemory in number,
  v_cacheUsedMemory in number,
  v_resourceMonitoringSaveData in number,
  v_totalMemoryUsage in number,
  v_tomcatPhysicalMemory in number,
  v_JVMMemory in number,
  v_JVMHeapMemory in number,
  v_JVMNonHeapMemory in number,
  v_oraclePhysicalMemory in number
  
  ) is
  p_msg varchar2(3000) := 'error';
  counts number :=0;
begin
  select count(1) into counts from tbl_resourcemonitoring;
  if counts>v_resourceMonitoringSaveData then
    delete from TBL_RESOURCEMONITORING where id not in (select id from (select id from TBL_RESOURCEMONITORING t order by t.acqtime desc) v where rownum <=v_resourceMonitoringSaveData);
    commit;
    update TBL_RESOURCEMONITORING t
    set t.acqtime=to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
        t.acrunstatus=v_acRunStatus,t.acversion=v_acVersion,t.cpuusedpercent=v_cpuUsedPercent,
        t.adrunstatus=v_adRunStatus,t.adversion=v_adVersion,
        t.memusedpercent=v_memUsedPercent,t.tablespacesize=v_tableSpaceSize,
        t.jedisStatus=v_jedisStatus,
        t.cachemaxmemory=v_cacheMaxMemory,
        t.cacheusedmemory=v_cacheUsedMemory,
        t.totalmemoryusage=v_totalMemoryUsage,
        t.tomcatphysicalmemory=v_tomcatPhysicalMemory,
        t.jvmmemoryusage=v_JVMMemory,
        t.jvmheapmemoryusage=v_JVMHeapMemory,
        t.jvmnonheapmemoryusage=v_JVMNonHeapMemory,
        t.oraclephysicalmemory=v_oraclePhysicalMemory
    where t.id=(select id from (select id from TBL_RESOURCEMONITORING  order by acqtime ) where rownum=1);
    commit;
     p_msg := '删除多余记录并更新成功';
  elsif counts=v_resourceMonitoringSaveData then
    update TBL_RESOURCEMONITORING t
    set t.acqtime=to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
        t.acrunstatus=v_acRunStatus,t.acversion=v_acVersion,t.cpuusedpercent=v_cpuUsedPercent,
        t.adrunstatus=v_adRunStatus,t.adversion=v_adVersion,
        t.memusedpercent=v_memUsedPercent,t.tablespacesize=v_tableSpaceSize,
        t.jedisStatus=v_jedisStatus,
        t.cachemaxmemory=v_cacheMaxMemory,
        t.cacheusedmemory=v_cacheUsedMemory,
        t.totalmemoryusage=v_totalMemoryUsage,
        t.tomcatphysicalmemory=v_tomcatPhysicalMemory,
        t.jvmmemoryusage=v_JVMMemory,
        t.jvmheapmemoryusage=v_JVMHeapMemory,
        t.jvmnonheapmemoryusage=v_JVMNonHeapMemory,
        t.oraclephysicalmemory=v_oraclePhysicalMemory
    where t.id=(select id from (select id from TBL_RESOURCEMONITORING  order by acqtime ) where rownum=1);
    commit;
    p_msg := '更新成功';
   elsif counts<v_resourceMonitoringSaveData then
     insert into tbl_resourcemonitoring (
         acqtime,acrunstatus,acversion,cpuusedpercent,adrunstatus,adversion,memusedpercent,tablespacesize,
         jedisStatus,cachemaxmemory,cacheusedmemory,totalmemoryusage,
         tomcatphysicalmemory,jvmmemoryusage,jvmheapmemoryusage,jvmnonheapmemoryusage,
         oraclephysicalmemory
      )values(
         to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
         v_acRunStatus,
         v_acVersion,
         v_cpuUsedPercent,
         v_adRunStatus,
         v_adVersion,
         v_memUsedPercent,
         v_tableSpaceSize,
         v_jedisStatus,
         v_cacheMaxMemory,
         v_cacheUsedMemory,
         v_totalMemoryUsage,
         v_tomcatPhysicalMemory,
         v_JVMMemory,
         v_JVMHeapMemory,
         v_JVMNonHeapMemory,
         v_oraclePhysicalMemory
      );
      commit;
      p_msg := '插入成功';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_resourcemonitoring;
/

CREATE OR REPLACE PROCEDURE prd_save_smsdevice (v_orgname   in varchar2,
                                                    v_wellName    in varchar2,
                                                    v_instance    in varchar2,
                                                    v_signInId    in varchar2,
                                                    v_sortNum  in NUMBER,
                                                    v_orgId in varchar2) as
  wellcount number :=0;
  smsOrgId number :=0;
  orgcount number :=0;
  otherCount number :=0;
  p_msg varchar2(3000) := 'error';
  p_sql varchar2(3000);
begin
  p_sql:='select count(*)  from tbl_org t where t.org_name='''||v_orgname||''' and t.org_id in ('||v_orgId||')';
  EXECUTE IMMEDIATE p_sql into orgcount;
  p_sql:='select count(*)  from tbl_smsdevice t where t.wellname='''||v_wellName||''' and  t.orgid not in ('||v_orgId||')';
  EXECUTE IMMEDIATE p_sql into otherCount;
  if orgcount=1 and otherCount=0 then
    p_sql:='select t.org_id  from tbl_org t where t.org_name='''||v_orgname||''' and t.org_id in ('||v_orgId||')';
    EXECUTE IMMEDIATE p_sql into smsOrgId;
    select count(*) into wellcount from tbl_smsdevice t where t.deviceName=v_wellName;
    if wellcount>0 then
      Update tbl_smsdevice t set
               t.orgid=smsOrgId,
               t.instancecode=(select t2.code from tbl_protocolsmsinstance t2 where t2.name=v_instance and rownum=1),
               t.signinid=v_signInId,
               t.sortnum=v_sortNum
           Where t.deviceName=v_wellName;
           commit;
           p_msg := '修改成功';
    elsif wellcount=0 then
      insert into tbl_smsdevice(orgId,deviceName,signinid,Sortnum)
      values(smsOrgId,v_wellName,v_signInId,v_sortNum);
      commit;
      update tbl_smsdevice t set
             t.instancecode=(select t2.code from tbl_protocolsmsinstance t2 where t2.name=v_instance and rownum=1)
      Where t.deviceName=v_wellName;
      commit;
      p_msg := '添加成功';
    end if;
  else
    p_msg:='无权限';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_smsdevice;
/

CREATE OR REPLACE PROCEDURE prd_save_srp_diagram (
       v_wellId in NUMBER,v_AcqTime in varchar2,
       v_productionData in varchar2,v_balanceInfo in varchar2,v_pumpingModelId in NUMBER,
       v_fesdiagramAcqTime in varchar2,v_fesdiagramSrc in NUMBER,v_STROKE in NUMBER,v_SPM in NUMBER,
       v_POSITION_CURVE in tbl_srpacqdata_hist.POSITION_CURVE%TYPE,
       v_LOAD_CURVE in tbl_srpacqdata_hist.LOAD_CURVE%TYPE,
       v_POWER_CURVE in tbl_srpacqdata_hist.POWER_CURVE%TYPE,
       v_CURRENT_CURVE in tbl_srpacqdata_hist.CURRENT_CURVE%TYPE,
       v_ResultStatus in NUMBER,
       v_Fmax in NUMBER,v_Fmin in NUMBER,
       v_UpStrokeIMax in NUMBER,v_DownStrokeIMax in NUMBER,v_UPStrokeWattMax in NUMBER,v_DownStrokeWattMax in NUMBER,v_IDegreeBalance in NUMBER,v_WattDegreeBalance in NUMBER,
       v_DeltaRadius in NUMBER,
       v_ResultCode in NUMBER,
       v_FullnessCoefficient in NUMBER,
       v_NoLiquidFullnessCoefficient in NUMBER,
       v_PlungerStroke in NUMBER,v_AvailablePlungerStroke in NUMBER,v_NoLiquidAvailableStroke  in NUMBER,
       v_UpperLoadLine in NUMBER,v_UpperLoadLineOfExact in NUMBER,v_LowerLoadLine in NUMBER,
       v_SMaxIndex in NUMBER,v_SMinIndex in NUMBER,
       v_PumpFSDiagram in tbl_srpacqdata_hist.PumpFSDiagram%TYPE,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_AvailablePlungerStrokeProd_V in NUMBER,v_PumpClearanceLeakProd_V in NUMBER,
       v_TVLeakVolumetricProduction in NUMBER,v_SVLeakVolumetricProduction in NUMBER,v_GasInfluenceProd_V in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_AvailablePlungerStrokeProd_W in NUMBER,v_PumpClearanceLeakProd_W in NUMBER,
       v_TVLeakWeightProduction in NUMBER,v_SVLeakWeightProduction in NUMBER,v_GasInfluenceProd_W in NUMBER,

       v_LevelDifferenceValue in NUMBER,v_CalcProducingfluidLevel in NUMBER,

       v_Submergence in NUMBER,

       v_averagewatt in NUMBER,v_PolishRodPower in NUMBER,v_WaterPower in NUMBER,
       v_SurfaceSystemEfficiency in NUMBER,v_WellDownSystemEfficiency in NUMBER,v_SystemEfficiency in NUMBER,
       v_energyper100mlift in NUMBER,v_area in NUMBER,
       v_RodFlexLength in NUMBER,v_TubingFlexLength in NUMBER,v_InertiaLength in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff3 in NUMBER,v_PumpEff4 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2,
       v_crankAngle in tbl_srpacqdata_hist.crankangle%TYPE,
       v_polishRodV in tbl_srpacqdata_hist.polishrodv%TYPE,
       v_polishRodA in tbl_srpacqdata_hist.polishroda%TYPE,
       v_PR in tbl_srpacqdata_hist.pr%TYPE,
       v_TF in tbl_srpacqdata_hist.tf%TYPE,
       v_loadTorque in tbl_srpacqdata_hist.loadtorque%TYPE,
       v_crankTorque in tbl_srpacqdata_hist.cranktorque%TYPE,
       v_currentBalanceTorque in tbl_srpacqdata_hist.currentbalancetorque%TYPE,
       v_currentNetTorque in tbl_srpacqdata_hist.currentnettorque%TYPE,
       v_expectedBalanceTorque in tbl_srpacqdata_hist.expectedbalancetorque%TYPE,
       v_expectedNetTorque in tbl_srpacqdata_hist.expectednettorque%TYPE,
       v_wellboreSlice in tbl_srpacqdata_hist.wellboreslice%TYPE,
       v_rpm in NUMBER) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_srpacqdata_latest t
      set t.fesdiagramacqtime=to_date(v_fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss'),
          t.fesdiagramsrc=v_fesdiagramSrc,
          t.productiondata=v_productionData,t.balanceinfo=v_balanceInfo,t.pumpingmodelid=v_pumpingModelId,
          t.stroke=v_STROKE,t.spm=v_SPM,
          t.position_curve=v_POSITION_CURVE,t.load_curve=v_LOAD_CURVE,
          t.power_curve=v_POWER_CURVE,t.current_curve=v_CURRENT_CURVE,
          t.resultstatus=v_ResultStatus,
          t.fmax=v_Fmax,t.fmin=v_Fmin,
          t.upstrokeimax=v_UpStrokeIMax,t.downstrokeimax=v_DownStrokeIMax,t.upstrokewattmax=v_UPStrokeWattMax,t.downstrokewattmax=v_DownStrokeWattMax,t.idegreebalance=v_IDegreeBalance,t.wattdegreebalance=v_WattDegreeBalance,
          t.deltaradius=v_DeltaRadius,
          t.resultcode=v_ResultCode,
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
          t.LevelDifferenceValue=v_LevelDifferenceValue,t.CalcProducingfluidLevel=v_CalcProducingfluidLevel,
          t.submergence=v_Submergence,
          t.averagewatt=v_averagewatt,t.polishrodpower=v_PolishRodPower,t.waterpower=v_WaterPower,
          t.surfacesystemefficiency=v_SurfaceSystemEfficiency,t.welldownsystemefficiency=v_WellDownSystemEfficiency,t.systemefficiency=v_SystemEfficiency,
          t.energyper100mlift=v_energyper100mlift,t.area=v_area,
          t.rodflexlength=v_RodFlexLength,t.tubingflexlength=v_TubingFlexLength,t.inertialength=v_InertiaLength,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff3=v_PumpEff3,t.pumpeff4=v_PumpEff4,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.crankangle=v_crankAngle,t.polishrodv=v_polishRodV,t.polishroda=v_polishRodA,t.pr=v_PR,t.tf=v_TF,
          t.loadtorque=v_loadTorque,t.cranktorque=v_crankTorque,t.currentbalancetorque=v_currentBalanceTorque,t.currentnettorque=v_currentNetTorque,
          t.expectedbalancetorque=v_expectedBalanceTorque,t.expectednettorque=v_expectedNetTorque,
          t.wellboreslice=v_wellboreSlice,
          t.rpm=v_rpm,
          t.realtimeliquidvolumetricproduction=v_LiquidVolumetricProduction,
          t.realtimeoilvolumetricproduction=v_OilVolumetricProduction,t.realtimewatervolumetricproduction=v_WaterVolumetricProduction,
          t.realtimeliquidweightproduction=v_LiquidWeightProduction,
          t.realtimeoilweightproduction=v_OilWeightProduction,t.realtimewaterweightproduction=v_WaterWeightProduction
      where t.deviceid=v_wellId;
  commit;

  update tbl_srpacqdata_hist t
      set t.fesdiagramacqtime=to_date(v_fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss'),
          t.fesdiagramsrc=v_fesdiagramSrc,
          t.productiondata=v_productionData,t.balanceinfo=v_balanceInfo,t.pumpingmodelid=v_pumpingModelId,
          t.stroke=v_STROKE,t.spm=v_SPM,
          t.position_curve=v_POSITION_CURVE,t.load_curve=v_LOAD_CURVE,
          t.power_curve=v_POWER_CURVE,t.current_curve=v_CURRENT_CURVE,
          t.resultstatus=v_ResultStatus,
          t.fmax=v_Fmax,t.fmin=v_Fmin,
          t.upstrokeimax=v_UpStrokeIMax,t.downstrokeimax=v_DownStrokeIMax,t.upstrokewattmax=v_UPStrokeWattMax,t.downstrokewattmax=v_DownStrokeWattMax,t.idegreebalance=v_IDegreeBalance,t.wattdegreebalance=v_WattDegreeBalance,
          t.deltaradius=v_DeltaRadius,
          t.resultcode=v_ResultCode,
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
          t.LevelDifferenceValue=v_LevelDifferenceValue,t.CalcProducingfluidLevel=v_CalcProducingfluidLevel,
          t.submergence=v_Submergence,
          t.averagewatt=v_averagewatt,t.polishrodpower=v_PolishRodPower,t.waterpower=v_WaterPower,
          t.surfacesystemefficiency=v_SurfaceSystemEfficiency,t.welldownsystemefficiency=v_WellDownSystemEfficiency,t.systemefficiency=v_SystemEfficiency,
          t.energyper100mlift=v_energyper100mlift,t.area=v_area,
          t.rodflexlength=v_RodFlexLength,t.tubingflexlength=v_TubingFlexLength,t.inertialength=v_InertiaLength,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff3=v_PumpEff3,t.pumpeff4=v_PumpEff4,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.crankangle=v_crankAngle,t.polishrodv=v_polishRodV,t.polishroda=v_polishRodA,t.pr=v_PR,t.tf=v_TF,
          t.loadtorque=v_loadTorque,t.cranktorque=v_crankTorque,t.currentbalancetorque=v_currentBalanceTorque,t.currentnettorque=v_currentNetTorque,
          t.expectedbalancetorque=v_expectedBalanceTorque,t.expectednettorque=v_expectedNetTorque,
          t.wellboreslice=v_wellboreSlice,
          t.rpm=v_rpm,
          t.realtimeliquidvolumetricproduction=v_LiquidVolumetricProduction,
          t.realtimeoilvolumetricproduction=v_OilVolumetricProduction,t.realtimewatervolumetricproduction=v_WaterVolumetricProduction,
          t.realtimeliquidweightproduction=v_LiquidWeightProduction,
          t.realtimeoilweightproduction=v_OilWeightProduction,t.realtimewaterweightproduction=v_WaterWeightProduction
      where t.deviceid=v_wellId and t.acqtime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_srp_diagram;
/

CREATE OR REPLACE PROCEDURE prd_save_srp_diagramcaldata (
       v_recordId in NUMBER,
       v_ResultStatus in NUMBER,
       v_Fmax in NUMBER,v_Fmin in NUMBER,
       v_UpStrokeIMax in NUMBER,v_DownStrokeIMax in NUMBER,v_UPStrokeWattMax in NUMBER,v_DownStrokeWattMax in NUMBER,v_IDegreeBalance in NUMBER,v_WattDegreeBalance in NUMBER,
       v_DeltaRadius in NUMBER,
       v_ResultCode in NUMBER,
       v_FullnessCoefficient in NUMBER,
       v_NoLiquidFullnessCoefficient in NUMBER,
       v_PlungerStroke in NUMBER,v_AvailablePlungerStroke in NUMBER,v_NoLiquidAvailableStroke  in NUMBER,
       v_UpperLoadLine in NUMBER,v_UpperLoadLineOfExact in NUMBER,v_LowerLoadLine in NUMBER,
       v_SMaxIndex in NUMBER,v_SMinIndex in NUMBER,
       v_PumpFSDiagram in tbl_srpacqdata_hist.PumpFSDiagram%TYPE,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_AvailablePlungerStrokeProd_V in NUMBER,v_PumpClearanceLeakProd_V in NUMBER,
       v_TVLeakVolumetricProduction in NUMBER,v_SVLeakVolumetricProduction in NUMBER,v_GasInfluenceProd_V in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_AvailablePlungerStrokeProd_W in NUMBER,v_PumpClearanceLeakProd_W in NUMBER,
       v_TVLeakWeightProduction in NUMBER,v_SVLeakWeightProduction in NUMBER,v_GasInfluenceProd_W in NUMBER,

       v_LevelDifferenceValue in NUMBER,v_CalcProducingfluidLevel in NUMBER,

       v_Submergence in NUMBER,

       v_averagewatt in NUMBER,v_PolishRodPower in NUMBER,v_WaterPower in NUMBER,
       v_SurfaceSystemEfficiency in NUMBER,v_WellDownSystemEfficiency in NUMBER,v_SystemEfficiency in NUMBER,
       v_energyper100mlift in NUMBER,v_area in NUMBER,
       v_RodFlexLength in NUMBER,v_TubingFlexLength in NUMBER,v_InertiaLength in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff3 in NUMBER,v_PumpEff4 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2,
       v_crankAngle in tbl_srpacqdata_hist.crankangle%TYPE,
       v_polishRodV in tbl_srpacqdata_hist.polishrodv%TYPE,
       v_polishRodA in tbl_srpacqdata_hist.polishroda%TYPE,
       v_PR in tbl_srpacqdata_hist.pr%TYPE,
       v_TF in tbl_srpacqdata_hist.tf%TYPE,
       v_loadTorque in tbl_srpacqdata_hist.loadtorque%TYPE,
       v_crankTorque in tbl_srpacqdata_hist.cranktorque%TYPE,
       v_currentBalanceTorque in tbl_srpacqdata_hist.currentbalancetorque%TYPE,
       v_currentNetTorque in tbl_srpacqdata_hist.currentnettorque%TYPE,
       v_expectedBalanceTorque in tbl_srpacqdata_hist.expectedbalancetorque%TYPE,
       v_expectedNetTorque in tbl_srpacqdata_hist.expectednettorque%TYPE,
       v_wellboreSlice in tbl_srpacqdata_hist.wellboreslice%TYPE) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_srpacqdata_hist t
      set t.resultstatus=v_ResultStatus,
          t.fmax=v_Fmax,t.fmin=v_Fmin,
          t.upstrokeimax=v_UpStrokeIMax,t.downstrokeimax=v_DownStrokeIMax,t.upstrokewattmax=v_UPStrokeWattMax,t.downstrokewattmax=v_DownStrokeWattMax,t.idegreebalance=v_IDegreeBalance,t.wattdegreebalance=v_WattDegreeBalance,
          t.deltaradius=v_DeltaRadius,
          t.resultcode=v_ResultCode,
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
          t.LevelDifferenceValue=v_LevelDifferenceValue,t.CalcProducingfluidLevel=v_CalcProducingfluidLevel,
          t.submergence=v_Submergence,
          t.averagewatt=v_averagewatt,t.polishrodpower=v_PolishRodPower,t.waterpower=v_WaterPower,
          t.surfacesystemefficiency=v_SurfaceSystemEfficiency,t.welldownsystemefficiency=v_WellDownSystemEfficiency,t.systemefficiency=v_SystemEfficiency,
          t.energyper100mlift=v_energyper100mlift,t.area=v_area,
          t.rodflexlength=v_RodFlexLength,t.tubingflexlength=v_TubingFlexLength,t.inertialength=v_InertiaLength,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff3=v_PumpEff3,t.pumpeff4=v_PumpEff4,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.crankangle=v_crankAngle,t.polishrodv=v_polishRodV,t.polishroda=v_polishRodA,t.pr=v_PR,t.tf=v_TF,
          t.loadtorque=v_loadTorque,t.cranktorque=v_crankTorque,t.currentbalancetorque=v_currentBalanceTorque,t.currentnettorque=v_currentNetTorque,
          t.expectedbalancetorque=v_expectedBalanceTorque,t.expectednettorque=v_expectedNetTorque,
          t.wellboreslice=v_wellboreSlice,
          t.realtimeliquidvolumetricproduction=v_LiquidVolumetricProduction,
          t.realtimeoilvolumetricproduction=v_OilVolumetricProduction,t.realtimewatervolumetricproduction=v_WaterVolumetricProduction,
          t.realtimeliquidweightproduction=v_LiquidWeightProduction,
          t.realtimeoilweightproduction=v_OilWeightProduction,t.realtimewaterweightproduction=v_WaterWeightProduction
      where t.id=v_recordId;
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_srp_diagramcaldata;
/

CREATE OR REPLACE PROCEDURE prd_save_srp_diagramdaily (
  v_wellId in number,v_ResultStatus in number,
  v_resultcode in number,v_resultString in tbl_srpdailycalculationdata.resultstring%TYPE,
  v_ExtendedDays in number,
  v_Stroke in number,v_SPM in number,
  v_FMax in number,v_FMin in number,v_fullnessCoefficient in number,
  v_TheoreticalProduction in number,
  v_liquidVolumetricProduction in number,v_oilVolumetricProduction in number,v_waterVolumetricProduction in number,v_volumewatercut in number,
  v_liquidWeightProduction in number,v_oilWeightProduction in number,v_waterWeightProduction in number,v_weightwatercut in number,
  v_pumpEff in number,v_pumpEff1 in number,v_pumpEff2 in number,v_pumpEff3 in number,v_pumpEff4 in number,
  v_wellDownSystemEfficiency in number,v_surfaceSystemEfficiency in number,v_systemEfficiency in number,v_energyper100mlift in number,
  v_iDegreeBalance in number,v_wattDegreeBalance in number,v_DeltaRadius in number,
  v_pumpSettingDepth in number,
  v_producingfluidLevel in number,v_calcProducingfluidLevel in number,v_levelDifferenceValue in number,
  v_submergence in number,
  v_casingPressure in number,v_tubingPressure in number,
  v_rpm in number,
  v_commStatus in number,v_commTime in number,v_commTimeEfficiency in number,
  v_commRange in tbl_srpdailycalculationdata.commrange%TYPE,
  v_runStatus in number,v_runTime in number,v_runTimeEfficiency in number,
  v_runRange in tbl_srpdailycalculationdata.runrange%TYPE,
  v_calDate in varchar2,
  v_recordCount in number
  ) is
  p_msg varchar2(3000) := 'error';
  p_count number:=0;
begin
  select count(*) into p_count from tbl_srpdailycalculationdata t where t.deviceid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd') ;
  if p_count>0 then
    p_msg := '记录存在';
    update tbl_srpdailycalculationdata t
    set t.commstatus=v_commStatus,t.commtime=v_commTime,t.commtimeefficiency=v_commTimeEfficiency,t.commrange=v_commRange,
    t.runstatus=v_runStatus,t.runtime=v_runTime,t.runtimeefficiency=v_runTimeEfficiency,t.runrange=v_runRange
    where t.deviceid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd') ;
    commit;

    if v_recordCount>0 then
      update tbl_srpdailycalculationdata t
      set t.resultstatus=v_ResultStatus,t.resultcode=v_resultcode,t.resultstring=v_resultString,t.extendeddays=v_ExtendedDays,
          t.stroke=v_Stroke,t.spm=v_SPM,t.fmax=v_FMax,t.fmin=v_FMin,t.fullnesscoefficient=v_fullnessCoefficient,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_liquidVolumetricProduction,t.oilvolumetricproduction=v_oilVolumetricProduction,
          t.watervolumetricproduction=v_waterVolumetricProduction,t.volumewatercut=v_volumewatercut,
          t.liquidweightproduction=v_liquidWeightProduction,t.oilweightproduction=v_oilWeightProduction,
          t.waterweightproduction=v_waterWeightProduction,t.weightwatercut=v_weightwatercut,
          t.pumpeff=v_pumpEff,t.pumpeff1=v_pumpEff1,t.pumpeff2=v_pumpEff2,t.pumpeff3=v_pumpEff3,t.pumpeff4=v_pumpEff4,
          t.welldownsystemefficiency=v_wellDownSystemEfficiency,t.surfacesystemefficiency=v_surfaceSystemEfficiency,
          t.systemefficiency=v_systemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.idegreebalance=v_iDegreeBalance,t.wattdegreebalance=v_wattDegreeBalance,t.deltaradius=v_DeltaRadius,
          t.pumpsettingdepth=v_pumpSettingDepth,
          t.producingfluidlevel=v_producingfluidLevel,t.calcproducingfluidlevel=v_calcProducingfluidLevel,t.leveldifferencevalue=v_levelDifferenceValue,
          t.submergence=v_submergence,
          t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure,
          t.rpm=v_rpm
      where t.deviceid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd') ;
      commit;
    end if;

    p_msg := '更新成功';
  elsif p_count=0 then
    p_msg := '记录不存在';
    insert into tbl_srpdailycalculationdata(
           deviceid,caldate,resultstatus,resultcode,resultstring,extendeddays,
    stroke,spm,fmax,fmin,fullnesscoefficient,
    theoreticalproduction,
    liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,volumewatercut,
    liquidweightproduction,oilweightproduction,waterweightproduction,weightwatercut,
    pumpeff,pumpeff1,pumpeff2,pumpeff3,pumpeff4,
    welldownsystemefficiency,surfacesystemefficiency,systemefficiency,energyper100mlift,
    idegreebalance,wattdegreebalance,deltaradius,
    pumpsettingdepth,
    producingfluidlevel,calcproducingfluidlevel,leveldifferencevalue,
    submergence,
    casingpressure,tubingpressure,rpm,
    commstatus,commtime,commtimeefficiency,commrange,
    runstatus,runtime,runtimeefficiency,runrange
    )values(
    v_wellId,to_date(v_calDate,'yyyy-mm-dd'),
    v_ResultStatus,v_resultcode,v_resultString,v_ExtendedDays,
    v_Stroke,v_SPM,v_FMax,v_FMin,v_fullnessCoefficient,
    v_TheoreticalProduction,
    v_liquidVolumetricProduction,v_oilVolumetricProduction,v_waterVolumetricProduction,v_volumewatercut,
    v_liquidWeightProduction,v_oilWeightProduction,v_waterWeightProduction,v_weightwatercut,
    v_pumpEff,v_pumpEff1,v_pumpEff2,v_pumpEff3,v_pumpEff4,
    v_wellDownSystemEfficiency,v_surfaceSystemEfficiency,v_systemEfficiency,v_energyper100mlift,
    v_iDegreeBalance,v_wattDegreeBalance,v_DeltaRadius,
    v_pumpSettingDepth,
    v_producingfluidLevel,v_calcProducingfluidLevel,v_levelDifferenceValue,
    v_submergence,
    v_casingPressure,v_tubingPressure,v_rpm,
    v_commStatus,v_commTime,v_commTimeEfficiency,v_commRange,
    v_runStatus,v_runTime,v_runTimeEfficiency,v_runRange
    );
    commit;
    p_msg := '添加成功';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_srp_diagramdaily;
/

CREATE OR REPLACE PROCEDURE prd_save_srp_diagramdailyrecal (
  v_recordId in number,v_ResultStatus in number,
  v_resultcode in number,v_resultString in tbl_srpdailycalculationdata.resultstring%TYPE,
  v_ExtendedDays in number,
  v_Stroke in number,v_SPM in number,
  v_FMax in number,v_FMin in number,v_fullnessCoefficient in number,
  v_TheoreticalProduction in number,
  v_liquidVolumetricProduction in number,v_oilVolumetricProduction in number,v_waterVolumetricProduction in number,v_volumewatercut in number,
  v_liquidWeightProduction in number,v_oilWeightProduction in number,v_waterWeightProduction in number,v_weightwatercut in number,
  v_pumpEff in number,v_pumpEff1 in number,v_pumpEff2 in number,v_pumpEff3 in number,v_pumpEff4 in number,
  v_wellDownSystemEfficiency in number,v_surfaceSystemEfficiency in number,v_systemEfficiency in number,v_energyper100mlift in number,
  v_iDegreeBalance in number,v_wattDegreeBalance in number,v_DeltaRadius in number,
  v_pumpSettingDepth in number,
  v_producingfluidLevel in number,v_calcProducingfluidLevel in number,v_levelDifferenceValue in number,
  v_submergence in number,
  v_tubingPressure in number,v_casingPressure in number,
  v_rpm in number,
  v_recordCount in number
  ) is
  p_msg varchar2(3000) := 'error';
begin
  if v_recordCount>0 then
    update tbl_srpdailycalculationdata t
    set t.resultstatus=v_ResultStatus,t.resultcode=v_resultcode,t.resultstring=v_resultString,t.extendeddays=v_ExtendedDays,
        t.stroke=v_Stroke,t.spm=v_SPM,t.fmax=v_FMax,t.fmin=v_FMin,t.fullnesscoefficient=v_fullnessCoefficient,
        t.theoreticalproduction=v_TheoreticalProduction,
        t.liquidvolumetricproduction=v_liquidVolumetricProduction,t.oilvolumetricproduction=v_oilVolumetricProduction,
        t.watervolumetricproduction=v_waterVolumetricProduction,t.volumewatercut=v_volumewatercut,
        t.liquidweightproduction=v_liquidWeightProduction,t.oilweightproduction=v_oilWeightProduction,
        t.waterweightproduction=v_waterWeightProduction,t.weightwatercut=v_weightwatercut,
        t.pumpeff=v_pumpEff,t.pumpeff1=v_pumpEff1,t.pumpeff2=v_pumpEff2,t.pumpeff3=v_pumpEff3,t.pumpeff4=v_pumpEff4,
        t.welldownsystemefficiency=v_wellDownSystemEfficiency,t.surfacesystemefficiency=v_surfaceSystemEfficiency,
        t.systemefficiency=v_systemEfficiency,t.energyper100mlift=v_energyper100mlift,
        t.idegreebalance=v_iDegreeBalance,t.wattdegreebalance=v_wattDegreeBalance,t.deltaradius=v_DeltaRadius,
        t.pumpsettingdepth=v_pumpSettingDepth,
        t.producingfluidlevel=v_producingfluidLevel,t.calcproducingfluidlevel=v_calcProducingfluidLevel,t.leveldifferencevalue=v_levelDifferenceValue,
        t.submergence=v_submergence,
        t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure,
        t.rpm=v_rpm
    where t.id=v_recordId ;
    commit;
    p_msg := '更新成功';
    dbms_output.put_line('p_msg:' || p_msg);
  end if;
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_srp_diagramdailyrecal;
/

CREATE OR REPLACE PROCEDURE prd_save_srp_diagramtimingtotal (
  v_wellId in number,v_ResultStatus in number,
  v_resultcode in number,v_resultString in tbl_srpdailycalculationdata.resultstring%TYPE,
  v_ExtendedDays in number,
  v_Stroke in number,v_SPM in number,
  v_FMax in number,v_FMin in number,v_fullnessCoefficient in number,
  v_TheoreticalProduction in number,
  v_liquidVolumetricProduction in number,v_oilVolumetricProduction in number,v_waterVolumetricProduction in number,v_volumewatercut in number,
  v_liquidWeightProduction in number,v_oilWeightProduction in number,v_waterWeightProduction in number,v_weightwatercut in number,
  v_pumpEff in number,v_pumpEff1 in number,v_pumpEff2 in number,v_pumpEff3 in number,v_pumpEff4 in number,
  v_wellDownSystemEfficiency in number,v_surfaceSystemEfficiency in number,v_systemEfficiency in number,v_energyper100mlift in number,
  v_iDegreeBalance in number,v_wattDegreeBalance in number,v_DeltaRadius in number,
  v_pumpSettingDepth in number,
  v_producingfluidLevel in number,v_calcProducingfluidLevel in number,v_levelDifferenceValue in number,
  v_submergence in number,
  v_casingPressure in number,v_tubingPressure in number,
  v_rpm in number,

  v_commStatus in number,v_commTime in number,v_commTimeEfficiency in number,
  v_commRange in tbl_srptimingcalculationdata.commrange%TYPE,
  v_runStatus in number,v_runTime in number,v_runTimeEfficiency in number,
  v_runRange in tbl_srptimingcalculationdata.runrange%TYPE,
  v_calTime in varchar2,
  v_recordCount in number
  ) is
  p_msg varchar2(3000) := 'error';
  p_count number:=0;
begin
  select count(1) into p_count from tbl_srptimingcalculationdata t where t.deviceid=v_wellId and t.caltime=to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss') ;
  if p_count>0 then
    p_msg := '记录存在';
    update tbl_srptimingcalculationdata t
    set t.commstatus=v_commStatus,t.commtime=v_commTime,t.commtimeefficiency=v_commTimeEfficiency,t.commrange=v_commRange,
    t.runstatus=v_runStatus,t.runtime=v_runTime,t.runtimeefficiency=v_runTimeEfficiency,t.runrange=v_runRange
    where t.deviceid=v_wellId and t.caltime=to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss') ;
    commit;

    if v_recordCount>0 then
      update tbl_srptimingcalculationdata t
      set t.resultstatus=v_ResultStatus,t.resultcode=v_resultcode,t.resultstring=v_resultString,t.extendeddays=v_ExtendedDays,
          t.stroke=v_Stroke,t.spm=v_SPM,t.fmax=v_FMax,t.fmin=v_FMin,t.fullnesscoefficient=v_fullnessCoefficient,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_liquidVolumetricProduction,t.oilvolumetricproduction=v_oilVolumetricProduction,
          t.watervolumetricproduction=v_waterVolumetricProduction,t.volumewatercut=v_volumewatercut,
          t.liquidweightproduction=v_liquidWeightProduction,t.oilweightproduction=v_oilWeightProduction,
          t.waterweightproduction=v_waterWeightProduction,t.weightwatercut=v_weightwatercut,
          t.pumpeff=v_pumpEff,t.pumpeff1=v_pumpEff1,t.pumpeff2=v_pumpEff2,t.pumpeff3=v_pumpEff3,t.pumpeff4=v_pumpEff4,
          t.welldownsystemefficiency=v_wellDownSystemEfficiency,t.surfacesystemefficiency=v_surfaceSystemEfficiency,
          t.systemefficiency=v_systemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.idegreebalance=v_iDegreeBalance,t.wattdegreebalance=v_wattDegreeBalance,t.deltaradius=v_DeltaRadius,
          t.pumpsettingdepth=v_pumpSettingDepth,
          t.producingfluidlevel=v_producingfluidLevel,t.calcproducingfluidlevel=v_calcProducingfluidLevel,t.leveldifferencevalue=v_levelDifferenceValue,
          t.submergence=v_submergence,
          t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure,
          t.rpm=v_rpm
      where t.deviceid=v_wellId and t.caltime=to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss') ;
      commit;
      --更新实时计算数据
      update tbl_srptimingcalculationdata t set
             (
             t.resultcode,t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,
             t.theoreticalproduction,
             t.realtimeliquidvolumetricproduction,t.realtimeoilvolumetricproduction,t.realtimewatervolumetricproduction,
             t.realtimeliquidweightproduction,t.realtimeoilweightproduction,t.realtimewaterweightproduction,
             t.pumpeff,t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,
             t.wattdegreebalance,t.idegreebalance,t.deltaradius,
             t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.energyper100mlift,
             t.calcProducingfluidLevel,t.levelDifferenceValue,
             t.submergence,
             t.rpm
             )
             =(
             select
             t2.resultcode,t2.stroke,t2.spm,t2.fmax,t2.fmin,t2.fullnesscoefficient,
             t2.theoreticalproduction,
             t2.realtimeliquidvolumetricproduction,t2.realtimeoilvolumetricproduction,t2.realtimewatervolumetricproduction,
             t2.realtimeliquidweightproduction,t2.realtimeoilweightproduction,t2.realtimewaterweightproduction,
             t2.pumpeff,t2.pumpeff1,t2.pumpeff2,t2.pumpeff3,t2.pumpeff4,
             t2.wattdegreebalance,t2.idegreebalance,t2.deltaradius,
             t2.surfacesystemefficiency,t2.welldownsystemefficiency,t2.systemefficiency,t2.energyper100mlift,
             t2.calcProducingfluidLevel,t2.levelDifferenceValue,
             t2.submergence,
             t2.rpm
             from tbl_srpacqdata_hist t2
             where t2.id=(
                   select v2.id from
                   (select v.id,rownum r from
                   (select t3.id from  tbl_srpacqdata_hist t3
                   where t3.commstatus=1 and t3.resultstatus=1
                   and t3.acqtime between to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss')-1 and  to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss')
                   and t3.deviceid=v_wellId
                   order by t3.acqtime desc) v
                   ) v2
                   where r=1
             )
             and t2.deviceid=v_wellId )
             where t.deviceid=v_wellId and t.caltime=to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss');
       commit;
    end if;
    p_msg := '更新成功';
  elsif p_count=0 then
    p_msg := '记录不存在';
    insert into tbl_srptimingcalculationdata(
           deviceid,caltime,resultstatus,resultcode,resultstring,extendeddays,
    stroke,spm,fmax,fmin,fullnesscoefficient,
    theoreticalproduction,
    liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,volumewatercut,
    liquidweightproduction,oilweightproduction,waterweightproduction,weightwatercut,
    pumpeff,pumpeff1,pumpeff2,pumpeff3,pumpeff4,
    welldownsystemefficiency,surfacesystemefficiency,systemefficiency,energyper100mlift,
    idegreebalance,wattdegreebalance,deltaradius,
    pumpsettingdepth,
    producingfluidlevel,calcproducingfluidlevel,leveldifferencevalue,
    submergence,
    casingpressure,tubingpressure,
    commstatus,commtime,commtimeefficiency,commrange,
    runstatus,runtime,runtimeefficiency,runrange
    )values(
    v_wellId,to_date(v_calTime,'yyyy-mm-dd hh24:mi:ss'),
    v_ResultStatus,v_resultcode,v_resultString,v_ExtendedDays,
    v_Stroke,v_SPM,v_FMax,v_FMin,v_fullnessCoefficient,
    v_TheoreticalProduction,
    v_liquidVolumetricProduction,v_oilVolumetricProduction,v_waterVolumetricProduction,v_volumewatercut,
    v_liquidWeightProduction,v_oilWeightProduction,v_waterWeightProduction,v_weightwatercut,
    v_pumpEff,v_pumpEff1,v_pumpEff2,v_pumpEff3,v_pumpEff4,
    v_wellDownSystemEfficiency,v_surfaceSystemEfficiency,v_systemEfficiency,v_energyper100mlift,
    v_iDegreeBalance,v_wattDegreeBalance,v_DeltaRadius,
    v_pumpSettingDepth,
    v_producingfluidLevel,v_calcProducingfluidLevel,v_levelDifferenceValue,
    v_submergence,
    v_casingPressure,v_tubingPressure,
    v_commStatus,v_commTime,v_commTimeEfficiency,v_commRange,
    v_runStatus,v_runTime,v_runTimeEfficiency,v_runRange
    );
    commit;
    p_msg := '添加成功';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_srp_diagramtimingtotal;
/

CREATE OR REPLACE PROCEDURE prd_save_srp_diagram_vacuate (
       v_wellId in NUMBER,v_AcqTime in varchar2,
       v_productionData in varchar2,v_balanceInfo in varchar2,v_pumpingModelId in NUMBER,
       v_fesdiagramAcqTime in varchar2,v_fesdiagramSrc in NUMBER,v_STROKE in NUMBER,v_SPM in NUMBER,
       v_POSITION_CURVE in tbl_srpacqdata_vacuate.POSITION_CURVE%TYPE,
       v_LOAD_CURVE in tbl_srpacqdata_vacuate.LOAD_CURVE%TYPE,
       v_POWER_CURVE in tbl_srpacqdata_vacuate.POWER_CURVE%TYPE,
       v_CURRENT_CURVE in tbl_srpacqdata_vacuate.CURRENT_CURVE%TYPE,
       v_ResultStatus in NUMBER,
       v_Fmax in NUMBER,v_Fmin in NUMBER,
       v_UpStrokeIMax in NUMBER,v_DownStrokeIMax in NUMBER,v_UPStrokeWattMax in NUMBER,v_DownStrokeWattMax in NUMBER,v_IDegreeBalance in NUMBER,v_WattDegreeBalance in NUMBER,
       v_DeltaRadius in NUMBER,
       v_ResultCode in NUMBER,
       v_FullnessCoefficient in NUMBER,
       v_NoLiquidFullnessCoefficient in NUMBER,
       v_PlungerStroke in NUMBER,v_AvailablePlungerStroke in NUMBER,v_NoLiquidAvailableStroke  in NUMBER,
       v_UpperLoadLine in NUMBER,v_UpperLoadLineOfExact in NUMBER,v_LowerLoadLine in NUMBER,
       v_SMaxIndex in NUMBER,v_SMinIndex in NUMBER,
       v_PumpFSDiagram in tbl_srpacqdata_vacuate.PumpFSDiagram%TYPE,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_AvailablePlungerStrokeProd_V in NUMBER,v_PumpClearanceLeakProd_V in NUMBER,
       v_TVLeakVolumetricProduction in NUMBER,v_SVLeakVolumetricProduction in NUMBER,v_GasInfluenceProd_V in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_AvailablePlungerStrokeProd_W in NUMBER,v_PumpClearanceLeakProd_W in NUMBER,
       v_TVLeakWeightProduction in NUMBER,v_SVLeakWeightProduction in NUMBER,v_GasInfluenceProd_W in NUMBER,

       v_LevelDifferenceValue in NUMBER,v_CalcProducingfluidLevel in NUMBER,

       v_Submergence in NUMBER,

       v_averagewatt in NUMBER,v_PolishRodPower in NUMBER,v_WaterPower in NUMBER,
       v_SurfaceSystemEfficiency in NUMBER,v_WellDownSystemEfficiency in NUMBER,v_SystemEfficiency in NUMBER,
       v_energyper100mlift in NUMBER,v_area in NUMBER,
       v_RodFlexLength in NUMBER,v_TubingFlexLength in NUMBER,v_InertiaLength in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff3 in NUMBER,v_PumpEff4 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2,
       v_crankAngle in tbl_srpacqdata_vacuate.crankangle%TYPE,
       v_polishRodV in tbl_srpacqdata_vacuate.polishrodv%TYPE,
       v_polishRodA in tbl_srpacqdata_vacuate.polishroda%TYPE,
       v_PR in tbl_srpacqdata_vacuate.pr%TYPE,
       v_TF in tbl_srpacqdata_vacuate.tf%TYPE,
       v_loadTorque in tbl_srpacqdata_vacuate.loadtorque%TYPE,
       v_crankTorque in tbl_srpacqdata_vacuate.cranktorque%TYPE,
       v_currentBalanceTorque in tbl_srpacqdata_vacuate.currentbalancetorque%TYPE,
       v_currentNetTorque in tbl_srpacqdata_vacuate.currentnettorque%TYPE,
       v_expectedBalanceTorque in tbl_srpacqdata_vacuate.expectedbalancetorque%TYPE,
       v_expectedNetTorque in tbl_srpacqdata_vacuate.expectednettorque%TYPE,
       v_wellboreSlice in tbl_srpacqdata_vacuate.wellboreslice%TYPE,
       v_rpm in NUMBER) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_srpacqdata_vacuate t
      set t.fesdiagramacqtime=to_date(v_fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss'),
          t.fesdiagramsrc=v_fesdiagramSrc,
          t.productiondata=v_productionData,t.balanceinfo=v_balanceInfo,t.pumpingmodelid=v_pumpingModelId,
          t.stroke=v_STROKE,t.spm=v_SPM,
          t.position_curve=v_POSITION_CURVE,t.load_curve=v_LOAD_CURVE,
          t.power_curve=v_POWER_CURVE,t.current_curve=v_CURRENT_CURVE,
          t.resultstatus=v_ResultStatus,
          t.fmax=v_Fmax,t.fmin=v_Fmin,
          t.upstrokeimax=v_UpStrokeIMax,t.downstrokeimax=v_DownStrokeIMax,t.upstrokewattmax=v_UPStrokeWattMax,t.downstrokewattmax=v_DownStrokeWattMax,t.idegreebalance=v_IDegreeBalance,t.wattdegreebalance=v_WattDegreeBalance,
          t.deltaradius=v_DeltaRadius,
          t.resultcode=v_ResultCode,
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
          t.LevelDifferenceValue=v_LevelDifferenceValue,t.CalcProducingfluidLevel=v_CalcProducingfluidLevel,
          t.submergence=v_Submergence,
          t.averagewatt=v_averagewatt,t.polishrodpower=v_PolishRodPower,t.waterpower=v_WaterPower,
          t.surfacesystemefficiency=v_SurfaceSystemEfficiency,t.welldownsystemefficiency=v_WellDownSystemEfficiency,t.systemefficiency=v_SystemEfficiency,
          t.energyper100mlift=v_energyper100mlift,t.area=v_area,
          t.rodflexlength=v_RodFlexLength,t.tubingflexlength=v_TubingFlexLength,t.inertialength=v_InertiaLength,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff3=v_PumpEff3,t.pumpeff4=v_PumpEff4,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString,
          t.crankangle=v_crankAngle,t.polishrodv=v_polishRodV,t.polishroda=v_polishRodA,t.pr=v_PR,t.tf=v_TF,
          t.loadtorque=v_loadTorque,t.cranktorque=v_crankTorque,t.currentbalancetorque=v_currentBalanceTorque,t.currentnettorque=v_currentNetTorque,
          t.expectedbalancetorque=v_expectedBalanceTorque,t.expectednettorque=v_expectedNetTorque,
          t.wellboreslice=v_wellboreSlice,
          t.rpm=v_rpm,
          t.realtimeliquidvolumetricproduction=v_LiquidVolumetricProduction,
          t.realtimeoilvolumetricproduction=v_OilVolumetricProduction,t.realtimewatervolumetricproduction=v_WaterVolumetricProduction,
          t.realtimeliquidweightproduction=v_LiquidWeightProduction,
          t.realtimeoilweightproduction=v_OilWeightProduction,t.realtimewaterweightproduction=v_WaterWeightProduction
      where t.deviceid=v_wellId and t.acqtime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_srp_diagram_vacuate;
/

CREATE OR REPLACE PROCEDURE prd_save_systemLog (
  v_time in varchar2,
  v_action in number,
  v_userId in varchar2,
  v_loginIp in varchar2,
  v_remark in varchar2
  ) is
  p_msg varchar2(3000) := 'error';
begin

  insert into tbl_systemlog (createtime,action,user_id,loginip,remark)
  values(
         to_date(v_time,'yyyy-mm-dd hh24:mi:ss'),
         v_action,
         v_userId,
         v_loginIp,
         v_remark
      );
      commit;
      p_msg := '插入成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_systemLog;
/

CREATE OR REPLACE PROCEDURE prd_update_auxiliarydevice (
                                                       v_id in number,
                                                       v_name in varchar2,
                                                       v_type in number,
                                                       v_model in varchar2,
                                                       v_remark in varchar2,
                                                       v_sort in number,
                                                       v_result out NUMBER,
                                                       v_resultstr out varchar2
                                                       ) is
  p_msg varchar2(3000) := 'error';
  p_typeName varchar2(3000) := '泵辅件';
  counts number :=0;
begin
  select count(1) into counts from tbl_auxiliarydevice t where t.name=v_name and t.type=v_type and t.model=v_model and t.id<>v_id;
  if counts=0 then
    update tbl_auxiliarydevice t set t.remark=v_remark,t.sort=v_sort,t.name=v_name,t.model=v_model,t.type=v_type
    where t.id=v_id;
    commit;
    v_result:=1;
    v_resultstr := '修改成功';
    p_msg := '修改成功';
  else
    if v_type<>0 then
      p_typeName:='管辅件';
    end if;
    v_result:=-22;
    v_resultstr :='规格型号:'||v_model||',名称:'||v_name||'的'||p_typeName||'设备已存在，保存无效';
    p_msg :='规格型号:'||v_model||',名称:'||v_name||'的'||p_typeName||'设备已存在，保存无效';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_update_auxiliarydevice;
/

CREATE OR REPLACE PROCEDURE prd_update_device ( v_recordId in NUMBER,
                                                    v_deviceName    in varchar2,
                                                    v_applicationScenarios    in NUMBER,
                                                    v_deviceTabInstance   in varchar2,
                                                    v_instance    in varchar2,
                                                    v_displayInstance    in varchar2,
                                                    v_reportInstance    in varchar2,
                                                    v_alarmInstance    in varchar2,
                                                    v_tcpType    in varchar2,
                                                    v_signInId    in varchar2,
                                                    v_ipPort    in varchar2,
                                                    v_slave   in varchar2,
                                                    v_peakDelay in NUMBER,
                                                    v_status in NUMBER,
                                                    v_commissioningDate in varchar2,
                                                    v_sortNum  in NUMBER,
                                                    v_result out NUMBER,
                                                    v_resultstr out varchar2) as
  wellcount number :=0;
  othercount number :=0;
  otherDeviceAllPath varchar2(3000) := '';
  p_msg varchar2(3000) := 'error';
begin
  --验证权限
  select count(1) into wellcount from tbl_device t
  where t.devicename=v_deviceName and t.id<>v_recordId
  and t.orgid=( select t2.orgid from tbl_device t2 where t2.id=v_recordId);
    if wellcount=0 then
        select count(1) into othercount from tbl_device t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave)
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null
        and t.id<>v_recordId;
        if v_recordId >0 and othercount=0 then
          Update tbl_device t
           Set t.devicename=v_deviceName,
               t.applicationscenarios=v_applicationScenarios,
               t.calculatetype=(select t2.id from tbl_tabmanager_device t2 where t2.name=v_deviceTabInstance and rownum=1),
               t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and rownum=1),
               t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and rownum=1),
               t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and rownum=1),
               t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and rownum=1),
               t.tcptype=v_tcpType,t.ipport=v_ipPort,t.signinid=v_signInId,t.slave=v_slave,t.peakdelay=v_peakDelay,
               t.status=v_status,
               t.commissioningdate=to_date(v_commissioningDate,'yyyy-mm-dd'),
               t.sortnum=v_sortNum,
               t.productiondataupdatetime=sysdate
           Where t.id=v_recordId;
           commit;
           v_result:=1;
           v_resultstr := '修改成功';
           p_msg := '修改成功';
        elsif othercount>0 then
          select substr(v.path_zh_cn||'/'||t.devicename,2) into otherDeviceAllPath
          from tbl_device t,
          (select org.org_id,
          sys_connect_by_path(org.org_name_zh_cn,'/') as path_zh_cn,
          sys_connect_by_path(org.org_name_en,'/') as path_en,
          sys_connect_by_path(org.org_name_ru,'/') as path_ru
          from tbl_org org
          start with org.org_parent=0
          connect by   org.org_parent= prior org.org_id) v
          where t.orgid=v.org_id
          and t.id=(select t2.id from tbl_device t2
              where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
              and to_number(t2.slave)=to_number(v_slave)
              and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null
              and t2.id<>v_recordId);
          v_result:=-22;
          v_resultstr :='设备'||v_deviceName||'注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突，保存无效';
          p_msg := '设备'||v_deviceName||'注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突，保存无效';
        end if;
    else
      v_result:=-33;
      v_resultstr :='同组织下已存在设备'||v_deviceName||'，保存无效';
      p_msg := '同组织下已存在设备'||v_deviceName||'，保存无效';
    end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_update_device;
/

CREATE OR REPLACE PROCEDURE prd_update_pcp_rpm_latest (v_deviceId in NUMBER) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_pcpacqdata_latest t set (acqtime,
  rpm,
  productiondata,resultstatus,
  resultcode,
  theoreticalproduction,
  liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,
  liquidweightproduction,oilweightproduction,waterweightproduction,
  submergence,
  averagewatt,waterpower,
  systemefficiency,energyper100mlift,
  pumpeff1,pumpeff2,pumpeff,
  pumpintakep,pumpintaket,pumpintakegol,pumpIntakevisl,pumpIntakebo,
  pumpoutletp,pumpoutlett,pumpoutletgol,pumpoutletvisl,pumpoutletbo,
  rodstring,
  realtimeliquidvolumetricproduction,
  realtimeoilvolumetricproduction,realtimewatervolumetricproduction,
  realtimeliquidweightproduction,
  realtimeoilweightproduction,realtimewaterweightproduction)=
  (select acqtime,
  rpm,
  productiondata,resultstatus,
  resultcode,
  theoreticalproduction,
  liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,
  liquidweightproduction,oilweightproduction,waterweightproduction,
  submergence,
  averagewatt,waterpower,
  systemefficiency,energyper100mlift,
  pumpeff1,pumpeff2,pumpeff,
  pumpintakep,pumpintaket,pumpintakegol,pumpIntakevisl,pumpIntakebo,
  pumpoutletp,pumpoutlett,pumpoutletgol,pumpoutletvisl,pumpoutletbo,
  rodstring,
  realtimeliquidvolumetricproduction,
  realtimeoilvolumetricproduction,realtimewatervolumetricproduction,
  realtimeliquidweightproduction,
  realtimeoilweightproduction,realtimewaterweightproduction
  from tbl_pcpacqdata_hist t3 where t3.id=
  (select v.id from (select t2.id from tbl_srpacqdata_hist t2 where t2.deviceid=v_deviceId and t2.resultstatus=1 order by t2.acqtime desc) v where rownum=1) )
  where t.deviceid=v_deviceId;
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_update_pcp_rpm_latest;
/

CREATE OR REPLACE PROCEDURE prd_update_smsdevice (v_recordId   in NUMBER,
                                                    v_wellName    in varchar2,
                                                    v_instance    in varchar2,
                                                    v_signInId    in varchar2,
                                                    v_sortNum  in NUMBER) as
  wellcount number :=0;
  p_msg varchar2(3000) := 'error';
begin
  --验证权限
  select count(1) into wellcount from tbl_smsdevice t where t.devicename=v_wellName and t.id<>v_recordId;
    if wellcount=0 then
        if v_recordId >0 then
          Update tbl_smsdevice t set
               t.devicename=v_wellName,
               t.instancecode=(select t2.code from tbl_protocolsmsinstance t2 where t2.name=v_instance and rownum=1),
               t.signinid=v_signInId,
               t.sortnum=v_sortNum
           Where t.id=v_recordId;
           commit;
           p_msg := '修改成功';
        end if;
    end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_update_smsdevice;
/

CREATE OR REPLACE PROCEDURE prd_update_srp_diagram_latest (v_deviceId in NUMBER) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_srpacqdata_latest t set (fesdiagramacqtime,fesdiagramsrc,
  productiondata,balanceinfo,
  stroke,spm,
  position_curve,load_curve,power_curve,current_curve,
  resultstatus,
  fmax,fmin,
  upstrokeimax,downstrokeimax,upstrokewattmax,downstrokewattmax,idegreebalance,wattdegreebalance,
  deltaradius,
  resultcode,
  fullnesscoefficient,
  noliquidfullnesscoefficient,
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
  LevelDifferenceValue,CalcProducingfluidLevel,
  submergence,
  averagewatt,polishrodpower,waterpower,
  surfacesystemefficiency,welldownsystemefficiency,systemefficiency,
  energyper100mlift,area,
  rodflexlength,tubingflexlength,inertialength,
  pumpeff1,pumpeff2,pumpeff3,pumpeff4,pumpeff,
  pumpintakep,pumpintaket,pumpintakegol,pumpIntakevisl,pumpIntakebo,
  pumpoutletp,pumpoutlett,pumpoutletgol,pumpoutletvisl,pumpoutletbo,
  rodstring,
  crankangle,polishrodv,polishroda,pr,tf,
  loadtorque,cranktorque,currentbalancetorque,currentnettorque,
  expectedbalancetorque,expectednettorque,
  wellboreslice,
  rpm,
  realtimeliquidvolumetricproduction,
  realtimeoilvolumetricproduction,realtimewatervolumetricproduction,
  realtimeliquidweightproduction,realtimeoilweightproduction,realtimewaterweightproduction)=
  (select fesdiagramacqtime,fesdiagramsrc,
  productiondata,balanceinfo,
  stroke,spm,
  position_curve,load_curve,power_curve,current_curve,
  resultstatus,
  fmax,fmin,
  upstrokeimax,downstrokeimax,upstrokewattmax,downstrokewattmax,idegreebalance,wattdegreebalance,
  deltaradius,
  resultcode,
  fullnesscoefficient,
  noliquidfullnesscoefficient,
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
  LevelDifferenceValue,CalcProducingfluidLevel,
  submergence,
  averagewatt,polishrodpower,waterpower,
  surfacesystemefficiency,welldownsystemefficiency,systemefficiency,
  energyper100mlift,area,
  rodflexlength,tubingflexlength,inertialength,
  pumpeff1,pumpeff2,pumpeff3,pumpeff4,pumpeff,
  pumpintakep,pumpintaket,pumpintakegol,pumpIntakevisl,pumpIntakebo,
  pumpoutletp,pumpoutlett,pumpoutletgol,pumpoutletvisl,pumpoutletbo,
  rodstring,
  crankangle,polishrodv,polishroda,pr,tf,
  loadtorque,cranktorque,currentbalancetorque,currentnettorque,
  expectedbalancetorque,expectednettorque,
  wellboreslice,
  rpm,
  realtimeliquidvolumetricproduction,
  realtimeoilvolumetricproduction,realtimewatervolumetricproduction,
  realtimeliquidweightproduction,realtimeoilweightproduction,realtimewaterweightproduction
  from tbl_srpacqdata_hist t3 where t3.id=
  (select v.id from (select t2.id from tbl_srpacqdata_hist t2 where t2.deviceid=v_deviceId and t2.resultstatus=1 order by t2.fesdiagramacqtime desc) v where rownum=1) )
  where t.deviceid=v_deviceId;
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_update_srp_diagram_latest;
/