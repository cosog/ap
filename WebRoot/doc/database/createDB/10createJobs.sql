declare
    job number;
begin
  sys.dbms_job.submit(job => job,
                      what => 'prd_init_rpc_daily;',
                      next_date => to_date('03-04-2019 01:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'TRUNC(sysdate+ 1)  +1/ (24)');
  commit;
end;
/

declare
    job number;
begin
  sys.dbms_job.submit(job => job,
                      what => 'prd_clearResourceProbeData;',
                      next_date => to_date('03-04-2019 01:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'TRUNC(sysdate+ 1)  +1/ (24)');
  commit;
end;
/