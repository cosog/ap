@echo off
@echo 正在创建表空间及用户.....
sqlplus sys/orcl@orcl  as sysdba @1createSpaceAndUser.sql>log.txt

@echo 创建表格及初始化.....
sqlplus agile/agile@orcl @createAndInitDB.sql>log.txt

@pause 