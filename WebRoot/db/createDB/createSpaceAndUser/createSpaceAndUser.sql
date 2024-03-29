Alter PROFILE DEFAULT LIMIT PASSWORD_LIFE_TIME UNLIMITED;
drop tablespace ap_temp including contents and datafiles;
drop tablespace ap_data including contents and datafiles;
drop user ap cascade;
create temporary tablespace ap_temp
TEMPFILE '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_temp.dbf'
size 50m reuse
autoextend on
next 50m maxsize unlimited
extent management local;
create tablespace ap_data
logging
DATAFILE '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data.dbf'
size 350m reuse
autoextend on
next 50m maxsize unlimited
extent management local;
create user ap identified by ap123#
default tablespace ap_data
temporary tablespace ap_temp;
grant connect,resource,dba to ap;
exit;
