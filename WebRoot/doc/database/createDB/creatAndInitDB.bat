@echo off
@echo 创建表格及初始化.....
sqlplus agile/agile@orcl @createAndInitDB.sql>createAndInitDB.txt

@pause 