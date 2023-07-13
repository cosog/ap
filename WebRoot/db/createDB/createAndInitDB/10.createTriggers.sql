
CREATE OR REPLACE TRIGGER BEF_HIBERNATE_SEQUENCE_INSERT
BEFORE INSERT ON TBL_DIST_ITEM
FOR EACH ROW
BEGIN
  SELECT to_char(HIBERNATE_SEQUENCE.nextval)INTO :new.DATAITEMID FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_a_pcpdevice_i   after  insert or update  on tbl_pcpdevice FOR EACH ROW
declare
  pcp_RT   number(10);
begin
  select count(id) into pcp_RT from tbl_pcpacqdata_latest t where t.wellid = :new.id;
    if pcp_RT = 0  then
       insert into tbl_pcpacqdata_latest (wellId) (select :new.id from dual);
    end if;
end;
/

CREATE OR REPLACE TRIGGER trg_a_rpcdevice_i   after  insert or update  on tbl_rpcdevice FOR EACH ROW
declare
  rpc_RT   number(10);
begin
  select count(id) into rpc_RT from tbl_rpcacqdata_latest t where t.wellid = :new.id;
    if rpc_RT = 0  then
       insert into tbl_rpcacqdata_latest (wellId) (select :new.id from dual);
    end if;
end;
/

CREATE OR REPLACE TRIGGER trg_b_protocol_i   before  insert on tbl_protocol FOR EACH ROW
BEGIN
  SELECT seq_protocol.nextval,'protocol' || seq_protocol.nextval INTO :new.id, :new.code FROM dual;
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

CREATE OR REPLACE TRIGGER 
trg_b_alarm_item2unit_conf_i   before  insert on TBL_ALARM_ITEM2UNIT_CONF FOR EACH ROW
BEGIN
  SELECT seq_alarm_item2unit_conf.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER 
trg_b_alarm_unit_conf_i   before  insert on TBL_ALARM_UNIT_CONF FOR EACH ROW
BEGIN
  SELECT seq_alarm_unit_conf.nextval,'alarmunit' || seq_alarm_unit_conf.nextval INTO :new.id, :new.unit_code FROM dual;
end;
/

create or replace trigger trg_b_code_i   before  insert on TBL_CODE FOR EACH ROW
BEGIN
  SELECT SEQ_code.nextval INTO :new.id FROM dual;
END;
/

create or replace trigger trg_b_datamapping_i   before  insert on tbl_datamapping FOR EACH ROW
BEGIN
  SELECT seq_datamapping.nextval INTO :new.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER 
trg_b_deviceoperationlog_i   before  insert on TBL_DEVICEOPERATIONLOG FOR EACH ROW
BEGIN
  SELECT seq_deviceoperationlog.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER 
trg_b_display_item2unit_conf_i   before  insert on TBL_DISPLAY_ITEMS2UNIT_CONF FOR EACH ROW
BEGIN
  SELECT seq_display_items2unit_conf.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_display_unit_conf_i   before  insert on TBL_DISPLAY_UNIT_CONF FOR EACH ROW
BEGIN
  SELECT SEQ_DISPLAY_UNIT_CONF.nextval,'unit' || SEQ_DISPLAY_UNIT_CONF.nextval INTO :new.id, :new.unit_code FROM dual;
end;
/

create or replace trigger trg_b_module2role_i   before  insert  on tbl_module2role FOR EACH ROW
BEGIN
       SELECT seq_role_module.nextval INTO :new.rm_id FROM dual;
END;
/

create or replace trigger trg_b_module_i   before  insert on TBL_MODULE FOR EACH ROW
BEGIN
  SELECT SEQ_MODULE.nextval INTO :new.MD_id FROM dual;
END;
/

create or replace trigger trg_b_org_i_u   before  insert  on TBL_ORG FOR EACH ROW
BEGIN
  case
       when inserting then
            SELECT SEQ_ORG.nextval INTO :new.ORG_ID FROM dual;
  end case;
END;
/

CREATE OR REPLACE TRIGGER trg_b_pcpacqdata_hist_i   before  insert on TBL_PCPACQDATA_HIST FOR EACH ROW
BEGIN
  SELECT seq_pcpacqdata_hist.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_pcpacqdata_latest_i   before  insert on TBL_PCPACQDATA_LATEST FOR EACH ROW
BEGIN
  SELECT seq_pcpacqdata_latest.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_pcpacqrawdata_i   before  insert on tbl_pcpacqrawdata FOR EACH ROW
BEGIN
  SELECT seq_pcpacqrawdata.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_pcpalarminfo_hist_i   before  update or insert on tbl_pcpalarminfo_hist FOR EACH ROW
declare
    recordCount number(8,2) :=0;
BEGIN
  SELECT seq_pcpalarminfo_hist.nextval INTO :new.id FROM dual;
  select count(1) into recordCount from tbl_pcpalarminfo_latest t
  where t.wellid=:new.wellid and t.alarmtype=:new.alarmtype and t.itemname=:new.itemname;
  if recordCount=0 then
    insert into tbl_pcpalarminfo_latest(
    wellid,alarmtime,itemname,alarmtype,alarmvalue,alarminfo,alarmlimit,
    hystersis,alarmlevel,recoverytime,issendmessage,issendmail
    )values(
    :new.wellid,:new.alarmtime,:new.itemname,:new.alarmtype,:new.alarmvalue,:new.alarminfo,:new.alarmlimit,
    :new.hystersis,:new.alarmlevel,:new.recoverytime,:new.issendmessage,:new.issendmail
    );
  else
    update tbl_pcpalarminfo_latest t
    set t.alarmvalue=:new.alarmvalue,t.alarmtime=:new.alarmtime,
        t.alarminfo=:new.alarminfo,t.alarmlimit=:new.alarmlimit,
        t.hystersis=:new.hystersis,t.alarmlevel=:new.alarmlevel,t.recoverytime=:new.recoverytime,
        t.issendmessage=:new.issendmessage, t.issendmail=:new.issendmail
    where t.wellid=:new.wellid and t.alarmtype=:new.alarmtype and t.itemname=:new.itemname;
  end if;
end;
/

create or replace trigger trg_b_pcpalarminfo_l_i   before  insert on tbl_pcpalarminfo_latest FOR EACH ROW
BEGIN
  SELECT seq_pcpalarminfo_latest.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_PCPDAILY_i   before  insert on TBL_PCPDAILYCALCULATIONDATA FOR EACH ROW
BEGIN
  SELECT SEQ_PCPDAILYCALCULATIONDATA.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER 
trg_b_pcpdevicegraphset_i   before  insert on tbl_pcpdevicegraphicset FOR EACH ROW
BEGIN
  SELECT seq_pcpdevicegraphicset.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_pcpdevice_i   before  insert on tbl_pcpdevice FOR EACH ROW
BEGIN
  SELECT seq_pcpdevice.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_protocolalarminstance_i   before  insert on tbl_protocolalarminstance FOR EACH ROW
BEGIN
  SELECT seq_protocolalarminstance.nextval,'alarminstance' || seq_protocolalarminstance.nextval INTO :new.id, :new.code FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_protocoldisplayinst_i   before  insert on tbl_protocoldisplayinstance FOR EACH ROW
BEGIN
  SELECT seq_protocoldisplayinstance.nextval,'displayinstance' || seq_protocoldisplayinstance.nextval INTO :new.id, :new.code FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_protocolreportinst_i   before  insert on tbl_protocolreportinstance FOR EACH ROW
BEGIN
  SELECT seq_protocolreportinstance.nextval,'reportinstance' || seq_protocolreportinstance.nextval INTO :new.id, :new.code FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_protocolinstance_i   before  insert on tbl_protocolinstance FOR EACH ROW
BEGIN
  SELECT seq_protocolinstance.nextval,'instance' || seq_protocolinstance.nextval INTO :new.id, :new.code FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_protocolsmsinstance_i   before  insert on tbl_protocolsmsinstance FOR EACH ROW
BEGIN
  SELECT seq_protocolsmsinstance.nextval,'smsinstance' || seq_protocolsmsinstance.nextval INTO :new.id, :new.code FROM dual;
end;
/

create or replace trigger trg_b_pumpingmodel_i   before  insert on tbl_pumpingmodel FOR EACH ROW
BEGIN
  SELECT seq_pumpingmodel.nextval INTO :new.id FROM dual;
end;
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

create or replace trigger trg_b_rpcacqdata_hist_i   before  insert on tbl_rpcacqdata_hist FOR EACH ROW
BEGIN
  SELECT seq_rpcacqdata_hist.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpcacqdata_latest_i   before  insert on tbl_rpcacqdata_latest FOR EACH ROW
BEGIN
  SELECT seq_rpcacqdata_latest.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpcacqrawdata_i   before  insert on tbl_rpcacqrawdata FOR EACH ROW
BEGIN
  SELECT seq_rpcacqrawdata.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpcalarminfo_hist_i   before  update or insert on tbl_rpcalarminfo_hist FOR EACH ROW
declare
    recordCount number(8,2) :=0;
BEGIN
  SELECT seq_rpcalarminfo_hist.nextval INTO :new.id FROM dual;
  select count(1) into recordCount from tbl_rpcalarminfo_latest t
  where t.wellid=:new.wellid and t.alarmtype=:new.alarmtype and t.itemname=:new.itemname;
  if recordCount=0 then
    insert into tbl_rpcalarminfo_latest(
    wellid,alarmtime,itemname,alarmtype,alarmvalue,alarminfo,alarmlimit,
    hystersis,alarmlevel,recoverytime,issendmessage,issendmail
    )values(
    :new.wellid,:new.alarmtime,:new.itemname,:new.alarmtype,:new.alarmvalue,:new.alarminfo,:new.alarmlimit,
    :new.hystersis,:new.alarmlevel,:new.recoverytime,:new.issendmessage,:new.issendmail
    );
  else
    update tbl_rpcalarminfo_latest t
    set t.alarmvalue=:new.alarmvalue,t.alarmtime=:new.alarmtime,
        t.alarminfo=:new.alarminfo,t.alarmlimit=:new.alarmlimit,
        t.hystersis=:new.hystersis,t.alarmlevel=:new.alarmlevel,t.recoverytime=:new.recoverytime,
        t.issendmessage=:new.issendmessage, t.issendmail=:new.issendmail
    where t.wellid=:new.wellid and t.alarmtype=:new.alarmtype and t.itemname=:new.itemname;
  end if;
end;
/

create or replace trigger trg_b_rpcalarminfo_latest_i   before  insert on tbl_rpcalarminfo_latest FOR EACH ROW
BEGIN
  SELECT seq_rpcalarminfo_latest.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_RPCDAILY_i   before  insert on TBL_RPCDAILYCALCULATIONDATA FOR EACH ROW
BEGIN
  SELECT SEQ_RPCDAILYCALCULATIONDATA.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER 
trg_b_rpcdevicegraphicset_i   before  insert on tbl_rpcdevicegraphicset FOR EACH ROW
BEGIN
  SELECT seq_rpcdevicegraphicset.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpcdevice_i   before  insert on tbl_rpcdevice FOR EACH ROW
BEGIN
  SELECT seq_rpcdevice.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_rpc_worktype_i_u   before insert on TBL_RPC_WORKTYPE FOR EACH ROW
BEGIN
  SELECT SEQ_RPC_WORKTYPE.nextval INTO :new.id FROM dual;
END;
/

create or replace trigger trg_b_smsdevice_i   before  insert on tbl_smsdevice FOR EACH ROW
BEGIN
  SELECT seq_smsdevice.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER 
trg_b_systemlog_i   before  insert on tbl_systemlog FOR EACH ROW
BEGIN
  SELECT seq_systemlog.nextval INTO :new.id FROM dual;
end;
/

create or replace trigger trg_b_user_i   before  insert on TBL_USER FOR EACH ROW
BEGIN
  SELECT SEQ_USER.nextval INTO :new.USER_NO FROM dual;
END;
/

create or replace trigger trg_b_runstatusconfig_i   before  insert on tbl_runstatusconfig FOR EACH ROW
BEGIN
  SELECT seq_runstatusconfig.nextval INTO :new.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER 
trg_b_report_item2unit_conf_i   before  insert on TBL_REPORT_ITEMS2UNIT_CONF FOR EACH ROW
BEGIN
  SELECT seq_report_items2unit_conf.nextval INTO :new.id FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_report_unit_conf_i   before  insert on TBL_REPORT_UNIT_CONF FOR EACH ROW
BEGIN
  SELECT SEQ_REPORT_UNIT_CONF.nextval,'unit' || SEQ_REPORT_UNIT_CONF.nextval INTO :new.id, :new.unit_code FROM dual;
end;
/

CREATE OR REPLACE TRIGGER trg_b_videokey_i   before  insert on TBL_VIDEOKEY FOR EACH ROW
BEGIN
  SELECT SEQ_VIDEOKEY.nextval INTO :new.id FROM dual;
END;
/