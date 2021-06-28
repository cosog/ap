@echo off
@echo 创建例子数据.....
sqlplus agile/agile@orcl @deleteExampleData.sql>deleteExampleData.txt

@pause 