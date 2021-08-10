/*==============================================================*/
/* ��ʼ��tbl_acq_group_conf����                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, ACQ_CYCLE, SAVE_CYCLE, PROTOCOL, REMARK)
values (1, 'group1', '���ͻ���ɢ����', 300, 300, 'A11-Modbus', '����״̬����Ρ�Ƶ�ʡ�ѹ�����¶ȵ�����');

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, ACQ_CYCLE, SAVE_CYCLE, PROTOCOL, REMARK)
values (2, 'group2', '���ͻ���ͼ����', 300, 300, 'A11-Modbus', '��̡�����Լ�λ�ơ��غɡ����ʡ�������������');

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, ACQ_CYCLE, SAVE_CYCLE, PROTOCOL, REMARK)
values (3, 'group3', '����ɼ���', 60, 60, 'private-lq1000', '����ɼ���');

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, ACQ_CYCLE, SAVE_CYCLE, PROTOCOL, REMARK)
values (4, 'group4', '��̨˹�ɼ���', 60, 60, 'private-kd93', '��̨˹�ɼ���');

/*==============================================================*/
/* ��ʼ��tbl_acq_unit_conf����                                    */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', '���ͻ�ȫ������', 'A11-Modbus', '��ͼ���ݺ���ɢ����');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (2, 'unit2', '���ͻ���ɢ����', 'A11-Modbus', '����״̬����Ρ�Ƶ�ʡ�ѹ�����¶ȵ�����');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (3, 'unit3', '���ͻ���ͼ����', 'A11-Modbus', '��̡�����Լ�λ�ơ��غɡ����ʡ�������������');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (4, 'unit4', '��̨˹�ɼ���Ԫ', 'private-kd93', '��̨˹�ɼ���Ԫ');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (5, 'unit5', '����ɼ���Ԫ', 'private-lq1000', '����ɼ���Ԫ');

/*==============================================================*/
/* ��ʼ��tbl_acq_item2group_conf����                                          */
/*==============================================================*/
insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1245, null, '����״̬', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1246, null, '��ͣ����', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1247, null, 'A�����', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1248, null, 'B�����', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1249, null, 'C�����', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1250, null, 'A���ѹ', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1251, null, 'B���ѹ', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1252, null, 'C���ѹ', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1253, null, '�й�����', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1254, null, '�޹�����', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1255, null, '�й�����', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1256, null, '�޹�����', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1257, null, '������', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1258, null, '��������', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1259, null, '��ѹ', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1260, null, '��ѹ', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1261, null, '��ѹ', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1262, null, '��������', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1263, null, '��Һ��', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1264, null, '��ˮ��', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1265, null, '��Ƶ����Ƶ��', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1266, null, '��Ƶ����Ƶ��', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1235, null, '��ͼ�ɼ����', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1236, null, '��ͼ���õ���', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1237, null, '��ͼʵ�����', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1238, null, '��ͼ�ɼ�ʱ��', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1239, null, '���', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1240, null, '���', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1241, null, '��ͼ����-λ��', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1242, null, '��ͼ����-�غ�', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1243, null, '��ͼ����-����', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1244, null, '��ͼ����-����', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1334, null, '�豸�ͺű�ʶλ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1335, null, '��Ƶ������״̬', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1336, null, '��Ƶ�����ϴ���', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1337, null, 'A���ѹ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1338, null, 'A�����', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1339, null, 'B���ѹ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1340, null, 'B�����', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1341, null, 'C���ѹ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1342, null, 'C�����', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1343, null, 'ƽ����ѹ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1344, null, 'ƽ������', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1345, null, '�ܹ���', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1346, null, '�ϼƹ�������', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1347, null, '��Ƶ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1348, null, '�ܵ���', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1349, null, '���ۼ�ʱ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1350, null, '�����¶�', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1351, null, '����ѹ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1352, null, '�����¶�', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1353, null, '����ѹ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1354, null, '�׹�ѹ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1355, null, '�����¶�', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1356, null, '���ƾ����¶�', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1357, null, '���ƾ���ѹ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1358, null, '���ƹ�����', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1359, null, '��������', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1360, null, '����ִ��״̬', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1361, null, 'Ƿѹ����ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1362, null, 'Ƿѹ��ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1363, null, '��ѹ����ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1364, null, '��ѹ��ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1365, null, 'Ƿ�ر���ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1366, null, 'Ƿ����ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1367, null, '���ر���ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1368, null, '������ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1369, null, '��ѹ��ƽ�Ᵽ��ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1370, null, '��ѹ��ƽ����ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1371, null, '������ƽ�Ᵽ��ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1372, null, '������ƽ����ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1373, null, '�����¶ȱ���ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1374, null, '�����¶ȱ�����ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1375, null, '����ѹ������ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1376, null, '����ѹ��������ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1377, null, '�����¶ȱ���ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1378, null, '�����¶ȱ�����ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1379, null, '���þ����¶ȱ���ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1380, null, '���þ����¶ȱ�����ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1381, null, '����ѹ������ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1382, null, '����ѹ��������ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1383, null, '���ƾ���ѹ������ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1384, null, '���ƾ���ѹ��������ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1385, null, 'Һ�汣��ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1386, null, 'Һ�汣����ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1387, null, '����Һ�汣��ֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1388, null, '����Һ�汣����ʱֵ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1389, null, '����ģʽ', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1390, null, '��Ъ����ʱ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1391, null, '��Ъͣ��ʱ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1392, null, 'Ŀ�꾮��ѹ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1393, null, '����Ŀ�꾮��ѹ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1394, null, 'Ŀ��Һ�����', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1395, null, '����Ŀ��Һ�����', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1396, null, '����汾��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1397, null, '����ѹ��', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1398, null, '����˲ʱ����', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1399, null, '�����ۼ�����', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1400, null, '˲ʱ����', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1267, null, '�豸�ͺű�ʶλ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1268, null, '��Ƶ������״̬', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1269, null, '��Ƶ�����ϴ���', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1270, null, 'A���ѹ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1271, null, 'A�����', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1272, null, 'B���ѹ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1273, null, 'B�����', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1274, null, 'C���ѹ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1275, null, 'C�����', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1276, null, 'ƽ����ѹ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1277, null, 'ƽ������', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1278, null, '�ܹ���', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1279, null, '�ϼƹ�������', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1280, null, '��Ƶ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1281, null, '�ܵ���', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1282, null, '���ۼ�ʱ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1283, null, '�����¶�', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1284, null, '����ѹ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1285, null, '�����¶�', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1286, null, '����ѹ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1287, null, '�׹�ѹ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1288, null, '�����¶�', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1289, null, '���ƾ����¶�', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1290, null, '���ƾ���ѹ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1291, null, '���ƹ�����', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1292, null, '��������', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1293, null, '����ִ��״̬', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1294, null, 'Ƿѹ����ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1295, null, 'Ƿѹ��ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1296, null, '��ѹ����ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1297, null, '��ѹ��ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1298, null, 'Ƿ�ر���ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1299, null, 'Ƿ����ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1300, null, '���ر���ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1301, null, '������ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1302, null, '��ѹ��ƽ�Ᵽ��ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1303, null, '��ѹ��ƽ����ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1304, null, '������ƽ�Ᵽ��ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1305, null, '������ƽ����ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1306, null, '�����¶ȱ���ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1307, null, '�����¶ȱ�����ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1308, null, '����ѹ������ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1309, null, '����ѹ��������ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1310, null, '�����¶ȱ���ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1311, null, '�����¶ȱ�����ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1312, null, '���þ����¶ȱ���ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1313, null, '���þ����¶ȱ�����ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1314, null, '����ѹ������ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1315, null, '����ѹ��������ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1316, null, '���ƾ���ѹ������ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1317, null, '���ƾ���ѹ��������ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1318, null, 'Һ�汣��ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1319, null, 'Һ�汣����ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1320, null, '����Һ�汣��ֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1321, null, '����Һ�汣����ʱֵ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1322, null, '����ģʽ', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1323, null, '��Ъ����ʱ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1324, null, '��Ъͣ��ʱ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1325, null, 'Ŀ�꾮��ѹ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1326, null, '����Ŀ�꾮��ѹ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1327, null, 'Ŀ��Һ�����', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1328, null, '����Ŀ��Һ�����', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1329, null, '����汾��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1330, null, '����ѹ��', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1331, null, '����˲ʱ����', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1332, null, '�����ۼ�����', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1333, null, '˲ʱ����', null, '0,0,0', 4);


/*==============================================================*/
/* ��ʼ��tbl_acq_group2unit_conf����                              */
/*==============================================================*/
insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, MATRIX, UNITID)
values (1, 1, '0,0,0', 1);

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, MATRIX, UNITID)
values (2, 2, '0,0,0', 1);

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, MATRIX, UNITID)
values (3, 1, '0,0,0', 2);

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, MATRIX, UNITID)
values (4, 2, '0,0,0', 3);

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, MATRIX, UNITID)
values (5, 3, '0,0,0', 5);

insert into TBL_ACQ_GROUP2UNIT_CONF (ID, GROUPID, MATRIX, UNITID)
values (6, 4, '0,0,0', 4);

/*==============================================================*/
/* ��ʼ��tbl_code����                                          */
/*==============================================================*/
insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (222, 'AnchoringState', 'δê��', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (223, 'AnchoringState', 'ê��', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (475, 'BJJB', '����', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (259, 'BJJB', 'һ������', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (260, 'BJJB', '��������', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (261, 'BJJB', '��������', '', null, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (506, 'BJJB', '����', '', null, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (262, 'BJJB1', '�ļ�����', '', null, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (250, 'BJLX', 'ͨ�ű���', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (251, 'BJLX', '�ɼ�����', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (252, 'BJLX', '��Ƶ��RFID����', '', null, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (253, 'BJLX', '��Ƶ����', '', null, '301', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (254, 'BJLX', 'RFID����', '', null, '302', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (255, 'BJLX', '��Ƶ��RFID����', '', null, '303', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (256, 'BJLX', '��������', '', null, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (257, 'BJLX', 'ƽ�ⱨ��', '', null, '500', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (258, 'BJLX', '�豸����', '', null, '600', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (81, 'BJLX', '�غɴ���������', '', null, '601', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (82, 'BJLX', 'ѹ������������', '', null, '602', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (83, 'BJLX', '�¶ȴ���������', '', null, '603', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (61, 'BJLX', '��������', '', null, '700', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (735, 'BJLX', '��α���', '', null, '800', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (795, 'BJLX', '����״̬����', '', null, '900', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (796, 'BJQJYS', '000000', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (797, 'BJQJYS', 'ffffff', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (798, 'BJQJYS', 'ffffff', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (799, 'BJQJYS', '000000', '', null, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (476, 'BJYS', '00ff00', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (477, 'BJYS', 'dc2828', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (478, 'BJYS', 'f09614', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (479, 'BJYS', 'fae600', '', null, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (507, 'BJYS', '#808080', '', null, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (804, 'BJYSTMD', '0', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (805, 'BJYSTMD', '1', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (806, 'BJYSTMD', '1', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (807, 'BJYSTMD', '1', '', null, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (271, 'BJZT', '����', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (272, 'BJZT', '����', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (696, 'BarrelType', '��Ͳ��', '', null, 'H', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (698, 'BarrelType', '��ϱ�', '', null, 'L', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (699, 'BarrelType', '���Ͳ,�������ܷ�����', '', null, 'P', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (700, 'BarrelType', '����Ͳ,�������ܷ�����', '', null, 'S', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (697, 'BarrelType', '����Ͳ,���ڽ�������', '', null, 'W', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (701, 'BarrelType', '���Ͳ,���ڽ�������,���������ƹ���', '', null, 'X', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (207, 'CCJZT', '����', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (208, 'CCJZT', '���', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (209, 'CCJZT', 'ͣ��', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (648, 'CURVETYPE', 'A���ѹ����', '', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (649, 'CURVETYPE', 'B���ѹ����', '', null, '102', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (650, 'CURVETYPE', 'C���ѹ����', '', null, '103', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (651, 'CURVETYPE', '����ƽ����ѹ����', '', null, '104', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (652, 'CURVETYPE', 'A���������', '', null, '105', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (653, 'CURVETYPE', 'B���������', '', null, '106', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (654, 'CURVETYPE', 'C���������', '', null, '107', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (655, 'CURVETYPE', '����ƽ����������', '', null, '108', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (656, 'CURVETYPE', 'A���й���������', '', null, '109', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (657, 'CURVETYPE', 'B���й���������', '', null, '110', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (658, 'CURVETYPE', 'C���й���������', '', null, '111', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (659, 'CURVETYPE', '�������й���������', '', null, '112', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (660, 'CURVETYPE', 'A���޹���������', '', null, '113', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (661, 'CURVETYPE', 'B���޹���������', '', null, '114', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (662, 'CURVETYPE', 'C���޹���������', '', null, '115', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (663, 'CURVETYPE', '�������޹���������', '', null, '116', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (664, 'CURVETYPE', 'A�����ڹ�������', '', null, '117', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (665, 'CURVETYPE', 'B�����ڹ�������', '', null, '118', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (666, 'CURVETYPE', 'C�����ڹ�������', '', null, '119', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (667, 'CURVETYPE', '���������ڹ�������', '', null, '120', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (668, 'CURVETYPE', 'A�๦����������', '', null, '121', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (669, 'CURVETYPE', 'B�๦����������', '', null, '122', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (670, 'CURVETYPE', 'C�๦����������', '', null, '123', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (671, 'CURVETYPE', '�����ۺϹ�����������', '', null, '124', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (672, 'CURVETYPE', '��ƵƵ������', '', null, '125', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (236, 'CYJLX', '������ͻ�', '', null, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (237, 'CYJLX', '�����ͳ��ͻ�', '', null, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (238, 'CYJLX', '˫¿ͷ���ͻ�', '', null, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (239, 'CYJLX', '��ƫ������ͻ�', '', null, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (240, 'CYJLX', '������س��ͻ�', '', null, '205', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (241, 'CYJLX', '��ʽƤ����', '', null, '306', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (242, 'CYJLX', '��ʽ������', '', null, '307', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (243, 'CYJLX', 'ֱ�������ͻ�', '', null, '308', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (692, 'CZDJ', '������', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (693, 'CZDJ', '������С', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (694, 'CZDJ', '�����ϴ�', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (695, 'CZDJ', '������', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (214, 'EJGJB', 'C����', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (215, 'EJGJB', 'D����', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (216, 'EJGJB', 'K����', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (217, 'EJGJB', 'H����', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (455, 'GKJGLY', '�������', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (456, 'GKJGLY', '�˹���Ԥ', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (840, 'GTLX', '����Excel', '.xls', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (716, 'GTLX', '����', '.t', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (717, 'GTLX', '����', '.g*', null, '102', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (718, 'GTLX', '��ʱ', '.crd', null, '103', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (719, 'GTLX', '��ɽ', '.gat', null, '104', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (720, 'GTLX', '����', '.', null, '105', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (721, 'GTLX', '����̩��', '.txt', null, '106', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (722, 'GTLX', '����˼̹', '.mdb', null, '107', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (723, 'GTLX', '������ӥ', '.gat', null, '108', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (724, 'GTLX', '����˼̹GT', '.gt', null, '109', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (725, 'GTLX', '����˹DKM', '.dkm', null, '110', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (726, 'GTLX', '����˹MDB', '.mdb', null, '111', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (727, 'GTLX', '��ʱ�Ʋ�', '.dat', null, '112', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (728, 'GTLX', '������DK', '.dk', null, '113', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (729, 'GTLX', '����MDB', '.mdb', null, '114', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (730, 'GTLX', '�����ķ�����MDB', '.mdb', null, '115', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (815, 'GTLX', '����Excel', '.xls', null, '121', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (1, 'HFBZ', '�п��Ϸ�', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (2, 'HFBZ', '�п��Ƿ�', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (3, 'HFBZ', '�޿��Ƿ�', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (480, 'JCLX', '���ͻ�����', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (481, 'JCLX', '�ݸ˱þ���', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (482, 'JCSBLX', '����RTU', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (483, 'JCSBLX', '��ͨ��', '', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (484, 'JCSBLX', 'ȼ�ͷ����', '', null, '102', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (485, 'JCSBLX', 'ȼ�������', '', null, '103', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (502, 'JCSBTB', 'BMap_Symbol_SHAPE_CIRCLE', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (503, 'JCSBTB', 'BMap_Symbol_SHAPE_CIRCLE', '', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (504, 'JCSBTB', 'BMap_Symbol_SHAPE_CIRCLE', '', null, '102', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (505, 'JCSBTB', 'BMap_Symbol_SHAPE_CIRCLE', '', null, '103', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (500, 'JCTB', '/images/icon/wellsite/wellsite0.png', '', 0, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (629, 'JCTB', '/images/icon/wellsite/wellsite2.png', '', 200, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (630, 'JCTB', '/images/icon/wellsite/wellsite3.png', '', 300, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (631, 'JCTB', '/images/icon/wellsite/wellsite4.png', '', 400, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (628, 'JCTB', '/images/icon/wellsite/wellsite1.png', '', 100, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (635, 'JCTB', '/images/icon/wellsite/wellsite3.png', '', 300, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (636, 'JCTB', '/images/icon/wellsite/wellsite4.png', '', 400, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (634, 'JCTB', '/images/icon/wellsite/wellsite2.png', '', 200, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (633, 'JCTB', '/images/icon/wellsite/wellsite1.png', '', 100, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (632, 'JCTB', '/images/icon/wellsite/wellsite2.png', '', 0, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (161, 'JLX', '�ɳ���', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (162, 'JLX', '�;�', '', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (163, 'JLX', '��ˮ��', '', null, '102', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (164, 'JLX', '��ϡ��', '', null, '103', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (165, 'JLX', '���;�', '', null, '104', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (166, 'JLX', '����', '', null, '111', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (167, 'JLX', '��������', '', null, '112', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (168, 'JLX', 'ú������', '', null, '113', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (169, 'JLX', 'ҳ����', '', null, '114', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (170, 'JLX', 'ע�뾮', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (171, 'JLX', 'עˮ��', '', null, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (172, 'JLX', 'ע�۾�', '', null, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (173, 'JLX', '��Ԫ��������', '', null, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (174, 'JLX', 'ע������', '', null, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (838, 'JSBZ', '�������ݶ�ȡʧ��', '', null, '-44', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (837, 'JSBZ', '�������ݽ���ʧ��', '', null, '-55', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (836, 'JSBZ', '������ɳ���', '', null, '-66', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (835, 'JSBZ', '�����쳣', '', null, '-77', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (839, 'JSBZ', '��Ӧ���ݱ���ʧ��', '', null, '-88', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (265, 'JSBZ', '����У�����', '', null, '-99', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (263, 'JSBZ', 'δ����', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (264, 'JSBZ', '����ɹ�', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (531, 'JTB', '/images/icon/well/well4.gif', '', 400, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (486, 'JTB', '/images/icon/well/well0.gif', '', 0, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (528, 'JTB', '/images/icon/well/well1.gif', '', 100, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (529, 'JTB', '/images/icon/well/well2.gif', '', 200, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (530, 'JTB', '/images/icon/well/well3.gif', '', 300, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (532, 'JTB', '/images/icon/well/well0.gif', '', 0, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (533, 'JTB', '/images/icon/well/well1.gif', '', 100, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (536, 'JTB', '/images/icon/well/well4.png', '', 400, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (535, 'JTB', '/images/icon/well/well3.gif', '', 300, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (534, 'JTB', '/images/icon/well/well2.gif', '', 200, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (537, 'JTB', '/images/icon/well/well0.gif', '', 0, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (538, 'JTB', '/images/icon/well/well1.gif', '', 100, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (539, 'JTB', '/images/icon/well/well2.gif', '', 200, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (540, 'JTB', '/images/icon/well/well3.gif', '', 300, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (542, 'JTB', '/images/icon/well/well4.png', '', 400, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (543, 'JTB', '/images/icon/well/pumpUnit0.gif', '', 0, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (546, 'JTB', '/images/icon/well/pumpUnit3.gif', '', 300, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (545, 'JTB', '/images/icon/well/pumpUnit2.gif', '', 200, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (544, 'JTB', '/images/icon/well/pumpUnit1.gif', '', 100, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (547, 'JTB', '/images/icon/well/pumpUnit4.png', '', 400, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (551, 'JTB', '/images/icon/well/well3.gif', '', 300, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (550, 'JTB', '/images/icon/well/well2.gif', '', 200, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (549, 'JTB', '/images/icon/well/well1.gif', '', 100, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (548, 'JTB', '/images/icon/well/well0.gif', '', 0, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (552, 'JTB', '/images/icon/well/well4.png', '', 400, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (556, 'JTB', '/images/icon/well/well3.gif', '', 300, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (555, 'JTB', '/images/icon/well/well2.gif', '', 200, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (554, 'JTB', '/images/icon/well/well1.gif', '', 100, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (553, 'JTB', '/images/icon/well/well0.gif', '', 0, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (557, 'JTB', '/images/icon/well/well4.png', '', 400, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (558, 'JTB', '/images/icon/well/well0.gif', '', 0, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (559, 'JTB', '/images/icon/well/well1.gif', '', 100, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (560, 'JTB', '/images/icon/well/well2.gif', '', 200, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (561, 'JTB', '/images/icon/well/well3.gif', '', 300, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (562, 'JTB', '/images/icon/well/well4.png', '', 400, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (566, 'JTB', '/images/icon/well/well1.gif', '', 300, '205', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (565, 'JTB', '/images/icon/well/well1.gif', '', 200, '205', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (564, 'JTB', '/images/icon/well/well1.gif', '', 100, '205', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (563, 'JTB', '/images/icon/well/well0.gif', '', 0, '205', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (567, 'JTB', '/images/icon/well/well1.gif', '', 400, '205', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (569, 'JTB', '/images/icon/well/well1.gif', '', 100, '206', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (568, 'JTB', '/images/icon/well/well0.gif', '', 0, '206', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (570, 'JTB', '/images/icon/well/well1.gif', '', 200, '206', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (571, 'JTB', '/images/icon/well/well1.gif', '', 300, '206', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (572, 'JTB', '/images/icon/well/well1.gif', '', 400, '206', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (574, 'JTB', '/images/icon/well/well1.gif', '', 100, '207', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (573, 'JTB', '/images/icon/well/well0.gif', '', 0, '207', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (575, 'JTB', '/images/icon/well/well1.gif', '', 200, '207', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (577, 'JTB', '/images/icon/well/well1.gif', '', 400, '207', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (576, 'JTB', '/images/icon/well/well1.gif', '', 300, '207', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (578, 'JTB', '/images/icon/well/well0.gif', '', 0, '208', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (579, 'JTB', '/images/icon/well/well1.gif', '', 100, '208', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (581, 'JTB', '/images/icon/well/well1.gif', '', 300, '208', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (580, 'JTB', '/images/icon/well/well1.gif', '', 200, '208', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (582, 'JTB', '/images/icon/well/well1.gif', '', 400, '208', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (585, 'JTB', '/images/icon/well/well1.gif', '', 200, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (586, 'JTB', '/images/icon/well/well1.gif', '', 300, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (587, 'JTB', '/images/icon/well/well1.gif', '', 400, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (583, 'JTB', '/images/icon/well/well0.gif', '', 0, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (584, 'JTB', '/images/icon/well/well1.gif', '', 100, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (590, 'JTB', '/images/icon/well/well1.gif', '', 200, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (591, 'JTB', '/images/icon/well/well1.gif', '', 300, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (589, 'JTB', '/images/icon/well/well1.gif', '', 100, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (588, 'JTB', '/images/icon/well/well0.gif', '', 0, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (592, 'JTB', '/images/icon/well/well1.gif', '', 400, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (594, 'JTB', '/images/icon/well/pcpump1.png', '', 100, '401', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (593, 'JTB', '/images/icon/well/pcpump0.png', '', 0, '401', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (595, 'JTB', '/images/icon/well/pcpump2.png', '', 200, '401', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (597, 'JTB', '/images/icon/well/pcpump4.png', '', 400, '401', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (596, 'JTB', '/images/icon/well/pcpump3.png', '', 300, '401', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (598, 'JTB', '/images/icon/well/well0.gif', '', 0, '402', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (599, 'JTB', '/images/icon/well/well1.gif', '', 100, '402', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (601, 'JTB', '/images/icon/well/well1.gif', '', 300, '402', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (600, 'JTB', '/images/icon/well/well1.gif', '', 200, '402', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (602, 'JTB', '/images/icon/well/well1.gif', '', 400, '402', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (606, 'JTB', '/images/icon/well/well1.gif', '', 300, '500', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (605, 'JTB', '/images/icon/well/well1.gif', '', 200, '500', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (604, 'JTB', '/images/icon/well/well1.gif', '', 100, '500', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (603, 'JTB', '/images/icon/well/well0.gif', '', 0, '500', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (607, 'JTB', '/images/icon/well/well1.gif', '', 400, '500', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (611, 'JTB', '/images/icon/well/well1.gif', '', 300, '600', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (610, 'JTB', '/images/icon/well/well1.gif', '', 200, '600', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (609, 'JTB', '/images/icon/well/well1.gif', '', 100, '600', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (608, 'JTB', '/images/icon/well/well0.gif', '', 0, '600', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (612, 'JTB', '/images/icon/well/well1.gif', '', 400, '600', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (616, 'JTB', '/images/icon/well/well1.gif', '', 300, '700', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (615, 'JTB', '/images/icon/well/well1.gif', '', 200, '700', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (614, 'JTB', '/images/icon/well/well1.gif', '', 100, '700', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (613, 'JTB', '/images/icon/well/well0.gif', '', 0, '700', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (617, 'JTB', '/images/icon/well/well1.gif', '', 400, '700', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (618, 'JTB', '/images/icon/well/well0.gif', '', 0, '701', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (619, 'JTB', '/images/icon/well/well1.gif', '', 100, '701', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (620, 'JTB', '/images/icon/well/well1.gif', '', 200, '701', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (621, 'JTB', '/images/icon/well/well1.gif', '', 300, '701', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (622, 'JTB', '/images/icon/well/well1.gif', '', 400, '701', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (623, 'JTB', '/images/icon/well/well0.gif', '', 0, '800', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (624, 'JTB', '/images/icon/well/well1.gif', '', 100, '800', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (625, 'JTB', '/images/icon/well/well1.gif', '', 200, '800', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (626, 'JTB', '/images/icon/well/well1.gif', '', 300, '800', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (627, 'JTB', '/images/icon/well/well1.gif', '', 400, '800', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (731, 'KTZT', '�ɵ�', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (732, 'KTZT', '���ڲ��ɵ�', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (733, 'KTZT', '���ⲻ�ɵ�', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (734, 'KTZT', '��ʩ��', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (331, 'LLJLX', '����վ������', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (332, 'LLJLX', 'Ӧ����������', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (333, 'LLJLX', '��������������', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (179, 'LiftingType', '����', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (180, 'LiftingType', '����', '', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (181, 'LiftingType', '���ͻ�', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (182, 'LiftingType', '������ͻ�', '', null, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (183, 'LiftingType', '�����ͳ��ͻ�', '', null, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (184, 'LiftingType', '˫¿ͷ���ͻ�', '', null, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (185, 'LiftingType', '��ƫ������ͻ�', '', null, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (186, 'LiftingType', '������س��ͻ�', '', null, '205', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (187, 'LiftingType', '��ʽƤ����', '', null, '206', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (188, 'LiftingType', '��ʽ������', '', null, '207', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (189, 'LiftingType', 'ֱ�������ͻ�', '', null, '208', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (190, 'LiftingType', '��Ǳ��', '', null, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (191, 'LiftingType', '�ݸ˱�', '', null, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (192, 'LiftingType', '�������ݸ˱�', '', null, '401', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (193, 'LiftingType', '�������ݸ˱�', '', null, '402', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (194, 'LiftingType', 'ˮ��������', '', null, '500', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (195, 'LiftingType', 'ˮ��������', '', null, '600', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (196, 'LiftingType', '����', '', null, '700', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (197, 'LiftingType', '��������', '', null, '701', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (311, 'LiftingType', '����', '', null, '800', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (391, 'MD_TYPE', '����ģ��', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (393, 'MD_TYPE', '����ģ��', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (234, 'NJYSJSXZ', '���γߴ����', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (235, 'NJYSJSXZ', '����˵��������', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (508, 'ORG_ICON', '/images/icon/org/org0.png', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (509, 'ORG_ICON', '/images/icon/org/org1.png', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (510, 'ORG_ICON', '/images/icon/org/org2.png', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (511, 'ORG_ICON', '/images/icon/index.png', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (512, 'ORG_ICON', '/images/icon/org/org4.png', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (513, 'ORG_ICON', '/images/icon/index.png', '', null, '5', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (514, 'ORG_ICON', '/images/icon/index.png', '', null, '6', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (515, 'ORG_ICON', '/images/icon/index.png', '', null, '7', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (371, 'ORG_TYPE', '����', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (372, 'ORG_TYPE', '�ּ�', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (373, 'ORG_TYPE', '����', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (374, 'ORG_TYPE', '��', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (375, 'ORG_TYPE', '�Ӽ�', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (376, 'ORG_TYPE', '����', '', null, '5', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (377, 'ORG_TYPE', '����վ', '', null, '6', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (378, 'ORG_TYPE', '����', '', null, '7', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (860, 'PROTOCOL', 'modbus-tcp', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (861, 'PROTOCOL', 'modbus-rtu', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (247, 'PumpGrade', 'I����', '�񼶱�', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (248, 'PumpGrade', 'II����', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (249, 'PumpGrade', 'III����', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (246, 'PumpType', '��ɰ����', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (245, 'PumpType', '��ʽ��', '', null, 'R', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (244, 'PumpType', '��ʽ��', '', null, 'T', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (198, 'QTLX', '˥��ʽ', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (199, 'QTLX', 'ˮ��', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (200, 'QTLX', '�ۺ�����', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (201, 'QTLX', '��Ԫ������', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (202, 'QTLX', '������', '', null, '5', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (203, 'QTLX', '��������', '', null, '6', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (204, 'QTLX', '������������й��', '', null, '7', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (431, 'ROLE_FLAG', '����', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (432, 'ROLE_FLAG', '��������', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (755, 'RuntimeEfficiencySource', '�˹�¼��', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (756, 'RuntimeEfficiencySource', '�������', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (757, 'RuntimeEfficiencySource', '�ɿ�ֱ��', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (775, 'RuntimeEfficiencySource', '���ݿ�ֱ��', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (205, 'SFPFCL', '������', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (206, 'SFPFCL', '����', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (218, 'SJGJB', 'C����', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (219, 'SJGJB', 'D����', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (220, 'SJGJB', 'K����', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (221, 'SJGJB', 'H����', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (351, 'SSGLDW', '�п���', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (352, 'SSGLDW', '������', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (175, 'SSJW', '1������', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (176, 'SSJW', '2������', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (177, 'SSZCDY', '1ע�ɵ�Ԫ', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (178, 'SSZCDY', '2ע�ɵ�Ԫ', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (21, 'USER_TITLE', '�п���', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (22, 'USER_TITLE', '����Ѳ��һ��', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (23, 'USER_TITLE', '����Ѳ�����', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (24, 'USER_TITLE', '����Ѳ������', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (25, 'USER_TITLE', '����Ѳ������', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (291, 'USER_TITLE', '����', '', null, '5', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (411, 'USER_TYPE', '���ݷ���Ա', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (412, 'USER_TYPE', 'ϵͳ����Ա', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (413, 'USER_TYPE', '���ݹ���Ա', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (688, 'XGXSDJ', '������', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (689, 'XGXSDJ', '�����ϴ�', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (690, 'XGXSDJ', '������С', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (691, 'XGXSDJ', '������', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (233, 'XZFX', '��ʱ��', '', null, 'Anticlockwise', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (232, 'XZFX', '˳ʱ��', '', null, 'Clockwise', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (210, 'YJGJB', 'C����', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (211, 'YJGJB', 'D����', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (212, 'YJGJB', 'K����', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (213, 'YJGJB', 'H����', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (224, 'ZRFS', '��ͳע��', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (225, 'ZRFS', '�ֲ�ע��', '', null, '2', '');

/*==============================================================*/
/* ��ʼ��tbl_rpc_statistics_conf����                                          */
/*==============================================================*/
insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (256, '0��2 t/d', null, 0.000, 2.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (257, '2��5 t/d', null, 2.010, 5.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (258, '5��10 t/d', null, 5.010, 10.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (259, '10��20 t/d', null, 10.010, 20.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (260, '����20 t/d', null, 20.000, 9999999.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (41, '����-20%', 1, -9999999.000, -20.010, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (42, '-20%��-10%', 2, -20.000, -10.010, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (43, '-10%��10%', 3, -10.000, 10.000, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (44, '10%��20%', 4, 10.010, 20.000, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (45, '����20 %', 5, 20.010, 9999999.000, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (271, '0��2 m^3/d', 1, 0.000, 2.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (272, '2��5 m^3/d', 2, 2.010, 5.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (273, '5��10 m^3/d', 3, 5.010, 10.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (274, '10��20 m^3/d', 4, 10.010, 20.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (275, '����20  m^3/d', 5, 20.000, 9999999.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (169, 'С��5%', null, 0.000, 5.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (170, '5%~10%', null, 5.010, 10.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (171, '10%~15%', null, 10.010, 15.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (172, '15%~20%', null, 15.010, 20.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (173, '20%~25%', null, 20.010, 25.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (174, '����25%', null, 25.010, 9999999.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (121, '����Ƿƽ��', 1564, 0.000, 70.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (122, 'Ƿƽ��', 1562, 70.010, 85.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (123, 'ƽ��', 1561, 85.010, 100.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (124, '��ƽ��', 1563, 100.010, 115.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (125, '���ع�ƽ��', 1565, 115.010, 9999999.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (163, 'С��5%', null, 0.000, 5.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (164, '5%~10%', null, 5.010, 10.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (165, '10%~15%', null, 10.010, 15.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (166, '15%~20%', null, 15.010, 20.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (167, '20%~25%', null, 20.010, 25.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (168, '����25.0%', null, 25.010, 9999999.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (52, '����Ƿƽ��', 1564, 0.000, 70.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (53, 'Ƿƽ��', 1562, 70.010, 85.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (54, 'ƽ��', 1561, 85.010, 100.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (55, '��ƽ��', 1563, 100.010, 115.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (56, '���ع�ƽ��', 1565, 115.010, 9999999.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (81, '0~50kW��h', 1, 0.000, 50.000, 'RYDL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (82, '50~100kW��h', 2, 50.010, 100.000, 'RYDL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (83, '100~200kW��h', 3, 100.010, 200.000, 'RYDL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (84, '����200kW��h', 4, 200.010, 9999999.000, 'RYDL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (61, '0~0.2', 1, 0.000, 0.200, 'SCSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (62, '0.2~0.4', 2, 0.201, 0.400, 'SCSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (63, '0.4~0.6', 3, 0.401, 0.600, 'SCSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (64, '0.6~0.8', 4, 0.601, 0.800, 'SCSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (65, '0.8~1', 5, 0.801, 1.000, 'SCSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (101, '0~0.2', 1, 0.000, 0.200, 'TXSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (102, '0.2~0.4', 2, 0.201, 0.400, 'TXSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (103, '0.4~0.4', 3, 0.401, 0.600, 'TXSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (104, '0.6~0.8', 4, 0.601, 0.800, 'TXSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (105, '0.8~1', 5, 0.801, 1.000, 'TXSL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (46, 'С��5%', 1, 0.000, 5.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (47, '5%~10%', 2, 5.010, 10.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (48, '10%~15%', 3, 10.010, 15.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (49, '15%~20%', 4, 15.010, 20.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (50, '20%~25%', 5, 20.010, 25.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (51, '����25%', 6, 25.010, 9999999.000, 'XTXL');

/*==============================================================*/
/* ��ʼ��tbl_rpc_worktype����                                          */
/*==============================================================*/
insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (123, 1100, '������', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (21, 1101, '����', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (22, 1102, '����', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (24, 1104, '����', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (26, 1201, '����', '"ͼ�����������غ��߸�����������״����Һ��ӽ��ھ��ڣ������ϸߣ��ӽ����������������"', ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (1, 1202, '����', '"ͼ�γ�ƽ���ı��Σ�����ϵ����0.6��"', ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (27, 1203, '��������', '"ͼ���Ľ����ԳʽǶȣ�����ж����ƽ�У�0.3�ܳ���ϵ����0.6��"', ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (28, 1204, '��Һ����', '"ͼ���Ľ����ԳʽǶȣ�0.1�ܳ���ϵ����0.3����û�Ƚϵ͡�"', '���򽵵ͳ��', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (29, 1205, '��Һ����', '"ͼ�����沿�ֳʽǶȣ�����ϵ����0.1��"', '���򽵵ͳ��', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (30, 1206, '���', '"ͼ�γ���״������Ϊ�㡣"', '���򽵵ͳ��', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (31, 1207, '���¶�', '"����ϵ����0.3����û�Ƚϸߡ�"', 'ϴ�����ҩ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (32, 1208, '����', '"ͼ�ο����������غ��ߣ�����ж�ع��̻�����ж����ƽ��������Ϊ�㡣"', '�����������', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (33, 1209, '��Ӱ��', '"ͼ���Ľ�Բ���ʻ��ȣ����ͱ�Խ�ߣ�Բ�������ʰ뾶Խ�����ػ���������ж���߲����ԡ�"', '�����������', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (34, 1210, '��϶©', '"ͼ���ϲ�����б��ͼ���ϡ����غ��߲�ƽ�С�"', '���', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (35, 1211, '�͹�©', '"ͼ�γ�����״��ͼ�����������غ��߸����������½���"', '�͹ܴ�ѹ����', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (36, 1212, '�ζ�����©ʧ', '"ͼ���ϲ���Բ����ȱ�����غ��ߵ����������غ��ߣ����ع��̻�����ж����ǰ��"', 'ϴ�������û���', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (37, 1213, '�̶�����©ʧ', '"ͼ���²���Բ����ȱ�����غ��߸����������غ��ߣ�������ǰ��ж�ع��̻�����"', 'ϴ�������û���', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (38, 1214, '˫����©ʧ', '"ͼ�γ���Բ״��ͼ�����ϡ��������غ���֮�䣻©ʧ����ʱ���;������͡�"', 'ϴ�������û���', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (39, 1215, '�ζ�����ʧ����͹�©', '"ͼ�γ�����״��ͼ�����������غ��߸���������Ϊ�㡣"', 'ϴ�������û���', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (40, 1216, '�̶�����ʧ��', '"ͼ�γ�����״��ͼ�����������غ��߸���������Ϊ�㡣"', 'ϴ�������û���', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (41, 1217, '˫����ʧ��', '"ͼ�γ�����״��ͼ�����ϡ��������غ���֮�䣻����Ϊ�㡣"', 'ϴ�������û���', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (42, 1218, '���������', '"ͼ�����Ͻ�͹��"', 'У�������豸', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (43, 1219, '����', '"ͼ�����½���β��"', '���ᣨ���󣩷����', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (44, 1220, '����δ���빤��Ͳ', '"ͼ�γ�����״��ͼ�����������غ��߸���������Ϊ�㡣"', '�·ţ���С�������', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (45, 1221, '�����ѳ�����Ͳ', '"ͼ���ұ�ȱ�����½���β�����ع�����ͻȻж�ء�"', '�·ţ���С�������', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (46, 1222, '�˶���', '"ͼ�γ�����״�����˼⣻ͼ�����������غ����·����ϸ�λ�þྮ��Խ��ͼ��Խ���ƣ�����ͻȻΪ�㡣"', '�滻���͸�', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (47, 1223, '�ˣ��ã���', '"ͼ�γ�б����״����ͨ��ͼ�ιյ��ҵ�����λ�ã��;������͡�"', 'ϴ������', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (48, 1224, '��΢����', '"ͼ�ηʴ󣻲����½���"', 'ϴ������', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (49, 1225, '���ؽ���', null, 'ϴ������', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (50, 1226, '��΢��ɰ', '"ͼ�γʲ����򡢲��ظ��ľ��״���;�����������"', '��ɰ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (51, 1227, '���س�ɰ', null, '��ɰ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (52, 1228, '��΢��ú��', null, '��ú��', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (53, 1229, '���س�ú��', null, '��ú��', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (54, 1230, '�����غɴ�', '"ͼ��˳ʱ��ƫת��"', '���ͳ��', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (55, 1231, 'Ӧ������', null, '�Ż����͸������', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (56, 1232, '�ɼ��쳣', null, '���ɼ��Ǳ�', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (143, 1301, 'ͣ��', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (23, 1302, 'ͣ��', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (104, 1303, 'ȱ��', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (107, 1304, '����ѹ', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (108, 1305, 'Ƿ��ѹ', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (105, 1306, '���ع���', null, '������˶���', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (106, 1307, 'Ƿ��', null, '�������˶��ѻ�Ƥ����', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (109, 1308, '���ش�', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (110, 1309, '΢��', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (146, 1310, '΢����', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (145, 1311, '�����ѹ������', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (144, 1312, '�������������', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (61, 1561, 'ƽ��', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (62, 1562, 'Ƿƽ��', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (63, 1563, '��ƽ��', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (64, 1564, '����Ƿƽ��', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (65, 1565, '���ع�ƽ��', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (83, 1566, '���ڲ��ɵ�', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (84, 1567, '���ⲻ�ɵ�', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (85, 1568, '��ʩ��', null, ' ', null);

/*==============================================================*/
/* ��ʼ��tbl_rpc_alarmtype_conf����                                          */
/*==============================================================*/
insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (123, 1100, 200, 300, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (21, 1101, 100, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (22, 1102, 100, 0, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (24, 1104, 900, 0, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (26, 1201, 400, 0, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (1, 1202, 400, 0, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (27, 1203, 400, 0, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (28, 1204, 400, 0, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (29, 1205, 400, 300, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (30, 1206, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (31, 1207, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (32, 1208, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (33, 1209, 400, 300, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (34, 1210, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (35, 1211, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (36, 1212, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (37, 1213, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (38, 1214, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (39, 1215, 400, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (40, 1216, 400, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (41, 1217, 400, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (42, 1218, 400, 300, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (43, 1219, 400, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (44, 1220, 400, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (45, 1221, 400, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (46, 1222, 400, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (47, 1223, 400, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (48, 1224, 400, 300, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (49, 1225, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (50, 1226, 400, 300, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (51, 1227, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (52, 1228, 400, 300, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (53, 1229, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (54, 1230, 400, 300, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (55, 1231, 400, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (56, 1232, 200, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (143, 1301, 800, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (23, 1302, 900, 300, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (103, 1303, 800, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (106, 1304, 800, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (107, 1305, 800, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (104, 1306, 800, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (105, 1307, 800, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (108, 1308, 800, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (109, 1309, 800, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (148, 1310, 800, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (149, 1311, 800, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (150, 1312, 800, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (61, 1561, 500, 0, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (62, 1562, 500, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (63, 1563, 500, 200, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (64, 1564, 500, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (65, 1565, 500, 100, 1, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (83, 1566, 500, 0, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (84, 1567, 500, 0, 0, '');

insert into tbl_rpc_alarmtype_conf (ID, RESULTCODE, ALARMTYPE, ALARMLEVEL, ALARMSIGN, REMARK)
values (85, 1568, 500, 0, 0, '');
