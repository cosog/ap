@echo off
@echo ���ڴ�����ռ估�û�.....
sqlplus sys/orcl@orclpdb  as sysdba @1��createSpaceAndUser.sql>createSpaceAndUser.txt

@echo ������񼰳�ʼ��.....
sqlplus ap/ap123#@orclpdb @createAndInitDB.sql>createAndInitDB.txt

@pause 