@echo off
@echo 创建例子数据.....
sqlplus ap/ap123#@orclpdb @initExampleData.sql>initExampleData.txt

@pause 