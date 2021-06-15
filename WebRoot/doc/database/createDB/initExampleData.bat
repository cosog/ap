@echo off
@echo 创建例子数据.....
sqlplus agile/agile@orcl @11initExampleData.sql>initExampleData.txt

@pause 