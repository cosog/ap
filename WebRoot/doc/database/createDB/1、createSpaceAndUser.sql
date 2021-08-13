drop tablespace ap_temp including contents and datafiles;
drop tablespace ap_data including contents and datafiles;
drop user ap cascade;
create temporary tablespace ap_temp
TEMPFILE 'C:\oracle19c\oradata\ORCL\orclpdb\ap_temp.dbf'
size 50m
autoextend on
next 50m maxsize unlimited
extent management local;
create tablespace ap_data
logging
DATAFILE 'C:\oracle19c\oradata\ORCL\orclpdb\ap_data.dbf'
size 350m
autoextend on
next 50m maxsize unlimited
extent management local;
create user ap identified by ap123#
default tablespace ap_data
temporary tablespace ap_temp;
grant connect,resource,dba to ap;
exit;
