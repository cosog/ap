create temporary tablespace ap_temp
TEMPFILE 'D:\oracle19c\oradata\ORCL\orclpdb\ap_temp.dbf'
size 50m reuse
autoextend on
next 50m maxsize unlimited
extent management local;
create tablespace ap_data
logging
DATAFILE 'D:\oracle19c\oradata\ORCL\orclpdb\ap_data.dbf'
size 350m reuse
autoextend on
next 50m maxsize unlimited
extent management local;
create user ap identified by Ap201#
default tablespace ap_data
temporary tablespace ap_temp;
grant connect,resource,dba to ap;
exit;
