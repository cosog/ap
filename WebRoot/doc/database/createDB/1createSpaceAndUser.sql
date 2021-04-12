drop tablespace agile_temp including contents and datafiles;
drop tablespace agile_data including contents and datafiles;
drop user agile cascade;
create temporary tablespace agile_temp
TEMPFILE 'C:\oracle\oradata\ORCL\orclpdb\agile_temp.dbf'
size 50m
autoextend on
next 50m maxsize unlimited
extent management local;
create tablespace agile_data
logging
DATAFILE 'C:\oracle\oradata\ORCL\orclpdb\agile_data.dbf'
size 350m
autoextend on
next 50m maxsize unlimited
extent management local;
create user agile identified by agile
default tablespace agile_data
temporary tablespace agile_temp;
grant connect,resource,dba to agile;
exit;
