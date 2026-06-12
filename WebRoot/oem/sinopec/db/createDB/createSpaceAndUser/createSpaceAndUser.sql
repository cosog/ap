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
Alter PROFILE DEFAULT LIMIT PASSWORD_LIFE_TIME UNLIMITED;
create user ap identified by Ap201#
default tablespace ap_data
temporary tablespace ap_temp;
grant connect,resource,dba to ap;
exit;




create user ap_hc identified by Ap201#
default tablespace ap_hc_data
temporary tablespace ap_hc_temp;
grant connect,resource,dba to ap_hc;


ALTER USER ap ACCOUNT UNLOCK;