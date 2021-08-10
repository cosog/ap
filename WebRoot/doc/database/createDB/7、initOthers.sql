/*==============================================================*/
/* 初始化tbl_acq_group_conf数据                                          */
/*==============================================================*/
insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, ACQ_CYCLE, SAVE_CYCLE, PROTOCOL, REMARK)
values (1, 'group1', '抽油机离散数据', 300, 300, 'A11-Modbus', '运行状态、电参、频率、压力、温度等数据');

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, ACQ_CYCLE, SAVE_CYCLE, PROTOCOL, REMARK)
values (2, 'group2', '抽油机功图数据', 300, 300, 'A11-Modbus', '冲程、冲次以及位移、载荷、功率、电流曲线数据');

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, ACQ_CYCLE, SAVE_CYCLE, PROTOCOL, REMARK)
values (3, 'group3', '灵旗采集组', 60, 60, 'private-lq1000', '灵旗采集组');

insert into TBL_ACQ_GROUP_CONF (ID, GROUP_CODE, GROUP_NAME, ACQ_CYCLE, SAVE_CYCLE, PROTOCOL, REMARK)
values (4, 'group4', '科台斯采集组', 60, 60, 'private-kd93', '科台斯采集组');

/*==============================================================*/
/* 初始化tbl_acq_unit_conf数据                                    */
/*==============================================================*/
insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (1, 'unit1', '抽油机全部数据', 'A11-Modbus', '功图数据和离散数据');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (2, 'unit2', '抽油机离散数据', 'A11-Modbus', '运行状态、电参、频率、压力、温度等数据');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (3, 'unit3', '抽油机功图数据', 'A11-Modbus', '冲程、冲次以及位移、载荷、功率、电流曲线数据');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (4, 'unit4', '科台斯采集单元', 'private-kd93', '科台斯采集单元');

insert into TBL_ACQ_UNIT_CONF (ID, UNIT_CODE, UNIT_NAME, PROTOCOL, REMARK)
values (5, 'unit5', '灵旗采集单元', 'private-lq1000', '灵旗采集单元');

/*==============================================================*/
/* 初始化tbl_acq_item2group_conf数据                                          */
/*==============================================================*/
insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1245, null, '运行状态', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1246, null, '启停控制', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1247, null, 'A相电流', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1248, null, 'B相电流', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1249, null, 'C相电流', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1250, null, 'A相电压', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1251, null, 'B相电压', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1252, null, 'C相电压', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1253, null, '有功功耗', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1254, null, '无功功耗', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1255, null, '有功功率', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1256, null, '无功功率', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1257, null, '反向功率', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1258, null, '功率因数', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1259, null, '油压', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1260, null, '套压', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1261, null, '回压', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1262, null, '井口流温', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1263, null, '动液面', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1264, null, '含水率', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1265, null, '变频设置频率', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1266, null, '变频运行频率', null, '0,0,0', 1);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1235, null, '功图采集间隔', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1236, null, '功图设置点数', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1237, null, '功图实测点数', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1238, null, '功图采集时间', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1239, null, '冲次', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1240, null, '冲程', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1241, null, '功图数据-位移', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1242, null, '功图数据-载荷', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1243, null, '功图数据-电流', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1244, null, '功图数据-功率', null, '0,0,0', 2);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1334, null, '设备型号标识位', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1335, null, '变频器运行状态', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1336, null, '变频器故障代码', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1337, null, 'A相电压', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1338, null, 'A相电流', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1339, null, 'B相电压', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1340, null, 'B相电流', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1341, null, 'C相电压', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1342, null, 'C相电流', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1343, null, '平均电压', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1344, null, '平均电流', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1345, null, '总功率', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1346, null, '合计功率因数', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1347, null, '总频率', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1348, null, '总电能', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1349, null, '总累计时间', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1350, null, '井口温度', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1351, null, '井口压力', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1352, null, '井下温度', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1353, null, '井下压力', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1354, null, '套管压力', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1355, null, '柜内温度', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1356, null, '自制井下温度', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1357, null, '自制井下压力', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1358, null, '自制故障码', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1359, null, '保护开关', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1360, null, '保护执行状态', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1361, null, '欠压保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1362, null, '欠压延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1363, null, '过压保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1364, null, '过压延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1365, null, '欠载保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1366, null, '欠载延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1367, null, '过载保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1368, null, '过载延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1369, null, '电压不平衡保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1370, null, '电压不平衡延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1371, null, '电流不平衡保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1372, null, '电流不平衡延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1373, null, '井口温度保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1374, null, '井口温度保护延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1375, null, '井口压力保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1376, null, '井口压力保护延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1377, null, '井下温度保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1378, null, '井下温度保护延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1379, null, '内置井下温度保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1380, null, '内置井下温度保护延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1381, null, '井下压力保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1382, null, '井下压力保护延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1383, null, '自制井下压力保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1384, null, '自制井下压力保护延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1385, null, '液面保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1386, null, '液面保护延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1387, null, '自制液面保护值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1388, null, '自制液面保护延时值', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1389, null, '运行模式', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1390, null, '间歇运行时间', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1391, null, '间歇停机时间', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1392, null, '目标井下压力', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1393, null, '自制目标井下压力', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1394, null, '目标液面深度', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1395, null, '自制目标液面深度', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1396, null, '程序版本号', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1397, null, '气体压力', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1398, null, '气体瞬时流量', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1399, null, '气体累计流量', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1400, null, '瞬时排量', null, '0,0,0', 3);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1267, null, '设备型号标识位', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1268, null, '变频器运行状态', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1269, null, '变频器故障代码', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1270, null, 'A相电压', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1271, null, 'A相电流', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1272, null, 'B相电压', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1273, null, 'B相电流', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1274, null, 'C相电压', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1275, null, 'C相电流', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1276, null, '平均电压', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1277, null, '平均电流', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1278, null, '总功率', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1279, null, '合计功率因数', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1280, null, '总频率', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1281, null, '总电能', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1282, null, '总累计时间', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1283, null, '井口温度', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1284, null, '井口压力', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1285, null, '井下温度', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1286, null, '井下压力', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1287, null, '套管压力', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1288, null, '柜内温度', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1289, null, '自制井下温度', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1290, null, '自制井下压力', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1291, null, '自制故障码', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1292, null, '保护开关', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1293, null, '保护执行状态', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1294, null, '欠压保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1295, null, '欠压延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1296, null, '过压保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1297, null, '过压延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1298, null, '欠载保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1299, null, '欠载延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1300, null, '过载保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1301, null, '过载延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1302, null, '电压不平衡保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1303, null, '电压不平衡延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1304, null, '电流不平衡保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1305, null, '电流不平衡延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1306, null, '井口温度保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1307, null, '井口温度保护延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1308, null, '井口压力保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1309, null, '井口压力保护延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1310, null, '井下温度保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1311, null, '井下温度保护延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1312, null, '内置井下温度保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1313, null, '内置井下温度保护延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1314, null, '井下压力保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1315, null, '井下压力保护延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1316, null, '自制井下压力保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1317, null, '自制井下压力保护延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1318, null, '液面保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1319, null, '液面保护延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1320, null, '自制液面保护值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1321, null, '自制液面保护延时值', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1322, null, '运行模式', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1323, null, '间歇运行时间', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1324, null, '间歇停机时间', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1325, null, '目标井下压力', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1326, null, '自制目标井下压力', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1327, null, '目标液面深度', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1328, null, '自制目标液面深度', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1329, null, '程序版本号', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1330, null, '气体压力', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1331, null, '气体瞬时流量', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1332, null, '气体累计流量', null, '0,0,0', 4);

insert into TBL_ACQ_ITEM2GROUP_CONF (ID, ITEMID, ITEMNAME, ITEMCODE, MATRIX, GROUPID)
values (1333, null, '瞬时排量', null, '0,0,0', 4);


/*==============================================================*/
/* 初始化tbl_acq_group2unit_conf数据                              */
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
/* 初始化tbl_code数据                                          */
/*==============================================================*/
insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (222, 'AnchoringState', '未锚定', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (223, 'AnchoringState', '锚定', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (475, 'BJJB', '正常', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (259, 'BJJB', '一级报警', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (260, 'BJJB', '二级报警', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (261, 'BJJB', '三级报警', '', null, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (506, 'BJJB', '离线', '', null, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (262, 'BJJB1', '四级报警', '', null, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (250, 'BJLX', '通信报警', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (251, 'BJLX', '采集报警', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (252, 'BJLX', '视频和RFID报警', '', null, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (253, 'BJLX', '视频报警', '', null, '301', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (254, 'BJLX', 'RFID报警', '', null, '302', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (255, 'BJLX', '视频和RFID报警', '', null, '303', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (256, 'BJLX', '工况报警', '', null, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (257, 'BJLX', '平衡报警', '', null, '500', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (258, 'BJLX', '设备报警', '', null, '600', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (81, 'BJLX', '载荷传感器报警', '', null, '601', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (82, 'BJLX', '压力传感器报警', '', null, '602', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (83, 'BJLX', '温度传感器报警', '', null, '603', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (61, 'BJLX', '波动报警', '', null, '700', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (735, 'BJLX', '电参报警', '', null, '800', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (795, 'BJLX', '运行状态报警', '', null, '900', '');

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
values (271, 'BJZT', '正常', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (272, 'BJZT', '报警', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (696, 'BarrelType', '整筒泵', '', null, 'H', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (698, 'BarrelType', '组合泵', '', null, 'L', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (699, 'BarrelType', '厚壁筒,用于软密封柱塞', '', null, 'P', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (700, 'BarrelType', '薄壁筒,用于软密封柱塞', '', null, 'S', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (697, 'BarrelType', '薄壁筒,用于金属柱塞', '', null, 'W', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (701, 'BarrelType', '厚壁筒,用于金属柱塞,薄壁型螺纹构型', '', null, 'X', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (207, 'CCJZT', '运行', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (208, 'CCJZT', '间抽', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (209, 'CCJZT', '停井', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (648, 'CURVETYPE', 'A相电压曲线', '', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (649, 'CURVETYPE', 'B相电压曲线', '', null, '102', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (650, 'CURVETYPE', 'C相电压曲线', '', null, '103', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (651, 'CURVETYPE', '三相平均电压曲线', '', null, '104', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (652, 'CURVETYPE', 'A相电流曲线', '', null, '105', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (653, 'CURVETYPE', 'B相电流曲线', '', null, '106', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (654, 'CURVETYPE', 'C相电流曲线', '', null, '107', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (655, 'CURVETYPE', '三相平均电流曲线', '', null, '108', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (656, 'CURVETYPE', 'A相有功功率曲线', '', null, '109', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (657, 'CURVETYPE', 'B相有功功率曲线', '', null, '110', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (658, 'CURVETYPE', 'C相有功功率曲线', '', null, '111', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (659, 'CURVETYPE', '三相总有功功率曲线', '', null, '112', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (660, 'CURVETYPE', 'A相无功功率曲线', '', null, '113', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (661, 'CURVETYPE', 'B相无功功率曲线', '', null, '114', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (662, 'CURVETYPE', 'C相无功功率曲线', '', null, '115', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (663, 'CURVETYPE', '三相总无功功率曲线', '', null, '116', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (664, 'CURVETYPE', 'A相视在功率曲线', '', null, '117', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (665, 'CURVETYPE', 'B相视在功率曲线', '', null, '118', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (666, 'CURVETYPE', 'C相视在功率曲线', '', null, '119', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (667, 'CURVETYPE', '三相总视在功率曲线', '', null, '120', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (668, 'CURVETYPE', 'A相功率因数曲线', '', null, '121', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (669, 'CURVETYPE', 'B相功率因数曲线', '', null, '122', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (670, 'CURVETYPE', 'C相功率因数曲线', '', null, '123', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (671, 'CURVETYPE', '三相综合功率因数曲线', '', null, '124', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (672, 'CURVETYPE', '变频频率曲线', '', null, '125', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (236, 'CYJLX', '常规抽油机', '', null, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (237, 'CYJLX', '异相型抽油机', '', null, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (238, 'CYJLX', '双驴头抽油机', '', null, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (239, 'CYJLX', '下偏杠铃抽油机', '', null, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (240, 'CYJLX', '调径变矩抽油机', '', null, '205', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (241, 'CYJLX', '立式皮带机', '', null, '306', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (242, 'CYJLX', '立式链条机', '', null, '307', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (243, 'CYJLX', '直线驱抽油机', '', null, '308', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (692, 'CZDJ', '合理波动', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (693, 'CZDJ', '波动较小', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (694, 'CZDJ', '波动较大', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (695, 'CZDJ', '波动大', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (214, 'EJGJB', 'C级杆', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (215, 'EJGJB', 'D级杆', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (216, 'EJGJB', 'K级杆', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (217, 'EJGJB', 'H级杆', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (455, 'GKJGLY', '软件计算', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (456, 'GKJGLY', '人工干预', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (840, 'GTLX', '长庆Excel', '.xls', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (716, 'GTLX', '蚌埠', '.t', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (717, 'GTLX', '江汉', '.g*', null, '102', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (718, 'GTLX', '金时', '.crd', null, '103', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (719, 'GTLX', '凯山', '.gat', null, '104', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (720, 'GTLX', '三环', '.', null, '105', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (721, 'GTLX', '威尔泰克', '.txt', null, '106', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (722, 'GTLX', '西安思坦', '.mdb', null, '107', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (723, 'GTLX', '西安威鹰', '.gat', null, '108', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (724, 'GTLX', '西安思坦GT', '.gt', null, '109', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (725, 'GTLX', '俄罗斯DKM', '.dkm', null, '110', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (726, 'GTLX', '俄罗斯MDB', '.mdb', null, '111', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (727, 'GTLX', '金时计产', '.dat', null, '112', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (728, 'GTLX', '博铭达DK', '.dk', null, '113', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (729, 'GTLX', '金凯瑞MDB', '.mdb', null, '114', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (730, 'GTLX', '北京四方世纪MDB', '.mdb', null, '115', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (815, 'GTLX', '公用Excel', '.xls', null, '121', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (1, 'HFBZ', '有卡合法', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (2, 'HFBZ', '有卡非法', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (3, 'HFBZ', '无卡非法', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (480, 'JCLX', '抽油机井场', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (481, 'JCLX', '螺杆泵井场', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (482, 'JCSBLX', '井场RTU', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (483, 'JCSBLX', '多通阀', '', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (484, 'JCSBLX', '燃油发电机', '', null, '102', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (485, 'JCSBLX', '燃气发电机', '', null, '103', '');

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
values (161, 'JLX', '采出井', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (162, 'JLX', '油井', '', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (163, 'JLX', '掺水井', '', null, '102', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (164, 'JLX', '掺稀井', '', null, '103', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (165, 'JLX', '稠油井', '', null, '104', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (166, 'JLX', '气井', '', null, '111', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (167, 'JLX', '凝析气井', '', null, '112', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (168, 'JLX', '煤层气井', '', null, '113', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (169, 'JLX', '页岩气', '', null, '114', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (170, 'JLX', '注入井', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (171, 'JLX', '注水井', '', null, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (172, 'JLX', '注聚井', '', null, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (173, 'JLX', '三元复合驱井', '', null, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (174, 'JLX', '注蒸汽井', '', null, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (838, 'JSBZ', '请求数据读取失败', '', null, '-44', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (837, 'JSBZ', '请求数据解码失败', '', null, '-55', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (836, 'JSBZ', '井数许可超限', '', null, '-66', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (835, 'JSBZ', '计算异常', '', null, '-77', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (839, 'JSBZ', '相应数据编码失败', '', null, '-88', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (265, 'JSBZ', '数据校验错误', '', null, '-99', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (263, 'JSBZ', '未计算', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (264, 'JSBZ', '计算成功', '', null, '1', '');

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
values (731, 'KTZT', '可调', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (732, 'KTZT', '向内不可调', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (733, 'KTZT', '向外不可调', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (734, 'KTZT', '措施中', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (331, 'LLJLX', '集气站流量计', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (332, 'LLJLX', '应用型流量计', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (333, 'LLJLX', '处理中心流量计', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (179, 'LiftingType', '自喷', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (180, 'LiftingType', '泡排', '', null, '101', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (181, 'LiftingType', '抽油机', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (182, 'LiftingType', '常规抽油机', '', null, '201', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (183, 'LiftingType', '异相型抽油机', '', null, '202', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (184, 'LiftingType', '双驴头抽油机', '', null, '203', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (185, 'LiftingType', '下偏杠铃抽油机', '', null, '204', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (186, 'LiftingType', '调径变矩抽油机', '', null, '205', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (187, 'LiftingType', '立式皮带机', '', null, '206', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (188, 'LiftingType', '立式链条机', '', null, '207', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (189, 'LiftingType', '直线驱抽油机', '', null, '208', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (190, 'LiftingType', '电潜泵', '', null, '300', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (191, 'LiftingType', '螺杆泵', '', null, '400', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (192, 'LiftingType', '地面驱螺杆泵', '', null, '401', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (193, 'LiftingType', '井下驱螺杆泵', '', null, '402', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (194, 'LiftingType', '水力活塞泵', '', null, '500', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (195, 'LiftingType', '水力射流泵', '', null, '600', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (196, 'LiftingType', '气举', '', null, '700', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (197, 'LiftingType', '柱塞气举', '', null, '701', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (311, 'LiftingType', '其他', '', null, '800', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (391, 'MD_TYPE', '启用模块', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (393, 'MD_TYPE', '备用模块', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (234, 'NJYSJSXZ', '几何尺寸计算', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (235, 'NJYSJSXZ', '厂家说明书输入', '', null, '2', '');

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
values (371, 'ORG_TYPE', '集团', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (372, 'ORG_TYPE', '局级', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (373, 'ORG_TYPE', '厂级', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (374, 'ORG_TYPE', '矿级', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (375, 'ORG_TYPE', '队级', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (376, 'ORG_TYPE', '工区', '', null, '5', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (377, 'ORG_TYPE', '集气站', '', null, '6', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (378, 'ORG_TYPE', '其他', '', null, '7', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (860, 'PROTOCOL', 'modbus-tcp', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (861, 'PROTOCOL', 'modbus-rtu', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (247, 'PumpGrade', 'I级泵', 'Ⅰ级泵', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (248, 'PumpGrade', 'II级泵', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (249, 'PumpGrade', 'III级泵', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (246, 'PumpType', '防砂卡泵', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (245, 'PumpType', '杆式泵', '', null, 'R', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (244, 'PumpType', '管式泵', '', null, 'T', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (198, 'QTLX', '衰竭式', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (199, 'QTLX', '水驱', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (200, 'QTLX', '聚合物驱', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (201, 'QTLX', '三元复合驱', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (202, 'QTLX', '蒸汽驱', '', null, '5', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (203, 'QTLX', '蒸汽吞吐', '', null, '6', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (204, 'QTLX', '蒸汽辅助重力泄油', '', null, '7', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (431, 'ROLE_FLAG', '集团', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (432, 'ROLE_FLAG', '下属部门', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (755, 'RuntimeEfficiencySource', '人工录入', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (756, 'RuntimeEfficiencySource', '软件计算', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (757, 'RuntimeEfficiencySource', '采控直读', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (775, 'RuntimeEfficiencySource', '数据库直读', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (205, 'SFPFCL', '不劈分', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (206, 'SFPFCL', '劈分', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (218, 'SJGJB', 'C级杆', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (219, 'SJGJB', 'D级杆', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (220, 'SJGJB', 'K级杆', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (221, 'SJGJB', 'H级杆', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (351, 'SSGLDW', '中控室', '', null, '100', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (352, 'SSGLDW', '技术部', '', null, '200', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (175, 'SSJW', '1开井网', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (176, 'SSJW', '2开井网', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (177, 'SSZCDY', '1注采单元', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (178, 'SSZCDY', '2注采单元', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (21, 'USER_TITLE', '中控室', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (22, 'USER_TITLE', '工况巡检一组', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (23, 'USER_TITLE', '工况巡检二组', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (24, 'USER_TITLE', '工况巡检三组', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (25, 'USER_TITLE', '工况巡检四组', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (291, 'USER_TITLE', '其他', '', null, '5', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (411, 'USER_TYPE', '数据分析员', '', null, '0', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (412, 'USER_TYPE', '系统管理员', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (413, 'USER_TYPE', '数据管理员', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (688, 'XGXSDJ', '波动大', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (689, 'XGXSDJ', '波动较大', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (690, 'XGXSDJ', '波动较小', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (691, 'XGXSDJ', '合理波动', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (233, 'XZFX', '逆时针', '', null, 'Anticlockwise', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (232, 'XZFX', '顺时针', '', null, 'Clockwise', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (210, 'YJGJB', 'C级杆', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (211, 'YJGJB', 'D级杆', '', null, '2', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (212, 'YJGJB', 'K级杆', '', null, '3', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (213, 'YJGJB', 'H级杆', '', null, '4', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (224, 'ZRFS', '笼统注入', '', null, '1', '');

insert into tbl_code (ID, ITEMCODE, ITEMNAME, REMARK, STATE, ITEMVALUE, TABLECODE)
values (225, 'ZRFS', '分层注入', '', null, '2', '');

/*==============================================================*/
/* 初始化tbl_rpc_statistics_conf数据                                          */
/*==============================================================*/
insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (256, '0～2 t/d', null, 0.000, 2.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (257, '2～5 t/d', null, 2.010, 5.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (258, '5～10 t/d', null, 5.010, 10.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (259, '10～20 t/d', null, 10.010, 20.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (260, '大于20 t/d', null, 20.000, 9999999.000, 'CYL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (41, '大于-20%', 1, -9999999.000, -20.010, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (42, '-20%～-10%', 2, -20.000, -10.010, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (43, '-10%～10%', 3, -10.000, 10.000, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (44, '10%～20%', 4, 10.010, 20.000, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (45, '大于20 %', 5, 20.010, 9999999.000, 'CYLBD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (271, '0～2 m^3/d', 1, 0.000, 2.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (272, '2～5 m^3/d', 2, 2.010, 5.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (273, '5～10 m^3/d', 3, 5.010, 10.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (274, '10～20 m^3/d', 4, 10.010, 20.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (275, '大于20  m^3/d', 5, 20.000, 9999999.000, 'CYLF');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (169, '小于5%', null, 0.000, 5.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (170, '5%~10%', null, 5.010, 10.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (171, '10%~15%', null, 10.010, 15.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (172, '15%~20%', null, 15.010, 20.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (173, '20%~25%', null, 20.010, 25.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (174, '大于25%', null, 25.010, 9999999.000, 'DMXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (121, '严重欠平衡', 1564, 0.000, 70.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (122, '欠平衡', 1562, 70.010, 85.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (123, '平衡', 1561, 85.010, 100.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (124, '过平衡', 1563, 100.010, 115.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (125, '严重过平衡', 1565, 115.010, 9999999.000, 'GLPHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (163, '小于5%', null, 0.000, 5.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (164, '5%~10%', null, 5.010, 10.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (165, '10%~15%', null, 10.010, 15.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (166, '15%~20%', null, 15.010, 20.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (167, '20%~25%', null, 20.010, 25.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (168, '大于25.0%', null, 25.010, 9999999.000, 'JXXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (52, '严重欠平衡', 1564, 0.000, 70.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (53, '欠平衡', 1562, 70.010, 85.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (54, '平衡', 1561, 85.010, 100.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (55, '过平衡', 1563, 100.010, 115.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (56, '严重过平衡', 1565, 115.010, 9999999.000, 'PHD');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (81, '0~50kW·h', 1, 0.000, 50.000, 'RYDL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (82, '50~100kW·h', 2, 50.010, 100.000, 'RYDL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (83, '100~200kW·h', 3, 100.010, 200.000, 'RYDL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (84, '大于200kW·h', 4, 200.010, 9999999.000, 'RYDL');

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
values (46, '小于5%', 1, 0.000, 5.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (47, '5%~10%', 2, 5.010, 10.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (48, '10%~15%', 3, 10.010, 15.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (49, '15%~20%', 4, 15.010, 20.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (50, '20%~25%', 5, 20.010, 25.000, 'XTXL');

insert into tbl_rpc_statistics_conf (ID, S_LEVEL, S_CODE, S_MIN, S_MAX, S_TYPE)
values (51, '大于25%', 6, 25.010, 9999999.000, 'XTXL');

/*==============================================================*/
/* 初始化tbl_rpc_worktype数据                                          */
/*==============================================================*/
insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (123, 1100, '无数据', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (21, 1101, '离线', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (22, 1102, '在线', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (24, 1104, '运行', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (26, 1201, '抽喷', '"图形在下理论载荷线附近，呈条带状；动液面接近于井口；产量较高，接近或大于理论排量。"', ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (1, 1202, '正常', '"图形呈平行四边形；充满系数≥0.6。"', ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (27, 1203, '充满不足', '"图形四角明显呈角度；增、卸载线平行；0.3≤充满系数﹤0.6。"', ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (28, 1204, '供液不足', '"图形四角明显呈角度；0.1≤充满系数﹤0.3；沉没度较低。"', '间抽或降低冲次', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (29, 1205, '供液极差', '"图形下面部分呈角度；充满系数﹤0.1。"', '间抽或降低冲次', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (30, 1206, '抽空', '"图形呈条状；产量为零。"', '间抽或降低冲次', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (31, 1207, '泵下堵', '"充满系数﹤0.3；沉没度较高。"', '洗井或加药', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (32, 1208, '气锁', '"图形靠近上理论载荷线；增、卸载过程缓慢，卸载线平缓；产量为零。"', '合理控制气体', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (33, 1209, '气影响', '"图形四角圆滑呈弧度，气油比越高，圆弧的曲率半径越大；增载缓慢，增、卸载线不明显。"', '合理控制气体', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (34, 1210, '间隙漏', '"图形上部分倾斜；图形上、下载荷线不平行。"', '检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (35, 1211, '油管漏', '"图形呈条带状；图形在下理论载荷线附近；产量下降。"', '油管打压试验', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (36, 1212, '游动凡尔漏失', '"图形上部分圆滑、缺损；上载荷线低于上理论载荷线；增载过程缓慢，卸载提前。"', '洗井、碰泵或检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (37, 1213, '固定凡尔漏失', '"图形下部分圆滑、缺损；下载荷线高于下理论载荷线；增载提前，卸载过程缓慢。"', '洗井、碰泵或检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (38, 1214, '双凡尔漏失', '"图形呈椭圆状；图形在上、下理论载荷线之间；漏失严重时，油井不出油。"', '洗井、碰泵或检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (39, 1215, '游动凡尔失灵或油管漏', '"图形呈条带状；图形在下理论载荷线附近；产量为零。"', '洗井、碰泵或检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (40, 1216, '固定凡尔失灵', '"图形呈条带状；图形在上理论载荷线附近；产量为零。"', '洗井、碰泵或检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (41, 1217, '双凡尔失灵', '"图形呈条带状；图形在上、下理论载荷线之间；产量为零。"', '洗井、碰泵或检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (42, 1218, '上死点别、碰', '"图形右上角凸起。"', '校正井口设备', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (43, 1219, '碰泵', '"图形左下角拖尾。"', '上提（增大）防冲距', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (44, 1220, '柱塞未下入工作筒', '"图形呈条带状；图形在下理论载荷线附近；产量为零。"', '下放（缩小）防冲距', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (45, 1221, '柱塞脱出工作筒', '"图形右边缺损；右下角拖尾；增载过程中突然卸载。"', '下放（缩小）防冲距', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (46, 1222, '杆断脱', '"图形呈条带状，两端尖；图形在下理论载荷线下方，断杆位置距井口越近图形越下移；产量突然为零。"', '替换抽油杆', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (47, 1223, '杆（泵）卡', '"图形呈斜条带状；可通过图形拐点找到被卡位置；油井不出油。"', '洗井或检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (48, 1224, '轻微结蜡', '"图形肥大；产量下降。"', '洗井或检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (49, 1225, '严重结蜡', null, '洗井或检泵', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (50, 1226, '轻微出砂', '"图形呈不规则、不重复的锯齿状；油井出油正常。"', '防砂', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (51, 1227, '严重出砂', null, '防砂', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (52, 1228, '轻微出煤渣', null, '防煤渣', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (53, 1229, '严重出煤渣', null, '防煤渣', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (54, 1230, '惯性载荷大', '"图形顺时针偏转。"', '降低冲次', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (55, 1231, '应力超标', null, '优化抽油杆柱组合', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (56, 1232, '采集异常', null, '检查采集仪表', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (143, 1301, '停电', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (23, 1302, '停抽', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (104, 1303, '缺相', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (107, 1304, '过电压', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (108, 1305, '欠电压', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (105, 1306, '严重过载', null, '井卡或杆断脱', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (106, 1307, '欠载', null, '井卡、杆断脱或皮带断', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (109, 1308, '严重打滑', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (110, 1309, '微打滑', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (146, 1310, '微过载', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (145, 1311, '三相电压不均衡', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (144, 1312, '三相电流不均衡', null, null, null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (61, 1561, '平衡', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (62, 1562, '欠平衡', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (63, 1563, '过平衡', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (64, 1564, '严重欠平衡', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (65, 1565, '严重过平衡', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (83, 1566, '向内不可调', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (84, 1567, '向外不可调', null, ' ', null);

insert into TBL_RPC_WORKTYPE (ID, RESULTCODE, RESULTNAME, RESULTDESCRIPTION, OPTIMIZATIONSUGGESTION, REMARK)
values (85, 1568, '措施中', null, ' ', null);

/*==============================================================*/
/* 初始化tbl_rpc_alarmtype_conf数据                                          */
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
