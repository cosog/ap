@echo off
@echo ���ڴ�����ռ估�û�.....
sqlplus sys/orcl@orcl  as sysdba @1��createSpaceAndUser.sql>createSpaceAndUser.txt

@echo ������񼰳�ʼ��.....
sqlplus ap/ap123#@orcl @createAndInitDB.sql>createAndInitDB.txt

@pause 