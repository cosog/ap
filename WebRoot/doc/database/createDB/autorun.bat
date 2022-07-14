@echo off
@echo 正在创建表空间及用户.....
sqlplus sys/orcl@orclpdb  as sysdba @1、createSpaceAndUser.sql>createSpaceAndUser.txt

@echo 创建表格及初始化.....
sqlplus ap/ap123#@orclpdb @createAndInitDB.sql>createAndInitDB.txt

@pause 