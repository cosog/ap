@echo off
@echo 正在创建表空间及用户.....
sqlplus sys/orcl@orcl  as sysdba @1、createSpaceAndUser.sql>createSpaceAndUser.txt

@echo 创建表格及初始化.....
sqlplus agile/agile@orcl @createAndInitDB.sql>createAndInitDB.txt

@pause 