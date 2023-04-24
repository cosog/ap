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
EXECUTE IMMEDIATE 'truncate table tbl_rpcacqdata_latest';
EXECUTE IMMEDIATE 'truncate table tbl_rpcacqdata_hist';
EXECUTE IMMEDIATE 'truncate table tbl_rpcalarminfo_latest';
EXECUTE IMMEDIATE 'truncate table tbl_rpcalarminfo_hist';
EXECUTE IMMEDIATE 'truncate table tbl_rpcacqrawdata';
EXECUTE IMMEDIATE 'truncate table tbl_rpcdeviceaddinfo';
EXECUTE IMMEDIATE 'truncate table tbl_rpcdevicegraphicset';
EXECUTE IMMEDIATE 'truncate table tbl_pcpacqdata_latest';
EXECUTE IMMEDIATE 'truncate table tbl_pcpacqdata_hist';
EXECUTE IMMEDIATE 'truncate table tbl_pcpalarminfo_latest';
EXECUTE IMMEDIATE 'truncate table tbl_pcppalarminfo_hist';
EXECUTE IMMEDIATE 'truncate table tbl_pcpacqrawdata';
EXECUTE IMMEDIATE 'truncate table tbl_pcpdeviceaddinfo';
EXECUTE IMMEDIATE 'truncate table tbl_pcpdevicegraphicset';
EXECUTE IMMEDIATE 'truncate table tbl_deviceoperationlog';
EXECUTE IMMEDIATE 'truncate table tbl_systemlog';
EXECUTE IMMEDIATE 'truncate table tbl_resourcemonitoring';
EXECUTE IMMEDIATE 'truncate table tbl_auxiliary2master';
EXECUTE IMMEDIATE 'truncate table tbl_auxiliarydevice';
EXECUTE IMMEDIATE 'truncate table tbl_rpcdevice';
EXECUTE IMMEDIATE 'truncate table tbl_pcpdevice';
EXECUTE IMMEDIATE 'truncate table tbl_smsdevice';

--重置所有序列
 prd_reset_sequence('seq_rpcacqdata_latest');
 prd_reset_sequence('seq_rpcacqdata_hist');
 prd_reset_sequence('seq_rpcalarminfo_latest');
 prd_reset_sequence('seq_rpcalarminfo_hist');
 prd_reset_sequence('seq_rpcacqrawdata');
 prd_reset_sequence('seq_rpcdeviceaddinfo');
 prd_reset_sequence('seq_rpcdevicegraphicset');
 prd_reset_sequence('seq_pcpacqdata_latest');
 prd_reset_sequence('seq_pcpacqdata_hist');
 prd_reset_sequence('seq_pcpalarminfo_latest');
 prd_reset_sequence('seq_pcpalarminfo_hist');
 prd_reset_sequence('seq_pcpacqrawdata');
 prd_reset_sequence('seq_pcpdeviceaddinfo');
 prd_reset_sequence('seq_pcpdevicegraphicset');
 prd_reset_sequence('seq_deviceoperationlog');
 prd_reset_sequence('seq_systemlog');
 prd_reset_sequence('seq_resourcemonitoring');
 prd_reset_sequence('seq_auxiliary2master');
 prd_reset_sequence('seq_auxiliarydevice');
 prd_reset_sequence('seq_rpcdevice');
 prd_reset_sequence('seq_pcpdevice');
 prd_reset_sequence('seq_smsdevice');
end prd_clear_data;
/

CREATE OR REPLACE PROCEDURE prd_save_rpcdevice (
                                                    v_orgId  in NUMBER,
                                                    v_wellName    in varchar2,
                                                    v_devicetype in NUMBER,
                                                    v_applicationScenariosName    in varchar2,
                                                    v_instance    in varchar2,
                                                    v_displayInstance    in varchar2,
                                                    v_reportInstance    in varchar2,
                                                    v_alarmInstance    in varchar2,
                                                    v_tcpType    in varchar2,
                                                    v_signInId    in varchar2,
                                                    v_ipPort    in varchar2,
                                                    v_slave   in varchar2,
                                                    v_peakDelay in NUMBER,
                                                    v_videoUrl   in varchar2,
                                                    v_status in NUMBER,
                                                    v_sortNum  in NUMBER,
                                                    v_productionData in varchar2,
                                                    v_manufacturer in varchar2,
                                                    v_model in varchar2,
                                                    v_stroke  in NUMBER,
                                                    v_balanceinfo in varchar2,
                                                    v_isCheckout in NUMBER,
                                                    v_license in NUMBER,
                                                    v_result out NUMBER,
                                                    v_resultstr out varchar2) as
  wellcount number :=0;
  wellId number :=0;
  othercount number :=0;
  otherrpccount number :=0;
  otherpcpcount number :=0;
  rpcTotalCount number :=0;
  pcpTotalCount number :=0;
  totalCount number :=0;
  otherDeviceAllPath varchar2(3000) := '';
  p_msg varchar2(3000) := 'error';
begin
  select count(1) into wellcount from tbl_rpcdevice t where t.wellname=v_wellName and t.orgid=v_orgId;
  select count(1) into rpcTotalCount from tbl_rpcdevice t;
  select count(1) into pcpTotalCount from tbl_pcpdevice t;
  totalCount :=rpcTotalCount+pcpTotalCount;
  if v_isCheckout=0 then
    if wellcount>0 then
      select t.id into wellId from tbl_rpcdevice t where t.wellname=v_wellName and t.orgid=v_orgId;
      --判断signinid和slave是否已存在
      select count(1) into otherrpccount from tbl_rpcdevice t
      where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null
        and t.id<>wellId;
      select count(1) into otherpcpcount from tbl_pcpdevice t
      where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
      othercount:=otherrpccount+otherpcpcount;
      if othercount=0 then
        Update tbl_rpcdevice t
        Set t.orgid   = v_orgId,t.devicetype=v_devicetype,
          t.applicationscenarios=(select c.itemvalue from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' and c.itemname=v_applicationScenariosName),
          t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and t2.devicetype=0 and rownum=1),
          t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and t2.devicetype=0 and rownum=1),
          t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and t2.devicetype=0 and rownum=1),
          t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and t2.devicetype=0 and rownum=1),
          t.tcptype=v_tcpType,t.signinid=v_signInId,t.ipport=v_ipPort,t.slave=v_slave,t.peakdelay=v_peakDelay,
          t.videourl=v_videourl,
          t.status=v_status, t.sortnum=v_sortNum,
          t.productiondata=v_productionData,
          t.pumpingmodelid=(select t2.id from tbl_pumpingmodel t2 where t2.manufacturer=v_manufacturer and t2.model=v_model),
          t.stroke=v_stroke,t.balanceinfo=v_balanceinfo
        Where t.wellName=v_wellName and t.orgid=v_orgId;
        commit;
        v_result:=1;
        v_resultstr := '修改成功';
        p_msg := '修改成功';
      else
        if otherrpccount>0 then
          select substr(v.path||'/'||t.wellname||'抽油机',2) into otherDeviceAllPath  from tbl_rpcdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
          from tbl_org org
          start with org.org_parent=0
          connect by   org.org_parent= prior org.org_id) v
          where t.orgid=v.org_id
          and t.id=(select t2.id from tbl_rpcdevice t2
            where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
            and to_number(t2.slave)=to_number(v_slave) 
            and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null
            and t2.id<>wellId);
        elsif otherpcpcount>0 then
          select substr(v.path||'/'||t.wellname||'螺杆泵',2) into otherDeviceAllPath  from tbl_pcpdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
          from tbl_org org
          start with org.org_parent=0
          connect by   org.org_parent= prior org.org_id) v
          where t.orgid=v.org_id
          and t.id=(select t2.id from tbl_pcpdevice t2
            where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
            and to_number(t2.slave)=to_number(v_slave) 
            and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null
          );
        end if;
        v_result:=-22;
        v_resultstr := '注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
        p_msg := '注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
      end if;
    elsif wellcount=0 then
      --判断signinid和slave是否已存在
        select count(1) into otherrpccount from tbl_rpcdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        select count(1) into otherpcpcount from tbl_pcpdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        othercount:=otherrpccount+otherpcpcount;
        if othercount=0 then
          if totalCount<v_license then
            insert into tbl_rpcdevice(orgId,wellName,devicetype,tcptype,signinid,ipport,slave,peakdelay,videourl,status,Sortnum,productiondata,stroke,balanceinfo)
            values(v_orgId,v_wellName,v_devicetype,v_tcpType,v_signInId,v_ipPort,v_slave,v_peakDelay,v_videourl,v_status,v_sortNum,v_productionData,v_stroke,v_balanceinfo);
            commit;
            update tbl_rpcdevice t
            set t.applicationscenarios=(select c.itemvalue from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' and c.itemname=v_applicationScenariosName),
                t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and t2.devicetype=0 and rownum=1),
                t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and t2.devicetype=0 and rownum=1),
                t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and t2.devicetype=0 and rownum=1),
                t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and t2.devicetype=0 and rownum=1),
                t.pumpingmodelid=(select t2.id from tbl_pumpingmodel t2 where t2.manufacturer=v_manufacturer and t2.model=v_model)
            Where t.wellName=v_wellName and t.orgid=v_orgId;
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
          if otherrpccount>0 then
             select substr(v.path||'/'||t.wellname||'抽油机',2) into otherDeviceAllPath  from tbl_rpcdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_rpcdevice t2 
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          elsif otherpcpcount>0 then
             select substr(v.path||'/'||t.wellname||'螺杆泵',2) into otherDeviceAllPath  from tbl_pcpdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_pcpdevice t2 
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
           end if;
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
        select count(1) into otherrpccount from tbl_rpcdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        select count(1) into otherpcpcount from tbl_pcpdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        othercount:=otherrpccount+otherpcpcount;
        if othercount=0 then
          if totalCount<v_license then
            insert into tbl_rpcdevice(orgId,wellName,devicetype,tcptype,signinid,ipport,slave,peakdelay,videourl,status,Sortnum,productiondata,stroke,balanceinfo)
            values(v_orgId,v_wellName,v_devicetype,v_tcpType,v_signInId,v_ipPort,v_slave,v_peakDelay,v_videourl,v_status,v_sortNum,v_productionData,v_stroke,v_balanceinfo);
            commit;
            update tbl_rpcdevice t
            set t.applicationscenarios=(select c.itemvalue from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' and c.itemname=v_applicationScenariosName),
                t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and t2.devicetype=0 and rownum=1),
                t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and t2.devicetype=0 and rownum=1),
                t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and t2.devicetype=0 and rownum=1),
                t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and t2.devicetype=0 and rownum=1),
                t.pumpingmodelid=(select t2.id from tbl_pumpingmodel t2 where t2.manufacturer=v_manufacturer and t2.model=v_model)
            Where t.wellName=v_wellName and t.orgid=v_orgId;
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
          if otherrpccount>0 then
             select substr(v.path||'/'||t.wellname||'抽油机',2) into otherDeviceAllPath  from tbl_rpcdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_rpcdevice t2 
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          elsif otherpcpcount>0 then
             select substr(v.path||'/'||t.wellname||'螺杆泵',2) into otherDeviceAllPath  from tbl_pcpdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_pcpdevice t2 
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
           end if;
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
end prd_save_rpcdevice;
/

CREATE OR REPLACE PROCEDURE prd_save_pcpdevice (
                                                    v_orgId  in NUMBER,
                                                    v_wellName    in varchar2,
                                                    v_devicetype in NUMBER,
                                                    v_applicationScenariosName    in varchar2,
                                                    v_instance    in varchar2,
                                                    v_displayInstance    in varchar2,
                                                    v_reportInstance    in varchar2,
                                                    v_alarmInstance    in varchar2,
                                                    v_tcpType    in varchar2,
                                                    v_signInId    in varchar2,
                                                    v_ipPort    in varchar2,
                                                    v_slave   in varchar2,
                                                    v_peakDelay in NUMBER,
                                                    v_videoUrl   in varchar2,
                                                    v_status in NUMBER,
                                                    v_sortNum  in NUMBER,
                                                    v_productionData in varchar2,
                                                    v_isCheckout in NUMBER,
                                                    v_license in NUMBER,
                                                    v_result out NUMBER,
                                                    v_resultstr out varchar2) as
  wellcount number :=0;
  wellId number :=0;
  othercount number :=0;
  otherrpccount number :=0;
  otherpcpcount number :=0;
  rpcTotalCount number :=0;
  pcpTotalCount number :=0;
  totalCount number :=0;
  otherDeviceAllPath varchar2(3000) := '';
  p_msg varchar2(3000) := 'error';
begin
  select count(1) into wellcount from tbl_pcpdevice t where t.wellname=v_wellName and t.orgid=v_orgId;
  select count(1) into rpcTotalCount from tbl_rpcdevice t;
  select count(1) into pcpTotalCount from tbl_pcpdevice t;
  totalCount :=rpcTotalCount+pcpTotalCount;
  if v_isCheckout=0 then
    if wellcount>0 then
      select t.id into wellId from tbl_pcpdevice t where t.wellname=v_wellName and t.orgid=v_orgId;
      --判断signinid和slave是否已存在
      select count(1) into otherpcpcount from tbl_pcpdevice t
      where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null
        and t.id<>wellId;
      select count(1) into otherrpccount from tbl_rpcdevice t
      where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
      othercount:=otherrpccount+otherpcpcount;
      if othercount=0 then
        Update tbl_pcpdevice t
        Set t.orgid   = v_orgId,t.devicetype=v_devicetype,
          t.applicationscenarios=(select c.itemvalue from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' and c.itemname=v_applicationScenariosName),
          t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and t2.devicetype=1 and rownum=1),
          t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and t2.devicetype=1 and rownum=1),
          t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and t2.devicetype=1 and rownum=1),
          t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and t2.devicetype=1 and rownum=1),
          t.tcptype=v_tcpType,t.signinid=v_signInId,t.ipport=v_ipPort,t.slave=v_slave,t.peakdelay=v_peakDelay,
          t.videourl=v_videourl,
          t.status=v_status,t.sortnum=v_sortNum,
          t.productiondata=v_productionData
        Where t.wellName=v_wellName and t.orgid=v_orgId;
        commit;
        v_result:=1;
        v_resultstr := '修改成功';
        p_msg := '修改成功';
      else
        if otherpcpcount>0 then
          select substr(v.path||'/'||t.wellname||'螺杆泵',2) into otherDeviceAllPath  from tbl_pcpdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
          from tbl_org org
          start with org.org_parent=0
          connect by   org.org_parent= prior org.org_id) v
          where t.orgid=v.org_id
          and t.id=(select t2.id from tbl_pcpdevice t2
            where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
            and to_number(t2.slave)=to_number(v_slave) 
            and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null
            and t2.id<>wellId);
        elsif otherrpccount>0 then
          select substr(v.path||'/'||t.wellname||'抽油机',2) into otherDeviceAllPath  from tbl_rpcdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
          from tbl_org org
          start with org.org_parent=0
          connect by   org.org_parent= prior org.org_id) v
          where t.orgid=v.org_id
          and t.id=(select t2.id from tbl_rpcdevice t2
            where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
            and to_number(t2.slave)=to_number(v_slave) 
            and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null
          );
        end if;
        v_result:=-22;
        v_resultstr := '注册包ID/IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
        p_msg := '注册包ID/IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
      end if;
    elsif wellcount=0 then
      --判断signinid和slave是否已存在
        select count(1) into otherpcpcount from tbl_pcpdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        select count(1) into otherrpccount from tbl_rpcdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        othercount:=otherrpccount+otherpcpcount;
        if othercount=0 then
          if totalCount<v_license then
            insert into tbl_pcpdevice(orgId,wellName,devicetype,tcptype,signinid,ipport,slave,peakdelay,videourl,status,Sortnum,productiondata)
            values(v_orgId,v_wellName,v_devicetype,v_tcpType,v_signInId,v_ipPort,v_slave,v_peakDelay,v_videourl,v_status,v_sortNum,v_productionData);
            commit;
            update tbl_pcpdevice t
            set t.applicationscenarios=(select c.itemvalue from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' and c.itemname=v_applicationScenariosName),
                t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and t2.devicetype=1 and rownum=1),
                t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and t2.devicetype=1 and rownum=1),
                t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and t2.devicetype=1 and rownum=1),
                t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and t2.devicetype=1 and rownum=1)
            Where t.wellName=v_wellName and t.orgid=v_orgId;
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
          if otherpcpcount>0 then
             select substr(v.path||'/'||t.wellname||'螺杆泵',2) into otherDeviceAllPath  from tbl_pcpdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_pcpdevice t2 
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          elsif otherrpccount>0 then
             select substr(v.path||'/'||t.wellname||'抽油机',2) into otherDeviceAllPath  from tbl_rpcdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_rpcdevice t2 
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          end if;
          v_result:=-22;
          v_resultstr := '注册包ID/IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
          p_msg := '注册包ID/IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
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
        select count(1) into otherpcpcount from tbl_pcpdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        select count(1) into otherrpccount from tbl_rpcdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        othercount:=otherrpccount+otherpcpcount;
        if othercount=0 then
          if totalCount<v_license then
            insert into tbl_pcpdevice(orgId,wellName,devicetype,tcptype,signinid,ipport,slave,peakdelay,videourl,status,Sortnum,productiondata)
            values(v_orgId,v_wellName,v_devicetype,v_tcpType,v_signInId,v_ipPort,v_slave,v_peakDelay,v_videourl,v_status,v_sortNum,v_productionData);
            commit;
            update tbl_pcpdevice t
            set t.applicationscenarios=(select c.itemvalue from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' and c.itemname=v_applicationScenariosName),
                t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and t2.devicetype=1 and rownum=1),
                t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and t2.devicetype=1 and rownum=1),
                t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and t2.devicetype=1 and rownum=1),
                t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and t2.devicetype=1 and rownum=1)
            Where t.wellName=v_wellName and t.orgid=v_orgId;
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
          if otherpcpcount>0 then
             select substr(v.path||'/'||t.wellname||'螺杆泵',2) into otherDeviceAllPath  from tbl_pcpdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_pcpdevice t2 
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          elsif otherrpccount>0 then
             select substr(v.path||'/'||t.wellname||'抽油机',2) into otherDeviceAllPath  from tbl_rpcdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_rpcdevice t2 
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          end if;
          v_result:=-22;
          v_resultstr := '注册包ID/IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
          p_msg := '注册包ID/IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突';
        end if;
    end if;
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
  Exception
    When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_pcpdevice;
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
    select count(*) into wellcount from tbl_smsdevice t where t.wellName=v_wellName;
    if wellcount>0 then
      Update tbl_smsdevice t set
               t.orgid=smsOrgId,
               t.instancecode=(select t2.code from tbl_protocolsmsinstance t2 where t2.name=v_instance and rownum=1),
               t.signinid=v_signInId,
               t.sortnum=v_sortNum
           Where t.wellName=v_wellName;
           commit;
           p_msg := '修改成功';
    elsif wellcount=0 then
      insert into tbl_smsdevice(orgId,wellName,signinid,Sortnum)
      values(smsOrgId,v_wellName,v_signInId,v_sortNum);
      commit;
      update tbl_smsdevice t set
             t.instancecode=(select t2.code from tbl_protocolsmsinstance t2 where t2.name=v_instance and rownum=1)
      Where t.wellName=v_wellName;
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

CREATE OR REPLACE PROCEDURE prd_save_pumpingmodel (
                                                    v_manufacturer in varchar2,
                                                    v_model in varchar2,
                                                    v_stroke in varchar2,
                                                    v_crankRotationDirection in varchar2,
                                                    v_offsetAngleOfCrank in number,
                                                    v_crankGravityRadius in number,
                                                    v_singleCrankWeight in number,
                                                    v_singleCrankPinWeight in number,
                                                    v_structuralUnbalance in number,
                                                    v_balanceWeight in varchar2,
                                                    v_isCheckout in NUMBER,
                                                    v_result out NUMBER,
                                                    v_resultstr out varchar2
  ) is
  p_msg varchar2(3000) := 'error';
  counts number :=0;
begin
  select count(1) into counts from tbl_pumpingmodel t where t.manufacturer=v_manufacturer and upper(t.model)=upper(v_model);
  if counts=0 then
    insert into tbl_pumpingmodel (manufacturer,model,stroke,crankrotationdirection,offsetangleofcrank,
    crankgravityradius,singlecrankweight,singlecrankpinweight,structuralunbalance,balanceweight)
    values(v_manufacturer,v_model,v_stroke,v_crankRotationDirection,v_offsetAngleOfCrank,
    v_crankGravityRadius,v_singleCrankWeight,v_singleCrankPinWeight,v_structuralUnbalance,v_balanceWeight);
    commit;
    v_result:=0;
    v_resultstr := '添加成功';
    p_msg := '添加成功';
  elsif counts>0 then
    if v_isCheckout=1 then
      v_result:=-33;
      v_resultstr := '该型号抽油机已存在';
      p_msg := '该型号抽油机已存在';
    elsif v_isCheckout=0 then
      update tbl_pumpingmodel t 
      set t.stroke=v_stroke,t.crankrotationdirection=v_crankRotationDirection,
        t.offsetangleofcrank=v_offsetAngleOfCrank,t.crankgravityradius=v_crankGravityRadius,
        t.singlecrankweight=v_singleCrankWeight,t.singlecrankpinweight=v_singleCrankPinWeight,t.structuralunbalance=v_structuralUnbalance,
        t.balanceweight=v_balanceWeight
      where t.manufacturer=v_manufacturer and t.model=v_model;
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
end prd_save_pumpingmodel;
/

CREATE OR REPLACE PROCEDURE prd_update_rpcdevice ( v_recordId in NUMBER,
                                                    v_wellName    in varchar2,
                                                    v_devicetype in NUMBER,
                                                    v_applicationScenariosName    in varchar2,
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
                                                    v_sortNum  in NUMBER,
                                                    v_result out NUMBER,
                                                    v_resultstr out varchar2) as
  wellcount number :=0;
  othercount number :=0;
  otherrpccount number :=0;
  otherpcpcount number :=0;
  otherDeviceAllPath varchar2(3000) := '';
  p_msg varchar2(3000) := 'error';
begin
  --验证权限
  select count(1) into wellcount from tbl_rpcdevice t
  where t.wellname=v_wellName and t.id<>v_recordId
  and t.orgid=( select t2.orgid from tbl_rpcdevice t2 where t2.id=v_recordId);
    if wellcount=0 then
        select count(1) into otherrpccount from tbl_rpcdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null
        and t.id<>v_recordId;
        select count(1) into otherpcpcount from tbl_pcpdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        othercount:=otherrpccount+otherpcpcount;
        if v_recordId >0 and othercount=0 then
          Update tbl_rpcdevice t
           Set t.wellname=v_wellName,
               t.devicetype=v_devicetype,
               t.applicationscenarios=(select c.itemvalue from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' and c.itemname=v_applicationScenariosName),
               t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and t2.devicetype=0 and rownum=1),
               t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and t2.devicetype=0 and rownum=1),
               t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and t2.devicetype=0 and rownum=1),
               t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and t2.devicetype=0 and rownum=1),
               t.tcptype=v_tcpType,t.ipport=v_ipPort,t.signinid=v_signInId,t.slave=v_slave,t.peakdelay=v_peakDelay,
               t.status=v_status,
               t.sortnum=v_sortNum
           Where t.id=v_recordId;
           commit;
           v_result:=1;
           v_resultstr := '修改成功';
           p_msg := '修改成功';
        elsif othercount>0 then
          if otherrpccount>0 then
             select substr(v.path||'/'||t.wellname||'抽油机',2) into otherDeviceAllPath  from tbl_rpcdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_rpcdevice t2
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null
                 and t2.id<>v_recordId);
          elsif otherpcpcount>0 then
             select substr(v.path||'/'||t.wellname||'螺杆泵',2) into otherDeviceAllPath  from tbl_pcpdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_pcpdevice t2
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          end if;
          v_result:=-22;
          v_resultstr :='设备'||v_wellName||'注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突，保存无效';
          p_msg := '设备'||v_wellName||'注册包ID/下位机IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突，保存无效';
        end if;
    else
      v_result:=-33;
      v_resultstr :='同组织下已存在设备'||v_wellName||'，保存无效';
      p_msg := '同组织下已存在设备'||v_wellName||'，保存无效';
    end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_update_rpcdevice;
/

CREATE OR REPLACE PROCEDURE prd_update_pcpdevice ( v_recordId in NUMBER,
                                                    v_wellName    in varchar2,
                                                    v_devicetype in NUMBER,
                                                    v_applicationScenariosName    in varchar2,
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
                                                    v_sortNum  in NUMBER,
                                                    v_result out NUMBER,
                                                    v_resultstr out varchar2) as
  wellcount number :=0;
  othercount number :=0;
  otherrpccount number :=0;
  otherpcpcount number :=0;
  otherDeviceAllPath varchar2(3000) := '';
  p_msg varchar2(3000) := 'error';
begin
  --验证权限
  select count(1) into wellcount from tbl_pcpdevice t
  where t.wellname=v_wellName and t.id<>v_recordId
  and t.orgid=( select t2.orgid from tbl_pcpdevice t2 where t2.id=v_recordId);
    if wellcount=0 then
        select count(1) into otherpcpcount from tbl_pcpdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null
        and t.id<>v_recordId;
        select count(1) into otherrpccount from tbl_rpcdevice t
        where decode(v_tcpType,'TCP Server',t.ipport,t.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
        and to_number(t.slave)=to_number(v_slave) 
        and decode(v_tcpType,'TCP Server',t.ipport,t.signinid) is not null and t.slave is not null;
        othercount:=otherrpccount+otherpcpcount;
        if v_recordId >0 and othercount=0 then
          Update tbl_pcpdevice t
           Set t.wellname=v_wellName,
               t.devicetype=v_devicetype,
               t.applicationscenarios=(select c.itemvalue from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' and c.itemname=v_applicationScenariosName),
               t.instancecode=(select t2.code from tbl_protocolinstance t2 where t2.name=v_instance and t2.devicetype=1 and rownum=1),
               t.displayinstancecode=(select t2.code from tbl_protocoldisplayinstance t2 where t2.name=v_displayInstance and t2.devicetype=1 and rownum=1),
               t.reportinstancecode=(select t2.code from tbl_protocolreportinstance t2 where t2.name=v_reportInstance and t2.devicetype=1 and rownum=1),
               t.alarminstancecode=(select t2.code from tbl_protocolalarminstance t2 where t2.name=v_alarmInstance and t2.devicetype=1 and rownum=1),
               t.tcptype=v_tcpType,t.ipport=v_ipPort,t.signinid=v_signInId,t.slave=v_slave,t.peakdelay=v_peakDelay,
               t.status=v_status,
               t.sortnum=v_sortNum
           Where t.id=v_recordId;
           commit;
           v_result:=1;
           v_resultstr := '修改成功';
           p_msg := '修改成功';
        elsif othercount>0 then
          if otherpcpcount>0 then
             select substr(v.path||'/'||t.wellname||'螺杆泵',2) into otherDeviceAllPath  from tbl_pcpdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_pcpdevice t2
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null
                 and t2.id<>v_recordId);
          elsif otherrpccount>0 then
             select substr(v.path||'/'||t.wellname||'抽油机',2) into otherDeviceAllPath  from tbl_rpcdevice t, (select org.org_id, sys_connect_by_path(org.org_name,'/') as path
             from tbl_org org
             start with org.org_parent=0
             connect by   org.org_parent= prior org.org_id) v
             where t.orgid=v.org_id
             and t.id=(select t2.id from tbl_rpcdevice t2
                 where decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid)=decode(v_tcpType,'TCP Server',v_ipPort,v_signInId)
                 and to_number(t2.slave)=to_number(v_slave) 
                 and decode(v_tcpType,'TCP Server',t2.ipport,t2.signinid) is not null and t2.slave is not null);
          end if;
          v_result:=-22;
          v_resultstr :='设备'||v_wellName||'注册包ID/IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突，保存无效';
          p_msg := '设备'||v_wellName||'注册包ID/IP端口和设备从地址与'||otherDeviceAllPath||'设备冲突，保存无效';
        end if;
    else
      v_result:=-33;
      v_resultstr :='同组织下已存在设备'||v_wellName||'，保存无效';
      p_msg := '同组织下已存在设备'||v_wellName||'，保存无效';
    end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_update_pcpdevice;
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
  select count(1) into wellcount from tbl_smsdevice t where t.wellname=v_wellName and t.id<>v_recordId;
    if wellcount=0 then
        if v_recordId >0 then
          Update tbl_smsdevice t set
               t.wellName=v_wellName,
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

CREATE OR REPLACE PROCEDURE prd_update_pumpingmodel (
                                                       v_id in number,
                                                       v_manufacturer in varchar2,
                                                       v_model in varchar2,
                                                       v_stroke in varchar2,
                                                       v_crankRotationDirection in varchar2,
                                                       v_offsetAngleOfCrank in number,
                                                       v_crankGravityRadius in number,
                                                       v_singleCrankWeight in number,
                                                       v_singleCrankPinWeight in number,
                                                       v_structuralUnbalance in number,
                                                       v_balanceWeight in varchar2,
                                                       v_result out NUMBER,
                                                       v_resultstr out varchar2
                                                       ) is
  p_msg varchar2(3000) := 'error';
  counts number :=0;
begin
  select count(1) into counts from tbl_pumpingmodel t where t.manufacturer=v_manufacturer and upper(t.model)=upper(v_model) and t.id<>v_id;
  if counts=0 then
    update tbl_pumpingmodel t 
    set t.manufacturer=v_manufacturer,t.model=v_model,t.stroke=v_stroke,t.crankrotationdirection=v_crankRotationDirection,
        t.offsetangleofcrank=v_offsetAngleOfCrank,t.crankgravityradius=v_crankGravityRadius,
        t.singlecrankweight=v_singleCrankWeight,t.singlecrankpinweight=v_singleCrankPinWeight,t.structuralunbalance=v_structuralUnbalance,
        t.balanceweight=v_balanceWeight
    where t.id=v_id;
    commit;
    v_result:=1;
    v_resultstr := '修改成功';
    p_msg := '修改成功';
  else
    v_result:=-22;
    v_resultstr :='厂家:'||v_manufacturer||',型号:'||v_model||'的抽油机已存在，保存无效';
    v_resultstr :='厂家:'||v_manufacturer||',型号:'||v_model||'的抽油机已存在，保存无效';
  end if;
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_update_pumpingmodel;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_diagram (
       v_wellId in NUMBER,v_AcqTime in varchar2,
       v_productionData in varchar2,v_balanceInfo in varchar2,v_pumpingModelId in NUMBER,
       v_fesdiagramAcqTime in varchar2,v_fesdiagramSrc in NUMBER,v_STROKE in NUMBER,v_SPM in NUMBER,
       v_POSITION_CURVE in tbl_rpcacqdata_hist.POSITION_CURVE%TYPE,
       v_LOAD_CURVE in tbl_rpcacqdata_hist.LOAD_CURVE%TYPE,
       v_POWER_CURVE in tbl_rpcacqdata_hist.POWER_CURVE%TYPE,
       v_CURRENT_CURVE in tbl_rpcacqdata_hist.CURRENT_CURVE%TYPE,
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
       v_PumpFSDiagram in tbl_rpcacqdata_hist.PumpFSDiagram%TYPE,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_AvailablePlungerStrokeProd_V in NUMBER,v_PumpClearanceLeakProd_V in NUMBER,
       v_TVLeakVolumetricProduction in NUMBER,v_SVLeakVolumetricProduction in NUMBER,v_GasInfluenceProd_V in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_AvailablePlungerStrokeProd_W in NUMBER,v_PumpClearanceLeakProd_W in NUMBER,
       v_TVLeakWeightProduction in NUMBER,v_SVLeakWeightProduction in NUMBER,v_GasInfluenceProd_W in NUMBER,

       v_LevelCorrectValue in NUMBER,v_ProducingfluidLevel in NUMBER,

       v_averagewatt in NUMBER,v_PolishRodPower in NUMBER,v_WaterPower in NUMBER,
       v_SurfaceSystemEfficiency in NUMBER,v_WellDownSystemEfficiency in NUMBER,v_SystemEfficiency in NUMBER,
       v_energyper100mlift in NUMBER,v_area in NUMBER,
       v_RodFlexLength in NUMBER,v_TubingFlexLength in NUMBER,v_InertiaLength in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff3 in NUMBER,v_PumpEff4 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2,
       v_crankAngle in tbl_rpcacqdata_hist.crankangle%TYPE,
       v_polishRodV in tbl_rpcacqdata_hist.polishrodv%TYPE,
       v_polishRodA in tbl_rpcacqdata_hist.polishroda%TYPE,
       v_PR in tbl_rpcacqdata_hist.pr%TYPE,
       v_TF in tbl_rpcacqdata_hist.tf%TYPE,
       v_loadTorque in tbl_rpcacqdata_hist.loadtorque%TYPE,
       v_crankTorque in tbl_rpcacqdata_hist.cranktorque%TYPE,
       v_currentBalanceTorque in tbl_rpcacqdata_hist.currentbalancetorque%TYPE,
       v_currentNetTorque in tbl_rpcacqdata_hist.currentnettorque%TYPE,
       v_expectedBalanceTorque in tbl_rpcacqdata_hist.expectedbalancetorque%TYPE,
       v_expectedNetTorque in tbl_rpcacqdata_hist.expectednettorque%TYPE,
       v_wellboreSlice in tbl_rpcacqdata_hist.wellboreslice%TYPE) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_rpcacqdata_latest t
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
          t.levelcorrectvalue=v_LevelCorrectValue,t.inverproducingfluidlevel=v_ProducingfluidLevel,
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
          t.wellboreslice=v_wellboreSlice
      where t.wellid=v_wellId;
  commit;
  update tbl_rpcacqdata_hist t
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
          t.levelcorrectvalue=v_LevelCorrectValue,t.inverproducingfluidlevel=v_ProducingfluidLevel,
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
          t.wellboreslice=v_wellboreSlice
      where t.wellid=v_wellId and t.acqtime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_diagram;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_diagramcaldata (
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
       v_PumpFSDiagram in tbl_rpcacqdata_hist.PumpFSDiagram%TYPE,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_AvailablePlungerStrokeProd_V in NUMBER,v_PumpClearanceLeakProd_V in NUMBER,
       v_TVLeakVolumetricProduction in NUMBER,v_SVLeakVolumetricProduction in NUMBER,v_GasInfluenceProd_V in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
       v_AvailablePlungerStrokeProd_W in NUMBER,v_PumpClearanceLeakProd_W in NUMBER,
       v_TVLeakWeightProduction in NUMBER,v_SVLeakWeightProduction in NUMBER,v_GasInfluenceProd_W in NUMBER,

       v_LevelCorrectValue in NUMBER,v_ProducingfluidLevel in NUMBER,

       v_averagewatt in NUMBER,v_PolishRodPower in NUMBER,v_WaterPower in NUMBER,
       v_SurfaceSystemEfficiency in NUMBER,v_WellDownSystemEfficiency in NUMBER,v_SystemEfficiency in NUMBER,
       v_energyper100mlift in NUMBER,v_area in NUMBER,
       v_RodFlexLength in NUMBER,v_TubingFlexLength in NUMBER,v_InertiaLength in NUMBER,
       v_PumpEff1 in NUMBER,v_PumpEff2 in NUMBER,v_PumpEff3 in NUMBER,v_PumpEff4 in NUMBER,v_PumpEff in NUMBER,
       v_PumpIntakeP in NUMBER,v_PumpIntakeT in NUMBER,v_PumpIntakeGOL in NUMBER,v_PumpIntakeVisl in NUMBER,v_PumpIntakeBo in NUMBER,
       v_PumpOutletP in NUMBER,v_PumpOutletT in NUMBER,v_PumpOutletGOL in NUMBER,v_PumpOutletVisl in NUMBER,v_PumpOutletBo in NUMBER,
       v_RodString in varchar2,
       v_crankAngle in tbl_rpcacqdata_hist.crankangle%TYPE,
       v_polishRodV in tbl_rpcacqdata_hist.polishrodv%TYPE,
       v_polishRodA in tbl_rpcacqdata_hist.polishroda%TYPE,
       v_PR in tbl_rpcacqdata_hist.pr%TYPE,
       v_TF in tbl_rpcacqdata_hist.tf%TYPE,
       v_loadTorque in tbl_rpcacqdata_hist.loadtorque%TYPE,
       v_crankTorque in tbl_rpcacqdata_hist.cranktorque%TYPE,
       v_currentBalanceTorque in tbl_rpcacqdata_hist.currentbalancetorque%TYPE,
       v_currentNetTorque in tbl_rpcacqdata_hist.currentnettorque%TYPE,
       v_expectedBalanceTorque in tbl_rpcacqdata_hist.expectedbalancetorque%TYPE,
       v_expectedNetTorque in tbl_rpcacqdata_hist.expectednettorque%TYPE,
       v_wellboreSlice in tbl_rpcacqdata_hist.wellboreslice%TYPE) as
  p_msg varchar2(3000) := 'error';
begin
  update tbl_rpcacqdata_hist t
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
          t.levelcorrectvalue=v_LevelCorrectValue,t.inverproducingfluidlevel=v_ProducingfluidLevel,
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
          t.wellboreslice=v_wellboreSlice
      where t.id=v_recordId;
  commit;
  p_msg := '修改成功';
  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg :=p_msg||','|| Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_diagramcaldata;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_diagramdaily (
  v_wellId in number,v_ResultStatus in number,
  v_resultcode in number,v_resultString in tbl_rpcdailycalculationdata.resultstring%TYPE,
  v_ExtendedDays in number,
  v_Stroke in number,v_SPM in number,
  v_FMax in number,v_FMin in number,v_fullnessCoefficient in number,
  v_TheoreticalProduction in number,
  v_liquidVolumetricProduction in number,v_oilVolumetricProduction in number,v_waterVolumetricProduction in number,v_volumewatercut in number,
  v_liquidWeightProduction in number,v_oilWeightProduction in number,v_waterWeightProduction in number,v_weightwatercut in number,
  v_pumpEff in number,v_pumpEff1 in number,v_pumpEff2 in number,v_pumpEff3 in number,v_pumpEff4 in number,
  v_wellDownSystemEfficiency in number,v_surfaceSystemEfficiency in number,v_systemEfficiency in number,v_energyper100mlift in number,
  v_iDegreeBalance in number,v_wattDegreeBalance in number,v_DeltaRadius in number,
  v_producingfluidLevel in number,v_casingPressure in number,v_tubingPressure in number,
  
  v_commStatus in number,v_commTime in number,v_commTimeEfficiency in number,
  v_commRange in tbl_rpcdailycalculationdata.commrange%TYPE,
  v_runStatus in number,v_runTime in number,v_runTimeEfficiency in number,
  v_runRange in tbl_rpcdailycalculationdata.runrange%TYPE,
  v_calDate in varchar2,
  v_recordCount in number
  ) is
  p_msg varchar2(3000) := 'error';
  p_count number:=0;
begin
  select count(*) into p_count from tbl_rpcdailycalculationdata t where t.wellid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd') ;
  if p_count>0 then
    p_msg := '记录存在';
    update tbl_rpcdailycalculationdata t
    set t.commstatus=v_commStatus,t.commtime=v_commTime,t.commtimeefficiency=v_commTimeEfficiency,t.commrange=v_commRange,
    t.runstatus=v_runStatus,t.runtime=v_runTime,t.runtimeefficiency=v_runTimeEfficiency,t.runrange=v_runRange
    where t.wellid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd') ;
    commit;
    
    if v_recordCount>0 then
      update tbl_rpcdailycalculationdata t
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
          t.producingfluidlevel=v_producingfluidLevel,t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure
      where t.wellid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd') ;
      commit;
    end if;
    
    p_msg := '更新成功';
  elsif p_count=0 then
    p_msg := '记录不存在';
    insert into tbl_rpcdailycalculationdata(
           wellid,caldate,resultstatus,resultcode,resultstring,extendeddays,
    stroke,spm,fmax,fmin,fullnesscoefficient,
    theoreticalproduction,
    liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,volumewatercut,
    liquidweightproduction,oilweightproduction,waterweightproduction,weightwatercut,
    pumpeff,pumpeff1,pumpeff2,pumpeff3,pumpeff4,
    welldownsystemefficiency,surfacesystemefficiency,systemefficiency,energyper100mlift,
    idegreebalance,wattdegreebalance,deltaradius,
    producingfluidlevel,casingpressure,tubingpressure,
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
end prd_save_rpc_diagramdaily;
/

CREATE OR REPLACE PROCEDURE prd_save_rpc_diagramdailyrecal (
  v_recordId in number,v_ResultStatus in number,
  v_resultcode in number,v_resultString in tbl_rpcdailycalculationdata.resultstring%TYPE,
  v_ExtendedDays in number,
  v_Stroke in number,v_SPM in number,
  v_FMax in number,v_FMin in number,v_fullnessCoefficient in number,
  v_TheoreticalProduction in number,
  v_liquidVolumetricProduction in number,v_oilVolumetricProduction in number,v_waterVolumetricProduction in number,v_volumewatercut in number,
  v_liquidWeightProduction in number,v_oilWeightProduction in number,v_waterWeightProduction in number,v_weightwatercut in number,
  v_pumpEff in number,v_pumpEff1 in number,v_pumpEff2 in number,v_pumpEff3 in number,v_pumpEff4 in number,
  v_wellDownSystemEfficiency in number,v_surfaceSystemEfficiency in number,v_systemEfficiency in number,v_energyper100mlift in number,
  v_iDegreeBalance in number,v_wattDegreeBalance in number,v_DeltaRadius in number,
  v_producingfluidLevel in number,v_tubingPressure in number,v_casingPressure in number,
  v_recordCount in number
  ) is
  p_msg varchar2(3000) := 'error';
begin
  if v_recordCount>0 then
    update tbl_rpcdailycalculationdata t
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
        t.producingfluidlevel=v_producingfluidLevel,t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure
    where t.id=v_recordId ;
    commit;
    p_msg := '更新成功';
    dbms_output.put_line('p_msg:' || p_msg);
  end if;
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_rpc_diagramdailyrecal;
/

CREATE OR REPLACE PROCEDURE prd_save_pcp_rpm (
       v_wellId in NUMBER,v_AcqTime in varchar2,v_RPM in NUMBER,v_productionData in varchar2,
       v_ResultStatus in NUMBER,
       v_ResultCode in NUMBER,
       v_TheoreticalProduction in NUMBER,
       v_LiquidVolumetricProduction in NUMBER,v_OilVolumetricProduction in NUMBER,v_WaterVolumetricProduction in NUMBER,
       v_LiquidWeightProduction in NUMBER,v_OilWeightProduction in NUMBER,v_WaterWeightProduction in NUMBER,
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
          t.averagewatt=v_averagewatt,t.waterpower=v_WaterPower,
          t.systemefficiency=v_SystemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString
      where t.wellid=v_wellId;
  commit;
  update tbl_pcpacqdata_hist t
      set t.rpm=v_RPM,
          t.productiondata=v_productionData,t.resultstatus=v_ResultStatus,
          t.resultcode=v_ResultCode,
          t.theoreticalproduction=v_TheoreticalProduction,
          t.liquidvolumetricproduction=v_LiquidVolumetricProduction,t.oilvolumetricproduction=v_OilVolumetricProduction,t.watervolumetricproduction=v_WaterVolumetricProduction,
          t.liquidweightproduction=v_LiquidWeightProduction,t.oilweightproduction=v_OilWeightProduction,t.waterweightproduction=v_WaterWeightProduction,
          t.averagewatt=v_averagewatt,t.waterpower=v_WaterPower,
          t.systemefficiency=v_SystemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString
      where t.wellid=v_wellId and t.acqtime=to_date(v_AcqTime,'yyyy-mm-dd hh24:mi:ss');
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
          t.averagewatt=v_averagewatt,t.waterpower=v_WaterPower,
          t.systemefficiency=v_SystemEfficiency,t.energyper100mlift=v_energyper100mlift,
          t.pumpeff1=v_PumpEff1,t.pumpeff2=v_PumpEff2,t.pumpeff=v_PumpEff,
          t.pumpintakep=v_PumpIntakeP,t.pumpintaket=v_PumpIntakeT,t.pumpintakegol=v_PumpIntakeGOL,t.pumpIntakevisl=v_PumpIntakeVisl,t.pumpIntakebo=v_PumpIntakeBo,
          t.pumpoutletp=v_PumpOutletP,t.pumpoutlett=v_PumpOutletT,t.pumpoutletgol=v_PumpOutletGOL,t.pumpoutletvisl=v_PumpOutletVisl,t.pumpoutletbo=v_PumpOutletBo,
          t.rodstring=v_RodString
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
  v_producingfluidLevel in number,v_casingPressure in number,v_tubingPressure in number,
  v_commStatus in number,v_commTime in number,v_commTimeEfficiency in number,
  v_commRange in tbl_pcpdailycalculationdata.commrange%TYPE,
  v_runStatus in number,v_runTime in number,v_runTimeEfficiency in number,
  v_runRange in tbl_pcpdailycalculationdata.runrange%TYPE,
  v_calDate in varchar2,v_recordCount in number
  ) is
  p_msg varchar2(3000) := 'error';
  p_count number:=0;
begin
  select count(*) into p_count from tbl_pcpdailycalculationdata t where t.wellid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd');
  if p_count>0 then
    p_msg := '记录存在';
    update tbl_pcpdailycalculationdata t
    set t.commstatus=v_commStatus,t.commtime=v_commTime,t.commtimeefficiency=v_commTimeEfficiency,t.commrange=v_commRange,
    t.runstatus=v_runStatus,t.runtime=v_runTime,t.runtimeefficiency=v_runTimeEfficiency,t.runrange=v_runRange
    where t.wellid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd');
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
          t.producingfluidlevel=v_producingfluidLevel,t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure
       where t.wellid=v_wellId and t.caldate=to_date(v_calDate,'yyyy-mm-dd');
       commit;
    end if;
    p_msg := '更新成功';
  elsif p_count=0 then
    p_msg := '记录不存在';
    insert into tbl_pcpdailycalculationdata(
           wellid,caldate,resultstatus,extendeddays,rpm,
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
  v_producingfluidLevel in number,v_casingPressure in number,v_tubingPressure in number,v_recordCount in number
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
    t.producingfluidlevel=v_producingfluidLevel,t.casingpressure=v_casingPressure,t.tubingpressure=v_tubingPressure
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

CREATE OR REPLACE PROCEDURE prd_save_rpcalarminfo (
  v_wellName in varchar2,
  v_deviceType in number,
  v_alarmTime in varchar2,
  v_itemName in varchar2,
  v_alarmType in number,
  v_alarmValue in number,
  v_alarmInfo in varchar2,
  v_alarmLimit in number,
  v_hystersis in number,
  v_alarmLevel in number,
  v_isSendMessage in number,
  v_isSendMail in number
  ) is
  p_msg varchar2(3000) := 'error';
  counts number :=0;
  p_wellid number :=0;
begin
  select count(1) into counts from tbl_rpcalarminfo_hist t
  where t.wellid=( select t2.id from tbl_rpcdevice t2 where t2.wellname=v_wellName and t2.devicetype=v_deviceType )
  and t.alarmtime=to_date(v_alarmTime,'yyyy-mm-dd hh24:mi:ss')
  and t.itemname=v_itemName;
  select t.id into p_wellid from tbl_rpcdevice t where t.wellname=v_wellName and t.devicetype=v_deviceType ;
  if counts=0 and p_wellid>0 then
    insert into tbl_rpcalarminfo_hist (wellid,alarmtime,itemname,alarmtype,alarmvalue,alarminfo,alarmlimit,
    hystersis,alarmlevel,issendmessage,issendmail)
    values(
         p_wellid,
         to_date(v_alarmTime,'yyyy-mm-dd hh24:mi:ss'),
         v_itemName,
         v_alarmType,
         v_alarmValue,
         v_alarmInfo,
         v_alarmLimit,
         v_hystersis,
         v_alarmLevel,
         v_isSendMessage,
         v_isSendMail
      );
    commit;
    p_msg := '插入成功';
  elsif counts>0 then
    update tbl_rpcalarminfo_hist t set t.alarmtype=v_alarmType,alarmvalue=v_alarmValue,
    alarminfo=v_alarmInfo,alarmlimit=v_alarmLimit,hystersis=v_hystersis,alarmlevel=v_alarmLevel,
    issendmessage=v_isSendMessage,issendmail=v_isSendMail
    where t.wellid=p_wellid
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
end prd_save_rpcalarminfo;
/

CREATE OR REPLACE PROCEDURE prd_save_pcpalarminfo (
  v_wellName in varchar2,
  v_deviceType in number,
  v_alarmTime in varchar2,
  v_itemName in varchar2,
  v_alarmType in number,
  v_alarmValue in number,
  v_alarmInfo in varchar2,
  v_alarmLimit in number,
  v_hystersis in number,
  v_alarmLevel in number,
  v_isSendMessage in number,
  v_isSendMail in number
  ) is
  p_msg varchar2(3000) := 'error';
  counts number :=0;
  p_wellid number :=0;
begin
  select count(1) into counts from tbl_pcpalarminfo_hist t
  where t.wellid=( select t2.id from tbl_pcpdevice t2 where t2.wellname=v_wellName and t2.devicetype=v_deviceType )
  and t.alarmtime=to_date(v_alarmTime,'yyyy-mm-dd hh24:mi:ss')
  and t.itemname=v_itemName;
  select t.id into p_wellid from tbl_pcpdevice t where t.wellname=v_wellName and t.devicetype=v_deviceType ;
  if counts=0 and p_wellid>0 then
    insert into tbl_pcpalarminfo_hist (wellid,alarmtime,itemname,alarmtype,alarmvalue,alarminfo,alarmlimit,
    hystersis,alarmlevel,issendmessage,issendmail)
    values(
         p_wellid,
         to_date(v_alarmTime,'yyyy-mm-dd hh24:mi:ss'),
         v_itemName,
         v_alarmType,
         v_alarmValue,
         v_alarmInfo,
         v_alarmLimit,
         v_hystersis,
         v_alarmLevel,
         v_isSendMessage,
         v_isSendMail
      );
    commit;
    p_msg := '插入成功';
  elsif counts>0 then
    update tbl_pcpalarminfo_hist t set t.alarmtype=v_alarmType,alarmvalue=v_alarmValue,
    alarminfo=v_alarmInfo,alarmlimit=v_alarmLimit,hystersis=v_hystersis,alarmlevel=v_alarmLevel,
    issendmessage=v_isSendMessage,issendmail=v_isSendMail
    where t.wellid=p_wellid
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
end prd_save_pcpalarminfo;
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
                                                        runColor   in varchar2,
                                                        stopColor     in varchar2,
                                                        runOpacity   in varchar2,
                                                        stopOpacity     in varchar2) is
  p_msg varchar2(30) := 'error';
begin
    --数据报警
    Update tbl_code t1 set t1.itemname=overviewBackgroundColor0 where t1.itemcode='BJYS' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=overviewBackgroundColor1 where t1.itemcode='BJYS' and t1.itemvalue=100;
    Update tbl_code t1 set t1.itemname=overviewBackgroundColor2 where t1.itemcode='BJYS' and t1.itemvalue=200;
    Update tbl_code t1 set t1.itemname=overviewBackgroundColor3 where t1.itemcode='BJYS' and t1.itemvalue=300;

    Update tbl_code t1 set t1.itemname=overviewColor0 where t1.itemcode='BJQJYS' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=overviewColor1 where t1.itemcode='BJQJYS' and t1.itemvalue=100;
    Update tbl_code t1 set t1.itemname=overviewColor2 where t1.itemcode='BJQJYS' and t1.itemvalue=200;
    Update tbl_code t1 set t1.itemname=overviewColor3 where t1.itemcode='BJQJYS' and t1.itemvalue=300;

    Update tbl_code t1 set t1.itemname=overviewOpacity0 where t1.itemcode='BJYSTMD' and t1.itemvalue=0;
    Update tbl_code t1 set t1.itemname=overviewOpacity1 where t1.itemcode='BJYSTMD' and t1.itemvalue=100;
    Update tbl_code t1 set t1.itemname=overviewOpacity2 where t1.itemcode='BJYSTMD' and t1.itemvalue=200;
    Update tbl_code t1 set t1.itemname=overviewOpacity3 where t1.itemcode='BJYSTMD' and t1.itemvalue=300;

    --通信
    Update tbl_code t1 set t1.itemname=goOnlineBackgroundColor where t1.itemcode='TXBJYS' and t1.itemvalue=2;
    Update tbl_code t1 set t1.itemname=onlineBackgroundColor where t1.itemcode='TXBJYS' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=offlineBackgroundColor where t1.itemcode='TXBJYS' and t1.itemvalue=0;

    Update tbl_code t1 set t1.itemname=goOnlineColor where t1.itemcode='TXBJQJYS' and t1.itemvalue=2;
    Update tbl_code t1 set t1.itemname=onlineColor where t1.itemcode='TXBJQJYS' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=offlineColor where t1.itemcode='TXBJQJYS' and t1.itemvalue=0;

    Update tbl_code t1 set t1.itemname=goOnlineOpacity where t1.itemcode='TXBJYSTMD' and t1.itemvalue=2;
    Update tbl_code t1 set t1.itemname=onlineOpacity where t1.itemcode='TXBJYSTMD' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=offlineOpacity where t1.itemcode='TXBJYSTMD' and t1.itemvalue=0;

    --运行
    Update tbl_code t1 set t1.itemname=runBackgroundColor where t1.itemcode='YXBJYS' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=stopBackgroundColor where t1.itemcode='YXBJYS' and t1.itemvalue=0;

    Update tbl_code t1 set t1.itemname=runColor where t1.itemcode='YXBJQJYS' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=stopColor where t1.itemcode='YXBJQJYS' and t1.itemvalue=0;

    Update tbl_code t1 set t1.itemname=runOpacity where t1.itemcode='YXBJYSTMD' and t1.itemvalue=1;
    Update tbl_code t1 set t1.itemname=stopOpacity where t1.itemcode='YXBJYSTMD' and t1.itemvalue=0;
    commit;
    p_msg := '修改成功';

  dbms_output.put_line('p_msg:' || p_msg);
Exception
  When Others Then
    p_msg := Sqlerrm || ',' || '操作失败';
    dbms_output.put_line('p_msg:' || p_msg);
end prd_save_alarmcolor;
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
  insert into tbl_deviceoperationlog (createtime,wellname,devicetype,action,user_id,loginip,remark)
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

CREATE OR REPLACE PROCEDURE prd_save_resourcemonitoring (
  v_acqTime in varchar2,
  v_acRunStatus in number,
  v_acVersion in varchar2,
  v_adRunStatus in number,
  v_adVersion in varchar2,
  v_cpuUsedPercent in varchar2,
  v_memUsedPercent in number,
  v_tableSpaceSize in number,
  v_jedisStatus in number
  ) is
  p_msg varchar2(3000) := 'error';
  counts number :=0;
begin
  select count(1) into counts from tbl_resourcemonitoring;
  if counts>1000 then
    delete from TBL_RESOURCEMONITORING where id not in (select id from (select id from TBL_RESOURCEMONITORING t order by t.acqtime desc) v where rownum <=1000);
    commit;
    update TBL_RESOURCEMONITORING t
    set t.acqtime=to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
        t.acrunstatus=v_acRunStatus,t.acversion=v_acVersion,t.cpuusedpercent=v_cpuUsedPercent,
        t.adrunstatus=v_adRunStatus,t.adversion=v_adVersion,
        t.memusedpercent=v_memUsedPercent,t.tablespacesize=v_tableSpaceSize,
        t.jedisStatus=v_jedisStatus
    where t.id=(select id from (select id from TBL_RESOURCEMONITORING  order by acqtime ) where rownum=1);
    commit;
     p_msg := '删除多余记录并更新成功';
  elsif counts=1000 then
    update TBL_RESOURCEMONITORING t
    set t.acqtime=to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
        t.acrunstatus=v_acRunStatus,t.acversion=v_acVersion,t.cpuusedpercent=v_cpuUsedPercent,
        t.adrunstatus=v_adRunStatus,t.adversion=v_adVersion,
        t.memusedpercent=v_memUsedPercent,t.tablespacesize=v_tableSpaceSize,
        t.jedisStatus=v_jedisStatus
    where t.id=(select id from (select id from TBL_RESOURCEMONITORING  order by acqtime ) where rownum=1);
    commit;
    p_msg := '更新成功';
   elsif counts<1000 then
     insert into tbl_resourcemonitoring (
         acqtime,acrunstatus,acversion,cpuusedpercent,adrunstatus,adversion,memusedpercent,tablespacesize,jedisStatus
      )values(
         to_date(v_acqTime,'yyyy-mm-dd hh24:mi:ss'),
         v_acRunStatus,
         v_acVersion,
         v_cpuUsedPercent,
         v_adRunStatus,
         v_adVersion,
         v_memUsedPercent,
         v_tableSpaceSize,
         v_jedisStatus
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

CREATE OR REPLACE PROCEDURE prd_init_device_daily is
begin
    insert into tbl_rpcdailycalculationdata (wellid,caldate)
    select id, to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') from tbl_rpcdevice well
    where well.id not in ( select t2.wellid from tbl_rpcdailycalculationdata t2 where t2.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'));
    commit;
    
    update tbl_rpcdailycalculationdata t set t.headerlabelinfo=
    ( select t2.headerlabelinfo from  tbl_rpcdailycalculationdata t2 
    where t2.wellid=t.wellid and t2.caldate=
    ( select max(t3.caldate) from tbl_rpcdailycalculationdata t3 where t3.wellid=t2.wellid and t3.headerlabelinfo is not null ))
    where t.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')
    and t.headerlabelinfo is null;
    commit;

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

end prd_init_device_daily;
/