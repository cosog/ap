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


alter tablespace ap_data add datafile '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data02.dbf' size 350M autoextend on next 50M Maxsize UNLIMITED;
alter tablespace ap_data add datafile '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data03.dbf' size 350M autoextend on next 50M Maxsize UNLIMITED;
alter tablespace ap_data add datafile '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data04.dbf' size 350M autoextend on next 50M Maxsize UNLIMITED;
alter tablespace ap_data add datafile '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data05.dbf' size 350M autoextend on next 50M Maxsize UNLIMITED;
alter tablespace ap_data add datafile '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data06.dbf' size 350M autoextend on next 50M Maxsize UNLIMITED;
alter tablespace ap_data add datafile '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data07.dbf' size 350M autoextend on next 50M Maxsize UNLIMITED;
alter tablespace ap_data add datafile '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data08.dbf' size 350M autoextend on next 50M Maxsize UNLIMITED;
alter tablespace ap_data add datafile '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data09.dbf' size 350M autoextend on next 50M Maxsize UNLIMITED;
alter tablespace ap_data add datafile '/opt/oracle/oradata/ORCLCDB/ORCLPDB1/ap_data10.dbf' size 350M autoextend on next 50M Maxsize UNLIMITED;


Alter PROFILE DEFAULT LIMIT PASSWORD_LIFE_TIME UNLIMITED;

create user ap identified by Ap201#
default tablespace ap_data
temporary tablespace ap_temp;
grant connect,resource,dba to ap;
exit;